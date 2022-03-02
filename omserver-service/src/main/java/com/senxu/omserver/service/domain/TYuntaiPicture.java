package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

//import javax.persistence.*;

/**
 * t_yuntai_picture（云台抓拍图片）对象 t_yuntai_picture
 * 
 * @author ruoyi
 * @date 2021-06-22
 */
/*@Table(name = "t_yuntai_picture")
@Entity*/
public class TYuntaiPicture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 云台ID */
    @Excel(name = "云台ID")
    private Long yuntaiId;

    /** 所属线路 */
    @Excel(name = "所属线路")
    private Long lineId;

    /** 杆塔名称 */
    @Excel(name = "杆塔名称")
    private Long towerId;

    /** 图片目录路径 */
    @Excel(name = "图片目录路径")
    private String imageDir;

    /** 图片（原图） */
    @Excel(name = "图片（原图）")
    private String imageName;

    /** 告警图片目录路径 */
    @Excel(name = "告警图片目录路径")
    private String alarmDir;

    /** 告警图片 */
    @Excel(name = "告警图片")
    private String alarmUrl;

    /** 设备厂商 */
    @Excel(name = "设备厂商")
    private String factory;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String kind;

    /** 杆塔形状 */
    @Excel(name = "设备类型")
    private String shape;



    /** 抓拍人 */
    @Excel(name = "抓拍人")
    private String captureUser;


    /** 抓拍时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "抓拍时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date captureTime;

    /** 拍摄预置位 */
    @Excel(name = "拍摄预置位")
    private String presetBit;

    /** 通道号 */
    @Excel(name = "通道号")
    private String channelNumber;

    /** 图片类型：数据字典配置 */
    @Excel(name = "图片类型：数据字典配置",dictType = "img_type")
    private String imgType;

    /** 图片类型：数据字典配置
     *    TimerCap(0,"定时拍照"),
     *     DomainReqCap(2,"手动拍照"),
     *     AlarmCap(1,"报警上传");
     * */
    //@Column(name = "id",type = MySqlTypeConstant.INT,length = 11,isKey = true,isAutoIncrement = true)
    @Excel(name = "抓拍类型")
    private Integer captureType;

    /** 是否告警：0 否 1 是  */
    @Excel(name = "是否告警",readConverterExp = "0=否,1=是")
    private Integer isAlarm;

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


    /** 线路Id */
    private String lineIds;
    
    /** 线路Id */
    private String towerIds;

    private String[] imgTypes;

    private Integer isBrowse;//是否已预览

    private Long currentUserId;//当前用户

    private Integer imgSize;//返回记录数量

    private TYuntaiPicture historyPicture;//历史图片

    /** 构造函数 */
    public TYuntaiPicture(){
    }
    /** 构造函数 */
    public TYuntaiPicture(String lineIds){
        this.lineIds= lineIds;
    }

    /** 构造函数 */
    public TYuntaiPicture(String lineIds,String towerIds){
        this.lineIds= lineIds;
        this.towerIds=towerIds;
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
    public void setLineId(Long lineId) 
    {
        this.lineId = lineId;
    }

    public Long getLineId() 
    {
        return lineId;
    }
    public void setTowerId(Long towerId) 
    {
        this.towerId = towerId;
    }

    public Long getTowerId() 
    {
        return towerId;
    }
    public void setImageDir(String imageDir) 
    {
        this.imageDir = imageDir;
    }

    public String getImageDir() 
    {
        return imageDir;
    }
    public void setImageName(String imageName) 
    {
        this.imageName = imageName;
    }

    public String getImageName() 
    {
        return imageName;
    }
    public void setAlarmDir(String alarmDir) 
    {
        this.alarmDir = alarmDir;
    }

    public String getAlarmDir() 
    {
        return alarmDir;
    }
    public void setAlarmUrl(String alarmUrl) 
    {
        this.alarmUrl = alarmUrl;
    }

    public String getAlarmUrl() 
    {
        return alarmUrl;
    }
    public void setFactory(String factory) 
    {
        this.factory = factory;
    }

    public String getFactory() 
    {
        return factory;
    }
    public void setKind(String kind) 
    {
        this.kind = kind;
    }

    public String getKind() 
    {
        return kind;
    }
    public void setCaptureUser(String captureUser) 
    {
        this.captureUser = captureUser;
    }

    public String getCaptureUser() 
    {
        return captureUser;
    }
    public void setCaptureTime(Date captureTime) 
    {
        this.captureTime = captureTime;
    }

    public Date getCaptureTime() 
    {
        return captureTime;
    }
    public void setPresetBit(String presetBit) 
    {
        this.presetBit = presetBit;
    }

    public String getPresetBit() 
    {
        return presetBit;
    }
    public void setChannelNumber(String channelNumber) 
    {
        this.channelNumber = channelNumber;
    }

    public String getChannelNumber() 
    {
        return channelNumber;
    }
    public void setImgType(String imgType) 
    {
        this.imgType = imgType;
    }

    public String getImgType() 
    {
        return imgType;
    }
    public void setIsAlarm(Integer isAlarm) 
    {
        this.isAlarm = isAlarm;
    }

    public Integer getIsAlarm() 
    {
        return isAlarm;
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

    public String[] getImgTypes() {
        return imgTypes;
    }

    public void setImgTypes(String[] imgTypes) {
        this.imgTypes = imgTypes;
    }

    public Integer getIsBrowse() {
        return isBrowse;
    }

    public void setIsBrowse(Integer isBrowse) {
        this.isBrowse = isBrowse;
    }

    public Long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(Long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public Integer getImgSize() {
        return imgSize;
    }

    public void setImgSize(Integer imgSize) {
        this.imgSize = imgSize;
    }

    public Integer getCaptureType() {
        return captureType;
    }

    public void setCaptureType(Integer captureType) {
        this.captureType = captureType;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getLineIds() {
        return lineIds;
    }

    public void setLineIds(String lineIds) {
        this.lineIds = lineIds;
    }

    public TYuntaiPicture getHistoryPicture() {
        return historyPicture;
    }

    public void setHistoryPicture(TYuntaiPicture historyPicture) {
        this.historyPicture = historyPicture;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("yuntaiId", getYuntaiId())
            .append("lineId", getLineId())
            .append("towerId", getTowerId())
            .append("imageDir", getImageDir())
            .append("imageName", getImageName())
            .append("alarmDir", getAlarmDir())
            .append("alarmUrl", getAlarmUrl())
            .append("factory", getFactory())
            .append("kind", getKind())
            .append("captureUser", getCaptureUser())
            .append("captureTime", getCaptureTime())
            .append("presetBit", getPresetBit())
            .append("channelNumber", getChannelNumber())
            .append("imgType", getImgType())
            .append("isAlarm", getIsAlarm())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .append("isBrowse", getIsBrowse())
                .append("shape",getShape())
            .append("currentUserId", getCurrentUserId())
            .toString();
    }
}
