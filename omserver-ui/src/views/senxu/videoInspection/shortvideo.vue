<template>
     <!-- 短视频 -->
    <el-card class="app-container">
        <tree-container  @ceshi="ccc">
            <div class="todayInspection">
                <div class="top">
                    <div class="title">
                        <el-breadcrumb separator-class="el-icon-arrow-right" v-if="Object.keys(getTreedata).length>0 && getTreedata.parent.length>0">
                            <el-breadcrumb-item v-for="(e,i) in getTreedata.parent" :key="i">{{e.name}}</el-breadcrumb-item>
                        </el-breadcrumb>
                    </div>
                    <div class="tools">
                        <el-form :inline="true" :model="queryParams" ref="formInline" size="mini">
                            <el-form-item label="拍摄日期" prop="captureTime">
                                <el-date-picker size="mini" :picker-options="expireTimeOption" format="yyyy-MM-dd" clear-icon="" value-format="yyyy-MM-dd" v-model="queryParams.captureTime" type="date" placeholder="选择日期"></el-date-picker>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                                <el-button icon="el-icon-refresh" size="mini" @click="resetForm('formInline')">重置</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
                <el-row class="lists" :gutter="10" v-if="lists.length>0">
                    <el-col class="item" :span="8" v-for="(e,i) in lists" :key="i">
                        <video controls  >
                            <source  :src="domain(e.videoUrl)" type="video/mp4">
                        </video>
                    </el-col>
                </el-row>
                <el-empty v-else description="暂无数据"></el-empty>
                 <custom-pagination  v-show="total>0" :total="total" :pageSizes="[6,10,14,18]" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"  @pagination="getlist"></custom-pagination>
            </div>
        </tree-container>
    </el-card>
</template>
<script>
import * as global  from "@/utils/global";
import { mapGetters } from 'vuex'
import { get_history_video } from "@/api/senxu/yuntai_video/index";
export default {
    data () {
        return {
            lists: [],
            total: 0,
            getTreedata:{}, // 树结构数据
            queryParams: {pageNum: 1,pageSize: 10, yuntaiId: undefined, captureTime: undefined,lineId:undefined,towerId: undefined},
            expireTimeOption: {
                // 当天后不可以选择
                disabledDate(date) {
                    return date.getTime() > Date.now();
                }
            }
        }
    },
    created() {this.queryParams.captureTime = this.parseTime(new Date(), '{y}-{m}-{d}');},
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
        domain(src) {
            let domainSrc =  src;
            return domainSrc;
        },
        ccc(data) {
            this.getTreedata = data;
            this.queryParams.lineId= undefined; this.queryParams.towerId=undefined; this.queryParams.yuntaiId=undefined;
            if(data.node[0].node.type == "line") {this.queryParams.lineId = data.node[0].node.id;}
            else if(data.node[0].node.type == "tower") {this.queryParams.towerId = data.node[0].node.id;this.queryParams.lineId = data.node[0].node.parentId;}
            else if(data.node[0].node.type == "yuntai") {this.queryParams.yuntaiId = data.node[0].node.id;}
            this.getlist();
        },
        getlist() {
            this.lists = [];
            get_history_video(this.queryParams).then(response => {
                if(response.code == 200) {
                    this.total = response.total;
                    this.lists = response.rows;
                }
            });
        },
        resetForm() {
            this.queryParams.captureTime = this.parseTime(new Date(), '{y}-{m}-{d}');
            this.getlist();
        },
        handleQuery() {
            this.getlist();
        }
    }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card__body{height:100%;position: relative;}}
}
.todayInspection {display:flex; flex-direction: column;height:100%;overflow:hidden;
    .top{display:flex;align-items:center; flex-shrink: 0;margin-bottom:20px;
        .title{font-size:14px;}
        .tools{margin-left:auto;
            ::v-deep{
                .el-select,.el-date-editor{width:150px;}
                .el-form-item{margin-bottom:0;}
            }
        }
        .layout{margin-left:$base-padding;color:$base-menu-color;cursor: pointer;}
    }
    .lists{ flex:1;overflow-y:auto;overflow-x:hidden;
        .item{margin-top:10px;&:first-child,&:nth-child(2),&:nth-child(3){margin-top:0;}
            video{width:100%;height:100%; background:#000;}
        }
    }
}
</style>
