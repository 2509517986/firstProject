package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TAirportSlotbatteryMapper;
import com.senxu.omserver.service.domain.TAirportSlotbattery;
import com.senxu.omserver.service.service.ITAirportSlotbatteryService;

/**
 * t_airport_slotbattery（机巢电池槽）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TAirportSlotbatteryServiceImpl implements ITAirportSlotbatteryService 
{
    @Autowired
    private TAirportSlotbatteryMapper tAirportSlotbatteryMapper;

    /**
     * 查询t_airport_slotbattery（机巢电池槽）
     * 
     * @param slotbatteryId t_airport_slotbattery（机巢电池槽）ID
     * @return t_airport_slotbattery（机巢电池槽）
     */
    @Override
    public TAirportSlotbattery selectTAirportSlotbatteryById(Long slotbatteryId)
    {
        return tAirportSlotbatteryMapper.selectTAirportSlotbatteryById(slotbatteryId);
    }

    /**
     * 查询t_airport_slotbattery（机巢电池槽）列表
     * 
     * @param tAirportSlotbattery t_airport_slotbattery（机巢电池槽）
     * @return t_airport_slotbattery（机巢电池槽）
     */
    @Override
    public List<TAirportSlotbattery> selectTAirportSlotbatteryList(TAirportSlotbattery tAirportSlotbattery)
    {
        return tAirportSlotbatteryMapper.selectTAirportSlotbatteryList(tAirportSlotbattery);
    }

    /**
     * 新增t_airport_slotbattery（机巢电池槽）
     * 
     * @param tAirportSlotbattery t_airport_slotbattery（机巢电池槽）
     * @return 结果
     */
    @Override
    public int insertTAirportSlotbattery(TAirportSlotbattery tAirportSlotbattery)
    {
        return tAirportSlotbatteryMapper.insertTAirportSlotbattery(tAirportSlotbattery);
    }

    /**
     * 修改t_airport_slotbattery（机巢电池槽）
     * 
     * @param tAirportSlotbattery t_airport_slotbattery（机巢电池槽）
     * @return 结果
     */
    @Override
    public int updateTAirportSlotbattery(TAirportSlotbattery tAirportSlotbattery)
    {
        tAirportSlotbattery.setUpdateTime(DateUtils.getNowDate());
        return tAirportSlotbatteryMapper.updateTAirportSlotbattery(tAirportSlotbattery);
    }

    /**
     * 批量删除t_airport_slotbattery（机巢电池槽）
     * 
     * @param slotbatteryIds 需要删除的t_airport_slotbattery（机巢电池槽）ID
     * @return 结果
     */
    @Override
    public int deleteTAirportSlotbatteryByIds(Long[] slotbatteryIds)
    {
        return tAirportSlotbatteryMapper.deleteTAirportSlotbatteryByIds(slotbatteryIds);
    }

    /**
     * 删除t_airport_slotbattery（机巢电池槽）信息
     * 
     * @param slotbatteryId t_airport_slotbattery（机巢电池槽）ID
     * @return 结果
     */
    @Override
    public int deleteTAirportSlotbatteryById(Long slotbatteryId)
    {
        return tAirportSlotbatteryMapper.deleteTAirportSlotbatteryById(slotbatteryId);
    }
}
