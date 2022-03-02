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
import com.senxu.omserver.service.domain.TYuntaiConfigStrategy;
import com.senxu.omserver.service.service.ITYuntaiConfigStrategyService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_yuntai_config_strategy（云台配置策略）Controller
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Api("云台配置策略")
@RestController
@RequestMapping("/service/configStrategy")
public class TYuntaiConfigStrategyController extends BaseController
{
    @Autowired
    private ITYuntaiConfigStrategyService tYuntaiConfigStrategyService;

    /**
     * 查询t_yuntai_config_strategy（云台配置策略）列表
     */
    @ApiOperation("查询云台配置策略列表")
    //@PreAuthorize("@ss.hasPermi('service:configStrategy:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiConfigStrategy tYuntaiConfigStrategy)
    {
        startPage();
        List<TYuntaiConfigStrategy> list = tYuntaiConfigStrategyService.selectTYuntaiConfigStrategyList(tYuntaiConfigStrategy);
        return getDataTable(list);
    }

    /**
     * 导出t_yuntai_config_strategy（云台配置策略）列表
     */
    @ApiOperation("导出云台配置策略列表")
    //@PreAuthorize("@ss.hasPermi('service:configStrategy:export')")
    @Log(title = "t_yuntai_config_strategy（云台配置策略）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiConfigStrategy tYuntaiConfigStrategy)
    {
        List<TYuntaiConfigStrategy> list = tYuntaiConfigStrategyService.selectTYuntaiConfigStrategyList(tYuntaiConfigStrategy);
        ExcelUtil<TYuntaiConfigStrategy> util = new ExcelUtil<TYuntaiConfigStrategy>(TYuntaiConfigStrategy.class);
        return util.exportExcel(list, "t_yuntai_config_strategy（云台配置策略）数据");
    }

    /**
     * 获取t_yuntai_config_strategy（云台配置策略）详细信息
     */
    @ApiOperation("获取云台配置策略详细")
    //@PreAuthorize("@ss.hasPermi('service:configStrategy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiConfigStrategyService.selectTYuntaiConfigStrategyById(id));
    }

    /**
     * 新增t_yuntai_config_strategy（云台配置策略）
     */
    @ApiOperation("新增云台配置策略信息")
    //@PreAuthorize("@ss.hasPermi('service:configStrategy:add')")
    @Log(title = "t_yuntai_config_strategy（云台配置策略）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiConfigStrategy tYuntaiConfigStrategy)
    {
        return toAjax(tYuntaiConfigStrategyService.insertTYuntaiConfigStrategy(tYuntaiConfigStrategy));
    }

    /**
     * 修改t_yuntai_config_strategy（云台配置策略）
     */
    @ApiOperation("修改云台配置策略")
    //@PreAuthorize("@ss.hasPermi('service:configStrategy:edit')")
    @Log(title = "t_yuntai_config_strategy（云台配置策略）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiConfigStrategy tYuntaiConfigStrategy)
    {
        return toAjax(tYuntaiConfigStrategyService.updateTYuntaiConfigStrategy(tYuntaiConfigStrategy));
    }

    /**
     * 删除t_yuntai_config_strategy（云台配置策略）
     */
    @ApiOperation("删除云台配置策略")
    //@PreAuthorize("@ss.hasPermi('service:configStrategy:remove')")
    @Log(title = "t_yuntai_config_strategy（云台配置策略）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiConfigStrategyService.deleteTYuntaiConfigStrategyByIds(ids));
    }
}
