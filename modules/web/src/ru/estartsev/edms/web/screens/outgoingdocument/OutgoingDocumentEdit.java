package ru.estartsev.edms.web.screens.outgoingdocument;

import com.haulmont.bpm.entity.ProcAttachment;
import com.haulmont.bpm.gui.procactionsfragment.ProcActionsFragment;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.app.core.file.FileDownloadHelper;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstanceLoader;
import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.*;
import ru.estartsev.edms.service.entityServices.OutgoingDocumentService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;

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
    private Table<ProcAttachment> attachmentsTable;

    @Inject
    private InstanceLoader<OutgoingDocument> outgoingDocumentDl;

    @Inject
    private TimeSource timeSource;

    @Inject
    private UserSessionSource userSessionSource;

    @Inject
    TabSheet mainTabSheet;

    @Subscribe
    private void onBeforeShow(BeforeShowEvent event) {
        outgoingDocumentDl.load();
        procAttachmentsDl.setParameter("entityId", outgoingDocumentDc.getItem().getId());
        procAttachmentsDl.load();
        procActionsFragment.initializer()
                .standard()
                .init(PROCESS_CODE, getEditedEntity());

        FileDownloadHelper.initGeneratedColumn(attachmentsTable, "file");

        if (getEditedEntity().getStatus().getId().equals(3)){
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
    protected void beforeCommitChanges(BeforeCommitChangesEvent event) {
        OutgoingDocument document = getEditedEntity();
        LocalDate localDate = document.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        document.setTitle(outgoingDocumentService.setTitleForNewDocument(document.getDocumentType(), document.getRegNumber(),
                localDate, document.getDestination().getShortTitle(), document.getTheme()));
    }

    @Subscribe("logbookField")
    protected void onLogbookFieldValueChange(HasValue.ValueChangeEvent event) {
        OutgoingDocument document = getEditedEntity();
        Logbook currentLogbook = document.getLogbook();
        LocalDate localDate = document.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        document.setRegNumber(outgoingDocumentService.setRegNumberFromTemplate(currentLogbook.getNumberFormat(), localDate,
                currentLogbook.getNumberOfDigits()));
        document.setRegistrationDate(timeSource.currentTimestamp());
    }

    @Subscribe("actField")
    protected void onActFieldValueChange(HasValue.ValueChangeEvent event) {
        OutgoingDocument document = getEditedEntity();
        document.setSentToAct(timeSource.currentTimestamp());
    }
}