
CREATE TABLE rminf_jg_vehicleinfo(
  ID VARCHAR(50) NOT NULL    COMMENT 'Id',
  KKBH VARCHAR(32) NOT NULL    COMMENT '卡口编号',
  FXLX VARCHAR(32) NULL    COMMENT '方向类型',
  CDH VARCHAR(32) NULL    COMMENT '车道号',
  HPHM VARCHAR(32) NULL    COMMENT '号牌号码',
  HPZL VARCHAR(32) NULL    COMMENT '号牌种类',
  GCSJ TIMESTAMP(3) NULL    COMMENT '过车时间',
  CLSD VARCHAR(32) NULL    COMMENT '车辆速度',
  CLXS VARCHAR(32) NULL    COMMENT '车辆限速',
  WFDM VARCHAR(32) NULL    COMMENT '违章行为编码',
  CWKC VARCHAR(32) NULL    COMMENT '车外廓长',
  HPYS VARCHAR(32) NULL    COMMENT '号牌颜色',
  CLLX VARCHAR(32) NULL    COMMENT '车辆类型',
  FZHPZL VARCHAR(32) NULL    COMMENT '辅助号牌种类',
  FZHPHM VARCHAR(32) NULL    COMMENT '辅助号牌号码',
  FZHPYS VARCHAR(32) NULL    COMMENT '辅助号牌颜色',
  CLPP VARCHAR(32) NULL    COMMENT '车辆品牌',
  CLWX VARCHAR(32) NULL    COMMENT '车辆外形',
  CSYS VARCHAR(32) NULL    COMMENT '车身颜色',
  TPLJ VARCHAR(32) NULL    COMMENT '通行图片路径',
  TP1 VARCHAR(255) NULL    COMMENT '通行图片1',
  TP2 VARCHAR(255) NULL    COMMENT '通行图片2',
  TP3 VARCHAR(255) NULL    COMMENT '通行图片3',
  TZTP VARCHAR(32) NULL    COMMENT '特征图片',
  CID VARCHAR(32) NULL    COMMENT 'Rfid卡号',
  TID VARCHAR(32) NULL    COMMENT 'Rfid卡号',
  ZKRS VARCHAR(32) default '0'  NULL    COMMENT '载客人数',
  CREATE_TIME DATETIME NOT NULL    COMMENT '创建时间',
  UPDATE_TIME DATETIME NOT NULL    COMMENT '修改时间',
  constraint PK_rminf_jg_vehicleinfo primary key (ID)
);


   ALTER TABLE rminf_jg_vehicleinfo COMMENT '集光过车（超员）';
                
