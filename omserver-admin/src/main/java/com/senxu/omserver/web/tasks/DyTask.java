package com.senxu.omserver.web.tasks;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.senxu.omserver.common.core.redis.RedisCache;
import com.senxu.omserver.common.utils.StringUtils;
import com.senxu.omserver.service.domain.*;
import com.senxu.omserver.service.mapper.*;
import com.senxu.omserver.web.tasks.server.DyTaskServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 需要在redis.conf开启
 * 1、开启key过期回调 notify-keyspace-events Ex
 * 2、AOF持久化 appendonly yes
 * 本地环境如果连接了测试环境的数据库则需要把定时任务注解注释，防止本地redis没有数据多次插入到测试数据库
 */
@Component
@EnableScheduling
public class DyTask {
    private static Logger logger = LoggerFactory.getLogger(task.class);

    @Autowired
    public DyTaskServer dyTaskServer;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TAirportMapper tAirportMapper;

    @Autowired
    private TAirportSlotbatteryMapper tAirportSlotbatteryMapper;

    @Autowired
    private TAirportBatteryMapper tAirportBatteryMapper;

    @Autowired
    private TTaskMapper tTaskMapper;

    @Autowired
    private TRouteMapper tRouteMapper;

    @Autowired
    private TRoutePointMapper tRoutePointMapper;

    @Autowired
    private TTaskHistoryMapper tTaskHistoryMapper;

    @Autowired
    private TTaskPictureMapper tTaskPictureMapper;

    /**
     * 1.机巢信息每天定时更新2次（晚上12点，中午1点各一次），对应t_airport；
     */
//    @Scheduled(cron="0 0 0,13 * * ?")
//    @Scheduled(fixedDelay = 5*60*1000,initialDelay = 20*1000)
    public void refreshAirPort(){
        List<TAirport> list1 = dyTaskServer.getStationarys();//固定机场
        List<TAirport> list2 = dyTaskServer.getPickups();//胶囊机场
        list1.addAll(list2);
        List<TAirportSlotbattery> list3 = new ArrayList<>();//机巢电池槽
        List<TAirportBattery> list4 = new ArrayList<>();//机巢电池

        for (TAirport tAirport : list1) {
            try{
                list3.clear();
                list4.clear();
                if("1".equals(tAirport.getRelname())){//固定机场
                    JSONObject result = dyTaskServer.getSnapshot(tAirport.getAirportCode());//请求成功的结果
                    if(result != null){
                        JSONObject status = result.getJSONObject("status");//机场状态
                        JSONObject devices = status.getJSONObject("devices");//设备

                        JSONObject airport = devices.getJSONObject("airport");//固定机场智能硬件状态

                        JSONObject ups = airport.getJSONObject("ups");//UPS状态
                        String upsStatus = ups.getString("status");//ups工作状态：“市电供电”或“电池箱供电”
                        tAirport.setCityElectricity(upsStatus);//--------------------------------------------------------------------------------------------(t_airport)供电类型

                        JSONObject plc = airport.getJSONObject("plc");//PLC状态
                        String actionStatus = plc.getString("actionStatus");//机场状态
                        tAirport.setActionStatus(actionStatus);//--------------------------------------------------------------------------------------------(t_airport)机场状态
                        Boolean smoke = plc.getBoolean("smoke");//是否发生烟雾
                        tAirport.setIsSmoke(smoke ? "1" : "0");//--------------------------------------------------------------------------------------------(t_airport)是否有烟雾
                        Boolean flood = plc.getBoolean("flood");//是否发生进水
                        tAirport.setIsPonding(flood ? "1" : "0");//------------------------------------------------------------------------------------------(t_airport)是否积水
                        Boolean intrusion = plc.getBoolean("intrusion");//是否有人闯入
                        tAirport.setIntrusion(intrusion ? "1" : "0");//--------------------------------------------------------------------------------------(t_airport)是否有人闯入

                        JSONArray slotBattery = airport.getJSONArray("slotBattery");//电池槽状态
                        for (int i = 0; i<slotBattery.size(); i++){
                            JSONObject slotBatteryObj = (JSONObject)slotBattery.get(i);
                            TAirportSlotbattery tAirportSlotbattery = new TAirportSlotbattery();
                            tAirportSlotbattery.setOrd(i+1);//-----------------------------------------------------------------------------------------------(t_airport_slotbattery)电池槽序号
                            tAirportSlotbattery.setState(slotBatteryObj.getString("status"));//---------------------------------------------------------(t_airport_slotbattery)电池槽的状态
                            tAirportSlotbattery.setOccupy(slotBatteryObj.getString("occupy"));//--------------------------------------------------------(t_airport_slotbattery)电池槽是否被占用
                            tAirportSlotbattery.setErrmsg(slotBatteryObj.getString("errMsg"));//--------------------------------------------------------(t_airport_slotbattery)电压表坏时的详细错误信息
                            tAirportSlotbattery.setUpdateTime(new Date());//---------------------------------------------------------------------------------(t_airport_slotbattery)数据同步时间
                            tAirportSlotbattery.setSpare0(tAirport.getAirportCode());//----------------------------------------------------------------------(t_airport_slotbattery)机巢ID在新增时无法获取，先将多翼id存到备用字段
                            list3.add(tAirportSlotbattery);

                            JSONArray battery = slotBatteryObj.getJSONArray("battery");//电池状态
                            for(int j = 0; j<battery.size(); j++){
                                JSONObject batteryObj = (JSONObject)battery.get(j);
                                TAirportBattery tAirportBattery = new TAirportBattery();
                                tAirportBattery.setState(batteryObj.getString("status"));//--------------------------------------------------------------(t_airport_battery)电池状态
                                JSONObject chargeVolt = batteryObj.getJSONObject("chargeVolt");//电压
                                String chargeVoltValue = chargeVolt.getString("value");//值
                                tAirportBattery.setChargevolt(chargeVoltValue);//-----------------------------------------------------------------------------(t_airport_battery)电压
                                JSONObject soc = batteryObj.getJSONObject("soc");//电量
                                String socValue = soc.getString("value");//值
                                tAirportBattery.setSoc(socValue);//-------------------------------------------------------------------------------------------(t_airport_battery)电量
                                tAirportBattery.setUpdateTime(new Date());//----------------------------------------------------------------------------------(t_airport_battery)数据同步时间
                                tAirportBattery.setSpare0(tAirport.getAirportCode());//-----------------------------------------------------------------------(t_airport_battery)机巢ID在新增时无法获取，先将多翼id存到备用字段
                                tAirportBattery.setSpare1(tAirportSlotbattery.getOrd()+"");//-----------------------------------------------------------------(t_airport_battery)电池槽ID在新增时无法获取，先将电池槽序号存到备用字段
                                list4.add(tAirportBattery);
                            }
                        }

                        JSONObject tempHumiTransmitterPost = airport.getJSONObject("tempHumiTransmitterPost");//舱体温湿度状态
                        JSONObject tempHumiTransmitterPostData = tempHumiTransmitterPost.getJSONObject("data");
                        JSONObject humidity = tempHumiTransmitterPostData.getJSONObject("humidity");//湿度
                        String humidityValue = humidity.getString("value");//值
                        tAirport.setHumidity(humidityValue);//-----------------------------------------------------------------------------------------------(t_airport)湿度
                        JSONObject temperature = tempHumiTransmitterPostData.getJSONObject("temperature");//温度
                        String temperatureValue = temperature.getString("value");//值
                        tAirport.setInTemperature(temperatureValue);//---------------------------------------------------------------------------------------(t_airport)舱内温度

                        JSONObject meteorologicalStation = airport.getJSONObject("meteorologicalStation");//气象站状态
                        String canTakeOff = meteorologicalStation.getString("canTakeOff");//当前气象数据是否满足飞行条件
                        tAirport.setMeteorologicalState(canTakeOff);//---------------------------------------------------------------------------------------(t_airport)气象站-状态是否满足飞行条件
                        JSONObject meteorologicalStationStatus = meteorologicalStation.getJSONObject("status");//这里接口取的值和文档的类型不一样，也比文档少了单位字段
                        String meteTemperatureCelsius = meteorologicalStationStatus.getString("temperatureCelsius");//温度 精度:1.0
                        tAirport.setMeteorologicalTemperature(meteTemperatureCelsius);//---------------------------------------------------------------------(t_airport)气象站-温度
                        String meteHumidity = meteorologicalStationStatus.getString("humidity");//湿度 精度:1.0
                        tAirport.setMeteorologicalHumidity(meteHumidity);//----------------------------------------------------------------------------------(t_airport)气象站-湿度
                        String meteWindSpeed = meteorologicalStationStatus.getString("windSpeed");//风速 精度:1.0
                        tAirport.setMeteorologicalWindspeed(meteWindSpeed);//--------------------------------------------------------------------------------(t_airport)气象站-风速
                        String meteWindDirection = meteorologicalStationStatus.getString("windDirection");//风向 精度:1.0
                        tAirport.setMeteorologicalWinddirection(meteWindDirection);//------------------------------------------------------------------------(t_airport)气象站-雨量
                        String meteRainfall = meteorologicalStationStatus.getString("rainfall");//雨量 精度:1.0
                        tAirport.setMeteorologicalRainfall(meteRainfall);//----------------------------------------------------------------------------------(t_airport)气象站-气压
                        String meteAirPressure = meteorologicalStationStatus.getString("airPressure");//气压 精度:1.0
                        tAirport.setMeteorologicalAirpressure(meteAirPressure);//----------------------------------------------------------------------------(t_airport)气象站-风向

                        JSONObject drone = devices.getJSONObject("drone");//固定机场无人机综合状态
                        JSONObject droneStatus = drone.getJSONObject("status");//无人机综合状态

                        JSONObject physicRC = droneStatus.getJSONObject("physicRC");//地面站、遥控器、无人机的连接状态
                        Boolean isAircraftConnected = physicRC.getBoolean("isAircraftConnected");//无人机连续状态
                        tAirport.setUavState(isAircraftConnected ? 1L : 0L);//-------------------------------------------------------------------------------(t_airport)无人机状态

                        JSONObject physicDrone = droneStatus.getJSONObject("physicDrone");//无人机物理状态，只有当地面站和无人机已连接时状态才可靠
                        String absoluteAltitude = physicDrone.getString("absoluteAltitude");//无人机当前海拔高度，单位：米
                        tAirport.setUavAltinm(absoluteAltitude);//-------------------------------------------------------------------------------------------(t_airport)高度（海拔）
                        String aircraftId = physicDrone.getString("aircraftId");//飞控id
                        tAirport.setFcId(aircraftId);//------------------------------------------------------------------------------------------------------(t_airport)飞控ID
                        JSONArray cameraType = physicDrone.getJSONArray("cameraType");//固定机场无人机云台负载类型信息
                        StringBuffer key = new StringBuffer();//负载类型
                        for(int i=0; i<cameraType.size(); i++){
                            JSONObject obj = (JSONObject)cameraType.get(i);
                            key.append(obj.getString("key")).append(",");
                        }
                        tAirport.setMount(key.toString());//-------------------------------------------------------------------------------------------------(t_airport)挂载设备
                        String chargeRemainingInPercent = physicDrone.getString("chargeRemainingInPercent");//电池电量百分比的整数部分， 0～100
                        tAirport.setUavBattery(chargeRemainingInPercent);//----------------------------------------------------------------------------------(t_airport)电量
                        String isRTKBeingUsed = physicDrone.getString("isRTKBeingUsed");//false 时，飞机不能正常飞行
                        tAirport.setUavRtk(isRTKBeingUsed);//------------------------------------------------------------------------------------------------(t_airport)RTK状态
                        String isVideoFeedOk = physicDrone.getString("isVideoFeedOk");//图传连接状态是否正常
                        tAirport.setUavTran(isVideoFeedOk);//------------------------------------------------------------------------------------------------(t_airport)数据传输

                        String droneId = drone.getString("droneId");//无人机ID
                        tAirport.setUavId(droneId);//--------------------------------------------------------------------------------------------------------(t_airport)无人机ID

                    }
                }

                //插入机巢实体类到数据库
                if(redisCache.isNotEmptySet("dyAirportCode",tAirport.getAirportCode())){
                    tAirportMapper.updateTAirportByCode(tAirport);
                }else{
                    Set<String> dataSet = new HashSet<>();
                    dataSet.add(tAirport.getAirportCode());
                    redisCache.setCacheSet("dyAirportCode", dataSet);
                    tAirportMapper.insertTAirport(tAirport);
                }

                Long airportId = tAirportMapper.selectAirportIdByAirportCode(tAirport.getAirportCode());//机巢id

                //先删除对应机巢所有电池槽再插入实体类到数据库
                tAirportSlotbatteryMapper.deleteTAirportSlotbatteryByAirportCode(tAirport.getAirportCode());
                for (TAirportSlotbattery tAirportSlotbattery : list3) {
                    tAirportSlotbattery.setAirportId(airportId);//机巢id
                    tAirportSlotbatteryMapper.insertTAirportSlotbattery(tAirportSlotbattery);
                }

                //先删除对应机巢所有电池再插入实体类到数据库
                tAirportBatteryMapper.deleteTAirportBatteryByAirportCode(tAirport.getAirportCode());
                for (TAirportBattery tAirportBattery : list4) {
                    TAirportSlotbattery tAirportSlotbattery = new TAirportSlotbattery();
                    tAirportSlotbattery.setSpare0(tAirportBattery.getSpare0());
                    tAirportSlotbattery.setOrd(Integer.valueOf(tAirportBattery.getSpare1()));
                    List<TAirportSlotbattery> list = tAirportSlotbatteryMapper.selectTAirportSlotbatteryList(tAirportSlotbattery);
                    Long slotbatteryId = list.get(0).getSlotbatteryId();
                    tAirportBattery.setSlotbatteryId(slotbatteryId);//电池槽id
                    tAirportBattery.setAirportId(airportId);//机巢id
                    tAirportBatteryMapper.insertTAirportBattery(tAirportBattery);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //分开的定时任务出现了先后顺序问题
        refreshTask();
        refreshRoute();
        refreshTaskHistory();
        refreshTaskPicture();
    }

    /**
     * 2.任务信息每5分钟更新一次，对应t_task；
     */
//    @Scheduled(fixedDelay = 5*60*1000,initialDelay = 40*1000)
    public void refreshTask(){
        List<String> list = dyTaskServer.getAssignment();
        for (String taskCode : list) {
            try{
                TTask tTask = dyTaskServer.getAssignmentByCode(taskCode);
                if(redisCache.isNotEmptySet("dyTaskCode",taskCode)){
                    tTaskMapper.updateTTaskByCode(tTask);
                }else{
                    Set<String> dataSet = new HashSet<>();
                    dataSet.add(taskCode);
                    redisCache.setCacheSet("dyTaskCode", dataSet);
                    tTaskMapper.insertTTask(tTask);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 3.航线信息每5分钟更新一次，对应t_route；
     * 4.航点信息每5分钟更新一次，对应t_route_point；
     */
//    @Scheduled(fixedDelay = 5*60*1000,initialDelay = 60*1000)
    public void refreshRoute(){
        List<String> list = dyTaskServer.getAssignment();
        for (String taskCode : list) {
            List<TRoute> TRouteList= dyTaskServer.getRouteByTaskCode(taskCode);
            for (TRoute tRoute:TRouteList) {
                //航线详情
                try{
                    tRoute = dyTaskServer.getRouteByCode(tRoute);
                    if(redisCache.isNotEmptySet("dyRouteCode",tRoute.getTaskId()+"&&"+tRoute.getRouteCode())){//修改成多对多关系
                        tRouteMapper.updateTRouteByCode(tRoute);
                    }else{
                        Set<String> dataSet = new HashSet<>();
                        dataSet.add(tRoute.getTaskId()+"&&"+tRoute.getRouteCode());
                        redisCache.setCacheSet("dyRouteCode", dataSet);
                        Long routeId = tRouteMapper.selectRouteIdByRouteCode(tRoute.getRouteCode());//查询出这条航线是否存在，保证routeId与routeCode一对一
                        if(routeId != null){
                            tRoute.setRouteId(routeId);
                        }
                        tRouteMapper.insertTRoute(tRoute);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                //航点详情
                try{
                    //先删除该航线下所有航点再插入
                    tRoutePointMapper.deleteTRoutePointByRouteId(tRouteMapper.selectRouteIdByRouteCode(tRoute.getRouteCode()));
                    List<TRoutePoint> TRoutePointList = dyTaskServer.getRoutePointByRouteCode(tRoute);
                    for (TRoutePoint tRoutePoint:TRoutePointList) {
                        tRoutePointMapper.insertTRoutePoint(tRoutePoint);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 5.历史巡航记录信息每5分钟更新一次，对应t_task_history；
     */
//    @Scheduled(fixedDelay = 5*60*1000,initialDelay = 60*1000)
    public void refreshTaskHistory(){
        List<TTaskHistory> list = dyTaskServer.inspectRecord();
        for (TTaskHistory tTaskHistory : list) {
            try{
                if(redisCache.isNotEmptySet("dyTaskhCode",tTaskHistory.getTaskCode())){
                    continue;//流水记录，有这个任务就跳过
                }else{
                    Set<String> dataSet = new HashSet<>();
                    dataSet.add(tTaskHistory.getTaskCode());
                    redisCache.setCacheSet("dyTaskhCode", dataSet);
                    tTaskHistoryMapper.insertTTaskHistory(tTaskHistory);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 6.巡航图片信息每5分钟更新一次，对应t_task_picture；
     */
//    @Scheduled(fixedDelay = 5*60*1000,initialDelay = 80*1000)
    public void refreshTaskPicture(){
        List<String> list = dyTaskServer.getAssignment();
        for (String taskCode : list) {
            Map<String,String[]> map = dyTaskServer.flightsResults(taskCode);//坐标map
            List<TRoute> TRouteList= dyTaskServer.getRouteByTaskCode(taskCode);
            for (TRoute tRoute:TRouteList) {
                List<TTaskPicture> TTaskPictureList = dyTaskServer.imagesAssignment(taskCode,tRoute.getRouteCode());
                for(TTaskPicture tTaskPicture : TTaskPictureList){
                    try{
                        if(redisCache.isNotEmptySet("dyImageCode",tTaskPicture.getImageCode())){
                            continue;//流水记录，有这个图片就跳过
                        }else{
                            Set<String> dataSet = new HashSet<>();
                            dataSet.add(tTaskPicture.getImageCode());
                            redisCache.setCacheSet("dyImageCode", dataSet);
                            String[] strings = map.get(tTaskPicture.getImageCode());
                            if(strings != null && strings.length>0){
                                tTaskPicture.setLat(strings[0]);
                                tTaskPicture.setLon(strings[1]);
                            }
                            tTaskPictureMapper.insertTTaskPicture(tTaskPicture);
                            dyTaskServer.images(tTaskPicture.getImageCode(),tTaskPicture.getImageName());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 9.所有在线无人机信息，每1秒钟更新一次，这里没有对应的数据表，更新到t_airport无人机状态就可以，如果查询结果发现在线无人机当前t_airport没有记录，则自动更新机巢信息
     */
//    @Scheduled(fixedDelay = 10000,initialDelay = 40*1000)
    public void refreshDrone(){
        TAirport airport = new TAirport();
        airport.setRelname("1");
        List<TAirport> dblist = tAirportMapper.selectTAirportList(airport);//数据库所有固定机场

        List<String> onLinelist = new ArrayList();//接口固定机场在线的无人机
        JSONArray array = dyTaskServer.getDronesOnLine();
        if(array == null){//调用失败，忽略
            return;
        }
        for(int i = 0; i<array.size(); i++){
            JSONObject object = (JSONObject)array.get(i);
            String droneId = object.getString("droneId");//无人机ID
            String stationaryId = object.getString("stationaryId");//所属固定机场ID
            if(StringUtils.isNotEmpty(stationaryId)){
                onLinelist.add(droneId);
            }
        }

        for(TAirport tAirport : dblist){
            boolean flag = false;//标记位，是否接口查得到
            for(String droneId : onLinelist){
                if(droneId.equals(tAirport.getUavId()) && tAirport.getUavState() == 0L){//接口在线，数据库不在线需要更新和修改标志位
                    tAirport.setUavState(1L);
                    tAirportMapper.updateTAirport(tAirport);
                    flag = true;
                    break;
                }else if (droneId.equals(tAirport.getUavId()) && tAirport.getUavState() == 1L){//接口在线，数据库在线，修改标记位
                    flag = true;
                    break;
                }
            }
            //接口不在线，数据库在线需要更新
            if(tAirport.getUavState() == null || (!flag && tAirport.getUavState() == 1L)){
                tAirport.setUavState(0L);
                tAirportMapper.updateTAirport(tAirport);
            }
        }
    }

}
