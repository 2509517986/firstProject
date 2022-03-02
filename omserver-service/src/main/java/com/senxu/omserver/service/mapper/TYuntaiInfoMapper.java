package com.senxu.omserver.service.mapper;

import java.util.List;

import com.senxu.omserver.service.domain.TLineInfo;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiStatistic;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.domain.vo.TYuntaiStatistic;
import org.apache.ibatis.annotations.Param;

/**
 * t_yuntai_info（云台信息）Mapper接口
 * 
 * @author ruoyi
 * @date 2021-06-03
 */
public interface TYuntaiInfoMapper 
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
     * 查询t_yuntai_info（云台信息）列表-按传入参数分页
     *
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return t_yuntai_info（云台信息）集合
     */
    public List<TYuntaiInfo> selectTYuntaiInfoListPage(TYuntaiInfo tYuntaiInfo);

    /**
     * 查询t_yuntai_info（云台信息）列表-合计数
     * @param tYuntaiInfo
     * @return
     */
    public int selectTYuntaiInfoCount(TYuntaiInfo tYuntaiInfo);

    /**
     * 查询t_yuntai_info（云台信息）统计数量
     * @param tYuntaiInfo
     * @return
     */
    public TYuntaiStatistic selectTYuntaiTotal(TYuntaiInfo tYuntaiInfo);


    /**
     * 根据TowerId查询该线路所包含的杆塔信息
     *
     * @param yuntaiInfo 线路ID
     * @return t_yuntai_info（杆塔信息）集合
     */
    public List<TYuntaiInfo> selectTYuntaiInfoListByTowerId(TYuntaiInfo yuntaiInfo);


    /**
     * 通过日期、线路id、获取统计所管辖云台设备
     * @param tYuntaiInfo
     * @return
     */
    public List<TInspectionYuntaiStatistic> yuntaiStatisticsByDate(TYuntaiInfo tYuntaiInfo);

    /**
     * 通过日期、线路id、获取统计所管辖云台设备
     * @param tYuntaiInfo
     * @return
     */
    public List<TInspectionYuntaiHiddenDanger> yuntaiHiddenDangerByDate(TYuntaiInfo tYuntaiInfo);

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
     * 删除t_yuntai_info（云台信息）
     * 
     * @param id t_yuntai_info（云台信息）ID
     * @return 结果
     */
    public int deleteTYuntaiInfoById(Long id);

    /**
     * 批量删除t_yuntai_info（云台信息）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiInfoByIds(Long[] ids);

}
