package ru.estartsev.edms.web.screens.outgoingdocument;

import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.reports.gui.actions.RunReportAction;
import ru.estartsev.edms.entity.OutgoingDocument;

import javax.inject.Inject;

@UiController("edms_OutgoingDocument.browse")
@UiDescriptor("outgoing-document-browse.xml")
@LookupComponent("outgoingDocumentsTable")
@LoadDataBeforeShow
public class OutgoingDocumentBrowse extends StandardLookup<OutgoingDocument> {

    @Inject
    private Button printReportBtn;

    @Subscribe
    private void onInit(InitEvent event) {
        printReportBtn.setAction(new RunReportAction("report"));
    }
}