<template>
    <!-- 视频参数-录像策略时间表 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏录像策略时间表" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>录像策略时间表</span>
            <el-button size="mini" type="primary" class="querybtn" @click="query" :disabled="fromTreeData.node[0].yuntais.length<0">查询设备</el-button>
        </div>
        <div class="box-content">
            <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                    <el-button type="success" @click="saveVideoStrategy" icon="el-icon-plus" :disabled="Object.keys(thisYuntaiInfo).length<1 || open"  size="mini" >设置</el-button>
                </el-col>   
                <el-col :span="1.5">
                    <el-button type="primary" plain icon="el-icon-plus" :disabled="Object.keys(thisYuntaiInfo).length<1 || open" size="mini" @click="handleAdd" >添加</el-button>
                </el-col>
            </el-row>
            <el-table v-loading="loading" :data="lineList" height="200"  @selection-change="handleSelectionChange">
                <!-- <el-table-column type="selection" width="50" align="center" />
                <el-table-column label="ID" align="center" key="id" prop="id"/> -->
                <el-table-column label="序号" align="center" type="index" width="50"  :index="table_index"> </el-table-column>
                <el-table-column label="动作类型" align="center" key="actionCategory" prop="actionCategory" :show-overflow-tooltip="true" >
                     <template slot-scope="scope">
                        {{typeof(scope.row.actionCategory) == 'number' ?dicts.actiontype.obj[scope.row.actionCategory].dictLabel:''}}
                    </template>
                </el-table-column>
                <el-table-column label="参数(预置位)" align="center" key="actionParam" prop="actionParam" :show-overflow-tooltip="true" >
                    <template slot-scope="scope">
                        {{scope.row.actionCategory==0?"预置位":scope.row.actionCategory==1?"巡航":"巡检"}}{{scope.row.actionParam}}
                    </template>
                </el-table-column>
                <el-table-column label="开始时间" align="center" key="startTime" prop="startTime" :show-overflow-tooltip="true" />
                <el-table-column label="持续秒数" align="center" key="duration" prop="duration" :show-overflow-tooltip="true" />
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="deleteRow(scope.$index, lineList)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 批量添加 | 添加 -->
        <el-dialog title="添加" :visible.sync="dialogVisible" width="30%">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm" v-if="dialogVisible">
                <el-form-item label="动作类别" prop="actionCategory">
                    <el-radio-group v-model="ruleForm.actionCategory" @change="changeCategory">
                        <el-radio :label="item.dictValue" v-for="item in dicts.actiontype.obj" :key="item.dictValue">{{item.dictLabel}}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="预览位置" prop="actionParam">
                    <el-select  v-model="ruleForm.actionParam" filterable  placeholder="请选择" size="small" clearable style="width:100%;" v-if="ruleForm.actionCategory == 0">
                        <el-option v-for="(item,index) in bits" :key="index" :label="'预置位'+item" :value="item"></el-option>
                    </el-select>
                    <el-select  v-model="ruleForm.actionParam" filterable  placeholder="请选择" size="small" clearable style="width:100%;" v-if="ruleForm.actionCategory == 1">
                        <el-option v-for="(item,index) in cruiseTotal" :key="index" :label="'巡航'+item" :value="item"></el-option>
                    </el-select>
                    <el-select  v-model="ruleForm.actionParam" filterable  placeholder="请选择" size="small" clearable style="width:100%;" v-if="ruleForm.actionCategory == 2">
                        <el-option v-for="(item,index) in inspectionTotal" :key="index" :label="'巡检'+item" :value="item"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="开始时间" prop="startTime">
                    <el-time-picker size="small"  v-model="ruleForm.startTime" format="HH:mm:ss" clear-icon="" value-format="HH:mm:ss" style="width:100%;" placeholder="开始时间"></el-time-picker>
                </el-form-item>
                <el-form-item label="持续秒数" prop="duration">
                    <el-input-number v-model="ruleForm.duration" style="width:100%;"  controls-position="right" :min="1" placeholder="请输入时间间隔"  size="small" ></el-input-number>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm" size="small">确 定</el-button>
                <el-button @click="cancel" size="small">取 消</el-button>
            </span>
        </el-dialog>
    </el-card>
</template>
<script>
import { getVideoStrategy, saveVideoStrategy } from "@/api/senxu/config/index";
import * as global  from "@/utils/global";
import { mapGetters } from 'vuex'
export default {
    props: ['favIcon', 'treeData'],
    name: 'recordingTimeline',
    data () {
        return{
            channelNo: undefined,
            channelType: undefined,
            open: true,
            form:{},
            thisYuntaiInfo: {},
            fromTreeData: {},
            act:false,
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 总条数
            total: 1,
             // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                id: undefined,
                kind:2 // 1图像，2视频
            },
            lineList: [],
            ids: [],
            loading: false,
            dialogVisible: false,
            inspectionTotal:global.INSPECTION_TOTAL, // 巡检            
            cruiseTotal: global.CRUISE_TOTAL, // 巡航
             // 表单校验
            rules: {
                actionCategory: [
                    { required: true, message: "动作类型不能为空", trigger: "change" }
                ],
                startTime: [
                    { required: true, message: "抓拍时间不能为空", trigger: "change" }
                ],
                actionParam: [
                    { required: true, message: "预置位不能为空", trigger: "change" }
                ],
                duration: [
                    {required: true, message: "时间间隔不能为空", trigger: "blur" }
                ]
            },
            ruleForm: {actionCategory: '0',startTime:undefined,actionParam:undefined,duration:undefined}
        }
    },
    computed: {        
        ...mapGetters([
            'dicts',
        ]),
        bits() {
            return global.PRESET_BIT_TOTAL;
        }
    }, 
    watch: {
        treeData:{
            handler (newVal){
                this.lineList = [];
                this.ruleForm = {actionCategory: '0',startTime:undefined,actionParam:undefined,duration:undefined};
                this.open = true;
                this.fromTreeData = newVal;
            },
            deep:true
        },
    },
    created() {
        this.fromTreeData = this.treeData;
        let name = this.$options.name;
        this.act = global.fav_init_toggle(name) == 0?false:true;
    },
    methods: {
        table_index(index) {
            return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
        },
        reset () {
            this.ruleForm = {
                actionCategory:'0',
                startTime:undefined,
                actionParam:undefined,
                duration:undefined
            }
        },
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        handleAdd () {
            // 批量添加 | 添加
            this.dialogVisible = true;
            this.$nextTick(() => {
                this.$refs["ruleForm"].clearValidate();
            });
        },
        // 多选框选中数据
        handleSelectionChange(selection) {
            this.ids = selection.map(item => item.id);
            this.single = selection.length != 1;
            this.multiple = !selection.length;
        },
        /** 查询列表 */
        getList() {
            this.get_configStrategy_list();
        },
        /** 提交按钮 */
        submitForm() {
            this.$refs["ruleForm"].validate(valid => {
                if (valid) {
                    this.lineList.push({actionCategory:parseInt(this.ruleForm.actionCategory),actionParam:this.ruleForm.actionParam,duration:this.ruleForm.duration,startTime:this.ruleForm.startTime});
                    this.dialogVisible = false;
                }
            });
        },
        saveVideoStrategy() {
            // 设置视频
            if(this.lineList.length<1) {this.$message("请新增策略!");return; }
            saveVideoStrategy({details:this.lineList,channelNo:this.channelNo,channelType: this.channelType}, this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    this.msgSuccess("设置成功");
                }
            });
        },
        query() {
            // 查询设备
            this.thisYuntaiInfo = {};
            this.open = true;
            // this.thisYuntaiInfo['id'] = 1; // 测试
            // this.queryParams.id = 1; // 测试
            for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];
                    this.queryParams.id = this.fromTreeData.node[0].yuntais[i].id;
                    this.get_configStrategy_list();
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
        },
        get_configStrategy_list() {
            this.loading = true;
             getVideoStrategy(this.queryParams.id).then(response => {
                 if(response.code == 200) {
                    this.open = false;
                    this.lineList = response.videoStrategy.details?response.videoStrategy.details:[];
                    this.channelNo = response.videoStrategy.channelNo;
                    this.channelType = response.videoStrategy.channelType;
                    this.loading = false;
                 }
                
            });
        },
        // 取消按钮
        cancel() {
            this.dialogVisible = false;
        },
        deleteRow(index, rows) {
            rows.splice(index, 1);
        },
        changeCategory() {
            // 切换动作类型
            this.ruleForm.startTime = undefined;
            this.ruleForm.actionParam = undefined;
            this.ruleForm.duration = undefined;
            this.$nextTick(() => {
                this.$refs["ruleForm"].clearValidate();
            });
        }
    }
}
</script>
<style lang="scss" scoped>
.head{display:flex;align-items:center;
    .fav{margin-right:5px;cursor: pointer;font-size:20px;color:$base-color-default;}
    .querybtn{margin-left:auto;}
}
::v-deep{
    .el-input-number__decrease,.el-input-number__increase{display: none;}
  .el-input-number .el-input__inner{padding:0 15px;text-align: left;}
  .el-input-number{width:100%!important;}
}
.box-content{padding:10px 10px 0;}
</style>