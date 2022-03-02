package com.senxu.omserver.service.service.impl;

import java.util.List;

import com.senxu.omserver.common.core.domain.model.LoginUser;
import com.senxu.omserver.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.senxu.omserver.service.mapper.TYuntaiPreviewMapper;
import com.senxu.omserver.service.domain.TYuntaiPreview;
import com.senxu.omserver.service.service.ITYuntaiPreviewService;

/**
 * t_yuntai_preview（图片预览记录）Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-06-11
 */
@Service
public class TYuntaiPreviewServiceImpl implements ITYuntaiPreviewService 
{
    @Autowired
    private TYuntaiPreviewMapper tYuntaiPreviewMapper;

    /**
     * 查询t_yuntai_preview（图片预览记录）
     * 
     * @param id t_yuntai_preview（图片预览记录）ID
     * @return t_yuntai_preview（图片预览记录）
     */
    @Override
    public TYuntaiPreview selectTYuntaiPreviewById(Long id)
    {
        return tYuntaiPreviewMapper.selectTYuntaiPreviewById(id);
    }

    /**
     * 查询t_yuntai_preview（图片预览记录）列表
     * 
     * @param tYuntaiPreview t_yuntai_preview（图片预览记录）
     * @return t_yuntai_preview（图片预览记录）
     */
    @Override
    public List<TYuntaiPreview> selectTYuntaiPreviewList(TYuntaiPreview tYuntaiPreview)
    {
        return tYuntaiPreviewMapper.selectTYuntaiPreviewList(tYuntaiPreview);
    }

    /**
     * 新增t_yuntai_preview（图片预览记录）
     * 
     * @param tYuntaiPreview t_yuntai_preview（图片预览记录）
     * @return 结果
     */
    @Override
    public int insertTYuntaiPreview(TYuntaiPreview tYuntaiPreview)
    {
        return tYuntaiPreviewMapper.insertTYuntaiPreview(tYuntaiPreview);
    }

    /**
     * 修改t_yuntai_preview（图片预览记录）
     * 
     * @param tYuntaiPreview t_yuntai_preview（图片预览记录）
     * @return 结果
     */
    @Override
    public int updateTYuntaiPreview(TYuntaiPreview tYuntaiPreview)
    {
        return tYuntaiPreviewMapper.updateTYuntaiPreview(tYuntaiPreview);
    }

    /**
     * 批量删除t_yuntai_preview（图片预览记录）
     * 
     * @param ids 需要删除的t_yuntai_preview（图片预览记录）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiPreviewByIds(Long[] ids)
    {
        return tYuntaiPreviewMapper.deleteTYuntaiPreviewByIds(ids);
    }

    /**
     * 删除t_yuntai_preview（图片预览记录）信息
     * 
     * @param id t_yuntai_preview（图片预览记录）ID
     * @return 结果
     */
    @Override
    public int deleteTYuntaiPreviewById(Long id)
    {
        return tYuntaiPreviewMapper.deleteTYuntaiPreviewById(id);
    }



    @Override
    public int isPreviewTYuntaiPreviewByIds(Long[] ids, LoginUser user) {
        int result=0;
        for(Long id:ids){
            result=isPreviewTYuntaiPreviewById(id,user);
        }
        return result;
    }

    @Override
    public int isPreviewTYuntaiPreviewById(Long id,LoginUser user) {
        TYuntaiPreview ytPreview=new TYuntaiPreview();
        ytPreview.setPictureId(id);
        ytPreview.setPreviewUserid(user.getUser().getUserId());
        ytPreview.setPreviewUser(user.getUsername());
        ytPreview.setPreviewTime(DateUtils.getNowDate());
        return tYuntaiPreviewMapper.insertTYuntaiPreview(ytPreview);
    }
}
