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
import com.senxu.omserver.service.domain.TTowerVibration;
import com.senxu.omserver.service.service.ITTowerVibrationService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.Cmd27HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.cmd.response.TowerVibrationInfoF;
import senxu.com.cmd.response.TowerVibrationInfoS;

/**
 * 杆塔振动采样Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/vibration")
public class TTowerVibrationController extends BaseController
{
    @Autowired
    private ITTowerVibrationService tTowerVibrationService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询杆塔振动采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTowerVibration tTowerVibration)
    {
        startPage();
//        List<TTowerVibration> list = tTowerVibrationService.selectTTowerVibrationList(tTowerVibration);
//        return getDataTable(list);

        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tTowerVibration.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd27H(yuntaiInfo.getCode());
        List<TTowerVibration> list = new ArrayList<>();
        Cmd27HResponse cmd27HResponse = null;
        if(response.isSuccess()) {
            try {
                cmd27HResponse = (Cmd27HResponse) response.getCmdResponse();
                TowerVibrationInfoF infoF = (TowerVibrationInfoF) cmd27HResponse.getList().get(0);
                for (int i = 1; i < cmd27HResponse.getList().size(); i++) {
                    TTowerVibration vibration = new TTowerVibration();
                    TowerVibrationInfoS infoS = (TowerVibrationInfoS)cmd27HResponse.getList().get(i);
                    vibration.setTime(infoF.getCollectDate());
                    vibration.setVibrationFre(String.valueOf(infoS.getVibrationFre()));
                    vibration.setAmplitude(String.valueOf(infoS.getAmplitude()));
                    vibration.setState("1");
                    vibration.setTowerId(String.valueOf(yuntaiInfo.getTowerId()));
                    list.add(vibration);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出杆塔振动采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:export')")
    @Log(title = "杆塔振动采样", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTowerVibration tTowerVibration)
    {
        List<TTowerVibration> list = tTowerVibrationService.selectTTowerVibrationList(tTowerVibration);
        ExcelUtil<TTowerVibration> util = new ExcelUtil<TTowerVibration>(TTowerVibration.class);
        return util.exportExcel(list, "杆塔振动采样数据");
    }

    /**
     * 获取杆塔振动采样详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tTowerVibrationService.selectTTowerVibrationById(id));
    }

    /**
     * 新增杆塔振动采样
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:add')")
    @Log(title = "杆塔振动采样", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTowerVibration tTowerVibration)
    {
        return toAjax(tTowerVibrationService.insertTTowerVibration(tTowerVibration));
    }

    /**
     * 修改杆塔振动采样
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:edit')")
    @Log(title = "杆塔振动采样", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTowerVibration tTowerVibration)
    {
        return toAjax(tTowerVibrationService.updateTTowerVibration(tTowerVibration));
    }

    /**
     * 删除杆塔振动采样
     */
    @PreAuthorize("@ss.hasPermi('system:vibration:remove')")
    @Log(title = "杆塔振动采样", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tTowerVibrationService.deleteTTowerVibrationByIds(ids));
    }
}
