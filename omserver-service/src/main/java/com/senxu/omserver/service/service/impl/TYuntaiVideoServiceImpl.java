package com.senxu.omserver.service.service.impl;

import java.util.Date;
import java.util.List;

import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiVideoMapper;
import com.senxu.omserver.service.domain.TYuntaiVideo;
import com.senxu.omserver.service.service.ITYuntaiVideoService;

/**
 * t_yuntai_video（云台短视频）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiVideoServiceImpl implements ITYuntaiVideoService 
{
    @Autowired
    private TYuntaiVideoMapper tYuntaiVideoMapper;

    /**
     * 查询t_yuntai_video（云台短视频）
     * 
     * @param id t_yuntai_video（云台短视频）ID
     * @return t_yuntai_video（云台短视频）
     */
    @Override
    public TYuntaiVideo selectTYuntaiVideoById(Long id)
    {
        return tYuntaiVideoMapper.selectTYuntaiVideoById(id);
    }

    /**
     * 查询t_yuntai_video（云台短视频）列表
     * 
     * @param tYuntaiVideo t_yuntai_video（云台短视频）
     * @return t_yuntai_video（云台短视频）
     */
    @Override
    public List<TYuntaiVideo> selectTYuntaiVideoList(TYuntaiVideo tYuntaiVideo)
    {
        //时间传入来的是类似2021-11-01 00:00:00.0(Timestamp)
        //但数据库有时分
        //tYuntaiVideo.setCaptureTime();
        Date nextDate=DateUtils.addDays(tYuntaiVideo.getCaptureTime(),1);
        tYuntaiVideo.setSpare0(DateUtils.parseDateToStr("yyyy-MM-dd",nextDate));
        return tYuntaiVideoMapper.selectTYuntaiVideoList(tYuntaiVideo);
    }

    /**
     * 新增t_yuntai_video（云台短视频）
     * 
     * @param tYuntaiVideo t_yuntai_video（云台短视频）
     * @return 结果
     */
    @Override
    public int insertTYuntaiVideo(TYuntaiVideo tYuntaiVideo)
    {
        return tYuntaiVideoMapper.insertTYuntaiVideo(tYuntaiVideo);
    }

    /**
     * 修改t_yuntai_video（云台短视频）
     * 
     * @param tYuntaiVideo t_yuntai_video（云台短视频）
     * @return 结果
     */
    @Override
    public int updateTYuntaiVideo(TYuntaiVideo tYuntaiVideo)
    {
        return tYuntaiVideoMapper.updateTYuntaiVideo(tYuntaiVideo);
    }

    /**
     * 批量删除t_yuntai_video（云台短视频）
     * 
     * @param ids 需要删除的t_yuntai_video（云台短视频）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiVideoByIds(Long[] ids)
    {
        return tYuntaiVideoMapper.deleteTYuntaiVideoByIds(ids);
    }

    /**
     * 删除t_yuntai_video（云台短视频）信息
     * 
     * @param id t_yuntai_video（云台短视频）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiVideoById(Long id)
    {
        return tYuntaiVideoMapper.deleteTYuntaiVideoById(id);
    }
}
