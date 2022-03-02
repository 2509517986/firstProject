<template>
    <!-- 设备配置-终端功能配置 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏终端参数" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>终端功能配置</span>
            <el-button size="mini" type="primary" class="querybtn" @click="query" :disabled="fromTreeData.node[0].yuntais.length<0">查询设备</el-button>
        </div>
        <div class="box-content"  v-loading="open && Object.keys(thisYuntaiInfo).length>0">
             <el-transfer :titles="['终端功能选择列表', '待启用功能项']" filterable filter-placeholder="请输入搜索内容" v-model="value" :data="data" >
                <el-button class="transfer-footer" slot="right-footer" size="mini" @click="setting" :disabled="Object.keys(thisYuntaiInfo).length<1 || open ">设置</el-button>
            </el-transfer>
        </div>
    </el-card>
</template>
<script>
import * as global  from "@/utils/global";
import { mapGetters } from 'vuex'
import { getHeartbeat, getFunction,saveFunction } from "@/api/senxu/config/index";
export default {
    props: ['favIcon', 'treeData'],
    name: 'terminalConfig',
    data () {
        return{
            open:true,
            act:false,
            value:[],
            checkVal: [],
            fromTreeData: {},
            thisYuntaiInfo: {},
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
                this.value = [];
                this.fromTreeData = newVal;
            },
            deep:true
        },
    },
    created() {
        this.fromTreeData = this.treeData;
        this.getFunctions();
        let name = this.$options.name;
        this.act = global.fav_init_toggle(name) ==0?false:true;
    },
    methods: {
        getFunctions() {            
            this.data = [];
            if(this.dicts.functions && this.dicts.functions.arry && this.dicts.functions.arry.length>0 && Object.keys(this.dicts).length>0) {
                this.dicts.functions.arry.forEach((item, index) => {
                    this.data.push({
                        label: item.text,
                        key: item.value
                    });
                });
            }
        },
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        setting() {
            saveFunction({yuntaiId:this.thisYuntaiInfo.id,functions:this.value}).then(response => {
                if(response.code == 200) { 
                    this.$message.success(response.msg);
                } else {
                    this.$message(response.msg);
                }
            });
        },
        checkchange(data) {
            this.checkVal = data;
        },
        resetCheckVal(data) {
            if(data.length<1) {return;}
            let obj = {};
            for(let i = 0;i<this.data.length;i++) {   
                obj[this.data[i].key] = 0;                     
            }
                               
            for(let j = 0; j<data.length;j++) {
                let key = data[j];
                obj[key] = 1;
            } 
            return obj;
        },
        query() {
            // 查询设备
            this.thisYuntaiInfo = {};
            this.value = [];
            this.checkVal  = [];
            this.getFunctions();
            this.open = true;
            // this.thisYuntaiInfo['id'] = 1; // 测试
            for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];  
                    getHeartbeat(this.thisYuntaiInfo.id).then(response => {
                        if(response.code == 200 && response.data) {   
                            this.open = false; 
                            if( response.data.functions && response.data.functions.length>0) {
                                this.value = this.resetToInt(response.data.functions);
                            }
                        } 
                    });                  
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
        },
        resetToInt(array) {
            let newArray = [];
            for(let i = 0;i<array.length;i++) {
                newArray.push(parseInt(array[i]));
            }
            return newArray;
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
.box-content{display: flex; justify-content: center; padding:20px 0 0;}
</style>