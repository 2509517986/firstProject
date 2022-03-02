package com.senxu.omserver.service.service.impl;

import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TInspectiontTactics;
import com.senxu.omserver.service.mapper.TInspectiontTacticsMapper;
import com.senxu.omserver.service.service.TInspectiontTacticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * t_inspection_tactics（巡检策略配置）对象 t_inspection_tactics
 *
 * @author lvhp
 * @date 2021-10-25
 */
@Service
public class TInspectiontTacticsServiceImpl implements TInspectiontTacticsService {


    @Autowired(required = false)
    TInspectiontTacticsMapper tInspectiontTacticsMapper;

    /**
     * 查询t_inspection_tactics（巡检策略信息）
     *
     * @param id t_inspection_tactics（巡检策略信息）ID
     * @return t_inspection_tactics（巡检策略信息）
     */
    @Override
    public TInspectiontTactics selectTInspectiontTacticsById(Long id) {
        return tInspectiontTacticsMapper.selectTInspectiontTacticsById(id);
    }

    /**
     * 查询t_inspection_tactics（巡检策略信息）列表
     *
     * @param tInspectiontTactics t_inspection_tactics（巡检策略信息）
     * @return t_inspection_tactics（巡检策略信息）
     */
    @Override
    public List<TInspectiontTactics> selectTInspectiontTacticsList(TInspectiontTactics tInspectiontTactics) {
        return tInspectiontTacticsMapper.selectTInspectiontTacticsList(tInspectiontTactics);
    }

    /**
     * 新增t_inspection_tactics（巡检策略信息）
     *
     * @param tInspectiontTactics t_inspection_tactics（巡检策略信息）
     * @return 结果
     */
    @Override
    public int insertTInspectiontTactics(TInspectiontTactics tInspectiontTactics) {
        tInspectiontTactics.setCreateTime(DateUtils.getNowDate());
        return tInspectiontTacticsMapper.insertTInspectiontTactics(tInspectiontTactics);
    }

    /**
     * 修改t_inspection_tactics（巡检策略信息）
     *
     * @param tInspectiontTactics t_inspection_tactics（巡检策略信息）
     * @return 结果
     */
    @Override
    public int updateTInspectiontTactics(TInspectiontTactics tInspectiontTactics) {
        return tInspectiontTacticsMapper.updateTInspectiontTactics(tInspectiontTactics);
    }

    /**
     * 删除t_inspection_tactics（巡检策略信息）信息
     *
     * @param id t_inspection_tactics（巡检策略信息）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectiontTacticsById(Long id) {
        return tInspectiontTacticsMapper.deleteTInspectiontTacticsById(id);
    }
}
