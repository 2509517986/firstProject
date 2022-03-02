package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TAirportBattery;
import org.springframework.stereotype.Repository;

/**
 * t_airport_battery（机巢电池）Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Repository
public interface TAirportBatteryMapper 
{
    /**
     * 查询t_airport_battery（机巢电池）
     * 
     * @param batteryId t_airport_battery（机巢电池）ID
     * @return t_airport_battery（机巢电池）
     */
    public TAirportBattery selectTAirportBatteryById(Long batteryId);

    /**
     * 查询t_airport_battery（机巢电池）列表
     * 
     * @param tAirportBattery t_airport_battery（机巢电池）
     * @return t_airport_battery（机巢电池）集合
     */
    public List<TAirportBattery> selectTAirportBatteryList(TAirportBattery tAirportBattery);

    /**
     * 新增t_airport_battery（机巢电池）
     * 
     * @param tAirportBattery t_airport_battery（机巢电池）
     * @return 结果
     */
    public int insertTAirportBattery(TAirportBattery tAirportBattery);

    /**
     * 修改t_airport_battery（机巢电池）
     * 
     * @param tAirportBattery t_airport_battery（机巢电池）
     * @return 结果
     */
    public int updateTAirportBattery(TAirportBattery tAirportBattery);

    /**
     * 删除t_airport_battery（机巢电池）
     * 
     * @param batteryId t_airport_battery（机巢电池）ID
     * @return 结果
     */
    public int deleteTAirportBatteryById(Long batteryId);

    /**
     * 批量删除t_airport_battery（机巢电池）
     * 
     * @param batteryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTAirportBatteryByIds(Long[] batteryIds);

    /**
     * 删除某一机巢下所有t_airport_battery（机巢电池）
     *
     * @param airportCode 机巢编号
     * @return 结果
     */
    public int deleteTAirportBatteryByAirportCode(String airportCode);
}
