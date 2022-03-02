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
public class TYuntaiConfigVideo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long yuntaiId;


    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String videoConduit;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String videoConduitType;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String videoRate;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String videoFrameInterval;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String videoEncoded;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String videoBitrateType;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String videoBitrate;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String videoResolution;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String upgradePack;


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

    public String getVideoConduit() {
        return videoConduit;
    }

    public void setVideoConduit(String videoConduit) {
        this.videoConduit = videoConduit;
    }

    public String getVideoConduitType() {
        return videoConduitType;
    }

    public void setVideoConduitType(String videoConduitType) {
        this.videoConduitType = videoConduitType;
    }

    public String getVideoRate() {
        return videoRate;
    }

    public void setVideoRate(String videoRate) {
        this.videoRate = videoRate;
    }

    public String getVideoFrameInterval() {
        return videoFrameInterval;
    }

    public void setVideoFrameInterval(String videoFrameInterval) {
        this.videoFrameInterval = videoFrameInterval;
    }

    public String getVideoEncoded() {
        return videoEncoded;
    }

    public void setVideoEncoded(String videoEncoded) {
        this.videoEncoded = videoEncoded;
    }

    public String getVideoBitrateType() {
        return videoBitrateType;
    }

    public void setVideoBitrateType(String videoBitrateType) {
        this.videoBitrateType = videoBitrateType;
    }

    public String getVideoBitrate() {
        return videoBitrate;
    }

    public void setVideoBitrate(String videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public String getVideoResolution() {
        return videoResolution;
    }

    public void setVideoResolution(String videoResolution) {
        this.videoResolution = videoResolution;
    }

    public String getUpgradePack() {
        return upgradePack;
    }

    public void setUpgradePack(String upgradePack) {
        this.upgradePack = upgradePack;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("videoConduit", getVideoConduit())
            .append("videoConduitType", getVideoConduitType())
            .append("videoRate", getVideoRate())
            .append("videoFrameInterval", getVideoFrameInterval())
            .append("videoEncoded", getVideoEncoded())
            .append("videoBitrateType", getVideoBitrateType())
            .append("videoBitrate", getVideoBitrate())
            .append("videoResolution", getVideoResolution())
            .append("upgradePack", getUpgradePack())
            .toString();
    }
}
