package com.senxu.omserver.service.mapper;

import com.senxu.omserver.service.domain.TFunctionParameters;

import java.util.List;

/**
 * t_function_parameters（功能参数）对象 t_function_parameters
 *
 * @author lvhp
 * @date 2021-10-25
 */
public interface TFunctionParametersMapper {

    /**
     * 查询t_function_parameters（功能参数）
     *
     * @param id t_function_parameters（功能参数）ID
     * @return t_function_parameters（功能参数）
     */
    public TFunctionParameters selectTFunctionParametersById(Long id);

    /**
     * 查询t_function_parameters（功能参数）列表
     *
     * @param tFunctionParameters t_function_parameters（功能参数）
     * @return t_function_parameters（功能参数）集合
     */
    public List<TFunctionParameters> selectTFunctionParametersList(TFunctionParameters tFunctionParameters);

    /**
     * 新增t_function_parameters（功能参数）
     *
     * @param tFunctionParameters t_function_parameters（功能参数）
     * @return 结果
     */
    public int insertTFunctionParameters(TFunctionParameters tFunctionParameters);

    /**
     * 修改t_function_parameters（功能参数）
     *
     * @param tFunctionParameters t_function_parameters（功能参数）
     * @return 结果
     */
    public int updateTFunctionParameters(TFunctionParameters tFunctionParameters);

    /**
     * 删除t_function_parameters（功能参数）
     *
     * @param id t_function_parameters（功能参数）ID
     * @return 结果
     */
    public int deleteTFunctionParametersById(Long id);

    /**
     * 查询t_function_parameters（功能参数）
     *
     * @param yuntaiId t_function_parameters（功能参数）ID
     * @return t_function_parameters（功能参数）
     */
    public TFunctionParameters selectTFunctionParametersByYuntaiId(Long yuntaiId);
}
