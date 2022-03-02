package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiMountainFireAlarm;

/**
 * 山火告警Service接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface ITYuntaiMountainFireAlarmService 
{
    /**
     * 查询山火告警
     * 
     * @param id 山火告警ID
     * @return 山火告警
     */
    public TYuntaiMountainFireAlarm selectTYuntaiMountainFireAlarmById(Long id);

    /**
     * 查询山火告警列表
     * 
     * @param tYuntaiMountainFireAlarm 山火告警
     * @return 山火告警集合
     */
    public List<TYuntaiMountainFireAlarm> selectTYuntaiMountainFireAlarmList(TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm);

    /**
     * 新增山火告警
     * 
     * @param tYuntaiMountainFireAlarm 山火告警
     * @return 结果
     */
    public int insertTYuntaiMountainFireAlarm(TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm);

    /**
     * 修改山火告警
     * 
     * @param tYuntaiMountainFireAlarm 山火告警
     * @return 结果
     */
    public int updateTYuntaiMountainFireAlarm(TYuntaiMountainFireAlarm tYuntaiMountainFireAlarm);

    /**
     * 批量删除山火告警
     * 
     * @param ids 需要删除的山火告警ID
     * @return 结果
     */
    public int deleteTYuntaiMountainFireAlarmByIds(Long[] ids);

    /**
     * 删除山火告警信息
     * 
     * @param id 山火告警ID
     * @return 结果
     */
    public int deleteTYuntaiMountainFireAlarmById(Long id);
}
