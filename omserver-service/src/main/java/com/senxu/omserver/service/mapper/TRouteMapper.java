package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TRoute;
import org.springframework.stereotype.Repository;

/**
 * t_route（航线信息）Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Repository
public interface TRouteMapper 
{
    /**
     * 查询t_route（航线信息）
     * 
     * @param routeId t_route（航线信息）ID
     * @return t_route（航线信息）
     */
    public TRoute selectTRouteById(Long routeId);

    /**
     * 查询t_route（航线信息）
     *
     * @param taskId t_route（航线信息）ID
     * @return t_route（航线信息）
     */
    public TRoute selectTRouteByTaskId(Long taskId);

    /**
     * 查询t_route（航线信息）列表
     * 
     * @param tRoute t_route（航线信息）
     * @return t_route（航线信息）集合
     */
    public List<TRoute> selectTRouteList(TRoute tRoute);

    /**
     * 新增t_route（航线信息）
     * 
     * @param tRoute t_route（航线信息）
     * @return 结果
     */
    public int insertTRoute(TRoute tRoute);

    /**
     * 修改t_route（航线信息）
     * 
     * @param tRoute t_route（航线信息）
     * @return 结果
     */
    public int updateTRoute(TRoute tRoute);

    /**
     * 修改t_route（航线信息）
     *
     * @param tRoute t_route（航线信息）
     * @return 结果
     */
    public int updateTRouteByCode(TRoute tRoute);

    /**
     * 删除t_route（航线信息）
     * 
     * @param routeId t_route（航线信息）ID
     * @return 结果
     */
    public int deleteTRouteById(Long routeId);

    /**
     * 批量删除t_route（航线信息）
     * 
     * @param routeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTRouteByIds(Long[] routeIds);

    /**
     * 根据航线编号查航线id
     *
     * @param routeCode 航线编号
     * @return 结果
     */
    public Long selectRouteIdByRouteCode(String routeCode);
}
