package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TRoutePointMapper;
import com.senxu.omserver.service.domain.TRoutePoint;
import com.senxu.omserver.service.service.ITRoutePointService;

/**
 * t_route_point（航线点信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TRoutePointServiceImpl implements ITRoutePointService 
{
    @Autowired(required = false)
    private TRoutePointMapper tRoutePointMapper;

    /**
     * 查询t_route_point（航线点信息）
     * 
     * @param routePointId t_route_point（航线点信息）ID
     * @return t_route_point（航线点信息）
     */
    @Override
    public TRoutePoint selectTRoutePointById(Long routePointId)
    {
        return tRoutePointMapper.selectTRoutePointById(routePointId);
    }

    @Override
    public TRoutePoint selectTRoutePointByRouteId(Long routeId) {
        return tRoutePointMapper.selectTRoutePointByRouteId(routeId);
    }

    /**
     * 查询t_route_point（航线点信息）列表
     * 
     * @param tRoutePoint t_route_point（航线点信息）
     * @return t_route_point（航线点信息）
     */
    @Override
    public List<TRoutePoint> selectTRoutePointList(TRoutePoint tRoutePoint)
    {
        return tRoutePointMapper.selectTRoutePointList(tRoutePoint);
    }

    /**
     * 新增t_route_point（航线点信息）
     * 
     * @param tRoutePoint t_route_point（航线点信息）
     * @return 结果
     */
    @Override
    public int insertTRoutePoint(TRoutePoint tRoutePoint)
    {
        return tRoutePointMapper.insertTRoutePoint(tRoutePoint);
    }

    /**
     * 修改t_route_point（航线点信息）
     * 
     * @param tRoutePoint t_route_point（航线点信息）
     * @return 结果
     */
    @Override
    public int updateTRoutePoint(TRoutePoint tRoutePoint)
    {
        tRoutePoint.setUpdateTime(DateUtils.getNowDate());
        return tRoutePointMapper.updateTRoutePoint(tRoutePoint);
    }

    /**
     * 批量删除t_route_point（航线点信息）
     * 
     * @param routePointIds 需要删除的t_route_point（航线点信息）ID
     * @return 结果
     */
    @Override
    public int deleteTRoutePointByIds(Long[] routePointIds)
    {
        return tRoutePointMapper.deleteTRoutePointByIds(routePointIds);
    }

    /**
     * 删除t_route_point（航线点信息）信息
     * 
     * @param routePointId t_route_point（航线点信息）ID
     * @return 结果
     */
    @Override
    public int deleteTRoutePointById(Long routePointId)
    {
        return tRoutePointMapper.deleteTRoutePointById(routePointId);
    }
}
