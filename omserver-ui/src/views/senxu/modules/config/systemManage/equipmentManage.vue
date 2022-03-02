<template>
    <!-- 设备系统管理-系统设备管理 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏系统设备管理" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>系统设备管理</span>
            <!-- <el-button size="mini" type="primary" class="querybtn" @click="query">查询设备</el-button> -->
        </div>
        <div class="box-content">
             <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" size="mini" class="demo-ruleForm">
                <el-form-item label="原密码" prop="oldPassword">
                    <el-row :gutter="5">
                        <el-col :span="8"><el-input oninput="value=value.replace(/[^\d]/g, '');if(value.length > 4)value = value.slice(0, 4)" v-model="ruleForm.oldPassword" size="mini" placeholder="请输入原密码"></el-input></el-col>
                    </el-row>
                </el-form-item>
                <el-form-item label="新密码" prop="newPassword1">
                    <el-row :gutter="5">
                        <el-col :span="8"><el-input oninput="value=value.replace(/[^\d]/g, '');if(value.length > 4)value = value.slice(0, 4)" v-model="ruleForm.newPassword1" size="mini" placeholder="请输入新密码"></el-input></el-col>
                    </el-row>
                </el-form-item>
                <el-form-item label="确认密码" prop="newPassword2">
                    <el-row :gutter="5">
                        <el-col :span="8"><el-input oninput="value=value.replace(/[^\d]/g, '');if(value.length > 4)value = value.slice(0, 4)" v-model="ruleForm.newPassword2" size="mini" placeholder="请再次输入新密码"></el-input></el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="mini" :disabled="Object.keys(thisYuntaiInfo).length<1" @click="setDevicePassword">设置设备密码</el-button>
                </el-form-item>
            </el-form>
            <el-form  :model="resetForm"  ref="resetForm">
                 <el-form-item label="选择日期" prop="name">
                     <el-row :gutter="5">
                         <el-col :span="8"><el-date-picker format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" size="mini" style="width:100%;" v-model="datetime" type="datetime"></el-date-picker></el-col>
                         <el-col :span="1.5"><el-button type="primary" size="mini" :disabled="Object.keys(thisYuntaiInfo).length<1 || open" @click="getTiming">设置</el-button></el-col>
                         <el-col :span="1.5"><el-button type="primary" size="mini" :disabled="Object.keys(thisYuntaiInfo).length<1" @click="getDeviceDate">查询</el-button></el-col>
                         <el-col :span="1.5"><el-button type="primary" size="mini" :disabled="Object.keys(thisYuntaiInfo).length<1 || open" @click="getTimeLock">与本机时间同步</el-button></el-col>
                     </el-row>
                </el-form-item>
                <el-form-item>
                    <el-row :gutter="5">
                        <el-col :span="1.5"><el-button type="primary" size="mini" :disabled="Object.keys(thisYuntaiInfo).length<1" @click="restart">重启设备</el-button></el-col>
                        <el-col :span="1.5"><el-button size="mini" type="success" :disabled="Object.keys(thisYuntaiInfo).length<1" @click="smsWakeUp">唤醒设备</el-button></el-col>
                        <!-- <el-col :span="1.5"><el-button type="primary" size="mini" :disabled="Object.keys(thisYuntaiInfo).length<1" @click="restart1">气象数据测试按钮</el-button></el-col>
                        <el-col :span="1.5"><el-button type="primary" size="mini" :disabled="Object.keys(thisYuntaiInfo).length<1" @click="restart2">导线温度测试按钮</el-button></el-col> -->
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-row :gutter="5">
                        <el-col :span="24">注：(如果设备在线，会立即执行抓拍图片或者录制小视频数据上报到平台)</el-col>
                    </el-row>
                </el-form-item>
                <el-form-item v-if="true">
                    <el-row :gutter="5">
                        <el-col :span="8"><el-input style="width:100%;"  size="mini" v-model="resetForm.fileName" placeholder="请输入/usr/OM/下的文件名" ></el-input></el-col>
                        <el-col :span="3"><el-button type="primary" size="mini" :disabled="Object.keys(thisYuntaiInfo).length<1" @click="downloadFile">下载文件</el-button></el-col>
                    </el-row>
                </el-form-item>
                <!--<el-form-item v-if="true">
                    <el-row :gutter="5">
                        <el-col :span="24">注：(如果设备在线，会立即日志文件上报到平台)</el-col>
                    </el-row>
                </el-form-item>-->


            </el-form>
        </div>
    </el-card>
</template>
<script>
import {getDeviceDate, setDevicePassword,getTiming,downloadFile,restart,restart1,restart2,smsWakeUp } from "@/api/senxu/config/index";
import {manualCap, capShortVideo } from "@/api/senxu/yuntai_picture/index";
import * as global  from "@/utils/global";
export default {
    props: ['favIcon', 'treeData'],
    name: 'equipmentManage',
    data () {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入原密码'));
            } else {
            if (this.ruleForm.newPassword !== '') {
                this.$refs.ruleForm.validateField('newPassword1');
                this.$refs.ruleForm.validateField('newPassword2');
            }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入新密码'));
            }else {
                callback();
            }
            if(this.ruleForm.newPassword2 == ''){
                callback(new Error('请输入确认密码'));
            }

        };
        var validatePass3 = (rule, value, callback) => {
            if (value === '' && this.ruleForm.newPassword1 !== '') {
                callback(new Error('请再次输入新密码'));
            } else {
                if(value !== this.ruleForm.newPassword1) {
                     callback(new Error('新密码和确认密码不一致'));
                }else{
                    callback();
                }
            }
        };
        return{
             // 表单校验
            rules: {
                oldPassword: [
                    { validator: validatePass, trigger: 'blur'}
                ],
                newPassword1: [
                    { validator: validatePass2, trigger: 'blur'}
                ],
                newPassword2: [
                    { validator: validatePass3, trigger: 'blur'}
                ],
            },
            resetForm:{fileName:''},
            ruleForm: {oldPassword:'',newPassword1:'',newPassword2:''},
            act:false,
            thisYuntaiInfo: {},
            fromTreeData: {},
            open:true,
            datetime: ''
        }
    },
    created() {
        this.fromTreeData = this.treeData;
        this.query();
        let name = this.$options.name;
        this.act = global.fav_init_toggle(name) == 0?false:true;
    },
    watch: {
        treeData:{
            handler (newVal){
                this.fromTreeData = newVal;
                this.ruleForm.oldPassword = '';
                this.ruleForm.newPassword1 = '';
                this.ruleForm.newPassword2 = '';
                this.ruleForm.fileName = '';
                this.datetime = '';
                this.open = true;
                this.query();
            },
            deep:true
        },
    },
    methods: {

        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        query() {
            this.thisYuntaiInfo = {};
            // this.thisYuntaiInfo['id'] = 1; // 测试
           for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
        },
        setDevicePassword() {
            // 设置密码
            this.$refs['ruleForm'].validate((valid) => {
                if (valid) {
                   setDevicePassword({oldPassword:this.ruleForm.oldPassword,newPassword:this.ruleForm.newPassword1}, this.thisYuntaiInfo.id).then(response => {
                       if(response.code == 200) {
                           this.$message({
                               type: "success",
                               message: "设置成功"
                           });
                            this.ruleForm={brand_right:0}
                       }
                   });
                } else {
                    return false;
                }
            });
        },
        getDeviceDate() {
            // 获取本机时间
            getDeviceDate(this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    this.open = false;
                    if(response.data && response.data.length>0) {
                        this.datetime = response.data;
                    }
                }
            });
        },
        getTimeLock() {
            // 与本机时间同步
            this.datetime = this.parseTime(new Date(), '{y}-{m}-{d} {h}:{i}:{s}');
        },
        getTiming () {
            // 设置校时
            if(this.datetime.length<1) return;
            let datetime = new Date(this.datetime);
            let year= datetime.getFullYear(),mon = (datetime.getMonth() + 1)>9 ? (datetime.getMonth() + 1) : ('0' + (datetime.getMonth() + 1)),date = datetime.getDate().toString().length === 2 ? datetime.getDate() : ('0' + datetime.getDate()),hour = datetime.getHours().toString().length === 2 ? datetime.getHours() : ('0' + datetime.getHours()),min = datetime.getMinutes().toString().length === 2 ? datetime.getMinutes() : ('0' + datetime.getMinutes()),second = datetime.getSeconds().toString().length === 2 ? datetime.getSeconds() : ('0' + datetime.getSeconds());
            getTiming({year,mon,date,hour,min,second},this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    this.$message({
                        type: "success",
                        message: response.msg
                    });
                } else {
                    this.$message(response.msg);
                }
            });
        },
        restart() {
            // 重启设备
            restart(this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    this.$message({
                        type: "success",
                        message: response.msg
                    });
                } else {
                    this.$message(response.msg);
                }
            });
        },
        downloadFile(){
          // 重启设备
          console.info("test...");
         /* this.$confirm('是否确认导出所有云台数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            return export_yuntai(queryParams);
          }).then(response => {
            this.download(response.msg);
          })*/

          downloadFile({fileName:this.$refs['resetForm'].model.fileName},this.thisYuntaiInfo.id).then(response => {
            if(response.code == 200) {
              /*this.$message({
                type: "success",
                message: response.msg
              });*/
              this.download(response.msg);
            } else {
              this.$message(response.msg);
            }
          }).then(response => {

          });
        },
        restart1() {
            // 重启设备
            restart1(this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    this.$message({
                        type: "success",
                        message: response.msg
                    });
                } else {
                    this.$message(response.msg);
                }
            });
        },
        restart2() {
            // 重启设备
            restart2(this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    this.$message({
                        type: "success",
                        message: response.msg
                    });
                } else {
                    this.$message(response.msg);
                }
            });
        },
        smsWakeUp() {
            // 唤醒设备
            smsWakeUp(this.thisYuntaiInfo.id).then(response => {
                if(response.code == 200) {
                    this.$message({
                        type: "success",
                        message: response.msg
                    });
                } else {
                    this.$message(response.msg);
                }
            });
        },
        photoCaptur () {
            // 抓拍图片
            let formData = new FormData();
            formData.append('manualCapType',0);
            formData.append('presetBit',0);
            formData.append('yunTaiId',this.thisYuntaiInfo.id);
            formData.append('async',0);
            manualCap(formData).then(response => {
                if(response.code == 200) {
                   this.$message({
                       type: "success",
                       message: "抓拍成功"
                   })
                } else {
                    this.$message("抓拍失败");
                }
            });
        },
        videoCaptur () {
            // 抓拍小视频
            let formData = new FormData();
            formData.append('presetBit', 0);
            formData.append('yunTaiId',this.thisYuntaiInfo.id);
            formData.append('duration', 20);
            capShortVideo(formData).then(response => {
                if(response.code == 200) {
                   this.$message({
                       type: "success",
                       message: "抓拍20s小视频成功"
                   })
                } else {
                    this.$message("抓拍失败");
                }
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
.box-content{padding:10px 10px 0;}
</style>
