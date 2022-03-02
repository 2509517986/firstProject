package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiLog;

/**
 * t_yuntai_log（云台设备事件）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface TYuntaiLogMapper 
{
    /**
     * 查询t_yuntai_log（云台设备事件）
     * 
     * @param id t_yuntai_log（云台设备事件）ID
     * @return t_yuntai_log（云台设备事件）
     */
    public TYuntaiLog selectTYuntaiLogById(Long id);

    /**
     * 查询t_yuntai_log（云台设备事件）列表
     * 
     * @param tYuntaiLog t_yuntai_log（云台设备事件）
     * @return t_yuntai_log（云台设备事件）集合
     */
    public List<TYuntaiLog> selectTYuntaiLogList(TYuntaiLog tYuntaiLog);

    /**
     * 新增t_yuntai_log（云台设备事件）
     * 
     * @param tYuntaiLog t_yuntai_log（云台设备事件）
     * @return 结果
     */
    public int insertTYuntaiLog(TYuntaiLog tYuntaiLog);

    /**
     * 修改t_yuntai_log（云台设备事件）
     * 
     * @param tYuntaiLog t_yuntai_log（云台设备事件）
     * @return 结果
     */
    public int updateTYuntaiLog(TYuntaiLog tYuntaiLog);

    /**
     * 删除t_yuntai_log（云台设备事件）
     * 
     * @param id t_yuntai_log（云台设备事件）ID
     * @return 结果
     */
    public int deleteTYuntaiLogById(Long id);

    /**
     * 批量删除t_yuntai_log（云台设备事件）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiLogByIds(Long[] ids);
}
