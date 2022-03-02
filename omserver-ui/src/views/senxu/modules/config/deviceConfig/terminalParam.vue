<template>
    <!-- 设备配置-终端参数 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏终端参数" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>终端参数</span>
            <el-button size="mini" type="primary" class="querybtn" @click="query" :disabled="fromTreeData.node[0].yuntais.length<0">查询设备</el-button>
        </div>
        <div class="box-content"  v-loading="open && Object.keys(thisYuntaiInfo).length>0">
            <el-form ref="form" :model="form" label-width="80px" size="mini">
                <el-form-item :label="'硬件重启\n时间'" prop="heartbeatReboot">
                   <el-row :gutter="5">
                        <el-col :md="1" style="padding:0!important;">日期：</el-col>
                        <!-- <el-col :span="9">
                            <el-date-picker  v-model="form.heartbeatReboot" size="mini"  format="yyyy-MM-dd HH:mm" value-format="yyyy-MM-dd HH:mm" type="datetime" placeholder="选择日期时间"></el-date-picker>
                        </el-col> -->
                        <el-col :md="6" :xl="6">
                            <el-form-item prop="date1">
                                <el-select size="mini" v-model="form.heartbeatRebootDay" style="width:100%">
                                    <el-option v-for="item in options" :key="item.val" :label="item.label" :value="item.val"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :md="6" :xl="6">
                            <el-form-item prop="date2">
                                <el-col :span="21"><el-input-number style="width:100%" class="ipm" size="mini" v-model="form.heartbeatRebootHour" :min="0" :max="24"></el-input-number></el-col>
                                <el-col :span="3"><span>时</span></el-col>
                            </el-form-item>
                        </el-col>
                        <el-col :md="6" :xl="6">
                            <el-form-item prop="date3">
                                <el-col :span="21"><el-input-number style="width:100%" class="ipm" size="mini" v-model="form.heartbeatRebootMinute" :min="0" :max="60"></el-input-number></el-col>
                                <el-col :span="3"><span>分</span></el-col>
                            </el-form-item>
                        </el-col>
                   </el-row>
                </el-form-item>
                <el-form-item :label="'心跳间隔\n(min)'" prop="heartbeatInterval">
                    <el-slider size="mini" :show-tooltip="false" :min="0" show-input input-size="mini" v-model="form.heartbeatInterval"></el-slider>
                </el-form-item>
                <el-form-item :label="'采集间隔\n(min)'" prop="heartbeatCollect">
                    <el-slider size="mini" :show-tooltip="false" :min="0" show-input input-size="mini" v-model="form.heartbeatCollect"></el-slider>
                </el-form-item>
               <el-form-item :label="'休眠时长\n(min)'" prop="heartbeatDormancy">
                    <el-slider size="mini" :show-tooltip="false" :min="0" show-input input-size="mini" v-model="form.heartbeatDormancy"></el-slider>
                </el-form-item>
                <el-form-item :label="'在线时长\n(min)'" prop="heartbeatOnline">
                    <el-slider size="mini" :show-tooltip="false" :min="0" :max="60" show-input input-size="mini" v-model="form.heartbeatOnline"></el-slider>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="mini" @click="onSubmit" :disabled="Object.keys(thisYuntaiInfo).length<1 || open">设置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </el-card>
</template>
<script>
import * as global  from "@/utils/global";
import {getHeartbeat,saveHeartbeat } from "@/api/senxu/config/index";
export default {
    props: ['favIcon', 'treeData'],
    name: 'terminalParam',
    data () {
        return{
            open: true,
            act:false,
            fromTreeData: {},
            form: {
                heartbeatReboot: 0,
                heartbeatInterval: 0,
                heartbeatCollect: 0,
                heartbeatDormancy: 0,
                heartbeatOnline: 0,
                heartbeatRebootMinute: 0,
                heartbeatRebootHour: 0,
                heartbeatRebootDay: 0
            },
            thisYuntaiInfo: {},
            options: [{val:0,label:'每天'}]
        }
    },
    watch: {
        treeData:{
            handler (newVal){
                this.open = true;
                this.form = {
                    heartbeatReboot: 0,
                    heartbeatInterval: 0,
                    heartbeatCollect: 0,
                    heartbeatDormancy: 0,
                    heartbeatOnline: 0,
                    heartbeatRebootMinute: 0,
                    heartbeatRebootHour: 0,
                    heartbeatRebootDay: 0
                },
                this.fromTreeData = newVal;
            },
            deep:true
        },
    },
    created() {
        let days = this.mGetDate();
        for(let i = 0; i < days;i ++) {
            let obj = {'val':i+1, 'label': i+1}
            this.options.push(obj);
        }
        this.fromTreeData = this.treeData;
        let name = this.$options.name;
        this.act = global.fav_init_toggle(name) == 0?false:true;
    },
    mounted() {
    },
    methods: {
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        onSubmit() {
            this.form['yuntaiId'] = this.thisYuntaiInfo.id;
            saveHeartbeat(this.form).then(response => {
                if(response.code == 200) { 
                    this.$message.success(response.msg);
                } else {
                    this.$message(response.msg);
                }
            });
        },
        mGetDate(){
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth()+1;
            var d = new Date(year, month, 0);
            return d.getDate();
        },
        query() {
            // 查询设备
            this.thisYuntaiInfo = {};
            this.open = true;
            // this.thisYuntaiInfo['id'] = 1; // 测试
            for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];
                    this.getHeartbeat(this.thisYuntaiInfo.id);
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
            
        },
        getHeartbeat(id) {
            getHeartbeat(id).then(response => {
                if(response.code == 200 && response.data) {    
                    this.open = false;          
                  this.form = {
                      heartbeatReboot: response.data.heartbeat.heartbeatReboot?response.data.heartbeat.heartbeatReboot:0,
                      heartbeatInterval: response.data.heartbeat.heartbeatInterval?response.data.heartbeat.heartbeatInterval:0,
                      heartbeatCollect: response.data.heartbeat.heartbeatCollect?response.data.heartbeat.heartbeatCollect:0,
                      heartbeatDormancy: response.data.heartbeat.heartbeatDormancy?response.data.heartbeat.heartbeatDormancy:0,
                      heartbeatOnline: response.data.heartbeat.heartbeatOnline?response.data.heartbeat.heartbeatOnline:0,
                      heartbeatRebootMinute: response.data.heartbeat.heartbeatRebootMinute?response.data.heartbeat.heartbeatRebootMinute:0,
                      heartbeatRebootHour: response.data.heartbeat.heartbeatRebootHour?response.data.heartbeat.heartbeatRebootHour:0,
                      heartbeatRebootDay: response.data.heartbeat.heartbeatRebootDay
                  }
                } 
            });
        }
    }
}
</script>
<style lang="scss" scoped>
.head{display:flex;align-items:center;
    .fav{margin-right:5px;cursor: pointer;font-size:20px;color:$base-color-default;}
    .querybtn{margin-left:auto;}
}
.box-content{padding:20px 120px 0;
    ::v-deep{      
        .el-form-item__label{line-height:16px;}                          
        // el-silder
        .el-slider{flex:1;padding-left:10px;margin: 0 auto;}
        .el-input-number__decrease,.el-input-number__increase{display: none;}
        .el-slider__runway.show-input{margin-right:50px;}
        .el-input-number{width:40px;}
        .ipm{width: 100% !important;;}
        // .el-input__inner{padding-left:5px;}
        .el-slider__input .el-input__inner{padding:0px;}
        .el-input-number--mini .el-input__inner{padding-left:0;padding-right:0;}
    }
}
</style>