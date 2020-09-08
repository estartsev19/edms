package ru.estartsev.edms.web.screens.outgoingdocument;

import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.OutgoingDocument;
import ru.estartsev.edms.service.entityServices.OutgoingDocumentService;

import javax.inject.Inject;

@UiController("edms_OutgoingDocument.edit")
@UiDescriptor("outgoing-document-edit.xml")
@EditedEntityContainer("outgoingDocumentDc")
@LoadDataBeforeShow
public class OutgoingDocumentEdit extends StandardEditor<OutgoingDocument> {

    @Inject
    OutgoingDocumentService outgoingDocumentService;


    @Subscribe
    protected void onInitEntity(InitEntityEvent<OutgoingDocument> event){
        OutgoingDocument document = event.getEntity();
        document.setDateCreation(AppBeans.get(TimeSource.class).currentTimestamp());
        document.setAuthor(AppBeans.get(UserSessionSource.class).getUserSession().getUser());
        document.setExecutor(outgoingDocumentService.getCurrentWorker());
    }
}