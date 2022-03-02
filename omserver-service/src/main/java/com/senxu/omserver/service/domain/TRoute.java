package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * t_route（航线信息）对象 t_route
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public class TRoute
{
    private static final long serialVersionUID = 1L;

    /** 航线ID */
    private Long routeId;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskId;

    /** 航线编号(多翼的ID) */
    @Excel(name = "航线编号(多翼的ID)")
    private String routeCode;

    /** 航线名称 */
    @Excel(name = "航线名称")
    private String name;

    /** 航线描述 */
    @Excel(name = "航线描述")
    private String describes;

    /** 航线组ID（多翼的ID） */
    @Excel(name = "航线组ID", readConverterExp = "多=翼的ID")
    private String groupId;

    /** 航线类型 */
    @Excel(name = "航线类型")
    private String kind;

    /** 航线安全起点-纬度 */
    @Excel(name = "航线安全起点-纬度")
    private String startLat;

    /** 航线安全起点-经度 */
    @Excel(name = "航线安全起点-经度")
    private String startLon;

    /** 航线安全起点-高度(海拔) */
    @Excel(name = "航线安全起点-高度(海拔)")
    private String startAltinm;

    /** 航线安全终止点-纬度 */
    @Excel(name = "航线安全终止点-纬度")
    private String endLat;

    /** 航线安全终止点-经度 */
    @Excel(name = "航线安全终止点-经度")
    private String endLon;

    /** 航线安全终止点-高度(海拔) */
    @Excel(name = "航线安全终止点-高度(海拔)")
    private String endAltinm;

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

    private List<TRoutePoint> tRoutePoint;

    /**查询关键字*/
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<TRoutePoint> gettRoutePoint() {
        return tRoutePoint;
    }

    public void settRoutePoint(List<TRoutePoint> tRoutePoint) {
        this.tRoutePoint = tRoutePoint;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setRouteId(Long routeId) 
    {
        this.routeId = routeId;
    }

    public Long getRouteId() 
    {
        return routeId;
    }
    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setRouteCode(String routeCode) 
    {
        this.routeCode = routeCode;
    }

    public String getRouteCode() 
    {
        return routeCode;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDescribes(String describes) 
    {
        this.describes = describes;
    }

    public String getDescribes() 
    {
        return describes;
    }
    public void setGroupId(String groupId) 
    {
        this.groupId = groupId;
    }

    public String getGroupId() 
    {
        return groupId;
    }
    public void setKind(String kind) 
    {
        this.kind = kind;
    }

    public String getKind() 
    {
        return kind;
    }
    public void setStartLat(String startLat) 
    {
        this.startLat = startLat;
    }

    public String getStartLat() 
    {
        return startLat;
    }
    public void setStartLon(String startLon) 
    {
        this.startLon = startLon;
    }

    public String getStartLon() 
    {
        return startLon;
    }
    public void setStartAltinm(String startAltinm) 
    {
        this.startAltinm = startAltinm;
    }

    public String getStartAltinm() 
    {
        return startAltinm;
    }
    public void setEndLat(String endLat) 
    {
        this.endLat = endLat;
    }

    public String getEndLat() 
    {
        return endLat;
    }
    public void setEndLon(String endLon) 
    {
        this.endLon = endLon;
    }

    public String getEndLon() 
    {
        return endLon;
    }
    public void setEndAltinm(String endAltinm) 
    {
        this.endAltinm = endAltinm;
    }

    public String getEndAltinm() 
    {
        return endAltinm;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("routeId", getRouteId())
            .append("taskId", getTaskId())
            .append("routeCode", getRouteCode())
            .append("name", getName())
            .append("describes", getDescribes())
            .append("groupId", getGroupId())
            .append("kind", getKind())
            .append("startLat", getStartLat())
            .append("startLon", getStartLon())
            .append("startAltinm", getStartAltinm())
            .append("endLat", getEndLat())
            .append("endLon", getEndLon())
            .append("endAltinm", getEndAltinm())
            .append("updateTime", getUpdateTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }

    public TRoute(Long taskId) {
        this.taskId = taskId;
    }

    public TRoute(){

    }
}
