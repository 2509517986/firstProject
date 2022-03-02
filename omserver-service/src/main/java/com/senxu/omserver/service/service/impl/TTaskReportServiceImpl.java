package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TTaskReportMapper;
import com.senxu.omserver.service.domain.TTaskReport;
import com.senxu.omserver.service.service.ITTaskReportService;

/**
 * t_task_report（无人机任务巡检报告）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TTaskReportServiceImpl implements ITTaskReportService 
{
    @Autowired
    private TTaskReportMapper tTaskReportMapper;

    /**
     * 查询t_task_report（无人机任务巡检报告）
     * 
     * @param reportId t_task_report（无人机任务巡检报告）ID
     * @return t_task_report（无人机任务巡检报告）
     */
    @Override
    public TTaskReport selectTTaskReportById(Long reportId)
    {
        return tTaskReportMapper.selectTTaskReportById(reportId);
    }

    /**
     * 查询t_task_report（无人机任务巡检报告）列表
     * 
     * @param tTaskReport t_task_report（无人机任务巡检报告）
     * @return t_task_report（无人机任务巡检报告）
     */
    @Override
    public List<TTaskReport> selectTTaskReportList(TTaskReport tTaskReport)
    {
        return tTaskReportMapper.selectTTaskReportList(tTaskReport);
    }

    /**
     * 新增t_task_report（无人机任务巡检报告）
     * 
     * @param tTaskReport t_task_report（无人机任务巡检报告）
     * @return 结果
     */
    @Override
    public int insertTTaskReport(TTaskReport tTaskReport)
    {
        return tTaskReportMapper.insertTTaskReport(tTaskReport);
    }

    /**
     * 修改t_task_report（无人机任务巡检报告）
     * 
     * @param tTaskReport t_task_report（无人机任务巡检报告）
     * @return 结果
     */
    @Override
    public int updateTTaskReport(TTaskReport tTaskReport)
    {
        return tTaskReportMapper.updateTTaskReport(tTaskReport);
    }

    /**
     * 批量删除t_task_report（无人机任务巡检报告）
     * 
     * @param reportIds 需要删除的t_task_report（无人机任务巡检报告）ID
     * @return 结果
     */
    @Override
    public int deleteTTaskReportByIds(Long[] reportIds)
    {
        return tTaskReportMapper.deleteTTaskReportByIds(reportIds);
    }

    /**
     * 删除t_task_report（无人机任务巡检报告）信息
     * 
     * @param reportId t_task_report（无人机任务巡检报告）ID
     * @return 结果
     */
    @Override
    public int deleteTTaskReportById(Long reportId)
    {
        return tTaskReportMapper.deleteTTaskReportById(reportId);
    }
}
