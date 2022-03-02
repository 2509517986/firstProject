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
public class TYuntaiConfigPicture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long yuntaiId;


    /** $column.columnComment */
    @Excel(name = "终端参数-通道号")
    private String pictureConduit;

    /** $column.columnComment */
    @Excel(name = "终端参数-通道类型")
    private String pictureConduitType;

    /** $column.columnComment */
    @Excel(name = "终端参数-色彩模式")
    private String pictureColor;

    /** $column.columnComment */
    @Excel(name = "终端参数-亮度")
    private String pictureBrightness;

    /** $column.columnComment */
    @Excel(name = "终端参数-对比度")
    private String pictureContrast;

    /** $column.columnComment */
    @Excel(name = "终端参数-饱和度")
    private String pictureSaturation;

    /** $column.columnComment */
    @Excel(name = "终端参数-分辨率")
    private String pictureResolution;

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

    public String getPictureConduit() {
        return pictureConduit;
    }

    public void setPictureConduit(String pictureConduit) {
        this.pictureConduit = pictureConduit;
    }

    public String getPictureConduitType() {
        return pictureConduitType;
    }

    public void setPictureConduitType(String pictureConduitType) {
        this.pictureConduitType = pictureConduitType;
    }

    public String getPictureColor() {
        return pictureColor;
    }

    public void setPictureColor(String pictureColor) {
        this.pictureColor = pictureColor;
    }

    public String getPictureBrightness() {
        return pictureBrightness;
    }

    public void setPictureBrightness(String pictureBrightness) {
        this.pictureBrightness = pictureBrightness;
    }

    public String getPictureContrast() {
        return pictureContrast;
    }

    public void setPictureContrast(String pictureContrast) {
        this.pictureContrast = pictureContrast;
    }

    public String getPictureSaturation() {
        return pictureSaturation;
    }

    public void setPictureSaturation(String pictureSaturation) {
        this.pictureSaturation = pictureSaturation;
    }

    public String getPictureResolution() {
        return pictureResolution;
    }

    public void setPictureResolution(String pictureResolution) {
        this.pictureResolution = pictureResolution;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("pictureConduit", getPictureConduit())
            .append("pictureConduitType", getPictureConduitType())
            .append("pictureColor", getPictureColor())
            .append("pictureBrightness", getPictureBrightness())
            .append("pictureContrast", getPictureContrast())
            .append("pictureSaturation", getPictureSaturation())
            .append("pictureResolution", getPictureResolution())

            .toString();
    }
}
