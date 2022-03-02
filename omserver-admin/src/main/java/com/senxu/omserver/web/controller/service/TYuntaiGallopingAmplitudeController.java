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
import com.senxu.omserver.service.domain.TYuntaiGallopingAmplitude;
import com.senxu.omserver.service.service.ITYuntaiGallopingAmplitudeService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.Cmd29HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.cmd.response.GallopingAmplitudeFreF;
import senxu.com.cmd.response.GallopingAmplitudeFreS;

/**
 * 舞动振幅频率数据Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/amplitude")
public class TYuntaiGallopingAmplitudeController extends BaseController
{
    @Autowired
    private ITYuntaiGallopingAmplitudeService tYuntaiGallopingAmplitudeService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;


    /**
     * 查询舞动振幅频率数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:amplitude:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude)
    {
        startPage();
//        List<TYuntaiGallopingAmplitude> list = tYuntaiGallopingAmplitudeService.selectTYuntaiGallopingAmplitudeList(tYuntaiGallopingAmplitude);
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiGallopingAmplitude.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd29H(yuntaiInfo.getCode());
        List<TYuntaiGallopingAmplitude> list = new ArrayList<>();
        Cmd29HResponse cmd29HResponse = null;
        if(response.isSuccess()) {
            try {
                cmd29HResponse = (Cmd29HResponse) response.getCmdResponse();
                GallopingAmplitudeFreF infoF = (GallopingAmplitudeFreF) cmd29HResponse.getList().get(0);
                for (int i = 1; i < cmd29HResponse.getList().size(); i++) {
                    TYuntaiGallopingAmplitude amplitude = new TYuntaiGallopingAmplitude();
                    GallopingAmplitudeFreS infoS = (GallopingAmplitudeFreS)cmd29HResponse.getList().get(i);
                    amplitude.setTime(infoF.getCollectDate());
                    amplitude.setGallopingFre(String.valueOf(infoS.getGallopingFre()));
                    amplitude.setGallopingAmplitude(String.valueOf(infoS.getGallopingAmplitude()));
                    amplitude.setVerticalGallopingAmplitude(String.valueOf(infoS.getVerticalGallopingAmplitude()));
                    amplitude.setLevelGallopingAmplitude(String.valueOf(infoS.getLevelGallopingAmplitude()));
                    amplitude.setGallopingincAngleOfEllipse(String.valueOf(infoS.getGallopingIncAngleOfEllipse()));
                    amplitude.setVoltage(String.valueOf(infoS.getVoltage()));
                    amplitude.setState("1");
                    list.add(amplitude);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出舞动振幅频率数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:amplitude:export')")
    @Log(title = "舞动振幅频率数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude)
    {
        List<TYuntaiGallopingAmplitude> list = tYuntaiGallopingAmplitudeService.selectTYuntaiGallopingAmplitudeList(tYuntaiGallopingAmplitude);
        ExcelUtil<TYuntaiGallopingAmplitude> util = new ExcelUtil<TYuntaiGallopingAmplitude>(TYuntaiGallopingAmplitude.class);
        return util.exportExcel(list, "舞动振幅频率数据数据");
    }

    /**
     * 获取舞动振幅频率数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:amplitude:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiGallopingAmplitudeService.selectTYuntaiGallopingAmplitudeById(id));
    }

    /**
     * 新增舞动振幅频率数据
     */
    @PreAuthorize("@ss.hasPermi('system:amplitude:add')")
    @Log(title = "舞动振幅频率数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude)
    {
        return toAjax(tYuntaiGallopingAmplitudeService.insertTYuntaiGallopingAmplitude(tYuntaiGallopingAmplitude));
    }

    /**
     * 修改舞动振幅频率数据
     */
    @PreAuthorize("@ss.hasPermi('system:amplitude:edit')")
    @Log(title = "舞动振幅频率数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude)
    {
        return toAjax(tYuntaiGallopingAmplitudeService.updateTYuntaiGallopingAmplitude(tYuntaiGallopingAmplitude));
    }

    /**
     * 删除舞动振幅频率数据
     */
    @PreAuthorize("@ss.hasPermi('system:amplitude:remove')")
    @Log(title = "舞动振幅频率数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiGallopingAmplitudeService.deleteTYuntaiGallopingAmplitudeByIds(ids));
    }
}
