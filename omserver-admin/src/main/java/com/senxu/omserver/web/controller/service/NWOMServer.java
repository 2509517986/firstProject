package com.senxu.omserver.web.controller.service;

import com.google.gson.Gson;
import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.common.core.domain.entity.SysDictData;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.TimerUtil;
import com.senxu.omserver.service.domain.TYuntaiAlarm;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.domain.TYuntaiPicture;
import com.senxu.omserver.service.domain.TYuntaiVideo;
import com.senxu.omserver.service.service.ITYuntaiAlarmService;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.service.service.ITYuntaiPictureService;
import com.senxu.omserver.system.service.ISysDictDataService;
import com.senxu.omserver.web.enums.CapPicTypeEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;
import senxu.com.base.dto.DeviceInfo;
import senxu.com.base.util.UdpManager;
import senxu.com.base.vo.ImageAlarmRecord;
import senxu.com.base.vo.ShortVideoUploadVO;
import senxu.com.cmd.BridgeContext;
import senxu.com.cmd.config.AgentConfig;
import senxu.com.cmd.enums.CmdCtrlEnum;
import senxu.com.cmd.enums.UpgradeStatusEnum;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.service.IDeviceBridge;
import senxu.com.service.IServerBridge;
import senxu.com.service.ServiceCallback;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther yudarr
 * @date 2021/7/4 22:58
 **/
@Component
@Slf4j
public class NWOMServer {

    public IDeviceBridge iDeviceBridge = null;
    public IServerBridge iServerBridge = null;
    public BridgeContext bridgeContext;
    public ConcurrentHashMap registerDevs = new ConcurrentHashMap();
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private ITYuntaiPictureService yuntaiPictureService;
    @Autowired
    private ITYuntaiAlarmService yuntaiAlarmService;
    @Autowired
    private DataSource masterDataSource;
    public static java.util.List<TYuntaiInfo> onlineDevices;
    public static ConcurrentHashMap<String, TYuntaiInfo> onlineDeviceMap = new ConcurrentHashMap();
    /*
     * 手动拍照的图片
     * 由于设备服务端区分不了手动还策略拍照，故手动拍了后存在该信息上
     * key: "设备编号_通道_预置位“  value:1
     */
    public static ConcurrentHashMap manualCaps = new ConcurrentHashMap();
  /*  public static String capPicPath = "c://capPic";
    ;
    public static String shortVideoPath = "c://shortVideo";
    ;
    public static String mappingBasePath = "capPic";//"capPic";//profile已定义了免登陆
    public static String shortVideoMappingBasePath = "shortVideo";*/
    @Autowired
    public  OmServerProperties omServerProperties;
    @Autowired
    public ISysDictDataService sysDictDataService;
    public IDeviceBridge getDeviceBridge() {
        if (iDeviceBridge == null) {
            init();
        }
        return iDeviceBridge;
    }

    @SneakyThrows
    /*@ConditionalOnBean({FilterRegistrationBean.class})*/
    @PostConstruct
    public void init() {
        log.info("开启南网服务器。。");


        //查询数据库获取配置信息
        /*SysDictData sysDictData=new SysDictData();
        sysDictData.setDictType("nw_server_config");
        Map nwServerConfig=new HashMap();
        java.util.List<SysDictData>  sysDictDataList=sysDictDataService.selectDictDataList(sysDictData);
        for (int i = 0; i <sysDictDataList.size(); i++) {
            sysDictDataList.get(i)
        }*/


        //先将设备设成离线
/*
        if(Base.path==null){
            log.info("设置Base.path。。");
            String classPath = PathUtil.getClassPath();
            // classPath="jar:D:\\ideaWorksplace\\sxty\\web\\target\\web-1.0-SNAPSHOT.jar!\\BOOT-INF\\classes!\\";
            log.info("classPath:"+classPath);
            Base.path = new File(classPath);
        }
        Var.load();*/
        //查询所有在线的//由于处理不了当重新编译时，dev-tool导致数据库连接已关闭的问题，将连接写在定时器外
        /*try {

         *//*
            for (int i = 0; i < onlineDevices.size(); i++) {
                onlineDeviceMap.put(onlineDevices.get(i).getCode(),onlineDevices.get(i));
            }*//*

            //DbUtil.query("select * from audit_device_info where  on_line=1 ",null,false);
        } catch (Exception e) {
            //当重新编译时，dev-tool起作用，会导致数据库连接已关闭
                   *//* if(e instanceof CannotGetJdbcConnectionException){
                        masterDataSource..masterDataSource.getConnection().getConnection().
                     }*//*
            e.printStackTrace();
        }*/
        //每1分钟判断 是隔了10分钟无收心跳
        TimerUtil.start(5000, 60000, new Runnable() {
            @Override
            public void run() {
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setState(1l);
                if(tYuntaiInfoService!=null){
                    onlineDevices = tYuntaiInfoService.selectTYuntaiInfoListHeartbeat(tYuntaiInfo);
                    for (int i = 0; onlineDevices != null && i < onlineDevices.size(); i++) {
                        TYuntaiInfo dev = onlineDevices.get(i);
                        if (dev.getOnlineTime() != null) {
                            Date lastJumpTime = (Date) dev.getOnlineTime();
                            String device_no = dev.getCode();

                            if (DateUtils.addSeconds(lastJumpTime, 10 * 60).before(DateUtils.getNowDate())
                            ) {
                                log.info(device_no + "下线了！！");
                                dev.setState(-1l);
                                dev.setOnlineTime(DateUtils.getNowDate());
                                tYuntaiInfoService.updateTYuntaiInfo(dev);
                            }
                        }
                    }
                }
            }
        });
        //当前为上级服务器调用主站接口
        BridgeContext bridgeContext = new BridgeContext();
        this.bridgeContext = bridgeContext;

        AgentConfig agentConfig = AgentConfig.builder().imagePath(omServerProperties.capPicPath).udpPort(omServerProperties.serverPort)
                .offLine(1).shortVideoPath(omServerProperties.shortVideoPath).videoPort(omServerProperties.videoPort)
                .hisVideoPort(omServerProperties.hisVideoPort).build();
        IServerBridge iServiceBridge = new IServerBridge() {
           /* @Override
            public void requestToServer(String deviceCode, CmdCtrlEnum cmdName, JSONObject cmdProperties, ServiceCallback<DeviceCommandResponse> callback) {
                log.info("设备回调,deviceCode={},cmdName={},response={}", deviceCode, cmdName, cmdProperties == null ? "{}" : cmdProperties);
            }*/

            @Override
            public void requestToServer(String s, CmdCtrlEnum cmdCtrlEnum, net.sf.json.JSONObject jsonObject, ServiceCallback<DeviceCommandResponse> serviceCallback) {
                System.out.println(" ");
                System.out.println("----------------------------");
            }

            @Override
            public void register(DeviceInfo deviceInfo) {
                log.info("设备{}注册/心跳", deviceInfo.getDeviceCode());
                TYuntaiInfo tYuntaiInfoCondi = new TYuntaiInfo();
                //查询是否有该设备
                tYuntaiInfoCondi.setCode(deviceInfo.getDeviceCode());
                java.util.List yuntais = tYuntaiInfoService.selectTYuntaiInfoListHeartbeat(tYuntaiInfoCondi);
                if (yuntais.size() == 0) {//必须先有设备
                    return;
                }
                TYuntaiInfo tYuntaiInfo = (TYuntaiInfo) yuntais.get(0);
                tYuntaiInfo.setState(1l);
                tYuntaiInfo.setOnlineTime(DateUtils.getNowDate());
                tYuntaiInfo.setIp(deviceInfo.getHost());

               /* TYuntaiInfo tYuntaiInfo2=onlineDeviceMap.get(tYuntaiInfo.getCode());
                if(tYuntaiInfo2!=null){
                    tYuntaiInfo2.setOnlineTime(DateUtils.getNowDate());
                    tYuntaiInfo2.setState(1l);
                }*/

                tYuntaiInfoService.updateTYuntaiInfo(tYuntaiInfo);
                //最好有注册时间
               /* if(registerDevs.get(tYuntaiInfo.getCode())==null)
                {
                    tYuntaiInfo.seto.put("register_time", DateUtils.getDate());
                }


                registerDevs.put(deviceInfo.getDeviceCode(),tYuntaiInfo);*/
            }

            @Override
            public void offLine(DeviceInfo deviceInfo) {
                log.info("设备{}离线", deviceInfo.getDeviceCode());
                TYuntaiInfo yuntaiInfo = new TYuntaiInfo();
                yuntaiInfo.setCode(deviceInfo.getDeviceCode());
                yuntaiInfo.setState(-1l);//.put("on_line",0);
                yuntaiInfo.setOnlineTime(DateUtils.getNowDate());
                yuntaiInfo.setIp(deviceInfo.getHost());
                tYuntaiInfoService.updateTYuntaiInfo(yuntaiInfo);
                // DbUtil.updateJsonByIdField("audit_device_info", o,"device_code");
            }

            @SneakyThrows
            @Override
            public void pictureAlarm(String deviceCode, ImageAlarmRecord record) {
                log.info("设备{}智能数据上报{}", deviceCode, new Gson().toJson(record));
                String realBasePath = "";

                // String relativePath=record.getImagePath().replace(Var.getString(Constant.realBasePath),"");
                //获取相对路径
                String relativePath = record.getImagePath().replace(omServerProperties.capPicPath, "").replaceAll("//", "/");
                // log.info("相对的路径："+relativePath);

                //获取图片名称
                String[] paths = record.getImagePath().split("//");
                String picName = paths[paths.length - 1];
                String imageDir = "/" + omServerProperties.mappingBasePath + relativePath.replace(picName, "");
                log.info("图片名称：" + picName);
                log.info("图片目录：" + imageDir);
                //手动抓拍
                // String picPath = Var.getString(Constant.mappingBasePath) + relativePath;
                // String picPath = mappingBasePath + relativePath;
                //if(record.getAlarmType()==0){
                // 如果是手动  "设备编号_通道_预置位“
                if (manualCaps.containsKey(deviceCode + "_" + record.getChannelNo() + "_" + record.getPreset())) {
                    //create_time:"{#sys.now#}",type:2,type_name:"手动抓拍"
//                    TYuntaiPicture yuntaiPicture = (TYuntaiPicture) manualCaps.get(deviceCode + "_" + record.getChannelNo() + "_" + record.getPreset());
//                    yuntaiPicture.setImageName(picName);
//                    yuntaiPicture.setImageDir(imageDir);
//                    yuntaiPicture.setChannelNumber(String.valueOf(record.getChannelNo()));
//                    yuntaiPicture.setPresetBit(String.valueOf(record.getPreset()));
////                        yuntaiPicture.setImgType();//设置图片类型  TODO
//                    yuntaiPicture.setIsAlarm(0);
//
//                    yuntaiPictureService.insertTYuntaiPicture(yuntaiPicture);

                    //  manualCaps.remove(deviceCode+"_"+record.getChannelNo()+"_"+record.getPreset());
                } else {
                    if(record.getAlarmType() != 0 && record.getDetails().size() > 0){
                        TYuntaiInfo tYuntaiInfo = tYuntaiInfoService.selectTYuntaiInfoByCode(deviceCode);
                        TYuntaiPicture yuntaiPicture=new TYuntaiPicture();
                        yuntaiPicture.setYuntaiId(tYuntaiInfo.getId());
                        yuntaiPicture.setLineId(tYuntaiInfo.getLineId());
                        yuntaiPicture.setTowerId(tYuntaiInfo.getTowerId());
                        yuntaiPicture.setImageName(picName);
                        yuntaiPicture.setImageDir(imageDir);
                        yuntaiPicture.setFactory(tYuntaiInfo.getFactory());
                        yuntaiPicture.setCaptureType(CapPicTypeEnum.AlarmCap.getCode());
                        yuntaiPicture.setCaptureUser("admin");
                        yuntaiPicture.setCaptureTime(record.getAlarmDate());
                        yuntaiPicture.setChannelNumber(String.valueOf(record.getChannelNo()));
                        yuntaiPicture.setPresetBit(String.valueOf(record.getPreset()));
                        yuntaiPicture.setIsAlarm(1);
                        yuntaiPictureService.insertTYuntaiPicture(yuntaiPicture);
                        tYuntaiInfo.setImageStatus(new Long(1));
                        tYuntaiInfoService.updateTYuntaiInfo(tYuntaiInfo);
                        TYuntaiAlarm tYuntaiAlarm = new TYuntaiAlarm();
                        tYuntaiAlarm.setAlarmDir(yuntaiPicture.getImageDir());
                        tYuntaiAlarm.setAlarmUrl(yuntaiPicture.getImageName());
                        tYuntaiAlarm.setYuntaiId(yuntaiPicture.getYuntaiId());
                        tYuntaiAlarm.setCaptureUser("admin");
                        tYuntaiAlarm.setDealUser("admin");
                        tYuntaiAlarm.setConfirmTime(record.getAlarmDate());
                        tYuntaiAlarm.setTowerId(yuntaiPicture.getTowerId());
                        tYuntaiAlarm.setLineId(yuntaiPicture.getLineId());
                        tYuntaiAlarm.setFactory(yuntaiPicture.getFactory());
                        tYuntaiAlarm.setChannelNumber(yuntaiPicture.getChannelNumber());
                        tYuntaiAlarm.setPresetBit(yuntaiPicture.getPresetBit());
                        tYuntaiAlarm.setAlarmSource("2");
                        tYuntaiAlarm.setAlarmDate(new Date());//告警时间为什么原来是直接取抓拍时间呢？
                        tYuntaiAlarm.setConfirmState(0);
                        tYuntaiAlarm.setIsAlarm(1);
                        tYuntaiAlarm.setImgType(tYuntaiAlarm.getImgType());
                        yuntaiAlarmService.insertTYuntaiAlarm(tYuntaiAlarm);
                    }else{
                        TYuntaiInfo tYuntaiInfo = tYuntaiInfoService.selectTYuntaiInfoByCode(deviceCode);
                        TYuntaiPicture yuntaiPicture=new TYuntaiPicture();
                        yuntaiPicture.setYuntaiId(tYuntaiInfo.getId());
                        yuntaiPicture.setLineId(tYuntaiInfo.getLineId());
                        yuntaiPicture.setTowerId(tYuntaiInfo.getTowerId());
                        yuntaiPicture.setImageName(picName);
                        yuntaiPicture.setImageDir(imageDir);
                        yuntaiPicture.setFactory(tYuntaiInfo.getFactory());
                        yuntaiPicture.setCaptureType(CapPicTypeEnum.TimerCap.getCode());
                        yuntaiPicture.setCaptureUser("admin");
                        yuntaiPicture.setCaptureTime(record.getAlarmDate());
                        yuntaiPicture.setChannelNumber(String.valueOf(record.getChannelNo()));
                        yuntaiPicture.setPresetBit(String.valueOf(record.getPreset()));
                        yuntaiPicture.setIsAlarm(0);
                        yuntaiPictureService.insertTYuntaiPicture(yuntaiPicture);
                        tYuntaiInfo.setImageStatus(new Long(1));
                        tYuntaiInfoService.updateTYuntaiInfo(tYuntaiInfo);
                       /* AuditCapPicServ.save(deviceCode, CapPicTypeEnum.TimerCap.getCode(),record.getImagePath(),record.getPreset(), picPath,
                                picName,0);*/
                    }

                }
               /* }
                //智能报警
                else{
                   *//* JSONObject o=AuditCapPicServ.save(deviceCode,CapPicTypeEnum.AlarmCap.getCode(),record.getImagePath(),record.getPreset(), picPath,
                            picName,1);
                    AuditAlermServ.saveAIAlerm(deviceCode,o.optString("id"));*//*
                    TYuntaiPicture yuntaiPicture=new TYuntaiPicture();
                    yuntaiPictureService.insertTYuntaiPicture(yuntaiPicture);
                    TYuntaiAlarm yuntaiAlarm=new TYuntaiAlarm();
                    yuntaiAlarmService.insertTYuntaiAlarm(yuntaiAlarm);
                }*/
            }

            @Override
            public void online(DeviceInfo deviceInfo) {
                log.info("设备{}在线", deviceInfo.getDeviceCode());
                System.out.println(111);
            }

            @Override
            public void dormancyNotice(DeviceInfo deviceInfo) {

            }

            @Override
            public void shortVideoUpload(String deviceCode, ShortVideoUploadVO shortVideoUploadVO) {

            }

            @Override
            public void upgradeProgressNotice(String s, int i, int i1) {

            }

            @Override
            public void upgradeProgressNotice(String s, int i, UpgradeStatusEnum upgradeStatusEnum, Date date, Date date1, String s1, String s2) {

            }
        };


        try {
            iDeviceBridge = bridgeContext.init(agentConfig, iServiceBridge);//启动主站
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  iDeviceBridge.remoteModulation(deviceCode,   "1234",   1,   5,   0);

    }
}