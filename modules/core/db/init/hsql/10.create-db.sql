-- begin EDMS_OUTGOING_DOCUMENT
create table EDMS_OUTGOING_DOCUMENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOCUMENT_TYPE_ID varchar(36) not null,
    REG_NUMBER varchar(255),
    REGISTRATION_DATE date,
    DESTINATION_ID varchar(36) not null,
    RECIPIENT varchar(255),
    THEME varchar(255) not null,
    EXECUTOR_ID varchar(36) not null,
    SIGNER_ID varchar(36),
    FOOTNOTE varchar(255),
    TITLE varchar(255),
    AUTHOR_ID varchar(36) not null,
    CREATION_DATE date not null,
    DATE_CHANGE date,
    STATUS integer,
    LOGBOOK_ID varchar(36),
    CONTENT varchar(255),
    ACT_ID varchar(36),
    SENT_TO_ACT date,
    RECONCILATION_START_DATE date,
    RECONCILATION_COMPLETE_DATE date,
    RECONCILATION_RESULT varchar(255),
    COMMENT_ varchar(255),
    --
    primary key (ID)
)^
-- end EDMS_OUTGOING_DOCUMENT
-- begin EDMS_DOCUMENT_TYPE
create table EDMS_DOCUMENT_TYPE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255) not null,
    --
    primary key (ID)
)^
-- end EDMS_DOCUMENT_TYPE
-- begin EDMS_NOMENCLATURE
create table EDMS_NOMENCLATURE (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    TITLE varchar(255),
    --
    primary key (ID)
)^
-- end EDMS_NOMENCLATURE
-- begin EDMS_LOGBOOK
create table EDMS_LOGBOOK (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    TITLE varchar(255),
    NUMBER_FORMAT varchar(255) not null,
    NUMBER_OF_DIGITS integer,
    --
    primary key (ID)
)^
-- end EDMS_LOGBOOK
-- begin EDMS_WORKER
create table EDMS_WORKER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PERSONNEL_NUMBER varchar(255) not null,
    USER_ID varchar(36) not null,
    LAST_NAME varchar(255) not null,
    FIRST_NAME varchar(255) not null,
    PATRONYMIC varchar(255),
    DEPARTMENT_ID varchar(36) not null,
    EMAIL varchar(255),
    PHONE_NUMBER varchar(255),
    PHOTO_ID varchar(36),
    --
    primary key (ID)
)^
-- end EDMS_WORKER
-- begin EDMS_ORGANIZATION
create table EDMS_ORGANIZATION (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    SHORT_TITLE varchar(255) not null,
    FULL_TITLE varchar(255) not null,
    LEGAL_ADDRESS varchar(255),
    MAILING_ADDRESS varchar(255),
    --
    primary key (ID)
)^
-- end EDMS_ORGANIZATION
-- begin EDMS_DEPARTMENT
create table EDMS_DEPARTMENT (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255) not null,
    LEAD_DEPARTMENT_ID varchar(36),
    DEPARTMENT_MANAGER_ID varchar(36),
    --
    primary key (ID)
)^
-- end EDMS_DEPARTMENT
-- begin EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK
create table EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK (
    OUTGOING_DOCUMENT_ID varchar(36) not null,
    FILE_DESCRIPTOR_ID varchar(36) not null,
    primary key (OUTGOING_DOCUMENT_ID, FILE_DESCRIPTOR_ID)
)^
-- end EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK
