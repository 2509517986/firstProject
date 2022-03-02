package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TTask;

/**
 * t_task（任务信息）Service接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public interface ITTaskService 
{
    /**
     * 查询t_task（任务信息）
     * 
     * @param taskId t_task（任务信息）ID
     * @return t_task（任务信息）
     */
    public TTask selectTTaskById(Long taskId);

    /**
     * 查询t_task（任务信息）列表
     * 
     * @param tTask t_task（任务信息）
     * @return t_task（任务信息）集合
     */
    public List<TTask> selectTTaskList(TTask tTask);

    /**
     * 新增t_task（任务信息）
     * 
     * @param tTask t_task（任务信息）
     * @return 结果
     */
    public int insertTTask(TTask tTask);

    /**
     * 修改t_task（任务信息）
     * 
     * @param tTask t_task（任务信息）
     * @return 结果
     */
    public int updateTTask(TTask tTask);

    /**
     * 批量删除t_task（任务信息）
     * 
     * @param taskIds 需要删除的t_task（任务信息）ID
     * @return 结果
     */
    public int deleteTTaskByIds(Long[] taskIds);

    /**
     * 删除t_task（任务信息）信息
     * 
     * @param taskId t_task（任务信息）ID
     * @return 结果
     */
    public int deleteTTaskById(Long taskId);
}
