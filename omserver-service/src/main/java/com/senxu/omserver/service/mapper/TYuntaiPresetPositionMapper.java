package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiPresetPosition;

/**
 * t_yuntai_preset_position（云台预置位信息）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface TYuntaiPresetPositionMapper 
{
    /**
     * 查询t_yuntai_preset_position（云台预置位信息）
     * 
     * @param id t_yuntai_preset_position（云台预置位信息）ID
     * @return t_yuntai_preset_position（云台预置位信息）
     */
    public TYuntaiPresetPosition selectTYuntaiPresetPositionById(Long id);

    /**
     * 查询t_yuntai_preset_position（云台预置位信息）列表
     * 
     * @param tYuntaiPresetPosition t_yuntai_preset_position（云台预置位信息）
     * @return t_yuntai_preset_position（云台预置位信息）集合
     */
    public List<TYuntaiPresetPosition> selectTYuntaiPresetPositionList(TYuntaiPresetPosition tYuntaiPresetPosition);

    /**
     * 新增t_yuntai_preset_position（云台预置位信息）
     * 
     * @param tYuntaiPresetPosition t_yuntai_preset_position（云台预置位信息）
     * @return 结果
     */
    public int insertTYuntaiPresetPosition(TYuntaiPresetPosition tYuntaiPresetPosition);

    /**
     * 修改t_yuntai_preset_position（云台预置位信息）
     * 
     * @param tYuntaiPresetPosition t_yuntai_preset_position（云台预置位信息）
     * @return 结果
     */
    public int updateTYuntaiPresetPosition(TYuntaiPresetPosition tYuntaiPresetPosition);

    /**
     * 删除t_yuntai_preset_position（云台预置位信息）
     * 
     * @param id t_yuntai_preset_position（云台预置位信息）ID
     * @return 结果
     */
    public int deleteTYuntaiPresetPositionById(Long id);

    /**
     * 批量删除t_yuntai_preset_position（云台预置位信息）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiPresetPositionByIds(Long[] ids);

    TYuntaiPresetPosition selectTYuntaiPresetPosition(TYuntaiPresetPosition yuntaiPresetPosition);
}
