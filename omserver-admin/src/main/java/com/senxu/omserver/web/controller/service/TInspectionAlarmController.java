package com.senxu.omserver.web.controller.service;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.senxu.omserver.service.domain.TInspectionAlarm;
import com.senxu.omserver.service.service.ITInspectionAlarmService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_inspection_alarm（巡检告警记录）Controller
 * 
 * @author panyc
 * @date 2021-06-10
 */
@Api("巡检告警记录")
@RestController
@RequestMapping("/service/inspectionAlarm")
public class TInspectionAlarmController extends BaseController
{
    @Autowired
    private ITInspectionAlarmService tInspectionAlarmService;

    /**
     * 查询t_inspection_alarm（巡检告警记录）列表
     */
    @ApiOperation("获取巡检告警记录列表")
    //@PreAuthorize("@ss.hasPermi('service:inspectionAlarm:list')")
    @GetMapping("/list")
    public TableDataInfo list(TInspectionAlarm tInspectionAlarm)
    {
        startPage();
        List<TInspectionAlarm> list = tInspectionAlarmService.selectTInspectionAlarmList(tInspectionAlarm);
        return getDataTable(list);
    }

    /**
     * 导出t_inspection_alarm（巡检告警记录）列表
     */
    @ApiOperation("导出巡检告警记录列表")
    //@PreAuthorize("@ss.hasPermi('service:inspectionAlarm:export')")
    @Log(title = "t_inspection_alarm（巡检告警记录列表）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TInspectionAlarm tInspectionAlarm)
    {
        List<TInspectionAlarm> list = tInspectionAlarmService.selectTInspectionAlarmList(tInspectionAlarm);
        ExcelUtil<TInspectionAlarm> util = new ExcelUtil<TInspectionAlarm>(TInspectionAlarm.class);
        return util.exportExcel(list, "t_inspection_alarm（巡检告警记录）数据");
    }

    /**
     * 获取t_inspection_alarm（巡检告警记录）详细信息
     */
    @ApiOperation("获取巡检告警记录详细")
    //@PreAuthorize("@ss.hasPermi('service:inspectionAlarm:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tInspectionAlarmService.selectTInspectionAlarmById(id));
    }

    /**
     * 新增t_inspection_alarm（巡检告警记录）
     */
    @ApiOperation("新增巡检告警记录")
    //@PreAuthorize("@ss.hasPermi('service:inspectionAlarm:add')")
    @Log(title = "t_inspection_alarm（巡检告警记录）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TInspectionAlarm tInspectionAlarm)
    {
        return toAjax(tInspectionAlarmService.insertTInspectionAlarm(tInspectionAlarm));
    }

    /**
     * 修改t_inspection_alarm（巡检告警记录）
     */
    @ApiOperation("修改巡检告警记录")
    //@PreAuthorize("@ss.hasPermi('service:inspectionAlarm:edit')")
    @Log(title = "t_inspection_alarm（巡检告警记录）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TInspectionAlarm tInspectionAlarm)
    {
        return toAjax(tInspectionAlarmService.updateTInspectionAlarm(tInspectionAlarm));
    }

    /**
     * 删除t_inspection_alarm（巡检告警记录）
     */
    @ApiOperation("删除巡检告警记录")
    //@PreAuthorize("@ss.hasPermi('service:inspectionAlarm:remove')")
    @Log(title = "t_inspection_alarm（巡检告警记录）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tInspectionAlarmService.deleteTInspectionAlarmByIds(ids));
    }
}
