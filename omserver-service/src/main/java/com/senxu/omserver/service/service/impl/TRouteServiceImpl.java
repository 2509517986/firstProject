package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TRouteMapper;
import com.senxu.omserver.service.domain.TRoute;
import com.senxu.omserver.service.service.ITRouteService;

/**
 * t_route（航线信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TRouteServiceImpl implements ITRouteService 
{
    @Autowired
    private TRouteMapper tRouteMapper;

    /**
     * 查询t_route（航线信息）
     * 
     * @param routeId t_route（航线信息）ID
     * @return t_route（航线信息）
     */
    @Override
    public TRoute selectTRouteById(Long routeId)
    {
        return tRouteMapper.selectTRouteById(routeId);
    }

    @Override
    public TRoute selectTRouteByTaskId(Long taskId) {
        return tRouteMapper.selectTRouteByTaskId(taskId);
    }

    /**
     * 查询t_route（航线信息）列表
     * 
     * @param tRoute t_route（航线信息）
     * @return t_route（航线信息）
     */
    @Override
    public List<TRoute> selectTRouteList(TRoute tRoute)
    {
        return tRouteMapper.selectTRouteList(tRoute);
    }

    /**
     * 新增t_route（航线信息）
     * 
     * @param tRoute t_route（航线信息）
     * @return 结果
     */
    @Override
    public int insertTRoute(TRoute tRoute)
    {
        return tRouteMapper.insertTRoute(tRoute);
    }

    /**
     * 修改t_route（航线信息）
     * 
     * @param tRoute t_route（航线信息）
     * @return 结果
     */
    @Override
    public int updateTRoute(TRoute tRoute)
    {
        tRoute.setUpdateTime(DateUtils.getNowDate());
        return tRouteMapper.updateTRoute(tRoute);
    }

    /**
     * 批量删除t_route（航线信息）
     * 
     * @param routeIds 需要删除的t_route（航线信息）ID
     * @return 结果
     */
    @Override
    public int deleteTRouteByIds(Long[] routeIds)
    {
        return tRouteMapper.deleteTRouteByIds(routeIds);
    }

    /**
     * 删除t_route（航线信息）信息
     * 
     * @param routeId t_route（航线信息）ID
     * @return 结果
     */
    @Override
    public int deleteTRouteById(Long routeId)
    {
        return tRouteMapper.deleteTRouteById(routeId);
    }
}
