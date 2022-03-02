package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiBreakdown;

/**
 * t_yuntai_breakdown（云台缺销记录）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface ITYuntaiBreakdownService 
{
    /**
     * 查询t_yuntai_breakdown（云台缺销记录）
     * 
     * @param id t_yuntai_breakdown（云台缺销记录）ID
     * @return t_yuntai_breakdown（云台缺销记录）
     */
    public TYuntaiBreakdown selectTYuntaiBreakdownById(Long id);

    /**
     * 查询t_yuntai_breakdown（云台缺销记录）列表
     * 
     * @param tYuntaiBreakdown t_yuntai_breakdown（云台缺销记录）
     * @return t_yuntai_breakdown（云台缺销记录）集合
     */
    public List<TYuntaiBreakdown> selectTYuntaiBreakdownList(TYuntaiBreakdown tYuntaiBreakdown);

    /**
     * 新增t_yuntai_breakdown（云台缺销记录）
     * 
     * @param tYuntaiBreakdown t_yuntai_breakdown（云台缺销记录）
     * @return 结果
     */
    public int insertTYuntaiBreakdown(TYuntaiBreakdown tYuntaiBreakdown);

    /**
     * 修改t_yuntai_breakdown（云台缺销记录）
     * 
     * @param tYuntaiBreakdown t_yuntai_breakdown（云台缺销记录）
     * @return 结果
     */
    public int updateTYuntaiBreakdown(TYuntaiBreakdown tYuntaiBreakdown);

    /**
     * 批量删除t_yuntai_breakdown（云台缺销记录）
     * 
     * @param ids 需要删除的t_yuntai_breakdown（云台缺销记录）ID
     * @return 结果
     */
    public int deleteTYuntaiBreakdownByIds(Long[] ids);

    /**
     * 删除t_yuntai_breakdown（云台缺销记录）信息
     * 
     * @param id t_yuntai_breakdown（云台缺销记录）ID
     * @return 结果
     */
    public int deleteTYuntaiBreakdownById(Long id);
}
