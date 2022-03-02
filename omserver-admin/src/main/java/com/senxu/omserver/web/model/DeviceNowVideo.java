package com.senxu.omserver.web.model;

import lombok.Data;
import senxu.com.base.util.SocketCom;
import senxu.com.base.util.TcpReceiveCallBack;
import senxu.com.base.util.UdpReceiveCallBack;

import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @auther yudarr
 * @date 2022-02-15 16:50
 **/
@Data
public class DeviceNowVideo {
    public Process process;
    public Integer transVideoPort;
    public int receiveNum;
    public String deviceCode;
    public java.util.List<byte[]> tcpPackets=new ArrayList();
    public ConcurrentLinkedQueue<byte[]> queues=new ConcurrentLinkedQueue();
    private TcpReceiveCallBack tcpReceiveCallBack;
    private UdpReceiveCallBack udpReceiveCallBack;
    private boolean transmitFlg=true;
    private Thread transmitThread;

    public void onReceiveTcpData(byte[] bytes, Socket socket) throws InstantiationException, IllegalAccessException {
        if(tcpReceiveCallBack!=null){
            tcpReceiveCallBack.run(bytes,socket);
        }

    }

    public void onReceiveUpdData(byte[] bytes, DatagramPacket datagramPacket,int num) {
        if(udpReceiveCallBack!=null){
            udpReceiveCallBack.run(bytes,datagramPacket,num);
        }

    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void startTransmit(SocketCom socketCom) {
        if(transmitThread!=null){
            transmitThread.interrupt();
            transmitFlg=false;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       transmitThread= new Thread(()->{
            transmitFlg=true;
            while (transmitFlg){
                if(queues.size()>1000){
                    synchronized (queues){
                        queues.clear();
                    }
                }
                if(queues.size()==0){
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                byte[] bytes=queues.poll();
                if(bytes!=null){
                    socketCom.sendUdpInfo(bytes,false);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
       transmitThread.start();
    }
}
