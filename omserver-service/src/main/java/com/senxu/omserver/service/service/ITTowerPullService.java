package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TTowerPull;

/**
 * 杆塔拉力采样Service接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface ITTowerPullService 
{
    /**
     * 查询杆塔拉力采样
     * 
     * @param id 杆塔拉力采样ID
     * @return 杆塔拉力采样
     */
    public TTowerPull selectTTowerPullById(Long id);

    /**
     * 查询杆塔拉力采样列表
     * 
     * @param tTowerPull 杆塔拉力采样
     * @return 杆塔拉力采样集合
     */
    public List<TTowerPull> selectTTowerPullList(TTowerPull tTowerPull);

    /**
     * 新增杆塔拉力采样
     * 
     * @param tTowerPull 杆塔拉力采样
     * @return 结果
     */
    public int insertTTowerPull(TTowerPull tTowerPull);

    /**
     * 修改杆塔拉力采样
     * 
     * @param tTowerPull 杆塔拉力采样
     * @return 结果
     */
    public int updateTTowerPull(TTowerPull tTowerPull);

    /**
     * 批量删除杆塔拉力采样
     * 
     * @param ids 需要删除的杆塔拉力采样ID
     * @return 结果
     */
    public int deleteTTowerPullByIds(Long[] ids);

    /**
     * 删除杆塔拉力采样信息
     * 
     * @param id 杆塔拉力采样ID
     * @return 结果
     */
    public int deleteTTowerPullById(Long id);
}
