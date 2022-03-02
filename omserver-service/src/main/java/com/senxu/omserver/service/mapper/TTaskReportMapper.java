package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TTaskReport;

/**
 * t_task_report（无人机任务巡检报告）Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public interface TTaskReportMapper 
{
    /**
     * 查询t_task_report（无人机任务巡检报告）
     * 
     * @param reportId t_task_report（无人机任务巡检报告）ID
     * @return t_task_report（无人机任务巡检报告）
     */
    public TTaskReport selectTTaskReportById(Long reportId);

    /**
     * 查询t_task_report（无人机任务巡检报告）列表
     * 
     * @param tTaskReport t_task_report（无人机任务巡检报告）
     * @return t_task_report（无人机任务巡检报告）集合
     */
    public List<TTaskReport> selectTTaskReportList(TTaskReport tTaskReport);

    /**
     * 新增t_task_report（无人机任务巡检报告）
     * 
     * @param tTaskReport t_task_report（无人机任务巡检报告）
     * @return 结果
     */
    public int insertTTaskReport(TTaskReport tTaskReport);

    /**
     * 修改t_task_report（无人机任务巡检报告）
     * 
     * @param tTaskReport t_task_report（无人机任务巡检报告）
     * @return 结果
     */
    public int updateTTaskReport(TTaskReport tTaskReport);

    /**
     * 删除t_task_report（无人机任务巡检报告）
     * 
     * @param reportId t_task_report（无人机任务巡检报告）ID
     * @return 结果
     */
    public int deleteTTaskReportById(Long reportId);

    /**
     * 批量删除t_task_report（无人机任务巡检报告）
     * 
     * @param reportIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTTaskReportByIds(Long[] reportIds);
}
