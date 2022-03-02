package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiSensor;

/**
 * t_yuntai_sensor（云台全感器数据）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface ITYuntaiSensorService 
{
    /**
     * 查询t_yuntai_sensor（云台全感器数据）
     * 
     * @param id t_yuntai_sensor（云台全感器数据）ID
     * @return t_yuntai_sensor（云台全感器数据）
     */
    public TYuntaiSensor selectTYuntaiSensorById(Long id);

    /**
     * 查询t_yuntai_sensor（云台全感器数据）列表
     * 
     * @param tYuntaiSensor t_yuntai_sensor（云台全感器数据）
     * @return t_yuntai_sensor（云台全感器数据）集合
     */
    public List<TYuntaiSensor> selectTYuntaiSensorList(TYuntaiSensor tYuntaiSensor);

    /**
     * 新增t_yuntai_sensor（云台全感器数据）
     * 
     * @param tYuntaiSensor t_yuntai_sensor（云台全感器数据）
     * @return 结果
     */
    public int insertTYuntaiSensor(TYuntaiSensor tYuntaiSensor);

    /**
     * 修改t_yuntai_sensor（云台全感器数据）
     * 
     * @param tYuntaiSensor t_yuntai_sensor（云台全感器数据）
     * @return 结果
     */
    public int updateTYuntaiSensor(TYuntaiSensor tYuntaiSensor);

    /**
     * 批量删除t_yuntai_sensor（云台全感器数据）
     * 
     * @param ids 需要删除的t_yuntai_sensor（云台全感器数据）ID
     * @return 结果
     */
    public int deleteTYuntaiSensorByIds(Long[] ids);

    /**
     * 删除t_yuntai_sensor（云台全感器数据）信息
     * 
     * @param id t_yuntai_sensor（云台全感器数据）ID
     * @return 结果
     */
    public int deleteTYuntaiSensorById(Long id);
}
