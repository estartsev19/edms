package ru.estartsev.edms.web.screens.organization;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Organization;
import ru.estartsev.edms.service.OrganizationService;

import javax.inject.Inject;

@UiController("edms_Organization.edit")
@UiDescriptor("organization-edit.xml")
@EditedEntityContainer("organizationDc")
@LoadDataBeforeShow
public class OrganizationEdit extends StandardEditor<Organization> {

    @Inject
    OrganizationService organizationService;

    @Subscribe
    protected void beforeCommitChanges(BeforeCommitChangesEvent event) {
        Organization organization = getEditedEntity();
        if (organization.getCode() == null) {
            organization.setCode(organizationService.createCode());
        }
    }
}