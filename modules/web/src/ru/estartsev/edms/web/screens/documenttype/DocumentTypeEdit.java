package ru.estartsev.edms.web.screens.documenttype;

import com.google.common.base.Strings;
import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.DocumentType;
import ru.estartsev.edms.service.EntityCodeCreateService;

import javax.inject.Inject;

@UiController("edms_DocumentType.edit")
@UiDescriptor("document-type-edit.xml")
@EditedEntityContainer("documentTypeDc")
@DialogMode(forceDialog = true)
@LoadDataBeforeShow
public class DocumentTypeEdit extends StandardEditor<DocumentType> {

    @Inject
    private EntityCodeCreateService entityCodeCreateService;

    @Subscribe
    protected void beforeCommitChanges(BeforeCommitChangesEvent event) {
        DocumentType documentType = getEditedEntity();
        if (Strings.isNullOrEmpty(documentType.getCode())) {
            documentType.setCode(entityCodeCreateService.createCode("ВД", "documentTypeSequence"));
        }
    }
}