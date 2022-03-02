<template>
    <!-- 手动抓拍小视频 -->
     <el-dialog title="手动抓拍小视频" :visible="true" class="manualcapture" @close="close" width="30%">
       <div class="con">
           <el-slider v-model="duration" :show-tooltip="false" :min="1" :step="5" :max="60" show-input size="mini"></el-slider>
           <span>秒</span>
       </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="close" size="mini">取 消</el-button>
            <el-button type="primary" @click="sure" size="mini">确 定</el-button>
        </span>
    </el-dialog>
</template>
<script>
import { capShortVideo } from "@/api/senxu/yuntai_picture/index";
export default {
    name: 'ManualCaptureVideo',
    props:['yuntaiinfo'],
    data () {
        return {
            duration: 20
        }
    },
    methods: {
        close() {
            this.$parent.$parent.visible2 = false;
        },
        sure() {
            let formData = new FormData();
            formData.append('presetBit', 0);
            formData.append('yunTaiId',this.yuntaiinfo.id);
            formData.append('time', this.duration);
            capShortVideo(formData).then(response => {
                if(response.code == 200) {
                   this.$message({
                       type: "success",
                       message: "抓拍"+this.duration+"s小视频成功"
                   })
                } else {
                    this.$message("抓拍失败");
                }
            });
            this.close();
        }
    }
}
</script>
<style lang="scss" scoped>
.manualcapture {
    ::v-deep{
        .el-input-number__decrease,.el-input-number__increase{display: none;}
        .el-slider__runway.show-input{margin-right:80px;}
        .el-input-number{width:60px;}
        .el-input__inner{padding:0;}
    }
    .con{display:flex;align-items: center;
        .el-slider{flex:1;}
        span{margin:0 10px;}
    }
}
</style>