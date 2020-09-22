-- begin EDMS_OUTGOING_DOCUMENT
create table EDMS_OUTGOING_DOCUMENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    DOCUMENT_TYPE_ID uuid not null,
    REG_NUMBER varchar(255),
    REGISTRATION_DATE date,
    DESTINATION_ID uuid not null,
    RECIPIENT varchar(255),
    THEME varchar(255) not null,
    EXECUTOR_ID uuid not null,
    SIGNER_ID uuid,
    FOOTNOTE varchar(255),
    TITLE varchar(255),
    AUTHOR_ID uuid not null,
    CREATION_DATE date not null,
    DATE_CHANGE date,
    STATUS integer,
    LOGBOOK_ID uuid,
    CONTENT varchar(255),
    ACT_ID uuid,
    SENT_TO_ACT date,
    --
    primary key (ID)
)^
-- end EDMS_OUTGOING_DOCUMENT
-- begin EDMS_DOCUMENT_TYPE
create table EDMS_DOCUMENT_TYPE (
    ID uuid,
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
    ID uuid,
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
    ID uuid,
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
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PERSONNEL_NUMBER varchar(255) not null,
    USER_ID uuid not null,
    LAST_NAME varchar(255) not null,
    FIRST_NAME varchar(255) not null,
    PATRONYMIC varchar(255),
    DEPARTMENT_ID uuid not null,
    EMAIL varchar(255),
    PHONE_NUMBER varchar(255),
    PHOTO_ID uuid,
    --
    primary key (ID)
)^
-- end EDMS_WORKER
-- begin EDMS_ORGANIZATION
create table EDMS_ORGANIZATION (
    ID uuid,
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
    ID uuid,
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
    LEAD_DEPARTMENT_ID uuid,
    DEPARTMENT_MANAGER_ID uuid,
    --
    primary key (ID)
)^
-- end EDMS_DEPARTMENT
-- begin EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK
create table EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK (
    OUTGOING_DOCUMENT_ID uuid,
    FILE_DESCRIPTOR_ID uuid,
    primary key (OUTGOING_DOCUMENT_ID, FILE_DESCRIPTOR_ID)
)^
-- end EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK
