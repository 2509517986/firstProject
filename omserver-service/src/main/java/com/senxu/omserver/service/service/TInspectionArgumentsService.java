package com.senxu.omserver.service.service;

import com.senxu.omserver.service.domain.TInspectionArguments;

import java.util.List;

/**
 * t_inspection_arguments（巡检参数配置）对象 t_inspection_arguments
 *
 * @author lvhp
 * @date 2021-10-25
 */
public interface TInspectionArgumentsService {

    /**
     * 查询t_inspection_arguments（巡检参数）
     *
     * @param id t_inspection_arguments（巡检参数）ID
     * @return t_inspection_arguments（巡检参数）
     */
    public TInspectionArguments selectTInspectionArgumentsById(Long id);

    /**
     * 查询t_inspection_arguments（巡检参数）列表
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数）
     * @return t_inspection_arguments（巡检参数）集合
     */
    public List<TInspectionArguments> selectTInspectionArgumentsList(TInspectionArguments tInspectionArguments);


    /**
     * 新增t_inspection_arguments（巡检参数）
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数）
     * @return 结果
     */
    public int insertTInspectionArguments(TInspectionArguments tInspectionArguments);

    /**
     * 修改t_inspection_arguments（巡检参数）
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数）
     * @return 结果
     */
    public int updateTInspectionArguments(TInspectionArguments tInspectionArguments);

    /**
     * 删除t_inspection_arguments（巡检参数）信息
     *
     * @param id t_inspection_arguments（巡检参数）ID
     * @return 结果
     */
    public int deleteTInspectionArgumentsById(Long id);
}
