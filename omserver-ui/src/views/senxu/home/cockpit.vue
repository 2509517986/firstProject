<!--
 * @Author: YJY
 * @Date: 2022-02-01 09:01:55
 * @System: proManagement
 * @Company: 首页-驾驶舱组件开发
 * @LastEditTime: 2022-02-21 21:15:05
 * @Description: 驾驶舱 概况
-->
<template>    
  <div class="app-cockpit">
  <!-- <div class="app-cockpit" :style="`transform: scale(${scaleWidth},${scaleHeight});`"> -->
     
    <el-card class="cockpit-card" :body-style="{padding:'0 20px 20px 20px'}">
        <span class="title">巡检统计</span>
        <el-row :gutter="10">
            <el-col :span="9" class="col">
                <el-col :span="11" style="text-align:center;">
                    <span class="col_chart">计划完成度</span>
                    <el-progress type="circle" :percentage="chartCircalVal" style="padding:15px 0"></el-progress>
                     <el-radio-group v-model="radio" class="col_radio" @change="radioChange">
                        <el-radio :label="0">未完成</el-radio>
                        <el-radio :label="1">已完成</el-radio>
                    </el-radio-group>
                </el-col>
                <el-col :span="13">
                    <span class="col_chart">巡检任务完成进度</span>
                    <div v-for="(item,index) in chartList" :key="index">
                        <span class="col_chart_title">{{item.name}}</span>
                        <el-progress :percentage="setChartProgress(item.prop,item.props)"  :color="!item.status ? '#409EFF' : '#F56C6C'"  :format="format(item.prop,item.props)"></el-progress>
                    </div>
                </el-col>
            </el-col>
            <el-col :span="7" class="col">
                <span class="col_title">各机场平均运行时长</span>
                <span class="col_Num">{{runTimInfo.avgRuntime}}H</span>
                <div class="col_list_header">
                    <span class="col_list_title"><strong>设备名称</strong></span>
                    <span class="col_list_title"><strong>运行总时长</strong></span>
                </div>
                <div class="col_list">
                    <div class="col_list_num" v-for="(item,index ) in runTimInfo.datas" :key="index">
                        <el-divider></el-divider>
                        <span class="col_list_name">{{item.name}}</span>
                        <span class="col_list_name">{{item.runtime}}</span>
                    </div>
                </div>

               
            </el-col>
            <el-col :span="8">
                <el-row :gutter="10">
                    <el-col :span="12" class="child_col">
                        <span class="col_title">飞行总架次</span>
                        <span class="col_Num">{{basicInfo.totolFightNum}}</span>
                    </el-col>
                    <el-col :span="12" class="child_col">
                        <span class="col_title">飞行总里程</span>
                        <span class="col_Num">{{basicInfo.totalMileage}}KM</span>
                    </el-col>
                </el-row>
                 <el-row :gutter="10">
                    <el-col :span="12" class="child_col">
                        <span  class="col_title">巡检照片总数</span>
                        <span class="col_Num">{{basicInfo.totalPic}}</span>
                    </el-col>
                    <el-col :span="12" class="child_col">
                        <span class="col_title">异常总数</span>
                        <span class="col_Num">{{basicInfo.totalError}}</span>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
        
    </el-card>
    <el-card class="cockpit_btm_card">
        <el-row :gutter="10" class="btm_card_row">
            <el-col :span="14" class="btm_col">

                <span class="title">今日巡检计划</span>
                <el-row>
                    <el-col :span="6" class="btn_today">
                        <span class="col_title">今日创建计划总数</span>
                        <span class="col_Num">{{todayInspectionPlan.todayCreateNum}}</span>
                    </el-col>
                    <el-col :span="6" class="btn_today">
                        <span class="col_title">今日完成计划数</span>
                        <span class="col_Num">{{todayInspectionPlan.todayFinishNum}}</span>
                    </el-col>
                    <el-col :span="6" class="btn_today">
                        <span class="col_title">今日已取消计划数</span>
                        <span class="col_Num">{{todayInspectionPlan.todayCancelNum}}</span>
                    </el-col>
                    <el-col :span="6" class="btn_today">
                        <span class="col_title">今日已过期计划数</span>
                        <span class="col_Num">{{todayInspectionPlan.todayDelayNum}}</span>
                    </el-col>

                </el-row>
                <el-row style="height:calc(100% - 110px)">
                    <span class="title">今日完成计划</span>
                    <el-table :data="todayFinishPlans" style="width: 100%;padding:0 3px;" height="calc(100% - 30px)">
                        <el-table-column v-for="(item,index) in todayFinishPlansHeader" :prop="item.prop" align="center" :label="item.label"  :key="index" />
                    </el-table>
                </el-row>

            </el-col>
            <el-col :span="10" class="btm_col">
                <span class="title">异常统计</span>
                <el-table  :data="errorInfos"  style="width: 100%;padding:0 3px" height="calc(100% - 30px)">
                    <el-table-column v-for="(item,index) in errorInfosHeader" :prop="item.prop" align="center" :label="item.label"   :key="index" /> 
                </el-table>

            </el-col>

        </el-row>
    </el-card>
  </div>
</template>
<script>    
import { get_cockpitData } from "@/api/senxu/cockpit/index";
export default {
    name: 'cockpit',
    components: {  },
    data () {
        return {

            scaleWidth:'',
            scaleHeight:'',
            chartCircalVal:0,
            radio:1,
            chartList:[
                {name:'航点飞行(可见光)',prop:'doneVisible',props:'visible',status:''},
                {name:'带状正射(可见光)',prop:'doneVisiblePassageway',props:'visiblePassageway',status:''},
                {name:'区域航拍(可见光)',prop:'doneVisibleArea',props:'visibleArea',status:''},
                {name:'航点飞行(红外光)',prop:'doneInfrared',props:'infrared',status:'exception'},
                {name:'带状正射(红外光)',prop:'doneInfraredPassageway',props:'infraredPassageway',status:'exception'},
                {name:'区域航拍(红外光)',prop:'doneInfraredArea',props:'infraredArea',status:'exception'},
            ],
            
            // 计划完成情况
            planFinishInfo:{
                doneVisible: 0,//完成的航点飞行（可见光)
                visible: 0,//航点飞行（可见光)
                doneVisiblePassageway: 0,//完成的带状正射（可见光） 
                visiblePassageway: 0, //带状正射（可见光）
                doneVisibleInstant: 0, 
                visibleInstant: 0,
                visibleArea: 0,//区域航拍（可见光）
                doneVisibleArea: 0,//完成的区域航拍（可见光）
                infrared: 0, //航点飞行（红外光）
                doneInfrared: 0,//完成的航点飞行（红外光）
                infraredPassageway: 0, //带状正射（红外光）
                doneInfraredPassageway: 0,//带状正射（红外光）
                doneInfraredArea: 0,//区域航拍（红外光）
                infraredArea: 0,//区域航拍（红外光）
                doneInfraredInstant: 0, 
                infraredInstant: 0,
                totalPlan: 0, //总计划数
                donePlan: 0,//完成计划数
            },
            // 各机场平均运行时长
            runTimInfo:{
                avgRuntime:'',
                datas:[],
            },
            // 汇总
            basicInfo:{
                totalError: 0,
                totalMileage: 0,
                totalPic: 0,
                totolFightNum: 0,
            },
            // 今日巡检计划
            todayInspectionPlan:{
                todayCancelNum: 0,
                todayCreateNum: 0,
                todayDelayNum: 0,
                todayFinishNum: 0,
            },
            // 今日完成计划
            todayFinishPlansHeader:[
                {label:'计划名称',prop:'describes',width:'100'},
                {label:'计划周期',prop:'planningCycle',width:'100'},
                {label:'任务内容',prop:'taskContent',width:'100'},
                {label:'计划状态',prop:'status',width:'100'},
            ],
            plansState:{
                created:'已创建',
                started:'进行中',
                done:'已完成',
                canceled:'已取消',
                delay:'已过期',
                interrupted:'已中断，',
            },
            todayFinishPlans:[],

            // 异常统计
            errorInfosHeader:[
                {label:'各异常类别',prop:'device',width:'100'},
                {label:'异常总数',prop:'totalNum',width:'90'},
                {label:'一般异常数',prop:'normalNum',width:'90'},
                {label:'严重异常数',prop:'seriousNum',width:'90'},
                {label:'危险异常数',prop:'dangerNum',width:'90'},
            ],
            errorInfos:[],


           
            

        }
    },
    created(){

        this.initStyle();

        this.getInitData();
    },

    methods:{
        /**
         * @description: 初始化样式
         * @param {*}
         * @return {*}
         */        
        initStyle(){
            if (window.innerWidth){
                // ie9+ 最新的浏览器
                this.scaleWidth = window.innerWidth / 1920;
                this.scaleHeight = window.innerHeight / 1080;
            } else if (document.compatMode === "CSS1Compat") {
                // W3C
                this.scaleWidth = document.documentElement.clientWidth / 1920;
                this.scaleHeight = document.documentElement.clientHeight / 1080;
            } else{
                this.scaleWidth = document.body.clientWidth / 1920;
                this.scaleHeight = document.body.clientHeight / 1080;
            }
        },
        /**
         * @description: 获取初始化数据
         * @param {*}
         * @return {*}
         */        
        getInitData(){
            get_cockpitData().then(res =>{
                
                const { code ,data,msg} = res;
                if(code == 200){

                    this.basicInfo = data.basicInfo;

                    this.planFinishInfo = data.planFinishInfo;

                    this.todayInspectionPlan = data.todayInspectionPlan;

                    // 动态设置圆形表格的值
                    this.chartCircalVal = this.setChartProgress('donePlan','totalPlan')
                    // 获取到
                    this.runTimInfo = data.runTimInfo;

                    this.todayFinishPlans = data.todayFinishPlans.map(it =>{
                        it.plansCycle = `${it.startTime}-${it.endTime}`;
                        it.state = this.plansState[it.state];
                        return it
                    });
                    // 异常
                    this.errorInfos = data.errorInfos;

                }else{
                    this.$message.warning(msg)
                }
            })
        },
        /**
         * @description: 返回planFinishInfo数据
         * @param {*} doneVal
         * @param {*} val
         * @return {*}
         */        
        setChartProgress(doneVal,val){
            if(this.planFinishInfo[doneVal] == 0){
                return 0
            }
            return Number( ( (this.planFinishInfo[doneVal] / this.planFinishInfo[val]) * 100).toFixed(0) )

        },
        format(doneVal,val){ 
            return () =>{

                
                return `${this.planFinishInfo[doneVal]}/${this.planFinishInfo[val]}`
            }
        },
        radioChange(val){
            if( this.planFinishInfo.donePlan == 0){
                if(val == 0){
                    // 未完成
                 this.chartCircalVal  = 100
                }else{
                     this.chartCircalVal = 0
                }
            }else{
                if(val == 0){ 

                    // 未完成 总数减去已完成除以总数
                    this.chartCircalVal =  Number(  ( (this.planFinishInfo.totalPlan - this.planFinishInfo.donePlan ) / this.planFinishInfo.totalPlan  * 100).toFixed(0) )

                }else{
                    // 已完成 已完成除以总数
                    this.chartCircalVal = this.setChartProgress('donePlan','totalPlan')

                }
            }


           
            

        }
    }
}

</script>


<style lang="scss" scoped>
.app-cockpit{
    width: 100%;
    height: 100%;
    // padding: 10px;
    background: #fff;
    .cockpit-card{
        height: 40%;
        width: 100%;
        overflow-y: auto;
        .el-card__body{
            padding: 0px 20px 20px 20px;
        }
        .col{
            height: 236px;
            box-shadow: 2px 2px 5px 2px rgb(0 0 0 / 10%);
            .col_chart{
                display: block;
                height: 30px;
                line-height: 30px;
                text-align: left;
            }
            .col_chart_title{
                display: block;
                padding-left: 20px;
                font-size: 12px;
            }
            .col_radio{
                .el-radio{
                    margin-right: 10px;
                }
            }
            .col_Num{
                height: 50px;
                line-height: 50px;
            }
            .col_list_header{ 
                .col_list_title{
                    width: 50%;
                    text-align: center;
                    display: inline-block;
                }
            }
           
            .col_list{
                height: 100px;
                overflow: auto;
                .col_list_num{
                     .el-divider--horizontal{
                         margin:5px 0;
                     }
                    .col_list_name{
                         width: 50%;
                        text-align: center;
                        display: inline-block;

                    }
                }
            }

        }
        .child_col{
            height: 118px;
            box-shadow: 2px 2px 5px 2px rgb(0 0 0 / 10%);

        }
    }
    .cockpit_btm_card{
        ::v-deep .el-card__body{
            height:100%;
            padding: 15px 20px 10px 20px;
        }
        margin-top: 5px;
        height: 60%;
        width: 100%;
        .btm_card_row{
            height: 100%;
             .btm_col{
                height: 100%;
                box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
                .btn_today{
                    height: 80px;
                }
            }
        }
       

    }
    .title{
        color:rgb(24, 144, 255);
        height: 30px;
        line-height: 30px;
        padding-left: 5px;
        font-size: 14px;
        font-weight: 600;
    }
    .col_title,.col_Num{
        display: block;
        width: 100%;
        text-align: center;
        display: inline-block;
        height: 50px;
        line-height: 50px;
        font-weight: 600;
    }
    .col_title{
        height: 40px;
        line-height: 40px;
    }
    .col_Num{
        color: rgb(24, 144, 255);
    }
    

}
</style>
