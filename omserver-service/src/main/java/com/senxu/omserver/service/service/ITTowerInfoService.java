package com.senxu.omserver.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.senxu.omserver.service.domain.TTowerInfo;
import com.senxu.omserver.service.domain.TYuntaiInfo;

/**
 * t_tower_info（杆塔信息）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-02
 */
public interface ITTowerInfoService 
{
    /**
     * 查询t_tower_info（杆塔信息）
     * 
     * @param id t_tower_info（杆塔信息）ID
     * @return t_tower_info（杆塔信息）
     */
    public TTowerInfo selectTTowerInfoById(Long id);

    /**
     * 查询t_tower_info（杆塔信息）列表
     * 
     * @param tTowerInfo t_tower_info（杆塔信息）
     * @return t_tower_info（杆塔信息）集合
     */
    public List<TTowerInfo> selectTTowerInfoList(TTowerInfo tTowerInfo);


    /**
     * 根据LineId查询该线路所包含的杆塔信息
     *
     * @param line_id 线路ID
     * @return t_tower_info（杆塔信息）集合
     */
    public List<HashMap<String, Object>> selectTTowerInfoListByLineId(Long lineId, String towerName, TYuntaiInfo yuntai);

    /**
     * 根据LineId查询该线路所包含的杆塔信息
     *
     * @param line_id 线路ID
     * @return t_tower_info（杆塔信息）集合
     */
    public List<HashMap<String, Object>> selectTTowerInfoListByLineId(Long lineId, Set<Long> towerInfos, List<TYuntaiInfo> yuntaiList);


    /**
     * 根据LineId查询该线路所包含的杆塔信息
     *
     * @param lineId 线路ID
     * @return t_tower_info（杆塔信息）集合
     */
    public List<TTowerInfo> selectTTowerInfoListByLineId(Long lineId);


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
     * 批量删除t_tower_info（杆塔信息）
     * 
     * @param ids 需要删除的t_tower_info（杆塔信息）ID
     * @return 结果
     */
    public int deleteTTowerInfoByIds(Long[] ids);

    /**
     * 删除t_tower_info（杆塔信息）信息
     * 
     * @param id t_tower_info（杆塔信息）ID
     * @return 结果
     */
    public int deleteTTowerInfoById(Long id);
}
