package com.senxu.omserver.service.service;

import com.senxu.omserver.service.domain.TInspectiontTactics;

import java.util.List;

/**
 * t_inspection_tactics（巡检策略配置）对象 t_inspection_tactics
 *
 * @author lvhp
 * @date 2021-10-25
 */
public interface TInspectiontTacticsService {

    /**
     * 查询t_inspection_tactics（巡检策略）
     *
     * @param id t_inspection_tactics（巡检策略）ID
     * @return t_inspection_tactics（巡检策略）
     */
    public TInspectiontTactics selectTInspectiontTacticsById(Long id);

    /**
     * 查询t_inspection_tactics（巡检策略）列表
     *
     * @param tInspectiontTactics t_inspection_tactics（巡检策略）
     * @return t_inspection_tactics（巡检策略）集合
     */
    public List<TInspectiontTactics> selectTInspectiontTacticsList(TInspectiontTactics tInspectiontTactics);


    /**
     * 新增t_inspection_tactics（巡检策略）
     *
     * @param tInspectiontTactics t_inspection_tactics（巡检策略）
     * @return 结果
     */
    public int insertTInspectiontTactics(TInspectiontTactics tInspectiontTactics);

    /**
     * 修改t_inspection_tactics（巡检策略）
     *
     * @param tInspectiontTactics t_inspection_tactics（巡检策略）
     * @return 结果
     */
    public int updateTInspectiontTactics(TInspectiontTactics tInspectiontTactics);

    /**
     * 删除t_inspection_tactics（巡检策略）信息
     *
     * @param id t_inspection_tactics（巡检策略）ID
     * @return 结果
     */
    public int deleteTInspectiontTacticsById(Long id);
}
