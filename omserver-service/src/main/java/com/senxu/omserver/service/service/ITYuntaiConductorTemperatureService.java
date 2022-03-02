package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiConductorTemperature;

/**
 * 导线温度采样Service接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface ITYuntaiConductorTemperatureService 
{
    /**
     * 查询导线温度采样
     * 
     * @param id 导线温度采样ID
     * @return 导线温度采样
     */
    public TYuntaiConductorTemperature selectTYuntaiConductorTemperatureById(Long id);

    /**
     * 查询导线温度采样列表
     * 
     * @param tYuntaiConductorTemperature 导线温度采样
     * @return 导线温度采样集合
     */
    public List<TYuntaiConductorTemperature> selectTYuntaiConductorTemperatureList(TYuntaiConductorTemperature tYuntaiConductorTemperature);

    /**
     * 新增导线温度采样
     * 
     * @param tYuntaiConductorTemperature 导线温度采样
     * @return 结果
     */
    public int insertTYuntaiConductorTemperature(TYuntaiConductorTemperature tYuntaiConductorTemperature);

    /**
     * 修改导线温度采样
     * 
     * @param tYuntaiConductorTemperature 导线温度采样
     * @return 结果
     */
    public int updateTYuntaiConductorTemperature(TYuntaiConductorTemperature tYuntaiConductorTemperature);

    /**
     * 批量删除导线温度采样
     * 
     * @param ids 需要删除的导线温度采样ID
     * @return 结果
     */
    public int deleteTYuntaiConductorTemperatureByIds(Long[] ids);

    /**
     * 删除导线温度采样信息
     * 
     * @param id 导线温度采样ID
     * @return 结果
     */
    public int deleteTYuntaiConductorTemperatureById(Long id);
}
