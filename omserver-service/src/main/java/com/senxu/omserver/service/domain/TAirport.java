package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.Date;

/**
 * t_airport（机巢信息）对象 t_airport
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public class TAirport
{
    private static final long serialVersionUID = 1L;

    /** 机巢ID */
    private Long airportId;

    /** 机巢名称 */
    @Excel(name = "机巢名称")
    private String airportName;

    /** 机巢编号(多翼的ID) */
    @Excel(name = "机巢编号(多翼的ID)")
    private String airportCode;

    /** 机场类型：1  固定机场，2 胶囊机场 */
    @Excel(name = "机场类型：1  固定机场，2 胶囊机场")
    private String relname;

    /** 所在位置-纬度 */
    @Excel(name = "所在位置-纬度")
    private String locationLat;

    /** 所在位置-经度 */
    @Excel(name = "所在位置-经度")
    private String locationLon;

    /** 所在位置-高度(海拔) */
    @Excel(name = "所在位置-高度(海拔)")
    private String locationAltinm;

    /** 预设备降点-纬度 */
    @Excel(name = "预设备降点-纬度")
    private String spareLat;

    /** 预设备降点-经度 */
    @Excel(name = "预设备降点-经度")
    private String spareLon;

    /** 预设备降点-高度(海拔) */
    @Excel(name = "预设备降点-高度(海拔)")
    private String spareAltinm;

    /** 挂载设备 */
    @Excel(name = "挂载设备")
    private String mount;

    /** 机场状态 */
    @Excel(name = "机场状态")
    private String actionStatus;

    /** 在线状态：0 不在线 1 在线 */
    @Excel(name = "在线状态：0 不在线 1 在线")
    private Long state;

    /** 是否有烟雾：0 否 1 是 */
    @Excel(name = "是否有烟雾：0 否 1 是")
    private String isSmoke;

    /** 湿度 */
    @Excel(name = "湿度")
    private String humidity;

    /** 是否积水：0 否 1 是 */
    @Excel(name = "是否积水：0 否 1 是")
    private String isPonding;

    /** 舱内温度 */
    @Excel(name = "舱内温度")
    private String inTemperature;

    /** 供电类型：市电供电，电池箱供电 */
    @Excel(name = "供电类型：市电供电，电池箱供电")
    private String cityElectricity;

    /** 是否有人闯入：0 否 1 是 */
    @Excel(name = "是否有人闯入：0 否 1 是")
    private String intrusion;

    /** 气象站-状态是否满足飞行条件 */
    @Excel(name = "气象站-状态是否满足飞行条件")
    private String meteorologicalState;

    /** 气象站-温度 */
    @Excel(name = "气象站-温度")
    private String meteorologicalTemperature;

    /** 气象站-湿度 */
    @Excel(name = "气象站-湿度")
    private String meteorologicalHumidity;

    /** 气象站-风速 */
    @Excel(name = "气象站-风速")
    private String meteorologicalWindspeed;

    /** 气象站-雨量 */
    @Excel(name = "气象站-雨量")
    private String meteorologicalRainfall;

    /** 气象站-气压 */
    @Excel(name = "气象站-气压")
    private String meteorologicalAirpressure;

    /** 气象站-风向 */
    @Excel(name = "气象站-风向")
    private String meteorologicalWinddirection;

    /** 机巢外视频URL */
    @Excel(name = "机巢外视频URL")
    private String outsideVideoUrl;

    /** 机巢内视频URL */
    @Excel(name = "机巢内视频URL")
    private String innerVideoUrl;

    /** 无人机ID */
    @Excel(name = "无人机ID")
    private String uavId;

    /** 飞控ID */
    @Excel(name = "飞控ID")
    private String fcId;

    /** 无人机状态：0 不在线 1 在线 */
    @Excel(name = "无人机状态：0 不在线 1 在线")
    private Long uavState;

    /** RTK状态：0 不正常 1 正常 */
    @Excel(name = "RTK状态：0 不正常 1 正常")
    private String uavRtk;

    /** 电量 */
    @Excel(name = "电量")
    private String uavBattery;

    /** 高度（海拔） */
    @Excel(name = "高度", readConverterExp = "海=拔")
    private String uavAltinm;

    /** 数据传输 */
    @Excel(name = "数据传输")
    private String uavTran;

    /** 无人机视频流 */
    @Excel(name = "无人机视频流")
    private String uavSrcurl;

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

    /**查询关键字*/
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setAirportId(Long airportId)
    {
        this.airportId = airportId;
    }

    public Long getAirportId() 
    {
        return airportId;
    }
    public void setAirportCode(String airportCode) 
    {
        this.airportCode = airportCode;
    }

    public String getAirportCode() 
    {
        return airportCode;
    }
    public void setRelname(String relname) 
    {
        this.relname = relname;
    }

    public String getRelname() 
    {
        return relname;
    }
    public void setLocationLat(String locationLat) 
    {
        this.locationLat = locationLat;
    }

    public String getLocationLat() 
    {
        return locationLat;
    }
    public void setLocationLon(String locationLon) 
    {
        this.locationLon = locationLon;
    }

    public String getLocationLon() 
    {
        return locationLon;
    }
    public void setLocationAltinm(String locationAltinm) 
    {
        this.locationAltinm = locationAltinm;
    }

    public String getLocationAltinm() 
    {
        return locationAltinm;
    }
    public void setSpareLat(String spareLat) 
    {
        this.spareLat = spareLat;
    }

    public String getSpareLat() 
    {
        return spareLat;
    }
    public void setSpareLon(String spareLon) 
    {
        this.spareLon = spareLon;
    }

    public String getSpareLon() 
    {
        return spareLon;
    }
    public void setSpareAltinm(String spareAltinm) 
    {
        this.spareAltinm = spareAltinm;
    }

    public String getSpareAltinm() 
    {
        return spareAltinm;
    }
    public void setMount(String mount) 
    {
        this.mount = mount;
    }

    public String getMount() 
    {
        return mount;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public void setIsSmoke(String isSmoke)
    {
        this.isSmoke = isSmoke;
    }

    public String getIsSmoke() 
    {
        return isSmoke;
    }
    public void setHumidity(String humidity) 
    {
        this.humidity = humidity;
    }

    public String getHumidity() 
    {
        return humidity;
    }
    public void setIsPonding(String isPonding) 
    {
        this.isPonding = isPonding;
    }

    public String getIsPonding() 
    {
        return isPonding;
    }
    public void setInTemperature(String inTemperature) 
    {
        this.inTemperature = inTemperature;
    }

    public String getInTemperature() 
    {
        return inTemperature;
    }
    public void setCityElectricity(String cityElectricity) 
    {
        this.cityElectricity = cityElectricity;
    }

    public String getCityElectricity() 
    {
        return cityElectricity;
    }
    public void setIntrusion(String intrusion) 
    {
        this.intrusion = intrusion;
    }

    public String getIntrusion() 
    {
        return intrusion;
    }
    public void setMeteorologicalState(String meteorologicalState) 
    {
        this.meteorologicalState = meteorologicalState;
    }

    public String getMeteorologicalState() 
    {
        return meteorologicalState;
    }
    public void setMeteorologicalTemperature(String meteorologicalTemperature) 
    {
        this.meteorologicalTemperature = meteorologicalTemperature;
    }

    public String getMeteorologicalTemperature() 
    {
        return meteorologicalTemperature;
    }
    public void setMeteorologicalHumidity(String meteorologicalHumidity) 
    {
        this.meteorologicalHumidity = meteorologicalHumidity;
    }

    public String getMeteorologicalHumidity() 
    {
        return meteorologicalHumidity;
    }
    public void setMeteorologicalWindspeed(String meteorologicalWindspeed) 
    {
        this.meteorologicalWindspeed = meteorologicalWindspeed;
    }

    public String getMeteorologicalWindspeed() 
    {
        return meteorologicalWindspeed;
    }
    public void setMeteorologicalRainfall(String meteorologicalRainfall) 
    {
        this.meteorologicalRainfall = meteorologicalRainfall;
    }

    public String getMeteorologicalRainfall() 
    {
        return meteorologicalRainfall;
    }
    public void setMeteorologicalAirpressure(String meteorologicalAirpressure) 
    {
        this.meteorologicalAirpressure = meteorologicalAirpressure;
    }

    public String getMeteorologicalAirpressure() 
    {
        return meteorologicalAirpressure;
    }
    public void setMeteorologicalWinddirection(String meteorologicalWinddirection) 
    {
        this.meteorologicalWinddirection = meteorologicalWinddirection;
    }

    public String getMeteorologicalWinddirection() 
    {
        return meteorologicalWinddirection;
    }
    public void setOutsideVideoUrl(String outsideVideoUrl) 
    {
        this.outsideVideoUrl = outsideVideoUrl;
    }

    public String getOutsideVideoUrl() 
    {
        return outsideVideoUrl;
    }
    public void setInnerVideoUrl(String innerVideoUrl) 
    {
        this.innerVideoUrl = innerVideoUrl;
    }

    public String getInnerVideoUrl() 
    {
        return innerVideoUrl;
    }
    public void setUavId(String uavId) 
    {
        this.uavId = uavId;
    }

    public String getUavId() 
    {
        return uavId;
    }
    public void setFcId(String fcId) 
    {
        this.fcId = fcId;
    }

    public String getFcId() 
    {
        return fcId;
    }
    public void setUavState(Long uavState) 
    {
        this.uavState = uavState;
    }

    public Long getUavState() 
    {
        return uavState;
    }
    public void setUavRtk(String uavRtk) 
    {
        this.uavRtk = uavRtk;
    }

    public String getUavRtk() 
    {
        return uavRtk;
    }
    public void setUavBattery(String uavBattery) 
    {
        this.uavBattery = uavBattery;
    }

    public String getUavBattery() 
    {
        return uavBattery;
    }
    public void setUavAltinm(String uavAltinm) 
    {
        this.uavAltinm = uavAltinm;
    }

    public String getUavAltinm() 
    {
        return uavAltinm;
    }
    public void setUavTran(String uavTran) 
    {
        this.uavTran = uavTran;
    }

    public String getUavTran() 
    {
        return uavTran;
    }
    public void setUavSrcurl(String uavSrcurl) 
    {
        this.uavSrcurl = uavSrcurl;
    }

    public String getUavSrcurl() 
    {
        return uavSrcurl;
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
            .append("airportId", getAirportId())
            .append("airportCode", getAirportCode())
            .append("relname", getRelname())
            .append("locationLat", getLocationLat())
            .append("locationLon", getLocationLon())
            .append("locationAltinm", getLocationAltinm())
            .append("spareLat", getSpareLat())
            .append("spareLon", getSpareLon())
            .append("spareAltinm", getSpareAltinm())
            .append("mount", getMount())
            .append("state", getState())
            .append("isSmoke", getIsSmoke())
            .append("humidity", getHumidity())
            .append("isPonding", getIsPonding())
            .append("inTemperature", getInTemperature())
            .append("cityElectricity", getCityElectricity())
            .append("intrusion", getIntrusion())
            .append("meteorologicalState", getMeteorologicalState())
            .append("meteorologicalTemperature", getMeteorologicalTemperature())
            .append("meteorologicalHumidity", getMeteorologicalHumidity())
            .append("meteorologicalWindspeed", getMeteorologicalWindspeed())
            .append("meteorologicalRainfall", getMeteorologicalRainfall())
            .append("meteorologicalAirpressure", getMeteorologicalAirpressure())
            .append("meteorologicalWinddirection", getMeteorologicalWinddirection())
            .append("outsideVideoUrl", getOutsideVideoUrl())
            .append("innerVideoUrl", getInnerVideoUrl())
            .append("uavId", getUavId())
            .append("fcId", getFcId())
            .append("uavState", getUavState())
            .append("uavRtk", getUavRtk())
            .append("uavBattery", getUavBattery())
            .append("uavAltinm", getUavAltinm())
            .append("uavTran", getUavTran())
            .append("uavSrcurl", getUavSrcurl())
            .append("updateTime", getUpdateTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
