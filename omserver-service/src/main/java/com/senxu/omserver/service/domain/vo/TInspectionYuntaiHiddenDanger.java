package com.senxu.omserver.service.domain.vo;

import com.senxu.omserver.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 用于巡检报告视频设备数据统计
 * 
 * @author 潘永潮
 * @date 2021-06-21
 */
public class TInspectionYuntaiHiddenDanger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**隐患类型  */
    private String hiddenDanger;

    /** 类型统计 */
    private int total;


    public String getHiddenDanger() {
        return hiddenDanger;
    }

    public void setHiddenDanger(String hiddenDanger) {
        this.hiddenDanger = hiddenDanger;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "TInspectionYuntaiHiddenDanger{" +
                "hiddenDanger='" + hiddenDanger + '\'' +
                ", total=" + total +
                '}';
    }
}
