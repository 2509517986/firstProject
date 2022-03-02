package com.senxu.omserver.web.controller.service;

import java.util.ArrayList;
import java.util.List;

import com.senxu.omserver.service.domain.TTowerVibration;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.service.service.ITYuntaiMountainFireAlarmService;
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
import com.senxu.omserver.service.domain.TYuntaiMountainFireAlarm;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.*;

/**
 * 山火告警Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/alarm")
public class TYuntaiMountainFireAlarmController extends BaseController
{
    @Autowired
    private ITYuntaiMountainFireAlarmService tYuntaiMountainFireAlarmService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询山火告警列表
     */
    @PreAuthorize("@ss.hasPermi('system:alarm:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm)
    {
        startPage();
//        List<TYuntaiMountainFireAlarm> list = tYuntaiMountainFireAlarmService.selectTYuntaiMountainFireAlarmList(tYuntaiMountainFireAlarm);
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiMountainFireAlarm.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd2DH(yuntaiInfo.getCode());
        List<TYuntaiMountainFireAlarm> list = new ArrayList<>();
        Cmd2DHResponse cmd2DHResponse = null;
        if(response.isSuccess()) {
            try {
                cmd2DHResponse = (Cmd2DHResponse) response.getCmdResponse();
                WildfirePoliceInfoF infoF = (WildfirePoliceInfoF) cmd2DHResponse.getList().get(0);
                for (int i = 1; i < cmd2DHResponse.getList().size(); i++) {
                    TYuntaiMountainFireAlarm fireAlarm = new TYuntaiMountainFireAlarm();
                    WildfirePoliceInfoS infoS = (WildfirePoliceInfoS)cmd2DHResponse.getList().get(i);
                    fireAlarm.setTime(infoF.getCollectDate());
                    fireAlarm.setPoliceStatus(String.valueOf(infoS.getPoliceStatus()));
                    fireAlarm.setCurrentPoliceFre(String.valueOf(infoS.getCurrentPoliceFre()));
                    fireAlarm.setState("1");
                    list.add(fireAlarm);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return getDataTable(list);
    }

    /**
     * 导出山火告警列表
     */
    @PreAuthorize("@ss.hasPermi('system:alarm:export')")
    @Log(title = "山火告警", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm)
    {
        List<TYuntaiMountainFireAlarm> list = tYuntaiMountainFireAlarmService.selectTYuntaiMountainFireAlarmList(tYuntaiMountainFireAlarm);
        ExcelUtil<TYuntaiMountainFireAlarm> util = new ExcelUtil<TYuntaiMountainFireAlarm>(TYuntaiMountainFireAlarm.class);
        return util.exportExcel(list, "山火告警数据");
    }

    /**
     * 获取山火告警详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:alarm:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiMountainFireAlarmService.selectTYuntaiMountainFireAlarmById(id));
    }

    /**
     * 新增山火告警
     */
    @PreAuthorize("@ss.hasPermi('system:alarm:add')")
    @Log(title = "山火告警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm)
    {
        return toAjax(tYuntaiMountainFireAlarmService.insertTYuntaiMountainFireAlarm(tYuntaiMountainFireAlarm));
    }

    /**
     * 修改山火告警
     */
    @PreAuthorize("@ss.hasPermi('system:alarm:edit')")
    @Log(title = "山火告警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm)
    {
        return toAjax(tYuntaiMountainFireAlarmService.updateTYuntaiMountainFireAlarm(tYuntaiMountainFireAlarm));
    }

    /**
     * 删除山火告警
     */
    @PreAuthorize("@ss.hasPermi('system:alarm:remove')")
    @Log(title = "山火告警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiMountainFireAlarmService.deleteTYuntaiMountainFireAlarmByIds(ids));
    }
}
