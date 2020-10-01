package ru.estartsev.edms.web.screens.nomenclature;

import com.google.common.base.Strings;
import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Nomenclature;
import ru.estartsev.edms.service.EntityCodeCreateService;

import javax.inject.Inject;

@UiController("edms_Nomenclature.edit")
@UiDescriptor("nomenclature-edit.xml")
@EditedEntityContainer("nomenclatureDc")
@LoadDataBeforeShow
public class NomenclatureEdit extends StandardEditor<Nomenclature> {

    @Inject
    private EntityCodeCreateService entityCodeCreateService;

    @Subscribe
    protected void beforeCommitChanges(BeforeCommitChangesEvent event) {
        Nomenclature nomenclature = getEditedEntity();
        if (Strings.isNullOrEmpty(nomenclature.getCode())) {
            nomenclature.setCode(entityCodeCreateService.createCode("НД", "nomenclatureService"));
        }
    }
}