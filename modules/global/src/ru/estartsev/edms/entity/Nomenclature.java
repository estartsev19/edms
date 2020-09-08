package ru.estartsev.edms.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.AppBeans;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|title")
@Table(name = "EDMS_NOMENCLATURE")
@Entity(name = "edms_Nomenclature")
public class Nomenclature extends StandardEntity {
    private static final long serialVersionUID = -7710940663223142897L;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "TITLE")
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