package ru.estartsev.edms.web.screens.documenttype;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.DocumentType;

@UiController("edms_DocumentType.browse")
@UiDescriptor("document-type-browse.xml")
@LookupComponent("documentTypesTable")
@LoadDataBeforeShow
public class DocumentTypeBrowse extends StandardLookup<DocumentType> {
}