create table WY_LIB_SCENE(
    ID                NUMBER         not null,
    city_id          NUMBER         not null,
    country_id       NUMBER         ,
    scene_type       NUMBER  not null,
    scene_level      NUMBER  not null,
    name              VARCHAR2(128)  not null,
    LONGITUDE        NUMBER(38,5),
    LATITUDE         NUMBER(38,5),
    remark           VARCHAR2(1024),
    in_time          DATE,
    in_user          NUMBER
    update_time       DATE,
    constraint PK_WY_LIB_SCENE primary key (ID)
);

create index wy_lib_scene_cityid_index on WY_LIB_SCENE(city_id);
create index wy_lib_scene_counrtyid_index on WY_LIB_SCENE(counrty_id);


create sequence WY_LIB_SCENE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;