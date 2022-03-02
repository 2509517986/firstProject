package com.senxu.omserver.web.tasks;

import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TUploadList;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITUploadListService;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.web.controller.service.NWOMServer;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import senxu.com.cmd.response.Cmd0AHResponse;
import senxu.com.cmd.response.DeviceCommandResponse;

import java.util.List;

@Component
@EnableScheduling
public class task {
    @Autowired
    private NWOMServer nwomServer;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private ITUploadListService tUploadListService;

    private static Logger logger = LoggerFactory.getLogger(task.class);

    /**
     * 定时更新云台信息
     */
    @Scheduled(fixedRate = 10*60*1000)
    public void TYuntaiConfigjob(){
        List<TYuntaiInfo> yuntaiList = tYuntaiInfoService.selectTYuntaiInfoList(new TYuntaiInfo());

        for(TYuntaiInfo obj:yuntaiList) {
            try {
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setId(obj.getId());

                //查询除离线外电量,更新数据库
                if (obj.getState() != -1) {
                    int electricPower = 0;
                    for (int i=0; i<3; i++){//不清楚为什么有时候水印有值但获取到0，增加重试
                        electricPower = getElectricPower(obj.getCode());
                        if(electricPower != 0){
                            break;
                        }else{
                            Thread.sleep(3000);
                        }
                    }
                    if(electricPower != 0){
                        tYuntaiInfo.setElectricPower(electricPower);
                    }else{
                        logger.info(obj.getCode() +"电量为0");
                    }
                }
                //计算在线/离线时长
                if(obj.getOnlineTime() != null){
                    long diff = DateUtils.getNowDate().getTime()-obj.getOnlineTime().getTime();
                    long hour = diff / (1000 * 60 * 60);
                    tYuntaiInfo.setOnlineDuration(hour+"小时");
                }
                //软件版本，更新时间
                List<TUploadList> tUploadLists = tUploadListService.selectTUploadListByYuntaiId(obj.getId());
                if(tUploadLists.size()>0){
                    tYuntaiInfo.setVersion(tUploadLists.get(0).getFileName());
                    tYuntaiInfo.setVersionUpdate(DateFormatUtils.format(tUploadLists.get(0).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                }

                tYuntaiInfoService.updateTYuntaiInfo(tYuntaiInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用0AH指令获取电量
     * @param code
     * @return
     */
    public int getElectricPower(String code){
        int electricPower = 0;
        try {

            DeviceCommandResponse response = nwomServer.getDeviceBridge().Cmd0AH(code);
            if (response.isSuccess()) {
                Cmd0AHResponse cmd0AHResponse = (Cmd0AHResponse) response.getCmdResponse();
                electricPower = cmd0AHResponse.getValidFunction().length > 0 ? (int) cmd0AHResponse.getValidFunction()[0] : 0;//电量，等于0时值就传不过去
            }
        } catch (Exception e) {
              e.printStackTrace(); //由于有任务计划，不停输出不好
        }finally {
            return electricPower;
        }
    }
}
