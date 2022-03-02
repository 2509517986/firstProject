package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_yuntai_alarm（云台抓告警）对象 t_yuntai_alarm
 * 
 * @author ruoyi
 * @date 2021-06-22
 */
public class TYuntaiAlarm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 抓拍图片ID（从抓拍图片转过来的告警会保存对应的抓拍图片ID） */
    @Excel(name = "抓拍图片ID（从抓拍图片转过来的告警会保存对应的抓拍图片ID）")
    private Long pictureId;

    /** 云台ID */
    @Excel(name = "云台ID")
    private Long yuntaiId;

    /** 所属线路 */
    @Excel(name = "所属线路")
    private Long lineId;

    /** 杆塔名称 */
    @Excel(name = "杆塔名称")
    private Long towerId;

    /** 告警图片目录路径 */
    @Excel(name = "告警图片目录路径")
    private String alarmDir;

    /** 告警图片 */
    @Excel(name = "告警图片")
    private String alarmUrl;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抓拍时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date captureTime;

    /** 拍摄预置位 */
    @Excel(name = "拍摄预置位")
    private String presetBit;

    /** 通道号 */
    @Excel(name = "通道号")
    private String channelNumber;

    /** 图片类型：塔基防范、超高检测、违章检测(数据字典配置) */
    @Excel(name = "图片类型",dictType = "img_type")
    private String imgType;

    /** 确认状态：0 否  1 是 */
    @Excel(name = "确认状态",readConverterExp = "0=否,1=是")
    private Integer confirmState;

    /** 确认人 */
    @Excel(name = "确认人")
    private String confirmUser;

    /** 确认时间 */
    @Excel(name = "确认时间")
    private Date confirmTime;


    /** 忽略此告警：0 否  1 是 */
    @Excel(name = "忽略此告警",readConverterExp = "0=否,1=是")
    private Integer isIgnore;

    /** 目标检测：0 错误 1 正确 */
    @Excel(name = "目标检测",readConverterExp = "0=错误,1=正确")
    private Integer isCorrect;

    /** 影响线路：0 否  1 是 */
    @Excel(name = "影响线路",readConverterExp = "0=否,1=是")
    private Integer affectedLine;

    /** 告警来源：1 手动告警 2 自动告警 */
    @Excel(name = "告警来源",dictType = "alarm_source")
    private String alarmSource;

    /** 智能分析：吊车、吊塔、推土机(数据字典配置，根据篮网协议配置) */
    @Excel(name = "智能分析",dictType = "alarm_ai")
    private String alarmAi;

    /** 场景点分类：导线，地线，玻璃绝缘子，陶瓷绝缘子，防震锤，金具 */
    @Excel(name = "场景点分类",dictType = "scene_type")
    private String sceneType;


    /** 告警时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "告警时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date alarmDate;


    /** 告警说明(描述) */
    @Excel(name = "告警说明(描述)")
    private String alarmDes;

    /** 大于导线安全距离：0 否 1 是 */
    @Excel(name = "大于导线安全距离",readConverterExp = "0=否,1=是")
    private Integer securityDistance;

    /** 场景点名称 */
    @Excel(name = "场景点名称")
    private String sceneName;

    /** 是否需要下发隐患通知：0 否 1 是 */
    @Excel(name = "是否需要下发隐患通知",readConverterExp = "0=否,1=是")
    private Integer isInform;

    /** 是否需要现场处理：0 否 1 是 */
    @Excel(name = "是否需要现场处理",readConverterExp = "0=否,1=是")
    private Integer isScene;

    /** 处理状态：0 未处理 1 已处理 2 无需处理 */
    @Excel(name = "处理状态",readConverterExp = "0=未处理,1=已处理,2=无需处理")
    private Long dealWith;

    /** 处理期限：3 天  5 天  7 天 */
    @Excel(name = "处理期限：3 天  5 天  7 天")
    private Long dealWithLimit;

    /** 责任人 */
    @Excel(name = "责任人")
    private String dutyUser;

    /** 责任人电话 */
    @Excel(name = "责任人电话")
    private String dutyPhone;

    /** 通知时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "通知时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date informDate;

    /** 短信通知内容 */
    @Excel(name = "短信通知内容")
    private String informContent;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dealDate;

    /** 处理人 */
    @Excel(name = "处理人")
    private String dealUser;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String dealResult;

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

    /** 线路Id */
    private String lineIds;

    /** 杆塔Id */
    private String towerIds;

    /** 云台名称 */
    private String yuntaiName;
    /** 线路名称 */
    private String lineName;
    /** 塔名称 */
    private String towerName;
    /** 是否已预览 */
    private String isBrowse;

    private String[] alarmSources;

    private String[] imgTypes;
    private String[] confirmStates;

    private String[] alarmAis;

    private Integer isAlarm;

    public String[] getAlarmSources() {
        return alarmSources;
    }

    public void setAlarmSources(String[] alarmSources) {
        this.alarmSources = alarmSources;
    }

    public String[] getImgTypes() {
        return imgTypes;
    }

    public void setImgTypes(String[] imgTypes) {
        this.imgTypes = imgTypes;
    }

    public String[] getConfirmStates() {
        return confirmStates;
    }

    public void setConfirmStates(String[] confirmStates) {
        this.confirmStates = confirmStates;
    }

    public String[] getAlarmAis() {
        return alarmAis;
    }

    public void setAlarmAis(String[] alarmAis) {
        this.alarmAis = alarmAis;
    }

    public String getLineIds() {
        return lineIds;
    }

    public void setLineIds(String lineIds) {
        this.lineIds = lineIds;
    }

    public String getYuntaiName() {
        return yuntaiName;
    }

    public void setYuntaiName(String yuntaiName) {
        this.yuntaiName = yuntaiName;
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

    public String getIsBrowse() {
        return isBrowse;
    }

    public void setIsBrowse(String isBrowse) {
        this.isBrowse = isBrowse;
    }

    /** 构造函数 */
    public TYuntaiAlarm(){
    }
    /** 构造函数 */
    public TYuntaiAlarm(String lineIds){
        this.lineIds= lineIds;
    }
    /** 构造函数 */
    public TYuntaiAlarm(String lineIds, String towerIds){
        this.lineIds= lineIds;
        this.towerIds= towerIds;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPictureId(Long pictureId) 
    {
        this.pictureId = pictureId;
    }

    public Long getPictureId() 
    {
        return pictureId;
    }
    public void setYuntaiId(Long yuntaiId) 
    {
        this.yuntaiId = yuntaiId;
    }

    public Long getYuntaiId() 
    {
        return yuntaiId;
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
    public void setPresetBit(String presetBit) 
    {
        this.presetBit = presetBit;
    }

    public String getPresetBit() 
    {
        return presetBit;
    }
    public void setChannelNumber(String channelNumber) 
    {
        this.channelNumber = channelNumber;
    }

    public String getChannelNumber() 
    {
        return channelNumber;
    }
    public void setImgType(String imgType) 
    {
        this.imgType = imgType;
    }

    public String getImgType() 
    {
        return imgType;
    }
    public void setIsIgnore(Integer isIgnore) 
    {
        this.isIgnore = isIgnore;
    }

    public Integer getIsIgnore() 
    {
        return isIgnore;
    }
    public void setIsCorrect(Integer isCorrect) 
    {
        this.isCorrect = isCorrect;
    }

    public Integer getIsCorrect() 
    {
        return isCorrect;
    }
    public void setAffectedLine(Integer affectedLine) 
    {
        this.affectedLine = affectedLine;
    }

    public Integer getAffectedLine() 
    {
        return affectedLine;
    }
    public void setAlarmSource(String alarmSource) 
    {
        this.alarmSource = alarmSource;
    }

    public String getAlarmSource() 
    {
        return alarmSource;
    }
    public void setAlarmAi(String alarmAi) 
    {
        this.alarmAi = alarmAi;
    }

    public String getAlarmAi() 
    {
        return alarmAi;
    }
    public void setSceneType(String sceneType) 
    {
        this.sceneType = sceneType;
    }

    public String getSceneType() 
    {
        return sceneType;
    }
    public void setAlarmDes(String alarmDes) 
    {
        this.alarmDes = alarmDes;
    }

    public Date getAlarmDate() {
        return alarmDate;
    }
    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmDes()
    {
        return alarmDes;
    }
    public void setSecurityDistance(Integer securityDistance) 
    {
        this.securityDistance = securityDistance;
    }

    public Integer getSecurityDistance() 
    {
        return securityDistance;
    }
    public void setSceneName(String sceneName) 
    {
        this.sceneName = sceneName;
    }

    public String getSceneName() 
    {
        return sceneName;
    }
    public void setIsInform(Integer isInform) 
    {
        this.isInform = isInform;
    }

    public Integer getIsInform() 
    {
        return isInform;
    }
    public void setIsScene(Integer isScene) 
    {
        this.isScene = isScene;
    }

    public Integer getIsScene() 
    {
        return isScene;
    }
    public void setDealWith(Long dealWith) 
    {
        this.dealWith = dealWith;
    }

    public Long getDealWith() 
    {
        return dealWith;
    }
    public void setDealWithLimit(Long dealWithLimit) 
    {
        this.dealWithLimit = dealWithLimit;
    }

    public Long getDealWithLimit() 
    {
        return dealWithLimit;
    }
    public void setDutyUser(String dutyUser) 
    {
        this.dutyUser = dutyUser;
    }

    public String getDutyUser() 
    {
        return dutyUser;
    }
    public void setDutyPhone(String dutyPhone) 
    {
        this.dutyPhone = dutyPhone;
    }

    public String getDutyPhone() 
    {
        return dutyPhone;
    }
    public void setInformDate(Date informDate) 
    {
        this.informDate = informDate;
    }

    public Date getInformDate() 
    {
        return informDate;
    }
    public void setInformContent(String informContent) 
    {
        this.informContent = informContent;
    }

    public String getInformContent() 
    {
        return informContent;
    }
    public void setDealDate(Date dealDate) 
    {
        this.dealDate = dealDate;
    }

    public Date getDealDate() 
    {
        return dealDate;
    }
    public void setDealUser(String dealUser) 
    {
        this.dealUser = dealUser;
    }

    public String getDealUser() 
    {
        return dealUser;
    }
    public void setDealResult(String dealResult) 
    {
        this.dealResult = dealResult;
    }

    public String getDealResult() 
    {
        return dealResult;
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

    public Integer getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(Integer confirmState) {
        this.confirmState = confirmState;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getIsAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(Integer isAlarm) {
        this.isAlarm = isAlarm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pictureId", getPictureId())
            .append("yuntaiId", getYuntaiId())
            .append("lineId", getLineId())
            .append("towerId", getTowerId())
            .append("alarmDir", getAlarmDir())
            .append("alarmUrl", getAlarmUrl())
            .append("factory", getFactory())
            .append("kind", getKind())
            .append("captureUser", getCaptureUser())
            .append("captureTime", getCaptureTime())
            .append("presetBit", getPresetBit())
            .append("channelNumber", getChannelNumber())
            .append("imgType", getImgType())
            .append("isIgnore", getIsIgnore())
            .append("isCorrect", getIsCorrect())
            .append("affectedLine", getAffectedLine())
            .append("alarmSource", getAlarmSource())
            .append("alarmAi", getAlarmAi())
            .append("sceneType", getSceneType())
            .append("alarmDate", getAlarmDate())
            .append("alarmDes", getAlarmDes())
            .append("securityDistance", getSecurityDistance())
            .append("sceneName", getSceneName())
            .append("isInform", getIsInform())
            .append("isScene", getIsScene())
            .append("dealWith", getDealWith())
            .append("dealWithLimit", getDealWithLimit())
            .append("dutyUser", getDutyUser())
            .append("dutyPhone", getDutyPhone())
            .append("informDate", getInformDate())
            .append("informContent", getInformContent())
            .append("dealDate", getDealDate())
            .append("dealUser", getDealUser())
            .append("dealResult", getDealResult())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
