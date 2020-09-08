package ru.estartsev.edms.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|title")
@Table(name = "EDMS_DOCUMENT_TYPE")
@Entity(name = "edms_DocumentType")
public class DocumentType extends StandardEntity {
    private static final long serialVersionUID = -9062231927422150474L;

    @Column(name = "CODE", nullable = false)
    @NotNull
    private String code;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}