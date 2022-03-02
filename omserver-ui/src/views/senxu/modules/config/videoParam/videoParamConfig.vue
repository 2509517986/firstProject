<template>
    <!-- 视频参数-视频参数配置 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏视频参数配置" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>视频参数配置</span>
            <el-button size="mini" type="primary" class="querybtn" @click="query" :disabled="fromTreeData.node[0].yuntais.length<0">查询设备</el-button>
        </div>
        <div class="box-content"  v-loading="open && Object.keys(thisYuntaiInfo).length>0">
            <el-form ref="form" :model="form" label-width="80px" size="mini">
                <el-form-item label="通道号">
                   <span class="channel">{{form.videoConduit?form.videoConduit:0}}</span>
                </el-form-item>
                <el-form-item label="通道类型">
                    <!-- <el-select v-model="form.videoConduitType" placeholder="请选择" style="width:100%" size="mini">
                        <el-option v-for="item in dicts.video_conduit_type.obj" :key="item.dictValue" :label="item.dictLabel" :value="item.dictValue"></el-option>
                    </el-select> -->
                     <el-radio-group v-model="form.videoConduitType">
                        <el-radio label="0">主通道</el-radio>
                        <el-radio label="1">从通道</el-radio>
                        <el-radio label="2">第3码率</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="频率">
                    <el-slider size="mini" :show-tooltip="false" :min="1" show-input input-size="mini" :max="30" v-model="form.videoRate"></el-slider>
                </el-form-item>                
                <el-form-item label="1帧间隔">
                    <el-slider size="mini" :show-tooltip="false" :min="1" show-input input-size="mini" :max="200" v-model="form.videoFrameInterval"></el-slider>
                </el-form-item>
                 <el-form-item label="编码方式">
                    <el-select v-model="form.videoEncoded" placeholder="请选择" style="width:100%" size="mini">
                        <el-option v-for="item in dicts.video_encoded.obj" :key="item.dictValue" :label="item.dictLabel" :value="item.dictValue"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="位率类型">
                    <el-radio-group v-model="form.videoBitrateType">
                        <el-radio :label="item.dictValue" v-for="item in dicts.video_bitrate_type.obj" :key="item.dictValue">{{item.dictLabel}}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item :label="'码率\n(Kbps)'">
                    <el-slider size="mini" v-model="form.videoBitrate" :show-tooltip="false" :min="16" :max="10000" show-input input-size="mini"></el-slider>
                </el-form-item>
                <el-form-item label="分辨率" porp="videoResolution">
                    <el-select v-model="form.videoResolution" placeholder="请选择" style="width:100%" size="small">
                        <el-option v-for="item in dicts.resolution.obj" :key="item.dictValue" :label="item.dictLabel" :value="item.dictValue"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="mini" @click="onSubmit" :disabled="Object.keys(thisYuntaiInfo).length<1 || open">设置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </el-card>
</template>
<script>
import * as global  from "@/utils/global";
import { mapGetters } from 'vuex'
import { getVideo,saveVideo } from "@/api/senxu/config/index";
export default {
    props: ['favIcon', 'treeData'],
    name: 'videoParamConfig',
    data () {
        return{
            open: true,
            act:false,
            fromTreeData: {},
            form: {videoConduit:"0",videoConduitType:"0",videoRate:1, videoFrameInterval:1,videoEncoded:"0",videoBitrateType:"0",videoBitrate: 16,videoResolution:"1"},
            thisYuntaiInfo: {}
        }
    },
    computed: {
        ...mapGetters([
            'dicts',
        ])
    },  
    watch: {
        treeData:{
            handler (newVal){
                this.open = true;
                this.form = {videoConduit:"0",videoConduitType:"0",videoRate:1, videoFrameInterval:1,videoEncoded:"0",videoBitrateType:"0", videoBitrate: 16,videoResolution:"1"};
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
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        onSubmit() {
            this.form['yuntaiId'] = this.thisYuntaiInfo.id;
            saveVideo(this.form).then(response => {
                if(response.code == 200) { 
                    this.$message.success(response.msg);
                } else {
                    this.$message(response.msg);
                }
            });
        },
        query() {
            // 查询设备
            this.thisYuntaiInfo = {};
            this.open = true;
            // this.thisYuntaiInfo['id'] = 1; // 测试
            for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];
                    this.getVideo(this.thisYuntaiInfo.id);
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
        },
        getVideo(id) {
            getVideo(id).then(response => {
                if(response.code == 200 && response.TYuntaiConfigVideo) {   
                    // this.form = response.TYuntaiConfigVideo; 
                    this.open = false;
                    this.form = {
                        videoConduit: response.TYuntaiConfigVideo.videoConduit?response.TYuntaiConfigVideo.videoConduit:"0",
                        videoConduitType:response.TYuntaiConfigVideo.videoConduitType?response.TYuntaiConfigVideo.videoConduitType:"0"
                        ,videoRate:response.TYuntaiConfigVideo.videoRate?parseInt(response.TYuntaiConfigVideo.videoRate):1
                        ,videoFrameInterval: response.TYuntaiConfigVideo.videoFrameInterval?parseInt(response.TYuntaiConfigVideo.videoFrameInterval):1
                        ,videoEncoded:response.TYuntaiConfigVideo.videoEncoded?response.TYuntaiConfigVideo.videoEncoded:"0"
                        ,videoBitrateType:response.TYuntaiConfigVideo.videoBitrateType?response.TYuntaiConfigVideo.videoBitrateType:"0"
                        ,videoBitrate:response.TYuntaiConfigVideo.videoBitrate?parseInt(response.TYuntaiConfigVideo.videoBitrate):16
                        ,videoResolution:response.TYuntaiConfigVideo.videoResolution?response.TYuntaiConfigVideo.videoResolution:"1"
                    }
                } 
            })
        }            
    }
}
</script>
<style lang="scss" scoped>
.head{display:flex;align-items:center;
    .fav{margin-right:5px;cursor: pointer;font-size:20px;color:$base-color-default;}
    .querybtn{margin-left:auto;}
}
.box-content{padding:10px 120px 0;
    ::v-deep{                     
        .el-form-item__label{line-height:34px;}             
        // el-silder
        .el-slider{flex:1;padding-left:10px;margin: 0 auto;}
        .el-input-number__decrease,.el-input-number__increase{display: none;}
        .el-slider__runway.show-input{margin-right:50px;}
        .el-input-number{width:40px;}
        .el-input__inner{padding-left:5px;}
        .el-slider__input .el-input__inner{padding:0px;}
    }
    .channel{border-radius: 3px;background:$base-color-default;color:#fff;display:inline-block;width:38px;height:28px;line-height: 28px;text-align:center;}
}
</style>