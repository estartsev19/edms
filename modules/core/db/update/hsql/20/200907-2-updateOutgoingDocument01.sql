alter table EDMS_OUTGOING_DOCUMENT add constraint FK_EDMS_OUTGOING_DOCUMENT_ON_RECONCILING foreign key (RECONCILING_ID) references EDMS_WORKER(ID);
create index IDX_EDMS_OUTGOING_DOCUMENT_ON_RECONCILING on EDMS_OUTGOING_DOCUMENT (RECONCILING_ID);
