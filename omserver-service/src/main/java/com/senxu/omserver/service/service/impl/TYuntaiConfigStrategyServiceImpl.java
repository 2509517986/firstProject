package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiConfigStrategyMapper;
import com.senxu.omserver.service.domain.TYuntaiConfigStrategy;
import com.senxu.omserver.service.service.ITYuntaiConfigStrategyService;

/**
 * t_yuntai_config_strategy（云台配置策略）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiConfigStrategyServiceImpl implements ITYuntaiConfigStrategyService 
{
    @Autowired
    private TYuntaiConfigStrategyMapper tYuntaiConfigStrategyMapper;

    /**
     * 查询t_yuntai_config_strategy（云台配置策略）
     * 
     * @param id t_yuntai_config_strategy（云台配置策略）ID
     * @return t_yuntai_config_strategy（云台配置策略）
     */
    @Override
    public TYuntaiConfigStrategy selectTYuntaiConfigStrategyById(Long id)
    {
        return tYuntaiConfigStrategyMapper.selectTYuntaiConfigStrategyById(id);
    }

    /**
     * 查询t_yuntai_config_strategy（云台配置策略）列表
     * 
     * @param tYuntaiConfigStrategy t_yuntai_config_strategy（云台配置策略）
     * @return t_yuntai_config_strategy（云台配置策略）
     */
    @Override
    public List<TYuntaiConfigStrategy> selectTYuntaiConfigStrategyList(TYuntaiConfigStrategy tYuntaiConfigStrategy)
    {
        return tYuntaiConfigStrategyMapper.selectTYuntaiConfigStrategyList(tYuntaiConfigStrategy);
    }

    /**
     * 新增t_yuntai_config_strategy（云台配置策略）
     * 
     * @param tYuntaiConfigStrategy t_yuntai_config_strategy（云台配置策略）
     * @return 结果
     */
    @Override
    public int insertTYuntaiConfigStrategy(TYuntaiConfigStrategy tYuntaiConfigStrategy)
    {
        tYuntaiConfigStrategy.setCreateTime(DateUtils.getNowDate());
        return tYuntaiConfigStrategyMapper.insertTYuntaiConfigStrategy(tYuntaiConfigStrategy);
    }

    /**
     * 修改t_yuntai_config_strategy（云台配置策略）
     * 
     * @param tYuntaiConfigStrategy t_yuntai_config_strategy（云台配置策略）
     * @return 结果
     */
    @Override
    public int updateTYuntaiConfigStrategy(TYuntaiConfigStrategy tYuntaiConfigStrategy)
    {
        return tYuntaiConfigStrategyMapper.updateTYuntaiConfigStrategy(tYuntaiConfigStrategy);
    }

    /**
     * 批量删除t_yuntai_config_strategy（云台配置策略）
     * 
     * @param ids 需要删除的t_yuntai_config_strategy（云台配置策略）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiConfigStrategyByIds(Long[] ids)
    {
        return tYuntaiConfigStrategyMapper.deleteTYuntaiConfigStrategyByIds(ids);
    }

    /**
     * 删除t_yuntai_config_strategy（云台配置策略）信息
     * 
     * @param id t_yuntai_config_strategy（云台配置策略）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiConfigStrategyById(Long id)
    {
        return tYuntaiConfigStrategyMapper.deleteTYuntaiConfigStrategyById(id);
    }
}
