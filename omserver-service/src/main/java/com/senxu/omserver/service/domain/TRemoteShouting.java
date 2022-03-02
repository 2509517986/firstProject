package com.senxu.omserver.service.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_remote_shouting（无人机远程喊话）对象 t_remote_shouting
 * 
 * @author ruoyi
 * @date 2022-01-25
 */
public class TRemoteShouting
{
    private static final long serialVersionUID = 1L;

    /** 喊话ID */
    private Long shoutingId;

    /** 机巢ID */
    @Excel(name = "机巢ID")
    private Long airportId;

    /** 无人机ID(多翼的ID) */
    @Excel(name = "无人机ID(多翼的ID)")
    private String uavId;

    /** 输入方式：1 文本输入   2 语音输入 */
    @Excel(name = "输入方式：1 文本输入   2 语音输入")
    private String inputType;

    /** 是否循环播放： 0 否   1是 */
    @Excel(name = "是否循环播放： 0 否   1是")
    private Integer circulatePlay;

    /** 循环次数 */
    @Excel(name = "循环次数")
    private Long circulateCount;

    /** 声音类型：1 女声   2 男生 */
    @Excel(name = "声音类型：1 女声   2 男生")
    private String soundType;

    /** 喊话内容 */
    @Excel(name = "喊话内容")
    private String content;

    /** 录入人 */
    @Excel(name = "录入人")
    private String inputUser;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inputTime;

    /** 发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sendTime;

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

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setShoutingId(Long shoutingId) 
    {
        this.shoutingId = shoutingId;
    }

    public Long getShoutingId() 
    {
        return shoutingId;
    }
    public void setAirportId(Long airportId) 
    {
        this.airportId = airportId;
    }

    public Long getAirportId() 
    {
        return airportId;
    }
    public void setUavId(String uavId) 
    {
        this.uavId = uavId;
    }

    public String getUavId() 
    {
        return uavId;
    }
    public void setInputType(String inputType) 
    {
        this.inputType = inputType;
    }

    public String getInputType() 
    {
        return inputType;
    }
    public void setCirculatePlay(Integer circulatePlay) 
    {
        this.circulatePlay = circulatePlay;
    }

    public Integer getCirculatePlay() 
    {
        return circulatePlay;
    }
    public void setCirculateCount(Long circulateCount) 
    {
        this.circulateCount = circulateCount;
    }

    public Long getCirculateCount() 
    {
        return circulateCount;
    }
    public void setSoundType(String soundType) 
    {
        this.soundType = soundType;
    }

    public String getSoundType() 
    {
        return soundType;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setInputUser(String inputUser) 
    {
        this.inputUser = inputUser;
    }

    public String getInputUser() 
    {
        return inputUser;
    }
    public void setInputTime(Date inputTime) 
    {
        this.inputTime = inputTime;
    }

    public Date getInputTime() 
    {
        return inputTime;
    }
    public void setSendTime(Date sendTime) 
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime() 
    {
        return sendTime;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("shoutingId", getShoutingId())
            .append("airportId", getAirportId())
            .append("uavId", getUavId())
            .append("inputType", getInputType())
            .append("circulatePlay", getCirculatePlay())
            .append("circulateCount", getCirculateCount())
            .append("soundType", getSoundType())
            .append("content", getContent())
            .append("inputUser", getInputUser())
            .append("inputTime", getInputTime())
            .append("sendTime", getSendTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .toString();
    }
}
