<template>
    <!-- 图片详情右侧功能 -->
    <div class="rightbox">
        <el-scrollbar horizontal="false">
            <div class="box" >
                <span class="title">日历快速查询</span>
                <div class="list histortime" v-if="type == 'history'">
                    <el-form :inline="true" :model="queryParams" ref="formInline" size="mini">
                            <el-form-item label="开始时间" prop="presetBit">
                                <el-date-picker size="mini" format="yyyy-MM-dd HH:mm:ss" clear-icon="" value-format="yyyy-MM-dd HH:mm:ss" v-model="begDate" type="datetime" placeholder="选择日期"></el-date-picker>
                            </el-form-item>
                            <el-form-item label="结束时间" prop="day">                        
                                <el-date-picker size="mini" format="yyyy-MM-dd HH:mm:ss" clear-icon="" value-format="yyyy-MM-dd HH:mm:ss" v-model="endDate" type="datetime" placeholder="选择日期"></el-date-picker>
                            </el-form-item> 
                            <el-form-item>
                                <el-button type="primary" icon="el-icon-search" size="mini" @click="pickerChange">搜索</el-button>
                            </el-form-item>
                    </el-form>
                     <!-- <el-date-picker format="yyyy-MM-dd HH:mm:ss" @change="pickerChange" clear-icon="" value-format="yyyy-MM-dd HH:mm:ss" size="mini" v-model="queryParams.day" type="datetimerange" :default-time="['00:00:00']" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker> -->
                </div>
                <div class="list" v-else>
                   <el-calendar v-model="queryParams.day" :first-day-of-week=7 class="customcalendar">
                       <template slot="dateCell" slot-scope="{date, data}">
                            <div slot="reference" class="div-Calendar" @click="calendarOnClick(data)">
                                <p :class="data.isSelected ? 'is-selected' : ''">{{ data.day.split('-')[2] }}</p>
                            </div>
                        </template>
                   </el-calendar>
                </div>
            </div>
            <div class="box" >
                <span class="title">图片类型</span>
                <div class="list imglists">
                    <div :class="['item',{act:imgTypes.length<1}]" @click="all"><span>全部</span></div>
                    <template v-if="Object.keys(dicts).length>0 && dicts.img_type">
                        <div :class="['item',{act:e.default}]" v-for="(e,i) in dicts.img_type.obj" :key="i" @click="imgclick(e)"><span>{{e.dictLabel}}</span></div>
                    </template>
                </div>
            </div>
            <div class="box" >
                <span class="title">预置位</span>
                <div class="list">
                    <el-input v-model="queryParams.presetBit" size="mini" style="margin-top:5px;" @blur="bitBlur"></el-input>
                </div>
            </div>
            <div class="box" >
                <span class="title">功能导航</span>
                <div class="list menu">
                    <el-button size="mini" @click="$router.push({path: '/videoInspection/control/' + queryParams.yuntaiId})">实时视频</el-button>
                    <el-button size="mini" @click="$router.push({path: '/yuntaimanage/realtimeConfig/' + queryParams.yuntaiId})">设备管理</el-button>
                    <!-- <el-button size="mini">监测数据</el-button> -->
                    <el-button size="mini" @click="handPic">手动抓拍</el-button>
                    <el-button size="mini" @click="handVideo">抓拍小视频</el-button>
                    <!-- <el-button size="mini">录像回放</el-button> -->
                    <el-button size="mini" @click="$router.push({path: '/alarmManagedetail/' + queryParams.yuntaiId})">告警图片</el-button>
                </div>
            </div>
        </el-scrollbar>
        <div class="page">
            <custom-pagination :firstend="false" :autoplay="false" @pagination="pagination" :pageSizeShow="false" :total="total2" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"></custom-pagination>
        </div>
    </div>
</template>
<script>
import { mapGetters } from 'vuex'
import * as global  from "@/utils/global";
import { add_picture,edit_picture,get_picture_detail,del_picture,export_picture,get_pictureList,get_pictureListByDay,get_pictureListByDetails,get_pictureHistory } from "@/api/senxu/yuntai_picture/index";
export default {
    name: "pictureFilter",
    props: ['cc','total2', 'yuntaiinfo', 'type'],
    data () {
        return {
            calendar: '', // 日历
            presetBit: 1,
            imgTypes: [],
            total: '',
            pics:[],
            max: '', // 预置位最大值
            queryParams:{},
            begDate: '', // 历史抓拍详情开始时间
            endDate: '',// 历史抓拍详情结束时间
            day: undefined,
        }
    },
    created () {
        this.max = global.PRESET_BIT_TOTAL;
    },
    computed: {
        ...mapGetters([
            'dicts',
        ])
    },
    watch: {
        cc: {
            handler(newdata) {
                // 当预置位为0时，撤销预置位
                newdata.presetBit = newdata.presetBit == 0?undefined:newdata.presetBit;
                if(this.type == 'history') {
                    let dayArray = newdata.day.split(',');
                    this.begDate = dayArray[0];
                    this.endDate = dayArray[1];
                }
                this.queryParams = Object.assign({},newdata);
                this.imgTypes = [];
                for(let i in this.dicts.img_type.obj) {
                    this.dicts.img_type.obj[i].default = false;
                }
            },
            deep:true
        }
    },
    mounted() {  
        document.querySelectorAll('.customcalendar .el-button-group button').forEach((item)=>{item.innerHTML = ''}); // 隐藏日历按钮 
    },
    methods: {
        imgclick(item) {
            // 图片类型
            item.default = !item.default;
            this.queryParams.imgTypes= undefined;
            this.imgTypes = [];
            for(let i in this.dicts.img_type.obj) {
                if(this.dicts.img_type.obj[i].default) {
                    this.imgTypes.push(parseInt(this.dicts.img_type.obj[i].dictValue));
                }
            }
            this.queryParams.imgTypes = this.imgTypes.length>0?this.imgTypes.join(','):undefined;
            this.queryParams.day = this.day;
            this.getPics();
        },
        all() {
            this.queryParams.imgTypes = undefined;
            this.imgTypes = [];
            for(let i in this.dicts.img_type.obj) {
                this.dicts.img_type.obj[i].default = false;
            }
            this.getPics();
        },
        pagination (data) {
            // 分页返回数据
            this.$parent.noautoplay(); // 停止播放
            this.getPics();
        },
        hideclick() {
            this.hide=!this.hide;
            this.$parent.update();
        },
        bitBlur() {
            if(parseInt(this.queryParams.presetBit) < 1) {
                this.msgSuccess("预置位的值最小值为1"); 
                this.queryParams.presetBit = undefined; 
                return;
            } else if(parseInt(this.queryParams.presetBit) > parseInt(global.PRESET_BIT_TOTAL)) {
                this.msgSuccess("预置位的值不能大于"+global.PRESET_BIT_TOTAL); 
                this.queryParams.presetBit = undefined;
                return;
            }
            this.getPics();
        },
        getPics () {
            // 请求接口获取数据
            console.log(">>>>>>>this.queryParams.day",this.queryParams.day);
            get_pictureListByDetails(this.queryParams).then(response => {
                if(response.code == 200 && response.rows && response.rows.length>0) {
                    // response.presetBit = this.queryParams.presetBit;
                    // if(this.type=='history') {
                    //     response.day = this.queryParams.day;
                    // } else {
                    //     response.day = this.parseTime(this.queryParams.day, '{y}-{m}-{d}');
                    // }
                    this.pics = response.rows;
                    this.total = response.total;
                    this.$emit("getfilter",response);
                }
            });
        },
        calendarOnClick(date) {
            console.log(">>>>>>>date.day",date.day);
            this.day = date.day;
            this.queryParams.day = date.day;
            this.queryParams.presetBit = undefined;
            this.queryParams.pageNum = 1;
            this.getPics();
        },
        handPic() {       
            if(this.yuntaiinfo.state != 1) {
                this.msgSuccess("该设备离线状态");
                return;
            }
            // 手动抓拍
            this.$parent.$parent.$parent.visible = true;
            this.$parent.$parent.$parent.thisyuntai = this.yuntaiinfo;
        },
        handVideo() {
            if(this.yuntaiinfo.state != 1) {
                this.msgSuccess("该设备离线状态");
                return;
            }
            // 手动抓拍
            this.$parent.$parent.$parent.visible2 = true;
            this.$parent.$parent.$parent.thisyuntai = this.yuntaiinfo;
        },
        pickerChange() {
            // 历史抓拍时间段选择
            if(new Date(this.begDate) > new Date(this.endDate)) {
                this.$message('日期开始时间大于结束时间');
                return;
            }
            this.queryParams.pageNum = 1;
            this.queryParams.day = this.begDate + ',' + this.endDate;
            this.getPics();
        }
    }
}
</script>
<style lang="scss" scoped>
.rightbox{width:100%;height:100%;padding-left:5px;overflow:hidden;display:flex; flex-direction: column;
    .box+.box{margin-top:15px;}
    .box{overflow:hidden;
        .title{height:35px;line-height: 35px;color:#fff;background:$base-color-blue3;display: block;padding-left:10px;}
        .imglists{display: flex;flex-wrap: wrap;padding:5px 0;
            .item{width:33.333%;padding:2px 2px;cursor: pointer;
                &.act span{border-color:$base-color-blue3;background:$base-color-blue3;color:#fff;}
                span{padding:5px 0;font-size:12px;display: block;text-align:center;border-radius: 4px;border:1px solid $base-border-color;}
            }
        }
        .menu {display: flex;flex-wrap: wrap;padding-top:5px;
            .el-button{width:31.333%;margin:2px 1%;padding:5px 0;}
        }
    }
    ::v-deep{
        .histortime{.el-input__inner{width:200px;}}
    }
    .el-scrollbar{flex:1;}
    .page{display: flex;justify-content: center;}
    .customcalendar{
        .el-calendar__header{display:block;position: relative;padding:8px 10px;
            .el-calendar__title{text-align: center;}
            .el-calendar__button-group{position: absolute;top:0;left:5px;right:5px;bottom:0;
                .el-button-group{position: relative;width:100%;height:100%;
                    button:nth-child(2){display: none;}
                    .el-button{border:0;position: absolute;}
                    .el-button:first-child{left:0;top:8px;background: url('~$img/left.png') center center;background-size:cover;width:16px;height:16px;padding:0;}
                    .el-button:last-child{right:0;top:8px;background: url('~$img/right.png') center center;background-size:cover;width:16px;height:16px;padding:0;}
                }
            }
        }
        .el-calendar__body{padding:0 5px;     
            .el-calendar-table td .el-calendar-day:hover{color:#000;}
            .el-calendar-table td.is-selected .el-calendar-day,.el-calendar-table td.is-selected:hover .el-calendar-day{background:$base-color-default;color:#fff;border-radius:3px;}
            .el-calendar-table thead th{padding:7px 0;}
            .el-calendar-table tr td{border:0;}
            .el-calendar-day p {margin:3px 0;padding:0;}
            .el-calendar-table .el-calendar-day{height:25px;padding:0;text-align:center;line-height:25px;}
        }
    }
}
</style>
