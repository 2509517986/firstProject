package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiConductorVibrationMapper;
import com.senxu.omserver.service.domain.TYuntaiConductorVibration;
import com.senxu.omserver.service.service.ITYuntaiConductorVibrationService;

/**
 * 导线振动采样Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TYuntaiConductorVibrationServiceImpl implements ITYuntaiConductorVibrationService 
{
    @Autowired
    private TYuntaiConductorVibrationMapper tYuntaiConductorVibrationMapper;

    /**
     * 查询导线振动采样
     * 
     * @param id 导线振动采样ID
     * @return 导线振动采样
     */
    @Override
    public TYuntaiConductorVibration selectTYuntaiConductorVibrationById(Long id)
    {
        return tYuntaiConductorVibrationMapper.selectTYuntaiConductorVibrationById(id);
    }

    /**
     * 查询导线振动采样列表
     * 
     * @param tYuntaiConductorVibration 导线振动采样
     * @return 导线振动采样
     */
    @Override
    public List<TYuntaiConductorVibration> selectTYuntaiConductorVibrationList(TYuntaiConductorVibration tYuntaiConductorVibration)
    {
        return tYuntaiConductorVibrationMapper.selectTYuntaiConductorVibrationList(tYuntaiConductorVibration);
    }

    /**
     * 新增导线振动采样
     * 
     * @param tYuntaiConductorVibration 导线振动采样
     * @return 结果
     */
    @Override
    public int insertTYuntaiConductorVibration(TYuntaiConductorVibration tYuntaiConductorVibration)
    {
        return tYuntaiConductorVibrationMapper.insertTYuntaiConductorVibration(tYuntaiConductorVibration);
    }

    /**
     * 修改导线振动采样
     * 
     * @param tYuntaiConductorVibration 导线振动采样
     * @return 结果
     */
    @Override
    public int updateTYuntaiConductorVibration(TYuntaiConductorVibration tYuntaiConductorVibration)
    {
        return tYuntaiConductorVibrationMapper.updateTYuntaiConductorVibration(tYuntaiConductorVibration);
    }

    /**
     * 批量删除导线振动采样
     * 
     * @param ids 需要删除的导线振动采样ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiConductorVibrationByIds(Long[] ids)
    {
        return tYuntaiConductorVibrationMapper.deleteTYuntaiConductorVibrationByIds(ids);
    }

    /**
     * 删除导线振动采样信息
     * 
     * @param id 导线振动采样ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiConductorVibrationById(Long id)
    {
        return tYuntaiConductorVibrationMapper.deleteTYuntaiConductorVibrationById(id);
    }
}
