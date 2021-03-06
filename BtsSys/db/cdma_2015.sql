create table WY_BTS_SPECIAL(
    INT_ID            NUMBER         not null,
    NAME              VARCHAR2(128)   not null,
    CITY_ID          NUMBER          not null,
    BSC_NAME         VARCHAR2(128)         ,
    BTS_ID            NUMBER         not null,
    BTSNAME           VARCHAR2(128)   not null,
    INTIME            DATE  not null,
    UPDATETIME        DATE  not null,
    TYPE  NUMBER     not null,
    NET_TYPE NUMBER not null,
    STATE NUMBER not null,
    DELETE_FLAG NUMBER DEFAULT 0,
    constraint PK_WY_BTS_SPECIAL primary key (INT_ID)
)

alter table wy_wrongname add NET_TYPE NUMBER(1);
alter table wy_wrongname add WRONG_MSG VARCHAR(512);

insert into wy_menu(ID,PID, name,type,url, orderby, flag)values(57,15,'特殊站点查询','menu','business/special.action',28,0);
insert into wy_menu(ID,PID, name,type,url, orderby, flag)values(58,15,'LTE基站查询','menu','lte/lteData.action',23,0);
insert into wy_menu(ID,PID, name,type,url, orderby, flag)values(59,15,'LTE小区查询','menu','lte/cellData.action',24,0);
insert into wy_menu(ID,PID, name,type,url, orderby, flag)values(60,15,'LTE废弃查询','menu','lte/lteAbandon.action',29,0);


 Insert into WY_COLUMNS_CONFIG (ID,CN_NAME,EN_NAME,TYPE,ORDERNO,
 REMARK,ENABLE,ENEDIT,COMBOBOX) values (304,'名称','NAME',8,2,'wy_tunel',0,0,0);
