package com.senxu.omserver.service.mapper;

import java.util.List;

import com.senxu.omserver.service.domain.TYuntaiAlarm;
import com.senxu.omserver.service.domain.TYuntaiPicture;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;

/**
 * t_yuntai_picture（云台抓拍图片）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface TYuntaiPictureMapper 
{
    /**
     * 查询t_yuntai_picture（云台抓拍图片）
     * 
     * @param id t_yuntai_picture（云台抓拍图片）ID
     * @return t_yuntai_picture（云台抓拍图片）
     */
    public TYuntaiPicture selectTYuntaiPictureById(Long id);

    /**
     * 获取历史图片
     *
     * @param tYuntaiPicture
     * @return t_yuntai_picture（云台抓拍图片）
     */
    public TYuntaiPicture selectHistoryPicture(TYuntaiPicture tYuntaiPicture);


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
     * 删除t_yuntai_picture（云台抓拍图片）
     * 
     * @param id t_yuntai_picture（云台抓拍图片）ID
     * @return 结果
     */
    public int deleteTYuntaiPictureById(Long id);

    /**
     * 批量删除t_yuntai_picture（云台抓拍图片）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiPictureByIds(Long[] ids);

    /**
     *  查询t_yuntai_picture（云台抓拍图片）列表-今日抓拍查询统计数量
     * @param tYuntaiPicture
     * @return
     */
    Integer selectTYuntaiPictureCount(TYuntaiPicture tYuntaiPicture);

    /**
     *  查询t_yuntai_picture（云台抓拍图片）列表-抓拍查询统计数量
     * @param tYuntaiPicture
     * @return
     */
    Integer selectTYuntaiPictureCount1(TYuntaiPicture tYuntaiPicture);

    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表，通过传入：线路id、巡检日期
     *
     * @param tYuntaiPicture
     * @return t_yuntai_picture（云台抓拍图片）集合
     */
    public List<TYuntaiPicture> findReportByDate(TYuntaiPicture tYuntaiPicture);

}
