<template>
    <!-- 平台信息-平台连接配置 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏平台连接配置" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>平台连接配置</span>
            <el-button size="mini" type="primary" class="querybtn" @click="query" :disabled="fromTreeData.node[0].yuntais.length<0">查询设备</el-button>
        </div>
        <div class="box-content"  v-loading="open && Object.keys(thisYuntaiInfo).length>0">
            <el-form ref="form" :model="form" label-width="80px" size="mini">
                <el-form-item label="平台地址" prop="platformIp">
                    <el-input size="mini" v-model="form.platformIp"></el-input>
                </el-form-item>
                <el-form-item label="端口号" prop="platformProt">
                    <el-input size="mini" v-model="form.platformProt"></el-input>
                </el-form-item>
                <el-form-item label="卡号" prop="platformCard">
                    <el-input size="mini" v-model="form.platformCard"></el-input>
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
import {getPlatform,savePlatform } from "@/api/senxu/config/index";
export default {
    props: ['favIcon', 'treeData'],
    name: 'platformInfo',
    data () {
        return{
            open: true,
            form:{},
            act:false,
            thisYuntaiInfo: {},
            fromTreeData: {}
        }
    },
    watch: {
        treeData:{
            handler (newVal){
                this.open = true;
                this.form = {};
                this.fromTreeData = newVal;
            },
            deep:true
        },
    },
    created() {
        this.fromTreeData = this.treeData;
        let name = this.$options.name;
        this.act = global.fav_init_toggle(name) == 0?false:true;
    },
    methods: {
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        onSubmit() {
            this.form['yuntaiId'] = this.thisYuntaiInfo.id;
            savePlatform(this.form).then(response => {
                if(response.code == 200) { 
                    this.$message.success(response.msg);
                } else {
                    this.$message(response.msg);
                }
            });
        },
        query() {
            // 查询设备
            this.thisYuntaiInfo = {};
            this.open = true;
            // this.thisYuntaiInfo['id'] = 1; // 测试
            for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];
                    this.getPlatform(this.thisYuntaiInfo.id);
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
            
        },
        getPlatform(id) {
            getPlatform(id).then(response => {
                if(response.code == 200 && response.data) {   
                    this.open = false;
                    this.form = {
                        platformIp: response.data.platformIp
                        ,platformProt: response.data.platformProt
                        ,platformCard: response.data.platformCard
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
.box-content{padding:20px 120px 0;width:60%;}
</style>