package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiEquipmentFailureMapper;
import com.senxu.omserver.service.domain.TYuntaiEquipmentFailure;
import com.senxu.omserver.service.service.ITYuntaiEquipmentFailureService;

/**
 * 设备故障报警Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TYuntaiEquipmentFailureServiceImpl implements ITYuntaiEquipmentFailureService 
{
    @Autowired
    private TYuntaiEquipmentFailureMapper tYuntaiEquipmentFailureMapper;

    /**
     * 查询设备故障报警
     * 
     * @param id 设备故障报警ID
     * @return 设备故障报警
     */
    @Override
    public TYuntaiEquipmentFailure selectTYuntaiEquipmentFailureById(Long id)
    {
        return tYuntaiEquipmentFailureMapper.selectTYuntaiEquipmentFailureById(id);
    }

    /**
     * 查询设备故障报警列表
     * 
     * @param tYuntaiEquipmentFailure 设备故障报警
     * @return 设备故障报警
     */
    @Override
    public List<TYuntaiEquipmentFailure> selectTYuntaiEquipmentFailureList(TYuntaiEquipmentFailure tYuntaiEquipmentFailure)
    {
        return tYuntaiEquipmentFailureMapper.selectTYuntaiEquipmentFailureList(tYuntaiEquipmentFailure);
    }

    /**
     * 新增设备故障报警
     * 
     * @param tYuntaiEquipmentFailure 设备故障报警
     * @return 结果
     */
    @Override
    public int insertTYuntaiEquipmentFailure(TYuntaiEquipmentFailure tYuntaiEquipmentFailure)
    {
        return tYuntaiEquipmentFailureMapper.insertTYuntaiEquipmentFailure(tYuntaiEquipmentFailure);
    }

    /**
     * 修改设备故障报警
     * 
     * @param tYuntaiEquipmentFailure 设备故障报警
     * @return 结果
     */
    @Override
    public int updateTYuntaiEquipmentFailure(TYuntaiEquipmentFailure tYuntaiEquipmentFailure)
    {
        return tYuntaiEquipmentFailureMapper.updateTYuntaiEquipmentFailure(tYuntaiEquipmentFailure);
    }

    /**
     * 批量删除设备故障报警
     * 
     * @param ids 需要删除的设备故障报警ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiEquipmentFailureByIds(Long[] ids)
    {
        return tYuntaiEquipmentFailureMapper.deleteTYuntaiEquipmentFailureByIds(ids);
    }

    /**
     * 删除设备故障报警信息
     * 
     * @param id 设备故障报警ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiEquipmentFailureById(Long id)
    {
        return tYuntaiEquipmentFailureMapper.deleteTYuntaiEquipmentFailureById(id);
    }
}
