package com.senxu.omserver.web.tasks.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.senxu.omserver.common.constant.DyUrlConstants;
import com.senxu.omserver.service.domain.*;
import com.senxu.omserver.service.mapper.TAirportMapper;
import com.senxu.omserver.service.mapper.TRouteMapper;
import com.senxu.omserver.service.mapper.TTaskHistoryMapper;
import com.senxu.omserver.service.mapper.TTaskMapper;
import com.senxu.omserver.service.service.DyLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Service
public class DyTaskServer {
    @Autowired
    public DyLoginService dyLoginService;

    @Autowired
    private TAirportMapper tAirportMapper;

    @Autowired
    private TTaskMapper tTaskMapper;

    @Autowired
    private TRouteMapper tRouteMapper;

    @Autowired
    private TTaskHistoryMapper tTaskHistoryMapper;

    /**
     * 获取所有固定机场
     * @return
     */
    public List<TAirport> getStationarys() {
        List<TAirport> list = new ArrayList<TAirport>();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.stationarys,params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONArray result = object.getJSONArray("result");//请求成功的结果
                for (int i = 0; i < result.size(); i++) {
                    TAirport tAirport = new TAirport();
                    JSONObject obj = (JSONObject) result.get(i);
                    tAirport.setAirportCode(obj.getString("stationaryId"));//机巢编号(多翼的ID)
                    tAirport.setAirportName(obj.getString("name"));//机场名称
                    tAirport.setRelname("1");//机场类型
                    tAirport.setLocationLat(obj.getString("lat"));//所在位置-纬度
                    tAirport.setLocationLon(obj.getString("lon"));//所在位置-经度
                    tAirport.setLocationAltinm(obj.getString("alt"));//所在位置-高度(海拔)
                    if (obj.getJSONArray("alternatePoints").size() > 0) {
                        JSONObject alternate = (JSONObject) obj.getJSONArray("alternatePoints").get(0);
                        tAirport.setSpareLat(alternate.getString("lat"));//预设备降点-纬度
                        tAirport.setSpareLon(alternate.getString("lon"));//预设备降点-经度
                        tAirport.setSpareAltinm(alternate.getString("altInM"));//预设备降点-高度(海拔)
                    }
                    JSONArray cameraType = obj.getJSONArray("cameraType");//固定机场无人机云台负载类型信息
                    StringBuffer key = new StringBuffer();//负载类型
                    for(int j=0; j<cameraType.size(); j++){
                        JSONObject cameraTypeObj = (JSONObject)cameraType.get(j);
                        key.append(cameraTypeObj.getString("key")).append(",");
                    }
                    tAirport.setMount(key.toString());//挂载设备
                    tAirport.setState(obj.getBoolean("isOnLine") ? 1L : 0L);//状态
                    tAirport.setUpdateTime(new Date());//数据同步时间
                    list.add(tAirport);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    /**
     * 获取所有胶囊机场
     * @return
     */
    public List<TAirport> getPickups() {
        List<TAirport> list = new ArrayList<TAirport>();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.pickups,params);
            if(!dyJson.startsWith("{")){
                return list;
            }
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONArray result = object.getJSONArray("result");//请求成功的结果
                for (int i = 0; i < result.size(); i++) {
                    TAirport tAirport = new TAirport();
                    JSONObject obj = (JSONObject) result.get(i);
                    tAirport.setAirportCode(String.valueOf(obj.getString("pickupId")));//机巢编号(多翼的ID)
                    tAirport.setAirportName(obj.getString("name"));//机场名称
                    tAirport.setRelname("2");//机场类型
                    tAirport.setState(obj.getBoolean("isOnLine") ? 1L : 0L);//状态
                    tAirport.setUpdateTime(new Date());//数据同步时间
                    list.add(tAirport);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    /**
     * 获取固定机场状态最新快照V2 返回json，对应插入t_airport（机巢信息表）、t_airport_battery（机巢电池表）、t_airport_slotbattery（机巢电池槽）
     * @return
     */
    public JSONObject getSnapshot(String airportCode) {
        JSONObject result = null;
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.airportV2+airportCode+"/snapshot",params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                result = object.getJSONObject("result");//请求成功的结果
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 获取所有在线的无人机 返回json，对应插入t_airport（机巢信息表）
     * @return
     */
    public JSONArray getDronesOnLine() {
        JSONArray result = null;
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.dronesOnLine,params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                result = object.getJSONArray("result");//请求成功的结果
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 获取所有任务编号
     * @return
     */
    public List<String> getAssignment() {
        List<String> list = new ArrayList<String>();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.assignment,params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONObject result = object.getJSONObject("result");//请求成功的结果
                JSONArray array = result.getJSONArray("idList");
                for (int i = 0; i < array.size(); i++) {
                    String taskCode = array.getString(i);//任务编号
                    list.add(taskCode);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    /**
     * 获取任务详情
     * @return
     */
    public TTask getAssignmentByCode(String taskCode) {
        TTask tTask = new TTask();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.assignment+taskCode,params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONObject result = object.getJSONObject("result");//请求成功的结果
                tTask.setAirportId(tAirportMapper.selectAirportIdByAirportCode(result.getString("airportId")));//机巢ID
                tTask.setTaskCode(result.getString("id"));//任务编号(多翼的ID)
                tTask.setDescribes(result.getString("taskDescription"));//任务描述
                tTask.setState(result.getString("status"));//任务状态
                tTask.setCreateTime(new Date(result.getLong("createdAt")));//创建毫秒时间截
                tTask.setStartTime(result.getDate("startTime"));//任务计划开始时间
                tTask.setEndTime(result.getDate("endTime"));//任务过期时间
                tTask.setUpdateTime(new Date());//数据同步时间
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return tTask;
        }
    }

    /**
     * 根据任务编号获取航线编号
     * @return
     */
    public List<TRoute> getRouteByTaskCode(String taskCode) {
        List<TRoute> list = new ArrayList<TRoute>();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.assignment+taskCode,params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONObject result = object.getJSONObject("result");//请求成功的结果
                JSONArray array = result.getJSONArray("routeIdList");
                Long taskId = tTaskMapper.selectTaskIdByTaskCode(taskCode);
                for (int i = 0; i < array.size(); i++) {
                    TRoute tRoute = new TRoute();
                    tRoute.setTaskId(taskId);//任务ID
                    tRoute.setRouteCode(array.getString(i));//航线编号(多翼的id)
                    list.add(tRoute);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    /**
     * 根据航线ID获取精确航线信息
     * @return
     */
    public TRoute getRouteByCode(TRoute tRoute) {
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.route+tRoute.getRouteCode(),params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONObject result = object.getJSONObject("result");//请求成功的结果
                tRoute.setName(result.getString("routeName"));//航线名称
                tRoute.setDescribes(result.getString("routeDescription"));//航线描述
                tRoute.setGroupId(result.getString("groupId"));//航线组ID（多翼的ID）
                tRoute.setKind(result.getString("routeType"));//航线类型
                tRoute.setStartLat(result.getJSONObject("startPoint").getString("lat"));//航线安全起点-纬度
                tRoute.setStartLon(result.getJSONObject("startPoint").getString("lon"));//航线安全起点-经度
                tRoute.setStartAltinm(result.getJSONObject("startPoint").getString("alt"));//航线安全起点-高度(海拔)
                tRoute.setEndLat(result.getJSONObject("endPoint").getString("lat"));//航线安全终止点-纬度
                tRoute.setEndLon(result.getJSONObject("endPoint").getString("lon"));//航线安全终止点-经度
                tRoute.setEndAltinm(result.getJSONObject("endPoint").getString("alt"));//航线安全终止点-高度(海拔)
                tRoute.setUpdateTime(new Date());//数据同步时间
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return tRoute;
        }
    }

    /**
     * 根据航线ID获取航点信息
     * @return
     */
    public List<TRoutePoint> getRoutePointByRouteCode(TRoute tRoute) {
        List<TRoutePoint> list = new ArrayList<TRoutePoint>();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.route+tRoute.getRouteCode(),params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONObject result = object.getJSONObject("result");//请求成功的结果
                JSONArray array = result.getJSONArray("fpList");
                for (int i = 0; i < array.size(); i++) {
                    TRoutePoint tRoutePoint = new TRoutePoint();
                    tRoutePoint.setRouteId(tRouteMapper.selectRouteIdByRouteCode(tRoute.getRouteCode()));//航线ID
                    tRoutePoint.setOrd(i);//航点序号
                    JSONObject obj = (JSONObject) array.get(i);
                    tRoutePoint.setLat(obj.getString("lat"));//纬度
                    tRoutePoint.setLon(obj.getString("lon"));//经度
                    tRoutePoint.setAlt(obj.getString("alt"));//高度(海拔)
                    tRoutePoint.setUpdateTime(new Date());//数据同步时间
                    list.add(tRoutePoint);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    /**
     * 查询历史完成的巡检路线
     * @return
     */
    public List<TTaskHistory> inspectRecord() {
        List<TTaskHistory> list = new ArrayList<TTaskHistory>();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.targetsInspectRecord,params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONArray result = object.getJSONArray("result");//请求成功的结果
                for (int i = 0; i < result.size(); i++) {
                    TTaskHistory tTaskHistory = new TTaskHistory();
                    JSONObject obj = (JSONObject) result.get(i);
                    tTaskHistory.setTaskCode(obj.getString("assignmentId"));//任务编号(多翼的ID)
                    TTask tTask = tTaskMapper.selectTTaskById(tTaskMapper.selectTaskIdByTaskCode(obj.getString("assignmentId")));//任务实体类
                    if(tTask != null){
                        tTaskHistory.setTaskId(tTask.getTaskId());//任务ID
                        tTaskHistory.setDescribes(tTask.getDescribes());//任务描述
                        TAirport tAirport = tAirportMapper.selectTAirportById(tTask.getAirportId());//机巢实体类
                        if(tAirport != null){
                            tTaskHistory.setAirportId(tAirport.getAirportId());//机巢ID
                            tTaskHistory.setAriportName(tAirport.getAirportName());//机场名称
                        }
                    }
                    tTaskHistory.setStartTime(new Date(obj.getLong("inspectStartTime")));//开始巡检时间
                    tTaskHistory.setEndTime(new Date(obj.getLong("inspectDoneTime")));//结束巡检时间
                    tTaskHistory.setDuration((obj.getLong("inspectDoneTime") - obj.getLong("inspectStartTime")) / 60000L);//用时(分钟)
                    tTaskHistory.setUpdateTime(new Date());//数据同步时间
                    list.add(tTaskHistory);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    /**
     * 根据任务ID和航线ID查询所有图片ID
     * @return
     */
    public List<TTaskPicture> imagesAssignment(String taskCode,String routeCode) {
        List<TTaskPicture> list = new ArrayList<TTaskPicture>();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.imagesAssignment+taskCode+"/"+routeCode+"?onlyId=true",params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONObject result = object.getJSONObject("result");//请求成功的结果
                JSONArray array = result.getJSONArray("idList");
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = (JSONObject) array.get(i);
                    TTaskPicture tTaskPicture = new TTaskPicture();

                    TTaskHistory tTaskHistory = new TTaskHistory();
                    tTaskHistory.setTaskCode(taskCode);
                    List<TTaskHistory> ttaskhlist = tTaskHistoryMapper.selectTTaskHistoryList(tTaskHistory);
                    tTaskPicture.setTaskhId(ttaskhlist.size()>0 ? ttaskhlist.get(0).getTaskhId() : null);//历史巡检记录ID

                    TTask tTask = new TTask();
                    tTask.setTaskCode(taskCode);
                    List<TTask> tTasklist = tTaskMapper.selectTTaskList(tTask);
                    tTaskPicture.setTaskCode(taskCode);//任务编号(多翼的ID)
                    tTaskPicture.setAirportId(tTasklist.size()>0 ? tTasklist.get(0).getAirportId() : null);//机场ID
                    tTaskPicture.setTaskId(tTasklist.size()>0 ? tTasklist.get(0).getTaskId() : null);//任务ID

                    tTaskPicture.setRouteId(tRouteMapper.selectRouteIdByRouteCode(routeCode));//航线id
                    tTaskPicture.setRouteCode(routeCode);//航线编号(多翼的id)

                    tTaskPicture.setImageCode(obj.getString("imageId"));//图片编号
                    tTaskPicture.setPath(DyUrlConstants.filepath2+obj.getString("imageName")+".jpg");//图片存放目录
                    tTaskPicture.setImageName(obj.getString("imageName"));//图片名称
                    tTaskPicture.setUpdateTime(new Date());//数据同步时间
                    list.add(tTaskPicture);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    /**
     * 根据任务ID获取任务结果
     * @return
     */
    public Map<String,String[]> flightsResults(String taskCode) {
        Map<String,String[]> map = new HashMap<>();
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            String dyJson = dyLoginService.doGet(DyUrlConstants.header+DyUrlConstants.flightsResults+"?assignment="+taskCode,params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess) {
                JSONArray result = object.getJSONArray("result");//请求成功的结果   接口是array，文档是object
                for (int k = 0; k<result.size(); k++) {
                    JSONObject resultObj = (JSONObject) result.get(k);
                    JSONArray images = resultObj.getJSONArray("images");//任务图片信息数组
                    for (int i = 0; i < images.size(); i++) {
                        JSONObject image = (JSONObject) images.get(i);
                        JSONArray info = image.getJSONArray("info");//图片信息数组
                        for(int j = 0; j < info.size(); j++){
                            JSONObject obj = (JSONObject) info.get(j);
                            String imageCode = obj.getString("md5");//多翼图片id
                            JSONObject location = obj.getJSONObject("location");//图片位置信息
                            String [] local = new String [2];
                            local[0] = location.getString("lat");//纬度
                            local[1] = location.getString("lon");//经度
                            map.put(imageCode,local);
                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return map;
        }
    }

    /**
     * 根据图片ID下载图片
     * @return
     */
    public void images(String imageCode,String imageName) {
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.set("imageName",imageName);
            dyLoginService.getImg(DyUrlConstants.header+DyUrlConstants.images+imageCode,params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 喊话播放
     * @return
     */
    public String remoteShout(TRemoteShouting tRemoteShouting) {
        String result = null;
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("stationaryId",tAirportMapper.selectTAirportById(tRemoteShouting.getAirportId()).getAirportCode());//机场id
            params.add("sex","1".equals(tRemoteShouting.getSoundType()) ? "woman" : "man");//男女声
            params.add("times",tRemoteShouting.getCirculateCount()+"");//循环次数
            params.add("data",tRemoteShouting.getContent());//语音文字内容或MP3音频文件
            String dyJson = dyLoginService.doPost(DyUrlConstants.header+DyUrlConstants.remoteShout,params);
            result = dyJson;
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess){
                result = object.getString("result");
            }else{
                JSONObject error = object.getJSONObject("error");//请求失败的错误信息
                result = error.getString("errMsg");//错误提示
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return result;
        }
    }

    /**
     * 打开某一无人机视频流、打开固定机场某一监控摄像头的视频流
     * @return
     */
    public String liveviewStart(String airportId, int msgId, String streamId, int liveTime ) {
        String srcUrl = null;
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("airportId",airportId);//机场id
            params.add("msgId",msgId);//消息序列号
            params.add("streamId",streamId);//无人机的飞控编号
            params.add("liveTime",liveTime);//持续推流时间，毫秒
            String dyJson = dyLoginService.doPost(DyUrlConstants.header+DyUrlConstants.liveviewStart,params);
            srcUrl = dyJson;
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess){
                JSONObject result = object.getJSONObject("result");//请求成功的结果
                srcUrl = result.getString("srcUrl");//视频流地址
            }else{
                JSONObject error = object.getJSONObject("error");//请求失败的错误信息
                srcUrl = error.getString("errMsg");//错误提示
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return srcUrl;
        }
    }

    /**
     * 关闭某一无人机视频流、关闭固定机场某一监控摄像头的视频流
     * @return
     */
    public String liveviewStop(String airportId, int msgId, String streamId) {
        String msg = null;
        try{
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("airportId",airportId);//机场id
            params.add("msgId",msgId);//消息序列号
            params.add("streamId",streamId);//无人机的飞控编号
            String dyJson = dyLoginService.doPost(DyUrlConstants.header+DyUrlConstants.liveviewStop,params);
            JSONObject object = JSONObject.parseObject(dyJson);

            boolean isSuccess = object.getBoolean("isSuccess");//请求是否成功
            if(isSuccess){
                msg = "关闭成功";
            }else{
                JSONObject error = object.getJSONObject("error");//请求失败的错误信息
                msg = error.getString("errMsg");//错误提示
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return msg;
        }
    }
}
