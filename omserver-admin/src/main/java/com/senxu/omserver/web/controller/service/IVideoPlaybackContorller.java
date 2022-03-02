package com.senxu.omserver.web.controller.service;

import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.constant.HttpStatus;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.core.page.TableDataInfo;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.IVideoPlayback;
import com.senxu.omserver.service.service.IVideoPlaybackService;
import com.senxu.omserver.web.model.DevicePlayBack;
import com.senxu.omserver.web.server.HisVideoUdpServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import senxu.com.base.dto.DeviceInfo;
import senxu.com.base.util.*;
import senxu.com.cmd.BridgeContext;
import senxu.com.cmd.Contants;
import senxu.com.cmd.request.Cmd9AHRequest;
import senxu.com.cmd.request.Cmd9BHRequest;
import senxu.com.cmd.request.Cmd9CHRequest;
import senxu.com.cmd.response.Cmd99HResponse;
import senxu.com.cmd.response.Cmd99HResponseDetail;
import senxu.com.cmd.response.Cmd9BHResponse;
import senxu.com.cmd.response.DeviceCommandResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * t_video_playback（录像回放）Controller
 *
 * @author lvhp
 * @date 2021-10-29
 */
@Slf4j
@Api("录像回放管理")
@RestController
@RequestMapping("/service/videoPlayback")
public class IVideoPlaybackContorller extends BaseController {
    @Autowired
    HisVideoUdpServer hisVideoUdpServer;
    @Autowired
    IVideoPlaybackService iVideoPlaybackService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private NWOMServer nwomServer;
    private SocketCom socketCom;
    @Autowired
    private OmServerProperties omServerProperties;
    /*
    private JSONObject responseable=new JSONObject();
   // private Date startPushTime;
    private ConcurrentHashMap<String,JSONObject> devices=new ConcurrentHashMap<>();*/
    /**
     * 查询t_video_playback（录像回放）列表
     */
    @ApiOperation("获取录像回放列表")
    //@PreAuthorize("@ss.hasPermi('service:arguments:list')")
    @GetMapping("/list")
    public TableDataInfo list(HttpServletRequest request,IVideoPlayback iVideoPlayback, String type) {
        startPage();

        if(iVideoPlayback.getCode()==null){
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setMsg("设备编号code不能为空");
            rspData.setRows(null);
            rspData.setTotal(0);
            return rspData;
            // AjaxResult.error("设备编码不能为空！");
        }

        Date endDate=null;
        //无传时间时，默认100日前到现在
        if(iVideoPlayback.getCaptureTime()==null){
            Date date=DateUtil.incDay(new Date(),-100);
            endDate=DateUtil.incDay(new Date(),1);
            iVideoPlayback.setCaptureTime(date);
        }
        else  {
            endDate= DateUtil.incDay(iVideoPlayback.getCaptureTime(),1);
        }

        if(type!=null&&type.equals("platform")){
            List<IVideoPlayback> list = iVideoPlaybackService.selectIVideoPlaybackList(iVideoPlayback);
            for (int i = 0; i < list.size(); i++) {
                IVideoPlayback videoPlayback=list.get(i);
                videoPlayback.setVideoUrl(videoPlayback.getFilename());
            }
            return getDataTable(list);
        }
       else{
            byte[] typeBytes=new byte[4];
            typeBytes[0]=(byte)0xff;
            typeBytes[1]=(byte)0xff;
            typeBytes[2]=(byte)0xff;
            typeBytes[3]=(byte)0xff;
            DeviceCommandResponse<Cmd99HResponse> response=nwomServer.bridgeContext.videoPlaybackService.getVideoFiles(iVideoPlayback.getCode(),
                    1,typeBytes,
                    iVideoPlayback.getCaptureTime(),endDate,
                    0,0,0);
            if(!response.isSuccess()){
                TableDataInfo rspData = new TableDataInfo();
                rspData.setCode(HttpStatus.ERROR);
                rspData.setMsg("调用接口失败！"+response.getMessage());
                rspData.setRows(null);
                rspData.setTotal(0);
                return rspData;
                //return  AjaxResult.error(response.getMessage());
            }
            DeviceInfo deviceInfo=nwomServer.bridgeContext.deviceInfoMap.get(iVideoPlayback.getCode());
            String mediaIp=request.getServerName();
            //适应局域网 ,由于拿当前ip时可能取了46的，也不知设备是10段的，还是46段的
            /*if(deviceInfo.getHost().startsWith("192.168.10")){
                mediaIp=mediaIp.replace("192.168.46","192.168.10");
            }
            if(deviceInfo.getHost().startsWith("192.168.46")){
                mediaIp=mediaIp.replace("192.168.10","192.168.46");
            }*/
           // log.info("当前视频的ip");
            List details=response.getCmdResponse().getDetails();
            List<IVideoPlayback> list =new ArrayList<>();
            String ip=SysUtil.getCurIp();
            for (int i = 0;details!=null&& i < details.size(); i++) {
                Cmd99HResponseDetail detail = (Cmd99HResponseDetail) details.get(i);
                IVideoPlayback videoPlayback = new IVideoPlayback();
                videoPlayback.setCode(iVideoPlayback.getCode());
                videoPlayback.setCreateTime(detail.getFileStartDate());
                videoPlayback.setCaptureTime(detail.getFileStartDate());
                //0应代表了是udp
                videoPlayback.setFilename("0@" + DateUtil.format(detail.getFileStartDate(), "yyyyMMddHHmmss") + "_" + DateUtil.format(detail.getFileEndDate(), "yyyyMMddHHmmss"));
                videoPlayback.setVideoUrl("rtmp://" + mediaIp +":"+omServerProperties.getRtmpServerPort()+"/live/" + videoPlayback.getCode() + "_" + videoPlayback.getFilename());//rtmp不允许多个/
                // videoPlayback.setVideoUrl("rtmp://"+ip+"/vod/"+videoPlayback.getCode()+"_"+videoPlayback.getFilename());//rtmp不允许多个/
                long duration = (detail.getFileEndDate().getTime() - detail.getFileStartDate().getTime()) / 1000;

                //将持续时间放入缓存
                DevicePlayBack devicePlayBack= hisVideoUdpServer.devices.get(iVideoPlayback.getCode());
                if(devicePlayBack==null){
                    devicePlayBack=new DevicePlayBack();
                    hisVideoUdpServer.devices.put(iVideoPlayback.getCode(),devicePlayBack);
                }
                devicePlayBack.setDuration(duration);
               /* JSONObject device = devices.get(iVideoPlayback.getCode());
                if (device == null)
                {
                    device=new JSONObject();
                    devices.put(iVideoPlayback.getCode(),device);
                }
                device.put("duration",duration);
*/
                videoPlayback.setDuration(duration);

                videoPlayback.setEndTime(detail.getFileEndDate());
                list.add(videoPlayback);
            }
            return getDataTable(list);
         }

        /**/

        //List<IVideoPlayback> list = new ArrayList<>();
        //return getDataTable(list);
    }


    /**
     * 查询t_video_playback（录像回放）列表
     */
    @ApiOperation("播放录像")
    //@PreAuthorize("@ss.hasPermi('service:arguments:list')")
    @GetMapping("/play")
    public AjaxResult play(HttpServletRequest request,IVideoPlayback videoPlayback,String openFFmpeg,String waiNet) {
        logger.info("播放"+videoPlayback.getCode());
        //播放清空旧的队列
        synchronized (hisVideoUdpServer.udpManager.getUpdQueue()){
            hisVideoUdpServer.udpManager.getUpdQueue().clear();
        }

        DeviceInfo deviceInfo=nwomServer.bridgeContext.deviceInfoMap.get(videoPlayback.getCode());
        if(deviceInfo==null){
            return  AjaxResult.error(videoPlayback.getCode()+"没上线！") ;

        }
       // String mediaIp=getMediaIp(deviceInfo);

        //获取设备回放对象
        DevicePlayBack devicePlayBack=hisVideoUdpServer.getDeviceByCode(videoPlayback.getCode());
        devicePlayBack.setHasReceivedData(false);
        devicePlayBack.setDuration(createDuration(videoPlayback.getFilename()));
        devicePlayBack.setPlaytime(0);
        devicePlayBack.setEnd(false);

        //设置接收到udp时的回调
        devicePlayBack.setUdpReceiveCallBack(new UdpReceiveCallBack() {
            @Override
            public void run(byte[] rcvBytes, DatagramPacket datagramPacket, Integer count) {
               if(devicePlayBack!=null&&!devicePlayBack.isHasReceivedData()){
                        log.info(videoPlayback.getCode()+"设备回复了平台！！！");
                        devicePlayBack.setHasReceivedData(true);//.put("responseable",true);
                        Date startPushTime=new Date();
                        devicePlayBack.setStartPushTime(startPushTime);
                        devicePlayBack.setPause(false);
                        log.info("更新hasReceivedData"+devicePlayBack.isHasReceivedData());
                        //1s后才计时，因为转发占了些时间，不然前端会2s开始
                       try {
                           Thread.sleep(1000);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       devicePlayBack.startRecordtime();
                }
            }
        });

        if(openFFmpeg!=null&&openFFmpeg.equals("0")) {
            log.info("不打开ffmpeg推rtmp ,用于测试用途 ,测试可以打开sdp文件");
        }
        else{
            String mediaIp=request.getServerName();
            rtpToRtmp(mediaIp,videoPlayback);
        }


        Cmd9AHRequest cmd9AHRequest=new Cmd9AHRequest();
        cmd9AHRequest.setChannelNo(1);
        //cmd9AHRequest.setProtocol(Contants.PROTOCAL_TCP);
        cmd9AHRequest.setProtocol(Contants.PROTOCAL_UDP);
        /*if(waiNet!=null&&waiNet.equals("1")){
            cmd9AHRequest.setIp(omServerProperties.getMediaServerIp()   );
        }
        else{*/
            //设备是在内网访问
            if(deviceInfo.getHost().contains("192.168")){
                cmd9AHRequest.setIp(omServerProperties.getMediaServerIpOnInNet());
            }
            else{
                cmd9AHRequest.setIp(omServerProperties.getMediaServerIp()   );
            }
            /*
            if(omServerProperties.getMediaServerIpOnInNet()!=null){//由于一个电脑可能有多个ip，应执行指定

            }
            else{
                cmd9AHRequest.setIp(SysUtil.getCurIp() );
            }*/
      //  }


        cmd9AHRequest.setPort(nwomServer.bridgeContext.getAgentConfig().getHisVideoPort());
        cmd9AHRequest.setSsrc(1000);
        String str=videoPlayback.getFilename().substring(2);
        String[] items=str.split("[_]");
        Date startDate = DateUtil.strToDate(items[0], "yyyyMMddHHmmss");
        Date endDate = DateUtil.strToDate(items[1], "yyyyMMddHHmmss");
        cmd9AHRequest.setStartDate(startDate);
        cmd9AHRequest.setEndDate(endDate);
        hisVideoUdpServer.udpManager.receivceCount =0;
        hisVideoUdpServer.udpManager.transmitCount =0;
        DeviceCommandResponse commandResponse=   nwomServer.bridgeContext.videoPlaybackService.beginVideoPlayback(videoPlayback.getCode(),cmd9AHRequest);
        if(!commandResponse.isSuccess()){
            return AjaxResult.error(commandResponse.getMessage());
        }
        return waitDeviceResponse(devicePlayBack,"播放成功！","播放超时，请联系管理员");
    }

    private byte[] changeTcpRtp2Rtp(byte[] receiveBytes) {
        byte[] rtpBytes = Arrays.copyOfRange(receiveBytes,  2,receiveBytes.length);
        return  rtpBytes;
    }


    /**
     * 将rtp包转换成tcp格式的RTP包，用于tcp传输
     *
     * @return
     */
    public byte[] changeRtp2TcpRtp(byte[] rtpPackBytes) {

        /*byte[] lenBytes = intTo2Bytes(rtpPackBytes.length);
        byte[] tcpBytes = new byte[rtpPackBytes.length + 4];//组成新的TCP包
        tcpBytes[0] = 0x24;//magic
        tcpBytes[1] = 0x00;//channel
        tcpBytes[2] = lenBytes[1];//len 高位
        tcpBytes[3] = lenBytes[0];//len 低位
        System.arraycopy(rtpPackBytes, 0, tcpBytes, 4, rtpPackBytes.length);
        return tcpBytes;*/

        //todo  金三立研发人员回复，是因为我们TCP包开头多了两个字节  24 00，到时出画面慢
        byte[] lenBytes = BasicUtil.intToBytes(rtpPackBytes.length,2);
        byte[] tcpBytes = new byte[rtpPackBytes.length + 2];//组成新的TCP包
        tcpBytes[0] = lenBytes[1];//len 高位
        tcpBytes[1] = lenBytes[0];//len 低位
        System.arraycopy(rtpPackBytes, 0, tcpBytes, 2, rtpPackBytes.length);
        return tcpBytes;

    }

    /**
     * 查询t_video_playback（录像回放）列表
     */
    @ApiOperation("播放录像")
    //@PreAuthorize("@ss.hasPermi('service:arguments:list')")
    @GetMapping("/stop")
    public AjaxResult stop(IVideoPlayback videoPlayback) {
        logger.info("停止"+videoPlayback.getCode()+"录像回放");
        Cmd9CHRequest cmd9CHRequest=new Cmd9CHRequest();
        cmd9CHRequest.setChannelNo(1);
        cmd9CHRequest.setSsrc(1000);

        hisVideoUdpServer.devices.remove(videoPlayback.getCode());
        //devices.remove(videoPlayback.getCode());

        ShellCmdUtil shellCmdUtil=new ShellCmdUtil();
        shellCmdUtil.killProcessByName("ffmpeg");
        DeviceCommandResponse commandResponse=   nwomServer.bridgeContext.videoPlaybackService.endVideoPlayback(videoPlayback.getCode(),cmd9CHRequest);
        if(!commandResponse.isSuccess()){
            return AjaxResult.error(commandResponse.getMessage());
        }
        return AjaxResult.success("停止成功！");
    }


    /**
     * 查询t_video_playback（录像回放）列表
     */
    @ApiOperation("控制录像")
    //@PreAuthorize("@ss.hasPermi('service:arguments:list')")
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public AjaxResult download(HttpServletRequest request,IVideoPlayback videoPlayback) {
        logger.info("下载"+videoPlayback.getCode()+"录像回放");
        DeviceInfo deviceInfo=nwomServer.bridgeContext.deviceInfoMap.get(videoPlayback.getCode());
        if(deviceInfo==null){
            return  AjaxResult.error(videoPlayback.getCode()+"没上线！") ;
        }
        //保存数据
        iVideoPlaybackService.insertIVideoPlayback(videoPlayback);
        //保存成文件
        String str=videoPlayback.getFilename().substring(2);
        /*String mediaIp=getMediaIp(deviceInfo);*/
        String mediaIp=request.getServerName();
        rtpToRtmp(mediaIp,videoPlayback,"c:\\omserver_res\\playback\\"+str+".flv");
        //发送指令
        Cmd9AHRequest cmd9AHRequest=new Cmd9AHRequest();
        cmd9AHRequest.setChannelNo(1);
        cmd9AHRequest.setProtocol(Contants.PROTOCAL_UDP);
        String[] items=str.split("[_]");
        Date startDate = DateUtil.strToDate(items[0], "yyyyMMddHHmmss");
        cmd9AHRequest.setStartDate(startDate);
        Date endDate = DateUtil.strToDate(items[1], "yyyyMMddHHmmss");
        cmd9AHRequest.setEndDate(endDate);

        cmd9AHRequest.setIp(mediaIp);
        cmd9AHRequest.setPort(nwomServer.bridgeContext.getAgentConfig().getHisVideoPort());
        cmd9AHRequest.setSsrc(1000);
        DeviceCommandResponse commandResponse=   nwomServer.bridgeContext.videoPlaybackService.beginVideoPlayback(videoPlayback.getCode(),cmd9AHRequest);
        if(!commandResponse.isSuccess()){
            return AjaxResult.error(commandResponse.getMessage());
        }
        //return AjaxResult.success("播放成功！");
        return AjaxResult.success("下载中！");
    }

    private String getMediaIp(DeviceInfo deviceInfo) {
        String mediaIp="";
        if(omServerProperties.getMediaServerIp()!=null){
            mediaIp= omServerProperties.getMediaServerIp();
        }
        else{
            mediaIp=SysUtil.getCurIp();
            //适应局域网 ,由于拿当前ip时可能取了46的，也不知设备是10段的，还是46段的
            if(deviceInfo.getHost().startsWith("192.168.10")){
                mediaIp=mediaIp.replace("192.168.46","192.168.10");
            }
            if(deviceInfo.getHost().startsWith("192.168.46")){
                mediaIp=mediaIp.replace("192.168.10","192.168.46");
            }
        }

        return  mediaIp;
    }

    /**
     * 查询t_video_playback（录像回放）列表
     */
    @ApiOperation("控制录像")
    //@PreAuthorize("@ss.hasPermi('service:arguments:list')")
    @RequestMapping(value = "/control",method = RequestMethod.GET)
    public AjaxResult control(HttpServletRequest request,IVideoPlayback videoPlayback,String offset,String scale) {
        logger.info("调节"+videoPlayback.getCode()+"录像回放");
        //播放清空旧的队列
        synchronized (hisVideoUdpServer.udpManager.getUpdQueue()){

            hisVideoUdpServer.udpManager.getUpdQueue().clear();
        }
        nwomServer.bridgeContext.hisVideoUdpServer.receivceCount =0;
        nwomServer.bridgeContext.hisVideoUdpServer.transmitCount =0;

        DeviceInfo deviceInfo=nwomServer.bridgeContext.deviceInfoMap.get(videoPlayback.getCode());
        if(deviceInfo==null){
            return  AjaxResult.error(videoPlayback.getCode()+"没上线！") ;
        }
      //  String mediaIp=getMediaIp(deviceInfo);
        String mediaIp=request.getServerName();

        DevicePlayBack devicePlayBack=hisVideoUdpServer.getDeviceByCode(videoPlayback.getCode());
        long duration = createDuration(videoPlayback.getFilename());
        devicePlayBack.setDuration(duration);
        devicePlayBack.setEnd(false);
        devicePlayBack.setPlaytime(Integer.parseInt(offset));
        //devicePlayBack.setResponseable(false);
        //开启时
        if(!scale.equals("0")){
            //若之前不是停止，先停止
            /*if(!devicePlayBack.isPause()){
                //停止计时，推流
                devicePlayBack.recordtimeFlg=false;
                Cmd9BHRequest cmd9BHRequest=new Cmd9BHRequest().builder()
                        .channelNo(1)
                        .errorCode(0)
                        .offset(Integer.parseInt(offset))
                        .scale(0)
                        .ssrc(1000).build();
                DeviceCommandResponse<Cmd9BHResponse> response=nwomServer.bridgeContext.videoPlaybackService.controlVideoPlayback(
                        videoPlayback.getCode(),cmd9BHRequest
                );
                //等1秒完全停  //由于区分不了是移动前推的的流还是移动后推的流
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            rtpToRtmp(mediaIp, videoPlayback);
             devicePlayBack.setHasReceivedData(false);
            //接收到数据时恢复运行
            devicePlayBack.setUdpReceiveCallBack(new UdpReceiveCallBack() {
                @Override
                public void run(byte[] rcvBytes, DatagramPacket datagramPacket, Integer count) {
                    if(!devicePlayBack.isHasReceivedData()){
                        log.info(videoPlayback.getCode()+"恢复运行！");
                        devicePlayBack.setPause(false);
                        devicePlayBack.setHasReceivedData(true);
                    }
                }
            });

        }
        //暂停时
        else{
            //关闭ffmpeg
            new Thread(()-> {
                ShellCmdUtil shellCmdUtil = new ShellCmdUtil();
                shellCmdUtil.killProcessByName("ffmpeg");
            }).start();
            devicePlayBack.setHasReceivedData(true);
            devicePlayBack.setPause(true);
        }

        Cmd9BHRequest cmd9BHRequest=new Cmd9BHRequest().builder()
                .channelNo(1)
                .errorCode(0)
                .offset(Integer.parseInt(offset))
                .scale(Float.parseFloat(scale))
                .ssrc(1000).build();
        DeviceCommandResponse<Cmd9BHResponse> response=nwomServer.bridgeContext.videoPlaybackService.controlVideoPlayback(
                videoPlayback.getCode(),cmd9BHRequest
        );
        if(!response.isSuccess()){
            return AjaxResult.error(response.getMessage());
        }
        return waitDeviceResponse(devicePlayBack,"调节成功！","调节失败！");
    }

    private long createDuration(String filename) {
        String str=filename.substring(2);
        String[] items=str.split("[_]");
        Date startDate = DateUtil.strToDate(items[0], "yyyyMMddHHmmss");
        Date endDate = DateUtil.strToDate(items[1], "yyyyMMddHHmmss");
        return (endDate.getTime() - startDate.getTime()) / 1000;
    }

    private AjaxResult waitDeviceResponse(DevicePlayBack devicePlayBack,String succesMsg,String errorMsg) {
        int count=0;
        while(true){
            //log.info(responseable.optString("responseable"));
            if(devicePlayBack.isHasReceivedData()){
                //return AjaxResult.error("播放失败！");
                return AjaxResult.success(succesMsg);//"播放成功！！"
            }
            else {
                try {
                    if(count>300){//等待超过30秒
                        return AjaxResult.error(errorMsg);//"播放失败，请联系管理员！"
                    }
                    Thread.sleep(100);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void rtpToRtmp(String mediaIp, IVideoPlayback videoPlayback) {
        rtpToRtmp(mediaIp,videoPlayback,null);
    }
    private void rtpToRtmp(String mediaIp, IVideoPlayback videoPlayback,String fileName) {
       // String ip= SysUtil.getCurIp();

        new Thread(()->{
            ShellCmdUtil shellCmdUtil=new ShellCmdUtil();
            shellCmdUtil.killProcessByName("ffmpeg");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                //-b v 码率
                //-c:v 编码器
                //-g gob
                //-preset ultrafast preset的参数主要调节编码速度和质量的平衡，有ultrafast、superfast、veryfast、faster、fast、medium、slow、slower、veryslow、placebo这10个选项，从快到慢。 // -r 15
                if(fileName!=null&&!fileName.equals("")){
                    //1s = 1000000μs  -rw_timeout 30000000 //会即刻闪退无效力
                    shellCmdUtil.exec(" cmd  /c start C:\\omserver_res\\ffmpeg\\bin\\ffmpeg  -i c:\\omserver_res\\h264.sdp  -c:v libx264 -preset ultrafast -b:v 2048k -maxrate 3000k -bufsize 1600   -g 100 " +
                            "  -threads 4 -f flv "+fileName );
                }
                else{
                    shellCmdUtil.exec(" cmd /c start C:\\omserver_res\\ffmpeg\\bin\\ffmpeg    -i c:\\omserver_res\\h264.sdp  -c:v libx264 -preset ultrafast -b:v 2048k -maxrate 3000k -bufsize 1600   -g 100 " +
                            "  -threads 4 -f flv rtmp://"+mediaIp+":"+omServerProperties.getRtmpServerPort()+"/live/"+videoPlayback.getCode()+"_"+videoPlayback.getFilename() );
                }
                //start "" - 启动新窗口是不显示窗口的标题；
                // /c 表示，这个cmd一旦被调用，程序不用等待cmd后面的内容执行完毕，就返回到调用start的环境里
                // shellCmdUtil.exec(" cmd /c start C:\\omserver_res\\ffmpeg\\bin\\ffmpeg    -i c:\\omserver_res\\h264.sdp  -r 15 -c:v libx264 -preset ultrafast -b:v 3000k -maxrate 3000k -bufsize 600   -g 20 " +
                //              "  -threads 2 -f flv rtmp://"+ip+"/vod/"+videoPlayback.getCode()+"_"+videoPlayback.getFilename() );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();




       /* try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }//testmode
        new Thread(()->{
            ShellCmdUtil shellCmdUtil=new ShellCmdUtil();
            try {
                shellCmdUtil.exec("cmd /c start ffmpeg -r 15  -re -i rtsp://admin:a123456789@192.168.10.4 -vcodec copy -tune:v zerolatency -threads 2   -f rtp rtp://192.168.10.32:8011?pkt_size=1440");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/

    }


    /**
     * 获取t_video_playback（录像回放）详细信息
     */
    @ApiOperation("获取录像回放详细")
    //@PreAuthorize("@ss.hasPermi('service:arguments:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        IVideoPlayback playback = iVideoPlaybackService.selectIVideoPlaybackById(id);

        return AjaxResult.success(playback);
        //       return AjaxResult.success();
    }

    /**
     * 新增t_video_playback（录像回放）
     */
    @ApiOperation("新增录像回放")
    //@PreAuthorize("@ss.hasPermi('service:arguments:add')")
    @Log(title = "t_video_playback（录像回放）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IVideoPlayback iVideoPlayback) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        iVideoPlayback.setCreateUser(String.valueOf(userId));
         return toAjax(iVideoPlaybackService.insertIVideoPlayback(iVideoPlayback));
   //     return AjaxResult.success();
    }

    /**
     * 修改t_video_playback（录像回放）
     */
    @ApiOperation("修改录像回放")
    //@PreAuthorize("@ss.hasPermi('service:arguments:edit')")
    @Log(title = "t_video_playback（录像回放）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IVideoPlayback iVideoPlayback) {
       return toAjax(iVideoPlaybackService.updateIVideoPlayback(iVideoPlayback));
        //      return AjaxResult.success();
    }

    /**
     * 删除t_video_playback（录像回放）
     */
    @ApiOperation("删除录像回放")
    //@PreAuthorize("@ss.hasPermi('service:arguments:remove')")
    @Log(title = "t_video_playback（录像回放）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
       return toAjax(iVideoPlaybackService.deleteIVideoPlaybackById(id));
        //       return AjaxResult.success();
    }


    /**
     * 删除t_video_playback（录像回放）
     */
    @ApiOperation("获取当前录像时间")
    @Log(title = "t_video_playback（录像回放）")
    @GetMapping("/getCurPlayTime")
    public AjaxResult getCurTime(String code) {
        JSONObject o=new JSONObject();
        o.put("code",code);
        DevicePlayBack devicePlayBack=hisVideoUdpServer.devices.get(code);
        if(devicePlayBack!=null){
           // Date startPushTime= (Date) devicePlayBack.getStartPushTime();//.get("startPushTime");*//*
            long duration=devicePlayBack.getDuration();
            long playtime=devicePlayBack.getPlaytime();
            if(playtime<=duration){
                o.put("nowtimes",playtime);
                o.put("status",1);//播放中
            }
            else
            {
               // o.put("nowtimes",playtime);
                o.put("status",2);//已完成
            }
        } else{
            o.put("status",0);//未播放
        }
        /*JSONObject device=devices.get(code);
        if(device!=null){*//*
            Date startPushTime= (Date) device.get("startPushTime");*//*
            long duration=device.optLong("duration");
            long playtime=device.optLong("playtime");
            if(playtime<=duration){
                o.put("nowtimes",playtime);
                o.put("status",1);//播放中
            }
            else
            {
                o.put("nowtimes",playtime);
                o.put("status",2);//已完成
            }
        }
        else{
            o.put("status",0);//未播放
        }*/
        return AjaxResult.success(o);
        //       return AjaxResult.success();
    }




}


//先接收数据 //先缓冲起来，避免丢失 //只支持同时一个看
        /*UdpManager hisVideoUdpServer = nwomServer.bridgeContext.hisVideoUdpServer;
        BridgeContext bridgeContext = nwomServer.bridgeContext;
        if(hisVideoUdpServer ==null){
            hisVideoUdpServer=new UdpManager();
            hisVideoUdpServer.receivceCount =0;
            hisVideoUdpServer.transmitCount =0;
            nwomServer.bridgeContext.hisVideoUdpServer=hisVideoUdpServer;
            hisVideoUdpServer.init(mediaIp,nwomServer.bridgeContext.getAgentConfig().getHisVideoPort());//ip要设上，因为有时一台机有多个ip
            hisVideoUdpServer.receive(new UdpReceiveCallBack() {
                @Override
                public void run(byte[] rcvBytes, DatagramPacket datagramPacket, Integer receivceCount) {

                    if(receivceCount%100==0){
                        log.info("接收到了"+receivceCount+"个");
                    }
                    *//*try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*//*
                    if(!responseable.optBoolean("responseable")){
                        responseable.put("responseable",true);
                        Date startPushTime=new Date();

                        JSONObject device=devices.get(videoPlayback.getCode());
                        device.put("startPushTime",startPushTime);
                        device.put("stop",false);
                        log.info("更新responseable"+responseable.optString("responseable"));




                        new Thread(()->{
                            while (true){
                               // JSONObject device=devices.get(videoPlayback.getCode());

                                while (true){
                                    boolean stop=device.optBoolean("stop");
                                    if(!stop){
                                        break;
                                    }
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                long playtime=device.optLong("playtime");
                                playtime+=1;
                                device.put("playtime",playtime);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        }).start();
                    }
                }
            });
            UdpManager finalHisVideoUdpServer = hisVideoUdpServer;


            hisVideoUdpServer.transmit(mediaIp,8012,new UdpTransmitCallBack(){

                @Override
                public void run(byte[] rcvBytes, DatagramSocket datagramSocket, DatagramPacket udpSendPacket, Integer count) {
                    if(finalHisVideoUdpServer.transmitCount%100==0){
                        log.info("【=>"+udpSendPacket.getAddress()+":"+udpSendPacket.getPort()+" 转发数据数"+finalHisVideoUdpServer.transmitCount+"】");
                    }

                }
            });

        }*/

        /*TcpServer hisVideoTcpServer = nwomServer.bridgeContext.hisVideoTcpServer;
        if(hisVideoTcpServer ==null){
            //创建TCPServer
            hisVideoTcpServer=new TcpServer(nwomServer.bridgeContext.getAgentConfig().getHisVideoPort());
            nwomServer.bridgeContext.hisVideoTcpServer=hisVideoTcpServer;
            TcpServer finalHisVideoTcpServer = hisVideoTcpServer;
            new Thread(()-> {
                finalHisVideoTcpServer.init(new TcpReceiveCallBack() {
                    @Override
                    public void run(byte[] receiveBytes, Socket socket) throws IllegalAccessException, InstantiationException {
                        receiveBytes=changeTcpRtp2Rtp(receiveBytes);//变成udp
                        rtpQueues.add(new UdpData(socket.getInetAddress().getHostName(),socket.getPort(),receiveBytes));
                    }
                });
            }).start();

            new Thread(()->{
                //转成rtmp
                try {
                    SocketCom   socketCom=new SocketCom(ip,8012);
                    socketCom.genUdpSocketToBindIPPort();

                    while (true){
                        if(! rtpQueues.isEmpty()&& rtpQueues.size()>0){
                            UdpData udpData= rtpQueues.poll();
                            byte[] bytes=udpData.getCmdBytes();
                              socketCom.sendUdpInfo(bytes,false);

                        }else{

                            Thread.sleep(1);
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        }*/