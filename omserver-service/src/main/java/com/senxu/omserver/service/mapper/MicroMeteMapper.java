package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.MicroMete;

/**
 * 气象信息获取Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-20
 */
public interface MicroMeteMapper 
{
    /**
     * 查询气象信息获取
     * 
     * @param id 气象信息获取ID
     * @return 气象信息获取
     */
    public MicroMete selectMicroMeteById(Integer id);

    /**
     * 查询气象信息获取列表
     * 
     * @param microMete 气象信息获取
     * @return 气象信息获取集合
     */
    public List<MicroMete> selectMicroMeteList(MicroMete microMete);

    /**
     * 新增气象信息获取
     * 
     * @param microMete 气象信息获取
     * @return 结果
     */
    public int insertMicroMete(MicroMete microMete);

    /**
     * 修改气象信息获取
     * 
     * @param microMete 气象信息获取
     * @return 结果
     */
    public int updateMicroMete(MicroMete microMete);

    /**
     * 删除气象信息获取
     * 
     * @param id 气象信息获取ID
     * @return 结果
     */
    public int deleteMicroMeteById(Integer id);

    /**
     * 批量删除气象信息获取
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMicroMeteByIds(Integer[] ids);
}
