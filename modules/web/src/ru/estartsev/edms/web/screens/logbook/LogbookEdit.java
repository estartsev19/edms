package ru.estartsev.edms.web.screens.logbook;

import com.google.common.base.Strings;
import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Logbook;
import ru.estartsev.edms.service.EntityCodeCreateService;

import javax.inject.Inject;

@UiController("edms_Logbook.edit")
@UiDescriptor("logbook-edit.xml")
@EditedEntityContainer("logbookDc")
@LoadDataBeforeShow
public class LogbookEdit extends StandardEditor<Logbook> {

    @Inject
    private EntityCodeCreateService entityCodeCreateService;

    @Subscribe
    protected void beforeCommitChanges(BeforeCommitChangesEvent event){
        Logbook logbook = getEditedEntity();
        if (Strings.isNullOrEmpty(logbook.getCode())) {
            logbook.setCode(entityCodeCreateService.createCode("Ж", "logbookSequence"));
        }
    }
}