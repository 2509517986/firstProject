package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * t_yuntai_preset_position（云台预置位信息）对象 t_yuntai_preset_position
 *
 * @author ruoyi
 * @date 2021-06-11
 */
public class TYuntaiPresetPosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 云台ID */
    @Excel(name = "云台ID")
    private Long yuntaiId;

    /** 预置位编号 */
    @Excel(name = "预置位编号")
    private String presetBit;

    public String getPresetName() {
        return presetName;
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }

    /** 预置位名称 */
    @Excel(name = "预置位名称")
    private String presetName;

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
    public void setPresetBit(String presetBit)
    {
        this.presetBit = presetBit;
    }

    public String getPresetBit()
    {
        return presetBit;
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
            .append("presetBit", getPresetBit())
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
