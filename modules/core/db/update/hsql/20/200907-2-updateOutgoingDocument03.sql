alter table EDMS_OUTGOING_DOCUMENT add constraint FK_EDMS_OUTGOING_DOCUMENT_ON_LOGBOOK foreign key (LOGBOOK_ID) references EDMS_LOGBOOK(ID);
create index IDX_EDMS_OUTGOING_DOCUMENT_ON_LOGBOOK on EDMS_OUTGOING_DOCUMENT (LOGBOOK_ID);