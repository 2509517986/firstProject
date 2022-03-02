package com.senxu.omserver.web.controller.service;

import java.util.List;

import com.senxu.omserver.service.domain.TUploadList;
import com.senxu.omserver.service.service.ITUploadListService;
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
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * 【文件上传信息】Controller
 * 
 * @author lvhp
 * @date 2021-10-30
 */
@RestController
@RequestMapping("/service/uploadList")
public class TUploadListController extends BaseController
{
    @Autowired(required = false)
    private ITUploadListService tUploadListService;

    /**
     * 查询【文件上传信息】列表
     */
    //@PreAuthorize("@ss.hasPermi('service:uploadList:list')")
    @GetMapping("/list")
    public TableDataInfo list(TUploadList tUploadList)
    {
        startPage();
        List<TUploadList> list = tUploadListService.selectTUploadListList(tUploadList);
        return getDataTable(list);
    }

    /**
     * 导出【文件上传信息】列表
     */
    //@PreAuthorize("@ss.hasPermi('service:uploadList:export')")
    @Log(title = "【文件上传信息】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TUploadList tUploadList)
    {
        List<TUploadList> list = tUploadListService.selectTUploadListList(tUploadList);
        ExcelUtil<TUploadList> util = new ExcelUtil<TUploadList>(TUploadList.class);
        return util.exportExcel(list, "【文件上传信息】数据");
    }

    /**
     * 获取【文件上传信息】详细信息
     */
    //@PreAuthorize("@ss.hasPermi('service:uploadList:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tUploadListService.selectTUploadListById(id));
    }

    /**
     * 获取【文件上传信息】详细信息
     */
    //@PreAuthorize("@ss.hasPermi('service:uploadList:query')")
    @GetMapping(value = "/queryByYuntaiId")
    public AjaxResult getInfoByYuntaiId(@PathVariable("yuntaiId") Long yuntaiId)
    {
        return AjaxResult.success(tUploadListService.selectTUploadListByYuntaiId(yuntaiId));
    }

    /**
     * 新增【文件上传信息】
     */
    //@PreAuthorize("@ss.hasPermi('service:uploadList:add')")
    @Log(title = "【文件上传信息】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TUploadList tUploadList)
    {
        return toAjax(tUploadListService.insertTUploadList(tUploadList));
    }

    /**
     * 修改【文件上传信息】
     */
    //@PreAuthorize("@ss.hasPermi('service:uploadList:edit')")
    @Log(title = "【文件上传信息】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TUploadList tUploadList)
    {
        return toAjax(tUploadListService.updateTUploadList(tUploadList));
    }

    /**
     * 删除【文件上传信息】
     */
    //@PreAuthorize("@ss.hasPermi('service:uploadList:remove')")
    @Log(title = "【文件上传信息】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tUploadListService.deleteTUploadListByIds(ids));
    }
}
