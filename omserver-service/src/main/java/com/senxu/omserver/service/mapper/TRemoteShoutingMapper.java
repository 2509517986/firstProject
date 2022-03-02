package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TRemoteShouting;

/**
 * t_remote_shouting（无人机远程喊话）Mapper接口
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public interface TRemoteShoutingMapper 
{
    /**
     * 查询t_remote_shouting（无人机远程喊话）
     * 
     * @param shoutingId t_remote_shouting（无人机远程喊话）ID
     * @return t_remote_shouting（无人机远程喊话）
     */
    public TRemoteShouting selectTRemoteShoutingById(Long shoutingId);

    /**
     * 查询t_remote_shouting（无人机远程喊话）列表
     * 
     * @param tRemoteShouting t_remote_shouting（无人机远程喊话）
     * @return t_remote_shouting（无人机远程喊话）集合
     */
    public List<TRemoteShouting> selectTRemoteShoutingList(TRemoteShouting tRemoteShouting);

    /**
     * 新增t_remote_shouting（无人机远程喊话）
     * 
     * @param tRemoteShouting t_remote_shouting（无人机远程喊话）
     * @return 结果
     */
    public int insertTRemoteShouting(TRemoteShouting tRemoteShouting);

    /**
     * 修改t_remote_shouting（无人机远程喊话）
     * 
     * @param tRemoteShouting t_remote_shouting（无人机远程喊话）
     * @return 结果
     */
    public int updateTRemoteShouting(TRemoteShouting tRemoteShouting);

    /**
     * 删除t_remote_shouting（无人机远程喊话）
     * 
     * @param shoutingId t_remote_shouting（无人机远程喊话）ID
     * @return 结果
     */
    public int deleteTRemoteShoutingById(Long shoutingId);

    /**
     * 批量删除t_remote_shouting（无人机远程喊话）
     * 
     * @param shoutingIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteTRemoteShoutingByIds(Long[] shoutingIds);
}
