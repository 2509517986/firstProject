package com.senxu.omserver.service.service;

import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.service.domain.TYuntaiPreview;

/**
 * t_yuntai_preview（图片预览记录）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface ITYuntaiPreviewService 
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
     * 批量删除t_yuntai_preview（图片预览记录）
     * 
     * @param ids 需要删除的t_yuntai_preview（图片预览记录）ID
     * @return 结果
     */
    public int deleteTYuntaiPreviewByIds(Long[] ids);

    /**
     * 删除t_yuntai_preview（图片预览记录）信息
     * 
     * @param id t_yuntai_preview（图片预览记录）ID
     * @return 结果
     */
    public int deleteTYuntaiPreviewById(Long id);


    /**
     * 标识已预览t_yuntai_preview（图片预览记录）
     *
     * @param ids 标识已预览的图片
     * @return 结果
     */
    public int isPreviewTYuntaiPreviewByIds(Long[] ids, LoginUser user);


    /**
     * 标识已预览t_yuntai_preview（图片预览记录）
     *
     * @param id 标识已预览的图片
     * @return 结果
     */
    public int isPreviewTYuntaiPreviewById(Long id,LoginUser user);
}
