package com.senxu.omserver.service.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TLineInfo;
import com.senxu.omserver.service.domain.TYuntaiAlarm;
import com.senxu.omserver.service.domain.TYuntaiPicture;
import com.senxu.omserver.service.mapper.TLineInfoMapper;
import com.senxu.omserver.service.mapper.TYuntaiAlarmMapper;
import com.senxu.omserver.service.mapper.TYuntaiPictureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TInspectionAlarmMapper;
import com.senxu.omserver.service.domain.TInspectionAlarm;
import com.senxu.omserver.service.service.ITInspectionAlarmService;

/**
 * t_inspection_alarm（巡检告警记录）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-10
 */
@Service
public class TInspectionAlarmServiceImpl implements ITInspectionAlarmService 
{
    @Autowired
    private TInspectionAlarmMapper tInspectionAlarmMapper;

    @Autowired
    private TYuntaiPictureMapper tYuntaiPictureMapper;

    @Autowired
    private TYuntaiAlarmMapper tYuntaiAlarmMapper;

    @Autowired
    private TLineInfoMapper tLineInfoMapper;


    /**
     * 查询t_inspection_alarm（巡检告警记录）
     * 
     * @param id t_inspection_alarm（巡检告警记录）ID
     * @return t_inspection_alarm（巡检告警记录）
     */
    @Override
    public TInspectionAlarm selectTInspectionAlarmById(Long id)
    {
        return tInspectionAlarmMapper.selectTInspectionAlarmById(id);
    }

    /**
     * 查询t_inspection_alarm（巡检告警记录）列表
     * 
     * @param tInspectionAlarm t_inspection_alarm（巡检告警记录）
     * @return t_inspection_alarm（巡检告警记录）
     */
    @Override
    public List<TInspectionAlarm> selectTInspectionAlarmList(TInspectionAlarm tInspectionAlarm)
    {
        return tInspectionAlarmMapper.selectTInspectionAlarmList(tInspectionAlarm);
    }

    @Override
    public List<TInspectionAlarm> findReportByDate(String lineIds, Long reportId, String sDate) {
        List<TInspectionAlarm> listAlarm=new ArrayList<TInspectionAlarm>();

        if(reportId==null || reportId==0){
            TYuntaiAlarm yuntanAlarm=new TYuntaiAlarm(lineIds);
            yuntanAlarm.setSpare0(sDate);

            List<TYuntaiAlarm> list=tYuntaiAlarmMapper.findReportByDate(yuntanAlarm);

            for(TYuntaiAlarm obj:list){
                TInspectionAlarm alarm=new TInspectionAlarm();
                alarm.setAlarmId(obj.getId());
                alarm.setLineId(obj.getLineId());
                alarm.setTowerId(obj.getTowerId());
                alarm.setPictureId(obj.getPictureId());
                TLineInfo line=tLineInfoMapper.selectTLineInfoById(obj.getLineId());
                alarm.setLevel(line.getLevel());
                alarm.setVoltage(line.getVoltage());
                alarm.setSceneName(obj.getSceneName());
                alarm.setSceneType(obj.getSceneType());

                //获取预置位，云台ID用来查询最近的历史图片
                TYuntaiPicture pic=new TYuntaiPicture();
                pic.setYuntaiId(obj.getYuntaiId());
                pic.setPresetBit(obj.getPresetBit());
                pic.setId(obj.getPictureId());
                TYuntaiPicture picTmp=tYuntaiPictureMapper.selectHistoryPicture(pic);
                alarm.setPictureId(picTmp.getId());
                alarm.setImageDir(picTmp.getImageDir());
                alarm.setImageName(picTmp.getImageDir());
                alarm.setAlarmDir(picTmp.getAlarmDir());
                alarm.setAlarmUrl(picTmp.getAlarmUrl());
                listAlarm.add(alarm);
            }
        }else{
            TInspectionAlarm tInspectionAlarm=new TInspectionAlarm();
            tInspectionAlarm.setReportId(reportId);
            listAlarm= tInspectionAlarmMapper.selectTInspectionAlarmList(tInspectionAlarm);
        }

    return listAlarm;

    }

    /**
     * 新增t_inspection_alarm（巡检告警记录）
     * 
     * @param tInspectionAlarm t_inspection_alarm（巡检告警记录）
     * @return 结果
     */
    @Override
    public int insertTInspectionAlarm(TInspectionAlarm tInspectionAlarm)
    {
        tInspectionAlarm.setCreateTime(DateUtils.getNowDate());
        return tInspectionAlarmMapper.insertTInspectionAlarm(tInspectionAlarm);
    }

    /**
     * 修改t_inspection_alarm（巡检告警记录）
     * 
     * @param tInspectionAlarm t_inspection_alarm（巡检告警记录）
     * @return 结果
     */
    @Override
    public int updateTInspectionAlarm(TInspectionAlarm tInspectionAlarm)
    {
        return tInspectionAlarmMapper.updateTInspectionAlarm(tInspectionAlarm);
    }

    /**
     * 批量删除t_inspection_alarm（巡检告警记录）
     * 
     * @param ids 需要删除的t_inspection_alarm（巡检告警记录）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionAlarmByIds(Long[] ids)
    {
        return tInspectionAlarmMapper.deleteTInspectionAlarmByIds(ids);
    }

    /**
     * 删除t_inspection_alarm（巡检告警记录）信息
     * 
     * @param id t_inspection_alarm（巡检告警记录）ID
     * @return 结果
     */
    @Override
    public int deleteTInspectionAlarmById(Long id)
    {
        return tInspectionAlarmMapper.deleteTInspectionAlarmById(id);
    }
}
