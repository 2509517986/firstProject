package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TAirport;
import org.springframework.stereotype.Repository;

/**
 * t_airport（机巢信息）Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Repository
public interface TAirportMapper 
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
     * 修改t_airport（机巢信息）
     *
     * @param tAirport t_airport（机巢信息）
     * @return 结果
     */
    public int updateTAirportByCode(TAirport tAirport);

    /**
     * 删除t_airport（机巢信息）
     * 
     * @param airportId t_airport（机巢信息）ID
     * @return 结果
     */
    public int deleteTAirportById(Long airportId);

    /**
     * 批量删除t_airport（机巢信息）
     * 
     * @param airportIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTAirportByIds(Long[] airportIds);
    /**
     * 根据机巢编号查机巢id
     *
     * @param airportCode 机巢编号
     * @return 结果
     */
    public Long selectAirportIdByAirportCode(String airportCode);
}
