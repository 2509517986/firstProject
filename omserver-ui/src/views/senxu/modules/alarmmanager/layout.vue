<template>
<div class="alarmLayout">
    <div class="list" v-if="pics.length>0">
        <el-checkbox-group class="con" v-model="checkedEquipments" @change="handleCheckedCitiesChange">
            <div :class="['item', 'item' + queryParams.pageSize]" v-for="(e,i) in pics" :key="i" @dblclick="dbclick(i)">
                <el-tooltip class="ccc" effect="dark" content="双击查看详情" placement="top">
                    <div class="box">
                        <img :src="reDomain(e.alarmDir,e.alarmUrl)" alt="">
                        <div class="top">
                            <el-checkbox :label="e.id" :checked="!e.confirmState&&e.confirmState == 1" :disabled="e.confirmState == 1"></el-checkbox>
                            <div class="txt"><span>{{e.confirmState&&e.confirmState == 1?'已确认':'未确认'}}</span><span v-if="e.alarmSource!=1">智能分析告警上报</span><span>{{e.alarmSource==1?'手动告警':'自动告警'}}</span></div>
                        </div>
                        <div class="mask">
                            <div class="citem"><span class="view"  v-if="e.isBrowse && e.isBrowse != 0">已浏览</span><span class="name">{{e.lineName?e.lineName:''}}{{e.towerName?e.towerName:''}}{{e.yuntaiName?e.yuntaiName:''}}</span></div>
                            <div class="citem"><span class="time">{{e.alarmDate?e.alarmDate:'暂无时间'}}</span><span class="alarm">{{e.alarmSource == 1?e.imgType&&dicts['img_type'].obj[e.imgType]&&dicts['img_type'].obj[e.imgType].dictLabel:e.alarmAi&&dicts['alarm_ai'].obj[e.alarmAi].dictLabel}}</span></div>
                        </div>
                    </div>                    
                </el-tooltip>
            </div>
        </el-checkbox-group>
    </div>
    <el-empty description="暂无数据" v-else></el-empty>
    <div class="foot" v-if="pics.length>0">
            <el-checkbox class="checkall" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
            <el-button type="primary" class="btn" size="mini" @click="sureOk">一键确认</el-button>
            <custom-pagination  v-show="total>0" :total="total" :autoplay="false" :pageSizes="[4,8,12,16]" :page.sync="pageNum" :limit.sync="pageSize"  @pagination="getList"></custom-pagination>
    </div>
    <!-- 告警确认 -->
    <alarm-confrim :ids="selectids" v-if="show"></alarm-confrim>
    <!-- 图片详情 -->
    <alarm-detail v-if="show2" :yuntaiId="pics[imgindex].yuntaiId" :spare0="queryParams.spare0" :spare1="queryParams.spare1"></alarm-detail>
</div>
</template>
<script>
import * as global  from "@/utils/global";
import { mapGetters } from 'vuex'
import { ge_yuntaiAlarm_list2, get_editAlarm } from "@/api/senxu/alarm/index";
import { yuntaiPreview } from "@/api/senxu/yuntai_picture/index";
export default {
    name: "alarmLayout",
    props: ['query'],
    data () {
        return {
            queryParams: {},
            pageNum:1,
            pageSize:8,
            checkedEquipments: [],
            checkAll:false,
            show:false,
            show2:false,
            ids:[],
            total:0,
            pics: [],
            selectids: [],
            imgindex: undefined
        }
    },
    watch: {
        query: {
            handler(newval) {
                if(Object.keys(this.queryParams).length>0) {
                    this.queryParams = newval
                    this.getPictureList();
                }
            },
            deep:true
        }
    },
    mounted() {     
        this.queryParams = this.query;  
        if(Object.keys(this.query).length>0) {
            this.getPictureList();
        }
    },
    computed: {
        ...mapGetters([
            'dicts',
        ]),
    },
    methods: {
        getList(data) {
            // 分页翻回的信息
            this.checkAll = false;
            this.getPictureList();
        },   
        reDomain(url,name) {
            return  url + name;
        },
        handleCheckedCitiesChange(val) {
            let checkedCount = val.length;
            this.checkAll = checkedCount === this.pics.length;
        },
        checkallData(val,data) {
            // 循环全选
            this.checkedEquipments = [];
            for(let i = 0; i<data.length;i++) {
                if(val) {
                    // 全选选中
                    this.checkedEquipments.push(data[i].id);
                } else {
                    // 反选，但因已确认，checkbox有已选择的数据id
                    this.checkedEquipments = this.ids;
                }
            }
        },
        handleCheckAllChange(val) {
            // 全选
            this.checkallData(val,this.pics);
        },
        sureOk() { 
            this.selectids = Object.assign([], this.checkedEquipments);  
            if(this.ids.length>0) {
                // for(let i = 0; i < this.ids.length; i++) {
                //     let index = this.checkedEquipments.indexOf(this.ids[i]);
                //     this.selectids.splice(index,1);
                // }
                for(let i = this.ids.length - 1;i>=0; i--) {
                    let index = this.checkedEquipments.indexOf(this.ids[i]);
                    this.selectids.splice(index,1);
                }
            }
            if(this.selectids.length<1) {
                this.$message({
                    message: '请选择未确认内容',
                    type: 'warning'
                });
                return;
            }
            this.show = true;
        },        
        getPictureList() {
            /** 查询列表 */
            this.pics = [];
            this.queryParams['pageNum'] = this.pageNum;
            this.queryParams['pageSize'] = this.pageSize;
            this.checkedEquipments = [];
            this.ids = [];
            this.selectids = [];
            ge_yuntaiAlarm_list2(this.queryParams).then(response => {
                if(response.code == 200 && response.rows) {                
                this.pics = response.rows;
                this.total = response.total;
                this.ids = this.checkedEquipments;
                this.selectids = [];
                let array = [];
                // 已预览
                if(this.pics.length>0) {
                    for(let i = 0;i < this.pics.length; i++) {
                        if(this.pics[i].isBrowse == null || this.pics[i].isBrowse == 0) {
                        array.push(this.pics[i].id);
                        }
                    }
                    this.yuntaiPreview(array);
                }
                }
            });
        },    
        yuntaiPreview(arry) { 
            /** 已预览 */
            if(arry.length<1){return;}       
            get_editAlarm({"ids": arry.join(',')}).then(response => {});
        },
        dbclick(index) {
            // 双击详情
            this.show2 = true;
            this.imgindex = index;
        }
    },
    beforeDestroy () {
        this.$baseEventBus.$off('alarmQuery');
    }
}
</script>
<style lang="scss" scoped>
.alarmLayout{display:flex; flex-direction: column;height:100%;width:100%;
    .list{flex:1;padding-top:10px;overflow:hidden;
      .con{margin:0 -5px;display:flex;flex-wrap:wrap;width:100%;height:100%;align-content: flex-start;font-size:12px;
        .item{padding:5px;
            .box{position: relative;width:100%;height:100%;border-radius:4px;overflow:hidden;&.ccc{font-size:0;}
                img{width:100%;object-fit: cover;height: 100%;}
                .mask{position: absolute;left:0px;right:0px;bottom:0px;height:35px;background:$base-color-gray;font-size:12px;
                    .citem{position: relative;
                        .view{position:absolute;top:0px;left:7px;color:$base-color-green;}
                        .name{width:90%;margin:0 auto;}
                        .time{display:inline-block;text-align:left;vertical-align: middle;}
                        .alarm{padding-left:20px;display:inline-block;vertical-align: middle;}
                    }
                    span{display:block;transform: scale(0.8);text-align:center;color:#fff;}
                }
                .top{font-size:12px;position: absolute;left:0px;right:0px;top:0px;height:25px;padding:0 10px;background:$base-color-gray;display:flex;align-items:center;
                    .txt{margin-left:auto;
                        span{color:#fff;transform: scale(0.8); transform-origin: top right;margin-left:5px;display:inline-block;}
                    }
                    ::v-deep{.el-checkbox__label{display:none;}}
                }
            }
            &.item4{width:50%;height:50%;}
            &.item8{width:25%;height:50%;}
            &.item12{width:25%;height:33.3333%;}
            &.item16{width:25%;height:25%;}
        }
      }
    }
    .foot{position: relative;display:flex;align-items:center;
        ::v-deep{
            .el-checkbox__label{font-size:12px;}
        }
        .btn{margin-left:20px;}
        .pagination-container{margin-left:auto;}
    }
}
</style>