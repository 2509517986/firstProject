<template>
<!--巡检报告-日报表-->
<div class="datereport">
    <el-row>
      <el-col :span="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
          <!-- <el-form-item label="内容关键字搜索" prop="keyword">
            <el-input v-model="queryParams.keyword" placeholder="请输入线路编号/线路名称" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery"/>
          </el-form-item>       -->
          <el-form-item label="巡检日期" prop="inspectionTime">
            <el-date-picker size="mini" :picker-options="expireTimeOption" format="yyyy-MM-dd" value-format="yyyy-MM-dd" v-model="queryParams.inspectionTime" type="date" placeholder="选择日期"></el-date-picker>
          </el-form-item>        
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">       
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="userList">
          <el-table-column label="编号" align="center" type="index" width="50"  :index="table_index"> </el-table-column>
          <el-table-column label="创建时间" align="center" key="createTime" prop="createTime" v-if="columns[0].visible" />
          <el-table-column label="巡检生成日期" align="center" key="inspectionTime" prop="inspectionTime" v-if="columns[1].visible" />
          <el-table-column label="巡检计划" align="center" key="inspectionPlan" prop="inspectionPlan" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="巡检结论" align="center" key="inspectionResult" prop="inspectionResult" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="巡检人" align="center" key="inspector" prop="inspector" v-if="columns[4].visible" :show-overflow-tooltip="true" />
          <el-table-column label="设备数量" align="center" key="spare0" prop="spare0" v-if="columns[5].visible" :show-overflow-tooltip="true" />
          <el-table-column label="在线数量" align="center" key="spare1" prop="spare1" v-if="columns[6].visible" :show-overflow-tooltip="true" />
          <el-table-column label="操作" align="center" width="160"  class-name="small-padding fixed-width">
            <template slot-scope="scope">
                <el-button size="mini"  type="text"  icon="el-icon-download"  @click="handleExport(scope.row)">下载</el-button>
                <el-button type="text" size="mini"  @click="handleClick(scope.row)">编辑</el-button>
                <el-button @click="delClick(scope.row)" type="text" size="mini">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <custom-pagination  v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"  @pagination="getList" :autoplay="false"></custom-pagination>
      </el-col>
    </el-row>
    </div>
</template>
<script>
import { add_inspectionReport, get_inspectionReport, get_inspectionReport_list, export_inspectionReport, get_report_pictureList, del_inspectionReport } from "@/api/senxu/inspectionReport/index";
export default {
    name:'dateReport',
    props: ['treeData'],
    data () {
        return {
            loading: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
             // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                lineIds: undefined,
                keyword: undefined,
                inspectionTime: undefined
            },
            expireTimeOption: {
                // 当天后不可以选择
                disabledDate(date) {
                    return date.getTime() > Date.now();
                }
            },
            userList: [],
            // 列信息
            columns: [
                { key: 0, label: `创建时间`, visible: true },
                { key: 1, label: `巡检生成日期`, visible: true },
                { key: 2, label: `巡检计划`, visible: true },
                { key: 3, label: `巡检结论`, visible: true },
                { key: 4, label: `巡检人`, visible: true },
                { key: 5, label: `设备数量`, visible: true },
                { key: 6, label: `在线数量`, visible: true }
            ]
        }
    },
    watch: {
        treeData: {
            handler (newval) {
                this.getTreeData(newval);
            },
            deep: true
        }
    },
    created() {
        this.getTreeData(this.treeData);
    },
    methods: {
        table_index(index) {
            return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
        },
        getTreeData (data) {
            // 树返回的数据
            if(data.node[0].node.type == "line") {this.queryParams.lineIds = data.node[0].node.id;} 
            else if(data.node[0].node.type == "tower" || data.node[0].node.type == "yuntai") {this.$message("请选择线路"); return;}
            this.getList();
        },
        /** 导出按钮操作  */
        handleExport (item) {
          this.$confirm('是否确认导出当前数据?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return export_inspectionReport(item);
            }).then(response => {
              this.download(response.msg);
            })
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.page = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.dateRange = [];
            this.resetForm("queryForm");
            this.handleQuery();
        },
        getList() {
            this.loading = true;
            get_inspectionReport_list(this.queryParams).then(response => {
                if(response.code == 200 && response.rows) {
                  this.userList = response.rows;
                  this.total = response.total;
                  this.loading = false;
                }
            });
        },
        delClick(item) {
          // 删除
          let ids = item.id;
           this.$confirm('是否确认删除吗?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return del_inspectionReport(ids);
            }).then(() => {
              this.getList();
              this.msgSuccess("删除成功");
            }).catch(()=>{});
        },
        handleClick(item) {
          // 编辑
          item.lineIds = this.queryParams.lineIds;
          this.$emit('update:msg','one');
          this.$emit('update:childData', {thisReport: item})
        }
    }
}
</script>
<style lang="scss" scoped>
.datereport{

}
</style>