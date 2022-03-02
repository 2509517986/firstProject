package com.senxu.omserver.web.controller.service;

import java.util.Date;
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
import com.senxu.omserver.service.domain.TYuntaiVideo;
import com.senxu.omserver.service.service.ITYuntaiVideoService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_yuntai_video（云台短视频）Controller
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Api("云台短视频")
@RestController
@RequestMapping("/service/yuntaiVideo")
public class TYuntaiVideoController extends BaseController
{
    @Autowired
    private ITYuntaiVideoService tYuntaiVideoService;

    /**
     * 查询t_yuntai_video（云台短视频）列表
     */
    @ApiOperation("查询云台短视频列表")
   //@PreAuthorize("@ss.hasPermi('service:yuntaiVideo:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiVideo tYuntaiVideo)
    {
        startPage();
        List<TYuntaiVideo> list = tYuntaiVideoService.selectTYuntaiVideoList(tYuntaiVideo);
        return getDataTable(list);
    }

    /**
     * 导出t_yuntai_video（云台短视频）列表
     */
    @ApiOperation("导出云台短视频列表")
   //@PreAuthorize("@ss.hasPermi('service:yuntaiVideo:export')")
    @Log(title = "t_yuntai_video（云台短视频）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiVideo tYuntaiVideo)
    {
        List<TYuntaiVideo> list = tYuntaiVideoService.selectTYuntaiVideoList(tYuntaiVideo);
        ExcelUtil<TYuntaiVideo> util = new ExcelUtil<TYuntaiVideo>(TYuntaiVideo.class);
        return util.exportExcel(list, "t_yuntai_video（云台短视频）数据");
    }

    /**
     * 获取t_yuntai_video（云台短视频）详细信息
     */
    @ApiOperation("获取云台短视频详细")
   //@PreAuthorize("@ss.hasPermi('service:yuntaiVideo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiVideoService.selectTYuntaiVideoById(id));
    }

    /**
     * 新增t_yuntai_video（云台短视频）
     */
    @ApiOperation("新增云台短视频")
   //@PreAuthorize("@ss.hasPermi('service:yuntaiVideo:add')")
    @Log(title = "t_yuntai_video（云台短视频）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiVideo tYuntaiVideo)
    {
        return toAjax(tYuntaiVideoService.insertTYuntaiVideo(tYuntaiVideo));
    }

    /**
     * 修改t_yuntai_video（云台短视频）
     */
    @ApiOperation("修改云台短视频")
   //@PreAuthorize("@ss.hasPermi('service:yuntaiVideo:edit')")
    @Log(title = "t_yuntai_video（云台短视频）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiVideo tYuntaiVideo)
    {
        return toAjax(tYuntaiVideoService.updateTYuntaiVideo(tYuntaiVideo));
    }

    /**
     * 删除t_yuntai_video（云台短视频）
     */
    @ApiOperation("删除云台短视频")
   //@PreAuthorize("@ss.hasPermi('service:yuntaiVideo:remove')")
    @Log(title = "t_yuntai_video（云台短视频）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiVideoService.deleteTYuntaiVideoByIds(ids));
    }

    /**
     * 获取t_yuntai_video（云台历史视频）详细信息
     */
    @ApiOperation("获取云台历史视频详细")
   //@PreAuthorize("@ss.hasPermi('service:yuntaiVideo:query')")
    @GetMapping(value = "/getVideoList")
    public AjaxResult getVideoList(String presetBit, Date captureTime)
    {
        TYuntaiVideo tYuntaiVideo = new TYuntaiVideo();
        tYuntaiVideo.setPresetBit(presetBit);
        tYuntaiVideo.setCaptureTime(captureTime);
        return AjaxResult.success(tYuntaiVideoService.selectTYuntaiVideoList(tYuntaiVideo));
    }
}
