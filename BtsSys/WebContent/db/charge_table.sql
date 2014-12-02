DROP TABLE WY_BTS_CHARGE;
CREATE TABLE WY_BTS_CHARGE (
   INT_ID               NUMBER         NOT NULL,
   BTS_TYPE             NUMBER(1)      NOT NULL,
   COST_TYPE            NUMBER(1)      NOT NULL,
   CONTRACT_STARTTIME   DATE,
   CONTRACT_ENDTIME     DATE,
   PAY_CYCLE           NUMBER(2),
   PAY_DAY             NUMBER(2),
   AHEAD_DAY           NUMBER(2),
   REMIND_USER         VARCHAR2(64),
   REMIND_TEL          VARCHAR2(64),
   NEXT_REMIND_TIME    DATE,
   MONEY               NUMBER(20,2),
   CONTRACT_FILE       VARCHAR2(128),
   REMARK              VARCHAR2(1024),
   EACH_TEL            VARCHAR2(64),
   EACH_ACCOUNTNAME    VARCHAR2(64),
   EACH_BANKNUM        VARCHAR2(64),
   PAY_TYPE            NUMBER(1),
   BANK_ACCOUNT        VARCHAR2(64),
   BALANCE             NUMBER(20,2),
   LAST_PAY_TIME       DATE,
   NEXT_PAY_TIME       DATE,
   IS_REMIND           NUMBER DEFAULT 0,
   IN_TIME             DATE DEFAULT SYSDATE,
   IN_USER             NUMBER,
   CONSTRAINT PK_WY_BTS_CHARGE PRIMARY KEY (INT_ID,BTS_TYPE,COST_TYPE)
);

DROP TABLE WY_SMS_LOG;
CREATE TABLE WY_SMS_LOG  (
   ID                 NUMBER                          NOT NULL,
   INT_ID             NUMBER                          NOT NULL,
   BTS_TYPE           NUMBER(1)                       NOT NULL,
   COST_TYPE          NUMBER(1)                       NOT NULL,
   REMIND_USER        VARCHAR2(64),
   REMIND_TEL         VARCHAR2(64)                    NOT NULL,
   CONTENT            VARCHAR2(512),
   STATUS             NUMBER(1),
   IN_TIME            DATE,
   CONSTRAINT PK_WY_SMS_LOG PRIMARY KEY (ID)
);

DROP TABLE WY_BTS_CHARGE_LIST;
CREATE TABLE WY_BTS_CHARGE_LIST  (
   ID                   NUMBER                          NOT NULL,
   INT_ID               NUMBER                          NOT NULL,
   BTS_TYPE             NUMBER(1)                       NOT NULL,
   NAME                 VARCHAR2(64)                    NOT NULL,
   CITY_ID              NUMBER                          NOT NULL,
   COUNTRY_ID           NUMBER                          NOT NULL,
   BSC_NAME             VARCHAR2(64)                    NOT NULL,
   BTS_ID               NUMBER                          NOT NULL,
   COST_TYPE            NUMBER(1)                       NOT NULL,
   MONEY                NUMBER(20,2)                    NOT NULL,
   PROOF_FILE           VARCHAR2(128),
   REMARK               VARCHAR2(512),
   PAY_TIME             DATE                            NOT NULL,
   PAY_USER             VARCHAR2(128),
   IS_TIMEOUT           NUMBER(1)  DEFAULT 0,
   IN_TIME              DATE,
   IN_USER              NUMBER,
   MONTH_DEGREE         NUMBER(20,2),
   BASE_DEGREE          NUMBER(20,2),
   PAY_TYPE             NUMBER(1)   DEFAULT 1,
   CONSTRAINT PK_WY_BTS_CHARGE_LIST PRIMARY KEY (ID)
)

DROP  SEQUENCE WY_BTS_CHARGE_LIST_SEQ;
CREATE SEQUENCE WY_BTS_CHARGE_LIST_SEQ
MINVALUE 1
MAXVALUE 999999999999999999999999999
START WITH 1
INCREMENT BY 1
CACHE 20;

DROP TABLE SMGP_SUBMIT;
CREATE TABLE SMGP_SUBMIT
(
  ID            INTEGER NOT NULL,
  SEQNUM        INTEGER,
  STATUS        INTEGER DEFAULT 9,
  NEEDTIME      INTEGER DEFAULT 0,
  MSGID         VARCHAR2(255),
  COMPLETECODE  INTEGER,
  SEND_TIME     DATE,
  COMP_TIME     DATE,
  NEEDREPLY     INTEGER DEFAULT 0,
  MSGLEVEL      INTEGER DEFAULT 1,
  SERVICEID     VARCHAR2(11),
  MSGFORMAT     INTEGER DEFAULT 15,
  VALIDTIME     VARCHAR2(18),
  ATTIME        VARCHAR2(18),
  SRCTERMID     VARCHAR2(21),
  DESTTERMID    VARCHAR2(21),
  MSGLEN        INTEGER,
  MSGCONTENT    VARCHAR2(255),
  FEETYPE       VARCHAR2(2) DEFAULT '00',
  FEECODE       VARCHAR2(6) DEFAULT '000000',
  FIXEDFEE      VARCHAR2(6) DEFAULT '000000',
  FEETERMINALID VARCHAR2(21),
  MSGTYPE       INTEGER DEFAULT 6
)

ALTER TABLE SMGP_SUBMIT
  ADD CONSTRAINT PK_SMGP_SUBMIT PRIMARY KEY (ID);


CREATE SEQUENCE Q_SMGP_SUBMIT
MINVALUE 1
MAXVALUE 999999999999999999999999999
START WITH 1
INCREMENT BY 1
CACHE 20;


CREATE SEQUENCE WY_SMS_LOG_SEQ
MINVALUE 1
MAXVALUE 999999999999999999999999999
START WITH 1
INCREMENT BY 1
CACHE 20;

insert into wy_menu(ID,PID, name,type,url, orderby, flag)values(48,23,'费用设置','menu','charge/charge.action',73,0);
insert into wy_menu(ID,PID, name,type,url, orderby, flag)values(49,23,'费用缴费','menu','charge/pay.action',74,0);
insert into wy_menu(ID,PID, name,type,url, orderby, flag)values(50,23,'缴费查询','menu','charge/payQuery.action',75,0);
insert into wy_menu(ID,PID, name,type,url, orderby, flag)values(51,23,'缴费统计','menu','charge/payCount.action',77,0);



