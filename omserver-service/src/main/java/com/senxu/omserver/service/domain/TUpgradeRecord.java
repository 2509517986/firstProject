package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【设备升级记录】对象 t_upgrade_record
 * 
 * @author ruoyi
 * @date 2021-10-30
 */
public class TUpgradeRecord
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String code;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 设备进度 */
    @Excel(name = "设备进度")
    private String progress;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String state;

    /** 升级结果 */
    @Excel(name = "升级结果")
    private String result;

    /** 升级结果描述 */
    @Excel(name = "升级结果描述")
    private String description;

    /** 升级方式 */
    @Excel(name = "升级方式")
    private String manner;

    /** 传输协议 */
    @Excel(name = "传输协议")
    private String protocol;

    /** 目的设备 */
    @Excel(name = "目的设备")
    private String purposeEquipment;

    /** 通道号 */
    @Excel(name = "通道号")
    private String chanelNum;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String fileType;

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 升级用户 */
    @Excel(name = "升级用户")
    private String createUser;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 旧版本号 */
    @Excel(name = "旧版本号")
    private String oldVersion;

    /** 新版本号 */
    @Excel(name = "新版本号")
    private String newVersion;

    /** 云台Id */
    @Excel(name = "云台Id")
    private Long yuntaiId;

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
    public void setProgress(String progress) 
    {
        this.progress = progress;
    }

    public String getProgress() 
    {
        return progress;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChanelNum() {
        return chanelNum;
    }

    public void setChanelNum(String chanelNum) {
        this.chanelNum = chanelNum;
    }

    public void setManner(String manner)
    {
        this.manner = manner;
    }

    public String getManner() 
    {
        return manner;
    }
    public void setProtocol(String protocol) 
    {
        this.protocol = protocol;
    }

    public String getProtocol() 
    {
        return protocol;
    }
    public void setPurposeEquipment(String purposeEquipment) 
    {
        this.purposeEquipment = purposeEquipment;
    }

    public String getPurposeEquipment() 
    {
        return purposeEquipment;
    }

    public void setFileType(String fileType) 
    {
        this.fileType = fileType;
    }

    public String getFileType() 
    {
        return fileType;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setBeginTime(Date beginTime) 
    {
        this.beginTime = beginTime;
    }

    public Date getBeginTime() 
    {
        return beginTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setOldVersion(String oldVersion) 
    {
        this.oldVersion = oldVersion;
    }

    public String getOldVersion() 
    {
        return oldVersion;
    }
    public void setNewVersion(String newVersion) 
    {
        this.newVersion = newVersion;
    }

    public String getNewVersion() 
    {
        return newVersion;
    }

    public Long getYuntaiId() {
        return yuntaiId;
    }

    public void setYuntaiId(Long yuntaiId) {
        this.yuntaiId = yuntaiId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("progress", getProgress())
            .append("state", getState())
            .append("result", getResult())
            .append("describe", getDescription())
            .append("manner", getManner())
            .append("protocol", getProtocol())
            .append("purposeEquipment", getPurposeEquipment())
            .append("chanelnum", getChanelNum())
            .append("fileType", getFileType())
            .append("fileName", getFileName())
            .append("createUser", getCreateUser())
            .append("beginTime", getBeginTime())
            .append("endTime", getEndTime())
            .append("oldVersion", getOldVersion())
            .append("newVersion", getNewVersion())
            .append("yuntaiId", getYuntaiId())
            .toString();
    }
}
