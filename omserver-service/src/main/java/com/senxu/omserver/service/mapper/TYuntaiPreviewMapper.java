package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiPreview;

/**
 * t_yuntai_preview（图片预览记录）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface TYuntaiPreviewMapper 
{
    /**
     * 查询t_yuntai_preview（图片预览记录）
     * 
     * @param id t_yuntai_preview（图片预览记录）ID
     * @return t_yuntai_preview（图片预览记录）
     */
    public TYuntaiPreview selectTYuntaiPreviewById(Long id);

    /**
     * 查询t_yuntai_preview（图片预览记录）列表
     * 
     * @param tYuntaiPreview t_yuntai_preview（图片预览记录）
     * @return t_yuntai_preview（图片预览记录）集合
     */
    public List<TYuntaiPreview> selectTYuntaiPreviewList(TYuntaiPreview tYuntaiPreview);

    /**
     * 新增t_yuntai_preview（图片预览记录）
     * 
     * @param tYuntaiPreview t_yuntai_preview（图片预览记录）
     * @return 结果
     */
    public int insertTYuntaiPreview(TYuntaiPreview tYuntaiPreview);

    /**
     * 修改t_yuntai_preview（图片预览记录）
     * 
     * @param tYuntaiPreview t_yuntai_preview（图片预览记录）
     * @return 结果
     */
    public int updateTYuntaiPreview(TYuntaiPreview tYuntaiPreview);

    /**
     * 删除t_yuntai_preview（图片预览记录）
     * 
     * @param id t_yuntai_preview（图片预览记录）ID
     * @return 结果
     */
    public int deleteTYuntaiPreviewById(Long id);

    /**
     * 批量删除t_yuntai_preview（图片预览记录）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiPreviewByIds(Long[] ids);



    public int isPreviewTYuntaiPreviewByIds(Long[] ids);

    public int isPreviewTYuntaiPreviewById(Long id);
}
