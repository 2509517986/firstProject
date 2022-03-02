package com.senxu.omserver.service.service.impl;

import java.util.*;

import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TTowerInfoMapper;
import com.senxu.omserver.service.domain.TTowerInfo;
import com.senxu.omserver.service.service.ITTowerInfoService;

/**
 * t_tower_info（杆塔信息）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-02
 */
@Service
public class TTowerInfoServiceImpl implements ITTowerInfoService 
{
    @Autowired
    private TTowerInfoMapper tTowerInfoMapper;


    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService; //云台Service

    /**
     * 查询t_tower_info（杆塔信息）
     * 
     * @param id t_tower_info（杆塔信息）ID
     * @return t_tower_info（杆塔信息）
     */
    @Override
    public TTowerInfo selectTTowerInfoById(Long id)
    {
        return tTowerInfoMapper.selectTTowerInfoById(id);
    }

    /**
     * 查询t_tower_info（杆塔信息）列表
     * 
     * @param tTowerInfo t_tower_info（杆塔信息）
     * @return t_tower_info（杆塔信息）
     */
    @Override
    public List<TTowerInfo> selectTTowerInfoList(TTowerInfo tTowerInfo)
    {
        return tTowerInfoMapper.selectTTowerInfoList(tTowerInfo);
    }


    @Override
    public List<HashMap<String, Object>> selectTTowerInfoListByLineId(Long lineId,String towerName, TYuntaiInfo yuntai) {
        List<HashMap<String, Object>> towerMap = new ArrayList<HashMap<String, Object>>();//树状Map
        List<TTowerInfo> list=new ArrayList<TTowerInfo>();

        TTowerInfo towerInfo=new TTowerInfo();
        towerInfo.setLineId(lineId);
        towerInfo.setName(towerName);
        list=tTowerInfoMapper.selectTTowerInfoListByLineId(towerInfo);

        for(TTowerInfo tower:list){
            towerMap.add(new HashMap<String, Object>(){{
                TYuntaiInfo info =  yuntai;
                int i = 0;
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setTowerId(tower.getId());
                List<TYuntaiInfo> list=tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
                for(TYuntaiInfo tYuntaiInfo1 : list){
                    if(tYuntaiInfo1.getState() != -1 && tYuntaiInfo1.getState() != null){
                        i++;
                    }
                }
                put("id",tower.getId());
                put("icon","towerIcon");
                put("code",tower.getCode());
                put("name",tower.getName());
                put("parentId",lineId);
                put("type","tower");
                put("total",list.size());
                put("online",i);
                //经纬度
                String[] listPoint=new String[]{tower.getLongitude(),tower.getLatitude()};

                put("point",listPoint);
                put("children",tYuntaiInfoService.selectTYuntaiInfoListByTowerId(tower.getId(),info));

            }});
        }

        return towerMap;
    }

    @Override
    public List<HashMap<String, Object>> selectTTowerInfoListByLineId(Long lineId, Set<Long> towerInfos, List<TYuntaiInfo> yuntaiList) {
        List<HashMap<String, Object>> towerMap = new ArrayList<HashMap<String, Object>>();//树状Map
        List<TTowerInfo> list=new ArrayList<TTowerInfo>();
        List<TTowerInfo> towerInfolist=new ArrayList<TTowerInfo>();
        TTowerInfo towerInfo=new TTowerInfo();
        towerInfo.setLineId(lineId);
        list=tTowerInfoMapper.selectTTowerInfoListByLineId(towerInfo);
        for (TTowerInfo info : list){
            Iterator<Long> it = towerInfos.iterator();
            while (it.hasNext()) {
                if(it.next().equals(info.getId())){
                    towerInfolist.add(info);
                }
            }
        }
        for(TTowerInfo tower:towerInfolist){
            towerMap.add(new HashMap<String, Object>(){{
                int i = 0;
                TYuntaiInfo tYuntaiInfo = new TYuntaiInfo();
                tYuntaiInfo.setTowerId(tower.getId());
                List<TYuntaiInfo> list=tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
                for(TYuntaiInfo tYuntaiInfo1 : list){
                    if(tYuntaiInfo1.getState() != -1 && tYuntaiInfo1.getState() != null){
                        i++;
                    }
                }
                put("id",tower.getId());
                put("icon","towerIcon");
                put("code",tower.getCode());
                put("name",tower.getName());
                put("parentId",lineId);
                put("type","tower");
                put("total",list.size());
                put("online",i);
                //经纬度
                String[] listPoint=new String[]{tower.getLongitude(),tower.getLatitude()};

                put("point",listPoint);
                put("children",tYuntaiInfoService.selectTYuntaiInfoListByTowerId(tower.getId(),yuntaiList));

            }});
        }

        return towerMap;
    }

    @Override
    public List<TTowerInfo> selectTTowerInfoListByLineId(Long lineId) {
        TTowerInfo towerInfo=new TTowerInfo();
        towerInfo.setLineId(lineId);
        return tTowerInfoMapper.selectTTowerInfoListByLineId(towerInfo);
    }

    /**
     * 新增t_tower_info（杆塔信息）
     * 
     * @param tTowerInfo t_tower_info（杆塔信息）
     * @return 结果
     */
    @Override
    public int insertTTowerInfo(TTowerInfo tTowerInfo)
    {
        tTowerInfo.setCreateTime(DateUtils.getNowDate());
        return tTowerInfoMapper.insertTTowerInfo(tTowerInfo);
    }

    /**
     * 修改t_tower_info（杆塔信息）
     * 
     * @param tTowerInfo t_tower_info（杆塔信息）
     * @return 结果
     */
    @Override
    public int updateTTowerInfo(TTowerInfo tTowerInfo)
    {
        return tTowerInfoMapper.updateTTowerInfo(tTowerInfo);
    }

    /**
     * 批量删除t_tower_info（杆塔信息）
     * 
     * @param ids 需要删除的t_tower_info（杆塔信息）ID
     * @return 结果
     */
    @Override
    public int deleteTTowerInfoByIds(Long[] ids)
    {
        return tTowerInfoMapper.deleteTTowerInfoByIds(ids);
    }

    /**
     * 删除t_tower_info（杆塔信息）信息
     * 
     * @param id t_tower_info（杆塔信息）ID
     * @return 结果
     */
    @Override
    public int deleteTTowerInfoById(Long id)
    {
        return tTowerInfoMapper.deleteTTowerInfoById(id);
    }
}
