package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_task_report（无人机任务巡检报告）对象 t_task_report
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public class TTaskReport
{
    private static final long serialVersionUID = 1L;

    /** 报告ID */
    private Long reportId;

    /** 历史巡检记录ID */
    @Excel(name = "历史巡检记录ID")
    private Long taskhId;

    /** 任务编号(多翼的ID) */
    @Excel(name = "任务编号(多翼的ID)")
    private String taskCode;

    /** 航线编号(多翼的ID) */
    @Excel(name = "航线编号(多翼的ID)")
    private String routeCode;

    /** 报告生成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报告生成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportTime;

    /** 巡检区域 */
    @Excel(name = "巡检区域")
    private String area;

    /** 巡检计划 */
    @Excel(name = "巡检计划")
    private String plan;

    /** 巡检时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "巡检时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionTime;

    /** 巡检进度 */
    @Excel(name = "巡检进度")
    private String progress;

    /** 巡检班组 */
    @Excel(name = "巡检班组")
    private String dept;

    /** 拍摄照片总数 */
    @Excel(name = "拍摄照片总数")
    private Long pictureCount;

    /** 缺陷照片总数 */
    @Excel(name = "缺陷照片总数")
    private Long defectCount;

    /** 可见光照片数 */
    @Excel(name = "可见光照片数")
    private Long visibleLightCount;

    /** 可见光缺陷数 */
    @Excel(name = "可见光缺陷数")
    private Long visibleLightDefect;

    /** 红外照片数 */
    @Excel(name = "红外照片数")
    private Long irCount;

    /** 危急缺陷数 */
    @Excel(name = "危急缺陷数")
    private Long criticalDefect;

    /** 红外缺陷数 */
    @Excel(name = "红外缺陷数")
    private Long irDefect;

    /** 各缺陷类型 */
    @Excel(name = "各缺陷类型")
    private Long ortherDefect;

    /** 缺陷总数 */
    @Excel(name = "缺陷总数")
    private Long totalDefect;

    /** 一般缺陷数 */
    @Excel(name = "一般缺陷数")
    private Long generalDefect;

    /** 严重缺陷数 */
    @Excel(name = "严重缺陷数")
    private Long graveDefect;

    /** 录入人 */
    @Excel(name = "录入人")
    private String inputUser;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inputTime;

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

    public void setReportId(Long reportId) 
    {
        this.reportId = reportId;
    }

    public Long getReportId() 
    {
        return reportId;
    }
    public void setTaskhId(Long taskhId) 
    {
        this.taskhId = taskhId;
    }

    public Long getTaskhId() 
    {
        return taskhId;
    }
    public void setTaskCode(String taskCode) 
    {
        this.taskCode = taskCode;
    }

    public String getTaskCode() 
    {
        return taskCode;
    }
    public void setRouteCode(String routeCode) 
    {
        this.routeCode = routeCode;
    }

    public String getRouteCode() 
    {
        return routeCode;
    }
    public void setReportTime(Date reportTime) 
    {
        this.reportTime = reportTime;
    }

    public Date getReportTime() 
    {
        return reportTime;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setPlan(String plan) 
    {
        this.plan = plan;
    }

    public String getPlan() 
    {
        return plan;
    }
    public void setInspectionTime(Date inspectionTime) 
    {
        this.inspectionTime = inspectionTime;
    }

    public Date getInspectionTime() 
    {
        return inspectionTime;
    }
    public void setProgress(String progress) 
    {
        this.progress = progress;
    }

    public String getProgress() 
    {
        return progress;
    }
    public void setDept(String dept) 
    {
        this.dept = dept;
    }

    public String getDept() 
    {
        return dept;
    }
    public void setPictureCount(Long pictureCount) 
    {
        this.pictureCount = pictureCount;
    }

    public Long getPictureCount() 
    {
        return pictureCount;
    }
    public void setDefectCount(Long defectCount) 
    {
        this.defectCount = defectCount;
    }

    public Long getDefectCount() 
    {
        return defectCount;
    }
    public void setVisibleLightCount(Long visibleLightCount) 
    {
        this.visibleLightCount = visibleLightCount;
    }

    public Long getVisibleLightCount() 
    {
        return visibleLightCount;
    }
    public void setVisibleLightDefect(Long visibleLightDefect) 
    {
        this.visibleLightDefect = visibleLightDefect;
    }

    public Long getVisibleLightDefect() 
    {
        return visibleLightDefect;
    }
    public void setIrCount(Long irCount) 
    {
        this.irCount = irCount;
    }

    public Long getIrCount() 
    {
        return irCount;
    }
    public void setCriticalDefect(Long criticalDefect) 
    {
        this.criticalDefect = criticalDefect;
    }

    public Long getCriticalDefect() 
    {
        return criticalDefect;
    }
    public void setIrDefect(Long irDefect) 
    {
        this.irDefect = irDefect;
    }

    public Long getIrDefect() 
    {
        return irDefect;
    }
    public void setOrtherDefect(Long ortherDefect) 
    {
        this.ortherDefect = ortherDefect;
    }

    public Long getOrtherDefect() 
    {
        return ortherDefect;
    }
    public void setTotalDefect(Long totalDefect) 
    {
        this.totalDefect = totalDefect;
    }

    public Long getTotalDefect() 
    {
        return totalDefect;
    }
    public void setGeneralDefect(Long generalDefect) 
    {
        this.generalDefect = generalDefect;
    }

    public Long getGeneralDefect() 
    {
        return generalDefect;
    }
    public void setGraveDefect(Long graveDefect) 
    {
        this.graveDefect = graveDefect;
    }

    public Long getGraveDefect() 
    {
        return graveDefect;
    }
    public void setInputUser(String inputUser) 
    {
        this.inputUser = inputUser;
    }

    public String getInputUser() 
    {
        return inputUser;
    }
    public void setInputTime(Date inputTime) 
    {
        this.inputTime = inputTime;
    }

    public Date getInputTime() 
    {
        return inputTime;
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
            .append("reportId", getReportId())
            .append("taskhId", getTaskhId())
            .append("taskCode", getTaskCode())
            .append("routeCode", getRouteCode())
            .append("reportTime", getReportTime())
            .append("area", getArea())
            .append("plan", getPlan())
            .append("inspectionTime", getInspectionTime())
            .append("progress", getProgress())
            .append("dept", getDept())
            .append("pictureCount", getPictureCount())
            .append("defectCount", getDefectCount())
            .append("visibleLightCount", getVisibleLightCount())
            .append("visibleLightDefect", getVisibleLightDefect())
            .append("irCount", getIrCount())
            .append("criticalDefect", getCriticalDefect())
            .append("irDefect", getIrDefect())
            .append("ortherDefect", getOrtherDefect())
            .append("totalDefect", getTotalDefect())
            .append("generalDefect", getGeneralDefect())
            .append("graveDefect", getGraveDefect())
            .append("inputUser", getInputUser())
            .append("inputTime", getInputTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
