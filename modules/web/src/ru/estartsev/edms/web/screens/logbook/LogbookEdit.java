package ru.estartsev.edms.web.screens.logbook;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Logbook;
import ru.estartsev.edms.service.entityServices.LogbookService;

import javax.inject.Inject;

@UiController("edms_Logbook.edit")
@UiDescriptor("logbook-edit.xml")
@EditedEntityContainer("logbookDc")
@LoadDataBeforeShow
public class LogbookEdit extends StandardEditor<Logbook> {

    @Inject
    LogbookService logbookService;

    @Subscribe
    protected void beforeCommitChanges(BeforeCommitChangesEvent event){
        Logbook logbook = getEditedEntity();
        if (logbook.getCode() == null) {
            logbook.setCode(logbookService.createCode());
        }
    }
}