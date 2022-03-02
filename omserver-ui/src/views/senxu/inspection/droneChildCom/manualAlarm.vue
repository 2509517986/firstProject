<!--
 * @Author: YJY
 * @Date: 2022-02-08 20:47:03
 * @System: proManagement
 * @Company: LESSO
 * @LastEditTime: 2022-02-12 16:43:36
 * @Description: file content
-->
<template>
    <!-- 手动告警 -->
     <el-dialog title="请对当前选择的图片进行处理" :visible="true" :append-to-body="true" class="manualAlarm" @close="close" width="50%">
        
        <el-form :model="form" label-width="160px" class="drone-manual-alarm" :rules="rules" ref="ruleForm">
            <el-form-item label="目标检测">
                 <el-radio-group v-model="form.isCorrect">
                    <el-radio :label="1">正常</el-radio>
                    <el-radio :label="0">异常</el-radio> 
                </el-radio-group>
            </el-form-item>
            <el-form-item label="异常类别">
                 <el-checkbox-group v-model="form.abnormalType">
                    <el-checkbox :label="1">金具</el-checkbox>
                    <el-checkbox :label="2">附属设备</el-checkbox>
                    <el-checkbox :label="3">杆塔</el-checkbox>
                    <el-checkbox :label="4">通道环境</el-checkbox>
                    <el-checkbox :label="5">导地线</el-checkbox>
                    <el-checkbox :label="6">绝缘子</el-checkbox>
                    <el-checkbox :label="7">基础</el-checkbox>
                </el-checkbox-group>
            </el-form-item>
             <el-form-item label="缺陷个数">
                 <el-input type="number"  placeholder="请输入内容" v-model="form.defectCount"  maxlength="10" clearable />
            </el-form-item>
            <el-form-item label="缺陷严重程度">
                 <el-radio-group v-model="form.defectType">
                    <el-radio :label="1">一般</el-radio>
                    <el-radio :label="2">严重</el-radio> 
                    <el-radio :label="3">危急</el-radio> 
                </el-radio-group>
            </el-form-item>
            <el-form-item label="影响线路" >
                 <el-radio-group v-model="form.affectedLine">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio> 
                </el-radio-group> 
            </el-form-item> 
            <el-form-item label="大于导线安全距离" prop="securityDistance">
                <el-radio-group v-model="form.securityDistance">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio> 
                </el-radio-group>
            </el-form-item> 
             <el-form-item label="是否需要下发隐患通知" prop="isInform">
                <el-radio-group v-model="form.isInform">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio> 
                </el-radio-group>
            </el-form-item> 
             <el-form-item label="是否需要现场处理" prop="isScene">
                <el-radio-group v-model="form.isScene">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="0">否</el-radio> 
                </el-radio-group>
            </el-form-item> 
            <el-form-item label="描述" prop="describes">
                <el-input type="textarea" v-model="form.describes" placeholder="请具体描述" size="mini" :rows="2"></el-input>
            </el-form-item>
        </el-form>
          
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
import { set_manual_alarm } from "@/api/senxu/inspection/index";
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
                isCorrect:1,//目标检测
                abnormalType:[],//异常类别
                defectCount:0,//缺陷个数
                defectType:1,//缺陷严重程度
                affectedLine:0,//影响线路
                securityDistance:0,//大于导线安全距离
                isInform:0,//是否下发隐患通知
                isScene:0,//是否现场处理
                describes:'',//描述
                imageId:'',//图片id

                // pictureId: "" // 抓拍图片ID
                // // ,yuntaiId: "" // 云台ID
                // // ,lineId: "" // 线路ID
                // // ,towerId: "" // 杆塔ID
                // // ,alamDir: "" // 告警图片目录
                // // ,alarmUrl:"" // 告警图片
                // // ,factory: "" // 设备厂商
                // // ,kind: "" // 设备类型
                // // ,captureUser: "" // 抓拍人
                // // ,captureTime: "" // 抓拍时间
                // // ,presetBit: "" // 拍摄预置位
                // ,alarmDes: "" // 告警描述
                // ,dutyUser: "" // 责任人
                // ,imgType: "" // 告警类型
            },
            rules: {
                securityDistance: [
                    { required: true, message: '是否大于安全距离必选', trigger: 'change' }
                ],
                isInform: [
                    { required: true, message: '是否下发隐患通知必选', trigger: 'change' }
                ],
                isScene: [
                    { required: true, message: '是否现场处理必选', trigger: 'change' }
                ]
            }
        }
    },
    created() { 
        // console.log(this.alarminfo);
        // 需要将值补充进去
        Object.keys(this.form).forEach(it =>{
            if(it == 'abnormalType' ){
                this.form[it] = this.setValueType( this.alarminfo[it],'Array')
            }else{
                this.form[it] =  Number(this.alarminfo[it]) ?  Number(this.alarminfo[it]) : this.alarminfo[it]
               
            }
        })

        // this.form.captureUser = this.alarminfo.captureUser;
        // this.form.captureTime = this.alarminfo.captureTime;
        // this.form.presetBit = this.alarminfo.presetBit;
        this.form.imageId = this.alarminfo.imageId;
    },
    computed: {
        ...mapGetters([
            'name',
            'dicts'
        ])
    },
    methods: {
        close(e) {
            this.$emit('close',e)
        },
        reDomain(url,name) {
            return  url + name;
        },
        setValueType(val,type){
            if(type == 'String'){
                if(Object.prototype.toString.call(val) == '[object String]'  ){
                    return val
                }else{
                    return val.join(',')
                }
                return 
            }else if(type == 'Array'){
                if(!val){  return []  }
                if(Object.prototype.toString.call(val) == '[object Array]'  ){
                    return val
                }else{
                    return val.split(',').map(Number)
                }
            }
        },
        submitForm(formName) {
           this.$refs[formName].validate((valid) => {
            if (valid) {
                this.form.abnormalType = this.setValueType( this.form.abnormalType,'String') 
                set_manual_alarm(this.form).then(response => {    
                    if(response.code == 200) {
                        this.msgSuccess(response.msg); 
                        // 逻辑有点乱，以前没思考，这个是告警详情单独处理。
                           
                        this.close(true);
                    } 
                    
                    this.form.abnormalType = this.setValueType( this.form.abnormalType,'Array')              
                }).catch(e=>{
                     this.form.abnormalType = this.setValueType( this.form.abnormalType,'Array')       
                    
                });
            }
        });
        }
    }
}
</script>
<style lang="scss" scoped>
.manualAlarm{
   .drone-manual-alarm{
       .el-form-item{
           margin-bottom:5px;
       }
   }
}
</style>