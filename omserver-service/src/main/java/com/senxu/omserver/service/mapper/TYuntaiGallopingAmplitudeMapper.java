package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiGallopingAmplitude;

/**
 * 舞动振幅频率数据Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface TYuntaiGallopingAmplitudeMapper 
{
    /**
     * 查询舞动振幅频率数据
     * 
     * @param id 舞动振幅频率数据ID
     * @return 舞动振幅频率数据
     */
    public TYuntaiGallopingAmplitude selectTYuntaiGallopingAmplitudeById(Long id);

    /**
     * 查询舞动振幅频率数据列表
     * 
     * @param tYuntaiGallopingAmplitude 舞动振幅频率数据
     * @return 舞动振幅频率数据集合
     */
    public List<TYuntaiGallopingAmplitude> selectTYuntaiGallopingAmplitudeList(TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude);

    /**
     * 新增舞动振幅频率数据
     * 
     * @param tYuntaiGallopingAmplitude 舞动振幅频率数据
     * @return 结果
     */
    public int insertTYuntaiGallopingAmplitude(TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude);

    /**
     * 修改舞动振幅频率数据
     * 
     * @param tYuntaiGallopingAmplitude 舞动振幅频率数据
     * @return 结果
     */
    public int updateTYuntaiGallopingAmplitude(TYuntaiGallopingAmplitude tYuntaiGallopingAmplitude);

    /**
     * 删除舞动振幅频率数据
     * 
     * @param id 舞动振幅频率数据ID
     * @return 结果
     */
    public int deleteTYuntaiGallopingAmplitudeById(Long id);

    /**
     * 批量删除舞动振幅频率数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiGallopingAmplitudeByIds(Long[] ids);
}
