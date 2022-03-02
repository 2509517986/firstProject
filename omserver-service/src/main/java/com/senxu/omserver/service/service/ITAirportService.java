package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TAirport;

/**
 * t_airport（机巢信息）Service接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public interface ITAirportService 
{
    /**
     * 查询t_airport（机巢信息）
     * 
     * @param airportId t_airport（机巢信息）ID
     * @return t_airport（机巢信息）
     */
    public TAirport selectTAirportById(Long airportId);

    /**
     * 查询t_airport（机巢信息）列表
     * 
     * @param tAirport t_airport（机巢信息）
     * @return t_airport（机巢信息）集合
     */
    public List<TAirport> selectTAirportList(TAirport tAirport);

    /**
     * 新增t_airport（机巢信息）
     * 
     * @param tAirport t_airport（机巢信息）
     * @return 结果
     */
    public int insertTAirport(TAirport tAirport);

    /**
     * 修改t_airport（机巢信息）
     * 
     * @param tAirport t_airport（机巢信息）
     * @return 结果
     */
    public int updateTAirport(TAirport tAirport);

    /**
     * 批量删除t_airport（机巢信息）
     * 
     * @param airportIds 需要删除的t_airport（机巢信息）ID
     * @return 结果
     */
    public int deleteTAirportByIds(Long[] airportIds);

    /**
     * 删除t_airport（机巢信息）信息
     * 
     * @param airportId t_airport（机巢信息）ID
     * @return 结果
     */
    public int deleteTAirportById(Long airportId);
}
