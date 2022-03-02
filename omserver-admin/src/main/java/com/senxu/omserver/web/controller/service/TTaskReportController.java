package com.senxu.omserver.web.controller.service;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.service.domain.TTaskReport;
import com.senxu.omserver.service.service.ITTaskReportService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_task_report（无人机任务巡检报告）Controller
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/system/report")
public class TTaskReportController extends BaseController
{
    @Autowired
    private ITTaskReportService tTaskReportService;

    /**
     * 查询t_task_report（无人机任务巡检报告）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTaskReport tTaskReport)
    {
        startPage();
        List<TTaskReport> list = tTaskReportService.selectTTaskReportList(tTaskReport);
        return getDataTable(list);
    }

    /**
     * 导出t_task_report（无人机任务巡检报告）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:report:export')")
    @Log(title = "t_task_report（无人机任务巡检报告）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTaskReport tTaskReport)
    {
        List<TTaskReport> list = tTaskReportService.selectTTaskReportList(tTaskReport);
        ExcelUtil<TTaskReport> util = new ExcelUtil<TTaskReport>(TTaskReport.class);
        return util.exportExcel(list, "t_task_report（无人机任务巡检报告）数据");
    }

    /**
     * 获取t_task_report（无人机任务巡检报告）详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:report:query')")
    @GetMapping(value = "/{reportId}")
    public AjaxResult getInfo(@PathVariable("reportId") Long reportId)
    {
        return AjaxResult.success(tTaskReportService.selectTTaskReportById(reportId));
    }

    /**
     * 新增t_task_report（无人机任务巡检报告）
     */
    //@PreAuthorize("@ss.hasPermi('system:report:add')")
    @Log(title = "t_task_report（无人机任务巡检报告）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTaskReport tTaskReport)
    {
        return toAjax(tTaskReportService.insertTTaskReport(tTaskReport));
    }

    /**
     * 修改t_task_report（无人机任务巡检报告）
     */
    //@PreAuthorize("@ss.hasPermi('system:report:edit')")
    @Log(title = "t_task_report（无人机任务巡检报告）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTaskReport tTaskReport)
    {
        return toAjax(tTaskReportService.updateTTaskReport(tTaskReport));
    }

    /**
     * 删除t_task_report（无人机任务巡检报告）
     */
    //@PreAuthorize("@ss.hasPermi('system:report:remove')")
    @Log(title = "t_task_report（无人机任务巡检报告）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reportIds}")
    public AjaxResult remove(@PathVariable Long[] reportIds)
    {
        return toAjax(tTaskReportService.deleteTTaskReportByIds(reportIds));
    }
}
