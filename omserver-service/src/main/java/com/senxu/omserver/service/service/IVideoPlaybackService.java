package com.senxu.omserver.service.service;

import com.senxu.omserver.service.domain.IVideoPlayback;

import java.util.List;

/**
 * t_video_playback（录像回放）对象 t_video_playback
 *
 * @author lvhp
 * @date 2021-10-29
 */
public interface IVideoPlaybackService {

    /**
     * 查询t_video_playback（录像回放）
     *
     * @param id t_video_playback（录像回放）ID
     * @return t_video_playback（录像回放）
     */
    public IVideoPlayback selectIVideoPlaybackById(Long id);

    /**
     * 查询t_video_playback（录像回放）列表
     *
     * @param IVideoPlayback t_video_playback（录像回放）
     * @return t_video_playback（录像回放）集合
     */
    public List<IVideoPlayback> selectIVideoPlaybackList(IVideoPlayback IVideoPlayback);


    /**
     * 新增t_video_playback（录像回放）
     *
     * @param IVideoPlayback t_video_playback（录像回放）
     * @return 结果
     */
    public int insertIVideoPlayback(IVideoPlayback IVideoPlayback);

    /**
     * 修改t_video_playback（录像回放）
     *
     * @param IVideoPlayback t_video_playback（录像回放）
     * @return 结果
     */
    public int updateIVideoPlayback(IVideoPlayback IVideoPlayback);

    /**
     * 删除t_video_playback（录像回放）信息
     *
     * @param id t_video_playback（录像回放）ID
     * @return 结果
     */
    public int deleteIVideoPlaybackById(Long id);
}
