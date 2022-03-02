<template>
    <div class="pictureBox">
        <div class="swiper-box">
            <div :class="['swiper-gallery', {hover: hover}]" @mouseenter.stop.prevent="mouseenter" @mouseleave.stop.prevent="mouseleave">
                <swiper v-if="showSwiper && pics.length>0" class="swiper gallery-top swiper-no-swiping" :options="swiperOptionTop" ref="swiperPic">
                    <swiper-slide v-for="(e,i) in pics" :key="i" :data-swiper-autoplay="value">
                        <div class="img-box" @mousewheel.prevent="rollImg"><img class="swiper-lazy" style="transform:scale(1)" :data-src="reDomain(e.alarmDir, e.alarmUrl)" :ref="img_index ==i?'imgDiv':''" alt="" @mousedown="move"></div>
                        <div class="swiper-lazy-preloader"></div>
                    </swiper-slide>
                </swiper>
                <template v-if="pics.length>0">
                    <el-button type="primary" icon="el-icon-arrow-left" circle class="arrowleft arrowicon" @click="arrowleft"></el-button>
                    <el-button type="primary" icon="el-icon-arrow-right" circle class="arrowright arrowicon" @click="arrowright"></el-button>
                </template>
            </div>
            <div v-if="pics.length<1" class="nopicture">暂无图片</div>
            <div class="top_hover">
                <div class="info">
                    <span class="title">{{thisPicInfo.lineName}}{{thisPicInfo.towerName}}{{thisPicInfo.yuntaiName}}</span><span>{{thisPicInfo.alarmDate}}</span><span>{{thisPicInfo.confirmState&&thisPicInfo.confirmState == 1?'已确认':'未确认'}}</span><span v-if="thisPicInfo.alarmSource!=1">智能分析告警上报</span><span>{{thisPicInfo.alarmSource==1?'手动告警':'智能分析告警上报'}}</span>
                </div>
                <div class="tools">
                    <template v-if="pics.length>0">
                        <!-- 下载 -->
                        <div class="item" @click="downloadImg"><i class="el-icon-download"></i></div>
                        <!-- 放大 -->
                        <div class="item" @click="zoombig"><i class="el-icon-zoom-in"></i></div>
                        <!-- 缩小 -->
                        <div class="item" @click="narrow"><i class="el-icon-zoom-out"></i></div>
                        <!-- 重置 -->
                        <div class="item" @click="reset"><i class="el-icon-refresh-right"></i></div>
                        <div class="item" @click="alarmHandling">
                            <el-button size="mini" type="warning" @click="alarmConfrim">告警处理</el-button>
                        </div>
                        <div class="item" @click="handClick" v-if="thisPicInfo.alarmSource!=1">                            
                            <el-button size="mini" type="danger">手动告警</el-button>
                        </div>
                        <div class="item">
                            <span>翻页间隔(秒)</span>
                            <el-select v-model="value" @change="currentSel" placeholder="请选择" size="mini">
                                <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
                            </el-select>
                            <el-button size="mini" @click="autoplay_click" type="primary">{{autoplay?'停止':'播放'}}</el-button>
                        </div>
                    </template>
                    <div class="item">
                        <el-date-picker style="transform: scale(0.8);transform-origin: right;width:200px;" v-model="date" size="mini"  format="yyyy-MM-dd"  value-format="yyyy-MM-dd" type="daterange"  range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" @change="changeDate"></el-date-picker>  
                    </div>
                    <div class="item">
                        <el-button size="mini" type="primary" @click="close">关闭</el-button>
                    </div>
                </div>
            </div>
            <!-- swiper2 Thumbs -->
            <div class="swiper-thumbnail"  @click.stop="noautoplay">
                <div class="swiper gallery-thumbs">
                    <template  v-if="pics.length>0" >
                        <div :class="['swiper-slide', {act:img_index == i}]" v-for="(e,i) in pics" :key="i" @click="slideto(i)">                        
                            <div class="box">
                                <!-- <span class="title">{{e.name}}</span> -->
                                <div class="mask">
                                    <!-- <span>{{e.captureUser}}</span> -->
                                    <span>{{e.alarmDate?e.alarmDate:'暂无时间'}}</span>
                                </div>
                                <img :src="reDomain(e.alarmDir, e.alarmUrl)">
                            </div>
                        </div>
                    </template>
                </div>
                <div class="thumbnail-arrow thumbnail-arrow-left" @click="lesspageto()"><i class="el-icon-arrow-left"></i></div>
                <div class="thumbnail-arrow thumbnail-arrow-right" @click="addpageto()"><i class="el-icon-arrow-right"></i></div>
            </div>
        </div>
        <!-- 告警处理-->
        <alarm-confrim :ids="thisPicInfo.id" v-if="show"></alarm-confrim>
        <!-- 手动告警 -->
        <manual-alarm v-if="visible3" :alarminfo="thisPicInfo" :yuntaiinfo="thisyuntai"></manual-alarm>
    </div>
</template>
<script>
import { ge_yuntaiAlarm_list2 } from "@/api/senxu/alarm/index";
import { Swiper, SwiperSlide} from 'vue-awesome-swiper'
import { mapGetters } from 'vuex'
import 'swiper/css/swiper.css'
import * as global from '@/utils/global'
export default {
    name: "alarmDetail",
    components: {Swiper,SwiperSlide},
    props: ['yuntaiId', 'spare0', 'spare1'],
    data () {
        let self = this;
        return {
            timmer: "",
            showSwiper: true,
            img_index: 0, // 图片索引
            total: 100, // 总数
            show: false, // 告警处理显示/隐藏
            autoplay: false,
            pics: [],
            visible3: false, // 手动告警
            hover: false,
            thisyuntai: {}, // 云台信息
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
                        self.thisPicInfo = self.pics[this.activeIndex];
                        self.resetMeths(); // 重置拖拽，缩小，放大                    
                        // 当前页面最后一张时触发
                        // if(this.activeIndex == (parseInt(self.pics.length) - 1) && self.autoplay) {                 
                        //     self.timmer = setTimeout(()=>{
                        //         // 翻页获取下一页数据
                        //         if(self.queryParams.pageNum>self.pageTotal()) {                   
                        //             self.autoplay = false;
                        //             self.$refs.swiperPic.$swiper.autoplay.stop(); 
                        //             clearTimeout(self.timmer);
                        //             return;
                        //         }
                        //         self.queryParams.pageNum++;
                        //         self.getPictureList();
                        //     },self.value)
                        // }
                        if(self.autoplay) {
                            if(this.activeIndex == (parseInt(self.pics.length) - 1)) {
                                if((parseInt(self.queryParams.pageNum)+1) > self.pageTotal()) {
                                    self.noautoplay();
                                } else {
                                    self.queryParams.pageNum++;
                                    self.getPictureList();
                                }
                            }
                        }
                    },
                }
            },
            value: 5000,
            date: [],
            queryParams: {pageNum:1,pageSize:8,yuntaiId:undefined,spare0:undefined,spare1:undefined},
            thisPicInfo: {}, // 当前图片信息   
            options:[{label: "5",value: 5000},{label: "10",value: 10000},{label: "15",value: 15000},{label: "20",value: 20000},{label: "25",value: 25000},{label: "30",value: 30000}]
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
        if(this.$route.name == "alarmManagedetail") {
            this.queryParams.yuntaiId = this.$route.params.treeId;
        } else {
            this.queryParams.yuntaiId = this.yuntaiId;
            this.queryParams.spare0 = this.spare0;
            this.queryParams.spare1 = this.spare1;
            this.date = this.spare0 && this.spare1?[this.spare0,this.spare1]:[];
        }
        this.getPictureList();   
    },
    methods: {
        handClick() {
            // 手动告警
            if(this.pictureInfo.isAlarm == 1) {this.$message('已手动告警');return;}
            this.thisPicInfo['imageDir'] = this.thisPicInfo.alarmDir;
            this.thisPicInfo['imageName'] = this.thisPicInfo.alarmUrl;
            this.visible3=true;
            this.thisImgInfo=this.thisPicInfo;
            this.thisyuntai=this.thisPicInfo;
        },
        alarmConfrim () {
            // 确认处理
            if(this.thisPicInfo.confirmState==1) {
                this.$message('已告警处理');
                return;
            }
            this.show=true;
        },
        changeDate(val) {
            // 修改时间
            this.thisPicInfo = {};
            this.queryParams.spare0 = val[0];
            this.queryParams.spare1 = val[1];
            this.getPictureList();
        },
        pageTotal () {
            let v = this.total % this.queryParams.pageSize == 0? this.total / this.queryParams.pageSize :Math.ceil(this.total / this.queryParams.pageSize);
            return v;
        },
        reDomain(url,name) {
            // 图片地址
            return  url + name;
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
        downloadImg() {
            // 下载当前图片
            let imgsrc = this.reDomain(this.thisPicInfo.alarmDir, this.thisPicInfo.alarmUrl);
            global.downloadImg(imgsrc,this.thisPicInfo.imageName);
        },
        autoplay_click (){   
            // 自动播放
            this.hover = false; // 鼠标移入效果取消
            if(this.queryParams.pageNum == this.pageTotal() && (parseInt(this.img_index)+1) == this.pics.length) {
                this.noautoplay();
                this.msgSuccess("已是最后一页");
            } else {
                this.autoplay = !this.autoplay; // 自动播放开启
            }
            if(this.autoplay) {
                this.$refs.swiperPic.$swiper.autoplay.start();
            } else {
                this.$refs.swiperPic.$swiper.autoplay.stop();
            }
        },
        slideto (idx) {
            // 点击缩略图
            this.$refs.swiperPic.$swiper.slideTo(idx, 400, false);
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
            this.getPictureList();
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
            this.getPictureList();
        },
        getPictureList () {
            // 请求接口获取数据
            let self = this;     
            self.img_index = 0; 
            self.pics = [];   
            ge_yuntaiAlarm_list2(self.queryParams).then(response => {
                if(response.code == 200&&response.rows && response.rows.length>0) {
                    self.pics = response.rows;
                    self.total = response.total;
                    self.thisPicInfo = response.rows[0];
                    if(self.autoplay) {
                        this.$nextTick(()=>{
                            self.$refs.swiperPic.$swiper.autoplay.start();
                            if(self.queryParams.pageNum == self.pageTotal() && self.pics.length==1) {
                                self.noautoplay();
                            }
                        });
                    }
               }
            });
        },
        noautoplay () {
            // 停止自动播放
            if(this.autoplay) {
                this.$refs.swiperPic.$swiper.autoplay.stop();
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
            if(this.$route.name == "alarmManagedetail") {
                this.$router.go(-1);
            } else {      
                this.$parent.show2 = false; 
                this.$parent.getPictureList();
            }
        },
        update () {
            let self = this;
            self.showSwiper = false;
            this.$nextTick(() => {
                self.showSwiper = true;
            });
        },
        arrowleft() {
            this.noautoplay();
            if(this.img_index == 0 && this.queryParams.pageNum != 1) {
                this.queryParams.pageNum--;
                this.getPictureList();
            }
        },
        arrowright() {
            if((parseInt(this.img_index) + 1) == this.pics.length && this.queryParams.pageNum < this.pageTotal()) {
                this.queryParams.pageNum++;
                this.getPictureList();
            }
            this.noautoplay();
        }
    }
}
</script>
<style lang="scss" scoped>
.pictureBox{display:flex; flex-direction: column;height:100%;overflow:hidden;background:#000; position: absolute;top:0;bottom:0;left:0;right:0;z-index:2;
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
                span+span{margin-left:10px;}
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
                .top_hover{opacity: 1;}
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
                        .mask{position: absolute;background:rgba(0,0,0,0.6);bottom:0;left:0;right:0;height:18px;padding-top:2px;z-index:2;opacity:0.7;
                            span{display:block;text-align:center;color:#fff;font-size:$base-font-size-small;line-height: 18px;}
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
</style>