package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiAlarmMapper;
import com.senxu.omserver.service.domain.TYuntaiAlarm;
import com.senxu.omserver.service.service.ITYuntaiAlarmService;

/**
 * t_yuntai_alarm（云台抓告警）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiAlarmServiceImpl implements ITYuntaiAlarmService 
{
    @Autowired
    private TYuntaiAlarmMapper tYuntaiAlarmMapper;

    /**
     * 查询t_yuntai_alarm（云台抓告警）
     * 
     * @param id t_yuntai_alarm（云台抓告警）ID
     * @return t_yuntai_alarm（云台抓告警）
     */
    @Override
    public TYuntaiAlarm selectTYuntaiAlarmById(Long id)
    {
        return tYuntaiAlarmMapper.selectTYuntaiAlarmById(id);
    }

    /**
     * 查询t_yuntai_alarm（云台抓告警）列表
     * 
     * @param tYuntaiAlarm t_yuntai_alarm（云台抓告警）
     * @return t_yuntai_alarm（云台抓告警）
     */
    @Override
    public List<TYuntaiAlarm> selectTYuntaiAlarmList(TYuntaiAlarm tYuntaiAlarm)
    {
        return tYuntaiAlarmMapper.selectTYuntaiAlarmList(tYuntaiAlarm);
    }

    /**
     * 查询t_yuntai_alarm（云台抓告警）列表
     *
     * @param tYuntaiAlarm t_yuntai_alarm（云台抓告警）
     * @return t_yuntai_alarm（云台抓告警）
     */
    @Override
    public List<TYuntaiAlarm> selectTYuntaiAlarmLisByDetail(TYuntaiAlarm tYuntaiAlarm)
    {
        return tYuntaiAlarmMapper.selectTYuntaiAlarmListByDetail(tYuntaiAlarm);
    }

    /**
     * 新增t_yuntai_alarm（云台抓告警）
     * 
     * @param tYuntaiAlarm t_yuntai_alarm（云台抓告警）
     * @return 结果
     */
    @Override
    public int insertTYuntaiAlarm(TYuntaiAlarm tYuntaiAlarm)
    {
        return tYuntaiAlarmMapper.insertTYuntaiAlarm(tYuntaiAlarm);
    }

    /**
     * 修改t_yuntai_alarm（云台抓告警）
     * 
     * @param tYuntaiAlarm t_yuntai_alarm（云台抓告警）
     * @return 结果
     */
    @Override
    public int updateTYuntaiAlarm(TYuntaiAlarm tYuntaiAlarm)
    {
        return tYuntaiAlarmMapper.updateTYuntaiAlarm(tYuntaiAlarm);
    }

    /**
     * 批量删除t_yuntai_alarm（云台抓告警）
     * 
     * @param ids 需要删除的t_yuntai_alarm（云台抓告警）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiAlarmByIds(Long[] ids)
    {
        return tYuntaiAlarmMapper.deleteTYuntaiAlarmByIds(ids);
    }

    /**
     * 删除t_yuntai_alarm（云台抓告警）信息
     * 
     * @param id t_yuntai_alarm（云台抓告警）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiAlarmById(Long id)
    {
        return tYuntaiAlarmMapper.deleteTYuntaiAlarmById(id);
    }
}
