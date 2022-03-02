package com.senxu.omserver.web.controller.service;

import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
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
import com.senxu.omserver.service.domain.TTaskPicture;
import com.senxu.omserver.service.service.ITTaskPictureService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_task_picture（无人机任务巡检图片）Controller
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/system/taskPicture")
public class TTaskPictureController extends BaseController
{
    @Autowired
    private ITTaskPictureService tTaskPictureService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询t_task_picture（无人机任务巡检图片）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTaskPicture tTaskPicture)
    {
        startPage();
        List<TTaskPicture> list = tTaskPictureService.selectTTaskPictureList(tTaskPicture);
        return getDataTable(list);
    }

    /**
     * 导出t_task_picture（无人机任务巡检图片）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:export')")
    @Log(title = "t_task_picture（无人机任务巡检图片）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTaskPicture tTaskPicture)
    {
        List<TTaskPicture> list = tTaskPictureService.selectTTaskPictureList(tTaskPicture);
        ExcelUtil<TTaskPicture> util = new ExcelUtil<TTaskPicture>(TTaskPicture.class);
        return util.exportExcel(list, "t_task_picture（无人机任务巡检图片）数据");
    }

    /**
     * 获取t_task_picture（无人机任务巡检图片）详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:query')")
    @GetMapping(value = "/{imageId}")
    public AjaxResult getInfo(@PathVariable("imageId") Long imageId)
    {
        return AjaxResult.success(tTaskPictureService.selectTTaskPictureById(imageId));
    }

    /**
     * 新增t_task_picture（无人机任务巡检图片）
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:add')")
    @Log(title = "t_task_picture（无人机任务巡检图片）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTaskPicture tTaskPicture)
    {
        return toAjax(tTaskPictureService.insertTTaskPicture(tTaskPicture));
    }

    /**
     * 修改t_task_picture（无人机任务巡检图片）
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:edit')")
    @Log(title = "t_task_picture（无人机任务巡检图片）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTaskPicture tTaskPicture)
    {
        tTaskPicture.setAlarmTime(DateUtils.getNowDate());
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String userName = loginUser.getUser().getUserName();
        tTaskPicture.setAlarmUser(userName);
        return toAjax(tTaskPictureService.updateTTaskPicture(tTaskPicture));
    }

    /**
     * 删除t_task_picture（无人机任务巡检图片）
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:remove')")
    @Log(title = "t_task_picture（无人机任务巡检图片）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{imageIds}")
    public AjaxResult remove(@PathVariable Long[] imageIds)
    {
        return toAjax(tTaskPictureService.deleteTTaskPictureByIds(imageIds));
    }
}
