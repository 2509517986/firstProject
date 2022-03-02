package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TInspectionAlarm;

/**
 * t_inspection_alarm（巡检告警记录）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-10
 */
public interface TInspectionAlarmMapper 
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
     * 删除t_inspection_alarm（巡检告警记录）
     * 
     * @param id t_inspection_alarm（巡检告警记录）ID
     * @return 结果
     */
    public int deleteTInspectionAlarmById(Long id);

    /**
     * 批量删除t_inspection_alarm（巡检告警记录）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTInspectionAlarmByIds(Long[] ids);
}
