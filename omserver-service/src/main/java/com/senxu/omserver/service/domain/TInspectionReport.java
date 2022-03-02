package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * t_inspection_report（巡检报告）对象 t_inspection_report
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public class TInspectionReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 巡检人 */
    @Excel(name = "巡检人")
    private String inspector;

    /** 巡检计划 */
    @Excel(name = "巡检计划")
    private String inspectionPlan;

    /** 安装点分类：跨越铁路，跨域高速,跨域通航河流,跨域公路,防风防汛点,山火易发区,树障隐患区 */
    //@Excel(name = "安装点分类",dictType = "install_type")
    private String installType;


    /** 安装点分类(隐患类型)：跨越高速，跨越通航河流 */
//    @Excel(name = "安装点分类(隐患类型)：跨越高速，跨越通航河流")
    private String hiddenDanger;

    public String getHiddenDanger() {
        return hiddenDanger;
    }

    public void setHiddenDanger(String hiddenDanger) {
        this.hiddenDanger = hiddenDanger;
    }

    /** 场景点分类：导线，地线，玻璃绝缘子，陶瓷绝缘子，防震锤，金具 */
    //@Excel(name = "场景点分类",dictType = "scene_type")
    private String sceneType;

    /** 巡检时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "巡检时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionTime;

    /** 巡检结论 */
    @Excel(name = "巡检结论")
    private String inspectionResult;

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

    /** 查询关键字 */
    private String keyword;

    /** 线路Id */
    private String lineIds;

    /** 离线总量 */
    @Excel(name = "离线总量")
    private String offline;

    /** 离线率 */
    @Excel(name = "离线率")
    private String offlineRate;

    /** 在线总量 */
    @Excel(name = "在线总量")
    private String online;

    /** 在线率 */
    @Excel(name = "在线率")
    private String onlineRate;

    /** 设备总量 */
    @Excel(name = "设备总量")
    private String total;

    public TInspectionReport(){

    }

    public TInspectionReport(String lineIds){
        this.lineIds= lineIds;
    }

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInspector(String inspector) 
    {
        this.inspector = inspector;
    }

    public String getInspector() 
    {
        return inspector;
    }
    public void setInspectionPlan(String inspectionPlan) 
    {
        this.inspectionPlan = inspectionPlan;
    }

    public String getInspectionPlan() 
    {
        return inspectionPlan;
    }
    public void setInstallType(String installType)
    {
        this.installType = installType;
    }

    public String getInstallType()
    {
        return installType;
    }
    public void setSceneType(String sceneType) 
    {
        this.sceneType = sceneType;
    }

    public String getSceneType() 
    {
        return sceneType;
    }
    public void setInspectionTime(Date inspectionTime) 
    {
        this.inspectionTime = inspectionTime;
    }

    public Date getInspectionTime() 
    {
        return inspectionTime;
    }
    public void setInspectionResult(String inspectionResult) 
    {
        this.inspectionResult = inspectionResult;
    }

    public String getInspectionResult() 
    {
        return inspectionResult;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLineIds() {
        return lineIds;
    }

    public void setLineIds(String lineIds) {
        this.lineIds = lineIds;
    }


    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getOfflineRate() {
        return offlineRate;
    }

    public void setOfflineRate(String offlineRate) {
        this.offlineRate = offlineRate;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getOnlineRate() {
        return onlineRate;
    }

    public void setOnlineRate(String onlineRate) {
        this.onlineRate = onlineRate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "TInspectionReport{" +
                "id=" + id +
                ", inspector='" + inspector + '\'' +
                ", inspectionPlan='" + inspectionPlan + '\'' +
                ", installType='" + installType + '\'' +
                ", hiddenDanger='" + hiddenDanger + '\'' +
                ", sceneType='" + sceneType + '\'' +
                ", inspectionTime=" + inspectionTime +
                ", inspectionResult='" + inspectionResult + '\'' +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", spare0='" + spare0 + '\'' +
                ", spare1='" + spare1 + '\'' +
                ", spare2='" + spare2 + '\'' +
                ", spare3='" + spare3 + '\'' +
                ", spare4='" + spare4 + '\'' +
                ", keyword='" + keyword + '\'' +
                ", lineIds='" + lineIds + '\'' +
                ", offline='" + offline + '\'' +
                ", offlineRate='" + offlineRate + '\'' +
                ", online='" + online + '\'' +
                ", onlineRate='" + onlineRate + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
