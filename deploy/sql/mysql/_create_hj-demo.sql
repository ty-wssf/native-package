
CREATE TABLE nop_demo(
  ID INTEGER NOT NULL    COMMENT 'Id',
  NAME VARCHAR(255) NOT NULL    COMMENT '名称',
  ENABLED BOOLEAN NOT NULL  default 'false'    COMMENT '是否启用',
  ADD_TIME DATETIME NULL    COMMENT '创建时间',
  UPDATE_TIME DATETIME NULL    COMMENT '更新时间',
  DELETED BOOLEAN NULL    COMMENT '逻辑删除',
  constraint PK_nop_demo primary key (ID)
);


   ALTER TABLE nop_demo COMMENT 'Nopdemo';
                
