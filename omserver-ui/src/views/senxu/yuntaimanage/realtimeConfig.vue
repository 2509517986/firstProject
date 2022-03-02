<template>
  <!-- 参数实时配置 -->
  <el-card class="app-container">
    <tree-container @ceshi="ccc">
      <div class="realtimeConfigBox">
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane v-for="(e,i) in tabs" :key="i" :label="e.name"></el-tab-pane>
        </el-tabs>
        <div class="tabcontent">
          <span class="title">当前设备：{{Object.keys(thisYuntaiInfo).length>0?thisYuntaiInfo.name:"暂无在线设备"}}</span>
          <template v-if="tabcomponents.length>0">
            <div class="item" v-for="(ee,ii) in tabcomponents" :key="ii">
              <component :is="ee" :favIcon="activeName == '0'?false:true" :treeData.sync="treeData" v-if="Object.keys(treeData).length>0"></component>
            </div>
          </template>
          <!-- <span v-else class="nofav">暂无收藏<br><font @click="activeName = 'tab_1'">请添加配置收藏</font></span> -->
        </div>
      </div>
    </tree-container>
  </el-card>
</template>
<script>
import * as global  from "@/utils/global";
export default {
    data () {
        return {
          activeName:'0',
          treeData: {},
          tabcomponents: [],
          thisYuntaiInfo: {},
          tabs:[
            // {name:"常用配置",components:[]},
            {name:"设备配置",components:['terminalParam','terminalConfig', 'osdConfig']}
            ,{name:"平台信息",components:['platformInfo']}
            ,{name:"图像采集",components:['acquisitionParam','snapTime']}
            ,{name:"视频参数",components:['videoParamConfig','recordingTimeline']}
            ,{name:"设备系统管理",components:['equipmentManage','remoteUpgrade']}
          ]
        }
    },
    created() {
      //
      this.tabcomponents = this.tabs[0].components;
      // this.tabs[0].components = global.getLocalStorage("configMenu")?global.getLocalStorage("configMenu"):[];
    },
    methods: {
      ccc(data) {
        // 树传过来数据
        this.treeData = data;
        this.getYuntaiInfo(data);
      },
      handleClick(tab, event) {
        // if(this.activeName == '0') {          
        //   this.tabs[0].components = global.getLocalStorage("configMenu")?global.getLocalStorage("configMenu"):[];
        // }
        this.tabcomponents = [];
        this.tabcomponents = this.tabs[this.activeName].components;
        // console.log(tab, event);
      },
      getYuntaiInfo(data) {
        for(let i = 0;i < data.node[0].yuntais.length;i++) {
              if(data.node[0].yuntais[i].state == 1) {
                  this.thisYuntaiInfo = data.node[0].yuntais[i];
                  return;
              }
        }
      }
    }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card__body{height:100%;}}
  .realtimeConfigBox{height:100%;display:flex; flex-direction: column;
  }
  .title{display: block;padding: 0px 13px;background: #fff;margin: 0px 0 5px;}
  .tabcontent{flex:1;overflow-y: auto;
    .item{padding:10px;}
  }
  .nofav{height: 150px;padding-top:60px;display: block;background: #f7f7f7;border-radius: 4px;color: #ccc;width: 98%;text-align: center;margin: 10px auto 0;font{color:$base-color-default;padding-top:5px;display:block;cursor: pointer;}}
}
</style>