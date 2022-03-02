package com.senxu.omserver.service.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;

import java.util.Date;

/**
 * t_function_parameters（功能参数）对象 t_function_parameters
 *
 * @author lvhp
 * @date 2021-10-25
 */
public class TFunctionParameters{
    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long id;

    /**
     * 亮度
     */
    @Excel(name = "亮度")
    private Integer brightness;

    /**
     * 对比度
     */
    @Excel(name = "对比度")
    private Integer contrast;

    /**
     * 饱和度
     */
    @Excel(name = "饱和度")
    private Integer saturation;

    /**
     * 雨刮
     */
    @Excel(name = "雨刮")
    private int wiper;

    /**
     * 视窗加热
     */
    @Excel(name = "视窗加热")
    private int windowsHeat;

    /**
     * 云台ID
     */
    @Excel(name = "云台ID")
    private Long yuntaiId;

    private int pictureColor1;//通道1色彩选择


    private int pictureResolution1;//通道1图像大小（分辨率）

    public int getPictureColor1() {
        return pictureColor1;
    }

    public void setPictureColor1(int pictureColor1) {
        this.pictureColor1 = pictureColor1;
    }

    public int getPictureResolution1() {
        return pictureResolution1;
    }

    public void setPictureResolution1(int pictureResolution1) {
        this.pictureResolution1 = pictureResolution1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public Integer getContrast() {
        return contrast;
    }

    public void setContrast(Integer contrast) {
        this.contrast = contrast;
    }

    public Integer getSaturation() {
        return saturation;
    }

    public void setSaturation(Integer saturation) {
        this.saturation = saturation;
    }

    public int getWiper() {
        return wiper;
    }

    public void setWiper(int wiper) {
        this.wiper = wiper;
    }

    public int getWindowsHeat() {
        return windowsHeat;
    }

    public void setWindowsHeat(int windowsHeat) {
        this.windowsHeat = windowsHeat;
    }

    public Long getYuntaiId() {
        return yuntaiId;
    }

    public void setYuntaiId(Long yuntaiId) {
        this.yuntaiId = yuntaiId;
    }

    public TFunctionParameters() {
    }

    public TFunctionParameters(Integer brightness, Integer contrast, Integer saturation, int wiper, int windowsHeat, Long yuntaiId) {
        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.wiper = wiper;
        this.windowsHeat = windowsHeat;
        this.yuntaiId = yuntaiId;
    }
}
