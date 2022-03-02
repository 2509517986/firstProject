<template>
    <el-card class="app-container">        
        <tree-container @ceshi="ccc">
            <div class="todayInspection">
                <div class="top">
                    <div class="title">
                        <el-breadcrumb separator-class="el-icon-arrow-right" v-if="Object.keys(getTreedata).length>0 && getTreedata.parent.length>0">
                            <el-breadcrumb-item v-for="(e,i) in getTreedata.parent" :key="i">{{e.name}}</el-breadcrumb-item>
                        </el-breadcrumb>
                    </div>
                    <div class="tools">
                       <el-form :inline="true" :model="queryParams" ref="formInline"  size="mini">
                            <el-form-item label="预置位" prop="presetBit">
                                <el-select v-model="queryParams.presetBit" clearable placeholder="请选择" size="mini">
                                    <el-option v-for="(item,index) in bits" :key="index" :label="item" :value="item"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="图片类型" prop="imgTypes">
                                <el-select v-model="queryParams.imgTypes" clearable placeholder="请选择" size="mini">
                                    <el-option v-for="(item,index) in dicts.img_type.obj" :key="index" :label="item.dictLabel" :value="item.dictValue"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="浏览状态" prop="isBrowse">
                                <el-select v-model="queryParams.isBrowse" clearable placeholder="请选择" size="mini">
                                    <el-option v-for="item in options3" :key="item.id" :label="item.label" :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="" prop="date"> 
                                <el-date-picker format="yyyy-MM-dd HH:mm:ss" clear-icon="" value-format="yyyy-MM-dd HH:mm:ss" size="mini" v-model="date" type="datetimerange" :default-time="['00:00:00']" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>   
                            </el-form-item> 
                            <el-form-item>
                                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                                <el-button icon="el-icon-refresh" size="mini" @click="resetForm('formInline')">重置</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                    <div class="layout">
                        <svg-icon @click="clickLayout" :icon-class="layout?'list':'layout'" style="height: 18px;width: 18px;" />
                    </div>
                </div>
                <div class="lists" v-if="layout && pics.length>0">
                    <div class="listcon">
                        <el-card shadow="never" class="item" v-for="(e,i) in pics" :key="i" :body-style="{paddingLeft: '0px',paddingRight: '0px',paddingTop:'20px',paddingBottom:'0'}">
                            <div slot="header" class="elcard-top">
                                <svg-icon :icon-class="e.yuntai.monitorType==1?'bolt':'yuntaiIcon'" :class="['yuntaiIcon', {online:e.yuntai.state != -1}]"></svg-icon>
                                <span class="name">{{e.yuntai.name}}</span>
                                <el-tag size="small">{{Object.keys(dicts).length>0 && dicts.oem && dicts.oem.obj && dicts.oem.obj[e.yuntai.factory].dictLabel}}</el-tag>
                                <div class="btns">
                                    <el-button type="primary" size="mini"   @click="visible2=true;thisyuntai=e.yuntai">抓拍小视频</el-button>
                                    <el-button type="danger" size="mini"  @click="visible=true;thisyuntai=e.yuntai">手动抓拍</el-button>
                                    <el-dropdown  size="mini">
                                        <el-button size="mini" type="primary">更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>
                                        <el-dropdown-menu slot="dropdown">
                                            <el-dropdown-item @click.native="viewmore(e.yuntai)">查看更多</el-dropdown-item>
                                        </el-dropdown-menu>
                                    </el-dropdown>
                                </div>
                            </div>
                            <el-row :gutter="12" v-if="e.pictures&&e.pictures.length>0">
                                <el-col :span="6" v-for="(ee,ii) in e.pictures" :key="ii" shadow="hover" @dblclick.native="dblclick(e.yuntai,ee)">
                                    <el-card :class="['imgcard',{act:e.act==ii}]" :body-style="{padding: '0px',position: 'relative'}">
                                        <el-tooltip class="item" effect="dark" content="双击查看详情" placement="top">
                                            <el-image :src="reDomain(ee.imageDir, ee.imageName)" alt="" fit="cover" class="img" lazy></el-image> 
                                        </el-tooltip>                                       
                                        <div class="mask">
                                            <span class="absolute" v-if="ee.isBrowse">已浏览</span>
                                            <span>{{Object.keys(dicts).length>0 && ee.captureType&& dicts.capture_type && dicts.capture_type.obj && dicts.capture_type.obj[ee.captureType].dictLabel}}</span>
                                            <span>{{ee.captureTime}}</span>
                                        </div>
                                        <div class="tool">
                                            <el-button size="mini" :class="{hui:ee.isAlarm == 1}"  type="danger"  @click.stop="alarmClick(ee,e.yuntai)">{{ee.isAlarm == 0?'手动告警':'已告警'}}</el-button>
                                            <i class="el-icon-download" @click="downloadImg(ee)"></i>
                                        </div>
                                    </el-card>
                                </el-col>
                            </el-row>
                            <el-row v-else :gutter="12"><el-col :span="6"><el-image class="error_img"><div slot="error" class="image-slot"><span>暂无图片</span></div></el-image></el-col></el-row>
                        </el-card>
                    </div>
                    <custom-pagination  v-show="total>0" :pageSizeShow="true" :pageSizes="[4,8,12,16]" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"  @pagination="getList"></custom-pagination>
                </div>
                <div class="lists" v-if="!layout && pics.length>0">
                    <div class="fourLayout">
                        <div class="item" v-for="(e,i) in pics" :key="i">
                            <div class="itemcon">
                                <el-tooltip class="item" effect="dark" content="双击查看详情" placement="top">
                                    <el-image v-if="e.pictures && e.pictures.length>0"  @dblclick.native="dblclick(e.yuntai,e.pictures[0])" :src="reDomain(e.pictures[0].imageDir, e.pictures[0].imageName)" alt="" fit="cover" class="img" lazy></el-image>
                                </el-tooltip>
                                <el-image class="error_img" v-if="e.pictures && e.pictures.length<1"><div slot="error" class="image-slot"><span>暂无图片</span></div></el-image>
                                <div class="mask">
                                    <span class="absolute" v-if="e.pictures&&e.pictures.length>0&&e.pictures[0].isBrowse">已浏览</span>
                                    <span>{{e.yuntai.name}}</span>
                                    <span>{{e.pictures && e.pictures.length>0?e.pictures[0].captureTime:'暂无时间'}}</span>    
                                </div>
                                <div class="tools" v-if="e.pictures && e.pictures.length>0">
                                    <span>{{Object.keys(dicts).length>0 && dicts.oem && dicts.oem.obj && dicts.oem.obj[e.yuntai.factory] && dicts.oem.obj[e.yuntai.factory].dictLabel}}</span>
                                    <span>{{Object.keys(dicts).length>0 && dicts.monitor_type && dicts.monitor_type.obj && dicts.monitor_type.obj[e.yuntai.monitorType] && dicts.monitor_type.obj[e.yuntai.monitorType].dictLabel}}</span>
                                    <svg-icon icon-class="photography" @click="visible=true;thisyuntai=e.yuntai"/>
                                    <svg-icon icon-class="video"  @click="visible2=true;thisyuntai=e.yuntai"/>
                                    
                                </div>
                                <div class="tool" v-if="e.pictures && e.pictures.length>0">
                                    <el-button :class="{hui:e.pictures[0].isAlarm == 1}" size="mini" type="danger"  @click="alarmClick(e.pictures[0],e.yuntai)">{{e.pictures[0].isAlarm == 0?'手动告警':'已告警'}}</el-button>
                                    <i class="el-icon-download" @click="downloadImg(e.pictures[0])"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <custom-pagination  v-show="total>0" :total="total" :pageSizes="[4,8,12,16]" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"  @pagination="getList"></custom-pagination>
                </div>               
                <el-empty v-if="pics.length<1" description="暂无数据"></el-empty>    
            </div>
        </tree-container>
        <!-- 显示详情 -->
        <div :class="['showdetailbox',{left: show == 2}]" v-if="show == 1 || show == 2" ><picture-info @close="close" :imgcheck="imgcheckdata" :type="'history'"></picture-info></div>
        <!-- 手动抓拍图片 -->
        <manual-capture-img v-if="visible" :yuntaiinfo="thisyuntai"></manual-capture-img>
        <!-- 手动抓拍小视频 -->
        <manual-capture-video v-if="visible2" :yuntaiinfo="thisyuntai"></manual-capture-video>
        <!-- 手动告警 -->
        <manual-alarm v-if="visible3" :alarminfo="thisalarm" :yuntaiinfo="thisyuntai"></manual-alarm>
    </el-card>
</template>
<script>
import { mapGetters } from 'vuex'
import { add_picture,edit_picture,get_picture_detail,del_picture,export_picture,get_pictureList,manualCap, get_pictureListByDay,yuntaiPreview,get_pictureListByDetails,get_pictureHistory } from "@/api/senxu/yuntai_picture/index";
import * as global  from "@/utils/global";
export default {
    data () {
        return {
            show:0, //0-列表 1-图片详情 2-点击设备
            visible: false,
            visible2: false,
            visible3: false,
            timmer: "",
            options2: [{label: '1',id:1},{label:'2',id:2}],
            options3: [{label: '已浏览',id:1},{label:'未浏览',id:0}],
            thisalarm: {}, // 当前图片信息
            thisyuntai: {}, // 当前云台信息
            imgTypes: [], // 图片类型
            total: 150,
            date: "",
            queryParams: {pageNum:1,pageSize:4,lineId:undefined,isBrowse:undefined,imgTypes:undefined,endDate:'',begDate:'',towerId:undefined,yuntaiIds:undefined,presetBit:undefined,imgSize:4},
            layout: true,
            pics: [],
            getTreedata:{}, // 树结构数据
            imgcheckdata: {presetBit:1,day:undefined,yuntaiId:undefined,single:false}, // 传递到图片详情
            expireTimeOption: {
                // 当天后不可以选择
                disabledDate(date) {
                    return date.getTime() > Date.now();
                }
            }
        }
    },
    created () {        
        // 默认时间
        let startdate = this.parseTime(new Date(),'{y}-{m}-{d}') + ' 00:00:00';
        this.date = [startdate,this.parseTime(new Date())];
        this.queryParams.begDate = this.date[0];
        this.queryParams.endDate = this.date[1];
    },
    mounted() {
    },
    computed: {
        ...mapGetters([
            'dicts',
        ]),
        bits() {
            return global.PRESET_BIT_TOTAL;
        }
    },    
    methods: {
        clickLayout() {
            // 风格转换
            this.layout = !this.layout;
            // this.queryParams.imgSize = this.layout?4:1;
        },
        reset() {
            // this.queryParams.day = this.parseTime(new Date(), '{y}-{m}-{d}');
            this.queryParams.presetBit = undefined;           
            this.queryParams.isBrowse = undefined;
            this.queryParams.imgTypes = undefined;
            this.queryParams.pageNum = 1;
            // 
            this.show = 0;            
            let startdate = this.parseTime(new Date(),'{y}-{m}-{d}') + ' 00:00:00';
            this.date = [startdate,this.parseTime(new Date())];
            this.queryParams.begDate = this.date[0];
            this.queryParams.endDate = this.date[1];
            this.imgcheckdata.presetBit = undefined;
            // 历史传递时间
            this.imgcheckdata.day = this.date.join(',');
            this.imgcheckdata.single = false; 
        },
        reDomain(url,name) {
            return url + name;
        },
        ccc(data) {
            // this.reset();
            // 从树结构带来数据
            this.getTreedata = data;
            this.queryParams.yuntaiIds=undefined;
            this.queryParams.yuntaiId=undefined;
            this.queryParams.towerId=undefined;
            this.show = 0;
            if(data.node[0].node.type == "line") {this.queryParams.lineId = data.node[0].node.id;} 
            else if(data.node[0].node.type == "tower") {this.queryParams.towerId = data.node[0].node.id;this.queryParams.lineId = data.node[0].node.parentId;}
            else if(data.node[0].node.type == "yuntai") {this.queryParams.yuntaiIds = data.node[0].node.id;}
            this.getPictureList();
        },
        /** 查询列表 */
        getPictureList() {
            get_pictureHistory(this.queryParams).then(response => {
                this.pics = response.rows;
                this.total = response.total;
                let array = [];
                // 已预览
                if(this.pics.length>0) {
                    for(let i = 0;i < this.pics.length; i++) {
                        if(this.pics[i].pictures && this.pics[i].pictures.length>0) {
                            for(let j = 0; j < this.pics[i].pictures.length;j++) {
                                if(this.pics[i].pictures[j].isBrowse == null || this.pics[i].pictures[j].isBrowse == 0) {
                                    array.push(this.pics[i].pictures[j].id);
                                }
                            }
                        }
                    }
                    this.yuntaiPreview(array);
                }
            });
        },
        /** 已预览 */
        yuntaiPreview(arry) { 
            if(arry.length<1){return;}              
            let formData = new FormData();            
            formData.append("ids", arry);      
            yuntaiPreview(formData).then(response => {
                
            });
        },
        msg(txt) {
            this.$alert(txt, {
                confirmButtonText: '确定',
                callback: action => {}
            });
        },
        close(data) {
            this.show = data.show;
        },
        downloadImg(data) {
            // 下载当前图片
            let imgsrc = this.reDomain(data.imageDir, data.imageName);
            global.downloadImg(imgsrc,data.imageName);
        },
        dblclick (parent,item) {
            // 显示详情 -- 
            this.show = 1;
            this.imgcheckdata.yuntaiId = parent.id;
            this.imgcheckdata.presetBit = item.presetBit;
            this.imgcheckdata.day = this.date.join(',');
            this.imgcheckdata.single = true;  
            this.imgcheckdata.state = parent.state;
            this.thisyuntai = parent;
        },
        handleQuery() {
            // 搜索
            this.pics=[];
            this.queryParams.pageNum = 1;           
            this.queryParams.begDate = this.date[0]?this.date[0]:"";
            this.queryParams.endDate = this.date[1]?this.date[1]:"";
            this.imgcheckdata.day = this.date.join(',');
            this.queryParams.presetBit = this.queryParams.presetBit&&this.queryParams.presetBit != ""?this.queryParams.presetBit:undefined;
            this.getPictureList();
        },
        resetForm(formName) {
            // this.queryParams.day = this.parseTime(new Date(), '{y}-{m}-{d}');
            // this.$refs[formName].resetFields();            
            this.reset();
            this.getPictureList();
        },
        getList (data) {
            // 分页组件返回的 api请求数据   
            this.queryParams.pageSize = data.limit; 
            this.getPictureList();
        },
        viewmore(item) {
            // 查看更多
            this.show = 1;
            this.imgcheckdata.yuntaiId = item.id;
            this.imgcheckdata.state = item.state;
            this.imgcheckdata.presetBit = this.queryParams.presetBit;
            this.imgcheckdata.day = this.date.join(',');
            this.imgcheckdata.single = true;
        },
        alarmClick(pictureInfo,yuntainInfo) {
            // 手动告警
            if(pictureInfo.isAlarm == 1) {return;}
            this.visible3=true;
            this.thisalarm=pictureInfo;
            this.thisyuntai=yuntainInfo;
        }
    },
    destroyed() {
        clearTimeout(this.timmer);
    }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card__body{height:100%;position: relative;}}
}
.showdetailbox{position: absolute;top:15px;left:20px;right:20px;bottom:20px;z-index: 3;&.left{left:290px;}}
.todayInspection {display:flex; flex-direction: column;height:100%;overflow:hidden;
    .top{display:flex;align-items:center; flex-shrink: 0;
        .title{font-size:14px;}
        .tools{margin-left:auto;
            ::v-deep{
                .el-select{width:100px;}
                .el-date-editor{width:310px;}
                .el-form-item{margin-bottom:0;}
            }
        }
        .layout{margin-left:$base-padding;color:$base-menu-color;cursor: pointer;}
    }
    .lists{ flex:1;display:flex; flex-direction: column;overflow: hidden;
        .listcon{flex:1;overflow-y:auto;overflow-x:hidden;
            .item{border-radius: 0;border:0;padding:0;padding-top:$base-padding;&:first-child{padding-top:0;}
                ::v-deep{
                    .el-card__header{padding-left:0;padding-bottom:13px;}
                }
                .elcard-top{display:flex;align-items:center;
                    .name{font-size:13px;}
                    .yuntaiIcon{margin-right:3px;font-size:14px;color:$base-menu-color;&.online{color:$base-color-default;}}
                    .el-tag{margin:0 $base-padding10;}
                }
                .btns{
                    .el-dropdown{margin-left: $base-padding10;}
                }
                .imgcard{border:0;position: relative;cursor: pointer;
                    &:before{content: "";position: absolute;background:rgba(0,0,0,0.5);left:0;right:0;bottom:0;top:0;z-index:1;display:none;}
                    .img{width:100%;display:block;height:165px;}
                    .mask{position: absolute;background:rgba(0,0,0,0.6);bottom:0;left:0;right:0;height:35px;padding-top:2px;z-index:2;opacity:0.7;
                        span{display:block;text-align:center;color:#fff;font-size:$base-font-size-small;}
                        span.absolute{position: absolute;color:$base-color-green;left:7px;top:8px;}
                    }
                    .tool{position: absolute;display:none;bottom:8px;;right:10px;align-items:center;z-index:2;
                        .hui{background:$base-menu-color;border-color:$base-menu-color;}
                        i{color:#fff;font-size:20px;margin-left:10px;}
                    }
                    &.act{&:before{display:none;}opacity: 1;.mask{opacity: 1;}}
                    &:hover{&:before{display: none;}  opacity: 1;               
                        .mask{display:none;}
                        .tool{display:flex;}
                    }
                }
            }
            .error_img{height:165px;width:100%;font-size:14px;display:flex;border-radius: 4px; flex-direction: column; justify-content: center; align-items: center; background: #333;color: #909399;}
        }        
        .fourLayout{display:flex;flex-wrap:wrap;flex:1;overflow-y:auto;overflow-x:hidden;margin:$base-padding10 -5px 0;
            .item{position: relative;width:50%;padding:10px 5px 0;cursor: pointer;height:50%;
                .itemcon{width:100%;height:100%;position: relative;overflow:hidden;overflow: hidden;border-radius:4px;}
                .img{width:100%;height:100%;display:block;object-fit: cover;}
                .el-image{border-radius:4px;overflow:hidden;}
                .mask{position: absolute;background:rgba(0,0,0,0.6);bottom:0;left:0px;right:0px;height:35px;padding-top:2px;
                    span{display:block;text-align:center;color:#fff;font-size:$base-font-size-small;}
                    span.absolute{position: absolute;color:$base-color-green;left:10px;top:8px;}
                }
                .tools{position: absolute;background:rgba(0,0,0,0.6);left:0px;top:0px;right:0px;align-items:center;height:30px;display:flex;align-items:center; justify-content: flex-end;color:#fff;padding:0 10px;
                    span{margin:0 5px;}
                    svg{color:#fff;font-size:18px;margin-left:10px;&:hover{color:$base-color-red2;}}
                }
                .tool{position: absolute;display:none;bottom:6px;;right:20px;align-items:center;z-index:2;
                    .hui{background:$base-menu-color;border-color:$base-menu-color;}
                    i{color:#fff;font-size:20px;margin-left:10px;}
                }
                &:hover{
                    .mask{display: none;}
                    .tool{display: flex;}
                }
            }
            .error_img{width:100%;height:100%;display:block;font-size:14px;display:flex; flex-direction: column; justify-content: center; align-items: center; background: #333;color: #909399;}
        }
    }
}
</style>