package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TInspectionReport;
import com.senxu.omserver.service.domain.TInspectionYuntai;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;

/**
 * t_inspection_report（巡检报告）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface ITInspectionReportService 
{
    /**
     * 查询t_inspection_report（巡检报告）
     * 
     * @param id t_inspection_report（巡检报告）ID
     * @return t_inspection_report（巡检报告）
     */
    public TInspectionReport selectTInspectionReportById(Long id);

    /**
     * 按日期查询巡检报告
     *
     * @param sDate 传入日期
     * @return t_inspection_report（巡检报告）
     */
    public TInspectionReport findReportByDate(String sDate);


    /**
     * 查询t_inspection_report（巡检报告）列表
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return t_inspection_report（巡检报告）集合
     */
    public List<TInspectionReport> selectTInspectionReportList(TInspectionReport tInspectionReport);

    /**
     * 新增t_inspection_report（巡检报告）
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return 结果
     */
    public int insertTInspectionReport(TInspectionReport tInspectionReport,String userName);

    /**
     * 修改t_inspection_report（巡检报告）
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return 结果
     */
    public int updateTInspectionReport(TInspectionReport tInspectionReport);

    /**
     * 批量删除t_inspection_report（巡检报告）
     * 
     * @param ids 需要删除的t_inspection_report（巡检报告）ID
     * @return 结果
     */
    public int deleteTInspectionReportByIds(Long[] ids);

    /**
     * 删除t_inspection_report（巡检报告）信息
     * 
     * @param id t_inspection_report（巡检报告）ID
     * @return 结果
     */
    public int deleteTInspectionReportById(Long id);

}
