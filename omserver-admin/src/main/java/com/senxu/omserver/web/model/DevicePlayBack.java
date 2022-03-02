package com.senxu.omserver.web.model;

import lombok.Data;
import senxu.com.base.util.UdpReceiveCallBack;

import java.util.Date;

/**
 * @auther yudarr
 * @date 2021-12-21 11:44
 **/
@Data
public class DevicePlayBack {
    /**
     * 是否已接收数据
     */
    private boolean hasReceivedData;
    /**
     * 开始推流时间
     */
    private Date startPushTime;
    /**
     * 当前是否暂停
     */
    private boolean pause;
    /**
     * 当前是否结束
     */
    private boolean end;
    /**
     * 播放了的时间
     */
    private long playtime;
    /**
     * 播放时长
     */
    private long duration;


    private Thread playtimeThread;
    public boolean recordtimeFlg=true;


    /**
     * 开始计时
     */
    public void startRecordtime() {
        if(playtimeThread!=null){
            recordtimeFlg=false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playtimeThread.interrupt();
            playtimeThread=null;
        }
        recordtimeFlg=true;
        playtimeThread=new Thread(()->{
                while (recordtimeFlg){
                    while (true){
                        if(!isPause()){
                            break;
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    long playtime=getPlaytime();
                    if(playtime>getDuration()){
                        setEnd(true);
                        break;
                    }
                    else{
                        playtime+=1;
                        setPlaytime(playtime);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

            });

        playtimeThread.start();

    }

    UdpReceiveCallBack udpReceiveCallBack;
    public void onReceiveUpdData() {
        udpReceiveCallBack.run(null,null,null);
    }
}
