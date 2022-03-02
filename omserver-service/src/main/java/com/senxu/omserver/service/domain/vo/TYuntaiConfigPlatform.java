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
public class TYuntaiConfigPlatform extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long yuntaiId;


    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String platformIp;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String platformProt;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String platformCard;


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

    public String getPlatformIp() {
        return platformIp;
    }

    public void setPlatformIp(String platformIp) {
        this.platformIp = platformIp;
    }

    public String getPlatformProt() {
        return platformProt;
    }

    public void setPlatformProt(String platformProt) {
        this.platformProt = platformProt;
    }

    public String getPlatformCard() {
        return platformCard;
    }

    public void setPlatformCard(String platformCard) {
        this.platformCard = platformCard;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("platformIp", getPlatformIp())
            .append("platformProt", getPlatformProt())
            .append("platformCard", getPlatformCard())
            .toString();
    }
}
