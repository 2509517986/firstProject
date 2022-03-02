<template>
<!-- 环境 -->
    <div class="history-device-box">
        <div class="top">
            <div :class="['title', {act:tabVal == 0}]" @click="tabVal = 0;">气象状态</div>
            <!-- <div :class="['title', {act:tabVal == 1}]" @click="tabVal = 1;">污秽数据</div> -->
            <div class="tools">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="exportData">导出全部数据</el-button>
            </div>
            <div class="layout" v-if="tabVal == 0">
                <svg-icon @click="layout = !layout" :icon-class="layout?'list':'layout'" style="height: 18px;width: 18px;" />
            </div>
        </div>
        <div class="con">
            <div class="charts" v-if="layout">
                <div class="item"><history-chart :chartData="mergeData2({data:[chartData.temperature,chartData.humidity],time:chartData.collectDate})"></history-chart></div>
                <div class="item"><history-chart :chartData="mergeData2({data:[chartData.instantWindSpeed,chartData.instantWindDirection],time:chartData.collectDate})"></history-chart></div>
                <div class="item"><history-chart :chartData="mergeData2({data:[chartData.rainfall],time:chartData.collectDate})"></history-chart></div>
                <div class="item"><history-chart :chartData="mergeData2({data:[chartData.pressure],time:chartData.collectDate})"></history-chart></div>
            </div> 
            <div class="tablelist" v-else>
                <el-table ref="filterTable" :data="tableData" style="width: 100%">
                    <el-table-column v-for="(e,i) in tablecolumn[tabVal]" :key="i" :prop="e.key" :label="e.name" :width="e.width?e.width:'auto'"></el-table-column>
                    <el-table-column prop="status" label="状态" width="100" :filters="[{ text: '有效', value: '1' }, { text: '无效', value: '0' }]" :filter-method="filterHandler">
                        <template slot-scope="scope">
                            {{scope.row.state=='1'?'有效':'无效'}}
                        </template>
                    </el-table-column>
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
import {export_mete, get_mete} from '@/api/senxu/dataCollection'
export default {
    name: 'historyenvironment',
    props: ['childData'],
    data () {
        return {
            layout: true,
            tabVal:0,
            chartData:{
                temperature: {name:'温度',unit: '℃', list:[]}
                ,humidity: {name:'湿度',unit: 'rh',list:[]}
                ,instantWindSpeed: {name:'风速',unit: 'm/s', list:[]}
                ,instantWindDirection: {name:'风向',unit: '°', list:[]}
                ,rainfall: {name:'降雨量',unit: 'mm', list:[]}
                ,pressure: {name:'气压',unit: 'hPa', list:[]}
                ,collectDate:{name:'时间',unit:'', list:[]}
            },
            tableData: [],
            tablecolumn: [
                [
                    // {key: 'channelNo', name:'通道名称'}
                    {key: 'collectDate', name: '采样时间', width: '100'}
                    ,{key: 'rainfall', name: '降雨量(mm)'}
                    // ,{key: 'rainfallAccumulate', name: '雨量累积(mm/d)'}
                    ,{key: 'instantWindSpeed', name: '风速(m/s)'}
                    // ,{key: 'maxInstantWindSpeed', name: '最大风速(m/s)'}
                    // ,{key: 'minInstantWindSpeed', name: '最小风速(m/s)'}
                    ,{key: 'avgWindSpeedOneMinute', name: '1分钟平均风速(m/s)', width: '100'}
                    ,{key: 'avgWindSpeedTenMinute', name: '10分钟平均风速(m/s)', width: '100'}
                    ,{key: 'maxWindSpeedTenMinute', name: '10分钟最大风速(m/s)', width: '100'}
                    // ,{key: 'a11', name: '最大垂直风速(m/s)'}
                    // ,{key: 'a12', name: '最小垂直风速(m/s)'}
                    // ,{key: 'a13', name: 'U轴风速(m/s)'}
                    // ,{key: 'a14', name: 'V轴风速(m/s)'}
                    // ,{key: 'a15', name: 'W轴风速(m/s)'}
                    ,{key: 'pressure', name: '气压(hPa)'}
                    // ,{key: 'maxPressure', name: '最大气压(hPa)'}
                    // ,{key: 'minPressure', name: '最小气压(hPa)'}
                    ,{key: 'instantWindDirection', name: '风向(°)'}
                    ,{key: 'avgWindDirectionOneMinute', name: '1分钟平均风向(°)'}
                    ,{key: 'avgWindDirectionTenMinute', name: '10分钟平均风向(°)'}
                    ,{key: 'a22', name: '露点温度(℃)'}
                    // ,{key: 'a23', name: '最大露点温度(℃)'}
                    // ,{key: 'a24', name: '最小露点温度(℃)'}
                    ,{key: 'humidity', name: '湿度(%RH)'}
                    // ,{key: 'maxHumidity', name: '最大湿度(%RH)'}
                    // ,{key: 'minHumidity', name: '最小湿度(%RH)'}
                    // ,{key: 'rainfallHour', name: '降雨量(mm)'}
                    // ,{key: 'a29', name: '电子罗盘角度'}
                    // ,{key: 'a30', name: '降落物状态'}
                    // ,{key: 'a31', name: '定位状态'}
                    // ,{key: 'speeds', name: '航速'}
                    // ,{key: 'a33', name: '航向'}
                    // ,{key: 'longitude', name: '经度'}
                    // ,{key: 'latitude', name: '纬度'}
                    // ,{key: 'a36', name: '粉尘浓度'}
                    // ,{key: 'a37', name: '能见度'}
                    ,{key: 'sunshine', name: '日照'}
                    // ,{key: 'a39', name: '光照度'}
                    // ,{key: 'a40', name: '日辐射量'}
                    // ,{key: 'a41', name: '太阳辐射功率'}
                    // ,{key: 'a42', name: '真实风向'}
                    // ,{key: 'a43', name: '海拨高度'}
                    // ,{key: 'a44', name: '通道ID'}
                ],
                // [
                //     {key: 'a1', name: '通道名称'}
                //     ,{key: 'a2', name: '通道ID'}
                //     ,{key: 'a3', name: '时间'}
                //     ,{key: 'a4', name: '日最高温度'}
                //     ,{key: 'a5', name: '日最低温度'}
                //     ,{key: 'a6', name: '日最高湿度'}
                //     ,{key: 'a7', name: '日最低湿度'}
                //     ,{key: 'a8', name: '盐密(mg/cm2)'}
                //     ,{key: 'a9', name: '灰密(mg/cm2)'}
                // ]
            ]
        }
    },
    mounted() {
        this.getMete()
    },
    watch: {
        childData: {
            handler(newval) {
                this.getMete()
            }, deep: true
        }
    },
    methods: {
        getMete () {
            if(Object.keys(this.childData.yuntaiInfo).length<1) return;
            get_mete({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
                if(response.code == 200) {
                    this.tableData = response.rows;
                    if(this.tableData.length>0) {
                        for(let key in this.chartData) {
                            this.chartData[key].list = [];
                            for(let j=0; j < this.tableData.length;j++) {
                                this.chartData[key].list.push(this.tableData[j][key]);
                            }
                        }
                    }
                }
            });
        },
        exportData() {

        },
        mergeData2(obj) {
            if(typeof(obj) == 'object') {
                return obj;
            }
        },
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] == value;
        }
    }
}
</script>
<style lang="scss" scope>
.history-device-box{height: 100%;display: flex;flex-direction: column;
     .top{display:flex;align-items:center; flex-shrink: 0;border-bottom:1px solid $base-border-color;padding-bottom:5px;
        .title{font-size:14px;position: relative;font-size:12px;padding:5px 20px;border-radius: 2px;border:1px solid $base-border-color;cursor: pointer;
            &.act,&:hover{background:$base-color-default;color:#fff;border-color:$base-color-default;}
            // &::before{content:"";position: absolute;top:1px;left:0;width:3px;height:19px;background:$base-color-default;}
        }
        .title+.title{margin-left:10px;}
        .tools{margin-left:auto;}
        .layout{margin-left:$base-padding;color:$base-menu-color;cursor: pointer;}
    }    
    .con{flex:1;padding-top:10px;
        .charts{display: flex;height: 100%; flex-wrap: wrap;align-content: flex-start;
         .item{width:50%;height:50%;}
        }
    }
}
</style>
