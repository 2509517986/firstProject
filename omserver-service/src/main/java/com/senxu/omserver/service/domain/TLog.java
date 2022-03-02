package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.senxu.omserver.common.core.domain.BaseEntity;

/**
 * t_log（日志）对象 t_log
 * 
 * @author ruoyi
 * @date 2021-07-23
 */
public class TLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志编号 */
    private Long id;

    /** 操作用户姓名 */
    @Excel(name = "操作用户姓名")
    private String relname;

    /** 操作用户账号 */
    @Excel(name = "操作用户账号")
    private String username;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operaTime;

    /** 日志类型 */
    @Excel(name = "日志类型")
    private String operaStlye;

    /** 操作信息 */
    @Excel(name = "操作信息")
    private String operaRecord;

    /** 操作模块 */
    @Excel(name = "操作模块")
    private String operaModel;

    /** 操作结果 */
    @Excel(name = "操作结果")
    private String operaResult;

    /** 操作IP */
    @Excel(name = "操作IP")
    private String userIp;

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

    /** 查询关键字 */
    private String keyword;


    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRelname(String relname) 
    {
        this.relname = relname;
    }

    public String getRelname() 
    {
        return relname;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setOperaTime(Date operaTime) 
    {
        this.operaTime = operaTime;
    }

    public Date getOperaTime() 
    {
        return operaTime;
    }
    public void setOperaStlye(String operaStlye) 
    {
        this.operaStlye = operaStlye;
    }

    public String getOperaStlye() 
    {
        return operaStlye;
    }
    public void setOperaRecord(String operaRecord) 
    {
        this.operaRecord = operaRecord;
    }

    public String getOperaRecord() 
    {
        return operaRecord;
    }
    public void setOperaModel(String operaModel) 
    {
        this.operaModel = operaModel;
    }

    public String getOperaModel() 
    {
        return operaModel;
    }
    public void setOperaResult(String operaResult) 
    {
        this.operaResult = operaResult;
    }

    public String getOperaResult() 
    {
        return operaResult;
    }
    public void setUserIp(String userIp) 
    {
        this.userIp = userIp;
    }

    public String getUserIp() 
    {
        return userIp;
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

    public String getKeyword() { return keyword;}
    public void setKeyword(String keyword) { this.keyword = keyword; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("relname", getRelname())
            .append("username", getUsername())
            .append("operaTime", getOperaTime())
            .append("operaStlye", getOperaStlye())
            .append("operaRecord", getOperaRecord())
            .append("operaModel", getOperaModel())
            .append("operaResult", getOperaResult())
            .append("userIp", getUserIp())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
