package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TTowerTilt;

/**
 * 杆塔倾斜数据采样Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface TTowerTiltMapper 
{
    /**
     * 查询杆塔倾斜数据采样
     * 
     * @param id 杆塔倾斜数据采样ID
     * @return 杆塔倾斜数据采样
     */
    public TTowerTilt selectTTowerTiltById(Long id);

    /**
     * 查询杆塔倾斜数据采样列表
     * 
     * @param tTowerTilt 杆塔倾斜数据采样
     * @return 杆塔倾斜数据采样集合
     */
    public List<TTowerTilt> selectTTowerTiltList(TTowerTilt tTowerTilt);

    /**
     * 新增杆塔倾斜数据采样
     * 
     * @param tTowerTilt 杆塔倾斜数据采样
     * @return 结果
     */
    public int insertTTowerTilt(TTowerTilt tTowerTilt);

    /**
     * 修改杆塔倾斜数据采样
     * 
     * @param tTowerTilt 杆塔倾斜数据采样
     * @return 结果
     */
    public int updateTTowerTilt(TTowerTilt tTowerTilt);

    /**
     * 删除杆塔倾斜数据采样
     * 
     * @param id 杆塔倾斜数据采样ID
     * @return 结果
     */
    public int deleteTTowerTiltById(Long id);

    /**
     * 批量删除杆塔倾斜数据采样
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTTowerTiltByIds(Long[] ids);
}
