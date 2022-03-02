package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * t_airport_battery（机巢电池）对象 t_airport_battery
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public class TAirportBattery
{
    private static final long serialVersionUID = 1L;

    /** 机巢电池ID */
    private Long batteryId;

    /** 机巢ID */
    @Excel(name = "机巢ID")
    private Long airportId;

    /** 电池槽ID */
    @Excel(name = "电池槽ID")
    private Long slotbatteryId;

    /** 电池状态 */
    @Excel(name = "电池状态")
    private String state;

    /** 电压 */
    @Excel(name = "电压")
    private String chargevolt;

    /** 电量 */
    @Excel(name = "电量")
    private String soc;

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


    public void setBatteryId(Long batteryId) 
    {
        this.batteryId = batteryId;
    }

    public Long getBatteryId() 
    {
        return batteryId;
    }
    public void setAirportId(Long airportId) 
    {
        this.airportId = airportId;
    }

    public Long getAirportId() 
    {
        return airportId;
    }
    public void setSlotbatteryId(Long slotbatteryId) 
    {
        this.slotbatteryId = slotbatteryId;
    }

    public Long getSlotbatteryId() 
    {
        return slotbatteryId;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setChargevolt(String chargevolt) 
    {
        this.chargevolt = chargevolt;
    }

    public String getChargevolt() 
    {
        return chargevolt;
    }
    public void setSoc(String soc) 
    {
        this.soc = soc;
    }

    public String getSoc() 
    {
        return soc;
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
            .append("batteryId", getBatteryId())
            .append("airportId", getAirportId())
            .append("slotbatteryId", getSlotbatteryId())
            .append("state", getState())
            .append("chargevolt", getChargevolt())
            .append("soc", getSoc())
            .append("updateTime", getUpdateTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
