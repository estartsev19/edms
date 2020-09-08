package ru.estartsev.edms.service.entityServices;

import org.springframework.stereotype.Service;
import ru.estartsev.edms.core.EntityCodeCreator;

import javax.inject.Inject;

@Service(LogbookService.NAME)
public class LogbookServiceBean implements LogbookService {

    @Inject
    EntityCodeCreator codeCreator;

    public String createCode(){
        return codeCreator.createCode("Ж00000", "logbookSequence");
    }
}