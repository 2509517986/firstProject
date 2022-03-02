package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiPicture;

/**
 * t_yuntai_picture（云台抓拍图片）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface ITYuntaiPictureService 
{
    /**
     * 查询t_yuntai_picture（云台抓拍图片）
     * 
     * @param id t_yuntai_picture（云台抓拍图片）ID
     * @return t_yuntai_picture（云台抓拍图片）
     */
    public TYuntaiPicture selectTYuntaiPictureById(Long id);

    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表
     * 
     * @param tYuntaiPicture t_yuntai_picture（云台抓拍图片）
     * @return t_yuntai_picture（云台抓拍图片）集合
     */
    public List<TYuntaiPicture> selectTYuntaiPictureList(TYuntaiPicture tYuntaiPicture);


    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表-今日抓拍查询
     *
     * @param tYuntaiPicture
     * @return t_yuntai_picture（云台抓拍图片）集合
     */
    public List<TYuntaiPicture> selectTYuntaiPictureListByDay(TYuntaiPicture tYuntaiPicture);

    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表-今日抓拍查询合计数量
     *
     * @param tYuntaiPicture
     * @return
     */
    public Integer selectTYuntaiPictureCount(TYuntaiPicture tYuntaiPicture);

    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表-今日抓拍查询合计数量
     *
     * @param tYuntaiPicture
     * @return
     */
    public Integer selectTYuntaiPictureCount1(TYuntaiPicture tYuntaiPicture);


    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表-历史抓拍查询
     *
     * @param tYuntaiPicture
     * @return t_yuntai_picture（云台抓拍图片）集合
     */
    public List<TYuntaiPicture> selectTYuntaiPictureListByHistory(TYuntaiPicture tYuntaiPicture);



    /**
     * 新增t_yuntai_picture（云台抓拍图片）
     * 
     * @param tYuntaiPicture t_yuntai_picture（云台抓拍图片）
     * @return 结果
     */
    public int insertTYuntaiPicture(TYuntaiPicture tYuntaiPicture);

    /**
     * 修改t_yuntai_picture（云台抓拍图片）
     * 
     * @param tYuntaiPicture t_yuntai_picture（云台抓拍图片）
     * @return 结果
     */
    public int updateTYuntaiPicture(TYuntaiPicture tYuntaiPicture);

    /**
     * 批量删除t_yuntai_picture（云台抓拍图片）
     * 
     * @param ids 需要删除的t_yuntai_picture（云台抓拍图片）ID
     * @return 结果
     */
    public int deleteTYuntaiPictureByIds(Long[] ids);

    /**
     * 删除t_yuntai_picture（云台抓拍图片）信息
     * 
     * @param id t_yuntai_picture（云台抓拍图片）ID
     * @return 结果
     */
    public int deleteTYuntaiPictureById(Long id);
}
