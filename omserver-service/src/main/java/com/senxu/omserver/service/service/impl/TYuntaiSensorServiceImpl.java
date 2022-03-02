package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiSensorMapper;
import com.senxu.omserver.service.domain.TYuntaiSensor;
import com.senxu.omserver.service.service.ITYuntaiSensorService;

/**
 * t_yuntai_sensor（云台全感器数据）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiSensorServiceImpl implements ITYuntaiSensorService 
{
    @Autowired
    private TYuntaiSensorMapper tYuntaiSensorMapper;

    /**
     * 查询t_yuntai_sensor（云台全感器数据）
     * 
     * @param id t_yuntai_sensor（云台全感器数据）ID
     * @return t_yuntai_sensor（云台全感器数据）
     */
    @Override
    public TYuntaiSensor selectTYuntaiSensorById(Long id)
    {
        return tYuntaiSensorMapper.selectTYuntaiSensorById(id);
    }

    /**
     * 查询t_yuntai_sensor（云台全感器数据）列表
     * 
     * @param tYuntaiSensor t_yuntai_sensor（云台全感器数据）
     * @return t_yuntai_sensor（云台全感器数据）
     */
    @Override
    public List<TYuntaiSensor> selectTYuntaiSensorList(TYuntaiSensor tYuntaiSensor)
    {
        return tYuntaiSensorMapper.selectTYuntaiSensorList(tYuntaiSensor);
    }

    /**
     * 新增t_yuntai_sensor（云台全感器数据）
     * 
     * @param tYuntaiSensor t_yuntai_sensor（云台全感器数据）
     * @return 结果
     */
    @Override
    public int insertTYuntaiSensor(TYuntaiSensor tYuntaiSensor)
    {
        return tYuntaiSensorMapper.insertTYuntaiSensor(tYuntaiSensor);
    }

    /**
     * 修改t_yuntai_sensor（云台全感器数据）
     * 
     * @param tYuntaiSensor t_yuntai_sensor（云台全感器数据）
     * @return 结果
     */
    @Override
    public int updateTYuntaiSensor(TYuntaiSensor tYuntaiSensor)
    {
        return tYuntaiSensorMapper.updateTYuntaiSensor(tYuntaiSensor);
    }

    /**
     * 批量删除t_yuntai_sensor（云台全感器数据）
     * 
     * @param ids 需要删除的t_yuntai_sensor（云台全感器数据）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiSensorByIds(Long[] ids)
    {
        return tYuntaiSensorMapper.deleteTYuntaiSensorByIds(ids);
    }

    /**
     * 删除t_yuntai_sensor（云台全感器数据）信息
     * 
     * @param id t_yuntai_sensor（云台全感器数据）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiSensorById(Long id)
    {
        return tYuntaiSensorMapper.deleteTYuntaiSensorById(id);
    }
}
