package com.senxu.omserver.web.controller.service;

import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TAirport;
import com.senxu.omserver.service.service.DyLoginService;
import com.senxu.omserver.service.service.ITAirportService;
import com.senxu.omserver.web.tasks.server.DyTaskServer;
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
import com.senxu.omserver.service.domain.TRemoteShouting;
import com.senxu.omserver.service.service.ITRemoteShoutingService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_remote_shouting（无人机远程喊话）Controller
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/system/shouting")
public class TRemoteShoutingController extends BaseController
{
    @Autowired
    private ITRemoteShoutingService tRemoteShoutingService;
    @Autowired
    private DyTaskServer dyTaskServer;
    @Autowired
    private ITAirportService tAirportService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询t_remote_shouting（无人机远程喊话）列表
     */
    //PreAuthorize("@ss.hasPermi('system:shouting:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRemoteShouting tRemoteShouting)
    {
        startPage();
        List<TRemoteShouting> list = tRemoteShoutingService.selectTRemoteShoutingList(tRemoteShouting);
        return getDataTable(list);
    }

    /**
     * 导出t_remote_shouting（无人机远程喊话）列表
     */
    //PreAuthorize("@ss.hasPermi('system:shouting:export')")
    @Log(title = "t_remote_shouting（无人机远程喊话）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TRemoteShouting tRemoteShouting)
    {
        List<TRemoteShouting> list = tRemoteShoutingService.selectTRemoteShoutingList(tRemoteShouting);
        ExcelUtil<TRemoteShouting> util = new ExcelUtil<TRemoteShouting>(TRemoteShouting.class);
        return util.exportExcel(list, "t_remote_shouting（无人机远程喊话）数据");
    }

    /**
     * 获取t_remote_shouting（无人机远程喊话）详细信息
     */
    //PreAuthorize("@ss.hasPermi('system:shouting:query')")
    @GetMapping(value = "/{shoutingId}")
    public AjaxResult getInfo(@PathVariable("shoutingId") Long shoutingId)
    {
        return AjaxResult.success(tRemoteShoutingService.selectTRemoteShoutingById(shoutingId));
    }

    /**
     * 新增t_remote_shouting（无人机远程喊话）
     */
    //PreAuthorize("@ss.hasPermi('system:shouting:add')")
    @Log(title = "t_remote_shouting（无人机远程喊话）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRemoteShouting tRemoteShouting)
    {
        AjaxResult result = new AjaxResult();
        TAirport tAirport = new TAirport();
        tAirport.setUavId(tRemoteShouting.getUavId());
        List<TAirport> list = tAirportService.selectTAirportList(tAirport);
        tRemoteShouting.setAirportId(list.get(0).getAirportId());
        tRemoteShouting.setInputTime(DateUtils.getNowDate());
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String userName = loginUser.getUser().getUserName();
        tRemoteShouting.setInputUser(userName);
        tRemoteShoutingService.insertTRemoteShouting(tRemoteShouting);
        String msg = dyTaskServer.remoteShout(tRemoteShouting);
        result.put("result",msg);
        return result;
    }

    /**
     * 修改t_remote_shouting（无人机远程喊话）
     */
    //PreAuthorize("@ss.hasPermi('system:shouting:edit')")
    @Log(title = "t_remote_shouting（无人机远程喊话）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRemoteShouting tRemoteShouting)
    {

        return toAjax(tRemoteShoutingService.updateTRemoteShouting(tRemoteShouting));
    }

    /**
     * 删除t_remote_shouting（无人机远程喊话）
     */
    //PreAuthorize("@ss.hasPermi('system:shouting:remove')")
    @Log(title = "t_remote_shouting（无人机远程喊话）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shoutingIds}")
    public AjaxResult remove(@PathVariable Long[] shoutingIds)
    {
        return toAjax(tRemoteShoutingService.deleteTRemoteShoutingByIds(shoutingIds));
    }
}
