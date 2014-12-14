
create table WY_LIB_village (
    ID               NUMBER         not null,
    city_id          NUMBER         not null,
    country_id       NUMBER         not null,
    town             VARCHAR2(128)  not null,
    village          VARCHAR2(128)  not null,
    villagename      VARCHAR2(128)  not null,
    LONGITUDE        NUMBER(38,5),
    LATITUDE         NUMBER(38,5),
    constraint PK_WY_LIB_village primary key (ID)
);

create index wy_lib_village_cityid_index on WY_LIB_village(city_id);
create index wy_lib_village_counrtyid_index on WY_LIB_village(counrty_id);
create index wy_lib_village_town_index on WY_LIB_village(town);
create index wy_lib_village_village_index on WY_LIB_village(village);


create sequence WY_LIB_village_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;


create trigger TRG_LIB_VILLAGE before insert on WY_LIB_VILLAGE
for each row
begin
select WY_LIB_village_SEQ.nextval into :new.ID from dual;
end;

--基础数据修改优化

--增加字段
alter table WY_BTS_MANUAL add town varchar2(128);
alter table WY_BTS_MANUAL add village varchar2(128);
alter table WY_BBU_MANUAL add town varchar2(128);
alter table WY_BBU_MANUAL add village varchar2(128);
alter table WY_INDOOR_MANUAL add town varchar2(128);
alter table WY_INDOOR_MANUAL add village varchar2(128);
alter table wy_tunel_manual add town varchar2(128);
alter table wy_tunel_manual add village varchar2(128);

--wy_columns_config的修改
update wy_columns_config set cn_name ='室内分布小区名称' where id=144;
delete wy_columns_config where id in(141,142);--删除小区的塔跪类型
update wy_columns_config set cn_name='隧道覆盖小区名称' where id=228
update wy_columns_config set 
delete wy_columns_config where id in(233,234);


--添加索引
--wy_bts
create index wy_bts_cityid_index on WY_BTS(city_id);
create index wy_bts_counrtyid_index on WY_BTS(COUNTY_ID);
create index wy_bts_name_index on WY_BTS(NAME);
create index wy_bts_indoor_index on WY_BTS(IS_INDOOR);
create index wy_bts_btsname_index on WY_BTS(BTS_NAME);
create index wy_bts_bscname_index on WY_BTS(BSC_NAME);
create index wy_bts_btsid_index on WY_BTS(BTS_ID);
create index wy_bts_manualflag_index on WY_BTS(MANUAL_FLAG);
create index wy_bts_deleteflag_index on WY_BTS(DELETE_FLAG);
--wy_bbu
create index wy_bbu_cityid_index on WY_BBU(city_id);
create index wy_bbu_counrtyid_index on WY_BBU(COUNTY_ID);
create index wy_bbu_name_index on WY_BBU(NAME);
create index wy_bbu_btsname_index on WY_BBU(BTS_NAME);
create index wy_bbu_bscname_index on WY_BBU(BSC_NAME);
create index wy_bbu_btsid_index on WY_BBU(BTS_ID);
create index wy_bbu_manualflag_index on WY_BBU(MANUAL_FLAG);
create index wy_bbu_deleteflag_index on WY_BBU(DELETE_FLAG);
create index wy_bbu_isshare_index on WY_BBU(IS_SHARE);
create index wy_bbu_bbutype_index on WY_BBU(BBU_TYPE);

--wy_cell

--wy_indoor_manual
create index wy_indoor_cityid_index on wy_indoor_manual(city_id);
create index wy_indoor_counrtyid_index on wy_indoor_manual(COUNTRY_ID);
create index wy_indoor_name_index on wy_indoor_manual(NAME);
create index wy_indoor_bscname_index on wy_indoor_manual(BSC_NAME);
create index wy_indoor_addflag_index on wy_indoor_manual(ADD_FLAG);

