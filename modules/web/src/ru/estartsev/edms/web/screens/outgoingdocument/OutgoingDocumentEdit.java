package ru.estartsev.edms.web.screens.outgoingdocument;

import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Logbook;
import ru.estartsev.edms.entity.OutgoingDocument;
import ru.estartsev.edms.service.entityServices.OutgoingDocumentService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;

@UiController("edms_OutgoingDocument.edit")
@UiDescriptor("outgoing-document-edit.xml")
@EditedEntityContainer("outgoingDocumentDc")
@LoadDataBeforeShow
public class OutgoingDocumentEdit extends StandardEditor<OutgoingDocument> {

    @Inject
    OutgoingDocumentService outgoingDocumentService;


    @Subscribe
    protected void onInitEntity(InitEntityEvent<OutgoingDocument> event) {
        OutgoingDocument document = event.getEntity();
        document.setDateCreation(AppBeans.get(TimeSource.class).currentTimestamp());
        document.setAuthor(AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        document.setExecutor(outgoingDocumentService.getCurrentWorker());
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
        document.setRegistrationDate(AppBeans.get(TimeSource.class).currentTimestamp());
    }

    @Subscribe("actField")
    protected void onActFieldValueChange(HasValue.ValueChangeEvent event) {
        OutgoingDocument document = getEditedEntity();
        document.setSentToAct(AppBeans.get(TimeSource.class).currentTimestamp());
    }
}