package com.senxu.omserver.web.controller.service;

import java.util.Date;
import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
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
import com.senxu.omserver.service.domain.TTowerInfo;
import com.senxu.omserver.service.service.ITTowerInfoService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_tower_info（杆塔信息）Controller
 * 
 * @author panyc
 * @date 2021-06-02
 */
@Api("杆塔信息管理")
@RestController
@RequestMapping("/service/towerInfo")
public class TTowerInfoController extends BaseController
{
    @Autowired
    private ITTowerInfoService tTowerInfoService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询t_tower_info（杆塔信息）列表
     */
    @ApiOperation("获取杆塔列表")
    //@PreAuthorize("@ss.hasPermi('service:tower:list')")
    @GetMapping("/list")
    public TableDataInfo list(TTowerInfo tTowerInfo)
    {
        startPage();
        List<TTowerInfo> list = tTowerInfoService.selectTTowerInfoList(tTowerInfo);
        return getDataTable(list);
    }

    /**
     * 导出t_tower_info（杆塔信息）列表
     */
    @ApiOperation("导出杆塔列表")
    //@PreAuthorize("@ss.hasPermi('service:tower:export')")
    @Log(title = "t_tower_info（杆塔信息）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TTowerInfo tTowerInfo)
    {
        List<TTowerInfo> list = tTowerInfoService.selectTTowerInfoList(tTowerInfo);
        ExcelUtil<TTowerInfo> util = new ExcelUtil<TTowerInfo>(TTowerInfo.class);
        return util.exportExcel(list, "t_tower_info（杆塔信息）数据");
    }

    /**
     * 获取t_tower_info（杆塔信息）详细信息
     */
    @ApiOperation("获取杆塔详细")
    //@PreAuthorize("@ss.hasPermi('service:tower:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tTowerInfoService.selectTTowerInfoById(id));
    }

    /**
     * 新增t_tower_info（杆塔信息）
     */
    @ApiOperation("新增杆塔信息")
    //@PreAuthorize("@ss.hasPermi('service:tower:add')")
    @Log(title = "t_tower_info（杆塔信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TTowerInfo tTowerInfo)
    {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        tTowerInfo.setCreateUser(loginUser.getUser().getUserName());
        tTowerInfo.setModifyUser(loginUser.getUser().getUserName());
        tTowerInfo.setModifyTime(new Date());
        return toAjax(tTowerInfoService.insertTTowerInfo(tTowerInfo));
    }

    /**
     * 修改t_tower_info（杆塔信息）
     */
    @ApiOperation("修改杆塔信息")
    //@PreAuthorize("@ss.hasPermi('service:tower:edit')")
    @Log(title = "t_tower_info（杆塔信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TTowerInfo tTowerInfo)
    {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        tTowerInfo.setModifyUser(loginUser.getUser().getUserName());
        tTowerInfo.setModifyTime(new Date());
        return toAjax(tTowerInfoService.updateTTowerInfo(tTowerInfo));
    }

    /**
     * 删除t_tower_info（杆塔信息）
     */
    @ApiOperation("删除杆塔信息")
    //@PreAuthorize("@ss.hasPermi('service:tower:remove')")
    @Log(title = "t_tower_info（杆塔信息）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (int i = 0; i < ids.length ; i++) {
            Long id = ids[i];
            TTowerInfo towerInfo = tTowerInfoService.selectTTowerInfoById(id);
            TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
            tYuntaiInfo.setTowerId(id);
            List<TYuntaiInfo> list = tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
            if(list.size() > 0){
                return AjaxResult.error("存在下级部门,不允许删除");
            }
        }
        return toAjax(tTowerInfoService.deleteTTowerInfoByIds(ids));
    }
}
