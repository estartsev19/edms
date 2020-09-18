package ru.estartsev.edms.service.entityServices;

import org.springframework.stereotype.Service;
import ru.estartsev.edms.core.EntityCodeCreator;
import ru.estartsev.edms.service.OrganizationService;

import javax.inject.Inject;

@Service(OrganizationService.NAME)
public class OrganizationServiceBean implements OrganizationService {

    @Inject
    EntityCodeCreator codeCreator;

    public String createCode(){
        return codeCreator.createCode("ОРГ", "organizationSequence");
    }
}