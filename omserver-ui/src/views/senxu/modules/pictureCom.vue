<template>
    <!-- 图片详情自定义组件 -->
    <div class="pictureBox">
        <div class="swiper-box">
            <div v-if="pics.length>0" :class="['swiper-gallery', {hover: hover}]" @mouseenter.stop.prevent="mouseenter" @mouseleave.stop.prevent="mouseleave">
                <swiper v-show="showSwiper" class="swiper gallery-top swiper-no-swiping" :options="swiperOptionTop" ref="swiperTop">
                    <swiper-slide v-for="(e,i) in pics" :key="i" :data-swiper-autoplay="value">
                        <div class="img-box" @mousewheel.prevent="rollImg"><img class="swiper-lazy" style="transform:scale(1)" :data-src="e.src" :ref="img_index ==i?'imgDiv':''" alt="" @mousedown="move"></div>
                        <div class="swiper-lazy-preloader"></div>
                    </swiper-slide>
                </swiper>
                <el-button type="primary" icon="el-icon-arrow-left" circle class="arrowleft arrowicon" @click="noautoplay"></el-button>
                <el-button type="primary" icon="el-icon-arrow-right" circle class="arrowright arrowicon" @click="noautoplay"></el-button>
                <!-- 预置位上一个 -->
                <el-button type="primary" icon="el-icon-arrow-up" circle class="arrowtop arrowicon" @click="less_preset_init()"></el-button>
                <!-- 预置位下一个 -->
                <el-button type="primary" icon="el-icon-arrow-down" circle class="arrowbottom arrowicon" @click="add_preset_init()"></el-button>
                
                <div class="top_hover">
                    <div class="info">
                         <span class="title">35kV良吕线#04 [3373/4366]<font>下方塔基</font>2021-05-12 13:20:20&nbsp;&nbsp;定时抓拍</span>
                    </div>
                    <div class="tools">
                        <!-- 下载 -->
                        <div class="item" @click="downloadImg"><i class="el-icon-download"></i></div>
                        <!-- 放大 -->
                        <div class="item" @click="zoombig"><i class="el-icon-zoom-in"></i></div>
                        <!-- 缩小 -->
                        <div class="item" @click="narrow"><i class="el-icon-zoom-out"></i></div>
                        <!-- 重置 -->
                        <div class="item" @click="reset"><i class="el-icon-refresh-right"></i></div>
                        <div class="item" @click="alarmHandling" v-if="false">
                            <el-button size="mini" type="warning" @click="visible=true">告警处理</el-button>
                        </div>
                        <div class="item" @click="ManualAlarm">
                            <el-button size="mini" type="danger" @click="visible2=true">手动告警</el-button>
                        </div>
                        <div class="item">
                            <span>翻页间隔(秒)</span>
                            <el-select v-model="value" @change="currentSel" placeholder="请选择" size="mini">
                                <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
                            </el-select>
                            <el-button size="mini" @click="autoplay_click" type="primary">{{autoplay?'停止':'播放'}}</el-button>
                        </div>
                        <div class="item" v-if="imgcheck && imgcheck.single">
                            <el-button size="mini" type="primary" @click="close">关闭</el-button>
                        </div>
                    </div>
                </div>
            </div>
            <div v-else>暂无图片</div>
            <!-- swiper2 Thumbs -->
            <div class="swiper-thumbnail" v-if="pics.length>0"  @click.stop="noautoplay">
                <div class="swiper gallery-thumbs">
                    <div :class="['swiper-slide', {act:img_index == i}]" v-for="(e,i) in pics" :key="i" @click="slideto(i)">                        
                        <div class="box">
                            <span class="title">{{e.name}}</span>
                            <img :src="e.src" :alt="e.name">
                        </div>
                    </div>
                </div>
                <div class="thumbnail-arrow thumbnail-arrow-left" @click="lesspageto()"><i class="el-icon-arrow-left"></i></div>
                <div class="thumbnail-arrow thumbnail-arrow-right" @click="addpageto()"><i class="el-icon-arrow-right"></i></div>
            </div>
        </div>
        <!-- 告警处理-->
        <alarm-handling v-if="visible" :alarmVisible="visible" @setValue="setValue"></alarm-handling>
        <!-- 手动告警 -->
        <manual-alarm v-if="visible2" :alarmVisible="visible2" @setValue="setValue"></manual-alarm>
    </div>
</template>
<script>
import { Swiper, SwiperSlide} from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'
import * as global from '@/utils/global'
export default {
    name: 'pictureCom',
    components: {Swiper,SwiperSlide},
    props: ['imgcheck'],
    data () {
        let self = this;
        return {
            downloadImgInfo: { // 下载图片信息
                url:"",
                name: ""
            },
            timmer: "",
            showSwiper: true,
            visible: false, // 告警处理显示/隐藏
            visible2: false, // 手动告警显示/隐藏
            preset_bit: '', // 预置位
            img_index: '0', // 图片索引
            page: 1, // 页码
            pageSize: 5, // 共几页
            count: '10', // 每页显示几个
            autoplay: false,
            pics: [
                // {src: require('$img/ccc/9.png'),name: "2021-05-11 01:00:00", id:1, preset_bit: '5'}
                // ,{src: require('$img/ccc/1.png'),name: "2021-05-11 02:00:00", id:2, preset_bit: '5'}
                // ,{src: require('$img/ccc/2.png'),name: "2021-05-11 02:00:00", id:3, preset_bit: '5'}
                // ,{src: require('$img/ccc/3.png'),name: "2021-05-11 04:00:00", id:4, preset_bit: '5'}
                // ,{src: require('$img/ccc/4.png'),name: "2021-05-11 05:00:00", id:5, preset_bit: '75'}
                // ,{src: require('$img/ccc/5.png'),name: "2021-05-11 06:00:00", id:6, preset_bit: '55'}
                // ,{src: require('$img/ccc/6.png'),name: "2021-05-11 07:00:00", id:7, preset_bit: '45'}
                // ,{src: require('$img/ccc/7.png'),name: "2021-05-11 08:00:00", id:8, preset_bit: '35'}
                // ,{src: require('$img/ccc/8.png'),name: "2021-05-11 09:00:00", id:9, preset_bit: '25'}
                // ,{src: require('$img/ccc/10.png'),name: "2021-05-11 10:00:00", id:10, preset_bit: '15'}
            ],
            hover: false,
            swiperOptionTop: {
                spaceBetween: 0,                
                lazy: true,  
                observer:true,
                observeParents:true,
                updateOnWindowResize: true,
                navigation: {
                    nextEl: '.arrowright',
                    prevEl: '.arrowleft'
                },
                on:{
                    slideChange: function(){
                        self.img_index = this.activeIndex;
                        self.preset_bit = self.pics[this.activeIndex].preset_bit;
                        self.resetMeths(); // 重置拖拽，缩小，放大
                        self.downloadImgInfo.url = self.pics[this.activeIndex].src; // 下载当前图片的url
                        self.downloadImgInfo.name = self.pics[this.activeIndex].name; // 下载当前图片的name
                        // 当前页面最后一张时触发
                        if(this.activeIndex == parseInt(self.count - 1)) {                         
                            self.$refs.swiperTop.$swiper.autoplay.stop();
                            // 翻页获取下一页数据
                            if(self.page < self.pageSize) {
                                self.page++;
                                console.log(self.page)
                                self.getPics(self.page);
                                if(self.autoplay) {
                                    self.$refs.swiperTop.$swiper.autoplay.start();
                                }
                            }
                        }
                    },
                }
            },
            value: 5000,
            time: '',
            options:[{label: "5",value: 5000},{label: "10",value: 10000},{label: "15",value: 15000},{label: "20",value: 20000},{label: "25",value: 25000},{label: "30",value: 30000}]
        }
    },
    mounted() {
    },
    created () {this.init()},
    methods: {
        init () {
            if(this.pics.length>0){
                this.preset_bit = this.pics[0].preset_bit; // 默认预置位
                this.downloadImgInfo.url = this.pics[0].src; // 下载当前图片的url
                this.downloadImgInfo.name = this.pics[0].name; // 下载当前图片的name
            }
        },
        setValue(val) {
            if(Object.keys(val).length>0){
                if(val.name=='alarm') {
                    this.visible = val.val;
                } else {
                    this.visible2 = val.val;
                }
            }
        },
        mouseenter () {
            this.hover = true;
        },
        mouseleave () {
            if(document.querySelector('.el-select-dropdown').style.display == 'none') {
                this.hover = false;
            }
        },
        downloadImg() {
            // 下载图片
            global.downloadImg(this.downloadImgInfo.url,this.downloadImgInfo.name);
        },
        autoplay_click (){
            // 自动播放
            this.hover = false; // 鼠标移入效果取消
            this.autoplay = !this.autoplay; // 自动播放开启
            if(this.autoplay) {
                this.$refs.swiperTop.$swiper.autoplay.start();
            } else {
                this.$refs.swiperTop.$swiper.autoplay.stop();
            }
        },
        slideto (idx) {
            // 点击缩略图
            this.$refs.swiperTop.$swiper.slideTo(idx, 400, false);
            this.preset_bit = this.pics[idx].preset_bit;
        },
        less_preset_init() {
            // 上一个预置位
            let preset_bit = parseInt(this.preset_bit), PRESET_BIT_TOTAL = parseInt(global.PRESET_BIT_TOTAL);
            if(preset_bit < 2) {
                return;
            }
            preset_bit = preset_bit - 1;
            this.preset_bit = preset_bit;
            this.resetMeths();
        },
        add_preset_init() {
            // 下一个预置位
            let preset_bit = parseInt(this.preset_bit), PRESET_BIT_TOTAL = parseInt(global.PRESET_BIT_TOTAL);
            if((preset_bit + 1) > PRESET_BIT_TOTAL) {
                return;
            }
            preset_bit = preset_bit + 1;
            this.preset_bit = preset_bit;
            this.resetMeths();
        },
        lesspageto () {
            // 上一页
            let page = parseInt(this.page), pageSize = parseInt(this.pageSize);
            if(page < 2) {
                return;
            }
            page = page - 1;
            this.page = page;
        },
        addpageto () {
            // 下一页
            let page = parseInt(this.page), pageSize = parseInt(this.pageSize);
            if((page + 1) > pageSize) {
                return;
            }
            page = page + 1;
            this.page = page;
        },
        getPics (page) {
            // 请求接口获取数据
            this.img_index = 0;
        },
        noautoplay () {
            // 停止自动播放
            if(this.autoplay) {
                this.$refs.swiperTop.$swiper.autoplay.stop();
                this.autoplay = false;
            }
        },
        narrow () {
            // 缩小   
            let that = this;             
            that.noautoplay();    // 触发暂定自动播放    
            that.zoom(-0.15);
            return false;    
        },
        zoombig () {
            // 放大
            let that = this;  
            that.noautoplay();    // 触发暂定自动播放
            that.zoom(0.15);
            return false; 
        },
        alarmHandling () {
            // 告警处理
            this.noautoplay();    // 触发暂定自动播放 
        },
        ManualAlarm () {
            // 手动告警
            this.noautoplay();    // 触发暂定自动播放 
        },
        currentSel (selVal) {
            // 自动播放下拉
            this.value = selVal;
            this.noautoplay();    // 触发暂定自动播放 
        },
        reset () {
            // 重置
            this.resetMeths();
            this.noautoplay();    // 触发暂定自动播放 
        },
        resetMeths () {
            // 重置图像拖拽、放大、缩小
            var divs = document.querySelectorAll('.gallery-top .swiper-slide .img-box');
            if(divs && divs.length>0) {
                for(var i = 0;i<divs.length;i++) {
                    divs[i].style="";
                    divs[i].querySelector('img').style.transform = "scale(1)";
                }
            }
        },
        move(e) {
            e.preventDefault();
            this.noautoplay();    // 触发暂定自动播放 
            var left = document.querySelector('.swiper-slide-active .img-box');
            var img = document.querySelector('.swiper-slide-active .img-box');
            var x = e.pageX - img.offsetLeft;
            var y = e.pageY - img.offsetTop;
            // 添加鼠标移动事件
            left.addEventListener('mousemove', move)
                function move(e) {
                    img.style.left = e.pageX - x + 'px'
                    img.style.top = e.pageY - y + 'px'
                }
            // 添加鼠标抬起事件，鼠标抬起，将事件移除
            img.addEventListener('mouseup', function() {
                left.removeEventListener('mousemove', move)
            })
            // 鼠标离开父级元素，把事件移除
            left.addEventListener('mouseout', function() {
                left.removeEventListener('mousemove', move)
            })
        },
        rollImg() { 
            // 滚轮放大
            this.noautoplay();    // 触发暂定自动播放 
            this.zoom((event.wheelDelta / 1000));
            return false; 
        },
        zoom(val) {
            //放大 缩放图片
            let chmapScale = this.$refs.imgDiv[0].style.transform.slice(6,-1);
            chmapScale = val + parseFloat(chmapScale);
            if (chmapScale >= 1 && chmapScale <5) { 
                this.$refs.imgDiv[0].style.transform = "scale(" + chmapScale + ")";
            }
        },
        close () {            
            this.$baseEventBus.$emit('showdetail', {showid:0});
        },
        update () {
            let self = this;
            self.showSwiper = false;
            self.timmer = setTimeout(() => {
                self.showSwiper = true;
            }, 200);
        }
    },
    destroyed() {
        clearTimeout(this.timmer);
    }
}
</script>
<style lang="scss" scoped>
.pictureBox{display:flex; flex-direction: column;height:100%;overflow:hidden;background:#000;
   .top{background:$base-color-blue3;display:flex;align-items:center;height:35px;color:#fff;padding:0 $base-padding;
       span{line-height:35px; }
       .title{
           font{color:$base-color-green2;margin:0 $base-padding10;}
       }
       .time{padding-left:40px;}
       .tag{padding-left:$base-padding10;}
       i{color:#fff;font-size:20px;margin-left:auto;cursor: pointer;}
   }
   .swiper-box{flex:1;display:flex;position: relative; flex-direction: column;overflow:hidden; 
    .arrowicon{transition: all 0.3s;position: absolute;z-index: 5;width:40px;height:40px;font-size:18px;border:0;background:rgba(0,0,0,0.8);color:rgba(255,255,255,0.7);
        &.arrowleft,&.arrowright{top:50%;margin-top:-30px;}
        &.arrowtop,&.arrowbottom{left:50%;margin-left:-30px;}
        &.arrowleft{left:-160px;}
        &.arrowright{right:-160px;}
        &.arrowtop{top:-160px;}
        &.arrowbottom{bottom:-160px;}
    }   
    .swiper-gallery{flex:1;width: 100%;overflow: hidden;position: relative;
        .gallery-top{width:100%;height:100%;
            .swiper-slide{overflow:hidden;}
            .img-box{position: relative;width:100%;height:100%;
                img{position:absolute;object-fit: cover;width:100%;height:100%;display: block;}
            }
        }
        .top_hover{font-size:12px;position: absolute; opacity: 1; z-index: 5;top:0;left:0;right:0;background:rgba(0,0,0,0.6);height:35px;display:flex;align-items:center;
            .info{display:flex;align-items:center; color:#fff;padding:0 $base-padding10;
                .title{
                    font{color:$base-color-green2;margin:0 $base-padding10;}
                }
                span{transform-origin: left;transform: scale(0.8);}
                i{color:#fff;font-size:20px;margin-left:auto;cursor: pointer;}
            }
            .tools{display:flex;align-items:center;height:100%;justify-content: flex-end; padding:0 $base-padding10;margin-left:auto;
                .item+.item{margin-left:5px;}
                .item{color:#fff;
                    span{transform: scale(0.8);transform-origin: left;display:inline-block;}
                    &:hover{i{color:$base-color-default;}}
                    i{font-size:14px;cursor: pointer;}
                    ::v-deep{
                        .el-button{transform: scale(0.8);transform-origin: left;}
                        .el-select{width:70px;transform: scale(0.8);transform-origin: left;}
                    }
                }
            }
        }
        &.hover{
            .top_hover{opacity: 1;}
            .arrowicon{
                &.arrowleft{left:16px;}
                &.arrowright{right:16px;}
                &.arrowtop{top:50px;}
                &.arrowbottom{bottom:20px;}
            }
        }
    }
    .swiper-thumbnail{height:120px;padding:5px 25px 10px;position: relative;
        .gallery-thumbs{height:100%;width: 100%;display:flex;
            .swiper-slide {flex:1;height: 100%;opacity: 0.4;overflow: hidden;margin: 0 0.5%;cursor: pointer;
                &.act{opacity: 1;}
                .box{display: flex; flex-direction: column;height:100%;
                    span.title{display:block;text-align:center;color:#fff; flex-shrink: 0;height: 23px;line-height: 23px;font-size:$base-font-size-small;}
                    img{object-fit: cover;width:100%;flex:1;display: block;}
                }
            }
            .swiper-slide-active {opacity: 1; }
        }
        .thumbnail-arrow{position: absolute;width:25px;top:10px;bottom:10px;background:rgba(255,255,255,0.15);color:rgba(255,255,255,0.7);display:flex;justify-content: center; align-items: center; cursor: pointer;&:hover{background:rgba(255,255,255,0.2);}}
        .thumbnail-arrow-left{left:0;}
        .thumbnail-arrow-right{right:0;}
    }
   } 
}
</style>