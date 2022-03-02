package com.senxu.omserver.service.service;

import java.util.List;

import com.senxu.omserver.service.domain.TInspectionAlarm;
import com.senxu.omserver.service.domain.TInspectionPicture;
import com.senxu.omserver.service.domain.TInspectionReport;

/**
 * t_inspection_picture（巡检图片记录）Service接口
 * 
 * @author ruoyi
 * @date 2021-09-14
 */
public interface ITInspectionPictureService 
{
    /**
     * 查询t_inspection_picture（巡检图片记录）
     * 
     * @param id t_inspection_picture（巡检图片记录）ID
     * @return t_inspection_picture（巡检图片记录）
     */
    public TInspectionPicture selectTInspectionPictureById(Long id);

    /**
     * 查询t_inspection_picture（巡检图片记录）列表
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return t_inspection_picture（巡检图片记录）集合
     */
    public List<TInspectionPicture> selectTInspectionPictureList(TInspectionPicture tInspectionPicture);

    /**
     * 新增t_inspection_picture（巡检图片记录）
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return 结果
     */
    public int insertTInspectionPicture(TInspectionPicture tInspectionPicture);

    /**
     * 修改t_inspection_picture（巡检图片记录）
     * 
     * @param tInspectionPicture t_inspection_picture（巡检图片记录）
     * @return 结果
     */
    public int updateTInspectionPicture(TInspectionPicture tInspectionPicture);

    /**
     * 批量删除t_inspection_picture（巡检图片记录）
     * 
     * @param ids 需要删除的t_inspection_picture（巡检图片记录）ID
     * @return 结果
     */
    public int deleteTInspectionPictureByIds(Long[] ids);

    /**
     * 删除t_inspection_picture（巡检图片记录）信息
     * 
     * @param id t_inspection_picture（巡检图片记录）ID
     * @return 结果
     */
    public int deleteTInspectionPictureById(Long id);

    /**
     * 通过日期及杆塔id获取云台告警列表
     *
     * @param reportId 巡检报告Id
     * @param sDate 日期 yyyy-MM-dd
     * @return t_inspection_picture（巡检图片记录）集合
     */
    public List<TInspectionPicture> findReportByDate(String lineIds, Long reportId, String sDate,String towerIds, List<String> installTypeList, List<String> sceneTypeList,Integer imgSize,Long yuntaiId);

}
