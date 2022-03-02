<template>
     <!-- 日志中心 -->
    <el-card class="app-container">
        <div class="logContainer">
            <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="110px"  size="mini">
                <el-form-item  size="mini" label="用户名" prop="relname">
                    <el-input  v-model="queryParams.relname"  placeholder="请输入用户名称" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery"/>
                </el-form-item>    
                <el-form-item  size="mini" label="内容关键字搜索" prop="keyword">
                    <el-input  v-model="queryParams.keyword"  placeholder="请输入类型名称" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery"/>
                </el-form-item>    
                <el-form-item label="时间" prop="operaTime">
                    <el-date-picker size="mini" :picker-options="expireTimeOption" format="yyyy-MM-dd" clear-icon="" value-format="yyyy-MM-dd" v-model="queryParams.operaTime" type="date" placeholder="选择日期"></el-date-picker> 
                </el-form-item>   
                <el-form-item  size="mini">
                    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                    <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="list" :height="tableHeight">
                <el-table-column label="序号" align="center" type="index" width="50"  :index="table_index"> </el-table-column>
                <el-table-column label="日志ID" align="center" key="id" prop="id" />
                <el-table-column label="用户" align="center" key="relname" prop="relname" />
                <el-table-column label="远程登入IP" align="center" key="userIp" prop="userIp" />
                <el-table-column label="时间" align="center" key="operaTime" prop="operaTime" />
                <el-table-column label="类型" align="center" key="operaStlye" prop="operaStlye" />
                <el-table-column label="模块" align="center" key="operaModel" prop="operaModel" />
                <el-table-column label="内容" width="150" align="center" key="operaResult" prop="operaResult" />
            </el-table>
            <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

        </div>
    </el-card>
</template>
<script>
import { get_log_list } from "@/api/senxu/log";
export default {
    data () {
        return {
            queryParams: {relname:undefined,keyword:undefined,operaTime:undefined,pageNum:1,pageSize: 10},
            loading: false,
            total:0,
            list: [],            
            expireTimeOption: {
                // 当天后不可以选择
                disabledDate(date) {
                    return date.getTime() > Date.now();
                }
            }
        }
    },
    computed: {
        tableHeight () {
            return window.innerHeight - 280;
        }
    },
    created() {
        this.queryParams.operaTime = this.parseTime(new Date(), '{y}-{m}-{d}');
        this.getList();
    },
    methods: {
        table_index(index) {
            return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
        },
        getList() {
            this.loading = true;
            this.queryParams.relname = this.queryParams.relname&&this.queryParams.relname.length>0?this.queryParams.relname:undefined;
            this.queryParams.keyword = this.queryParams.keyword&&this.queryParams.keyword.length>0?this.queryParams.keyword:undefined;
            this.queryParams.operaTime = this.queryParams.operaTime&&this.queryParams.operaTime.length>0?this.queryParams.operaTime:undefined;
            get_log_list(this.queryParams).then(response => {
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
            this.queryParams.pageNum = 1;this.queryParams.relname = undefined;this.queryParams.keyword = undefined;this.queryParams.operaTime = undefined;
            this.getList();
        }
    }
}
</script>
<style lang="scss" scoped>
    .logContainer{}
</style>