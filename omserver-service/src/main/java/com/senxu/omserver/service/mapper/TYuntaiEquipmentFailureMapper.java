package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiEquipmentFailure;

/**
 * 设备故障报警Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface TYuntaiEquipmentFailureMapper 
{
    /**
     * 查询设备故障报警
     * 
     * @param id 设备故障报警ID
     * @return 设备故障报警
     */
    public TYuntaiEquipmentFailure selectTYuntaiEquipmentFailureById(Long id);

    /**
     * 查询设备故障报警列表
     * 
     * @param tYuntaiEquipmentFailure 设备故障报警
     * @return 设备故障报警集合
     */
    public List<TYuntaiEquipmentFailure> selectTYuntaiEquipmentFailureList(TYuntaiEquipmentFailure tYuntaiEquipmentFailure);

    /**
     * 新增设备故障报警
     * 
     * @param tYuntaiEquipmentFailure 设备故障报警
     * @return 结果
     */
    public int insertTYuntaiEquipmentFailure(TYuntaiEquipmentFailure tYuntaiEquipmentFailure);

    /**
     * 修改设备故障报警
     * 
     * @param tYuntaiEquipmentFailure 设备故障报警
     * @return 结果
     */
    public int updateTYuntaiEquipmentFailure(TYuntaiEquipmentFailure tYuntaiEquipmentFailure);

    /**
     * 删除设备故障报警
     * 
     * @param id 设备故障报警ID
     * @return 结果
     */
    public int deleteTYuntaiEquipmentFailureById(Long id);

    /**
     * 批量删除设备故障报警
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiEquipmentFailureByIds(Long[] ids);
}
