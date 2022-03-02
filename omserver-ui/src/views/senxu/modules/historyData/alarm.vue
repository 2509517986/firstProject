<template>
<!-- 导线 -->
    <div class="history-device-box">
        <div class="top">
            <div :class="['title', {act:tabVal == 0}]" @click="tabVal = 0">综合防盗报警</div>
            <div :class="['title', {act:tabVal == 1}]" @click="tabVal = 1">山火报警</div>
            <div :class="['title', {act:tabVal == 2}]" @click="tabVal = 2">大风舞动报警</div>
            <div :class="['title', {act:tabVal == 3}]" @click="tabVal = 3">设备故障报警</div>
            <div class="tools">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="exportData">导出全部数据</el-button>
            </div>
        </div>
        <div class="con">
            <div class="tablelist">                
                <el-table ref="filterTable" :data="tableData" style="width: 100%">
                    <el-table-column v-for="(e,i) in tablecolumn[tabVal]" :key="i" :prop="e.key" :label="e.name"></el-table-column>
                    <!-- <el-table-column prop="state" label="状态" width="100" :filters="[{ text: '有效', value: '1' }, { text: '无效', value: '0' }]" :filter-method="filterHandler">
                        <template slot-scope="scope">
                            {{scope.row.state=='1'?'有效':'无效'}}
                        </template>
                    </el-table-column> -->
                </el-table>
            </div>
        </div>
    </div>
</template>
<script>
import {export_antitheft, get_antitheft,export_fire_alarm,get_fire_alarm,export_danced,get_danced,export_failure,get_failure}  from '@/api/senxu/dataCollection'
export default {
    name: 'historyalarm',
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
                    // {key: 'channel_id', name: '通道ID'}
                    {key: 'time', name:'采集时间'}
                    ,{key: 'extForceDesPolice', name: '外力破坏报警'}
                    ,{key: 'speedEff', name: '速度'}
                    ,{key: 'shockPolice', name: '震动告警'}
                    ,{key: 'infraredPolice', name: '红外告警'}
                    ,{key: 'infraredDetectionArea', name: '红外探测区域'}
                ],
                [
                    // {key: 'channel_id', name: '通道ID'}
                    {key: 'time', name: '采集时间'}
                    ,{key: 'policeStatus', name: '报警状态'}
                    ,{key: 'currentPoliceFre', name: '日警次数'}
                ],
                [
                    // {key: 'channel_id', name: '通道ID'}
                    {key: 'time', name: '采集时间'}
                    ,{key: 'avgPull', name: '拉力平均值'}
                    ,{key: 'pullChangeRange', name: '拉力变化幅值'}
                    ,{key: 'pullChangeFre', name: '拉力变化频率'}
                    ,{key: 'avgWindDefAngle', name: '风偏角平均值'}
                    ,{key: 'windDefAngleRange', name: '风偏角幅值'}
                    ,{key: 'windDefAngleChangeRange', name: '风偏角变化频率'}
                ],
                [
                    // {key: 'channel_id', name: '通道ID'}
                     {key: 'time', name: '采集时间'}
                    ,{key: 'functioncode', name: '功能编码'}
                    ,{key: 'faultcode', name: '故障编码'}
                ]
            ]
        }
    },
    mounted() {
            this.getAntitheft();
    },
    watch: {
        childData: {
            handler(newval) {         
                if(this.tabVal == 0){
                    this.getAntitheft();
                }       
                if(this.tabVal == 1){
                    this.getFireAlarm();
                } 
                if(this.tabVal == 2){
                    this.getDanced();
                } 
                if(this.tabVal == 3){
                    this.getFailure();
                } 
            }, deep: true
        }
    },
    methods: {
        getAntitheft () {
            if(Object.keys(this.childData.yuntaiInfo).length<1) return;
            get_antitheft({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
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
        getFireAlarm () {
            if(Object.keys(this.childData.yuntaiInfo).length<1) return;
            get_fire_alarm({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
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
        getDanced () {
            if(Object.keys(this.childData.yuntaiInfo).length<1) return;
            get_danced({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
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
        getFailure () {
            if(Object.keys(this.childData.yuntaiInfo).length<1) return;
            get_failure({yuntaiId: this.childData.yuntaiInfo.id}).then((response) => {
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