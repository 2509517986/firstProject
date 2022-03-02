package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TLog;

/**
 * t_log（日志）Service接口
 * 
 * @author ruoyi
 * @date 2021-07-23
 */
public interface ITLogService 
{
    /**
     * 查询t_log（日志）
     * 
     * @param id t_log（日志）ID
     * @return t_log（日志）
     */
    public TLog selectTLogById(Long id);

    /**
     * 查询t_log（日志）列表
     * 
     * @param tLog t_log（日志）
     * @return t_log（日志）集合
     */
    public List<TLog> selectTLogList(TLog tLog);

    /**
     * 新增t_log（日志）
     * 
     * @param tLog t_log（日志）
     * @return 结果
     */
    public int insertTLog(TLog tLog);

    /**
     * 修改t_log（日志）
     * 
     * @param tLog t_log（日志）
     * @return 结果
     */
    public int updateTLog(TLog tLog);

    /**
     * 批量删除t_log（日志）
     * 
     * @param ids 需要删除的t_log（日志）ID
     * @return 结果
     */
    public int deleteTLogByIds(Long[] ids);

    /**
     * 删除t_log（日志）信息
     * 
     * @param id t_log（日志）ID
     * @return 结果
     */
    public int deleteTLogById(Long id);
}
