package com.senxu.omserver.service.mapper;

import com.senxu.omserver.service.domain.TInspectionArguments;

import java.util.List;

/**
 * t_inspection_arguments（巡检参数配置）对象 t_inspection_arguments
 *
 * @author lvhp
 * @date 2021-10-25
 */
public interface TInspectionArgumentsMapper {

    /**
     * 查询t_inspection_arguments（巡检参数信息）
     *
     * @param id t_inspection_arguments（巡检参数信息）ID
     * @return t_inspection_arguments（巡检参数信息）
     */
    public TInspectionArguments selectTInspectionArgumentsById(Long id);

    /**
     * 查询t_inspection_arguments（巡检参数信息）列表
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数信息）
     * @return t_inspection_arguments（巡检参数信息）集合
     */
    public List<TInspectionArguments> selectTInspectionArgumentsList(TInspectionArguments tInspectionArguments);

    /**
     * 新增t_inspection_arguments（巡检参数信息）
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数信息）
     * @return 结果
     */
    public int insertTInspectionArguments(TInspectionArguments tInspectionArguments);

    /**
     * 修改t_inspection_arguments（巡检参数信息）
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数信息）
     * @return 结果
     */
    public int updateTInspectionArguments(TInspectionArguments tInspectionArguments);

    /**
     * 删除t_inspection_arguments（巡检参数信息）
     *
     * @param id t_inspection_arguments（巡检参数信息）ID
     * @return 结果
     */
    public int deleteTInspectionArgumentsById(Long id);
}
