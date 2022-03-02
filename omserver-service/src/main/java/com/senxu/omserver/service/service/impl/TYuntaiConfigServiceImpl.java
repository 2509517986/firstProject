package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiConfigMapper;
import com.senxu.omserver.service.domain.TYuntaiConfig;
import com.senxu.omserver.service.service.ITYuntaiConfigService;

/**
 * t_yuntai_config（云台配置记录）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-15
 */
@Service
public class TYuntaiConfigServiceImpl implements ITYuntaiConfigService 
{
    @Autowired
    private TYuntaiConfigMapper tYuntaiConfigMapper;

    /**
     * 查询t_yuntai_config（云台配置记录）
     * 
     * @param id t_yuntai_config（云台配置记录）ID
     * @return t_yuntai_config（云台配置记录）
     */
    @Override
    public TYuntaiConfig selectTYuntaiConfigById(Long id)
    {
        return tYuntaiConfigMapper.selectTYuntaiConfigById(id);
    }

    /**
     * 查询t_yuntai_config（云台配置记录）列表
     * 
     * @param tYuntaiConfig t_yuntai_config（云台配置记录）
     * @return t_yuntai_config（云台配置记录）
     */
    @Override
    public List<TYuntaiConfig> selectTYuntaiConfigList(TYuntaiConfig tYuntaiConfig)
    {
        return tYuntaiConfigMapper.selectTYuntaiConfigList(tYuntaiConfig);
    }

    /**
     * 新增t_yuntai_config（云台配置记录）
     * 
     * @param tYuntaiConfig t_yuntai_config（云台配置记录）
     * @return 结果
     */
    @Override
    public int insertTYuntaiConfig(TYuntaiConfig tYuntaiConfig)
    {
        tYuntaiConfig.setCreateTime(DateUtils.getNowDate());
        return tYuntaiConfigMapper.insertTYuntaiConfig(tYuntaiConfig);
    }

    /**
     * 修改t_yuntai_config（云台配置记录）
     * 
     * @param tYuntaiConfig t_yuntai_config（云台配置记录）
     * @return 结果
     */
    @Override
    public int updateTYuntaiConfig(TYuntaiConfig tYuntaiConfig)
    {
        return tYuntaiConfigMapper.updateTYuntaiConfig(tYuntaiConfig);
    }

    /**
     * 批量删除t_yuntai_config（云台配置记录）
     * 
     * @param ids 需要删除的t_yuntai_config（云台配置记录）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiConfigByIds(Long[] ids)
    {
        return tYuntaiConfigMapper.deleteTYuntaiConfigByIds(ids);
    }

    /**
     * 删除t_yuntai_config（云台配置记录）信息
     * 
     * @param id t_yuntai_config（云台配置记录）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiConfigById(Long id)
    {
        return tYuntaiConfigMapper.deleteTYuntaiConfigById(id);
    }
}
