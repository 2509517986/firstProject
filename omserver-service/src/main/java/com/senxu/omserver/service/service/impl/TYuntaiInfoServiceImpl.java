package com.senxu.omserver.service.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TLineInfo;
import com.senxu.omserver.service.domain.TYuntaiBreakdown;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiHiddenDanger;
import com.senxu.omserver.service.domain.vo.TInspectionYuntaiStatistic;
import com.senxu.omserver.service.domain.vo.TYuntaiStatistic;
import com.senxu.omserver.service.mapper.TYuntaiBreakdownMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiInfoMapper;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;

/**
 * t_yuntai_info（云台信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-03
 */
@Service
public class TYuntaiInfoServiceImpl implements ITYuntaiInfoService 
{
    @Autowired(required = false)
    private TYuntaiInfoMapper tYuntaiInfoMapper;

    @Autowired(required = false)
    private TYuntaiBreakdownMapper tYuntaiBreakdownMapper;
    /**
     * 查询t_yuntai_info（云台信息）
     * 
     * @param id t_yuntai_info（云台信息）ID
     * @return t_yuntai_info（云台信息）
     */
    @Override
    public TYuntaiInfo selectTYuntaiInfoById(Long id)
    {
        return tYuntaiInfoMapper.selectTYuntaiInfoById(id);
    }

    @Override
    public TYuntaiInfo selectTYuntaiInfoByCode(String code) {
        return tYuntaiInfoMapper.selectTYuntaiInfoByCode(code);
    }


    /**
     * 查询t_yuntai_info（云台信息）列表
     *
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return t_yuntai_info（云台信息）
     */
    @Override
    public List<TYuntaiInfo> selectTYuntaiInfoList(TYuntaiInfo tYuntaiInfo)
    {
        List<TYuntaiInfo> list=tYuntaiInfoMapper.selectTYuntaiInfoList(tYuntaiInfo);
        int i=0;
        for(TYuntaiInfo info:list){
            TYuntaiBreakdown tYuntaiBreakdown =new TYuntaiBreakdown();
            tYuntaiBreakdown.setYuntaiId(info.getId());
            int missingCount=tYuntaiBreakdownMapper.selectTYuntaiBreakdownCount(tYuntaiBreakdown);
            list.get(i++).setMissingCount(missingCount);
        }

        return list;
    }


    /**
     * 查询t_yuntai_info（云台信息）列表
     * 
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return t_yuntai_info（云台信息）
     */
    @Override
    public List<TYuntaiInfo> selectTYuntaiInfoListHeartbeat(TYuntaiInfo tYuntaiInfo)
    {
        return tYuntaiInfoMapper.selectTYuntaiInfoListHeartbeat(tYuntaiInfo);
    }

    @Override
    public List<TYuntaiInfo> selectTYuntaiInfoListPage(TYuntaiInfo tYuntaiInfo) {
        return tYuntaiInfoMapper.selectTYuntaiInfoListPage(tYuntaiInfo);
    }

    @Override
    public Integer selectTYuntaiCount(TYuntaiInfo yuntaiInfo) {
        return tYuntaiInfoMapper.selectTYuntaiInfoCount(yuntaiInfo);
    }

    /**
     * 查询云台统计数量
     * @param yuntaiInfo t_yuntai_info（云台信息）
     * @return
     */
    @Override
    public TYuntaiStatistic selectTYuntaiTotal(TYuntaiInfo yuntaiInfo) {
        return tYuntaiInfoMapper.selectTYuntaiTotal(yuntaiInfo);
    }


    @Override
    public List<HashMap<String, Object>> selectTYuntaiInfoListByTowerId(Long TowerId,TYuntaiInfo tYuntaiInfo) {
        List<HashMap<String, Object>> yuntaiMap = new ArrayList<HashMap<String, Object>>();//树状Map
        TYuntaiInfo tYuntai = new TYuntaiInfo();
        if(tYuntaiInfo.getId() != null){
            tYuntai.setCode(tYuntaiInfo.getCode());
        }else {
            tYuntai.setTowerId(TowerId);
        }
        List<TYuntaiInfo> list=tYuntaiInfoMapper.selectTYuntaiInfoListByTowerId(tYuntai);
        for(TYuntaiInfo yuntai:list) {
            yuntaiMap.add(new HashMap<String, Object>() {{
                put("id", yuntai.getId());
                put("icon", "yuntaiIcon");
                put("code",yuntai.getCode());
                put("name", yuntai.getName());
                put("parentId", TowerId);
                put("type", "yuntai");
                put("state", yuntai.getState());
                put("electricPower", yuntai.getElectricPower());
                put("imageStatus", yuntai.getImageStatus());
                put("monitorType",yuntai.getMonitorType());
            }});
        }
        return yuntaiMap;
    }

    @Override
    public List<HashMap<String, Object>> selectTYuntaiInfoListByTowerId(Long TowerId,List<TYuntaiInfo> yuntaiList) {
        List<HashMap<String, Object>> yuntaiMap = new ArrayList<HashMap<String, Object>>();//树状Map
        TYuntaiInfo tYuntai = new TYuntaiInfo();
        tYuntai.setTowerId(TowerId);
        List<TYuntaiInfo> list=tYuntaiInfoMapper.selectTYuntaiInfoListByTowerId(tYuntai);
        for(TYuntaiInfo tYuntaiInfo : list){
            if(yuntaiList != null && yuntaiList.size() > 0){
                for (int i = 0; i <yuntaiList.size() ; i++) {
                    if(yuntaiList.get(i).getId().equals(tYuntaiInfo.getId())){
                        yuntaiMap.add(new HashMap<String, Object>() {{
                            put("id", tYuntaiInfo.getId());
                            put("icon", "yuntaiIcon");
                            put("code",tYuntaiInfo.getCode());
                            put("name", tYuntaiInfo.getName());
                            put("parentId", TowerId);
                            put("type", "yuntai");
                            put("state", tYuntaiInfo.getState());
                            put("electricPower", tYuntaiInfo.getElectricPower());
                            put("imageStatus", tYuntaiInfo.getImageStatus());
                            put("monitorType",tYuntaiInfo.getMonitorType());
                        }});
                    }
                }
            }else {
                    yuntaiMap.add(new HashMap<String, Object>() {{
                        put("id", tYuntaiInfo.getId());
                        put("icon", "yuntaiIcon");
                        put("code",tYuntaiInfo.getCode());
                        put("name", tYuntaiInfo.getName());
                        put("parentId", TowerId);
                        put("type", "yuntai");
                        put("state", tYuntaiInfo.getState());
                        put("electricPower", tYuntaiInfo.getElectricPower());
                        put("imageStatus", tYuntaiInfo.getImageStatus());
                        put("monitorType",tYuntaiInfo.getMonitorType());
                    }});
            }

        }
//        for(TYuntaiInfo yuntai:list) {
//            yuntaiMap.add(new HashMap<String, Object>() {{
//                put("id", yuntai.getId());
//                put("icon", "yuntaiIcon");
//                put("code",yuntai.getCode());
//                put("name", yuntai.getName());
//                put("parentId", TowerId);
//                put("type", "yuntai");
//                put("state", yuntai.getState());
//                put("electricPower", yuntai.getElectricPower());
//                put("imageStatus", yuntai.getImageStatus());
//                put("monitorType",yuntai.getMonitorType());
//            }});
//        }
        return yuntaiMap;
    }


    /**
     * 新增t_yuntai_info（云台信息）
     * 
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return 结果
     */
    @Override
    public int insertTYuntaiInfo(TYuntaiInfo tYuntaiInfo)
    {
        tYuntaiInfo.setCreateTime(DateUtils.getNowDate());
        return tYuntaiInfoMapper.insertTYuntaiInfo(tYuntaiInfo);
    }

    /**
     * 修改t_yuntai_info（云台信息）
     * 
     * @param tYuntaiInfo t_yuntai_info（云台信息）
     * @return 结果
     */
    @Override
    public int updateTYuntaiInfo(TYuntaiInfo tYuntaiInfo)
    {
        return tYuntaiInfoMapper.updateTYuntaiInfo(tYuntaiInfo);
    }

    /**
     * 批量删除t_yuntai_info（云台信息）
     * 
     * @param ids 需要删除的t_yuntai_info（云台信息）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiInfoByIds(Long[] ids)
    {
        return tYuntaiInfoMapper.deleteTYuntaiInfoByIds(ids);
    }

    /**
     * 删除t_yuntai_info（云台信息）信息
     * 
     * @param id t_yuntai_info（云台信息）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiInfoById(Long id)
    {
        return tYuntaiInfoMapper.deleteTYuntaiInfoById(id);
    }


    @Override
    public List<TInspectionYuntaiHiddenDanger> yuntaiHiddenDangerByDate(String lineIds,String towerIds) {
         return tYuntaiInfoMapper.yuntaiHiddenDangerByDate(new TYuntaiInfo(lineIds,towerIds));
    }

    @Override
    public List<TInspectionYuntaiStatistic> yuntaiStatisticsByDate(String lineIds,String towerIds) {
        return tYuntaiInfoMapper.yuntaiStatisticsByDate(new TYuntaiInfo(lineIds,towerIds));
    }
}
