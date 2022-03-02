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
import com.senxu.omserver.service.domain.TAirportBattery;
import com.senxu.omserver.service.service.ITAirportBatteryService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_airport_battery（机巢电池）Controller
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/system/battery")
public class TAirportBatteryController extends BaseController
{
    @Autowired
    private ITAirportBatteryService tAirportBatteryService;

    /**
     * 查询t_airport_battery（机巢电池）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:battery:list')")
    @GetMapping("/list")
    public TableDataInfo list(TAirportBattery tAirportBattery)
    {
        startPage();
        List<TAirportBattery> list = tAirportBatteryService.selectTAirportBatteryList(tAirportBattery);
        return getDataTable(list);
    }

    /**
     * 导出t_airport_battery（机巢电池）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:battery:export')")
    @Log(title = "t_airport_battery（机巢电池）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TAirportBattery tAirportBattery)
    {
        List<TAirportBattery> list = tAirportBatteryService.selectTAirportBatteryList(tAirportBattery);
        ExcelUtil<TAirportBattery> util = new ExcelUtil<TAirportBattery>(TAirportBattery.class);
        return util.exportExcel(list, "t_airport_battery（机巢电池）数据");
    }

    /**
     * 获取t_airport_battery（机巢电池）详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:battery:query')")
    @GetMapping(value = "/{batteryId}")
    public AjaxResult getInfo(@PathVariable("batteryId") Long batteryId)
    {
        return AjaxResult.success(tAirportBatteryService.selectTAirportBatteryById(batteryId));
    }

    /**
     * 新增t_airport_battery（机巢电池）
     */
    //@PreAuthorize("@ss.hasPermi('system:battery:add')")
    @Log(title = "t_airport_battery（机巢电池）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TAirportBattery tAirportBattery)
    {
        return toAjax(tAirportBatteryService.insertTAirportBattery(tAirportBattery));
    }

    /**
     * 修改t_airport_battery（机巢电池）
     */
    //@PreAuthorize("@ss.hasPermi('system:battery:edit')")
    @Log(title = "t_airport_battery（机巢电池）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TAirportBattery tAirportBattery)
    {
        return toAjax(tAirportBatteryService.updateTAirportBattery(tAirportBattery));
    }

    /**
     * 删除t_airport_battery（机巢电池）
     */
    //@PreAuthorize("@ss.hasPermi('system:battery:remove')")
    @Log(title = "t_airport_battery（机巢电池）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{batteryIds}")
    public AjaxResult remove(@PathVariable Long[] batteryIds)
    {
        return toAjax(tAirportBatteryService.deleteTAirportBatteryByIds(batteryIds));
    }
}
