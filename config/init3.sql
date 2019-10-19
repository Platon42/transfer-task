create schema TRANSFER;

CREATE SEQUENCE TRANSFER.CLIENT_ID;
CREATE SEQUENCE TRANSFER.BENEFICIARY_ID;
CREATE SEQUENCE TRANSFER.BENEFICIARY_ACCOUNT_ID;
CREATE SEQUENCE TRANSFER.CLIENT_ACCOUNT_ID;
CREATE SEQUENCE TRANSFER.TRANSACTION_ID;
CREATE SEQUENCE TRANSFER.BALANCE_ID;

set schema TRANSFER;

create table BENEFICIARY
(
    BENEFICIARY_ID INTEGER default (NEXT VALUE FOR TRANSFER.BENEFICIARY_ID) auto_increment,
    STREET_LINE    VARCHAR(50),
    CITY           VARCHAR(50),
    COUNTRY        VARCHAR(3),
    POSTCODE       VARCHAR(20),
    constraint BENEFICIARY_PK
		primary key (BENEFICIARY_ID)
);

create table BENEFICIARY_ACCOUNT
(
    BENEFICIARY_ACCOUNT_ID INTEGER default (NEXT VALUE FOR TRANSFER.BENEFICIARY_ACCOUNT_ID) auto_increment,
    ACCOUNT_NO             BIGINT not null
		unique,
    NAME                   VARCHAR(50),
    IS_CLIENT              BOOLEAN,
    BENEFICIARY_ID         INTEGER,
    CURRENCY               VARCHAR(10) not null,
    constraint BENEFICIARY_ACCOUNT_BENEFICIARY_BENEFICIARY_ID_FK
		foreign key (BENEFICIARY_ID) references BENEFICIARY
			on update cascade on delete cascade
);

create unique index BENEFICIARY_ACCOUNT_ACCOUNT_ID_UINDEX
	on BENEFICIARY_ACCOUNT (BENEFICIARY_ACCOUNT_ID);

create unique index PRIMARY_KEY_39
	on BENEFICIARY_ACCOUNT (BENEFICIARY_ACCOUNT_ID);

create unique index PRIMARY_KEY_393
	on BENEFICIARY_ACCOUNT (BENEFICIARY_ACCOUNT_ID);

create unique index PRIMARY_KEY_4
	on BENEFICIARY_ACCOUNT (BENEFICIARY_ACCOUNT_ID);

alter table BENEFICIARY_ACCOUNT
	add constraint BENEFICIARY_ACCOUNT_PK
		primary key (BENEFICIARY_ACCOUNT_ID);

create table CLIENT
(
    CLIENT_ID        INTEGER default (NEXT VALUE FOR TRANSFER.CLIENT_ID) auto_increment,
    FIRST_NAME       VARCHAR(50) not null,
    LAST_NAME        VARCHAR(50) not null,
    MIDDLE_NAME      VARCHAR(50),
    BIRTHDAY         DATE not null,
    SEX              INTEGER not null,
    RESIDENT_COUNTRY VARCHAR(20) not null,
    constraint CLIENT_PK
		primary key (CLIENT_ID)
);

create table CLIENT_ACCOUNT
(
    CLIENT_ACCOUNT_ID INTEGER   default (NEXT VALUE FOR TRANSFER.CLIENT_ACCOUNT_ID) auto_increment,
    ACCOUNT_NO        BIGINT not null
		unique,
    BALANCE           DOUBLE    default 0,
    CURRENCY          VARCHAR(3),
    CREATED_AT        TIMESTAMP default CURRENT_TIMESTAMP(),
    UPDATED_AT        TIMESTAMP,
    NAME              VARCHAR(50),
    COUNTRY_OF_ISSUE  VARCHAR(20),
    CLIENT_ID         INTEGER not null,
    constraint ACCOUNT_PK
		primary key (CLIENT_ACCOUNT_ID),
    constraint CLIENT_ACCOUNT_CLIENT_CLIENT_ID_FK
		foreign key (CLIENT_ID) references CLIENT
			on update cascade on delete cascade
);

create table TRANSACTION
(
    TRANSACTION_ID    INTEGER default (NEXT VALUE FOR TRANSFER.TRANSACTION_ID) auto_increment,
    SOURCE_ACCOUNT_ID INTEGER not null,
    TARGET_ACCOUNT_ID INTEGER,
    AMOUNT            INTEGER,
    CURRENCY          VARCHAR(3) not null,
    CREATED_AT        TIMESTAMP WITH TIME ZONE not null,
    TRANSACTION_TYPE  VARCHAR(20),
    constraint TRANSACTION_HISTORY_PK
		primary key (TRANSACTION_ID)
);

create table BALANCE
(
    BALANCE_ID     INTEGER default (NEXT VALUE FOR TRANSFER.BALANCE_ID) auto_increment,
    ACCOUNT_ID     INTEGER,
    TRANSACTION_ID INTEGER,
    BEFORE_BALANCE DOUBLE,
    PAST_BALANCE   DOUBLE,
    constraint BALANCE_HISTORY_PK
		primary key (BALANCE_ID),
    constraint BALANCE_HISTORY_ACCOUNT_ACCOUNT_ID_FK
		foreign key (ACCOUNT_ID) references CLIENT_ACCOUNT
			on update cascade on delete cascade,
    constraint BALANCE_HISTORY_TRANSACTION_HISTORY_TRANSACTION_ID_FK
		foreign key (TRANSACTION_ID) references TRANSACTION
			on update cascade on delete cascade
);

