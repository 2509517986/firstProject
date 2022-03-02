package com.senxu.omserver.web.controller.service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.senxu.omserver.common.config.RuoYiConfig;
import com.senxu.omserver.common.constant.Constants;
import com.senxu.omserver.common.core.domain.entity.SysDictData;
import com.senxu.omserver.common.utils.DictUtils;
import com.senxu.omserver.common.utils.bean.BeanUtils;
import com.senxu.omserver.common.utils.file.FileUtils;
import com.senxu.omserver.service.domain.TYuntaiConfigStrategy;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.domain.TYuntaiPresetPosition;
import com.senxu.omserver.service.domain.vo.*;
import com.senxu.omserver.service.service.ITYuntaiConfigStrategyService;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.service.service.ITYuntaiPresetPositionService;
import com.senxu.omserver.web.model.DownLoadInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.service.domain.TYuntaiConfig;
import com.senxu.omserver.service.service.ITYuntaiConfigService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import senxu.com.cmd.ex.CmdFAH;
import senxu.com.cmd.request.*;
import senxu.com.cmd.response.*;
import senxu.com.service.IDeviceBridge;
import senxu.com.service.ServiceCallback;
import senxu.com.service.impl.DeviceBridgeImpl;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * t_yuntai_config（云台配置记录）Controller
 * 
 * @author panyc
 * @date 2021-06-15
 */
@Api("云台抓告警")
@RestController
@RequestMapping("/service/yuntaiConfig")
public class TYuntaiConfigController extends BaseController
{
    @Autowired
    private ITYuntaiConfigService tYuntaiConfigService;


    @Autowired
    private ITYuntaiConfigStrategyService tStrategyService;

    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;

    @Autowired
    private ITYuntaiPresetPositionService tYuntaiPresetPositionService;

    @Autowired
    private NWOMServer nwomServer;


    /**
     * 从设备下载文件
     */
    @ApiOperation("下载文件")
    //@PreAuthorize("@ss.hasPermi('service:info:query')")
    @PostMapping(value = "/downloadFile/{id}")
    public AjaxResult downloadFile(@PathVariable("id") Long id,@RequestBody DownLoadInfo downloadInfo)
    {
        TYuntaiInfo tYuntaiInfo= tYuntaiInfoService.selectTYuntaiInfoById(id);
        if(tYuntaiInfo==null){
            return AjaxResult.error("该云台找不到！");
        }
        Object data=new JSONObject();
        String deviceCode=tYuntaiInfo.getCode();
        String filename=downloadInfo.getFileName();
        String outputname=filename;
        if(filename.indexOf("/")>-1){
            String[] items=filename.split("/");
            outputname=items[items.length-1];
        }
        else if(filename.indexOf("\\")>-1){
            String[] items=filename.split("\\\\");
            outputname=items[items.length-1];
        }
        final boolean[] responseError = {false};
        nwomServer.getDeviceBridge().download(deviceCode, tYuntaiInfo.getPasword(), filename, new ServiceCallback<DeviceCommandResponse>() {
            @Override
            public void onResponse(DeviceCommandResponse var1) {
                CmdFAHResponse cmdFAHResponse=(CmdFAHResponse) var1.getCmdResponse();
                if(cmdFAHResponse.errorCode==1){
                    responseError[0] =true;
                }
            }
        });





        File file=new File(CmdFAH.serverFileName);
        //若文件未生成不断等待
        int i=0;
        while(!file.exists()){
            if(responseError[0]){
                return  AjaxResult.error("找不到该文件！！！");
            }
            try {
                //10分钟后结束
                if(i>10*60){
                    return  AjaxResult.error("下载文件超时！！！");
                  //  break;
                }
                Thread.sleep(1000);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //复制到若依下载目录
        try {
            FileUtils.copyFile(file, new File(RuoYiConfig.getDownloadPath()+outputname));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        //WebUtil.send(FileUti);
        /*boolean useZip=false;
        // downloadName = WebUtil.fetch(request, "downloadName");

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(filename);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String fileNameISO = null;
        try {
            fileNameISO = new String(filename.getBytes("utf-8"), "iso-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + fileNameISO);
        try {
            download(filename, response);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }*/

        return  AjaxResult.success(outputname);
    }

    @SneakyThrows
    public   void download(String filename, HttpServletResponse res) throws IOException {
        // 发送给客户端的数据
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        // 读取filename
        bis = new BufferedInputStream(new FileInputStream(new File("d://download/" + filename)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
    }

    /**
     * 获取云台的配置信息
     */
    @ApiOperation("获取云台配置详细")
    //@PreAuthorize("@ss.hasPermi('service:info:query')")
    @GetMapping(value = "/commonGet/{id}")
    public AjaxResult getConfig(@PathVariable("id") Long id, String type, HttpServletRequest request)
    {
        TYuntaiInfo tYuntaiInfo= tYuntaiInfoService.selectTYuntaiInfoById(id);
        if(tYuntaiInfo==null){
            return AjaxResult.error("该云台找不到！");
        }
        Object data=new JSONObject();
        if(type.equals("videoUrl")){
            JSONObject dataJ=(JSONObject) data;
            String ip = request.getServerName();
            dataJ.put("videoUrl","rtmp://"+ip+"/live/"+tYuntaiInfo.getCode());
            //dataJ.put("videoUrl","rtmp://192.168.10.33/live/"+tYuntaiInfo.getCode());
        }
        if(type.equals("preset")){
            TYuntaiPresetPosition yuntaiPresetPosition=new TYuntaiPresetPosition();
            yuntaiPresetPosition.setYuntaiId(id);
            data=tYuntaiPresetPositionService.selectTYuntaiPresetPositionList(yuntaiPresetPosition);
            //data.put("datas", JSONArray.fromObject(l));
        }
        return AjaxResult.success(data);
    }

    /**
     * 设置云台的配置信息
     */
    @ApiOperation("设置云台配置信息")
    //@PreAuthorize("@ss.hasPermi('service:info:add')")
    @PostMapping(value = "/commonSet/{id}")
    public AjaxResult setConfig(@PathVariable("id") Long id, String type, JSONObject params)
    {
        TYuntaiInfo tYuntaiInfo= tYuntaiInfoService.selectTYuntaiInfoById(id);
        if(tYuntaiInfo==null){
            return AjaxResult.error("该云台找不到！");
        }
        if(type.equals("preset")){
            TYuntaiPresetPosition yuntaiPresetPosition=new TYuntaiPresetPosition();
            yuntaiPresetPosition.setYuntaiId(id);
           // yuntaiPresetPosition.
           // tYuntaiPresetPositionService.updateTYuntaiPresetPosition(yuntaiPresetPosition);
            //data.put("datas", JSONArray.fromObject(l));
        }
        return AjaxResult.success(tYuntaiInfoService.selectTYuntaiInfoById(id));
    }


    /**
     * 查询t_yuntai_config（云台配置记录）列表
     */
    @ApiOperation("查询云台配置记录列表")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiConfig tYuntaiConfig)
    {
        startPage();
        List<TYuntaiConfig> list = tYuntaiConfigService.selectTYuntaiConfigList(tYuntaiConfig);
        return getDataTable(list);
    }

    /**
     * 导出t_yuntai_config（云台配置记录）列表
     */
    @ApiOperation("导出云台配置记录列表")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:export')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiConfig tYuntaiConfig)
    {
        List<TYuntaiConfig> list = tYuntaiConfigService.selectTYuntaiConfigList(tYuntaiConfig);
        ExcelUtil<TYuntaiConfig> util = new ExcelUtil<TYuntaiConfig>(TYuntaiConfig.class);
        return util.exportExcel(list, "t_yuntai_config（云台配置记录）数据");
    }

    /**
     * 获取t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("获取云台配置记录详细")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/getAllInfo/{yuntaiId}")
    public AjaxResult getAllInfo(@PathVariable("yuntaiId") Long yuntaiId)
    {
        List<TYuntaiConfig> list=tYuntaiConfigService.selectTYuntaiConfigList(new TYuntaiConfig(yuntaiId));
        if(list !=null && list.size() > 0) {
            return AjaxResult.success(list.get(0));
        }else{
            return AjaxResult.error("没读取到数据或数据库没有相应记录！！！");
        }
    }


    /**
     * 获取t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("获取云台配置记录：心跳配置参数")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/getHeartbeat/{yuntaiId}")
    public AjaxResult getHeartbeat(@PathVariable("yuntaiId") Long yuntaiId)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd0AH(yuntaiInfo.getCode());

        if(response.isSuccess()){
            AjaxResult ajax = AjaxResult.success();
            Cmd0AHResponse cmd0AHResponse=(Cmd0AHResponse)response.getCmdResponse();
            TYuntaiConfigHeartbeat heartbeat = new TYuntaiConfigHeartbeat();
            heartbeat.setHeartbeatCollect(cmd0AHResponse.getHeartbeatCollect());
            heartbeat.setHeartbeatInterval(cmd0AHResponse.getHeartbeatInterval());
            heartbeat.setHeartbeatDormancy(cmd0AHResponse.getHeartbeatDormancy());
            heartbeat.setHeartbeatOnline(cmd0AHResponse.getHeartbeatOnline());
            heartbeat.setHeartbeatRebootDay(new Long(cmd0AHResponse.getHeartbeatRebootDate()));
            heartbeat.setHeartbeatRebootHour(new Long(cmd0AHResponse.getHeartbeatRebootHour()));
            heartbeat.setHeartbeatRebootMinute(new Long(cmd0AHResponse.getHeartbeatRebootMinute()));
            List<String> list = new ArrayList<>();
            byte[] bytes = cmd0AHResponse.getValidFunction();
            for(byte b : bytes){
                list.add(String.valueOf(b));
            }
            ajax.put("heartbeat", heartbeat);
            ajax.put("functions", list);
            return AjaxResult.success(ajax);
        }else{
            return AjaxResult.error(response.getMessage());
        }
    }


    /**
     * 保存t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("保存云台配置记录：心跳配置参数")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:save')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/saveHeartbeat")
    public AjaxResult saveHeartbeat(@RequestBody TYuntaiConfigHeartbeat heartbeat)
    {
        //TODO 这里应该通过南网协议将参数设置到工控机上，然后再保存数据到数据库

        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(heartbeat.getYuntaiId());
        Cmd03HRequest cmd03HRequest = new Cmd03HRequest();
        cmd03HRequest.setPassword(yuntaiInfo.getPasword());//密码
        cmd03HRequest.setHeartbeatInterval(heartbeat.getHeartbeatInterval());//心跳间隔
        cmd03HRequest.setAcquisitionInterval(heartbeat.getHeartbeatCollect());//采集间隔
        cmd03HRequest.setSleepTime(heartbeat.getHeartbeatDormancy());//休眠时长
        cmd03HRequest.setOnlineTime(heartbeat.getHeartbeatOnline());//在线时长
        cmd03HRequest.setRestartDate(heartbeat.getHeartbeatRebootDay().intValue());//重启日
        cmd03HRequest.setRestartHour(heartbeat.getHeartbeatRebootHour().intValue());//重启小时
        cmd03HRequest.setRestartMinute(heartbeat.getHeartbeatRebootMinute().intValue());//重启分
        cmd03HRequest.setVerCode("1234");
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd03H(yuntaiInfo.getCode(),cmd03HRequest);
        return AjaxResult.success();
    }


    /**
     * 获取t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("获取云台配置记录：终端功能配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/getFunction/{yuntaiId}")
    public AjaxResult getFunction(@PathVariable("yuntaiId") Long yuntaiId)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd0BH(yuntaiInfo.getCode(),new Cmd0BHRequest());

        if(response.isSuccess()){
                AjaxResult ajax = AjaxResult.success();
                ajax.put("function", (Cmd0BHRequest)response.getCmdResponse());
                return AjaxResult.success(ajax);
        }else{
            return AjaxResult.error(response.getMessage());
        }
    }


    /**
     * 保存t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("保存云台配置记录：终端功能配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:save')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/saveFunction")
    public AjaxResult saveFunction(@RequestBody JSONObject functions)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        String yuntaiId = functions.getString("yuntaiId");
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(Long.parseLong(yuntaiId));
        Cmd0BHRequest cmd0BHRequest = new Cmd0BHRequest();
        cmd0BHRequest.setPassword(yuntaiInfo.getPasword());
        JSONArray array = functions.getJSONArray("functions");
        byte[] bytes = new byte[20];
        for(int i = 0;i<bytes.length;i++){
            if(i+1 <= array.size()){
                bytes[i] = (byte)((Integer) array.get(i)&0xff);
            }else {
                bytes[i] = 0;
            }

        }
        cmd0BHRequest.setValidFunction(bytes);
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd0BH(yuntaiInfo.getCode(),cmd0BHRequest);
        return AjaxResult.success();
    }




    /**
     * 获取t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("获取云台配置记录：图像采集配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/getPicture/{yuntaiId}")
    public AjaxResult getPicture(@PathVariable("yuntaiId") Long yuntaiId)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd0AH(yuntaiInfo.getCode());

        if(response.isSuccess()){
            AjaxResult ajax = AjaxResult.success();
            System.out.println("response.getCmdResponse()============================");
            System.out.println(response.getCmdResponse());
            System.out.println("========================================================");


            Cmd0AHResponse cmd0AHResponse=(Cmd0AHResponse)response.getCmdResponse();
            TYuntaiConfigPicture picture = new TYuntaiConfigPicture();
            picture.setPictureConduit("1");
            picture.setPictureColor(cmd0AHResponse.getPictureColor1()+"");
            picture.setPictureResolution(cmd0AHResponse.getPictureResolution1()+"");
            picture.setPictureBrightness(cmd0AHResponse.getPictureBrightness1()+"");
            picture.setPictureContrast(cmd0AHResponse.getPictureContrast1()+"");
            picture.setPictureSaturation(cmd0AHResponse.getPictureSaturation1()+"");

            ajax.put("picture", picture);
            return AjaxResult.success(ajax);
        }else{
            return AjaxResult.error(response.getMessage());
        }
    }


    /**
     * 保存t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("保存云台配置记录：图像采集配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:save')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/savePicture")
    public AjaxResult savePicture(@RequestBody TYuntaiConfigPicture picture)
    {

        /**
         *     Cmd81HRequest(String password, int color1, int imageSize1, int brightness1, int contrastRatio1, int saturation1, int color2, int imageSize2, int brightness2, int contrastRatio2, int saturation2) {
         *         this.password = password;
         *         this.color1 = color1;
         *         this.imageSize1 = imageSize1;
         *         this.brightness1 = brightness1;
         *         this.contrastRatio1 = contrastRatio1;
         *         this.saturation1 = saturation1;
         *         this.color2 = color2;
         *         this.imageSize2 = imageSize2;
         *         this.brightness2 = brightness2;
         *         this.contrastRatio2 = contrastRatio2;
         *         this.saturation2 = saturation2;
         */
        //通过UDP发送指令给客户端，查询客户端配置
        picture.setId(picture.getYuntaiId());
        picture.setPictureConduit("1");
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(picture.getYuntaiId());

        System.out.println("**********************************************");
        System.out.println(picture.toString());
        System.out.println("**********************************************");


//        Cmd81HRequest cmd81HRequest = new Cmd81HRequest.Cmd81HRequestBuilder().password(yuntaiInfo.getPasword()).color1(Integer.getInteger(picture.getPictureColor())).
//                imageSize1(Integer.getInteger(picture.getPictureResolution())).brightness1(Integer.getInteger(picture.getPictureBrightness())).
//                contrastRatio1(Integer.getInteger(picture.getPictureContrast())).saturation1(Integer.getInteger(picture.getPictureSaturation())).color2(0)
//                .imageSize2(0).brightness2(0).contrastRatio2(0).saturation2(0).build();

        Cmd81HRequest cmd81HRequest =new Cmd81HRequest(yuntaiInfo.getPasword(),
                Integer.parseInt(picture.getPictureColor()),
                Integer.parseInt(picture.getPictureResolution()),//分辨率
                Integer.parseInt(picture.getPictureBrightness()),
                Integer.parseInt(picture.getPictureContrast()),
                Integer.parseInt(picture.getPictureSaturation()),
                0,0,0,0,0
                );



        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd81H(yuntaiInfo.getCode(),cmd81HRequest);
        return AjaxResult.success();

        //TODO 这里应该通过南网协议将参数设置到工控机上，然后再保存数据到数据库
//        List<TYuntaiConfig> list=tYuntaiConfigService.selectTYuntaiConfigList(new TYuntaiConfig(picture.getYuntaiId()));
//        if(list==null || list.size()==0) {
//            if(picture!=null && picture.getYuntaiId()>0) {
//                TYuntaiConfig yuntai=new TYuntaiConfig(picture.getYuntaiId());
//                BeanUtils.copyBeanProp(yuntai,picture);
//                return AjaxResult.success(tYuntaiConfigService.insertTYuntaiConfig(yuntai));
//            }else {
//                return AjaxResult.error("传入参数没有yuntaiId，保存失败！！！");
//            }
//        }else {
//            TYuntaiConfig yuntai=new TYuntaiConfig(0L);
//            yuntai=list.get(0);
//            picture.setId(yuntai.getId());
//            BeanUtils.copyBeanProp(yuntai,picture);
//            return AjaxResult.success(tYuntaiConfigService.updateTYuntaiConfig(yuntai));
//        }
    }



    /**
     * 获取t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("获取云台配置：平台参数配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/getPlatform/{yuntaiId}")
    public AjaxResult getPlatform(@PathVariable("yuntaiId") Long yuntaiId)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        //查询客户端配置信息终端参数心跳间隔，图像采集参数
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd07H(yuntaiInfo.getCode());

        if(response!=null) {
            TYuntaiConfigPlatform platform = new TYuntaiConfigPlatform();
            Cmd07HResponse cmd07=(Cmd07HResponse)response.getCmdResponse();
            platform.setPlatformIp(cmd07.getIp());
            platform.setPlatformProt(cmd07.getPort()+"");
            platform.setPlatformCard(cmd07.getCardNo());
            platform.setYuntaiId(yuntaiId);
            return AjaxResult.success(platform);
        }else{
            return AjaxResult.error("没读取到数据或数据库没有相应记录！！！");
        }
    }


    /**
     * 保存t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("保存云台配置：平台参数配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:save')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/savePlatform")
    public AjaxResult savePlatform(@RequestBody TYuntaiConfigPlatform platform)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(platform.getYuntaiId());

        //传入cmd06HRequest
        Cmd06HRequest cmd06HRequest= new Cmd06HRequest();
        cmd06HRequest.setPassword(yuntaiInfo.getPasword());
        cmd06HRequest.setCardNo1(platform.getPlatformCard());
        cmd06HRequest.setIp1(platform.getPlatformIp());
        cmd06HRequest.setPort1(Integer.parseInt(platform.getPlatformProt()));
        cmd06HRequest.setCardNo2(platform.getPlatformCard());
        cmd06HRequest.setIp2(platform.getPlatformIp());
        cmd06HRequest.setPort2(Integer.parseInt(platform.getPlatformProt()));
        //查询客户端配置信息终端参数心跳间隔，图像采集参数
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd06H(yuntaiInfo.getCode(),cmd06HRequest);

        if(response.isSuccess()){
            return AjaxResult.success();
        }else{
            return AjaxResult.error(response.getMessage());
        }

    }



    /**
     * 获取t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("获取云台配置：视频参数配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/getVideo/{yuntaiId}")
    public AjaxResult getVideo(@PathVariable("yuntaiId") Long yuntaiId)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        Cmd8DHRequest cmd8DHRequest = new Cmd8DHRequest();
        cmd8DHRequest.setChannelNo(1);
        cmd8DHRequest.setChannelType(0);
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd8DH(yuntaiInfo.getCode(),cmd8DHRequest);//这里工控机只返回了数据

        System.out.println("查询客户端配置:getVideo=="+response.getCmdResponse().toString());

        JSONObject object = JSONObject.fromObject(response.getCmdResponse());
        TYuntaiConfigVideo video = new TYuntaiConfigVideo();
        video.setVideoConduit(object.getString("channelNo"));
        video.setVideoConduitType(object.getString("channelType"));
        video.setVideoRate(object.getString("frameRate"));
        video.setVideoFrameInterval(object.getString("IFrameInterval"));
        video.setVideoBitrateType(object.getString("channelNo"));
        video.setVideoBitrate(object.getString("bitRate"));
        video.setVideoBitrateType(object.getString("bitRateType"));
        video.setVideoEncoded(object.getString("codeType"));
        video.setVideoResolution(object.getString("resolutionRate"));

        AjaxResult ajax = AjaxResult.success();
        ajax.put("TYuntaiConfigVideo", video);
        return ajax;
    }


    /**
     * 保存t_yuntai_config（云台配置记录）详细信息
     */
    @ApiOperation("保存云台配置：视频参数配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:save')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/saveVideo")
    public AjaxResult saveVideo(@RequestBody TYuntaiConfigVideo video)
    {
//        //TODO 这里应该通过南网协议将参数设置到工控机上，然后再保存数据到数据库
        /**
         * getVideo==Cmd8DHResponse(channelNo=4, channelType=22, frameRate=1, IFrameInterval=1, codeType=0, bitRate=32,
         * bitRateType=0, resolutionRate=13)
         */
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(video.getYuntaiId());
        Cmd8CHRequest cmd8CHRequest = new Cmd8CHRequest();
        cmd8CHRequest.setPassword(yuntaiInfo.getPasword());//密码
        cmd8CHRequest.setChannelNo(Integer.parseInt(video.getVideoConduit()));//通道号
        cmd8CHRequest.setChannelType(Integer.parseInt(video.getVideoConduitType()));//通道类型
        cmd8CHRequest.setFrameRate(Integer.parseInt(video.getVideoRate()));//帧率
        cmd8CHRequest.setIFrameInterval(Integer.parseInt(video.getVideoFrameInterval()));//一帧间隔
        cmd8CHRequest.setCodeType(Integer.parseInt(video.getVideoEncoded()));//编码类型
        cmd8CHRequest.setBitRate(Integer.parseInt(video.getVideoBitrate()));//码率
        cmd8CHRequest.setBitRateType(Integer.parseInt(video.getVideoBitrateType()));//位率类型
        cmd8CHRequest.setResolutionRate(Integer.parseInt(video.getVideoResolution()));//分辨率
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd8CH(yuntaiInfo.getCode(),cmd8CHRequest);//这里工控机只返回了数据
        return AjaxResult.success();
    }


    /**
     * 查询拍照策略
     */
    @ApiOperation("查询拍照策略")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:getCaptureStrategy')")
    @Log(title = "t_yuntai_config（云台配置记录）")
    @GetMapping(value = "/getCaptureStrategy/{yuntaiId}")
    public AjaxResult getCaptureStrategy(@PathVariable("yuntaiId") Long yuntaiId){
        //通过UDP发送指令给客户端，查询客户端配置(这里是直接向设备发送指令，等待设备回复，没有将数据放到后台，与其他接口不同）
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        Cmd8BHRequest cmd8BHRequest = new Cmd8BHRequest();
        cmd8BHRequest.setChannelNo(1);
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd8BH(yuntaiInfo.getCode(),cmd8BHRequest);//这里工控机只返回了数据
        AjaxResult ajax = AjaxResult.success();
        if(response.isSuccess()){
            ajax.put("captureStrategy",response.getCmdResponse());
            return ajax;
        }else{
            return AjaxResult.error(response.getMessage());
        }
    }


    /**
     * 设置拍照策略
     */
    @ApiOperation("设置拍照策略")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:saveCaptureStrategy')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/saveCaptureStrategy/{yuntaiId}")
    public AjaxResult saveCaptureStrategy(@PathVariable("yuntaiId") Long yuntaiId, @RequestBody Cmd82HRequest cmd82HRequest){
        //通过UDP发送指令给客户端
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        if(cmd82HRequest!=null){
            cmd82HRequest.setPassword(yuntaiInfo.getPasword());
        }
        cmd82HRequest.setGroupNum(cmd82HRequest.getDetails().size());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd82H(yuntaiInfo.getCode(),cmd82HRequest);//这里工控机只返回了数据
        AjaxResult ajax = AjaxResult.success();
        if(response.isSuccess()){
            return ajax;
        }else{
            return AjaxResult.error(response.getMessage());
        }
    }




    /**
     * 查询录像策略
     */
    @ApiOperation("查询录像策略")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:getVideoStrategy')")
    @Log(title = "t_yuntai_config（云台配置记录）")
    @GetMapping(value = "/getVideoStrategy/{yuntaiId}")
    public AjaxResult getVideoStrategy(@PathVariable("yuntaiId") Long yuntaiId){
        //通过UDP发送指令给客户端，查询客户端配置(这里是直接向设备发送指令，等待设备回复，没有将数据放到后台，与其他接口不同）
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        Cmd91HRequest cmd91HRequest = new Cmd91HRequest();
        cmd91HRequest.setChannelNo(1);
        cmd91HRequest.setChannelType(0);
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd91H(yuntaiInfo.getCode(),cmd91HRequest);//这里工控机只返回了数据
        AjaxResult ajax = AjaxResult.success();
        if(response.isSuccess()){
            ajax.put("videoStrategy",response.getCmdResponse());
            return ajax;
        }else{
            return AjaxResult.error(response.getMessage());
        }
    }


    /**
     * 设置拍照策略
     */
    @ApiOperation("设置拍照策略")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:saveVideoStrategy')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/saveVideoStrategy")
    public AjaxResult saveVideoStrategy(Long yuntaiId, @RequestBody Cmd90HRequest cmd90HRequest){
        //通过UDP发送指令给客户端
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        if(cmd90HRequest!=null){
            cmd90HRequest.setPassword(yuntaiInfo.getPasword());
        }
        cmd90HRequest.setChannelNo(1);
        cmd90HRequest.setChannelType(0);
        cmd90HRequest.setGroupNum(cmd90HRequest.getDetails().size());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd90H(yuntaiInfo.getCode(),cmd90HRequest);//这里工控机只返回了数据
        AjaxResult ajax = AjaxResult.success();
        if(response.isSuccess()){
            return ajax;
        }else{
            return AjaxResult.error(response.getMessage());
        }
    }



    /**
     * 新增t_yuntai_config（云台配置记录）
     */
    @ApiOperation("新增云台配置记录")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:add')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiConfig tYuntaiConfig)
    {
        return toAjax(tYuntaiConfigService.insertTYuntaiConfig(tYuntaiConfig));
    }

    /**
     * 修改t_yuntai_config（云台配置记录）
     */
    @ApiOperation("保存云台配置记录详细")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:edit')")
    @Log(title = "t_yuntai_config（云台配置记录详细）", businessType = BusinessType.UPDATE)
    @PostMapping("/saveAllInfo")
    public AjaxResult saveAllInfo(@RequestBody TYuntaiConfig tYuntaiConfig)
    {
        return toAjax(tYuntaiConfigService.updateTYuntaiConfig(tYuntaiConfig));
    }

    /**
     * 删除t_yuntai_config（云台配置记录）
     */
    @ApiOperation("删除云台配置记录")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:remove')")
    @Log(title = "t_yuntai_config（云台配置记录）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiConfigService.deleteTYuntaiConfigByIds(ids));
    }


    /**
     * 获取工控机设备时间
     */
    @ApiOperation("获取工控机设备时间")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/getDeviceDate/{yuntaiId}")
    public AjaxResult getDeviceDate(@PathVariable("yuntaiId") Long yuntaiId)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        //查询客户端配置信息终端参数心跳间隔，图像采集参数
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd0DH(yuntaiInfo.getCode());

        if(response!=null) {
            Cmd0DHResponse cmd0d=(Cmd0DHResponse)response.getCmdResponse();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String  date = sdf.format(cmd0d.getDeviceCurDate());
            return AjaxResult.success("查询成功",date);
        }else{
            return AjaxResult.error("没读取到数据或数据库没有相应记录！！！");
        }
    }


    /**
     * 修改设备密码
     */
    @ApiOperation("修改设备密码")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @PostMapping(value = "/setDevicePassword/{yuntaiId}")
    public AjaxResult setDevicePassword(@PathVariable("yuntaiId") Long yuntaiId,@RequestBody Cmd02HRequest cmd02HRequest)
    {
        //通过UDP发送指令给客户端，查询客户端配置
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
//        if(!yuntaiInfo.getPasword().equals(cmd02HRequest.getOldPassword())){
//            return AjaxResult.error("原密码错误，请重新输入");
//        }
        //查询客户端配置信息终端参数心跳间隔，图像采集参数
        try {
            DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd02H(yuntaiInfo.getCode(),cmd02HRequest);
            if(response!=null) {
                yuntaiInfo.setSpare0(cmd02HRequest.getNewPassword());
                tYuntaiInfoService.updateTYuntaiInfo(yuntaiInfo);
                return AjaxResult.success();
            }else{
                return AjaxResult.error("修改设备密码失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("系统错误，请联系管理员");
        }
    }

    /**
     * OSD参数配置
     */
    @ApiOperation("OSD参数配置")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @PostMapping(value = "/setOSD/{yuntaiId}")
    public AjaxResult setOSD(@PathVariable("yuntaiId") Long yuntaiId,@RequestBody Cmd8EHRequest cmd8EHRequest)
    {
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        cmd8EHRequest.setPassword(yuntaiInfo.getPasword());
//        cmd8EHRequest.setPassword("1234");
//        cmd8EHRequest.setChannelNo(1);
//        cmd8EHRequest.setShowTime(1);
//        cmd8EHRequest.setShowText(1);
//        cmd8EHRequest.setText("设备SX1002");//森旭测试球机1630
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd8EH(yuntaiInfo.getCode(),cmd8EHRequest);
        return AjaxResult.success();
    }

    /**
     * OSD参数查询
     */
    @ApiOperation("OSD参数查询")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/getOSD/{yuntaiId}")
    public AjaxResult getOSD(@PathVariable("yuntaiId") Long yuntaiId,String channelNo)
    {
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        Cmd8FHRequest cmd8FHRequest = new Cmd8FHRequest();
        cmd8FHRequest.setChannelNo(1);
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd8FH(yuntaiInfo.getCode(),cmd8FHRequest);
        if(response!=null) {
            return AjaxResult.success(response.getCmdResponse());
        }else{
            return AjaxResult.error("没读取到数据或数据库没有相应记录！！！");
        }
    }

    /**
     * 设备校时
     */
    @ApiOperation("设备校时")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @PostMapping(value = "/getTiming/{yuntaiId}")
    public AjaxResult restPassword(@PathVariable("yuntaiId") Long yuntaiId,Cmd01HRequest cmd01HRequest)
    {
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);

        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd01H(yuntaiInfo.getCode(),cmd01HRequest);
        if(response!=null) {
            return AjaxResult.success(response.getCmdResponse());
        }else{
            return AjaxResult.error("设备校时失败");
        }
    }

    /**
     * 装置重启
     */
    @ApiOperation("装置重启")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/restart/{yuntaiId}")
    public AjaxResult restart(@PathVariable("yuntaiId") Long yuntaiId)
    {
//        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
//        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd26H(yuntaiInfo.getCode());
//        if(response!=null) {
//            return AjaxResult.success(response.getCmdResponse());
//        }else{
//            return AjaxResult.error("获取气象失败");
//        }

        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        Cmd08HRequest cmd08HRequest = new Cmd08HRequest();
        cmd08HRequest.setPassword(yuntaiInfo.getPasword());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd08H(yuntaiInfo.getCode(),cmd08HRequest);
        if(response!=null) {
            return AjaxResult.success(response.getCmdResponse());
        }else{
            return AjaxResult.error("装置重启失败");
        }
    }

    /**
     * 短信唤醒
     */
    @ApiOperation("短信唤醒")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/smsWakeUp/{yuntaiId}")
    public AjaxResult smsWakeUp(@PathVariable("yuntaiId") Long yuntaiId)
    {
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        Cmd09HRequest cmd09HRequest = new Cmd09HRequest();
        cmd09HRequest.setPassword(yuntaiInfo.getPasword());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd09H(yuntaiInfo.getCode(),cmd09HRequest);
        if(response!=null) {
            return AjaxResult.success(response.getCmdResponse());
        }else{
            return AjaxResult.error("短信唤醒失败");
        }
    }

    /**
     * 气象测试
     */
    @ApiOperation("气象测试")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/mete/{yuntaiId}")
    public AjaxResult mete(@PathVariable("yuntaiId") Long yuntaiId)
    {
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        Cmd09HRequest cmd09HRequest = new Cmd09HRequest();
        cmd09HRequest.setPassword(yuntaiInfo.getPasword());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd25H(yuntaiInfo.getCode());
        if(response!=null) {
            return AjaxResult.success(response.getCmdResponse());
        }else{
            return AjaxResult.error("获取气象测试失败");
        }
    }

    /**
     * 导线温度
     */
    @ApiOperation("导线温度")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiConfig:query')")
    @GetMapping(value = "/temperature/{yuntaiId}")
    public AjaxResult temperature(@PathVariable("yuntaiId") Long yuntaiId)
    {
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        Cmd09HRequest cmd09HRequest = new Cmd09HRequest();
        cmd09HRequest.setPassword(yuntaiInfo.getPasword());
        DeviceCommandResponse response=nwomServer.getDeviceBridge().Cmd26H(yuntaiInfo.getCode());
        if(response!=null) {
            return AjaxResult.success(response.getCmdResponse());
        }else{
            return AjaxResult.error("获取导线温度失败");
        }
    }


}
