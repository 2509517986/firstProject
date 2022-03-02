package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiConductorVibration;

/**
 * 导线振动采样Service接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface ITYuntaiConductorVibrationService 
{
    /**
     * 查询导线振动采样
     * 
     * @param id 导线振动采样ID
     * @return 导线振动采样
     */
    public TYuntaiConductorVibration selectTYuntaiConductorVibrationById(Long id);

    /**
     * 查询导线振动采样列表
     * 
     * @param tYuntaiConductorVibration 导线振动采样
     * @return 导线振动采样集合
     */
    public List<TYuntaiConductorVibration> selectTYuntaiConductorVibrationList(TYuntaiConductorVibration tYuntaiConductorVibration);

    /**
     * 新增导线振动采样
     * 
     * @param tYuntaiConductorVibration 导线振动采样
     * @return 结果
     */
    public int insertTYuntaiConductorVibration(TYuntaiConductorVibration tYuntaiConductorVibration);

    /**
     * 修改导线振动采样
     * 
     * @param tYuntaiConductorVibration 导线振动采样
     * @return 结果
     */
    public int updateTYuntaiConductorVibration(TYuntaiConductorVibration tYuntaiConductorVibration);

    /**
     * 批量删除导线振动采样
     * 
     * @param ids 需要删除的导线振动采样ID
     * @return 结果
     */
    public int deleteTYuntaiConductorVibrationByIds(Long[] ids);

    /**
     * 删除导线振动采样信息
     * 
     * @param id 导线振动采样ID
     * @return 结果
     */
    public int deleteTYuntaiConductorVibrationById(Long id);
}
