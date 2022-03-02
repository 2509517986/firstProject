<template>
    <!-- 设备配置-OSD参数配置 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="OSD参数配置" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>OSD参数配置</span>
            <el-button size="mini" type="primary" class="querybtn" @click="query" :disabled="fromTreeData.node[0].yuntais.length<0">查询设备</el-button>
        </div>
        <div class="box-content" v-loading="open && Object.keys(thisYuntaiInfo).length>0">
             <el-form ref="form" :model="form" label-width="80px" size="mini">
                <el-form-item label="时间OSD">
                    <el-switch size="mini" v-model="form.showTime" inactive-text="" :active-text="form.showTime?'开':'关'"> </el-switch>
                </el-form-item>
                <el-form-item label="自定义OSD">
                    <el-switch size="mini" v-model="form.showText" inactive-text="" :active-text="form.showText?'开':'关'"> </el-switch>
                </el-form-item>
                <el-form-item label="自定义文本">
                    <el-input v-model="form.text" class="customIpt"></el-input>
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
import { mapGetters } from 'vuex'
import { getOSD, setOSD } from "@/api/senxu/config/index";
export default {
    props: ['favIcon', 'treeData'],
    name: 'osdConfig',
    data () {
        return{
            open:true,
            act:false,
            fromTreeData: {},
            thisYuntaiInfo: {},
            form: {
                showTime: 0,
                showText: 0,
                text: ''

            },
            data: []
        }
    },
    computed: {
        ...mapGetters([
        'dicts',
        ])
    },
    watch: {
        treeData:{
            handler (newVal){
                this.open = true;
                this.fromTreeData = newVal;
            },
            deep:true
        },
    },
    created() {
        this.fromTreeData = this.treeData;
        let name = this.$options.name;
        this.act = global.fav_init_toggle(name) ==0?false:true;
    },
    methods: {
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        onSubmit() {
            let showTime = this.form.showTime?1:0;
            let showText = this.form.showText?1:0;
            setOSD({channelNo:this.form.channelNo,showTime,showText,text:this.form.text},this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    this.$message({
                        type: "success",
                        message: "操作成功"
                    });
                } else {
                    this.$message(response.msg);
                }
            });
        },
        query() {
            // 查询设备
            this.thisYuntaiInfo = {};
            this.open = true;
            for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];  
                    getOSD({channelNo: 1}, this.thisYuntaiInfo.id).then(response => {
                        if(response.code == 200 && response.data) {   
                            this.open = false; 
                            this.form = response.data;
                            this.form.showTime = this.form.showTime == 1?true:false;
                            this.form.showText = this.form.showText == 1?true:false;
                        } 
                    });                  
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
        }
    }
}
</script>
<style lang="scss" scoped>
::v-deep{.transfer-footer{margin-left: 15px;padding: 6px 5px;}.el-transfer-panel{width:300px;}.el-transfer-panel__body{height:320px;}.el-transfer-panel__list.is-filterable{height:268px;}}
.head{display:flex;align-items:center;
    .fav{margin-right:5px;cursor: pointer;font-size:20px;color:$base-color-default;}
    .querybtn{margin-left:auto;}
}
.box-content{display: flex;  padding:20px 120px 0;
    .customIpt{
        ::v-deep{.el-input__inner{width:450px;}}
    }
}
</style>