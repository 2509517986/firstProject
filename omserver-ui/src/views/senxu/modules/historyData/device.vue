<template>
<!-- 设备 -->
    <div class="history-device-box">
        <div class="top">
            <div class="title act">电源</div>
            <div class="tools">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="exportData">导出全部数据</el-button>
            </div>
            <div class="layout">
                <svg-icon @click="layout = !layout" :icon-class="layout?'list':'layout'" style="height: 18px;width: 18px;" />
            </div>
        </div>
        <div class="con">
            <div class="charts" v-if="layout">
                <div class="item"><history-chart :chartData="mergeData2({data:[chartData.data1,chartData.data2],time:chartData.time})"></history-chart></div>
                <div class="item"><history-chart :chartData="mergeData2({data:[chartData.data3,chartData.data4],time:chartData.time})"></history-chart></div>
                <div class="item"><history-chart :chartData="mergeData2({data:[chartData.data5,chartData.data6],time:chartData.time})"></history-chart></div>
                <div class="item"><history-chart :chartData="mergeData2({data:[chartData.data7,chartData.data8],time:chartData.time})"></history-chart></div>
            </div> 
            <div class="tablelist" v-else>
                <el-table ref="filterTable" :data="tableData" style="width: 100%">
                    <el-table-column prop="date" label="通道名称"></el-table-column>
                    <el-table-column prop="name" label="通道ID"></el-table-column>
                    <el-table-column prop="address" label="设备名称"></el-table-column>
                    <el-table-column prop="address1" label="时间"></el-table-column>
                    <el-table-column prop="address2" label="光伏阵列电流"></el-table-column>
                    <el-table-column prop="address3" label="蓄电池电压"></el-table-column>
                    <el-table-column prop="address4" label="蓄电池电流"></el-table-column>
                    <el-table-column prop="address5" label="负载电压"></el-table-column>
                    <el-table-column prop="address6" label="负载电流"></el-table-column>
                    <el-table-column prop="address7" label="电池温度"></el-table-column> 
                    <el-table-column prop="address8" label="剩余电量"></el-table-column> 
                    <el-table-column prop="address9" label="充电功率(mW)"></el-table-column> 
                    <el-table-column prop="address10" label="电池编号"></el-table-column> 
                    <el-table-column prop="status" label="状态" width="100" :filters="[{ text: '有效', value: '1' }, { text: '无效', value: '0' }]" :filter-method="filterHandler">
                        <template slot-scope="scope">
                            {{scope.row.status=='1'?'有效':'无效'}}
                        </template>
                    </el-table-column>
            </el-table>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: 'historydevice',
    props: ['treedata', 'timedata'],
    data () {
        return {
            layout: true,
            chartData:{
                data1: {name:'剩余电量',unit: '%',list:[9, 11, 13, 12, 11, 9, 9, 7, 6, 4, 3, 3]}
                ,data2: {name:'工作温度',unit: '℃', list:[76, 76, 70, 62, 58, 53, 50, 49, 46, 44, 44, 42]}
                ,data3: {name:'光伏阵列电流',unit: 'A', list:[26, 26, 20, 22, 28, 23, 20, 39, 46, 54, 74, 82]}
                ,data4: {name:'光伏阵列电压',unit: 'V', list:[16, 26, 10, 12, 18, 13, 40, 19, 16, 14, 14, 12]}
                ,data5: {name:'蓄电池电流',unit: 'A', list:[29, 21, 13, 12, 11, 29, 39, 77, 16, 42, 33, 34]}
                ,data6: {name:'蓄电池电压',unit: 'V', list:[6, 56, 50, 52, 38, 63, 70, 89, 26, 14, 34, 42]}
                ,data7: {name:'负载电流',unit: 'A', list:[36, 46, 20, 22, 28, 23, 20, 19, 26, 34, 34, 32]}
                ,data8: {name:'负载电压',unit: 'V', list:[66, 66, 60, 62, 48, 23, 10, 39, 56, 64, 74, 82]}
                ,time:{name:'时间',unit:'',list:['2000-1-1 00:00:00','2000-2-2 00:00:00','2000-12-2 00:00:00','2000-2-12 00:00:00','2000-2-22 00:00:00','2000-2-23 00:00:00','2000-2-24 00:00:00','2000-2-25 00:00:00','2000-2-26 00:00:00','2000-2-27 00:00:00','2000-2-28 00:00:00','2000-2-29 00:00:00']}
            },
            tableData: [
                {date:"11",name: "22",address:"33",address1:"44",address2:"55",address3:"66",address4:"77",address5:"88",address6:"99",address7:"1010",address8:"1111",address9:"1212",address10:"1313",status:"1"}
                ,{date:"111",name: "22",address:"33",address1:"44",address2:"55",address3:"66",address4:"77",address5:"88",address6:"99",address7:"1010",address8:"1111",address9:"1212",address10:"1313",status:"1"}
                ,{date:"1111",name: "22",address:"33",address1:"44",address2:"55",address3:"66",address4:"77",address5:"88",address6:"99",address7:"1010",address8:"1111",address9:"1212",address10:"1313",status:"0"}
            ]
        }
    },
    methods: {
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