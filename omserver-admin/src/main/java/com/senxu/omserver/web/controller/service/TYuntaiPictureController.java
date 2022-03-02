package com.senxu.omserver.web.controller.service;

import java.util.*;

import com.google.gson.Gson;
import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.common.utils.StringUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.domain.TYuntaiPresetPosition;
import com.senxu.omserver.service.domain.TYuntaiVideo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.service.service.ITYuntaiPresetPositionService;
import com.senxu.omserver.service.service.ITYuntaiVideoService;
import com.senxu.omserver.web.enums.CapPicTypeEnum;
import com.senxu.omserver.web.enums.ManualCapTypeEnum;
import com.senxu.omserver.web.model.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.service.domain.TYuntaiPicture;
import com.senxu.omserver.service.service.ITYuntaiPictureService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.base.vo.ImageAlarmRecord;
import senxu.com.base.vo.ShortVideoUploadVO;
import senxu.com.cmd.callback.CapPicCallback;
import senxu.com.cmd.callback.ShootVideoCallback;
import senxu.com.cmd.response.Cmd0AHResponse;
import senxu.com.cmd.response.Cmd93HErrorResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.service.ServiceCallback;

/**
 * t_yuntai_picture（云台抓拍图片）Controller
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Api("云台抓拍图片")
@RestController
@RequestMapping("/service/yuntaPicture")
@Slf4j
public class TYuntaiPictureController extends BaseController
{
    @Autowired
    private NWOMServer nwomServer;
    @Autowired
    private ITYuntaiPictureService tYuntaiPictureService;
    @Autowired
    private ITYuntaiVideoService tYuntaiVideoService;
    @Autowired
    public OmServerProperties omServerProperties;

    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ITYuntaiPresetPositionService yuntaiPresetPositionService;


    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表
     */
    @ApiOperation("查询云台抓拍图片列表")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiPicture tYuntaiPicture)
    {
        startPage();
        List<TYuntaiPicture> list = tYuntaiPictureService.selectTYuntaiPictureList(tYuntaiPicture);
        return getDataTable(list);
    }


    /**
     * 新增t_yuntai_picture（云台抓拍图片）
     */
    @ApiOperation("手动抓拍图片")
    /*@PreAuthorize("@ss.hasPermi('service:yuntaiPicture:manualCap')")*/
    /* @Log(title = "t_yuntai_picture（云台抓拍图片）", businessType = BusinessType.INSERT)*/
    @PostMapping("/manualCap")
    @SneakyThrows
    public AjaxResult manualCapPres(@ApiParam(value = "预置位",defaultValue = "0") @RequestParam Integer presetBit ,
                                    @ApiParam(value = "手动抓拍类型",defaultValue = "0") @RequestParam Integer manualCapType,
                                @RequestParam Long yunTaiId,@RequestParam(required =  false) Integer sync)
    {//,@ApiParam(value = "是否异步",defaultValue = "1") @RequestParam Integer sync
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yunTaiId);
        //默认同步
       // Integer sync=null;
        if(sync==null){
            sync=0;//1 前端会停着不动
        }
        //默认0预置位
        if(presetBit==null){
            presetBit=0;
        }
        //默认是当前位置抓拍
        if(manualCapType==null){
            manualCapType=0;
        }


        JSONObject r=new JSONObject();
        log.info("开始手动拍照。。。");
        r.put("success",true);
        ManualCapTypeEnum capTypeEnum=ManualCapTypeEnum.valueOf(manualCapType);
        //  /** 上图状态：1 正常 0 未上图*/
        yuntaiInfo.setImageStatus(new Long(1));
        tYuntaiInfoService.updateTYuntaiInfo(yuntaiInfo);

       // Result result=new Result();
        AjaxResult result1;
       // result.setMsg("正在抓拍图片中，请稍候！");
        if(capTypeEnum==ManualCapTypeEnum.CurPreset) {
            return capPic( yuntaiInfo,0,  sync,username);
        }
        else if(capTypeEnum==ManualCapTypeEnum.AllPreset) {
            TYuntaiPresetPosition yuntaiPresetPositionQuery=new TYuntaiPresetPosition();
            yuntaiPresetPositionQuery.setYuntaiId(yunTaiId);
            List<TYuntaiPresetPosition> list= yuntaiPresetPositionService.selectTYuntaiPresetPositionList(yuntaiPresetPositionQuery);
            AjaxResult ajaxResult=AjaxResult.success("正在拍摄中，请稍候");

            new Thread(
                    ()->{
                        for (int i = 0; i < list.size(); i++) {
                            TYuntaiPresetPosition yuntaiPresetPosition=list.get(i);
                            capPic( yuntaiInfo,Integer.parseInt(yuntaiPresetPosition.getPresetBit()),  0,username);
                            try {
                                Thread.currentThread().sleep(60000);//由于设备端用了全局变量设预置位，所以要等图片上传完成，这个延时需要设长点。等设备端优化了可以设短点
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).start();

            return  ajaxResult;
        }
        else if(capTypeEnum==ManualCapTypeEnum.ThatPreset) {
             if(presetBit==null){
                    throw new Exception("预置位不能为空");
                }
             return capPic( yuntaiInfo,presetBit, sync,username);

        }

        return  AjaxResult.error("拍摄失败！");
    }

    private AjaxResult capPic(TYuntaiInfo yuntaiInfo,Integer preset,  Integer sync,String username) {
        Date nowDate = new Date();
        String now =  DateUtils.parseDateToStr( "yyyyMMddHHmmss", nowDate);
        if(preset==0){
            log.info("当前位置拍摄");
        }
        else{
            log.info(yuntaiInfo.getCode()+"开始拍预置位："+preset);
        }

//        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        JSONObject o=new JSONObject();
        o.put("capSuccess",false);

       // nwomServer.manualCaps.put(yuntaiInfo.getCode()+"_"+1+"_"+preset,1);
        try{
            DeviceCommandResponse response= nwomServer.iDeviceBridge.capture(yuntaiInfo.getCode(), 1, preset, null, new CapPicCallback() {
                @Override
                public void uploadSuccess(String deviceCode, ImageAlarmRecord record) {
                    TYuntaiPicture picture=new TYuntaiPicture();
                    picture.setCaptureTime(nowDate);
                    picture.setYuntaiId(yuntaiInfo.getId());
                    picture.setLineId(yuntaiInfo.getLineId());
                    picture.setTowerId(yuntaiInfo.getTowerId());
                    picture.setFactory(yuntaiInfo.getFactory());
                    picture.setCaptureUser(username);
                    picture.setChannelNumber("1");
                    picture.setPresetBit(String.valueOf(preset));
                    picture.setCaptureType(CapPicTypeEnum.DomainReqCap.getCode());
                    String[] paths=record.getImagePath().split("//");
                    String picName=paths[paths.length-1];
                    String relativePath=record.getImagePath().replace(omServerProperties.capPicPath,"").replaceAll("//","/");
                    String imageDir = "/"+omServerProperties.mappingBasePath +  relativePath.replace(picName, "");
                    log.info("图片名称："+picName);
                    log.info("图片目录："+imageDir);
                    picture.setImageName(picName);
                    picture.setImageDir(imageDir);
                    picture.setChannelNumber(String.valueOf(record.getChannelNo()));
                    picture.setPresetBit(String.valueOf(record.getPreset()));
//                        yuntaiPicture.setImgType();//设置图片类型  TODO
                    picture.setIsAlarm(0);
                    tYuntaiPictureService.insertTYuntaiPicture(picture);
                    o.put("capSuccess",true);

                    //完成图片上传
                    yuntaiInfo.setImageStatus(new Long(1));
                    tYuntaiInfoService.updateTYuntaiInfo(yuntaiInfo);
                    //TODO:应过滤报警上传
                }
            });
            if(!response.isSuccess()){
                return AjaxResult.error(response.getMessage());
            }
        }
        catch (Exception ex){
          //  result.setMsg();
            return AjaxResult.error("抓拍出现错误，请检查云台是否在线！");
        }


        if(sync==1){
            Date begin=new Date();
            boolean outTime=false;
            while(true){
                boolean capSuccess= o.getBoolean("capSuccess");
                Date now1=new Date();
                if(now1.getTime()-begin.getTime()>60*3*1000){
                    return  AjaxResult.error("拍摄图片超时！");
                }
                if(capSuccess){
                    return  AjaxResult.success("成功拍摄图片！");
                }
                else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            return  AjaxResult.success("正在拍摄图片，请稍候！");
        }
       /* if(async==0){
            waitResult(yuntaiInfo,"0",now,result,username);
        }*/
    }

    /**
     * 等待云台某个时间，某个预置的结果
     * @param yuntaiInfo
     * @param begintime
     * @param result
     */
    @SneakyThrows
    private void waitResult(TYuntaiInfo yuntaiInfo, String presetBit,String begintime, Result result,String username) {
        int count=0;
        while(true) {
            if (count > 30) {//等超过30秒结束
                result.setSuccess(false);
                result.setMsg("超时没查询图片，请联系管理员！");
                break;
            }
            TYuntaiPicture yuntaiPicture = new TYuntaiPicture();
            yuntaiPicture.setYuntaiId(yuntaiInfo.getId());
            yuntaiPicture.setPresetBit(presetBit);
            yuntaiPicture.setCaptureType(CapPicTypeEnum.DomainReqCap.getCode());
            yuntaiPicture.setSpare0(begintime);//用预留字段作查询的开始结束条件
            yuntaiPicture.setCaptureUser(username);
            List<TYuntaiPicture> l = tYuntaiPictureService.selectTYuntaiPictureListByHistory(yuntaiPicture);
            if (l.size() > 0) {
                result.setMsg("拍摄成功！");
                result.setSuccess(true);
                break;
            }
            Thread.sleep(1000);
            count++;
        }
    }

    /**
     * capShortVideo（云台抓拍小视频）
     */
    @ApiOperation("抓拍小视频")
    @PostMapping("/capShortVideo")
    @SneakyThrows
    public AjaxResult capShortVideo(
                                    @RequestParam  Long yunTaiId,
                                    @ApiParam(required = false,value = "持续时间 ,单位：秒")
                                    @RequestParam(required = false) Integer time,
                                    @ApiParam(required = false,value = "是否同步")
                                    @RequestParam(required = false) Integer syna)
    {
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yunTaiId);
        if(time==null){
            time=20;
        }
        if(syna==null){
            syna=0;
        }
        JSONObject o=new JSONObject();
        o.put("capSuccess",false);
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        DeviceCommandResponse response= nwomServer.iDeviceBridge.shootShortVideo(yuntaiInfo.getCode(), 1, time,null, new ShootVideoCallback() {
            @Override
            public void uploadSuccess(String deviceCode, ShortVideoUploadVO shortVideoUploadVO) {
                TYuntaiVideo tYuntaiVideo=new TYuntaiVideo();
                tYuntaiVideo.setCaptureTime(new Date());
                tYuntaiVideo.setPresetBit("0");
                TYuntaiInfo tYuntaiInfo = tYuntaiInfoService.selectTYuntaiInfoByCode(deviceCode);
                tYuntaiVideo.setLineId(tYuntaiInfo.getLineId());
                tYuntaiVideo.setYuntaiId(tYuntaiInfo.getId());
                tYuntaiVideo.setCaptureUser(loginUser.getUsername());
                //存取相对路径
                String url=shortVideoUploadVO.getFilePathName();//.replaceAll(omServerProperties.shortVideoPath,omServerProperties.shortVideoMappingBasePath);
                //以防万一也写上
                url=url.replaceAll("c://","/").replaceAll("C://","/")
                        .replaceAll("d://","/").replaceAll("D://","/")
                    .replaceAll("e://","/").replaceAll("E://","/")
                        .replaceAll("f://","/") .replaceAll("F://","/");
               // tYuntaiVideo.setVideoUrl(shortVideoUploadVO.getFilePathName().replaceAll("c://","/").replaceAll("d://","/")
                 //       .replaceAll("e://","/").replaceAll("f://","/"));
                tYuntaiVideo.setVideoUrl(url);
                tYuntaiVideo.setTowerId(tYuntaiInfo.getTowerId());
                tYuntaiVideoService.insertTYuntaiVideo(tYuntaiVideo);
                o.put("capSuccess",true);
                log.info("添加视频纪录成功");
            }
        });


            if(response.isSuccess()){
                if(syna==1){
                    Date begin=new Date();
                    boolean outTime=false;
                    while(true){
                        boolean capSuccess= o.getBoolean("capSuccess");
                        Date now=new Date();
                        if(now.getTime()-begin.getTime()>60*10*1000){
                            return  AjaxResult.error("超时了！");
                        }
                        if(capSuccess){
                            return  AjaxResult.success("成功录制小视频！");
                        }
                        else {
                            Thread.sleep(1000);
                        }
                    }
                }
                else {
                    return  AjaxResult.success("正在录制小视频，请稍候！");
                }
            }
            else{

                if(response.getCmdResponse()!=null&&response.getCmdResponse() instanceof Cmd93HErrorResponse){
                    return  AjaxResult.error(response.getMessage(),response.getCmdResponse());
                }else{
                    return  AjaxResult.error(response.getMessage());
                }

            }
    }
    /**
     * 查询云台今日抓拍图片列表
     */
    @ApiOperation("查询云台今日抓拍图片列表")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:listByDay')")
    @GetMapping("/listByDay")
    public TableDataInfo ListByDay(Long lineId,Long towerId,Long[] yuntaiIds,String presetBit,String day,Integer imgSize)
    {
        startPage();
        List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
        TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
        tYuntaiInfo.setLineId(lineId);
        tYuntaiInfo.setTowerId(towerId);
        if(yuntaiIds!=null) {
            tYuntaiInfo.setSpare0(StringUtils.arrayLong2Str(yuntaiIds, ","));
        }
        List<TYuntaiInfo> yuntaiList = tYuntaiInfoService.selectTYuntaiInfoListPage(tYuntaiInfo);

        for(TYuntaiInfo obj:yuntaiList){
            TYuntaiPicture tYuntaiPicture = new TYuntaiPicture();
            tYuntaiPicture.setPresetBit(presetBit);
            tYuntaiPicture.setSpare0(day);
            tYuntaiPicture.setYuntaiId(obj.getId());
            tYuntaiPicture.setLineId(lineId);
            tYuntaiPicture.setTowerId(towerId);
            tYuntaiPicture.setImgSize(imgSize);
            List<TYuntaiPicture> list = tYuntaiPictureService.selectTYuntaiPictureListByDay(tYuntaiPicture);

            map.add(new HashMap<String, Object>(){{
                put("yuntai",obj);
                put("pictures",list);
            }});
        }

        return getDataTable2(map,tYuntaiInfoService.selectTYuntaiCount(tYuntaiInfo));
    }



    /**
     * 查询今日抓拍详情图片列表，支持分页查询
     */
    @ApiOperation("查询今日抓拍详情图片列表，支持分页查询")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:ListByDetails')")
    @GetMapping("/ListByDetails")
    public TableDataInfo ListByDetails(Long lineId,Long towerId,Long yuntaiId,String presetBit,String day,String[] imgTypes)
    {
        startPage();

        List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
        TYuntaiPicture tYuntaiPicture = new TYuntaiPicture();
        tYuntaiPicture.setPresetBit(presetBit);
        tYuntaiPicture.setSpare0(day);
        if(day != null){
            String[] array = day.split(",");
            if(array.length > 1){
                tYuntaiPicture.setSpare0(array[0]);
                tYuntaiPicture.setSpare1(array[1]);
            }else {
                tYuntaiPicture.setSpare0(day);
            }
        }
        tYuntaiPicture.setYuntaiId(yuntaiId);
        tYuntaiPicture.setLineId(lineId);
        tYuntaiPicture.setTowerId(towerId);
        tYuntaiPicture.setImgTypes(imgTypes);
        List<TYuntaiPicture> list = tYuntaiPictureService.selectTYuntaiPictureListByDay(tYuntaiPicture);
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);

        map.add(new HashMap<String, Object>(){{
            put("yuntai",yuntaiInfo);
            put("pictures",list);
        }});

        return getDataTable2(map,tYuntaiPictureService.selectTYuntaiPictureCount(tYuntaiPicture));
    }




    /**
     * 查询云台历史抓拍图片列表
     * @return
     */
    @ApiOperation("查询云台历史抓拍图片列表")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:listByHistory')")
    @GetMapping("/listByHistory")
    public TableDataInfo listByHistory(Long lineId, Long towerId, Long[] yuntaiIds, String presetBit, String begDate, String endDate,String[] imgTypes,Integer isBrowse,Integer imgSize)
    {
        DigestUtils.md5DigestAsHex(new byte[]{});
        startPage();
        List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
        TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
        tYuntaiInfo.setLineId(lineId);
        tYuntaiInfo.setTowerId(towerId);
        if(yuntaiIds!=null) {
            tYuntaiInfo.setSpare0(StringUtils.arrayLong2Str(yuntaiIds, ","));
        }
        List<TYuntaiInfo> yuntaiList = tYuntaiInfoService.selectTYuntaiInfoListPage(tYuntaiInfo);


        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());

        for(TYuntaiInfo obj:yuntaiList){
            TYuntaiPicture tYuntaiPicture=new TYuntaiPicture();
            tYuntaiPicture.setPresetBit(presetBit);
            tYuntaiPicture.setYuntaiId(obj.getId());
            tYuntaiPicture.setSpare0(begDate);
            tYuntaiPicture.setSpare1(endDate);
            tYuntaiPicture.setLineId(lineId);
            tYuntaiPicture.setTowerId(towerId);
            tYuntaiPicture.setImgTypes(imgTypes);
            tYuntaiPicture.setCurrentUserId(loginUser.getUser().getUserId());
            tYuntaiPicture.setIsBrowse(isBrowse);
            tYuntaiPicture.setImgSize(imgSize);

            List<TYuntaiPicture> list = tYuntaiPictureService.selectTYuntaiPictureListByHistory(tYuntaiPicture);
            map.add(new HashMap<String, Object>(){{
                put("yuntai",obj);
                put("pictures",list);
            }});
        }
        return getDataTable2(map,tYuntaiInfoService.selectTYuntaiCount(tYuntaiInfo));
    }


    /**
     * 导出t_yuntai_picture（云台抓拍图片）列表
     */
    @ApiOperation("导出云台抓拍图片列表")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:export')")
    @Log(title = "t_yuntai_picture（云台抓拍图片）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiPicture tYuntaiPicture)
    {
        List<TYuntaiPicture> list = tYuntaiPictureService.selectTYuntaiPictureList(tYuntaiPicture);
        ExcelUtil<TYuntaiPicture> util = new ExcelUtil<TYuntaiPicture>(TYuntaiPicture.class);
        return util.exportExcel(list, "t_yuntai_picture（云台抓拍图片）数据");
    }

    /**
     * 获取t_yuntai_picture（云台抓拍图片）详细信息
     */
    @ApiOperation("获取云台抓拍图片详细")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiPictureService.selectTYuntaiPictureById(id));
    }

    /**
     * 新增t_yuntai_picture（云台抓拍图片）
     */
    @ApiOperation("新增云台抓拍图片")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:add')")
    @Log(title = "t_yuntai_picture（云台抓拍图片）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiPicture tYuntaiPicture)
    {
        return toAjax(tYuntaiPictureService.insertTYuntaiPicture(tYuntaiPicture));
    }

    /**
     * 修改t_yuntai_picture（云台抓拍图片）
     */
    @ApiOperation("修改云台抓拍图片")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:edit')")
    @Log(title = "t_yuntai_picture（云台抓拍图片）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiPicture tYuntaiPicture)
    {
        return toAjax(tYuntaiPictureService.updateTYuntaiPicture(tYuntaiPicture));
    }

    /**
     * 删除t_yuntai_picture（云台抓拍图片）
     */
    @ApiOperation("删除云台抓拍图片")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:remove')")
    @Log(title = "t_yuntai_picture（云台抓拍图片）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiPictureService.deleteTYuntaiPictureByIds(ids));
    }
}
