package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;

import java.util.Date;
/**
 * t_yuntai_cruise（巡航表）对象 t_yuntai_cruise
 *
 * @author lvhp
 * @date 2021-10-25
 */
public class TYuntaiCruise {

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
    private Integer chanelNum;

    /**
     * 巡航组号
     */
    @Excel(name = "巡航组号")
    private Integer cruiseGroupNum;

    /**
     * 预置位号
     */
    @Excel(name = "预置位号")
    private Integer presetNum;

    /**
     * 停留时间
     */
    @Excel(name = "停留时间")
    private int residenceTime;

    /**
     * 速率
     */
    @Excel(name = "速率")
    private Integer speed;

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

    public Integer getChanelNum() {
        return chanelNum;
    }

    public void setChanelNum(Integer chanelNum) {
        this.chanelNum = chanelNum;
    }

    public Integer getCruiseGroupNum() {
        return cruiseGroupNum;
    }

    public void setCruiseGroupNum(Integer cruiseGroupNum) {
        this.cruiseGroupNum = cruiseGroupNum;
    }

    public Integer getPresetNum() {
        return presetNum;
    }

    public void setPresetNum(Integer presetNum) {
        this.presetNum = presetNum;
    }

    public int getResidenceTime() {
        return residenceTime;
    }

    public void setResidenceTime(int residenceTime) {
        this.residenceTime = residenceTime;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
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

