package com.senxu.omserver.web.controller.service;

import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.framework.web.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import com.senxu.omserver.service.domain.TYuntaiPreview;
import com.senxu.omserver.service.service.ITYuntaiPreviewService;
import com.senxu.omserver.common.utils.poi.ExcelUtil;
import com.senxu.omserver.common.core.page.TableDataInfo;

/**
 * t_yuntai_preview（图片预览记录）Controller
 * 
 * @author panyc
 * @date 2021-06-11
 */
@Api("图片预览记录")
@RestController
@RequestMapping("/service/yuntaiPreview")
public class TYuntaiPreviewController extends BaseController
{
    @Autowired
    private ITYuntaiPreviewService tYuntaiPreviewService;
    @Autowired
    private TokenService tokenService;

    /**
     * 标识图片已预览批量
     * @param ids
     * @return
     */
    @ApiOperation("标识图片已预览批量")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiPreview:isPreviewBatch')")
    @Log(title = "t_yuntai_preview（图片预览记录）", businessType = BusinessType.INSERT)
	@PostMapping
    public AjaxResult isPreviewBatch(Long[] ids)
    {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return toAjax(tYuntaiPreviewService.isPreviewTYuntaiPreviewByIds(ids,tokenService.getLoginUser(ServletUtils.getRequest())));
    }


    /**
     * 标识图片已预览
     * @param id
     * @return
     */
    @ApiOperation("标识图片已预览")
    //@PreAuthorize("@ss.hasPermi('service:yuntaiPreview:isPreview')")
    @Log(title = "t_yuntai_preview（图片预览记录）", businessType = BusinessType.INSERT)
    @PutMapping
    public AjaxResult isPreview(Long id)
    {
        //获取当前用户
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return toAjax(tYuntaiPreviewService.isPreviewTYuntaiPreviewById(id,tokenService.getLoginUser(ServletUtils.getRequest())));
    }



    /**
     * 查询t_yuntai_preview（图片预览记录）列表
     */
//    @ApiOperation("查询图片预览记录列表")
//    //@PreAuthorize("@ss.hasPermi('service:yuntaiPreview:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(TYuntaiPreview tYuntaiPreview)
//    {
//        startPage();
//        List<TYuntaiPreview> list = tYuntaiPreviewService.selectTYuntaiPreviewList(tYuntaiPreview);
//        return getDataTable(list);
//    }

    /**
     * 导出t_yuntai_preview（图片预览记录）列表
     */
//    @ApiOperation("导出图片预览记录列表")
//    //@PreAuthorize("@ss.hasPermi('service:yuntaiPreview:export')")
//    @Log(title = "t_yuntai_preview（图片预览记录）", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(TYuntaiPreview tYuntaiPreview)
//    {
//        List<TYuntaiPreview> list = tYuntaiPreviewService.selectTYuntaiPreviewList(tYuntaiPreview);
//        ExcelUtil<TYuntaiPreview> util = new ExcelUtil<TYuntaiPreview>(TYuntaiPreview.class);
//        return util.exportExcel(list, "t_yuntai_preview（图片预览记录）数据");
//    }

    /**
     * 获取t_yuntai_preview（图片预览记录）详细信息
     */
//    @ApiOperation("获取图片预览记录详细")
//    //@PreAuthorize("@ss.hasPermi('service:yuntaiPreview:query')")
//    @GetMapping(value = "/{id}")
//    public AjaxResult getInfo(@PathVariable("id") Long id)
//    {
//        return AjaxResult.success(tYuntaiPreviewService.selectTYuntaiPreviewById(id));
//    }

    /**
     * 新增t_yuntai_preview（图片预览记录）
     */
//    @ApiOperation("新增图片预览记录")
//    //@PreAuthorize("@ss.hasPermi('service:yuntaiPreview:add')")
//    @Log(title = "t_yuntai_preview（图片预览记录）", businessType = BusinessType.INSERT)
//    @PostMapping
//    public AjaxResult add(@RequestBody TYuntaiPreview tYuntaiPreview)
//    {
//        return toAjax(tYuntaiPreviewService.insertTYuntaiPreview(tYuntaiPreview));
//    }

    /**
     * 修改t_yuntai_preview（图片预览记录）
     */
//    @ApiOperation("修改图片预览记录")
//    //@PreAuthorize("@ss.hasPermi('service:yuntaiPreview:edit')")
//    @Log(title = "t_yuntai_preview（图片预览记录）", businessType = BusinessType.UPDATE)
//    @PutMapping
//    public AjaxResult edit(@RequestBody TYuntaiPreview tYuntaiPreview)
//    {
//        return toAjax(tYuntaiPreviewService.updateTYuntaiPreview(tYuntaiPreview));
//    }







    /**
     * 删除t_yuntai_preview（图片预览记录）
     */
//    @ApiOperation("删除图片预览记录")
//    //@PreAuthorize("@ss.hasPermi('service:yuntaiPreview:remove')")
//    @Log(title = "t_yuntai_preview（图片预览记录）", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(tYuntaiPreviewService.deleteTYuntaiPreviewByIds(ids));
//    }
}
