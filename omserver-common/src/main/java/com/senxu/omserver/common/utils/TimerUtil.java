package com.senxu.omserver.common.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @auther yudarr
 * @date 2021/3/16 17:05
 **/
public class TimerUtil {


    public static Timer start(int delay,int period,Runnable runnable) {
        Timer   timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                runnable.run();
            }

        },  delay,period);
        return  timer;
    }
}
