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
import com.senxu.omserver.service.domain.TInspectionYuntai;
import com.senxu.omserver.service.service.ITInspectionYuntaiService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_inspection_yuntai（巡检云台记录）Controller
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Api("巡检云台记录")
@RestController
@RequestMapping("/service/inspectionYuntai")
public class TInspectionYuntaiController extends BaseController
{
    @Autowired
    private ITInspectionYuntaiService tInspectionYuntaiService;

    /**
     * 查询t_inspection_yuntai（巡检云台记录）列表
     */
    @ApiOperation("查询巡检云台记录列表")
    //@PreAuthorize("@ss.hasPermi('service:inspectionYuntai:list')")
    @GetMapping("/list")
    public TableDataInfo list(TInspectionYuntai tInspectionYuntai)
    {
        startPage();
        List<TInspectionYuntai> list = tInspectionYuntaiService.selectTInspectionYuntaiList(tInspectionYuntai);
        return getDataTable(list);
    }

    /**
     * 导出t_inspection_yuntai（巡检云台记录）列表
     */
    @ApiOperation("导出巡检云台记录列表")
    //@PreAuthorize("@ss.hasPermi('service:inspectionYuntai:export')")
    @Log(title = "t_inspection_yuntai（巡检云台记录）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TInspectionYuntai tInspectionYuntai)
    {
        List<TInspectionYuntai> list = tInspectionYuntaiService.selectTInspectionYuntaiList(tInspectionYuntai);
        ExcelUtil<TInspectionYuntai> util = new ExcelUtil<TInspectionYuntai>(TInspectionYuntai.class);
        return util.exportExcel(list, "t_inspection_yuntai（巡检云台记录）数据");
    }

    /**
     * 获取t_inspection_yuntai（巡检云台记录）详细信息
     */
    @ApiOperation("获取巡检云台记录详细信息")
    //@PreAuthorize("@ss.hasPermi('service:inspectionYuntai:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tInspectionYuntaiService.selectTInspectionYuntaiById(id));
    }

    /**
     * 新增t_inspection_yuntai（巡检云台记录）
     */
    @ApiOperation("新增巡检云台记录")
    //@PreAuthorize("@ss.hasPermi('service:inspectionYuntai:add')")
    @Log(title = "t_inspection_yuntai（巡检云台记录）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TInspectionYuntai tInspectionYuntai)
    {
        return toAjax(tInspectionYuntaiService.insertTInspectionYuntai(tInspectionYuntai));
    }

    /**
     * 修改t_inspection_yuntai（巡检云台记录）
     */
    @ApiOperation("修改巡检云台记录")
    //@PreAuthorize("@ss.hasPermi('service:inspectionYuntai:edit')")
    @Log(title = "t_inspection_yuntai（巡检云台记录）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TInspectionYuntai tInspectionYuntai)
    {
        return toAjax(tInspectionYuntaiService.updateTInspectionYuntai(tInspectionYuntai));
    }

    /**
     * 删除t_inspection_yuntai（巡检云台记录）
     */
    @ApiOperation("删除巡检云台记录")
    //@PreAuthorize("@ss.hasPermi('service:inspectionYuntai:remove')")
    @Log(title = "t_inspection_yuntai（巡检云台记录）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tInspectionYuntaiService.deleteTInspectionYuntaiByIds(ids));
    }
}
