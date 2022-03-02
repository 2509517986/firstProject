package com.senxu.omserver.web.controller.service;

import com.senxu.omserver.common.constant.DyUrlConstants;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ftl.ExportWord;
import com.senxu.omserver.service.domain.TTaskReport;
import com.senxu.omserver.service.service.DyLoginService;
import com.senxu.omserver.service.service.ITTaskReportService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 无人机首页，统计，架驶仓
 * @auther yudarr
 * @date 2022-01-26 16:19
 **/
@Api("无人机巡检报告")
@RestController
@RequestMapping("/service/unmannedVehiclesInspectionReport")
public class TUnmannedVehiclesInspectionReportController {
    @Autowired
    private DyLoginService dyLoginService;
    @Autowired
    private ITTaskReportService tTaskReportService;

    @GetMapping("/info")
    public AjaxResult info(TTaskReport tTaskReport, HttpServletRequest request)
    {
        com.alibaba.fastjson.JSONObject dyHomeTotal=getDYHomeTotal();
        String taskId=request.getParameter("taskId");
        TTaskReport taskReportQuery=new TTaskReport();
        TTaskReport taskReport=null;
        if(taskId!=null&&!taskId.isEmpty()){
            taskReportQuery.setTaskhId(Long.parseLong(taskId));
            java.util.List taskReports=tTaskReportService.selectTTaskReportList(taskReportQuery);
            if(taskReports!=null&&taskReports.size()>0) {
                taskReport=(TTaskReport) taskReports.get(0);
            }
        }


        JSONObject r=new JSONObject();
        //taskReport.
        JSONObject basicInfo=new JSONObject();


        if(taskReport!=null){
            basicInfo.put("inspectionArea",taskReport.getArea());//巡检区域
            basicInfo.put("inspectionPlan",taskReport.getPlan());//巡检计划
            basicInfo.put("inspectionBeginTime",taskReport.getInspectionTime());//巡检时间
            basicInfo.put("inspectionEndTime",taskReport.getInspectionTime());//巡检时间
            basicInfo.put("inspectionProcess",taskReport.getProgress());//巡检进度
            basicInfo.put("inspectionGroup",taskReport.getDept());//巡检班组
        }
        else{
            basicInfo.put("inspectionArea",70);//巡检区域
            basicInfo.put("inspectionPlan","红外精细化-完成");//巡检计划
            basicInfo.put("inspectionBeginTime","2022年01月19日 13:41");//巡检时间
            basicInfo.put("inspectionEndTime","2022年01月19日 23:59");//巡检时间
            basicInfo.put("inspectionProcess","1/1");//巡检进度
            basicInfo.put("inspectionGroup","运检四班");//巡检班组
        }

        r.put("basicInfo",basicInfo);


        JSONObject diagnose=new JSONObject();
        if(taskReport!=null){
            diagnose.put("picTotal",taskReport.getPictureCount());//拍摄照片总数
            diagnose.put("defectPicTotal",taskReport.getDefectCount());//缺陷照片总数
            diagnose.put("visiblePicTotal",taskReport.getVisibleLightCount());//可见光照片数
            diagnose.put("infraredPicTotal",taskReport.getIrCount());//红外照片数
            diagnose.put("visibleDefectPicTotal",taskReport.getVisibleLightDefect());//可见光缺陷数
            diagnose.put("infraredDefectPicTotal",taskReport.getIrDefect());// 红外缺陷数


        }
        else{
            diagnose.put("picTotal",10);//拍摄照片总数
            diagnose.put("defectPicTotal",5);//缺陷照片总数
            diagnose.put("visiblePicTotal",8);//可见光照片数
            diagnose.put("infraredPicTotal",2);//红外照片数
            diagnose.put("visibleDefectPicTotal",3);//可见光缺陷数
            diagnose.put("infraredDefectPicTotal",2);// 红外缺陷数
        }

        r.put("diagnose",diagnose);

        JSONObject defectStatic=new JSONObject();
        if(taskReport!=null){
            defectStatic.put("seriousNum",taskReport.getGraveDefect());
            defectStatic.put("dangerNum",taskReport.getCriticalDefect());
            defectStatic.put("totalNum",taskReport.getTotalDefect());
            defectStatic.put("normalNum",taskReport.getGeneralDefect());
        }
        else{
            defectStatic.put("seriousNum",3);
            defectStatic.put("dangerNum",3);
            defectStatic.put("totalNum",9);
            defectStatic.put("normalNum",3);
        }


        r.put("defectStatic",defectStatic);//缺陷统计 //直接从接口拿

        JSONObject defectPosition=new JSONObject();
        JSONArray defectList=createDefectList();
        defectPosition.put("list",defectList);
        JSONObject defectTotal=createTotal(defectList,"defectNum,commonDefectNum,seriousDefectNum,criticalDefectNum");
        defectPosition.put("total",defectTotal);
        r.put("defectPosition",defectPosition);//缺陷位置信息

        return AjaxResult.success(r);
    }

    @RequestMapping(value = "/exportWord", method= RequestMethod.GET)
    public void exportWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String taskId=request.getParameter("taskId");
        TTaskReport taskReportQuery=new TTaskReport();
        TTaskReport taskReport=null;
        if(taskId!=null&&!taskId.isEmpty()){
            taskReportQuery.setTaskhId(Long.parseLong(taskId));
            java.util.List taskReports=tTaskReportService.selectTTaskReportList(taskReportQuery);
            if(taskReports!=null&&taskReports.size()>0) {
                taskReport=(TTaskReport) taskReports.get(0);
            }
        }

        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("date",DateUtils.dateTimeNow(DateUtils.YYYY_MM_DD_HH_MM_SS));

        if(taskReport!=null){
            dataMap.put("inspectionArea",taskReport.getArea());//巡检区域
            dataMap.put("inspectionPlan",taskReport.getPlan());//巡检计划
            dataMap.put("inspectionBeginTime",taskReport.getInspectionTime());//巡检时间
            dataMap.put("inspectionEndTime",taskReport.getInspectionTime());//巡检时间
            dataMap.put("inspectionProcess",taskReport.getProgress());//巡检进度
            dataMap.put("inspectionGroup",taskReport.getDept());//巡检班组

            dataMap.put("picTotal",taskReport.getPictureCount());//拍摄照片总数
            dataMap.put("defectPicTotal",taskReport.getDefectCount());//缺陷照片总数
            dataMap.put("visiblePicTotal",taskReport.getVisibleLightCount());//可见光照片数
            dataMap.put("infraredPicTotal",taskReport.getIrCount());//红外照片数
            dataMap.put("visibleDefectPicTotal",taskReport.getVisibleLightDefect());//可见光缺陷数
            dataMap.put("infraredDefectPicTotal",taskReport.getIrDefect());// 红外缺陷数

            dataMap.put("seriousNum",taskReport.getGraveDefect());
            dataMap.put("dangerNum",taskReport.getCriticalDefect());
            dataMap.put("totalNum",taskReport.getTotalDefect());
            dataMap.put("normalNum",taskReport.getGeneralDefect());

            JSONArray defectList=createDefectList();
            dataMap.put("list",defectList);
        }else{
            dataMap.put("inspectionArea",70);//巡检区域
            dataMap.put("inspectionPlan","红外精细化-完成");//巡检计划
            dataMap.put("inspectionBeginTime","2022年01月19日 13:41");//巡检时间
            dataMap.put("inspectionEndTime","2022年01月19日 23:59");//巡检时间
            dataMap.put("inspectionProcess","1/1");//巡检进度
            dataMap.put("inspectionGroup","运检四班");//巡检班组

            dataMap.put("picTotal",10);//拍摄照片总数
            dataMap.put("defectPicTotal",5);//缺陷照片总数
            dataMap.put("visiblePicTotal",8);//可见光照片数
            dataMap.put("infraredPicTotal",2);//红外照片数
            dataMap.put("visibleDefectPicTotal",3);//可见光缺陷数
            dataMap.put("infraredDefectPicTotal",2);// 红外缺陷数

            dataMap.put("seriousNum",3);
            dataMap.put("dangerNum",3);
            dataMap.put("totalNum",9);
            dataMap.put("normalNum",3);

            JSONArray defectList=createDefectList();
            dataMap.put("list1",defectList);
        }

        new ExportWord("UTF-8").exportDoc(response, "Report-"+DateUtils.dateTimeNow()+".doc", "Report.ftl", dataMap);
    }

    /**
     * 获取多翼首页信息
     * @return
     */
    private com.alibaba.fastjson.JSONObject getDYHomeTotal() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        //{flight: "all", mark: "all"}
        params.add("flight","all");
        params.add("mark","all");
        String result=dyLoginService.doPost(DyUrlConstants.header+"/api/v1/uni/homeTotal",params);
        com.alibaba.fastjson.JSONObject invokeResult = com.alibaba.fastjson.JSONObject.parseObject(result);//须用啊里爸爸解释
        return invokeResult.getJSONObject("result");
    }

    private JSONObject createTotal(JSONArray list, String hzFields) {
        JSONObject total=new JSONObject();
        String[] fields=hzFields.split(",");
        for (int i = 0; i < list.size(); i++) {
            JSONObject o=list.getJSONObject(i);
            Iterator<String> it=o.keys();
            while (it.hasNext()){
                String key=it.next();
                if(hzFields.indexOf(key)==-1){
                    continue;
                }
                Integer v=o.getInt(key);
                Object totalV=total.get(key);
                Integer totalVInt=0;
                if(totalV!=null&&totalV instanceof  Integer){
                    totalVInt=(Integer) totalV;
                }
                totalVInt+=v;
                total.put(key,totalVInt);
            }
        }
        return total;
    }

    private JSONArray createDefectList() {
        DefectPositionInfo defectPositionInfo=new DefectPositionInfo(1,"区域1",13.1,112,10,2,3,5);
        DefectPositionInfo defectPositionInfo2=new DefectPositionInfo(2,"区域2",14.1,122,21,2,3,5);

        JSONArray array=new JSONArray();
        array.add(JSONObject.fromObject(defectPositionInfo));
        array.add(JSONObject.fromObject(defectPositionInfo2));
        return array;
    }

    @AllArgsConstructor
    @Data
    public  class DefectPositionInfo{
        private int picId;
        private String areaName;
        private double longitude;//拍摄经度
        private double latitude;//拍摄纬度
        private int defectNum;// 缺陷个数
        private int commonDefectNum;//一般缺陷
        private int seriousDefectNum;//严重缺陷
        private int criticalDefectNum;//危急缺陷
    }
}
