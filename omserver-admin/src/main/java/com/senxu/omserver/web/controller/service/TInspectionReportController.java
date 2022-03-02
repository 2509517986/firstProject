package com.senxu.omserver.web.controller.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.senxu.omserver.common.core.domain.entity.SysDictData;
import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.common.utils.StringUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.*;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiStatistic;
import com.senxu.omserver.service.service.*;
import com.senxu.omserver.system.service.ISysDeptService;
import com.senxu.omserver.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_inspection_report（巡检报告）Controller
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Api("巡检报告")
@RestController
@RequestMapping("/service/inspectionReport")
public class TInspectionReportController extends BaseController
{
    @Autowired
    private ITInspectionReportService tInspectionReportService;
    @Autowired
    private ITLineInfoService tLineInfoService;
    @Autowired
    private ITTowerInfoService tTowerInfoService; //杆塔Service
    @Autowired
    private ISysDeptService deptService;
    @Autowired
    private ITYuntaiInfoService tYuntiInfoService; //云台Service

    @Autowired
    private ITYuntaiAlarmService tYuntiAlarmService;

    @Autowired
    private ITYuntaiPictureService tYuntaiPictureService; //

    @Autowired
    private ITInspectionPictureService tInspectionPictureService; //

    @Autowired
    private ISysDictTypeService dictTypeService;

    @Autowired
    private TokenService tokenService;


    /**
     * 查询t_inspection_report（巡检报告）列表
     */
    @ApiOperation("查询巡检报告列表")
    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(TInspectionReport tInspectionReport)
    {
        startPage();
        List<TInspectionReport> list = tInspectionReportService.selectTInspectionReportList(tInspectionReport);
        return getDataTable(list);
    }


    /**
     * 导出t_inspection_report（巡检报告）列表
     */
    @ApiOperation("导出巡检报告列表")
    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:export')")
    @Log(title = "t_inspection_report（巡检报告）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TInspectionReport tInspectionReport)
    {
        TInspectionReport tInspectionReport1 = new TInspectionReport();
        tInspectionReport1.setId(tInspectionReport.getId());
        List<TInspectionReport> list = tInspectionReportService.selectTInspectionReportList(tInspectionReport1);
        ExcelUtil<TInspectionReport> util = new ExcelUtil<TInspectionReport>(TInspectionReport.class);
        return util.exportExcel(list, "t_inspection_report（巡检报告）数据");
    }

    /**
     * 获取t_inspection_report（巡检报告）详细信息
     */
    @ApiOperation("获取巡检报告详细信息")
    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tInspectionReportService.selectTInspectionReportById(id));
    }

//    /**
//     * 获取t_inspection_report（巡检报告）详细信息
//     */
//    @ApiOperation("获取巡检报告详细信息")
//    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id)
//    {
//        AjaxResult ajax = new AjaxResult();
//        TInspectionReport tInspectionReport = tInspectionReportService.selectTInspectionReportById(id);
//        List<TInspectionYuntaiStatistic> yuntaiStatisticList=tYuntiInfoService.yuntaiStatisticsByDate(String.valueOf(id),null);
//        ajax.put("yuntaiStatistic", yuntaiStatisticList);
//        ajax.put("tInspectionReport", tInspectionReport);
//        return ajax;
//    }

    /**
     * 获取当日巡检报告
     */
//    @ApiOperation("获取当日巡检报告")
//    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:findReportByDate')")
//    @GetMapping(value = "/findReportByDate")
//    @Deprecated//该方法已弃用   pyc 2021-9-13
//    public AjaxResult findReportByDate(Long roleId,String userName,String sDate)
//    {
//       TInspectionReport tInspectionReport=tInspectionReportService.findReportByDate(sDate);
//        AjaxResult ajax = AjaxResult.success();
//       //当前日期没有记录则初始化巡检报告
//        if(tInspectionReport==null){
//            /**
//             * 第一步自动生成巡检结论：
//             * 例如：2019年12月06日巡检288回线路，286基杆塔，288个位置，其中智能分析发现9个位置异常，193个位置正常，86个位置未知。
//             */
//            String sResult= DateUtils.getDateCN();
//            //先查询统计当前用户角色获取线路条数
//            List<TLineInfo> lineList = tLineInfoService.selectTLineInfoList(new TLineInfo(deptService.selectDeptListByRoleId(roleId)));
//            sResult+="巡检"+lineList.size()+"线路，";
//            //先查询统计当前用户角色获取杆塔个数
//            String lineIds="";
//            for(TLineInfo obj :lineList){
//                if(lineIds.equals(""))
//                    lineIds+=obj.getId();
//                else
//                    lineIds+=","+obj.getId();
//            }
//            List<TTowerInfo> towerList = tTowerInfoService.selectTTowerInfoList(new TTowerInfo(lineIds));
//            sResult+=towerList.size()+"基杆塔，";
//
//            //再查询统计当前用户角色获取云台个数，
//            List<TYuntaiInfo> yuntaiList=tYuntiInfoService.selectTYuntaiInfoList(new TYuntaiInfo(lineIds));
//            List<TInspectionAlarm> inspectionAlarmList=tInspectionAlarmService.findReportByDate(lineIds,null,sDate);
//            List<TInspectionYuntai> inspectionYuntiList=tInspectionYuntaiService.findReportByDate(lineIds,null);
//            sResult+=yuntaiList.size()+"个云台，其中智能分析发现"+inspectionAlarmList.size()+"个位置异常，"+inspectionYuntiList.size()+"个位置正常，X个位置未知";
//
//            tInspectionReport=new TInspectionReport();
//            tInspectionReport.setInspectionResult(sResult);//巡检结论
//            tInspectionReport.setInspector(userName);//巡检人
//            tInspectionReport.setInspectionPlan("场景定时巡检");
//
//            /**
//             * 第二步生成统计信息
//             * 视频设备数据统计,在线设备类型
//             */
//            ajax.put("yuntaiStatistic", tInspectionYuntaiService.yuntaiStatisticsByDate(lineIds,null));
//            ajax.put("yuntaiHiddenDanger", tInspectionYuntaiService.yuntaiHiddenDangerByDate(lineIds,null));
//
//            /**
//             * 第三步生成巡检结果统计（即告警图片和历史图片）
//             *
//             */
//            ajax.put("InspectionAlarm", tInspectionAlarmService.findReportByDate(lineIds,null,sDate));
//
//        }else{
//            ajax.put("yuntaiStatistic", tInspectionYuntaiService.yuntaiStatisticsByDate(null,tInspectionReport.getId()));
//            ajax.put("yuntaiHiddenDanger", tInspectionYuntaiService.yuntaiHiddenDangerByDate(null,tInspectionReport.getId()));
//            ajax.put("inspectionAlarm", tInspectionAlarmService.findReportByDate(null,tInspectionReport.getId(),null));
//        }
//
//        ajax.put("inspectionReport",tInspectionReport);
//        ajax.put("intallType",dictTypeService.selectDictDataByType("install_type"));//安装点分类
//        ajax.put("sceneType",dictTypeService.selectDictDataByType("scene_type"));//场景点分类
//
//        return ajax;
//    }


    /**
     * 获取巡检报告明细，默认是取当前日期，所有线路下所有杆塔，所有云台的巡检报告,
     * 当tInspectionReport.id==0或tInspectionReport.id==null时，属于新增未保存状态，
     * 当tInspectionReport.id值大于0时，表示获取指定的日报表信息
     *
     * 查询条件 ，查询日期：inspectionTime、查询线路：spare0、查询安装点分类：installType、查询点分类:sceneType
     */
    @ApiOperation("获取巡检报告明细：当传入tInspectionReport.id==0和对应时间可以用于查询过滤：查询条件 ，查询日期：inspectionTime、查询安装点分类：installType、查询点分类:sceneType")
    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:newReport')")
    @GetMapping(value = "/findReportByDate")
    public AjaxResult findReportByDate(TInspectionReport tInspectionReport)
    {

        AjaxResult ajax = AjaxResult.success();

        //当前日期没有记录则初始化巡检报告
        if(tInspectionReport.getId()!=null && tInspectionReport.getId()!=0){
            tInspectionReport=tInspectionReportService.selectTInspectionReportById(tInspectionReport.getId());
        }
            /**
             * 初始化查询条件：查询日期：inspectionTime、查询线路：spare0、查询安装点分类：installType、查询点分类:sceneType
             */
            //获取当前用户
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            String operName = loginUser.getUsername();

            String sDate="";
            String sResult="";//巡检结论


            if(tInspectionReport.getInspectionTime()==null){
                sDate=DateUtils.getDate();
                sResult=DateUtils.getDateCN();//巡检结论日期
            }else{
                sDate=DateUtils.parseDateToStr("yyyy-MM-dd",tInspectionReport.getInspectionTime());
                sResult=DateUtils.parseDateToStr("yyyy年MM月dd日",tInspectionReport.getInspectionTime());
            }


            /**
             * 第一步自动生成巡检结论：
             * 例如：2019年12月06日巡检288回线路，286基杆塔，288个位置，其中智能分析发现9个位置异常，193个位置正常，86个位置未知。
             */
            List<TLineInfo> lineList=new ArrayList<>();
            if(tInspectionReport.getLineIds()==null) {
                List<Integer> deptIds = deptService.selectDeptListByUserId(loginUser.getUser().getUserId());
                //先查询统计当前用户角色获取用户所属部门，然后通过所属部门所属部门管辖的线路条数
                lineList = tLineInfoService.selectTLineInfoList(new TLineInfo(deptIds));
            }else{
                TLineInfo lineInfo=new TLineInfo();
                lineInfo=tLineInfoService.selectTLineInfoById(Long.parseLong(tInspectionReport.getLineIds()));
                lineList.add(lineInfo);
            }

            sResult+="巡检"+lineList.size()+"线路，";
            //先查询统计当前用户角色获取杆塔个数
            String lineIds="";
            List<String> list = new ArrayList<>();
            for(TLineInfo obj :lineList){
                if(lineIds.equals("")){
                    lineIds+=obj.getId();
                    list.add(String.valueOf(obj.getId()));
                }
                else {
                    lineIds += "," + obj.getId();
                    list.add(String.valueOf(obj.getId()));
                }

            }
            TTowerInfo queryTower= new TTowerInfo();
            if(tInspectionReport.getInstallType()!=null && !tInspectionReport.getInstallType().equals("")) {
                queryTower.setInstallTypes(tInspectionReport.getInstallType());//过滤条件
            }
            if(tInspectionReport.getSceneType()!=null && !tInspectionReport.getSceneType().equals("")) {
                queryTower.setSceneTypes(tInspectionReport.getSceneType());//过滤条件
            }

            queryTower.setLineIdList(list);
            List<TTowerInfo> towerList = tTowerInfoService.selectTTowerInfoList(queryTower);
            sResult+=towerList.size()+"基杆塔，";
            //获取杆塔ID
            String towerIds="";
            for(TTowerInfo obj :towerList){
                if(towerIds.equals(""))
                    towerIds+=obj.getId();
                else
                    towerIds+=","+obj.getId();
            }

            List<String> lineIdList = new ArrayList<>();
            List<String> towerIdList = new ArrayList<>();
            String[] lineArray = lineIds.split(",");
            String[] towerIArray = towerIds.split(",");
            for (int i = 0; i < lineArray.length; i++) {
                lineIdList.add(lineArray[i]);
            }
            for (int i = 0; i < towerIArray.length; i++) {
                towerIdList.add(towerIArray[i]);
            }
            //再查询统计当前用户角色获取云台个数，
            TYuntaiInfo  tYuntaiInfo = new TYuntaiInfo();
            tYuntaiInfo.setLineIdList(lineIdList);
            tYuntaiInfo.setTowerIdList(towerIdList);
            List<TYuntaiInfo> yuntaiList=tYuntiInfoService.selectTYuntaiInfoList(tYuntaiInfo);//这里还要传入查询安装点分类、查询点分类查询条件
            List<TYuntaiAlarm> yuntaiAlarmList=tYuntiAlarmService.selectTYuntaiAlarmList(new TYuntaiAlarm(lineIds,towerIds));

            sResult+=yuntaiList.size()+"个云台，其中智能分析发现"+yuntaiAlarmList.size()+"个位置异常，"+yuntaiList.size()+"个位置正常，X个位置未知";

            tInspectionReport=new TInspectionReport();
            tInspectionReport.setInspectionResult(sResult);//巡检结论
            tInspectionReport.setInspector(operName);//巡检人
            tInspectionReport.setInspectionPlan("场景定时巡检");
            tInspectionReport.setInspectionTime(new Date());

            /**
             * 第二步生成统计信息
             * 视频设备数据统计,在线设备类型
             */
            List<TInspectionYuntaiStatistic> yuntaiStatisticList=tYuntiInfoService.yuntaiStatisticsByDate(lineIds,towerIds);
            ajax.put("yuntaiStatistic", yuntaiStatisticList);
            ajax.put("yuntaiHiddenDanger", tYuntiInfoService.yuntaiHiddenDangerByDate(lineIds,towerIds));

            if(yuntaiStatisticList!=null && yuntaiStatisticList.size()>0) {
                tInspectionReport.setSpare1(yuntaiStatisticList.get(0).getTotal().toString());//设备数量
                tInspectionReport.setSpare2(yuntaiStatisticList.get(0).getOnline().toString());//在线数量
            }

        ajax.put("inspectionReport",tInspectionReport);
        ajax.put("intallType",dictTypeService.selectDictDataByType("install_type"));//安装点分类
        ajax.put("sceneType",dictTypeService.selectDictDataByType("scene_type"));//场景点分类

        return ajax;
    }


    /**
     *
     * @param tInspectionReport
     * @return
     */
    @ApiOperation("查询巡检报告图片列表")
    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:pictureList')")
    @GetMapping("/pictureList")
    public TableDataInfo pictureList(TInspectionReport tInspectionReport){

        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        List<TLineInfo> lineList=new ArrayList<TLineInfo>();
        if(tInspectionReport.getLineIds()==null) {
            List<Integer> deptIds = deptService.selectDeptListByUserId(loginUser.getUser().getUserId());
            //先查询统计当前用户角色获取用户所属部门，然后通过所属部门所属部门管辖的线路条数
            lineList = tLineInfoService.selectTLineInfoList(new TLineInfo(deptIds));
        }else{
            TLineInfo lineInfo=new TLineInfo();
            lineInfo=tLineInfoService.selectTLineInfoById(Long.parseLong(tInspectionReport.getLineIds()));
            lineList.add(lineInfo);
        }

        //先查询统计当前用户角色获取杆塔个数
        String lineIds="";
        for(TLineInfo obj :lineList){
            if(lineIds.equals(""))
                lineIds+=obj.getId();
            else
                lineIds+=","+obj.getId();
        }
        TTowerInfo queryTower= new TTowerInfo(lineIds);
        List<String> installTypeList = new ArrayList<>();
        List<String> sceneTypeList = new ArrayList<>();
        if(tInspectionReport.getInstallType()!=null && !tInspectionReport.getInstallType().equals("")) {
            String array[] = tInspectionReport.getInstallType().split(",");
            for(int i = 0; i<array.length;i++){
                installTypeList.add(array[i]);
            }
            queryTower.setInstallTypes(tInspectionReport.getInstallType());//过滤条件
        }
        if(tInspectionReport.getSceneType()!=null && !tInspectionReport.getSceneType().equals("")) {
            String array[] = tInspectionReport.getSceneType().split(",");
            for(int i = 0; i<array.length;i++){
                sceneTypeList.add(array[i]);
            }
            queryTower.setSceneTypeList(sceneTypeList);//过滤条件
        }
        List<TTowerInfo> towerList = tTowerInfoService.selectTTowerInfoList(queryTower);
        //获取杆塔ID
        String towerIds="";
        for(TTowerInfo obj :towerList){
            if(towerIds.equals(""))
                towerIds+=obj.getId();
            else
                towerIds+=","+obj.getId();
        }

        String  sDate="";
        if(tInspectionReport.getInspectionTime()==null){
            sDate=DateUtils.getDate();
        }else{
            sDate=DateUtils.parseDateToStr("yyyy-MM-dd",tInspectionReport.getInspectionTime());
        }
        //根据安装点分类，场景点分类,进行过滤
        TYuntaiPicture yuntanPicture=new TYuntaiPicture(lineIds);
        yuntanPicture.setSpare0(sDate);

        System.out.println("lineIds="+lineIds);
        System.out.println("towerIds="+towerIds);
        System.out.println("sDate="+sDate);

        startPage();
        List<TInspectionPicture> inspectionPictureList=tInspectionPictureService.findReportByDate(lineIds,null,sDate,towerIds,
                installTypeList,sceneTypeList,0,null);//查询当前日期的告警图片
        int total = tYuntaiPictureService.selectTYuntaiPictureCount1(yuntanPicture);
        return getDataTable2(inspectionPictureList,total);
    }

    /**
     * 查询云台今日抓拍图片列表
     */
    @ApiOperation("查询云台今日抓拍图片列表")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiPicture:listByDay')")
    @GetMapping("/listByDay")
    public TableDataInfo ListByDay(Long lineId,Long towerId,Long[] yuntaiIds,String presetBit,String day,Integer imgSize,Date inspectionTime,
                                   String installType,  String sceneType)
    {
        startPage();
        List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();
        TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
        tYuntaiInfo.setLineId(lineId);
        tYuntaiInfo.setTowerId(towerId);
        if(yuntaiIds!=null) {
            tYuntaiInfo.setSpare0(StringUtils.arrayLong2Str(yuntaiIds, ","));
        }
        List<TYuntaiInfo> yuntaiList = tYuntiInfoService.selectTYuntaiInfoListPage(tYuntaiInfo);

        String  sDate="";
        List<String> installTypeList = new ArrayList<>();
        List<String> sceneTypeList = new ArrayList<>();
        if(inspectionTime==null){
            sDate=DateUtils.getDate();
        }else{
            sDate=DateUtils.parseDateToStr("yyyy-MM-dd",inspectionTime);
        }
        if(installType !=null && ! installType.equals("")) {
            String array[] = installType.split(",");
            for(int i = 0; i<array.length;i++){
                installTypeList.add(array[i]);
            }
        }
        if(sceneType!=null && ! sceneType.equals("")) {
            String array[] = sceneType.split(",");
            for(int i = 0; i<array.length;i++){
                sceneTypeList.add(array[i]);
            }
        }

        for(TYuntaiInfo obj:yuntaiList){
            TYuntaiPicture tYuntaiPicture = new TYuntaiPicture();
            tYuntaiPicture.setPresetBit(presetBit);
            tYuntaiPicture.setSpare0(day);
            tYuntaiPicture.setYuntaiId(obj.getId());
            tYuntaiPicture.setLineId(lineId);
            tYuntaiPicture.setTowerId(towerId);
            tYuntaiPicture.setImgSize(imgSize);
//            List<TYuntaiPicture> list = tYuntaiPictureService.selectTYuntaiPictureListByDay(tYuntaiPicture);
            //查询当前日期的告警图片
             List<TInspectionPicture> list=tInspectionPictureService.findReportByDate("",null,sDate,"",
                    installTypeList,sceneTypeList, imgSize,obj.getId());
            map.add(new HashMap<String, Object>(){{
                put("yuntai",obj);
                put("pictures",list);
            }});
        }

        return getDataTable2(map,tYuntiInfoService.selectTYuntaiCount(tYuntaiInfo));
    }




    /**
     * 新增t_inspection_report（巡检报告）  原来是会保存巡检报告下面图片信息的，现在改为不保存，直接查询原来的t_yuntai_picture表，所以暂时先屏蔽当前方法改用原来的直接保存巡检表
     */
//    @ApiOperation("新增巡检报告：用于新增保存巡检报告的操作")
//    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:add')")
//    @Log(title = "t_inspection_report（巡检报告）", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody TInspectionReport tInspectionReport)
//    {
//        Long lineId=0L;
//        if(tInspectionReport.getSpare0()!=null) {
//            lineId=Long.parseLong(tInspectionReport.getSpare0());
//        }
//
//        tInspectionReport.setSpare0("");//清空lindId值
//        tInspectionReportService.insertTInspectionReport(tInspectionReport);//保存巡检报告
//
//        //当前日期没有记录则初始化巡检报告
//        if(tInspectionReport.getId()==null||tInspectionReport.getId()==0){
//            /**
//             * 初始化查询条件：查询日期：inspectionTime、查询线路：spare0、查询安装点分类：installType、查询点分类:sceneType
//             */
//            //获取当前用户
//            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//            String operName = loginUser.getUsername();
//
//            String sDate="";
//            String sResult="";//巡检结论
//
//
//            if(tInspectionReport.getInspectionTime()==null){
//                sDate=DateUtils.dateTime();
//                sResult=DateUtils.getDateCN();//巡检结论日期
//            }else{
//                sDate=DateUtils.parseDateToStr("yyyy-MM-dd",tInspectionReport.getInspectionTime());
//                sResult=DateUtils.parseDateToStr("yyyy年MM月dd日",tInspectionReport.getInspectionTime());
//            }
//
//            /**
//             * 第一步自动生成巡检结论：
//             * 例如：2019年12月06日巡检288回线路，286基杆塔，288个位置，其中智能分析发现9个位置异常，193个位置正常，86个位置未知。
//             */
//            List<TLineInfo> lineList=null;
//            if(tInspectionReport.getSpare0()==null) {
//                List<Integer> deptIds = deptService.selectDeptListByUserId(loginUser.getUser().getUserId());
//                //先查询统计当前用户角色获取用户所属部门，然后通过所属部门所属部门管辖的线路条数
//                lineList = tLineInfoService.selectTLineInfoList(new TLineInfo(deptIds));
//            }else{
//                TLineInfo lineInfo=new TLineInfo();
//                lineInfo=tLineInfoService.selectTLineInfoById(lineId);
//                lineList.add(lineInfo);
//            }
//
//            sResult+="巡检"+lineList.size()+"线路，";
//            //先查询统计当前用户角色获取杆塔个数
//            String lineIds="";
//            for(TLineInfo obj :lineList){
//                if(lineIds.equals(""))
//                    lineIds+=obj.getId();
//                else
//                    lineIds+=","+obj.getId();
//            }
//            TTowerInfo queryTower= new TTowerInfo(lineIds);
//            queryTower.setInstallTypes(tInspectionReport.getInstallType());//过滤条件
//            queryTower.setSceneTypes(tInspectionReport.getSceneType());//过滤条件
//            List<TTowerInfo> towerList = tTowerInfoService.selectTTowerInfoList(queryTower);
//            sResult+=towerList.size()+"基杆塔，";
//            //获取杆塔ID
//            String towerIds="";
//            for(TTowerInfo obj :towerList){
//                if(towerIds.equals(""))
//                    towerIds+=obj.getId();
//                else
//                    towerIds+=","+obj.getId();
//            }
//
//            //保存巡检报告云台信息
//            List<TYuntaiInfo> yuntaiList=tYuntiInfoService.selectTYuntaiInfoList(new TYuntaiInfo(lineIds,towerIds));//这里还要传入查询安装点分类、查询点分类查询条件
//            for(TYuntaiInfo obj:yuntaiList) {
//                TInspectionYuntai yunti=new TInspectionYuntai();
//                yunti.setReportId(tInspectionReport.getId());
//                yunti.setYuntaiId(obj.getId());
//                yunti.setLineId(obj.getLineId());
//                yunti.setTowerId(obj.getTowerId());
//                yunti.setTowerId(obj.getTowerId());
//                yunti.setHiddenDanger(obj.getHiddenDanger());
//                yunti.setCreateUser(operName);
//                yunti.setCreateTime(new Date());
//                tInspectionYuntaiService.insertTInspectionYuntai(yunti);
//            }
//            //保存巡检报告图片信息
//            List<TInspectionPicture> inspectionPictureList=tInspectionPictureService.findReportByDate(lineIds,null,sDate,towerIds);//查询当前日期的告警图片
//            for(TInspectionPicture obj:inspectionPictureList){
//                obj.setReportId(tInspectionReport.getId());
//                tInspectionPictureService.insertTInspectionPicture(obj);
//            }
//        }
//
//        return toAjax(1);
//    }


    @ApiOperation("新增巡检报告：用于新增保存巡检报告的操作")
    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:add')")
    @Log(title = "t_inspection_report（巡检报告）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TInspectionReport tInspectionReport)
    {
        List<TInspectionYuntaiStatistic> yuntaiStatisticList=tYuntiInfoService.yuntaiStatisticsByDate(tInspectionReport.getLineIds(),null);
        tInspectionReport.setOffline(String.valueOf(yuntaiStatisticList.get(0).getOffline()));
        tInspectionReport.setOfflineRate(String.valueOf(yuntaiStatisticList.get(0).getOfflineRate()));
        tInspectionReport.setOnline(String.valueOf(yuntaiStatisticList.get(0).getOnline()));
        tInspectionReport.setOnlineRate(String.valueOf(yuntaiStatisticList.get(0).getOnlineRate()));
        tInspectionReport.setTotal(String.valueOf(yuntaiStatisticList.get(0).getTotal()));
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String userName = loginUser.getUser().getUserName();
        return toAjax(tInspectionReportService.insertTInspectionReport(tInspectionReport,userName));
    }


    /**
     * 修改t_inspection_report（巡检报告）
     */
    @ApiOperation("修改巡检报告")
    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:edit')")
    @Log(title = "t_inspection_report（巡检报告）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TInspectionReport tInspectionReport)
    {
        return toAjax(tInspectionReportService.updateTInspectionReport(tInspectionReport));
    }

    /**
     * 删除t_inspection_report（巡检报告）
     */
    @ApiOperation("删除巡检报告")
    //@PreAuthorize("@ss.hasPermi('service:inspectionReport:remove')")
    @Log(title = "t_inspection_report（巡检报告）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tInspectionReportService.deleteTInspectionReportByIds(ids));
    }
}
