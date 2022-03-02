package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TTowerVibration;

/**
 * 杆塔振动采样Service接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface ITTowerVibrationService 
{
    /**
     * 查询杆塔振动采样
     * 
     * @param id 杆塔振动采样ID
     * @return 杆塔振动采样
     */
    public TTowerVibration selectTTowerVibrationById(Long id);

    /**
     * 查询杆塔振动采样列表
     * 
     * @param tTowerVibration 杆塔振动采样
     * @return 杆塔振动采样集合
     */
    public List<TTowerVibration> selectTTowerVibrationList(TTowerVibration tTowerVibration);

    /**
     * 新增杆塔振动采样
     * 
     * @param tTowerVibration 杆塔振动采样
     * @return 结果
     */
    public int insertTTowerVibration(TTowerVibration tTowerVibration);

    /**
     * 修改杆塔振动采样
     * 
     * @param tTowerVibration 杆塔振动采样
     * @return 结果
     */
    public int updateTTowerVibration(TTowerVibration tTowerVibration);

    /**
     * 批量删除杆塔振动采样
     * 
     * @param ids 需要删除的杆塔振动采样ID
     * @return 结果
     */
    public int deleteTTowerVibrationByIds(Long[] ids);

    /**
     * 删除杆塔振动采样信息
     * 
     * @param id 杆塔振动采样ID
     * @return 结果
     */
    public int deleteTTowerVibrationById(Long id);
}
