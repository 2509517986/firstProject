<template>
    <!-- 手动告警 -->
     <el-dialog title="手动告警" :visible="true" :append-to-body="true" class="manualAlarm" @close="close" width="50%">
         <el-row :gutter="20">
             <el-col :span="15">
                 <img  :src="reDomain(alarminfo.imageDir, alarminfo.imageName)" alt="" class="img">
                 <div class="tools" v-if="form.imgType&&form.imgType.length>0 && false">
                   <div class="lt">
                        <!-- 放大 -->
                        <div class="item"><i class="el-icon-zoom-in"></i></div>
                        <!-- 缩小 -->
                        <div class="item"><i class="el-icon-zoom-out"></i></div>
                        <!-- 重置 -->
                        <div class="item"><i class="el-icon-refresh-right"></i></div>
                   </div>
                   <div class="rt">
                       <!-- 绘制区域 -->
                       <el-button type="primary" size="mini">绘制区域</el-button>
                       <!-- 清除区域 -->
                       <el-button type="primary" size="mini">删除选中区域</el-button>
                   </div>
                 </div>
             </el-col>
             <el-col :span="9">
                 <el-form :model="form" label-width="80px" :rules="rules" ref="ruleForm">
                    <el-form-item label="监测点">
                        <el-input :disabled="true" v-model="project"  size="mini"></el-input>
                    </el-form-item>
                    <el-form-item label="录入人">
                        <el-input :disabled="true" v-model="form.captureUser"  size="mini"></el-input>
                    </el-form-item>
                    <el-form-item label="录入时间">
                        <el-date-picker v-model="form.captureTime" style="width:100%;" :disabled="true" type="datetime"  format="yyyy-MM-dd HH:mm:ss" clear-icon="" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"  size="mini"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="责任人" prop="dutyUser">
                        <el-input v-model="form.dutyUser" size="mini"></el-input>
                    </el-form-item>
                    <template v-if="Object.keys(dicts).length>0 && dicts.img_type">
                        <el-form-item label="告警类型" prop="imgType">
                            <el-select v-model="form.imgType" placeholder="请选择" size="mini" style="width:100%;" >
                                <el-option v-for="(e,i) in dicts.img_type.obj" :key="i" :label="e.dictLabel" :value="e.dictValue" ></el-option>
                            </el-select>
                        </el-form-item>
                    </template>
                    <el-form-item label="告警描述" prop="alarmDes">
                        <el-input type="textarea" v-model="form.alarmDes" placeholder="请具体描述" size="mini"></el-input>
                    </el-form-item>
                </el-form>
             </el-col>
         </el-row>        
        <span slot="footer" class="dialog-footer">
            <el-button @click="close" size="mini">取 消</el-button>
            <el-button type="primary" @click="submitForm('ruleForm')" size="mini">确 定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import { add_alarm_picture } from "@/api/senxu/alarm/index";
import { mapGetters } from 'vuex'
import * as global  from "@/utils/global";
export default {
    props: ['alarminfo', 'yuntaiinfo'],
    name: 'manualAlarm',
    data() {
        return {
            options: [ // 告警类型
                {label: "类型一", id:1}
                ,{label: "类型二", id:2}
            ],
            project: "" ,
            date: "",
            form: {
                pictureId: "" // 抓拍图片ID
                // ,yuntaiId: "" // 云台ID
                // ,lineId: "" // 线路ID
                // ,towerId: "" // 杆塔ID
                // ,alamDir: "" // 告警图片目录
                // ,alarmUrl:"" // 告警图片
                // ,factory: "" // 设备厂商
                // ,kind: "" // 设备类型
                // ,captureUser: "" // 抓拍人
                // ,captureTime: "" // 抓拍时间
                // ,presetBit: "" // 拍摄预置位
                ,alarmDes: "" // 告警描述
                ,dutyUser: "" // 责任人
                ,imgType: "" // 告警类型
            },
            rules: {
                dutyUser: [
                    { required: true, message: '请输入责任人', trigger: 'blur' }
                ],
                imgType: [
                    { required: true, message: '请选择告警类型', trigger: 'change' }
                ]
            }
        }
    },
    created() {
        this.project = this.yuntaiinfo.lineName + this.yuntaiinfo.towerName + this.yuntaiinfo.name;
        this.form.captureUser = this.alarminfo.captureUser;
        this.form.captureTime = this.alarminfo.captureTime;
        // this.form.presetBit = this.alarminfo.presetBit;
        this.form.pictureId = this.alarminfo.id;
        // this.form.factory = this.alarminfo.factory;        
        // this.form.alamDir = this.alarminfo.imageDir;
        // this.form.alarmUrl = this.alarminfo.imageName;
        // this.form.lineId = this.alarminfo.lineId;
        // this.form.towerId = this.alarminfo.towerId;
        // this.form.yuntaiId = this.alarminfo.yuntaiId;
        // this.form.kind = this.alarminfo.kind;
    },
    computed: {
        ...mapGetters([
            'name',
            'dicts'
        ])
    },
    methods: {
        close() {
            if(this.$route.name == "alarmManagedetail") {
                this.$parent.visible3 = false;
            } else {
                this.$parent.$parent.visible3 = false;
            }            
        },
        reDomain(url,name) {
            return  url + name;
        },
        submitForm(formName) {
           this.$refs[formName].validate((valid) => {
            if (valid) {
                add_alarm_picture(this.form).then(response => {    
                    if(response.code == 200) {
                        this.msgSuccess(response.msg); 
                        // 逻辑有点乱，以前没思考，这个是告警详情单独处理。
                        if(this.$route.name == "alarmManagedetail") {
                            // console.log("1");
                            this.$parent.getPictureList();
                        } else {    
                            // console.log("2");
                            if(this.alarminfo.type == 'autoplay') {  
                                // console.log("2.1");                     
                                this.$parent.$parent.getPics();
                            } else { 
                                // console.log("2.2"); 
                                this.$parent.$parent.getPictureList();
                                if(this.$parent.$parent.show == 1 || this.$parent.$parent.show == 2) {
                                    // console.log("2.11"); 
                                    this.$parent.$children[1].getPics();
                                } else if(this.$parent.$parent.show == 0) {
                                    // 今日图片列表
                                        // console.log("2.12"); 
                                        this.$parent.$parent.getPictureList();
                                }     
                            }                    
                        }           
                        this.close();
                    }               
                });
            }
        });
        }
    }
}
</script>
<style lang="scss" scoped>
.manualAlarm{
    .img{height:345px;display: block;width:100%;}
    .tools{display: flex;align-items: center;padding-top:10px;
        .lt{display: flex;align-items:center;
            .item+.item{margin-left:5px;}
        }
        .rt{margin-left:auto;}
    }
}
</style>