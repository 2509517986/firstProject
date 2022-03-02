package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiComprehensiveAntitheft;

/**
 * 综合防盗数据Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface TYuntaiComprehensiveAntitheftMapper 
{
    /**
     * 查询综合防盗数据
     * 
     * @param id 综合防盗数据ID
     * @return 综合防盗数据
     */
    public TYuntaiComprehensiveAntitheft selectTYuntaiComprehensiveAntitheftById(Long id);

    /**
     * 查询综合防盗数据列表
     * 
     * @param tYuntaiComprehensiveAntitheft 综合防盗数据
     * @return 综合防盗数据集合
     */
    public List<TYuntaiComprehensiveAntitheft> selectTYuntaiComprehensiveAntitheftList(TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft);

    /**
     * 新增综合防盗数据
     * 
     * @param tYuntaiComprehensiveAntitheft 综合防盗数据
     * @return 结果
     */
    public int insertTYuntaiComprehensiveAntitheft(TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft);

    /**
     * 修改综合防盗数据
     * 
     * @param tYuntaiComprehensiveAntitheft 综合防盗数据
     * @return 结果
     */
    public int updateTYuntaiComprehensiveAntitheft(TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft);

    /**
     * 删除综合防盗数据
     * 
     * @param id 综合防盗数据ID
     * @return 结果
     */
    public int deleteTYuntaiComprehensiveAntitheftById(Long id);

    /**
     * 批量删除综合防盗数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiComprehensiveAntitheftByIds(Long[] ids);
}
