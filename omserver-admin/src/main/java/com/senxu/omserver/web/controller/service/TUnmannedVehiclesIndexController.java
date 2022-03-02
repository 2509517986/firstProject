package com.senxu.omserver.web.controller.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.senxu.omserver.OmServerProperties;
import com.senxu.omserver.common.constant.DyUrlConstants;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.service.domain.TTask;
import com.senxu.omserver.service.domain.TTaskHistory;
import com.senxu.omserver.service.service.DyLoginService;
import com.senxu.omserver.service.service.ITTaskService;
import com.senxu.omserver.web.enums.DyTaskStatusEnum;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senxu.com.base.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 无人机首页，统计，架驶仓
 * @auther yudarr
 * @date 2022-01-26 16:19
 **/
@Slf4j
@Api("无人机管理概况")
@RestController
@RequestMapping("/service/unmannedVehiclesIndex")
public class TUnmannedVehiclesIndexController {

    @Autowired
    private DyLoginService dyLoginService;

    @Autowired
    private  ITTaskService itTaskService;

    @Autowired
    private OmServerProperties omServerProperties;

    @GetMapping("/homeTotal")
    public AjaxResult homeTotal(TTaskHistory tTaskHistory)
    {

        JSONObject homeTotal =getDYHomeTotal();
        JSONObject  devices =getDYDevices();
        JSONArray marks=getDYMarks();
        JSONArray plans=getDYPLANS();
        JSONArray groups=getDYGroups();
        JSONObject resultO=new JSONObject();
        JSONObject basicInfo,planFinishInfo, runTimInfo,todayInspectionPlan;
        JSONArray errorInfos;
        planFinishInfo=homeTotal.getJSONObject("plan");//计划完成度
        runTimInfo=createRunTimeInfo(homeTotal,devices);//各机场平均运行时长
        basicInfo=createBasicInfo(homeTotal,devices,marks);//飞行总架次,飞行总里程,,
        errorInfos=homeTotal.getJSONArray("mark");//异常统计
        //targetInfo->targetIdList
        String today=DateUtil.format(new Date(), "yyyy-MM-dd");
        List todayFinishPlans=new ArrayList();
        Integer todayCreateNum=0;
        Integer todayCancelNum=0;
        Integer todayFinishNum=0;
        Integer todayDelayNum=0;
        for (int i = 0; i < plans.size(); i++) {
            /*
             "id":"b3503de1-fcfb-4c2d-83b0-ba3533c1f8cd",
            "name":"216计划",
            "targetInfo":Array[1],
            "managementTeam":"运检四班",
            "createdAt":1644991477883,
            "updatedAt":1644991534692,
            "createdBy":"lisi",
            "description":null,
            "startAt":1644991444000,
            "deadline":1644995099000,
            "statusUpdatedAt":1644991477883,
            "status":"created",
            "isMarked":false,
            "markedAt":-1,
            "hasInterruptedAssignment":false,
            "priority":0,
            "totalNum":2,
            "assignedNum":2,
            "inspectedNum":
             */
           JSONObject plan= plans.getJSONObject(i);
            JSONObject newplan=new JSONObject();
            newplan.put("describes",plan.getString("name"));
            newplan.put("name",plan.getString("name"));
            newplan.put("status", DyTaskStatusEnum.getByVal(plan.getString("status")).getDesc());
            Date startAt = new Date(plan.getLong("startAt"));
            Date createAt = new Date(plan.getLong("createdAt"));
            Date updateAt = new Date(plan.getLong("updatedAt"));
            Date deadline = new Date(plan.getLong("deadline"));

            String startTimeStr = DateUtil.format(startAt, "yyyy-MM-dd HH:mm");
            String startDateStr = DateUtil.format(startAt, "yyyy-MM-dd");
            String createDateStr = DateUtil.format(createAt, "yyyy-MM-dd");
            String updateDateStr = DateUtil.format(updateAt, "yyyy-MM-dd");

            if(createDateStr.equals(today)){
                todayCreateNum++;
            }
            if(updateDateStr.equals(today)&&plan.getString("status").equals("canceled")){
                todayCancelNum++;
            }
            if(startDateStr.equals(today)&&plan.getString("status").equals("done")){
                todayFinishNum++;
            }
            if(updateDateStr.equals(today)&&plan.getString("status").equals("delay")){
                todayDelayNum++;
            }

            //不是今天的
            if(!omServerProperties.DYShowAllPlanOnIndex&&!startDateStr.equals(today)||!plan.getString("status").equals("done")){
                continue;
            }
            String deadlineStr = DateUtil.format(deadline, "yyyy-MM-dd HH:mm");
            newplan.put("startTime", startTimeStr);
            String planningCycle=startTimeStr;
            if(!deadlineStr.equals("undefined")){
                planningCycle +=("-"+deadlineStr);
            }
            newplan.put("planningCycle",planningCycle);
            String taskContent = getTaskContent(groups, plan);
            newplan.put("taskContent",taskContent);
            todayFinishPlans.add(newplan);
        }
   
        todayInspectionPlan =new JSONObject();
        todayInspectionPlan.put("todayCreateNum",todayCreateNum);
        todayInspectionPlan.put("todayCancelNum",todayCancelNum);
        todayInspectionPlan.put("todayFinishNum",todayFinishNum);
        todayInspectionPlan.put("todayDelayNum",todayDelayNum);
       // todayInspectionPlan=createTodayInspectionPlan();//今天巡检计划
      //  List todayFinishPlans=createTodayFinishInfo();//今天完成计划

        resultO.put("basicInfo",basicInfo);
        resultO.put("planFinishInfo",planFinishInfo);
        resultO.put("runTimInfo",runTimInfo);
        resultO.put("todayInspectionPlan",todayInspectionPlan);
        resultO.put("todayFinishPlans",todayFinishPlans);
        resultO.put("errorInfos",errorInfos);
        log.info(resultO.toJSONString());
        return AjaxResult.success("操作成功",resultO);
    }




    public static void main(String[] args) {
       // Long t=new Long(1645804799000);

    }



    private String getTaskContent(JSONArray groups, JSONObject plan) {
        JSONArray targetInfos=plan.getJSONArray("targetInfo");
        String taskContent="";
        for (int j = 0; j < targetInfos.size(); j++) {
            JSONObject targetInfo=targetInfos.getJSONObject(j);
            String groupId=targetInfo.getString("groupId");
            JSONArray targetIdList=targetInfo.getJSONArray("targetIdList");
            JSONObject group= getGroupFromJson(groupId,groups);//航线组
            if(group==null){
                continue;
            }
            JSONObject info=group.getJSONObject("info");
            taskContent+=info.getString("name");
            taskContent+=":";
            for (int k = 0; k < targetIdList.size(); k++) {
                JSONObject target=targetIdList.getJSONObject(k);//航线
                JSONObject route= getRouteFromJson(group,target.getJSONObject("id").getString("id"));
                JSONObject routeInfo=route.getJSONObject("info");
                String name=routeInfo.getString("identifier");
                taskContent+=name;
                if(k!=targetIdList.size()-1){
                    taskContent+=",";
                }
            }



        }
        return taskContent;
    }

    /**
     *   从如下中取  {
     *                     "id_version":{
     *                         "id":"78149d8f-56fd-4a7f-a35d-b9eef76d0ddd",
     *                         "version":"v1"
     *                     },
     * @param group
     * @param routeId
     * @return
     */
    private JSONObject getRouteFromJson(JSONObject group, String routeId) {
        JSONArray targets=group.getJSONArray("targets");
        for (int i = 0; i < targets.size(); i++) {
            JSONObject target=targets.getJSONObject(i);
            String id=target.getJSONObject("id_version").getString("id");
            if(id.equals(routeId)){
                return  target;
            }
        }
        return  null;
    }

    private JSONObject getGroupFromJson(String groupId, JSONArray groups) {
        for (int i = 0; i < groups.size(); i++) {
            JSONObject g=groups.getJSONObject(i);
            if(g.getString("id").equals(groupId)){
                return  g;
            }
        }
        return  null;
    }

    private JSONArray getDYGroups() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        String result=dyLoginService.doGet(DyUrlConstants.header+"/api/v1/uni/groupList",params);
        com.alibaba.fastjson.JSONObject invokeResult = com.alibaba.fastjson.JSONObject.parseObject(result);//须用啊里爸爸解释
        JSONArray resultO =invokeResult.getJSONArray("result");
        return resultO;
    }

    private JSONArray getDYPLANS() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        //http://159.75.216.222:9009/api/v1/uni/planList
        String result=dyLoginService.doGet(DyUrlConstants.header+"/api/v1/uni/planList",params);
        com.alibaba.fastjson.JSONObject invokeResult = com.alibaba.fastjson.JSONObject.parseObject(result);//须用啊里爸爸解释
        JSONArray resultO =invokeResult.getJSONArray("result");
        return resultO;
    }

    private JSONObject getDYDevices() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        String result=dyLoginService.doGet(DyUrlConstants.header+"/api/v1/config/devices",params);
        com.alibaba.fastjson.JSONObject invokeResult = com.alibaba.fastjson.JSONObject.parseObject(result);//须用啊里爸爸解释
         JSONObject resultO =invokeResult.getJSONObject("result");
        return resultO;
    }

    private JSONObject getDYHomeTotal() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        //{flight: "all", mark: "all"}
        params.add("flight","all");
        params.add("mark","all");
        String result=dyLoginService.doPost(DyUrlConstants.header+"/api/v1/uni/homeTotal",params);
        com.alibaba.fastjson.JSONObject invokeResult = com.alibaba.fastjson.JSONObject.parseObject(result);//须用啊里爸爸解释
        return invokeResult.getJSONObject("result");
    }

    // JSONObject basicInfo=createBasicInfo();
       /* JSONObject finishInfo=createFinishInfo();
        JSONObject runTimInfo=createRunTimeInfo();
        JSONObject todayInspectionPlan=createTodayInspectionPlan();
        JSONArray todayFinishPlans=createTodayFinishInfo();
        JSONArray errorInfos=createErrorInfos();


        r.put("basicInfo",basicInfo);
        r.put("finishInfo",finishInfo);
        r.put("runTimInfo",runTimInfo);
        r.put("todayInspectionPlan",todayInspectionPlan);
        r.put("todayFinishPlans",todayFinishPlans);
        r.put("errorInfos",errorInfos);
*/



    /**
     *
     *
     *
     *
     *
     * 今日巡检计划todayInspectionPlan
     * 今日创建计划总数 todayCreatePlanNum
     * 今日已完成计划数 todayFinishPlanNum
     * 今日已取消计划数 todayCancelPlanNum
     * 今日已过期计划数 todayOutDateNum
     *
     * 今日已完成计划todayFinishPlans
     *
     * 计划名称，计划周期，任务内容，计划状态
     * planName,planPreiod,taskContent,platStatust
     *
     * 异常统计 errorInfos
     *
     * 各异常类别  异常总数一般异常数  危急异常数
     * class      errornum         common_errornum danger_errornum
     * @param homeTotal
     * @param devices
     */
    private JSONObject createRunTimeInfo(JSONObject homeTotal, JSONObject devices) {
        /*

         * 运行情况runTimInfo
         * 各机场平均运行时长
         * 设备运行总时长【array】
         */
        JSONObject r=new JSONObject();

        double avgRuntime=0;
        JSONArray array=new JSONArray();
        int airportSize = devices.getJSONArray("airport").size();
        for (int i = 0; i < airportSize; i++) {
            JSONObject jsonObject=devices.getJSONArray("airport").getJSONObject(i);
            String name = jsonObject.getString("name");
            String totalLiveTimeS="0";
            double totalLiveTimeD=0;
            if(jsonObject.containsKey("totalLiveTime")){
                totalLiveTimeD = ((double)jsonObject.getLong("totalLiveTime")) / (1000 * 3600);
                totalLiveTimeS=getDoubleToString(totalLiveTimeD,2);
                avgRuntime+=totalLiveTimeD;
            }
            array.add(getDevRuntimeInfo(name, totalLiveTimeS));
        }
        r.put("avgRuntime",getDoubleToString(avgRuntime/airportSize,2));
        r.put("datas",array);
        return r;
    }

    /**
     * 将double转为取NUM小数的字段
     * @param numDouble
     * @param size  小数点后几位
     */
    public static String getDoubleToString (Double numDouble,int size){
        String result="";
        //转换为小数点后2位
        BigDecimal bd=new BigDecimal(numDouble);
        BigDecimal bd1=bd.setScale(size, BigDecimal.ROUND_HALF_UP);
        double d=Double.parseDouble(bd1.toString());
        //System.out.println(d);
        result=String.valueOf(d);

       // double d = 12.2289;
       // java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
       // System.out.println(df.format(d));
        return result;
    }
    private JSONObject getDevRuntimeInfo(String name, String runtime) {
        JSONObject o=new JSONObject();
        o.put("name",name);
        o.put("runtime",runtime);
        return o;
    }

    private JSONObject createTodayInspectionPlan() {
        TTask task1=new TTask();
        // state, create_time, start_time, end_time, update_time,
        Date now=new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR,0);
        now=cal.getTime();
        cal.add(Calendar.DATE,1);
        Date tomorrow=new Date();
        tomorrow=cal.getTime();
        task1.setCreateTimeBegin(now);
        task1.setCreateTimeEnd(tomorrow);
        List createlist=itTaskService.selectTTaskList(task1);

        TTask task2=new TTask();
        task2.setStartTimeBegin(now);
        task2.setStartTimeEnd(tomorrow);
        task2.setState("canceled");
        List cancelList=itTaskService.selectTTaskList(task2);

        TTask task3=new TTask();
        task3.setStartTimeBegin(now);
        task3.setStartTimeEnd(tomorrow);
        task3.setState("done");
        List finishList=itTaskService.selectTTaskList(task3);


        TTask task4=new TTask();
        task4.setStartTimeBegin(now);
        task4.setStartTimeEnd(tomorrow);
        task4.setState("delay");
        List delayList=itTaskService.selectTTaskList(task4);

        JSONObject r=new JSONObject();
        r.put("todayCreateNum",createlist.size());
        r.put("todayCancelNum",cancelList.size());
        r.put("todayFinishNum",finishList.size());
        r.put("todayDelayNum",delayList.size());
        return r;
    }

    private List createTodayFinishInfo() {
        TTask task1=new TTask();
        // state, create_time, start_time, end_time, update_time,
        Date now=new Date();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR,0);
        now=cal.getTime();
        cal.add(Calendar.DATE,1);
        Date tomorrow=new Date();
        tomorrow=cal.getTime();
        task1.setStartTimeBegin(now);
        task1.setStartTimeEnd(tomorrow);
        task1.setState("done");
        List finishList=itTaskService.selectTTaskList(task1);

        //JSONArray r=new JSONArray();
        return finishList;
    }

    private JSONArray createErrorInfos() {
        JSONArray r=new JSONArray();
        return r;
    }


    private JSONObject createBasicInfo(JSONObject homeTotal,JSONObject devices, JSONArray marks) {



        com.alibaba.fastjson.JSONArray drones =devices.getJSONArray("drone");
        int totolFightNum=0;
        int totalMileage=0;
        int totalPic=0;
        int totalError=0;
        for (int i = 0; i < drones.size(); i++) {
            JSONObject jsonObject=drones.getJSONObject(i);

            if(isNotNull(jsonObject,"flightSorties")){
                totolFightNum+=jsonObject.getInteger("flightSorties");//飞行种类，感觉不是但只有这个数据了
            }
            if(isNotNull(jsonObject,"totalFlightMileage")){
                totalMileage+=jsonObject.getInteger("totalFlightMileage");
            }

         //   totalPic+=jsonObject.getInteger("flightSorties");
          //  totalError+=jsonObject.getInteger("flightSorties");
        }

        JSONArray flight=homeTotal.getJSONArray("flight");
        for (int i = 0; i < flight.size(); i++) {
            JSONObject jsonObject=flight.getJSONObject(i);
            if(isNotNull(jsonObject,"inspectImage")){
                totalPic+=jsonObject.getInteger("inspectImage");
            }
        }
        for (int i = 0; i < marks.size(); i++) {
            JSONObject jsonObject=marks.getJSONObject(i);
            /*if(isNotNull(jsonObject,"totalImage")){
                totalPic+=jsonObject.getInteger("totalImage");
            }*/
            if(isNotNull(jsonObject,"markedImage")){
                totalError+=jsonObject.getInteger("markedImage");
            }
        }

        JSONObject basicInfo=new JSONObject();

        basicInfo.put("totolFightNum",totolFightNum);//飞行总架次totolnum
        basicInfo.put("totalMileage",totalMileage);//飞行总里程totalmileage
        basicInfo.put("totalPic",totalPic);//巡检照片总数totalpic
        basicInfo.put("totalError",totalError);//异常总数totalerror
        return basicInfo;
    }

    private boolean isNotNull(JSONObject jsonObject,String field) {
        return jsonObject.containsKey(field)&&jsonObject.get(field)!=null;
    }

    private JSONArray getDYMarks() {
        MultiValueMap<String, Object> params2 = new LinkedMultiValueMap<>();
        String marksResult =dyLoginService.doGet(DyUrlConstants.header+"/api/v1/marks/all",params2);
        com.alibaba.fastjson.JSONObject deviceResultO = com.alibaba.fastjson.JSONObject.parseObject(marksResult);
        return deviceResultO.getJSONArray("result");
    }

    private JSONObject getProcess(int finishNum, int totalNum) {
        JSONObject o=new JSONObject();
        o.put("finishNum",finishNum);
        o.put("totalNum",totalNum);
        return o;
    }

}
