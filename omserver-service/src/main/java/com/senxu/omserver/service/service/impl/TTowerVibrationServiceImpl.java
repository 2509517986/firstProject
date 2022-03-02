package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TTowerVibrationMapper;
import com.senxu.omserver.service.domain.TTowerVibration;
import com.senxu.omserver.service.service.ITTowerVibrationService;

/**
 * 杆塔振动采样Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TTowerVibrationServiceImpl implements ITTowerVibrationService 
{
    @Autowired
    private TTowerVibrationMapper tTowerVibrationMapper;

    /**
     * 查询杆塔振动采样
     * 
     * @param id 杆塔振动采样ID
     * @return 杆塔振动采样
     */
    @Override
    public TTowerVibration selectTTowerVibrationById(Long id)
    {
        return tTowerVibrationMapper.selectTTowerVibrationById(id);
    }

    /**
     * 查询杆塔振动采样列表
     * 
     * @param tTowerVibration 杆塔振动采样
     * @return 杆塔振动采样
     */
    @Override
    public List<TTowerVibration> selectTTowerVibrationList(TTowerVibration tTowerVibration)
    {
        return tTowerVibrationMapper.selectTTowerVibrationList(tTowerVibration);
    }

    /**
     * 新增杆塔振动采样
     * 
     * @param tTowerVibration 杆塔振动采样
     * @return 结果
     */
    @Override
    public int insertTTowerVibration(TTowerVibration tTowerVibration)
    {
        return tTowerVibrationMapper.insertTTowerVibration(tTowerVibration);
    }

    /**
     * 修改杆塔振动采样
     * 
     * @param tTowerVibration 杆塔振动采样
     * @return 结果
     */
    @Override
    public int updateTTowerVibration(TTowerVibration tTowerVibration)
    {
        return tTowerVibrationMapper.updateTTowerVibration(tTowerVibration);
    }

    /**
     * 批量删除杆塔振动采样
     * 
     * @param ids 需要删除的杆塔振动采样ID
     * @return 结果
     */
    @Override
    public int deleteTTowerVibrationByIds(Long[] ids)
    {
        return tTowerVibrationMapper.deleteTTowerVibrationByIds(ids);
    }

    /**
     * 删除杆塔振动采样信息
     * 
     * @param id 杆塔振动采样ID
     * @return 结果
     */
    @Override
    public int deleteTTowerVibrationById(Long id)
    {
        return tTowerVibrationMapper.deleteTTowerVibrationById(id);
    }
}
