package ru.estartsev.edms.web.screens.outgoingdocument;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.OutgoingDocument;

@UiController("edms_OutgoingDocument.browse")
@UiDescriptor("outgoing-document-browse.xml")
@LookupComponent("outgoingDocumentsTable")
@LoadDataBeforeShow
public class OutgoingDocumentBrowse extends StandardLookup<OutgoingDocument> {
}