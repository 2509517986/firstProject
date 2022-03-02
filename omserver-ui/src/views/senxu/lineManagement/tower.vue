<template>
  <el-card class="app-container">
      <!--杆塔数据-->
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="110px"  size="mini">
          <el-form-item label="内容关键字搜索" prop="keyword">
            <el-input v-model="queryParams.keyword" placeholder="请输入杆塔编号/杆塔名称"  clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery"/>
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
            <el-button type="primary" plain icon="el-icon-plus"  size="mini"  @click="handleAdd" >新增</el-button>
          </el-col>   
          <!-- <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"  @click="handleUpdate"  v-hasPermi="['service:tower:edit']">修改</el-button>
          </el-col> -->
          <el-col :span="1.5">
            <el-button  type="danger" plain  icon="el-icon-delete"  size="mini"  :disabled="multiple"  @click="handleDelete" >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain  icon="el-icon-download" size="mini" @click="handleExport" >导出全部数据</el-button>     
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="init" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="towerList" :height="tableHeight"   @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="杆塔编号" align="center" key="code" prop="code" v-if="columns[0].visible" />
          <el-table-column label="杆塔名称" align="center" key="name" prop="name" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="管辖部门" align="center" key="deptName" prop="deptName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="所属线路" align="center" key="lineName" prop="lineName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="杆塔类型" align="center" key="kind" prop="kind" v-if="columns[4].visible" :show-overflow-tooltip="true" >
            <template slot-scope="scope">
              {{scope.row.kind?dicts.tower_type.obj[scope.row.kind]&&dicts.tower_type.obj[scope.row.kind].dictLabel:''}}
            </template>
          </el-table-column>
          <el-table-column label="杆塔形状" align="center" key="shape" prop="shape" v-if="columns[5].visible" :show-overflow-tooltip="true" >
            <template slot-scope="scope">
              {{scope.row.shape?dicts.tower_shape.obj[scope.row.shape]&&dicts.tower_shape.obj[scope.row.shape].dictLabel:''}}
            </template>
          </el-table-column>
          <el-table-column label="塔高(m)" align="center" key="high" prop="high" v-if="columns[6].visible" :show-overflow-tooltip="true" />
          <el-table-column label="占地面积(m)" align="center" key="area" prop="area" v-if="columns[7].visible" :show-overflow-tooltip="true" />
          <el-table-column label="建成时间" align="center" key="completeDate" prop="completeDate" v-if="columns[8].visible" :show-overflow-tooltip="true" />
          <el-table-column label="状态"  :filters="dicts.sys_state.arry" :filter-method="filterHandler" align="center" key="state" prop="state" v-if="columns[9].visible" :show-overflow-tooltip="true" >
            <template slot-scope="scope">
              {{scope.row.state?dicts.sys_state.obj[scope.row.state]&&dicts.sys_state.obj[scope.row.state].dictLabel:''}}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="120"  class-name="small-padding fixed-width">
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
            <el-form ref="form" :model="form" :rules="rules" label-width="100px" class="dialogform">
              <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="杆塔编号" prop="code">
                      <el-input v-model="form.code" placeholder="请输入杆塔编号" size="small"/>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="杆塔名称" prop="name">
                    <el-input v-model="form.name" placeholder="请输入杆塔名称"  size="small"/>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="管辖部门" prop="deptId">
                      <treeselect size="small"  @select="treeSelectFun" @input="inputChange" v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择管辖部门" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="所属线路" prop="lineId">
                      <el-select v-model="form.lineId" filterable placeholder="请选择" size="small" @change="lineChange">
                          <el-option v-for="item in lineList" :key="item.id" :label="item.name" :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>               
              </el-row>
              <el-row :gutter="20">
                <el-col :span="12">
                    <el-form-item label="杆塔类型" prop="kind">
                      <el-select v-model="form.kind" placeholder="请选择" filterable size="small">
                        <el-option v-for="(item, key) in dicts.tower_type.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>
                 <el-col :span="12">
                    <el-form-item label="杆塔形状" prop="shape">
                      <el-select v-model="form.shape" placeholder="请选择" filterable size="small">
                        <el-option v-for="(item, key) in dicts.tower_shape.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col> 
              </el-row>
                <el-row :gutter="20">
                 <el-col :span="12">
                    <el-form-item label="安装点分类" prop="installType">
                      <el-select v-model="form.installType" multiple placeholder="请选择" filterable size="small">
                        <el-option v-for="(item, key) in dicts.install_type.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col> 
                 <el-col :span="12">
                    <el-form-item label="场景点分类" prop="sceneType">
                      <el-select v-model="form.sceneType" multiple placeholder="请选择" filterable size="small">
                        <el-option v-for="(item, key) in dicts.scene_type.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
                    </el-form-item>
                </el-col>               
                </el-row>
                <el-row :gutter="20">
                   <el-col :span="12">
                    <el-form-item label="塔高(m)" prop="high">
                      <el-input-number v-model="form.high" controls-position="right" :min="0" placeholder="请输入塔高"  size="small" ></el-input-number>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="占地面积(m)" prop="area">
                      <el-input-number v-model="form.area" controls-position="right" :min="0" placeholder="请输入占地面积"  size="small" ></el-input-number>
                    </el-form-item>
                </el-col>
                </el-row>
                <el-row :gutter="20">
                <el-col :span="12">
                  <el-form-item label="建成时间" prop="completeDate">
                    <el-date-picker v-model="form.completeDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="date" placeholder="选择日期" size="small"></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="状态" prop="state">
                      <el-select v-model="form.state" placeholder="请选择" size="small">
                        <el-option v-for="(item, key) in dicts.sys_state.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
                      </el-select>
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
                      <!-- <el-input v-model="form.address" placeholder="请输入详细地址"  size="small"> 
                        <template slot="prepend">{{mapPointData.address}}</template>
                      </el-input> -->
                      <el-input v-model="form.address" placeholder="请输入详细地址"  size="small"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :span="24">
                    <el-form-item label="图像" prop="image">
                      <div class="uploadimg">
                        <div class="img">
                          <el-image :src="form.image" fit="cover" class="elimage">
                            <div slot="error" class="image-slot" v-if="!form.image">
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
                        <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" size="small"></el-input>
                      </el-form-item>
                    </el-col>
                </el-row>               
            </el-form>
          </el-col>
          <el-col :span="11">
              <!-- 地图 -->              
              <map-com v-if="open" @list="pushdata" :point="point" :type="true" :deviceType="'tower'"></map-com>
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
import { add_tower,edit_tower,get_tower_detail,del_tower,get_towerList,export_tower } from "@/api/senxu/devicemanager/tower";
import { uploadFile } from "@/api/senxu/common/uploadfile";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import { mapGetters } from 'vuex'
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import * as global  from "@/utils/global";
export default {
  name: "towerManagement",
  components: { Treeselect },
  data() {
    return {
      dialogVisible: false, // 图片放大
      point: [],
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
      // 用户表格数据
      towerList: null,
      // 弹出层标题
      title: "",
      // 状态数据字典
      statusOptions: {},
      // 杆塔形状数据字典
      shapeOptions: {},
      // 杆塔类型数据字典
      kindOptions: {},
      // 所属线路
      lineList: [],
      // 部门树选项
      deptOptions: [],
      // 是否显示弹出层
      open: false,
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
      // 列信息
      columns: [
        { key: 0, label: `杆塔编号`, visible: true },
        { key: 1, label: `杆塔名称`, visible: true },
        { key: 2, label: `管辖部门`, visible: true },
        { key: 3, label: `所属线路`, visible: true },
        { key: 4, label: `杆塔类型`, visible: true },
        { key: 5, label: `杆塔形状`, visible: true },
        { key: 6, label: `塔高`, visible: true },
        { key: 7, label: `占地面积`, visible: true },
        { key: 8, label: `建成时间`, visible: true },
        { key: 9, label: `状态`, visible: true }
      ],
      // 表单校验
      rules: {
        code: [
          { required: true, message: "线路编号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "线路名称不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "管辖部门不能为空", trigger: "input"}
        ], 
        installType: [
          { required: true, message: "安装点分类不能为空", trigger: "change", type: "array"}
        ],
        sceneType: [
          { required: true, message: "场景点分类不能为空", trigger: "change", type: "array"}
        ],
        lineId: [
          { required: true, message: "所属线路不能为空", trigger: "change" }
        ],
        kind: [
          { required: true, message: "杆塔类型不能为空", trigger: "change" }
        ],
        shape: [
          { required: true, message: "杆塔形状不能为空", trigger: "change" }
        ], 
        high: [
          { required: true, message: "塔高不能为空", trigger: "blur" }
        ],
        area: [
          { required: true, message: "占地面积不能为空", trigger: "blur" }
        ], 
        state: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ],
        longitude: [
          { required: true, message: "经度不能为空", trigger: "change" }
        ], 
        latitude: [
          { required: true, message: "纬度不能为空", trigger: "change" }
        ],
        address: [
          { required: true, message: "详情地址不能为空", trigger: "blur" }
        ],
        completeDate: [
          { required: true, message: "建成时间不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.init();
  },
  mounted() {},
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
      this.getList();
      // 线路
      get_lineList().then(response => {
        this.lineList = response.rows;
      });
    },
    // 地图数据
    pushdata (data) {
      this.form.address = data.address;
      this.form.longitude = data.point.lng;
      this.form.latitude = data.point.lat;
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
        get_towerList(this.queryParams).then(response => {
          this.towerList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
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
      if(this.lineList.length>0) {
        for(let i =0;i<this.lineList.length;i++) {
          if(this.lineList[i].id == id) {
            this.lineName = this.lineList[i].name;
          }
        }
      }
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
      this.reset();
    },
     // 表单重置
    reset() {
      this.form = {
        code: undefined,
        name: undefined,
        deptId: undefined, 
        lineId: undefined,
        kind: undefined,
        shape: undefined,
        high: undefined, 
        area: undefined,
        state: undefined, 
        longitude: undefined,
        image: undefined,
        latitude: undefined,
        address: undefined,
        remark: undefined,
        installType: undefined,
        sceneType: undefined,
        completeDate: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.page = 1;
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
      this.open = true;
      this.title = "添加";      
      this.$nextTick(()=>{
        this.$refs.form.resetFields();
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {    
      const id = row.id || this.ids  
        this.form = {}
      get_tower_detail(id).then(response => {
        this.point[0] = response.data.longitude;
        this.point[1] = response.data.latitude;
        response.data.state = response.data.state?response.data.state.toString():'';
        this.form = response.data;
        this.form.installType = this.form.installType?this.form.installType.split(","):'';
        this.form.sceneType = this.form.sceneType?this.form.sceneType.split(","):'';
          // 匹配 字典删除后
        this.slowl('tower_type', response.data.kind, 'kind');
        this.slowl('tower_shape', response.data.shape, 'shape');
        this.lineName = response.data.name;
        this.open = true;
        this.title = "修改";        
        this.$nextTick(()=>{
          this.$refs.form.resetFields();
        });
      });
    },
     /** 导出按钮操作  */
    handleExport () {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有杆塔数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return export_tower(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          let params = {};
          let installType = "", sceneType = "";
          this.form.deptName = this.deptName;
          this.form.lineName = this.lineName;
          params = Object.assign({}, this.form);          
          installType = this.form.installType.join(',');
          sceneType = this.form.sceneType.join(',');
          params.installType = installType;
          params.sceneType = sceneType;
          if (this.form.id != undefined) {
            edit_tower(params).then(response => {
              if(response.code == 200) {
                this.msgSuccess(response.msg);
                this.open = false;
                this.getList();
              }
            });
          } else {
            add_tower(params).then(response => {
              if(response.code == 200) {
                this.msgSuccess(response.msg);
                this.open = false;
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
      this.$confirm('是否确认删除杆塔名称为"' + idsname + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return del_tower(id);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(()=>{});
    },
    Upload(item) {
      let formData= new FormData();
      formData.append('file', item.file);
      uploadFile(formData, 'tower').then(response => {
        this.form.image = response.fileName;
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
  .el-date-editor{width:100%;}
  .dialogform{height:400px;overflow-x: hidden;overflow-y: auto;}
  .el-input-number__decrease,.el-input-number__increase{display: none;}
  .el-input-number .el-input__inner{padding:0 15px;text-align: left;}
  .el-input-number{width:100%!important;}
  .el-table{flex:1;}
}
</style>