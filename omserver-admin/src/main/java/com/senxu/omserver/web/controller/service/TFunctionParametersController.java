package com.senxu.omserver.web.controller.service;

import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.core.page.TableDataInfo;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.service.domain.TFunctionParameters;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.service.service.TFunctionParametersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import senxu.com.cmd.response.Cmd0AHResponse;
import senxu.com.cmd.response.DeviceCommandResponse;

import java.util.List;

/**
 * t_function_parameters（功能参数）Controller
 *
 * @author lvhp
 * @date 2021-10-26
 */
@Api("功能参数管理")
@RestController
@RequestMapping("/service/function")
public class TFunctionParametersController extends BaseController {

    @Autowired(required = false)
    TFunctionParametersService tFunctionParametersService;
    @Autowired(required = false)
    ITYuntaiInfoService yuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;
    /**
     * 查询t_function_parameters（功能参数）列表
     */
    @ApiOperation("获取巡检参数列表")
    //@PreAuthorize("@ss.hasPermi('service:arguments:list')")
    @GetMapping("/list")
    public TableDataInfo list(TFunctionParameters tFunctionParameters) {
        startPage();
        List<TFunctionParameters> list = tFunctionParametersService.selectTFunctionParametersList(tFunctionParameters);
        return getDataTable(list);
    }

    /**
     * 获取t_function_parameters（功能参数）详细信息
     */
    @ApiOperation("获取功能参数详细")
    //@PreAuthorize("@ss.hasPermi('service:arguments:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        TYuntaiInfo yuntaiInfo= yuntaiInfoService.selectTYuntaiInfoById(id);
        DeviceCommandResponse<Cmd0AHResponse> deviceCommandResponse=nwomServer.getDeviceBridge().Cmd0AH(yuntaiInfo.getCode());
        Cmd0AHResponse cmd0AHResponse= deviceCommandResponse.getCmdResponse();
        TFunctionParameters functionParameters=new TFunctionParameters();
        functionParameters.setBrightness(cmd0AHResponse.getPictureBrightness1());
        functionParameters.setContrast(cmd0AHResponse.getPictureContrast1());
        functionParameters.setSaturation(cmd0AHResponse.getPictureSaturation1());
        functionParameters.setWindowsHeat(cmd0AHResponse.getHeartbeatInterval());
        return AjaxResult.success(functionParameters);
    }

    /**
     * 新增t_function_parameters（功能参数）
     */
    @ApiOperation("新增功能参数")
    //@PreAuthorize("@ss.hasPermi('service:arguments:add')")
    @Log(title = "t_function_parameters（功能参数）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TFunctionParameters tFunctionParameters) {
        return toAjax(tFunctionParametersService.insertTFunctionParameters(tFunctionParameters));
    }

    /**
     * 修改t_function_parameters（功能参数）
     */
    @ApiOperation("修改功能参数")
    //@PreAuthorize("@ss.hasPermi('service:arguments:edit')")
    @Log(title = "t_function_parameters（功能参数）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TFunctionParameters tFunctionParameters) {

        TYuntaiInfo yuntaiInfo= yuntaiInfoService.selectTYuntaiInfoById(tFunctionParameters.getYuntaiId());
        tFunctionParametersService.updateTFunctionParameters(tFunctionParameters);

        if(tFunctionParameters.getBrightness()!=null
        ||tFunctionParameters.getContrast()!=null
        ||tFunctionParameters.getSaturation()!=null){
            DeviceCommandResponse<Cmd0AHResponse> deviceCommandResponse=nwomServer.getDeviceBridge().
                    setPicParams(yuntaiInfo.getCode()
                            ,yuntaiInfo.getPasword(),tFunctionParameters.getBrightness(),tFunctionParameters.getContrast(),
                            tFunctionParameters.getSaturation(),tFunctionParameters.getPictureColor1(),tFunctionParameters.getPictureResolution1());

        }

         //return toAjax(tFunctionParametersService.updateTFunctionParameters(tFunctionParameters));
        /*case 17:// * 17&-1：广州局功能：就是控制雨刷开启；省电科院测试功能：巡航一圈后缩放开启
        ptzCtrl(PTZActionEnum.OPEN_WIPER, request.val);
        break;
        case 18://* 18&-1：广州局功能：就是控制雨刷关闭；省电科院测试功能：巡航一圈后缩放停止
        ptzCtrl(PTZActionEnum.CLOSE_WIPER, request.val);//关闭雨刷，好像会自动关闭，不用关，需要测试验证
        break;
        */
        if(tFunctionParameters.getWiper()==1)
        {
            nwomServer.getDeviceBridge().remoteModulation(yuntaiInfo.getCode(), yuntaiInfo.getPasword(), 1, 17, 0);

        }
        else{
            nwomServer.getDeviceBridge().remoteModulation(yuntaiInfo.getCode(), yuntaiInfo.getPasword(), 1, 18, 0);

        }
       /* if(tFunctionParameters.getWindowsHeat()==1)
        {
            nwomServer.getDeviceBridge().remoteModulation(yuntaiInfo.getCode(), yuntaiInfo.getPasword(), 1, 9, 17);

        }
        else{
            nwomServer.getDeviceBridge().remoteModulation(yuntaiInfo.getCode(), yuntaiInfo.getPasword(), 1, 9, 18);

        }*/
        return AjaxResult.success("");
    }

    /**
     * 删除t_function_parameters（功能参数）
     */
    @ApiOperation("删除功能参数")
    //@PreAuthorize("@ss.hasPermi('service:arguments:remove')")
    @Log(title = "t_function_parameters（功能参数）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(tFunctionParametersService.deleteTFunctionParametersById(id));
    }

    /**
     * 设置t_function_parameters（功能参数）详细信息
     */
    @ApiOperation("设置巡检参数详细")
    //@PreAuthorize("@ss.hasPermi('service:arguments:set')")
    @GetMapping(value = "/setParameters")
    public AjaxResult setParameters(TFunctionParameters tFunctionParameters) {
        //查询当前参数记录，如果存在则修改当前参数，不存在则新增参数记录
        TFunctionParameters functionParameters = tFunctionParametersService.selectTFunctionParametersByYuntaiId(tFunctionParameters.getYuntaiId());
        if(functionParameters.getId() == null || "".equals(functionParameters.getId())){
            tFunctionParametersService.insertTFunctionParameters(tFunctionParameters);
        }else {
            tFunctionParametersService.updateTFunctionParameters(tFunctionParameters);
        }
        return AjaxResult.success(tFunctionParametersService.selectTFunctionParametersByYuntaiId(tFunctionParameters.getYuntaiId()));
    }
}