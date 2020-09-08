package ru.estartsev.edms.core;

import com.haulmont.cuba.core.app.UniqueNumbers;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(EntityCodeCreator.NAME)
public class EntityCodeCreator {
    public static final String NAME = "edms_EntityCodeCreator";

    @Inject
    UniqueNumbers uniqueNumbers;

    public String createCode(String prefix, String sequence){
        return prefix + uniqueNumbers.getNextNumber(sequence);
    }
}