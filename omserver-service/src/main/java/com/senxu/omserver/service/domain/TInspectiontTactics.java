package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.Map;

/**
 * t_inspection_tactics（巡检策略配置）对象 t_inspection_tactics
 *
 * @author lvhp
 * @date 2021-10-25
 */
public class TInspectiontTactics {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long id;

    /**
     * 通道号
     */
    @Excel(name = "通道号")
    private String chanelNum;

    /**
     * 巡检组号码
     */
    @Excel(name = "巡检组号码")
    private String inspectionGroupNum;

    /**
     * 使能标志
     */
    @Excel(name = "使能标志")
    private String enabled;

    /**
     * 巡检次数
     */
    @Excel(name = "巡检次数")
    private int inspectionTime;

    /**
     * 开始时间
     */
    @Excel(name = "开始时间")
    private String beginTime;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createUser;

    /** 新增时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "新增时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 设备编号
     */
    @Excel(name = "设备编号")
    private String code;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChanelNum() {
        return chanelNum;
    }

    public void setChanelNum(String chanelNum) {
        this.chanelNum = chanelNum;
    }

    public String getInspectionGroupNum() {
        return inspectionGroupNum;
    }

    public void setInspectionGroupNum(String inspectionGroupNum) {
        this.inspectionGroupNum = inspectionGroupNum;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public int getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(int inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
