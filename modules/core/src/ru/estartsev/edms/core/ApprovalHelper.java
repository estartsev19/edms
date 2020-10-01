package ru.estartsev.edms.core;

import com.haulmont.cuba.core.global.DataManager;
import org.springframework.stereotype.Component;
import ru.estartsev.edms.entity.OutgoingDocument;
import ru.estartsev.edms.entity.OutgoingDocumentStatus;

import javax.inject.Inject;
import java.util.UUID;

@Component(ApprovalHelper.NAME)
public class ApprovalHelper {
    public static final String NAME = "edms_ApprovalHelper";

    @Inject
    private DataManager dataManager;

    /**
     * Метод обновляет статус исходящего документа в процессе "documentApproval"
     * @param entityId - id сущности
     * @param status - значение статуса исходящего документа (enum OutgoingDocumentStatus)
     */
    public void updateState(UUID entityId, int status) {
            OutgoingDocument outgoingDocument = dataManager.load(OutgoingDocument.class)
                    .query("select e from edms_OutgoingDocument e where e.id = :id")
                    .parameter("id", entityId)
                    .one();
            if (outgoingDocument != null) {
                outgoingDocument.setStatus(OutgoingDocumentStatus.fromId(status));
            }
            dataManager.commit(outgoingDocument);
    }
}