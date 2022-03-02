<template>
<!-- 告警管理 -->
<el-card class="app-container">  
  <tree-container @ceshi="ccc">
    <div class="alarmmanager">
      <div class="top">
        <div class="tools">
          <el-form :inline="true" :model="queryParams" ref="formInline">
            <el-form-item label="搜索" prop="channelNumber">
              <el-input v-model="queryParams.channelNumber" placeholder="请输入通道名称" clearable size="mini"/>
            </el-form-item>  
            <el-form-item label="告警时间" prop="date">
              <el-date-picker style="width:200px;" v-model="date" size="mini"  format="yyyy-MM-dd"  value-format="yyyy-MM-dd" type="daterange"  range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>  
            </el-form-item>   
            <!-- <el-form-item label="智能分析类型" prop="alarmSource">
                <el-select v-model="alarmSource" placeholder="请选择" size="mini" clearable>
                    <el-option label="终端分析" value="2"></el-option>
                    <el-option label="服务器端分析" value="3"></el-option>
                </el-select>
            </el-form-item> -->
            <el-form-item label="浏览状态" prop="isBrowse">
                <el-select v-model="queryParams.isBrowse" placeholder="请选择" size="mini" clearable>
                    <el-option label="已浏览" value="1"></el-option>
                    <el-option label="未浏览" value="0"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery('formInline')">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery('formInline')">重置</el-button>
                <el-button size="mini" type="success" @click="open = !open">{{open?"折叠":"展开"}}</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="layout">
            <svg-icon @click="tabchange" :icon-class="layout?'list':'layout'" style="height: 18px;width: 18px;" />
        </div>
      </div>
      <div class="checkboxs" v-if="open">
          <div class="item" v-for="(e,i) in checklist" :key="i">
            <template v-if="e.key != 'alarmAis'">
              <h4>{{e.name}}：</h4>
              <div class="checkboxs">
                  <el-checkbox class="checkall" v-model="e.checkAll" @change="val => handleCheckAllChange(val, e)">全选</el-checkbox>
                  <el-checkbox-group v-model="e.checkedEquipments" @change="val => handleCheckedCitiesChange(val, e)">
                      <el-checkbox :label="ee.value" v-for="(ee,ii) in e.list" :key="ii">{{ee.text}}</el-checkbox>
                  </el-checkbox-group>
              </div>
            </template>
          </div>
      </div>
        <alarm-layout  v-if="layout ==0 && Object.keys(postQuery).length>0" :query="postQuery"></alarm-layout>
        <alarm-list v-if="layout ==1 && Object.keys(postQuery).length>0" :query="postQuery"></alarm-list>
    </div>
  </tree-container>
</el-card>
</template>
<script>
import { mapGetters } from 'vuex'
import { add_picture,edit_picture,get_picture_detail,del_picture,export_picture,get_pictureList,manualCap, get_pictureListByDay,yuntaiPreview,get_pictureListByDetails,get_pictureHistory } from "@/api/senxu/yuntai_picture/index";
export default {
  data () {
    return {
      postQuery:{},
      queryParams: {
        channelNumber: undefined,
        spare0: undefined, // 开始时间
        spare1: undefined, // 结束时间
        imgTypes: undefined, // 图片上报
        alarmSources: undefined, // 告警来源 && 智能分析类型
        alarmAis: undefined, // 智能分析
        isBrowse: undefined, // 浏览状态
        confirmStates:undefined, // 确认状态
        lineId:undefined,
        towerId:undefined,
        yuntaiId:undefined
      },
      date: [], // 查询时间
      alarmSource:[], // 告警来源 && 智能分析类型
      layout: 0, // 布局形式
      open: true, // 折叠，展开
      data: [],
      form: {},
      checklist: [
        // {   
        //   name: "上报类型",
        //   checkAll: true,
        //   checkedEquipments: [],
        //   list: [
        //       {id:1,name:'正常告警'},{id:2,name:'重复告警'},{id:3,name:'忽略告警'}
        //   ]
        // },
         {
          name: "图片上报",
          checkAll: true,
          checkedEquipments: [],
          key: 'imgTypes',
          list: [
              {id:1,name:'塔基防范'},{id:2,name:'超高检测'},{id:3,name:'违章检测'},{id:4,name:'杆塔倾斜'},{id:5,name:'区域入侵'},{id:6,name:'线缆断裂'},{id:7,name:'异物悬挂'},{id:8,name:'线缆覆冰'}
          ]
        }, 
        {
          name: "智能分析",
          checkAll: true,
          checkedEquipments: [],
          key: 'alarmAis',
          list: [
              {id:1,name:'吊车'},{id:2,name:'塔吊'},{id:3,name:'堆土机'},{id:4,name:'挖掘机'},{id:5,name:'公共汽车'},{id:6,name:'卡车'},{id:7,name:'汽车'},{id:8,name:'其他车辆'}
          ]
        },
         {
          name: "告警来源",
          checkAll: true,
          checkedEquipments: [],
          key: 'alarmSources',
          list: [
              {id:2,name:'自动告警'},{id:1,name:'手动告警'}
          ]
        }, {
          name: "确认状态",
          checkAll: true,
          key: 'confirmStates',
          checkedEquipments: [],
          list: [
              {value:0,text:'未确认'},{value:1,text:'已确认'}
          ]
        }
      ]
    }
  },  
  computed: {
    ...mapGetters([
        'dicts',
    ])
  }, 
  mounted() {
    // 图片上传
    this.checklist[0].list = this.dicts.img_type.arry;
    // 智能分析
    this.checklist[1].list = this.dicts.alarm_ai.arry;
    // 告警来源
    this.checklist[2].list = this.dicts.alarm_source.arry;
    // 数据初始化-默认全选
    for(let i  in this.checklist) {
        this.checkallData(true, this.checklist[i]);
    }
  },
  methods: {
    tabchange() {
      // 风格切换
      this.layout = this.layout == 0?1:0;
      this.postQuery = {};
      this.postQuery = JSON.parse(JSON.stringify(this.queryParams));
    },
    ccc(data) {
      // 树返回的数据
      this.queryParams.towerId = undefined;this.queryParams.yuntaiId = undefined;this.queryParams.lineId=undefined;
      if(data.node[0].node.type == "line") {this.queryParams.lineId = data.node[0].node.id;} 
      else if(data.node[0].node.type == "tower") {this.queryParams.towerId = data.node[0].node.id;this.queryParams.lineId = data.node[0].node.parentId;}
      else if(data.node[0].node.type == "yuntai") {this.queryParams.yuntaiId = data.node[0].node.id;}      
      this.postQuery = JSON.parse(JSON.stringify(this.queryParams));
    }, 
    resetQuery(formname) {
      // 重置
      this.queryParams.channelNumber = undefined;
      this.queryParams.spare0 = undefined; // 开始时间
      this.queryParams.spare1 = undefined; // 结束时间  
      this.date = [];
      this.queryParams.isBrowse = undefined; // 浏览状态
      for(let i = 0; i< this.checklist.length;i++) {
        this.handleCheckAllChange(true, this.checklist[i]);
        this.checklist[i].checkAll = true;
      }
      this.postQuery = JSON.parse(JSON.stringify(this.queryParams));
    },
    handleQuery(formname) {
      // 查询
      this.queryParams.channelNumber = this.queryParams.channelNumber&&this.queryParams.channelNumber.length>0?this.queryParams.channelNumber:undefined;
      this.queryParams.isBrowse = this.queryParams.isBrowse&&this.queryParams.isBrowse.length>0?this.queryParams.isBrowse:undefined;
      this.queryParams.spare0 = this.date&&this.date.length>0?this.date[0]:undefined;
      this.queryParams.spare1 = this.date&&this.date.length>0?this.date[1]:undefined;
      this.postQuery = JSON.parse(JSON.stringify(this.queryParams));
    },
    checkallData(val,item) {
        // 循环全选
        let cc = []
        for(let key in item.list) {
            cc.push(item.list[key].value)
        }
        item.checkedEquipments = val ? cc : [];
    },
    handleCheckAllChange(val,item) {
        // 全选
        this.checkallData(val,item)
        this.queryParams[item.key] = undefined;
    },
    handleCheckedCitiesChange(val,item) {
        // 单个checkbox
        let checkedCount = val.length;
        item.checkAll = checkedCount === item.list.length;
        if(item.key == 'alarmSources') {    
          this.queryParams['alarmSources'] = item.checkedEquipments.length>0?item.checkedEquipments.length == item.list.length?undefined:item.checkedEquipments[0] == 1?item.checkedEquipments.join(","):'2,3':undefined;
        } else {
          this.queryParams[item.key] = item.checkedEquipments.length>0?item.checkedEquipments.length == item.list.length?undefined:item.checkedEquipments.join(","):undefined;
          
        }
    }
  }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card,.el-card__body{height:100%;}.el-checkbox__input.is-checked + .el-checkbox__label{color:#606266;}}
  .alarmmanager{display:flex; flex-direction: column;height:100%;
    .top{display:flex;align-items:center;
      ::v-deep{.el-form-item{margin-bottom:0;}.el-input,.el-select{width:120px;}.el-date-editor{width:180px;}}
      .tools{flex:1;}
      .layout{margin-left:$base-padding;color:$base-menu-color;cursor: pointer;}
    }
    .checkboxs{margin-top:5px;
        .item{display:flex;margin:5px 0;align-items:center;
            h4{width:65px;padding-top:4px;margin:0;color:$base-color-black;font-size:12px;font-weight: normal;}
            .checkboxs{flex:1;overflow:hidden;position: relative;
                .checkall{position:absolute;}
                .el-checkbox-group{
                    .el-checkbox:first-child{margin-left:55px;}
                }
                ::v-deep{.el-checkbox{padding:0;margin-right:10px;.el-checkbox__label{font-size:12px;padding-left:5px;}}}
            }
        }
    }
    .conc{flex:1;overflow:hidden;display:flex;}
  }
}
</style>