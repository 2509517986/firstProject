package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_inspection_alarm（巡检告警记录）对象 t_inspection_alarm
 * 
 * @author 潘永潮
 * @date 2021-06-21
 */
public class TInspectionAlarm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 巡检报告ID */
    @Excel(name = "巡检报告ID")
    private Long reportId;

    /** 告警ID */
    @Excel(name = "告警ID")
    private Long alarmId;

    /** 所属线路 */
    @Excel(name = "所属线路")
    private Long lineId;

    /** 杆塔名称 */
    @Excel(name = "杆塔名称")
    private Long towerId;

    /** 巡检班组 */
    @Excel(name = "巡检班组")
    private String deptName;

    /** 历史抓拍图片ID */
    @Excel(name = "历史抓拍图片ID")
    private Long pictureId;

    /** 场景点分类：导线，地线，玻璃绝缘子，陶瓷绝缘子，防震锤，金具 */
    @Excel(name = "场景点分类",dictType = "scene_type")
    private String sceneType;

    /** 电压等级(高压等级) */
    @Excel(name = "电压等级(高压等级)",dictType = "line_voltage")
    private String level;

    /** 传输电压 */
    @Excel(name = "传输电压")
    private String voltage;

    /** 场景点名称 */
    @Excel(name = "场景点名称")
    private String sceneName;

    /** 智能巡检结果 */
    @Excel(name = "智能巡检结果")
    private String checkResult;

    /** 图片目录路径 */
    @Excel(name = "图片目录路径")
    private String imageDir;

    /** 人工诊断 */
    @Excel(name = "人工诊断")
    private String diagnose;

    /** 图片（原图） */
    @Excel(name = "图片（原图）")
    private String imageName;

    /** 新增人 */
    @Excel(name = "新增人")
    private String createUser;

    /** 告警图片目录路径 */
    @Excel(name = "告警图片目录路径")
    private String alarmDir;

    /** 告警图片 */
    @Excel(name = "告警图片")
    private String alarmUrl;

    /** 更新人 */
    @Excel(name = "更新人")
    private String modifyUser;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /** 备用字段0 */
    @Excel(name = "备用字段0")
    private String spare0;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String spare1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String spare2;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String spare3;

    /** 备用字段4 */
    @Excel(name = "备用字段4")
    private String spare4;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setReportId(Long reportId) 
    {
        this.reportId = reportId;
    }

    public Long getReportId() 
    {
        return reportId;
    }
    public void setAlarmId(Long alarmId) 
    {
        this.alarmId = alarmId;
    }

    public Long getAlarmId() 
    {
        return alarmId;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setTowerId(Long towerId) 
    {
        this.towerId = towerId;
    }

    public Long getTowerId() 
    {
        return towerId;
    }
    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }
    public void setPictureId(Long pictureId) 
    {
        this.pictureId = pictureId;
    }

    public Long getPictureId() 
    {
        return pictureId;
    }
    public void setSceneType(String sceneType) 
    {
        this.sceneType = sceneType;
    }

    public String getSceneType() 
    {
        return sceneType;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setVoltage(String voltage) 
    {
        this.voltage = voltage;
    }

    public String getVoltage() 
    {
        return voltage;
    }
    public void setSceneName(String sceneName) 
    {
        this.sceneName = sceneName;
    }

    public String getSceneName() 
    {
        return sceneName;
    }
    public void setCheckResult(String checkResult) 
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult() 
    {
        return checkResult;
    }
    public void setImageDir(String imageDir) 
    {
        this.imageDir = imageDir;
    }

    public String getImageDir() 
    {
        return imageDir;
    }
    public void setDiagnose(String diagnose) 
    {
        this.diagnose = diagnose;
    }

    public String getDiagnose() 
    {
        return diagnose;
    }
    public void setImageName(String imageName) 
    {
        this.imageName = imageName;
    }

    public String getImageName() 
    {
        return imageName;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setAlarmDir(String alarmDir) 
    {
        this.alarmDir = alarmDir;
    }

    public String getAlarmDir() 
    {
        return alarmDir;
    }
    public void setAlarmUrl(String alarmUrl) 
    {
        this.alarmUrl = alarmUrl;
    }

    public String getAlarmUrl() 
    {
        return alarmUrl;
    }
    public void setModifyUser(String modifyUser) 
    {
        this.modifyUser = modifyUser;
    }

    public String getModifyUser() 
    {
        return modifyUser;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }
    public void setSpare0(String spare0) 
    {
        this.spare0 = spare0;
    }

    public String getSpare0() 
    {
        return spare0;
    }
    public void setSpare1(String spare1) 
    {
        this.spare1 = spare1;
    }

    public String getSpare1() 
    {
        return spare1;
    }
    public void setSpare2(String spare2) 
    {
        this.spare2 = spare2;
    }

    public String getSpare2() 
    {
        return spare2;
    }
    public void setSpare3(String spare3) 
    {
        this.spare3 = spare3;
    }

    public String getSpare3() 
    {
        return spare3;
    }
    public void setSpare4(String spare4) 
    {
        this.spare4 = spare4;
    }

    public String getSpare4() 
    {
        return spare4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reportId", getReportId())
            .append("alarmId", getAlarmId())
            .append("lineId", getLineId())
            .append("towerId", getTowerId())
            .append("deptName", getDeptName())
            .append("pictureId", getPictureId())
            .append("sceneType", getSceneType())
            .append("level", getLevel())
            .append("voltage", getVoltage())
            .append("sceneName", getSceneName())
            .append("checkResult", getCheckResult())
            .append("imageDir", getImageDir())
            .append("diagnose", getDiagnose())
            .append("remark", getRemark())
            .append("imageName", getImageName())
            .append("createUser", getCreateUser())
            .append("alarmDir", getAlarmDir())
            .append("alarmUrl", getAlarmUrl())
            .append("createTime", getCreateTime())
            .append("modifyUser", getModifyUser())
            .append("modifyTime", getModifyTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
