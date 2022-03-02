package com.senxu.omserver.service.service;

import java.util.List;

import com.senxu.omserver.service.domain.TInspectionYuntai;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiStatistic;

/**
 * t_inspection_yuntai（巡检云台记录）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface ITInspectionYuntaiService 
{
    /**
     * 查询t_inspection_yuntai（巡检云台记录）
     * 
     * @param id t_inspection_yuntai（巡检云台记录）ID
     * @return t_inspection_yuntai（巡检云台记录）
     */
    public TInspectionYuntai selectTInspectionYuntaiById(Long id);


    /**
     * 通过日期线路id获取云台列表
     * @param lineIds 线路Id
     * @param reportId  巡检报告Id
     * @return
     */
    public List<TInspectionYuntai> findReportByDate(String lineIds, Long reportId,String towerIds);


    /**
     * 通过日期、线路id、获取统计所管辖云台设备
     * @param lineIds 线路Id
     * @param reportId  巡检报告Id
     * @return
     */
    public List<TInspectionYuntaiStatistic> yuntaiStatisticsByDate(String lineIds, Long reportId);

    /**
     * 通过日期、线路id、获取统计所管辖云台设备
     * @param lineIds 线路Id
     * @param reportId  巡检报告Id
     * @return
     */
    public List<TInspectionYuntaiHiddenDanger> yuntaiHiddenDangerByDate(String lineIds, Long reportId);

    /**
     * 查询t_inspection_yuntai（巡检云台记录）列表
     * 
     * @param tInspectionYuntai
     * @return t_inspection_yuntai（巡检云台记录）集合
     */
    public List<TInspectionYuntai> selectTInspectionYuntaiList(TInspectionYuntai tInspectionYuntai);

    /**
     * 新增t_inspection_yuntai（巡检云台记录）
     * 
     * @param tInspectionYuntai t_inspection_yuntai（巡检云台记录）
     * @return 结果
     */
    public int insertTInspectionYuntai(TInspectionYuntai tInspectionYuntai);

    /**
     * 修改t_inspection_yuntai（巡检云台记录）
     * 
     * @param tInspectionYuntai t_inspection_yuntai（巡检云台记录）
     * @return 结果
     */
    public int updateTInspectionYuntai(TInspectionYuntai tInspectionYuntai);

    /**
     * 批量删除t_inspection_yuntai（巡检云台记录）
     * 
     * @param ids 需要删除的t_inspection_yuntai（巡检云台记录）ID
     * @return 结果
     */
    public int deleteTInspectionYuntaiByIds(Long[] ids);

    /**
     * 删除t_inspection_yuntai（巡检云台记录）信息
     * 
     * @param id t_inspection_yuntai（巡检云台记录）ID
     * @return 结果
     */
    public int deleteTInspectionYuntaiById(Long id);
}
