package com.senxu.omserver.web.controller.service;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
import com.senxu.omserver.service.domain.TTaskHistory;
import com.senxu.omserver.service.service.ITTaskHistoryService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_task_history（历史巡检记录）Controller
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/system/history")
public class TTaskHistoryController extends BaseController
{
    @Autowired
    private ITTaskHistoryService tTaskHistoryService;

    /**
     * 查询t_task_history（历史巡检记录）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:history:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTaskHistory tTaskHistory)
    {
        startPage();
        if(tTaskHistory.getEndTime() != null){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(tTaskHistory.getEndTime());
            calendar.add(calendar.DATE,1); //把日期往后增加一天,整数  往后推,负数往前移动
            tTaskHistory.setEndTime(calendar.getTime()); //这个时间就是日期往后推一天的结果
        }
        List<TTaskHistory> list = tTaskHistoryService.selectTTaskHistoryList(tTaskHistory);
        return getDataTable(list);
    }

    /**
     * 导出t_task_history（历史巡检记录）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:history:export')")
    @Log(title = "t_task_history（历史巡检记录）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTaskHistory tTaskHistory)
    {
        List<TTaskHistory> list = tTaskHistoryService.selectTTaskHistoryList(tTaskHistory);
        ExcelUtil<TTaskHistory> util = new ExcelUtil<TTaskHistory>(TTaskHistory.class);
        return util.exportExcel(list, "t_task_history（历史巡检记录）数据");
    }

    /**
     * 获取t_task_history（历史巡检记录）详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:history:query')")
    @GetMapping(value = "/{taskhId}")
    public AjaxResult getInfo(@PathVariable("taskhId") Long taskhId)
    {
        return AjaxResult.success(tTaskHistoryService.selectTTaskHistoryById(taskhId));
    }

    /**
     * 新增t_task_history（历史巡检记录）
     */
    //@PreAuthorize("@ss.hasPermi('system:history:add')")
    @Log(title = "t_task_history（历史巡检记录）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTaskHistory tTaskHistory)
    {
        return toAjax(tTaskHistoryService.insertTTaskHistory(tTaskHistory));
    }

    /**
     * 修改t_task_history（历史巡检记录）
     */
    //@PreAuthorize("@ss.hasPermi('system:history:edit')")
    @Log(title = "t_task_history（历史巡检记录）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTaskHistory tTaskHistory)
    {
        return toAjax(tTaskHistoryService.updateTTaskHistory(tTaskHistory));
    }

    /**
     * 删除t_task_history（历史巡检记录）
     */
    //@PreAuthorize("@ss.hasPermi('system:history:remove')")
    @Log(title = "t_task_history（历史巡检记录）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskhIds}")
    public AjaxResult remove(@PathVariable Long[] taskhIds)
    {
        return toAjax(tTaskHistoryService.deleteTTaskHistoryByIds(taskhIds));
    }
}
