package ru.estartsev.edms.web.screens.logbook;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Logbook;

@UiController("edms_Logbook.browse")
@UiDescriptor("logbook-browse.xml")
@LookupComponent("logbooksTable")
@LoadDataBeforeShow
public class LogbookBrowse extends StandardLookup<Logbook> {
}