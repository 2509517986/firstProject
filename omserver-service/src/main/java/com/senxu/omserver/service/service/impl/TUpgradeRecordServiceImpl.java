package com.senxu.omserver.service.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TUpgradeRecordMapper;
import com.senxu.omserver.service.domain.TUpgradeRecord;
import com.senxu.omserver.service.service.ITUpgradeRecordService;

/**
 * 【设备升级记录】Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-30
 */
@Service
public class TUpgradeRecordServiceImpl implements ITUpgradeRecordService 
{
    @Autowired(required = false)
    private TUpgradeRecordMapper tUpgradeRecordMapper;

    /**
     * 查询【设备升级记录】
     * 
     * @param id 【设备升级记录】ID
     * @return 【设备升级记录】
     */
    @Override
    public TUpgradeRecord selectTUpgradeRecordById(Long id)
    {
        return tUpgradeRecordMapper.selectTUpgradeRecordById(id);
    }

    /**
     * 查询【设备升级记录】
     *
     * @param yuntaiId 【设备升级记录】ID
     * @return 【设备升级记录】
     */
    @Override
    public TUpgradeRecord selectTUpgradeRecordByYuntaiId(Long yuntaiId)
    {
        return tUpgradeRecordMapper.selectTUpgradeRecordById(yuntaiId);
    }

    /**
     * 查询【设备升级记录】列表
     * 
     * @param tUpgradeRecord 【设备升级记录】
     * @return 【设备升级记录】
     */
    @Override
    public List<TUpgradeRecord> selectTUpgradeRecordList(TUpgradeRecord tUpgradeRecord)
    {
        return tUpgradeRecordMapper.selectTUpgradeRecordList(tUpgradeRecord);
    }

    /**
     * 新增【设备升级记录】
     * 
     * @param tUpgradeRecord 【设备升级记录】
     * @return 结果
     */
    @Override
    public int insertTUpgradeRecord(TUpgradeRecord tUpgradeRecord)
    {
        return tUpgradeRecordMapper.insertTUpgradeRecord(tUpgradeRecord);
    }

    /**
     * 修改【设备升级记录】
     * 
     * @param tUpgradeRecord 【设备升级记录】
     * @return 结果
     */
    @Override
    public int updateTUpgradeRecord(TUpgradeRecord tUpgradeRecord)
    {
        return tUpgradeRecordMapper.updateTUpgradeRecord(tUpgradeRecord);
    }

    /**
     * 批量删除【设备升级记录】
     * 
     * @param ids 需要删除的【设备升级记录】ID
     * @return 结果
     */
    @Override
    public int deleteTUpgradeRecordByIds(Long[] ids)
    {
        return tUpgradeRecordMapper.deleteTUpgradeRecordByIds(ids);
    }

    /**
     * 删除【设备升级记录】信息
     * 
     * @param id 【设备升级记录】ID
     * @return 结果
     */
    @Override
    public int deleteTUpgradeRecordById(Long id)
    {
        return tUpgradeRecordMapper.deleteTUpgradeRecordById(id);
    }
}
