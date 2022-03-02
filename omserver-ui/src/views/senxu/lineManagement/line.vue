<template>
  <el-card class="app-container" v-if="dicts && Object.keys(dicts).length>0">
      <!--线路数据-->
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="110px"  size="mini">
          <el-form-item  size="mini" label="内容关键字搜索" prop="keyword">
            <el-input  v-model="queryParams.keyword"  placeholder="请输入线路编号/线路名称" clearable size="mini" style="width: 240px" @keyup.enter.native="handleQuery"/>
          </el-form-item>         
          <el-form-item  size="mini">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus"  size="mini"  @click="handleAdd">新增</el-button>
          </el-col>   
          <!-- <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single"  @click="handleUpdate"  v-hasPermi="['service:lineInfo:edit']">修改</el-button>
          </el-col> -->
          <el-col :span="1.5">
            <el-button  type="danger" plain  icon="el-icon-delete"  size="mini"  :disabled="multiple"  @click="handleDelete" >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain  icon="el-icon-download" size="mini" @click="handleExport" >导出全部数据</el-button>     
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="init" :columns="columns"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="lineList" :height="tableHeight"  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="线路编号" align="center" key="code" prop="code" v-if="columns[0].visible" />
          <el-table-column label="线路名称" align="center" key="name" prop="name" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="管辖部门" align="center" key="deptName" prop="deptName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="线高(m)" align="center" key="high" prop="high" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="传输电压" align="center" key="voltage" prop="voltage" v-if="columns[4].visible" :show-overflow-tooltip="true" />
          <el-table-column label="高压等级" align="center" key="level" prop="level" v-if="columns[5].visible" :show-overflow-tooltip="true" >
            <template slot-scope="scope">
              {{scope.row.level&&dicts.line_voltage&&dicts.line_voltage.obj[scope.row.level]?dicts.line_voltage.obj[scope.row.level].dictLabel:''}}
            </template>
          </el-table-column>
          <el-table-column label="全长(km)" align="center" key="length" prop="length" v-if="columns[6].visible" :show-overflow-tooltip="true" />
          <el-table-column label="建成时间" width="150" align="center" key="completeDate" prop="completeDate" v-if="columns[7].visible" :show-overflow-tooltip="true" />
          <el-table-column label="起点塔号" align="center" key="startPoint" prop="startPoint" v-if="columns[8].visible" :show-overflow-tooltip="true" />
          <el-table-column label="终点塔号" align="center" key="endPoint" prop="endPoint" v-if="columns[9].visible" :show-overflow-tooltip="true" />
          <el-table-column label="状态"  :filters="dicts.sys_state.arry" :filter-method="filterHandler" align="center" key="state" prop="state" v-if="columns[10].visible" :show-overflow-tooltip="true" >
            <template slot-scope="scope">
              {{scope.row.state&&dicts.sys_state.obj&&dicts.sys_state.obj[scope.row.state]?dicts.sys_state.obj[scope.row.state].dictLabel:''}}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="120"  class-name="small-padding fixed-width">
            <template slot-scope="scope">
                <!-- <el-button size="mini"  type="text"  icon="el-icon-edit"  @click="handleUpdate(scope.row)" v-hasPermi="['service:lineInfo:edit']">查看</el-button> -->
                <el-button size="mini" type="text"  icon="el-icon-edit"  @click="handleUpdate(scope.row)" >修改</el-button>
                <el-button size="mini" type="text" icon="el-icon-delete"  @click="handleDelete(scope.row)" >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :destroy-on-close="true" @open="opendialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px"  v-if="open" >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="线路编号" prop="code">
              <el-input v-model="form.code" placeholder="请输入线路编号" size="small"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="线路名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入线路名称"  size="small"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="管辖部门" prop="deptId">
              <treeselect @select="treeSelectFun" @input="inputChange" v-model="form.deptId" :normalizer="normalizer" :options="deptOptions" :show-count="true" placeholder="请选择管辖部门"  size="small"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="线高(m)" prop="high">
              <el-input-number v-model="form.high" controls-position="right" :min="0" placeholder="请输入线高值"  size="small" ></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="传输电压(kV)" prop="voltage">
              <el-input v-model="form.voltage" min="0" placeholder="请输入传输电压" size="small"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="高压等级" prop="level">
              <el-select v-model="form.level" placeholder="请选择" size="small">
                <el-option v-for="(item, key) in dicts.line_voltage.obj" :key="key" :label="item.dictLabel" :value="key"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="全长(km)" prop="length">
              <el-input-number v-model="form.length" controls-position="right" :min="0" placeholder="请输入全长值"  size="small" ></el-input-number>
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
            <el-form-item label="起点塔号" prop="startPoint">
              <el-input v-model="form.startPoint" placeholder="请输入起点塔号"  size="small"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="终点塔号" prop="endPoint">
              <el-input v-model="form.endPoint" placeholder="请输入终点塔号"  size="small"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="建成时间" prop="completeDate">
              <el-date-picker v-model="form.completeDate" format="yyyy-MM-dd" value-format="yyyy-MM-dd" type="date" placeholder="选择日期" size="small"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" size="small"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" size="mini">确 定</el-button>
        <el-button @click="cancel" size="mini">取 消</el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { add_lineInfo,edit_lineInfo,get_lineInfo_detail,del_lineInfo,get_lineList,export_lineInfo } from "@/api/senxu/devicemanager/line";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import * as global  from "@/utils/global";
import { mapGetters } from 'vuex'
export default {
  name: "lineManagement",
  components: { Treeselect },
  data() {
    return {
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
      // 表格数据
      lineList: null,
      // 状态数据字典
      statusOptions: {},
      // 电压等级数据字典
      levelOptions: {},
      // 弹出层标题
      title: "",
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
        keyword: undefined
      },
      deptName: '', // 部门
      // 列信息
      columns: [
        { key: 0, label: `线路编号`, visible: true },
        { key: 1, label: `线路名称`, visible: true },
        { key: 2, label: `管辖部门`, visible: true },
        { key: 3, label: `线高`, visible: true },
        { key: 4, label: `传输电压`, visible: true },
        { key: 5, label: `高压等级`, visible: true },
        { key: 6, label: `全长`, visible: true },
        { key: 7, label: `建成时间`, visible: true },
        { key: 8, label: `起点塔号`, visible: true },
        { key: 9, label: `终点塔号`, visible: true },
        { key: 10, label: `状态`, visible: true }
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
          { required: true, message: "管辖部门不能为空", trigger: "change" }
        ], 
        high: [
          { required: true, message: "线高不能为空", trigger: "blur" }
        ],
        voltage: [
          { required: true, message: "传输电压不能为空", trigger: "blur" }
        ],
        level: [
          { required: true, message: "高压等级不能为空", trigger: "change" }
        ], 
        length: [
          { required: true, message: "全长不能为空", trigger: "blur" }
        ],
        state: [
          { required: true, message: "状态不能为空", trigger: "change" }
        ], 
        startPoint: [
          { required: true, message: "起点塔号不能为空", trigger: "blur" }
        ],
        endPoint: [
          { required: true, message: "终点塔号不能为空", trigger: "blur" }
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
  computed: {
    ...mapGetters([
      'dicts',
    ]),
    tableHeight () {
      return window.innerHeight - 350;
    },
    
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
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      get_lineList(this.queryParams).then(response => {
        this.lineList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
      this.open = true;
      this.title = "添加";        
      this.$nextTick(()=>{
        this.$refs.form.resetFields();
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids
      get_lineInfo_detail(id).then(response => {
        response.data.state = response.data.state?response.data.state.toString():'';
        this.form = response.data;
          // 匹配 字典删除后
        this.slowl('line_voltage', response.data.level, 'level');
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
      this.$confirm('是否确认导出所有线路数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return export_lineInfo(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.deptName = this.deptName;
          if (this.form.id != undefined) {
            edit_lineInfo(this.form).then(response => {
              if(response.code == 200) {
                this.msgSuccess(response.msg);
                this.open = false;
                this.getList();
              }
            });
          } else {
            add_lineInfo(this.form).then(response => {
              if(response.code == 200) {
                this.msgSuccess(response.msg);
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const idsname = row.name || this.idsname;
      this.$confirm('是否确认删除线路名称为"' + idsname + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return del_lineInfo(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(()=>{});
    }
  }
};
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;}
::v-deep{  
  .el-card__body{height:100%;display: flex; flex-direction: column;}
  .el-select{width:100%;}
  .el-card,.el-card__body{height:100%;}
  .el-input-number__decrease,.el-input-number__increase{display: none;}
  .el-input-number .el-input__inner{padding:0 15px;text-align: left;}
  .el-input-number{width:100%!important;}
  .el-table{flex:1;}
}
</style>