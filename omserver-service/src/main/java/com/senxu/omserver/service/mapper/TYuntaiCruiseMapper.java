package com.senxu.omserver.service.mapper;

import com.senxu.omserver.service.domain.TYuntaiCruise;

import java.util.List;

/**
 * t_yuntai_cruise（巡航表）对象 t_yuntai_cruise
 *
 * @author lvhp
 * @date 2021-10-25
 */
public interface TYuntaiCruiseMapper {

    /**
     * 查询t_line_info（巡航信息）
     *
     * @param id t_line_info（巡航信息）ID
     * @return t_line_info（巡航信息）
     */
    public TYuntaiCruise selectTYuntaiCruiseById(Long id);

    /**
     * 查询t_line_info（巡航信息）列表
     *
     * @param tYuntaiCruise t_line_info（巡航信息）
     * @return t_line_info（巡航信息）集合
     */
    public List<TYuntaiCruise> selectTYuntaiCruiseList(TYuntaiCruise tYuntaiCruise);

    /**
     * 新增t_line_info（巡航信息）
     *
     * @param tYuntaiCruise t_line_info（巡航信息）
     * @return 结果
     */
    public int insertTYuntaiCruise(TYuntaiCruise tYuntaiCruise);

    /**
     * 修改t_line_info（巡航信息）
     *
     * @param tYuntaiCruise t_line_info（巡航信息）
     * @return 结果
     */
    public int updateTYuntaiCruise(TYuntaiCruise tYuntaiCruise);

    /**
     * 删除t_line_info（巡航信息）
     *
     * @param id t_line_info（巡航信息）ID
     * @return 结果
     */
    public int deleteTYuntaiCruiseById(Long id);
}
