package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TLineInfoMapper;
import com.senxu.omserver.service.domain.TLineInfo;
import com.senxu.omserver.service.service.ITLineInfoService;

/**
 * t_line_info（线路信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-05-26
 */
@Service
public class TLineInfoServiceImpl implements ITLineInfoService 
{
    @Autowired
    private TLineInfoMapper tLineInfoMapper;

    /**
     * 查询t_line_info（线路信息）
     * 
     * @param id t_line_info（线路信息）ID
     * @return t_line_info（线路信息）
     */
    @Override
    public TLineInfo selectTLineInfoById(Long id)
    {
        return tLineInfoMapper.selectTLineInfoById(id);
    }

    /**
     * 查询t_line_info（线路信息）列表
     * 
     * @param tLineInfo t_line_info（线路信息）
     * @return t_line_info（线路信息）
     */
    @Override
    public List<TLineInfo> selectTLineInfoList(TLineInfo tLineInfo)
    {
        return tLineInfoMapper.selectTLineInfoList(tLineInfo);
    }

    /**
     * 新增t_line_info（线路信息）
     * 
     * @param tLineInfo t_line_info（线路信息）
     * @return 结果
     */
    @Override
    public int insertTLineInfo(TLineInfo tLineInfo)
    {
        tLineInfo.setCreateTime(DateUtils.getNowDate());
        return tLineInfoMapper.insertTLineInfo(tLineInfo);
    }

    /**
     * 修改t_line_info（线路信息）
     * 
     * @param tLineInfo t_line_info（线路信息）
     * @return 结果
     */
    @Override
    public int updateTLineInfo(TLineInfo tLineInfo)
    {
        return tLineInfoMapper.updateTLineInfo(tLineInfo);
    }

    /**
     * 批量删除t_line_info（线路信息）
     * 
     * @param ids 需要删除的t_line_info（线路信息）ID
     * @return 结果
     */
    @Override
    public int deleteTLineInfoByIds(Long[] ids)
    {
        return tLineInfoMapper.deleteTLineInfoByIds(ids);
    }

    /**
     * 删除t_line_info（线路信息）信息
     * 
     * @param id t_line_info（线路信息）ID
     * @return 结果
     */
    @Override
    public int deleteTLineInfoById(Long id)
    {
        return tLineInfoMapper.deleteTLineInfoById(id);
    }
}
