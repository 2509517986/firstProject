<template>
    <!-- 手动抓拍图片 -->
    <el-dialog title="手动抓拍" :visible="true" class="manualcapture" @close="close" width="30%">
        <el-radio-group v-model="postdata.manualCapType">
            <el-radio v-for="(e,i) in list" :key="i" :label="e.id" size="mini">{{e.name}}</el-radio>
        </el-radio-group>
        <!-- 设置预置位 -->
        <div class="iptContainer" v-if="postdata.manualCapType == 2">
            <span>预置位</span>
            <!-- <el-input v-model="preset_bit" size="mini"></el-input> -->
             <el-input-number style="width:120px;margin-left:10px;" v-model="preset_bit" :min="0" label="1" size="mini" :max="255"></el-input-number>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="close" size="mini">取 消</el-button>
            <el-button type="primary" @click="manualCap" size="mini">确 定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import { add_picture,edit_picture,get_picture_detail,del_picture,export_picture,get_pictureList,manualCap, get_pictureListByDay,yuntaiPreview,get_pictureListByDetails,get_pictureHistory } from "@/api/senxu/yuntai_picture/index";
export default {
    name: 'ManualCaptureImg',
    props:['yuntaiinfo'],
    data () {
        return {
            preset_bit: 0,
            list: [{id:0,name:"当前位置抓拍"}, {id:1, name:"抓拍所有预置位"}, {id:2, name: "预置位抓拍"}],
            postdata: {manualCapType:0, presetBit:undefined,yunTaiId:undefined,async:0}
        }
    },
    methods: {
        close() {
            this.$parent.$parent.visible = false;
        },
        manualCap() {
            // 手动抓拍
            // const loading = this.$loading({lock: true,text: '正在抓拍图片中，请稍候！',spinner: 'el-icon-loading', background: 'rgba(0, 0, 0, 0.7)'});            
            this.postdata.presetBit = this.postdata.manualCapType == 2?parseInt(this.preset_bit):0;
            this.postdata.yunTaiId = this.yuntaiinfo.id;
            let formData = new FormData();
            formData.append('manualCapType',this.postdata.manualCapType);
            formData.append('presetBit',this.postdata.presetBit);
            formData.append('yunTaiId',this.postdata.yunTaiId);
            formData.append('async',0);
            manualCap(formData).then(response => {
                if(response.code == 200) {
                    if(this.$parent.$parent.show == 1 || this.$parent.$parent.show == 2) {
                        this.$parent.$children[1].getPics();
                    } else if(this.$parent.$parent.show == 0) {
                         this.$parent.$parent.getPictureList();
                    }
                    this.$message({
                        type:"success",
                        message:"拍照成功"
                    });
                    this.close();
                    // loading.close();
                } else {
                    // loading.close();
                    this.msgSuccess("抓拍失败");
                }
            });
        }
    }
}
</script>
<style lang="scss" scoped>
.manualcapture {
    ::v-deep{
        .el-dialog__body{
            .el-radio-group{
                .el-radio{display:block;}
                .el-radio +.el-radio{margin-top:20px;}
            }
        }
        .el-slider{flex:1;padding-left:20px;margin: 0 auto;}
        .el-input-number__decrease,.el-input-number__increase{display: none;}
        .el-slider__runway.show-input{margin-right:50px;}
        .el-input-number{width:40px;}
        .el-input__inner{padding-left:5px;}
        .el-input-number .el-input__inner{text-align:left;}
    }
    .iptContainer{display:flex;align-items: center;margin-top:20px;
        span{padding-left:25px;}
        .el-input{width:30%;margin-left:10px;}

    }
}
</style>