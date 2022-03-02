package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


/**
 * t_route_point（航线点信息）对象 t_route_point
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public class TRoutePoint
{
    private static final long serialVersionUID = 1L;

    /** 航点ID */
    private Long routePointId;

    /** 航线ID */
    @Excel(name = "航线ID")
    private Long routeId;

    /** 航点序号 */
    @Excel(name = "航点序号")
    private int ord;

    /** 纬度 */
    @Excel(name = "纬度")
    private String lat;

    /** 经度 */
    @Excel(name = "经度")
    private String lon;

    /** 高度(海拔) */
    @Excel(name = "高度(海拔)")
    private String alt;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setRoutePointId(Long routePointId) 
    {
        this.routePointId = routePointId;
    }

    public Long getRoutePointId() 
    {
        return routePointId;
    }
    public void setRouteId(Long routeId) 
    {
        this.routeId = routeId;
    }

    public Long getRouteId() 
    {
        return routeId;
    }
    public void setOrd(int ord)
    {
        this.ord = ord;
    }

    public int getOrd()
    {
        return ord;
    }
    public void setLat(String lat) 
    {
        this.lat = lat;
    }

    public String getLat() 
    {
        return lat;
    }
    public void setLon(String lon) 
    {
        this.lon = lon;
    }

    public String getLon() 
    {
        return lon;
    }
    public void setAlt(String alt) 
    {
        this.alt = alt;
    }

    public String getAlt() 
    {
        return alt;
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
            .append("routePointId", getRoutePointId())
            .append("routeId", getRouteId())
            .append("ord", getOrd())
            .append("lat", getLat())
            .append("lon", getLon())
            .append("alt", getAlt())
            .append("updateTime", getUpdateTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }

    public TRoutePoint(Long routeId) {
        this.routeId = routeId;
    }

    public TRoutePoint() {
    }
}
