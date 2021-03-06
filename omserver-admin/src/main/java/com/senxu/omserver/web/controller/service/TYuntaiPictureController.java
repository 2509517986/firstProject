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
 * t_yuntai_picture????????????????????????Controller
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Api("??????????????????")
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
     * ??????t_yuntai_picture??????????????????????????????
     */
    @ApiOperation("??????????????????????????????")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiPicture tYuntaiPicture)
    {
        startPage();
        List<TYuntaiPicture> list = tYuntaiPictureService.selectTYuntaiPictureList(tYuntaiPicture);
        return getDataTable(list);
    }


    /**
     * ??????t_yuntai_picture????????????????????????
     */
    @ApiOperation("??????????????????")
    /*@PreAuthorize("@ss.hasPermi('service:yuntaiPicture:manualCap')")*/
    /* @Log(title = "t_yuntai_picture????????????????????????", businessType = BusinessType.INSERT)*/
    @PostMapping("/manualCap")
    @SneakyThrows
    public AjaxResult manualCapPres(@ApiParam(value = "?????????",defaultValue = "0") @RequestParam Integer presetBit ,
                                    @ApiParam(value = "??????????????????",defaultValue = "0") @RequestParam Integer manualCapType,
                                @RequestParam Long yunTaiId,@RequestParam(required =  false) Integer sync)
    {//,@ApiParam(value = "????????????",defaultValue = "1") @RequestParam Integer sync
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String username = loginUser.getUsername();
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yunTaiId);
        //????????????
       // Integer sync=null;
        if(sync==null){
            sync=0;//1 ?????????????????????
        }
        //??????0?????????
        if(presetBit==null){
            presetBit=0;
        }
        //???????????????????????????
        if(manualCapType==null){
            manualCapType=0;
        }


        JSONObject r=new JSONObject();
        log.info("???????????????????????????");
        r.put("success",true);
        ManualCapTypeEnum capTypeEnum=ManualCapTypeEnum.valueOf(manualCapType);
        //  /** ???????????????1 ?????? 0 ?????????*/
        yuntaiInfo.setImageStatus(new Long(1));
        tYuntaiInfoService.updateTYuntaiInfo(yuntaiInfo);

       // Result result=new Result();
        AjaxResult result1;
       // result.setMsg("????????????????????????????????????");
        if(capTypeEnum==ManualCapTypeEnum.CurPreset) {
            return capPic( yuntaiInfo,0,  sync,username);
        }
        else if(capTypeEnum==ManualCapTypeEnum.AllPreset) {
            TYuntaiPresetPosition yuntaiPresetPositionQuery=new TYuntaiPresetPosition();
            yuntaiPresetPositionQuery.setYuntaiId(yunTaiId);
            List<TYuntaiPresetPosition> list= yuntaiPresetPositionService.selectTYuntaiPresetPositionList(yuntaiPresetPositionQuery);
            AjaxResult ajaxResult=AjaxResult.success("???????????????????????????");

            new Thread(
                    ()->{
                        for (int i = 0; i < list.size(); i++) {
                            TYuntaiPresetPosition yuntaiPresetPosition=list.get(i);
                            capPic( yuntaiInfo,Integer.parseInt(yuntaiPresetPosition.getPresetBit()),  0,username);
                            try {
                                Thread.currentThread().sleep(60000);//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
                    throw new Exception("?????????????????????");
                }
             return capPic( yuntaiInfo,presetBit, sync,username);

        }

        return  AjaxResult.error("???????????????");
    }

    private AjaxResult capPic(TYuntaiInfo yuntaiInfo,Integer preset,  Integer sync,String username) {
        Date nowDate = new Date();
        String now =  DateUtils.parseDateToStr( "yyyyMMddHHmmss", nowDate);
        if(preset==0){
            log.info("??????????????????");
        }
        else{
            log.info(yuntaiInfo.getCode()+"?????????????????????"+preset);
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
                    log.info("???????????????"+picName);
                    log.info("???????????????"+imageDir);
                    picture.setImageName(picName);
                    picture.setImageDir(imageDir);
                    picture.setChannelNumber(String.valueOf(record.getChannelNo()));
                    picture.setPresetBit(String.valueOf(record.getPreset()));
//                        yuntaiPicture.setImgType();//??????????????????  TODO
                    picture.setIsAlarm(0);
                    tYuntaiPictureService.insertTYuntaiPicture(picture);
                    o.put("capSuccess",true);

                    //??????????????????
                    yuntaiInfo.setImageStatus(new Long(1));
                    tYuntaiInfoService.updateTYuntaiInfo(yuntaiInfo);
                    //TODO:?????????????????????
                }
            });
            if(!response.isSuccess()){
                return AjaxResult.error(response.getMessage());
            }
        }
        catch (Exception ex){
          //  result.setMsg();
            return AjaxResult.error("???????????????????????????????????????????????????");
        }


        if(sync==1){
            Date begin=new Date();
            boolean outTime=false;
            while(true){
                boolean capSuccess= o.getBoolean("capSuccess");
                Date now1=new Date();
                if(now1.getTime()-begin.getTime()>60*3*1000){
                    return  AjaxResult.error("?????????????????????");
                }
                if(capSuccess){
                    return  AjaxResult.success("?????????????????????");
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
            return  AjaxResult.success("?????????????????????????????????");
        }
       /* if(async==0){
            waitResult(yuntaiInfo,"0",now,result,username);
        }*/
    }

    /**
     * ????????????????????????????????????????????????
     * @param yuntaiInfo
     * @param begintime
     * @param result
     */
    @SneakyThrows
    private void waitResult(TYuntaiInfo yuntaiInfo, String presetBit,String begintime, Result result,String username) {
        int count=0;
        while(true) {
            if (count > 30) {//?????????30?????????
                result.setSuccess(false);
                result.setMsg("?????????????????????????????????????????????");
                break;
            }
            TYuntaiPicture yuntaiPicture = new TYuntaiPicture();
            yuntaiPicture.setYuntaiId(yuntaiInfo.getId());
            yuntaiPicture.setPresetBit(presetBit);
            yuntaiPicture.setCaptureType(CapPicTypeEnum.DomainReqCap.getCode());
            yuntaiPicture.setSpare0(begintime);//?????????????????????????????????????????????
            yuntaiPicture.setCaptureUser(username);
            List<TYuntaiPicture> l = tYuntaiPictureService.selectTYuntaiPictureListByHistory(yuntaiPicture);
            if (l.size() > 0) {
                result.setMsg("???????????????");
                result.setSuccess(true);
                break;
            }
            Thread.sleep(1000);
            count++;
        }
    }

    /**
     * capShortVideo???????????????????????????
     */
    @ApiOperation("???????????????")
    @PostMapping("/capShortVideo")
    @SneakyThrows
    public AjaxResult capShortVideo(
                                    @RequestParam  Long yunTaiId,
                                    @ApiParam(required = false,value = "???????????? ,????????????")
                                    @RequestParam(required = false) Integer time,
                                    @ApiParam(required = false,value = "????????????")
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
                //??????????????????
                String url=shortVideoUploadVO.getFilePathName();//.replaceAll(omServerProperties.shortVideoPath,omServerProperties.shortVideoMappingBasePath);
                //?????????????????????
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
                log.info("????????????????????????");
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
                            return  AjaxResult.error("????????????");
                        }
                        if(capSuccess){
                            return  AjaxResult.success("????????????????????????");
                        }
                        else {
                            Thread.sleep(1000);
                        }
                    }
                }
                else {
                    return  AjaxResult.success("????????????????????????????????????");
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
     * ????????????????????????????????????
     */
    @ApiOperation("????????????????????????????????????")
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
     * ?????????????????????????????????????????????????????????
     */
    @ApiOperation("?????????????????????????????????????????????????????????")
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
     * ????????????????????????????????????
     * @return
     */
    @ApiOperation("????????????????????????????????????")
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


        //??????????????????
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
     * ??????t_yuntai_picture??????????????????????????????
     */
    @ApiOperation("??????????????????????????????")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:export')")
    @Log(title = "t_yuntai_picture????????????????????????", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiPicture tYuntaiPicture)
    {
        List<TYuntaiPicture> list = tYuntaiPictureService.selectTYuntaiPictureList(tYuntaiPicture);
        ExcelUtil<TYuntaiPicture> util = new ExcelUtil<TYuntaiPicture>(TYuntaiPicture.class);
        return util.exportExcel(list, "t_yuntai_picture??????????????????????????????");
    }

    /**
     * ??????t_yuntai_picture????????????????????????????????????
     */
    @ApiOperation("??????????????????????????????")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiPictureService.selectTYuntaiPictureById(id));
    }

    /**
     * ??????t_yuntai_picture????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:add')")
    @Log(title = "t_yuntai_picture????????????????????????", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiPicture tYuntaiPicture)
    {
        return toAjax(tYuntaiPictureService.insertTYuntaiPicture(tYuntaiPicture));
    }

    /**
     * ??????t_yuntai_picture????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:edit')")
    @Log(title = "t_yuntai_picture????????????????????????", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiPicture tYuntaiPicture)
    {
        return toAjax(tYuntaiPictureService.updateTYuntaiPicture(tYuntaiPicture));
    }

    /**
     * ??????t_yuntai_picture????????????????????????
     */
    @ApiOperation("????????????????????????")
    @PreAuthorize("@ss.hasPermi('service:yuntaiPicture:remove')")
    @Log(title = "t_yuntai_picture????????????????????????", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiPictureService.deleteTYuntaiPictureByIds(ids));
    }
}
