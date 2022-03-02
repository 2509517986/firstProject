package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 气象信息获取对象 micro_mete
 * 
 * @author ruoyi
 * @date 2021-12-20
 */
public class MicroMete
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 采样时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "采样时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date collectDate;

    /** 雨量 */
    @Excel(name = "雨量")
    private String rainfall;

    /** 雨量累积 */
    @Excel(name = "雨量累积")
    private String rainfallAccumulate;

    /** 风速 */
    @Excel(name = "风速")
    private String instantWindSpeed;

    /** 最大风速 */
    @Excel(name = "最大风速")
    private String maxinstantWindSpeed;

    /** 最小风速 */
    @Excel(name = "最小风速")
    private String mininstantWindSpeed;

    /** 1分钟平均风速 */
    @Excel(name = "1分钟平均风速")
    private String avgWindSpeedOneMinute;

    /** 10分钟平均风速 */
    @Excel(name = "10分钟平均风速")
    private String avgWindSpeedTenMinute;

    /** 10分钟最大风速 */
    @Excel(name = "10分钟最大风速")
    private String maxWindSpeedTenMinute;

    /** 气压 */
    @Excel(name = "气压")
    private String pressure;

    /** 最大气压 */
    @Excel(name = "最大气压")
    private String maxPressure;

    /** 最小气压 */
    @Excel(name = "最小气压")
    private String minPressure;

    /** 风向 */
    @Excel(name = "风向")
    private String instantWindDirection;

    /** 1分钟平均风向 */
    @Excel(name = "1分钟平均风向")
    private String avgWindWirectionOneMinute;

    /** 10分钟平均风向 */
    @Excel(name = "10分钟平均风向")
    private String avgWindWirectionTenMinute;

    /** 湿度 */
    @Excel(name = "湿度")
    private String humidity;

    /** 最大湿度 */
    @Excel(name = "最大湿度")
    private String maxHumidity;

    /** 最小湿度 */
    @Excel(name = "最小湿度")
    private String minHumidity;

    /** 降雨量 */
    @Excel(name = "降雨量")
    private String rainfallHour;

    /** 航速 */
    @Excel(name = "航速")
    private String speed;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @Excel(name = "纬度")
    private String latitude;

    /** 日照 */
    @Excel(name = "日照")
    private String sunshine;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

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

    /** 温度 */
    @Excel(name = "温度")
    private String temperature;

    /** 云台id */
    @Excel(name = "云台id")
    private Long yuntaiId;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setCollectDate(Date collectDate) 
    {
        this.collectDate = collectDate;
    }

    public Date getCollectDate() 
    {
        return collectDate;
    }
    public void setRainfall(String rainfall) 
    {
        this.rainfall = rainfall;
    }

    public String getRainfall() 
    {
        return rainfall;
    }
    public void setRainfallAccumulate(String rainfallAccumulate) 
    {
        this.rainfallAccumulate = rainfallAccumulate;
    }

    public String getRainfallAccumulate() 
    {
        return rainfallAccumulate;
    }
    public void setInstantWindSpeed(String instantWindSpeed) 
    {
        this.instantWindSpeed = instantWindSpeed;
    }

    public String getInstantWindSpeed() 
    {
        return instantWindSpeed;
    }
    public void setMaxinstantWindSpeed(String maxinstantWindSpeed) 
    {
        this.maxinstantWindSpeed = maxinstantWindSpeed;
    }

    public String getMaxinstantWindSpeed() 
    {
        return maxinstantWindSpeed;
    }
    public void setMininstantWindSpeed(String mininstantWindSpeed) 
    {
        this.mininstantWindSpeed = mininstantWindSpeed;
    }

    public String getMininstantWindSpeed() 
    {
        return mininstantWindSpeed;
    }
    public void setAvgWindSpeedOneMinute(String avgWindSpeedOneMinute) 
    {
        this.avgWindSpeedOneMinute = avgWindSpeedOneMinute;
    }

    public String getAvgWindSpeedOneMinute() 
    {
        return avgWindSpeedOneMinute;
    }
    public void setAvgWindSpeedTenMinute(String avgWindSpeedTenMinute) 
    {
        this.avgWindSpeedTenMinute = avgWindSpeedTenMinute;
    }

    public String getAvgWindSpeedTenMinute() 
    {
        return avgWindSpeedTenMinute;
    }
    public void setMaxWindSpeedTenMinute(String maxWindSpeedTenMinute) 
    {
        this.maxWindSpeedTenMinute = maxWindSpeedTenMinute;
    }

    public String getMaxWindSpeedTenMinute() 
    {
        return maxWindSpeedTenMinute;
    }
    public void setPressure(String pressure) 
    {
        this.pressure = pressure;
    }

    public String getPressure() 
    {
        return pressure;
    }
    public void setMaxPressure(String maxPressure) 
    {
        this.maxPressure = maxPressure;
    }

    public String getMaxPressure() 
    {
        return maxPressure;
    }
    public void setMinPressure(String minPressure) 
    {
        this.minPressure = minPressure;
    }

    public String getMinPressure() 
    {
        return minPressure;
    }
    public void setInstantWindDirection(String instantWindDirection) 
    {
        this.instantWindDirection = instantWindDirection;
    }

    public String getInstantWindDirection() 
    {
        return instantWindDirection;
    }
    public void setAvgWindWirectionOneMinute(String avgWindWirectionOneMinute) 
    {
        this.avgWindWirectionOneMinute = avgWindWirectionOneMinute;
    }

    public String getAvgWindWirectionOneMinute() 
    {
        return avgWindWirectionOneMinute;
    }
    public void setAvgWindWirectionTenMinute(String avgWindWirectionTenMinute) 
    {
        this.avgWindWirectionTenMinute = avgWindWirectionTenMinute;
    }

    public String getAvgWindWirectionTenMinute() 
    {
        return avgWindWirectionTenMinute;
    }
    public void setHumidity(String humidity) 
    {
        this.humidity = humidity;
    }

    public String getHumidity() 
    {
        return humidity;
    }
    public void setMaxHumidity(String maxHumidity) 
    {
        this.maxHumidity = maxHumidity;
    }

    public String getMaxHumidity() 
    {
        return maxHumidity;
    }
    public void setMinHumidity(String minHumidity) 
    {
        this.minHumidity = minHumidity;
    }

    public String getMinHumidity() 
    {
        return minHumidity;
    }
    public void setRainfallHour(String rainfallHour) 
    {
        this.rainfallHour = rainfallHour;
    }

    public String getRainfallHour() 
    {
        return rainfallHour;
    }
    public void setSpeed(String speed) 
    {
        this.speed = speed;
    }

    public String getSpeed() 
    {
        return speed;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setSunshine(String sunshine) 
    {
        this.sunshine = sunshine;
    }

    public String getSunshine() 
    {
        return sunshine;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
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
    public void setTemperature(String temperature) 
    {
        this.temperature = temperature;
    }

    public String getTemperature() 
    {
        return temperature;
    }

    public Long getYuntaiId() {
        return yuntaiId;
    }

    public void setYuntaiId(Long yuntaiId) {
        this.yuntaiId = yuntaiId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("collectDate", getCollectDate())
            .append("rainfall", getRainfall())
            .append("rainfallAccumulate", getRainfallAccumulate())
            .append("instantWindSpeed", getInstantWindSpeed())
            .append("maxinstantWindSpeed", getMaxinstantWindSpeed())
            .append("mininstantWindSpeed", getMininstantWindSpeed())
            .append("avgWindSpeedOneMinute", getAvgWindSpeedOneMinute())
            .append("avgWindSpeedTenMinute", getAvgWindSpeedTenMinute())
            .append("maxWindSpeedTenMinute", getMaxWindSpeedTenMinute())
            .append("pressure", getPressure())
            .append("maxPressure", getMaxPressure())
            .append("minPressure", getMinPressure())
            .append("instantWindDirection", getInstantWindDirection())
            .append("avgWindWirectionOneMinute", getAvgWindWirectionOneMinute())
            .append("avgWindWirectionTenMinute", getAvgWindWirectionTenMinute())
            .append("humidity", getHumidity())
            .append("maxHumidity", getMaxHumidity())
            .append("minHumidity", getMinHumidity())
            .append("rainfallHour", getRainfallHour())
            .append("speed", getSpeed())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("sunshine", getSunshine())
            .append("state", getState())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .append("temperature", getTemperature())
            .append("yuntaiId", getYuntaiId())
            .toString();
    }
}
