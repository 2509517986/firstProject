<template>
  <el-card class="app-container">
  <!--云台数据-->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="110px"  size="mini">
      <el-form-item label="内容关键字搜索" prop="keyword">
        <el-input  v-model="queryParams.keyword" placeholder="请输入云台编号/云台名称" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>   
      <el-form-item label="线路" prop="lineId">
        <el-select v-model="queryParams.lineId" filterable placeholder="请选择" size="mini">
          <el-option v-for="item in lineList" :key="item.id" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>       
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus"  size="mini"  @click="handleAdd">新增</el-button>
      </el-col>   
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"  @click="handleUpdate"  v-hasPermi="['service:yuntai:edit']">修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button  type="danger" plain  icon="el-icon-delete"  size="mini"  :disabled="multiple"  @click="handleDelete" >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain  icon="el-icon-download" size="mini" @click="handleExport" >导出全部数据</el-button>     
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="init" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="yuntaiList"  :height="tableHeight"  @selection-change="handleSelectionChange" >
    <el-table-column type="selection" width="50" align="center" />
    <el-table-column label="云台名称" align="center" key="name" prop="name" v-if="columns[0].visible" :show-overflow-tooltip="true" />
    <el-table-column label="设备ID" align="center" key="code" prop="code" v-if="columns[1].visible" :show-overflow-tooltip="true" />
    <el-table-column label="运行单位" align="center" key="unit" prop="unit" v-if="columns[2].visible">
      <template slot-scope="scope">
        {{scope.row.unit?dicts.sys_company.obj[scope.row.unit]&&dicts.sys_company.obj[scope.row.unit].dictLabel:''}}
      </template>
    </el-table-column>
    <el-table-column label="电压等级" align="center" key="level" prop="level" v-if="columns[3].visible" :show-overflow-tooltip="true" >
      <template slot-scope="scope">
        {{scope.row.level?dicts.line_voltage.obj[scope.row.level]&&dicts.line_voltage.obj[scope.row.level].dictLabel:''}}
      </template>
    </el-table-column>
    <el-table-column label="所属线路" align="center" key="lineName" prop="lineName" v-if="columns[4].visible" :show-overflow-tooltip="true" />
    <el-table-column label="所属杆塔" align="center" key="towerName" prop="towerName" v-if="columns[5].visible" :show-overflow-tooltip="true" />
    <el-table-column label="安装方向" align="center" key="direction" prop="direction" v-if="columns[6].visible" :show-overflow-tooltip="true"/> 
    <el-table-column label="接入方式" align="center" key="intoType" prop="intoType" v-if="columns[7].visible" :show-overflow-tooltip="true">
      <template slot-scope="scope">
        {{scope.row.intoType?dicts.access_type.obj[scope.row.intoType]&&dicts.access_type.obj[scope.row.intoType].dictLabel:''}}
      </template>
    </el-table-column> 
    <el-table-column label="VPN专网卡号" align="center" key="cardNum" prop="cardNum" v-if="columns[8].visible" :show-overflow-tooltip="true" />
    <el-table-column label="VPN专网卡号2" align="center" key="cardNum2" prop="cardNum2" v-if="columns[9].visible" :show-overflow-tooltip="true" />
    <el-table-column label="网络类型" align="center" key="network" prop="network" v-if="columns[10].visible" :show-overflow-tooltip="true">
      <template slot-scope="scope">
        {{scope.row.network?dicts.network_type.obj[scope.row.network]&&dicts.network_type.obj[scope.row.network].dictLabel:''}}
      </template>
    </el-table-column>   
    <el-table-column label="安装时间" align="center" key="installDate" prop="installDate" v-if="columns[11].visible" :show-overflow-tooltip="true" />
    <el-table-column label="隐患类型" align="center" key="hiddenDanger" prop="hiddenDanger" v-if="columns[12].visible" :show-overflow-tooltip="true">
      <template slot-scope="scope">
        {{scope.row.hiddenDanger?dicts.danger_type.obj[scope.row.hiddenDanger]&&dicts.danger_type.obj[scope.row.hiddenDanger].dictLabel:''}}
      </template>
    </el-table-column>  
    <el-table-column label="隐患详情" align="center" key="hiddenDangerDes" prop="hiddenDangerDes" v-if="columns[13].visible" :show-overflow-tooltip="true" />
    <el-table-column label="运维班组" align="center" key="deptName" prop="deptName" v-if="columns[14].visible" :show-overflow-tooltip="true" />
    <el-table-column label="设备厂商" align="center" key="factory" prop="factory" v-if="columns[15].visible" :show-overflow-tooltip="true">
      <template slot-scope="scope">
        {{scope.row.factory?dicts.oem.obj[scope.row.factory]&&dicts.oem.obj[scope.row.factory].dictLabel:''}}
      </template>
    </el-table-column>
    <el-table-column label="监测类型" align="center" key="monitorType" prop="monitorType" v-if="columns[16].visible" :show-overflow-tooltip="true">
      <template slot-scope="scope">
        {{scope.row.monitorType?dicts.monitor_type.obj[scope.row.monitorType]&&dicts.monitor_type.obj[scope.row.monitorType].dictLabel:''}}
      </template>
    </el-table-column>
    <el-table-column label="上图状态" align="center" key="imageStatus" prop="imageStatus" v-if="columns[17].visible" :show-overflow-tooltip="true">
      <template slot-scope="scope">
        {{scope.row.imageStatus&&dicts.image_status.obj[scope.row.imageStatus]?dicts.image_status.obj[scope.row.imageStatus]&&dicts.image_status.obj[scope.row.imageStatus].dictLabel:''}}
      </template>
    </el-table-column>
    <el-table-column label="消缺记录" align="center" key="defectRecord" prop="defectRecord" v-if="columns[18].visible" :show-overflow-tooltip="true" />
    <el-table-column label="设备电量(%)" align="center" key="electricPower" prop="electricPower" v-if="columns[19].visible" :show-overflow-tooltip="true" />
    <el-table-column label="设备IP" align="center" key="ip" prop="ip" v-if="columns[20].visible" :show-overflow-tooltip="true" />
    <el-table-column label="在线状态" :filters="dicts.state.arry" :filter-method="filterHandler" align="center" key="state" prop="state" v-if="columns[21].visible" :show-overflow-tooltip="true">
      <template slot-scope="scope">
        {{scope.row.state?dicts.state.obj[scope.row.state]&&dicts.state.obj[scope.row.state].dictLabel:''}}
      </template>
    </el-table-column>
    <el-table-column label="在线/离线时间" align="center" key="onlineTime" prop="onlineTime" v-if="columns[22].visible" :show-overflow-tooltip="true" />
    <!-- <el-table-column label="设备在线/离线时长" align="center" key="onlineDuration" prop="onlineDuration" v-if="columns[23].visible" :show-overflow-tooltip="true" /> -->
    <el-table-column label="软件版本" align="center" key="version" prop="version" v-if="columns[23].visible" :show-overflow-tooltip="true" />
    <el-table-column label="软件更新时间" align="center" key="versionUpdate" prop="versionUpdate" v-if="columns[24].visible" :show-overflow-tooltip="true" />
    <!-- <el-table-column label="固件版本" align="center" key="version2" prop="version2" v-if="columns[26].visible" :show-overflow-tooltip="true" /> -->
      <el-table-column label="操作" align="center" width="160"  class-name="small-padding fixed-width">
        <template slot-scope="scope">
            <el-button size="mini"  type="text"  icon="el-icon-edit"  @click="handleUpdate(scope.row)" >修改</el-button>
            <el-button size="mini" type="text" icon="el-icon-delete"  @click="handleDelete(scope.row)" >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />


    <!-- 添加或修改参数配置对话框 -->
     <el-dialog :title="title" :visible.sync="open" width="1100px" append-to-body :destroy-on-close="true" @open="opendialog">
      <el-row :gutter="20"   v-if="open">
          <el-col :span="13">
              <el-form ref="form" :model="form" :rules="rules" label-width="130px" class="dialogform">
                <el-row :gutter="20">
                  <el-col :span="12">
                      <el-form-item label="设备ID" prop="code">
                          <el-input v-model="form.code" placeholder="请输入设备ID" size="small" :disabled="codeDisabled"/>
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="云台名称" prop="name">
                        <el-input v-model="form.name" placeholder="请输入云台名称"  size="small"/>
                      </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="12">
                      <el-form-item label="运维班组" prop="deptId">
                        <treeselect @select="treeSelectFun" @input="inputChange" v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择运维班组"  size="small"/>
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="所属线路" prop="lineId">
                        <el-select :value="form.lineId" filterable placeholder="请选择" size="small" @change="lineChange">
                            <el-option v-for="item in lineList" :key="item.id" :label="item.name" :value="item.id"></el-option>
                        </el-select>
                      </el-form-item>
                  </el-col>               
                </el-row>
                <el-row :gutter="20">
                <el-col :span="12">
                  <el-form-item label="杆塔名称" prop="towerId">
                      <el-select v-model="form.towerId" filterable placeholder="请选择" size="small" @change="querySearch">
                          <el-option v-for="item in towerList" :key="item.id" :label="item.name" :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
                 <el-col :span="12">
                    <el-form-item label="运行单位" prop="unit">
                      <el-select v-model="form.unit" placeholder="请选择" filterable size="small">
                        <el-option v-for="(item, key) in dicts.sys_company.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>               
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="12">
                    <el-form-item label="高压等级" prop="level">
                      <el-select v-model="form.level" placeholder="请选择" size="small">
                        <el-option v-for="(item, key) in dicts.line_voltage.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                <el-col :span="12">
                    <el-form-item label="安装方向" prop="direction">
                       <el-input v-model="form.direction" placeholder="请输入安装方向"  size="small"/>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row :gutter="20">
                   <el-col :span="12">
                    <el-form-item label="接入方式" prop="intoType">
                      <el-select v-model="form.intoType" placeholder="请选择" size="small">
                        <el-option v-for="(item, key) in dicts.access_type.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                   <el-form-item label="网络类型" prop="network">
                       <el-select v-model="form.network" placeholder="请选择" size="small">
                          <el-option v-for="(item, key) in dicts.network_type.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row :gutter="20">
                   <el-col :span="12">
                    <el-form-item label="VPN专网卡号" prop="cardNum">
                      <el-input v-model="form.cardNum" placeholder="请输入VPN专网卡号" size="small"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="VPN专网卡号2" prop="cardNum2">
                      <el-input v-model="form.cardNum2" placeholder="请输入VPN专网卡号2" size="small"/>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row :gutter="20">
                   <el-col :span="12">
                    <el-form-item label="安装状态" prop="installState">
                      <el-select v-model="form.installState" placeholder="请选择" size="small">
                          <el-option v-for="(item, key) in dicts.install_state.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                   <el-form-item label="安装时间" prop="installDate">
                      <el-date-picker v-model="form.installDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="date" placeholder="选择日期" size="small"></el-date-picker>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row :gutter="20">
                   <el-col :span="12">
                    <el-form-item label="隐患类型" prop="hiddenDanger">
                      <el-select v-model="form.hiddenDanger" placeholder="请选择" size="small">
                          <el-option v-for="(item, key) in dicts.danger_type.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="隐患详情" prop="hiddenDangerDes">
                      <el-input v-model="form.hiddenDangerDes" placeholder="请输入隐患详情" size="small"/>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row :gutter="20">
                   <el-col :span="12">
                    <el-form-item label="设备厂商" prop="factory">
                      <el-select v-model="form.factory" placeholder="请选择" size="small">
                          <el-option v-for="(item, key) in dicts.oem.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="监测类型" prop="monitorType">
                     <el-select v-model="form.monitorType" placeholder="请选择" size="small">
                          <el-option v-for="(item, key) in dicts.monitor_type.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row :gutter="20">                  
                   <el-col :span="12">
                       <el-form-item :label="'设备密码\n(南网协议传输密码)'" prop="pasword" class="otherP">
                        <el-input v-model="form.pasword" size="small" show-password placeholder="请输入密码"/>
                      </el-form-item>
                  </el-col>
                <el-col :span="12">
                    <el-form-item label="缺销记录" prop="defectRecord">
                     <el-input v-model="form.defectRecord" placeholder="请输入缺销记录"  size="small"/>
                    </el-form-item>
                </el-col>
                </el-row>    
                <el-row :gutter="20" v-if="dialogType == 0">                  
                   <el-col :span="12">
                    <el-form-item label="上图状态" prop="imageStatus">
                      <el-input :value="form.imageStatus&&dicts.image_status.obj[form.imageStatus]?dicts.image_status.obj[form.imageStatus].dictLabel:'暂无'" size="small" disabled/>
                    </el-form-item>
                </el-col>
                  <el-col :span="12">
                      <el-form-item label="在线状态" prop="state">
                        <el-input :value="form.state?dicts.state.obj[form.state].dictLabel:'暂无'" size="small" disabled/>
                      </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20" v-if="dialogType == 0">
                   <el-col :span="12">
                      <el-form-item label="在线/离线时间" prop="onlineTime">
                        <el-input :value="form.onlineTime?form.onlineTime:'暂无'" size="small" disabled/>
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                        <el-form-item label="电量(%)" prop="electricPower">
                        <el-input :value="form.electricPower?form.electricPower:'暂无'" size="small" disabled/>
                      </el-form-item>
                  </el-col>
                </el-row>      
                <el-row :gutter="20" v-if="dialogType == 0">
                   <el-col :span="12">
                      <el-form-item label="软件版本" prop="version">
                        <el-input :value="form.version?form.version:'暂无'" size="small" disabled/>
                      </el-form-item>
                  </el-col>
                  <el-col :span="12">
                      <el-form-item label="软件更新时间" prop="versionUpdate">
                        <el-input :value="form.versionUpdate?form.versionUpdate:'暂无'" size="small" disabled/>
                      </el-form-item>
                  </el-col>
                </el-row>                    
               <el-row :gutter="20">
                 <el-col :span="12">
                    <el-form-item label="经度" prop="longitude">
                      <el-input v-model="form.longitude" :disabled="true" placeholder="请点击地图获取经度"  size="small"/>
                    </el-form-item>
                </el-col>
                 <el-col :span="12">
                    <el-form-item label="维度" prop="latitude">
                      <el-input v-model="form.latitude" :disabled="true" placeholder="请点击地图获取维度"  size="small"/>
                    </el-form-item>
                </el-col>
                </el-row> 
                <el-row :gutter="20">
                  <el-col :span="24">
                     <el-form-item label="详情地址" prop="address">
                      <el-input v-model="form.address"  :disabled="true" placeholder="请输入详细地址"  size="small"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="24">
                    <el-form-item label="图像" prop="image">
                      <div class="uploadimg">
                        <div class="img">
                          <el-image :src="form.image" fit="cover" class="elimage">
                            <div slot="error" class="image-slot" v-if="!form.fileName">
                              <i class="el-icon-picture-outline"></i>
                            </div>
                          </el-image>
                          <div class="mask" v-if="form.image"  @click="dialogVisible = true" >
                            <i class="el-icon-zoom-in"></i>
                          </div>
                        </div>
                        <el-upload class="avatar-uploader" action="" :http-request="Upload" :show-file-list="false" :before-upload="beforeAvatarUpload">
                          <el-button size="small" type="primary">点击上传</el-button>
                          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2M</div>
                        </el-upload>
                      </div>
                      <el-dialog :visible.sync="dialogVisible" :append-to-body="true">
                        <img width="100%" :src="form.image" alt="">
                      </el-dialog>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="24">
                      <el-form-item label="备注">
                        <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"   size="small"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>               
            </el-form>
          </el-col>
          <el-col :span="11">
              <!-- 地图 -->              
              <map-com v-if="openmap" @list="pushdata" :point="point" :type="false" :deviceType="'yuntai'" :monitorType="form.monitorType"></map-com>
          </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" size="mini">确 定</el-button>
        <el-button @click="cancel" size="mini">取 消</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import {get_lineList} from "@/api/senxu/devicemanager/line";
import { mapGetters } from 'vuex'
import { get_tower_detail,get_towerList } from "@/api/senxu/devicemanager/tower";
import { add_yuntai,edit_yuntai,get_yuntai_detail,del_yuntai,get_yuntaiList,export_yuntai } from "@/api/senxu/devicemanager/yuntai";
import { uploadFile } from "@/api/senxu/common/uploadfile";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import * as global  from "@/utils/global";


var checkoutIP= (rule,value,callback)=>{
  const ipRules = /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/
  if(!ipRules.test(value)){callback(new Error('ip不合法'))
  }else{
      callback()
  }
};

export default {
  name: "yuntaiManagement",
  components: { Treeselect },
  data() {
    return {
      codeDisabled: true, // 修改云台，设备编号不能修改
      dialogVisible: false, // 图片放大
      dialogType: 0, // 0修改，1新增
      point: [],
      // 遮罩层
      loading: true,
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
      // 云台表格数据
      yuntaiList: null,
      // 弹出层标题
      title: "",
      // 状态数据字典
      statusOptions: {},
      // 电压等级数据字典
      levelOptions: {},
      // 运行单位
      companyOptions: {},
      // 接入方式
      accessOptions: {},
      // 网络类型
      networkOptions: {},
      // 隐患类型
      dangerOptions: {},
      // 上图状态
      imgStatusOptions: {},
      // 安装状态
      installOptions: {},
      // 监测类型
      monitorOptions: {},
      // 设备厂商
      oemOptions: {},
      // 所属线路
      lineList: [],
      // 部门树选项
      deptOptions: [],
      // 是否显示弹出层
      open: false,
      // 是否显示地图
      openmap: false,
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: undefined,
        lineId: undefined
      },
      deptName: '', // 部门
      lineName: '', // 线路名称
      towerName: '', // 杆塔名称
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
        // { key: 23, label: `设备在线/离线时长`, visible: true },
        { key: 23, label: `当前版本信息`, visible: true },
        { key: 24, label: `软件更新时间`, visible: true },
        // { key: 26, label: `固件版本`, visible: true }
      ],
      // 杆塔列表
      towerList: [],
      // 表单校验
      rules: {
        unit: [
          { required: true, message: "运行单位不能为空", trigger: "blur" }
        ],
        level: [
          { required: true, message: "电压等级不能为空", trigger: "change" }
        ],
        lineId: [
          { required: true, message: "所属线路不能为空", trigger: "change" }
        ],
        towerId: [
          { required: true, message: "所属杆塔不能为空", trigger: "change" }
        ],
        direction: [
          { required: true, message: "安装方向不能为空", trigger: "blur" }
        ],
        intoType: [
          { required: true, message: "接入方式不能为空", trigger: "change" }
        ],
        cardNum: [
          { required: true, message: "VPN专网卡号不能为空", trigger: "blur" }
        ],
        cardNum2: [
          { required: true, message: "VPN专网卡号2不能为空", trigger: "blur" }
        ],
        network: [
          { required: true, message: "网络类型不能为空", trigger: "change" }
        ],
        code: [
          { required: true, message: "设备ID不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "设备名称不能为空", trigger: "blur" }
        ],
        installState: [
          { required: true, message: "安装状态不能为空", trigger: "change" }
        ],
        installDate: [
          { required: true, message: "安装时间不能为空", trigger: "change" }
        ],
        hiddenDanger: [
          { required: true, message: "隐患类型不能为空", trigger: "change" }
        ],
        hiddenDangerDes: [
          { required: true, message: "隐患详情不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "运维班组不能为空", trigger: "input" }
        ],
        factory: [
          { required: true, message: "设备厂商不能为空", trigger: "change" }
        ],
        monitorType: [
          { required: true, message: "监测类型不能为空", trigger: "change" }
        ],
        defectRecord: [
          { required: true, message: "消缺记录不能为空", trigger: "blur" }
        ],
        // ip: [
        //  { required: true, message: 'IP不能为空', trigger: 'blur' }, { validator: checkoutIP, trigger: 'change' }
        // ],
        pasword: [
         { required: true, message: '密码', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.init();
  },
  computed: {
    ...mapGetters([
      'dicts',
    ]),
    tableHeight () {
      return window.innerHeight - 350;
    },
    domain () {
      return global.DOMAIN;
    }
  },
  methods: {
    slowl(key, data, name) {
      if(Object.keys(this.dicts[key].obj).length>0 && data){
        for(let k in this.dicts[key].obj) {
          if(data == k) {
            this.form[name] = data;
            return;
          } else {
            this.form[name] =  undefined;
          }
        }
      }
    },
    opendialog() {
      // 打开dialog回调// 字典
      this.$store.dispatch("SetDicts").then(() => {}).catch(() => {});
    },
    init() {
      this.getTreeselect();
      // 线路
      get_lineList().then(response => {
        this.lineList = response.rows;
      }); 
      this.getList();
    },
    pushdata (data) {
      this.form.address = data.address;
      this.form.longitude = data.point.lng;
      this.form.latitude = data.point.lat;
    },
    /** 查询云台列表 */
    getList() {
      this.loading = true;
      get_yuntaiList(this.queryParams).then(response => {
          this.yuntaiList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    querySearch(id) {
      // 塔杆名称筛选
      if(this.towerList.length<1) {return;}
      let restaurants = this.towerList.filter((item) => {
        return item.id == id;
      });
      this.towerName = restaurants[0].name;
      this.openmap = false;      
      this.getTowerDetail(id); 
    },
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.name.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
      };
    },
    handleSelect(item) {
      // 选中塔杆
      this.towerName = item.name;
    },
    inputChange() {     
      this.$refs['form'].validateField('deptId');
    },
    treeSelectFun(item) {
      // 部门树
      this.deptName = item.label;      
    },
    normalizer(node){
      //去掉部门树，children=null的属性
      if(node.children==null||node.children=='null'){
        delete node.children;
      }
    },
    lineChange(id) {
      // 线路名称
      this.form.lineId = id;
      for(let i =0;i<this.lineList.length;i++) {
        if(this.lineList[i].id == id) {
          this.lineName = this.lineList[i].name;
        }
      }
      // 杆塔
      get_towerList({lineId:id}).then(response => {
        if(response.code == 200) {
          this.towerList = response.rows;
          this.form.towerId = response.rows && response.rows.length>0?response.rows[0].id:undefined;
          if(this.form.towerId) {
            this.getTowerDetail(this.form.towerId); 
          } else {
            this.$message("该线路下暂无杆塔！");
          }
        } 
      }); 
    },
    getTowerDetail(id) {  
      // 获得当前杆塔详情    
        this.openmap = false;  
        get_tower_detail(id).then(response => {
          this.form.address = response.data&& response.data.address?response.data.address:undefined;
          this.form.longitude = response.data && response.data.longitude?response.data.longitude:undefined;
          this.form.latitude = response.data && response.data.latitude?response.data.latitude:undefined;
          this.point = [response.data.longitude,response.data.latitude];        
          this.openmap = true;
        });
    },
    filterHandler(value, row, column) {
      // 表格状态筛选事件
      const property = column['property'];
      return row[property] == value;
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.deptOptions = response.data;
      });
    },  
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.idsname = selection.map(item => item.name);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.openmap = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        name: undefined,
        pasword: undefined,
        unit: undefined,
        level: undefined,
        lineId: undefined,
        towerId: undefined,
        direction: undefined,
        intoType: undefined,
        cardNum: undefined,
        cardNum2: undefined,
        network: undefined,
        code: undefined,
        installState: undefined,
        installDate: undefined,
        hiddenDanger: undefined,
        image: undefined,
        hiddenDangerDes: undefined,
        deptId: undefined,
        factory: undefined,
        monitorType: undefined,
        defectRecord: undefined,
        // ip: undefined,
        longitude: undefined, 
        latitude: undefined,
        address: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
        this.reset();        
        this.point = [];
        this.dialogType = 1;
        this.codeDisabled = false;
        this.open = true;
        this.openmap = true;
        this.title = "添加云台";   
        this.$nextTick(()=>{
          this.$refs.form.resetFields();
        });
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.dialogType = 0;
      const id = row.id || this.ids;     
      get_yuntai_detail(id).then(response => {  
        this.codeDisabled = true;
        this.point[0] = response.data.longitude;
        this.point[1] = response.data.latitude;
        response.data.state = response.data.state?response.data.state.toString():'';
        response.data.intoType = response.data.intoType?response.data.intoType.toString():'';
        this.form = response.data;
        this.lineName = response.data.name;
        // 匹配 字典删除后
        this.slowl('oem', response.data.factory, 'factory');
        this.slowl('sys_company', response.data.unit, 'unit');
        this.slowl('access_type', response.data.intoType, 'intoType');
        this.slowl('line_voltage', response.data.level, 'level');
        this.slowl('network_type', response.data.network, 'network');
        this.slowl('install_state', response.data.installState, 'installState');
        this.slowl('danger_type', response.data.hiddenDanger, 'hiddenDanger');
        this.slowl('monitor_type', response.data.monitorType, 'monitorType');
        this.open = true;
        this.openmap = true;
        this.title = "修改";            
        this.$nextTick(()=>{
          this.$refs.form.resetFields();
        });    
        // 杆塔
        get_towerList({lineId:response.data.lineId}).then(response => {
          this.towerList = response.rows;       
        });
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        this.form.deptName = this.deptName;
        this.form.lineName = this.lineName;
        this.form.towerName = this.towerName;
        if (valid) {
          if (this.form.id != undefined) {
            edit_yuntai(this.form).then(response => {
              if(response.code == 200) {
                this.msgSuccess(response.msg);
                this.open = false;
                this.openmap = false;
                this.getList();
              } else {
                this.$message(response.msg);
                return;
              }
            });
          } else {
            add_yuntai(this.form).then(response => {
              if(response.code == 200) {
                this.msgSuccess(response.msg);
                this.open = false;
                this.openmap = false;
                this.getList();
              } else {
                this.$message(response.msg);
                return;
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id || this.ids;
      const idsname = row.name || this.idsname;
      this.$confirm('是否确认删除云台名称为"' + idsname + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return del_yuntai(id);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(()=>{});
    },
    Upload(item) {
      let formData= new FormData();
      formData.append('file', item.file);
      uploadFile(formData, 'yuntai').then(response => {
        this.form.image =  response.fileName;
      });
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg';
      const testmsg = /^image\/(jpeg|png|jpg)$/.test(file.type)
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!testmsg) {
        this.$message.error('上传图片格式不对!');
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
      }
      return testmsg && isLt2M;
    }
  }
};
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;}
.uploadimg{ display: flex; align-items: center;
  .img{position:relative;color: rgb(144, 147, 153);background: rgb(245, 247, 250);border: 1px dashed #d9d9d9;border-radius: 6px;cursor: pointer;position: relative;overflow: hidden;width:80px;height: 80px;display: flex;align-items:center;justify-content: center;
    i{font-size:20px;}
    .elimage{display:flex;justify-content: center; align-items: center;width:100%;height:100%;}
    &:hover .mask{display:flex;}
    .mask{position: absolute;top:0;right:0;bottom:0;left:0;background:rgba(0,0,0,0.5);justify-content: center;display:none; align-items: center;
      i{color:#fff;}
    }
  }
  .avatar-uploader{padding-left:20px;}
}
::v-deep{  
  .el-card__body{height:100%;display: flex; flex-direction: column;}
  .el-select{width:100%;}
  .el-date-editor{width:100%;}
  .dialogform{height:400px;overflow-x: hidden;overflow-y: auto;}
  .el-input-number__decrease,.el-input-number__increase{display: none;}
  .el-input-number .el-input__inner{padding:0 15px;text-align: left;}
  .el-input-number{width:100%!important;}
  .el-table{flex:1;}
  .otherP label{line-height:18px;}
}
</style>