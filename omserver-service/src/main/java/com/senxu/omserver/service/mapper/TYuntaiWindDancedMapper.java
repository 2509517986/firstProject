package com.senxu.omserver.service.mapper;

import java.util.List;
import com.senxu.omserver.service.domain.TYuntaiWindDanced;

/**
 * 大风舞动报警Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-16
 */
public interface TYuntaiWindDancedMapper 
{
    /**
     * 查询大风舞动报警
     * 
     * @param id 大风舞动报警ID
     * @return 大风舞动报警
     */
    public TYuntaiWindDanced selectTYuntaiWindDancedById(Long id);

    /**
     * 查询大风舞动报警列表
     * 
     * @param tYuntaiWindDanced 大风舞动报警
     * @return 大风舞动报警集合
     */
    public List<TYuntaiWindDanced> selectTYuntaiWindDancedList(TYuntaiWindDanced tYuntaiWindDanced);

    /**
     * 新增大风舞动报警
     * 
     * @param tYuntaiWindDanced 大风舞动报警
     * @return 结果
     */
    public int insertTYuntaiWindDanced(TYuntaiWindDanced tYuntaiWindDanced);

    /**
     * 修改大风舞动报警
     * 
     * @param tYuntaiWindDanced 大风舞动报警
     * @return 结果
     */
    public int updateTYuntaiWindDanced(TYuntaiWindDanced tYuntaiWindDanced);

    /**
     * 删除大风舞动报警
     * 
     * @param id 大风舞动报警ID
     * @return 结果
     */
    public int deleteTYuntaiWindDancedById(Long id);

    /**
     * 批量删除大风舞动报警
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTYuntaiWindDancedByIds(Long[] ids);
}
