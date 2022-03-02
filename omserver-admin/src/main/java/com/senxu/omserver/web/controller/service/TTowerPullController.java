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
import com.senxu.omserver.service.domain.TTowerPull;
import com.senxu.omserver.service.service.ITTowerPullService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.Cmd22HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.cmd.response.GroundWireInfoF;
import senxu.com.cmd.response.GroundWireInfoS;

/**
 * 杆塔拉力采样Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/pull")
public class TTowerPullController extends BaseController
{
    @Autowired
    private ITTowerPullService tTowerPullService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询杆塔拉力采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:pull:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTowerPull tTowerPull)
    {
        startPage();
//        List<TTowerPull> list = tTowerPullService.selectTTowerPullList(tTowerPull);
        List<TTowerPull> list = new ArrayList<>();
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tTowerPull.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd22H(yuntaiInfo.getCode());
        Cmd22HResponse cmd22HResponse = null;
        if(response.isSuccess()) {
            try {
                cmd22HResponse = (Cmd22HResponse)response.getCmdResponse();
                GroundWireInfoF wireInfoF = (GroundWireInfoF)cmd22HResponse.getList().get(0);
                for(int i = 1 ; i < cmd22HResponse.getList().size();i++){
                    GroundWireInfoS wireInfoS = new GroundWireInfoS();
                    TTowerPull pull = new TTowerPull();
                    pull.setTime(wireInfoF.getCollectDate());
                    pull.setMaxPull(String.valueOf(wireInfoS.getMaxPull()));
                    pull.setWindDefAngleOfMaxPull(String.valueOf(wireInfoS.getWindDefAngleOfMaxPull()));
                    pull.setIncAngleOfMaxPull(String.valueOf(wireInfoS.getIncAngleOfMaxPull()));
                    pull.setMinPull(String.valueOf(wireInfoS.getMinPull()));
                    pull.setWindDefAngleOfMinPull(String.valueOf(wireInfoS.getWindDefAngleOfMinPull()));
                    pull.setIncAngleOfMinPull(String.valueOf(wireInfoS.getIncAngleOfMinPull()));
                    pull.setMaxWindDefAngle(String.valueOf(wireInfoS.getMaxWindDefAngle()));
                    pull.setIncAngleOfMaxWind(String.valueOf(wireInfoS.getIncAngleOfMaxWind()));
                    pull.setPullOfMaxWind(String.valueOf(wireInfoS.getPullOfMaxWind()));
                    pull.setMinWindDefAngle(String.valueOf(wireInfoS.getMinWindDefAngle()));
                    pull.setIncAngleOfMinWind(String.valueOf(wireInfoS.getIncAngleOfMinWind()));
                    pull.setPullOfMinWind(String.valueOf(wireInfoS.getPullOfMinWind()));
                    list.add(pull);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出杆塔拉力采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:pull:export')")
    @Log(title = "杆塔拉力采样", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTowerPull tTowerPull)
    {
        List<TTowerPull> list = tTowerPullService.selectTTowerPullList(tTowerPull);
        ExcelUtil<TTowerPull> util = new ExcelUtil<TTowerPull>(TTowerPull.class);
        return util.exportExcel(list, "杆塔拉力采样数据");
    }

    /**
     * 获取杆塔拉力采样详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:pull:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tTowerPullService.selectTTowerPullById(id));
    }

    /**
     * 新增杆塔拉力采样
     */
    @PreAuthorize("@ss.hasPermi('system:pull:add')")
    @Log(title = "杆塔拉力采样", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTowerPull tTowerPull)
    {
        return toAjax(tTowerPullService.insertTTowerPull(tTowerPull));
    }

    /**
     * 修改杆塔拉力采样
     */
    @PreAuthorize("@ss.hasPermi('system:pull:edit')")
    @Log(title = "杆塔拉力采样", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTowerPull tTowerPull)
    {
        return toAjax(tTowerPullService.updateTTowerPull(tTowerPull));
    }

    /**
     * 删除杆塔拉力采样
     */
    @PreAuthorize("@ss.hasPermi('system:pull:remove')")
    @Log(title = "杆塔拉力采样", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tTowerPullService.deleteTTowerPullByIds(ids));
    }
}
