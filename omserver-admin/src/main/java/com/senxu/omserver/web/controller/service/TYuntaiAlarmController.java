package com.senxu.omserver.web.controller.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.senxu.omserver.common.constant.Constants;
import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TYuntaiPicture;
import com.senxu.omserver.service.service.ITYuntaiPictureService;
import com.sun.imageio.plugins.common.I18N;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.service.domain.TYuntaiAlarm;
import com.senxu.omserver.service.service.ITYuntaiAlarmService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_yuntai_alarm（云台告警）Controller
 *
 * @author panyc
 * @date 2021-06-11
 */
@Api("云台告警")
@RestController
@RequestMapping("/service/yuntaiAlarm")
public class TYuntaiAlarmController extends BaseController
{
    @Autowired
    private ITYuntaiAlarmService tYuntaiAlarmService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ITYuntaiPictureService pictureService;


    /**
     * 查询t_yuntai_alarm（云台告警）列表
     */
    @ApiOperation("查询云台告警列表")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiAlarm tYuntaiAlarm)
    {
        startPage();
        List<TYuntaiAlarm> list = tYuntaiAlarmService.selectTYuntaiAlarmList(tYuntaiAlarm);
        return getDataTable(list);
    }

    /**
     * 查询t_yuntai_alarm（云台告警）列表
     */
    @ApiOperation("查询云台告警列表")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:list')")
    @GetMapping("/listByDetail")
    public TableDataInfo listByDetail(TYuntaiAlarm tYuntaiAlarm)
    {
        startPage();
        List<TYuntaiAlarm> list = tYuntaiAlarmService.selectTYuntaiAlarmLisByDetail(tYuntaiAlarm);
        if(tYuntaiAlarm.getIsBrowse() != null){
            for (int i = 0; i < list.size(); i++) {
                TYuntaiAlarm alarm = list.get(i);
                if(Integer.parseInt(alarm.getIsBrowse()) >= 1){
                    alarm.setIsBrowse("1");
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 批量确认t_yuntai_alarm（云台告警）
     */
    @ApiOperation("批量确认告警")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:batchConfirm')")
    @Log(title = "t_yuntai_alarm（云台告警）", businessType = BusinessType.CONFIRM)
    @GetMapping("/batchConfirm")
    public AjaxResult batchConfirm(Long[] ids, TYuntaiAlarm info)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();

        if(ids!=null){
            for(int i=0;i<ids.length;i++){
                TYuntaiAlarm tYuntaiAlarm=tYuntaiAlarmService.selectTYuntaiAlarmById(ids[i]);
                tYuntaiAlarm.setConfirmState(Constants.TAG_是);//确认状态
                tYuntaiAlarm.setConfirmUser(operName);
                tYuntaiAlarm.setConfirmTime(DateUtils.getNowDate());//确认时间
                tYuntaiAlarm.setIsIgnore(info.getIsIgnore());//忽略此告警
                tYuntaiAlarm.setIsCorrect(info.getIsCorrect());  //目标检测
                tYuntaiAlarm.setAlarmDes(info.getAlarmDes());//告警说明(描述)
                tYuntaiAlarm.setAffectedLine(info.getAffectedLine());//影响线路
                tYuntaiAlarm.setSecurityDistance(info.getSecurityDistance());//大于导线安全距离
                tYuntaiAlarm.setIsInform(info.getIsInform());//是否需要下发隐患通知
                tYuntaiAlarm.setIsScene(info.getIsScene());//是否需要现场处理
                tYuntaiAlarmService.updateTYuntaiAlarm(tYuntaiAlarm);
            }
            return toAjax(1);
        }else {
            return toAjax(0);
        }
    }


    /**
     * 单条记录确认t_yuntai_alarm（云台告警）
     */
    @ApiOperation("单条记录确认告警")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:singleConfirm')")
    @Log(title = "t_yuntai_alarm（云台告警）", businessType = BusinessType.CONFIRM)
    @GetMapping("/singleConfirm")
    public AjaxResult singleConfirm(TYuntaiAlarm tYuntaiAlarm)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        System.out.println("tYuntaiAlarm.getId()=="+tYuntaiAlarm.getId());
        TYuntaiAlarm tmpAlarm=tYuntaiAlarmService.selectTYuntaiAlarmById(tYuntaiAlarm.getId());

        System.out.println("tmpAlarm=="+tmpAlarm);
        tmpAlarm.setConfirmState(Constants.TAG_是);
        tmpAlarm.setConfirmUser(operName);
        tmpAlarm.setConfirmTime(DateUtils.getNowDate());
        tmpAlarm.setIsIgnore(tYuntaiAlarm.getIsIgnore());
        tmpAlarm.setIsCorrect(tYuntaiAlarm.getIsCorrect());
        tmpAlarm.setAffectedLine(tYuntaiAlarm.getAffectedLine());//影响线路
        tmpAlarm.setSecurityDistance(tYuntaiAlarm.getSecurityDistance());
        tmpAlarm.setIsInform(tYuntaiAlarm.getIsInform());
        tmpAlarm.setIsScene(tYuntaiAlarm.getIsScene());
        tmpAlarm.setAlarmDes(tYuntaiAlarm.getAlarmDes());
        return toAjax(tYuntaiAlarmService.updateTYuntaiAlarm(tYuntaiAlarm));
    }



    /**
     * 导出t_yuntai_alarm（云台告警）列表
     */
    @ApiOperation("导出云台告警列表")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:export')")
    @Log(title = "t_yuntai_alarm（云台告警）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiAlarm tYuntaiAlarm)
    {
        List<TYuntaiAlarm> list = tYuntaiAlarmService.selectTYuntaiAlarmList(tYuntaiAlarm);
        ExcelUtil<TYuntaiAlarm> util = new ExcelUtil<TYuntaiAlarm>(TYuntaiAlarm.class);
        return util.exportExcel(list, "t_yuntai_alarm（云台告警）数据");
    }

    /**
     * 获取t_yuntai_alarm（云台告警）详细信息
     */
    @ApiOperation("获取云台告警详细")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiAlarmService.selectTYuntaiAlarmById(id));
    }

    /**
     * 新增t_yuntai_alarm（云台告警）
     */
    @ApiOperation("新增云台告警")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:add')")
    @Log(title = "t_yuntai_alarm（云台告警）", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody TYuntaiAlarm tYuntaiAlarm)
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        //设置图片预警标识
        TYuntaiPicture tYuntaiPicture=pictureService.selectTYuntaiPictureById(tYuntaiAlarm.getPictureId());
        tYuntaiPicture.setIsAlarm(1);
        pictureService.updateTYuntaiPicture(tYuntaiPicture);
        tYuntaiAlarm.setAlarmDir(tYuntaiPicture.getImageDir());
        tYuntaiAlarm.setAlarmUrl(tYuntaiPicture.getImageName());
        tYuntaiAlarm.setYuntaiId(tYuntaiPicture.getYuntaiId());
        tYuntaiAlarm.setCaptureUser(operName);
        tYuntaiAlarm.setDealUser(operName);
        tYuntaiAlarm.setConfirmTime(new Date());
        tYuntaiAlarm.setTowerId(tYuntaiPicture.getTowerId());
        tYuntaiAlarm.setLineId(tYuntaiPicture.getLineId());
        tYuntaiAlarm.setFactory(tYuntaiPicture.getFactory());
        tYuntaiAlarm.setChannelNumber(tYuntaiPicture.getChannelNumber());
        tYuntaiAlarm.setPresetBit(tYuntaiPicture.getPresetBit());
        tYuntaiAlarm.setAlarmSource("1");
        tYuntaiAlarm.setAlarmDate(new Date());//告警时间为什么原来是直接取抓拍时间呢？
        tYuntaiAlarm.setConfirmState(0);
        tYuntaiAlarm.setImgType(tYuntaiAlarm.getImgType());
        int result= tYuntaiAlarmService.insertTYuntaiAlarm(tYuntaiAlarm);
        return toAjax(result);
    }

    /**
     * 修改t_yuntai_alarm（云台告警）
     */
    @ApiOperation("修改云台告警")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:edit')")
    @Log(title = "t_yuntai_alarm（云台告警）", businessType = BusinessType.UPDATE)
    @GetMapping("/editAlarm")
    public AjaxResult edit(Long[] ids)
    {
        System.out.println(ids);
        if(ids.length > 0){
            for(Long id:ids){
                TYuntaiAlarm alarm = new TYuntaiAlarm();
                alarm.setId(id);
                alarm.setIsBrowse("1");
                tYuntaiAlarmService.updateTYuntaiAlarm(alarm);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 删除t_yuntai_alarm（云台告警）
     */
    @ApiOperation("删除云台告警")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiAlarm:remove')")
    @Log(title = "t_yuntai_alarm（云台告警）", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiAlarmService.deleteTYuntaiAlarmByIds(ids));
    }

}
