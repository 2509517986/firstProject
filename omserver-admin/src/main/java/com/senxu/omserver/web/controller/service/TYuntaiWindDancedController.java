package com.senxu.omserver.web.controller.service;

import java.util.ArrayList;
import java.util.List;

import com.senxu.omserver.service.domain.TTowerVibration;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.service.service.ITYuntaiWindDancedService;
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
import com.senxu.omserver.service.domain.TYuntaiWindDanced;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.*;

/**
 * 大风舞动报警Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/danced")
public class TYuntaiWindDancedController extends BaseController
{
    @Autowired
    private ITYuntaiWindDancedService tYuntaiWindDancedService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询大风舞动报警列表
     */
    @PreAuthorize("@ss.hasPermi('system:danced:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiWindDanced tYuntaiWindDanced)
    {
        startPage();
//        List<TYuntaiWindDanced> list = tYuntaiWindDancedService.selectTYuntaiWindDancedList(tYuntaiWindDanced);
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiWindDanced.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd2EH(yuntaiInfo.getCode());
        List<TYuntaiWindDanced> list = new ArrayList<>();
        Cmd2EHResponse cmd2EHResponse = null;
        if(response.isSuccess()) {
            try {
                cmd2EHResponse = (Cmd2EHResponse) response.getCmdResponse();
                GaleWavePoliceInfoF infoF = (GaleWavePoliceInfoF) cmd2EHResponse.getList().get(0);
                for (int i = 1; i < cmd2EHResponse.getList().size(); i++) {
                    TYuntaiWindDanced windDanced = new TYuntaiWindDanced();
                    GaleWavePoliceInfoS infoS = (GaleWavePoliceInfoS)cmd2EHResponse.getList().get(i);
                    windDanced.setTime(infoF.getCollectDate());
                    windDanced.setAvgPull(String.valueOf(infoS.getAvgPull()));
                    windDanced.setPullChangeRange(String.valueOf(infoS.getPullChangeRange()));
                    windDanced.setPullChangeFre(String.valueOf(infoS.getPullChangeFre()));
                    windDanced.setAvgWindDefAngle(String.valueOf(infoS.getAvgWindDefAngle()));
                    windDanced.setWindDefAngleRange(String.valueOf(infoS.getWindDefAngleRange()));
                    windDanced.setWindDefAngleChangeRange(String.valueOf(infoS.getWindDefAngleChangeRange()));
                    windDanced.setState("1");
                    list.add(windDanced);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出大风舞动报警列表
     */
    @PreAuthorize("@ss.hasPermi('system:danced:export')")
    @Log(title = "大风舞动报警", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiWindDanced tYuntaiWindDanced)
    {
        List<TYuntaiWindDanced> list = tYuntaiWindDancedService.selectTYuntaiWindDancedList(tYuntaiWindDanced);
        ExcelUtil<TYuntaiWindDanced> util = new ExcelUtil<TYuntaiWindDanced>(TYuntaiWindDanced.class);
        return util.exportExcel(list, "大风舞动报警数据");
    }

    /**
     * 获取大风舞动报警详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:danced:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiWindDancedService.selectTYuntaiWindDancedById(id));
    }

    /**
     * 新增大风舞动报警
     */
    @PreAuthorize("@ss.hasPermi('system:danced:add')")
    @Log(title = "大风舞动报警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiWindDanced tYuntaiWindDanced)
    {
        return toAjax(tYuntaiWindDancedService.insertTYuntaiWindDanced(tYuntaiWindDanced));
    }

    /**
     * 修改大风舞动报警
     */
    @PreAuthorize("@ss.hasPermi('system:danced:edit')")
    @Log(title = "大风舞动报警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiWindDanced tYuntaiWindDanced)
    {
        return toAjax(tYuntaiWindDancedService.updateTYuntaiWindDanced(tYuntaiWindDanced));
    }

    /**
     * 删除大风舞动报警
     */
    @PreAuthorize("@ss.hasPermi('system:danced:remove')")
    @Log(title = "大风舞动报警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiWindDancedService.deleteTYuntaiWindDancedByIds(ids));
    }
}
