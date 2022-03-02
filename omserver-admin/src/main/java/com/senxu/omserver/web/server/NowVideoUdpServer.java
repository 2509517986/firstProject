package com.senxu.omserver.web.server;

import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.common.utils.file.FileUtils;
import com.senxu.omserver.web.controller.service.NWOMServer;
import com.senxu.omserver.web.model.DeviceNowVideo;
import com.senxu.omserver.web.model.DevicePlayBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senxu.com.base.util.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @auther yudarr
 * @date 2021-12-21 9:50
 **/
@Slf4j
@Component
public class NowVideoUdpServer extends NowVideoServer {

    public UdpManager udpManager;

    public static void main(String[] args) {

    }

    @PostConstruct
    public void init(){
        //先接收数据 //先缓冲起来，避免丢失
        String mediaIp= SysUtil.getCurIp();//getMediaIp(deviceInfo);
        /*if(omServerProperties.mediaServerIp!=null){
            mediaIp=omServerProperties.mediaServerIp;
        }*/
        udpManager=new UdpManager();
        udpManager.receivceCount =0;
        udpManager.transmitCount =0;
        udpManager.putInQueueOnReceive=false;
        nwomServer.bridgeContext.hisVideoUdpServer=udpManager;
       udpManager.init(mediaIp,nwomServer.bridgeContext.getAgentConfig().getVideoPort());//ip要设上，因为有时一台机有多个ip

        udpManager.receive(new UdpReceiveCallBack() {
            @Override
            public void run(byte[] rcvBytes, DatagramPacket datagramPacket, Integer receivceCount) {


                String address = datagramPacket.getAddress().getHostAddress().replace("//", "");
                if(!nwomServer.bridgeContext.deviceHostMap.containsKey(address)) {
                    return;
                }
                String code=nwomServer.bridgeContext.deviceHostMap.get(address);
                DeviceNowVideo deviceNowVideo=getDeviceByCode(code);
               /* if(deviceNowVideo==null){
                    log.info("没有"+code+"视频对象！！");
                    return;
                }*/
                if(deviceNowVideo!=null&&deviceNowVideo.getUdpReceiveCallBack()!=null){
                    deviceNowVideo.onReceiveUpdData(rcvBytes,datagramPacket,receivceCount);
                }
                deviceNowVideo.receiveNum++;
                if(deviceNowVideo.receiveNum%1000==0){
                    log.info(deviceNowVideo.getDeviceCode()+"接收到了"+deviceNowVideo.receiveNum+"个");
                }

               /* File f=new File("C:\\omserver_res\\"+deviceNowVideo.getDeviceCode()+".h264");
                if(deviceNowVideo.receiveNum==1){
                    if(f.exists()){
                        f.delete();
                    }
                    try {
                        f.createNewFile();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                byte[] rbytes=new byte[rcvBytes.length-12];
                System.arraycopy(rcvBytes ,11,rbytes,0,rcvBytes.length-12);

                try {
                    FileUtils.writeByteArrayToFile(f,rcvBytes,true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }*/


                deviceNowVideo.queues.offer(rcvBytes);
            }
        });
      /*   UdpManager finalHisVideoUdpServer = udpManager;


        udpManager.transmit(mediaIp,8012,new UdpTransmitCallBack(){

            @Override
            public void run(byte[] rcvBytes, DatagramSocket datagramSocket, DatagramPacket udpSendPacket, Integer count) {
                if(udpManager.transmitCount%100==0){
                    log.info("【=>"+udpSendPacket.getAddress()+":"+udpSendPacket.getPort()+" 转发数据数"+udpManager.transmitCount+"】");
                }

            }
        });  */

    }






/*
    public Queue getDeviceTcpQueue(String deviceCode) {
        if(deviceTcpQueue.get(deviceCode)==null){
            deviceTcpQueue.put(deviceCode,new ConcurrentLinkedQueue());
        }
        return  deviceTcpQueue.get(deviceCode);
    }

    public void clearQueue(String deviceCode) {
        //播放前清空旧的队列
        synchronized (getDeviceTcpQueue(deviceCode)){
            getDeviceTcpQueue(deviceCode).clear();
        }
    }*/
}
