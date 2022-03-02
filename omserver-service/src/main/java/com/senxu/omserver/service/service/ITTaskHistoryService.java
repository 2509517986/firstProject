package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TTaskHistory;

/**
 * t_task_history（历史巡检记录）Service接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public interface ITTaskHistoryService 
{
    /**
     * 查询t_task_history（历史巡检记录）
     * 
     * @param taskhId t_task_history（历史巡检记录）ID
     * @return t_task_history（历史巡检记录）
     */
    public TTaskHistory selectTTaskHistoryById(Long taskhId);

    /**
     * 查询t_task_history（历史巡检记录）列表
     * 
     * @param tTaskHistory t_task_history（历史巡检记录）
     * @return t_task_history（历史巡检记录）集合
     */
    public List<TTaskHistory> selectTTaskHistoryList(TTaskHistory tTaskHistory);

    /**
     * 新增t_task_history（历史巡检记录）
     * 
     * @param tTaskHistory t_task_history（历史巡检记录）
     * @return 结果
     */
    public int insertTTaskHistory(TTaskHistory tTaskHistory);

    /**
     * 修改t_task_history（历史巡检记录）
     * 
     * @param tTaskHistory t_task_history（历史巡检记录）
     * @return 结果
     */
    public int updateTTaskHistory(TTaskHistory tTaskHistory);

    /**
     * 批量删除t_task_history（历史巡检记录）
     * 
     * @param taskhIds 需要删除的t_task_history（历史巡检记录）ID
     * @return 结果
     */
    public int deleteTTaskHistoryByIds(Long[] taskhIds);

    /**
     * 删除t_task_history（历史巡检记录）信息
     * 
     * @param taskhId t_task_history（历史巡检记录）ID
     * @return 结果
     */
    public int deleteTTaskHistoryById(Long taskhId);
}
