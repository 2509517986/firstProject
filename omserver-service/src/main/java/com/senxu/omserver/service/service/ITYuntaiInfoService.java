package com.senxu.omserver.service.service;


import java.util.HashMap;
import java.util.List;

import com.senxu.omserver.service.domain.TLineInfo;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiStatistic;
import com.senxu.omserver.service.domain.vo.TYuntaiStatistic;

/**
 * t_yuntai_info（云台信息）Service接口
 * 
 * @author ruoyi
 * @date 2021-06-03
 */
public interface ITYuntaiInfoService 
{
    /**
     * 查询t_yuntai_info（云台信息）
     * 
     * @param id t_yuntai_info（云台信息）ID
     * @return t_yuntai_info（云台信息）
     */
    public TYuntaiInfo selectTYuntaiInfoById(Long id);

    /**
     * 查询t_yuntai_info（云台信息）
     *
     * @param code t_yuntai_info（云台信息）code
     * @return t_yuntai_info（云台信息）
     */
    public TYuntaiInfo selectTYuntaiInfoByCode(String code);


    /**
     * 查询t_yuntai_info（云台信息）列表
     *
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return t_yuntai_info（云台信息）集合
     */
    public List<TYuntaiInfo> selectTYuntaiInfoList(TYuntaiInfo tYuntaiInfo);


    /**
     * 查询t_yuntai_info（云台信息）列表--NWOMServer专用
     * 
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return t_yuntai_info（云台信息）集合
     */
    public List<TYuntaiInfo> selectTYuntaiInfoListHeartbeat(TYuntaiInfo tYuntaiInfo);

    /**
     * 查询t_yuntai_info（云台信息）列表
     *
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return t_yuntai_info（云台信息）集合
     */
    public List<TYuntaiInfo> selectTYuntaiInfoListPage(TYuntaiInfo tYuntaiInfo);


    /**
     * 查询t_yuntai_info（云台信息）分页合计数
     *
     * @param yuntaiInfo t_yuntai_info（云台信息）
     * @return TYuntaiStatistic  统计数量
     */
    public Integer selectTYuntaiCount(TYuntaiInfo yuntaiInfo);


    /**
     * 查询t_yuntai_info（云台信息）统计数量
     *
     * @param yuntaiInfo t_yuntai_info（云台信息）
     * @return TYuntaiStatistic  统计数量
     */
    public TYuntaiStatistic selectTYuntaiTotal(TYuntaiInfo yuntaiInfo);

    /**
     * 根据TowerId查询该杆塔所包含的云台信息
     *
     * @param yuntaiInfo 线路ID
     * @return t_yuntai_info（云台信息）集合
     */
    public List<HashMap<String, Object>> selectTYuntaiInfoListByTowerId(Long TowerId,TYuntaiInfo yuntaiInfo);

    /**
     * 根据TowerId查询该杆塔所包含的云台信息
     *
     * @param yuntaiInfo 线路ID
     * @return t_yuntai_info（云台信息）集合
     */
    public List<HashMap<String, Object>> selectTYuntaiInfoListByTowerId(Long TowerId,List<TYuntaiInfo> yuntaiList);



    /**
     * 新增t_yuntai_info（云台信息）
     * 
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return 结果
     */
    public int insertTYuntaiInfo(TYuntaiInfo tYuntaiInfo);

    /**
     * 修改t_yuntai_info（云台信息）
     * 
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return 结果
     */
    public int updateTYuntaiInfo(TYuntaiInfo tYuntaiInfo);

    /**
     * 批量删除t_yuntai_info（云台信息）
     * 
     * @param ids 需要删除的t_yuntai_info（云台信息）ID
     * @return 结果
     */
    public int deleteTYuntaiInfoByIds(Long[] ids);

    /**
     * 删除t_yuntai_info（云台信息）信息
     * 
     * @param id t_yuntai_info（云台信息）ID
     * @return 结果
     */
    public int deleteTYuntaiInfoById(Long id);

    /**
     * 通过日期、线路id、获取统计所管辖云台设备
     * @param lineIds 线路Id
     * @param towerIds  巡检报告Id
     * @return
     */
    public List<TInspectionYuntaiHiddenDanger> yuntaiHiddenDangerByDate(String lineIds,String towerIds);

    /**
     * 通过日期、线路id、获取统计所管辖云台设备
     * @param lineIds 线路Id
     * @param towerIds  巡检报告Id
     * @return
     */
    public List<TInspectionYuntaiStatistic> yuntaiStatisticsByDate(String lineIds,String towerIds);
}
