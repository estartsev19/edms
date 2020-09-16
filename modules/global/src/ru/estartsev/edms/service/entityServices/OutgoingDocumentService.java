package ru.estartsev.edms.service.entityServices;

import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcDefinition;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.cuba.security.entity.User;
import ru.estartsev.edms.entity.DocumentType;
import ru.estartsev.edms.entity.Worker;

import java.time.LocalDate;

public interface OutgoingDocumentService {
    String NAME = "edms_OutgoingDocumentService";

    Worker getCurrentWorker();

    String setRegNumberFromTemplate(String template, LocalDate date, int numberOfDigits);

    String setTitleForNewDocument(DocumentType documentType, String regNumber, LocalDate date,
                                  String destination, String theme);

    ProcActor createProcActor(String procRoleCode, ProcInstance procInstance, User user);
}