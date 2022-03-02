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
import com.senxu.omserver.service.domain.TYuntaiComprehensiveAntitheft;
import com.senxu.omserver.service.service.ITYuntaiComprehensiveAntitheftService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.*;

/**
 * 综合防盗数据Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/antitheft")
public class TYuntaiComprehensiveAntitheftController extends BaseController
{
    @Autowired
    private ITYuntaiComprehensiveAntitheftService tYuntaiComprehensiveAntitheftService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询综合防盗数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:antitheft:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft)
    {
        startPage();
//        List<TYuntaiComprehensiveAntitheft> list = tYuntaiComprehensiveAntitheftService.selectTYuntaiComprehensiveAntitheftList(tYuntaiComprehensiveAntitheft);
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiComprehensiveAntitheft.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd2CH(yuntaiInfo.getCode());
        List<TYuntaiComprehensiveAntitheft> list = new ArrayList<>();
        Cmd2CHResponse cmd2CHResponse = null;
        if(response.isSuccess()) {
            try {
                cmd2CHResponse = (Cmd2CHResponse) response.getCmdResponse();
                ComSecurityInfoF infoF = (ComSecurityInfoF) cmd2CHResponse.getList().get(0);
                for (int i = 1; i < cmd2CHResponse.getList().size(); i++) {
                    ComSecurityInfoS infoS = (ComSecurityInfoS) cmd2CHResponse.getList().get(i);
                    TYuntaiComprehensiveAntitheft antitheft = new TYuntaiComprehensiveAntitheft();
                    antitheft.setTime(infoF.getCollectDate());
                    antitheft.setExtForceDesPolice(String.valueOf(infoS.getExtForceDesPolice()));
                    antitheft.setSpeedEff(String.valueOf(infoS.getSpeedEff()));
                    antitheft.setShockPolice(String.valueOf(infoS.getShockPolice()));
                    antitheft.setInfraredPolice(String.valueOf(infoS.getInfraredPolice()));
                    antitheft.setInfraredDetectionArea(String.valueOf(infoS.getInfraredDetectionArea()));
                    antitheft.setState("1");
                    list.add(antitheft);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出综合防盗数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:antitheft:export')")
    @Log(title = "综合防盗数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft)
    {
        List<TYuntaiComprehensiveAntitheft> list = tYuntaiComprehensiveAntitheftService.selectTYuntaiComprehensiveAntitheftList(tYuntaiComprehensiveAntitheft);
        ExcelUtil<TYuntaiComprehensiveAntitheft> util = new ExcelUtil<TYuntaiComprehensiveAntitheft>(TYuntaiComprehensiveAntitheft.class);
        return util.exportExcel(list, "综合防盗数据数据");
    }

    /**
     * 获取综合防盗数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:antitheft:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiComprehensiveAntitheftService.selectTYuntaiComprehensiveAntitheftById(id));
    }

    /**
     * 新增综合防盗数据
     */
    @PreAuthorize("@ss.hasPermi('system:antitheft:add')")
    @Log(title = "综合防盗数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft)
    {
        return toAjax(tYuntaiComprehensiveAntitheftService.insertTYuntaiComprehensiveAntitheft(tYuntaiComprehensiveAntitheft));
    }

    /**
     * 修改综合防盗数据
     */
    @PreAuthorize("@ss.hasPermi('system:antitheft:edit')")
    @Log(title = "综合防盗数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft)
    {
        return toAjax(tYuntaiComprehensiveAntitheftService.updateTYuntaiComprehensiveAntitheft(tYuntaiComprehensiveAntitheft));
    }

    /**
     * 删除综合防盗数据
     */
    @PreAuthorize("@ss.hasPermi('system:antitheft:remove')")
    @Log(title = "综合防盗数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiComprehensiveAntitheftService.deleteTYuntaiComprehensiveAntitheftByIds(ids));
    }
}
