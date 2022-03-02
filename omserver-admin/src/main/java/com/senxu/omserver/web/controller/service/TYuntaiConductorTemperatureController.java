package com.senxu.omserver.web.controller.service;

import java.util.ArrayList;
import java.util.List;

import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
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
import com.senxu.omserver.service.domain.TYuntaiConductorTemperature;
import com.senxu.omserver.service.service.ITYuntaiConductorTemperatureService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.Cmd26HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.cmd.response.WireInfoF;
import senxu.com.cmd.response.WireInfoS;

/**
 * 导线温度采样Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/temperature")
public class TYuntaiConductorTemperatureController extends BaseController
{
    @Autowired
    private ITYuntaiConductorTemperatureService tYuntaiConductorTemperatureService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询导线温度采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:temperature:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiConductorTemperature tYuntaiConductorTemperature)
    {
        startPage();
//        List<TYuntaiConductorTemperature> list = tYuntaiConductorTemperatureService.selectTYuntaiConductorTemperatureList(tYuntaiConductorTemperature);
//        return getDataTable(list);

        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiConductorTemperature.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd26H(yuntaiInfo.getCode());
        List<TYuntaiConductorTemperature> list = new ArrayList<>();
        Cmd26HResponse cmd26HResponse = null;
        if(response.isSuccess()) {
            try{
                cmd26HResponse = (Cmd26HResponse) response.getCmdResponse();
                WireInfoF wireInfoF = (WireInfoF) cmd26HResponse.getList().get(0);
                for (int i = 1; i < cmd26HResponse.getList().size(); i++) {
                    WireInfoS wireInfoS = (WireInfoS) cmd26HResponse.getList().get(i);
                    TYuntaiConductorTemperature temperature = new TYuntaiConductorTemperature();
                    temperature.setTime(wireInfoF.getCollectDate());
                    temperature.setTemperature(String.valueOf(wireInfoS.getTemperature()));
                    temperature.setCurrent(String.valueOf(wireInfoS.getCurrent()));
                    temperature.setVoltage(String.valueOf(wireInfoS.getVoltage()));
                    temperature.setState("1");
                    list.add(temperature);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return getDataTable(list);
    }

    /**
     * 导出导线温度采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:temperature:export')")
    @Log(title = "导线温度采样", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiConductorTemperature tYuntaiConductorTemperature)
    {
        List<TYuntaiConductorTemperature> list = tYuntaiConductorTemperatureService.selectTYuntaiConductorTemperatureList(tYuntaiConductorTemperature);
        ExcelUtil<TYuntaiConductorTemperature> util = new ExcelUtil<TYuntaiConductorTemperature>(TYuntaiConductorTemperature.class);
        return util.exportExcel(list, "导线温度采样数据");
    }

    /**
     * 获取导线温度采样详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:temperature:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiConductorTemperatureService.selectTYuntaiConductorTemperatureById(id));
    }

    /**
     * 新增导线温度采样
     */
    @PreAuthorize("@ss.hasPermi('system:temperature:add')")
    @Log(title = "导线温度采样", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiConductorTemperature tYuntaiConductorTemperature)
    {
        return toAjax(tYuntaiConductorTemperatureService.insertTYuntaiConductorTemperature(tYuntaiConductorTemperature));
    }

    /**
     * 修改导线温度采样
     */
    @PreAuthorize("@ss.hasPermi('system:temperature:edit')")
    @Log(title = "导线温度采样", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiConductorTemperature tYuntaiConductorTemperature)
    {
        return toAjax(tYuntaiConductorTemperatureService.updateTYuntaiConductorTemperature(tYuntaiConductorTemperature));
    }

    /**
     * 删除导线温度采样
     */
    @PreAuthorize("@ss.hasPermi('system:temperature:remove')")
    @Log(title = "导线温度采样", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiConductorTemperatureService.deleteTYuntaiConductorTemperatureByIds(ids));
    }
}
