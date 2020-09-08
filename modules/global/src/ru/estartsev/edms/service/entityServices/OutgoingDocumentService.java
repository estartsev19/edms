package ru.estartsev.edms.service.entityServices;

import ru.estartsev.edms.entity.Worker;

public interface OutgoingDocumentService {
    String NAME = "edms_OutgoingDocumentService";

    Worker getCurrentWorker();
}