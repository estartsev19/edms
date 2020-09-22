package ru.estartsev.edms.web.screens.outgoingdocument;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.bpm.entity.ProcInstance;
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
    OutgoingDocumentService outgoingDocumentService;

    @Inject
    private CollectionLoader<ProcAttachment> procAttachmentsDl;

    @Inject
    private InstanceContainer<OutgoingDocument> outgoingDocumentDc;

    @Inject
    protected ProcActionsFragment procActionsFragment;

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


    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        outgoingDocumentDl.load();
        procAttachmentsDl.setParameter("entityId", outgoingDocumentDc.getItem().getId());
        procAttachmentsDl.load();
        procActionsFragment.initializer()
                .standard()
                .setBeforeStartProcessPredicate(() -> {
                    if (commitChanges().getStatus() == OperationResult.Status.SUCCESS) {
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
                        return true;
                    }
                    return false;
                })
                .init(PROCESS_CODE, getEditedEntity());
        int entityStatus = getEditedEntity().getStatus().getId();
        log.info("EditedEntityStatus = {}", entityStatus);
        if (entityStatus == 3) {
            mainTabSheet.getTab("registrationTab").setEnabled(true);
        }
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
    protected void afterCommitChanges(AfterCommitChangesEvent event) {
        OutgoingDocument document = dataManager.load(OutgoingDocument.class)
                .query("select e from edms_OutgoingDocument e where e.id = :id")
                .parameter("id", getEditedEntity().getId())
                .view("outgoingDocument-editView")
                .one();
        LocalDate localDate = document.getDateCreation()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        document.setTitle(outgoingDocumentService.setTitleForNewDocument(document.getDocumentType(),
                document.getRegNumber(),
                localDate,
                document.getDestination().getShortTitle(),
                document.getTheme()));
        if (document.getStatus().getId() == 2) {
            document.setDateChange(timeSource.currentTimestamp());
        }
        document.setReconcilationStartDate(procActionsFragment
                .getProcInstance()
                .getStartDate());
        Date endDate = procActionsFragment
                .getProcInstance()
                .getEndDate();
        if (endDate != null) {
            document.setReconcilationCompleteDate(endDate);
        }
        if (document.getUpdateTs() != null){
            document.setDateChange(document.getUpdateTs());
        }
        dataManager.commit(document);
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
                    .withCaption("File is uploaded to temporary storage at " + file.getAbsolutePath())
                    .show();
        }
        FileDescriptor fd = addAttachmentField.getFileDescriptor();
        try {
            fileUploadingAPI.putFileIntoStorage(addAttachmentField.getFileId(), fd);
        } catch (FileStorageException e) {
            throw new RuntimeException("Error saving file to FileStorage", e);
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