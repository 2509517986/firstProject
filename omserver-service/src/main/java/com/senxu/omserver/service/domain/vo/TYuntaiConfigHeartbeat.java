package com.senxu.omserver.service.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * t_yuntai_config（云台配置记录）对象 t_yuntai_config
 * 
 * @author pyc
 * @date 2021-09-18
 */
public class TYuntaiConfigHeartbeat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long yuntaiId;

    /** 终端参数-硬件重启时间-天：0 每天 1 一天  2 两天 */
    @Excel(name = "终端参数-硬件重启时间-天：0 每天 1 一天  2 两天")
    private Long heartbeatRebootDay;

    /** 终端参数-硬件重启时间-时 */
    @Excel(name = "终端参数-硬件重启时间-时")
    private Long heartbeatRebootHour;

    /** 终端参数-硬件重启时间-分 */
    @Excel(name = "终端参数-硬件重启时间-分")
    private Long heartbeatRebootMinute;

    /** 终端参数-采集间隔 */
    @Excel(name = "终端参数-采集间隔")
    private Integer heartbeatCollect;

    /** 终端参数-采集间隔 */
    @Excel(name = "终端参数-采集间隔")
    private Integer heartbeatInterval;

    /** 终端参数-休眠时长 */
    @Excel(name = "终端参数-休眠时长")
    private Integer heartbeatDormancy;

    /** 终端参数-在线时长 */
    @Excel(name = "终端参数-在线时长")
    private Integer heartbeatOnline;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYuntaiId() {
        return yuntaiId;
    }

    public void setYuntaiId(Long yuntaiId) {
        this.yuntaiId = yuntaiId;
    }

    public Long getHeartbeatRebootDay() {
        return heartbeatRebootDay;
    }

    public void setHeartbeatRebootDay(Long heartbeatRebootDay) {
        this.heartbeatRebootDay = heartbeatRebootDay;
    }

    public Long getHeartbeatRebootHour() {
        return heartbeatRebootHour;
    }

    public void setHeartbeatRebootHour(Long heartbeatRebootHour) {
        this.heartbeatRebootHour = heartbeatRebootHour;
    }

    public Long getHeartbeatRebootMinute() {
        return heartbeatRebootMinute;
    }

    public void setHeartbeatRebootMinute(Long heartbeatRebootMinute) {
        this.heartbeatRebootMinute = heartbeatRebootMinute;
    }

    public Integer getHeartbeatCollect() {
        return heartbeatCollect;
    }

    public void setHeartbeatCollect(Integer heartbeatCollect) {
        this.heartbeatCollect = heartbeatCollect;
    }

    public Integer getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(Integer heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public Integer getHeartbeatDormancy() {
        return heartbeatDormancy;
    }

    public void setHeartbeatDormancy(Integer heartbeatDormancy) {
        this.heartbeatDormancy = heartbeatDormancy;
    }

    public Integer getHeartbeatOnline() {
        return heartbeatOnline;
    }

    public void setHeartbeatOnline(Integer heartbeatOnline) {
        this.heartbeatOnline = heartbeatOnline;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
                .append("heartbeatRebootDay", getHeartbeatRebootDay())
                .append("heartbeatRebootHour", getHeartbeatRebootHour())
                .append("heartbeatRebootMinute", getHeartbeatRebootMinute())
            .append("heartbeatCollect", getHeartbeatCollect())
            .append("heartbeatInterval", getHeartbeatInterval())
            .append("heartbeatDormancy", getHeartbeatDormancy())
            .append("heartbeatOnline", getHeartbeatOnline())
            .toString();
    }
}
