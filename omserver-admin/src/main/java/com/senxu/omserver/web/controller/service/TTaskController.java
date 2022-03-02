package com.senxu.omserver.web.controller.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.senxu.omserver.service.domain.TRoute;
import com.senxu.omserver.service.service.ITRouteService;
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
import com.senxu.omserver.service.domain.TTask;
import com.senxu.omserver.service.service.ITTaskService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_task（任务信息）Controller
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/system/task")
public class TTaskController extends BaseController
{
    @Autowired
    private ITTaskService tTaskService;
    @Autowired
    private ITRouteService tRouteService;

    /**
     * 查询t_task（任务信息）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTask tTask)
    {
        startPage();
        List<TTask> list = tTaskService.selectTTaskList(tTask);
        for(TTask task : list){
            List<TRoute> routeList = tRouteService.selectTRouteList(new TRoute(task.getTaskId()));
            task.setRouteList(routeList);
        }
        return getDataTable(list);
    }

    /**
     * 导出t_task（任务信息）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:task:export')")
    @Log(title = "t_task（任务信息）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTask tTask)
    {
        List<TTask> list = tTaskService.selectTTaskList(tTask);
        ExcelUtil<TTask> util = new ExcelUtil<TTask>(TTask.class);
        return util.exportExcel(list, "t_task（任务信息）数据");
    }

    /**
     * 获取t_task（任务信息）详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:task:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return AjaxResult.success(tTaskService.selectTTaskById(taskId));
    }

    /**
     * 新增t_task（任务信息）
     */
    //@PreAuthorize("@ss.hasPermi('system:task:add')")
    @Log(title = "t_task（任务信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTask tTask)
    {
        return toAjax(tTaskService.insertTTask(tTask));
    }

    /**
     * 修改t_task（任务信息）
     */
    //@PreAuthorize("@ss.hasPermi('system:task:edit')")
    @Log(title = "t_task（任务信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTask tTask)
    {
        return toAjax(tTaskService.updateTTask(tTask));
    }

    /**
     * 删除t_task（任务信息）
     */
    //@PreAuthorize("@ss.hasPermi('system:task:remove')")
    @Log(title = "t_task（任务信息）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(tTaskService.deleteTTaskByIds(taskIds));
    }

}
