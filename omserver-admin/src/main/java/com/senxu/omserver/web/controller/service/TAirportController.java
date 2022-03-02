package com.senxu.omserver.web.controller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.senxu.omserver.common.core.redis.RedisCache;
import com.senxu.omserver.service.domain.TRoute;
import com.senxu.omserver.service.domain.TRoutePoint;
import com.senxu.omserver.service.domain.TTask;
import com.senxu.omserver.service.service.ITRoutePointService;
import com.senxu.omserver.service.service.ITRouteService;
import com.senxu.omserver.service.service.ITTaskService;
import com.senxu.omserver.web.tasks.server.DyTaskServer;
import org.apache.commons.collections4.Put;
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
import com.senxu.omserver.service.domain.TAirport;
import com.senxu.omserver.service.service.ITAirportService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_airport（机巢信息）Controller
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/system/airport")
public class TAirportController extends BaseController
{
    @Autowired
    private ITAirportService tAirportService;
    @Autowired
    private DyTaskServer dyTaskServer;
    @Autowired
    private ITTaskService tTaskService;
    @Autowired
    private ITRouteService tRouteService;
    @Autowired
    private ITRoutePointService tRoutePointService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询t_airport（机巢信息）列表
     */
    //PreAuthorize("@ss.hasPermi('system:airport:list')")
    @GetMapping("/list")
    public TableDataInfo list(TAirport tAirport)
    {
        startPage();
        List<TAirport> list = tAirportService.selectTAirportList(tAirport);
        return getDataTable(list);
    }

    @GetMapping("/airportTree")
    public AjaxResult getAirportTree(TAirport tAirport)
    {
        List<HashMap<String, Object>> treeMap = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> treeson = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> treeRoute = new ArrayList<HashMap<String, Object>>();
        List<TAirport> list = tAirportService.selectTAirportList(tAirport);
        List<Long> longList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TAirport airport = list.get(i);
            longList.add(airport.getAirportId());
            HashMap<String, Object> uavMap = new HashMap<>();
            HashMap<String, Object> airportMap = new HashMap<>();
            uavMap.put("uavId",airport.getUavId());
            uavMap.put("fcId",airport.getFcId());
            uavMap.put("icon","uav");
            uavMap.put("uav_state",airport.getUavState());
            uavMap.put("airportId",airport.getAirportId());
            treeson.add(new HashMap<String, Object>(){{
                put("airportId",airport.getAirportId());
                put("airportName",airport.getAirportName());
                put("airportCode",airport.getAirportCode());
                put("type",airport.getRelname());
                put("icon","airport");
                put("state",airport.getState());
                put("locationLat",airport.getLocationLat());
                put("locationLon",airport.getLocationLon());
                put("locationAltinm",airport.getLocationAltinm());
                put("spareLat",airport.getSpareLat());
                put("spareLon",airport.getSpareLon());
                put("spareAltinm",airport.getSpareAltinm());
                put("children",uavMap);
            }});
        }

        treeMap.add(new HashMap<String, Object>(){{
            put("airportId","0");
            put("airportName","广东电网广州供电局");
            put("airportCode",null);
            put("type",null);
            put("icon","deptIcon");
            put("children",treeson);
        }});

        for (Long airportId : longList){
            TTask tTask = new TTask();
            tTask.setAirportId(airportId);
            List<TTask> taskList = tTaskService.selectTTaskList(tTask);
            for(TTask task : taskList) {
                List<TRoute> routes= tRouteService.selectTRouteList(new TRoute(task.getTaskId()));
                for(TRoute tRoute : routes){
                    if(tRoute != null){
                        treeRoute.add(new HashMap<String, Object>() {{
                            put("airportId",task.getAirportId());
                            put("routeId",tRoute.getRouteId());
                            put("taskId",tRoute.getTaskId());
                            put("routeCode",tRoute.getRouteCode());
                            put("name",tRoute.getName());
                            put("describes",tRoute.getDescribes());
                            put("groupId",tRoute.getGroupId());
                            put("kind",tRoute.getKind());
                            put("startLat",tRoute.getStartAltinm());
                            put("startLon",tRoute.getStartLon());
                            put("startAltinm",tRoute.getStartAltinm());
                            put("endLat",tRoute.getEndLat());
                            put("endLon",tRoute.getEndLon());
                            put("endAltinm",tRoute.getEndAltinm());
                            put("updateTime",tRoute.getUpdateTime());
                            put("tRoutePointList",tRoutePointService.selectTRoutePointList(new TRoutePoint(tRoute.getRouteId())));
                        }});
                    }
                }


            }
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("tree",treeMap);
        ajax.put("tRoute",treeRoute);
        return ajax;
    }

    /**
     * 导出t_airport（机巢信息）列表
     */
    //PreAuthorize("@ss.hasPermi('system:airport:export')")
    @Log(title = "t_airport（机巢信息）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TAirport tAirport)
    {
        List<TAirport> list = tAirportService.selectTAirportList(tAirport);
        ExcelUtil<TAirport> util = new ExcelUtil<TAirport>(TAirport.class);
        return util.exportExcel(list, "t_airport（机巢信息）数据");
    }

    /**
     * 获取t_airport（机巢信息）详细信息
     */
    //PreAuthorize("@ss.hasPermi('system:airport:query')")
    @GetMapping(value = "/{airportId}")
    public AjaxResult getInfo(@PathVariable("airportId") Long airportId)
    {
        return AjaxResult.success(tAirportService.selectTAirportById(airportId));
    }

    /**
     * 新增t_airport（机巢信息）
     */
    //PreAuthorize("@ss.hasPermi('system:airport:add')")
    @Log(title = "t_airport（机巢信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TAirport tAirport)
    {
        return toAjax(tAirportService.insertTAirport(tAirport));
    }

    /**
     * 修改t_airport（机巢信息）
     */
    //PreAuthorize("@ss.hasPermi('system:airport:edit')")
    @Log(title = "t_airport（机巢信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TAirport tAirport)
    {
        return toAjax(tAirportService.updateTAirport(tAirport));
    }

    /**
     * 删除t_airport（机巢信息）
     */
    //PreAuthorize("@ss.hasPermi('system:airport:remove')")
    @Log(title = "t_airport（机巢信息）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{airportIds}")
    public AjaxResult remove(@PathVariable Long[] airportIds)
    {
        return toAjax(tAirportService.deleteTAirportByIds(airportIds));
    }


    @GetMapping("/liveviewStart")
    public String liveviewStart(TAirport tAirport)
    {
        String msg = dyTaskServer.liveviewStart(tAirport.getAirportCode(),1,tAirport.getFcId(),10000);
        return msg;
    }

    @GetMapping("/liveviewStop")
    public String liveviewStop(TAirport tAirport)
    {
        String msg = dyTaskServer.liveviewStop(tAirport.getAirportCode(),1,tAirport.getFcId());
        return msg;
    }

    @GetMapping("/getDyToken")
    public AjaxResult getDyToken(TAirport tAirport)
    {
        String token = redisCache.getCacheObject("token");
        AjaxResult result = new AjaxResult();
        result.put("token",token);
        return result;
    }

}
