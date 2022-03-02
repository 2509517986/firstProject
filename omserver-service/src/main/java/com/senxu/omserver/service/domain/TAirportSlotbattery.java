package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * t_airport_slotbattery（机巢电池槽）对象 t_airport_slotbattery
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public class TAirportSlotbattery
{
    private static final long serialVersionUID = 1L;

    /** 电池槽ID */
    private Long slotbatteryId;

    /** 机巢ID */
    @Excel(name = "机巢ID")
    private Long airportId;

    /** 电池槽序号 */
    @Excel(name = "电池槽序号")
    private int ord;

    /** 电池槽的状态 */
    @Excel(name = "电池槽的状态")
    private String state;

    /** 电池槽是否被占用 */
    @Excel(name = "电池槽是否被占用")
    private String occupy;

    /** 电压表坏时的详细错误信息 */
    @Excel(name = "电压表坏时的详细错误信息")
    private String errmsg;

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

    public void setSlotbatteryId(Long slotbatteryId) 
    {
        this.slotbatteryId = slotbatteryId;
    }

    public Long getSlotbatteryId() 
    {
        return slotbatteryId;
    }
    public void setAirportId(Long airportId) 
    {
        this.airportId = airportId;
    }

    public Long getAirportId() 
    {
        return airportId;
    }
    public void setOrd(int ord)
    {
        this.ord = ord;
    }

    public int getOrd()
    {
        return ord;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setOccupy(String occupy) 
    {
        this.occupy = occupy;
    }

    public String getOccupy() 
    {
        return occupy;
    }
    public void setErrmsg(String errmsg) 
    {
        this.errmsg = errmsg;
    }

    public String getErrmsg() 
    {
        return errmsg;
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
            .append("slotbatteryId", getSlotbatteryId())
            .append("airportId", getAirportId())
            .append("ord", getOrd())
            .append("state", getState())
            .append("occupy", getOccupy())
            .append("errmsg", getErrmsg())
            .append("updateTime", getUpdateTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
