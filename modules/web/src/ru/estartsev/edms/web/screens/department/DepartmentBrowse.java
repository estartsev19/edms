package ru.estartsev.edms.web.screens.department;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Department;

@UiController("edms_Department.browse")
@UiDescriptor("department-browse.xml")
@LookupComponent("departmentsTable")
@LoadDataBeforeShow
public class DepartmentBrowse extends StandardLookup<Department> {
}