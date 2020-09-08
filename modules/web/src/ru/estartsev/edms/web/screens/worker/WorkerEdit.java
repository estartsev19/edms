package ru.estartsev.edms.web.screens.worker;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.security.entity.User;
import ru.estartsev.edms.entity.Worker;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.File;

@UiController("edms_Worker.edit")
@UiDescriptor("worker-edit.xml")
@EditedEntityContainer("workerDc")
@LoadDataBeforeShow
public class WorkerEdit extends StandardEditor<Worker> {

    @Inject
    protected PickerField<User> userField;

    @Subscribe("userField")
    protected void onUserFieldValueChange(HasValue.ValueChangeEvent<User> event) {
        User user = event.getValue();
        Worker worker = getEditedEntity();
        if (user.getFirstName() != null) {
            worker.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            worker.setLastName(user.getLastName());
        }
    }

}