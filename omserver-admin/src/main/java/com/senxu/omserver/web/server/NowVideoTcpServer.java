package com.senxu.omserver.web.server;

import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.web.controller.service.NWOMServer;
import com.senxu.omserver.web.model.DeviceNowVideo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senxu.com.base.util.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @auther yudarr
 * @date 2021-12-21 9:50
 **/
@Slf4j
@Data
@Component
public class NowVideoTcpServer extends  NowVideoServer{

    private ConcurrentHashMap<String,Queue> deviceTcpQueue=new ConcurrentHashMap<>();


    public TcpServer   tcpServer;
    @Autowired
    private OmServerProperties omServerProperties;
    private boolean exit;

    public static void main(String[] args) {

    }

    @PostConstruct
    public void init(){
        //先接收数据 //先缓冲起来，避免丢失
        String mediaIp= SysUtil.getCurIp();//getMediaIp(deviceInfo);
        /*if(omServerProperties.mediaServerIp!=null){
            mediaIp=omServerProperties.mediaServerIp;
        }*/
        new Thread(()->{//用新线程打开，不然后给里面的while卡着
            tcpServer =new TcpServer(omServerProperties.getVideoPort());

            tcpServer.init(
            new TcpReceiveCallBack() {
                @Override
                public void run(byte[] bytes, Socket socket) throws IllegalAccessException, InstantiationException {

                /*if(receivceCount%100==0){
                    log.info("接收到了"+receivceCount+"个");
                }*/
                    String address = socket.getInetAddress().toString().replace("/", "");
                    if(!nwomServer.bridgeContext.deviceHostMap.containsKey(address)) {
                        return;
                    }

                    InputStream inputStream = null;// 获取一个输入流，接收服务端的信息
                    try {
                        inputStream = socket.getInputStream();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    String code=nwomServer.bridgeContext.deviceHostMap.get(address);
                    if(code==null){
                        return;
                    }

                    DeviceNowVideo deviceNowVideo=getDeviceByCode(code);
                    if(deviceNowVideo!=null){
                        deviceNowVideo.onReceiveTcpData(bytes,socket);
                    }
                    deviceNowVideo.receiveNum++;
                    if(deviceNowVideo.receiveNum%1000==0){
                        log.info(deviceNowVideo.getDeviceCode()+"接收到了"+deviceNowVideo.receiveNum+"个");
                    }
                    deviceNowVideo.queues.offer(bytes);
                }
            });
        }).start();




    }






}
