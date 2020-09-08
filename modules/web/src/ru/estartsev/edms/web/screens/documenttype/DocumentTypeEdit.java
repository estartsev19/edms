package ru.estartsev.edms.web.screens.documenttype;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.DocumentType;
import ru.estartsev.edms.entity.Logbook;
import ru.estartsev.edms.service.entityServices.DocumentTypeService;

import javax.inject.Inject;

@UiController("edms_DocumentType.edit")
@UiDescriptor("document-type-edit.xml")
@EditedEntityContainer("documentTypeDc")
@LoadDataBeforeShow
public class DocumentTypeEdit extends StandardEditor<DocumentType> {

    @Inject
    DocumentTypeService documentTypeService;

    @Subscribe
    protected void beforeCommitChanges(BeforeCommitChangesEvent event) {
        DocumentType documentType = getEditedEntity();
        if (documentType.getCode() == null) {
            documentType.setCode(documentTypeService.createCode());
        }
    }
}