package com.senxu.omserver.service.service.impl;

import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.IVideoPlayback;
import com.senxu.omserver.service.mapper.IVideoPlaybackMapper;
import com.senxu.omserver.service.service.IVideoPlaybackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * t_video_playback（录像回放）对象 t_video_playback
 *
 * @author lvhp
 * @date 2021-10-25
 */
@Service
public class IVideoPlaybackServiceImpl implements IVideoPlaybackService {

    @Autowired(required = false)
    IVideoPlaybackMapper IVideoPlaybackMapper;

    /**
     * 查询t_video_playback（录像回放信息）
     *
     * @param id t_video_playback（录像回放信息）ID
     * @return t_video_playback（录像回放信息）
     */
    @Override
    public IVideoPlayback selectIVideoPlaybackById(Long id) {
        return IVideoPlaybackMapper.selectIVideoPlaybackById(id);
    }

    /**
     * 查询t_video_playback（录像回放信息）列表
     *
     * @param IVideoPlayback t_video_playback（录像回放信息）
     * @return t_video_playback（录像回放信息）
     */
    @Override
    public List<IVideoPlayback> selectIVideoPlaybackList(IVideoPlayback IVideoPlayback) {
        return IVideoPlaybackMapper.selectIVideoPlaybackList(IVideoPlayback);
    }

    /**
     * 新增t_video_playback（录像回放信息）
     *
     * @param IVideoPlayback t_video_playback（录像回放信息）
     * @return 结果
     */
    @Override
    public int insertIVideoPlayback(IVideoPlayback IVideoPlayback) {
        IVideoPlayback.setCreateTime(DateUtils.getNowDate());
        return IVideoPlaybackMapper.insertIVideoPlayback(IVideoPlayback);
    }

    /**
     * 修改t_video_playback（录像回放信息）
     *
     * @param IVideoPlayback t_video_playback（录像回放信息）
     * @return 结果
     */
    @Override
    public int updateIVideoPlayback(IVideoPlayback IVideoPlayback) {
        return IVideoPlaybackMapper.updateIVideoPlayback(IVideoPlayback);
    }

    /**
     * 删除t_video_playback（录像回放信息）信息
     *
     * @param id t_video_playback（录像回放信息）ID
     * @return 结果
     */
    @Override
    public int deleteIVideoPlaybackById(Long id) {
        return IVideoPlaybackMapper.deleteIVideoPlaybackById(id);
    }
}
