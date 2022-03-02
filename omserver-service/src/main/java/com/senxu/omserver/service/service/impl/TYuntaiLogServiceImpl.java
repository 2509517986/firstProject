package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiLogMapper;
import com.senxu.omserver.service.domain.TYuntaiLog;
import com.senxu.omserver.service.service.ITYuntaiLogService;

/**
 * t_yuntai_log（云台设备事件）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiLogServiceImpl implements ITYuntaiLogService 
{
    @Autowired
    private TYuntaiLogMapper tYuntaiLogMapper;

    /**
     * 查询t_yuntai_log（云台设备事件）
     * 
     * @param id t_yuntai_log（云台设备事件）ID
     * @return t_yuntai_log（云台设备事件）
     */
    @Override
    public TYuntaiLog selectTYuntaiLogById(Long id)
    {
        return tYuntaiLogMapper.selectTYuntaiLogById(id);
    }

    /**
     * 查询t_yuntai_log（云台设备事件）列表
     * 
     * @param tYuntaiLog t_yuntai_log（云台设备事件）
     * @return t_yuntai_log（云台设备事件）
     */
    @Override
    public List<TYuntaiLog> selectTYuntaiLogList(TYuntaiLog tYuntaiLog)
    {
        return tYuntaiLogMapper.selectTYuntaiLogList(tYuntaiLog);
    }

    /**
     * 新增t_yuntai_log（云台设备事件）
     * 
     * @param tYuntaiLog t_yuntai_log（云台设备事件）
     * @return 结果
     */
    @Override
    public int insertTYuntaiLog(TYuntaiLog tYuntaiLog)
    {
        return tYuntaiLogMapper.insertTYuntaiLog(tYuntaiLog);
    }

    /**
     * 修改t_yuntai_log（云台设备事件）
     * 
     * @param tYuntaiLog t_yuntai_log（云台设备事件）
     * @return 结果
     */
    @Override
    public int updateTYuntaiLog(TYuntaiLog tYuntaiLog)
    {
        return tYuntaiLogMapper.updateTYuntaiLog(tYuntaiLog);
    }

    /**
     * 批量删除t_yuntai_log（云台设备事件）
     * 
     * @param ids 需要删除的t_yuntai_log（云台设备事件）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiLogByIds(Long[] ids)
    {
        return tYuntaiLogMapper.deleteTYuntaiLogByIds(ids);
    }

    /**
     * 删除t_yuntai_log（云台设备事件）信息
     * 
     * @param id t_yuntai_log（云台设备事件）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiLogById(Long id)
    {
        return tYuntaiLogMapper.deleteTYuntaiLogById(id);
    }
}
