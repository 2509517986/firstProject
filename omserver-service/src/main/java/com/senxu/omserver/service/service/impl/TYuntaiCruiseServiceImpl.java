package com.senxu.omserver.service.service.impl;

import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TYuntaiCruise;
import com.senxu.omserver.service.mapper.TYuntaiCruiseMapper;
import com.senxu.omserver.service.service.TYuntaiCruiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * t_yuntai_cruise（巡航表）对象 t_yuntai_cruise
 *
 * @author lvhp
 * @date 2021-10-25
 */
@Service
public class TYuntaiCruiseServiceImpl implements TYuntaiCruiseService {

    @Autowired(required = false)
    TYuntaiCruiseMapper tyuntaiCruiseMapper;

    /**
     * 查询t_yuntai_cruise（巡航信息）
     *
     * @param id t_yuntai_cruise（巡航信息）ID
     * @return t_yuntai_cruise（巡航信息）
     */
    @Override
    public TYuntaiCruise selectTYuntaiCruiseById(Long id) {
        return tyuntaiCruiseMapper.selectTYuntaiCruiseById(id);
    }

    /**
     * 查询t_yuntai_cruise（巡航信息）列表
     *
     * @param tYuntaiCruise t_yuntai_cruise（巡航信息）
     * @return t_yuntai_cruise（巡航信息）
     */
    @Override
    public List<TYuntaiCruise> selectTYuntaiCruiseList(TYuntaiCruise tYuntaiCruise) {
        return tyuntaiCruiseMapper.selectTYuntaiCruiseList(tYuntaiCruise);
    }

    /**
     * 新增t_yuntai_cruise（巡航信息）
     *
     * @param tYuntaiCruise t_yuntai_cruise（巡航信息）
     * @return 结果
     */
    @Override
    public int insertTYuntaiCruise(TYuntaiCruise tYuntaiCruise) {
        tYuntaiCruise.setCreateTime(DateUtils.getNowDate());
        return tyuntaiCruiseMapper.insertTYuntaiCruise(tYuntaiCruise);
    }

    /**
     * 修改t_yuntai_cruise（巡航信息）
     *
     * @param tYuntaiCruise t_yuntai_cruise（巡航信息）
     * @return 结果
     */
    @Override
    public int updateTYuntaiCruise(TYuntaiCruise tYuntaiCruise) {
        return tyuntaiCruiseMapper.updateTYuntaiCruise(tYuntaiCruise);
    }

    /**
     * 删除t_yuntai_cruise（巡航信息）信息
     *
     * @param id t_yuntai_cruise（巡航信息）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiCruiseById(Long id) {
        return tyuntaiCruiseMapper.deleteTYuntaiCruiseById(id);
    }
}
