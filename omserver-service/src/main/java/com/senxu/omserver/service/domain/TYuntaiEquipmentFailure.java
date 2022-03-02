package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 设备故障报警对象 t_yuntai_equipment_failure
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public class TYuntaiEquipmentFailure
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 云台ID */
    @Excel(name = "云台ID")
    private Long yuntaiId;

    /** 通道ID */
    @Excel(name = "通道ID")
    private String channelId;

    /** 采样时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "采样时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /** 功能编码 */
    @Excel(name = "功能编码")
    private String functioncode;

    /** 故障编码 */
    @Excel(name = "故障编码")
    private String faultcode;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setYuntaiId(Long yuntaiId)
    {
        this.yuntaiId = yuntaiId;
    }

    public Long getYuntaiId()
    {
        return yuntaiId;
    }
    public void setChannelId(String channelId) 
    {
        this.channelId = channelId;
    }

    public String getChannelId() 
    {
        return channelId;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setFunctioncode(String functioncode) 
    {
        this.functioncode = functioncode;
    }

    public String getFunctioncode() 
    {
        return functioncode;
    }
    public void setFaultcode(String faultcode) 
    {
        this.faultcode = faultcode;
    }

    public String getFaultcode() 
    {
        return faultcode;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("channelId", getChannelId())
            .append("time", getTime())
            .append("functioncode", getFunctioncode())
            .append("faultcode", getFaultcode())
            .append("state", getState())
            .toString();
    }
}
