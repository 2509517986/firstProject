package com.senxu.omserver.web.controller.service;

import java.util.ArrayList;
import java.util.List;

import com.senxu.omserver.service.domain.TTowerTilt;
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
import com.senxu.omserver.service.domain.TYuntaiConductorVibration;
import com.senxu.omserver.service.service.ITYuntaiConductorVibrationService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.Cmd2BHResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.cmd.response.WireAeolianVibrationInfoF;
import senxu.com.cmd.response.WireAeolianVibrationInfoS;

/**
 * 导线振动采样Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/conductorVibration")
public class TYuntaiConductorVibrationController extends BaseController
{
    @Autowired
    private ITYuntaiConductorVibrationService tYuntaiConductorVibrationService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询导线振动采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiConductorVibration tYuntaiConductorVibration)
    {
        startPage();
//        List<TYuntaiConductorVibration> list = tYuntaiConductorVibrationService.selectTYuntaiConductorVibrationList(tYuntaiConductorVibration);
        List<TYuntaiConductorVibration> list = new ArrayList<>();
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiConductorVibration.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd2BH(yuntaiInfo.getCode());
        Cmd2BHResponse cmd2BHResponse = null;
        if(response.isSuccess()) {
            try {
                cmd2BHResponse = (Cmd2BHResponse) response.getCmdResponse();
                WireAeolianVibrationInfoF infoF = (WireAeolianVibrationInfoF) cmd2BHResponse.getList().get(0);
                for (int i = 1; i < cmd2BHResponse.getList().size(); i++) {
                    TYuntaiConductorVibration vibration = new TYuntaiConductorVibration();
                    WireAeolianVibrationInfoS infoS = (WireAeolianVibrationInfoS)cmd2BHResponse.getList().get(i);
                    vibration.setTime(infoF.getCollectDate());
                    vibration.setVibrationFre(String.valueOf(infoS.getVibrationFre()));
                    vibration.setAmplitude(String.valueOf(infoS.getAmplitude()));
                    vibration.setVoltage(String.valueOf(infoS.getVoltage()));
                    vibration.setState("1");
                    list.add(vibration);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return getDataTable(list);
    }

    /**
     * 导出导线振动采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:export')")
    @Log(title = "导线振动采样", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiConductorVibration tYuntaiConductorVibration)
    {
        List<TYuntaiConductorVibration> list = tYuntaiConductorVibrationService.selectTYuntaiConductorVibrationList(tYuntaiConductorVibration);
        ExcelUtil<TYuntaiConductorVibration> util = new ExcelUtil<TYuntaiConductorVibration>(TYuntaiConductorVibration.class);
        return util.exportExcel(list, "导线振动采样数据");
    }

    /**
     * 获取导线振动采样详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiConductorVibrationService.selectTYuntaiConductorVibrationById(id));
    }

    /**
     * 新增导线振动采样
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:add')")
    @Log(title = "导线振动采样", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiConductorVibration tYuntaiConductorVibration)
    {
        return toAjax(tYuntaiConductorVibrationService.insertTYuntaiConductorVibration(tYuntaiConductorVibration));
    }

    /**
     * 修改导线振动采样
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:edit')")
    @Log(title = "导线振动采样", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiConductorVibration tYuntaiConductorVibration)
    {
        return toAjax(tYuntaiConductorVibrationService.updateTYuntaiConductorVibration(tYuntaiConductorVibration));
    }

    /**
     * 删除导线振动采样
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:remove')")
    @Log(title = "导线振动采样", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiConductorVibrationService.deleteTYuntaiConductorVibrationByIds(ids));
    }
}
