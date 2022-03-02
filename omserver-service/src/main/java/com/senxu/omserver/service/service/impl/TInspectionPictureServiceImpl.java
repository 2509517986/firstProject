package com.senxu.omserver.service.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.senxu.omserver.common.core.domain.entity.SysDictData;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.DictUtils;
import com.senxu.omserver.common.utils.StringUtils;
import com.senxu.omserver.service.domain.*;
import com.senxu.omserver.service.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.service.ITInspectionPictureService;

/**
 * t_inspection_picture（巡检图片记录）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-09-14
 */
@Service
public class TInspectionPictureServiceImpl implements ITInspectionPictureService 
{
    @Autowired
    private TInspectionAlarmMapper tInspectionAlarmMapper;

    @Autowired
    private TYuntaiPictureMapper tYuntaiPictureMapper;

    @Autowired
    private TLineInfoMapper tLineInfoMapper;

    @Autowired
    private TTowerInfoMapper tTowerInfoMapper;

    @Autowired
    private TInspectionPictureMapper tInspectionPictureMapper;

    /**
     * 查询t_inspection_picture（巡检图片记录）
     * 
     * @param id t_inspection_picture（巡检图片记录）ID
     * @return t_inspection_picture（巡检图片记录）
     */
    @Override
    public TInspectionPicture selectTInspectionPictureById(Long id)
    {
        return tInspectionPictureMapper.selectTInspectionPictureById(id);
    }

    /**
     * 查询t_inspection_picture（巡检图片记录）列表
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return t_inspection_picture（巡检图片记录）
     */
    @Override
    public List<TInspectionPicture> selectTInspectionPictureList(TInspectionPicture tInspectionPicture)
    {
        return tInspectionPictureMapper.selectTInspectionPictureList(tInspectionPicture);
    }

    /**
     * 新增t_inspection_picture（巡检图片记录）
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return 结果
     */
    @Override
    public int insertTInspectionPicture(TInspectionPicture tInspectionPicture)
    {
        tInspectionPicture.setCreateTime(DateUtils.getNowDate());
        return tInspectionPictureMapper.insertTInspectionPicture(tInspectionPicture);
    }

    /**
     * 修改t_inspection_picture（巡检图片记录）
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return 结果
     */
    @Override
    public int updateTInspectionPicture(TInspectionPicture tInspectionPicture)
    {
        return tInspectionPictureMapper.updateTInspectionPicture(tInspectionPicture);
    }

    /**
     * 批量删除t_inspection_picture（巡检图片记录）
     * 
     * @param ids 需要删除的t_inspection_picture（巡检图片记录）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionPictureByIds(Long[] ids)
    {
        return tInspectionPictureMapper.deleteTInspectionPictureByIds(ids);
    }

    /**
     * 删除t_inspection_picture（巡检图片记录）信息
     * 
     * @param id t_inspection_picture（巡检图片记录）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionPictureById(Long id)
    {
        return tInspectionPictureMapper.deleteTInspectionPictureById(id);
    }

    @Override
    public List<TInspectionPicture> findReportByDate(String lineIds, Long reportId, String sDate,String towerIds,List<String> installTypeList,List<String> sceneTypeList,Integer imgSize,Long yuntaiId) {
        List<TInspectionPicture> listPicture=new ArrayList<TInspectionPicture>();

        if(reportId==null || reportId==0){
            //根据安装点分类，场景点分类,进行过滤
            TYuntaiPicture yuntanPicture=new TYuntaiPicture(lineIds,towerIds);
            yuntanPicture.setSpare0(sDate);
            yuntanPicture.setImgSize(imgSize);
            yuntanPicture.setYuntaiId(yuntaiId);

            List<TYuntaiPicture> list=tYuntaiPictureMapper.findReportByDate(yuntanPicture);

            for(TYuntaiPicture obj:list){
                TInspectionPicture picture=new TInspectionPicture();
                picture.setPictureId(obj.getId());
                picture.setLineId(obj.getLineId());
                picture.setTowerId(obj.getTowerId());
                picture.setImageDir(obj.getImageDir());
                picture.setImageName(obj.getImageName());
                picture.setCaptureUser(obj.getCaptureUser());
                picture.setCaptureTime(obj.getCaptureTime());
                picture.setChannelNumber(obj.getChannelNumber());
                picture.setPresetBit(obj.getPresetBit());
                picture.setImgType(obj.getImgType());
                picture.setIsAlarm(obj.getIsAlarm());
//                picture.setCheckResult(obj.getCheckResult());

                TLineInfo line=tLineInfoMapper.selectTLineInfoById(obj.getLineId());
                picture.setLevel(line.getLevel());
                picture.setVoltage(line.getVoltage());
                picture.setLineName(line.getName());

                //根据场景点分类筛选过滤，全选或者不选则全部查出
                TTowerInfo tower=tTowerInfoMapper.selectTTowerInfoById(obj.getTowerId());
                if(sceneTypeList.size() > 0){
                    Boolean b = false;
                    String array[] = tower.getSceneType().split(",");
                    for(int i = 0; i<array.length;i++){
                        for(int j = 0;j<sceneTypeList.size();j++){
                            if(StringUtils.equals(array[i],sceneTypeList.get(j))){
                                b = true;
                            }
                        }
                    }
                    if(!b){
                        continue;
                    }
                }
                if(installTypeList.size() > 0){
                    Boolean b = false;
                    String array[] = tower.getInstallType().split(",");
                    for(int i = 0; i<array.length;i++){
                        for(int j = 0;j<installTypeList.size();j++){
                            if(StringUtils.equals(array[i],installTypeList.get(j))){
                                b = true;
                            }
                        }
                    }
                    if(!b){
                        continue;
                    }
                }

                //这里安装点名不知道怎样填，暂时默认输入场景点分类
                String scene = "";
                String instal = "";
                if(tower.getSceneType() != null && !tower.getSceneType().equals("") ){
                    String[] array = tower.getSceneType().split(",");
                    for (int i = 0; i < array.length; i++) {
                        String value = DictUtils.getDictLabel("scene_type",array[i]);
                        System.out.println(value);
                        System.out.println();
                        scene = scene+value+",";
                    }
                    scene = scene.substring(0,scene.length()-1);
                    System.out.println(scene);
                    System.out.println();
                }
                picture.setSceneName(tower.getSceneType());//场景点名称
                picture.setSceneType(scene);//场景点分类
                picture.setTowerName(tower.getName());//杆塔名称

                if(tower.getInstallType() != null && !tower.getInstallType().equals("") ){
                    String[] array = tower.getInstallType().split(",");
                    for (int i = 0; i < array.length; i++) {
                        String value = DictUtils.getDictLabel("install_type",array[i]);
                        System.out.println(value);
                        System.out.println();
                        instal = instal+value+",";
                    }
                    instal = instal.substring(0,instal.length()-1);
                    System.out.println(instal);
                    System.out.println();
                }
                picture.setInstallType(instal);//场景点分类
                //获取预置位，云台ID用来查询最近的历史图片
                TYuntaiPicture pic=new TYuntaiPicture();
                pic.setYuntaiId(obj.getYuntaiId());
                pic.setPresetBit(obj.getPresetBit());
                pic.setId(obj.getId());

                System.out.println("查询历史图片的参数：");
                System.out.println("yuntaiid="+obj.getYuntaiId());
                System.out.println("PresetBit="+obj.getPresetBit());
                System.out.println("pictureid="+obj.getId());

                //历史图片以及预警图片
                TYuntaiPicture picTmp=tYuntaiPictureMapper.selectHistoryPicture(pic);
                picture.setHistoryPicture(picTmp);
                listPicture.add(picture);
            }
        }else{
            TInspectionPicture tInspectionPicture=new TInspectionPicture();
            tInspectionPicture.setReportId(reportId);
            listPicture= tInspectionPictureMapper.selectTInspectionPictureList(tInspectionPicture);
        }

        return listPicture;
    }


}
