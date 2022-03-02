<template>
  <el-card class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="110px" size="mini">
      <el-form-item size="mini" label="内容关键字搜索" prop="keyword">
        <el-input v-model="queryParams.keyword" placeholder="请输入任务ID/任务描述" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item size="mini">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="airportList" :height="tableHeight" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="任务ID" align="center" key="taskId" prop="taskId" v-if="columns[1].visible" :show-overflow-tooltip="true" />
      <el-table-column label="任务描述" align="center" key="describes" prop="describes" v-if="columns[2].visible" :show-overflow-tooltip="true" />
      <el-table-column label="任务状态" align="center" key="state" prop="state" v-if="columns[3].visible" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          {{scope.row.state=='done'?'已完成':scope.row.state=='created'?'已创建':scope.row.state=='started'?'进行中':scope.row.state=='canceled'?'已取消':scope.row.state=='delay'?'已过期':'已中断'}}
        </template>
      </el-table-column>
      <el-table-column label="任务创建时间" align="center" key="createTime" prop="createTime" v-if="columns[4].visible" :show-overflow-tooltip="true" />
      <el-table-column label="任务开始时间" align="center" key="startTime" prop="startTime" v-if="columns[5].visible" :show-overflow-tooltip="true">
      </el-table-column>
      <el-table-column label="任务结束时间" align="center" key="endTime" prop="endTime" v-if="columns[5].visible" :show-overflow-tooltip="true">
      </el-table-column>
      <el-table-column label="航线ID" width="150" align="center" key="routeId" prop="routeId" v-if="columns[7].visible" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="handleRoad(scope.row)">航线列表</el-button>
        </template>
      </el-table-column>
      <el-table-column label="地图" align="center" width="120" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="handleCheck(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getAirportTastList" />
    <!-- 航线列表弹框 -->
    <el-dialog :title="title" :visible.sync="openRoad" width="1000px" append-to-body :destroy-on-close="true" @open="opendialogRoad">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showTaskSearch" label-width="110px" size="mini">
        <el-form-item size="mini" label="内容关键字搜索" prop="keyword">
          <el-input v-model="roadListqueryParams.keyword" placeholder="请输入航线ID/航线名称" clearable size="mini" style="width: 240px" @keyup.enter.native="handleTaskQuery" />
        </el-form-item>
        <el-form-item size="mini">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleTaskQuery">搜索</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="loading" :data="airportRoadList" :height="tableHeight">
        <el-table-column label="编号" width="50" align="center">
          <template slot-scope="scope">
            <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
          </template>
        </el-table-column>
        <el-table-column label="航线ID" align="center" key="routeId" prop="routeId" v-if="columns[0].visible" />
        <el-table-column label="航线名称" align="center" key="name" prop="name" v-if="columns[1].visible" :show-overflow-tooltip="true" />
        <el-table-column label="航线描述" align="center" key="describes" prop="describes" v-if="columns[2].visible" :show-overflow-tooltip="true" />
        <el-table-column label="航线组ID" align="center" key="groupId" prop="groupId" v-if="columns[2].visible" :show-overflow-tooltip="true" />
        <el-table-column label="航线类型" align="center" key="kind" prop="kind" v-if="columns[3].visible" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            {{scope.row.kind==0?'可见光精确巡检':scope.row.kind==1?'可见光通道巡检':scope.row.kind==10?'红外光精确巡检':'红外光通道巡检'}}
          </template>
        </el-table-column>
        <el-table-column label="航线安全起始点" align="center" key="startLat" prop="startLat" v-if="columns[4].visible" :formatter="startLatFormat" :show-overflow-tooltip="true" />
        <el-table-column label="航线安全终止点" align="center" key="endLat" prop="endLat" v-if="columns[4].visible" :formatter="endLatFormat" :show-overflow-tooltip="true" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelRoad" size="mini">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1100px" append-to-body :destroy-on-close="true" @open="opendialog">
      <el-row :gutter="20" v-if="open">
        <el-col :span="14">
          <el-form ref="form" :model="form" label-width="100px" disabled v-if="open">
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="任务ID" prop="taskId">
                  <el-input v-model="form.taskId" size="small" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务描述" prop="describes">
                  <el-input v-model="form.describes" size="small" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="任务状态" prop="state">
                  <el-input v-model="form.state=='done'?'已完成':form.state=='created'?'已创建':form.state=='started'?'进行中':form.state=='canceled'?'已取消':form.state=='delay'?'已过期':'已中断'" size="small"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务创建时间" prop="createTime">
                  <el-input v-model="form.createTime" controls-position="right" :min="0" size="small"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="任务开始时间" prop="startTime">
                  <el-input v-model="form.startTime" min="0" size="small" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务过期时间" prop="endTime">
                  <el-input v-model="form.endTime" min="0" size="small" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-col>
        <el-col :span="10">
          <el-table v-loading="loading" :data="airportRoadList" height="195">
            <el-table-column label="编号" width="50" align="center">
              <template slot-scope="scope">
                <span>{{(roadListqueryParams.pageNum - 1) * roadListqueryParams.pageSize + scope.$index + 1}}</span>
              </template>
            </el-table-column>
            <el-table-column label="航线ID" align="center" key="routeId" prop="routeId" v-if="columns[0].visible" />
            <el-table-column label="航线名称" align="center" key="name" prop="name" v-if="columns[1].visible" :show-overflow-tooltip="true" />
            <el-table-column label="航线描述" align="center" key="describes" prop="describes" v-if="columns[2].visible" :show-overflow-tooltip="true" />
            <el-table-column label="航线类型" align="center" key="kind" prop="kind" v-if="columns[3].visible" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                {{scope.row.kind==0?'可见光精确巡检':scope.row.kind==1?'可见光通道巡检':scope.row.kind==10?'红外光精确巡检':'红外光通道巡检'}}
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <!-- 地图 -->
          <map-com v-if="open" @list="pushdata" :roadList="roadList" :point="point" :type="true" :deviceType="'jichao'"></map-com>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" size="mini">关 闭</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { mapGetters } from 'vuex'
import { getAirportTastList, getAirportRoadList, getAirportRoadDetail, getAllAirportRoadList, getAirportTaskByTaskId } from '@/api/senxu/airportManage/api'
export default {
  name: 'airportList',
  data () {
    return {
      airportList: [],//机巣列表数据
      airportRoadList: [],//航线列表数据
      filterdata: {}, //关键字搜索
      point: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 选中名称
      idsname: [],
      // 显示搜索条件
      showSearch: true,
      showTaskSearch: true,
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
      openRoad: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: undefined
      },
      roadListqueryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: undefined
      },
      taskId: '',//任务id
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
      roadList: [],//根据任务Id查询的航线数据
    }
  },
  created () {
    this.getAirportTastList()
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
    getAirportTastList () {
      getAirportTastList(this.queryParams).then(res => {
        this.airportList = res.rows;
        this.total = res.total;
        this.loading = false;
      })
    },
    getAllAirportRoadList () {
      getAirportTastList(this.queryParams).then(res => {
        this.airportRoadList = res.rows;
        this.total = res.total;
        this.loading = false;
      })
    },
    handleQuery () {
      this.queryParams.page = 1;
      this.getAirportTastList();
    },
    handleTaskQuery () {
      this.roadListqueryParams.page = 1;
      this.getAirportRoadList(this.taskId);
      // this.getAllAirportRoadList();
    },
    // 取消按钮
    cancel () {
      this.open = false;
      this.reset();
    },
    // 航线列表关闭按钮
    cancelRoad () {
      this.openRoad = false;
      this.queryParams.pageSize = 10;
      this.queryParams.keyword = undefined;
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
    // 打开航线列表
    opendialogRoad () {
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
      getAirportTastList(this.queryParams).then(res => {
        if (res.code == 200) {
          this.form = row;
          // 匹配 字典删除后
          this.title = "详细";
          this.getAirportRoadList(row.taskId, true)
        } else {
          this.$message({
            message: '航线信息不存在',
            type: 'warning'
          })
        }
      })
    },
    //點擊航线列表
    handleRoad (row) {
      this.taskId = row.taskId;
      this.roadListqueryParams.keyword = undefined;
      // 匹配 字典删除后
      this.openRoad = true;
      this.title = "航线列表";
      this.getAirportRoadList(row.taskId)
    },
    //获取航线列表
    getAirportRoadList (taskId, type) {

      let joinStr = `?taskId=${taskId}`;
      if (this.roadListqueryParams.keyword) {
        joinStr += `&keyword=${this.roadListqueryParams.keyword}`
      }
      // roadListqueryParams
      this.roadListqueryParams.pageSize = 1000;
      getAirportRoadDetail(joinStr).then(res => {
        this.airportRoadList = res.rows;
        // this.total = res.total;
        this.loading = false;
        let allRoadList = [];
        res.rows.forEach(item => {
          allRoadList = [{ "lng": item.startLon, "lat": item.startLat }];
          if (item.tRoutePoint.length > 0) {
            item.tRoutePoint.forEach(ite => {
              allRoadList.push({ "lng": ite.lon, "lat": ite.lat })
            })
          }
          item.routeList = allRoadList;
          allRoadList.push({ "lng": item.endLon, "lat": item.endLat });
        })
        console.log(res.rows, 'allRoadList----');
        this.roadList = res.rows;
        if (type) this.open = true;
      },
      this.roadListqueryParams.keyword = undefined
      )
    },
    //获取地图数据
    // 地图数据
    pushdata (data) {
      console.log(data, 'data');
      this.form.address = data.address;
      this.form.longitude = data.point.lng;
      this.form.latitude = data.point.lat;
    },
    startLatFormat (row) {
      return `${row.startLon},${row.startLat},${row.startAltinm}`;
    },
    endLatFormat (row) {
      return `${row.endLon
        },${row.endLat
        },${row.endAltinm}`;
    }
  }
}
</script>

<style>
</style>
