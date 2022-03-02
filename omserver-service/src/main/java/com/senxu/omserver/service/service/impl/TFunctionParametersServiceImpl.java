package com.senxu.omserver.service.service.impl;

import com.senxu.omserver.service.domain.TFunctionParameters;
import com.senxu.omserver.service.mapper.TFunctionParametersMapper;
import com.senxu.omserver.service.service.TFunctionParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TFunctionParametersServiceImpl implements TFunctionParametersService {

    @Autowired(required = false)
    TFunctionParametersMapper tFunctionParametersMapper;

    /**
     * 查询t_function_parameters（功能参数）
     *
     * @param id t_function_parameters（功能参数）ID
     * @return t_function_parameters（功能参数）
     */
    @Override
    public TFunctionParameters selectTFunctionParametersById(Long id) {
        return tFunctionParametersMapper.selectTFunctionParametersById(id);
    }

    /**
     * 查询t_function_parameters（功能参数）列表
     *
     * @param tFunctionParameters t_function_parameters（功能参数）
     * @return t_function_parameters（功能参数）集合
     */
    @Override
    public List<TFunctionParameters> selectTFunctionParametersList(TFunctionParameters tFunctionParameters) {
        return tFunctionParametersMapper.selectTFunctionParametersList(tFunctionParameters);
    }

    /**
     * 新增t_function_parameters（功能参数）
     *
     * @param tFunctionParameters t_function_parameters（功能参数）
     * @return 结果
     */
    @Override
    public int insertTFunctionParameters(TFunctionParameters tFunctionParameters) {
        return tFunctionParametersMapper.insertTFunctionParameters(tFunctionParameters);
    }

    /**
     * 修改t_function_parameters（功能参数）
     *
     * @param tFunctionParameters t_function_parameters（功能参数）
     * @return 结果
     */
    @Override
    public int updateTFunctionParameters(TFunctionParameters tFunctionParameters) {
        return tFunctionParametersMapper.updateTFunctionParameters(tFunctionParameters);
    }

    /**
     * 删除t_function_parameters（功能参数）信息
     *
     * @param id t_function_parameters（功能参数）ID
     * @return 结果
     */
    @Override
    public int deleteTFunctionParametersById(Long id) {
        return tFunctionParametersMapper.deleteTFunctionParametersById(id);
    }

    @Override
    public TFunctionParameters selectTFunctionParametersByYuntaiId(Long yuntaiId) {
        return tFunctionParametersMapper.selectTFunctionParametersByYuntaiId(yuntaiId);
    }
}
