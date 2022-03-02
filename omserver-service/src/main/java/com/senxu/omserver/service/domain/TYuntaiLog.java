package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * t_yuntai_log（云台设备事件）对象 t_yuntai_log
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public class TYuntaiLog extends BaseEntity
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

    /** 云台名称 */
    @Excel(name = "云台名称")
    private String name;

    /** 运行单位 */
    @Excel(name = "运行单位")
    private Long unit;

    /** 高压等级：1 高压 2 超高压 3 特高压 */
    @Excel(name = "高压等级",dictType = "line_voltage")
    private String level;

    /** 线路名称 */
    @Excel(name = "线路名称")
    private String lineName;

    /** 杆塔号 */
    @Excel(name = "杆塔号")
    private String towerName;

    /** 设备IP */
    @Excel(name = "设备IP")
    private String ip;

    /** 设备厂家 */
    @Excel(name = "设备厂家")
    private String factory;

    /** 设备状态：1 在线  2 休眠  -1 离线 */
    @Excel(name = "设备状态",dictType = "state")
    private Long state;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordTime;

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

    /** 线路Id */
    private String lineIds;

    /** 线路Id */
    private String towerIds;

    private String deptIds;

    private TYuntaiInfo yuntaiInfo;


    /** 构造函数 */
    public TYuntaiLog(){
    }

    public TYuntaiLog(String lineIds,String towerIds){
        this.lineIds= lineIds;
        this.towerIds=towerIds;
    }

    public TYuntaiLog(String deptIds){
        this.deptIds=deptIds;
    }

    public TYuntaiLog(String lineIds,String towerIds,String deptIds){
        this.lineIds= lineIds;
        this.towerIds=towerIds;
        this.deptIds=deptIds;
    }

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setUnit(Long unit) 
    {
        this.unit = unit;
    }

    public Long getUnit() 
    {
        return unit;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setLineName(String lineName) 
    {
        this.lineName = lineName;
    }

    public String getLineName() 
    {
        return lineName;
    }
    public void setTowerName(String towerName) 
    {
        this.towerName = towerName;
    }

    public String getTowerName() 
    {
        return towerName;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setFactory(String factory) 
    {
        this.factory = factory;
    }

    public String getFactory() 
    {
        return factory;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
    }
    public void setRecordTime(Date recordTime) 
    {
        this.recordTime = recordTime;
    }

    public Date getRecordTime() 
    {
        return recordTime;
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

    public String getSpare4() { return spare4; }

    public TYuntaiInfo getYuntaiInfo() {
        return yuntaiInfo;
    }

    public void setYuntaiInfo(TYuntaiInfo yuntaiInfo) {
        this.yuntaiInfo = yuntaiInfo;
    }

    public String getLineIds() {
        return lineIds;
    }

    public void setLineIds(String lineIds) {
        this.lineIds = lineIds;
    }

    public String getTowerIds() {
        return towerIds;
    }

    public void setTowerIds(String towerIds) {
        this.towerIds = towerIds;
    }

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("code", getCode())
            .append("name", getName())
            .append("unit", getUnit())
            .append("level", getLevel())
            .append("lineName", getLineName())
            .append("towerName", getTowerName())
            .append("ip", getIp())
            .append("factory", getFactory())
            .append("state", getState())
            .append("recordTime", getRecordTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
