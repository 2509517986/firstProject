package com.senxu.omserver.service.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import com.senxu.omserver.common.utils.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * t_yuntai_info（云台信息）对象 t_yuntai_info
 * 
 * @author ruoyi
 * @date 2021-06-03
 */
public class TYuntaiInfo<lineIds> extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 编号ID */
    @Excel(name = "编号ID")
    private String code;

    /** 云台名称 */
    @Excel(name = "云台名称")
    private String name;

    /** 设备密码（南网协议传输密码） */
//    @Excel(name = "设备密码")
    private String pasword;


    /** 设备类型：球机、枪机 */
//    @Excel(name = "设备类型：球机、枪机")
    private String kind;

    /** 管辖部门id */
    @Excel(name = "管辖部门id")
    private Long deptId;

    /** 管辖部门 */
    @Excel(name = "管辖部门")
    private String deptName;

    /** 所属线路ID */
    @Excel(name = "所属线路ID")
    private Long lineId;

    /** 所属线路名称 */
    @Excel(name = "所属线路名称")
    private String lineName;


    /** 所属杆塔ID */
    @Excel(name = "所属杆塔ID")
    private Long towerId;

    /** 所属杆塔名称 */
    @Excel(name = "所属杆塔名称")
    private String towerName;

    /** 运行单位 */
    @Excel(name = "运行单位",dictType = "sys_company")
    private String unit;

    /** 高压等级：1 高压 2 超高压 3 特高压 */
    @Excel(name = "高压等级",dictType = "line_voltage")
    private String level;

    /** 安装方向 */
    @Excel(name = "安装方向")
    private String direction;

    /** 接入方式 */
    @Excel(name = "接入方式",dictType = "access_type")
    private Long intoType;

    /** VPN专网卡号 */
    @Excel(name = "VPN专网卡号")
    private String cardNum;

    /** VPN专网卡号2 */
    @Excel(name = "VPN专网卡号2")
    private String cardNum2;

    /** 网络类型 */
    @Excel(name = "网络类型",dictType = "network_type")
    private String network;

    /** 安装状态：0 未安装  1 已安装 */
    @Excel(name = "安装状态",dictType = "install_state")
    private String installState;

    /** 安装时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安装时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installDate;

    /** 隐患类型：数据字典配置 */
    @Excel(name = "隐患类型",dictType = "danger_type")
    private String hiddenDanger;

    /** 隐患详情 */
    @Excel(name = "隐患详情")
    private String hiddenDangerDes;

    /** 设备厂商 */
    @Excel(name = "设备厂商",dictType = "oem")
    private String factory;

    /** 监测类型 */
    @Excel(name = "监测类型",dictType = "monitor_type")
    private String monitorType;

    /** 设备IP */
    @Excel(name = "设备IP")
    private String ip;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    /** 上图状态：1 正常 0 不正常 */
    @Excel(name = "上图状态",dictType = "image_status")
    private Long imageStatus;

    /** 缺销记录 */
    @Excel(name = "缺销记录")
    private String defectRecord;

    /** 状态：1 在线  2 休眠  -1 离线 */
    @Excel(name = "状态",dictType = "state")
    private Long state;



    /** 在线/离线时间(最后活动时间) */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "在线/离线时间(最后活动时间)", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date onlineTime;

    /** 设备在线/离线时长 */
    @Excel(name = "设备在线/离线时长")
    private String onlineDuration;

    @Excel(name = "电量")
    private Integer electricPower;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 维度 */
    @Excel(name = "维度")
    private String latitude;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 软件版本 */
    @Excel(name = "软件版本")
    private String version;

    /** 软件更新时间 */
    @Excel(name = "软件更新时间")
    private String versionUpdate;

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

    /** 线路Id */
    private String lineIds;

    /** 查询关键字 */
    private String keyword;

    /** 线路Id */
    private String towerIds;

    /**电量排序  传入值：0  降序  1 升序**/
    private Integer powerOrd;


    private List<String> states;
    private List<String> factorys;
    private List<String> hiddenDangers;
    private List<String> lineIdList;
    private List<String> towerIdList;
    private String imageStatuss;

    private String sBegDate;//查询的安装开始时间
    private String sEndDate;//查询的安装结束时间

    private Integer missingCount;//缺销记录

    public List<String> getLineIdList() {
        return lineIdList;
    }

    public void setLineIdList(List<String> lineIdList) {
        this.lineIdList = lineIdList;
    }

    public List<String> getTowerIdList() {
        return towerIdList;
    }

    public void setTowerIdList(List<String> towerIdList) {
        this.towerIdList = towerIdList;
    }

    public List<String> getHiddenDangers() {
        return hiddenDangers;
    }

    public void setHiddenDangers(List<String> hiddenDangers) {
        this.hiddenDangers = hiddenDangers;
    }

    /** 构造函数 */
    public TYuntaiInfo(){
    }
    /** 构造函数 */
    public TYuntaiInfo(List<Long> list){
        this.lineIds=StringUtils.list2Str(list,",");
    }

    public TYuntaiInfo(String lineIds){
        this.lineIds= lineIds;
    }

    public TYuntaiInfo(String lineIds,String towerIds){
        this.lineIds= lineIds;
        this.towerIds=towerIds;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public String getImageStatuss() {
        return imageStatuss;
    }

    public void setImageStatuss(String imageStatuss) {
        this.imageStatuss = imageStatuss;
    }

    public Integer getPowerOrd() {
        return powerOrd;
    }

    public void setPowerOrd(Integer powerOrd) {
        this.powerOrd = powerOrd;
    }

    public Integer getMissingCount() {
        return missingCount;
    }

    public void setMissingCount(Integer missingCount) {
        this.missingCount = missingCount;
    }

    public String getLineIds() {
        return lineIds;
    }

    public void setLineIds(String lineIds) {
        this.lineIds = lineIds;
    }

    public String getTowerIds() {
        return towerIds;
    }

    public void setTowerIds(String towerIds) {
        this.towerIds = towerIds;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setKind(String kind) 
    {
        this.kind = kind;
    }

    public String getKind() 
    {
        return kind;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getTowerName() {
        return towerName;
    }

    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setDirection(String direction) 
    {
        this.direction = direction;
    }

    public String getDirection() 
    {
        return direction;
    }
    public void setIntoType(Long intoType) 
    {
        this.intoType = intoType;
    }

    public Long getIntoType() 
    {
        return intoType;
    }
    public void setCardNum(String cardNum) 
    {
        this.cardNum = cardNum;
    }

    public String getCardNum() 
    {
        return cardNum;
    }
    public void setCardNum2(String cardNum2) 
    {
        this.cardNum2 = cardNum2;
    }

    public String getCardNum2() 
    {
        return cardNum2;
    }
    public void setNetwork(String network) 
    {
        this.network = network;
    }

    public String getNetwork() 
    {
        return network;
    }
    public void setInstallState(String installState)
    {
        this.installState = installState;
    }

    public String getInstallState()
    {
        return installState;
    }
    public void setInstallDate(Date installDate)
    {
        this.installDate = installDate;
    }

    public Date getInstallDate()
    {
        return installDate;
    }
    public void setHiddenDanger(String hiddenDanger) 
    {
        this.hiddenDanger = hiddenDanger;
    }

    public String getHiddenDanger() 
    {
        return hiddenDanger;
    }
    public void setHiddenDangerDes(String hiddenDangerDes) 
    {
        this.hiddenDangerDes = hiddenDangerDes;
    }

    public String getHiddenDangerDes() 
    {
        return hiddenDangerDes;
    }
    public void setFactory(String factory) 
    {
        this.factory = factory;
    }

    public String getFactory() 
    {
        return factory;
    }
    public void setMonitorType(String monitorType) 
    {
        this.monitorType = monitorType;
    }

    public String getMonitorType() 
    {
        return monitorType;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setImageStatus(Long imageStatus) 
    {
        this.imageStatus = imageStatus;
    }

    public Long getImageStatus() 
    {
        return imageStatus;
    }
    public void setDefectRecord(String defectRecord) 
    {
        this.defectRecord = defectRecord;
    }

    public String getDefectRecord() 
    {
        return defectRecord;
    }
    public void setState(Long state)
    {
        this.state = state;
    }

    public Long getState()
    {
        return state;
    }

    public void setOnlineTime(Date onlineTime)
    {
        this.onlineTime = onlineTime;
    }

    public Date getOnlineTime()
    {
        return onlineTime;
    }
    public void setOnlineDuration(String onlineDuration) 
    {
        this.onlineDuration = onlineDuration;
    }

    public String getOnlineDuration() 
    {
        return onlineDuration;
    }
    public void setLongitude(String longitude) 
    {
        this.longitude = longitude;
    }

    public String getLongitude() 
    {
        return longitude;
    }
    public void setLatitude(String latitude) 
    {
        this.latitude = latitude;
    }

    public String getLatitude() 
    {
        return latitude;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }
    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }
    public void setVersionUpdate(String versionUpdate) 
    {
        this.versionUpdate = versionUpdate;
    }

    public String getVersionUpdate() 
    {
        return versionUpdate;
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

    public void setElectricPower(Integer electricPower)
    {
        this.electricPower = electricPower;
    }

    public Integer getElectricPower()
    {
        return electricPower;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getsBegDate() {
        return sBegDate;
    }

    public void setsBegDate(String sBegDate) {
        this.sBegDate = sBegDate;
    }

    public String getsEndDate() {
        return sEndDate;
    }

    public void setsEndDate(String sEndDate) {
        this.sEndDate = sEndDate;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public List<String> getFactorys() {
        return factorys;
    }

    public void setFactorys(List<String> factorys) {
        this.factorys = factorys;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("pasword",getPasword())
            .append("kind", getKind())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("lineId", getLineId())
            .append("lineName", getLineName())
            .append("towerId", getTowerId())
            .append("towerName", getTowerName())
            .append("unit", getUnit())
            .append("level", getLevel())
            .append("direction", getDirection())
            .append("intoType", getIntoType())
            .append("cardNum", getCardNum())
            .append("cardNum2", getCardNum2())
            .append("network", getNetwork())
            .append("installState", getInstallState())
            .append("installDate", getInstallDate())
            .append("hiddenDanger", getHiddenDanger())
            .append("hiddenDangerDes", getHiddenDangerDes())
            .append("factory", getFactory())
            .append("monitorType", getMonitorType())
            .append("ip", getIp())
            .append("address", getAddress())
            .append("imageStatus", getImageStatus())
            .append("defectRecord", getDefectRecord())
            .append("state", getState())
            .append("onlineTime", getOnlineTime())
            .append("onlineDuration", getOnlineDuration())
            .append("electricPower", getElectricPower())
            .append("remark", getRemark())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("image", getImage())
            .append("version", getVersion())
            .append("versionUpdate", getVersionUpdate())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("modifyUser", getModifyUser())
            .append("modifyTime", getModifyTime())
            .append("spare0", getSpare0())
            .append("spare1", getSpare1())
            .append("spare2", getSpare2())
            .append("spare3", getSpare3())
            .append("spare4", getSpare4())
            .append("ImageStatuss", getImageStatuss())
            .append("States", getStates())
            .toString();
    }
}
