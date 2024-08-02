package com.hj.rminf.dao.entity._gen;

import io.nop.orm.model.IEntityModel;
import io.nop.orm.support.DynamicOrmEntity;
import io.nop.orm.support.OrmEntitySet; //NOPMD - suppressed UnusedImports - Auto Gen Code
import io.nop.orm.IOrmEntitySet; //NOPMD - suppressed UnusedImports - Auto Gen Code
import io.nop.api.core.convert.ConvertHelper;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

import com.hj.rminf.dao.entity.RminfJgVehicleinfo;

// tell cpd to start ignoring code - CPD-OFF
/**
 *  集光过车（超员）: rminf_jg_vehicleinfo
 */
@SuppressWarnings({"PMD.UselessOverridingMethod","PMD.UnusedLocalVariable","java:S3008","java:S1602","java:S1128","java:S1161",
        "PMD.UnnecessaryFullyQualifiedName","PMD.EmptyControlStatement","java:S116","java:S115","java:S101","java:S3776"})
public class _RminfJgVehicleinfo extends DynamicOrmEntity{
    
    /* Id: ID INTEGER */
    public static final String PROP_NAME_id_ = "id_";
    public static final int PROP_ID_id_ = 1;
    
    /* 卡口编号: KKBH VARCHAR */
    public static final String PROP_NAME_kkbh = "kkbh";
    public static final int PROP_ID_kkbh = 2;
    
    /* 号牌号码: HPHM VARCHAR */
    public static final String PROP_NAME_hphm = "hphm";
    public static final int PROP_ID_hphm = 3;
    
    /* 通行图片1: TP1 VARCHAR */
    public static final String PROP_NAME_tp1 = "tp1";
    public static final int PROP_ID_tp1 = 4;
    
    /* 通行图片2: TP2 VARCHAR */
    public static final String PROP_NAME_tp2 = "tp2";
    public static final int PROP_ID_tp2 = 5;
    
    /* 通行图片3: TP3 VARCHAR */
    public static final String PROP_NAME_tp3 = "tp3";
    public static final int PROP_ID_tp3 = 6;
    
    /* 创建时间: ADD_TIME DATETIME */
    public static final String PROP_NAME_addTime = "addTime";
    public static final int PROP_ID_addTime = 7;
    
    /* 更新时间: UPDATE_TIME DATETIME */
    public static final String PROP_NAME_updateTime = "updateTime";
    public static final int PROP_ID_updateTime = 8;
    
    /* 逻辑删除: DELETED BOOLEAN */
    public static final String PROP_NAME_deleted = "deleted";
    public static final int PROP_ID_deleted = 9;
    

    private static int _PROP_ID_BOUND = 10;

    

    protected static final List<String> PK_PROP_NAMES = Arrays.asList(PROP_NAME_id_);
    protected static final int[] PK_PROP_IDS = new int[]{PROP_ID_id_};

    private static final String[] PROP_ID_TO_NAME = new String[10];
    private static final Map<String,Integer> PROP_NAME_TO_ID = new HashMap<>();
    static{
      
          PROP_ID_TO_NAME[PROP_ID_id_] = PROP_NAME_id_;
          PROP_NAME_TO_ID.put(PROP_NAME_id_, PROP_ID_id_);
      
          PROP_ID_TO_NAME[PROP_ID_kkbh] = PROP_NAME_kkbh;
          PROP_NAME_TO_ID.put(PROP_NAME_kkbh, PROP_ID_kkbh);
      
          PROP_ID_TO_NAME[PROP_ID_hphm] = PROP_NAME_hphm;
          PROP_NAME_TO_ID.put(PROP_NAME_hphm, PROP_ID_hphm);
      
          PROP_ID_TO_NAME[PROP_ID_tp1] = PROP_NAME_tp1;
          PROP_NAME_TO_ID.put(PROP_NAME_tp1, PROP_ID_tp1);
      
          PROP_ID_TO_NAME[PROP_ID_tp2] = PROP_NAME_tp2;
          PROP_NAME_TO_ID.put(PROP_NAME_tp2, PROP_ID_tp2);
      
          PROP_ID_TO_NAME[PROP_ID_tp3] = PROP_NAME_tp3;
          PROP_NAME_TO_ID.put(PROP_NAME_tp3, PROP_ID_tp3);
      
          PROP_ID_TO_NAME[PROP_ID_addTime] = PROP_NAME_addTime;
          PROP_NAME_TO_ID.put(PROP_NAME_addTime, PROP_ID_addTime);
      
          PROP_ID_TO_NAME[PROP_ID_updateTime] = PROP_NAME_updateTime;
          PROP_NAME_TO_ID.put(PROP_NAME_updateTime, PROP_ID_updateTime);
      
          PROP_ID_TO_NAME[PROP_ID_deleted] = PROP_NAME_deleted;
          PROP_NAME_TO_ID.put(PROP_NAME_deleted, PROP_ID_deleted);
      
    }

    
    /* Id: ID */
    private java.lang.Integer _id_;
    
    /* 卡口编号: KKBH */
    private java.lang.String _kkbh;
    
    /* 号牌号码: HPHM */
    private java.lang.String _hphm;
    
    /* 通行图片1: TP1 */
    private java.lang.String _tp1;
    
    /* 通行图片2: TP2 */
    private java.lang.String _tp2;
    
    /* 通行图片3: TP3 */
    private java.lang.String _tp3;
    
    /* 创建时间: ADD_TIME */
    private java.time.LocalDateTime _addTime;
    
    /* 更新时间: UPDATE_TIME */
    private java.time.LocalDateTime _updateTime;
    
    /* 逻辑删除: DELETED */
    private java.lang.Boolean _deleted;
    

    public _RminfJgVehicleinfo(){
        // for debug
    }

    protected RminfJgVehicleinfo newInstance(){
        RminfJgVehicleinfo entity = new RminfJgVehicleinfo();
        entity.orm_attach(orm_enhancer());
        entity.orm_entityModel(orm_entityModel());
        return entity;
    }

    @Override
    public RminfJgVehicleinfo cloneInstance() {
        RminfJgVehicleinfo entity = newInstance();
        orm_forEachInitedProp((value, propId) -> {
            entity.orm_propValue(propId,value);
        });
        return entity;
    }

    @Override
    public String orm_entityName() {
      // 如果存在实体模型对象，则以模型对象上的设置为准
      IEntityModel entityModel = orm_entityModel();
      if(entityModel != null)
          return entityModel.getName();
      return "com.hj.rminf.dao.entity.RminfJgVehicleinfo";
    }

    @Override
    public int orm_propIdBound(){
      IEntityModel entityModel = orm_entityModel();
      if(entityModel != null)
          return entityModel.getPropIdBound();
      return _PROP_ID_BOUND;
    }

    @Override
    public Object orm_id() {
    
        return buildSimpleId(PROP_ID_id_);
     
    }

    @Override
    public boolean orm_isPrimary(int propId) {
        
            return propId == PROP_ID_id_;
          
    }

    @Override
    public String orm_propName(int propId) {
        if(propId >= PROP_ID_TO_NAME.length)
            return super.orm_propName(propId);
        String propName = PROP_ID_TO_NAME[propId];
        if(propName == null)
           return super.orm_propName(propId);
        return propName;
    }

    @Override
    public int orm_propId(String propName) {
        Integer propId = PROP_NAME_TO_ID.get(propName);
        if(propId == null)
            return super.orm_propId(propName);
        return propId;
    }

    @Override
    public Object orm_propValue(int propId) {
        switch(propId){
        
            case PROP_ID_id_:
               return getId_();
        
            case PROP_ID_kkbh:
               return getKkbh();
        
            case PROP_ID_hphm:
               return getHphm();
        
            case PROP_ID_tp1:
               return getTp1();
        
            case PROP_ID_tp2:
               return getTp2();
        
            case PROP_ID_tp3:
               return getTp3();
        
            case PROP_ID_addTime:
               return getAddTime();
        
            case PROP_ID_updateTime:
               return getUpdateTime();
        
            case PROP_ID_deleted:
               return getDeleted();
        
           default:
              return super.orm_propValue(propId);
        }
    }

    

    @Override
    public void orm_propValue(int propId, Object value){
        switch(propId){
        
            case PROP_ID_id_:{
               java.lang.Integer typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toInteger(value,
                       err-> newTypeConversionError(PROP_NAME_id_));
               }
               setId_(typedValue);
               break;
            }
        
            case PROP_ID_kkbh:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_kkbh));
               }
               setKkbh(typedValue);
               break;
            }
        
            case PROP_ID_hphm:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_hphm));
               }
               setHphm(typedValue);
               break;
            }
        
            case PROP_ID_tp1:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_tp1));
               }
               setTp1(typedValue);
               break;
            }
        
            case PROP_ID_tp2:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_tp2));
               }
               setTp2(typedValue);
               break;
            }
        
            case PROP_ID_tp3:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_tp3));
               }
               setTp3(typedValue);
               break;
            }
        
            case PROP_ID_addTime:{
               java.time.LocalDateTime typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toLocalDateTime(value,
                       err-> newTypeConversionError(PROP_NAME_addTime));
               }
               setAddTime(typedValue);
               break;
            }
        
            case PROP_ID_updateTime:{
               java.time.LocalDateTime typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toLocalDateTime(value,
                       err-> newTypeConversionError(PROP_NAME_updateTime));
               }
               setUpdateTime(typedValue);
               break;
            }
        
            case PROP_ID_deleted:{
               java.lang.Boolean typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toBoolean(value,
                       err-> newTypeConversionError(PROP_NAME_deleted));
               }
               setDeleted(typedValue);
               break;
            }
        
           default:
              super.orm_propValue(propId,value);
        }
    }

    @Override
    public void orm_internalSet(int propId, Object value) {
        switch(propId){
        
            case PROP_ID_id_:{
               onInitProp(propId);
               this._id_ = (java.lang.Integer)value;
               orm_id(); // 如果是设置主键字段，则触发watcher
               break;
            }
        
            case PROP_ID_kkbh:{
               onInitProp(propId);
               this._kkbh = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_hphm:{
               onInitProp(propId);
               this._hphm = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_tp1:{
               onInitProp(propId);
               this._tp1 = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_tp2:{
               onInitProp(propId);
               this._tp2 = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_tp3:{
               onInitProp(propId);
               this._tp3 = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_addTime:{
               onInitProp(propId);
               this._addTime = (java.time.LocalDateTime)value;
               
               break;
            }
        
            case PROP_ID_updateTime:{
               onInitProp(propId);
               this._updateTime = (java.time.LocalDateTime)value;
               
               break;
            }
        
            case PROP_ID_deleted:{
               onInitProp(propId);
               this._deleted = (java.lang.Boolean)value;
               
               break;
            }
        
           default:
              super.orm_internalSet(propId,value);
        }
    }

    
    /**
     * Id: ID
     */
    public java.lang.Integer getId_(){
         onPropGet(PROP_ID_id_);
         return _id_;
    }

    /**
     * Id: ID
     */
    public void setId_(java.lang.Integer value){
        if(onPropSet(PROP_ID_id_,value)){
            this._id_ = value;
            internalClearRefs(PROP_ID_id_);
            orm_id();
        }
    }
    
    /**
     * 卡口编号: KKBH
     */
    public java.lang.String getKkbh(){
         onPropGet(PROP_ID_kkbh);
         return _kkbh;
    }

    /**
     * 卡口编号: KKBH
     */
    public void setKkbh(java.lang.String value){
        if(onPropSet(PROP_ID_kkbh,value)){
            this._kkbh = value;
            internalClearRefs(PROP_ID_kkbh);
            
        }
    }
    
    /**
     * 号牌号码: HPHM
     */
    public java.lang.String getHphm(){
         onPropGet(PROP_ID_hphm);
         return _hphm;
    }

    /**
     * 号牌号码: HPHM
     */
    public void setHphm(java.lang.String value){
        if(onPropSet(PROP_ID_hphm,value)){
            this._hphm = value;
            internalClearRefs(PROP_ID_hphm);
            
        }
    }
    
    /**
     * 通行图片1: TP1
     */
    public java.lang.String getTp1(){
         onPropGet(PROP_ID_tp1);
         return _tp1;
    }

    /**
     * 通行图片1: TP1
     */
    public void setTp1(java.lang.String value){
        if(onPropSet(PROP_ID_tp1,value)){
            this._tp1 = value;
            internalClearRefs(PROP_ID_tp1);
            
        }
    }
    
    /**
     * 通行图片2: TP2
     */
    public java.lang.String getTp2(){
         onPropGet(PROP_ID_tp2);
         return _tp2;
    }

    /**
     * 通行图片2: TP2
     */
    public void setTp2(java.lang.String value){
        if(onPropSet(PROP_ID_tp2,value)){
            this._tp2 = value;
            internalClearRefs(PROP_ID_tp2);
            
        }
    }
    
    /**
     * 通行图片3: TP3
     */
    public java.lang.String getTp3(){
         onPropGet(PROP_ID_tp3);
         return _tp3;
    }

    /**
     * 通行图片3: TP3
     */
    public void setTp3(java.lang.String value){
        if(onPropSet(PROP_ID_tp3,value)){
            this._tp3 = value;
            internalClearRefs(PROP_ID_tp3);
            
        }
    }
    
    /**
     * 创建时间: ADD_TIME
     */
    public java.time.LocalDateTime getAddTime(){
         onPropGet(PROP_ID_addTime);
         return _addTime;
    }

    /**
     * 创建时间: ADD_TIME
     */
    public void setAddTime(java.time.LocalDateTime value){
        if(onPropSet(PROP_ID_addTime,value)){
            this._addTime = value;
            internalClearRefs(PROP_ID_addTime);
            
        }
    }
    
    /**
     * 更新时间: UPDATE_TIME
     */
    public java.time.LocalDateTime getUpdateTime(){
         onPropGet(PROP_ID_updateTime);
         return _updateTime;
    }

    /**
     * 更新时间: UPDATE_TIME
     */
    public void setUpdateTime(java.time.LocalDateTime value){
        if(onPropSet(PROP_ID_updateTime,value)){
            this._updateTime = value;
            internalClearRefs(PROP_ID_updateTime);
            
        }
    }
    
    /**
     * 逻辑删除: DELETED
     */
    public java.lang.Boolean getDeleted(){
         onPropGet(PROP_ID_deleted);
         return _deleted;
    }

    /**
     * 逻辑删除: DELETED
     */
    public void setDeleted(java.lang.Boolean value){
        if(onPropSet(PROP_ID_deleted,value)){
            this._deleted = value;
            internalClearRefs(PROP_ID_deleted);
            
        }
    }
    
}
// resume CPD analysis - CPD-ON
