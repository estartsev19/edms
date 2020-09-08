package ru.estartsev.edms.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|shortTitle")
@Table(name = "EDMS_ORGANIZATION")
@Entity(name = "edms_Organization")
public class Organization extends StandardEntity {
    private static final long serialVersionUID = -8813435788548544213L;

    @NotNull
    @Column(name = "CODE", nullable = false)
    private String code;

    @NotNull
    @Column(name = "SHORT_TITLE", nullable = false)
    private String shortTitle;

    @NotNull
    @Column(name = "FULL_TITLE", nullable = false)
    private String fullTitle;

    @Column(name = "LEGAL_ADDRESS")
    private String legalAddress;

    @Column(name = "MAILING_ADDRESS")
    private String mailingAddress;

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getLegalAddress() {
        return legalAddress;
    }

    public void setLegalAddress(String legalAddress) {
        this.legalAddress = legalAddress;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}