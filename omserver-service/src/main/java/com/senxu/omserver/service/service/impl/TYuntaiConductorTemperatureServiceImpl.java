package com.senxu.omserver.service.service.impl;

import java.util.List;

import com.senxu.omserver.service.domain.TYuntaiConductorTemperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiConductorTemperatureMapper;
import com.senxu.omserver.service.service.ITYuntaiConductorTemperatureService;

/**
 * 导线温度采样Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TYuntaiConductorTemperatureServiceImpl implements ITYuntaiConductorTemperatureService 
{
    @Autowired
    private TYuntaiConductorTemperatureMapper tYuntaiConductorTemperatureMapper;

    /**
     * 查询导线温度采样
     * 
     * @param id 导线温度采样ID
     * @return 导线温度采样
     */
    @Override
    public TYuntaiConductorTemperature selectTYuntaiConductorTemperatureById(Long id)
    {
        return tYuntaiConductorTemperatureMapper.selectTYuntaiConductorTemperatureById(id);
    }

    /**
     * 查询导线温度采样列表
     * 
     * @param tYuntaiConductorTemperature 导线温度采样
     * @return 导线温度采样
     */
    @Override
    public List<TYuntaiConductorTemperature> selectTYuntaiConductorTemperatureList(TYuntaiConductorTemperature tYuntaiConductorTemperature)
    {
        return tYuntaiConductorTemperatureMapper.selectTYuntaiConductorTemperatureList(tYuntaiConductorTemperature);
    }

    /**
     * 新增导线温度采样
     * 
     * @param tYuntaiConductorTemperature 导线温度采样
     * @return 结果
     */
    @Override
    public int insertTYuntaiConductorTemperature(TYuntaiConductorTemperature tYuntaiConductorTemperature)
    {
        return tYuntaiConductorTemperatureMapper.insertTYuntaiConductorTemperature(tYuntaiConductorTemperature);
    }

    /**
     * 修改导线温度采样
     * 
     * @param tYuntaiConductorTemperature 导线温度采样
     * @return 结果
     */
    @Override
    public int updateTYuntaiConductorTemperature(TYuntaiConductorTemperature tYuntaiConductorTemperature)
    {
        return tYuntaiConductorTemperatureMapper.updateTYuntaiConductorTemperature(tYuntaiConductorTemperature);
    }

    /**
     * 批量删除导线温度采样
     * 
     * @param ids 需要删除的导线温度采样ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiConductorTemperatureByIds(Long[] ids)
    {
        return tYuntaiConductorTemperatureMapper.deleteTYuntaiConductorTemperatureByIds(ids);
    }

    /**
     * 删除导线温度采样信息
     * 
     * @param id 导线温度采样ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiConductorTemperatureById(Long id)
    {
        return tYuntaiConductorTemperatureMapper.deleteTYuntaiConductorTemperatureById(id);
    }
}
