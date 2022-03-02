package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TAirportSlotbattery;
import org.springframework.stereotype.Repository;

/**
 * t_airport_slotbattery（机巢电池槽）Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Repository
public interface TAirportSlotbatteryMapper 
{
    /**
     * 查询t_airport_slotbattery（机巢电池槽）
     * 
     * @param slotbatteryId t_airport_slotbattery（机巢电池槽）ID
     * @return t_airport_slotbattery（机巢电池槽）
     */
    public TAirportSlotbattery selectTAirportSlotbatteryById(Long slotbatteryId);

    /**
     * 查询t_airport_slotbattery（机巢电池槽）列表
     * 
     * @param tAirportSlotbattery t_airport_slotbattery（机巢电池槽）
     * @return t_airport_slotbattery（机巢电池槽）集合
     */
    public List<TAirportSlotbattery> selectTAirportSlotbatteryList(TAirportSlotbattery tAirportSlotbattery);

    /**
     * 新增t_airport_slotbattery（机巢电池槽）
     * 
     * @param tAirportSlotbattery t_airport_slotbattery（机巢电池槽）
     * @return 结果
     */
    public int insertTAirportSlotbattery(TAirportSlotbattery tAirportSlotbattery);

    /**
     * 修改t_airport_slotbattery（机巢电池槽）
     * 
     * @param tAirportSlotbattery t_airport_slotbattery（机巢电池槽）
     * @return 结果
     */
    public int updateTAirportSlotbattery(TAirportSlotbattery tAirportSlotbattery);

    /**
     * 删除t_airport_slotbattery（机巢电池槽）
     * 
     * @param slotbatteryId t_airport_slotbattery（机巢电池槽）ID
     * @return 结果
     */
    public int deleteTAirportSlotbatteryById(Long slotbatteryId);

    /**
     * 批量删除t_airport_slotbattery（机巢电池槽）
     * 
     * @param slotbatteryIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTAirportSlotbatteryByIds(Long[] slotbatteryIds);

    /**
     * 删除某一机巢下所有t_airport_slotbattery（机巢电池槽）
     *
     * @param airportCode 机巢编号
     * @return 结果
     */
    public int deleteTAirportSlotbatteryByAirportCode(String airportCode);
}
