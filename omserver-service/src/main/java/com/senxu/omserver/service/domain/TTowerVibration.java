package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 杆塔振动采样对象 t_tower_vibration
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public class TTowerVibration{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 杆塔id */
    @Excel(name = "杆塔id")
    private String towerId;

    /** 通道名称 */
    @Excel(name = "通道名称")
    private String channelName;

    /** 通道ID */
    @Excel(name = "通道ID")
    private String channelId;

    /** 采样时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "采样时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /** 振动频率 */
    @Excel(name = "振动频率")
    private String vibrationFre;

    /** 振幅 */
    @Excel(name = "振幅")
    private String amplitude;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 云台id */
    @Excel(name = "云台id")
    private Long yuntaiId;

    public Long getYuntaiId() {
        return yuntaiId;
    }

    public void setYuntaiId(Long yuntaiId) {
        this.yuntaiId = yuntaiId;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTowerId(String towerId) 
    {
        this.towerId = towerId;
    }

    public String getTowerId() 
    {
        return towerId;
    }
    public void setChannelName(String channelName) 
    {
        this.channelName = channelName;
    }

    public String getChannelName() 
    {
        return channelName;
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
    public void setVibrationFre(String vibrationFre) 
    {
        this.vibrationFre = vibrationFre;
    }

    public String getVibrationFre() 
    {
        return vibrationFre;
    }
    public void setAmplitude(String amplitude) 
    {
        this.amplitude = amplitude;
    }

    public String getAmplitude() 
    {
        return amplitude;
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
            .append("towerId", getTowerId())
            .append("channelName", getChannelName())
            .append("channelId", getChannelId())
            .append("time", getTime())
            .append("vibrationFre", getVibrationFre())
            .append("amplitude", getAmplitude())
            .append("state", getState())
            .toString();
    }
}
