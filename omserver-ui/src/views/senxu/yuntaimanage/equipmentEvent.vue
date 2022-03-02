<template>
     <!-- 设备事件 -->
      <el-card class="app-container">
        <tree-container @ceshi="ccc">
              <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="70px" v-show="showSearch" size="mini" >
                <el-form-item label="搜索" prop="name">
                <el-input  v-model="queryParams.name"  placeholder="请输入设备名称" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery"/>
                </el-form-item>    
                <el-form-item label="记录时间" prop="day">                        
                    <el-date-picker clear-icon="" v-model="day" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                </el-form-item>      
                <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                </el-form-item>
            </el-form>
             <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                <el-button type="warning" plain  icon="el-icon-download" size="mini" @click="handleExport" >导出全部数据</el-button>     
                </el-col>
                <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
            </el-row>
            <el-table v-loading="loading" :data="eventlist" :height="tableHeight">
                <el-table-column label="序号" align="center" type="index" width="50"  :index="table_index"> </el-table-column>
                <el-table-column label="运行单位" align="center" key="unit" prop="unit" v-if="columns[0].visible">
                    <template slot-scope="scope">
                        {{scope.row.yuntaiInfo.unit?dicts.sys_company.obj[scope.row.yuntaiInfo.unit]&&dicts.sys_company.obj[scope.row.yuntaiInfo.unit].dictLabel:''}}
                    </template>
                </el-table-column>
                <el-table-column label="电压等级" align="center" key="level" prop="level" v-if="columns[1].visible" :show-overflow-tooltip="true" >
                    <template slot-scope="scope">
                        {{Object.keys(scope.row.yuntaiInfo).length>0 && scope.row.yuntaiInfo.level?dicts.line_voltage.obj[scope.row.yuntaiInfo.level]&&dicts.line_voltage.obj[scope.row.yuntaiInfo.level].dictLabel:''}}
                    </template>
                </el-table-column>
                <el-table-column label="线路名称" align="center" key="lineName" prop="lineName" v-if="columns[2].visible" :show-overflow-tooltip="true" >
                    <template slot-scope="scope">
                        {{Object.keys(scope.row.yuntaiInfo).length>0 && scope.row.yuntaiInfo.lineName?scope.row.yuntaiInfo.lineName:'暂无内容'}}
                    </template>
                </el-table-column>
                <el-table-column label="杆塔号" align="center" key="towerName" prop="towerName" v-if="columns[3].visible" :show-overflow-tooltip="true" >
                     <template slot-scope="scope">
                        {{Object.keys(scope.row.yuntaiInfo).length>0 && scope.row.yuntaiInfo.towerName?scope.row.yuntaiInfo.towerName:'暂无内容'}}
                    </template>
                </el-table-column>
                <el-table-column label="设备名称" align="center" key="name" prop="name" v-if="columns[4].visible" :show-overflow-tooltip="true" />
                <el-table-column label="设备ID" align="center" key="code" prop="code" v-if="columns[5].visible" :show-overflow-tooltip="true" />
                <el-table-column label="设备IP" align="center" key="ip" prop="ip" v-if="columns[6].visible" :show-overflow-tooltip="true" >
                    <template slot-scope="scope">
                        {{Object.keys(scope.row.yuntaiInfo).length>0 && scope.row.yuntaiInfo.ip?scope.row.yuntaiInfo.ip:'暂无内容'}}
                    </template>
                </el-table-column>
                <el-table-column label="设备厂商" align="center" key="factory" prop="factory" v-if="columns[7].visible" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        {{Object.keys(scope.row.yuntaiInfo).length>0 && scope.row.yuntaiInfo.factory?dicts.oem.obj[scope.row.yuntaiInfo.factory]&&dicts.oem.obj[scope.row.yuntaiInfo.factory].dictLabel:''}}
                    </template>
                </el-table-column>
                <el-table-column label="设备状态" :filters="dicts.sys_state.arry" :filter-method="filterHandler" align="center" key="state" prop="state" v-if="columns[8].visible" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        {{Object.keys(scope.row.yuntaiInfo).length>0 && scope.row.yuntaiInfo.state?dicts.sys_state.obj[scope.row.yuntaiInfo.state]&&dicts.sys_state.obj[scope.row.yuntaiInfo.state].dictLabel:''}}
                    </template>
                </el-table-column>
                <el-table-column label="记录时间" align="center" key="recordTime" prop="recordTime" v-if="columns[9].visible" :show-overflow-tooltip="true" />
            </el-table>
            <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
        </tree-container>
    </el-card>
</template>
<script>
import { mapGetters } from 'vuex'
import { get_yuntailog_list, export_yuntailog } from "@/api/senxu/config/yuntailog";
export default {
    data () {
        return {
            // 显示搜索条件
            showSearch: true,
            loading: true,
            day: [],
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                name: undefined,
                lineIds:undefined,towerIds:undefined,yuntaiId:undefined,
                spare0:undefined,
                spare1:undefined
            },      
            // 列信息
            columns: [
                { key: 0, label: `运行单位`, visible: true },
                { key: 1, label: `电压等级`, visible: true },
                { key: 2, label: `线路名称`, visible: true },
                { key: 3, label: `杆塔号`, visible: true },
                { key: 4, label: `设备名称`, visible: true },
                { key: 5, label: `设备ID`, visible: true },
                { key: 6, label: `设备IP`, visible: true },
                { key: 7, label: `设备厂家`, visible: true },
                { key: 8, label: `设备状态`, visible: true },
                { key: 9, label: `记录时间`, visible: true }
            ],
            eventlist:[],
            total:0 
        }
    },
    computed: {
        ...mapGetters([
            'dicts',
        ]),
        tableHeight () {
            return window.innerHeight - 330;
        }
    },
    created() {
        this.queryParams.spare0 = this.parseTime(new Date(),'{y}-{m}-{d}');
        this.queryParams.spare1 = this.parseTime(new Date(),'{y}-{m}-{d}');
        this.day = [this.parseTime(new Date(),'{y}-{m}-{d}'),this.parseTime(new Date(),'{y}-{m}-{d}')];
    },
    methods: {
        table_index(index) {
            return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
        },
        ccc(data) {
            // this.reset();
            this.queryParams.towerIds = undefined,this.queryParams.lineIds=undefined;this.queryParams.yuntaiId = undefined;
            // 从树结构带来数据
            if(data.node[0].node.type == "line") {this.queryParams.lineIds = data.node[0].node.id;} 
            else if(data.node[0].node.type == "tower") {this.queryParams.towerIds = data.node[0].node.id;this.queryParams.lineIds = data.node[0].node.parentId;}
            else if(data.node[0].node.type == "yuntai") {this.queryParams.yuntaiId = data.node[0].node.id;}
            this.getList();
        },
        reset () {            
            this.queryParams.name = undefined;
            this.queryParams.spare0 = this.parseTime(new Date(),'{y}-{m}-{d}');
            this.queryParams.spare1 = this.parseTime(new Date(),'{y}-{m}-{d}');
            this.day = [this.parseTime(new Date(),'{y}-{m}-{d}'),this.parseTime(new Date(),'{y}-{m}-{d}')];
        },
        /** 搜索按钮操作 */
        handleQuery() {
            this.queryParams.pageNum = 1;
            this.getList();
        },
        /** 重置按钮操作 */
        resetQuery() {
            this.reset();
            this.handleQuery();
        },
        getList () {      
            this.loading = true;
            this.queryParams.spare0 = this.day&&this.day.length>0?this.day[0]:undefined;
            this.queryParams.spare1 = this.day&&this.day.length>0?this.day[1]:undefined;
            get_yuntailog_list(this.queryParams).then(response => {
                if(response.code == 200 && response.rows) {
                    this.eventlist = response.rows;
                    this.total = response.total;
                    this.loading = false;
                }
            });
        },        
        filterHandler(value, row, column) {
            // 表格状态筛选事件
            const property = column['property'];
            return row[property] == value;
        },
        /** 导出按钮操作  */
        handleExport () {
            const queryParams = this.queryParams;
            this.$confirm('是否确认导出所有设备事件数据?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
                }).then(function() {
                return export_yuntailog(queryParams);
                }).then(response => {
                this.download(response.msg);
            })
        },
    }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card,.el-card__body{height:100%;}}
}
</style>