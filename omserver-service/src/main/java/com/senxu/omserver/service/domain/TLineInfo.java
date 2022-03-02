package com.senxu.omserver.service.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Joiner;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_line_info（线路信息）对象 t_line_info
 * 
 * @author ruoyi
 * @date 2021-06-17
 */
public class TLineInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 线路编号 */
    @Excel(name = "线路编号")
    private String code;

    /** 线路名称 */
    @Excel(name = "线路名称")
    private String name;

    /** 管辖部门id */
    @Excel(name = "管辖部门id")
    private Long deptId;

    /** 管辖部门 */
    @Excel(name = "管辖部门")
    private String deptName;

    /** 线高（米） */
    @Excel(name = "线高（米）")
    private BigDecimal high;

    /** 传输电压 */
    @Excel(name = "传输电压")
    private String voltage;

    /** 高压等级：1 高压 2 超高压 3 特高压 */
    @Excel(name = "高压等级",dictType = "line_voltage")
    private String level;

    /** 全长（千米） */
    @Excel(name = "全长千米）")
    private String length;

    /** 建成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "建成时间",width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeDate;


    /** 起点 */
    @Excel(name = "起点")
    private String startPoint;

    /** 终点 */
    @Excel(name = "终点")
    private String endPoint;

    /** 状态：1 正常 2 维护中 -1已弃用 */
    @Excel(name = "状态",dictType = "state")
    private Long state;

    /** 新增人 */
    @Excel(name = "新增人")
    private String createUser;

    /** 新增时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "新增时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;


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


    /** 用于存储角色所拥有部门权限的deptId，用逗号分隔  */
    private String deptIds;

    /** 当前用户ID */
    private Long userId;

    /** 查询关键字 */
    private String keyword;



    /** 构造函数 */
    public TLineInfo(){
    }
    /** 构造函数 */
    public TLineInfo(List<Integer> deptIds){
        if(deptIds==null)
            this.deptIds=null;
        else
            this.deptIds = StringUtils.join(deptIds.toArray(), ",");
    }

    /** 构造函数 */
    public TLineInfo(Long userId){
        this.userId=userId;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setDeptName(String deptName) 
    {
        this.deptName = deptName;
    }

    public String getDeptName() 
    {
        return deptName;
    }
    public void setHigh(BigDecimal high)
    {
        this.high = high;
    }

    public BigDecimal getHigh()
    {
        return high;
    }
    public void setVoltage(String voltage) 
    {
        this.voltage = voltage;
    }

    public String getVoltage() 
    {
        return voltage;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setLength(String length) 
    {
        this.length = length;
    }

    public String getLength() 
    {
        return length;
    }
    public void setStartPoint(String startPoint) 
    {
        this.startPoint = startPoint;
    }

    public String getStartPoint() 
    {
        return startPoint;
    }
    public void setEndPoint(String endPoint) 
    {
        this.endPoint = endPoint;
    }

    public String getEndPoint() 
    {
        return endPoint;
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

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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


    public void setDeptIds(String deptIds)
    {
        this.deptIds = deptIds;
    }

    public String getDeptIds()
    {
        return deptIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getcompleteDate() {
        return completeDate;
    }

    public void setcompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("high", getHigh())
            .append("voltage", getVoltage())
            .append("level", getLevel())
            .append("length", getLength())
            .append("completeDate", getcompleteDate())
            .append("startPoint", getStartPoint())
            .append("endPoint", getEndPoint())
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
            .append("keyword", getKeyword())
            .toString();
    }
}
