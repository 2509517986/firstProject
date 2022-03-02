<template>
    <!-- 设备台账列表 -->
  <el-card class="app-container">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="70px" v-show="showSearch" size="mini" >
        <el-form-item label="搜索" prop="keyword">
          <el-input  v-model="queryParams.keyword"  placeholder="请输入设备名称、线路名称、杆塔号" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery"/>
        </el-form-item>    
        <el-form-item label="安装时间" prop="day">                        
            <el-date-picker size="mini" :picker-options="expireTimeOption" format="yyyy-MM-dd" clear-icon="" value-format="yyyy-MM-dd" v-model="queryParams.installDate" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>      
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-row class="app-li" :gutter="10" v-show="showSearch">
        <el-col :span="24">
          <el-card shadow="never">
              <div class="typelist">
                  <div class="item" v-for="(e,i) in checklist" :key="i">
                      <h4>{{e.name}}：</h4>
                      <div class="checkboxs">
                          <el-checkbox  size="mini" class="checkall" v-model="e.checkAll" @change="val => handleCheckAllChange(val, e)">全选</el-checkbox>
                          <el-checkbox-group  size="mini" v-model="e.checkedEquipments" @change="val => handleCheckedCitiesChange(val, e)">
                              <el-checkbox  size="mini" :label="ee.dictValue" v-for="(ee,ii) in e.list" :key="ii">{{ee.dictLabel}}</el-checkbox>
                          </el-checkbox-group>
                      </div>
                  </div>
              </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button type="warning" plain  icon="el-icon-download" size="mini" @click="handleExport">导出全部数据</el-button>     
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
      </el-row>
      <el-table v-loading="loading" :data="ledgerlist" :height="tableHeight">
        <el-table-column type="number" width="50" align="center" />
        <el-table-column label="运行单位" align="center" key="unit" prop="unit" v-if="columns[0].visible">
          <template slot-scope="scope">
            {{scope.row.unit&&dicts.sys_company&&dicts.sys_company.obj&&dicts.sys_company.obj[scope.row.unit]?dicts.sys_company.obj[scope.row.unit].dictLabel:''}}
          </template>
        </el-table-column>
        <el-table-column label="电压等级" align="center" key="level" prop="level" v-if="columns[1].visible" :show-overflow-tooltip="true" >
          <template slot-scope="scope">
            {{scope.row.level&&dicts.line_voltage&&dicts.line_voltage.obj&&dicts.line_voltage.obj[scope.row.level]?dicts.line_voltage.obj[scope.row.level].dictLabel:''}}
          </template>
        </el-table-column>
        <el-table-column label="线路名称" align="center" key="lineName" prop="lineName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
        <el-table-column label="杆塔号" align="center" key="towerName" prop="towerName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
        <el-table-column label="操作" align="center" :show-overflow-tooltip="true" >
          <template slot-scope="scope">
            <el-button size="mini" type="text"  icon="el-icon-edit"  @click="handleUpdate(scope.row)" >操作</el-button>
          </template>
        </el-table-column>
        <el-table-column label="设备标签" align="center" key="high" prop="high" v-if="columns[4].visible" :show-overflow-tooltip="true" />
        <el-table-column label="设备名称" align="center" key="name" prop="name" v-if="columns[5].visible" :show-overflow-tooltip="true" />
        <el-table-column label="通道名称" align="center" key="voltage" prop="voltage" v-if="columns[6].visible" :show-overflow-tooltip="true" />
        <el-table-column label="安装方向" align="center" key="direction" prop="direction" v-if="columns[7].visible" :show-overflow-tooltip="true" />
        <el-table-column label="接入方式" align="center" key="intoType" prop="intoType" v-if="columns[8].visible" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            {{scope.row.intoType&&dicts.access_type&&dicts.access_type.obj&&dicts.access_type.obj[scope.row.intoType]?dicts.access_type.obj[scope.row.intoType].dictLabel:''}}
          </template>
        </el-table-column>  
        <el-table-column label="VPN专网卡号" align="center" key="cardNum" prop="cardNum" v-if="columns[9].visible" :show-overflow-tooltip="true" />
        <el-table-column label="VPN专网卡号2" align="center" key="cardNum2" prop="cardNum2" v-if="columns[10].visible" :show-overflow-tooltip="true" />        
        <el-table-column label="网络类型" align="center" key="network" prop="network" v-if="columns[11].visible" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            {{scope.row.network&&dicts.network_type&&dicts.network_type.obj&&dicts.network_type.obj[scope.row.network].dictLabel?dicts.network_type.obj[scope.row.network].dictLabel:''}}
          </template>
        </el-table-column> 
        <el-table-column label="终端ID" align="center" key="code" prop="code" v-if="columns[12].visible" :show-overflow-tooltip="true" />
        <el-table-column label="安装状态" align="center" key="installState" prop="installState" v-if="columns[13].visible" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            {{scope.row.factory&&dicts.install_state&&dicts.install_state.obj&&dicts.install_state.obj[scope.row.installState]?dicts.install_state.obj[scope.row.installState].dictLabel:''}}
          </template>
        </el-table-column> 
        <el-table-column label="安装时间" align="center" key="installDate" prop="installDate" v-if="columns[14].visible" :show-overflow-tooltip="true" />
        <el-table-column label="隐患类型" align="center" key="hiddenDanger" prop="hiddenDanger" v-if="columns[15].visible" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            {{scope.row.hiddenDanger&&dicts.danger_type&&dicts.danger_type.obj&&dicts.danger_type.obj[scope.row.hiddenDanger]?dicts.danger_type.obj[scope.row.hiddenDanger].dictLabel:''}}
          </template>
        </el-table-column>  
        <el-table-column label="隐患详情" align="center" key="hiddenDangerDes" prop="hiddenDangerDes" v-if="columns[16].visible" :show-overflow-tooltip="true" />
        <el-table-column label="设备厂商" align="center" key="factory" prop="factory" v-if="columns[17].visible" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            {{scope.row.factory&&dicts.oem&&dicts.oem.obj&&dicts.oem.obj[scope.row.factory]?dicts.oem.obj[scope.row.factory].dictLabel:''}}
          </template>
        </el-table-column>
        <el-table-column label="监测类型" align="center" key="monitorType" prop="monitorType" v-if="columns[18].visible" :show-overflow-tooltip="true">
           <template slot-scope="scope">
            {{scope.row.monitorType&&dicts.monitor_type&&dicts.monitor_type.obj&&dicts.monitor_type.obj[scope.row.monitorType]?dicts.monitor_type.obj[scope.row.monitorType].dictLabel:''}}
          </template>
        </el-table-column>
        <el-table-column label="上图状态" align="center" key="imageStatus" prop="imageStatus" v-if="columns[19].visible" :show-overflow-tooltip="true">
          <template slot-scope="scope">
             {{scope.row.imageStatus?dicts.image_status.obj[scope.row.imageStatus]&&dicts.image_status.obj[scope.row.imageStatus].dictLabel:''}}
          </template>
        </el-table-column>
        <el-table-column label="消缺记录" align="center" key="defectRecord" prop="defectRecord" v-if="columns[20].visible" :show-overflow-tooltip="true">
           <template slot-scope="scope">
            <el-button size="mini" type="text"  icon="el-icon-view"  @click="defectRecordClick(scope.row)" >查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="设备电量(%)" align="center" key="electricPower" prop="electricPower" v-if="columns[21].visible" :show-overflow-tooltip="true" />
        <el-table-column label="设备IP" width="150" align="center" key="ip" prop="ip" v-if="columns[22].visible" :show-overflow-tooltip="true" />
        <el-table-column label="在线状态" :filters="dicts.sys_state.arry" :filter-method="filterHandler" align="center" key="state" prop="state" v-if="columns[23].visible" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            {{scope.row.state&&dicts.sys_state&&dicts.sys_state.obj&&dicts.sys_state.obj[scope.row.state]?dicts.sys_state.obj[scope.row.state].dictLabel:''}}
          </template>
        </el-table-column>
        <el-table-column label="在线/离线时间" align="center" key="onlineTime" prop="onlineTime" v-if="columns[24].visible" :show-overflow-tooltip="true" />
        <el-table-column label="设备在线/离线时长" width="150" align="center" key="onlineDuration" prop="onlineDuration" v-if="columns[25].visible" :show-overflow-tooltip="true" />
        <el-table-column label="当前版本信息" width="150" align="center" key="completeDate" prop="completeDate" v-if="columns[26].visible" :show-overflow-tooltip="true" />
        <el-table-column label="版本日期" width="150" align="center" key="versionUpdate" prop="versionUpdate" v-if="columns[27].visible" :show-overflow-tooltip="true" />
        <el-table-column label="固件版本" width="150" align="center" key="version" prop="version" v-if="columns[28].visible" :show-overflow-tooltip="true" />
      </el-table>
      <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
    <!-- 添加消缺记录 -->
    <el-dialog title="添加消缺记录" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item  size="mini" label="线路杆塔" prop="lineName" v-if="Object.keys(thisEditData).length>0">
          <el-input v-model="thisEditData.lineName" disabled size="small"/>
        </el-form-item>
        <el-form-item  size="mini" label="设备编号" prop="code" v-if="Object.keys(thisEditData).length>0">
          <el-input v-model="thisEditData.code" disabled size="small"/>
        </el-form-item>
        <el-form-item  size="mini" label="责任人" prop="modifyUser" v-if="Object.keys(thisEditData).length>0">
          <el-input v-model="thisEditData.modifyUser" disabled size="small"/>
        </el-form-item>
        <el-form-item size="mini" label="所属厂家" prop="factory" v-if="Object.keys(thisEditData).length>0">
          <el-input v-model="thisEditData.factory" disabled size="small"/>
        </el-form-item>
        <el-form-item  size="mini" label="缺陷发生时间" prop="defectDate">
          <el-date-picker v-model="form.defectDate" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期" size="small"></el-date-picker>
        </el-form-item>
        <el-form-item  size="mini" label="消缺时间" prop="restoreTime">
          <el-date-picker v-model="form.restoreTime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" type="datetime" placeholder="选择日期" size="small"></el-date-picker>
        </el-form-item>
        <el-form-item  size="mini" label="消缺类型" prop="restoreType">
          <el-select v-model="form.restoreType" placeholder="请选择" size="small">
            <el-option v-for="(item, key) in dicts.t_yuntai_breakdown.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item  size="mini" label="处理措施" prop="recoverMeasures">
         <el-input v-model="form.recoverMeasures" type="textarea" placeholder="请输入内容" size="small"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" size="mini">确 定</el-button>
        <el-button @click="cancel" size="mini">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查询消缺记录 -->
    <el-dialog title="查询消缺记录" :visible.sync="open2" width="1100px" append-to-body>
      <el-table v-loading="loading" :data="breaklist" :height="tableHeight">
        <el-table-column type="number" width="50" align="center" />
        <el-table-column label="云台ID" align="center" key="yuntaiId" prop="yuntaiId" />
        <el-table-column label="云台编号" align="center" key="code" prop="code" />
        <el-table-column label="所属线路" align="center" key="lineName" prop="lineName" />
        <el-table-column label="责任人" align="center" key="dutyUser" prop="dutyUser" />
        <el-table-column label="设备厂商" align="center" key="factory" prop="factory" >
          <template slot-scope="scope">
            {{scope.row.factory?dicts.oem.obj[scope.row.factory]&&dicts.oem.obj[scope.row.factory].dictLabel:''}}
        </template>
        </el-table-column>
        <el-table-column label="缺陷发生时间" align="center" key="defectDate" prop="defectDate" />
        <el-table-column label="缺陷时间" align="center" key="restoreTime" prop="restoreTime" />
        <el-table-column label="缺销类型" align="center" key="restoreType" prop="restoreType" >
           <template slot-scope="scope">
              {{scope.row.restoreType&&dicts.t_yuntai_breakdown.obj[scope.row.restoreType]?dicts.t_yuntai_breakdown.obj[scope.row.restoreType].dictLabel:''}}
            </template>
        </el-table-column>
        <el-table-column label="处理措施" align="center" key="recoverMeasures" prop="recoverMeasures" />
        <!-- <el-table-column label="状态" align="center" key="state" prop="state" />
        <el-table-column label="操作" align="center" key="state" prop="state" width="160" >
          <template slot-scope="scope">
            <el-button size="mini"  type="text"  icon="el-icon-edit"  @click="handleUpdate2(scope.row)" v-hasPermi="['system:user:edit']">确认</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete"  @click="handleDelete2(scope.row)" v-hasPermi="['system:user:remove']">删除</el-button>
          </template>
        </el-table-column> -->
      </el-table>
    </el-dialog>
  </el-card>
</template>
<script>
import { mapGetters } from 'vuex'
import { add_yuntai,edit_yuntai,get_yuntai_detail,del_yuntai,get_yuntaiList,export_yuntai } from "@/api/senxu/devicemanager/yuntai";
import { add_breakdown,get_breakdownList,edit_breakdownInfo,del_breakdownInfo } from "@/api/senxu/config/breakdown";
export default {
  data () {
    return {      
      // 显示搜索条件
      showSearch: true,
      loading: true,
      breaklist: [], // 消缺记录列表
      open:false, // 添加消缺记录
      open2: false, // 弹出消缺记录
      thisEditData: {}, // 操作
      form: {
        defectDate: undefined,
        restoreTime: undefined,
        restoreType: undefined, 
        recoverMeasures: undefined
      },
      ids: [],
      // 选中名称
      idsname: [],
      ledgerlist:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: undefined,
        network:undefined,
        hiddenDanger:undefined,
        factory: undefined,
        spare4: undefined, // 状态
        imageStatuss: undefined,
        monitorType: undefined,
        installDate: undefined
      },
      total: 0, 
      // 显示搜索条件
      showSearch: true,
      checklist: [
        {   
            name: "网络类型",
            checkAll: true,
            checkedEquipments: [],
            key:'network_type',
            keyP:'network',
            list: {}
        },
        {
            name: "隐患类型",
            checkAll: true,
            checkedEquipments: [],
            key:'danger_type',
            keyP:'hiddenDanger',
            list: {}
        },
        {
            name: "设备厂商",
            checkAll: true,
            checkedEquipments: [],
            key:'oem',
            keyP:'factory',
            list: {}
        },
        {
            name: "监测类型",
            checkAll: true,
            checkedEquipments: [],
            key:'monitor_type',
            keyP:'monitorType',
            list: {}
        },
        {
            name: "在线状态",
            checkAll: true,
            key:'state',
            keyP:'spare4',
            checkedEquipments: [],
            list: {}
        },
        {
            name: "上图状态",
            checkAll: true,
            key:'image_status',
            keyP:'imageStatus',
            checkedEquipments: [],
            list: {}
        }                        
      ],
      expireTimeOption: {
          // 当天后不可以选择
          disabledDate(date) {
              return date.getTime() > Date.now();
          }
      },      
      // 列信息
      columns: [
        { key: 0, label: `云台名称`, visible: true },
        { key: 1, label: `设备ID`, visible: true },
        { key: 2, label: `运行单位`, visible: true },
        { key: 3, label: `电压等级`, visible: true },
        { key: 4, label: `所属线路`, visible: true },
        { key: 5, label: `所属杆塔`, visible: true },
        { key: 6, label: `安装方向`, visible: true },
        { key: 7, label: `接入方式`, visible: true },
        { key: 8, label: `VPN专网卡号`, visible: true },
        { key: 9, label: `VPN专网卡号2`, visible: true },
        { key: 10, label: `网络类型`, visible: true },
        { key: 11, label: `安装时间`, visible: true },
        { key: 12, label: `隐患类型`, visible: true },
        { key: 13, label: `隐患详情`, visible: true },
        { key: 14, label: `运维班组`, visible: true },
        { key: 15, label: `设备厂商`, visible: true },
        { key: 16, label: `监测类型`, visible: true },
        { key: 17, label: `上图状态`, visible: true },
        { key: 18, label: `消缺记录`, visible: true },
        { key: 19, label: `设备电量`, visible: true },
        { key: 20, label: `设备IP`, visible: true },
        { key: 21, label: `在线状态`, visible: true },
        { key: 22, label: `在线/离线时间`, visible: true },
        { key: 23, label: `设备在线/离线时长`, visible: true },
        { key: 24, label: `当前版本信息`, visible: true },
        { key: 25, label: `软件更新时间`, visible: true },
        { key: 26, label: `固件版本`, visible: true },
        { key: 27, label: `软件更新时间`, visible: true },
        { key: 28, label: `固件版本`, visible: true }
      ],
       // 表单校验
      rules: {
        defectDate: [
          { required: true, message: "缺陷发生时间不能为空", trigger: "change" }
        ],
        restoreTime: [
          { required: true, message: "消缺时间不能为空", trigger: "change" }
        ],
        restoreType: [
          { required: true, message: "消缺类型不能为空", trigger: "change" }
        ], 
        recoverMeasures: [
          { required: true, message: "处理措施不能为空", trigger: "blur" }
        ]
      }
    }
  },
  computed: {
    ...mapGetters([
      'dicts',
    ]),
    tableHeight () {
      return window.innerHeight - 500;
    }
  },
  created() {
    this.init();
    this.getList();
  },
  methods: {
    init() {
      this.queryParams.keyword = undefined;
      this.queryParams.installDate = this.parseTime(new Date(), '{y}-{m}-{d}');
      for(let i = 0;i<this.checklist.length;i++) {
        this.checklist[i].list = this.dicts[this.checklist[i].key].obj;
        this.checkAllv(true, this.checklist[i]);
      }
    },
    reset() {
      // 重置
      this.queryParams.keyword = undefined;
      this.queryParams.installDate = this.parseTime(new Date(), '{y}-{m}-{d}'); 
      for(let i = 0;i<this.checklist.length;i++) {
        this.checklist[i].checkAll = true;
        this.checkAllv(true, this.checklist[i]);
      }
    },
    submitForm() {
      // 提交消缺
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form['yuntaiId'] = this.thisEditData.yuntaiId;
          add_breakdown(this.form).then((response) => {
            if(response.code == 200) {
              this.open = false;
              this.getList();
              this.$message.success(response.msg);
            } else {
              this.$message(response.msg);
            }
          });
        }
      });

    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.reset();
      this.handleQuery();
    },
    getList () {      
      this.loading = true;
      get_yuntaiList(this.queryParams).then(response => {
        if(response.code == 200 && response.rows) {
          this.ledgerlist = response.rows;
          this.total = response.total;
          this.loading = false;
        }          
      });
    },
    handleUpdate(item) {
      // 操作
      this.open = true;
      this.$nextTick(()=>{
        this.$refs['form'].resetFields();
      });
      
      this.thisEditData = {
        factory: item.factory?this.dicts.oem.obj[item.factory]&&this.dicts.oem.obj[item.factory].dictLabel:'', // 设备厂商
        dutyPhone: item.dutyPhone, // 责任人电话
        modifyUser: item.modifyUser, //责任人
        lineName: item.lineName, // 所属线路
        code: item.code, // 设备编号 
        yuntaiId: item.id // 云台ID
      }
    },
    filterHandler(value, row, column) {
      // 表格状态筛选事件
      const property = column['property'];
      return row[property] == value;
    },
     /** 导出按钮操作  */
    handleExport () {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有云台数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return export_yuntai(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    checkAllv(val,item) {
        // 全选
        let cc = []
        for(let key in item.list) {
            cc.push(item.list[key].dictValue)
        }
        item.checkedEquipments = val ? cc : [];
        item.isIndeterminate = false;
        let key = item.keyP;
        this.queryParams[key] = undefined;
    },
    handleCheckAllChange(val,item) {
        // 全选
        this.checkAllv(val,item);        
    },
    handleCheckedCitiesChange(val,item) {
        // 单个checkbox
        let checkedCount = val.length;
        item.checkAll = checkedCount === Object.keys(item.list).length;
        item.isIndeterminate = checkedCount > 0 && checkedCount < item.list.length;
        if(item.checkedEquipments.length>0) {
          let key2 = item.keyP;
          if(item.checkedEquipments.length == Object.keys(item.list).length) {
            this.queryParams[key2] = undefined;
          } else {
            this.queryParams[key2] = item.checkedEquipments.join(",");
          }
        } else {
          this.queryParams[item.keyP] = undefined;
        }
    },
    // 取消按钮
    cancel() {
      this.open = false;
    },
    defectRecordClick(item) {
      // 查看消缺记录
      this.open2 = true;
      get_breakdownList({yuntaiId: item.id}).then(response => {
        if(response.code == 200) {
          this.breaklist = response.rows;
        }          
      });
    },
    handleUpdat2e() {
      // 提交消缺记录
      // edit_breakdownInfo
    },
    handleDelete2() {
      // 删除消缺记录
      // del_breakdownInfo
    }
  }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card,.el-card__body{height:100%;}}  
  .app-li{
    h4{margin:0;color:$base-color-black;}
    .typelist{
      .item+.item{margin-top:3px;}
      .item{display:flex;
          h4{width:80px;padding-top:4px;}
          .checkboxs{flex:1;overflow:hidden;position: relative;
              .checkall{position:absolute;}
              .el-checkbox-group{
                  .el-checkbox:first-child{margin-left:60px;}
              }
              ::v-deep{.el-checkbox{padding:4px 0;margin-right:15px;.el-checkbox__label{font-size:13px;}}}
          }
      }
    }
  }
  .mb8{padding-top:$base-padding;}
}
</style>