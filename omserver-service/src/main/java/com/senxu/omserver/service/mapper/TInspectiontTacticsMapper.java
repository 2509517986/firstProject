package com.senxu.omserver.service.mapper;

import com.senxu.omserver.service.domain.TInspectiontTactics;

import java.util.List;

/**
 * t_inspection_tactics（巡检策略配置）对象 t_inspection_tactics
 *
 * @author lvhp
 * @date 2021-10-25
 */
public interface TInspectiontTacticsMapper {

    /**
     * 查询t_inspection_tactics（巡检策略信息）
     *
     * @param id t_inspection_tactics（巡检策略信息）ID
     * @return t_inspection_tactics（巡检策略信息）
     */
    public TInspectiontTactics selectTInspectiontTacticsById(Long id);

    /**
     * 查询t_inspection_tactics（巡检策略信息）列表
     *
     * @param tInspectiontTactics t_inspection_tactics（巡检策略信息）
     * @return t_inspection_tactics（巡检策略信息）集合
     */
    public List<TInspectiontTactics> selectTInspectiontTacticsList(TInspectiontTactics tInspectiontTactics);

    /**
     * 新增t_inspection_tactics（巡检策略信息）
     *
     * @param tInspectiontTactics t_inspection_tactics（巡检策略信息）
     * @return 结果
     */
    public int insertTInspectiontTactics(TInspectiontTactics tInspectiontTactics);

    /**
     * 修改t_inspection_tactics（巡检策略信息）
     *
     * @param TInspectiontTactics t_inspection_tactics（巡检策略信息）
     * @return 结果
     */
    public int updateTInspectiontTactics(TInspectiontTactics TInspectiontTactics);

    /**
     * 删除t_inspection_tactics（巡检策略信息）
     *
     * @param id t_inspection_tactics（巡检策略信息）ID
     * @return 结果
     */
    public int deleteTInspectiontTacticsById(Long id);
}
