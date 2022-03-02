package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiConfig;

/**
 * t_yuntai_config（云台配置记录）Mapper接口
 * 
 * @author panyc
 * @date 2021-06-15
 */
public interface TYuntaiConfigMapper 
{
    /**
     * 查询t_yuntai_config（云台配置记录）
     * 
     * @param id t_yuntai_config（云台配置记录）ID
     * @return t_yuntai_config（云台配置记录）
     */
    public TYuntaiConfig selectTYuntaiConfigById(Long id);

    /**
     * 查询t_yuntai_config（云台配置记录）列表
     * 
     * @param tYuntaiConfig t_yuntai_config（云台配置记录）
     * @return t_yuntai_config（云台配置记录）集合
     */
    public List<TYuntaiConfig> selectTYuntaiConfigList(TYuntaiConfig tYuntaiConfig);

    /**
     * 新增t_yuntai_config（云台配置记录）
     * 
     * @param tYuntaiConfig t_yuntai_config（云台配置记录）
     * @return 结果
     */
    public int insertTYuntaiConfig(TYuntaiConfig tYuntaiConfig);

    /**
     * 修改t_yuntai_config（云台配置记录）
     * 
     * @param tYuntaiConfig t_yuntai_config（云台配置记录）
     * @return 结果
     */
    public int updateTYuntaiConfig(TYuntaiConfig tYuntaiConfig);

    /**
     * 删除t_yuntai_config（云台配置记录）
     * 
     * @param id t_yuntai_config（云台配置记录）ID
     * @return 结果
     */
    public int deleteTYuntaiConfigById(Long id);

    /**
     * 批量删除t_yuntai_config（云台配置记录）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiConfigByIds(Long[] ids);
}
