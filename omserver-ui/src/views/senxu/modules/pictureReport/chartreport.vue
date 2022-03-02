<template>
<!--巡检报告-->
<el-scrollbar style="height:100%;">
    <el-row class="app-li" :gutter="10">
        <el-col :span="24">
            <el-card shadow="never">
                <div class="des">
                    <div class="tit"><h4>巡检人：</h4><div class="rm" style="width:120px;" v-if="!editStatus">{{inspector}}</div><div class="rm" v-else><el-input style="width:120px;" size="mini" v-model="inspector"></el-input></div></div>
                    <div class="tit"><h4>巡检计划：</h4><div class="rm" style="width:120px;" v-if="!editStatus">{{inspectionPlan}}</div><div class="rm" v-else><el-input style="width:120px;" size="mini" v-model="inspectionPlan"></el-input></div></div>
                    <div class="tit"><h4>巡检时间：</h4><div class="rm" v-if="!editStatus">{{inspectionTime}}</div><div class="rm" v-else><el-date-picker size="mini" :picker-options="expireTimeOption" format="yyyy-MM-dd" @change="changeDate" clear-icon="" value-format="yyyy-MM-dd" v-model="inspectionTime" type="date" placeholder="选择日期"></el-date-picker></div></div>
                    <el-button type="success" size="mini" class="btn ml" @click="editStatus = !editStatus">{{editStatus?'取消':'编辑'}}</el-button>
                    <!-- <el-button type="primary" size="mini" class="btn" @click="reset">新增</el-button> -->
                    <el-button type="primary" size="mini" class="btn" @click="add_inspectionReport">生成巡检报告</el-button>
                    <!-- <el-button type="primary" size="mini" class="btn" :disabled="editStatus" @click="handleExport">导出报表</el-button> -->
                </div>
                <div class="conclusion">
                    <h4>巡检结论：</h4>
                    <p v-if="!editStatus">{{inspectionResult}}</p>
                    <div v-else class="textarea"><el-input style="width:805px;" size="mini" v-model="inspectionResult"></el-input></div>
                </div>
            </el-card>
        </el-col>
    </el-row>
    <el-row class="app-li" :gutter="10">
        <el-col :span="12">
            <el-card shadow="never" style="height:164px;">
                <h4>视频设备数据统计：</h4>
                <div class="videodata">
                    <div class="vchart"><pie-simple-chart :chart-data="resetChart(yuntaiStatistic[0])" className="piesimplechart"></pie-simple-chart></div>
                    <div class="rightcon">
                        <div class="item">
                            <span>设备总量</span>
                            <!-- <span>{{yuntaiStatistic.length>0 && yuntaiStatistic[0].total?yuntaiStatistic[0].total:0}}</span> -->
                            <span>{{total}}</span>
                        </div>
                        <div class="item">
                            <span>在线总量</span>
                            <!-- <span class="blue">{{yuntaiStatistic.length>0 && yuntaiStatistic[0].online?yuntaiStatistic[0].online:0}}</span> -->
                            <span>{{online}}</span>
                        </div>
                        <div class="item">
                            <span>在线率</span>
                            <!-- <span class="blue">{{yuntaiStatistic.length>0 && yuntaiStatistic[0].onlineRate?(yuntaiStatistic[0].onlineRate * 100).toFixed(2):0}}%</span> -->
                            <span>{{onlineRate}}</span>
                        </div>
                        <div class="item">
                            <span>离线总量</span>
                            <!-- <span class="red">{{yuntaiStatistic.length>0 && yuntaiStatistic[0].offline?yuntaiStatistic[0].offline:0}}</span> -->
                            <span>{{offline}}</span>
                        </div>
                        <div class="item">
                            <span>离线率</span>
                            <!-- <span class="red">{{yuntaiStatistic.length>0 && yuntaiStatistic[0].offlineRate?(yuntaiStatistic[0].offlineRate * 100).toFixed(2):0}}%</span> -->
                            <span>{{offlineRate}}</span>
                        </div>
                    </div>
                </div>
            </el-card>
        </el-col>
        <!-- <el-col :span="12">
            <el-card shadow="never" style="height:164px;">
                <h4>在线设备类型：</h4>
                <div class="onlinedevice" v-if="!yuntaiHiddenDanger.lenght>0">
                    <swiper class="swiper" :options="swiperOptionTop" ref="swiperTop">
                        <swiper-slide v-for="(e,i) in chartdata2" :key="i"> -->
                            <!-- 图表 -->
                            <!-- <pie-doughnut-chart :chart-data="resetChart(e)" className="piedoughnutchart"></pie-doughnut-chart>
                            <span>{{e.name}}</span>
                        </swiper-slide>                                    
                    </swiper>
                    <i class="el-icon-arrow-left arrowleft" slot="button-prev"></i>
                    <i class="el-icon-arrow-right arrowright" slot="button-next"></i>
                </div>
                <el-empty description="暂无数据" v-else></el-empty>
            </el-card>
        </el-col> -->
    </el-row>
    <el-row class="app-li" :gutter="10">
        <el-col :span="24">
            <el-card shadow="never">
                <h4>分类筛选：</h4>
                <el-button size="mini" type="primary" style="position:absolute;top:10px;right:20px;" @click="filterStatus = !filterStatus">{{filterStatus?"展开":"折叠"}}</el-button>
                <div class="typelist" v-if="!filterStatus">
                    <div class="item" v-for="(e,i) in checklist" :key="i">
                        <h4>{{e.name}}：</h4>
                        <div class="checkboxs">
                            <el-checkbox  size="mini" class="checkall" v-model="e.checkAll" @change="val => handleCheckAllChange(val, e)">全选</el-checkbox>
                            <el-checkbox-group  size="mini" v-model="e.checkedEquipments" @change="val => handleCheckedCitiesChange(val, e)">
                                <el-checkbox  size="mini" :label="ee.dictValue" v-for="(ee,ii) in e.list" :key="ii">{{ee.dictLabel}}</el-checkbox>
                            </el-checkbox-group>
                        </div>
                    </div>
                </div>
            </el-card>
        </el-col>
    </el-row>
    <el-row class="app-li" :gutter="10">
        <el-col :span="24">
            <el-card shadow="never">
                <h4>巡检结果统计：</h4>
                <el-row :gutter="10" class="reportContainer" v-if="picss.length>0">
                    <el-col :span="8" v-for="(e,i) in picss" :key="i" style="margin: 5px 0;">
                        <div class="imgs">
                            <el-tooltip class="item" effect="dark" content="双击查看详情" placement="top">
                                <div class="imgitem"  @dblclick="dblclick(e,e.pictures[0])"><div class="img"><img :src="e.pictures&&e.pictures.length>0?reDomain(e.pictures[0].imageDir,e.pictures[0].imageName):require('$img/no.jpg')" alt=""><div class="mask"><span>{{e.yuntai.name?e.yuntai.name:'暂无名称'}}</span><span>{{e.pictures&&e.pictures.length>0?e.pictures[0].captureTime:'暂无时间'}}</span></div></div><span class="title">巡检图片</span></div>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="双击查看详情" placement="top">
                                <div class="imgitem" @dblclick="dblclick(e, e.pictures[1])"><div class="img"><img :src="e.pictures&&e.pictures.length>1?reDomain(e.pictures[1].imageDir,e.pictures[1].imageName):require('$img/no.jpg')" alt=""><div class="mask"><span>{{e.yuntai.name?e.yuntai.name:'暂无名称'}}</span><span>{{e.pictures&&e.pictures.length>1?e.pictures[1].captureTime:'暂无时间'}}</span></div></div><span class="title">历史图片</span></div>
                            </el-tooltip>
                        </div>
                        <el-row class="table">
                            <el-col :span="24">
                                <span>杆塔：{{e.yuntai?e.yuntai.towerName:"未知"}}</span>
                                <!-- <el-button  size="mini" type="danger" @click="alarmClick({id:e.pictures[0].pictureId,captureUser:e.pictures[0].captureUser,captureTime:e.pictures[0].captureTime,imageDir:e.pictures[0].imageDir,imageName:e.pictures[0].imageName},{lineName:e.pictures[0].lineName,towerName:e.pictures[0].towerName,name:e.yuntai.name})" :disabled="e.pictures&&e.pictures.length>0&&e.pictures[0].isAlarm==1">{{e.isAlarm==1?"已告警":"手动报警"}}</el-button> -->
                            </el-col>
                            <el-col :span="12">
                                <el-tooltip class="item" effect="dark" :content='e.pictures&&e.pictures.length>0&&e.pictures[0].sceneType?e.pictures[0].sceneType:"未知"' placement="top">
                                    <span class="cl">场景点分类：{{e.pictures&&e.pictures.length>0&&e.pictures[0].sceneType?e.pictures[0].sceneType:"未知"}}</span>
                                </el-tooltip>
                            </el-col>
                            <!-- <el-col :span="12">
                                <span>智能巡检结果：未知</span>
                            </el-col> -->
                            <el-col :span="12">
                                <el-tooltip class="item" effect="dark" :content='e.pictures&&e.pictures.length>0&&e.pictures[0].installType?e.pictures[0].installType:"未知"' placement="top">
                                    <span class="cl">安装点分类：{{e.pictures&&e.pictures.length>0&&e.pictures[0].installType?e.pictures[0].installType:"未知"}}</span>
                                </el-tooltip>
                            </el-col>
                            <!-- <el-col :span="12">
                                <span>人工诊断：未知</span>
                            </el-col>
                            <el-col :span="24">
                                <span>备注：未知</span>
                            </el-col> -->
                        </el-row>
                    </el-col>
                </el-row>
                <el-empty description="暂无数据" v-else></el-empty>
                <custom-pagination  v-show="picss.length>0" :total="total" :pageSizes="[3,6,8,12]" :page.sync="pageNum" :limit.sync="pageSize"  @pagination="getList"  :autoplay="false"></custom-pagination>
            </el-card>
        </el-col>                   
    </el-row>
    <!-- 显示详情 -->
    <report-imgs v-if="show2" :yuntaiId="thisPicInfo.yuntaiId" :day="inspectionTime" :presetBit="thisPicInfo.presetBit"></report-imgs>
    <!-- 手动告警 -->
    <manual-alarm v-if="visible3" :alarminfo="thisalarm" :yuntaiinfo="thisyuntai"></manual-alarm>
</el-scrollbar>  
</template>
<script>
import { add_inspectionReport, get_inspectionReport, export_inspectionReport, get_report_pictureList, del_inspectionReport, get_inspectionReport_listByDay, get_inspectionReport_id } from "@/api/senxu/inspectionReport/index";
import { Swiper, SwiperSlide} from 'vue-awesome-swiper'
import { mapGetters } from 'vuex'
import 'swiper/css/swiper.css'
import * as global  from "@/utils/global"
export default {
    name: 'chartReport',
    props: ['treeData', 'childData'],
    components: {Swiper,SwiperSlide},
    data () {
        return {
            thisInfo: {},
            filterStatus:false, //折叠/显示
            thisalarm: {}, // 图片信息
            thisyuntai: {}, // 云台信息
            show2: false, // 是否显示详情
            editStatus:false, // 编辑状态
            id: undefined,
            inspectionPlan: undefined, // 巡检计划
            inspectionResult: undefined,//巡检结论
            inspectionTime: undefined, //巡检时间
            inspector: undefined, //巡检人
            installType: undefined, // 安装分类
            sceneType: undefined, // 场景分类
            yuntaiStatistic: [], // 视频设备数据统计
            yuntaiHiddenDanger: [], //在线设备类型
            lineIds: undefined, // lineID
            total: 0,
            picss: [], // 图片列表
            pageNum:1,
            pageSize:3,
            visible3: false, //  手动告警
            online: 0,
            onlineRate: 0,
            offline: 0,
            offlineRate: 0,
            expireTimeOption: {
                // 当天后不可以选择
                disabledDate(date) {
                    return date.getTime() > Date.now();
                }
            },
            swiperOptionTop: {
                slidesPerView: 4,
                spaceBetween: 0,
                slidesPerGroup: 4,
                observer:true,//修改swiper自己或子元素时，自动初始化swiper
                observeParents:true,         
                navigation: {
                    nextEl: '.arrowright',
                    prevEl: '.arrowleft'
                }
            },
            thisPicInfo: {}, // 当前图片信息
            chartdata: {name: "视频数据统计",total:288,online:218,offline:5,onlinerate: '99.03%',offlinerate: '99.03%'},
            // chartdata2: [
            //     {name: '重要跨越', total:0,online:0,offline:0,onlinerate: '99.03%',offlinerate: '99.03%'}
            //     ,{name: '防风防汛点', total:0,online:0,offline:0,onlinerate: '99.03%',offlinerate: '99.03%'}
            //     ,{name: '建筑施工', total:0,online:0,offline:0,onlinerate: '99.03%',offlinerate: '99.03%'}
            //     ,{name: '山火易发区', total:0,online:0,offline:0,onlinerate: '99.03%',offlinerate: '99.03%'}
            //     ,{name: '一所春运供保电', total:0,online:0,offline:0,onlinerate: '99.03%',offlinerate: '99.03%'}
            // ],
            checklist: [
                {   
                    name: "安装点分类",
                    checkAll: true,
                    checkedEquipments: [],
                    key: 'installType',
                    list: {}
                },
                {
                    name: "场景点分类",
                    checkAll: true,
                    checkedEquipments: [],
                    key: 'sceneType',
                    list: {}
                }                
            ],
        }
    },
    watch: {
        treeData: {
            handler (newval) {
                this.getTreeData(newval);
            },
            deep: true
        }
    },
    created() {
        this.inspectionTime = this.parseTime(new Date(), '{y}-{m}-{d}');
        this.checklist[0].list = this.dicts.install_type.obj;
        this.checklist[1].list = this.dicts.scene_type.obj;
        // 数据初始化-默认全选
        if(this.checklist.length>0) {
            for(let i = 0; i<this.checklist.length; i++) {
                this.checkAllv(true, this.checklist[i]);
            }
        }        
        if(Object.keys(this.childData).length>0 && Object.keys(this.childData.thisReport).length>0) {
            this.editStatus = true;
            this.id = this.childData.thisReport.id;
            this.lineIds = this.childData.thisReport.lineIds;
            this.get_inspectionReport_edit(this.id);
        } else {
            this.getTreeData(this.treeData);
        }
    },
    computed: {
        ...mapGetters([
            'dicts',
        ])
    }, 
    methods: {            
        /** 导出按钮操作  */
        handleExport () {
            let self = this;
            self.thisInfo.lineIds = this.lineIds;
            self.thisInfo.createTime = this.parseTime(new Date(), '{y}-{m}-{d} {h}:{i}:{s}');
            self.thisInfo.id = 0;
          self.$confirm('是否确认导出当前数据?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return export_inspectionReport(self.thisInfo);
            }).then(response => {
              self.download(response.msg);
            })
        },    
        reDomain(url,name) {
            return url + name;
        },
        alarmClick(pictureInfo,yuntainInfo) {
            // 手动告警
            if(pictureInfo.isAlarm == 1) {return;}
            this.visible3=true;
            this.thisalarm=pictureInfo;
            this.thisyuntai=yuntainInfo;
        },
        getTreeData (data) {
            // 树返回的数据
            if(data.node[0].node.type == "line") {this.lineIds = data.node[0].node.id;} 
            else if(data.node[0].node.type == "tower" || data.node[0].node.type == "yuntai") {this.$message('请选择线路');return;}

            this.getPictureList();
        },
        changeDate(data) {
            this.getPictureList();
        },
        resetChart(data) {
            // 重置数据 - 图表用的
            let online = data && data.online?data.online:0, total= data && data.total != 0?data.total:0,obj = {expectedData:online,actualData:total};
            return obj;
        },
        checkAllv(val,item) {
            // 全选
            let cc = []
            for(let key in item.list) {
                cc.push(item.list[key].dictValue)
            }
            item.checkedEquipments = val ? cc : [];
            item.isIndeterminate = false;
            if(item.key == 'installType') {this.installType = undefined} else if (item.key == 'sceneType') {this.sceneType = undefined}
        },
        handleCheckAllChange(val,item) {
            this.checkAllv(val,item);
            
            if(typeof(this.id) == 'undefined') {
                this.getPictureList();
            } else {
                this.get_inspectionReport_edit(this.id);
            }
        },
        handleCheckedCitiesChange(val,item) {
            // 单个checkbox
            let checkedCount = val.length;
            item.checkAll = checkedCount == Object.keys(item.list).length;
            item.isIndeterminate = checkedCount > 0 && checkedCount < item.list.length;
            if(item.key == 'installType') {
                if(item.checkedEquipments.length>0) {
                    if(item.checkedEquipments.length == Object.keys(item.list).length) {
                        this.installType = undefined;
                    } else {
                        this.installType = item.checkedEquipments.join(",");
                    }
                } else {
                    this.installType = undefined;
                }
            } else if (item.key == 'sceneType') {
                if(item.checkedEquipments.length>0) {
                    if(item.checkedEquipments.length == Object.keys(item.list).length) {
                        this.sceneType = undefined;
                    } else {
                        this.sceneType = item.checkedEquipments.join(",");
                    }
                } else {
                    this.sceneType = undefined;
                }
            }
            
            if(typeof(this.id) == 'undefined') {
                this.getPictureList();
            } else {
                this.get_inspectionReport_edit(this.id);
            }
        },
        getList (data) {
            // 分页数据
            this.get_report_pictureList();
        },
        dblclick(data, item) {
            if(item && Object.keys(item).length>0) {
                item['yuntaiId'] = data.yuntai.id;
                this.thisPicInfo = item;
                this.show2 = true;
            }
        },
        reset () {
            this.editStatus=false;
            this.getPictureList();
        },
        // 编辑详情
        get_inspectionReport_edit(id) {
            get_inspectionReport_id(id).then(response => {
                if(response.code == 200 && response.data) {            
                    this.inspectionPlan = response.data.inspectionPlan; 
                    this.inspectionResult = response.data.inspectionResult; 
                    this.inspectionTime = response.data.inspectionTime;    
                    this.inspector = response.data.inspector;
                    if(response.data.total == null){
                        this.total = 0;
                    }else{
                        this.total = response.data.total;  
                    }
                    if(response.data.online == null){
                        this.online = 0;
                    }else{
                        this.online = response.data.online;  
                    }
                    if(response.data.onlineRate == null){
                        this.onlineRate = 0;
                    }else{
                        this.onlineRate = response.data.onlineRate;  
                    }
                    if(response.data.offline == null){
                        this.offline = 0;
                    }else{
                        this.offline = response.data.offline;  
                    }
                    if(response.data.offlineRate == null){
                        this.offlineRate = 0;
                    }else{
                        this.offlineRate = response.data.offlineRate;  
                    }                                          
                    // this.yuntaiStatistic = response.yuntaiStatistic;     
                    // this.yuntaiHiddenDanger = response.yuntaiHiddenDanger;
                    // this.yuntaiStatistic = response.yuntaiStatistic;  
                    if(response.data.installType && response.data.installType.length>0) {
                        this.installType = response.data.installType;
                        this.checklist[0].checkedEquipments = response.data.installType.split(",");
                    }
                    if(response.data.sceneType && response.data.sceneType.length>0) {
                        this.sceneType =  response.data.sceneType;                        
                        this.checklist[1].checkedEquipments = response.data.sceneType.split(",");
                    }
                    this.get_report_pictureList();
                } 
            });
        },
        /** 查询列表 */
        getPictureList() {
            let obj= {}, lineIds = this.lineIds,installType = this.installType, sceneType = this.sceneType, inspectionTime = this.inspectionTime;
            obj = {lineIds, installType, sceneType,  inspectionTime}
            get_inspectionReport(obj).then(response => {
                if(response.code == 200) {                     
                    this.inspectionPlan = response.inspectionReport.inspectionPlan; 
                    this.inspectionResult = response.inspectionReport.inspectionResult; 
                    // if(!this.editStatus) {
                    //     this.inspectionTime = response.inspectionReport.inspectionTime;    
                    // }
                    this.inspector = response.inspectionReport.inspector;      
                    this.yuntaiHiddenDanger = response.yuntaiHiddenDanger;
                    this.yuntaiStatistic = response.yuntaiStatistic;
                    if(this.yuntaiStatistic.length > 0){     
                        this.total = this.yuntaiStatistic[0].total;
                        this.online = this.yuntaiStatistic[0].online;
                        if(this.yuntaiStatistic[0].onlineRate == null){
                            this.onlineRate = 0;
                        }else{
                            this.onlineRate = this.yuntaiStatistic[0].onlineRate;  
                        }  
                        this.offline = this.yuntaiStatistic[0].offline;  
                        if(this.yuntaiStatistic[0].offlineRate == null){
                            this.offlineRate = 0;
                        }else{
                            this.offlineRate = this.yuntaiStatistic[0].offlineRate;  
                        }    
                    }
                    this.thisInfo = response.inspectionReport; 
                    this.get_report_pictureList(); 
                } 
            });
        },
        // 新增保存巡检报告的操作
        add_inspectionReport() {
            let obj= {},  lineIds = this.lineIds,inspector = this.inspector, inspectionPlan = this.inspectionPlan, installType = this.installType, sceneType = this.sceneType, inspectionResult = this.inspectionResult, inspectionTime = this.inspectionTime;
            obj = {spare1:this.online,spare0:this.total,inspector, inspectionPlan, lineIds, installType, sceneType, inspectionResult, inspectionTime}
            add_inspectionReport(obj).then(response => {
                if(response.code == 200) {
                    this.thisInfo.inspector = inspector;
                    this.thisInfo.inspectionPlan = inspectionPlan;
                    this.thisInfo.installType = installType;
                    this.thisInfo.sceneType = sceneType;
                    this.thisInfo.inspectionResult = inspectionResult;
                    this.thisInfo.inspectionTime = inspectionTime;
                    this.$message.success(response.msg);
                    this.editStatus = false;
                }
            });
        },
        // 获取图片列表
        get_report_pictureList() {
            this.picss = [];
            let obj= {}, lineId = this.lineIds, installType = this.installType, sceneType = this.sceneType, inspectionTime = this.inspectionTime, pageSize = this.pageSize, pageNum = this.pageNum;
            obj = {lineId, inspectionTime,installType, sceneType,pageSize, pageNum,imgSize:2}
             get_inspectionReport_listByDay(obj).then(response => {
                if(response.code == 200) {
                  this.picss = response.rows;
                  this.total = response.total;
                }
            });
        },
        resetObje(objname, val) {
            if(val.length<1) return;
            let array = [], name="";
            array = val.split(',');
            for(let i = 0; i < array.length; i++) {
                for(let key in this.dicts[objname].obj) {
                    if(i == key) {
                        name += this.dicts[objname].obj[key].dictLabel + " ";
                    }
                }
            }
            return name;
        }
    }
}
</script>
<style lang="scss" scoped>
::v-deep{
    .el-scrollbar__wrap{overflow-x:hidden;}
    .el-scrollbar__bar.is-horizontal{display: none;}
    .el-checkbox__input.is-checked + .el-checkbox__label{color:#606266;}
}
.app-li{
    h4{margin:0;color:$base-color-black;}
    .des{display:flex;align-items:center;
        .tit{line-height: 28px;margin-right:80px;display:flex;align-items:center;color:$base-font-color;.rm{line-height: 28px;}}
        .btn.ml{margin-left:auto;}
    }
    .conclusion{padding-top:10px;
        p{margin:10px 0 0;line-height:28px;color:$base-font-color;font{color:$base-color-default;margin:0 5px;font-size:14px;}}
        .textarea{margin:10px 0 0;}
        ::v-deep{.el-input__inner{font-size:14px;}}
    }
    .videodata{display:flex;align-items:center;padding-top:20px;height:110px;
        .piesimplechart{}
        .rightcon{flex:1;padding-left:20px;display:flex; align-items: center;
            .item{width:20%;
                span{font-size:14px;color:$base-font-color;display: block;text-align:center;padding:8px 0;&.blue{color:$base-color-default;}&.red{color:$base-color-red2;}}
            }
        }
    }
    .onlinedevice{padding-top:20px;height:110px;position: relative;
        .swiper{width:90%;margin:0 auto;
            .swiper-slide{
                .piedoughnutchart{margin: 0 auto;}
                span{display:block;text-align:center;padding-top:5px;color:$base-font-color;}
            }
        }
        .arrowleft,.arrowright{position: absolute;top:50px;font-size:20px;outline: none; cursor: pointer;color:$base-font-color;}
        .arrowright{right:0;}
    }
    .typelist{position: relative;padding-top:10px;
        .item+.item{margin-top:10px;}
        .item{display:flex;
            h4{width:80px;padding-top:4px;}
            .checkboxs{flex:1;overflow:hidden;position: relative;
                .checkall{position:absolute;}
                .el-checkbox-group{
                    .el-checkbox:first-child{margin-left:60px;}
                }
                ::v-deep{.el-checkbox{padding:4px 0;margin-right:15px;.el-checkbox__label{font-size:13px;}}}
            }
        }
    }
    .reportContainer{padding-top:10px;
        .imgs{display:flex;justify-content: space-between;
            .imgitem{width:49%;
                .title{display:block;text-align:center;margin:10px 0;}
                .img{width:100%;height:170px;position: relative;border-radius:3px;overflow:hidden;cursor: pointer;
                    &:hover{
                        .mask{display:none;}
                    }
                    .title{display: block;text-align:center;}
                    img{display:block;object-fit: cover;width:100%;height:100%;}
                    .mask{background:$base-color-gray;position: absolute;bottom:0;left:0;right:0;height:35px;padding:2px 0;
                        span{display: block;text-align:center; transform: scale(0.8);color:#fff;}
                    }
                }
            }
        }
        .table{background:#fafafa;border:1px solid $base-border-color2;border-radius:3px;
            .el-col{padding:8px 10px;display:flex;align-items: center;.el-button{margin-left:auto;}border-bottom:1px solid $base-border-color2;
                &:last-child{border-bottom:0;}
            }
            .cl{display: block;overflow: hidden; white-space: nowrap;text-overflow: ellipsis;-webkit-line-clamp: 1; }
        }
    }
}
.app-li+.app-li{margin-top:$base-padding10;}
.el-col-12{
    width: 100%;
}
</style>