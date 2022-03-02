package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TTaskHistoryMapper;
import com.senxu.omserver.service.domain.TTaskHistory;
import com.senxu.omserver.service.service.ITTaskHistoryService;

/**
 * t_task_history（历史巡检记录）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TTaskHistoryServiceImpl implements ITTaskHistoryService 
{
    @Autowired
    private TTaskHistoryMapper tTaskHistoryMapper;

    /**
     * 查询t_task_history（历史巡检记录）
     * 
     * @param taskhId t_task_history（历史巡检记录）ID
     * @return t_task_history（历史巡检记录）
     */
    @Override
    public TTaskHistory selectTTaskHistoryById(Long taskhId)
    {
        return tTaskHistoryMapper.selectTTaskHistoryById(taskhId);
    }

    /**
     * 查询t_task_history（历史巡检记录）列表
     * 
     * @param tTaskHistory t_task_history（历史巡检记录）
     * @return t_task_history（历史巡检记录）
     */
    @Override
    public List<TTaskHistory> selectTTaskHistoryList(TTaskHistory tTaskHistory)
    {
        return tTaskHistoryMapper.selectTTaskHistoryList(tTaskHistory);
    }

    /**
     * 新增t_task_history（历史巡检记录）
     * 
     * @param tTaskHistory t_task_history（历史巡检记录）
     * @return 结果
     */
    @Override
    public int insertTTaskHistory(TTaskHistory tTaskHistory)
    {
        return tTaskHistoryMapper.insertTTaskHistory(tTaskHistory);
    }

    /**
     * 修改t_task_history（历史巡检记录）
     * 
     * @param tTaskHistory t_task_history（历史巡检记录）
     * @return 结果
     */
    @Override
    public int updateTTaskHistory(TTaskHistory tTaskHistory)
    {
        tTaskHistory.setUpdateTime(DateUtils.getNowDate());
        return tTaskHistoryMapper.updateTTaskHistory(tTaskHistory);
    }

    /**
     * 批量删除t_task_history（历史巡检记录）
     * 
     * @param taskhIds 需要删除的t_task_history（历史巡检记录）ID
     * @return 结果
     */
    @Override
    public int deleteTTaskHistoryByIds(Long[] taskhIds)
    {
        return tTaskHistoryMapper.deleteTTaskHistoryByIds(taskhIds);
    }

    /**
     * 删除t_task_history（历史巡检记录）信息
     * 
     * @param taskhId t_task_history（历史巡检记录）ID
     * @return 结果
     */
    @Override
    public int deleteTTaskHistoryById(Long taskhId)
    {
        return tTaskHistoryMapper.deleteTTaskHistoryById(taskhId);
    }
}
