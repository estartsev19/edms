package ru.estartsev.edms.service.entityServices;

import org.springframework.stereotype.Service;
import ru.estartsev.edms.core.EntityCodeCreator;

import javax.inject.Inject;

@Service(NomenclatureService.NAME)
public class NomenclatureServiceBean implements NomenclatureService {

    @Inject
    EntityCodeCreator codeCreator;

    @Override
    public String createCode() {
        return codeCreator.createCode("НД00000", "nomenclatureService");
    }
}