package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TInspectionReport;

/**
 * t_inspection_report（巡检报告）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface TInspectionReportMapper 
{
    /**
     * 查询t_inspection_report（巡检报告）
     * 
     * @param id t_inspection_report（巡检报告）ID
     * @return t_inspection_report（巡检报告）
     */
    public TInspectionReport selectTInspectionReportById(Long id);

    /**
     * 查询t_inspection_report（巡检报告）列表
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return t_inspection_report（巡检报告）集合
     */
    public List<TInspectionReport> selectTInspectionReportList(TInspectionReport tInspectionReport);

    /**
     * 按日期查询巡检报告
     *
     * @param sDate 传入日期
     * @return t_inspection_report（巡检报告）
     */
    public TInspectionReport findReportByDate(String sDate);


    /**
     * 新增t_inspection_report（巡检报告）
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return 结果
     */
    public int insertTInspectionReport(TInspectionReport tInspectionReport);

    /**
     * 修改t_inspection_report（巡检报告）
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return 结果
     */
    public int updateTInspectionReport(TInspectionReport tInspectionReport);

    /**
     * 删除t_inspection_report（巡检报告）
     * 
     * @param id t_inspection_report（巡检报告）ID
     * @return 结果
     */
    public int deleteTInspectionReportById(Long id);

    /**
     * 批量删除t_inspection_report（巡检报告）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTInspectionReportByIds(Long[] ids);
}
