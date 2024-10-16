
CREATE TABLE rminf_jg_vehicleinfo(
  ID VARCHAR2(50) NOT NULL ,
  KKBH VARCHAR2(32) NOT NULL ,
  FXLX VARCHAR2(32)  ,
  CDH VARCHAR2(32)  ,
  HPHM VARCHAR2(32)  ,
  HPZL VARCHAR2(32)  ,
  GCSJ TIMESTAMP  ,
  CLSD VARCHAR2(32)  ,
  CLXS VARCHAR2(32)  ,
  WFDM VARCHAR2(32)  ,
  CWKC VARCHAR2(32)  ,
  HPYS VARCHAR2(32)  ,
  CLLX VARCHAR2(32)  ,
  FZHPZL VARCHAR2(32)  ,
  FZHPHM VARCHAR2(32)  ,
  FZHPYS VARCHAR2(32)  ,
  CLPP VARCHAR2(32)  ,
  CLWX VARCHAR2(32)  ,
  CSYS VARCHAR2(32)  ,
  TPLJ VARCHAR2(32)  ,
  TP1 VARCHAR2(255)  ,
  TP2 VARCHAR2(255)  ,
  TP3 VARCHAR2(255)  ,
  TZTP VARCHAR2(32)  ,
  CID VARCHAR2(32)  ,
  TID VARCHAR2(32)  ,
  ZKRS VARCHAR2(32)   default '0' ,
  CREATE_TIME DATE NOT NULL ,
  UPDATE_TIME DATE NOT NULL ,
  constraint PK_rminf_jg_vehicleinfo primary key (ID)
);


      COMMENT ON TABLE rminf_jg_vehicleinfo IS '集光过车（超员）';
                
      COMMENT ON COLUMN rminf_jg_vehicleinfo.ID IS 'Id';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.KKBH IS '卡口编号';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.FXLX IS '方向类型';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CDH IS '车道号';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.HPHM IS '号牌号码';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.HPZL IS '号牌种类';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.GCSJ IS '过车时间';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CLSD IS '车辆速度';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CLXS IS '车辆限速';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.WFDM IS '违章行为编码';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CWKC IS '车外廓长';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.HPYS IS '号牌颜色';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CLLX IS '车辆类型';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.FZHPZL IS '辅助号牌种类';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.FZHPHM IS '辅助号牌号码';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.FZHPYS IS '辅助号牌颜色';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CLPP IS '车辆品牌';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CLWX IS '车辆外形';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CSYS IS '车身颜色';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.TPLJ IS '通行图片路径';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.TP1 IS '通行图片1';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.TP2 IS '通行图片2';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.TP3 IS '通行图片3';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.TZTP IS '特征图片';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CID IS 'Rfid卡号';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.TID IS 'Rfid卡号';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.ZKRS IS '载客人数';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.CREATE_TIME IS '创建时间';
                    
      COMMENT ON COLUMN rminf_jg_vehicleinfo.UPDATE_TIME IS '修改时间';
                    
