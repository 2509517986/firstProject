package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * t_inspection_arguments（巡检参数配置）对象 t_inspection_arguments
 *
 * @author lvhp
 * @date 2021-10-25
 */
@Data
public class TInspectionArguments{



    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long id;

    /**
     * 巡检组号
     */
    @Excel(name = "巡检组号")
    private Integer groupId;

    /**
     * 通道号
     */
    @Excel(name = "通道号")
    private String chanelNum;

    /**
     * 巡检点序号
     */
    @Excel(name = "巡检点序号")
    private String inspectionTacticsId;

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

    public String getInspectionTacticsId() {
        return inspectionTacticsId;
    }

    public void setInspectionTacticsId(String inspectionTacticsId) {
        this.inspectionTacticsId = inspectionTacticsId;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
