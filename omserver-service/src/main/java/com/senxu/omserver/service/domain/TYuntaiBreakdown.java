package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * t_yuntai_breakdown（云台缺销记录）对象 t_yuntai_breakdown
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public class TYuntaiBreakdown extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 云台ID */
    @Excel(name = "云台ID")
    private Long yuntaiId;

    /** 编号ID */
    @Excel(name = "编号ID")
    private String code;

    /** 所属线路ID */
    @Excel(name = "所属线路ID")
    private Long lineId;

    /** 所属线路名称 */
    @Excel(name = "所属线路名称")
    private String lineName;

    /** 责任人 */
    @Excel(name = "责任人")
    private String dutyUser;

    /** 责任人电话 */
    @Excel(name = "责任人电话")
    private String dutyPhone;

    /** 设备厂商 */
    @Excel(name = "设备厂商")
    private String factory;

    /** 缺陷发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "缺陷发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date defectDate;

    /** 缺销时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "缺销时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date restoreTime;

    /** 缺销类型 */
    @Excel(name = "缺销类型")
    private String restoreType;

    /** 处理措施 */
    @Excel(name = "处理措施")
    private String recoverMeasures;

    /** 状态：0  未处理 1  已处理  2 不用处理 */
    @Excel(name = "状态",dictType = "state")
    private Long state;

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
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setLineName(String lineName) 
    {
        this.lineName = lineName;
    }

    public String getLineName() 
    {
        return lineName;
    }
    public void setDutyUser(String dutyUser) 
    {
        this.dutyUser = dutyUser;
    }

    public String getDutyUser() 
    {
        return dutyUser;
    }
    public void setDutyPhone(String dutyPhone) 
    {
        this.dutyPhone = dutyPhone;
    }

    public String getDutyPhone() 
    {
        return dutyPhone;
    }
    public void setFactory(String factory) 
    {
        this.factory = factory;
    }

    public String getFactory() 
    {
        return factory;
    }
    public void setDefectDate(Date defectDate) 
    {
        this.defectDate = defectDate;
    }

    public Date getDefectDate() 
    {
        return defectDate;
    }
    public void setRestoreTime(Date restoreTime) 
    {
        this.restoreTime = restoreTime;
    }

    public Date getRestoreTime() 
    {
        return restoreTime;
    }
    public void setRestoreType(String restoreType) 
    {
        this.restoreType = restoreType;
    }

    public String getRestoreType() 
    {
        return restoreType;
    }
    public void setRecoverMeasures(String recoverMeasures) 
    {
        this.recoverMeasures = recoverMeasures;
    }

    public String getRecoverMeasures() 
    {
        return recoverMeasures;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
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
            .append("code", getCode())
            .append("lineId", getLineId())
            .append("lineName", getLineName())
            .append("dutyUser", getDutyUser())
            .append("dutyPhone", getDutyPhone())
            .append("factory", getFactory())
            .append("defectDate", getDefectDate())
            .append("restoreTime", getRestoreTime())
            .append("restoreType", getRestoreType())
            .append("recoverMeasures", getRecoverMeasures())
            .append("state", getState())
            .append("remark", getRemark())
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
