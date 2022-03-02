package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TRoutePoint;

/**
 * t_route_point（航线点信息）Service接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public interface ITRoutePointService 
{
    /**
     * 查询t_route_point（航线点信息）
     * 
     * @param routePointId t_route_point（航线点信息）ID
     * @return t_route_point（航线点信息）
     */
    public TRoutePoint selectTRoutePointById(Long routePointId);

    /**
     * 查询t_route_point（航线点信息）
     *
     * @param routeId t_route_point（航线点信息）ID
     * @return t_route_point（航线点信息）
     */
    public TRoutePoint selectTRoutePointByRouteId(Long routeId);

    /**
     * 查询t_route_point（航线点信息）列表
     * 
     * @param tRoutePoint t_route_point（航线点信息）
     * @return t_route_point（航线点信息）集合
     */
    public List<TRoutePoint> selectTRoutePointList(TRoutePoint tRoutePoint);

    /**
     * 新增t_route_point（航线点信息）
     * 
     * @param tRoutePoint t_route_point（航线点信息）
     * @return 结果
     */
    public int insertTRoutePoint(TRoutePoint tRoutePoint);

    /**
     * 修改t_route_point（航线点信息）
     * 
     * @param tRoutePoint t_route_point（航线点信息）
     * @return 结果
     */
    public int updateTRoutePoint(TRoutePoint tRoutePoint);

    /**
     * 批量删除t_route_point（航线点信息）
     * 
     * @param routePointIds 需要删除的t_route_point（航线点信息）ID
     * @return 结果
     */
    public int deleteTRoutePointByIds(Long[] routePointIds);

    /**
     * 删除t_route_point（航线点信息）信息
     * 
     * @param routePointId t_route_point（航线点信息）ID
     * @return 结果
     */
    public int deleteTRoutePointById(Long routePointId);
}
