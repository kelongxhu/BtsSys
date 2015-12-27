create table WY_LTE_BTS  (
   INT_ID                   NUMBER(20)                not null,
   NAME                     VARCHAR2(256)              not null,
   LONGITUDE               NUMBER (12,6),
   LATITUDE                NUMBER(12,6),
   is_indoor               VARCHAR2(10)               not null,
   is_rru                   VARCHAR2(10)              not null,
   RELATE_ENB_INTID       NUMBER(20)        not null,
   ENB_NAME                VARCHAR2(255)       not null,
   circuitRoom_ownership VARCHAR2(10)       not null,
   Trans_ownership        VARCHAR2(10)       not null,
   VENDER_NAME            VARCHAR2(100)       not null,
   SERVICE_LEVEL          VARCHAR2(10)        not null,
   SITE_TOGETHER          VARCHAR2(10) ,
   HIGHTRAIN_FLAG         VARCHAR2(10),
   REDLINE_FLAG           NUMBER(1),
   city_id                 NUMBER(6)        not null,
   country_id               NUMBER(6)          not null,
   manual_flag            NUMBER(1)         default 0,
   delete_flag            NUMBER(1)         default 0,
   INTIME                  DATE             default sysdate,
   DELETETIME             DATE,
   UPDATETIME             DATE,
   DELETE_RESONCODE      NUMBER(1)  default 0,
   DELETE_TEXT            VARCHAR2(500),
   constraint PK_WY_LTE_BTS primary key (INT_ID)
);


create table WY_LTE_BBU  (
   INT_ID               NUMBER(20)                      not null,
   NAME                 VARCHAR2(255)                    not null,
   LONGITUDE               NUMBER (12,6),
   LATITUDE                NUMBER(12,6),
   BBU_NO               VARCHAR2(255)                    not null,
   ENB_ID             NUMBER(10)                     not null,
   ENB_bts_name        VARCHAR2(255)                    not null,
   ENB_BTS_ID           NUMBER(20)                     not null,
   circuitRoom_ownership VARCHAR2(255)                ,
   Trans_ownership    VARCHAR2(255)                    ,
   vendor_NAME        VARCHAR2(255)                     not null,
   SITE_TOGETHER          VARCHAR2(10) ,
   CITY_ID              NUMBER(6)                       not null,
   county_ID          NUMBER(6)                         not null,
   IS_SHARE             NUMBER(1)                       not null,
   BBU_TYPE             NUMBER(1)                       not null,
   WY_LTE_BTSID        NUMBER(20)                     not null,
   HIGHTRAIN_FLAG      VARCHAR2(10)                     ,
   REDLINE_FLAG         NUMBER(1)                      ,
   manual_FLAG        NUMBER(1)                        default 0,
   delete_FLAG        NUMBER(1)                        default 0,
   DELETETIME           DATE                           ,
   INTIME               DATE                           default sysdate,
   UPDATETIME           DATE,
   DELETE_RESONCODE      NUMBER(1)  default 0,
   DELETE_TEXT            VARCHAR2(500),
   constraint PK_WY_LTE_BBU primary key (INT_ID)
);


create table WY_LTE_CELL  (
   INT_ID               NUMBER(20)                      not null,
   NAME                 VARCHAR2(255) not null,
   CITY_ID              NUMBER(6) not null,
   COUNTRY_ID           NUMBER(6) not null,
   VENDOR_NAME          VARCHAR2(32) not null,
   IS_INDOOR            VARCHAR2(10) not null,
   IS_RRU               VARCHAR2(10) not null,
   IS_GF                VARCHAR2(10) not null,
   IS_OR                NUMBER(1) not null,
   IS_RR                NUMBER(1) not null,
   IS_SR                NUMBER(1) not null,
   SITE_TOGETHER       VARCHAR2(10),
   LET_INT_ID           NUMBER(20) not null,
   ENB_INT_ID           NUMBER(20) not null,
   HIGHTRAIN_FLAG      VARCHAR2(10),
   REDLINE_FLAG         NUMBER(1),
   MANUAL_FLAG          NUMBER(1) default 0,
   DELETE_FLAG          NUMBER(1) default 0,
   DELETETIME          DATE,
   INTIME               DATE default sysdate,
   UPDATETIME           DATE,
   DELETE_RESONCODE      NUMBER(1) default 0,
   DELETE_TEXT            VARCHAR2(500),
   constraint PK_WY_LTE_CELL primary key (INT_ID)
);

create index ltebts_delete_idx on WY_LTE_BTS(delete_flag);
create index ltebts_city_idx on WY_LTE_BTS(city_id,country_id);
create index ltebbu_delete_idx on WY_LTE_BBU(delete_flag);
create index ltebbu_city_idx on WY_LTE_BBU(city_id,county_id);
create index ltebbu_share_idx on WY_LTE_BBU(IS_SHARE,BBU_TYPE);
create index ltecell_delete_idx on WY_LTE_CELL(delete_flag);
create index ltecell_city_idx on WY_LTE_CELL(city_id,country_id);
create index wrongname_delete_idx on WY_WRONGNAME(delete_flag,net_type);

