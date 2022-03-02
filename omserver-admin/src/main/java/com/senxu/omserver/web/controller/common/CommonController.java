package com.senxu.omserver.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.common.utils.ServletUtils;
import com.senxu.omserver.common.utils.file.FileGetSize;
import com.senxu.omserver.framework.web.service.TokenService;
import com.senxu.omserver.service.domain.TUploadList;
import com.senxu.omserver.service.service.ITUploadListService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.senxu.omserver.common.config.RuoYiConfig;
import com.senxu.omserver.common.constant.Constants;
import com.senxu.omserver.common.core.domain.AjaxResult;
import com.senxu.omserver.common.utils.StringUtils;
import com.senxu.omserver.common.utils.file.FileUploadUtils;
import com.senxu.omserver.common.utils.file.FileUtils;
import com.senxu.omserver.framework.config.ServerConfig;
import io.swagger.annotations.ApiOperation;

import static com.senxu.omserver.common.utils.file.FileGetSize.SIZETYPE_KB;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@Api("文件上传下载")
@RestController
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ITUploadListService itUploadListService;
    @Autowired
    private TokenService tokenService;

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @ApiOperation("通用下载请求")
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @ApiOperation("通用上传请求")
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            System.out.println(file.getInputStream().available() / 1048576+"M");
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getResource());
            System.out.println(file.getInputStream());
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 设备升级上传请求
     */
    @ApiOperation("设备升级上传请求")
    @PostMapping("/common/uploadFileZip")
    public AjaxResult uploadFileZip(MultipartFile file,String yuntaiId) throws Exception
    {
        try
        {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            Long userId = loginUser.getUser().getUserId();
            String size = FileGetSize.formatFileSize(file.getInputStream().available(),SIZETYPE_KB)+"KB";
            // 上传文件路径
            String filePath = RuoYiConfig.getPath();
            // 上传并返回新文件名称
//            String fileName1 = FileUploadUtils.upload(filePath, file);
            String fileName2 = FileUploadUtils.uploadFile(filePath, file);
            String url = serverConfig.getUrl() + filePath;
            AjaxResult ajax = AjaxResult.success();
            TUploadList tUploadList = new TUploadList();
            tUploadList.setFtpUrl(fileName2);
            tUploadList.setHttpUrl(url);
            tUploadList.setCreateTime(DateUtils.getNowDate());
            tUploadList.setCreateUser(String.valueOf(userId));
            tUploadList.setFileName(file.getOriginalFilename());
            tUploadList.setSize(size);
            tUploadList.setYuntaiId(Long.parseLong(yuntaiId));
            itUploadListService.insertTUploadList(tUploadList);
            ajax.put("ftpUrl", filePath);
            ajax.put("httpUrl", url);
            ajax.put("fileName", file.getOriginalFilename());
            ajax.put("fileSize", size);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求，支持带子目录参数
     * MultipartFile  文件
     * subdirectory  子目录名称
     */
    @ApiOperation("通用上传请求，支持带子目录参数")
    @PostMapping("/common/uploadFile")
    public AjaxResult uploadFile(MultipartFile file,String subdirectory) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath()+"/"+subdirectory;
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @ApiOperation("本地资源通用下载")
    @GetMapping("/common/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }
}
