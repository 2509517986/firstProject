package com.senxu.omserver.service.service.impl;

import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TInspectionArguments;
import com.senxu.omserver.service.mapper.TInspectionArgumentsMapper;
import com.senxu.omserver.service.service.TInspectionArgumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * t_inspection_arguments（巡检参数配置）对象 t_inspection_arguments
 *
 * @author lvhp
 * @date 2021-10-25
 */
@Service
public class TInspectionArgumentsServiceImpl implements TInspectionArgumentsService {

    @Autowired(required = false)
    TInspectionArgumentsMapper tInspectionArgumentsMapper;

    /**
     * 查询t_inspection_arguments（巡检参数信息）
     *
     * @param id t_inspection_arguments（巡检参数信息）ID
     * @return t_inspection_arguments（巡检参数信息）
     */
    @Override
    public TInspectionArguments selectTInspectionArgumentsById(Long id) {
        return tInspectionArgumentsMapper.selectTInspectionArgumentsById(id);
    }

    /**
     * 查询t_inspection_arguments（巡检参数信息）列表
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数信息）
     * @return t_inspection_arguments（巡检参数信息）
     */
    @Override
    public List<TInspectionArguments> selectTInspectionArgumentsList(TInspectionArguments tInspectionArguments) {
        return tInspectionArgumentsMapper.selectTInspectionArgumentsList(tInspectionArguments);
    }

    /**
     * 新增t_inspection_arguments（巡检参数信息）
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数信息）
     * @return 结果
     */
    @Override
    public int insertTInspectionArguments(TInspectionArguments tInspectionArguments) {
        tInspectionArguments.setCreateTime(DateUtils.getNowDate());
        return tInspectionArgumentsMapper.insertTInspectionArguments(tInspectionArguments);
    }

    /**
     * 修改t_inspection_arguments（巡检参数信息）
     *
     * @param tInspectionArguments t_inspection_arguments（巡检参数信息）
     * @return 结果
     */
    @Override
    public int updateTInspectionArguments(TInspectionArguments tInspectionArguments) {
        return tInspectionArgumentsMapper.updateTInspectionArguments(tInspectionArguments);
    }

    /**
     * 删除t_inspection_arguments（巡检参数信息）信息
     *
     * @param id t_inspection_arguments（巡检参数信息）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionArgumentsById(Long id) {
        return tInspectionArgumentsMapper.deleteTInspectionArgumentsById(id);
    }
}
