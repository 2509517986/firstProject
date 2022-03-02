package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TInspectionPicture;

/**
 * t_inspection_picture（巡检图片记录）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-09-14
 */
public interface TInspectionPictureMapper 
{
    /**
     * 查询t_inspection_picture（巡检图片记录）
     * 
     * @param id t_inspection_picture（巡检图片记录）ID
     * @return t_inspection_picture（巡检图片记录）
     */
    public TInspectionPicture selectTInspectionPictureById(Long id);

    /**
     * 查询t_inspection_picture（巡检图片记录）列表
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return t_inspection_picture（巡检图片记录）集合
     */
    public List<TInspectionPicture> selectTInspectionPictureList(TInspectionPicture tInspectionPicture);

    /**
     * 新增t_inspection_picture（巡检图片记录）
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return 结果
     */
    public int insertTInspectionPicture(TInspectionPicture tInspectionPicture);

    /**
     * 修改t_inspection_picture（巡检图片记录）
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return 结果
     */
    public int updateTInspectionPicture(TInspectionPicture tInspectionPicture);

    /**
     * 删除t_inspection_picture（巡检图片记录）
     * 
     * @param id t_inspection_picture（巡检图片记录）ID
     * @return 结果
     */
    public int deleteTInspectionPictureById(Long id);

    /**
     * 批量删除t_inspection_picture（巡检图片记录）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTInspectionPictureByIds(Long[] ids);
}
