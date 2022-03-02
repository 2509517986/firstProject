package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiMountainFireAlarmMapper;
import com.senxu.omserver.service.domain.TYuntaiMountainFireAlarm;
import com.senxu.omserver.service.service.ITYuntaiMountainFireAlarmService;

/**
 * 山火告警Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TYuntaiMountainFireAlarmServiceImpl implements ITYuntaiMountainFireAlarmService 
{
    @Autowired
    private TYuntaiMountainFireAlarmMapper tYuntaiMountainFireAlarmMapper;

    /**
     * 查询山火告警
     * 
     * @param id 山火告警ID
     * @return 山火告警
     */
    @Override
    public TYuntaiMountainFireAlarm selectTYuntaiMountainFireAlarmById(Long id)
    {
        return tYuntaiMountainFireAlarmMapper.selectTYuntaiMountainFireAlarmById(id);
    }

    /**
     * 查询山火告警列表
     * 
     * @param tYuntaiMountainFireAlarm 山火告警
     * @return 山火告警
     */
    @Override
    public List<TYuntaiMountainFireAlarm> selectTYuntaiMountainFireAlarmList(TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm)
    {
        return tYuntaiMountainFireAlarmMapper.selectTYuntaiMountainFireAlarmList(tYuntaiMountainFireAlarm);
    }

    /**
     * 新增山火告警
     * 
     * @param tYuntaiMountainFireAlarm 山火告警
     * @return 结果
     */
    @Override
    public int insertTYuntaiMountainFireAlarm(TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm)
    {
        return tYuntaiMountainFireAlarmMapper.insertTYuntaiMountainFireAlarm(tYuntaiMountainFireAlarm);
    }

    /**
     * 修改山火告警
     * 
     * @param tYuntaiMountainFireAlarm 山火告警
     * @return 结果
     */
    @Override
    public int updateTYuntaiMountainFireAlarm(TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm)
    {
        return tYuntaiMountainFireAlarmMapper.updateTYuntaiMountainFireAlarm(tYuntaiMountainFireAlarm);
    }

    /**
     * 批量删除山火告警
     * 
     * @param ids 需要删除的山火告警ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiMountainFireAlarmByIds(Long[] ids)
    {
        return tYuntaiMountainFireAlarmMapper.deleteTYuntaiMountainFireAlarmByIds(ids);
    }

    /**
     * 删除山火告警信息
     * 
     * @param id 山火告警ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiMountainFireAlarmById(Long id)
    {
        return tYuntaiMountainFireAlarmMapper.deleteTYuntaiMountainFireAlarmById(id);
    }
}
