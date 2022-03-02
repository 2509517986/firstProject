package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiComprehensiveAntitheftMapper;
import com.senxu.omserver.service.domain.TYuntaiComprehensiveAntitheft;
import com.senxu.omserver.service.service.ITYuntaiComprehensiveAntitheftService;

/**
 * 综合防盗数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TYuntaiComprehensiveAntitheftServiceImpl implements ITYuntaiComprehensiveAntitheftService 
{
    @Autowired
    private TYuntaiComprehensiveAntitheftMapper tYuntaiComprehensiveAntitheftMapper;

    /**
     * 查询综合防盗数据
     * 
     * @param id 综合防盗数据ID
     * @return 综合防盗数据
     */
    @Override
    public TYuntaiComprehensiveAntitheft selectTYuntaiComprehensiveAntitheftById(Long id)
    {
        return tYuntaiComprehensiveAntitheftMapper.selectTYuntaiComprehensiveAntitheftById(id);
    }

    /**
     * 查询综合防盗数据列表
     * 
     * @param tYuntaiComprehensiveAntitheft 综合防盗数据
     * @return 综合防盗数据
     */
    @Override
    public List<TYuntaiComprehensiveAntitheft> selectTYuntaiComprehensiveAntitheftList(TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft)
    {
        return tYuntaiComprehensiveAntitheftMapper.selectTYuntaiComprehensiveAntitheftList(tYuntaiComprehensiveAntitheft);
    }

    /**
     * 新增综合防盗数据
     * 
     * @param tYuntaiComprehensiveAntitheft 综合防盗数据
     * @return 结果
     */
    @Override
    public int insertTYuntaiComprehensiveAntitheft(TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft)
    {
        return tYuntaiComprehensiveAntitheftMapper.insertTYuntaiComprehensiveAntitheft(tYuntaiComprehensiveAntitheft);
    }

    /**
     * 修改综合防盗数据
     * 
     * @param tYuntaiComprehensiveAntitheft 综合防盗数据
     * @return 结果
     */
    @Override
    public int updateTYuntaiComprehensiveAntitheft(TYuntaiComprehensiveAntitheft tYuntaiComprehensiveAntitheft)
    {
        return tYuntaiComprehensiveAntitheftMapper.updateTYuntaiComprehensiveAntitheft(tYuntaiComprehensiveAntitheft);
    }

    /**
     * 批量删除综合防盗数据
     * 
     * @param ids 需要删除的综合防盗数据ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiComprehensiveAntitheftByIds(Long[] ids)
    {
        return tYuntaiComprehensiveAntitheftMapper.deleteTYuntaiComprehensiveAntitheftByIds(ids);
    }

    /**
     * 删除综合防盗数据信息
     * 
     * @param id 综合防盗数据ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiComprehensiveAntitheftById(Long id)
    {
        return tYuntaiComprehensiveAntitheftMapper.deleteTYuntaiComprehensiveAntitheftById(id);
    }
}
