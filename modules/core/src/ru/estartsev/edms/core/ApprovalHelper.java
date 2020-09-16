package ru.estartsev.edms.core;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Component;
import ru.estartsev.edms.entity.OutgoingDocument;
import ru.estartsev.edms.entity.OutgoingDocumentStatus;

import javax.inject.Inject;
import java.util.UUID;

@Component(ApprovalHelper.NAME)
public class ApprovalHelper {
    public static final String NAME = "edms_ApprovalHelper";

    @Inject
    private Persistence persistence;

    public void updateState(UUID entityId, int status) {
        try (Transaction tx = persistence.getTransaction()) {
            OutgoingDocument outgoingDocument = persistence.getEntityManager().find(OutgoingDocument.class, entityId);
            if (outgoingDocument != null) {
                outgoingDocument.setStatus(OutgoingDocumentStatus.fromId(status));
            }
            tx.commit();
        }
    }
}