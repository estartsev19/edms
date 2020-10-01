package ru.estartsev.edms.web.screens.department;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.screen.*;
import org.slf4j.Logger;
import ru.estartsev.edms.entity.Department;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@UiController("edms_Department.edit")
@UiDescriptor("department-edit.xml")
@EditedEntityContainer("departmentDc")
@DialogMode(forceDialog = true)
@LoadDataBeforeShow
public class DepartmentEdit extends StandardEditor<Department> {

    @Inject
    private Notifications notifications;

    @Subscribe
    protected void onBeforeCommit(BeforeCommitChangesEvent event) {
        Department department = getEditedEntity();
        boolean hasCycle = false;
        Set<Department> leadDepartments = new HashSet<>();
        if (department.getLeadDepartment() != null && department.getLeadDepartment().equals(department)) {
            hasCycle = true;
        }
        if (department.getLeadDepartment() != null) {
            Department leadDepartment = department.getLeadDepartment();
            while (leadDepartment.getLeadDepartment() != null) {
                leadDepartment = leadDepartment.getLeadDepartment();
                leadDepartments.add(leadDepartment);
                if (leadDepartments.contains(department)) {
                    hasCycle = true;
                    break;
                }
            }
        }
        if (hasCycle) {
            notifications.create()
                    .withCaption("Указанное подразделение не может быть ведущим подразделением")
                    .show();
            event.preventCommit();
        }
    }
}