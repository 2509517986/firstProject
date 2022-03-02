package com.senxu.omserver.service.service;

import java.util.List;

import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.service.domain.TInspectionAlarm;
import com.senxu.omserver.service.domain.TInspectionReport;

/**
 * t_inspection_alarm（巡检告警记录）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-10
 */
public interface ITInspectionAlarmService 
{
    /**
     * 查询t_inspection_alarm（巡检告警记录）
     * 
     * @param id t_inspection_alarm（巡检告警记录）ID
     * @return t_inspection_alarm（巡检告警记录）
     */
    public TInspectionAlarm selectTInspectionAlarmById(Long id);

    /**
     * 查询t_inspection_alarm（巡检告警记录）列表
     * 
     * @param tInspectionAlarm t_inspection_alarm（巡检告警记录）
     * @return t_inspection_alarm（巡检告警记录）集合
     */
    public List<TInspectionAlarm> selectTInspectionAlarmList(TInspectionAlarm tInspectionAlarm);



    /**
     * 通过日期及杆塔id获取云台告警列表
     *
     * @param lineIds 杆塔Id
     * @param reportId 巡检报告Id
     * @param sDate 日期 yyyy-MM-dd
     * @return t_inspection_alarm（巡检告警记录）集合
     */
    public List<TInspectionAlarm> findReportByDate(String lineIds,Long reportId,String sDate);

    /**
     * 新增t_inspection_alarm（巡检告警记录）
     * 
     * @param tInspectionAlarm t_inspection_alarm（巡检告警记录）
     * @return 结果
     */
    public int insertTInspectionAlarm(TInspectionAlarm tInspectionAlarm);

    /**
     * 修改t_inspection_alarm（巡检告警记录）
     * 
     * @param tInspectionAlarm t_inspection_alarm（巡检告警记录）
     * @return 结果
     */
    public int updateTInspectionAlarm(TInspectionAlarm tInspectionAlarm);

    /**
     * 批量删除t_inspection_alarm（巡检告警记录）
     * 
     * @param ids 需要删除的t_inspection_alarm（巡检告警记录）ID
     * @return 结果
     */
    public int deleteTInspectionAlarmByIds(Long[] ids);

    /**
     * 删除t_inspection_alarm（巡检告警记录）信息
     * 
     * @param id t_inspection_alarm（巡检告警记录）ID
     * @return 结果
     */
    public int deleteTInspectionAlarmById(Long id);
}
