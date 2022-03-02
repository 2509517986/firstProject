<template>
     <!-- 设备总览 -->
    <el-card class="app-container">  
        <tree-container @ceshi="ccc">
            <div class="top">
                <div class="leftc">
                    <span>全部：<font>{{Object.keys(TYuntaiStatistic).length>0 && TYuntaiStatistic.total?TYuntaiStatistic.total:0}}</font></span>
                    <span class="green">在线：<font>{{Object.keys(TYuntaiStatistic).length>0 && TYuntaiStatistic.online?TYuntaiStatistic.online:0}}</font></span>
                    <span class="red">离线：<font>{{Object.keys(TYuntaiStatistic).length>0 && TYuntaiStatistic.offline?TYuntaiStatistic.offline:0}}</font></span>
                    <span>休眠：<font>{{Object.keys(TYuntaiStatistic).length>0 && TYuntaiStatistic.dormancy?TYuntaiStatistic.dormancy:0}}</font></span>
                    <span class="blue">在线率：<font>{{Object.keys(TYuntaiStatistic).length>0 &&TYuntaiStatistic.onlineRate?(TYuntaiStatistic.onlineRate * 100).toFixed(2):0}}%</font></span>
                </div>
                <div class="rightc">
                    <span>排序：</span>
                    <el-radio-group v-model="queryParams.powerOrd" size="mini" @change="radioOrd">
                        <el-radio-button label="1">电量升序</el-radio-button>
                        <el-radio-button label="0">电量降序</el-radio-button>
                    </el-radio-group>
                </div>
            </div>
            <div class="content">
                <ul v-if="lists.length>0">
                    <li v-for="(e,i) in lists" :key="i">
                        <div :class="['box',e.state == -1 || !e.state?'out':e.state == 2?'sheep':'']" @click="torouter(e)">
                            <div class="p1"><div class="icon"><svg-icon icon-class="yuntaiIcon" class="ballIcon"></svg-icon></div><span>{{e.name}}</span></div>
                            <div class="p2">
                                <span class="status">{{e.state==1?"在线":e.state==2?"休眠":"离线"}}</span>
                                <img class="imgsend" :src="e.state==-1 || !e.state?'/static/images/s3.png':e.imageStatus == 1?'/static/images/s2.png':'/static/images/s1.png'">
                                <div class="batteryIcon">
                                    <div class="batteryIconBody">
                                        <div class="batteryIconProgress" :style="{width:(e.electricPower?e.electricPower:0) +'%'}"></div>
                                        <label class="batteryIconContent">{{e.electricPower?e.electricPower:0}}%</label>
                                        <div class="batteryIconHead"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
                <el-empty description="暂无数据" v-else></el-empty>
            </div>
        </tree-container>
    </el-card>
</template>
<script>
import {get_yuntaiList,selectTYuntaiTotal } from "@/api/senxu/devicemanager/yuntai";
export default {
    data () {
        return {
            queryParams:{powerOrd: 1, lineId: undefined, towerId:undefined},
            lists: [],
            TYuntaiStatistic:{} 
        }
    },
    mounted() {
    },
    methods: {
        torouter(item) {
            if(item.state == -1) {return;}
            this.$router.push({path: '/videoInspection/control/' + item.id})
        },
        ccc(data) {
             // 从树结构带来数据
            if(data.node[0].node.type == "line") {this.queryParams.lineId = data.node[0].node.id;this.queryParams.towerId = undefined;} 
            else if(data.node[0].node.type == "tower") {this.queryParams.towerId = data.node[0].node.id;this.queryParams.lineId = data.node[0].node.parentId;}
            else if(data.node[0].node.type == "yuntai") {this.$message('请选择线路或者杆塔');return;}
            this.getList();
            this.selectTYuntaiTotal();
        },     
        radioOrd() {
            this.getList();
        }, 
        /** 查询云台列表 */
        getList() {
            get_yuntaiList(this.queryParams).then(response => {
               if(response.code == 200) {
                    this.lists = response.rows;
               }
            });
        },
        selectTYuntaiTotal() {
            selectTYuntaiTotal(this.queryParams).then(response => {
               if(response.code == 200 && response.TYuntaiStatistic) {
                    this.TYuntaiStatistic = response.TYuntaiStatistic;
               }
            });
        }
    },
    destroyed() {

    }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card,.el-card__body{height:100%;}}
  .top{padding:$base-padding10 $base-padding;display:flex;align-items:center;
      .leftc{
          span{margin-right:$base-padding;
              &.blue{color:$base-color-default;}
              &.green{color:$base-green-color;}
              &.red{color:$base-color-red;}
              font{font-size:$base-font-size-big;}
          }
      }
      .rightc{margin-left:auto;display:flex;align-items:center;
          span{margin-right:5px;}
      }
  }
  .content{overflow: hidden;padding:$base-padding10 $base-padding;
      ul{display: flex;flex-wrap: wrap;margin:0 -$base-padding10;
          li{width:16.6666%;padding:$base-padding10;cursor: pointer;
              .box{border:1px solid $base-border-color;border-radius:4px;padding:$base-padding10;
                    .p1{display:flex;
                        .icon{width:50px;height:50px;display:flex; justify-content: center; align-items: center; text-align:center;border-radius: 50%;position: relative;border:1px solid $base-color-default;
                            .ballIcon{font-size:34px;color:$base-color-default;}
                        }
                        span{padding-left:$base-padding10;padding-top:$base-padding10;}
                    }
                    .p2{display: flex;align-items: center;padding-top:$base-padding10;
                        .status{position: relative;padding-left:15px;color:$base-green-color;
                            &:before{content: "";position: absolute;left:0;height:8px;width:8px;top:50%;margin-top:-4px;border-radius: 50%;background:$base-green-color;}
                        }
                        .imgsend{width:16px;font-size:16px;display:block;margin-left:auto;margin-right:15px;color:$base-color-hui;}
                        .batteryIcon{
                            .batteryIconBody{ position: relative;width: 30px;height:16px;border-radius: 2px;
                                &:before{content: "";border:1px solid $base-color-default;position:absolute;top:0;bottom:0;right:2px;left:0;}
                                .batteryIconProgress{height: 16px;background:$base-color-default;}
                                .batteryIconContent{position: relative; font-size: 12px; left: 0px; top: -17px;transform: scale(0.8);font-weight:normal;display:block;text-align:center;}
                                .batteryIconHead{ position: relative;height: 7px;width: 3px;background: $base-color-default;display: block;position: absolute;top: 4px; left: 28px;}
                            }
                        }
                    }
                    &.out {
                        .p1{
                            .icon{border-color:$base-menu-color;
                                .ballIcon{color:$base-menu-color;}
                                &:before{content:"";position: absolute;height:1px;left:0;right:0;background:$base-menu-color;top:25px; transform: rotate(45deg);}
                            }
                            span{color:$base-menu-color;}
                        }
                        .p2{
                            .status{color:$base-menu-color;&:before{background:$base-menu-color;}}
                            .batteryIcon{
                                .batteryIconBody{
                                    &:before{border-color:$base-menu-color;}
                                    .batteryIconContent{color:$base-menu-color;}
                                    .batteryIconProgress{background-color:$base-menu-color;}
                                    .batteryIconHead{background:$base-menu-color;}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
</style>