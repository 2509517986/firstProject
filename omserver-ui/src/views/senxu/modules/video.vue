<template>
    <div class="video-box">
        <video ref="videoPlayer" class="video-js vjs-default-skin vjs-big-play-centered videoplayer" webkit-playsinline playsinline x5-playsinline></video>
        <div class="playbtn" v-if="!play" @click="playclick"><i class="el-icon-caret-right"></i></div>
        <div class="mask"  v-else>
            <div class="tools">
                <div class="icon" @click="playStopClick">
                    <el-tooltip class="item" effect="dark" :content="!play2?'播放':'暂停'" placement="top-end" size="mini">
                        <i :class="!play2?'el-icon-video-play':'el-icon-video-pause'"></i>
                    </el-tooltip>
                </div>
                <div class="icon" @click="photoCaptur">
                    <el-tooltip class="item" effect="dark" content="抓拍图片" placement="top-end" size="mini">
                        <svg-icon icon-class="photography"/>
                    </el-tooltip>
                </div>
                <div class="icon" @click="videoCaptur">
                    <el-tooltip class="item" effect="dark" content="抓拍小视频" placement="top-end" size="mini">
                        <svg-icon icon-class="video" />
                    </el-tooltip>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import videojs from "video.js"
import 'video.js/dist/video-js.css'
import 'videojs-flash'
import { getVideoInfo, remoteControl, sendPlayCmd, sendStopCmd, setPicParams } from "@/api/senxu/yuntai_video/index";
import { manualCap, capShortVideo } from "@/api/senxu/yuntai_picture/index";

export default {
    name: "VideoPlayer",
    props: {videoSrc:{default:'',type: String},yuntai:{default:{},type: Object}, thisPresetBit:{default:{},type:Object},control:{default:{},type:Object}},
    data () {
        return {
            play:false,
            play2:true,
            videoPlayer: null,
            loading:true,
            stop: false,
            time:20,
            timeout: ""
        }
    },
    mounted() {
        this.videoPlayer = null;
        this.handelVideoUrl();
    },
    watch: {
        yuntai: {
            handler(newval,oldval) {
                if(Object.keys(oldval).length<1) return;
                this.sendStopCmd(oldval.code);
            },
            deep: true
        }
    },
    methods: {
        playclick() {
            // 播放，拉流
            this.play = true;
            this.sendPlayCmd();
        },
        playStopClick() {
            if(!this.play2){
                this.videoPlayer.play();
                this.sendPlayCmd();
                this.play2 = true;
            }else{
                this.videoPlayer.pause();
                this.sendStopCmd();
                this.play2 = false;
            }
        },
        sendStopCmd() {
            // 停止播放
            sendStopCmd({deviceCode :this.yuntai.code}).then(response => {
                if(response.code == 200) {
                    console.log(this.yuntai.code + '停止播放');
                    this.videoPlayer.on("ended",function(e){
                    })
                }
            })
        },
        sendPlayCmd () {
            this.loading = true;
            console.info("1111");
            sendPlayCmd({deviceCode :this.yuntai.code,videoProtocol: this.control.videoProtocol}).then(response => {
                if(response.code == 200) {
                    this.loading = false;
                    console.log(this.yuntai.code + '开始播放')
                }
            })
        },
        handelVideoUrl () {
            if(this.videoSrc){
                let options = {
                    live: true,
                    autoplay: true,
                    fluid: false,
                    muted:false,
                    // notSupportedMessage: "暂时无法播放",
                    controlBar: {
                        timeDivider: true,
                        durationDisplay: true,
                        remainingTimeDisplay: false,
                        fullscreenToggle: true
                    },
                    techOrder: ['html5','flash'],
                    flash: {
                      hls: {withCredentials: false},
                      swf: '/static/video-js.swf'
                    },
                    sources: [{
                        type: 'rtmp/mp4',
                        src: this.videoSrc
                    }]
                }
                this.videoPlayer = videojs(this.$refs.videoPlayer, {
                    ...options,
                });
                // this.videoPlayer.on('fullscreenchange', (e) => {
                //     if(document.fullscreenElement) {
                //     } else {
                //         this.$baseEventBus.$off("setFullScreen");
                //     }
                // })
            }
        },
        setFullScreen() {
            // 全屏
            this.videoPlayer.requestFullscreen();
            this.videoPlayer.isFullscreen(true);
        },
        photoCaptur () {
            // 抓拍图片
            let formData = new FormData();
            formData.append('manualCapType',0);
            formData.append('presetBit',Object.keys(this.thisPresetBit).length>0&&this.thisPresetBit.preset_bit?this.thisPresetBit.preset_bit:0);
            formData.append('yunTaiId',this.yuntai.id);
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
            formData.append('presetBit',Object.keys(this.thisPresetBit).length>0&&this.thisPresetBit.preset_bit?this.thisPresetBit.preset_bit:0);
            formData.append('yunTaiId',this.yuntai.id);
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
    },
    destroyed() {
        if(this.videoPlayer) {
            this.videoPlayer.dispose();
        }
    }
}
</script>
<style lang="scss" scoped>
.video-box{width:100%;height:100%;position: relative;
    .videoplayer{object-fit:fill;width:100%;height:100%;overflow: hidden;padding:0;position: relative;&>div{position: absolute;top: 0;left: 0;width: 100%;height: 100%;}}
    .playbtn{display: flex;width:50px;height:50px;border-radius:8px;border:1px solid #fff;background:rgba(255,255,255,0.5);position: absolute;top:50%;margin-top:-25px;left:50%;margin-left:-25px;color:rgba(0,0,0,0.8);font-size:30px; align-items: center; justify-content: center;}
    .mask{background:rgba(0, 0, 0, 0.7);height:30px;z-index: 3;position: absolute;bottom:0;width:100%;display:none;padding:0 10px;font-size:16px;
        .tools{display:flex;margin-left:auto;color:#fff;
           .icon{margin-left:10px;cursor: pointer; &.num{transform: scale(0.8);}}
        }
    }
    &:hover .mask{display:flex; align-items: center;}
}
</style>
