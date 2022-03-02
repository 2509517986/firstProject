package com.senxu.omserver.web.controller.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.senxu.omserver.service.domain.MicroMete;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.IMicroMeteService;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
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
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.Cmd25HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.cmd.response.MicroMeteF;
import senxu.com.cmd.response.MicroMeteS;

/**
 * 气象信息获取Controller
 * 
 * @author ruoyi
 * @date 2021-12-13
 */
@RestController
@RequestMapping("/system/mete")
public class MicroMeteController extends BaseController
{
    @Autowired
    private IMicroMeteService microMeteService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询气象信息获取列表
     */
    //@PreAuthorize("@ss.hasPermi('system:mete:list')")
    @GetMapping("/list")
    public TableDataInfo list(MicroMete microMete)
    {
        startPage();
//        List<MicroMete> list = microMeteService.selectMicroMeteList(microMete);
//        return getDataTable(list);
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(microMete.getYuntaiId());
        List<MicroMete> list = new ArrayList<>();
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd25H(yuntaiInfo.getCode());
        Cmd25HResponse cmd25HResponse = null;
        if(response.isSuccess()) {
            try{
                cmd25HResponse = (Cmd25HResponse)response.getCmdResponse();
                MicroMeteF microMeteF = (MicroMeteF)cmd25HResponse.getList().get(0);
                for(int i = 1 ; i < cmd25HResponse.getList().size();i++){
                    MicroMeteS microMeteS = (MicroMeteS)cmd25HResponse.getList().get(i);
                    MicroMete mete = new MicroMete();
                    mete.setCollectDate(microMeteF.getCollectDate());
                    mete.setTemperature(String.valueOf(getTemperature(microMeteS.getTemperature())));
                    mete.setHumidity(String.valueOf(microMeteS.getHumidity()));
                    mete.setInstantWindSpeed(String.valueOf(getInstantWindSpeed(microMeteS.getInstantWindSpeed())));
                    mete.setInstantWindDirection(String.valueOf(microMeteS.getInstantWindDirection()));
                    mete.setRainfall(String.valueOf(microMeteS.getRainfall()));
                    mete.setPressure(String.valueOf(microMeteS.getPressure()));
                    mete.setSunshine(String.valueOf(microMeteS.getSunshine()));
                    mete.setAvgWindSpeedOneMinute(String.valueOf(microMeteS.getAvgWindSpeedOneMinute()));
                    mete.setAvgWindSpeedTenMinute(String.valueOf(microMeteS.getAvgWindSpeedTenMinute()));
                    mete.setAvgWindWirectionOneMinute(String.valueOf(microMeteS.getAvgWindDirectionOneMinute()));
                    mete.setAvgWindWirectionTenMinute(String.valueOf(microMeteS.getAvgWindDirectionTenMinute()));
                    mete.setMaxWindSpeedTenMinute(String.valueOf(microMeteS.getMaxWindSpeedTenMinute()));
                    mete.setState("1");
                    list.add(mete);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return getDataTable(list);
    }

    /**
     * 导出气象信息获取列表
     */
    //@PreAuthorize("@ss.hasPermi('system:mete:export')")
    @Log(title = "气象信息获取", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MicroMete microMete)
    {
        List<MicroMete> list = microMeteService.selectMicroMeteList(microMete);
        ExcelUtil<MicroMete> util = new ExcelUtil<MicroMete>(MicroMete.class);
        return util.exportExcel(list, "气象信息获取数据");
    }

    /**
     * 获取气象信息获取详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:mete:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(microMeteService.selectMicroMeteById(id));
    }

    /**
     * 新增气象信息获取
     */
    //@PreAuthorize("@ss.hasPermi('system:mete:add')")
    @Log(title = "气象信息获取", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MicroMete microMete)
    {
        return toAjax(microMeteService.insertMicroMete(microMete));
    }

    /**
     * 修改气象信息获取
     */
    //@PreAuthorize("@ss.hasPermi('system:mete:edit')")
    @Log(title = "气象信息获取", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MicroMete microMete)
    {
        return toAjax(microMeteService.updateMicroMete(microMete));
    }

    /**
     * 删除气象信息获取
     */
    //@PreAuthorize("@ss.hasPermi('system:mete:remove')")
    @Log(title = "气象信息获取", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(microMeteService.deleteMicroMeteByIds(ids));
    }


    //温度计算公式
    public BigDecimal getTemperature(int temperature){
        BigDecimal temp = new BigDecimal((temperature-500)/10.0).setScale(1,BigDecimal.ROUND_DOWN);
        System.out.println("转换后温度："+temp);
        return temp;
    }

    //风速计算公式
    public BigDecimal getInstantWindSpeed(int instantWindSpeed){
        BigDecimal speed = new BigDecimal(instantWindSpeed/10.0).setScale(1,BigDecimal.ROUND_DOWN);
        System.out.println("转换后风速："+speed);
        return speed;
    }
}
