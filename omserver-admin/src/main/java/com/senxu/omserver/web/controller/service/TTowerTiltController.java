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
import com.senxu.omserver.service.domain.TTowerTilt;
import com.senxu.omserver.service.ITTowerTiltService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.*;

/**
 * 杆塔倾斜数据采样Controller
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@RestController
@RequestMapping("/system/tilt")
public class TTowerTiltController extends BaseController
{
    @Autowired
    private ITTowerTiltService tTowerTiltService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询杆塔倾斜数据采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:tilt:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTowerTilt tTowerTilt)
    {
        startPage();
//        List<TTowerTilt> list = tTowerTiltService.selectTTowerTiltList(tTowerTilt);
//        return getDataTable(list);
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(tTowerTilt.getYuntaiId());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd2AH(yuntaiInfo.getCode());
        List<TTowerTilt> list = new ArrayList<>();
        Cmd2AHResponse cmd2AHResponse = null;
        if(response.isSuccess()) {
            try{
                cmd2AHResponse = (Cmd2AHResponse) response.getCmdResponse();
                TowerTiltInfoF infoF = (TowerTiltInfoF) cmd2AHResponse.getList().get(0);
                for (int i = 1; i < cmd2AHResponse.getList().size(); i++) {
                    TTowerTilt towerTilt = new TTowerTilt();
                    TowerTiltInfoS infoS = (TowerTiltInfoS)cmd2AHResponse.getList().get(i);
                    towerTilt.setTime(infoF.getCollectDate());
                    towerTilt.setInLineOff(String.valueOf(infoS.getHorizontalLineOfF()));
                    towerTilt.setHorizontalLineOff(String.valueOf(infoS.getHorizontalLineOfF()));
                    towerTilt.setState("1");
                    towerTilt.setInLineOfs(String.valueOf(infoS.getInLineOfS()));
                    towerTilt.setHorizontalLineOfs(String.valueOf(infoS.getHorizontalLineOfS()));
                    list.add(towerTilt);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出杆塔倾斜数据采样列表
     */
    @PreAuthorize("@ss.hasPermi('system:tilt:export')")
    @Log(title = "杆塔倾斜数据采样", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTowerTilt tTowerTilt)
    {
        List<TTowerTilt> list = tTowerTiltService.selectTTowerTiltList(tTowerTilt);
        ExcelUtil<TTowerTilt> util = new ExcelUtil<TTowerTilt>(TTowerTilt.class);
        return util.exportExcel(list, "杆塔倾斜数据采样数据");
    }

    /**
     * 获取杆塔倾斜数据采样详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:tilt:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tTowerTiltService.selectTTowerTiltById(id));
    }

    /**
     * 新增杆塔倾斜数据采样
     */
    @PreAuthorize("@ss.hasPermi('system:tilt:add')")
    @Log(title = "杆塔倾斜数据采样", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTowerTilt tTowerTilt)
    {
        return toAjax(tTowerTiltService.insertTTowerTilt(tTowerTilt));
    }

    /**
     * 修改杆塔倾斜数据采样
     */
    @PreAuthorize("@ss.hasPermi('system:tilt:edit')")
    @Log(title = "杆塔倾斜数据采样", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTowerTilt tTowerTilt)
    {
        return toAjax(tTowerTiltService.updateTTowerTilt(tTowerTilt));
    }

    /**
     * 删除杆塔倾斜数据采样
     */
    @PreAuthorize("@ss.hasPermi('system:tilt:remove')")
    @Log(title = "杆塔倾斜数据采样", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tTowerTiltService.deleteTTowerTiltByIds(ids));
    }
}
