<!--
 * @Author: YJY
 * @Date: 2022-02-01 15:51:20
 * @System: proManagement
 * @Company: LESSO
 * @LastEditTime: 2022-02-03 20:06:10
 * @Description: file content
-->
<template>
     <!-- 历史巡检记录 -->
    <el-card class="app-container">
        <div class="historical">
            <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="70px"  size="mini">
                 
                <el-form-item  size="mini" label="关键字" prop="keyword">
                    <el-input  v-model="queryParams.keyword"  placeholder="请输入任务ID/任务描述" style="width:160px" clearable size="mini"  @keyup.enter.native="handleQuery"/>
                </el-form-item>    
                <el-form-item  label="开始时间" prop="startTime">
                    <el-date-picker size="mini" :picker-options="orderStartData" format="yyyy-MM-dd" clear-icon="" style="width:140px"  value-format="yyyy-MM-dd" v-model="queryParams.startTime" type="date" placeholder="选择日期" ></el-date-picker> 
                </el-form-item>  
                <el-form-item label="结束时间" prop="endTime">
                    <el-date-picker size="mini" :picker-options="orderEndData" format="yyyy-MM-dd" clear-icon="" style="width:140px" value-format="yyyy-MM-dd" v-model="queryParams.endTime" type="date" placeholder="选择日期"></el-date-picker> 
                </el-form-item>   
                <el-form-item  size="mini">
                    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                    <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="list" :height="tableHeight">
                <el-table-column label="序号" align="center" type="index" width="50"  :index="table_index"> </el-table-column>
                <el-table-column label="任务ID" align="center" key="taskId" prop="taskId" />
                <el-table-column label="任务描述" align="center" key="describes" prop="describes" />
                <el-table-column label="机场名称" align="center" key="ariportName" prop="ariportName" />
                <el-table-column label="开始巡检时间" align="center" key="startTime" prop="startTime" />
                <el-table-column label="结束巡检时间" align="center" key="endTime" prop="endTime" />
                <el-table-column label="用时(分钟)" align="center" key="duration" prop="duration" />
                <el-table-column
                    fixed="right"
                    label="操作"
                    width="100">
                    <template slot-scope="scope">
                        <el-button @click="openReport(scope.row)" type="text" size="small">巡检报告</el-button>
                    </template>
                </el-table-column>
                <!-- <el-table-column label="数据同步时间" width="150" align="center" key="updateTime" prop="updateTime" /> -->
                <!-- <el-table-column label="机巢ID" align="center" key="airportId" prop="airportId" /> -->
            </el-table>
            <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

        </div>
    </el-card>
</template>
<script>
import { get_historical_list } from "@/api/senxu/inspection/index";
export default {
    data () {
        return {
            queryParams: {startTime:undefined,keyword:undefined,endTime:undefined,pageNum:1,pageSize: 10},
            loading: false,
            total:0,
            list: [],      
            orderStartData:{
                disabledDate: (time) => {

                    if (this.queryParams.endTime) {
                        return time.getTime() > new Date(this.queryParams.endTime).getTime() || time.getTime() > Date.now();
                    }else{
                        return time.getTime() > Date.now();
                    }
                },
            }, 
            orderEndData:{
                disabledDate: (time) => {

                    if (this.queryParams.startTime) {
                        // 减掉86400000秒(一天)目的是把当前选中的下单日期包含在内可以选择，也就是说在选择结束日期时，可以选择当前下单日期
                        return time.getTime() < new Date(this.queryParams.startTime).getTime() - 86400000 || time.getTime() > Date.now();;  
                        
                    }else{
                        return time.getTime() > Date.now();
                    }
                },
            }, 
        }
    },
    computed: {
        tableHeight () {
            return window.innerHeight - 280;
        }
    },
    created() {
        // this.queryParams.startTime = this.parseTime(new Date(), '{y}-{m}-{d}');
        // this.queryParams.endTime = this.parseTime(new Date(), '{y}-{m}-{d}');
        this.getList();
    },
    methods: {
        table_index(index) {
            return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
        },
        getList() {
            this.loading = true; 
            get_historical_list(this.queryParams).then(response => {
                this.list = response.rows;
                this.total = response.total;
                this.loading = false;
            });
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        resetQuery() {
            this.queryParams.pageNum = 1;
            this.queryParams.startTime = undefined;
            this.queryParams.endTime = undefined;
            this.queryParams.keyword = undefined;
            this.getList();
        },
        /**
         * @description: 查看巡检报告
         * @param {*}
         * @return {*}
         */        
        openReport(row){
            this.$router.push({ path: '/inspection/report',query:{taskId:row.taskhId}});
        }
    }
}
</script>
<style lang="scss" scoped>
    .historical{}
</style>