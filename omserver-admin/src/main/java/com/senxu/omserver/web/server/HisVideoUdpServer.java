package com.senxu.omserver.web.server;

import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.web.controller.service.NWOMServer;
import com.senxu.omserver.web.model.DevicePlayBack;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senxu.com.base.util.SysUtil;
import senxu.com.base.util.UdpManager;
import senxu.com.base.util.UdpReceiveCallBack;
import senxu.com.base.util.UdpTransmitCallBack;
import senxu.com.cmd.BridgeContext;

import javax.annotation.PostConstruct;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther yudarr
 * @date 2021-12-21 9:50
 **/
@Slf4j
@Component
public class HisVideoUdpServer {
    @Autowired
    private NWOMServer nwomServer;
    public ConcurrentHashMap<String, DevicePlayBack> devices=new ConcurrentHashMap<>();
    public UdpManager   udpManager;

    @Autowired
    private OmServerProperties omServerProperties;
    static boolean  aa ;
    public static void main(String[] args) {


        System.out.println(aa);
    }

    @PostConstruct
    public void init(){
        //先接收数据 //先缓冲起来，避免丢失
        String mediaIp= omServerProperties.getMediaServerIpOnInNet();//SysUtil.getCurIp();//getMediaIp(deviceInfo);
        /*if(omServerProperties.mediaServerIp!=null){
            mediaIp=omServerProperties.mediaServerIp;
        }*/
        udpManager=new UdpManager();
        udpManager.receivceCount =0;
        udpManager.transmitCount =0;
        udpManager.putInQueueOnReceive =true;
        nwomServer.bridgeContext.hisVideoUdpServer=udpManager;
        udpManager.init(mediaIp,nwomServer.bridgeContext.getAgentConfig().getHisVideoPort());//ip要设上，因为有时一台机有多个ip
        udpManager.receive(new UdpReceiveCallBack() {
                @Override
                public void run(byte[] rcvBytes, DatagramPacket datagramPacket, Integer receivceCount) {

                    if(receivceCount%100==0){
                        log.info("接收到了"+receivceCount+"个");
                    }
                    if(!nwomServer.bridgeContext.deviceHostMap.containsKey(datagramPacket.getAddress().getHostAddress())) {
                        return;
                    }
                    String code=nwomServer.bridgeContext.deviceHostMap.get(datagramPacket.getAddress().getHostAddress());
                    DevicePlayBack devicePlayBack=devices.get(code);
                    if(devicePlayBack!=null){

                        devicePlayBack.onReceiveUpdData();
                    }


                }
            });
           // UdpManager finalHisVideoUdpServer = udpManager;


           udpManager.transmit(mediaIp,8012,new UdpTransmitCallBack(){

                @Override
                public void run(byte[] rcvBytes, DatagramSocket datagramSocket, DatagramPacket udpSendPacket, Integer count) {
                    if(udpManager.transmitCount%100==0){
                        log.info("【=>"+udpSendPacket.getAddress()+":"+udpSendPacket.getPort()+" 转发数据数"+udpManager.transmitCount+"】");
                    }

                }
            });

    }



    public DevicePlayBack getDeviceByCode(String code) {
        DevicePlayBack devicePlayBack=devices.get(code);
        if(devicePlayBack==null){
            devicePlayBack=new DevicePlayBack();
            devices.put(code,devicePlayBack);
        }
        return devicePlayBack;
    }
}
