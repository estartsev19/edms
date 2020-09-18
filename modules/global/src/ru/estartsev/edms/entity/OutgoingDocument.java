package ru.estartsev.edms.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.security.entity.User;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NamePattern("%s|title")
@Table(name = "EDMS_OUTGOING_DOCUMENT")
@Entity(name = "edms_OutgoingDocument")
public class OutgoingDocument extends StandardEntity {
    private static final long serialVersionUID = -9196030842258544072L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DOCUMENT_TYPE_ID")
    private DocumentType documentType;

    @Column(name = "REG_NUMBER")
    private String regNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "REGISTRATION_DATE")
    @NotNull
    private Date registrationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DESTINATION_ID")
    private Organization destination;

    @Column(name = "RECIPIENT")
    private String recipient;

    @Column(name = "THEME", nullable = false)
    @NotNull
    private String theme;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EXECUTOR_ID")
    private Worker executor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIGNER_ID")
    private Worker signer;

    @Column(name = "FOOTNOTE")
    private String footnote;

    @Column(name = "TITLE")
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AUTHOR_ID")
    private User author;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "CREATION_DATE", nullable = false)
    private Date dateCreation;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_CHANGE")
    private Date dateChange;

    @Column(name = "STATUS")
    private Integer status;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @ManyToMany
    @JoinTable(name = "EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK",
            joinColumns = @JoinColumn(name = "OUTGOING_DOCUMENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "FILE_DESCRIPTOR_ID"))
    private List<FileDescriptor> file;

    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOGBOOK_ID")
    private Logbook logbook;

    @Column(name = "CONTENT")
    private String content;

    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACT_ID")
    private Nomenclature act;

    @Temporal(TemporalType.DATE)
    @Column(name = "SENT_TO_ACT")
    private Date sentToAct;

    @Temporal(TemporalType.DATE)
    @Column(name = "RECONCILATION_START_DATE")
    private Date reconcilationStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "RECONCILATION_COMPLETE_DATE")
    private Date reconcilationCompleteDate;

    @Column(name = "RECONCILATION_RESULT")
    private String reconcilationResult;

    @Column(name = "COMMENT_")
    private String comment;

    public void setFile(List<FileDescriptor> file) {
        this.file = file;
    }

    public List<FileDescriptor> getFile() {
        return file;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setDateChange(Date dateChange) {
        this.dateChange = dateChange;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReconcilationResult() {
        return reconcilationResult;
    }

    public void setReconcilationResult(String reconcilationResult) {
        this.reconcilationResult = reconcilationResult;
    }

    public Date getReconcilationCompleteDate() {
        return reconcilationCompleteDate;
    }

    public void setReconcilationCompleteDate(Date reconcilationCompleteDate) {
        this.reconcilationCompleteDate = reconcilationCompleteDate;
    }

    public Date getReconcilationStartDate() {
        return reconcilationStartDate;
    }

    public void setReconcilationStartDate(Date reconcilationStartDate) {
        this.reconcilationStartDate = reconcilationStartDate;
    }

    public Date getSentToAct() {
        return sentToAct;
    }

    public void setSentToAct(Date sentToAct) {
        this.sentToAct = sentToAct;
    }

    public Nomenclature getAct() {
        return act;
    }

    public void setAct(Nomenclature act) {
        this.act = act;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Logbook getLogbook() {
        return logbook;
    }

    public void setLogbook(Logbook logbook) {
        this.logbook = logbook;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @PostConstruct
    private void init(){
        setRegistrationDate(today());
    }

    public Date getDateChange() {
        return dateChange;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Worker getSigner() {
        return signer;
    }

    public void setSigner(Worker signer) {
        this.signer = signer;
    }

    public Worker getExecutor() {
        return executor;
    }

    public void setExecutor(Worker executor) {
        this.executor = executor;
    }

    public Organization getDestination() {
        return destination;
    }

    public void setDestination(Organization destination) {
        this.destination = destination;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public void setStatus(OutgoingDocumentStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public OutgoingDocumentStatus getStatus() {
        return status == null ? null : OutgoingDocumentStatus.fromId(status);
    }

    public String getFootnote() {
        return footnote;
    }

    public void setFootnote(String footnote) {
        this.footnote = footnote;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getRegNumber() {
        return regNumber;
    }

    private Date today(){
        Date date = new Date();
        return date;
    }
}