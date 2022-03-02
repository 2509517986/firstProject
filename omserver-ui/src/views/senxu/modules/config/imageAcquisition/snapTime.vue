<template>
    <!-- 图像采集-抓拍时间 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏抓拍时间" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>抓拍时间</span>
            <el-button size="mini" type="primary" class="querybtn"  @click="query" :disabled="fromTreeData.node[0].yuntais.length<0">查询设备</el-button>
        </div>
        <div class="box-content">
            <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                    <el-button type="success" @click="addTimeList" icon="el-icon-plus" :disabled="Object.keys(thisYuntaiInfo).length<1 || open"  size="mini">设置</el-button>
                </el-col> 
                <el-col :span="1.5">
                    <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd('single')" :disabled="Object.keys(thisYuntaiInfo).length<1 || open" >添加</el-button>
                </el-col>
            </el-row>
            <el-table v-loading="loading" :data="lineList" height="200"  @selection-change="handleSelectionChange">
                <el-table-column label="序号" align="center" type="index" width="50"  :index="table_index"> </el-table-column>
                <!-- <el-table-column label="ID" align="center" key="id" prop="id"/> -->
                <el-table-column label="抓拍时间" align="center" :show-overflow-tooltip="true">
                    <template slot-scope="scope">
                        {{scope.row.hour}}:{{scope.row.minute}}
                    </template>
                </el-table-column>
                <el-table-column label="预置位" align="center" key="preset" prop="preset" :show-overflow-tooltip="true" />
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="danger" size="mini" @click="deleteRow(scope.$index, lineList)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <!-- 批量添加 | 添加 -->
        <el-dialog :title="type=='all'?'批量添加':'添加'" :visible.sync="dialogVisible" width="30%">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm" v-if="dialogVisible">
                <el-form-item label="时间范围" prop="name" v-if="type == 'all'">
                    <el-time-picker size="small" style="width:100%;"  format="yyyy-MM-dd" value-format="yyyy-MM-dd" is-range range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" placeholder="选择时间范围"></el-time-picker>
                </el-form-item>
                <el-form-item label="抓拍时间" prop="captureTime" v-else>
                    <el-time-picker size="small"  style="width:100%;"  format="HH:mm" clear-icon="" value-format="HH:mm" placeholder="抓拍时间" v-model="ruleForm.captureTime"></el-time-picker>
                </el-form-item>
                <el-form-item label="时间间隔(min)" prop="duration" v-if="type == 'all'">
                    <el-input size="small" style="width:100%;" v-model="ruleForm.duration" placeholder="请输入时间间隔"></el-input>
                </el-form-item>
                <el-form-item label="预置位" prop="preset">
                    <el-select v-model="ruleForm.preset" filterable  placeholder="请选择" size="small" clearable style="width:100%;">
                        <el-option v-for="(item,index) in bits" :key="index" :label="item" :value="item"></el-option>
                    </el-select>
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
import { getCaptureStrategy, saveCaptureStrategy } from "@/api/senxu/config/index";
import * as global  from "@/utils/global";
export default {
    props: ['favIcon', 'treeData'],
    name: 'snapTime',
    data () {
        return{
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
                kind:1 // 1图像，2视频
            },
            groupNum: '', // 获取抓拍时间信息
            channelNo: '',
            lineList: [],
            ids: [],
            loading: false,
            dialogVisible: false,
            type: '', // 批量添加 || 单个添加
            preset_bit_list: [], // 预置位
             // 表单校验
            rules: {
                captureTime: [
                    { required: true, message: "抓拍时间不能为空", trigger: "change" }
                ],
                preset: [
                    { required: true, message: "预置位不能为空", trigger: "change" }
                ],
                duration: [
                    {required: true, message: "时间间隔不能为空", trigger: "blur" }
                ]
            },
            ruleForm: {}
        }
    },
    computed: {
        bits() {
            return global.PRESET_BIT_TOTAL;
        }
    },
    watch: {
        treeData:{
            handler (newVal){
                this.open = true;
                this.form = {};
                this.lineList = [];
                this.ruleForm = {};
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
                captureTime:undefined,
                presetBit:undefined,
                duration:undefined
            }
        },
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        handleAdd (type) {
            // 批量添加 | 添加
            this.type = type;
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
            // 新增抓拍时间
            this.$refs["ruleForm"].validate(valid => {
                if (valid) {
                    let newAry = [],hour = "",minute="",preset="";
                    newAry = this.ruleForm.captureTime.split(":");
                    hour = newAry[0];
                    minute = newAry[1];
                    preset = this.ruleForm.preset;
                    this.lineList.push({hour,minute,preset});
                    this.dialogVisible = false;
                }
            });
        },
        addTimeList() {
            if(this.lineList.length<1) {this.$message("请新增策略!");return;}
            saveCaptureStrategy({details:this.lineList,channelNo:this.channelNo}, this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    // 设备反应慢，设置后不查询
                    // this.get_configStrategy_list();
                    this.$message({
                        type: "success",
                        message: "设置成功"
                    });
                } else {
                    this.$message(response.msg);
                }
            });;
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
             getCaptureStrategy(this.queryParams.id).then(response => {
                 if(response.code == 200) {
                    this.open = false;
                    this.lineList = response.captureStrategy.details?response.captureStrategy.details:[];
                    this.groupNum = response.captureStrategy.groupNum;
                    this.channelNo = response.captureStrategy.channelNo;
                    this.loading = false;
                 }
                
            });
        },
        saveCaptureStrategy() {

        },
        // 取消按钮
        cancel() {
            this.dialogVisible = false;
        },
        deleteRow(index, rows) {
            rows.splice(index, 1);
        }
    }
}
</script>
<style lang="scss" scoped>
.head{display:flex;align-items:center;
    .fav{margin-right:5px;cursor: pointer;font-size:20px;color:$base-color-default;}
    .querybtn{margin-left:auto;}
}
.box-content{padding:10px 10px 0;}
</style>