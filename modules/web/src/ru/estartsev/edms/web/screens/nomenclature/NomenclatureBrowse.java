package ru.estartsev.edms.web.screens.nomenclature;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Nomenclature;

@UiController("edms_Nomenclature.browse")
@UiDescriptor("nomenclature-browse.xml")
@LookupComponent("nomenclaturesTable")
@LoadDataBeforeShow
public class NomenclatureBrowse extends StandardLookup<Nomenclature> {
}