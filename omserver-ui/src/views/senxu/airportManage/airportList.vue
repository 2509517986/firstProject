<template>
  <el-card class="app-container">
    <!--线路数据-->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="110px" size="mini">
      <el-form-item size="mini" label="内容关键字搜索" prop="keyword">
        <el-input v-model="queryParams.keyword" placeholder="请输入机巣编号/机巣名称" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item size="mini">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="airportList" :height="tableHeight" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="机场类型" align="center" key="relname" prop="relname" v-if="columns[0].visible">
        <template slot-scope="scope">
          {{scope.row.relname==1?'固定机场':'胶囊机场'}}
        </template>
      </el-table-column>
      <el-table-column label="机巣编号" align="center" key="airportCode" prop="airportCode" v-if="columns[1].visible" :show-overflow-tooltip="true" />
      <el-table-column label="机巣名称" align="center" key="airportName" prop="airportName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
      <el-table-column label="所在位置" align="center" key="locationLon" prop="locationLon" :formatter="locationFormat" v-if="columns[3].visible" :show-overflow-tooltip="true" />
      <el-table-column label="预设备降点" align="center" key="spareLon" prop="spareLon" :formatter="spareFormat" v-if="columns[4].visible" :show-overflow-tooltip="true" />
      <el-table-column label="挂载设备" align="center" key="mount" prop="mount" v-if="columns[5].visible" :show-overflow-tooltip="true">
      </el-table-column>
      <el-table-column label="状态" align="center" key="state" prop="state" v-if="columns[6].visible" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{scope.row.uavState==1?'在线':'不在线'}}
        </template>
      </el-table-column>
      <el-table-column label="无人机ID" width="150" align="center" key="uavId" prop="uavId" v-if="columns[7].visible" :show-overflow-tooltip="true" />
      <el-table-column label="飞控ID" align="center" key="fcId" prop="fcId" v-if="columns[8].visible" :show-overflow-tooltip="true" />
      <el-table-column label="无人机状态" align="center" key="uavState" prop="uavState" v-if="columns[9].visible" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{scope.row.uavState==1?'在线':'不在线'}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="handleCheck(scope.row)">详细</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getAirportList" />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :destroy-on-close="true" @open="opendialog">
      <el-form ref="form" :model="form" label-width="100px" disabled v-if="open">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="机场编号" prop="airportCode">
              <el-input v-model="form.airportCode" size="small" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="机场名称" prop="airportName">
              <el-input v-model="form.airportName" size="small" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="机场类型" prop="relname">
              <el-input v-model="form.relname==1?'固定机场':'胶囊机场'"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="挂载设备" prop="mount">
              <el-input v-model="form.mount" controls-position="right" :min="0" size="small"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所在位置-经度" prop="locationLon">
              <el-input v-model="form.locationLon" min="0" size="small" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在位置-纬度" prop="locationLat">
              <el-input v-model="form.locationLat" min="0" size="small" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所在位置-高度（海拔）" label-width="150px" prop="locationAltinm">
              <el-input v-model="form.locationAltinm" controls-position="right" :min="0" size="small"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="state">
               <el-input v-model="form.state == 1?'在线':'不在线'"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="备降点-经度" prop="spareLon">
              <el-input v-model="form.spareLon" min="0" size="small" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="备降点-纬度" prop="spareLat">
              <el-input v-model="form.spareLat" min="0" size="small" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="备降点-海拔（高度）" label-width="150px" prop="spareAltinm">
              <el-input v-model="form.spareAltinm" min="0" size="small" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="飞控ID" prop="fcId">
              <el-input v-model="form.fcId" min="0" size="small" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="无人机ID" prop="uavId">
              <el-input v-model="form.uavId" min="0" size="small" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="无人机状态" prop="uavState">
               <el-input v-model="form.uavState == 1?'在线':'不在线'"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" size="mini">关 闭</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { mapGetters } from 'vuex'
import { getAirportList } from '@/api/senxu/airportManage/api'
export default {
  name: 'airportList',
  data () {
    return {
      airportList: [],//机巣列表数据
      filterdata: {}, //关键字搜索
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中名称
      idsname: [],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表单参数
      form: {},
      title: '',
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `机巣类型`, visible: true },
        { key: 1, label: `机巣编号`, visible: true },
        { key: 2, label: `机巣名称`, visible: true },
        { key: 3, label: `所在位置`, visible: true },
        { key: 4, label: `预设备降点`, visible: true },
        { key: 5, label: `固定机巣无人机云台挂在设备`, visible: true },
        { key: 6, label: `状态`, visible: true },
        { key: 7, label: `无人机ID`, visible: true },
        { key: 8, label: `飞控ID`, visible: true },
        { key: 9, label: `无人机状态`, visible: true }
      ],
    }
  },
  created () {
    this.getAirportList()
  },
  computed: {
    ...mapGetters([
      'dicts',
    ]),
    tableHeight () {
      return window.innerHeight - 350;
    },

  },
  methods: {
    getAirportList () {
      getAirportList(this.queryParams).then(res => {
        this.airportList = res.rows;
        this.total = res.total;
        this.loading = false;
      })
    },
    handleQuery () {
      this.queryParams.page = 1;
      this.getAirportList();
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset () {
      this.form = {
        airportCode: undefined,
        name: undefined,
        deptId: undefined,
        high: undefined,
        voltage: undefined,
        level: undefined,
        length: undefined,
        state: undefined,
        startPoint: undefined,
        endPoint: undefined,
        remark: undefined,
        completeDate: undefined
      };
      this.resetForm("form");
    },
    resetQuery () {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange (selection) {
      this.ids = selection.map(item => item.id);
      this.idsname = selection.map(item => item.name);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    opendialog () {
      // 打开dialog回调// 字典
      this.$store.dispatch("SetDicts").then(() => { }).catch(() => { });
    },
    slowl (key, data, name) {
      if (Object.keys(this.dicts[key].obj).length > 0 && data) {
        for (let k in this.dicts[key].obj) {
          if (data == k) {
            this.form[name] = data;
            return;
          } else {
            this.form[name] = undefined;
          }
        }
      }
    },
    //详细
    handleCheck (row) {
      this.form = row;
      // 匹配 字典删除后
      this.open = true;
      this.title = "详细";
      this.$nextTick(() => {
        this.$refs.form.resetFields();
      });
    },
    spareFormat (row) {
      if(row.spareLon){
        console.log(row, 'row');
        return `${row.spareLon},${row.spareLat},${row.spareAltinm}`;
      }else{
        return '';
      }
    },
    locationFormat (row) {
      if(row.locationLon){
        console.log(row, 'row');
        return `${row.locationLon},${row.locationLat},${row.locationAltinm}`;
       }else{
        return '';
      }
    }
  }
}
</script>

<style>
</style>