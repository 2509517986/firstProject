package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * t_task_picture（无人机任务巡检图片）对象 t_task_picture
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public class TTaskPicture
{
    private static final long serialVersionUID = 1L;

    /** 图片ID */
    private Long imageId;

    /** 历史巡检记录ID */
    @Excel(name = "历史巡检记录ID")
    private Long taskhId;

    /** 任务编号(多翼的ID) */
    @Excel(name = "任务编号(多翼的ID)")
    private String taskCode;

    /** 航线编号(多翼的ID) */
    @Excel(name = "航线编号(多翼的ID)")
    private String routeCode;

    /** 机场ID */
    @Excel(name = "机场ID")
    private Long airportId;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskId;

    /** 航线ID */
    @Excel(name = "航线ID")
    private Long routeId;

    /** 图片编号 */
    @Excel(name = "图片编号")
    private String imageCode;

    /** 图片存放目录 */
    @Excel(name = "图片存放目录")
    private String path;

    /** 图片名称 */
    @Excel(name = "图片名称")
    private String imageName;

    /** 纬度 */
    @Excel(name = "纬度")
    private String lat;

    /** 经度 */
    @Excel(name = "经度")
    private String lon;

    /** 目标检测：0 错误 1 正确 */
    @Excel(name = "目标检测：0 错误 1 正确")
    private Integer isCorrect;

    /** 异常类别：1 金具 、2 附属设施、3 杆塔、4 通道环境、5 导地线、6 绝缘子、7 基础' */
    @Excel(name = "异常类别：1 金具 、2 附属设施、3 杆塔、4 通道环境、5 导地线、6 绝缘子、7 基础'")
    private String abnormalType;

    /** 缺陷个数 */
    @Excel(name = "缺陷个数")
    private int defectCount;

    /** 缺陷类型(严重程度)：1 一般、2 严重、3 危急 */
    @Excel(name = "缺陷类型(严重程度)：1 一般、2 严重、3 危急")
    private String defectType;

    /** 影响线路：0 否  1 是 */
    @Excel(name = "影响线路：0 否  1 是")
    private Integer affectedLine;

    /** 大于导线安全距离：0 否 1 是 */
    @Excel(name = "大于导线安全距离：0 否 1 是")
    private Integer securityDistance;

    /** 是否需要下发隐患通知：0 否 1 是 */
    @Excel(name = "是否需要下发隐患通知：0 否 1 是")
    private Integer isInform;

    /** 是否需要现场处理：0 否 1 是 */
    @Excel(name = "是否需要现场处理：0 否 1 是")
    private Integer isScene;

    /** 描述 */
    @Excel(name = "描述")
    private String describes;

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

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 告警人 */
    @Excel(name = "告警人")
    private String alarmUser;

    /** 告警时间 */
    @Excel(name = "告警时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date alarmTime;

    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getAlarmUser() {
        return alarmUser;
    }

    public void setAlarmUser(String alarmUser) {
        this.alarmUser = alarmUser;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setImageId(Long imageId) 
    {
        this.imageId = imageId;
    }

    public Long getImageId() 
    {
        return imageId;
    }
    public void setTaskhId(Long taskhId) 
    {
        this.taskhId = taskhId;
    }

    public Long getTaskhId() 
    {
        return taskhId;
    }
    public void setTaskCode(String taskCode) 
    {
        this.taskCode = taskCode;
    }

    public String getTaskCode() 
    {
        return taskCode;
    }
    public void setRouteCode(String routeCode) 
    {
        this.routeCode = routeCode;
    }

    public String getRouteCode() 
    {
        return routeCode;
    }
    public void setImageCode(String imageCode) 
    {
        this.imageCode = imageCode;
    }

    public String getImageCode() 
    {
        return imageCode;
    }
    public void setPath(String path) 
    {
        this.path = path;
    }

    public String getPath() 
    {
        return path;
    }
    public void setImageName(String imageName) 
    {
        this.imageName = imageName;
    }

    public String getImageName() 
    {
        return imageName;
    }
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public int getDefectCount() {
        return defectCount;
    }

    public void setDefectCount(int defectCount) {
        this.defectCount = defectCount;
    }

    public String getDefectType() {
        return defectType;
    }

    public void setDefectType(String defectType) {
        this.defectType = defectType;
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

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getAbnormalType() {
        return abnormalType;
    }

    public void setAbnormalType(String abnormalType) {
        this.abnormalType = abnormalType;
    }

    public Integer getAffectedLine() {
        return affectedLine;
    }

    public void setAffectedLine(Integer affectedLine) {
        this.affectedLine = affectedLine;
    }

    public Integer getSecurityDistance() {
        return securityDistance;
    }

    public void setSecurityDistance(Integer securityDistance) {
        this.securityDistance = securityDistance;
    }

    public Integer getIsInform() {
        return isInform;
    }

    public void setIsInform(Integer isInform) {
        this.isInform = isInform;
    }

    public Integer getIsScene() {
        return isScene;
    }

    public void setIsScene(Integer isScene) {
        this.isScene = isScene;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("imageId", getImageId())
            .append("taskhId", getTaskhId())
            .append("taskCode", getTaskCode())
            .append("routeCode", getRouteCode())
            .append("imageCode", getImageCode())
            .append("path", getPath())
            .append("imageName", getImageName())
            .append("updateTime", getUpdateTime())
            .append("lat", getLat())
            .append("lon", getLon())
            .append("defectCount", getDefectCount())
            .append("defectType", getDefectType())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
