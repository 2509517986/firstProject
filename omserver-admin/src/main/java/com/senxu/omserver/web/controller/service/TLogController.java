package com.senxu.omserver.web.controller.service;

import java.util.List;

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
import com.senxu.omserver.service.domain.TLog;
import com.senxu.omserver.service.service.ITLogService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_log（日志）Controller
 * 
 * @author ruoyi
 * @date 2021-10-10
 */
@RestController
@RequestMapping("/service/log")
public class TLogController extends BaseController
{
    @Autowired
    private ITLogService tLogService;

    /**
     * 查询t_log（日志）列表
     */
    @ApiOperation("日志中心列表，通过传入keyword查询关键字，通过备用字段spare0,spare1 传入查询时间段")
    //@PreAuthorize("@ss.hasPermi('service:log:list')")
    @GetMapping("/list")
    public TableDataInfo list(TLog tLog)
    {
        startPage();
        List<TLog> list = tLogService.selectTLogList(tLog);
        return getDataTable(list);
    }

    /**
     * 导出t_log（日志）列表
     */
    //@PreAuthorize("@ss.hasPermi('service:log:export')")
    @Log(title = "t_log（日志）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TLog tLog)
    {
        List<TLog> list = tLogService.selectTLogList(tLog);
        ExcelUtil<TLog> util = new ExcelUtil<TLog>(TLog.class);
        return util.exportExcel(list, "t_log（日志）数据");
    }

    /**
     * 获取t_log（日志）详细信息
     */
    //@PreAuthorize("@ss.hasPermi('service:log:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tLogService.selectTLogById(id));
    }

    /**
     * 新增t_log（日志）
     */
    //@PreAuthorize("@ss.hasPermi('service:log:add')")
    @Log(title = "t_log（日志）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TLog tLog)
    {
        return toAjax(tLogService.insertTLog(tLog));
    }

    /**
     * 修改t_log（日志）
     */
    //@PreAuthorize("@ss.hasPermi('service:log:edit')")
    @Log(title = "t_log（日志）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TLog tLog)
    {
        return toAjax(tLogService.updateTLog(tLog));
    }

    /**
     * 删除t_log（日志）
     */
    //@PreAuthorize("@ss.hasPermi('service:log:remove')")
    @Log(title = "t_log（日志）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tLogService.deleteTLogByIds(ids));
    }
}
