<!--
 * @Author: YJY
 * @Date: 2022-02-01 15:53:02
 * @System: proManagement
 * @Company: LESSO
 * @LastEditTime: 2022-02-10 15:31:15
 * @Description: file content
-->
<template>
    <div  class="app-container">
        <el-row :gutter="10" class="row">
            <el-col :span="3">巡检区域：{{basicInfo.inspectionArea}}米</el-col>
            <el-col :span="5">巡检计划：{{basicInfo.inspectionPlan}}</el-col>
            <el-col :span="8" class="inspection_time"  :title="`${basicInfo.inspectionBeginTime}至${basicInfo.inspectionEndTime}`">巡检时间：{{basicInfo.inspectionBeginTime}}至{{basicInfo.inspectionEndTime}}</el-col>
            <el-col :span="3">巡检进度：{{basicInfo.inspectionProcess}}</el-col>
            <el-col :span="3">巡检班组：{{basicInfo.inspectionGroup}}</el-col>
            <el-col :span="2" style="text-align:right;padding-right:15px;"> 
                 <el-button type="primary" icon="el-icon-search" size="mini" @click="exportReport">导出</el-button>
            </el-col>
        </el-row>
        <el-divider class="divider"></el-divider>
        <el-row :gutter="10" class="chart_row">
            <el-col :span="14" class="chart_col">
                <div style="width:100%;height:100%;" id="lineChart"></div>
            </el-col>
            <el-col :span="10" class="chart_col">
                <div style="width:100%;height:100%;" id="bread"></div>
            </el-col>
        </el-row>
        <div class="h5">缺陷位置信息表</div>
        <span class="h5_span">无人机巡检过程中，拍摄的每张照片都有其对应的GPS坐标， 通过无人机智能巡检系统识别出缺陷位置信息列表，帮助运维人员更准确、快捷地定位缺陷位置。</span>
        <div>
            <el-table :data="defectPosition.list" style="width: 100%;padding:0 3px;" height="300px">
                <el-table-column v-for="(item,index) in defectPositionHeader" :prop="item.prop" align="center" :label="item.label"  :key="index" />
            </el-table>
        </div>
    </div>
</template>

<script>

import { get_report_list,export_report } from "@/api/senxu/inspection/index";

import * as echarts from 'echarts/core';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent
} from 'echarts/components';
import { PieChart } from 'echarts/charts';
import { LabelLayout } from 'echarts/features'; 


import { GridComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import dataVue from '../home/data.vue';


echarts.use([
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  PieChart,
  CanvasRenderer,
  LabelLayout,

  GridComponent, 
  BarChart,
]);

export default{
    data(){
        return{
            basicInfo:{
                inspectionPlan: '',//巡检计划
                inspectionBeginTime: '',//巡检开始时间
                inspectionEndTime: '',//巡检结束时间
                inspectionProcess: '',//巡检进度
                inspectionGroup: '',// 巡检班组
                inspectionArea: 0,//巡检范围
            },
            diagnose:{
                picTotal: '照片总数',//拍摄照片总数
                defectPicTotal: '缺陷照片总数',//缺陷照片总数     
                visiblePicTotal: '可见光照片数',//可见光照片数 
                infraredPicTotal: '红外照片数',//红外照片数  
                visibleDefectPicTotal: '可见光缺陷数',//可见光缺陷数
                infraredDefectPicTotal: '红外缺陷',// 红外缺陷

            },
            defectStatic:{
                seriousNum: '严重缺陷数',// 严重缺陷数     
                dangerNum: '危急缺陷数',//危急缺陷数 
                normalNum: '一般缺陷数',//一般缺陷数 
            },
            defectPositionHeader:[
                {label:'照片序号',prop:'picId'},
                {label:'区域名称',prop:'areaName'},
                {label:'拍摄经度',prop:'longitude'},
                {label:'拍摄纬度',prop:'latitude'},
                {label:'缺陷个数',prop:'defectNum'},
                {label:'一般缺陷',prop:'commonDefectNum'},
                {label:'严重缺陷',prop:'seriousDefectNum'},
                {label:'紧急缺陷',prop:'criticalDefectNum'},
            ],
            defectPosition:{
                total:{
                    commonDefectNum: 0,
                    criticalDefectNum: 0,
                    defectNum: 0,
                    latitude: 0,
                    longitude: 0,
                    seriousDefectNum: 0

                },
                list:[],
            },
            taskId:'',
        }
    },
    created(){
        
        if(this.$route?.query){
            let query = this.$route.query;
            if(query.taskId){
                //丢到当前组件保存
                this.taskId = query.taskId;
                this.getInitData({taskId:query.taskId});
            }else{
                this.$message.info('缺失该巡检记录taskId')
                this.$router.go(-1)
            }
        }
        console.log(process.env)

    },
    mounted(){
       
    },
    methods:{
        /**
         * @description: 获取巡检报告数据接口
         * @param {*}
         * @return {*}
         */        
        getInitData(query){
            get_report_list(query).then(res =>{
                const { code ,data,msg} = res;
                if(code == 200){
                    this.basicInfo = data.basicInfo;
                    // this.diagnose = data.diagnose;
                    // this.defectStatic = data.defectStatic;
                    this.defectPosition.total = data.defectPosition.total;
                    this.defectPosition.list = data.defectPosition.list;

                    let lineArr =[],lineTitleArr = [];
                    for(let i in data.diagnose){
                        lineTitleArr.push(this.diagnose[i])
                        lineArr.push(data.diagnose[i]);
                    }
                    this.initlineChart(lineTitleArr,lineArr);

                    let chartArr = [];
                    for(let i in this.defectStatic){
                        chartArr.push({
                            name: this.defectStatic[i],
                            value:data.defectStatic[i]
                        })
                    }
                    this.initChartBread(chartArr);

                }else{
                    this.$message.warning(msg)
                }
            })
        },
        /**
         * @description: 导出
         * @param {*}
         * @return {*}
         */
        
        exportReport(){
            let queryParams = {
                taskId:this.taskId
            }
            this.$confirm('是否确认导出当前巡检报告?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            }).then(function() {
                return export_report(queryParams);
            }).then(function (res) { //导出流
                let blob = new Blob([res]);//后端传的流文件
                let downloadFilename = "Report.docx";//设置导出的文件名  
                if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                //兼容ie浏览器
                    window.navigator.msSaveOrOpenBlob(blob, downloadFilename)
                }else {
                //谷歌,火狐等浏览器
                    let url = window.URL.createObjectURL(blob);
                    let downloadElement = document.createElement("a");
                    downloadElement.style.display = "none";
                    downloadElement.href = url;
                    downloadElement.download = downloadFilename;
                    document.body.appendChild(downloadElement);
                    downloadElement.click();
                    document.body.removeChild(downloadElement);
                    window.URL.revokeObjectURL(url);
                }
                this.$Message.success('导出成功！')
                }).catch(function (err) {
                    console.log(err)
                })

        },
        /**
         * @description: 折线图
         * @param {*}
         * @return {*}
         */        
        initlineChart(lineTitleArr,lineArr){
            let chartDom = document.getElementById('lineChart');
            let myChart = echarts.init(chartDom);
            let option;

            option = {
                title: {
                    text: '巡检诊断总览统计',
                    left: 'left',
                    textStyle: {
                        fontSize: 14
                    },
                },
                 tooltip: {
                    trigger: 'item',
                    formatter: '{b} : {c}'
                },
                xAxis: {
                    type: 'category',
                    // data: [ '照片总数', '可见光照片数', '可见光缺陷数', '缺陷总数', '红外照片数', '红外缺陷数']
                    data:lineTitleArr
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                    barWidth :'30',
                    // data: [ 200, 150, 80, 70, 110, 130],
                    data: lineArr,
                    type: 'bar',
                    colorBy: "data",//设置颜色随数据变化
                    }
                ]
            };

            option && myChart.setOption(option);
        },
        /**
         * @description: 画饼
         * @param {*}
         * @return {*}
         */        
        initChartBread(chartArr){
            let chartDom = document.getElementById('bread');
            let myChart = echarts.init(chartDom);
            let option;
            option = {
                title: {
                    text: '缺陷分类统计',
                    left: 'center',
                    textStyle: {
                        fontSize: 14
                    },
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{b} : {c} ({d}%)'
                },
                legend: {
                    top: 30,
                    orient: "vertical",//竖着
                    left: 'left',
                    data: ['一般缺陷数', '严重缺陷数', '危急缺陷数']
                },
                series: [
                    {
                    type: 'pie',
                    radius: '65%',
                    width:'70%',
                    height:'70%',
                    left:'20%',
                    top:'20%',
                    center: ['50%', '50%'],
                    selectedMode: 'single',
                    data: chartArr,
                    emphasis: {
                        itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        
    }
}

</script>

<style lang="scss" scoped>
    .app-container{
        width: 100%;
        height: 100%;
        background: #fff;
        .row{
            height: 40px;
            line-height: 40px;
            padding-left: 10px; 
            .inspection_time{
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
        }
        .chart_row{
            padding: 0 10px;
        }
        .el-divider--horizontal{
            margin:5px 0;
        }
        .chart_col{
            height: 300px;
            padding: 5px;
            #lineChart{
                border:1px solid #DCDFE6;
                // background-color: #DCDFE6;
            }
            #bread{
                border:1px solid #DCDFE6;
            }
        }
        .h5{
            padding: 5px 10px;
            font-weight: 600;
        }
        .h5_span{
            display: block;
            padding: 0 10px 10px 20px;
        }
    }
</style>