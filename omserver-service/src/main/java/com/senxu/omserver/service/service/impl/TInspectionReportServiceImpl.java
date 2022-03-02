package com.senxu.omserver.service.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TInspectionYuntai;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;
import com.senxu.omserver.service.mapper.TYuntaiInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TInspectionReportMapper;
import com.senxu.omserver.service.domain.TInspectionReport;
import com.senxu.omserver.service.service.ITInspectionReportService;

/**
 * t_inspection_report（巡检报告）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TInspectionReportServiceImpl implements ITInspectionReportService 
{
    @Autowired
    private TInspectionReportMapper tInspectionReportMapper;

    @Autowired
    private TYuntaiInfoMapper tYuntaiInfoMapper;

    /**
     * 查询t_inspection_report（巡检报告）
     * 
     * @param id t_inspection_report（巡检报告）ID
     * @return t_inspection_report（巡检报告）
     */
    @Override
    public TInspectionReport selectTInspectionReportById(Long id)
    {
        return tInspectionReportMapper.selectTInspectionReportById(id);
    }

    /**
     * 查询t_inspection_report（巡检报告）
     *
     * @param sDate 传入日期
     * @return t_inspection_report（巡检报告）
     */
    @Override
    public TInspectionReport findReportByDate(String sDate) {
        return tInspectionReportMapper.findReportByDate(sDate);
    }


    /**
     * 查询t_inspection_report（巡检报告）列表
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return t_inspection_report（巡检报告）
     */
    @Override
    public List<TInspectionReport> selectTInspectionReportList(TInspectionReport tInspectionReport)
    {
        return tInspectionReportMapper.selectTInspectionReportList(tInspectionReport);
    }

    /**
     * 新增t_inspection_report（巡检报告）
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return 结果
     */
    @Override
    public int insertTInspectionReport(TInspectionReport tInspectionReport,String userName)
    {
        tInspectionReport.setCreateTime(DateUtils.getNowDate());
        tInspectionReport.setCreateUser(userName);
        return tInspectionReportMapper.insertTInspectionReport(tInspectionReport);
    }

    /**
     * 修改t_inspection_report（巡检报告）
     * 
     * @param tInspectionReport t_inspection_report（巡检报告）
     * @return 结果
     */
    @Override
    public int updateTInspectionReport(TInspectionReport tInspectionReport)
    {
        return tInspectionReportMapper.updateTInspectionReport(tInspectionReport);
    }

    /**
     * 批量删除t_inspection_report（巡检报告）
     * 
     * @param ids 需要删除的t_inspection_report（巡检报告）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionReportByIds(Long[] ids)
    {
        return tInspectionReportMapper.deleteTInspectionReportByIds(ids);
    }

    /**
     * 删除t_inspection_report（巡检报告）信息
     * 
     * @param id t_inspection_report（巡检报告）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionReportById(Long id)
    {
        return tInspectionReportMapper.deleteTInspectionReportById(id);
    }


}
