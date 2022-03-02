package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiPresetPositionMapper;
import com.senxu.omserver.service.domain.TYuntaiPresetPosition;
import com.senxu.omserver.service.service.ITYuntaiPresetPositionService;

/**
 * t_yuntai_preset_position（云台预置位信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiPresetPositionServiceImpl implements ITYuntaiPresetPositionService 
{
    @Autowired
    private TYuntaiPresetPositionMapper tYuntaiPresetPositionMapper;

    /**
     * 查询t_yuntai_preset_position（云台预置位信息）
     * 
     * @param id t_yuntai_preset_position（云台预置位信息）ID
     * @return t_yuntai_preset_position（云台预置位信息）
     */
    @Override
    public TYuntaiPresetPosition selectTYuntaiPresetPositionById(Long id)
    {
        return tYuntaiPresetPositionMapper.selectTYuntaiPresetPositionById(id);
    }

    /**
     * 查询t_yuntai_preset_position（云台预置位信息）列表
     * 
     * @param tYuntaiPresetPosition t_yuntai_preset_position（云台预置位信息）
     * @return t_yuntai_preset_position（云台预置位信息）
     */
    @Override
    public List<TYuntaiPresetPosition> selectTYuntaiPresetPositionList(TYuntaiPresetPosition tYuntaiPresetPosition)
    {
        return tYuntaiPresetPositionMapper.selectTYuntaiPresetPositionList(tYuntaiPresetPosition);
    }

    /**
     * 新增t_yuntai_preset_position（云台预置位信息）
     * 
     * @param tYuntaiPresetPosition t_yuntai_preset_position（云台预置位信息）
     * @return 结果
     */
    @Override
    public int insertTYuntaiPresetPosition(TYuntaiPresetPosition tYuntaiPresetPosition)
    {
        return tYuntaiPresetPositionMapper.insertTYuntaiPresetPosition(tYuntaiPresetPosition);
    }

    /**
     * 修改t_yuntai_preset_position（云台预置位信息）
     * 
     * @param tYuntaiPresetPosition t_yuntai_preset_position（云台预置位信息）
     * @return 结果
     */
    @Override
    public int updateTYuntaiPresetPosition(TYuntaiPresetPosition tYuntaiPresetPosition)
    {
        return tYuntaiPresetPositionMapper.updateTYuntaiPresetPosition(tYuntaiPresetPosition);
    }

    /**
     * 批量删除t_yuntai_preset_position（云台预置位信息）
     * 
     * @param ids 需要删除的t_yuntai_preset_position（云台预置位信息）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiPresetPositionByIds(Long[] ids)
    {
        return tYuntaiPresetPositionMapper.deleteTYuntaiPresetPositionByIds(ids);
    }

    /**
     * 删除t_yuntai_preset_position（云台预置位信息）信息
     * 
     * @param id t_yuntai_preset_position（云台预置位信息）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiPresetPositionById(Long id)
    {
        return tYuntaiPresetPositionMapper.deleteTYuntaiPresetPositionById(id);
    }

    @Override
    public TYuntaiPresetPosition selectTYuntaiPresetPosition(TYuntaiPresetPosition yuntaiPresetPosition) {
        return tYuntaiPresetPositionMapper.selectTYuntaiPresetPosition(yuntaiPresetPosition);
    }
}
