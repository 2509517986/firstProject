<template>
<div class="alarmList">
    <el-row class="con" :gutter="10">
        <el-col :span="15">
            <el-table  ref="multipleTable" :height="tableHeight" :data="tableData" highlight-current-row :row-class-name="tableRowClassName" tooltip-effect="dark" style="width: 100%;height:100%;" @row-click="rowClick" @select-all="selectall" @selection-change="handleSelectionChange">
                <el-table-column width="45" type="selection" :selectable="checkSelectable">
                    <!-- <template slot-scope="scope">    
                         <el-checkbox :checked="scope.row.confirmState == 1" :disabled="scope.row.confirmState == 1"></el-checkbox> 
                    </template> -->
                </el-table-column>
                <el-table-column prop="alarmDate" label="告警时间" align="center" width="140"></el-table-column>
                <el-table-column prop="factory"  label="运行单位" align="center">
                    <template slot-scope="scope">                          
                        <span>{{scope.row.factory?dicts.oem.obj[scope.row.factory].dictLabel:''}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="channelNumber" label="通道名称" align="center"></el-table-column>
                <el-table-column prop="alarmDes"  label="告警描述" align="center">
                    <template slot-scope="scope">                          
                        <span>{{scope.row.alarmDes?scope.row.alarmDes:'暂无描述'}}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="confirmState" label="状态" align="center" width="90">
                    <template slot-scope="scope">
                        <el-button :type="scope.row.confirmState ==1?'success':'warning'" size="mini" @click="handleEdit(scope.$index, scope.row)">{{scope.row.confirmState==1?"已确认":"未确认"}}</el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="alarmSource"  label="告警类型" align="center">                    
                    <template slot-scope="scope">                          
                        <span>{{scope.row.alarmSource&&scope.row.alarmSource.length>0?scope.row.alarmSource == 1?'手动告警':'智能分析告警上图':'暂无'}}</span>
                    </template>
                </el-table-column>
                <!-- <el-table-column prop="name"  label="告警次数" align="center" width="80"></el-table-column> -->
            </el-table>
        </el-col>
        <el-col :span="9">
            <div class="swiper-box">
                <div v-if="tableData.length>0" :class="['swiper-gallery', {hover: hover}]" @mouseenter.stop.prevent="mouseenter" @mouseleave.stop.prevent="mouseleave">
                    <swiper class="swiper gallery-top swiper-no-swiping" :options="swiperOptionTop" ref="swiperTop">
                        <swiper-slide v-for="(e,i) in tableData" :key="i" :data-swiper-autoplay="value">                            
                            <!-- 标题 -->
                            <div class="mask">
                                <span>{{e.lineName}}{{e.towerName}}{{e.yuntaiName}}</span>
                                <span>{{e.alarmDate?e.alarmDate:'暂无时间'}}</span>
                                <span>{{e.alarmSource==1?'手动告警':'自动告警'}}</span>
                                <span>{{e.imgType?dicts['img_type'].obj[e.imgType].dictLabel:''}}</span>
                            </div>
                            <div class="img-box" @mousewheel.prevent="rollImg"><img class="swiper-lazy" style="transform:scale(1)" :data-src="reDomain(e.alarmDir,e.alarmUrl)" :ref="img_index ==i?'imgDiv':''" alt="" @mousedown="move"></div>
                            <div class="swiper-lazy-preloader"></div>
                        </swiper-slide>
                    </swiper>
                    <el-button type="primary" icon="el-icon-arrow-left" circle class="arrowleft arrowicon" @click="noautoplay"></el-button>
                    <el-button type="primary" icon="el-icon-arrow-right" circle class="arrowright arrowicon" @click="noautoplay"></el-button>
                    <!-- tools -->
                    <div class="tools">
                        <!-- 下载 -->
                        <div class="item" @click="downloadImg(tableData[img_index])"><i class="el-icon-download"></i></div>
                        <!-- 放大 -->
                        <div class="item" @click="zoombig"><i class="el-icon-zoom-in"></i></div>
                        <!-- 缩小 -->
                        <div class="item" @click="narrow"><i class="el-icon-zoom-out"></i></div>
                        <!-- 重置 -->
                        <div class="item" @click="reset"><i class="el-icon-refresh-right"></i></div>
                        <div class="item">
                            <el-button size="mini" type="primary" @click="alarmHandling(tableData[img_index])">告警处理</el-button>
                        </div>
                    </div>
                </div>
            </div>
        </el-col>
    </el-row>
    <el-row class="foot" :gutter="10">
        <el-col :span="8">
            <el-checkbox class="checkall" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
            <el-button type="primary" size="mini" class="btn" @click="sureOk">一键确认</el-button>
        </el-col>
        <el-col :span="16">
            <custom-pagination  v-show="total>0" :total="total" :autoplay="false" :pageSizes="[8,12,16,20]" :page.sync="pageNum" :limit.sync="pageSize"  @pagination="getList"></custom-pagination>
        </el-col>   
    </el-row>
      <!-- 告警确认 -->
      <alarm-confrim :ids="selectids" v-if="show"></alarm-confrim>
</div>
</template>
<script>
import { Swiper, SwiperSlide} from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'
import { mapGetters } from 'vuex'
import { ge_yuntaiAlarm_list2 } from "@/api/senxu/alarm/index";
import { yuntaiPreview } from "@/api/senxu/yuntai_picture/index";
import * as global  from "@/utils/global";
export default {
    name: "alarmList",
    props: ['query'],
    components: {Swiper,SwiperSlide},
    data() {
        let self = this;
        return {
            total: 0,
            queryParams: {},
            pageSize:8,
            pageNum:1,
            show: false,
            checkedEquipments: [],
            checkAll: false,
            autoplay: false,      
            tableData: [],
            hover: false,
            value: 5000,
            ids: [],            
            selectids: [],
            img_index: '0', // 图片索引
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
                        if(self.tableData.length<1) {return;}
                        self.img_index = this.activeIndex;
                        self.setCurrent(self.tableData[this.activeIndex]) //设置行高亮显示
                        self.resetMeths(); // 重置拖拽，缩小，放大
                    },
                }
            }
        }
    },
    watch: {
        query: {
            handler(newval) {
                if(Object.keys(this.queryParams).length>0) {
                    this.queryParams = newval
                    this.getPictureList();
                }
            },
            deep:true
        }
    },
    created() {     
        this.queryParams = this.query;  
        this.getPictureList();
    }, 
    computed: {
        ...mapGetters([
            'dicts',
        ]),
        tableHeight () {
            return window.innerHeight - 420;
        }
    },
    methods: {          
        alarmHandling(data) {
            // 告警处理
            if(data.confirmState==1) {
                this.$message('已告警处理');
                return;
            }
            this.selectids = [data.id];
            this.show=true;
        },
        tableRowClassName ({row, rowIndex}) {
            //把每一行的索引放进row
            row.index = rowIndex;
        },
        rowClick(row, column, event) {
            // this.setCurrent(row);
            this.$refs.swiperTop.$swiper.slideTo(row.index, 400, false);
        },
        handleCheckAllChange(val){
            // 全选            
            this.$refs.multipleTable.toggleAllSelection();
            this.checkallData(val,this.tableData);
        },
        checkallData(val,data) {
            // 循环全选
            this.checkedEquipments = [];
            for(let i = 0; i<data.length;i++) {
                if(val) {
                    // 全选选中
                    this.checkedEquipments.push(data[i].id);
                } else {
                    // 反选，但因已确认，checkbox有已选择的数据id
                    this.checkedEquipments = this.ids;
                }
            }
        },
        selectall(data) {
            // 全选
            this.checkAll =  data.length == this.tableData.length;
        },
        handleSelectionChange(val) {
            let array = [];
            if(val.length>0) {
                for(let i = 0;i<val.length;i++) {
                    array.push(val[i].id);
                }
            }
            this.checkedEquipments = array;
            let checkedCount = val.length;
            this.checkAll = checkedCount === this.tableData.length;
        },
        checkSelectable (row,index) {
            if (row.confirmState == 1) {
                return false;
            } else {
                return true;
            }
        },
        getList(data) {
            this.checkAll = false;
            this.getPictureList();
        },          
        downloadImg(data) {
            // 下载当前图片
            let imgsrc = this.reDomain(data.alarmDir, data.alarmUrl);
            global.downloadImg(imgsrc,data.alarmUrl);
        },      
        reDomain(url,name) {
            return  url + name;
        },
        setCurrent(row) {
           if(this.$refs.multipleTable){
                this.$refs.multipleTable.setCurrentRow(row);
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
        noautoplay () {
            // 停止自动播放
            if(this.autoplay) {
                this.$refs.swiperTop.$swiper.autoplay.stop();
                this.autoplay = false;
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
        mouseenter () {
            this.hover = true;
        },
        mouseleave () {
            if(document.querySelector('.el-select-dropdown').style.display == 'none') {
                this.hover = false;
            }
        },
        handleEdit(index,row) {
            // 操作
            if(row.confirmState==1) {
                return;
            }
            this.selectids = [row.id];
            this.show=true;
        },                
        getPictureList() {
            /** 查询列表 */
            this.tableData = [];
            this.queryParams['pageNum'] = this.pageNum;
            this.queryParams['pageSize'] = this.pageSize;
            this.checkedEquipments = [];
            this.ids = [];
            this.selectids = [];
            ge_yuntaiAlarm_list2(this.queryParams).then(response => {
                if(response.code == 200 && response.rows && response.rows.length>0) {                
                this.tableData = response.rows;
                this.total = response.total;
                this.selectids = [];
                let array = [];
                // 默认高亮第一
                this.setCurrent(this.tableData[0]);
                // 已预览
                if(this.tableData.length>0) {
                    for(let i = 0;i < this.tableData.length; i++) {
                        if(this.tableData[i].isBrowse == null || this.tableData[i].isBrowse == 0) {
                            array.push(this.tableData[i].id);
                        }
                        if(this.tableData[i].confirmState == 1) {                               
                            this.ids.push(this.tableData[i].id); 
                            this.$nextTick(()=>{
                                this.$refs.multipleTable.toggleRowSelection(this.tableData[i], true);
                            });                     
                        }
                    }
                    this.yuntaiPreview(array);
                }
                }
            });
        },    
        yuntaiPreview(arry) { 
            /** 已预览 */
            if(arry.length<1){return;}              
            let formData = new FormData();            
            formData.append("ids", arry);      
            yuntaiPreview(formData).then(response => {});
        },
        sureOk() {           
            this.selectids = Object.assign([], this.checkedEquipments);  
            if(this.ids.length>0) {
                for(let i = this.ids.length - 1;i>=0; i--) {
                    let index = this.checkedEquipments.indexOf(this.ids[i]);
                    this.selectids.splice(index,1);
                }
            }
            if(this.selectids.length<1) {
                this.$message({
                    message: '请选择未确认内容',
                    type: 'warning'
                });
                return;
            }
            this.show = true;
        }, 
    }
}
</script>
<style lang="scss" scoped>
.alarmList{height:100%;display:flex;flex-direction: column;width:100%;flex:1;overflow:hidden;
    .con{flex:1; overflow: hidden;padding-top:5px;
        ::v-deep{
            .el-col{height:100%;}
            .el-table__body-wrapper{height:calc(100% - 40px);}
            .el-table__row.warning-row{background:#d9ecff;}
        }
    }
    .swiper-box{height:100%;display:flex;position: relative; flex-direction: column;overflow:hidden; background:#000;
        .mask{position: absolute;left:0;right:0;height:30px;top:0;background:$base-color-gray;z-index: 2;display:flex;align-items:center;padding:0 $base-padding10;
            span{color:#fff;font-size:12px;}
            span+span{margin-left:$base-padding;}
            span:last-child{margin-left:auto;}
        }
        .arrowicon{transition: all 0.3s;position: absolute;z-index: 5;width:40px;height:40px;font-size:18px;border:0;background:rgba(0,0,0,0.8);color:rgba(255,255,255,0.7);
            &.arrowleft,&.arrowright{top:50%;margin-top:-30px;}
            &.arrowleft{left:-160px;}
            &.arrowright{right:-160px;}
        } 
        .swiper-gallery{height:100%;width: 100%;overflow: hidden;position: relative;background:#000;
            .gallery-top{width:100%;height:100%;
                .swiper-slide{overflow:hidden;}
                .img-box{position: relative;width:100%;height:100%;
                    img{position:absolute;object-fit: contain;width:100%;height:100%;display: block;}
                }
            }
            &.hover{
                .arrowicon{
                    &.arrowleft{left:16px;}
                    &.arrowright{right:16px;}
                }
            }
        }
        .tools{display:flex;align-items:center;height:100%;padding:0 $base-padding10;margin-left:auto;font-size:12px;position: absolute; opacity: 1; z-index: 5;bottom:0;left:0;right:0;background:rgba(0,0,0,0.6);height:35px;display:flex;align-items:center;
            .item+.item{margin-left:5px;}
            .item{color:#fff;
                &:last-child{margin-left:auto;}
                span{transform: scale(0.8);transform-origin: left;display:inline-block;}
                &:hover{i{color:$base-color-default;}}
                i{font-size:14px;cursor: pointer;}
                ::v-deep{
                    .el-button{transform: scale(0.8);transform-origin: right;}
                    .el-select{width:70px;transform: scale(0.8);transform-origin: left;}
                }
            }
        }
    }
    .foot{position: relative;display:flex;align-items:center;
        ::v-deep{
            .el-checkbox__label{font-size:12px;}
        }
        .btn{margin-left:20px;}
        .pagination-container{margin-left:auto;}
    }
}
</style>