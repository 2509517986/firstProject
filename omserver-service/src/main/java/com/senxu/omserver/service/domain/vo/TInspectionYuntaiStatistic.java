package com.senxu.omserver.service.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用于巡检报告视频设备数据统计
 * 
 * @author 潘永潮
 * @date 2021-06-21
 */
public class TInspectionYuntaiStatistic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**设备总量  */
    private BigDecimal total;

    /** 在线总量 */
    private BigDecimal online;

    /** 在线率 */
    private BigDecimal onlineRate;

    /** 离线总量 */
    private BigDecimal offline;

    /** 离线率 */
    private BigDecimal offlineRate;


    /** 休眠数量 */
    private BigDecimal dormancy;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getOnline() {
        return online;
    }

    public void setOnline(BigDecimal online) {
        this.online = online;
    }

    public BigDecimal getOnlineRate() {
        return onlineRate;
    }

    public void setOnlineRate(BigDecimal onlineRate) {
        this.onlineRate = onlineRate;
    }

    public BigDecimal getOffline() {
        return offline;
    }

    public void setOffline(BigDecimal offline) {
        this.offline = offline;
    }

    public BigDecimal getOfflineRate() {
        return offlineRate;
    }

    public void setOfflineRate(BigDecimal offlineRate) {
        this.offlineRate = offlineRate;
    }

    public BigDecimal getDormancy() {
        return dormancy;
    }

    public void setDormancy(BigDecimal dormancy) {
        this.dormancy = dormancy;
    }

    @Override
    public String toString() {
        return "TInspectionYuntaiStatistic{" +
                "total=" + total +
                ", online=" + online +
                ", onlineRate=" + onlineRate +
                ", offline=" + offline +
                ", offlineRate=" + offlineRate +
                ", dormancy=" + dormancy +
                '}';
    }
}
