package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 【文件上传信息】对象 t_upload_list
 * 
 * @author ruoyi
 * @date 2021-10-30
 */
public class TUploadList
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** ftp文件路径 */
    @Excel(name = "ftp文件路径")
    private String ftpUrl;

    /** http文件路径 */
    @Excel(name = "http文件路径")
    private String httpUrl;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String size;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /** 云台Id */
    @Excel(name = "云台Id")
    private Long yuntaiId;

    /** 新增时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "新增时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFtpUrl(String ftpUrl) 
    {
        this.ftpUrl = ftpUrl;
    }

    public String getFtpUrl() 
    {
        return ftpUrl;
    }
    public void setHttpUrl(String httpUrl) 
    {
        this.httpUrl = httpUrl;
    }

    public String getHttpUrl() 
    {
        return httpUrl;
    }
    public void setSize(String size) 
    {
        this.size = size;
    }

    public String getSize() 
    {
        return size;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }

    public Long getYuntaiId() {
        return yuntaiId;
    }

    public void setYuntaiId(Long yuntaiId) {
        this.yuntaiId = yuntaiId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileName", getFileName())
            .append("ftpUrl", getFtpUrl())
            .append("httpUrl", getHttpUrl())
            .append("size", getSize())
            .append("createTime", getCreateTime())
            .append("createUser", getCreateUser())
            .append("yuntaiid", getYuntaiId())
            .toString();
    }
}
