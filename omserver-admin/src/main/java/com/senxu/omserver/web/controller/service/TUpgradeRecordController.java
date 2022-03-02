package com.senxu.omserver.web.controller.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.senxu.omserver.service.domain.TYuntaiInfo;
import com.senxu.omserver.service.service.ITYuntaiInfoService;
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
import com.senxu.omserver.service.domain.TUpgradeRecord;
import com.senxu.omserver.service.service.ITUpgradeRecordService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;
import senxu.com.cmd.response.DeviceCommandResponse;

/**
 * 【设备升级记录】Controller
 * 
 * @author ruoyi
 * @date 2021-10-30
 */
@RestController
@RequestMapping("/service/record")
public class TUpgradeRecordController extends BaseController
{
    @Autowired
    private ITUpgradeRecordService tUpgradeRecordService;
    @Autowired
    private ITYuntaiInfoService tYuntaiInfoService;
    @Autowired
    private NWOMServer nwomServer;

    /**
     * 查询【设备升级记录】列表
     */
    //@PreAuthorize("@ss.hasPermi('service:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(TUpgradeRecord tUpgradeRecord)
    {
        startPage();
        List<TUpgradeRecord> list = tUpgradeRecordService.selectTUpgradeRecordList(tUpgradeRecord);
        return getDataTable(list);
    }

    /**
     * 导出【设备升级记录】列表
     */
    //@PreAuthorize("@ss.hasPermi('service:record:export')")
    @Log(title = "【设备升级记录】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TUpgradeRecord tUpgradeRecord)
    {
        List<TUpgradeRecord> list = tUpgradeRecordService.selectTUpgradeRecordList(tUpgradeRecord);
        ExcelUtil<TUpgradeRecord> util = new ExcelUtil<TUpgradeRecord>(TUpgradeRecord.class);
        return util.exportExcel(list, "【设备升级记录】数据");
    }

    /**
     * 获取【设备升级记录】详细信息
     */
    //@PreAuthorize("@ss.hasPermi('service:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tUpgradeRecordService.selectTUpgradeRecordById(id));
    }

    /**
     * 获取【设备升级记录】详细信息
     */
    //@PreAuthorize("@ss.hasPermi('service:record:query')")
    @GetMapping(value = "/queryByYuntaiId")
    public AjaxResult getInfoByYuntaiId(@PathVariable("yuntaiId") Long yuntaiId)
    {
        return AjaxResult.success(tUpgradeRecordService.selectTUpgradeRecordById(yuntaiId));
    }

    /**
     * 新增【设备升级记录】
     */
    //@PreAuthorize("@ss.hasPermi('service:record:add')")
    @Log(title = "【设备升级记录】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JSONObject jsonObject)
    {

        String code = jsonObject.getString("code");//设备PUID
        String httpUrl = jsonObject.getString("ftpUrl");//上传文件路径
        File file = new File(httpUrl);
        String fileName = file.getName();//文件名称
        String fileType = httpUrl.substring(httpUrl.length()-3,httpUrl.length()).trim();//文件类型
        String createUser = jsonObject.getString("createUser");//创建人
        String yuntaiId = jsonObject.getString("yuntaiId");//云台ID
        String manner = jsonObject.getString("manner");//升级方式
        String protocol = jsonObject.getString("protocol");//传输协议
        String purposeEquipment = jsonObject.getString("purposeEquipment");//升级目的设备
        TYuntaiInfo tYuntaiInfo = tYuntaiInfoService.selectTYuntaiInfoById(Long.parseLong(yuntaiId));
        TUpgradeRecord record = new TUpgradeRecord();
        record.setYuntaiId(Long.parseLong(yuntaiId));
        List<TUpgradeRecord> list = tUpgradeRecordService.selectTUpgradeRecordList(record);
        TUpgradeRecord tUpgradeRecord = new TUpgradeRecord();
        tUpgradeRecord.setCode(code);
        tUpgradeRecord.setName(tYuntaiInfo.getName());
        tUpgradeRecord.setFileName(fileName);
        tUpgradeRecord.setFileType(fileType);
        tUpgradeRecord.setCreateUser(createUser);
        tUpgradeRecord.setBeginTime(new Date());
        tUpgradeRecord.setEndTime(new Date());
        tUpgradeRecord.setYuntaiId(Long.parseLong(yuntaiId));
        tUpgradeRecord.setManner(manner);
        tUpgradeRecord.setProtocol(protocol);
        tUpgradeRecord.setPurposeEquipment(purposeEquipment);
        if(list.size()>0){
            tUpgradeRecord.setOldVersion(list.get(0).getFileName());
        }
        tUpgradeRecord.setNewVersion(fileName);
        DeviceCommandResponse response = nwomServer.getDeviceBridge().CmdCAH(code,1,1,httpUrl,1,0);
        if(response.getResponseCode() == 500 && response.getMessage().equals("文件不存在")){
            tUpgradeRecord.setState("0");//升级状态
            tUpgradeRecord.setResult("文件不存在");//升级结果
            tUpgradeRecordService.insertTUpgradeRecord(tUpgradeRecord);
            return AjaxResult.error(response.getErrorCode(),response.getMessage());
        }else if(response.getResponseCode() == 200){
            tUpgradeRecord.setState("1");//升级状态
            tUpgradeRecord.setResult("升级成功");//升级结果
            tUpgradeRecordService.insertTUpgradeRecord(tUpgradeRecord);
            return AjaxResult.success();
        }else {
            tUpgradeRecord.setState("0");//升级状态
            tUpgradeRecord.setResult("升级失败");//升级结果
            tUpgradeRecordService.insertTUpgradeRecord(tUpgradeRecord);
            return AjaxResult.error();
        }
    }

    /**
     * 修改【设备升级记录】
     */
    //@PreAuthorize("@ss.hasPermi('service:record:edit')")
    @Log(title = "【设备升级记录】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TUpgradeRecord tUpgradeRecord)
    {
        return toAjax(tUpgradeRecordService.updateTUpgradeRecord(tUpgradeRecord));
    }

    /**
     * 删除【设备升级记录】
     */
    //@PreAuthorize("@ss.hasPermi('service:record:remove')")
    @Log(title = "【设备升级记录】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tUpgradeRecordService.deleteTUpgradeRecordByIds(ids));
    }
}
