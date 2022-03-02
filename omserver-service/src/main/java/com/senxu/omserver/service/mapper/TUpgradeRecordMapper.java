package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TUpgradeRecord;

/**
 * 【设备升级记录】Mapper接口
 * 
 * @author ruoyi
 * @date 2021-10-30
 */
public interface TUpgradeRecordMapper 
{
    /**
     * 查询【设备升级记录】
     * 
     * @param id 【设备升级记录】ID
     * @return 【设备升级记录】
     */
    public TUpgradeRecord selectTUpgradeRecordById(Long id);

    /**
     * 查询【设备升级记录】
     *
     * @param yuntaiId 【设备升级记录】ID
     * @return 【设备升级记录】
     */
    public TUpgradeRecord selectTUpgradeRecordByYuntaiId(Long yuntaiId);

    /**
     * 查询【设备升级记录】列表
     * 
     * @param tUpgradeRecord 【设备升级记录】
     * @return 【设备升级记录】集合
     */
    public List<TUpgradeRecord> selectTUpgradeRecordList(TUpgradeRecord tUpgradeRecord);

    /**
     * 新增【设备升级记录】
     * 
     * @param tUpgradeRecord 【设备升级记录】
     * @return 结果
     */
    public int insertTUpgradeRecord(TUpgradeRecord tUpgradeRecord);

    /**
     * 修改【设备升级记录】
     * 
     * @param tUpgradeRecord 【设备升级记录】
     * @return 结果
     */
    public int updateTUpgradeRecord(TUpgradeRecord tUpgradeRecord);

    /**
     * 删除【设备升级记录】
     * 
     * @param id 【设备升级记录】ID
     * @return 结果
     */
    public int deleteTUpgradeRecordById(Long id);

    /**
     * 批量删除【设备升级记录】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTUpgradeRecordByIds(Long[] ids);
}
