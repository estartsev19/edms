package ru.estartsev.edms.service.entityServices;

import ru.estartsev.edms.entity.DocumentType;
import ru.estartsev.edms.entity.Worker;

import java.time.LocalDate;

public interface OutgoingDocumentService {
    String NAME = "edms_OutgoingDocumentService";

    Worker getCurrentWorker();

    String setRegNumberFromTemplate(String template, LocalDate date, int numberOfDigits);

    String setTitleForNewDocument(DocumentType documentType, String regNumber, LocalDate date,
                                  String destination, String theme);
}