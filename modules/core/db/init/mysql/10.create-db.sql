-- begin EDMS_OUTGOING_DOCUMENT
create table EDMS_OUTGOING_DOCUMENT (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    DOCUMENT_TYPE_ID varchar(32) not null,
    REG_NUMBER varchar(255),
    REGISTRATION_DATE date,
    DESTINATION_ID varchar(32) not null,
    RECIPIENT varchar(255),
    THEME varchar(255) not null,
    EXECUTOR_ID varchar(32) not null,
    SIGNER_ID varchar(32),
    FOOTNOTE varchar(255),
    TITLE varchar(255),
    AUTHOR_ID varchar(32) not null,
    CREATION_DATE date not null,
    DATE_CHANGE date,
    STATUS integer,
    LOGBOOK_ID varchar(32),
    CONTENT varchar(255),
    ACT_ID varchar(32),
    SENT_TO_ACT date,
    RECONCILING_ID varchar(32),
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
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
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
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
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
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
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
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    PERSONNEL_NUMBER varchar(255) not null,
    USER_ID varchar(32) not null,
    LAST_NAME varchar(255) not null,
    FIRST_NAME varchar(255) not null,
    PATRONYMIC varchar(255),
    DEPARTMENT_ID varchar(32) not null,
    EMAIL varchar(255),
    PHONE_NUMBER varchar(255),
    PHOTO_ID varchar(32),
    --
    primary key (ID)
)^
-- end EDMS_WORKER
-- begin EDMS_ORGANIZATION
create table EDMS_ORGANIZATION (
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
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
    ID varchar(32),
    VERSION integer not null,
    CREATE_TS datetime(3),
    CREATED_BY varchar(50),
    UPDATE_TS datetime(3),
    UPDATED_BY varchar(50),
    DELETE_TS datetime(3),
    DELETED_BY varchar(50),
    --
    CODE varchar(255) not null,
    NAME varchar(255) not null,
    LEAD_DEPARTMENT_ID varchar(32),
    DEPARTMENT_MANAGER_ID varchar(32),
    --
    primary key (ID)
)^
-- end EDMS_DEPARTMENT
-- begin EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK
create table EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK (
    OUTGOING_DOCUMENT_ID varchar(32),
    FILE_DESCRIPTOR_ID varchar(32),
    primary key (OUTGOING_DOCUMENT_ID, FILE_DESCRIPTOR_ID)
)^
-- end EDMS_OUTGOING_DOCUMENT_FILE_DESCRIPTOR_LINK
