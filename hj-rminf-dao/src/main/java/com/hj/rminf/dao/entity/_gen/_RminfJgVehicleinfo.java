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
    
    /* Id: ID VARCHAR */
    public static final String PROP_NAME_id_ = "id_";
    public static final int PROP_ID_id_ = 1;
    
    /* 卡口编号: KKBH VARCHAR */
    public static final String PROP_NAME_kkbh = "kkbh";
    public static final int PROP_ID_kkbh = 2;
    
    /* 方向类型: FXLX VARCHAR */
    public static final String PROP_NAME_fxlx = "fxlx";
    public static final int PROP_ID_fxlx = 3;
    
    /* 车道号: CDH VARCHAR */
    public static final String PROP_NAME_cdh = "cdh";
    public static final int PROP_ID_cdh = 4;
    
    /* 号牌号码: HPHM VARCHAR */
    public static final String PROP_NAME_hphm = "hphm";
    public static final int PROP_ID_hphm = 5;
    
    /* 号牌种类: HPZL VARCHAR */
    public static final String PROP_NAME_hpzl = "hpzl";
    public static final int PROP_ID_hpzl = 6;
    
    /* 过车时间: GCSJ TIMESTAMP */
    public static final String PROP_NAME_gcsj = "gcsj";
    public static final int PROP_ID_gcsj = 7;
    
    /* 车辆速度: CLSD VARCHAR */
    public static final String PROP_NAME_clsd = "clsd";
    public static final int PROP_ID_clsd = 8;
    
    /* 车辆限速: CLXS VARCHAR */
    public static final String PROP_NAME_clxs = "clxs";
    public static final int PROP_ID_clxs = 9;
    
    /* 违章行为编码: WFDM VARCHAR */
    public static final String PROP_NAME_wfdm = "wfdm";
    public static final int PROP_ID_wfdm = 10;
    
    /* 车外廓长: CWKC VARCHAR */
    public static final String PROP_NAME_cwkc = "cwkc";
    public static final int PROP_ID_cwkc = 11;
    
    /* 号牌颜色: HPYS VARCHAR */
    public static final String PROP_NAME_hpys = "hpys";
    public static final int PROP_ID_hpys = 12;
    
    /* 车辆类型: CLLX VARCHAR */
    public static final String PROP_NAME_cllx = "cllx";
    public static final int PROP_ID_cllx = 13;
    
    /* 辅助号牌种类: FZHPZL VARCHAR */
    public static final String PROP_NAME_fzhpzl = "fzhpzl";
    public static final int PROP_ID_fzhpzl = 14;
    
    /* 辅助号牌号码: FZHPHM VARCHAR */
    public static final String PROP_NAME_fzhphm = "fzhphm";
    public static final int PROP_ID_fzhphm = 15;
    
    /* 辅助号牌颜色: FZHPYS VARCHAR */
    public static final String PROP_NAME_fzhpys = "fzhpys";
    public static final int PROP_ID_fzhpys = 16;
    
    /* 车辆品牌: CLPP VARCHAR */
    public static final String PROP_NAME_clpp = "clpp";
    public static final int PROP_ID_clpp = 17;
    
    /* 车辆外形: CLWX VARCHAR */
    public static final String PROP_NAME_clwx = "clwx";
    public static final int PROP_ID_clwx = 18;
    
    /* 车身颜色: CSYS VARCHAR */
    public static final String PROP_NAME_csys = "csys";
    public static final int PROP_ID_csys = 19;
    
    /* 通行图片路径: TPLJ VARCHAR */
    public static final String PROP_NAME_tplj = "tplj";
    public static final int PROP_ID_tplj = 20;
    
    /* 通行图片1: TP1 VARCHAR */
    public static final String PROP_NAME_tp1 = "tp1";
    public static final int PROP_ID_tp1 = 21;
    
    /* 通行图片2: TP2 VARCHAR */
    public static final String PROP_NAME_tp2 = "tp2";
    public static final int PROP_ID_tp2 = 22;
    
    /* 通行图片3: TP3 VARCHAR */
    public static final String PROP_NAME_tp3 = "tp3";
    public static final int PROP_ID_tp3 = 23;
    
    /* 特征图片: TZTP VARCHAR */
    public static final String PROP_NAME_tztp = "tztp";
    public static final int PROP_ID_tztp = 24;
    
    /* Rfid卡号: CID VARCHAR */
    public static final String PROP_NAME_cid = "cid";
    public static final int PROP_ID_cid = 25;
    
    /* Rfid卡号: TID VARCHAR */
    public static final String PROP_NAME_tid = "tid";
    public static final int PROP_ID_tid = 26;
    
    /* 载客人数: ZKRS VARCHAR */
    public static final String PROP_NAME_zkrs = "zkrs";
    public static final int PROP_ID_zkrs = 27;
    
    /* 创建时间: CREATE_TIME DATETIME */
    public static final String PROP_NAME_createTime = "createTime";
    public static final int PROP_ID_createTime = 28;
    
    /* 修改时间: UPDATE_TIME DATETIME */
    public static final String PROP_NAME_updateTime = "updateTime";
    public static final int PROP_ID_updateTime = 29;
    

    private static int _PROP_ID_BOUND = 30;

    
    /* component:  */
    public static final String PROP_NAME_tp1Component = "tp1Component";
    
    /* component:  */
    public static final String PROP_NAME_tp2Component = "tp2Component";
    
    /* component:  */
    public static final String PROP_NAME_tp3Component = "tp3Component";
    

    protected static final List<String> PK_PROP_NAMES = Arrays.asList(PROP_NAME_id_);
    protected static final int[] PK_PROP_IDS = new int[]{PROP_ID_id_};

    private static final String[] PROP_ID_TO_NAME = new String[30];
    private static final Map<String,Integer> PROP_NAME_TO_ID = new HashMap<>();
    static{
      
          PROP_ID_TO_NAME[PROP_ID_id_] = PROP_NAME_id_;
          PROP_NAME_TO_ID.put(PROP_NAME_id_, PROP_ID_id_);
      
          PROP_ID_TO_NAME[PROP_ID_kkbh] = PROP_NAME_kkbh;
          PROP_NAME_TO_ID.put(PROP_NAME_kkbh, PROP_ID_kkbh);
      
          PROP_ID_TO_NAME[PROP_ID_fxlx] = PROP_NAME_fxlx;
          PROP_NAME_TO_ID.put(PROP_NAME_fxlx, PROP_ID_fxlx);
      
          PROP_ID_TO_NAME[PROP_ID_cdh] = PROP_NAME_cdh;
          PROP_NAME_TO_ID.put(PROP_NAME_cdh, PROP_ID_cdh);
      
          PROP_ID_TO_NAME[PROP_ID_hphm] = PROP_NAME_hphm;
          PROP_NAME_TO_ID.put(PROP_NAME_hphm, PROP_ID_hphm);
      
          PROP_ID_TO_NAME[PROP_ID_hpzl] = PROP_NAME_hpzl;
          PROP_NAME_TO_ID.put(PROP_NAME_hpzl, PROP_ID_hpzl);
      
          PROP_ID_TO_NAME[PROP_ID_gcsj] = PROP_NAME_gcsj;
          PROP_NAME_TO_ID.put(PROP_NAME_gcsj, PROP_ID_gcsj);
      
          PROP_ID_TO_NAME[PROP_ID_clsd] = PROP_NAME_clsd;
          PROP_NAME_TO_ID.put(PROP_NAME_clsd, PROP_ID_clsd);
      
          PROP_ID_TO_NAME[PROP_ID_clxs] = PROP_NAME_clxs;
          PROP_NAME_TO_ID.put(PROP_NAME_clxs, PROP_ID_clxs);
      
          PROP_ID_TO_NAME[PROP_ID_wfdm] = PROP_NAME_wfdm;
          PROP_NAME_TO_ID.put(PROP_NAME_wfdm, PROP_ID_wfdm);
      
          PROP_ID_TO_NAME[PROP_ID_cwkc] = PROP_NAME_cwkc;
          PROP_NAME_TO_ID.put(PROP_NAME_cwkc, PROP_ID_cwkc);
      
          PROP_ID_TO_NAME[PROP_ID_hpys] = PROP_NAME_hpys;
          PROP_NAME_TO_ID.put(PROP_NAME_hpys, PROP_ID_hpys);
      
          PROP_ID_TO_NAME[PROP_ID_cllx] = PROP_NAME_cllx;
          PROP_NAME_TO_ID.put(PROP_NAME_cllx, PROP_ID_cllx);
      
          PROP_ID_TO_NAME[PROP_ID_fzhpzl] = PROP_NAME_fzhpzl;
          PROP_NAME_TO_ID.put(PROP_NAME_fzhpzl, PROP_ID_fzhpzl);
      
          PROP_ID_TO_NAME[PROP_ID_fzhphm] = PROP_NAME_fzhphm;
          PROP_NAME_TO_ID.put(PROP_NAME_fzhphm, PROP_ID_fzhphm);
      
          PROP_ID_TO_NAME[PROP_ID_fzhpys] = PROP_NAME_fzhpys;
          PROP_NAME_TO_ID.put(PROP_NAME_fzhpys, PROP_ID_fzhpys);
      
          PROP_ID_TO_NAME[PROP_ID_clpp] = PROP_NAME_clpp;
          PROP_NAME_TO_ID.put(PROP_NAME_clpp, PROP_ID_clpp);
      
          PROP_ID_TO_NAME[PROP_ID_clwx] = PROP_NAME_clwx;
          PROP_NAME_TO_ID.put(PROP_NAME_clwx, PROP_ID_clwx);
      
          PROP_ID_TO_NAME[PROP_ID_csys] = PROP_NAME_csys;
          PROP_NAME_TO_ID.put(PROP_NAME_csys, PROP_ID_csys);
      
          PROP_ID_TO_NAME[PROP_ID_tplj] = PROP_NAME_tplj;
          PROP_NAME_TO_ID.put(PROP_NAME_tplj, PROP_ID_tplj);
      
          PROP_ID_TO_NAME[PROP_ID_tp1] = PROP_NAME_tp1;
          PROP_NAME_TO_ID.put(PROP_NAME_tp1, PROP_ID_tp1);
      
          PROP_ID_TO_NAME[PROP_ID_tp2] = PROP_NAME_tp2;
          PROP_NAME_TO_ID.put(PROP_NAME_tp2, PROP_ID_tp2);
      
          PROP_ID_TO_NAME[PROP_ID_tp3] = PROP_NAME_tp3;
          PROP_NAME_TO_ID.put(PROP_NAME_tp3, PROP_ID_tp3);
      
          PROP_ID_TO_NAME[PROP_ID_tztp] = PROP_NAME_tztp;
          PROP_NAME_TO_ID.put(PROP_NAME_tztp, PROP_ID_tztp);
      
          PROP_ID_TO_NAME[PROP_ID_cid] = PROP_NAME_cid;
          PROP_NAME_TO_ID.put(PROP_NAME_cid, PROP_ID_cid);
      
          PROP_ID_TO_NAME[PROP_ID_tid] = PROP_NAME_tid;
          PROP_NAME_TO_ID.put(PROP_NAME_tid, PROP_ID_tid);
      
          PROP_ID_TO_NAME[PROP_ID_zkrs] = PROP_NAME_zkrs;
          PROP_NAME_TO_ID.put(PROP_NAME_zkrs, PROP_ID_zkrs);
      
          PROP_ID_TO_NAME[PROP_ID_createTime] = PROP_NAME_createTime;
          PROP_NAME_TO_ID.put(PROP_NAME_createTime, PROP_ID_createTime);
      
          PROP_ID_TO_NAME[PROP_ID_updateTime] = PROP_NAME_updateTime;
          PROP_NAME_TO_ID.put(PROP_NAME_updateTime, PROP_ID_updateTime);
      
    }

    
    /* Id: ID */
    private java.lang.String _id_;
    
    /* 卡口编号: KKBH */
    private java.lang.String _kkbh;
    
    /* 方向类型: FXLX */
    private java.lang.String _fxlx;
    
    /* 车道号: CDH */
    private java.lang.String _cdh;
    
    /* 号牌号码: HPHM */
    private java.lang.String _hphm;
    
    /* 号牌种类: HPZL */
    private java.lang.String _hpzl;
    
    /* 过车时间: GCSJ */
    private java.sql.Timestamp _gcsj;
    
    /* 车辆速度: CLSD */
    private java.lang.String _clsd;
    
    /* 车辆限速: CLXS */
    private java.lang.String _clxs;
    
    /* 违章行为编码: WFDM */
    private java.lang.String _wfdm;
    
    /* 车外廓长: CWKC */
    private java.lang.String _cwkc;
    
    /* 号牌颜色: HPYS */
    private java.lang.String _hpys;
    
    /* 车辆类型: CLLX */
    private java.lang.String _cllx;
    
    /* 辅助号牌种类: FZHPZL */
    private java.lang.String _fzhpzl;
    
    /* 辅助号牌号码: FZHPHM */
    private java.lang.String _fzhphm;
    
    /* 辅助号牌颜色: FZHPYS */
    private java.lang.String _fzhpys;
    
    /* 车辆品牌: CLPP */
    private java.lang.String _clpp;
    
    /* 车辆外形: CLWX */
    private java.lang.String _clwx;
    
    /* 车身颜色: CSYS */
    private java.lang.String _csys;
    
    /* 通行图片路径: TPLJ */
    private java.lang.String _tplj;
    
    /* 通行图片1: TP1 */
    private java.lang.String _tp1;
    
    /* 通行图片2: TP2 */
    private java.lang.String _tp2;
    
    /* 通行图片3: TP3 */
    private java.lang.String _tp3;
    
    /* 特征图片: TZTP */
    private java.lang.String _tztp;
    
    /* Rfid卡号: CID */
    private java.lang.String _cid;
    
    /* Rfid卡号: TID */
    private java.lang.String _tid;
    
    /* 载客人数: ZKRS */
    private java.lang.String _zkrs;
    
    /* 创建时间: CREATE_TIME */
    private java.time.LocalDateTime _createTime;
    
    /* 修改时间: UPDATE_TIME */
    private java.time.LocalDateTime _updateTime;
    

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
        
            case PROP_ID_fxlx:
               return getFxlx();
        
            case PROP_ID_cdh:
               return getCdh();
        
            case PROP_ID_hphm:
               return getHphm();
        
            case PROP_ID_hpzl:
               return getHpzl();
        
            case PROP_ID_gcsj:
               return getGcsj();
        
            case PROP_ID_clsd:
               return getClsd();
        
            case PROP_ID_clxs:
               return getClxs();
        
            case PROP_ID_wfdm:
               return getWfdm();
        
            case PROP_ID_cwkc:
               return getCwkc();
        
            case PROP_ID_hpys:
               return getHpys();
        
            case PROP_ID_cllx:
               return getCllx();
        
            case PROP_ID_fzhpzl:
               return getFzhpzl();
        
            case PROP_ID_fzhphm:
               return getFzhphm();
        
            case PROP_ID_fzhpys:
               return getFzhpys();
        
            case PROP_ID_clpp:
               return getClpp();
        
            case PROP_ID_clwx:
               return getClwx();
        
            case PROP_ID_csys:
               return getCsys();
        
            case PROP_ID_tplj:
               return getTplj();
        
            case PROP_ID_tp1:
               return getTp1();
        
            case PROP_ID_tp2:
               return getTp2();
        
            case PROP_ID_tp3:
               return getTp3();
        
            case PROP_ID_tztp:
               return getTztp();
        
            case PROP_ID_cid:
               return getCid();
        
            case PROP_ID_tid:
               return getTid();
        
            case PROP_ID_zkrs:
               return getZkrs();
        
            case PROP_ID_createTime:
               return getCreateTime();
        
            case PROP_ID_updateTime:
               return getUpdateTime();
        
           default:
              return super.orm_propValue(propId);
        }
    }

    

    @Override
    public void orm_propValue(int propId, Object value){
        switch(propId){
        
            case PROP_ID_id_:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
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
        
            case PROP_ID_fxlx:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_fxlx));
               }
               setFxlx(typedValue);
               break;
            }
        
            case PROP_ID_cdh:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_cdh));
               }
               setCdh(typedValue);
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
        
            case PROP_ID_hpzl:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_hpzl));
               }
               setHpzl(typedValue);
               break;
            }
        
            case PROP_ID_gcsj:{
               java.sql.Timestamp typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toTimestamp(value,
                       err-> newTypeConversionError(PROP_NAME_gcsj));
               }
               setGcsj(typedValue);
               break;
            }
        
            case PROP_ID_clsd:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_clsd));
               }
               setClsd(typedValue);
               break;
            }
        
            case PROP_ID_clxs:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_clxs));
               }
               setClxs(typedValue);
               break;
            }
        
            case PROP_ID_wfdm:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_wfdm));
               }
               setWfdm(typedValue);
               break;
            }
        
            case PROP_ID_cwkc:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_cwkc));
               }
               setCwkc(typedValue);
               break;
            }
        
            case PROP_ID_hpys:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_hpys));
               }
               setHpys(typedValue);
               break;
            }
        
            case PROP_ID_cllx:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_cllx));
               }
               setCllx(typedValue);
               break;
            }
        
            case PROP_ID_fzhpzl:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_fzhpzl));
               }
               setFzhpzl(typedValue);
               break;
            }
        
            case PROP_ID_fzhphm:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_fzhphm));
               }
               setFzhphm(typedValue);
               break;
            }
        
            case PROP_ID_fzhpys:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_fzhpys));
               }
               setFzhpys(typedValue);
               break;
            }
        
            case PROP_ID_clpp:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_clpp));
               }
               setClpp(typedValue);
               break;
            }
        
            case PROP_ID_clwx:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_clwx));
               }
               setClwx(typedValue);
               break;
            }
        
            case PROP_ID_csys:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_csys));
               }
               setCsys(typedValue);
               break;
            }
        
            case PROP_ID_tplj:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_tplj));
               }
               setTplj(typedValue);
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
        
            case PROP_ID_tztp:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_tztp));
               }
               setTztp(typedValue);
               break;
            }
        
            case PROP_ID_cid:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_cid));
               }
               setCid(typedValue);
               break;
            }
        
            case PROP_ID_tid:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_tid));
               }
               setTid(typedValue);
               break;
            }
        
            case PROP_ID_zkrs:{
               java.lang.String typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toString(value,
                       err-> newTypeConversionError(PROP_NAME_zkrs));
               }
               setZkrs(typedValue);
               break;
            }
        
            case PROP_ID_createTime:{
               java.time.LocalDateTime typedValue = null;
               if(value != null){
                   typedValue = ConvertHelper.toLocalDateTime(value,
                       err-> newTypeConversionError(PROP_NAME_createTime));
               }
               setCreateTime(typedValue);
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
        
           default:
              super.orm_propValue(propId,value);
        }
    }

    @Override
    public void orm_internalSet(int propId, Object value) {
        switch(propId){
        
            case PROP_ID_id_:{
               onInitProp(propId);
               this._id_ = (java.lang.String)value;
               orm_id(); // 如果是设置主键字段，则触发watcher
               break;
            }
        
            case PROP_ID_kkbh:{
               onInitProp(propId);
               this._kkbh = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_fxlx:{
               onInitProp(propId);
               this._fxlx = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_cdh:{
               onInitProp(propId);
               this._cdh = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_hphm:{
               onInitProp(propId);
               this._hphm = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_hpzl:{
               onInitProp(propId);
               this._hpzl = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_gcsj:{
               onInitProp(propId);
               this._gcsj = (java.sql.Timestamp)value;
               
               break;
            }
        
            case PROP_ID_clsd:{
               onInitProp(propId);
               this._clsd = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_clxs:{
               onInitProp(propId);
               this._clxs = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_wfdm:{
               onInitProp(propId);
               this._wfdm = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_cwkc:{
               onInitProp(propId);
               this._cwkc = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_hpys:{
               onInitProp(propId);
               this._hpys = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_cllx:{
               onInitProp(propId);
               this._cllx = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_fzhpzl:{
               onInitProp(propId);
               this._fzhpzl = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_fzhphm:{
               onInitProp(propId);
               this._fzhphm = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_fzhpys:{
               onInitProp(propId);
               this._fzhpys = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_clpp:{
               onInitProp(propId);
               this._clpp = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_clwx:{
               onInitProp(propId);
               this._clwx = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_csys:{
               onInitProp(propId);
               this._csys = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_tplj:{
               onInitProp(propId);
               this._tplj = (java.lang.String)value;
               
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
        
            case PROP_ID_tztp:{
               onInitProp(propId);
               this._tztp = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_cid:{
               onInitProp(propId);
               this._cid = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_tid:{
               onInitProp(propId);
               this._tid = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_zkrs:{
               onInitProp(propId);
               this._zkrs = (java.lang.String)value;
               
               break;
            }
        
            case PROP_ID_createTime:{
               onInitProp(propId);
               this._createTime = (java.time.LocalDateTime)value;
               
               break;
            }
        
            case PROP_ID_updateTime:{
               onInitProp(propId);
               this._updateTime = (java.time.LocalDateTime)value;
               
               break;
            }
        
           default:
              super.orm_internalSet(propId,value);
        }
    }

    
    /**
     * Id: ID
     */
    public java.lang.String getId_(){
         onPropGet(PROP_ID_id_);
         return _id_;
    }

    /**
     * Id: ID
     */
    public void setId_(java.lang.String value){
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
     * 方向类型: FXLX
     */
    public java.lang.String getFxlx(){
         onPropGet(PROP_ID_fxlx);
         return _fxlx;
    }

    /**
     * 方向类型: FXLX
     */
    public void setFxlx(java.lang.String value){
        if(onPropSet(PROP_ID_fxlx,value)){
            this._fxlx = value;
            internalClearRefs(PROP_ID_fxlx);
            
        }
    }
    
    /**
     * 车道号: CDH
     */
    public java.lang.String getCdh(){
         onPropGet(PROP_ID_cdh);
         return _cdh;
    }

    /**
     * 车道号: CDH
     */
    public void setCdh(java.lang.String value){
        if(onPropSet(PROP_ID_cdh,value)){
            this._cdh = value;
            internalClearRefs(PROP_ID_cdh);
            
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
     * 号牌种类: HPZL
     */
    public java.lang.String getHpzl(){
         onPropGet(PROP_ID_hpzl);
         return _hpzl;
    }

    /**
     * 号牌种类: HPZL
     */
    public void setHpzl(java.lang.String value){
        if(onPropSet(PROP_ID_hpzl,value)){
            this._hpzl = value;
            internalClearRefs(PROP_ID_hpzl);
            
        }
    }
    
    /**
     * 过车时间: GCSJ
     */
    public java.sql.Timestamp getGcsj(){
         onPropGet(PROP_ID_gcsj);
         return _gcsj;
    }

    /**
     * 过车时间: GCSJ
     */
    public void setGcsj(java.sql.Timestamp value){
        if(onPropSet(PROP_ID_gcsj,value)){
            this._gcsj = value;
            internalClearRefs(PROP_ID_gcsj);
            
        }
    }
    
    /**
     * 车辆速度: CLSD
     */
    public java.lang.String getClsd(){
         onPropGet(PROP_ID_clsd);
         return _clsd;
    }

    /**
     * 车辆速度: CLSD
     */
    public void setClsd(java.lang.String value){
        if(onPropSet(PROP_ID_clsd,value)){
            this._clsd = value;
            internalClearRefs(PROP_ID_clsd);
            
        }
    }
    
    /**
     * 车辆限速: CLXS
     */
    public java.lang.String getClxs(){
         onPropGet(PROP_ID_clxs);
         return _clxs;
    }

    /**
     * 车辆限速: CLXS
     */
    public void setClxs(java.lang.String value){
        if(onPropSet(PROP_ID_clxs,value)){
            this._clxs = value;
            internalClearRefs(PROP_ID_clxs);
            
        }
    }
    
    /**
     * 违章行为编码: WFDM
     */
    public java.lang.String getWfdm(){
         onPropGet(PROP_ID_wfdm);
         return _wfdm;
    }

    /**
     * 违章行为编码: WFDM
     */
    public void setWfdm(java.lang.String value){
        if(onPropSet(PROP_ID_wfdm,value)){
            this._wfdm = value;
            internalClearRefs(PROP_ID_wfdm);
            
        }
    }
    
    /**
     * 车外廓长: CWKC
     */
    public java.lang.String getCwkc(){
         onPropGet(PROP_ID_cwkc);
         return _cwkc;
    }

    /**
     * 车外廓长: CWKC
     */
    public void setCwkc(java.lang.String value){
        if(onPropSet(PROP_ID_cwkc,value)){
            this._cwkc = value;
            internalClearRefs(PROP_ID_cwkc);
            
        }
    }
    
    /**
     * 号牌颜色: HPYS
     */
    public java.lang.String getHpys(){
         onPropGet(PROP_ID_hpys);
         return _hpys;
    }

    /**
     * 号牌颜色: HPYS
     */
    public void setHpys(java.lang.String value){
        if(onPropSet(PROP_ID_hpys,value)){
            this._hpys = value;
            internalClearRefs(PROP_ID_hpys);
            
        }
    }
    
    /**
     * 车辆类型: CLLX
     */
    public java.lang.String getCllx(){
         onPropGet(PROP_ID_cllx);
         return _cllx;
    }

    /**
     * 车辆类型: CLLX
     */
    public void setCllx(java.lang.String value){
        if(onPropSet(PROP_ID_cllx,value)){
            this._cllx = value;
            internalClearRefs(PROP_ID_cllx);
            
        }
    }
    
    /**
     * 辅助号牌种类: FZHPZL
     */
    public java.lang.String getFzhpzl(){
         onPropGet(PROP_ID_fzhpzl);
         return _fzhpzl;
    }

    /**
     * 辅助号牌种类: FZHPZL
     */
    public void setFzhpzl(java.lang.String value){
        if(onPropSet(PROP_ID_fzhpzl,value)){
            this._fzhpzl = value;
            internalClearRefs(PROP_ID_fzhpzl);
            
        }
    }
    
    /**
     * 辅助号牌号码: FZHPHM
     */
    public java.lang.String getFzhphm(){
         onPropGet(PROP_ID_fzhphm);
         return _fzhphm;
    }

    /**
     * 辅助号牌号码: FZHPHM
     */
    public void setFzhphm(java.lang.String value){
        if(onPropSet(PROP_ID_fzhphm,value)){
            this._fzhphm = value;
            internalClearRefs(PROP_ID_fzhphm);
            
        }
    }
    
    /**
     * 辅助号牌颜色: FZHPYS
     */
    public java.lang.String getFzhpys(){
         onPropGet(PROP_ID_fzhpys);
         return _fzhpys;
    }

    /**
     * 辅助号牌颜色: FZHPYS
     */
    public void setFzhpys(java.lang.String value){
        if(onPropSet(PROP_ID_fzhpys,value)){
            this._fzhpys = value;
            internalClearRefs(PROP_ID_fzhpys);
            
        }
    }
    
    /**
     * 车辆品牌: CLPP
     */
    public java.lang.String getClpp(){
         onPropGet(PROP_ID_clpp);
         return _clpp;
    }

    /**
     * 车辆品牌: CLPP
     */
    public void setClpp(java.lang.String value){
        if(onPropSet(PROP_ID_clpp,value)){
            this._clpp = value;
            internalClearRefs(PROP_ID_clpp);
            
        }
    }
    
    /**
     * 车辆外形: CLWX
     */
    public java.lang.String getClwx(){
         onPropGet(PROP_ID_clwx);
         return _clwx;
    }

    /**
     * 车辆外形: CLWX
     */
    public void setClwx(java.lang.String value){
        if(onPropSet(PROP_ID_clwx,value)){
            this._clwx = value;
            internalClearRefs(PROP_ID_clwx);
            
        }
    }
    
    /**
     * 车身颜色: CSYS
     */
    public java.lang.String getCsys(){
         onPropGet(PROP_ID_csys);
         return _csys;
    }

    /**
     * 车身颜色: CSYS
     */
    public void setCsys(java.lang.String value){
        if(onPropSet(PROP_ID_csys,value)){
            this._csys = value;
            internalClearRefs(PROP_ID_csys);
            
        }
    }
    
    /**
     * 通行图片路径: TPLJ
     */
    public java.lang.String getTplj(){
         onPropGet(PROP_ID_tplj);
         return _tplj;
    }

    /**
     * 通行图片路径: TPLJ
     */
    public void setTplj(java.lang.String value){
        if(onPropSet(PROP_ID_tplj,value)){
            this._tplj = value;
            internalClearRefs(PROP_ID_tplj);
            
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
     * 特征图片: TZTP
     */
    public java.lang.String getTztp(){
         onPropGet(PROP_ID_tztp);
         return _tztp;
    }

    /**
     * 特征图片: TZTP
     */
    public void setTztp(java.lang.String value){
        if(onPropSet(PROP_ID_tztp,value)){
            this._tztp = value;
            internalClearRefs(PROP_ID_tztp);
            
        }
    }
    
    /**
     * Rfid卡号: CID
     */
    public java.lang.String getCid(){
         onPropGet(PROP_ID_cid);
         return _cid;
    }

    /**
     * Rfid卡号: CID
     */
    public void setCid(java.lang.String value){
        if(onPropSet(PROP_ID_cid,value)){
            this._cid = value;
            internalClearRefs(PROP_ID_cid);
            
        }
    }
    
    /**
     * Rfid卡号: TID
     */
    public java.lang.String getTid(){
         onPropGet(PROP_ID_tid);
         return _tid;
    }

    /**
     * Rfid卡号: TID
     */
    public void setTid(java.lang.String value){
        if(onPropSet(PROP_ID_tid,value)){
            this._tid = value;
            internalClearRefs(PROP_ID_tid);
            
        }
    }
    
    /**
     * 载客人数: ZKRS
     */
    public java.lang.String getZkrs(){
         onPropGet(PROP_ID_zkrs);
         return _zkrs;
    }

    /**
     * 载客人数: ZKRS
     */
    public void setZkrs(java.lang.String value){
        if(onPropSet(PROP_ID_zkrs,value)){
            this._zkrs = value;
            internalClearRefs(PROP_ID_zkrs);
            
        }
    }
    
    /**
     * 创建时间: CREATE_TIME
     */
    public java.time.LocalDateTime getCreateTime(){
         onPropGet(PROP_ID_createTime);
         return _createTime;
    }

    /**
     * 创建时间: CREATE_TIME
     */
    public void setCreateTime(java.time.LocalDateTime value){
        if(onPropSet(PROP_ID_createTime,value)){
            this._createTime = value;
            internalClearRefs(PROP_ID_createTime);
            
        }
    }
    
    /**
     * 修改时间: UPDATE_TIME
     */
    public java.time.LocalDateTime getUpdateTime(){
         onPropGet(PROP_ID_updateTime);
         return _updateTime;
    }

    /**
     * 修改时间: UPDATE_TIME
     */
    public void setUpdateTime(java.time.LocalDateTime value){
        if(onPropSet(PROP_ID_updateTime,value)){
            this._updateTime = value;
            internalClearRefs(PROP_ID_updateTime);
            
        }
    }
    
   private io.nop.orm.component.OrmFileComponent _tp1Component;

   private static Map<String,Integer> COMPONENT_PROP_ID_MAP_tp1Component = new HashMap<>();
   static{
      
         COMPONENT_PROP_ID_MAP_tp1Component.put(io.nop.orm.component.OrmFileComponent.PROP_NAME_filePath,PROP_ID_tp1);
      
   }

   public io.nop.orm.component.OrmFileComponent getTp1Component(){
      if(_tp1Component == null){
          _tp1Component = new io.nop.orm.component.OrmFileComponent();
          _tp1Component.bindToEntity(this, COMPONENT_PROP_ID_MAP_tp1Component);
      }
      return _tp1Component;
   }

   private io.nop.orm.component.OrmFileComponent _tp2Component;

   private static Map<String,Integer> COMPONENT_PROP_ID_MAP_tp2Component = new HashMap<>();
   static{
      
         COMPONENT_PROP_ID_MAP_tp2Component.put(io.nop.orm.component.OrmFileComponent.PROP_NAME_filePath,PROP_ID_tp2);
      
   }

   public io.nop.orm.component.OrmFileComponent getTp2Component(){
      if(_tp2Component == null){
          _tp2Component = new io.nop.orm.component.OrmFileComponent();
          _tp2Component.bindToEntity(this, COMPONENT_PROP_ID_MAP_tp2Component);
      }
      return _tp2Component;
   }

   private io.nop.orm.component.OrmFileComponent _tp3Component;

   private static Map<String,Integer> COMPONENT_PROP_ID_MAP_tp3Component = new HashMap<>();
   static{
      
         COMPONENT_PROP_ID_MAP_tp3Component.put(io.nop.orm.component.OrmFileComponent.PROP_NAME_filePath,PROP_ID_tp3);
      
   }

   public io.nop.orm.component.OrmFileComponent getTp3Component(){
      if(_tp3Component == null){
          _tp3Component = new io.nop.orm.component.OrmFileComponent();
          _tp3Component.bindToEntity(this, COMPONENT_PROP_ID_MAP_tp3Component);
      }
      return _tp3Component;
   }

}
// resume CPD analysis - CPD-ON
