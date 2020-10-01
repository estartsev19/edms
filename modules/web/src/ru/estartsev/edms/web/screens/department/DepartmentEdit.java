package ru.estartsev.edms.web.screens.department;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Department;

import javax.inject.Inject;

@UiController("edms_Department.edit")
@UiDescriptor("department-edit.xml")
@EditedEntityContainer("departmentDc")
@LoadDataBeforeShow
public class DepartmentEdit extends StandardEditor<Department> {

    @Inject
    private Notifications notifications;

    @Subscribe
    protected void onBeforeCommit(BeforeCommitChangesEvent event) {
        Department department = getEditedEntity();
        if (department.getLeadDepartment() != null && department.getLeadDepartment().getCode().equals(department.getCode())) {
                notifications.create()
                        .withCaption("Текущее подразделение не может быть ведущим подразделением")
                        .show();
                event.preventCommit();
        }
    }
}