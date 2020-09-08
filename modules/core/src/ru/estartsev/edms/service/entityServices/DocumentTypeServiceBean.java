package ru.estartsev.edms.service.entityServices;

import org.springframework.stereotype.Service;
import ru.estartsev.edms.core.EntityCodeCreator;

import javax.inject.Inject;

@Service(DocumentTypeService.NAME)
public class DocumentTypeServiceBean implements DocumentTypeService {

    @Inject
    EntityCodeCreator codeCreator;

    @Override
    public String createCode() {
        return codeCreator.createCode("ВД00000", "documentTypeSequence");
    }
}