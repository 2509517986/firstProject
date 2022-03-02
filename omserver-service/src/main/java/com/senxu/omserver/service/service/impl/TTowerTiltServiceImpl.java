package com.senxu.omserver.service.service.impl;

import java.util.List;

import com.senxu.omserver.service.ITTowerTiltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TTowerTiltMapper;
import com.senxu.omserver.service.domain.TTowerTilt;

/**
 * 杆塔倾斜数据采样Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TTowerTiltServiceImpl implements ITTowerTiltService
{
    @Autowired
    private TTowerTiltMapper tTowerTiltMapper;

    /**
     * 查询杆塔倾斜数据采样
     * 
     * @param id 杆塔倾斜数据采样ID
     * @return 杆塔倾斜数据采样
     */
    @Override
    public TTowerTilt selectTTowerTiltById(Long id)
    {
        return tTowerTiltMapper.selectTTowerTiltById(id);
    }

    /**
     * 查询杆塔倾斜数据采样列表
     * 
     * @param tTowerTilt 杆塔倾斜数据采样
     * @return 杆塔倾斜数据采样
     */
    @Override
    public List<TTowerTilt> selectTTowerTiltList(TTowerTilt tTowerTilt)
    {
        return tTowerTiltMapper.selectTTowerTiltList(tTowerTilt);
    }

    /**
     * 新增杆塔倾斜数据采样
     * 
     * @param tTowerTilt 杆塔倾斜数据采样
     * @return 结果
     */
    @Override
    public int insertTTowerTilt(TTowerTilt tTowerTilt)
    {
        return tTowerTiltMapper.insertTTowerTilt(tTowerTilt);
    }

    /**
     * 修改杆塔倾斜数据采样
     * 
     * @param tTowerTilt 杆塔倾斜数据采样
     * @return 结果
     */
    @Override
    public int updateTTowerTilt(TTowerTilt tTowerTilt)
    {
        return tTowerTiltMapper.updateTTowerTilt(tTowerTilt);
    }

    /**
     * 批量删除杆塔倾斜数据采样
     * 
     * @param ids 需要删除的杆塔倾斜数据采样ID
     * @return 结果
     */
    @Override
    public int deleteTTowerTiltByIds(Long[] ids)
    {
        return tTowerTiltMapper.deleteTTowerTiltByIds(ids);
    }

    /**
     * 删除杆塔倾斜数据采样信息
     * 
     * @param id 杆塔倾斜数据采样ID
     * @return 结果
     */
    @Override
    public int deleteTTowerTiltById(Long id)
    {
        return tTowerTiltMapper.deleteTTowerTiltById(id);
    }
}
