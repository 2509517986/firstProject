package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TTowerInfo;
import org.apache.ibatis.annotations.Param;

/**
 * t_tower_info（杆塔信息）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-02
 */
public interface TTowerInfoMapper 
{
    /**
     * 查询t_tower_info（杆塔信息）
     * 
     * @param id t_tower_info（杆塔信息）ID
     * @return t_tower_info（杆塔信息）
     */
    public TTowerInfo selectTTowerInfoById(Long id);


    /**
     * 根据LineId查询该线路所包含的杆塔信息
     *
     * @param tTowerInfo 线路ID
     * @return t_tower_info（杆塔信息）集合
     */
    public List<TTowerInfo> selectTTowerInfoListByLineId(TTowerInfo tTowerInfo);

    /**
     * 查询t_tower_info（杆塔信息）列表
     * 
     * @param tTowerInfo t_tower_info（杆塔信息）
     * @return t_tower_info（杆塔信息）集合
     */
    public List<TTowerInfo> selectTTowerInfoList(TTowerInfo tTowerInfo);

    /**
     * 新增t_tower_info（杆塔信息）
     * 
     * @param tTowerInfo t_tower_info（杆塔信息）
     * @return 结果
     */
    public int insertTTowerInfo(TTowerInfo tTowerInfo);

    /**
     * 修改t_tower_info（杆塔信息）
     * 
     * @param tTowerInfo t_tower_info（杆塔信息）
     * @return 结果
     */
    public int updateTTowerInfo(TTowerInfo tTowerInfo);

    /**
     * 删除t_tower_info（杆塔信息）
     * 
     * @param id t_tower_info（杆塔信息）ID
     * @return 结果
     */
    public int deleteTTowerInfoById(Long id);

    /**
     * 批量删除t_tower_info（杆塔信息）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTTowerInfoByIds(Long[] ids);
}
