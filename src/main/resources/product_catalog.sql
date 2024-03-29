DROP TABLE IF EXISTS TBL_MS_PRODUCT;

CREATE TABLE TBL_MS_PRODUCT (
ID NUMBER(19) NOT NULL AUTO_INCREMENT,
PRODUCT_ID NUMBER(19) NOT NULL,
TITLE VARCHAR2(512) DEFAULT NULL,
DESCRIPTION VARCHAR2(512) DEFAULT NULL,
BRAND VARCHAR2(512) NOT NULL,
COLOR VARCHAR2(512) NOT NULL,
PRICE NUMBER(19) NOT NULL,
CREATED_AT  DATE NOT NULL,
UPDATED_AT DATE DEFAULT NULL,
VERSION_COUNTER NUMBER(19) NOT NULL,
PRIMARY KEY (ID);

ALTER TABLE TBL_MS_PRODUCT
ADD CONSTRAINT UK_TBL_MS_PRODUCT UNIQUE (PRODUCT_ID, BRAND));

DROP SEQUENCE IF EXISTS SEQ_TBL_MS_PRODUCT;

CREATE SEQUENCE SEQ_TBL_MS_PRODUCT
MINVALUE 1
MAXVALUE 9999999999999999
START WITH 1
INCREMENT BY 1
CACHE 100;

DROP TABLE IF EXISTS TBL_MS_PRODUCT_VERSION;

CREATE TABLE TBL_MS_PRODUCT_VERSION (
ID NUMBER(19) NOT NULL AUTO_INCREMENT,
PRODUCT_ID NUMBER(19) NOT NULL,
TITLE VARCHAR2(512) DEFAULT NULL,
DESCRIPTION VARCHAR2(512) DEFAULT NULL,
BRAND VARCHAR2(512) NOT NULL,
COLOR VARCHAR2(512) NOT NULL,
PRICE NUMBER(19) NOT NULL,
VALID_FROM  DATE NOT NULL,
VALID_TO DATE DEFAULT NULL,
VERSION_COUNTER NUMBER(19) NOT NULL,
PRIMARY KEY (ID);

ALTER TABLE TBL_MS_PRODUCT_VERSION
ADD CONSTRAINT UK_TBL_MS_PRODUCT_VERSION UNIQUE (PRODUCT_ID, BRAND, VERSION_COUNTER));

DROP SEQUENCE IF EXISTS SEQ_TBL_MS_PRODUCT_VERSION;

CREATE SEQUENCE SEQ_TBL_MS_PRODUCT_VERSION
MINVALUE 1
MAXVALUE 9999999999999999
START WITH 1
INCREMENT BY 1
CACHE 100;