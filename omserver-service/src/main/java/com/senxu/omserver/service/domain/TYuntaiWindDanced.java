package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 大风舞动报警对象 t_yuntai_wind_danced
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public class TYuntaiWindDanced
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

    /** 拉力平均值 */
    @Excel(name = "拉力平均值")
    private String avgPull;

    /** 相对于平均值的变化量 */
    @Excel(name = "相对于平均值的变化量")
    private String pullChangeRange;

    /** 拉力变化频率 */
    @Excel(name = "拉力变化频率")
    private String pullChangeFre;

    /** 风偏角平均值 */
    @Excel(name = "风偏角平均值")
    private String avgWindDefAngleSymbol;

    /** 风偏角平均值 */
    @Excel(name = "风偏角平均值")
    private String avgWindDefAngle;

    /** 风偏角幅值 */
    @Excel(name = "风偏角幅值")
    private String windDefAngleRangeSymbol;

    /** 风偏角幅值 */
    @Excel(name = "风偏角幅值")
    private String windDefAngleRange;

    /** 风偏角变化频率 */
    @Excel(name = "风偏角变化频率")
    private String windDefAngleChangeRange;

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
    public void setAvgPull(String avgPull) 
    {
        this.avgPull = avgPull;
    }

    public String getAvgPull() 
    {
        return avgPull;
    }
    public void setPullChangeRange(String pullChangeRange) 
    {
        this.pullChangeRange = pullChangeRange;
    }

    public String getPullChangeRange() 
    {
        return pullChangeRange;
    }
    public void setPullChangeFre(String pullChangeFre) 
    {
        this.pullChangeFre = pullChangeFre;
    }

    public String getPullChangeFre() 
    {
        return pullChangeFre;
    }
    public void setAvgWindDefAngleSymbol(String avgWindDefAngleSymbol) 
    {
        this.avgWindDefAngleSymbol = avgWindDefAngleSymbol;
    }

    public String getAvgWindDefAngleSymbol() 
    {
        return avgWindDefAngleSymbol;
    }
    public void setAvgWindDefAngle(String avgWindDefAngle) 
    {
        this.avgWindDefAngle = avgWindDefAngle;
    }

    public String getAvgWindDefAngle() 
    {
        return avgWindDefAngle;
    }
    public void setWindDefAngleRangeSymbol(String windDefAngleRangeSymbol) 
    {
        this.windDefAngleRangeSymbol = windDefAngleRangeSymbol;
    }

    public String getWindDefAngleRangeSymbol() 
    {
        return windDefAngleRangeSymbol;
    }
    public void setWindDefAngleRange(String windDefAngleRange) 
    {
        this.windDefAngleRange = windDefAngleRange;
    }

    public String getWindDefAngleRange() 
    {
        return windDefAngleRange;
    }
    public void setWindDefAngleChangeRange(String windDefAngleChangeRange) 
    {
        this.windDefAngleChangeRange = windDefAngleChangeRange;
    }

    public String getWindDefAngleChangeRange() 
    {
        return windDefAngleChangeRange;
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
            .append("avgPull", getAvgPull())
            .append("pullChangeRange", getPullChangeRange())
            .append("pullChangeFre", getPullChangeFre())
            .append("avgWindDefAngleSymbol", getAvgWindDefAngleSymbol())
            .append("avgWindDefAngle", getAvgWindDefAngle())
            .append("windDefAngleRangeSymbol", getWindDefAngleRangeSymbol())
            .append("windDefAngleRange", getWindDefAngleRange())
            .append("windDefAngleChangeRange", getWindDefAngleChangeRange())
            .append("state", getState())
            .toString();
    }
}
