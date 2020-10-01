package ru.estartsev.edms.web.screens.organization;

import com.google.common.base.Strings;
import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Organization;
import ru.estartsev.edms.service.EntityCodeCreateService;

import javax.inject.Inject;

@UiController("edms_Organization.edit")
@UiDescriptor("organization-edit.xml")
@EditedEntityContainer("organizationDc")
@DialogMode(forceDialog = true)
@LoadDataBeforeShow
public class OrganizationEdit extends StandardEditor<Organization> {

    @Inject
    private EntityCodeCreateService entityCodeCreateService;

    @Subscribe
    protected void beforeCommitChanges(BeforeCommitChangesEvent event) {
        Organization organization = getEditedEntity();
        if (Strings.isNullOrEmpty(organization.getCode())) {
            organization.setCode(entityCodeCreateService.createCode("ОРГ", "organizationSequence"));
        }
    }
}