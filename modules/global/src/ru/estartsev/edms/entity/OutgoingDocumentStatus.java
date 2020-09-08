package ru.estartsev.edms.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum OutgoingDocumentStatus implements EnumClass<Integer> {

    NEW(1),
    ON_APPROVAL(2),
    ON_SIGNING(3),
    ON_COMPLETION(4),
    REGISTERED(5);

    private Integer id;

    OutgoingDocumentStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static OutgoingDocumentStatus fromId(Integer id) {
        for (OutgoingDocumentStatus at : OutgoingDocumentStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}