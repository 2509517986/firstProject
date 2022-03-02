package com.senxu.omserver.web.controller.service;

import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.page.TableDataInfo;
import com.senxu.omserver.service.domain.MicroMete;
import com.senxu.omserver.service.domain.RealtimeData;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.IMicroMeteService;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senxu.com.base.dto.DeviceInfo;
import senxu.com.cmd.response.Cmd25HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.cmd.response.MicroMeteF;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/realtimeData")
public class realtimeDataController extends BaseController {

    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询实时数据
     */
    @GetMapping("/list/{yuntaiId}")
    public TableDataInfo list(@PathVariable("yuntaiId") Long yuntaiId)
    {
        startPage();
        RealtimeData realtimeData = new RealtimeData();
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        List<MicroMete> list = new ArrayList<>();
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd25H(yuntaiInfo.getCode());
        DeviceInfo deviceInfo = nwomServer.bridgeContext.deviceInfoMap.get(yuntaiInfo.getCode());
        Cmd25HResponse cmd25HResponse = null;
        if(response.isSuccess()) {
            try{
                cmd25HResponse = (Cmd25HResponse)response.getCmdResponse();
                MicroMeteF microMeteF = (MicroMeteF)cmd25HResponse.getList().get(0);
                realtimeData.setPowerV(String.valueOf(deviceInfo.getVoltage()));
                realtimeData.setSignalStrength(String.valueOf(deviceInfo.getSignalIntensity()));
                realtimeData.setOutdoorTemperature(String.valueOf(microMeteF.getTemperature()));
                realtimeData.setRelativeHumidity(String.valueOf(microMeteF.getHumidity()));
                realtimeData.setAtmosphericPressure(String.valueOf(microMeteF.getPressure()));
                realtimeData.setWindDirection(String.valueOf(microMeteF.getInstantWindDirection()));
                realtimeData.setWindSpeed(String.valueOf(microMeteF.getInstantWindSpeed()));
                realtimeData.setRainfallIntensity(String.valueOf(microMeteF.getRainfall()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return getDataTable(list);
    }

}
