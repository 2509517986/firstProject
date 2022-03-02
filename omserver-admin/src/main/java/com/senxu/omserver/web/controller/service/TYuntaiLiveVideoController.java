package com.senxu.omserver.web.controller.service;

import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.utils.file.FileUtils;
import com.senxu.omserver.service.domain.TFunctionParameters;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.domain.TYuntaiPresetPosition;
import com.senxu.omserver.service.service.*;
import com.senxu.omserver.web.enums.VideoProtocol;
import com.senxu.omserver.web.model.DeviceNowVideo;
import com.senxu.omserver.web.server.NowVideoServer;
import com.senxu.omserver.web.server.NowVideoTcpServer;
import com.senxu.omserver.web.server.NowVideoUdpServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import senxu.com.base.dto.DeviceInfo;
import senxu.com.base.util.*;
import senxu.com.cmd.response.Cmd0AHResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * t_yuntai_video（云台短视频）Controller
 *
 * @author panyc
 * @date 2021-06-11
 */
@Slf4j
@Api(tags = "云台实时视频")
@RestController
@RequestMapping("/service/liveVideo")
public class TYuntaiLiveVideoController extends BaseController
{
    /* @Autowired
     private ITYuntaiVideoService tYuntaiVideoService;
 */
    @Autowired
    private NWOMServer nwomServer;

    @Autowired
    private NowVideoTcpServer nowVideoTcpServer;
    @Autowired
    private NowVideoUdpServer nowVideoUdpServer;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;

    @Autowired
    private ITYuntaiPresetPositionService yuntaiPresetPositionService;

    @Autowired(required = false)
    private TFunctionParametersService tFunctionParametersService;

    @Autowired
    private OmServerProperties omServerProperties;
  /*  private static LinkedHashMap<Integer,Boolean> transVideoPorts =new LinkedHashMap();//10000;

    static {
        for (int i = 0; i < 30; i++) {
            transVideoPorts.put(10000+i,false);
        }
    }*/


    @ApiOperation("查询云台视频信息")
    //@PreAuthorize("@ss.hasPermi('service:liveVideo:getVideoInfo')")
    @GetMapping("/getVideoInfo")
    public AjaxResult getVideoInfo(@ApiParam(name = "id", value = "云台id", required = true)
                                   @RequestParam Long id, HttpServletRequest request)
    {
        Object data=new JSONObject();
        JSONObject dataJ=(JSONObject) data;
        TYuntaiInfo tYuntaiInfo= tYuntaiInfoService.selectTYuntaiInfoById(id);
        String waiNet=request.getParameter("waiNet");

       /*
       TFunctionParameters tFunctionParameters = tFunctionParametersService.selectTFunctionParametersByYuntaiId(id);
       if(tFunctionParameters == null){
           TFunctionParameters parameters = new TFunctionParameters(0,0,0,0,0,id);
           tFunctionParametersService.insertTFunctionParameters(parameters);
           dataJ.put("tFunctionParameters",parameters);
       }else {
           dataJ.put("tFunctionParameters",tFunctionParameters);
       }*/
        TFunctionParameters tFunctionParameters = new TFunctionParameters();
        DeviceCommandResponse<Cmd0AHResponse> response = null;
        try {
            response = nwomServer.getDeviceBridge().Cmd0AH(tYuntaiInfo.getCode());
        }catch (Exception e){
            return AjaxResult.error("启动失败");
        }
        Cmd0AHResponse cmd0AHResponse= response.getCmdResponse();
        tFunctionParameters.setBrightness(cmd0AHResponse.getPictureBrightness1());//通道1图像亮度
        tFunctionParameters.setContrast(cmd0AHResponse.getPictureContrast1());//通道1对比度
        tFunctionParameters.setSaturation(cmd0AHResponse.getPictureSaturation1()); //通道1饱和度
        tFunctionParameters.setPictureColor1(cmd0AHResponse.getPictureColor1());//通道1色彩选择
        tFunctionParameters.setPictureResolution1(cmd0AHResponse.getPictureResolution1());//通道1图像大小（分辨率）

        //视窗加热，雨刮在库中取（协议没有该内容）
        TFunctionParameters tFunctionParametersDB = tFunctionParametersService.selectTFunctionParametersByYuntaiId(id);
        if( tFunctionParametersDB!=null){
            tFunctionParameters.setWiper(tFunctionParametersDB.getWiper());
            tFunctionParameters.setWindowsHeat(tFunctionParametersDB.getWindowsHeat());
        }

        dataJ.put("tFunctionParameters",tFunctionParameters);

        String ip = request.getServerName();//.get"192.168.10.33";
        //由于前面调用时是用内网地址，导致这里也会是内网地址，这里应根据情况拿媒体播放地址
        //当是外网地址时，从服务器中取
         if(waiNet!=null&&!waiNet.equals("")){
            ip=omServerProperties.getMediaServerIp();
        }
        dataJ.put("videoUrl", "rtmp://" + ip + ":"+omServerProperties.getRtmpServerPort()+"/live/" +tYuntaiInfo.getCode());
        return AjaxResult.success(dataJ);
    }

    @ApiOperation("发送播放命令")
    //@PreAuthorize("@ss.hasPermi('service:liveVideo:getVideoInfo')")
    @GetMapping("/sendPlayCmd")
    @SneakyThrows
    public  AjaxResult sendPlayCmd( @ApiParam( value = "设备编码", required = true,defaultValue = "SX1002")
                                    @RequestParam String deviceCode, String videoProtocol,String openFFmpeg,HttpServletRequest request, HttpServletResponse response) {
        log.info("发送播放命令,设备编号:{}",deviceCode);
        String waiNet=request.getParameter("waiNet");

        DeviceInfo deviceInfo= nwomServer.bridgeContext.deviceInfoMap.get(deviceCode);
        if(deviceInfo==null){
            return AjaxResult.error(deviceCode+"没上线！") ;
        }
        int protocol=0;//默认udp TODO::

        String transIp = omServerProperties.getMediaServerIpOnInNet();

        //创建sdp文件
        // File file=new File()
        omServerProperties.mediaProtocol="tcp";
        NowVideoServer nowVideoServer=null;


       /* transVideoPort++;
        if(transVideoPort>11000){
            transVideoPort=10000;
        }*/
       //Integer transVideoPort=getFreeTransPort();

        if(videoProtocol==null||videoProtocol.equals("")){
            videoProtocol="udp";
        }
        if(videoProtocol.equals("udp")){
            protocol=0;
            nowVideoServer=nowVideoUdpServer;
        }
        else{
            protocol=1;
            nowVideoServer=nowVideoTcpServer;
        }
        SocketCom   socketCom=null;
        Integer transVideoPort=10000;
        for (int i = 0; i < 50; i++) {
            transVideoPort+=1;
            try{
                socketCom= new SocketCom(omServerProperties.getMediaServerIpOnInNet(),transVideoPort);
                boolean result=socketCom.genUdpSocketToBindIPPort();
                if(result){
                    log.info("使用"+transVideoPort+"端口");
                    break;
                }
                else
                {
                    log.info(transVideoPort+"端口已占用！");

                }
            }
            catch (Exception ex){
                log.info(transVideoPort+"端口已占用！");
            }

        }

        //获取设备回放对象
        DeviceNowVideo deviceNowVideo=nowVideoServer.getDeviceByCode(deviceCode);
        deviceNowVideo.setTransVideoPort(transVideoPort);
        //设置接收到udp时的回调
        deviceNowVideo.receiveNum=0;
        deviceNowVideo.startTransmit(socketCom);
        //rtp转rtmp
        rtpToRtmp(transIp,deviceNowVideo);


        String targetIp;
        /*if(waiNet!=null&&!waiNet.equals("")){
            targetIp=omServerProperties.getMediaServerIp();
        }
        else{
            targetIp=omServerProperties.getMediaServerIpOnInNet();
        }
        */
        //设备是内网就用内网，外网就用外网ip
        if(deviceInfo.getHost().contains("192.168")){
            targetIp=omServerProperties.getMediaServerIpOnInNet();
        }
        else{
            targetIp=omServerProperties.getMediaServerIp();
        }
        DeviceCommandResponse response1 = nwomServer.getDeviceBridge().startPlayRealtimeVideo(deviceCode, 1, 1, protocol, targetIp, omServerProperties.videoPort);
        if (!response1.isSuccess() && response1.getResponseCode() == 500) {
            log.error("播放失败!", response1.getMessage());
            // throw  new Exception("播放失败，设备不在线！");
            return AjaxResult.error(response1.getMessage());
        }


        return AjaxResult.success();
    }

   /* private Integer getFreeTransPort() {
        for (int i = 0; i < transVideoPorts.size(); i++) {
            boolean use=transVideoPorts.get(i);
            if(use){
                continue;
            }
            else{
                transVideoPorts.put(i,true);
                return i;
            }
        }

        return null;
    }
*/

    public   void rtpToRtmp(String mediaIp,DeviceNowVideo deviceNowVideo) {

        new Thread(()->{
            //收到视频数据，才调起转换
            while(deviceNowVideo.receiveNum==0){
                sleep(50);
            }
            ShellCmdUtil shellCmdUtil=new ShellCmdUtil();
            shellCmdUtil.killProcessByName("h264_live_"+deviceNowVideo.getDeviceCode()+".sdp");
            sleep(30);
            //创建sdp文件
            File file=new File("c:\\omserver_res\\h264_live_"+deviceNowVideo.getDeviceCode()+".sdp");
            if(file.exists()){
                file.delete();
            }


            String content="v=0\n" +
                    "o=- 0 0 IN IP4 "+omServerProperties.getMediaServerIpOnInNet()+"\n" +
                    "s=No Name\n" +
                    "c=IN IP4 "+omServerProperties.getMediaServerIpOnInNet()+"\n" +
                    "t=0 0\n" +
                    "a=tool:libavformat 57.83.100\n" +
                    "m=video "+ deviceNowVideo.transVideoPort +" RTP/AVP 96\n" +
                    "a=rtpmap:96 H264/90000\n" +
                    "a=fmtp:96 profile-level-id=420029; packetization-mode=1; sprop-parameter-sets=Z00AKp2oHgCJ+WbgICAoAAADAAgAAAMA9CA=,aO48gA==";
            try {
                FileUtils.writeStringToFile(file,content,"utf-8");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                //-b v 码率
                //-c:v 编码器
                //-g gob
                //-preset ultrafast preset的参数主要调节编码速度和质量的平衡，有ultrafast、superfast、veryfast、faster、fast、medium、slow、slower、veryslow、placebo这10个选项，从快到慢。 // -r 15

                Process process=shellCmdUtil.exec(" cmd /c start C:\\omserver_res\\ffmpeg\\bin\\ffmpeg    -i c:\\omserver_res\\h264_live_"+deviceNowVideo.getDeviceCode()+".sdp  -c:v libx264 -preset ultrafast -b:v 2048k -maxrate 3000k -bufsize 1600   -g 100 " +
                            "  -threads 2 -f flv rtmp://"+mediaIp+"/live/"+deviceNowVideo.getDeviceCode() ,true );
//-vcodec copy -tune:v zerolatency -threads 2 -f flv
                deviceNowVideo.process=process;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();



    }

    private void sleep(int millsecond) {
        try {
            Thread.sleep(millsecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("发送停止播放命令")
    //@PreAuthorize("@ss.hasPermi('service:liveVideo:sendStopCmd')")
    @GetMapping("/sendStopCmd")
    @SneakyThrows
    public  AjaxResult sendStopCmd(
            @ApiParam( value = "设备编码", required = true,defaultValue = "SX1002")
            @RequestParam  String deviceCode,HttpServletRequest request, HttpServletResponse response) {
        log.info("发送停止播放命令");
        DeviceNowVideo deviceNowVideo;
        if(omServerProperties.mediaProtocol.equals(VideoProtocol.udp.toString())){
            deviceNowVideo=nowVideoUdpServer.getDeviceByCode(deviceCode);
        }
        else{
            deviceNowVideo=nowVideoTcpServer.getDeviceByCode(deviceCode);
        }
        String closeFFmpegOnly=request.getParameter("closeFFmpegOnly");//为调试用
        if(deviceNowVideo.process!=null){

           // deviceNowVideo.process.getOutputStream().write("q".getBytes());
           // deviceNowVideo.process.getOutputStream().flush();
             deviceNowVideo.process.destroy();
        }
       // transVideoPorts.put(deviceNowVideo.getTransVideoPort(),false);
        if(closeFFmpegOnly!=null&&closeFFmpegOnly.equals("1")){
            return AjaxResult.success();
        }
     //  ShellCmdUtil shellCmdUtil=new ShellCmdUtil();
      //  shellCmdUtil.killProcessByName("h264_live_"+deviceCode+".sdp");
        sleep(30);
        String ip = request.getServerName();
        /*if (ip.equals("localhost")) {
            ip = SysUtil.getCurIp();
        }*/
        //public DeviceCommandResponse endPlayRealtimeVideo(String deviceCode, int channelNo, int codeType, int ssrc) {
        nwomServer.getDeviceBridge().endPlayRealtimeVideo(deviceCode, 1, 1);
        return AjaxResult.success();
    }

    /**
     * 远程控制
     *
     * @param request
     * @param response
     */
    @ApiOperation("发送远程控制命令")
    //@PreAuthorize("@ss.hasPermi('service:liveVideo:remoteControl')")
    @GetMapping("/remoteControl")
    @SneakyThrows
    public   AjaxResult remoteControl(
            @ApiParam( value = "命令【1\t0\t打开摄像机电源\n" +
                    "2\t所需预置点\t摄像机调节到指定预置点\n" +
                    "3\t0\t向上调节1个单位\n" +
                    "4\t0\t向下调节1个单位\n" +
                    "5\t0\t向左调节1个单位\n" +
                    "6\t0\t向右调节1个单位\n" +
                    "7\t0\t焦距向远方调节1个单位（镜头变倍放大）\n" +
                    "8\t0\t焦距向近处调节1个单位\n" +
                    "（镜头变倍缩小）\n" +
                    "9\t所需设置预置点\t保存当前位置为某预置点\n" +
                    "10\t关闭摄像机电源\t\n" +
                    "11\t\t光圈放大1个单位\n" +
                    "12\t\t光圈缩小1个单位\n" +
                    "13\t\t聚焦增加1个单位\n" +
                    "14\t\t聚焦减少1个单位\n" +
                    "15\t巡航号\t开始巡航\n" +
                    "16\t巡航号\t停止巡航\n" +
                    "17\t辅助开关号\t打开辅助开关\n" +
                    "18\t辅助开关号\t关闭辅助开关\n" +
                    "19\t0\t开始自动扫描\n" +
                    "20\t0\t停止自动扫描\n" +
                    "21\t0\t开始随机扫描\n" +
                    "22\t0\t停止随机扫描\n" +
                    "23\t0\t红外灯全开\n" +
                    "24\t0\t红外灯半开\n" +
                    "25\t0\t红外灯关闭\n" +
                    "26 \t所需预置位号\t删除预置位号\n" +
                    "27\t0\t设置自动扫描左边界\n" +
                    "28\t0\t设置自动扫描右边界\n" +
                    "29\t0\t设置自动扫描速度（1-10量化）\n" +
                    "30\t每单位的步长\t设置云台上下左右调节时，“1个单位”的步长。\n" +
                    "31\t巡检组号\t开始巡检\n" +
                    "32\t巡检组号\t停止巡检\n" +
                    "48\t0\t停止摄像机动作\n" +
                    "49\t速度\t开始摄像机向上运动\n" +
                    "50\t速度\t开始摄像机向下运动\n" +
                    "51\t速度\t开始摄像机向左运动\n" +
                    "52\t速度\t开始摄像机向右运动\n" +
                    "53\t速度\t开始摄像机向左上运动\n" +
                    "54\t速度\t开始摄像机向右上运动\n" +
                    "55\t速度\t开始摄像机向左下运动\n" +
                    "56\t速度\t开始摄像机向右下运动\n" +
                    "57\t0\t开始摄像机焦距向远方调节（镜头变倍放大）\n" +
                    "58\t0\t开始摄像机焦距向近处调节（镜头变倍缩小）\n" +
                    "59\t速度\t开始摄像机光圈放大\n" +
                    "60\t速度\t开始摄像机光圈缩小\n" +
                    "61\t速度\t开始摄像机聚焦增加\n" +
                    "62\t速度\t开始摄像机聚焦减少】", required = true,defaultValue = "0")
                    String cmd,
            @ApiParam( value = "参数值" ,defaultValue = "")
                    String param,
            @ApiParam( value = "设备编码", required = true,defaultValue = "SX1002",example = "SX1002")
                    String deviceCode,HttpServletRequest request, HttpServletResponse response) {
        /*String cmd = request.getParameter("cmd");
        String param = request.getParameter("param");
        String deviceCode = request.getParameter("deviceCode");*/
        TYuntaiInfo tYuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoByCode(deviceCode);
        Integer paramI = 0;
        if (param != null && !param.isEmpty()) {
            paramI = Integer.parseInt(param);
        }
        if (cmd.contains("_")) {
            String[] cmds = cmd.split("_");
            for (int i = 0; i < cmds.length; i++) {
                Integer cmdI = Integer.parseInt(cmds[i]);
                try {
                    nwomServer.getDeviceBridge().remoteModulation(deviceCode, tYuntaiInfo.getPasword(), 1, cmdI, paramI);
                } catch (Exception e) {
                    throw new Exception("远程控制失败！");
                }
            }
            return AjaxResult.success();
        } else {
            Integer cmdI = Integer.parseInt(cmd);
            try {
                nwomServer.getDeviceBridge().remoteModulation(deviceCode, tYuntaiInfo.getPasword(), 1, cmdI, paramI);

            } catch (Exception e) {
                throw new Exception("远程控制失败！");
            }

            //如果是预置位，保存数据库
            if (cmdI == 9) {
               /* JSONObject o = new JSONObject().put("device_code", deviceCode).put("preset", paramI);
                o.put("id", SysUtil.getId());
                com.sxty.commons.web.util.DbUtil.insertOrUpdate("audit_device_preset_set", o, new String[]{"device_code", "preset"}, null, null);
          */
                TYuntaiPresetPosition selectYuntaiPresetPosition=new TYuntaiPresetPosition();
                selectYuntaiPresetPosition.setYuntaiId(tYuntaiInfo.getId());
                selectYuntaiPresetPosition.setPresetBit(param);
                TYuntaiPresetPosition  yunTaiPresetPosition= yuntaiPresetPositionService.selectTYuntaiPresetPosition(selectYuntaiPresetPosition);
                if(yunTaiPresetPosition!=null){

                    yuntaiPresetPositionService.updateTYuntaiPresetPosition(yunTaiPresetPosition);
                }
                else{
                    yuntaiPresetPositionService.insertTYuntaiPresetPosition(selectYuntaiPresetPosition);
                }
            }
            if (cmdI == 26) {
                yuntaiPresetPositionService.deleteTYuntaiPresetPositionById(new Long(paramI.intValue()));
            }
            return AjaxResult.success();
        }

    }


    /**
     * 设置图像参数
     * @param request
     * @param response
     */
    @ApiOperation("设置图像参数")
    //@PreAuthorize("@ss.hasPermi('service:liveVideo:setPicParams')")
    @GetMapping("/setPicParams")
    @SneakyThrows
    public   AjaxResult setPicParams(HttpServletRequest request, HttpServletResponse response) {

       /* String deviceCode=request.getParameter("deviceCode");
        String brightnessS=request.getParameter("brightness");
        String contrastS=request.getParameter("contrast");
        String saturationS=request.getParameter("saturation");
        int brightness=Integer.parseInt(brightnessS);
        int contrast=Integer.parseInt(contrastS);
        int saturation=Integer.parseInt(saturationS);

        DeviceCommandResponse response1=NWOMServer.getDeviceBridge().setPicParams(deviceCode,"1234",brightness,contrast,saturation);
        if(response1.isSuccess()){
            setSuccess(request);
        }
        else{
            setError(request,response1.getMessage());
        }
        sendResult(request,response);*/
        return AjaxResult.success();
    }


}
