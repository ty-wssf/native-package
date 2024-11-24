package com.bcht.rminf.modules.hkpostgresql.model;

import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

/**
 * 实体类对应于数据库中的warning_instance_info表。
 */
@Entity
public interface WarningInstanceInfo {

    /**
     * 主键ID。
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id();

    /**
     * 市级行政区划代码。
     */
    @Nullable
    String cityLocalityCode();

    /**
     * 行政区划编号。
     */
    @Nullable
    String localityCode();

    /**
     * 接警单位编号。
     */
    @Nullable
    String alarmUnitNo();

    /**
     * 接警单位名称。
     */
    @Nullable
    String alarmUnitName();

    /**
     * 接警单编号。
     */
    @Nullable
    String alarmReceiptNo();

    /**
     * 关联接警单编号。
     */
    @Nullable
    String relAlarmReceiptNo();

    /**
     * 接警员编号。
     */
    @Nullable
    String policeReceiptNo();

    /**
     * 接警员姓名。
     */
    @Nullable
    String policeReceiptName();

    /**
     * 报警时间或呼入时间。
     */
    @Nullable
    LocalDateTime alarmTime();

    /**
     * 接警时间或接听时间。
     */
    @Nullable
    LocalDateTime answerAlarmTime();

    /**
     * 报警人姓名。
     */
    @Nullable
    String alarmName();

    /**
     * 报警电话。
     */
    @Nullable
    String alarmPhone();

    /**
     * 联系电话。
     */
    @Nullable
    String phone();

    /**
     * 事发地址。
     */
    @Nullable
    String incidentAddr();

    /**
     * 报警内容。
     */
    @Nullable
    String alarmContent();

    /**
     * 管辖单位编号。
     */
    @Nullable
    String ownUnitNo();

    /**
     * 管辖单位名称。
     */
    @Nullable
    String ownUnitName();

    /**
     * 无效案件标识(0:无效,1:有效)。
     */
    @Nullable
    String invalidCase();

    /**
     * 报警类别。
     */
    @Nullable
    String alarmCategory();

    /**
     * 报警类型。
     */
    @Nullable
    String alarmType();

    /**
     * 报警细类。
     */
    @Nullable
    String alarmDetails();

    /**
     * 自动定位X坐标(报警人位置)。
     */
    @Nullable
    String autox();

    /**
     * 自动定位Y坐标(报警人位置)。
     */
    @Nullable
    String autoy();

    /**
     * 址解析纬度。
     */
    @Nullable
    String latitude();

    /**
     * 地址解析经度。
     */
    @Nullable
    String longitude();

    /**
     * 中心点的空间信息。
     * 注意：需要一个合适的类来表示geometry类型，可能需要自定义或使用特定库。
     */
    // @Nullable
    // Object point();

    /**
     * 伤害程度代码。
     */
    @Nullable
    String degreeInjury();

    /**
     * 一案一码。
     */
    @Nullable
    String caseCode();

    /**
     * 处警单编号。
     */
    @Nullable
    String policeOrderNo();

    /**
     * 处警单位编号。
     */
    @Nullable
    String policeUnitNo();

    /**
     * 处警单位名称。
     */
    @Nullable
    String policeUnitName();

    /**
     * 处警员编号。
     */
    @Nullable
    String policeOfficerNo();

    /**
     * 处警员姓名。
     */
    @Nullable
    String policeOfficerName();

    /**
     * 派警时间。
     */
    @Nullable
    LocalDateTime dispatchTime();

    /**
     * 到达现场时间。
     */
    @Nullable
    LocalDateTime arrivalTime();

    /**
     * 首次反馈时间。
     */
    @Nullable
    LocalDateTime feedbackTime();

    /**
     * 反馈人。
     */
    @Nullable
    String feedbackPersonName();

    /**
     * 处理完毕时间。
     */
    @Nullable
    LocalDateTime finishedTime();

    /**
     * 反馈内容。
     */
    @Nullable
    String feedbackContent();

    /**
     * 出动人员信息。
     */
    @Nullable
    String outStaff();

    /**
     * 处警类别。
     */
    @Nullable
    String policeCategory();

    /**
     * 处警类型。
     */
    @Nullable
    String policeType();

    /**
     * 处警细类。
     */
    @Nullable
    String policeDetails();

    /**
     * 警情状态。
     */
    @Nullable
    String status();

    /**
     * 警情来源。
     */
    @Nullable
    String warningInstanceSource();

    /**
     * 警情关联图片列表，json格式。
     */
    @Nullable
    String picUrls();

    /**
     * 创建时间。
     */
    @Nullable
    LocalDateTime createTime();

    /**
     * 最近更新时间。
     */
    @Nullable
    LocalDateTime updateTime();

    /**
     * 警情状态是否超时：1超时0未超时。
     */
    @Nullable
    Integer processTimeout();

    /**
     * 预案等级。
     */
    @Nullable
    String planLevel();
}