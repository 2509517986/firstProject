package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiPictureMapper;
import com.senxu.omserver.service.domain.TYuntaiPicture;
import com.senxu.omserver.service.service.ITYuntaiPictureService;

/**
 * t_yuntai_picture（云台抓拍图片）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiPictureServiceImpl implements ITYuntaiPictureService 
{
    @Autowired
    private TYuntaiPictureMapper tYuntaiPictureMapper;

    /**
     * 查询t_yuntai_picture（云台抓拍图片）
     * 
     * @param id t_yuntai_picture（云台抓拍图片）ID
     * @return t_yuntai_picture（云台抓拍图片）
     */
    @Override
    public TYuntaiPicture selectTYuntaiPictureById(Long id)
    {
        return tYuntaiPictureMapper.selectTYuntaiPictureById(id);
    }

    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表
     * 
     * @param tYuntaiPicture t_yuntai_picture（云台抓拍图片）
     * @return t_yuntai_picture（云台抓拍图片）
     */
    @Override
    public List<TYuntaiPicture> selectTYuntaiPictureList(TYuntaiPicture tYuntaiPicture)
    {
        return tYuntaiPictureMapper.selectTYuntaiPictureList(tYuntaiPicture);
    }

    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表-今日抓拍查询
     *
     * @param tYuntaiPicture
     * @return t_yuntai_picture（云台抓拍图片）集合
     */
    @Override
    public List<TYuntaiPicture> selectTYuntaiPictureListByDay(TYuntaiPicture tYuntaiPicture) {
        return tYuntaiPictureMapper.selectTYuntaiPictureListByDay(tYuntaiPicture);
    }

    @Override
    public Integer selectTYuntaiPictureCount(TYuntaiPicture tYuntaiPicture) {
        return tYuntaiPictureMapper.selectTYuntaiPictureCount(tYuntaiPicture);
    }

    @Override
    public Integer selectTYuntaiPictureCount1(TYuntaiPicture tYuntaiPicture) {
        return tYuntaiPictureMapper.selectTYuntaiPictureCount1(tYuntaiPicture);
    }

    /**
     * 查询t_yuntai_picture（云台抓拍图片）列表-历史抓拍查询
     *
     * @param tYuntaiPicture
     * @return t_yuntai_picture（云台抓拍图片）集合
     */
    @Override
    public List<TYuntaiPicture> selectTYuntaiPictureListByHistory(TYuntaiPicture tYuntaiPicture) {
        return tYuntaiPictureMapper.selectTYuntaiPictureListByHistory(tYuntaiPicture);
    }

    /**
     * 新增t_yuntai_picture（云台抓拍图片）
     * 
     * @param tYuntaiPicture t_yuntai_picture（云台抓拍图片）
     * @return 结果
     */
    @Override
    public int insertTYuntaiPicture(TYuntaiPicture tYuntaiPicture)
    {
        return tYuntaiPictureMapper.insertTYuntaiPicture(tYuntaiPicture);
    }

    /**
     * 修改t_yuntai_picture（云台抓拍图片）
     * 
     * @param tYuntaiPicture t_yuntai_picture（云台抓拍图片）
     * @return 结果
     */
    @Override
    public int updateTYuntaiPicture(TYuntaiPicture tYuntaiPicture)
    {
        return tYuntaiPictureMapper.updateTYuntaiPicture(tYuntaiPicture);
    }

    /**
     * 批量删除t_yuntai_picture（云台抓拍图片）
     * 
     * @param ids 需要删除的t_yuntai_picture（云台抓拍图片）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiPictureByIds(Long[] ids)
    {
        return tYuntaiPictureMapper.deleteTYuntaiPictureByIds(ids);
    }

    /**
     * 删除t_yuntai_picture（云台抓拍图片）信息
     * 
     * @param id t_yuntai_picture（云台抓拍图片）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiPictureById(Long id)
    {
        return tYuntaiPictureMapper.deleteTYuntaiPictureById(id);
    }
}
