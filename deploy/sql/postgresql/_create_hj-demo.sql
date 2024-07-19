
CREATE TABLE nop_demo(
  ID INT4 NOT NULL ,
  NAME VARCHAR(255) NOT NULL ,
  ENABLED BOOLEAN NOT NULL  default 'false' ,
  ADD_TIME TIMESTAMP  ,
  UPDATE_TIME TIMESTAMP  ,
  DELETED BOOLEAN  ,
  constraint PK_nop_demo primary key (ID)
);


      COMMENT ON TABLE nop_demo IS 'Nopdemo';
                
      COMMENT ON COLUMN nop_demo.ID IS 'Id';
                    
      COMMENT ON COLUMN nop_demo.NAME IS '名称';
                    
      COMMENT ON COLUMN nop_demo.ENABLED IS '是否启用';
                    
      COMMENT ON COLUMN nop_demo.ADD_TIME IS '创建时间';
                    
      COMMENT ON COLUMN nop_demo.UPDATE_TIME IS '更新时间';
                    
      COMMENT ON COLUMN nop_demo.DELETED IS '逻辑删除';
                    
