package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * t_yuntai_video（云台短视频）对象 t_yuntai_video
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public class TYuntaiVideo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 云台ID */
    @Excel(name = "云台ID")
    private Long yuntaiId;

    /** 所属线路 */
    @Excel(name = "所属线路")
    private Long lineId;

    /** 杆塔名称 */
    @Excel(name = "杆塔名称")
    private Long towerId;

    /** 视频地址 */
    @Excel(name = "视频地址")
    private String videoUrl;

    /** 抓拍人 */
    @Excel(name = "抓拍人")
    private String captureUser;

    /** 抓拍时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "抓拍时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date captureTime;

    /** 拍摄策略 */
    @Excel(name = "拍摄策略")
    private String strategy;

    /** 拍摄预置位 */
    @Excel(name = "拍摄预置位")
    private String presetBit;

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
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setTowerId(Long towerId) 
    {
        this.towerId = towerId;
    }

    public Long getTowerId() 
    {
        return towerId;
    }
    public void setVideoUrl(String videoUrl) 
    {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() 
    {
        return videoUrl;
    }
    public void setCaptureUser(String captureUser) 
    {
        this.captureUser = captureUser;
    }

    public String getCaptureUser() 
    {
        return captureUser;
    }
    public void setCaptureTime(Date captureTime) 
    {
        this.captureTime = captureTime;
    }

    public Date getCaptureTime() 
    {
        return captureTime;
    }
    public void setStrategy(String strategy) 
    {
        this.strategy = strategy;
    }

    public String getStrategy() 
    {
        return strategy;
    }
    public void setPresetBit(String presetBit) 
    {
        this.presetBit = presetBit;
    }

    public String getPresetBit() 
    {
        return presetBit;
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
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("lineId", getLineId())
            .append("towerId", getTowerId())
            .append("videoUrl", getVideoUrl())
            .append("captureUser", getCaptureUser())
            .append("captureTime", getCaptureTime())
            .append("strategy", getStrategy())
            .append("presetBit", getPresetBit())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
