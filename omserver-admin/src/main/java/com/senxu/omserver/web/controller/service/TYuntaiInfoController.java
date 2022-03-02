package com.senxu.omserver.web.controller.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.vo.TYuntaiStatistic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.senxu.omserver.common.annotation.Log;
import com.senxu.omserver.common.core.controller.BaseController;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.enums.BusinessType;
import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

import javax.xml.crypto.Data;

/**
 * t_yuntai_info（云台信息）Controller
 *
 * @author panyc
 * @date 2021-06-03
 */
@Api("云台信息管理")
@RestController
@RequestMapping("/service/yuntaiInfo")
public class TYuntaiInfoController extends BaseController
{
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private TokenService tokenService;


    /**
     * 查询t_yuntai_info（云台信息）列表，应用于线路管理-->云台管理-->云台列表，以及云台管理-->设备总览
     */
    @ApiOperation("获取云台列表，应用于线路管理-->云台管理-->云台列表，以及云台管理-->设备总览(powerOrd 0 升序 1 降序)")
    //@PreAuthorize("@ss.hasPermi('service:yuntai:list')")
    @GetMapping("/list")
    public TableDataInfo list(TYuntaiInfo tYuntaiInfo)
    {
        startPage();
        if(tYuntaiInfo.getSpare4() != null ){
            String[] array = tYuntaiInfo.getSpare4().split(",");
            List<String> strings = new ArrayList<>();
            for(int i = 0; i<array.length;i++){
                strings.add(array[i]);
            }
            tYuntaiInfo.setStates(strings);
            tYuntaiInfo.setSpare4("");
        }
        if(tYuntaiInfo.getFactory() != null ){
            String[] array = tYuntaiInfo.getFactory().split(",");
            List<String> strings = new ArrayList<>();
            for(int i = 0; i<array.length;i++){
                strings.add(array[i]);
            }
            tYuntaiInfo.setFactorys(strings);
            tYuntaiInfo.setFactory("");
        }
        if(tYuntaiInfo.getHiddenDanger() != null ){
            String[] array = tYuntaiInfo.getHiddenDanger().split(",");
            List<String> strings = new ArrayList<>();
            for(int i = 0; i<array.length;i++){
                strings.add(array[i]);
            }
            tYuntaiInfo.setHiddenDangers(strings);
            tYuntaiInfo.setHiddenDanger("");
        }
        List<TYuntaiInfo> list = tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
        return getDataTable(list);
    }

    /**
     * 导出t_yuntai_info（云台信息）列表
     */
    @ApiOperation("导出云台列表")
    //@PreAuthorize("@ss.hasPermi('service:yuntai:export')")
    @Log(title = "t_yuntai_info（云台信息）", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TYuntaiInfo tYuntaiInfo)
    {
        List<TYuntaiInfo> list = tYuntaiInfoService.selectTYuntaiInfoList(tYuntaiInfo);
        ExcelUtil<TYuntaiInfo> util = new ExcelUtil<TYuntaiInfo>(TYuntaiInfo.class);
        return util.exportExcel(list, "t_yuntai_info（云台信息）数据");
    }

    /**
     * 获取t_yuntai_info（云台信息）详细信息
     */
    @ApiOperation("获取云台详细")
    //@PreAuthorize("@ss.hasPermi('service:yuntai:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tYuntaiInfoService.selectTYuntaiInfoById(id));
    }



    /**
     * 新增t_yuntai_info（云台信息）
     */
    @ApiOperation("新增云台信息")
    //@PreAuthorize("@ss.hasPermi('service:yuntai:add')")
    @Log(title = "t_yuntai_info（云台信息）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TYuntaiInfo tYuntaiInfo)
    {
        TYuntaiInfo info = new TYuntaiInfo();
        info.setCode(tYuntaiInfo.getCode());
        List<TYuntaiInfo> list = tYuntaiInfoService.selectTYuntaiInfoList(info);
        if(list.size() > 0 && list != null){
            return AjaxResult.success("新增失败, 设备ID重复");
        }
        tYuntaiInfo.setState(Long.parseLong("-1"));
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        tYuntaiInfo.setCreateUser(loginUser.getUser().getUserName());
        tYuntaiInfo.setModifyUser(loginUser.getUser().getUserName());
        tYuntaiInfo.setModifyTime(new Date());
        return toAjax(tYuntaiInfoService.insertTYuntaiInfo(tYuntaiInfo));
    }

    /**
     * 修改t_yuntai_info（云台信息）
     */
    @ApiOperation("修改云台信息")
    //@PreAuthorize("@ss.hasPermi('service:yuntai:edit')")
    @Log(title = "t_yuntai_info（云台信息）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TYuntaiInfo tYuntaiInfo)
    {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        tYuntaiInfo.setModifyUser(loginUser.getUser().getUserName());
        tYuntaiInfo.setModifyTime(new Date());
        return toAjax(tYuntaiInfoService.updateTYuntaiInfo(tYuntaiInfo));
    }

    /**
     * 删除t_yuntai_info（云台信息）
     */
    @ApiOperation("删除云台信息")
    //@PreAuthorize("@ss.hasPermi('service:yuntai:remove')")
    @Log(title = "t_yuntai_info（云台信息）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tYuntaiInfoService.deleteTYuntaiInfoByIds(ids));
    }

    /**
     * 查询t_yuntai_info（云台信息）所有状态统计
     */
    @ApiOperation("获取云台状态统计")
//    //@PreAuthorize("@ss.hasPermi('service:yuntai:list')")
    @GetMapping("/selectTYuntaiTotal")
    public AjaxResult selectTYuntaiTotal(TYuntaiInfo tYuntaiInfo)
    {
        AjaxResult ajax = AjaxResult.success();
        TYuntaiStatistic static1= tYuntaiInfoService.selectTYuntaiTotal(tYuntaiInfo);
        ajax.put("TYuntaiStatistic",static1);
        return ajax;
    }

}
