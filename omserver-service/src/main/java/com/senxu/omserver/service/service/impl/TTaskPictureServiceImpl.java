package com.senxu.omserver.service.service.impl;

import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TTaskPictureMapper;
import com.senxu.omserver.service.domain.TTaskPicture;
import com.senxu.omserver.service.service.ITTaskPictureService;

/**
 * t_task_picture（无人机任务巡检图片）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TTaskPictureServiceImpl implements ITTaskPictureService 
{
    @Autowired
    private TTaskPictureMapper tTaskPictureMapper;

    /**
     * 查询t_task_picture（无人机任务巡检图片）
     * 
     * @param imageId t_task_picture（无人机任务巡检图片）ID
     * @return t_task_picture（无人机任务巡检图片）
     */
    @Override
    public TTaskPicture selectTTaskPictureById(Long imageId)
    {
        return tTaskPictureMapper.selectTTaskPictureById(imageId);
    }

    /**
     * 查询t_task_picture（无人机任务巡检图片）列表
     * 
     * @param tTaskPicture t_task_picture（无人机任务巡检图片）
     * @return t_task_picture（无人机任务巡检图片）
     */
    @Override
    public List<TTaskPicture> selectTTaskPictureList(TTaskPicture tTaskPicture)
    {
        return tTaskPictureMapper.selectTTaskPictureList(tTaskPicture);
    }

    /**
     * 新增t_task_picture（无人机任务巡检图片）
     * 
     * @param tTaskPicture t_task_picture（无人机任务巡检图片）
     * @return 结果
     */
    @Override
    public int insertTTaskPicture(TTaskPicture tTaskPicture)
    {
        return tTaskPictureMapper.insertTTaskPicture(tTaskPicture);
    }

    /**
     * 修改t_task_picture（无人机任务巡检图片）
     * 
     * @param tTaskPicture t_task_picture（无人机任务巡检图片）
     * @return 结果
     */
    @Override
    public int updateTTaskPicture(TTaskPicture tTaskPicture)
    {
        return tTaskPictureMapper.updateTTaskPicture(tTaskPicture);
    }

    /**
     * 批量删除t_task_picture（无人机任务巡检图片）
     * 
     * @param imageIds 需要删除的t_task_picture（无人机任务巡检图片）ID
     * @return 结果
     */
    @Override
    public int deleteTTaskPictureByIds(Long[] imageIds)
    {
        return tTaskPictureMapper.deleteTTaskPictureByIds(imageIds);
    }

    /**
     * 删除t_task_picture（无人机任务巡检图片）信息
     * 
     * @param imageId t_task_picture（无人机任务巡检图片）ID
     * @return 结果
     */
    @Override
    public int deleteTTaskPictureById(Long imageId)
    {
        return tTaskPictureMapper.deleteTTaskPictureById(imageId);
    }
}
