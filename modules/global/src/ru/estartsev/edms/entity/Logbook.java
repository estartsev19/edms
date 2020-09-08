package ru.estartsev.edms.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|title")
@Table(name = "EDMS_LOGBOOK")
@Entity(name = "edms_Logbook")
public class Logbook extends StandardEntity {
    private static final long serialVersionUID = -1283817851980842514L;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "TITLE")
    private String title;

    @NotNull
    @Column(name = "NUMBER_FORMAT", nullable = false)
    private String numberFormat;

    @Column(name = "NUMBER_OF_DIGITS")
    private Integer numberOfDigits;

    public Integer getNumberOfDigits() {
        return numberOfDigits;
    }

    public void setNumberOfDigits(Integer numberOfDigits) {
        this.numberOfDigits = numberOfDigits;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

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