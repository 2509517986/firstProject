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
import com.senxu.omserver.service.domain.TInspectionPicture;
import com.senxu.omserver.service.service.ITInspectionPictureService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_inspection_picture（巡检图片记录）Controller
 * 
 * @author ruoyi
 * @date 2021-09-14
 */
@RestController
@RequestMapping("/system/picture")
public class TInspectionPictureController extends BaseController
{
    @Autowired
    private ITInspectionPictureService tInspectionPictureService;

    /**
     * 查询t_inspection_picture（巡检图片记录）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:list')")
    @GetMapping("/list")
    public TableDataInfo list(TInspectionPicture tInspectionPicture)
    {
        startPage();
        List<TInspectionPicture> list = tInspectionPictureService.selectTInspectionPictureList(tInspectionPicture);
        return getDataTable(list);
    }

    /**
     * 导出t_inspection_picture（巡检图片记录）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:export')")
    @Log(title = "t_inspection_picture（巡检图片记录）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TInspectionPicture tInspectionPicture)
    {
        List<TInspectionPicture> list = tInspectionPictureService.selectTInspectionPictureList(tInspectionPicture);
        ExcelUtil<TInspectionPicture> util = new ExcelUtil<TInspectionPicture>(TInspectionPicture.class);
        return util.exportExcel(list, "t_inspection_picture（巡检图片记录）数据");
    }

    /**
     * 获取t_inspection_picture（巡检图片记录）详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tInspectionPictureService.selectTInspectionPictureById(id));
    }

    /**
     * 新增t_inspection_picture（巡检图片记录）
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:add')")
    @Log(title = "t_inspection_picture（巡检图片记录）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TInspectionPicture tInspectionPicture)
    {
        return toAjax(tInspectionPictureService.insertTInspectionPicture(tInspectionPicture));
    }

    /**
     * 修改t_inspection_picture（巡检图片记录）
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:edit')")
    @Log(title = "t_inspection_picture（巡检图片记录）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TInspectionPicture tInspectionPicture)
    {
        return toAjax(tInspectionPictureService.updateTInspectionPicture(tInspectionPicture));
    }

    /**
     * 删除t_inspection_picture（巡检图片记录）
     */
    //@PreAuthorize("@ss.hasPermi('system:picture:remove')")
    @Log(title = "t_inspection_picture（巡检图片记录）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tInspectionPictureService.deleteTInspectionPictureByIds(ids));
    }
}
