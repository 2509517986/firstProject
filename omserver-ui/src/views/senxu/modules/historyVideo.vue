<template>
    <!-- 录像回放 -->
    <!-- <div class="video-box">
        <video ref="videoPlayer" class="video-js vjs-default-skin vjs-big-play-centered videoplayer" webkit-playsinline playsinline x5-playsinline></video>
        <div id="timeBar"><div id="currentBar"><div></div>
        <div id="mycontrols">

        </div>
    </div> -->
    <div class="detali_box_img video-media" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
        <div class="video-example">
            <video class="videoplayer" width="100%" height="100%" preload="auto" ref="videoCon"></video>
        </div>
        <!-- 播放按钮 -->
        <div class="playbtn" v-if="!play" @click="playclick"><i class="el-icon-caret-right"></i></div>
        <!-- 控制条的播放和暂停按钮 -->
        <div class="control-play" v-if="play">
            <p class="control-play-btn" @click="controlVideo">
                <span class="el-icon-video-play" v-show="!vcIsPlay"></span>
                <span class="el-icon-video-pause" v-show="vcIsPlay"></span>
            </p>
            <!-- 播放进度条 -->
            <div class="control-progress common-progress">
                <div>
                    <el-slider v-model="vcProgress" :show-tooltip="false" :max="durationProgress" input-size="small" @change="getNewTime"></el-slider>
                </div>
            </div>
            <!-- 当前播放时间 -->
            <div class="current-time">{{vcCurrentTime}}</div>
            <span class="split">/</span>
            <!-- 视频总时长 -->
            <div class="duration">{{duration_time}}</div>
            <!-- 倍速控制 -->
            <div class="video-speed-box">
                <el-dropdown placement="bottom" @command="handleCommand">
                    <span class="video-speed-show">{{speedTimeList[speedTime].com}}</span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item v-for="(e,i) in speedTimeList" :key="i" :command="i">{{e.com}}</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
    </div>
</template>
<script>

// Scale：回放速度，当为0 时，表示暂停；
// 当为1时，正常普通播放；
// 大于1，表示快进，2倍，4倍，8倍，16倍；
// 小于1，表示慢放，1/2倍，1/4倍， 1/8；
// OFFSET：为0，表示首次播放时，从当前录像文件最开始播放；如果大于0，则表示偏移的指定位置开始播放，如果暂停后，继续播放。
import videojs from "video.js"
import 'video.js/dist/video-js.css'
import 'videojs-flash'
import { historyvideo_play, historyvideo_stop, historyvideo_control, historyvideo_getCurPlayTime} from "@/api/senxu/yuntai_video/index";

export default {
    name: "HistoryVideoPlayer",
    props: {historyVideoObj:{default:{},type: Object},yuntai:{default:{},type: Object}},
    data () {
        return {
            play: false,
            loading: false,
            videoObj: null,
            vcIsPlay: false, //是否播放
            currentTimeVal: 0, // 当前时间
            //当前时间格式化
            vcCurrentTime: '00:00:00',
            //进度条的绑定时间
            vcProgress: 0,
            durationProgress: 0, //当前视频的总时长
            speedTime: 0, //倍速index
            speedTimeList: [//倍速 -- 后端没开发
                {com: '1X', val: '1'}
                // ,{com: '4X', val: '4'}
                // ,{com: '8X', val: '8'}
                // ,{com: '16X', val: '16'}
                // ,{com: '1/2X', val: '0.5'}
                // ,{com: '1/4X', val: '0.25'}
                // ,{com: '1/8X', val: '0.125'}
            ],
            duration_time: "00:00:00",
            speedTT: 0, // 倍速
            timmer: ''
        }
    },
    mounted() {
      console.log(this.historyVideoObj);
        this.loading = false;
        this.videoObj = null;
        this.vcIsPlay = false;
        this.speedTT = this.speedTimeList[this.speedTime].val;
        this.init();
    },
    methods: {
        /*从服务器获取当前时间，并设到进度中*/
        getCurPlayTime(cb) {
            historyvideo_getCurPlayTime({code: this.yuntai.code}).then((response => {
                if(response.code == 200) {
                    this.vcProgress = response.data.nowtimes;
                    console.log(this.vcProgress);
                    this.vcCurrentTime = this.getFormatVideoTime(this.vcProgress);
                    if(cb!=null){
                      cb();
                    }

                }
            }));
        },
        //定时获取时间，同时也会设到进度条
        progresstime() {
            var self=this;
            this.timmer = setTimeout(() => {
                //当超过总时间
                if((this.vcProgress + 1)>this.durationProgress) {
                    //停止定时器
                    clearTimeout(this.timmer);
                    //发送停止出流指令
                    historyvideo_stop({filename:this.historyVideoObj.filename, code:this.yuntai.code}).then(response => {
                      /*if(response.code == 200) {
                        this.loading = false;
                      }*/
                      this.loading = false;
                      self.videoObj.stop();
                    });
                    //停止控件

                   // self.videoObj.stop();
                    self.play=false;
                    return;
                }
                //没超过总时间 获取当前时间再递归执行


                this.getCurPlayTime();
                this.progresstime();
            }, 1000);
        },

        init() {
            if(Object.keys(this.historyVideoObj).length>0){
                let options = {
                    live: true,
                    autoplay: false,
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
                    width: '100%',
                    sources: [{
                        type: 'rtmp/mp4',
                        src: this.historyVideoObj.videoUrl
                    }]
                }
                this.durationProgress = this.historyVideoObj.duration;
                this.videoObj = videojs(this.$refs.videoCon,options);
                /*this.videoObj = videojs(this.$refs.videoCon, {
                    ...options,
                });*/
            }
        },
        playclick() {
            // 触发播放拉流
            this.loading = true;
            this.play = true;
            this.vcProgress=0;
            this.duration_time = this.getFormatVideoTime(this.durationProgress);
             historyvideo_play({filename:this.historyVideoObj.filename, code:this.yuntai.code}).then(response => {
                if(response.code == 200) {
                    console.log(this.yuntai.code + '开始播放');
                    this.playControl();
                }
            }).catch(error => {
                this.loading = false;
                this.play = false;
            });

          /**
           //先发送停止出流指令
           historyvideo_stop({filename:this.historyVideoObj.filename, code:this.yuntai.code}).then(response => {

          self.loading = false;
          if(self.videoObj){
            self.videoObj.stop();
          }
          // 触发播放拉流
          self.loading = true;
          self.play = true;
          self.vcProgress=0;
          self.duration_time = this.getFormatVideoTime(this.durationProgress);
          historyvideo_play({filename:self.historyVideoObj.filename, code:self.yuntai.code}).then(response => {
            if(response.code == 200) {
              console.log(self.yuntai.code + '开始播放');
              this.playControl();
            }
          }).catch(error => {
            self.loading = false;
            self.play = false;
          });
        });
           */
        },

      pauseControl(){
          console.info("暂停，删除定时器，停止进度条");
          this.loading = false;
          this.vcIsPlay = false;
          clearTimeout(this.timmer);
          this.videoObj.pause();
        },

        playControl(){
          console.info("打开定时器，开始播放");
          this.loading = false;
          this.vcIsPlay = true;
          this.progresstime();
          this.videoObj.play();

        },
        /**
         * 暂停与开始
         * */
        controlVideo() {
            //播放紧进行暂停
            if (this.vcIsPlay) {
               this.speedTT = 0;//暂停的参数
               // this.pause=1;
                this.pauseControl();
                console.info("通知后台停止拉流");
                this.historyvideoControl();

            }
            //停止时重新开始播放
            else {
               // this.loading = true;
                //获取当前速度
                this.speedTT = this.speedTimeList[this.speedTime].val;
                var self=this;
                this.historyvideoControl(function () {
                  self.playControl();
                });

            }
            //切换是否在播放标志
            //this.vcIsPlay = !this.vcIsPlay;
        },
        getFormatVideoTime(time) {
            let curtime = time
            let h = parseInt(curtime / 3600)
            let m = parseInt((curtime % 3600) / 60)
            let s = parseInt(curtime % 60)
            h = h < 10 ? '0' + h : h
            m = m < 10 ? '0' + m : m
            s = s < 10 ? '0' + s : s
            return h + ':' + m + ':' + s
        },
        /*
        跳转到新的时间
        (值改变时触发（使用鼠标拖曳时，只在松开鼠标后触发）)
         */
        getNewTime(val) {
            console.info("新的进度值："+val);
            this.vcCurrentTime = this.getFormatVideoTime(val);
            if (this.vcIsPlay) {
                //这里不用pauseControl是因为不想vcIsPlay发生变化
               //先暂停
                this.speedTT = 0;//暂停的参数
                // this.pause=1;
                clearTimeout(this.timmer);
                this.videoObj.pause();
                console.info("通知后台停止拉流");
                this.historyvideoControl();
                //2秒后再拉流
                var self=this;
                setTimeout(function () {

                  self.speedTT = 1;
                  self.historyvideoControl(function () {
                      self.playControl();
                    });
                },2000);
             }
            //先关控件，直到有数据再开控件
           // this.pauseControl();

        },
        historyvideoControl(successFunc) {
          //先获取了当前时间，再控制//不能先获取当前时间，不然拖动不了
          // this.getCurPlayTime(function(){
               this.loading = true;
               console.info("offset:"+this.vcProgress);
               historyvideo_control({filename:this.historyVideoObj.filename,offset:this.vcProgress, scale: this.speedTT, code:this.yuntai.code}).then(response => {
                 if(response.code == 200) {
                   this.loading = false;
                   if(successFunc!=null){
                     successFunc();
                   }

                 }
                 this.loading =false;
               }).catch(error => {
                 this.loading = false;
                 this.play = false;
               });
          // });

        },
        handleCommand(a) {
            this.speedTime = a;
            this.speedTT = this.speedTimeList[this.speedTime].val;
            this.historyvideoControl();
        }
    },
    destroyed() {
        if(this.videoObj) {
            this.videoObj.dispose();
            clearTimeout(this.timmer)
        }
    }
}
</script>
<style lang="scss" scoped>
.video-media{display:flex; flex-direction: column;flex:1;position: relative;overflow: hidden;
    .playbtn{display: flex;width:50px;height:50px;border-radius:8px;border:1px solid #fff;background:rgba(255,255,255,0.5);position: absolute;top:50%;margin-top:-25px;left:50%;margin-left:-25px;color:rgba(0,0,0,0.8);font-size:30px; align-items: center; justify-content: center;}
    .videoplayer{object-fit:fill;width:100%;height:100%;overflow: hidden;padding:0;position: relative;&>div{position: absolute;top: 0;left: 0;width: 100%;height: 100%;}}
    .video-example{flex:1;overflow: hidden;}
    .play-btn{position: absolute;}
    .control-play{height:50px;background:#f3f4f9;display:flex;align-items:center;padding:0 12px;
        .control-play-btn{font-size:18px;color:#9b9ca0;}
        .control-progress{flex:1;padding:0 20px;}
        .current-time,.duration{font-size:12px;color: #b4b5b9;}
        .split{display:inline-block;padding:0 10px;font-size:12px;color: #b4b5b9;}
        .video-speed-box{margin:0 15px;
            .video-speed-show{background:#dbe2ed;color: #9b9ca0;font-size:12px;border-radius:2px;width:25px;height:18px;text-align:center;line-height:18px;display:block;}
        }
    }
}
</style>
