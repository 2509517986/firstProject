package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_yuntai_preview（图片预览记录）对象 t_yuntai_preview
 * 
 * @author ruoyi
 * @date 2021-06-22
 */
public class TYuntaiPreview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 图片类型：1 抓拍 2 告警 */
    @Excel(name = "图片类型",readConverterExp = "1=抓拍,2=告警")
    private Integer kind;

    /** 图片ID */
    @Excel(name = "图片ID")
    private Long pictureId;


    /** 阅览人Id */
    @Excel(name = "阅览人Id")
    private Long previewUserid;


    /** 阅览人 */
    @Excel(name = "阅览人")
    private String previewUser;

    /** 阅览时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "阅览时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date previewTime;

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
    public void setKind(Integer kind)
    {
        this.kind = kind;
    }

    public Integer getKind()
    {
        return kind;
    }
    public void setPictureId(Long pictureId) 
    {
        this.pictureId = pictureId;
    }

    public Long getPictureId() 
    {
        return pictureId;
    }
    public void setPreviewUser(String previewUser) 
    {
        this.previewUser = previewUser;
    }

    public Long getPreviewUserid() {
        return previewUserid;
    }

    public void setPreviewUserid(Long previewUserid) {
        this.previewUserid = previewUserid;
    }

    public String getPreviewUser()
    {
        return previewUser;
    }
    public void setPreviewTime(Date previewTime) 
    {
        this.previewTime = previewTime;
    }

    public Date getPreviewTime() 
    {
        return previewTime;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("kind", getKind())
            .append("pictureId", getPictureId())
            .append("previewUser", getPreviewUser())
            .append("previewTime", getPreviewTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
