package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 舞动振幅频率数据对象 t_yuntai_galloping_amplitude
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public class TYuntaiGallopingAmplitude
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 云台ID */
    @Excel(name = "云台ID")
    private Long yuntaiId;

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

    /** 舞动频率 */
    @Excel(name = "舞动频率")
    private String gallopingFre;

    /** 舞动幅值 */
    @Excel(name = "舞动幅值")
    private String gallopingAmplitude;

    /** 垂直舞动幅值 */
    @Excel(name = "垂直舞动幅值")
    private String verticalGallopingAmplitude;

    /** 水平舞动幅值 */
    @Excel(name = "水平舞动幅值")
    private String levelGallopingAmplitude;

    /** 舞动椭圆倾斜角 */
    @Excel(name = "舞动椭圆倾斜角")
    private String gallopingincAngleOfEllipse;

    /** 传感器工作电压 */
    @Excel(name = "传感器工作电压")
    private String voltage;

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
    public void setGallopingFre(String gallopingFre) 
    {
        this.gallopingFre = gallopingFre;
    }

    public String getGallopingFre() 
    {
        return gallopingFre;
    }
    public void setGallopingAmplitude(String gallopingAmplitude) 
    {
        this.gallopingAmplitude = gallopingAmplitude;
    }

    public String getGallopingAmplitude() 
    {
        return gallopingAmplitude;
    }
    public void setVerticalGallopingAmplitude(String verticalGallopingAmplitude) 
    {
        this.verticalGallopingAmplitude = verticalGallopingAmplitude;
    }

    public String getVerticalGallopingAmplitude() 
    {
        return verticalGallopingAmplitude;
    }
    public void setLevelGallopingAmplitude(String levelGallopingAmplitude) 
    {
        this.levelGallopingAmplitude = levelGallopingAmplitude;
    }

    public String getLevelGallopingAmplitude() 
    {
        return levelGallopingAmplitude;
    }
    public void setGallopingincAngleOfEllipse(String gallopingincAngleOfEllipse) 
    {
        this.gallopingincAngleOfEllipse = gallopingincAngleOfEllipse;
    }

    public String getGallopingincAngleOfEllipse() 
    {
        return gallopingincAngleOfEllipse;
    }
    public void setVoltage(String voltage) 
    {
        this.voltage = voltage;
    }

    public String getVoltage() 
    {
        return voltage;
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
            .append("channelName", getChannelName())
            .append("channelId", getChannelId())
            .append("time", getTime())
            .append("gallopingFre", getGallopingFre())
            .append("gallopingAmplitude", getGallopingAmplitude())
            .append("verticalGallopingAmplitude", getVerticalGallopingAmplitude())
            .append("levelGallopingAmplitude", getLevelGallopingAmplitude())
            .append("gallopingincAngleOfEllipse", getGallopingincAngleOfEllipse())
            .append("voltage", getVoltage())
            .append("state", getState())
            .toString();
    }
}
