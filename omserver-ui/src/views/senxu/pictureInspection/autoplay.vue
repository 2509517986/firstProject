<template>
     <!-- 自动轮播 -->
  <el-card class="app-container">
    <div class="autoplay">
        <div class="top">
          <div class="tools">
            <div class="timetools" v-if="show">
              <span>距离页面跳转还有<font>{{time}}</font>秒,当前位于第<font>{{queryParams.pageNum}}</font>页</span>
            </div>
            <el-form :inline="true" :model="queryParams" ref="formInline" class="form" size="mini">
                <el-form-item label="查询时间" prop="day">    
                   <el-date-picker size="mini" @change="timechange" :picker-options="expireTimeOption" format="yyyy-MM-dd" clear-icon="" value-format="yyyy-MM-dd" v-model="queryParams.day" type="date" placeholder="选择日期"></el-date-picker>
                </el-form-item> 
                <el-form-item>
                    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery('formInline')">搜索</el-button>
                </el-form-item>
            </el-form>
          </div>
      </div>
      <div class="list" v-if="pics.length>0">
          <div class="con">
              <div :class="['item', 'item' + queryParams.pageSize]" v-for="(e,i) in pics" :key="i">
                  <div class="box">
                      <img :src="reDomain(e.imageDir, e.imageName)" alt="" >
                      <!-- <div class="top">{{e.type}}&nbsp;&nbsp;&nbsp;{{e.gt}}</div> -->
                      <div class="mask">
                          <span class="absolute" v-if="e.isBrowse">已浏览</span>
                          <span>{{Object.keys(dicts).length>0 && dicts.capture_type.obj[e.captureType].dictLabel}}</span>
                          <span>{{e.captureTime}}</span>
                      </div>
                      <div class="tools">
                          <el-button :class="{hui:e.isAlarm == 1}" size="mini" type="danger"  @click="alarmClick(e)">{{e.isAlarm == 0?'手动告警':'已告警'}}</el-button>
                          <el-button type="primary" size="mini"  @click="downloadImg(e)">下载</el-button>
                      </div>
                  </div>
              </div>
          </div>
      </div>
      <div class="list" v-else><el-empty description="暂无数据"></el-empty></div>
      <custom-pagination  v-show="total>0" :total="total" :pageSizes="[4,8,12,16]" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"  @pagination="getList" @autoplay="autoplayInfo"></custom-pagination>
    </div>
    <!-- 手动告警 -->
    <manual-alarm v-if="visible3" :alarminfo="thisalarm" :yuntaiinfo="yuntaiInfo"></manual-alarm>
  </el-card>
</template>
<script>
import { mapGetters } from 'vuex'
import { add_yuntai,edit_yuntai,get_yuntai_detail,del_yuntai,get_yuntaiList,export_yuntai } from "@/api/senxu/devicemanager/yuntai";
import { add_picture,edit_picture,get_picture_detail,del_picture,export_picture,get_pictureList,manualCap, get_pictureListByDay,yuntaiPreview,get_pictureListByDetails,get_pictureHistory } from "@/api/senxu/yuntai_picture/index";
import * as global  from "@/utils/global";export default {
  data () {
    return {
      formInline: {date: [new Date(new Date().getFullYear(), new Date().getMonth()+1, new Date().getDate(), 0, 0), new Date()]},
      total: 150,
      yuntaiInfo:{},
      show: true,
      timer: '', // 倒计时
      time: 10000,
      timeValue: 10000,
      visible3:false,
      thisalarm: {}, // 当前图片信息
      queryParams: {pageNum:1,pageSize:4,day: undefined},
      pics: [],      
      yuntaiId: undefined,
      expireTimeOption: {
          // 当天后不可以选择
          disabledDate(date) {
              return date.getTime() > Date.now();
          }
      }
    }
  },
  computed: {
      ...mapGetters([
          'dicts',
      ]),
  }, 
  created () {
    this.init();
  },
  methods: {
    init () {
        this.time = this.time / 1000;
        this.timeValue = this.timeValue / 1000;
        this.queryParams.day = this.parseTime(new Date(), '{y}-{m}-{d}');
        this.getPics();
    },
    timechange() {
      console.log(11)
    },
    handleQuery(formname) {
      this.getPics();
    },    
    reDomain(url,name) {
        return url + name;
    },
    getList(data) {
      let that = this;
      that.time = data.time / 1000;
      that.timeValue = data.time / 1000;
      that.getPics();
    },
    counttime(time) {
      let that = this;
      that.time = time == 0?that.timeValue:time;
      that.timer = setTimeout(()=>{
        that.time--;
        that.counttime(that.time);
      },1000);
    },    
    getPics () {
        // 请求接口获取数据
        let self = this;     
        get_pictureListByDetails(self.queryParams).then(response => {
            if(response.code == 200 && response.rows && response.rows.length>0) {
              self.pics = response.rows[0].pictures;
              self.total = response.total;  
              let array = [];
              // 已预览
              if(self.pics.length>0) {
                  for(let i = 0;i < self.pics.length; i++) {
                    if(self.pics[i].isBrowse == null || self.pics[i].isBrowse == 0) {
                      array.push(self.pics[i].id);
                    }
                  }
                  this.yuntaiPreview(array);
              }            
            }
        });
    },
    /** 已预览 */
    yuntaiPreview(arry) { 
        if(arry.length<1){return;}              
        let formData = new FormData();            
        formData.append("ids", arry);      
        yuntaiPreview(formData).then(response => { });
    }, 
    getYuntai() {
      get_yuntai_detail(this.yuntaiId).then(response => {
        if(response.code==200) {
          this.yuntaiInfo = response.data;
          this.visible3 = true;
        }
      });
    },  
    downloadImg(data) {
        // 下载当前图片
        let imgsrc = this.reDomain(data.imageDir, data.imageName);
        global.downloadImg(imgsrc,data.imageName);
    },
    alarmClick(picInfo) {
      if(picInfo.isAlarm == 1) {return;}
      this.yuntaiId = picInfo.yuntaiId;
      this.getYuntai();
      picInfo['type'] = 'autoplay';
      this.thisalarm = picInfo;
    },
    pageTotal () {
        let v = this.total % this.queryParams.pageSize == 0? this.total / this.queryParams.pageSize :Math.ceil(this.total / this.queryParams.pageSize);
        return v;
    },
    autoplayInfo(data) {
      // 分页播放开始
      // this.time = data.time / 1000;
      // this.timeValue = data.time / 1000;
      this.autoplay = data.autoplay; 
      if(!this.autoplay) {
        clearTimeout(this.timer); 
        this.time = this.timeValue;
        return;
      } 
      this.counttime(this.time);  
    },
    destroyed() {
        clearTimeout(this.timer);
    }
  }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card,.el-card__body{height:100%;}}
  .autoplay{display:flex; flex-direction: column;height:100%;
    .tools{display: flex;align-items: center;
      .el-form-item--mini.el-form-item{margin-bottom:0;}
      .timetools{margin-left:auto;line-height:29px;margin-right:15px;
        span{display:inline-block;color:$base-font-color;font{font-size:18px; color:$base-color-default;margin:0 5px;}}
      }
    }
    .list{flex:1;padding-top:10px;overflow:hidden;
      .con{margin:0 -5px;display:flex;flex-wrap:wrap;width:100%;height:100%;align-content: flex-start;
        .item{padding:5px;
            .box{position: relative;width:100%;height:100%;border-radius:4px;overflow:hidden;
                &:hover{
                    .mask{display:none;}
                    .tools{display:flex;}
                }
                img{width:100%;object-fit: cover;height: 100%;}        
                .mask{position: absolute;background:$base-color-gray;bottom:0;left:0;right:0;height:35px;padding-top:2px;z-index:2;opacity:0.7;
                    span{display:block;text-align:center;color:#fff;font-size:$base-font-size-small;}
                    span.absolute{position: absolute;color:$base-color-green;left:7px;top:8px;}
                }
                .tools{position: absolute;right:10px;bottom:10px;display: none;
                      .hui{background:$base-menu-color;border-color:$base-menu-color;}
                    .el-button{padding:3px 5px;margin: 0 2px;}
                }
                .top{position: absolute;top:10px;right:10px;transform: scale(0.8);color:#fff; transform-origin: top right;}
            }
            &.item4{width:50%;height:50%;}
            &.item8{width:25%;height:50%;}
            &.item12{width:25%;height:33.3333%;}
            &.item16{width:25%;height:25%;}
        }
      }
    }
  }  
}
</style>
