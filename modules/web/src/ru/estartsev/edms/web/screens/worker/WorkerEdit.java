package ru.estartsev.edms.web.screens.worker;

import com.google.common.base.Strings;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.security.entity.User;
import ru.estartsev.edms.entity.Worker;

@UiController("edms_Worker.edit")
@UiDescriptor("worker-edit.xml")
@EditedEntityContainer("workerDc")
@DialogMode(forceDialog = true)
@LoadDataBeforeShow
public class WorkerEdit extends StandardEditor<Worker> {

    @Subscribe("userField")
    protected void onUserFieldValueChange(HasValue.ValueChangeEvent<User> event) {
        User user = event.getValue();
        Worker worker = getEditedEntity();
        if (user != null) {
            if (!Strings.isNullOrEmpty(user.getFirstName())) {
                worker.setFirstName(user.getFirstName());
            }
            if (!Strings.isNullOrEmpty(user.getLastName())) {
                worker.setLastName(user.getLastName());
            }
            if (!Strings.isNullOrEmpty(user.getMiddleName())) {
                worker.setPatronymic(user.getMiddleName());
            }
            if (!Strings.isNullOrEmpty(user.getEmail())) {
                worker.setEmail(user.getEmail());
            }
        }
    }
}