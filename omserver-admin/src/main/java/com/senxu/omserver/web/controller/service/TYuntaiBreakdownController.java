package com.senxu.omserver.web.controller.service;

import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
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
import com.senxu.omserver.service.domain.TYuntaiBreakdown;
import com.senxu.omserver.service.service.ITYuntaiBreakdownService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_yuntai_breakdown（云台缺销记录）Controller
 * 
 * @author ruoyi
 * @date 2021-09-27
 */
@RestController
@RequestMapping("/service/breakdown")
public class TYuntaiBreakdownController extends BaseController
{
    @Autowired
    private ITYuntaiBreakdownService tYuntaiBreakdownService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private TokenService tokenService;


    /**
     * 查询t_yuntai_breakdown（云台缺销记录）列表
     */
    @ApiOperation("查询云台缺销记录列表")
    //@PreAuthorize("@ss.hasPermi('service:breakdown:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiBreakdown tYuntaiBreakdown)
    {
        startPage();
        List<TYuntaiBreakdown> list = tYuntaiBreakdownService.selectTYuntaiBreakdownList(tYuntaiBreakdown);
        return getDataTable(list);
    }

    /**
     * 导出t_yuntai_breakdown（云台缺销记录）列表
     */
    @ApiOperation("导出云台缺销记录列表")
    //@PreAuthorize("@ss.hasPermi('service:breakdown:export')")
    @Log(title = "t_yuntai_breakdown（云台缺销记录）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiBreakdown tYuntaiBreakdown)
    {
        List<TYuntaiBreakdown> list = tYuntaiBreakdownService.selectTYuntaiBreakdownList(tYuntaiBreakdown);
        ExcelUtil<TYuntaiBreakdown> util = new ExcelUtil<TYuntaiBreakdown>(TYuntaiBreakdown.class);
        return util.exportExcel(list, "t_yuntai_breakdown（云台缺销记录）数据");
    }

    /**
     * 获取t_yuntai_breakdown（云台缺销记录）详细信息
     */
    @ApiOperation("获取云台缺销记录详细")
    //@PreAuthorize("@ss.hasPermi('service:breakdown:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiBreakdownService.selectTYuntaiBreakdownById(id));
    }

    /**
     * 新增t_yuntai_breakdown（云台缺销记录）
     */
    @ApiOperation("新增云台缺销记录")
    //@PreAuthorize("@ss.hasPermi('service:breakdown:add')")
    @Log(title = "t_yuntai_breakdown（云台缺销记录）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiBreakdown tYuntaiBreakdown)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        TYuntaiInfo tYuntaiInfo = tYuntaiInfoService.selectTYuntaiInfoById(tYuntaiBreakdown.getYuntaiId());
        tYuntaiBreakdown.setCode(tYuntaiInfo.getCode());
        tYuntaiBreakdown.setDutyUser(loginUser.getUsername());
        tYuntaiBreakdown.setLineName(tYuntaiInfo.getLineName());
        tYuntaiBreakdown.setFactory(tYuntaiInfo.getFactory());
        return toAjax(tYuntaiBreakdownService.insertTYuntaiBreakdown(tYuntaiBreakdown));
    }

    /**
     * 修改t_yuntai_breakdown（云台缺销记录）
     */
    @ApiOperation("修改云台缺销记录")
    //@PreAuthorize("@ss.hasPermi('service:breakdown:edit')")
    @Log(title = "t_yuntai_breakdown（云台缺销记录）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiBreakdown tYuntaiBreakdown)
    {
        return toAjax(tYuntaiBreakdownService.updateTYuntaiBreakdown(tYuntaiBreakdown));
    }

    /**
     * 删除t_yuntai_breakdown（云台缺销记录）
     */
    @ApiOperation("删除云台缺销记录")
    //@PreAuthorize("@ss.hasPermi('service:breakdown:remove')")
    @Log(title = "t_yuntai_breakdown（云台缺销记录）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiBreakdownService.deleteTYuntaiBreakdownByIds(ids));
    }
}
