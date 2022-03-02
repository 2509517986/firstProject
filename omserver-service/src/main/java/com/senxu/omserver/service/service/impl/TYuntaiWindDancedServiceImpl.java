package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiWindDancedMapper;
import com.senxu.omserver.service.domain.TYuntaiWindDanced;
import com.senxu.omserver.service.service.ITYuntaiWindDancedService;

/**
 * 大风舞动报警Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TYuntaiWindDancedServiceImpl implements ITYuntaiWindDancedService 
{
    @Autowired
    private TYuntaiWindDancedMapper tYuntaiWindDancedMapper;

    /**
     * 查询大风舞动报警
     * 
     * @param id 大风舞动报警ID
     * @return 大风舞动报警
     */
    @Override
    public TYuntaiWindDanced selectTYuntaiWindDancedById(Long id)
    {
        return tYuntaiWindDancedMapper.selectTYuntaiWindDancedById(id);
    }

    /**
     * 查询大风舞动报警列表
     * 
     * @param tYuntaiWindDanced 大风舞动报警
     * @return 大风舞动报警
     */
    @Override
    public List<TYuntaiWindDanced> selectTYuntaiWindDancedList(TYuntaiWindDanced tYuntaiWindDanced)
    {
        return tYuntaiWindDancedMapper.selectTYuntaiWindDancedList(tYuntaiWindDanced);
    }

    /**
     * 新增大风舞动报警
     * 
     * @param tYuntaiWindDanced 大风舞动报警
     * @return 结果
     */
    @Override
    public int insertTYuntaiWindDanced(TYuntaiWindDanced tYuntaiWindDanced)
    {
        return tYuntaiWindDancedMapper.insertTYuntaiWindDanced(tYuntaiWindDanced);
    }

    /**
     * 修改大风舞动报警
     * 
     * @param tYuntaiWindDanced 大风舞动报警
     * @return 结果
     */
    @Override
    public int updateTYuntaiWindDanced(TYuntaiWindDanced tYuntaiWindDanced)
    {
        return tYuntaiWindDancedMapper.updateTYuntaiWindDanced(tYuntaiWindDanced);
    }

    /**
     * 批量删除大风舞动报警
     * 
     * @param ids 需要删除的大风舞动报警ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiWindDancedByIds(Long[] ids)
    {
        return tYuntaiWindDancedMapper.deleteTYuntaiWindDancedByIds(ids);
    }

    /**
     * 删除大风舞动报警信息
     * 
     * @param id 大风舞动报警ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiWindDancedById(Long id)
    {
        return tYuntaiWindDancedMapper.deleteTYuntaiWindDancedById(id);
    }
}
