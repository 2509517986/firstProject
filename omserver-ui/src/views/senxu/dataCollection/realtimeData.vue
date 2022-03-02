<template>
     <!-- 实时数据 -->
    <el-card class="app-container">        
        <tree-container @ceshi="ccc">
            <div class="realtimeData">
                <el-card shadow="never" class="box" :body-style="{paddingLeft: '0px',paddingRight: '0px',paddingTop:'20px',paddingBottom:'0'}">
                    <div slot="header" class="elcard-top">
                        <svg-icon icon-class="ballmachine" class="yuntaiIcon"></svg-icon>
                        <span class="name">通道名称：3123</span>
                    </div>
                    <div class="list">
                        <div :class="['item', {break:e.type=='rainfallIntensity'}]" v-for="(e,i) in list" :key="i">
                            <img :src="e.online==0?require('$img/data/' + parseInt(i+1) +'.png'):require('$img/data/' + parseInt(i+1) +'-1.png')" alt="">
                            <span class="name">{{e.name}}</span>
                            <p>{{e.value?e.value:''}}{{e.unit?e.unit:''}}</p>
                        </div>
                    </div>
                </el-card>
            </div>
        </tree-container>
    </el-card>
</template>
<script>
import * as global  from "@/utils/global";
import {get_realtimeData}  from '@/api/senxu/dataCollection'
export default {
    props: ['childData'],
    data () {
        return {
            // tableData = {},
            // powerV : '',
            // batteryPower : '',
            // outdoorTemperature : '',
            // relativeHumidity : '',
            // atmosphericPressure : '',
            // windDirection : '',
            // windSpeed : '',
            // rainfallIntensity : '',
            list: [
                 {online:0,name: '光伏阵列电流',value:'0.012', type:'energyA', unit: 'A'}
                ,{online:0,name: '光伏阵列电压',value: '14.9', type:'energyV', unit: 'V'}
                ,{online:0,name: '蓄电池电流',value: '-0.03', type:'powerA', unit: 'A'}
                ,{online:1,name: '蓄电池电压',value: '7.3', type:'powerV', unit: 'V'}
                ,{online:0,name: '负载电流',value: '0.063', type:'loadA', unit: 'A'}
                ,{online:0,name: '负载电压',value: '7.3', type:'loadV', unit: 'V'}
                ,{online:1,name: '电池电量',value: '99', type:'batteryPower', unit: '%'}
                ,{online:0,name: '电池温度',value: '29', type:'BatteryTemperature', unit: '℃'}
                ,{online:0,name: '剩余流量',value: '0', type:'RemainingFlow', unit: 'M'}
                ,{online:0,name: '信号强度',value: '18', type:'signalStrength', unit: null}
                ,{online:1,name: '室外温度',value: '40', type:'outdoorTemperature', unit: '°'}
                ,{online:0,name: '光辐射',value: null, type:'lightRadiation', unit: null}
                ,{online:1,name: '相对湿度',value: '40', type:'relativeHumidity', unit: '%'}
                ,{online:1,name: '大气压强',value: '101', type:'atmosphericPressure', unit: 'Pa'}
                ,{online:1,name: '风向',value: '东南', type:'windDirection', unit: '°'}
                ,{online:1,name: '风速',value: '11', type:'windSpeed', unit: 'm/s'}
                ,{online:1,name: '降雨强度',value: '12', type:'rainfallIntensity', unit: 'MM'}
                ,{online:1,name: '杆塔震动',value: null, type:'towerVibration', unit: null}
                ,{online:1,name: '杆塔倾斜',value: null, type:'towerTilt', unit: null}
                ,{online:1,name: '导线垂弧',value: null, type:'wireSag', unit: null}
                ,{online:1,name: '导线振动',value: null, type:'wireVibration', unit: null}
                ,{online:1,name: '舞动振幅',value: null, type:'gallopingAmplitude', unit: null}
                ,{online:1,name: '导线温度',value: null, type:'wireTemperature', unit: null}
                ,{online:1,name: '设备报警',value: null, type:'deviceAlarm', unit: null}
                ,{online:1,name: '山火报警',value: null, type:'fireAlarm', unit: null}
            ]
        }
    },
    // mounted() {
    //     this.getRealtimeData();
    // },
    // watch: {
    //     childData: {
    //         handler(newval) {         
    //             this.getRealtimeData();
    //         }, deep: true
    //     }
    // },
    // methods: {
    //     getRealtimeData () {
    //         if(Object.keys(this.childData.yuntaiInfo).length<1) return;
    //         get_realtimeData({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
    //             if(response.code == 200) {
    //                 this.$message({
    //                     type: "success",
    //                     message: "操作成功"
    //                 });
    //             } else {
    //                 this.$message(response.msg);
    //             }
    //         });
    //     }
    // },
    created () {
    },   
    methods: {
        ccc(data) {
        },
    }
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card__body{height:100%;position: relative;}}
  .realtimeData{
        ::v-deep{.el-card__header{padding-left:0;padding-bottom:13px;}}
        .box{border-radius: 0;border:0;padding:0;
            .yuntaiIcon{margin-right:3px;}
        }
        .list {padding:0 $base-padding;display: flex;flex-wrap:wrap;
            .item{display: flex; flex-direction: column;width:10%;padding:$base-padding 0;
                &.break{flex-basis: 40%;}
                img{flex-shrink: 0;width:48px;height:48px;display: block;}
                span.name{display: block;padding:8px 0 5px;}
            }
        }
  }
}
</style>