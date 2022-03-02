package com.senxu.omserver.web.controller.service;

import java.util.List;
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
import com.senxu.omserver.service.domain.TRoutePoint;
import com.senxu.omserver.service.service.ITRoutePointService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_route_point（航线点信息）Controller
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@RestController
@RequestMapping("/system/point")
public class TRoutePointController extends BaseController
{
    @Autowired
    private ITRoutePointService tRoutePointService;

    /**
     * 查询t_route_point（航线点信息）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:point:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoutePoint tRoutePoint)
    {
        startPage();
        List<TRoutePoint> list = tRoutePointService.selectTRoutePointList(tRoutePoint);
        return getDataTable(list);
    }

    /**
     * 导出t_route_point（航线点信息）列表
     */
    //@PreAuthorize("@ss.hasPermi('system:point:export')")
    @Log(title = "t_route_point（航线点信息）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TRoutePoint tRoutePoint)
    {
        List<TRoutePoint> list = tRoutePointService.selectTRoutePointList(tRoutePoint);
        ExcelUtil<TRoutePoint> util = new ExcelUtil<TRoutePoint>(TRoutePoint.class);
        return util.exportExcel(list, "t_route_point（航线点信息）数据");
    }

    /**
     * 获取t_route_point（航线点信息）详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:point:query')")
    @GetMapping(value = "/{routePointId}")
    public AjaxResult getInfo(@PathVariable("routePointId") Long routePointId)
    {
        return AjaxResult.success(tRoutePointService.selectTRoutePointById(routePointId));
    }

    /**
     * 新增t_route_point（航线点信息）
     */
    //@PreAuthorize("@ss.hasPermi('system:point:add')")
    @Log(title = "t_route_point（航线点信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoutePoint tRoutePoint)
    {
        return toAjax(tRoutePointService.insertTRoutePoint(tRoutePoint));
    }

    /**
     * 修改t_route_point（航线点信息）
     */
    //@PreAuthorize("@ss.hasPermi('system:point:edit')")
    @Log(title = "t_route_point（航线点信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoutePoint tRoutePoint)
    {
        return toAjax(tRoutePointService.updateTRoutePoint(tRoutePoint));
    }

    /**
     * 删除t_route_point（航线点信息）
     */
    //@PreAuthorize("@ss.hasPermi('system:point:remove')")
    @Log(title = "t_route_point（航线点信息）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{routePointIds}")
    public AjaxResult remove(@PathVariable Long[] routePointIds)
    {
        return toAjax(tRoutePointService.deleteTRoutePointByIds(routePointIds));
    }
}
