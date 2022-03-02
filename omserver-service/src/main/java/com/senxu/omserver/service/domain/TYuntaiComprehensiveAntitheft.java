package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 综合防盗数据对象 t_yuntai_comprehensive_antitheft
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public class TYuntaiComprehensiveAntitheft
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 云台id */
    @Excel(name = "云台id")
    private Long yuntaiId;

    /** 通道ID */
    @Excel(name = "通道ID")
    private String channelId;

    /** 外力破坏报警 */
    @Excel(name = "外力破坏报警")
    private String extForceDesPolice;

    /** 速度有效值 */
    @Excel(name = "速度有效值")
    private String speedEff;

    /** 速震动报警 */
    @Excel(name = "速震动报警")
    private String shockPolice;

    /** 红外报警 */
    @Excel(name = "红外报警")
    private String infraredPolice;

    /** 红外探测区域 */
    @Excel(name = "红外探测区域")
    private String infraredDetectionArea;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 采样时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "采样时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public Long getYuntaiId() {
        return yuntaiId;
    }

    public void setYuntaiId(Long yuntaiId) {
        this.yuntaiId = yuntaiId;
    }

    public void setChannelId(String channelId) 
    {
        this.channelId = channelId;
    }

    public String getChannelId() 
    {
        return channelId;
    }
    public void setExtForceDesPolice(String extForceDesPolice) 
    {
        this.extForceDesPolice = extForceDesPolice;
    }

    public String getExtForceDesPolice() 
    {
        return extForceDesPolice;
    }
    public void setSpeedEff(String speedEff) 
    {
        this.speedEff = speedEff;
    }

    public String getSpeedEff() 
    {
        return speedEff;
    }
    public void setShockPolice(String shockPolice) 
    {
        this.shockPolice = shockPolice;
    }

    public String getShockPolice() 
    {
        return shockPolice;
    }
    public void setInfraredPolice(String infraredPolice) 
    {
        this.infraredPolice = infraredPolice;
    }

    public String getInfraredPolice() 
    {
        return infraredPolice;
    }
    public void setInfraredDetectionArea(String infraredDetectionArea) 
    {
        this.infraredDetectionArea = infraredDetectionArea;
    }

    public String getInfraredDetectionArea() 
    {
        return infraredDetectionArea;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("channelId", getChannelId())
            .append("extForceDesPolice", getExtForceDesPolice())
            .append("speedEff", getSpeedEff())
            .append("shockPolice", getShockPolice())
            .append("infraredPolice", getInfraredPolice())
            .append("infraredDetectionArea", getInfraredDetectionArea())
            .append("state", getState())
            .append("time", getTime())
            .toString();
    }
}
