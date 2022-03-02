package com.senxu.omserver.service.service.impl;

import java.util.List;

import com.senxu.omserver.service.domain.TYuntaiGallopingAmplitude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiGallopingAmplitudeMapper;
import com.senxu.omserver.service.service.ITYuntaiGallopingAmplitudeService;

/**
 * 舞动振幅频率数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
@Service
public class TYuntaiGallopingAmplitudeServiceImpl implements ITYuntaiGallopingAmplitudeService 
{
    @Autowired
    private TYuntaiGallopingAmplitudeMapper tYuntaiGallopingAmplitudeMapper;

    /**
     * 查询舞动振幅频率数据
     * 
     * @param id 舞动振幅频率数据ID
     * @return 舞动振幅频率数据
     */
    @Override
    public TYuntaiGallopingAmplitude selectTYuntaiGallopingAmplitudeById(Long id)
    {
        return tYuntaiGallopingAmplitudeMapper.selectTYuntaiGallopingAmplitudeById(id);
    }

    /**
     * 查询舞动振幅频率数据列表
     * 
     * @param tYuntaiGallopingAmplitude 舞动振幅频率数据
     * @return 舞动振幅频率数据
     */
    @Override
    public List<TYuntaiGallopingAmplitude> selectTYuntaiGallopingAmplitudeList(TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude)
    {
        return tYuntaiGallopingAmplitudeMapper.selectTYuntaiGallopingAmplitudeList(tYuntaiGallopingAmplitude);
    }

    /**
     * 新增舞动振幅频率数据
     * 
     * @param tYuntaiGallopingAmplitude 舞动振幅频率数据
     * @return 结果
     */
    @Override
    public int insertTYuntaiGallopingAmplitude(TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude)
    {
        return tYuntaiGallopingAmplitudeMapper.insertTYuntaiGallopingAmplitude(tYuntaiGallopingAmplitude);
    }

    /**
     * 修改舞动振幅频率数据
     * 
     * @param tYuntaiGallopingAmplitude 舞动振幅频率数据
     * @return 结果
     */
    @Override
    public int updateTYuntaiGallopingAmplitude(TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude)
    {
        return tYuntaiGallopingAmplitudeMapper.updateTYuntaiGallopingAmplitude(tYuntaiGallopingAmplitude);
    }

    /**
     * 批量删除舞动振幅频率数据
     * 
     * @param ids 需要删除的舞动振幅频率数据ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiGallopingAmplitudeByIds(Long[] ids)
    {
        return tYuntaiGallopingAmplitudeMapper.deleteTYuntaiGallopingAmplitudeByIds(ids);
    }

    /**
     * 删除舞动振幅频率数据信息
     * 
     * @param id 舞动振幅频率数据ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiGallopingAmplitudeById(Long id)
    {
        return tYuntaiGallopingAmplitudeMapper.deleteTYuntaiGallopingAmplitudeById(id);
    }
}
