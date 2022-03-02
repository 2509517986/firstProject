<template>
    <!-- 设备系统管理-远程升级 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏远程升级" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>远程升级</span>
            <el-button size="mini" type="primary" class="querybtn" @click="query">查询设备</el-button>
        </div>
        <div class="box-content">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" size="mini" class="demo-ruleForm">
                <el-form-item>
                    <el-row :gutter="5">
                        <el-col :span="24">升级上传文件</el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-row :gutter="5">
                        <el-col :span="8">
                            <el-upload class="upload-demo" size="mini" drag action="#" ref="upload" :http-request="uploadFile" :before-remove="beforeRemove"  :show-file-list="true" :on-exceed="handleExceed" :file-list="fileList" :multiple="false" :auto-upload="false" :limit="1" accept=".zip">
                                <i class="el-icon-upload"></i>
                                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                                <div class="el-upload__tip" slot="tip">只能上传.zip文件，且不超过500M</div>
                                <div class="el-upload__tip" slot="tip" style="width:360px;">
                                    <el-row :gutter="10">
                                        <el-col :span="24">
                                            <el-button type="primary" size="mini" style="width:100%;" @click="submitUpload" :disabled="Object.keys(thisYuntaiInfo).length<1 || open">上传</el-button>
                                        </el-col>
                                        <el-col :span="12">
                                            <!-- <el-button size="mini" style="width:100%;" @click="canceUpload">取消</el-button> -->
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-upload>
                        </el-col>
                        <el-col :span="14">
                            <h4>强烈提醒：</h4>
                            <p>1. 设备升级文件一定要选择与设备匹配的版本，否则可能会导致设备故障。</p>
                            <p>2. 请等待设备升级命令发送成功之后再关闭本页面，否则可能会导致升级失败。</p>
                            <p>3. 新上传的设备升级文件若与服务器上已有的升级文件同名，上传成功后，将会覆盖服务器上的升级文件。</p>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-row :gutter="5">
                        <el-col :span="24">升级文件列表</el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-table :data="updateList" height="150" border @row-click="handleRowClick" highlight-current-row :row-class-name="tableRowClassName">
                        <el-table-column label="No." align="center" key="yuntaiId" prop="yuntaiId"/>
                        <el-table-column label="文件名" align="center" key="fileName" prop="fileName" :show-overflow-tooltip="true" />
                        <el-table-column label="FTP路径" align="center" key="ftpUrl" prop="ftpUrl" :show-overflow-tooltip="true" />
                        <!-- <el-table-column label="HTTP路径" align="center" key="httpUrl" prop="httpUrl" :show-overflow-tooltip="true" /> -->
                        <el-table-column label="文件大小" align="center" key="size" prop="size" :show-overflow-tooltip="true" />
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-form-item>
                <el-form-item>
                     <el-row :gutter="5">
                        <el-col :span="7">
                            <el-form-item label="当前升级包" prop="name">
                                <el-input size="mini" disabled v-model="fileName"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="升级方式" prop="manner">
                                 <el-select filterable  placeholder="请选择" size="mini" style="width:100%;" v-model="manner">
                                    <el-option v-for="item in updateOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>                    
                </el-form-item>
                <el-form-item>
                    <el-row :gutter="5">
                        <el-col :span="7">
                            <el-form-item label="升级目的设备" prop="purposeEquipment">
                                <el-select filterable  placeholder="请选择" size="mini" style="width:100%;" v-model="purposeEquipment" disabled>
                                    <el-option v-for="item in deviceChanel" :key="item.value" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="文件传输协议" prop="protocol">
                                 <el-select filterable  placeholder="请选择" size="mini" style="width:100%;" v-model="protocol">
                                    <el-option v-for="item in transferProtocol" :key="item.value" :label="item.label" :value="item.value"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-button size="mini" type="primary" @click="addrecord" :disabled="Object.keys(thisRowInfo).length<1 && recordDisabled">单设备升级</el-button>
                </el-form-item>
                <el-form-item>
                    <el-table :data="updateRecord" height="150" border>
                        <el-table-column label="No." align="center" key="code" prop="code"/>
                        <el-table-column label="设备" align="center" key="name" prop="name" :show-overflow-tooltip="true" />
                        <!-- <el-table-column label="升级进度" align="center" key="progress" prop="progress" :show-overflow-tooltip="true" /> -->
                        <el-table-column label="升级状态" align="center" key="state" prop="state" :show-overflow-tooltip="true">
                            <template slot-scope="scope">
                                {{scope.row.state?scope.row.state==1?"成功":"失败":'——'}}
                            </template>
                        </el-table-column>
                    </el-table>
                </el-form-item>
                <el-form-item>
                    <el-row :gutter="5">
                        <el-col :span="24">升级记录</el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-table :data="updateRecord" height="150" border>
                        <el-table-column label="No." align="center" key="code" prop="code"/>
                        <el-table-column label="设备PUID" align="center" key="yuntaiId" prop="yuntaiId" :show-overflow-tooltip="true" />
                        <el-table-column label="升级结果" align="center" key="result" prop="result" :show-overflow-tooltip="true" />
                        <!-- <el-table-column label="升级结果描述" align="center" key="description" prop="description" :show-overflow-tooltip="true" /> -->
                        <el-table-column label="升级目的设备" align="center" key="purposeEquipment" prop="purposeEquipment" :show-overflow-tooltip="true" >
                            <template slot-scope="scope">
                                {{scope.row.purposeEquipment?scope.row.purposeEquipment==1?"设备":"通道":'——'}}
                            </template>
                        </el-table-column>
                        <!-- <el-table-column label="通道号" align="center" key="chanelNum" prop="chanelNum" :show-overflow-tooltip="true" /> -->
                        <el-table-column label="升级文件类型" align="center" key="fileType" prop="fileType" :show-overflow-tooltip="true" />
                        <el-table-column label="升级文件名" align="center" key="fileName" prop="fileName" :show-overflow-tooltip="true" />
                        <el-table-column label="升级用户" align="center" key="createUser" prop="createUser" :show-overflow-tooltip="true" />
                        <el-table-column label="开始升级时间" align="center" key="beginTime" prop="beginTime" :show-overflow-tooltip="true" />
                        <el-table-column label="结束升级时间" align="center" key="endTime" prop="endTime" :show-overflow-tooltip="true" />
                        <el-table-column label="原来版本号" align="center" key="oldVersion" prop="oldVersion" :show-overflow-tooltip="true" />
                        <el-table-column label="升级后版本号" align="center" key="newVersion" prop="newVersion" :show-overflow-tooltip="true" />                      
                    </el-table>
                </el-form-item>
            </el-form>
        </div>
    </el-card>
</template>
<script>
import {uploadFileZip,get_uploadList,set_uploadList,del_uploadList,get_record,set_record,del_record, add_record} from "@/api/senxu/config/index";
import * as global  from "@/utils/global";
export default {
    props: ['favIcon', 'treeData'],
    name: 'remoteUpgrade',
    data () {
        return{
            open: true,
            recordDisabled: true, // 单设备升级
            act:false,
            thisYuntaiInfo: {},
            fromTreeData: {},
            fileName: '',
            ruleForm: {},            
             // 表单校验
            rules: {
                code: [
                    { required: true, message: "线路编号不能为空", trigger: "blur" }
                ],
                name: [
                    { required: true, message: "线路名称不能为空", trigger: "blur" }
                ],
            },
            purposeEquipment:'1', // 目的设备
            thisRowInfo: {}, // 当前行信息
            manner: '1', // 升级方式
            protocol: '1', // 传输协议
            updateOptions: [{value:'1',label:'普通升级'},{value:'2',label: '续点续传升级'}],
            deviceChanel: [{value:'1',label:'设备'},{value:'2',label: '通道'}],
            transferProtocol: [{value:'1',label:'HTTP'},{value:'2',label: 'FTP'}],
            updateList:[], // 升级文件列表
            updateProgress: [], // 升级进度
            updateRecord:[], // 升级记录
            fileList: [] // 上传文件
        }
    },
    watch: {
        treeData:{
            handler (newVal){
                this.updateRecord = [];
                this.updateList = [];
                this.open = true;
                this.fromTreeData = newVal;
                this.recordDisabled = true;
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
        handleRowClick(row) {
            // 点击当前行
            this.thisRowInfo = row;
            this.fileName = this.thisRowInfo.fileName;
            this.recordDisabled = false;
        },        
        tableRowClassName ({row, rowIndex}) {
            //把每一行的索引放进row
            row.index = rowIndex;
        },
        addrecord () {
            // 单设备升级
            let obj = {...this.thisRowInfo,manner:this.manner,protocol:this.protocol,purposeEquipment:this.purposeEquipment,code: this.thisYuntaiInfo.code}
            if(this.thisRowInfo.yuntaiId != null){
                add_record(obj).then(response => {
                    if(response.code == 200) {
                        this.get_record();
                        this.$message({
                            type: "success",
                            message: "升级成功"
                        });
                    } else {
                        this.$message(response.msg);
                    }
                });
            }else{ 
                this.$message("请选择任意一个文件升级");
                return;
            }
            // add_record(obj).then(response => {
            //     if(response.code == 200) {
            //         this.get_record();
            //         // this.$message({
            //         //     type: "success",
            //         //     message: "升级成功"
            //         // });
            //     } else {
            //         this.$message(response.msg);
            //     }
            // });
        },
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        query() {
            // 查询设备
            this.thisYuntaiInfo = {};
            this.open = true;
            // this.thisYuntaiInfo['id'] = 1; // 测试
            for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];
                    this.get_uploadList();
                    this.get_record();
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
            
        },
        get_uploadList() {
            get_uploadList({yuntaiId:this.thisYuntaiInfo.id}).then(response => {
                if(response.code == 200) {   
                    this.open = false;
                    this.updateList = response.rows; 
                    if(this.updateList.length<1) {this.fileName = '';} 
                } 
            });
        },
        get_record() {
            get_record({yuntaiId:this.thisYuntaiInfo.id}).then(response => {
                if(response.code == 200) {   
                    this.updateRecord = response.rows;
                    this.recordDisabled = false;
                } 
            });
        },
        handleDelete(row) {            
            const ids = row.id || this.ids;
            this.$confirm('是否确认删除?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
                }).then(function() {
                return del_uploadList(ids);
                }).then(() => {
                this.get_uploadList();
                this.msgSuccess("删除成功");
            }).catch(()=>{});
        },
        handleExceed(file, fileList) {
            this.fileList = []
            this.fileList.push(file[0])   // 默认覆盖上个文件
        },
        beforeRemove(file, fileList) {  // 移除上传的文件
            return this.$confirm(`确定移除“${file.name}”？`);
        },
        uploadFile(params) {
            const _file = params.file;
            // const isJPG = _file.type === 'application/x-zip-compressed';
            const isLt2M = _file.size / 1024 / 1024 < 500;

            // if (!isJPG) {
            //     this.$message.error('上传文件只能是 zip 格式!');
            //     return;
            // }
            if (!isLt2M) {
                this.$message.error('上传文件不能超过 500MB!');
                this.$refs.upload.clearFiles();
                return;
            }
            const formData = new FormData();
            formData.append('file', _file);
            // this.thisYuntaiInfo.id
            formData.append('yuntaiId', this.thisYuntaiInfo.id); // isOverride自定义的其他参数
            uploadFileZip (formData).then((response => {     
                if(response.code == 200) {
                    this.get_uploadList();
                    this.$message.success(response.msg || '文件导入成功，稍后查询处理结果');
                    this.open = true;
                } else {
                    this.$message.error(response.msg || '文件导入失败，稍后重试');
                    this.$refs.upload.clearFiles();
                }
            }));
        },
        canceUpload() {
            // 取消上传
        },
        submitUpload() {
            this.open = true;
            this.$refs.upload.submit();
        }
    }
}
</script>
<style lang="scss" scoped>
::v-deep{.el-table td, .el-table th{padding:5px 0;}}
.head{display:flex;align-items:center;
    .fav{margin-right:5px;cursor: pointer;font-size:20px;color:$base-color-default;}
    .querybtn{margin-left:auto;}
}
.box-content{padding:10px 10px 0;}
</style>