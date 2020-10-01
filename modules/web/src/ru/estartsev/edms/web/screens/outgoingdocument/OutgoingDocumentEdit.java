package ru.estartsev.edms.web.screens.outgoingdocument;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.entity.ProcTask;
import com.haulmont.bpm.gui.procactionsfragment.ProcActionsFragment;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.model.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.gui.util.OperationResult;
import com.haulmont.cuba.security.entity.User;
import org.slf4j.Logger;
import ru.estartsev.edms.entity.*;
import ru.estartsev.edms.service.entityServices.OutgoingDocumentService;

import javax.inject.Inject;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@UiController("edms_OutgoingDocument.edit")
@UiDescriptor("outgoing-document-edit.xml")
@EditedEntityContainer("outgoingDocumentDc")
@LoadDataBeforeShow
public class OutgoingDocumentEdit extends StandardEditor<OutgoingDocument> {

    private static final String PROCESS_CODE = "documentApproval";

    @Inject
    private OutgoingDocumentService outgoingDocumentService;

    @Inject
    private CollectionLoader<ProcAttachment> procAttachmentsDl;

    @Inject
    private CollectionLoader<ProcTask> procTasksDl;

    @Inject
    private InstanceContainer<OutgoingDocument> outgoingDocumentDc;

    @Inject
    private ProcActionsFragment procActionsFragment;

    @Inject
    private InstanceLoader<OutgoingDocument> outgoingDocumentDl;

    @Inject
    private TimeSource timeSource;

    @Inject
    private UserSessionSource userSessionSource;

    @Inject
    private TabSheet mainTabSheet;

    @Inject
    private FileUploadField addAttachmentField;

    @Inject
    private FileUploadingAPI fileUploadingAPI;

    @Inject
    private Notifications notifications;

    @Inject
    private DataManager dataManager;

    @Inject
    private Table<FileDescriptor> filesTable;

    @Inject
    private ExportDisplay exportDisplay;

    @Inject
    private Logger log;

    @Inject
    private Form processForm;

    @Inject
    private Form mainForm;

    @Inject
    private Form filesForm;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        outgoingDocumentDl.load();
        procAttachmentsDl.setParameter("entityId", outgoingDocumentDc.getItem().getId());
        procAttachmentsDl.load();
        procTasksDl.setParameter("entityId", outgoingDocumentDc.getItem().getId());
        procTasksDl.load();
        procActionsFragment.initializer()
                .standard()
                .setBeforeStartProcessPredicate(() -> {
                    if (commitChanges().getStatus() == OperationResult.Status.SUCCESS) {
                        setPredicate();
                        return true;
                    }
                    return false;
                })
                .init(PROCESS_CODE, getEditedEntity());

        OutgoingDocumentStatus documentStatus = getEditedEntity().getStatus();
        log.info("Entity status {}", documentStatus);
        if (documentStatus.equals(OutgoingDocumentStatus.NEW)) {
            enableMainAndFilesForm(true);
        } else if (documentStatus.equals(OutgoingDocumentStatus.ON_APPROVAL)) {
            enableMainAndFilesForm(false);
        } else if (documentStatus.equals(OutgoingDocumentStatus.ON_SIGNING)) {
            enableMainAndFilesForm(false);
            if (userSessionSource.getUserSession().getUser()
                    .equals(getEditedEntity().getExecutor().getUser())) {
                mainTabSheet.getTab("registrationTab").setEnabled(true);
                processForm.setEnabled(false);
            }
        } else if (documentStatus.equals(OutgoingDocumentStatus.ON_COMPLETION) &&
                userSessionSource.getUserSession().getUser()
                        .equals(getEditedEntity().getExecutor().getUser())) {
            enableMainAndFilesForm(true);
        } else if (documentStatus.equals(OutgoingDocumentStatus.REGISTERED)) {
            enableMainAndFilesForm(false);
            processForm.setEnabled(false);
        }
    }

    private void setPredicate() {
        ProcInstance procInstance = procActionsFragment.getProcInstance();
        OutgoingDocument outgoingDocument = getEditedEntity();
        Set<ProcActor> procActors = new HashSet<>();
        Worker executor = dataManager.load(Worker.class).view("worker-view-with-image")
                .id(outgoingDocument.getExecutor().getId())
                .one();
        User executorUser = executor.getUser();
        ProcActor initiatorProcActor = outgoingDocumentService
                .createProcActor("initiator", procInstance, executorUser);
        User managerUser = outgoingDocument
                .getExecutor()
                .getDepartment()
                .getDepartmentManager()
                .getUser();
        ProcActor managerProcActor = outgoingDocumentService
                .createProcActor("manager", procInstance, managerUser);
        procActors.add(initiatorProcActor);
        procActors.add(managerProcActor);
        if (outgoingDocument.getSigner() != null) {
            ProcActor signerProcActor = outgoingDocumentService
                    .createProcActor("signer", procInstance, outgoingDocument.getSigner().getUser());
            procActors.add(signerProcActor);
        }
        procInstance.setProcActors(procActors);
    }

    @Subscribe
    protected void onInitEntity(InitEntityEvent<OutgoingDocument> event) {
        OutgoingDocument document = event.getEntity();
        document.setDateCreation(timeSource.currentTimestamp());
        document.setAuthor(userSessionSource.getUserSession().getUser());
        document.setExecutor(outgoingDocumentService.getCurrentWorker());
        document.setStatus(OutgoingDocumentStatus.fromId(1));
    }

    @Subscribe
    protected void onBeforeClose(BeforeCloseEvent event) {
        if (getEditedEntity().getStatus().equals(OutgoingDocumentStatus.ON_SIGNING) && userSessionSource.getUserSession().getUser()
                .equals(getEditedEntity().getExecutor().getUser()) && getEditedEntity().getLogbook() == null) {
            notifications.create()
                    .withCaption("Укажите журнал регистрации")
                    .show();
            event.preventWindowClose();
            processForm.setEnabled(false);
        }
        getScreenData().loadAll();
    }

    @Subscribe
    protected void afterCommitChanges(AfterCommitChangesEvent event) {
        OutgoingDocument document = outgoingDocumentService.getCurrentOutgoingDocument(getEditedEntity().getId());
        LocalDate localDate = document.getDateCreation()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        document.setTitle(outgoingDocumentService.setTitleForNewDocument(document.getDocumentType(),
                document.getRegNumber(),
                localDate,
                document.getDestination().getShortTitle(),
                document.getTheme()));
        if (document.getStatus().equals(OutgoingDocumentStatus.ON_APPROVAL)) {
            document.setDateChange(timeSource.currentTimestamp());
        }
        if (document.getUpdateTs() != null) {
            document.setDateChange(document.getUpdateTs());
        }
        dataManager.commit(document);
    }

    private void enableMainAndFilesForm(boolean state) {
        mainForm.setEnabled(state);
        filesForm.setEnabled(state);
    }

    @Subscribe("logbookField")
    protected void onLogbookFieldValueChange(HasValue.ValueChangeEvent event) {
        OutgoingDocument document = getEditedEntity();
        Logbook currentLogbook = document.getLogbook();
        LocalDate localDate = document.getDateCreation()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        document.setRegNumber(outgoingDocumentService
                .setRegNumberFromTemplate(currentLogbook.getNumberFormat(), localDate,
                        currentLogbook.getNumberOfDigits()));
        document.setRegistrationDate(timeSource.currentTimestamp());
        processForm.setEnabled(true);
    }

    @Subscribe("actField")
    protected void onActFieldValueChange(HasValue.ValueChangeEvent event) {
        OutgoingDocument document = getEditedEntity();
        document.setSentToAct(timeSource.currentTimestamp());
    }

    @Subscribe("addAttachmentField")
    public void onUploadFieldFileUploadSucceed(FileUploadField.FileUploadSucceedEvent event) {
        File file = fileUploadingAPI.getFile(addAttachmentField.getFileId());
        if (file != null) {
            notifications.create()
                    .withCaption("Файл загружен в " + file.getAbsolutePath())
                    .show();
        }
        FileDescriptor fd = addAttachmentField.getFileDescriptor();
        try {
            fileUploadingAPI.putFileIntoStorage(addAttachmentField.getFileId(), fd);
        } catch (FileStorageException e) {
            log.error("Error saving file to FileStorage", e.fillInStackTrace());
            notifications.create()
                    .withCaption("При загрузке файла произошла ошибка")
                    .show();
        }
        List<FileDescriptor> filesList = getEditedEntity().getFile();
        if (filesList == null) {
            filesList = new ArrayList<>();
        }
        filesList.add(fd);
        getEditedEntity().setFile(filesList);
        dataManager.commit(fd);
        notifications.create()
                .withCaption("Uploaded file: " + addAttachmentField.getFileName())
                .show();
    }

    @Subscribe("addAttachmentField")
    public void onUploadFieldFileUploadError(UploadField.FileUploadErrorEvent event) {
        notifications.create()
                .withCaption("File upload error")
                .show();
    }

    public void downloadFile(Component source) {
        FileDescriptor fd = filesTable.getSingleSelected();
        if (fd != null) {
            exportDisplay.show(fd);
        }
    }
}