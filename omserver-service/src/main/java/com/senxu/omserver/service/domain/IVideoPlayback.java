package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;

import java.util.Date;
/**
 * t_video_playback（录像回放）Controller
 *
 * @author lvhp
 * @date 2021-10-29
 */

public class IVideoPlayback {

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long id;

    /** 预置位编号 */
    @Excel(name = "预置位编号")
    private String presetBit;

    /** 抓拍时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抓拍时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date captureTime;



    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createUser;

    /** 新增时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "新增时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 设备编号
     */
    @Excel(name = "设备编号")
    private String code;

    /**
     * 视频地址
     */
    private String videoUrl;

    private String filename;


    /**
     * 持续时间 单位 s
     */
    private long duration;



    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }




    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPresetBit() {
        return presetBit;
    }

    public void setPresetBit(String presetBit) {
        this.presetBit = presetBit;
    }

    public Date getCaptureTime() {
        return captureTime;
    }

    public void setCaptureTime(Date captureTime) {
        this.captureTime = captureTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
