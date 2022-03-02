package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiBreakdownMapper;
import com.senxu.omserver.service.domain.TYuntaiBreakdown;
import com.senxu.omserver.service.service.ITYuntaiBreakdownService;

/**
 * t_yuntai_breakdown（云台缺销记录）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiBreakdownServiceImpl implements ITYuntaiBreakdownService 
{
    @Autowired
    private TYuntaiBreakdownMapper tYuntaiBreakdownMapper;

    /**
     * 查询t_yuntai_breakdown（云台缺销记录）
     * 
     * @param id t_yuntai_breakdown（云台缺销记录）ID
     * @return t_yuntai_breakdown（云台缺销记录）
     */
    @Override
    public TYuntaiBreakdown selectTYuntaiBreakdownById(Long id)
    {
        return tYuntaiBreakdownMapper.selectTYuntaiBreakdownById(id);
    }

    /**
     * 查询t_yuntai_breakdown（云台缺销记录）列表
     * 
     * @param tYuntaiBreakdown t_yuntai_breakdown（云台缺销记录）
     * @return t_yuntai_breakdown（云台缺销记录）
     */
    @Override
    public List<TYuntaiBreakdown> selectTYuntaiBreakdownList(TYuntaiBreakdown tYuntaiBreakdown)
    {
        return tYuntaiBreakdownMapper.selectTYuntaiBreakdownList(tYuntaiBreakdown);
    }

    /**
     * 新增t_yuntai_breakdown（云台缺销记录）
     * 
     * @param tYuntaiBreakdown t_yuntai_breakdown（云台缺销记录）
     * @return 结果
     */
    @Override
    public int insertTYuntaiBreakdown(TYuntaiBreakdown tYuntaiBreakdown)
    {
        tYuntaiBreakdown.setCreateTime(DateUtils.getNowDate());
        return tYuntaiBreakdownMapper.insertTYuntaiBreakdown(tYuntaiBreakdown);
    }

    /**
     * 修改t_yuntai_breakdown（云台缺销记录）
     * 
     * @param tYuntaiBreakdown t_yuntai_breakdown（云台缺销记录）
     * @return 结果
     */
    @Override
    public int updateTYuntaiBreakdown(TYuntaiBreakdown tYuntaiBreakdown)
    {
        return tYuntaiBreakdownMapper.updateTYuntaiBreakdown(tYuntaiBreakdown);
    }

    /**
     * 批量删除t_yuntai_breakdown（云台缺销记录）
     * 
     * @param ids 需要删除的t_yuntai_breakdown（云台缺销记录）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiBreakdownByIds(Long[] ids)
    {
        return tYuntaiBreakdownMapper.deleteTYuntaiBreakdownByIds(ids);
    }

    /**
     * 删除t_yuntai_breakdown（云台缺销记录）信息
     * 
     * @param id t_yuntai_breakdown（云台缺销记录）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiBreakdownById(Long id)
    {
        return tYuntaiBreakdownMapper.deleteTYuntaiBreakdownById(id);
    }
}
