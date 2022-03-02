<template>
    <!-- 图片自定义组件+右侧功能 -->
    <div :class="['pictureInfo', {act:open}]" >    
        <div class="left" >
            <!-- 图片详情自定义组件  v-loading="loading" element-loading-background="#000"-->
            <div class="pictureBox" >
                <div class="swiper-box" >
                    <div v-if="pics.length>0 && pics" :class="['swiper-gallery', {hover: hover}]" @mouseenter.stop.prevent="mouseenter" @mouseleave.stop.prevent="mouseleave">
                        <swiper v-if="showSwiper && pics.length>0" class="swiper gallery-top swiper-no-swiping" :options="swiperOptionTop" ref="swiperTop">
                            <swiper-slide v-for="(e,i) in pics" :key="i" :data-swiper-autoplay="value">
                                <div class="img-box" @mousewheel.prevent="rollImg"><img class="swiper-lazy" style="transform:scale(1)" :data-src="reDomain(e.path, e.imageName)" :ref="img_index ==i?'imgDiv':''" alt="" @mousedown="move"></div>
                                <div class="swiper-lazy-preloader"></div>
                            </swiper-slide>
                        </swiper>
                        <template v-if="pics.length>0 && pics && pics.length>0">
                            <el-button type="primary" icon="el-icon-arrow-left" circle class="arrowleft arrowicon" @click="arrowleft"></el-button>
                            <el-button type="primary" icon="el-icon-arrow-right" circle class="arrowright arrowicon" @click="arrowright"></el-button>
                        </template>
                        <!-- 预置位上一个 如果是无人机巡检，则不需要上下 -->
                        <el-button type="primary" v-if="fatherName != 'drone'" icon="el-icon-arrow-up" circle class="arrowtop arrowicon" @click="less_preset_init()"></el-button>
                        <!-- 预置位下一个 -->
                        <el-button type="primary" v-if="fatherName != 'drone'" icon="el-icon-arrow-down" circle class="arrowbottom arrowicon" @click="add_preset_init()"></el-button>
                    </div>
                    <div v-if="pics.length>0 && pics && pics.length<1" class="nopicture">暂无图片</div>
                    <div class="top_hover">
                        <div class="info">
                            <span class="title">{{pics.length>0 && pics[0].yuntai && Object.keys(pics[0].yuntai).length>0?pics[0].yuntai.lineName+pics[0].yuntai.towerName + pics[0].yuntai.name + dicts.monitor_type.obj[pics[0].yuntai.monitorType].dictLabel: ''}}<font>{{thisPicInfo.shape&&thisPicInfo.shape.length>0?dicts.tower_shape.obj[thisPicInfo.shape].dictLabel:''}}</font>{{thisPicInfo.captureTime?thisPicInfo.captureTime:''}}&nbsp;&nbsp;{{thisPicInfo.captureUser?thisPicInfo.captureUser:''}}</span>
                        </div>
                        <div class="tools">
                            <template v-if="pics.length>0 && pics && pics.length>0">
                                <!-- 下载 -->
                                <div class="item" @click="downloadImg(pics[img_index])"><i class="el-icon-download"></i></div>
                                <!-- 放大 -->
                                <div class="item" @click="zoombig"><i class="el-icon-zoom-in"></i></div>
                                <!-- 缩小 -->
                                <div class="item" @click="narrow"><i class="el-icon-zoom-out"></i></div>
                                <!-- 重置 -->
                                <div class="item" @click="reset"><i class="el-icon-refresh-right"></i></div>
                                <div class="item" @click="alarmHandling" v-if="false">
                                    <el-button size="mini" type="warning" @click="visible=true">告警处理</el-button>
                                </div>
                                <!-- <div class="item">
                                    <el-button size="mini" type="danger" @click="alarmClick(pics[img_index],pics[0].yuntai)">手动告警</el-button>
                                </div> -->
                                <div class="item">
                                    <span>翻页间隔(秒)</span>
                                    <el-select v-model="value" @change="currentSel" placeholder="请选择" size="mini">
                                        <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
                                    </el-select>
                                    <el-button size="mini" @click="autoplay_click" type="primary">{{autoplay?'停止':'播放'}}</el-button>
                                </div>
                            </template>
                            <div class="item" v-if="imgcheck && imgcheck.single">
                                <el-button size="mini" type="primary" @click="close">关闭</el-button>
                            </div>
                        </div>
                    </div>
                    <!-- swiper2 Thumbs -->
                    <div class="swiper-thumbnail"  @click.stop="noautoplay">
                        <div class="swiper gallery-thumbs">
                            <template  v-if="pics.length>0 && pics && pics.length>0" >
                                <div :class="['swiper-slide', {act:img_index == i}]" v-for="(e,i) in pics" :key="i" @click="slideto(i)">                        
                                    <div class="box">
                                        <!-- <span class="title">{{e.name}}</span> -->
                                        <div class="mask">
                                            <span>{{Object.keys(dicts).length>0 && e.captureType && dicts.capture_type.obj[e.captureType] && dicts.capture_type.obj[e.captureType].dictLabel}}</span>
                                            <span>{{e.updateTime}}</span>
                                        </div>
                                        <img :src="reDomain(e.path, e.imageName)">
                                    </div>
                                </div>
                            </template>
                        </div>
                        <div class="thumbnail-arrow thumbnail-arrow-left" @click="lesspageto()"><i class="el-icon-arrow-left"></i></div>
                        <div class="thumbnail-arrow thumbnail-arrow-right" @click="addpageto()"><i class="el-icon-arrow-right"></i></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="right">
            <picture-filter @getfilter="filterdata" :cc="queryParams" :total2="total" :yuntaiinfo="yuntaiinfo" :type="type"></picture-filter>
        </div>
    </div>
</template>
<script>
import {get_uavInspection_list } from "@/api/senxu/inspection/index";
import { Swiper, SwiperSlide} from 'vue-awesome-swiper'
import { mapGetters } from 'vuex'
import 'swiper/css/swiper.css'
import * as global from '@/utils/global'
export default {
    name: "pictureInfo",
    components: {Swiper,SwiperSlide},
    props: ['imgcheck', 'type','fatherName'],
    data () {
        let self = this;
        return {
            loading:false,
            open:false,
            showSwiper: true,
            timmer: "",
            visible: false, // 告警处理显示/隐藏
            visible2: false, // 手动告警显示/隐藏
            preset_bit: '', // 预置位
            img_index: '0', // 图片索引
            total: 100, // 总数
            autoplay: false,
            // state:undefined, // 在线状态
            pics: [],
            thisPicInfo: {captureTime:undefined,captureUser:undefined,presetBit:undefined,shape:undefined},
            value: 5000,
            yuntaiinfo: {},// 云台信息
            queryParams: {pageNum:1,pageSize:8,airportId:'',updateTime:''},
            options:[{label: "5",value: 5000},{label: "10",value: 10000},{label: "15",value: 15000},{label: "20",value: 20000},{label: "25",value: 25000},{label: "30",value: 30000}],
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
                        console.log(this.activeIndex,this,'>>>>>activeIndex')
                        self.img_index = this.activeIndex;
                        self.preset_bit = self.pics[this.activeIndex].presetBit;
                        self.resetMeths(); // 重置拖拽，缩小，放大
                        self.thisPicInfo['captureTime'] = self.pics[this.activeIndex].captureTime; // 替换当前图片标题
                        self.thisPicInfo['captureUser'] = self.pics[this.activeIndex].captureUser; // 替换当前图片标题
                        self.thisPicInfo['presetBit'] = self.pics[this.activeIndex].presetBit; // 替换当前图片标题
                        self.thisPicInfo['shape'] = self.pics[this.activeIndex].shape;
                        // 当前页面最后一张时触发
                        if(self.autoplay) {
                            // console.log("当前页面最后一张时触发");
                            // console.log("self.value",self.value);
                            // console.log("self.pics.length",self.pics.length);
                            // console.log("self.queryParams.pageNum",self.queryParams.pageNum);
                            // console.log("this.activeIndex",this.activeIndex);
                            if(this.activeIndex == (parseInt(self.pics.length) - 1) ) {
                                console.log("1");
                                if((parseInt(self.queryParams.pageNum)+1) > self.pageTotal()) {
                                    console.log("1.1");
                                    self.noautoplay();
                                } else {
                                    this.timer = setTimeout(()=>{   //设置延迟执行
                                        console.log("1.2");
                                        self.queryParams.pageNum++;
                                        self.getPics();
                                    },self.value);

                                }
                            }
                        }
                    },
                }
            }
        }
    },
    created () {
       
    },
    computed: {
        ...mapGetters([
            'dicts',
        ])
    }, 
    mounted() {   
        this.queryParams.presetBit = this.imgcheck && this.imgcheck.presetBit;
        this.thisPicInfo['presetBit'] = this.imgcheck && this.imgcheck.presetBit?this.imgcheck.presetBit:0;
        this.queryParams.yuntaiId = this.imgcheck && this.imgcheck.yuntaiId;
        this.queryParams.updateTime = this.imgcheck && this.imgcheck.updateTime;
         // 无人机巡检进来，则需要设置各个值
        if(this.fatherName == 'drone'){

            this.queryParams.airportId = this.imgcheck && this.imgcheck.airportId;
            this.queryParams.pageNum = this.imgcheck.pageNum;
            this.queryParams.pageSize = this.imgcheck.pageSize;
        }
        
        console.log(">>>>>>>this.queryParams.updateTime",this.imgcheck , this.imgcheck.updateTime);
        this.open = this.imgcheck && this.imgcheck.single;
        this.getPics(); 
    
    },
    methods: {
        filterdata (data) {
            console.log(data,'拿到data数据',this.pics,this.imgcheck)
            this.update();
            this.pics = [];            
            this.img_index = 0;   
            this.pics = data.rows;           
            this.noautoplay();         
            this.total = data.total;
            this.thisPicInfo['captureTime'] = this.pics[0] && this.pics && this.pics.length>0 && this.pics[0].captureTime;
            this.thisPicInfo['captureUser'] = this.pics[0] && this.pics && this.pics.length>0 && this.pics[0].captureUser;
            this.thisPicInfo['shape'] = this.pics[0] && this.pics && this.pics.length>0 && this.pics[0].shape;
            this.thisPicInfo['presetBit'] = this.pics[0] && this.pics && this.pics.length>0?this.pics[0].presetBit:this.queryParams.presetBit;

        },
        pageTotal () {
            let v = this.total % this.queryParams.pageSize == 0? this.total / this.queryParams.pageSize :Math.ceil(this.total / this.queryParams.pageSize);
            return v;
        },
        reDomain(url,name) {
            // 图片地址
            return url ;
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
        pageTotal () {
            let v = this.total % this.queryParams.pageSize == 0? this.total / this.queryParams.pageSize :Math.ceil(this.total / this.queryParams.pageSize);
            return v;
        },
        mouseenter () {
            this.hover = true;
        },
        mouseleave () {
            if(document.querySelector('.el-select-dropdown') && document.querySelector('.el-select-dropdown').style.display == 'none') {
                this.hover = false;
            }
        },
        downloadImg(data) {
            // 下载当前图片
            let imgsrc = this.reDomain(data.path, data.imageName);
            global.downloadImg(imgsrc,data.imageName);
        },
        autoplay_click (){          
            // if(this.imgcheck.state != 1) {
            //     this.msgSuccess("该设备离线状态");
            //     return;
            // }
            // 自动播放
            this.hover = false; // 鼠标移入效果取消
            if(this.queryParams.pageNum == this.pageTotal() && (parseInt(this.img_index)+1) == this.pics.length) {
                this.msgSuccess("已是最后一页");
                this.noautoplay();
            } else {
                this.autoplay = !this.autoplay; // 自动播放开启
            }
            if(this.autoplay) {
                this.$refs.swiperTop.$swiper.autoplay.start();
            } else {
                this.$refs.swiperTop.$swiper.autoplay.stop();
            }
        },
        slideto (idx) {
            console.log(idx,'点击缩略图')
            // 点击缩略图
            this.$refs.swiperTop.$swiper.slideTo(idx, 400, false);
            this.preset_bit = this.pics[idx].presetBit;
        },
        less_preset_init() {
            // 上一个预置位
            let preset_bit = parseInt(this.thisPicInfo.presetBit), PRESET_BIT_TOTAL = parseInt(global.PRESET_BIT_TOTAL);
            if(preset_bit==0) {
                preset_bit = PRESET_BIT_TOTAL;
                // return;
            } else {
                preset_bit = preset_bit - 1;
            }       
            this.preset_bit = preset_bit; 
            this.queryParams.presetBit = this.preset_bit;
            this.thisPicInfo.presetBit = this.preset_bit; 
            this.queryParams.pageNum =  1;   
            this.getPics();
            this.resetMeths();
        },
        add_preset_init() {
            // 下一个预置位
            let preset_bit = parseInt(this.thisPicInfo.presetBit), PRESET_BIT_TOTAL = parseInt(global.PRESET_BIT_TOTAL);
            if(preset_bit == PRESET_BIT_TOTAL) {                
                preset_bit = 0;
            } else {
                preset_bit = preset_bit + 1;
            }
            this.preset_bit = preset_bit;
            this.queryParams.presetBit = this.preset_bit;
            this.queryParams.pageNum =  1;
            this.thisPicInfo.presetBit = this.preset_bit;
            this.getPics();
            this.resetMeths();
        },
        lesspageto () {
            this.noautoplay();
            // 上一页
            let page = parseInt(this.queryParams.pageNum);
            if(page < 2) {
                return;
            }
            page = page - 1;
            this.queryParams.pageNum = page;
            this.getPics();
        },
        addpageto () {
            // 下一页
            this.noautoplay();
            let page = parseInt(this.queryParams.pageNum);
            if((page + 1) > this.pageTotal()) {
                return;
            }
            page = page + 1;
            this.queryParams.pageNum = page;
            this.getPics();
        },
        getPics () {
            // 请求接口获取数据
            let self = this;
            self.img_index = 0;         
            self.pics = [];   
            this.loading = true
            get_uavInspection_list(self.queryParams).then(response => {
              console.log(response,'response----');
                if(response.code == 200 && response.rows && response.rows.length>0) {
                    this.loading = false 
                    self.pics = response.rows;
                    self.total = response.total;
                    // 如果是无人机巡检，则需要处理
                    if(this.fatherName == 'drone'){
                        self.pics.forEach((it,index) =>{
                            if(it.imageId == self.imgcheck.imageId){

                                self.img_index = index;
                                this.$nextTick(()=>{
                                    self.slideto(index)
                                });

                            }
                        })
                    }
                    // self.state = response.rows[0].yuntai.state;
                    self.yuntaiinfo = response.rows[0].yuntai;
                    if(self.pics && self.pics.length>0) {                        
                        self.thisPicInfo['shape'] = self.pics[0] && self.pics && self.pics.length>0 && self.pics[0].shape;
                        self.thisPicInfo['captureTime'] = self.pics[0] && self.pics && self.pics.length>0 && self.pics[0].captureTime;
                        self.thisPicInfo['captureUser'] = self.pics[0] && self.pics && self.pics.length>0 && self.pics[0].captureUser;
                        if(self.autoplay) {
                            this.$nextTick(()=>{
                                console.log(111)
                                self.$refs.swiperTop.$swiper.autoplay.start();
                                // if(self.queryParams.pageNum == self.pageTotal() && self.pics.length==1) {
                                //     self.noautoplay();
                                // }
                            });
                        }
                    } else {
                        console.log(111)
                        self.thisPicInfo['presetBit'] = 0;
                    }
               }
            }).catch(e =>{
                console.log(e);
                this.loading = false
                this.$message.error('请求报错')
            });
        },
        noautoplay () {
            // 停止自动播放
            if(this.autoplay) {
                this.$refs.swiperTop.$swiper.autoplay.stop();
                this.autoplay = false;
                clearTimeout(this.timmer);
                return;
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
            if(chmapScale<1) {chmapScale = 1;}
            if (chmapScale >= 1 && chmapScale <5) { 
                this.$refs.imgDiv[0].style.transform = "scale(" + chmapScale + ")";
            }
        },
        close () {       
            this.$emit('close', {show:0})   
        },
        update () {
            let self = this;
            self.showSwiper = false;
            setTimeout(() => {
                self.showSwiper = true;
            }, 200);
        },
        hideclick() {
            // 显示隐藏
            this.open = !this.open;
        },
        alarmClick(pictureinfo,yuntaiinfo) {    
            // 手动告警            
            if(pictureinfo.isAlarm == 1) {this.msgSuccess("该图片已告警"); return;}
            this.$parent.$parent.alarmClick(pictureinfo,yuntaiinfo);
            this.noautoplay();    // 触发暂定自动播放 
        },
        arrowleft() {
            this.noautoplay();
            if(this.img_index == 0 && this.queryParams.pageNum != 1) {
                this.queryParams.pageNum--;
                this.getPics();
            }
        },
        arrowright() {
            if((parseInt(this.img_index) + 1) == this.pics.length && this.queryParams.pageNum < this.pageTotal()) {
                this.queryParams.pageNum++;
                this.getPics();
            }
            this.noautoplay();
        }
    },
    destroyed() {
        clearTimeout(this.timmer);
    }
}
</script>
<style lang="scss" scoped>
.pictureInfo{display:flex;height:100%;overflow:hidden;position: relative;
    &.act{
        .right{width: 0 !important;}
        .left{width:100%!important;}
    }
   .top{background:$base-color-blue3;display:flex;align-items:center;height:35px;color:#fff;padding:0 $base-padding;
       span{line-height:35px;}
       .title{
           font{color:$base-color-green2;margin:0 $base-padding10;}
       }
       .time{padding-left:40px;}
       .tag{padding-left:$base-padding10;}
       i{color:#fff;font-size:20px;margin-left:auto;cursor: pointer;}
   }
   .el-loading-parent--relative{
        .el-loading-mask{
            background-color: #000;
        }
   }
 
   .left{height:100%;width:calc(100% - #{$base-right-width});transition:$base-transition;position: relative;
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
                .nopicture{position: absolute;width:200px;height:50px;text-align:center;line-height:50px;display:block;left:50%;margin-left:-100px;top:50%;margin-top:-25px;}
                .arrowicon{transition: all 0.3s;position: absolute;z-index: 5;width:40px;height:40px;font-size:18px;border:0;background:rgba(0,0,0,0.8);color:rgba(255,255,255,0.7);
                    &.arrowleft,&.arrowright{top:50%;margin-top:-30px;}
                    &.arrowtop,&.arrowbottom{left:50%;margin-left:-30px;}
                    &.arrowleft{left:-160px;}
                    &.arrowright{right:-160px;}
                    &.arrowtop{top:-160px;}
                    &.arrowbottom{bottom:-160px;}
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
                .swiper-gallery{flex:1;width: 100%;overflow: hidden;position: relative;
                    .gallery-top{width:100%;height:100%;
                        .swiper-slide{overflow:hidden;}
                        .img-box{position: relative;width:100%;height:100%;
                            img{position:absolute;object-fit: contain;width:100%;height:100%;display: block;}
                        }
                    }
                    &.hover{
                        .top_hover{opacity: 0;}
                        .arrowicon{
                            &.arrowleft{left:16px;}
                            &.arrowright{right:16px;}
                            &.arrowtop{top:50px;}
                            &.arrowbottom{bottom:20px;}
                        }
                    }
                }
                .swiper-thumbnail{height:120px;padding:10px 25px 10px;position: relative;
                    .gallery-thumbs{height:100%;width: 100%;display:flex;
                        .swiper-slide {width:12%;height: 100%;opacity: 0.4;overflow: hidden;margin: 0 0.25%;cursor: pointer;
                            &.act{opacity: 1;}
                            .box{display: flex; flex-direction: column;height:100%;position: relative;
                                // span.title{display:block;text-align:center;color:#fff; flex-shrink: 0;height: 23px;line-height: 23px;font-size:$base-font-size-small;}
                                img{object-fit: cover;width:100%;flex:1;display: block;}
                                .mask{position: absolute;background:rgba(0,0,0,0.6);bottom:0;left:0;right:0;height:35px;padding-top:2px;z-index:2;opacity:0.7;
                                    span{display:block;text-align:center;color:#fff;font-size:$base-font-size-small;}
                                    span.absolute{position: absolute;color:$base-color-green;left:7px;top:8px;}
                                }
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
        .more{position: absolute;padding:10px;right:0;bottom:130px;cursor:pointer;background:$base-color-blue3;z-index:1;width:30px;height: 90px;border-top-left-radius: 4px;border-bottom-left-radius: 4px;color:#fff; transform: scale(0.8); transform-origin: right;}
   }
   .right{width:$base-right-width;position: absolute;top:0px;right:0;bottom:0;background: #fff;transition: width 0.2s; }
}
</style>
