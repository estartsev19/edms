package ru.estartsev.edms.service;

import javax.inject.Inject;

public interface OrganizationService {
    String NAME = "edms_OrganizationService";

    public String createCode();
}