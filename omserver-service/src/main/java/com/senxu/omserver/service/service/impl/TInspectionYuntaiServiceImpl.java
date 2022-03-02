package com.senxu.omserver.service.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.*;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiStatistic;
import com.senxu.omserver.service.mapper.TYuntaiInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TInspectionYuntaiMapper;
import com.senxu.omserver.service.service.ITInspectionYuntaiService;

/**
 * t_inspection_yuntai（巡检云台记录）Service业务层处理
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Service
public class TInspectionYuntaiServiceImpl implements ITInspectionYuntaiService 
{
    @Autowired
    private TInspectionYuntaiMapper tInspectionYuntaiMapper;

    @Autowired
    private TYuntaiInfoMapper tYuntaiInfoMapper;


    /**
     * 查询t_inspection_yuntai（巡检云台记录）
     * 
     * @param id t_inspection_yuntai（巡检云台记录）ID
     * @return t_inspection_yuntai（巡检云台记录）
     */
    @Override
    public TInspectionYuntai selectTInspectionYuntaiById(Long id)
    {
        return tInspectionYuntaiMapper.selectTInspectionYuntaiById(id);
    }

    @Override
    public List<TInspectionYuntai> findReportByDate(String lineIds, Long reportId,String towerIds) {
        List<TInspectionYuntai> listYuntai=new ArrayList<TInspectionYuntai>();

        if(reportId==null || reportId==0){
            List<TYuntaiInfo> list=tYuntaiInfoMapper.selectTYuntaiInfoList(new TYuntaiInfo(lineIds,towerIds));
            for(TYuntaiInfo obj:list){
                TInspectionYuntai yuntai=new TInspectionYuntai();
                yuntai.setYuntaiId(obj.getId());
                yuntai.setLineId(obj.getLineId());
                yuntai.setTowerId(obj.getTowerId());
                yuntai.setHiddenDanger(obj.getHiddenDanger());
                yuntai.setState(obj.getState());

                listYuntai.add(yuntai);
            }
        }else{
            TInspectionYuntai tInspectionYuntai=new TInspectionYuntai();
            tInspectionYuntai.setReportId(reportId);
            listYuntai= tInspectionYuntaiMapper.selectTInspectionYuntaiList(tInspectionYuntai);
        }

        return listYuntai;
    }

    @Override
    public List<TInspectionYuntaiStatistic> yuntaiStatisticsByDate(String lineIds, Long reportId) {
        TInspectionYuntai yuntai= new TInspectionYuntai(lineIds);
        yuntai.setReportId(reportId);
        return tInspectionYuntaiMapper.yuntaiStatisticsByDate(yuntai);
    }

    @Override
    public List<TInspectionYuntaiHiddenDanger> yuntaiHiddenDangerByDate(String lineIds, Long reportId) {
        TInspectionYuntai yuntai=new TInspectionYuntai(lineIds);
        yuntai.setReportId(reportId);
        return tInspectionYuntaiMapper.yuntaiHiddenDangerByDate(yuntai);
    }

    /**
     * 查询t_inspection_yuntai（巡检云台记录）列表
     *
     * @param tInspectionYuntai t_inspection_yuntai（巡检云台记录）
     * @return t_inspection_yuntai（巡检云台记录）
     */
    @Override
    public List<TInspectionYuntai> selectTInspectionYuntaiList(TInspectionYuntai tInspectionYuntai)
    {
        return tInspectionYuntaiMapper.selectTInspectionYuntaiList(tInspectionYuntai);
    }

    /**
     * 新增t_inspection_yuntai（巡检云台记录）
     * 
     * @param tInspectionYuntai t_inspection_yuntai（巡检云台记录）
     * @return 结果
     */
    @Override
    public int insertTInspectionYuntai(TInspectionYuntai tInspectionYuntai)
    {
        tInspectionYuntai.setCreateTime(DateUtils.getNowDate());
        return tInspectionYuntaiMapper.insertTInspectionYuntai(tInspectionYuntai);
    }

    /**
     * 修改t_inspection_yuntai（巡检云台记录）
     * 
     * @param tInspectionYuntai t_inspection_yuntai（巡检云台记录）
     * @return 结果
     */
    @Override
    public int updateTInspectionYuntai(TInspectionYuntai tInspectionYuntai)
    {
        return tInspectionYuntaiMapper.updateTInspectionYuntai(tInspectionYuntai);
    }

    /**
     * 批量删除t_inspection_yuntai（巡检云台记录）
     * 
     * @param ids 需要删除的t_inspection_yuntai（巡检云台记录）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionYuntaiByIds(Long[] ids)
    {
        return tInspectionYuntaiMapper.deleteTInspectionYuntaiByIds(ids);
    }

    /**
     * 删除t_inspection_yuntai（巡检云台记录）信息
     * 
     * @param id t_inspection_yuntai（巡检云台记录）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionYuntaiById(Long id)
    {
        return tInspectionYuntaiMapper.deleteTInspectionYuntaiById(id);
    }
}
