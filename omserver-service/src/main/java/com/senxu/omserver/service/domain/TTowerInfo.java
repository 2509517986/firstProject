package com.senxu.omserver.service.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.senxu.omserver.common.annotation.Excel;
import com.senxu.omserver.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * t_tower_info（杆塔信息）对象 t_tower_info
 * 
 * @author ruoyi
 * @date 2021-06-02
 */
public class TTowerInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 杆塔编号 */
    @Excel(name = "杆塔编号")
    private String code;

    /** 杆塔名称 */
    @Excel(name = "杆塔名称")
    private String name;

    /** 管辖部门id */
    @Excel(name = "管辖部门id")
    private Long deptId;

    /** 管辖部门 */
    @Excel(name = "管辖部门")
    private String deptName;

    /** 所属线路id */
    @Excel(name = "所属线路id")
    private Long lineId;


    /** 所属线路名称 */
    @Excel(name = "所属线路名称")
    private String lineName;


    /** 杆塔类型：1 铁塔 2 水泥杆 3 木头杆 */
    @Excel(name = "杆塔类型",dictType = "tower_type")
    private String kind;

    /** 杆塔形状：1 直线杆-单杆
            2 直线杆-双杆
            3 转角杆-耐张杆
            4 转角杆-分单杆
            5 转角杆-双杆
            6 转角杆-三联杆
            7 终端杆
            8 换位杆
             */
    @Excel(name = "杆塔形状",dictType = "tower_shape")
    private String shape;

    /** 塔高（米） */
    @Excel(name = "塔高（米）")
    private BigDecimal high;

    /** 占地面积（米） */
    @Excel(name = "占地面积（米）")
    private String area;

    /** 建成时间 */
    @Excel(name = "建成时间")
    private String completeDate;

    /** 安装点分类：跨越铁路，跨域高速,跨域通航河流,跨域公路,防风防汛点,山火易发区,树障隐患区 */
    @Excel(name = "安装点分类",dictType = "install_type")
    private String installType;

    /** 场景点分类：导线，地线，玻璃绝缘子，陶瓷绝缘子，防震锤，金具 */
    @Excel(name = "场景点分类",dictType = "scene_type")
    private String sceneType;

    /** 状态：1 正常 2 维护中 -1已弃用 */
    @Excel(name = "状态",dictType = "state")
    private Long state;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    /** 经度 */
    @Excel(name = "经度")
    private String longitude;

    /** 维度 */
    @Excel(name = "维度")
    private String latitude;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

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

    /** 线路Id用 */
    private String lineIds;

    private List<String> lineIdList;

    /** 查询关键字 */
    private String keyword;

    /** 安装点分类 用于查询 **/
    private String installTypes;
    /** 场景点分类 用于查询 **/
    private String sceneTypes;
    /** 场景点分类 用于筛选 **/
    private List<String> sceneTypeList;

    /** 构造函数 */
    public TTowerInfo(){
    }
    /** 构造函数 */
    public TTowerInfo(String lineIds){
        this.lineIds= lineIds;
    }

    /** 构造函数 */
    public TTowerInfo(String installTypes,String sceneTypes){
        this.installTypes= installTypes;
        this.sceneTypes= sceneTypes;
    }

    public List<String> getLineIdList() {
        return lineIdList;
    }

    public void setLineIdList(List<String> lineIdList) {
        this.lineIdList = lineIdList;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
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
    public void setKind(String kind) 
    {
        this.kind = kind;
    }

    public String getKind() 
    {
        return kind;
    }
    public void setShape(String shape) 
    {
        this.shape = shape;
    }

    public String getShape() 
    {
        return shape;
    }
    public void setHigh(BigDecimal high)
    {
        this.high = high;
    }

    public BigDecimal getHigh()
    {
        return high;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setCompleteDate(String completeDate) 
    {
        this.completeDate = completeDate;
    }

    public String getCompleteDate() 
    {
        return completeDate;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
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

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getInstallType() {
        return installType;
    }

    public void setInstallType(String installType) {
        this.installType = installType;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public String getInstallTypes() {
        return installTypes;
    }

    public void setInstallTypes(String installTypes) {
        this.installTypes = installTypes;
    }

    public String getSceneTypes() {
        return sceneTypes;
    }

    public void setSceneTypes(String sceneTypes) {
        this.sceneTypes = sceneTypes;
    }

    public List<String> getSceneTypeList() {
        return sceneTypeList;
    }

    public void setSceneTypeList(List<String> sceneTypeList) {
        this.sceneTypeList = sceneTypeList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("name", getName())
            .append("deptId", getDeptId())
            .append("lineId", getLineId())
            .append("kind", getKind())
            .append("shape", getShape())
            .append("high", getHigh())
            .append("area", getArea())
            .append("completeDate", getCompleteDate())
            .append("installType", getInstallType())
            .append("sceneType", getSceneType())
            .append("state", getState())
            .append("address", getAddress())
            .append("remark", getRemark())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("image", getImage())
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
