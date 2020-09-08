package ru.estartsev.edms.web.screens.organization;

import com.haulmont.cuba.gui.screen.*;
import ru.estartsev.edms.entity.Organization;

@UiController("edms_Organization.browse")
@UiDescriptor("organization-browse.xml")
@LookupComponent("organizationsTable")
@LoadDataBeforeShow
public class OrganizationBrowse extends StandardLookup<Organization> {
}