package com.senxu.omserver.web.controller.service;

import java.util.List;

import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
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
import com.senxu.omserver.service.domain.TYuntaiPresetPosition;
import com.senxu.omserver.service.service.ITYuntaiPresetPositionService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_yuntai_preset_position（云台预置位信息）Controller
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Api("云台预置位信息:")
@RestController
@RequestMapping("/service/presetPosition")
public class TYuntaiPresetPositionController extends BaseController
{
    @Autowired
    private ITYuntaiPresetPositionService tYuntaiPresetPositionService;
    @Autowired
    private NWOMServer nwomServer;

    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    /**
     * 查询t_yuntai_preset_position（云台预置位信息）列表
     */
    @ApiOperation("查询云台预置位信息:列表")
    //@PreAuthorize("@ss.hasPermi('service:presetPosition:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiPresetPosition tYuntaiPresetPosition)
    {
        startPage();
        List<TYuntaiPresetPosition> list = tYuntaiPresetPositionService.selectTYuntaiPresetPositionList(tYuntaiPresetPosition);
        return getDataTable(list);
    }

    /**
     * 导出t_yuntai_preset_position（云台预置位信息）列表
     */
    @ApiOperation("导出云台预置位信息:列表")
    //@PreAuthorize("@ss.hasPermi('service:presetPosition:export')")
    @Log(title = "t_yuntai_preset_position（云台预置位信息）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiPresetPosition tYuntaiPresetPosition)
    {
        List<TYuntaiPresetPosition> list = tYuntaiPresetPositionService.selectTYuntaiPresetPositionList(tYuntaiPresetPosition);
        ExcelUtil<TYuntaiPresetPosition> util = new ExcelUtil<TYuntaiPresetPosition>(TYuntaiPresetPosition.class);
        return util.exportExcel(list, "t_yuntai_preset_position（云台预置位信息）数据");
    }

    /**
     * 获取t_yuntai_preset_position（云台预置位信息）详细信息
     */
    @ApiOperation("获取云台预置位信息:详细")
    //@PreAuthorize("@ss.hasPermi('service:presetPosition:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiPresetPositionService.selectTYuntaiPresetPositionById(id));
    }

    /**
     * 新增t_yuntai_preset_position（云台预置位信息）
     */
    @ApiOperation("新增云台预置位信息:")
    //@PreAuthorize("@ss.hasPermi('service:presetPosition:add')")
    @Log(title = "t_yuntai_preset_position（云台预置位信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiPresetPosition tYuntaiPresetPosition)
    {
        return toAjax(tYuntaiPresetPositionService.insertTYuntaiPresetPosition(tYuntaiPresetPosition));
    }

    /**
     * 修改t_yuntai_preset_position（云台预置位信息）
     */
    @ApiOperation("修改云台预置位信息:")
    //@PreAuthorize("@ss.hasPermi('service:presetPosition:edit')")
    @Log(title = "t_yuntai_preset_position（云台预置位信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiPresetPosition tYuntaiPresetPosition)
    {
        TYuntaiInfo  tYuntaiInfo =  tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiPresetPosition.getYuntaiId());
        AjaxResult ajaxResult=toAjax(tYuntaiPresetPositionService.updateTYuntaiPresetPosition(tYuntaiPresetPosition));
        //    "9\t所需设置预置点\t保存当前位置为某预置点\n" +
       // nwomServer.getDeviceBridge().remoteModulation(tYuntaiInfo.getCode(), tYuntaiInfo.getPasword(), 1, 9, 0);
        return ajaxResult;
    }

    /**
     * 删除t_yuntai_preset_position（云台预置位信息）
     */
    @ApiOperation("删除云台预置位信息:")
    //@PreAuthorize("@ss.hasPermi('service:presetPosition:remove')")
    @Log(title = "t_yuntai_preset_position（云台预置位信息）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiPresetPositionService.deleteTYuntaiPresetPositionByIds(ids));
    }
}
