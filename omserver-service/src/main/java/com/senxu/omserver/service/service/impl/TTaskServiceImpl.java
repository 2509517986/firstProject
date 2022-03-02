package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TTaskMapper;
import com.senxu.omserver.service.domain.TTask;
import com.senxu.omserver.service.service.ITTaskService;

/**
 * t_task（任务信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TTaskServiceImpl implements ITTaskService 
{
    @Autowired
    private TTaskMapper tTaskMapper;

    /**
     * 查询t_task（任务信息）
     * 
     * @param taskId t_task（任务信息）ID
     * @return t_task（任务信息）
     */
    @Override
    public TTask selectTTaskById(Long taskId)
    {
        return tTaskMapper.selectTTaskById(taskId);
    }

    /**
     * 查询t_task（任务信息）列表
     * 
     * @param tTask t_task（任务信息）
     * @return t_task（任务信息）
     */
    @Override
    public List<TTask> selectTTaskList(TTask tTask)
    {
        return tTaskMapper.selectTTaskList(tTask);
    }

    /**
     * 新增t_task（任务信息）
     * 
     * @param tTask t_task（任务信息）
     * @return 结果
     */
    @Override
    public int insertTTask(TTask tTask)
    {
        tTask.setUpdateTime(DateUtils.getNowDate());
        return tTaskMapper.insertTTask(tTask);
    }

    /**
     * 修改t_task（任务信息）
     * 
     * @param tTask t_task（任务信息）
     * @return 结果
     */
    @Override
    public int updateTTask(TTask tTask)
    {
        tTask.setUpdateTime(DateUtils.getNowDate());
        return tTaskMapper.updateTTask(tTask);
    }

    /**
     * 批量删除t_task（任务信息）
     * 
     * @param taskIds 需要删除的t_task（任务信息）ID
     * @return 结果
     */
    @Override
    public int deleteTTaskByIds(Long[] taskIds)
    {
        return tTaskMapper.deleteTTaskByIds(taskIds);
    }

    /**
     * 删除t_task（任务信息）信息
     * 
     * @param taskId t_task（任务信息）ID
     * @return 结果
     */
    @Override
    public int deleteTTaskById(Long taskId)
    {
        return tTaskMapper.deleteTTaskById(taskId);
    }
}
