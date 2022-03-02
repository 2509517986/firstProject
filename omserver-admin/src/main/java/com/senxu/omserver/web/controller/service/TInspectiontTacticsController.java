package com.senxu.omserver.web.controller.service;

import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.core.page.TableDataInfo;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TInspectiontTactics;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.service.service.TInspectiontTacticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import senxu.com.cmd.request.CmdB7HRequest;
import senxu.com.cmd.response.CmdB8HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;

/**
 * t_inspection_tactics（巡检策略配置）Controller
 *
 * @author lvhp
 * @date 2021-10-26
 */

@Api("巡检策略管理")
@RestController
@RequestMapping("/service/tactics")
public class TInspectiontTacticsController extends BaseController {

    @Autowired
    TInspectiontTacticsService tInspectiontTacticsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private NWOMServer nwomServer;

    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;

    /**
     * 查询t_inspection_tactics（巡检策略配置）列表
     */
    @ApiOperation("获取巡检策略配置")
    //@PreAuthorize("@ss.hasPermi('service:tactics:getInspectionTactics')")
    @GetMapping("/getInspectionTactics")
    public AjaxResult getInspectionTactics(TInspectiontTactics tInspectiontTactics)
    {
        AjaxResult ajaxResult=new AjaxResult();
        DeviceCommandResponse<CmdB8HResponse> response=nwomServer.bridgeContext.inspectService.getInspectiontTactics(tInspectiontTactics.getCode());
        if(!response.isSuccess()){
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(response.getResponseCode());
            rspData.setMsg(response.getMessage());
            return AjaxResult.error(response.getMessage());
        }

        //直接返回终端响应结果
        ajaxResult.put("CmdB8HResponse",response.getCmdResponse());

        return  ajaxResult;

    }

    /**
     * 获取t_inspection_tactics（巡检策略配置）详细信息
     */
    @ApiOperation("获取巡检策略配置详细")
    //@PreAuthorize("@ss.hasPermi('service:tactics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tInspectiontTacticsService.selectTInspectiontTacticsById(id));
    }

    /**
     * 保存t_inspection_tactics（巡检策略配置）
     */
    @ApiOperation("保存巡检策略配置")
    //@PreAuthorize("@ss.hasPermi('service:tactics:saveInspectionTactics')")
    @Log(title = "t_inspection_tactics（巡检策略配置）", businessType = BusinessType.INSERT)
    @PostMapping(value = "/saveInspectionTactics/{yuntaiId}")
    public AjaxResult saveInspectionTactics(@PathVariable("yuntaiId") Long yuntaiId, @RequestBody CmdB7HRequest cmdB7HRequest)
    {
        //通过UDP发送指令给客户端
        TYuntaiInfo yuntaiInfo=tYuntaiInfoService.selectTYuntaiInfoById(yuntaiId);
        if(cmdB7HRequest!=null){
            cmdB7HRequest.setPassword(yuntaiInfo.getPasword());//在这里要更新或设置一下密码
        }

        cmdB7HRequest.setStrategySetNum(cmdB7HRequest.getStrategySets().size());
        DeviceCommandResponse<CmdB7HRequest> response=nwomServer.bridgeContext.inspectService.saveInspectiontTactics(yuntaiInfo.getCode(),cmdB7HRequest);

        AjaxResult ajax = AjaxResult.success();
        if(response.isSuccess()){
            return ajax;
        }else{
            return AjaxResult.error(response.getMessage());
        }
    }

    /**
     * 修改t_inspection_tactics（巡检策略配置）
     */
    @ApiOperation("修改巡检策略配置")
    //@PreAuthorize("@ss.hasPermi('service:tactics:edit')")
    @Log(title = "t_inspection_tactics（巡检策略配置）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TInspectiontTactics tInspectiontTactics)
    {
        return toAjax(tInspectiontTacticsService.updateTInspectiontTactics(tInspectiontTactics));
    }

    /**
     * 删除t_inspection_tactics（巡检策略配置）
     */
    @ApiOperation("删除巡检策略配置")
    //@PreAuthorize("@ss.hasPermi('service:tactics:remove')")
    @Log(title = "t_inspection_tactics（巡检策略配置）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(tInspectiontTacticsService.deleteTInspectiontTacticsById(id));
    }

}
