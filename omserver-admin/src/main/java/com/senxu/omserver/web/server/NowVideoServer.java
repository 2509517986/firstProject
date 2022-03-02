package com.senxu.omserver.web.server;

import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.web.controller.service.NWOMServer;
import com.senxu.omserver.web.model.DeviceNowVideo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther yudarr
 * @date 2022-02-21 11:53
 **/
public class NowVideoServer {
    @Autowired
    protected NWOMServer nwomServer;
    @Autowired
    protected OmServerProperties omServerProperties;
    public ConcurrentHashMap<String, DeviceNowVideo> devices=new ConcurrentHashMap<>();

    public DeviceNowVideo getDeviceByCode(String code) {
        DeviceNowVideo deviceNowVideo=devices.get(code);
        if(deviceNowVideo==null){
            deviceNowVideo=new DeviceNowVideo();
            deviceNowVideo.setDeviceCode(code);
            devices.put(code,deviceNowVideo);
        }
        return deviceNowVideo;
    }
}
