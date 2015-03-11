create table WY_LIB_SCENE(
    ID                NUMBER         not null,
    city_id          NUMBER         not null,
    country_id       NUMBER         ,
    scene_type       NUMBER  not null,
    scene_level      NUMBER  not null,
    name             VARCHAR2(128)  not null,
    LONGITUDE        NUMBER(38,5),
    LATITUDE         NUMBER(38,5),
    remark           VARCHAR2(1024),
    in_time          DATE,
    in_user          NUMBER,
    update_time      DATE,
    delete_flag      NUMBER(1),
    constraint PK_WY_LIB_SCENE primary key (ID)
)

create index wy_lib_scene_cityid_index on WY_LIB_SCENE(city_id);
create index wy_lib_scene_counrtyid_index on WY_LIB_SCENE(country_id);


create sequence WY_LIB_SCENE_SEQ
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;


insert into wy_cons(ID, code,name, groupcode, orderby)values(276,1,'高铁','SCENE_TYPE',1);
insert into wy_cons(ID, code,name, groupcode, orderby)values(278,2,'高速公路','SCENE_TYPE',2);
insert into wy_cons(ID, code,name, groupcode, orderby)values(279,3,'机场','SCENE_TYPE',3);
insert into wy_cons(ID, code,name, groupcode, orderby)values(280,4,'车站港口码头','SCENE_TYPE',4);
insert into wy_cons(ID, code,name, groupcode, orderby)values(281,5,'住宅小区','SCENE_TYPE',5);
insert into wy_cons(ID, code,name, groupcode, orderby)values(282,6,'风景区','SCENE_TYPE',6);
insert into wy_cons(ID, code,name, groupcode, orderby)values(283,7,'宾馆','SCENE_TYPE',7);
insert into wy_cons(ID, code,name, groupcode, orderby)values(284,8,'写字楼','SCENE_TYPE',8);
insert into wy_cons(ID, code,name, groupcode, orderby)values(285,9,'商场','SCENE_TYPE',9);
insert into wy_cons(ID, code,name, groupcode, orderby)values(286,10,'体育场','SCENE_TYPE',10);
insert into wy_cons(ID, code,name, groupcode, orderby)values(287,11,'地铁','SCENE_TYPE',11);
insert into wy_cons(ID, code,name, groupcode, orderby)values(288,12,'校园','SCENE_TYPE',12);
insert into wy_cons(ID, code,name, groupcode, orderby)values(289,13,'隧道','SCENE_TYPE',13);
insert into wy_cons(ID, code,name, groupcode, orderby)values(290,14,'医院','SCENE_TYPE',14);
insert into wy_cons(ID, code,name, groupcode, orderby)values(291,15,'政府机关','SCENE_TYPE',15);
insert into wy_cons(ID, code,name, groupcode, orderby)values(292,16,'工业园区','SCENE_TYPE',16);
insert into wy_cons(ID, code,name, groupcode, orderby)values(293,17,'农村','SCENE_TYPE',17);
insert into wy_cons(ID, code,name, groupcode, orderby)values(294,18,'聚类市场','SCENE_TYPE',18);
insert into wy_cons(ID, code,name, groupcode, orderby)values(295,19,'休闲娱乐场所','SCENE_TYPE',19);
--场景级别
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(296,1,'高铁','SCENE_LEVEL',1,1);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(297,2,'动车','SCENE_LEVEL',2,1);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(298,1,'高速','SCENE_LEVEL',1,2);
insert into wy_cons(ID, code,name, groupcode, orderby，pid, remark)values(299,1,'1级','SCENE_LEVEL',1,3,'跑道长度<800');
insert into wy_cons(ID, code,name, groupcode, orderby，pid, remark)values(300,2,'2级','SCENE_LEVEL',2,3,'跑道长度800-1200');
insert into wy_cons(ID, code,name, groupcode, orderby，pid, remark)values(301,3,'3级','SCENE_LEVEL',3,3,'跑道长度1200-1800');
insert into wy_cons(ID, code,name, groupcode, orderby，pid, remark)values(302,4,'4级','SCENE_LEVEL',4,3,'跑道长度>1200');
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(303,1,'客运','SCENE_LEVEL',1,4);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(304,2,'大型货运','SCENE_LEVEL',2,4);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(305,3,'中型货运','SCENE_LEVEL',3,4);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(306,4,'其他','SCENE_LEVEL',4,4);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(307,1,'多层','SCENE_LEVEL',1,5);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(308,2,'高层','SCENE_LEVEL',2,5);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(309,3,'别墅','SCENE_LEVEL',3,5);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(310,4,'棚户区','SCENE_LEVEL',4,5);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(311,5,'其他','SCENE_LEVEL',5,5);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(312,5,'5A','SCENE_LEVEL',1,6);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(313,5,'4A','SCENE_LEVEL',2,6);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(314,3,'3A','SCENE_LEVEL',3,6);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(315,2,'2A','SCENE_LEVEL',4,6);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(316,1,'A','SCENE_LEVEL',5,6);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(317,5,'五星','SCENE_LEVEL',1,7);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(318,4,'四星','SCENE_LEVEL',2,7);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(319,3,'三星','SCENE_LEVEL',3,7);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(320,2,'二星','SCENE_LEVEL',4,7);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(321,1,'一星','SCENE_LEVEL',5,7);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(322,1,'大型','SCENE_LEVEL',1,8);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(323,2,'其他','SCENE_LEVEL',2,8);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(324,1,'大型','SCENE_LEVEL',1,9);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(325,2,'其他','SCENE_LEVEL',2,9);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(326,1,'室外综合体育场','SCENE_LEVEL',1,10);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(327,2,'室内综合体育馆','SCENE_LEVEL',2,10);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(328,3,'其他','SCENE_LEVEL',3,10);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(329,1,'全地下','SCENE_LEVEL',1,11);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(330,2,'全地上','SCENE_LEVEL',2,11);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(331,3,'地下&地上','SCENE_LEVEL',3,11);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(332,1,'大学','SCENE_LEVEL',1,12);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(333,2,'学院','SCENE_LEVEL',2,12);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(334,3,'职业学校','SCENE_LEVEL',3,12);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(335,4,'高中','SCENE_LEVEL',4,12);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(336,5,'初中','SCENE_LEVEL',5,12);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(337,6,'小学','SCENE_LEVEL',6,12);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(338,1,'公路','SCENE_LEVEL',1,13);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(339,2,'铁路','SCENE_LEVEL',2,13);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(340,3,'其它','SCENE_LEVEL',3,13);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(341,1,'三特','SCENE_LEVEL',1,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(342,2,'三甲','SCENE_LEVEL',2,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(343,3,'三乙','SCENE_LEVEL',3,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(344,4,'三丙','SCENE_LEVEL',4,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(345,5,'二甲','SCENE_LEVEL',5,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(346,6,'二乙','SCENE_LEVEL',6,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(347,7,'二丙','SCENE_LEVEL',7,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(348,8,'一甲','SCENE_LEVEL',8,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(349,9,'一乙','SCENE_LEVEL',9,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(350,10,'一丙','SCENE_LEVEL',10,14);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(351,1,'国家','SCENE_LEVEL',1,15);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(352,2,'省','SCENE_LEVEL',2,15);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(353,3,'地级市','SCENE_LEVEL',3,15);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(354,4,'县级市','SCENE_LEVEL',4,15);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(355,5,'县','SCENE_LEVEL',5,15);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(356,6,'乡镇','SCENE_LEVEL',6,15);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(357,7,'村','SCENE_LEVEL',7,15);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(358,1,'国家级','SCENE_LEVEL',1,16);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(359,2,'省级','SCENE_LEVEL',2,16);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(360,3,'市级','SCENE_LEVEL',3,16);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(361,4,'县级','SCENE_LEVEL',4,16);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(362,5,'其他','SCENE_LEVEL',5,16);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(363,1,'新农村示范村','SCENE_LEVEL',1,17);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(364,2,'其他','SCENE_LEVEL',2,17);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(365,1,'无级别','SCENE_LEVEL',1,18);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(366,1,'全地下','SCENE_LEVEL',1,19);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(367,2,'全地上','SCENE_LEVEL',2,19);
insert into wy_cons(ID, code,name, groupcode, orderby，pid)values(368,3,'地下&地上','SCENE_LEVEL',3,19);

--道路类别
insert into wy_cons(ID, code,name, groupcode, orderby)values(369,1,'国家高速','ROAD_TYPE',1);
insert into wy_cons(ID, code,name, groupcode, orderby)values(370,2,'国家高速联络线','ROAD_TYPE',2);
insert into wy_cons(ID, code,name, groupcode, orderby)values(371,3,'省内高速','ROAD_TYPE',3);

select * from wy_menu;
update wy_menu set url='school/sceneLib.action',name='场景库管理' where id=2;
delete wy_menu where id=8;

drop table WY_LIB_ROAD;
 CREATE TABLE WY_LIB_ROAD
   (	
   ID NUMBER NOT NULL,
   ROAD_NO VARCHAR2(65 BYTE), 
   CITY_ID NUMBER NOT NULL, 
  ROAD_PROP NUMBER NOT NULL, 
	 NAME VARCHAR2(512 BYTE) NOT NULL, 
	DOMESTIC_START VARCHAR2(256 BYTE), 
	DOMESTIC_END VARCHAR2(256 BYTE), 
	MILEAGE NUMBER(20,2), 
	OPEN_STATUS NUMBER(1,0), 
	IN_TIME DATE, 
  IN_USER NUMBER,
	UPDATE_TIME DATE, 
	REMARK VARCHAR2(512 BYTE), 
	DELETE_FLAG NUMBER DEFAULT 0,
  constraint PK_WY_LIB_ROAD primary key (ID)
  )
  
 
   
   create sequence WY_LIB_ROAD_SEQ
  minvalue 1
  maxvalue 999999999999999999999999999
  start with 1
  increment by 1
  cache 20;
 




