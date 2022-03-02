package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TTowerPullMapper;
import com.senxu.omserver.service.domain.TTowerPull;
import com.senxu.omserver.service.service.ITTowerPullService;

/**
 * 杆塔拉力采样Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TTowerPullServiceImpl implements ITTowerPullService 
{
    @Autowired
    private TTowerPullMapper tTowerPullMapper;

    /**
     * 查询杆塔拉力采样
     * 
     * @param id 杆塔拉力采样ID
     * @return 杆塔拉力采样
     */
    @Override
    public TTowerPull selectTTowerPullById(Long id)
    {
        return tTowerPullMapper.selectTTowerPullById(id);
    }

    /**
     * 查询杆塔拉力采样列表
     * 
     * @param tTowerPull 杆塔拉力采样
     * @return 杆塔拉力采样
     */
    @Override
    public List<TTowerPull> selectTTowerPullList(TTowerPull tTowerPull)
    {
        return tTowerPullMapper.selectTTowerPullList(tTowerPull);
    }

    /**
     * 新增杆塔拉力采样
     * 
     * @param tTowerPull 杆塔拉力采样
     * @return 结果
     */
    @Override
    public int insertTTowerPull(TTowerPull tTowerPull)
    {
        return tTowerPullMapper.insertTTowerPull(tTowerPull);
    }

    /**
     * 修改杆塔拉力采样
     * 
     * @param tTowerPull 杆塔拉力采样
     * @return 结果
     */
    @Override
    public int updateTTowerPull(TTowerPull tTowerPull)
    {
        return tTowerPullMapper.updateTTowerPull(tTowerPull);
    }

    /**
     * 批量删除杆塔拉力采样
     * 
     * @param ids 需要删除的杆塔拉力采样ID
     * @return 结果
     */
    @Override
    public int deleteTTowerPullByIds(Long[] ids)
    {
        return tTowerPullMapper.deleteTTowerPullByIds(ids);
    }

    /**
     * 删除杆塔拉力采样信息
     * 
     * @param id 杆塔拉力采样ID
     * @return 结果
     */
    @Override
    public int deleteTTowerPullById(Long id)
    {
        return tTowerPullMapper.deleteTTowerPullById(id);
    }
}
