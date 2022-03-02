package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiConfigStrategy;

/**
 * t_yuntai_config_strategy（云台配置策略）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface TYuntaiConfigStrategyMapper 
{
    /**
     * 查询t_yuntai_config_strategy（云台配置策略）
     * 
     * @param id t_yuntai_config_strategy（云台配置策略）ID
     * @return t_yuntai_config_strategy（云台配置策略）
     */
    public TYuntaiConfigStrategy selectTYuntaiConfigStrategyById(Long id);

    /**
     * 查询t_yuntai_config_strategy（云台配置策略）列表
     * 
     * @param tYuntaiConfigStrategy t_yuntai_config_strategy（云台配置策略）
     * @return t_yuntai_config_strategy（云台配置策略）集合
     */
    public List<TYuntaiConfigStrategy> selectTYuntaiConfigStrategyList(TYuntaiConfigStrategy tYuntaiConfigStrategy);

    /**
     * 新增t_yuntai_config_strategy（云台配置策略）
     * 
     * @param tYuntaiConfigStrategy t_yuntai_config_strategy（云台配置策略）
     * @return 结果
     */
    public int insertTYuntaiConfigStrategy(TYuntaiConfigStrategy tYuntaiConfigStrategy);

    /**
     * 修改t_yuntai_config_strategy（云台配置策略）
     * 
     * @param tYuntaiConfigStrategy t_yuntai_config_strategy（云台配置策略）
     * @return 结果
     */
    public int updateTYuntaiConfigStrategy(TYuntaiConfigStrategy tYuntaiConfigStrategy);

    /**
     * 删除t_yuntai_config_strategy（云台配置策略）
     * 
     * @param id t_yuntai_config_strategy（云台配置策略）ID
     * @return 结果
     */
    public int deleteTYuntaiConfigStrategyById(Long id);

    /**
     * 批量删除t_yuntai_config_strategy（云台配置策略）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiConfigStrategyByIds(Long[] ids);
}
