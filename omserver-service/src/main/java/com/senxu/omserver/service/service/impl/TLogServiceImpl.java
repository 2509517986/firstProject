package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TLogMapper;
import com.senxu.omserver.service.domain.TLog;
import com.senxu.omserver.service.service.ITLogService;

/**
 * t_log（日志）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-07-23
 */
@Service
public class TLogServiceImpl implements ITLogService 
{
    @Autowired
    private TLogMapper tLogMapper;

    /**
     * 查询t_log（日志）
     * 
     * @param id t_log（日志）ID
     * @return t_log（日志）
     */
    @Override
    public TLog selectTLogById(Long id)
    {
        return tLogMapper.selectTLogById(id);
    }

    /**
     * 查询t_log（日志）列表
     * 
     * @param tLog t_log（日志）
     * @return t_log（日志）
     */
    @Override
    public List<TLog> selectTLogList(TLog tLog)
    {
        return tLogMapper.selectTLogList(tLog);
    }

    /**
     * 新增t_log（日志）
     * 
     * @param tLog t_log（日志）
     * @return 结果
     */
    @Override
    public int insertTLog(TLog tLog)
    {
        return tLogMapper.insertTLog(tLog);
    }

    /**
     * 修改t_log（日志）
     * 
     * @param tLog t_log（日志）
     * @return 结果
     */
    @Override
    public int updateTLog(TLog tLog)
    {
        return tLogMapper.updateTLog(tLog);
    }

    /**
     * 批量删除t_log（日志）
     * 
     * @param ids 需要删除的t_log（日志）ID
     * @return 结果
     */
    @Override
    public int deleteTLogByIds(Long[] ids)
    {
        return tLogMapper.deleteTLogByIds(ids);
    }

    /**
     * 删除t_log（日志）信息
     * 
     * @param id t_log（日志）ID
     * @return 结果
     */
    @Override
    public int deleteTLogById(Long id)
    {
        return tLogMapper.deleteTLogById(id);
    }
}
