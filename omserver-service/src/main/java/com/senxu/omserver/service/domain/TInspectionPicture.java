package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_inspection_picture（巡检图片记录）对象 t_inspection_picture
 * 
 * @author ruoyi
 * @date 2021-09-14
 */
public class TInspectionPicture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 巡检报告ID */
    @Excel(name = "巡检报告ID")
    private Long reportId;

    /** 图片ID */
    @Excel(name = "图片ID")
    private Long pictureId;

    /** 所属线路 */
    @Excel(name = "所属线路")
    private Long lineId;

    /** 杆塔名称 */
    @Excel(name = "杆塔名称")
    private Long towerId;

    /** 巡检班组 */
    @Excel(name = "巡检班组")
    private String deptName;

    /** 电压等级(高压等级) */
    @Excel(name = "电压等级(高压等级)",dictType = "line_voltage")
    private String level;

    /** 传输电压 */
    @Excel(name = "传输电压")
    private String voltage;

    /** 历史抓拍图片ID */
    @Excel(name = "历史抓拍图片ID")
    private Long historyPictureId;

    /** 图片目录路径 */
    @Excel(name = "图片目录路径")
    private String imageDir;

    /** 图片（原图） */
    @Excel(name = "图片（原图）")
    private String imageName;

    /** 告警图片目录路径 */
    @Excel(name = "告警图片目录路径")
    private String alarmDir;

    /** 告警图片 */
    @Excel(name = "告警图片")
    private String alarmUrl;

    /** 场景点分类：导线，地线，玻璃绝缘子，陶瓷绝缘子，防震锤，金具 */
    @Excel(name = "场景点分类",dictType = "scene_type")
    private String sceneType;

    /** 场景点名称 */
    @Excel(name = "场景点名称")
    private String sceneName;

    /** 设备厂商 */
    @Excel(name = "设备厂商")
    private String factory;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String kind;

    /** 抓拍人 */
    @Excel(name = "抓拍人")
    private String captureUser;

    /** 抓拍时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "抓拍时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date captureTime;

    /** 通道号 */
    @Excel(name = "通道号")
    private String channelNumber;

    /** 拍摄预置位 */
    @Excel(name = "拍摄预置位")
    private String presetBit;

    /** 图片类型：数据字典配置 */
    @Excel(name = "图片类型：数据字典配置")
    private String imgType;

    /** 是否告警：0 否 1 是  */
    @Excel(name = "是否告警",readConverterExp = "0=否,1=是")
    private Integer isAlarm;

    /** 智能巡检结果 */
    @Excel(name = "智能巡检结果")
    private String checkResult;

    /** 人工诊断 */
    @Excel(name = "人工诊断")
    private String diagnose;

    /** 新增人 */
    @Excel(name = "新增人")
    private String createUser;

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

    private String lineName;//线路名称
    private String towerName;//杆塔名称

    private TYuntaiPicture historyPicture;//历史图片
    /**安装点分类*/
    @Excel(name = "安装点分类",dictType = "install_type")
    private String installType;


    public String getInstallType() {
        return installType;
    }

    public void setInstallType(String installType) {
        this.installType = installType;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getTowerName() {
        return towerName;
    }

    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    public TYuntaiPicture getHistoryPicture() {
        return historyPicture;
    }

    public void setHistoryPicture(TYuntaiPicture historyPicture) {
        this.historyPicture = historyPicture;
    }

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
    public void setPictureId(Long pictureId) 
    {
        this.pictureId = pictureId;
    }

    public Long getPictureId() 
    {
        return pictureId;
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
    public void setHistoryPictureId(Long historyPictureId) 
    {
        this.historyPictureId = historyPictureId;
    }

    public Long getHistoryPictureId() 
    {
        return historyPictureId;
    }
    public void setImageDir(String imageDir) 
    {
        this.imageDir = imageDir;
    }

    public String getImageDir() 
    {
        return imageDir;
    }
    public void setImageName(String imageName) 
    {
        this.imageName = imageName;
    }

    public String getImageName() 
    {
        return imageName;
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
    public void setSceneType(String sceneType) 
    {
        this.sceneType = sceneType;
    }

    public String getSceneType() 
    {
        return sceneType;
    }
    public void setSceneName(String sceneName) 
    {
        this.sceneName = sceneName;
    }

    public String getSceneName() 
    {
        return sceneName;
    }
    public void setFactory(String factory) 
    {
        this.factory = factory;
    }

    public String getFactory() 
    {
        return factory;
    }
    public void setKind(String kind) 
    {
        this.kind = kind;
    }

    public String getKind() 
    {
        return kind;
    }
    public void setCaptureUser(String captureUser) 
    {
        this.captureUser = captureUser;
    }

    public String getCaptureUser() 
    {
        return captureUser;
    }
    public void setCaptureTime(Date captureTime) 
    {
        this.captureTime = captureTime;
    }

    public Date getCaptureTime() 
    {
        return captureTime;
    }
    public void setChannelNumber(String channelNumber) 
    {
        this.channelNumber = channelNumber;
    }

    public String getChannelNumber() 
    {
        return channelNumber;
    }
    public void setPresetBit(String presetBit) 
    {
        this.presetBit = presetBit;
    }

    public String getPresetBit() 
    {
        return presetBit;
    }
    public void setImgType(String imgType) 
    {
        this.imgType = imgType;
    }

    public String getImgType() 
    {
        return imgType;
    }
    public void setIsAlarm(Integer isAlarm) 
    {
        this.isAlarm = isAlarm;
    }

    public Integer getIsAlarm() 
    {
        return isAlarm;
    }
    public void setCheckResult(String checkResult) 
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult() 
    {
        return checkResult;
    }
    public void setDiagnose(String diagnose) 
    {
        this.diagnose = diagnose;
    }

    public String getDiagnose() 
    {
        return diagnose;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
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
            .append("pictureId", getPictureId())
            .append("lineId", getLineId())
            .append("towerId", getTowerId())
            .append("deptName", getDeptName())
            .append("level", getLevel())
            .append("voltage", getVoltage())
            .append("historyPictureId", getHistoryPictureId())
            .append("imageDir", getImageDir())
            .append("imageName", getImageName())
            .append("alarmDir", getAlarmDir())
            .append("alarmUrl", getAlarmUrl())
            .append("sceneType", getSceneType())
            .append("sceneName", getSceneName())
            .append("factory", getFactory())
            .append("kind", getKind())
            .append("captureUser", getCaptureUser())
            .append("captureTime", getCaptureTime())
            .append("channelNumber", getChannelNumber())
            .append("presetBit", getPresetBit())
            .append("imgType", getImgType())
            .append("isAlarm", getIsAlarm())
            .append("checkResult", getCheckResult())
            .append("diagnose", getDiagnose())
            .append("remark", getRemark())
            .append("createUser", getCreateUser())
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
