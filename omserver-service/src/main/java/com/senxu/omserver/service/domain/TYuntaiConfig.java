package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_yuntai_config（云台配置记录）对象 t_yuntai_config
 * 
 * @author ruoyi
 * @date 2021-09-18
 */
public class TYuntaiConfig extends BaseEntity
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

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String platformCard;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String pictureConduit;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String pictureConduitType;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String pictureColor;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String pictureBrightness;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String pictureContrast;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String pictureSaturation;

    /** $column.columnComment */
    @Excel(name = "终端参数-在线时长")
    private String pictureResolution;

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

    /** 新增人 */
    @Excel(name = "新增人")
    private String createUser;

    /** 更新人 */
    @Excel(name = "更新人")
    private String modifyUser;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifyTime;

    /** 备用字段0 */
    @Excel(name = "备用字段0")
    private String spare0;

    /** 备用字段1 */
    @Excel(name = "备用字段1")
    private String spare1;

    /** 备用字段2 */
    @Excel(name = "备用字段2")
    private String spare2;

    /** 备用字段3 */
    @Excel(name = "备用字段3")
    private String spare3;

    /** 备用字段4 */
    @Excel(name = "备用字段4")
    private String spare4;

    public TYuntaiConfig(Long yuntaiId){
        this.yuntaiId=yuntaiId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setYuntaiId(Long yuntaiId) 
    {
        this.yuntaiId = yuntaiId;
    }

    public Long getYuntaiId() 
    {
        return yuntaiId;
    }

    public void setHeartbeatRebootDay(Long heartbeatRebootDay)
    {
        this.heartbeatRebootDay = heartbeatRebootDay;
    }

    public Long getHeartbeatRebootDay()
    {
        return heartbeatRebootDay;
    }
    public void setHeartbeatRebootHour(Long heartbeatRebootHour)
    {
        this.heartbeatRebootHour = heartbeatRebootHour;
    }

    public Long getHeartbeatRebootHour()
    {
        return heartbeatRebootHour;
    }
    public void setHeartbeatRebootMinute(Long heartbeatRebootMinute)
    {
        this.heartbeatRebootMinute = heartbeatRebootMinute;
    }

    public Long getHeartbeatRebootMinute()
    {
        return heartbeatRebootMinute;
    }

    public Integer getHeartbeatCollect() {
        return heartbeatCollect;
    }

    public void setHeartbeatCollect(Integer heartbeatCollect) {
        this.heartbeatCollect = heartbeatCollect;
    }

    public void setHeartbeatInterval(Integer heartbeatInterval)
    {
        this.heartbeatInterval = heartbeatInterval;
    }

    public Integer getHeartbeatInterval() 
    {
        return heartbeatInterval;
    }
    public void setHeartbeatDormancy(Integer heartbeatDormancy) 
    {
        this.heartbeatDormancy = heartbeatDormancy;
    }

    public Integer getHeartbeatDormancy() 
    {
        return heartbeatDormancy;
    }
    public void setHeartbeatOnline(Integer heartbeatOnline) 
    {
        this.heartbeatOnline = heartbeatOnline;
    }

    public Integer getHeartbeatOnline() 
    {
        return heartbeatOnline;
    }
    public void setFunctionPull(Integer functionPull) 
    {
        this.functionPull = functionPull;
    }

    public Integer getFunctionPull() 
    {
        return functionPull;
    }
    public void setFunctionLeakage(Integer functionLeakage) 
    {
        this.functionLeakage = functionLeakage;
    }

    public Integer getFunctionLeakage() 
    {
        return functionLeakage;
    }
    public void setFunctionMeteorological(Integer functionMeteorological) 
    {
        this.functionMeteorological = functionMeteorological;
    }

    public Integer getFunctionMeteorological() 
    {
        return functionMeteorological;
    }
    public void setFunctionWirewaytemp(Integer functionWirewaytemp) 
    {
        this.functionWirewaytemp = functionWirewaytemp;
    }

    public Integer getFunctionWirewaytemp() 
    {
        return functionWirewaytemp;
    }
    public void setFunctionVibrate(Integer functionVibrate) 
    {
        this.functionVibrate = functionVibrate;
    }

    public Integer getFunctionVibrate() 
    {
        return functionVibrate;
    }
    public void setFunctionLineDipangle(Integer functionLineDipangle) 
    {
        this.functionLineDipangle = functionLineDipangle;
    }

    public Integer getFunctionLineDipangle() 
    {
        return functionLineDipangle;
    }
    public void setFunctionVibraterate(Integer functionVibraterate) 
    {
        this.functionVibraterate = functionVibraterate;
    }

    public Integer getFunctionVibraterate() 
    {
        return functionVibraterate;
    }
    public void setFunctionTowerDipangle(Integer functionTowerDipangle) 
    {
        this.functionTowerDipangle = functionTowerDipangle;
    }

    public Integer getFunctionTowerDipangle() 
    {
        return functionTowerDipangle;
    }
    public void setFunctionLineVibrate(Integer functionLineVibrate) 
    {
        this.functionLineVibrate = functionLineVibrate;
    }

    public Integer getFunctionLineVibrate() 
    {
        return functionLineVibrate;
    }
    public void setFunctionPickproof(Integer functionPickproof) 
    {
        this.functionPickproof = functionPickproof;
    }

    public Integer getFunctionPickproof() 
    {
        return functionPickproof;
    }
    public void setFunctionFire(Integer functionFire) 
    {
        this.functionFire = functionFire;
    }

    public Integer getFunctionFire() 
    {
        return functionFire;
    }
    public void setFunctionFaultCheck(Integer functionFaultCheck) 
    {
        this.functionFaultCheck = functionFaultCheck;
    }

    public Integer getFunctionFaultCheck() 
    {
        return functionFaultCheck;
    }
    public void setFunctionGale(Integer functionGale) 
    {
        this.functionGale = functionGale;
    }

    public Integer getFunctionGale() 
    {
        return functionGale;
    }
    public void setFunctionBreeze(Integer functionBreeze) 
    {
        this.functionBreeze = functionBreeze;
    }

    public Integer getFunctionBreeze() 
    {
        return functionBreeze;
    }
    public void setFunctionWave(Integer functionWave) 
    {
        this.functionWave = functionWave;
    }

    public Integer getFunctionWave() 
    {
        return functionWave;
    }
    public void setFunctionFilth(Integer functionFilth) 
    {
        this.functionFilth = functionFilth;
    }

    public Integer getFunctionFilth() 
    {
        return functionFilth;
    }
    public void setFunctionSag(Integer functionSag) 
    {
        this.functionSag = functionSag;
    }

    public Integer getFunctionSag() 
    {
        return functionSag;
    }
    public void setFunctionTemp(Integer functionTemp) 
    {
        this.functionTemp = functionTemp;
    }

    public Integer getFunctionTemp() 
    {
        return functionTemp;
    }
    public void setFunctionCircularCurrent(Integer functionCircularCurrent) 
    {
        this.functionCircularCurrent = functionCircularCurrent;
    }

    public Integer getFunctionCircularCurrent() 
    {
        return functionCircularCurrent;
    }
    public void setFunctionFaultLocation(Integer functionFaultLocation) 
    {
        this.functionFaultLocation = functionFaultLocation;
    }

    public Integer getFunctionFaultLocation() 
    {
        return functionFaultLocation;
    }
    public void setFunctionFaultJufang(Integer functionFaultJufang) 
    {
        this.functionFaultJufang = functionFaultJufang;
    }

    public Integer getFunctionFaultJufang() 
    {
        return functionFaultJufang;
    }
    public void setFunctionJufangPutu(Integer functionJufangPutu) 
    {
        this.functionJufangPutu = functionJufangPutu;
    }

    public Integer getFunctionJufangPutu() 
    {
        return functionJufangPutu;
    }
    public void setFunctionFile(Integer functionFile) 
    {
        this.functionFile = functionFile;
    }

    public Integer getFunctionFile() 
    {
        return functionFile;
    }
    public void setFunctionImage(Integer functionImage) 
    {
        this.functionImage = functionImage;
    }

    public Integer getFunctionImage() 
    {
        return functionImage;
    }
    public void setFunctionVideo(Integer functionVideo) 
    {
        this.functionVideo = functionVideo;
    }

    public Integer getFunctionVideo() 
    {
        return functionVideo;
    }
    public void setPlatformIp(String platformIp) 
    {
        this.platformIp = platformIp;
    }

    public String getPlatformIp() 
    {
        return platformIp;
    }
    public void setPlatformProt(String platformProt) 
    {
        this.platformProt = platformProt;
    }

    public String getPlatformProt() 
    {
        return platformProt;
    }
    public void setPlatformCard(String platformCard) 
    {
        this.platformCard = platformCard;
    }

    public String getPlatformCard() 
    {
        return platformCard;
    }
    public void setPictureConduit(String pictureConduit) 
    {
        this.pictureConduit = pictureConduit;
    }

    public String getPictureConduit() 
    {
        return pictureConduit;
    }
    public void setPictureConduitType(String pictureConduitType) 
    {
        this.pictureConduitType = pictureConduitType;
    }

    public String getPictureConduitType() 
    {
        return pictureConduitType;
    }
    public void setPictureColor(String pictureColor) 
    {
        this.pictureColor = pictureColor;
    }

    public String getPictureColor() 
    {
        return pictureColor;
    }
    public void setPictureBrightness(String pictureBrightness) 
    {
        this.pictureBrightness = pictureBrightness;
    }

    public String getPictureBrightness() 
    {
        return pictureBrightness;
    }
    public void setPictureContrast(String pictureContrast) 
    {
        this.pictureContrast = pictureContrast;
    }

    public String getPictureContrast() 
    {
        return pictureContrast;
    }
    public void setPictureSaturation(String pictureSaturation) 
    {
        this.pictureSaturation = pictureSaturation;
    }

    public String getPictureSaturation() 
    {
        return pictureSaturation;
    }
    public void setPictureResolution(String pictureResolution) 
    {
        this.pictureResolution = pictureResolution;
    }

    public String getPictureResolution() 
    {
        return pictureResolution;
    }
    public void setVideoConduit(String videoConduit) 
    {
        this.videoConduit = videoConduit;
    }

    public String getVideoConduit() 
    {
        return videoConduit;
    }
    public void setVideoConduitType(String videoConduitType) 
    {
        this.videoConduitType = videoConduitType;
    }

    public String getVideoConduitType() 
    {
        return videoConduitType;
    }
    public void setVideoRate(String videoRate) 
    {
        this.videoRate = videoRate;
    }

    public String getVideoRate() 
    {
        return videoRate;
    }
    public void setVideoFrameInterval(String videoFrameInterval) 
    {
        this.videoFrameInterval = videoFrameInterval;
    }

    public String getVideoFrameInterval() 
    {
        return videoFrameInterval;
    }
    public void setVideoEncoded(String videoEncoded) 
    {
        this.videoEncoded = videoEncoded;
    }

    public String getVideoEncoded() 
    {
        return videoEncoded;
    }
    public void setVideoBitrateType(String videoBitrateType) 
    {
        this.videoBitrateType = videoBitrateType;
    }

    public String getVideoBitrateType() 
    {
        return videoBitrateType;
    }
    public void setVideoBitrate(String videoBitrate) 
    {
        this.videoBitrate = videoBitrate;
    }

    public String getVideoBitrate() 
    {
        return videoBitrate;
    }
    public void setVideoResolution(String videoResolution) 
    {
        this.videoResolution = videoResolution;
    }

    public String getVideoResolution() 
    {
        return videoResolution;
    }
    public void setUpgradePack(String upgradePack) 
    {
        this.upgradePack = upgradePack;
    }

    public String getUpgradePack() 
    {
        return upgradePack;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setModifyUser(String modifyUser) 
    {
        this.modifyUser = modifyUser;
    }

    public String getModifyUser() 
    {
        return modifyUser;
    }
    public void setModifyTime(Date modifyTime) 
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() 
    {
        return modifyTime;
    }
    public void setSpare0(String spare0) 
    {
        this.spare0 = spare0;
    }

    public String getSpare0() 
    {
        return spare0;
    }
    public void setSpare1(String spare1) 
    {
        this.spare1 = spare1;
    }

    public String getSpare1() 
    {
        return spare1;
    }
    public void setSpare2(String spare2) 
    {
        this.spare2 = spare2;
    }

    public String getSpare2() 
    {
        return spare2;
    }
    public void setSpare3(String spare3) 
    {
        this.spare3 = spare3;
    }

    public String getSpare3() 
    {
        return spare3;
    }
    public void setSpare4(String spare4) 
    {
        this.spare4 = spare4;
    }

    public String getSpare4() 
    {
        return spare4;
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
            .append("platformCard", getPlatformCard())
            .append("pictureConduit", getPictureConduit())
            .append("pictureConduitType", getPictureConduitType())
            .append("pictureColor", getPictureColor())
            .append("pictureBrightness", getPictureBrightness())
            .append("pictureContrast", getPictureContrast())
            .append("pictureSaturation", getPictureSaturation())
            .append("pictureResolution", getPictureResolution())
            .append("videoConduit", getVideoConduit())
            .append("videoConduitType", getVideoConduitType())
            .append("videoRate", getVideoRate())
            .append("videoFrameInterval", getVideoFrameInterval())
            .append("videoEncoded", getVideoEncoded())
            .append("videoBitrateType", getVideoBitrateType())
            .append("videoBitrate", getVideoBitrate())
            .append("videoResolution", getVideoResolution())
            .append("upgradePack", getUpgradePack())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("modifyUser", getModifyUser())
            .append("modifyTime", getModifyTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
