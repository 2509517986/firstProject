<!--
 * @Author: YJY
 * @Date: 2022-02-01 15:52:26
 * @LastEditTime: 2022-02-26 22:46:53
 * @Description: file content
-->
<template>
  <el-card class="app-container">
    <tree-container @ceshi="getTreeKey" :fatherName="'drone'">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="70px" v-show="showSearch" size="mini">
        <el-form-item>
          <div class="inspection-tips">
            {{inspectionTitle}}
          </div>
        </el-form-item>
        <el-form-item label="任务" prop="taskId">
          <el-input v-model="queryParams.taskId" placeholder="请输入任务ID" clearable size="mini" style="width: 120px" @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="航线" prop="routeId">
          <el-input v-model="queryParams.routeId" placeholder="请输入航线ID" clearable size="mini" style="width: 120px" @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="同步时间" prop="updateTime">
          <el-date-picker size="mini" :picker-options="expireTimeOption" format="yyyy-MM-dd" clear-icon="" style="width:140px" value-format="yyyy-MM-dd" v-model="queryParams.updateTime" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
        <el-form-item>
          <div class="layout">
            <svg-icon @click="()=>{this.layout = !this.layout}" :icon-class="layout?'list':'layout'" style="height: 18px;width: 18px;" />
          </div>
        </el-form-item>
      </el-form>
      <div class="inspetion-content" v-loading="loading">
        <!-- 存在  直接展示四条-->
        <div class="lists" v-if="layout && pics.length>0">
          <div class="listcon">
            <el-row :gutter="10">
              <el-col class="listcon-col" :span="6" v-for="(e, i) in pics" :key="i"  shadow="hover" @dblclick.native="dblclick(e)">
                 <el-card :class="['imgcard',{act:e.act==i}]" :body-style="{padding: '0px',position: 'relative'}">
                    <el-tooltip class="item" effect="dark" content="双击查看详情" placement="top">
                      <el-image :src="reDomain(e.path, e.imageName)" alt="" fit="cover" class="img" lazy></el-image>
                    </el-tooltip>
                    <div class="mask">
                      <span class="absolute" v-if="e.isBrowse">已浏览</span>
                      <span>{{Object.keys(dicts).length>0 && e.captureType && dicts.capture_type.obj[ee.captureType] && dicts.capture_type.obj[e.captureType].dictLabel}}</span>
                      <span>{{e.updateTime}}</span>
                    </div>
                    <div class="tool">
                      <el-button size="mini" :class="{hui:e.isCorrect == 0}" type="danger" @click.stop="alarmClick(e)">{{e.isCorrect == 0 ? '已告警' : '手动告警'}}</el-button>
                      <i class="el-icon-download" @click="downloadImg(e)"></i>
                    </div>
                  </el-card>
              </el-col>
            </el-row>

            <!-- <el-card shadow="never" class="item" v-for="(e,i) in pics" :key="i" :body-style="{paddingLeft: '0px',paddingRight: '0px',paddingTop:'20px',paddingBottom:'0'}">
              <el-row :gutter="12" v-if="e.pictures&&e.pictures.length>0">
                <el-col :span="6" v-for="(ee,ii) in e.pictures" :key="ii" shadow="hover" @dblclick.native="dblclick(e.yuntai,ee)">
                  <el-card :class="['imgcard',{act:e.act==ii}]" :body-style="{padding: '0px',position: 'relative'}">
                    <el-tooltip class="item" effect="dark" content="双击查看详情" placement="top">
                      <el-image :src="reDomain(ee.imageDir, ee.imageName)" alt="" fit="cover" class="img" lazy></el-image>
                    </el-tooltip>
                    <div class="mask">
                      <span class="absolute" v-if="ee.isBrowse">已浏览</span>
                      <span>{{Object.keys(dicts).length>0 && ee.captureType && dicts.capture_type.obj[ee.captureType] && dicts.capture_type.obj[ee.captureType].dictLabel}}</span>
                      <span>{{ee.captureTime}}</span>
                    </div>
                    <div class="tool">
                      <el-button size="mini" :class="{hui:ee.isAlarm == 1}" type="danger" @click.stop="alarmClick(ee,e.yuntai)">{{ee.isAlarm == 0?'手动告警':'已告警'}}</el-button>
                      <i class="el-icon-download" @click="downloadImg(ee)"></i>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
              <el-row v-else :gutter="12">
                <el-col :span="6">
                  <el-image class="error_img">
                    <div slot="error" class="image-slot"><span>暂无图片</span></div>
                  </el-image>
                </el-col>
              </el-row>

            </el-card> -->
          </div>
        </div>
        <!-- 存在，展示细致的两条 -->
        <div class="nolayoutLists" v-if="!layout && pics.length>0">

        <div class="lists" >
             <div class="fourLayout">
                <div class="item" v-for="(e,i) in pics" :key="i">
                    <div class="itemcon">
                        <el-tooltip class="item" effect="dark" content="双击查看详情" placement="top">
                            <el-image   @dblclick.native="dblclick(e)" :src="reDomain(e.path, e.imageName)" alt="" fit="cover" class="img" lazy></el-image>
                        </el-tooltip>
                        <!-- <el-image class="error_img" v-if="e.pictures && e.pictures.length<1"><div slot="error" class="image-slot"><span>暂无图片</span></div></el-image> -->
                        <div class="mask">
                            <span class="absolute" v-if="e.isBrowse">已浏览</span>
                            <span>{{e.imageName}}</span>
                            <span>{{e.updateTime ? e.updateTime :'暂无时间'}}</span>    
                        </div>
                        <div class="tools">
                            <span>{{Object.keys(dicts).length>0 && dicts.oem.obj[e.factory] && dicts.oem.obj[e.factory].dictLabel}}</span>
                            <span>{{Object.keys(dicts).length>0 && dicts.monitor_type.obj[e.monitorType] && dicts.monitor_type.obj[e.monitorType].dictLabel}}</span>
                            <svg-icon icon-class="photography" @click="visible=true;thisyuntai=e"/>
                            <svg-icon icon-class="video"  @click="visible2=true;thisyuntai=e"/>
                            
                        </div>
                        <div class="tool">
                            <el-button :class="{hui:e.isCorrect == 0 }" size="mini" type="danger"  @click="alarmClick(e)">{{e.isCorrect == 0 ?'已告警':'手动告警'}}</el-button>
                            <i class="el-icon-download" @click="downloadImg(e)"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>

        <!-- 不存在 -->
        <el-empty v-if="pics.length<1" description="暂无数据"></el-empty>
      </div>
      <custom-pagination v-show="total>0" :total="total" :pageSizes="[12,16,20,24]" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList"></custom-pagination>
    </tree-container>
    <!-- 显示详情 -->
    <div :class="['showdetailbox',{left: show == 2}]" v-if="show == 1 || show == 2" ><airportPicture @close="close" :fatherName="'drone'" :imgcheck="imgcheckdata" ></airportPicture></div>
       
    <!-- 手动告警 -->
    <manual-alarm v-if="visible3" :alarminfo="thisImgInfo" :yuntaiinfo="thisyuntai" @close="alarmClose"></manual-alarm>
   
  </el-card>
</template>
<script>
import { mapGetters } from 'vuex'
import { get_uavInspection_list } from "@/api/senxu/inspection/index";
import manualAlarm from './droneChildCom/manualAlarm.vue'
import airportPicture from '../modules/airportPicture.vue'
import * as global from "@/utils/global";
export default {
  components:{manualAlarm,airportPicture},
  data () {
    return {
      // 显示搜索条件
      showSearch: true,
      loading: true,
      day: [],
      timeLimit: 2,
      inspectionTitle: '',
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 12,
        airportId:'',
        name: undefined,
        lineIds: undefined,
        towerIds: undefined,
        yuntaiId: undefined,
        routeId: undefined,
        taskId: undefined,
        updateTime:'',
        // spare0: undefined,
        // spare1: undefined
      },
      // eventlist: [],
      expireTimeOption: {
        // 当天后不可以选择
        disabledDate (date) {
          return date.getTime() > Date.now();
        }
      },
      total: 1,
      layout: true,
      pics: [],
      // 弹窗
      show:0,//0-列表 1-图片详情 2-点击设备
      imgcheckdata: {presetBit:1,updateTime:undefined,yuntaiId:undefined,single:false,state:undefined,imageId:0}, // 传递到图片详情
      
      visible3: false,
      thisImgInfo: {}, // 当前图片信息
      thisyuntai: {}, // 当前云台信息
    }
  },
  computed: {
    ...mapGetters([
      'dicts',
    ]),
    tableHeight () {
      return window.innerHeight - 330;
    }
  },
  created () {
    // console.log(this.pics)
    this.queryParams.updateTime = this.parseTime(new Date(), '{y}-{m}-{d}');
    // this.queryParams.spare0 = this.parseTime(new Date(), '{y}-{m}-{d}');
    // this.queryParams.spare1 = this.parseTime(new Date(), '{y}-{m}-{d}');
    // this.day = [this.parseTime(new Date(), '{y}-{m}-{d}'), this.parseTime(new Date(), '{y}-{m}-{d}')];
  },
  methods: {
    alarmClose(e){
      if(e){
         this.getList();
      }
      this.visible3 = false;
    },
    table_index (index) {
      return (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1
    },
    getTreeKey (data) {
      console.log(data, 'data-----');
      // 判断当前层级，parent
      if(data.parent.length == 1){
        // 说明点击的是最顶层，这个时候默认提供第一个机场的id
        this.queryParams.airportId =  data.node[0].yuntais[0].children[0].airportId;      
        this.inspectionTitle = data.parent[data.parent.length - 1].name ||  data.node[0].yuntais[0].children[0].airportNames;

      }else {
        // 说明点击的是机场或是无人机,赋值当前
        this.queryParams.airportId =  data.node[0].yuntais[0].airportId;
        this.inspectionTitle = data.parent[data.parent.length - 1].name || data.node[0].yuntais[0].airportNames;
      }

      // this.queryParams.airportId =  data.node[0].yuntais[0].airportId;
      // this.reset();
      this.queryParams.towerIds = undefined, this.queryParams.lineIds = undefined; this.queryParams.yuntaiId = undefined;
      // 从树结构带来数据
      if (data.node[0].node.type == "line") { 
        this.queryParams.lineIds = data.node[0].node.id; }
      else if (data.node[0].node.type == "tower") { 
        this.queryParams.towerIds = data.node[0].node.id; 
        this.queryParams.lineIds = data.node[0].node.parentId; }
      else if (data.node[0].node.type == "yuntai") { 
        this.queryParams.yuntaiId = data.node[0].node.id; 
      }

      this.getList();
    },
    reset () {
      this.queryParams.name = undefined;
      this.queryParams.routeId = undefined;
      this.queryParams.taskId = undefined;

      this.queryParams.updateTime = this.parseTime(new Date(), '{y}-{m}-{d}');
      // this.queryParams.spare0 = this.parseTime(new Date(), '{y}-{m}-{d}');
      // this.queryParams.spare1 = this.parseTime(new Date(), '{y}-{m}-{d}');
      this.day = [this.parseTime(new Date(), '{y}-{m}-{d}'), this.parseTime(new Date(), '{y}-{m}-{d}')];
    },
    /** 搜索按钮操作 */
    handleQuery () {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery () {
      this.reset();
      this.handleQuery();
    },
    getList () {
      this.loading = true;
      // this.queryParams.spare0 = this.day && this.day.length > 0 ? this.day[0] : undefined;
      // this.queryParams.spare1 = this.day && this.day.length > 0 ? this.day[1] : undefined;
       this.pics =[]
      get_uavInspection_list(this.queryParams).then(response => {
        if (response.code == 200 && response.rows) {
          // this.eventlist = response.rows;
          this.pics = response.rows;
          this.total = response.total;
          // this.total = 35;
          this.loading = false;
        }
      }).catch(e=>{
        this.loading = false;
        this.$message.error('请求报错')
        console.log(e);
      });
    },
    filterHandler (value, row, column) {
      // 表格状态筛选事件
      const property = column['property'];
      return row[property] == value;
    },
    dblclick (parent, item) {
      console.log(parent, item, 'dblclick')
      // 显示详情
      this.show = 1;
      this.imgcheckdata.yuntaiId = parent.id;
      this.imgcheckdata.presetBit = parent?.presetBit;
      this.imgcheckdata.updateTime =  this.queryParams.updateTime;
      //机场ID
      this.imgcheckdata.airportId = parent.airportId;
      // 双击的图片的id
      this.imgcheckdata.imageId = parent.imageId;
      // 当前所在页面，当前页大小
      this.imgcheckdata.pageNum = this.queryParams.pageNum,
      this.imgcheckdata.pageSize = this.queryParams.pageSize,

      this.imgcheckdata.single = true;            
      this.imgcheckdata.state = parent.state;
      this.thisyuntai = parent;
    },
    close(data) {
        this.show = data.show;
    },
    reDomain (url, name) {
      // console.log(url, name, 'reDomain,url,name')
      return url;
    },
    alarmClick (pictureInfo, yuntainInfo) {
      // console.log(pictureInfo, yuntainInfo, '手动警告')
      // 手动告警
      // if(pictureInfo.isCorrect == 0) {return;}
      this.visible3=true;
      this.thisImgInfo=pictureInfo;
      // this.thisyuntai=yuntainInfo;
      // this.thisyuntai=pictureInfo;
    },
    downloadImg (data) {
      // 下载当前图片
      console.log(data, '下载图片')
      let imgsrc = this.reDomain(data.path, data.imageName);
      global.downloadImg(imgsrc, data.imageName);
    },
  }
}
</script>
<style lang="scss" scoped>
.app-container {
  height: $base-app-main-height;
  ::v-deep {
    .el-card,
    .el-card__body {
      height: 100%;
    }
  }
  .layout{
    margin-left:$base-padding;color:$base-menu-color;cursor: pointer;
  }
  .inspetion-content {
    border: 1px solid #ccc;
    width: 100%;
    height: calc(100% - 110px);
    position: relative;
    overflow: auto;
    .nolayoutLists{
      display: flex;
      height: 100%;
      width: 100%;
       .lists{
          flex:1;display:flex; flex-direction: column;overflow: hidden;
        .fourLayout{display:flex;flex-wrap:wrap;flex:1;overflow-y:auto;overflow-x:hidden;margin:$base-padding10 -5px 0;
              .item{
                  position: relative;width:50%;padding:10px 5px 0;cursor: pointer;height:50%;
                  .itemcon{width:100%;height:100%;position: relative;overflow:hidden;overflow: hidden;border-radius:4px;}
                  .img{width:100%;height:100%;display:block;object-fit: cover;}
                  .el-image{border-radius:4px;overflow:hidden;}
                  .mask{position: absolute;background:rgba(0,0,0,0.6);bottom:0;left:0px;right:0px;height:35px;padding-top:2px;
                      span{display:block;text-align:center;color:#fff;font-size:$base-font-size-small;}
                      span.absolute{position: absolute;color:$base-color-green;left:10px;top:8px;}
                  }
                  .tools{position: absolute;background:rgba(0,0,0,0.6);left:0px;top:0px;right:0px;align-items:center;height:30px;display:flex;align-items:center; justify-content: flex-end;color:#fff;padding:0 10px;
                      span{margin:0 5px;}
                      svg{color:#fff;font-size:18px;margin-left:10px;&:hover{color:$base-color-red2;}}
                  }
                  .tool{position: absolute;display:none;bottom:6px;;right:20px;align-items:center;z-index:2;
                      .hui{background:$base-menu-color;border-color:$base-menu-color;}
                      i{color:#fff;font-size:20px;margin-left:10px;}
                  }
                  &:hover{
                      .mask{display: none;}
                      .tool{display: flex;}
                  }
              }
              .error_img{width:100%;height:100%;display:block;font-size:14px;display:flex; flex-direction: column; justify-content: center; align-items: center; background: #333;color: #909399;}
          }
       }
    }

    .lists{
       flex:1;display:flex; flex-direction: column;overflow: hidden;
       .listcon{flex:1;overflow-y:auto;overflow-x:hidden;
          .listcon-col{
            padding-top:10px;
          }
          .item{
            border-radius: 0;border:0;padding:0;padding-top:$base-padding;
            &:first-child{padding-top:0;}
            ::v-deep{
                .el-card__header{padding-left:0;padding-bottom:13px;}
            }
            .imgcard{
              border:0;position: relative;cursor: pointer;
              &:before{content: "";position: absolute;background:rgba(0,0,0,0.5);left:0;right:0;bottom:0;top:0;z-index:1;display:none;}
              .img{width:100%;display:block;height:165px;}
              .mask{
                position: absolute;background:rgba(0,0,0,0.6);bottom:0;left:0;right:0;height:35px;padding-top:2px;z-index:2;opacity:0.7;
                span{display:block;text-align:center;color:#fff;font-size:$base-font-size-small;}
                span.absolute{position: absolute;color:$base-color-green;left:7px;top:0px;}
              }
              .tool{position: absolute;display:none;bottom:8px;;right:10px;align-items:center;z-index:2;
                  .hui{background:$base-menu-color;border-color:$base-menu-color;}
                  i{color:#fff;font-size:20px;margin-left:10px;}
              }
              &.act{
                &:before{display:none;}opacity: 1;.mask{opacity: 1;}
              }
              &:hover{
                &:before{display: none;}  opacity: 1;               
                .mask{display:none;}
                .tool{display:flex;}
              }
            }
          }
        }
       
        .imgcard {
              border: 0;
              position: relative;
              cursor: pointer;
              &:before {
                content: "";
                position: absolute;
                background: rgba(0, 0, 0, 0.5);
                left: 0;
                right: 0;
                bottom: 0;
                top: 0;
                z-index: 1;
                display: none;
              }
              .img {
                width: 100%;
                display: block;
                height: 165px;
              }
              .mask {
                position: absolute;
                background: rgba(0, 0, 0, 0.6);
                bottom: 0;
                left: 0;
                right: 0;
                height: 35px;
                padding-top: 2px;
                z-index: 2;
                opacity: 0.7;
                span {
                  display: block;
                  text-align: center;
                  color: #fff;
                  font-size: $base-font-size-small;
                }
                span.absolute {
                  position: absolute;
                  color: $base-color-green;
                  left: 7px;
                  top: 8px;
                }
              }
              .tool {
                position: absolute;
                display: none;
                bottom: 8px;
                right: 10px;
                align-items: center;
                z-index: 2;
                .hui {
                  background: $base-menu-color;
                  border-color: $base-menu-color;
                }
                i {
                  color: #fff;
                  font-size: 20px;
                  margin-left: 10px;
                }
              }
              &.act {
                &:before {
                  display: none;
                }
                opacity: 1;
                .mask {
                  opacity: 1;
                }
              }
              &:hover {
                &:before {
                  display: none;
                }
                opacity: 1;
                .mask {
                  display: none;
                }
                .tool {
                  display: flex;
                }
              }
        }
      }
        .error_img {
          height: 165px;
          width: 100%;
          font-size: 14px;
          display: flex;
          border-radius: 4px;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          background: #333;
          color: #909399;
        }
        
  }
}

.showdetailbox{position: absolute;top:15px;left:20px;right:20px;bottom:20px;z-index: 3;&.left{left:290px;}}
  

</style>
