<template>    
  <!-- 告警确认 -->
    <el-dialog title="请对当前选择的告警图片进行处理" :visible="true" :append-to-body="true" class="manualAlarm" @close="close" width="40%">
        <el-form :model="ruleForm" ref="ruleForm" label-width="220px" class="demo-ruleForm"  size="mini">
            <el-form-item label="忽略此告警" prop="isIgnore" required>
                <el-radio-group v-model="ruleForm.isIgnore">
                    <el-radio label="1" size="mini">是</el-radio>
                    <el-radio label="0" size="mini">否</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="目标检测" prop="isCorrect" required>
                 <el-radio-group v-model="ruleForm.isCorrect">
                    <el-radio label="1" size="mini">正确</el-radio>
                    <el-radio label="0" size="mini">错误</el-radio>
                </el-radio-group>
            </el-form-item>
            <template v-if="ruleForm.isCorrect == 1">
                <el-form-item label="影响线路" prop="affectedLine" required>
                    <el-radio-group v-model="ruleForm.affectedLine">
                        <el-radio label="1" size="mini">是</el-radio>
                        <el-radio label="0" size="mini">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="大于导线安全距离" prop="securityDistance" required>
                    <el-radio-group v-model="ruleForm.securityDistance">
                        <el-radio label="1" size="mini">是</el-radio>
                        <el-radio label="0" size="mini">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="是否需要下发隐患通知" prop="isInform" required>
                    <el-radio-group v-model="ruleForm.isInform">
                        <el-radio label="1" size="mini">是</el-radio>
                        <el-radio label="0" size="mini">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="是否需要现场处理" prop="isScene" required>
                    <el-radio-group v-model="ruleForm.isScene"  size="mini">
                        <el-radio label="1">是</el-radio>
                        <el-radio label="0">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="描述" prop="alarmDes">
                    <el-input type="textarea" v-model="ruleForm.alarmDes" placeholder="请具体描述" size="mini"></el-input>
                </el-form-item>  
            </template>         
                <el-form-item>                
                    <el-button @click="close" size="mini">取 消</el-button>
                    <el-button @click="submitForm('ruleForm')" size="mini" type="primary">确认</el-button>
                </el-form-item>
        </el-form> 
    </el-dialog>
</template>
<script>
import { add_alarm_picture,get_batchConfirm } from "@/api/senxu/alarm/index";
export default {
    name: "AlarmConfrim",
    props: ['ids'],
    data() {
        return{
            ruleForm: {ids:undefined, isIgnore:"0",isCorrect:"1",affectedLine:"0",securityDistance:"0",isInform:"0",isScene:"0",alarmDes:""}
        }
    },
    computed: {
    },
    methods: {
        submitForm() {   
            let str = Array.isArray(this.ids)?this.ids.join(','):this.ids;
            this.ruleForm.ids = str;
            get_batchConfirm(this.ruleForm).then(response => {
                if(response.code == 200) {
                  this.$message(response.msg);
                  this.$parent.getPictureList();
                  this.close();
                }
            });
        },
        close() {
            this.$parent.show = false;
        }
    }
}
</script>