package com.senxu.omserver.service.service;

import java.util.List;
import com.senxu.omserver.service.domain.TLineInfo;

/**
 * t_line_info（线路信息）Service接口
 * 
 * @author ruoyi
 * @date 2021-05-26
 */
public interface ITLineInfoService 
{
    /**
     * 查询t_line_info（线路信息）
     * 
     * @param id t_line_info（线路信息）ID
     * @return t_line_info（线路信息）
     */
    public TLineInfo selectTLineInfoById(Long id);

    /**
     * 查询t_line_info（线路信息）列表
     * 
     * @param tLineInfo t_line_info（线路信息）
     * @return t_line_info（线路信息）集合
     */
    public List<TLineInfo> selectTLineInfoList(TLineInfo tLineInfo);


    /**
     * 新增t_line_info（线路信息）
     * 
     * @param tLineInfo t_line_info（线路信息）
     * @return 结果
     */
    public int insertTLineInfo(TLineInfo tLineInfo);

    /**
     * 修改t_line_info（线路信息）
     * 
     * @param tLineInfo t_line_info（线路信息）
     * @return 结果
     */
    public int updateTLineInfo(TLineInfo tLineInfo);

    /**
     * 批量删除t_line_info（线路信息）
     * 
     * @param ids 需要删除的t_line_info（线路信息）ID
     * @return 结果
     */
    public int deleteTLineInfoByIds(Long[] ids);

    /**
     * 删除t_line_info（线路信息）信息
     * 
     * @param id t_line_info（线路信息）ID
     * @return 结果
     */
    public int deleteTLineInfoById(Long id);
}
