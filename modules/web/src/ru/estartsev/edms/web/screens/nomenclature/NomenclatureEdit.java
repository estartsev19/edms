package ru.estartsev.edms.web.screens.nomenclature;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Logbook;
import ru.estartsev.edms.entity.Nomenclature;
import ru.estartsev.edms.service.entityServices.NomenclatureService;

import javax.inject.Inject;

@UiController("edms_Nomenclature.edit")
@UiDescriptor("nomenclature-edit.xml")
@EditedEntityContainer("nomenclatureDc")
@LoadDataBeforeShow
public class NomenclatureEdit extends StandardEditor<Nomenclature> {

    @Inject
    NomenclatureService nomenclatureService;

    @Subscribe
    protected void beforeCommitChanges(BeforeCommitChangesEvent event) {
        Nomenclature nomenclature = getEditedEntity();
        if (nomenclature.getCode() == null) {
            nomenclature.setCode(nomenclatureService.createCode());
        }
    }
}