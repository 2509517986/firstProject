<template>
<!-- 杆塔 -->
    <div class="history-device-box">
        <div class="top">
            <div :class="['title', {act:tabVal == 0}]" @click="tabVal = 0">杆塔振动</div>
            <div :class="['title', {act:tabVal == 1}]" @click="tabVal = 1">杆塔倾斜</div>
            <div :class="['title', {act:tabVal == 2}]" @click="tabVal = 2">导地线</div>
            <div class="tools">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="exportData">导出全部数据</el-button>
            </div>
        </div>
        <div class="con">
            <div class="tablelist">
                <el-table ref="filterTable" :data="tableData" style="width: 100%">
                    <el-table-column v-for="(e,i) in tablecolumn[tabVal]" :key="i" :prop="e.key" :label="e.name"></el-table-column>
                    <el-table-column prop="state" label="状态" width="100" :filters="[{ text: '有效', value: '1' }, { text: '无效', value: '0' }]" :filter-method="filterHandler">
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
import {export_vibration, get_vibration, export_tilt, get_tilt, export_pull, get_pull} from '@/api/senxu/dataCollection'
export default {
    name: 'historytower',
    props: ['childData'],
    data () {
        return {
            layout: true,
            tabVal:0,
            chartData:{
                data1: {name:'温度',unit: '℃',list:[9, 11, 13, 12, 11, 9, 9, 7, 6, 4, 3, 3]}
                ,data2: {name:'湿度',unit: 'rh', list:[76, 76, 70, 62, 58, 53, 50, 49, 46, 44, 44, 42]}
                ,data3: {name:'风速',unit: 'm/s', list:[26, 26, 20, 22, 28, 23, 20, 39, 46, 54, 74, 82]}
                ,data4: {name:'风向',unit: '°', list:[16, 26, 10, 12, 18, 13, 40, 19, 16, 14, 14, 12]}
                ,data5: {name:'降雨量',unit: 'mm', list:[29, 21, 13, 12, 11, 29, 39, 77, 16, 42, 33, 34]}
                ,data6: {name:'气压',unit: 'hPa', list:[6, 56, 50, 52, 38, 63, 70, 89, 26, 14, 34, 42]}
                ,time:{name:'时间',unit:'',list:['2000-1-1 00:00:00','2000-2-2 00:00:00','2000-12-2 00:00:00','2000-2-12 00:00:00','2000-2-22 00:00:00','2000-2-23 00:00:00','2000-2-24 00:00:00','2000-2-25 00:00:00','2000-2-26 00:00:00','2000-2-27 00:00:00','2000-2-28 00:00:00','2000-2-29 00:00:00']}
            },
            tableData: [
                {date:"11",name: "22",address:"33",address1:"44",address2:"55",address3:"66",address4:"77",address5:"88",address6:"99",address7:"1010",address8:"1111",address9:"1212",address10:"1313",status:"1"}
                ,{date:"111",name: "22",address:"33",address1:"44",address2:"55",address3:"66",address4:"77",address5:"88",address6:"99",address7:"1010",address8:"1111",address9:"1212",address10:"1313",status:"1"}
                ,{date:"1111",name: "22",address:"33",address1:"44",address2:"55",address3:"66",address4:"77",address5:"88",address6:"99",address7:"1010",address8:"1111",address9:"1212",address10:"1313",status:"0"}
            ],
            tablecolumn: [
                [
                    // {key: 'channel_name', name: '通道名称'}
                    // ,{key: 'channel_id', name:'通道ID'}
                     {key: 'time', name: '时间'}
                    ,{key: 'vibrationFre', name: '振动频率(Hz)'}
                    ,{key: 'amplitude', name: '振幅(mm)'}
                ],
                [
                    // {key: 'channel_name', name: '通道名称'}
                    // ,{key: 'channel_id', name: '通道ID'}
                     {key: 'time', name: '时间'}
                    // ,{key: 'a4', name: '倾斜X坐标'}
                    // ,{key: 'a5', name: '倾斜Y坐标'}
                    ,{key: 'inLineOff', name: '1号顺线方向杆塔倾斜角'}
                    ,{key: 'horizontalLineOff', name: '1号横线方向杆塔倾斜角'}
                    ,{key: 'inLineOfs', name: '2号顺线方向杆塔倾斜角'}
                    ,{key: 'horizontalLineOfs', name: '2号横线方向杆塔倾斜角'}
                ],
                [
                    // {key: 'channel_name', name: '通道名称'}
                    // ,{key: 'channel_id', name: '通道ID'}
                    {key: 'time', name: '时间'}
                    // ,{key: 'a4', name: '传感器重量'}
                    ,{key: 'maxPull', name: '最大拉力(kg)'}
                    ,{key: 'windDefAngleOfMaxPull', name: '最大拉力时的风偏角'}
                    ,{key: 'incAngleOfMaxPull', name: '最大拉力时的倾斜角'}
                    ,{key: 'minPull', name: '最小拉力'}
                    ,{key: 'windDefAngleOfMinPull', name: '最小拉力时的风偏角'}
                    ,{key: 'incAngleOfMinPull', name: '最小拉力时的倾斜角'}
                    ,{key: 'maxWindDefAngle', name: '最大风偏角'}
                    ,{key: 'incAngleOfMaxWind', name: '最大风偏角时的倾斜度'}
                    ,{key: 'pullOfMaxWind', name: '最大风偏角时的拉力'}
                    ,{key: 'minWindDefAngle', name: '最小风偏角'}
                    ,{key: 'incAngleOfMinWind', name: '最小风偏角时的倾斜度'}
                    ,{key: 'pullOfMinWind', name: '最小风偏角时的拉力'}
                ]
            ]
        }
    },
    mounted() {
        this.getVibration();
    },
    methods: {
        getVibration () {
            if(Object.keys(this.childData.yuntaiInfo).length<1) return;
            get_vibration({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
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
        getTilt () {
            if(Object.keys(this.childData.yuntaiInfo).length<1) return;
            get_tilt({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
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
        getPull () {
            if(Object.keys(this.childData.yuntaiInfo).length<1) return;
            get_pull({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
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
        filterHandler(value, row, column) {
            const property = column['property'];
            return row[property] == value;
        }
    },
    watch: {
        childData: {
            handler(newval) {          
                if(this.tabVal == 0){
                    this.getVibration();
                }       
                if(this.tabVal == 1){
                    this.getTilt();
                } 
                if(this.tabVal == 2){
                    this.getPull();
                } 
            }, deep: true
        }
    },
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