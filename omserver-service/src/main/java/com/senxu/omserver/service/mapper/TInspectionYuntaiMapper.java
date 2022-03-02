package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TInspectionYuntai;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiStatistic;

/**
 * t_inspection_yuntai（巡检云台记录）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface TInspectionYuntaiMapper 
{
    /**
     * 查询t_inspection_yuntai（巡检云台记录）
     * 
     * @param id t_inspection_yuntai（巡检云台记录）ID
     * @return t_inspection_yuntai（巡检云台记录）
     */
    public TInspectionYuntai selectTInspectionYuntaiById(Long id);



    public List<TInspectionYuntaiStatistic> yuntaiStatisticsByDate(TInspectionYuntai tInspectionYuntai);


    public List<TInspectionYuntaiHiddenDanger> yuntaiHiddenDangerByDate(TInspectionYuntai tInspectionYuntai);
    /**
     * 查询t_inspection_yuntai（巡检云台记录）列表
     * 
     * @param tInspectionYuntai t_inspection_yuntai（巡检云台记录）
     * @return t_inspection_yuntai（巡检云台记录）集合
     */
    public List<TInspectionYuntai> selectTInspectionYuntaiList(TInspectionYuntai tInspectionYuntai);

    /**
     * 新增t_inspection_yuntai（巡检云台记录）
     * 
     * @param tInspectionYuntai t_inspection_yuntai（巡检云台记录）
     * @return 结果
     */
    public int insertTInspectionYuntai(TInspectionYuntai tInspectionYuntai);

    /**
     * 修改t_inspection_yuntai（巡检云台记录）
     * 
     * @param tInspectionYuntai t_inspection_yuntai（巡检云台记录）
     * @return 结果
     */
    public int updateTInspectionYuntai(TInspectionYuntai tInspectionYuntai);

    /**
     * 删除t_inspection_yuntai（巡检云台记录）
     * 
     * @param id t_inspection_yuntai（巡检云台记录）ID
     * @return 结果
     */
    public int deleteTInspectionYuntaiById(Long id);

    /**
     * 批量删除t_inspection_yuntai（巡检云台记录）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTInspectionYuntaiByIds(Long[] ids);
}
