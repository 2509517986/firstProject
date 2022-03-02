<!--
 * @Author: YJY
 * @Date: 2022-02-14 20:43:27
 * @System: proManagement
 * @Company: LESSO
 * @LastEditTime: 2022-02-14 21:06:23
 * @Description: file content
-->
<template>
    <!-- 手动告警 -->
     <el-dialog title="远程喊话" :visible="true" :append-to-body="true" class="remoteCall" @close="close" width="50%">
        
        <el-form :model="form" label-width="80px" class="remoteCall-form" :rules="rules" ref="ruleForm">
            <el-form-item label="声音" prop="soundType">
                 <el-radio-group v-model="form.soundType">
                    <el-radio :label="2">男声</el-radio>
                    <el-radio :label="1">女声</el-radio> 
                </el-radio-group>
            </el-form-item>
          
             <el-form-item label="循环次数" prop="circulateCount">
                 <el-input type="number"  placeholder="请输入内容" v-model="form.circulateCount"  maxlength="10" clearable />
            </el-form-item>
            
            
            <el-form-item label="喊话内容" prop="content">
                <el-input type="textarea" v-model="form.content" placeholder="请输入喊话内容" size="mini" :rows="2"></el-input>
            </el-form-item>
        </el-form>
          
        <span slot="footer" class="dialog-footer">
            <el-button @click="close" size="mini">取 消</el-button>
            <el-button type="primary" @click="submitForm('ruleForm')"  v-loading="loading" size="mini">确 定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import { mapGetters } from 'vuex'
import { originTalking } from "@/api/senxu/devicemanager/line";
export default {
    props: ['remoteCallObj'],
    name: 'remoteCall',
    data() {
        return {
            
            form: {
                uavId: '',
                soundType: 1,
                circulateCount: '',
                content: ''

               
            },
            rules: {
                soundType: [
                    { required: true, message: '请选择声音', trigger: 'change' }
                ],
                circulateCount: [
                    { required: true, message: '请输入循环计数', trigger: 'change' }
                ],
                content: [
                    { required: true, message: '请输入喊话内容', trigger: 'change' }
                ]
            },
            loading:false,
        }
    },
    created() { 
        // console.log(this.alarminfo);
        // 需要将值补充进去
        // Object.keys(this.form).forEach(it =>{
        //     if(it == 'abnormalType' ){
        //         this.form[it] = this.setValueType( this.alarminfo[it],'Array')
        //     }else{
        //         this.form[it] =  Number(this.alarminfo[it]) ?  Number(this.alarminfo[it]) : this.alarminfo[it]
               
        //     }
        // })

        this.form.uavId = this.remoteCallObj.uavId;
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
      
        submitForm(formName) {
           this.$refs[formName].validate((valid) => {
            if (valid) {
                this.loading =  true
                originTalking(this.form).then(res => {
                    this.loading= false
                        this.close(true);
                })
               
            }
        });
        }
    }
}
</script>
<style lang="scss" scoped>
.remoteCall{
   .remoteCall-form{
       .el-form-item{
           margin-bottom:22px;
       }
   }
}
</style>