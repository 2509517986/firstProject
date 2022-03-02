package com.senxu.omserver.web.controller.service;

import java.util.ArrayList;
import java.util.List;

import com.senxu.omserver.service.domain.TTowerVibration;
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
import com.senxu.omserver.service.domain.TYuntaiEquipmentFailure;
import com.senxu.omserver.service.service.ITYuntaiEquipmentFailureService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.*;

/**
 * 设备故障报警Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/failure")
public class TYuntaiEquipmentFailureController extends BaseController
{
    @Autowired
    private ITYuntaiEquipmentFailureService tYuntaiEquipmentFailureService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询设备故障报警列表
     */
    @PreAuthorize("@ss.hasPermi('system:failure:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiEquipmentFailure tYuntaiEquipmentFailure)
    {
        startPage();
//        List<TYuntaiEquipmentFailure> list = tYuntaiEquipmentFailureService.selectTYuntaiEquipmentFailureList(tYuntaiEquipmentFailure);

        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiEquipmentFailure.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd30H(yuntaiInfo.getCode());
        List<TYuntaiEquipmentFailure> list = new ArrayList<>();
        Cmd30HResponse cmd30HResponse = null;
        if(response.isSuccess()) {
            try {
                cmd30HResponse = (Cmd30HResponse) response.getCmdResponse();
                DeviceFaultInfoF infoF = (DeviceFaultInfoF) cmd30HResponse.getList().get(0);
                for (int i = 1; i < cmd30HResponse.getList().size(); i++) {
                    TYuntaiEquipmentFailure failure = new TYuntaiEquipmentFailure();
                    DeviceFaultInfoS infoS = (DeviceFaultInfoS)cmd30HResponse.getList().get(i);
                    failure.setTime(infoF.getCollectDate());
//                    failure.setFunctioncode(infoS.getFaultCode());
//                    failure.setFaultcode(infoS.getFaultCode());
                    failure.setFunctioncode("03");
                    failure.setFaultcode("03");
                    failure.setState("1");
                    list.add(failure);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return getDataTable(list);
    }

    /**
     * 导出设备故障报警列表
     */
    @PreAuthorize("@ss.hasPermi('system:failure:export')")
    @Log(title = "设备故障报警", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiEquipmentFailure tYuntaiEquipmentFailure)
    {
        List<TYuntaiEquipmentFailure> list = tYuntaiEquipmentFailureService.selectTYuntaiEquipmentFailureList(tYuntaiEquipmentFailure);
        ExcelUtil<TYuntaiEquipmentFailure> util = new ExcelUtil<TYuntaiEquipmentFailure>(TYuntaiEquipmentFailure.class);
        return util.exportExcel(list, "设备故障报警数据");
    }

    /**
     * 获取设备故障报警详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:failure:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiEquipmentFailureService.selectTYuntaiEquipmentFailureById(id));
    }

    /**
     * 新增设备故障报警
     */
    @PreAuthorize("@ss.hasPermi('system:failure:add')")
    @Log(title = "设备故障报警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiEquipmentFailure tYuntaiEquipmentFailure)
    {
        return toAjax(tYuntaiEquipmentFailureService.insertTYuntaiEquipmentFailure(tYuntaiEquipmentFailure));
    }

    /**
     * 修改设备故障报警
     */
    @PreAuthorize("@ss.hasPermi('system:failure:edit')")
    @Log(title = "设备故障报警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiEquipmentFailure tYuntaiEquipmentFailure)
    {
        return toAjax(tYuntaiEquipmentFailureService.updateTYuntaiEquipmentFailure(tYuntaiEquipmentFailure));
    }

    /**
     * 删除设备故障报警
     */
    @PreAuthorize("@ss.hasPermi('system:failure:remove')")
    @Log(title = "设备故障报警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiEquipmentFailureService.deleteTYuntaiEquipmentFailureByIds(ids));
    }
}
