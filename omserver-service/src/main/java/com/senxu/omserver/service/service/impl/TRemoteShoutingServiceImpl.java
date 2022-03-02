package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TRemoteShoutingMapper;
import com.senxu.omserver.service.domain.TRemoteShouting;
import com.senxu.omserver.service.service.ITRemoteShoutingService;

/**
 * t_remote_shouting（无人机远程喊话）Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
@Service
public class TRemoteShoutingServiceImpl implements ITRemoteShoutingService 
{
    @Autowired
    private TRemoteShoutingMapper tRemoteShoutingMapper;

    /**
     * 查询t_remote_shouting（无人机远程喊话）
     * 
     * @param shoutingId t_remote_shouting（无人机远程喊话）ID
     * @return t_remote_shouting（无人机远程喊话）
     */
    @Override
    public TRemoteShouting selectTRemoteShoutingById(Long shoutingId)
    {
        return tRemoteShoutingMapper.selectTRemoteShoutingById(shoutingId);
    }

    /**
     * 查询t_remote_shouting（无人机远程喊话）列表
     * 
     * @param tRemoteShouting t_remote_shouting（无人机远程喊话）
     * @return t_remote_shouting（无人机远程喊话）
     */
    @Override
    public List<TRemoteShouting> selectTRemoteShoutingList(TRemoteShouting tRemoteShouting)
    {
        return tRemoteShoutingMapper.selectTRemoteShoutingList(tRemoteShouting);
    }

    /**
     * 新增t_remote_shouting（无人机远程喊话）
     * 
     * @param tRemoteShouting t_remote_shouting（无人机远程喊话）
     * @return 结果
     */
    @Override
    public int insertTRemoteShouting(TRemoteShouting tRemoteShouting)
    {
        return tRemoteShoutingMapper.insertTRemoteShouting(tRemoteShouting);
    }

    /**
     * 修改t_remote_shouting（无人机远程喊话）
     * 
     * @param tRemoteShouting t_remote_shouting（无人机远程喊话）
     * @return 结果
     */
    @Override
    public int updateTRemoteShouting(TRemoteShouting tRemoteShouting)
    {
        return tRemoteShoutingMapper.updateTRemoteShouting(tRemoteShouting);
    }

    /**
     * 批量删除t_remote_shouting（无人机远程喊话）
     * 
     * @param shoutingIds 需要删除的t_remote_shouting（无人机远程喊话）ID
     * @return 结果
     */
    @Override
    public int deleteTRemoteShoutingByIds(Long[] shoutingIds)
    {
        return tRemoteShoutingMapper.deleteTRemoteShoutingByIds(shoutingIds);
    }

    /**
     * 删除t_remote_shouting（无人机远程喊话）信息
     * 
     * @param shoutingId t_remote_shouting（无人机远程喊话）ID
     * @return 结果
     */
    @Override
    public int deleteTRemoteShoutingById(Long shoutingId)
    {
        return tRemoteShoutingMapper.deleteTRemoteShoutingById(shoutingId);
    }
}
