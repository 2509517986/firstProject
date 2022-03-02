package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * t_yuntai_config_strategy（云台配置策略）对象 t_yuntai_config_strategy
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public class TYuntaiConfigStrategy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 云台ID */
    @Excel(name = "云台ID")
    private Long yuntaiId;

    /** 策略类型：1 图片抓拍 2 视频抓拍 */
    @Excel(name = "策略类型",readConverterExp = "1=图片抓拍,2=视频抓拍")
    private String kind;

    /** 抓拍时间/开始时间 */
    @Excel(name = "抓拍时间/开始时间")
    private String captureTime;

    /** 预置位 */
    @Excel(name = "预置位")
    private String presetBit;

    /** 动作类型 */
    @Excel(name = "动作类型")
    private String actionType;

    /** 持续时间(秒) */
    @Excel(name = "持续时间(秒)")
    private Long duration;

    /** 新增人 */
    @Excel(name = "新增人")
    private String createUser;

    /** 更新人 */
    @Excel(name = "更新人")
    private String modifyUser;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

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
    public void setKind(String kind) 
    {
        this.kind = kind;
    }

    public String getKind() 
    {
        return kind;
    }
    public void setCaptureTime(String captureTime) 
    {
        this.captureTime = captureTime;
    }

    public String getCaptureTime() 
    {
        return captureTime;
    }
    public void setPresetBit(String presetBit) 
    {
        this.presetBit = presetBit;
    }

    public String getPresetBit() 
    {
        return presetBit;
    }
    public void setActionType(String actionType) 
    {
        this.actionType = actionType;
    }

    public String getActionType() 
    {
        return actionType;
    }
    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setModifyUser(String modifyUser) 
    {
        this.modifyUser = modifyUser;
    }

    public String getModifyUser() 
    {
        return modifyUser;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
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
            .append("kind", getKind())
            .append("captureTime", getCaptureTime())
            .append("presetBit", getPresetBit())
            .append("actionType", getActionType())
            .append("duration", getDuration())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("modifyUser", getModifyUser())
            .append("modifyTime", getModifyTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
