create schema TRANSFER;
CREATE SEQUENCE TRANSFER.CLIENT_ID;
CREATE SEQUENCE TRANSFER.BENEFICIARY_ID;
CREATE SEQUENCE TRANSFER.ACCOUNT_ID;
CREATE SEQUENCE TRANSFER.TRANSACTION_ID;
CREATE SEQUENCE TRANSFER.BALANCE_ID;

create table TRANSFER.ACCOUNT
(
    ACCOUNT_ID       INTEGER default (NEXT VALUE FOR TRANSFER.ACCOUNT_ID) auto_increment,
    BENEFICIARY_ID   INTEGER,
    ACCOUNT_NO       INTEGER,
    BALANCE          DOUBLE,
    CURRENCY         VARCHAR(3 CHAR),
    CREATED_AT       TIMESTAMP,
    UPDATED_AT       TIMESTAMP,
    NAME             VARCHAR(50 CHAR),
    COUNTRY_OF_ISSUE VARCHAR(20 CHAR),
    constraint ACCOUNT_PK
		primary key (ACCOUNT_ID),
    unique (ACCOUNT_ID, ACCOUNT_ID)
);

create table TRANSFER.CLIENT
(
    CLIENT_ID        INTEGER default (NEXT VALUE FOR TRANSFER.CLIENT_ID) auto_increment,
    FIRST_NAME       VARCHAR(50 CHAR) not null,
    LAST_NAME        VARCHAR(50 CHAR) not null,
    MIDDLE_NAME      VARCHAR(50 CHAR),
    BIRTHDAY         DATE             not null,
    SEX              INTEGER          not null,
    RESIDENT_COUNTRY VARCHAR(20 CHAR) not null,
    constraint CLIENT_PK
		primary key (CLIENT_ID)
);

create table TRANSFER.BENEFICIARY
(
    BENEFICIARY_ID INTEGER default (NEXT VALUE FOR TRANSFER.BENEFICIARY_ID) auto_increment,
    STREET_LINE    VARCHAR(50 CHAR),
    CITY           VARCHAR(50 CHAR),
    COUNTRY        VARCHAR(3 CHAR),
    POSTCODE       VARCHAR(20 CHAR),
    CLIENT_ID      INTEGER,
    ACCOUNT_ID     INTEGER,
    constraint BENEFICIARY_PK
		primary key (BENEFICIARY_ID),
    constraint BENEFICIARY_ACCOUNT_ACCOUNT_ID_FK
		foreign key (ACCOUNT_ID) references TRANSFER.ACCOUNT,
    constraint BENEFICIARY_CLIENT_CLIENT_ID_FK
		foreign key (CLIENT_ID) references TRANSFER.CLIENT
			on update cascade on delete cascade
);

alter table TRANSFER.ACCOUNT
	add constraint ACCOUNT_BENEFICIARY_BENEFICIARY_ID_FK
		foreign key (BENEFICIARY_ID) references BENEFICIARY;

create table TRANSFER.TRANSACTION_HISTORY
(
    TRANSACTION_ID    INTEGER default (NEXT VALUE FOR TRANSFER.TRANSACTION_ID) auto_increment,
    SOURCE_ACCOUNT_ID INTEGER                  not null,
    TARGET_ACCOUNT_ID INTEGER,
    AMOUNT            INTEGER,
    CURRENCY          VARCHAR(3 CHAR)          not null,
    CREATED_AT        TIMESTAMP WITH TIME ZONE not null,
    T_TYPE            INTEGER,
    constraint TRANSACTION_HISTORY_PK
		primary key (TRANSACTION_ID),
    constraint TRANSACTION_HISTORY_ACCOUNT_ACCOUNT_ID_ACCOUNT_ID_FK
		foreign key (SOURCE_ACCOUNT_ID, TARGET_ACCOUNT_ID) references TRANSFER.ACCOUNT (ACCOUNT_ID, ACCOUNT_ID)
			on update cascade on delete cascade
);

create table TRANSFER.BALANCE_HISTORY
(
	BALANCE_ID INTEGER default (NEXT VALUE FOR TRANSFER.BALANCE_ID) auto_increment,
	ACCOUNT_ID INTEGER,
	TRANSACTION_ID INTEGER,
	BEFORE_BALANCE DOUBLE,
	PAST_BALANCE DOUBLE,
	constraint BALANCE_HISTORY_PK
		primary key (BALANCE_ID),
	constraint BALANCE_HISTORY_ACCOUNT_ACCOUNT_ID_FK
		foreign key (ACCOUNT_ID) references TRANSFER.ACCOUNT
			on update cascade on delete cascade,
	constraint BALANCE_HISTORY_TRANSACTION_HISTORY_TRANSACTION_ID_FK
        foreign key (TRANSACTION_ID) references TRANSFER.TRANSACTION_HISTORY
			on update cascade on delete cascade
);

