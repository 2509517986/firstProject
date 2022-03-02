package com.senxu.omserver.web.controller.service;

import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.core.page.TableDataInfo;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.common.utils.StringUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TInspectionArguments;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.service.service.TInspectionArgumentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import senxu.com.cmd.param.InspectionGroup;
import senxu.com.cmd.response.CmdB6HResponse;
import senxu.com.cmd.response.DeviceCommandResponse;
import senxu.com.service.InspectService;

import java.util.ArrayList;
import java.util.List;

/**
 * t_inspection_arguments（巡检参数配置）Controller
 *
 * @author lvhp
 * @date 2021-10-26
 */

@Api("巡检参数管理")
@RestController
@RequestMapping("/service/arguments")
public class TInspectionArgumentsController extends BaseController {

    @Autowired
    TInspectionArgumentsService tInspectionArgumentsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private NWOMServer nwomServer;
    @Autowired
    ITYuntaiInfoService yuntaiInfoService;

    /**
     * 查询t_inspection_arguments（巡检参数配置）列表
     */
    @ApiOperation("获取巡检参数列表")
    //@PreAuthorize("@ss.hasPermi('service:arguments:list')")
    @GetMapping("/list")
    public TableDataInfo list(TInspectionArguments tInspectionArguments) {
        //从接口获取设备的数据
        DeviceCommandResponse<CmdB6HResponse> response = nwomServer.bridgeContext.inspectService.getInspectionArguments(tInspectionArguments.getCode(), Integer.parseInt(tInspectionArguments.getChanelNum()));
        if (!response.isSuccess()) {
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(response.getResponseCode());
            rspData.setMsg(response.getMessage());
            return rspData;
        }

        CmdB6HResponse response2 = response.getCmdResponse();
        List list = new ArrayList();
        for (int i = 0; response2 != null && response2.getInspectionGroups() != null && i < response2.getInspectionGroups().size(); i++) {
            InspectionGroup group = response2.getInspectionGroups().get(i);
            if (tInspectionArguments.getGroupId() == null) {//全部显示
                for (int j = 0; j < group.getPointNum(); j++) {
                    TInspectionArguments arguments = new TInspectionArguments();
                    arguments.setInspectionTacticsId(String.valueOf(j + 1));
                    arguments.setGroupId(group.getNum());
                    arguments.setChanelNum(String.valueOf(response2.getChannelNo()));
                    arguments.setId(new Long(j + 1));
                    arguments.setCode(tInspectionArguments.getCode());
                    list.add(arguments);
                }
            } else {//查询某组的
                if (group.getNum() == tInspectionArguments.getGroupId()) {
                    for (int j = 0; j < group.getPointNum(); j++) {
                        TInspectionArguments arguments = new TInspectionArguments();
                        arguments.setInspectionTacticsId(String.valueOf(j + 1));
                        arguments.setGroupId(group.getNum());
                        arguments.setChanelNum(String.valueOf(response2.getChannelNo()));
                        arguments.setId(new Long(j + 1));
                        arguments.setCode(tInspectionArguments.getCode());
                        list.add(arguments);
                    }
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 获取t_inspection_arguments（巡检参数配置）详细信息
     */
    @ApiOperation("获取巡检参数详细")
    //@PreAuthorize("@ss.hasPermi('service:arguments:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tInspectionArgumentsService.selectTInspectionArgumentsById(id));
    }

    /**
     * 新增t_inspection_arguments（巡检参数配置）
     */
    @ApiOperation("新增巡检参数配置")
    //@PreAuthorize("@ss.hasPermi('service:arguments:add')")
    @Log(title = "t_inspection_arguments（巡检参数配置）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TInspectionArguments tInspectionArguments) {
        if (isEmpty(tInspectionArguments.getChanelNum())) {
            tInspectionArguments.setChanelNum("1");
        }
        if (isEmpty(tInspectionArguments.getInspectionTacticsId())) {
            return AjaxResult.error("必须有序号");
        }

        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        tInspectionArguments.setCreateUser(String.valueOf(userId));
        tInspectionArgumentsService.insertTInspectionArguments(tInspectionArguments);

        InspectService inspectService = nwomServer.bridgeContext.inspectService;
        TYuntaiInfo yuntaiInfo = yuntaiInfoService.selectTYuntaiInfoByCode(tInspectionArguments.getCode());
        DeviceCommandResponse response = inspectService.saveInspectionArguments(
                tInspectionArguments.getCode(), Integer.parseInt(tInspectionArguments.getChanelNum()), yuntaiInfo.getPasword()
                , inspectService.CMD_TYPE_ADD, tInspectionArguments.getGroupId(), Integer.parseInt(tInspectionArguments.getInspectionTacticsId())
        );
        if (!response.isSuccess()) {
            return AjaxResult.error(response.getMessage());
        }
        return AjaxResult.success("保存成功");
    }

    private boolean isEmpty(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 修改t_inspection_arguments（巡检参数配置）
     */
    @ApiOperation("修改巡检参数配置")
    //@PreAuthorize("@ss.hasPermi('service:arguments:edit')")
    @Log(title = "t_inspection_arguments（巡检参数配置）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TInspectionArguments tInspectionArguments) {
        InspectService inspectService = nwomServer.bridgeContext.inspectService;
        if (isEmpty(tInspectionArguments.getChanelNum())) {
            tInspectionArguments.setChanelNum("1");
        }
        if (isEmpty(tInspectionArguments.getInspectionTacticsId())) {
            return AjaxResult.error("必须有序号");
        }
        TYuntaiInfo yuntaiInfo = yuntaiInfoService.selectTYuntaiInfoByCode(tInspectionArguments.getCode());
        DeviceCommandResponse response = inspectService.saveInspectionArguments(
                tInspectionArguments.getCode(), Integer.parseInt(tInspectionArguments.getChanelNum()), yuntaiInfo.getPasword()
                , inspectService.CMD_TYPE_UPDATE, tInspectionArguments.getGroupId(), Integer.parseInt(tInspectionArguments.getInspectionTacticsId())
        );
        if (!response.isSuccess()) {
            return AjaxResult.error(response.getMessage());
        }
        return AjaxResult.success("保存成功");

        //return toAjax(tInspectionArgumentsService.updateTInspectionArguments(tInspectionArguments));
    }

    /**
     * 删除t_inspection_arguments（巡检参数配置）
     */
    @ApiOperation("删除巡检参数配置")
    //@PreAuthorize("@ss.hasPermi('service:arguments:remove')")
    @Log(title = "t_inspection_arguments（巡检参数配置）", businessType = BusinessType.DELETE)
    // @DeleteMapping("/{id}")
    @DeleteMapping("")
    // public AjaxResult remove(@PathVariable Long id) {
    public AjaxResult remove(@RequestParam String deviceCode, @RequestParam Integer groupNum, @RequestParam Integer inspectIndex, @RequestParam Integer channelnum) {
        if (channelnum == null) {
            channelnum = 1;
        }
        if (inspectIndex == null) {
            return AjaxResult.error("必须有序号");
        }

        TInspectionArguments tInspectionArguments = new TInspectionArguments();
        tInspectionArguments.setInspectionTacticsId(String.valueOf(inspectIndex));
        tInspectionArguments.setCode(deviceCode);
        tInspectionArguments.setChanelNum(String.valueOf(channelnum));
        tInspectionArguments.setGroupId(groupNum);
        List<TInspectionArguments> list = tInspectionArgumentsService.selectTInspectionArgumentsList(tInspectionArguments);
        tInspectionArgumentsService.deleteTInspectionArgumentsById(list.size() > 0 ? list.get(0).getId() : 0);

        TInspectionArguments tInspectionArguments2 = new TInspectionArguments();
        tInspectionArguments.setCode(deviceCode);
        tInspectionArguments.setChanelNum(String.valueOf(channelnum));
        tInspectionArguments.setGroupId(groupNum);
        List<TInspectionArguments> list2 = tInspectionArgumentsService.selectTInspectionArgumentsList(tInspectionArguments2);
        for (int i = 0; i < list2.size(); i++) {
            TInspectionArguments tInspectionArguments3 = list2.get(i);
            tInspectionArguments3.setInspectionTacticsId(String.valueOf(i + 1));
            tInspectionArgumentsService.updateTInspectionArguments(tInspectionArguments3);
        }

        InspectService inspectService = nwomServer.bridgeContext.inspectService;
        TYuntaiInfo yuntaiInfo = yuntaiInfoService.selectTYuntaiInfoByCode(deviceCode);
        DeviceCommandResponse response = inspectService.saveInspectionArguments(
                deviceCode, channelnum, yuntaiInfo.getPasword(), inspectService.CMD_TYPE_DEL, groupNum, inspectIndex
        );
        if (!response.isSuccess()) {
            return AjaxResult.error(response.getMessage());
        }
        return AjaxResult.success("删除成功");
        //  return toAjax(tInspectionArgumentsService.deleteTInspectionArgumentsById(id));
    }
}