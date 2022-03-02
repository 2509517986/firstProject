package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiVideo;

/**
 * t_yuntai_video（云台短视频）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
public interface ITYuntaiVideoService 
{
    /**
     * 查询t_yuntai_video（云台短视频）
     * 
     * @param id t_yuntai_video（云台短视频）ID
     * @return t_yuntai_video（云台短视频）
     */
    public TYuntaiVideo selectTYuntaiVideoById(Long id);

    /**
     * 查询t_yuntai_video（云台短视频）列表
     * 
     * @param tYuntaiVideo t_yuntai_video（云台短视频）
     * @return t_yuntai_video（云台短视频）集合
     */
    public List<TYuntaiVideo> selectTYuntaiVideoList(TYuntaiVideo tYuntaiVideo);

    /**
     * 新增t_yuntai_video（云台短视频）
     * 
     * @param tYuntaiVideo t_yuntai_video（云台短视频）
     * @return 结果
     */
    public int insertTYuntaiVideo(TYuntaiVideo tYuntaiVideo);

    /**
     * 修改t_yuntai_video（云台短视频）
     * 
     * @param tYuntaiVideo t_yuntai_video（云台短视频）
     * @return 结果
     */
    public int updateTYuntaiVideo(TYuntaiVideo tYuntaiVideo);

    /**
     * 批量删除t_yuntai_video（云台短视频）
     * 
     * @param ids 需要删除的t_yuntai_video（云台短视频）ID
     * @return 结果
     */
    public int deleteTYuntaiVideoByIds(Long[] ids);

    /**
     * 删除t_yuntai_video（云台短视频）信息
     * 
     * @param id t_yuntai_video（云台短视频）ID
     * @return 结果
     */
    public int deleteTYuntaiVideoById(Long id);
}
