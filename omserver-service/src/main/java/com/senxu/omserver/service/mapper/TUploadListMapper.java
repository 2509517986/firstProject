package com.senxu.omserver.service.mapper;

import java.util.List;

import com.senxu.omserver.service.domain.TUploadList;

/**
 * 【文件上传信息】Mapper接口
 * 
 * @author ruoyi
 * @date 2021-10-30
 */
public interface TUploadListMapper 
{
    /**
     * 查询【文件上传信息】
     * 
     * @param id 【文件上传信息】ID
     * @return 【文件上传信息】
     */
    public TUploadList selectTUploadListById(Long id);

    /**
     * 查询【文件上传信息】
     *
     * @param yuntaiId 【文件上传信息】ID
     * @return 【文件上传信息】
     */
    public List<TUploadList> selectTUploadListByYuntaiId(Long yuntaiId);

    /**
     * 查询【文件上传信息】列表
     * 
     * @param tUploadList 【文件上传信息】
     * @return 【文件上传信息】集合
     */
    public List<TUploadList> selectTUploadListList(TUploadList tUploadList);

    /**
     * 新增【文件上传信息】
     * 
     * @param tUploadList 【文件上传信息】
     * @return 结果
     */
    public int insertTUploadList(TUploadList tUploadList);

    /**
     * 修改【文件上传信息】
     * 
     * @param tUploadList 【文件上传信息】
     * @return 结果
     */
    public int updateTUploadList(TUploadList tUploadList);

    /**
     * 删除【文件上传信息】
     * 
     * @param id 【文件上传信息】ID
     * @return 结果
     */
    public int deleteTUploadListById(Long id);

    /**
     * 批量删除【文件上传信息】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTUploadListByIds(Long[] ids);
}
