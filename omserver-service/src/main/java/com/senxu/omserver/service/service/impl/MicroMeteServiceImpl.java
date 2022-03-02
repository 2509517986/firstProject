package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.MicroMeteMapper;
import com.senxu.omserver.service.domain.MicroMete;
import com.senxu.omserver.service.service.IMicroMeteService;

/**
 * 气象信息获取Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-20
 */
@Service
public class MicroMeteServiceImpl implements IMicroMeteService 
{
    @Autowired
    private MicroMeteMapper microMeteMapper;

    /**
     * 查询气象信息获取
     * 
     * @param id 气象信息获取ID
     * @return 气象信息获取
     */
    @Override
    public MicroMete selectMicroMeteById(Integer id)
    {
        return microMeteMapper.selectMicroMeteById(id);
    }

    /**
     * 查询气象信息获取列表
     * 
     * @param microMete 气象信息获取
     * @return 气象信息获取
     */
    @Override
    public List<MicroMete> selectMicroMeteList(MicroMete microMete)
    {
        return microMeteMapper.selectMicroMeteList(microMete);
    }

    /**
     * 新增气象信息获取
     * 
     * @param microMete 气象信息获取
     * @return 结果
     */
    @Override
    public int insertMicroMete(MicroMete microMete)
    {
        return microMeteMapper.insertMicroMete(microMete);
    }

    /**
     * 修改气象信息获取
     * 
     * @param microMete 气象信息获取
     * @return 结果
     */
    @Override
    public int updateMicroMete(MicroMete microMete)
    {
        return microMeteMapper.updateMicroMete(microMete);
    }

    /**
     * 批量删除气象信息获取
     * 
     * @param ids 需要删除的气象信息获取ID
     * @return 结果
     */
    @Override
    public int deleteMicroMeteByIds(Integer[] ids)
    {
        return microMeteMapper.deleteMicroMeteByIds(ids);
    }

    /**
     * 删除气象信息获取信息
     * 
     * @param id 气象信息获取ID
     * @return 结果
     */
    @Override
    public int deleteMicroMeteById(Integer id)
    {
        return microMeteMapper.deleteMicroMeteById(id);
    }
}
