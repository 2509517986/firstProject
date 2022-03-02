package com.senxu.omserver.service.service.impl;

import java.util.List;
import com.senxu.omserver.common.utils.DateUtils;
import com.senxu.omserver.service.domain.TUploadList;
import com.senxu.omserver.service.mapper.TUploadListMapper;
import com.senxu.omserver.service.service.ITUploadListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 【文件上传信息】Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-10-30
 */
@Service
public class TUploadListServiceImpl implements ITUploadListService
{
    @Autowired(required = false)
    private TUploadListMapper tUploadListMapper;

    /**
     * 查询【文件上传信息】
     * 
     * @param id 【文件上传信息】ID
     * @return 【文件上传信息】
     */
    @Override
    public TUploadList selectTUploadListById(Long id)
    {
        return tUploadListMapper.selectTUploadListById(id);
    }

    /**
     * 查询【文件上传信息】
     *
     * @param yuntaiId 【文件上传信息】ID
     * @return 【文件上传信息】
     */
    @Override
    public List<TUploadList> selectTUploadListByYuntaiId(Long yuntaiId) {
        return tUploadListMapper.selectTUploadListByYuntaiId(yuntaiId);
    }

    /**
     * 查询【文件上传信息】列表
     * 
     * @param tUploadList 【文件上传信息】
     * @return 【文件上传信息】
     */
    @Override
    public List<TUploadList> selectTUploadListList(TUploadList tUploadList)
    {
        return tUploadListMapper.selectTUploadListList(tUploadList);
    }

    /**
     * 新增【文件上传信息】
     * 
     * @param tUploadList 【文件上传信息】
     * @return 结果
     */
    @Override
    public int insertTUploadList(TUploadList tUploadList)
    {
        tUploadList.setCreateTime(DateUtils.getNowDate());
        return tUploadListMapper.insertTUploadList(tUploadList);
    }

    /**
     * 修改【文件上传信息】
     * 
     * @param tUploadList 【文件上传信息】
     * @return 结果
     */
    @Override
    public int updateTUploadList(TUploadList tUploadList)
    {
        return tUploadListMapper.updateTUploadList(tUploadList);
    }

    /**
     * 批量删除【文件上传信息】
     * 
     * @param ids 需要删除的【文件上传信息】ID
     * @return 结果
     */
    @Override
    public int deleteTUploadListByIds(Long[] ids)
    {
        return tUploadListMapper.deleteTUploadListByIds(ids);
    }

    /**
     * 删除【文件上传信息】信息
     * 
     * @param id 【文件上传信息】ID
     * @return 结果
     */
    @Override
    public int deleteTUploadListById(Long id)
    {
        return tUploadListMapper.deleteTUploadListById(id);
    }
}
