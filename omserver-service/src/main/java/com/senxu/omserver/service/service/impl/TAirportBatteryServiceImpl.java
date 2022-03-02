package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TAirportBatteryMapper;
import com.senxu.omserver.service.domain.TAirportBattery;
import com.senxu.omserver.service.service.ITAirportBatteryService;

/**
 * t_airport_battery（机巢电池）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TAirportBatteryServiceImpl implements ITAirportBatteryService 
{
    @Autowired
    private TAirportBatteryMapper tAirportBatteryMapper;

    /**
     * 查询t_airport_battery（机巢电池）
     * 
     * @param batteryId t_airport_battery（机巢电池）ID
     * @return t_airport_battery（机巢电池）
     */
    @Override
    public TAirportBattery selectTAirportBatteryById(Long batteryId)
    {
        return tAirportBatteryMapper.selectTAirportBatteryById(batteryId);
    }

    /**
     * 查询t_airport_battery（机巢电池）列表
     * 
     * @param tAirportBattery t_airport_battery（机巢电池）
     * @return t_airport_battery（机巢电池）
     */
    @Override
    public List<TAirportBattery> selectTAirportBatteryList(TAirportBattery tAirportBattery)
    {
        return tAirportBatteryMapper.selectTAirportBatteryList(tAirportBattery);
    }

    /**
     * 新增t_airport_battery（机巢电池）
     * 
     * @param tAirportBattery t_airport_battery（机巢电池）
     * @return 结果
     */
    @Override
    public int insertTAirportBattery(TAirportBattery tAirportBattery)
    {
        return tAirportBatteryMapper.insertTAirportBattery(tAirportBattery);
    }

    /**
     * 修改t_airport_battery（机巢电池）
     * 
     * @param tAirportBattery t_airport_battery（机巢电池）
     * @return 结果
     */
    @Override
    public int updateTAirportBattery(TAirportBattery tAirportBattery)
    {
        tAirportBattery.setUpdateTime(DateUtils.getNowDate());
        return tAirportBatteryMapper.updateTAirportBattery(tAirportBattery);
    }

    /**
     * 批量删除t_airport_battery（机巢电池）
     * 
     * @param batteryIds 需要删除的t_airport_battery（机巢电池）ID
     * @return 结果
     */
    @Override
    public int deleteTAirportBatteryByIds(Long[] batteryIds)
    {
        return tAirportBatteryMapper.deleteTAirportBatteryByIds(batteryIds);
    }

    /**
     * 删除t_airport_battery（机巢电池）信息
     * 
     * @param batteryId t_airport_battery（机巢电池）ID
     * @return 结果
     */
    @Override
    public int deleteTAirportBatteryById(Long batteryId)
    {
        return tAirportBatteryMapper.deleteTAirportBatteryById(batteryId);
    }
}
