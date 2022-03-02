package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiAlarm;

/**
 * t_yuntai_alarm（云台抓告警）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface TYuntaiAlarmMapper 
{
    /**
     * 查询t_yuntai_alarm（云台抓告警）
     * 
     * @param id t_yuntai_alarm（云台抓告警）ID
     * @return t_yuntai_alarm（云台抓告警）
     */
    public TYuntaiAlarm selectTYuntaiAlarmById(Long id);

    /**
     * 查询t_yuntai_alarm（云台抓告警）列表
     * 
     * @param tYuntaiAlarm t_yuntai_alarm（云台抓告警）
     * @return t_yuntai_alarm（云台抓告警）集合
     */
    public List<TYuntaiAlarm> selectTYuntaiAlarmList(TYuntaiAlarm tYuntaiAlarm);

    /**
     * 查询t_yuntai_alarm（云台抓告警）列表
     *
     * @param tYuntaiAlarm t_yuntai_alarm（云台抓告警）
     * @return t_yuntai_alarm（云台抓告警）集合
     */
    public List<TYuntaiAlarm> selectTYuntaiAlarmListByDetail(TYuntaiAlarm tYuntaiAlarm);

    /**
     * 查询t_yuntai_alarm（云台抓告警）列表，通过传入：线路id、巡检日期
     *
     * @param tYuntaiAlarm
     * @return t_yuntai_alarm（云台抓告警）集合
     */
    public List<TYuntaiAlarm> findReportByDate(TYuntaiAlarm tYuntaiAlarm);


    /**
     * 新增t_yuntai_alarm（云台抓告警）
     * 
     * @param tYuntaiAlarm t_yuntai_alarm（云台抓告警）
     * @return 结果
     */
    public int insertTYuntaiAlarm(TYuntaiAlarm tYuntaiAlarm);

    /**
     * 修改t_yuntai_alarm（云台抓告警）
     * 
     * @param tYuntaiAlarm t_yuntai_alarm（云台抓告警）
     * @return 结果
     */
    public int updateTYuntaiAlarm(TYuntaiAlarm tYuntaiAlarm);

    /**
     * 删除t_yuntai_alarm（云台抓告警）
     * 
     * @param id t_yuntai_alarm（云台抓告警）ID
     * @return 结果
     */
    public int deleteTYuntaiAlarmById(Long id);

    /**
     * 批量删除t_yuntai_alarm（云台抓告警）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiAlarmByIds(Long[] ids);
}
