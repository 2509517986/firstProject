package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TAirportMapper;
import com.senxu.omserver.service.domain.TAirport;
import com.senxu.omserver.service.service.ITAirportService;

/**
 * t_airport（机巢信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TAirportServiceImpl implements ITAirportService 
{
    @Autowired
    private TAirportMapper tAirportMapper;

    /**
     * 查询t_airport（机巢信息）
     * 
     * @param airportId t_airport（机巢信息）ID
     * @return t_airport（机巢信息）
     */
    @Override
    public TAirport selectTAirportById(Long airportId)
    {
        return tAirportMapper.selectTAirportById(airportId);
    }

    /**
     * 查询t_airport（机巢信息）列表
     * 
     * @param tAirport t_airport（机巢信息）
     * @return t_airport（机巢信息）
     */
    @Override
    public List<TAirport> selectTAirportList(TAirport tAirport)
    {
        return tAirportMapper.selectTAirportList(tAirport);
    }

    /**
     * 新增t_airport（机巢信息）
     * 
     * @param tAirport t_airport（机巢信息）
     * @return 结果
     */
    @Override
    public int insertTAirport(TAirport tAirport)
    {
        return tAirportMapper.insertTAirport(tAirport);
    }

    /**
     * 修改t_airport（机巢信息）
     * 
     * @param tAirport t_airport（机巢信息）
     * @return 结果
     */
    @Override
    public int updateTAirport(TAirport tAirport)
    {
        tAirport.setUpdateTime(DateUtils.getNowDate());
        return tAirportMapper.updateTAirport(tAirport);
    }

    /**
     * 批量删除t_airport（机巢信息）
     * 
     * @param airportIds 需要删除的t_airport（机巢信息）ID
     * @return 结果
     */
    @Override
    public int deleteTAirportByIds(Long[] airportIds)
    {
        return tAirportMapper.deleteTAirportByIds(airportIds);
    }

    /**
     * 删除t_airport（机巢信息）信息
     * 
     * @param airportId t_airport（机巢信息）ID
     * @return 结果
     */
    @Override
    public int deleteTAirportById(Long airportId)
    {
        return tAirportMapper.deleteTAirportById(airportId);
    }
}
