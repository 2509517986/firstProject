package com.senxu.omserver.web.controller.service;

import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.core.page.TableDataInfo;
import com.senxu.omserver.common.core.redis.RedisCache;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TInspectionArguments;
import com.senxu.omserver.service.domain.TYuntaiCruise;
import com.senxu.omserver.service.service.TYuntaiCruiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.senxu.omserver.common.core.controller.BaseController;
import senxu.com.cmd.param.CruiseGroup;
import senxu.com.cmd.param.CruisePoint;
import senxu.com.cmd.param.InspectionGroup;
import senxu.com.cmd.response.CmdB3HResponse;
import senxu.com.cmd.response.CmdB6HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.service.CruiseService;
import senxu.com.service.InspectService;

/**
 * t_yuntai_cruise（巡航信息）Controller
 *
 * @author lvhp
 * @date 2021-10-26
 */

@Api("巡航管理")
@RestController
@RequestMapping("/service/cruise")
public class TYuntaiCruiseController extends BaseController{
    
    @Autowired
    TYuntaiCruiseService tYuntaiCruiseService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private NWOMServer nwomServer;


    /**
     * 查询t_yuntai_cruise（巡航信息）列表
     *
     */
    @ApiOperation("获取巡航列表")
    //@PreAuthorize("@ss.hasPermi('service:cruise:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiCruise tYuntaiCruise)
    {
       // List<TYuntaiCruise> list = tYuntaiCruiseService.selectTYuntaiCruiseList(tYuntaiCruise);
        DeviceCommandResponse<CmdB3HResponse> response=nwomServer.bridgeContext.cruiseService.getCruiseArguments(tYuntaiCruise.getCode(),tYuntaiCruise.getChanelNum());//,
        if(!response.isSuccess()){
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(response.getResponseCode());
            rspData.setMsg(response.getMessage());
            return rspData;
        }
        CmdB3HResponse cmdB3HResponse=response.getCmdResponse();
        System.out.println("response.getResponseCode()********************"+response.getResponseCode());
        System.out.println("response.getCmdResponse()********************"+response.getCmdResponse());



        //3.解析cmdB3HResponse返回给前端
        List list=new ArrayList();

        for (int i = 0;cmdB3HResponse!=null&&cmdB3HResponse.getCruiseGroups()!=null&& i < cmdB3HResponse.getCruiseGroups().size(); i++) {
            CruiseGroup group = cmdB3HResponse.getCruiseGroups().get(i);
            if(group.getNum() == tYuntaiCruise.getCruiseGroupNum()){
                for (CruisePoint point : group.getPointList()) {
                    TYuntaiCruise arguments = new TYuntaiCruise();
                    arguments.setPresetNum(point.getPreset());
                    arguments.setId(new Long(point.getSeq()));//巡航点序号
                    arguments.setSpeed(point.getSpeed());
                    arguments.setResidenceTime(point.getStayTime());
                    arguments.setCruiseGroupNum(group.getNum());
                    arguments.setChanelNum(cmdB3HResponse.getChannelNo());
                    arguments.setCode(tYuntaiCruise.getCode());
                    list.add(arguments);
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 获取t_yuntai_cruise（巡航信息）详细信息
     */
    @ApiOperation("获取巡航详细")
    //@PreAuthorize("@ss.hasPermi('service:cruise:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiCruiseService.selectTYuntaiCruiseById(id));
    }

    /**
     * 新增t_yuntai_cruise（巡航信息）
     */
    @ApiOperation("新增巡航信息")
    //@PreAuthorize("@ss.hasPermi('service:cruise:add')")
    @Log(title = "t_yuntai_cruise（巡航信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiCruise tYuntaiCruise)
    //public AjaxResult add(@RequestBody JSONObject jsonObject)
    {
        /** 入参格式
         * {"chanelNum":"1","cruiseGroupNum":1,"code":"0003",
         * "list":[
         * {"presetNum":1,"residenceTime":1,"speed":1},
         * {"presetNum":1,"residenceTime":1,"speed":1]}
         */
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        tYuntaiCruise.setCreateUser(String.valueOf(userId));
        tYuntaiCruiseService.insertTYuntaiCruise(tYuntaiCruise);
        List<TYuntaiCruise> tYuntaiCruises = tYuntaiCruiseService.selectTYuntaiCruiseList(new TYuntaiCruise());
        CruiseService cruiseService = nwomServer.bridgeContext.cruiseService;
        DeviceCommandResponse  response= cruiseService.saveCruiseArguments(
                tYuntaiCruise.getCode(),tYuntaiCruise.getChanelNum(),
                "1234",cruiseService.CMD_TYPE_ADD,tYuntaiCruise.getCruiseGroupNum(),tYuntaiCruises.get(0).getId().intValue(),
                tYuntaiCruise.getPresetNum(),
                tYuntaiCruise.getSpeed(),
                tYuntaiCruise.getResidenceTime()
        );
        if(!response.isSuccess()){
            return AjaxResult.error(response.getMessage());
        }
        return AjaxResult.success("保存成功");
    }

    /**
     * 修改t_yuntai_cruise（巡航信息）
     */
    @ApiOperation("修改巡航信息")
    //@PreAuthorize("@ss.hasPermi('service:cruise:edit')")
    @Log(title = "t_yuntai_cruise（巡航信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiCruise tYuntaiCruise)
    {
        CruiseService cruiseService = nwomServer.bridgeContext.cruiseService;
        DeviceCommandResponse  response= cruiseService.saveCruiseArguments(
                tYuntaiCruise.getCode(),tYuntaiCruise.getChanelNum(),
                "1234",cruiseService.CMD_TYPE_UPDATE,tYuntaiCruise.getCruiseGroupNum(), tYuntaiCruise.getId().intValue(),
                tYuntaiCruise.getPresetNum(),
                tYuntaiCruise.getSpeed(),
                tYuntaiCruise.getResidenceTime()
        );


        if(!response.isSuccess()){
            return AjaxResult.error(response.getMessage());
        }
        return AjaxResult.success("保存成功");
        // return toAjax(tYuntaiCruiseService.updateTYuntaiCruise(tYuntaiCruise));
    }

    /**
     * 删除t_yuntai_cruise（巡航信息）
     */
    @ApiOperation("删除巡航信息" )
    //@PreAuthorize("@ss.hasPermi('service:cruise:remove')")
    @Log(title = "t_yuntai_cruise（巡航信息）", businessType = BusinessType.DELETE)
    @DeleteMapping("")
    public AjaxResult remove(@RequestParam String deviceCode,@RequestParam Integer groupNum,
                             @RequestParam Integer pointIndex,@RequestParam Integer channelnum,
                             @RequestParam Integer preset, @RequestParam Integer speed,@RequestParam Integer stayTime)
    {
        CruiseService cruiseService = nwomServer.bridgeContext.cruiseService;

//String deviceCode, int channelNo, String password, int cmd, int groupNum, int pointIndex, int preset, int speed, int stayTime
        DeviceCommandResponse  response= cruiseService.saveCruiseArguments(
                deviceCode,channelnum,"1234",cruiseService.CMD_TYPE_DEL,groupNum,pointIndex,preset,speed,stayTime);
        if(!response.isSuccess()){
            return AjaxResult.error(response.getMessage());
        }
        return AjaxResult.success("删除成功");
        //return toAjax(tYuntaiCruiseService.deleteTYuntaiCruiseById(id));
    }

}
