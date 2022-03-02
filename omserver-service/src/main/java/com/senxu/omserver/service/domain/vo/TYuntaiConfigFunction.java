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
public class TYuntaiConfigFunction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long yuntaiId;


    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionPull;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionLeakage;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionMeteorological;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionWirewaytemp;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionVibrate;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionLineDipangle;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionVibraterate;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionTowerDipangle;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionLineVibrate;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionPickproof;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionFire;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionFaultCheck;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionGale;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionBreeze;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionWave;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionFilth;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionSag;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionTemp;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionCircularCurrent;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionFaultLocation;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionFaultJufang;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionJufangPutu;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionFile;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionImage;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private Integer functionVideo;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String platformIp;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String platformProt;

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

    public Integer getFunctionPull() {
        return functionPull;
    }

    public void setFunctionPull(Integer functionPull) {
        this.functionPull = functionPull;
    }

    public Integer getFunctionLeakage() {
        return functionLeakage;
    }

    public void setFunctionLeakage(Integer functionLeakage) {
        this.functionLeakage = functionLeakage;
    }

    public Integer getFunctionMeteorological() {
        return functionMeteorological;
    }

    public void setFunctionMeteorological(Integer functionMeteorological) {
        this.functionMeteorological = functionMeteorological;
    }

    public Integer getFunctionWirewaytemp() {
        return functionWirewaytemp;
    }

    public void setFunctionWirewaytemp(Integer functionWirewaytemp) {
        this.functionWirewaytemp = functionWirewaytemp;
    }

    public Integer getFunctionVibrate() {
        return functionVibrate;
    }

    public void setFunctionVibrate(Integer functionVibrate) {
        this.functionVibrate = functionVibrate;
    }

    public Integer getFunctionLineDipangle() {
        return functionLineDipangle;
    }

    public void setFunctionLineDipangle(Integer functionLineDipangle) {
        this.functionLineDipangle = functionLineDipangle;
    }

    public Integer getFunctionVibraterate() {
        return functionVibraterate;
    }

    public void setFunctionVibraterate(Integer functionVibraterate) {
        this.functionVibraterate = functionVibraterate;
    }

    public Integer getFunctionTowerDipangle() {
        return functionTowerDipangle;
    }

    public void setFunctionTowerDipangle(Integer functionTowerDipangle) {
        this.functionTowerDipangle = functionTowerDipangle;
    }

    public Integer getFunctionLineVibrate() {
        return functionLineVibrate;
    }

    public void setFunctionLineVibrate(Integer functionLineVibrate) {
        this.functionLineVibrate = functionLineVibrate;
    }

    public Integer getFunctionPickproof() {
        return functionPickproof;
    }

    public void setFunctionPickproof(Integer functionPickproof) {
        this.functionPickproof = functionPickproof;
    }

    public Integer getFunctionFire() {
        return functionFire;
    }

    public void setFunctionFire(Integer functionFire) {
        this.functionFire = functionFire;
    }

    public Integer getFunctionFaultCheck() {
        return functionFaultCheck;
    }

    public void setFunctionFaultCheck(Integer functionFaultCheck) {
        this.functionFaultCheck = functionFaultCheck;
    }

    public Integer getFunctionGale() {
        return functionGale;
    }

    public void setFunctionGale(Integer functionGale) {
        this.functionGale = functionGale;
    }

    public Integer getFunctionBreeze() {
        return functionBreeze;
    }

    public void setFunctionBreeze(Integer functionBreeze) {
        this.functionBreeze = functionBreeze;
    }

    public Integer getFunctionWave() {
        return functionWave;
    }

    public void setFunctionWave(Integer functionWave) {
        this.functionWave = functionWave;
    }

    public Integer getFunctionFilth() {
        return functionFilth;
    }

    public void setFunctionFilth(Integer functionFilth) {
        this.functionFilth = functionFilth;
    }

    public Integer getFunctionSag() {
        return functionSag;
    }

    public void setFunctionSag(Integer functionSag) {
        this.functionSag = functionSag;
    }

    public Integer getFunctionTemp() {
        return functionTemp;
    }

    public void setFunctionTemp(Integer functionTemp) {
        this.functionTemp = functionTemp;
    }

    public Integer getFunctionCircularCurrent() {
        return functionCircularCurrent;
    }

    public void setFunctionCircularCurrent(Integer functionCircularCurrent) {
        this.functionCircularCurrent = functionCircularCurrent;
    }

    public Integer getFunctionFaultLocation() {
        return functionFaultLocation;
    }

    public void setFunctionFaultLocation(Integer functionFaultLocation) {
        this.functionFaultLocation = functionFaultLocation;
    }

    public Integer getFunctionFaultJufang() {
        return functionFaultJufang;
    }

    public void setFunctionFaultJufang(Integer functionFaultJufang) {
        this.functionFaultJufang = functionFaultJufang;
    }

    public Integer getFunctionJufangPutu() {
        return functionJufangPutu;
    }

    public void setFunctionJufangPutu(Integer functionJufangPutu) {
        this.functionJufangPutu = functionJufangPutu;
    }

    public Integer getFunctionFile() {
        return functionFile;
    }

    public void setFunctionFile(Integer functionFile) {
        this.functionFile = functionFile;
    }

    public Integer getFunctionImage() {
        return functionImage;
    }

    public void setFunctionImage(Integer functionImage) {
        this.functionImage = functionImage;
    }

    public Integer getFunctionVideo() {
        return functionVideo;
    }

    public void setFunctionVideo(Integer functionVideo) {
        this.functionVideo = functionVideo;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("functionPull", getFunctionPull())
            .append("functionLeakage", getFunctionLeakage())
            .append("functionMeteorological", getFunctionMeteorological())
            .append("functionWirewaytemp", getFunctionWirewaytemp())
            .append("functionVibrate", getFunctionVibrate())
            .append("functionLineDipangle", getFunctionLineDipangle())
            .append("functionVibraterate", getFunctionVibraterate())
            .append("functionTowerDipangle", getFunctionTowerDipangle())
            .append("functionLineVibrate", getFunctionLineVibrate())
            .append("functionPickproof", getFunctionPickproof())
            .append("functionFire", getFunctionFire())
            .append("functionFaultCheck", getFunctionFaultCheck())
            .append("functionGale", getFunctionGale())
            .append("functionBreeze", getFunctionBreeze())
            .append("functionWave", getFunctionWave())
            .append("functionFilth", getFunctionFilth())
            .append("functionSag", getFunctionSag())
            .append("functionTemp", getFunctionTemp())
            .append("functionCircularCurrent", getFunctionCircularCurrent())
            .append("functionFaultLocation", getFunctionFaultLocation())
            .append("functionFaultJufang", getFunctionFaultJufang())
            .append("functionJufangPutu", getFunctionJufangPutu())
            .append("functionFile", getFunctionFile())
            .append("functionImage", getFunctionImage())
            .append("functionVideo", getFunctionVideo())
            .append("platformIp", getPlatformIp())
            .append("platformProt", getPlatformProt())
            .toString();
    }
}
