package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 杆塔拉力采样对象 t_tower_pull
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public class TTowerPull
{
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

    /** 最大拉力 */
    @Excel(name = "最大拉力")
    private String maxPull;

    /** 最大拉力的风偏角 */
    @Excel(name = "最大拉力的风偏角")
    private String windDefAngleOfMaxPull;

    /** 最大拉力时的倾斜角 */
    @Excel(name = "最大拉力时的倾斜角")
    private String incAngleOfMaxPull;

    /** 最小拉力 */
    @Excel(name = "最小拉力")
    private String minPull;

    /** 最小拉力的风偏角 */
    @Excel(name = "最小拉力的风偏角")
    private String windDefAngleOfMinPull;

    /** 最小拉力的倾斜角 */
    @Excel(name = "最小拉力的倾斜角")
    private String incAngleOfMinPull;

    /** 最大风偏角 */
    @Excel(name = "最大风偏角")
    private String maxWindDefAngle;

    /** 最大风偏角的倾斜角 */
    @Excel(name = "最大风偏角的倾斜角")
    private String incAngleOfMaxWind;

    /** 最大风偏角的拉力 */
    @Excel(name = "最大风偏角的拉力")
    private String pullOfMaxWind;

    /** 最小风偏角 */
    @Excel(name = "最小风偏角")
    private String minWindDefAngle;

    /** 最小风偏角的倾斜角 */
    @Excel(name = "最小风偏角的倾斜角")
    private String incAngleOfMinWind;

    /** 最小风偏角的拉力 */
    @Excel(name = "最小风偏角的拉力")
    private String pullOfMinWind;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 采集时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "采集时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date time;

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
    public void setMaxPull(String maxPull) 
    {
        this.maxPull = maxPull;
    }

    public String getMaxPull() 
    {
        return maxPull;
    }
    public void setWindDefAngleOfMaxPull(String windDefAngleOfMaxPull) 
    {
        this.windDefAngleOfMaxPull = windDefAngleOfMaxPull;
    }

    public String getWindDefAngleOfMaxPull() 
    {
        return windDefAngleOfMaxPull;
    }
    public void setIncAngleOfMaxPull(String incAngleOfMaxPull) 
    {
        this.incAngleOfMaxPull = incAngleOfMaxPull;
    }

    public String getIncAngleOfMaxPull() 
    {
        return incAngleOfMaxPull;
    }
    public void setMinPull(String minPull) 
    {
        this.minPull = minPull;
    }

    public String getMinPull() 
    {
        return minPull;
    }
    public void setWindDefAngleOfMinPull(String windDefAngleOfMinPull) 
    {
        this.windDefAngleOfMinPull = windDefAngleOfMinPull;
    }

    public String getWindDefAngleOfMinPull() 
    {
        return windDefAngleOfMinPull;
    }
    public void setIncAngleOfMinPull(String incAngleOfMinPull) 
    {
        this.incAngleOfMinPull = incAngleOfMinPull;
    }

    public String getIncAngleOfMinPull() 
    {
        return incAngleOfMinPull;
    }
    public void setMaxWindDefAngle(String maxWindDefAngle) 
    {
        this.maxWindDefAngle = maxWindDefAngle;
    }

    public String getMaxWindDefAngle() 
    {
        return maxWindDefAngle;
    }
    public void setIncAngleOfMaxWind(String incAngleOfMaxWind) 
    {
        this.incAngleOfMaxWind = incAngleOfMaxWind;
    }

    public String getIncAngleOfMaxWind() 
    {
        return incAngleOfMaxWind;
    }
    public void setPullOfMaxWind(String pullOfMaxWind) 
    {
        this.pullOfMaxWind = pullOfMaxWind;
    }

    public String getPullOfMaxWind() 
    {
        return pullOfMaxWind;
    }
    public void setMinWindDefAngle(String minWindDefAngle) 
    {
        this.minWindDefAngle = minWindDefAngle;
    }

    public String getMinWindDefAngle() 
    {
        return minWindDefAngle;
    }
    public void setIncAngleOfMinWind(String incAngleOfMinWind) 
    {
        this.incAngleOfMinWind = incAngleOfMinWind;
    }

    public String getIncAngleOfMinWind() 
    {
        return incAngleOfMinWind;
    }
    public void setPullOfMinWind(String pullOfMinWind) 
    {
        this.pullOfMinWind = pullOfMinWind;
    }

    public String getPullOfMinWind() 
    {
        return pullOfMinWind;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("towerId", getTowerId())
            .append("channelName", getChannelName())
            .append("channelId", getChannelId())
            .append("maxPull", getMaxPull())
            .append("windDefAngleOfMaxPull", getWindDefAngleOfMaxPull())
            .append("incAngleOfMaxPull", getIncAngleOfMaxPull())
            .append("minPull", getMinPull())
            .append("windDefAngleOfMinPull", getWindDefAngleOfMinPull())
            .append("incAngleOfMinPull", getIncAngleOfMinPull())
            .append("maxWindDefAngle", getMaxWindDefAngle())
            .append("incAngleOfMaxWind", getIncAngleOfMaxWind())
            .append("pullOfMaxWind", getPullOfMaxWind())
            .append("minWindDefAngle", getMinWindDefAngle())
            .append("incAngleOfMinWind", getIncAngleOfMinWind())
            .append("pullOfMinWind", getPullOfMinWind())
            .append("state", getState())
            .append("time", getTime())
            .toString();
    }
}
