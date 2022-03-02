package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TTaskPicture;
import org.springframework.stereotype.Repository;

/**
 * t_task_picture（无人机任务巡检图片）Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Repository
public interface TTaskPictureMapper 
{
    /**
     * 查询t_task_picture（无人机任务巡检图片）
     * 
     * @param imageId t_task_picture（无人机任务巡检图片）ID
     * @return t_task_picture（无人机任务巡检图片）
     */
    public TTaskPicture selectTTaskPictureById(Long imageId);

    /**
     * 查询t_task_picture（无人机任务巡检图片）列表
     * 
     * @param tTaskPicture t_task_picture（无人机任务巡检图片）
     * @return t_task_picture（无人机任务巡检图片）集合
     */
    public List<TTaskPicture> selectTTaskPictureList(TTaskPicture tTaskPicture);

    /**
     * 新增t_task_picture（无人机任务巡检图片）
     * 
     * @param tTaskPicture t_task_picture（无人机任务巡检图片）
     * @return 结果
     */
    public int insertTTaskPicture(TTaskPicture tTaskPicture);

    /**
     * 修改t_task_picture（无人机任务巡检图片）
     * 
     * @param tTaskPicture t_task_picture（无人机任务巡检图片）
     * @return 结果
     */
    public int updateTTaskPicture(TTaskPicture tTaskPicture);

    /**
     * 删除t_task_picture（无人机任务巡检图片）
     * 
     * @param imageId t_task_picture（无人机任务巡检图片）ID
     * @return 结果
     */
    public int deleteTTaskPictureById(Long imageId);

    /**
     * 批量删除t_task_picture（无人机任务巡检图片）
     * 
     * @param imageIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTTaskPictureByIds(Long[] imageIds);
}
