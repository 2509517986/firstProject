<template>
     <!-- 历史数据 -->
    <el-card class="app-container">                
        <tree-container @ceshi="ccc">
            <div class="historydata">
                <el-tabs v-model="activeName" type="card" class="tabsControl" @tab-click="tabchange">
                    <el-tab-pane :label="e.label" v-for="(e,i) in tabs" :key="i" ></el-tab-pane>
                </el-tabs>
                <div class="tabPane">
                    <component v-if="show && Object.keys(postToData).length>0" :is="iscomponent" :childData="postToData"></component>
                </div>
                <div class="timeAbs">
                    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="70px" size="mini" >
                        <el-form-item label="" prop="day">                        
                            <el-date-picker format="yyyy-MM-dd" value-format="yyyy-MM-dd" :picker-options="pickerOptions" v-model="queryParams.date" size="mini"  type="daterange"  range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                        </el-form-item>      
                        <el-form-item>
                            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查询</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
        </tree-container>
    </el-card>
</template>
<script>
import device from '../modules/historyData/device.vue'
export default {
  components: { device },
    data () {
        return{
            show: true,
            choiceDate: null,
            activeName: 0,
            postToData: {}, // 子组件
            tabs: [
                // {label: "设备", component: 'historydevice'}
                 {label: "环境", component: 'historyenvironment'}
                ,{label: "导线", component: 'historywire'}
                ,{label: "杆塔", component: 'historytower'}
                ,{label: "报警", component: "historyalarm"}
            ],
            yuntaiInfo: {},
            queryParams: {date: []},
            iscomponent: 'historyenvironment',
            pickerOptions: {
                onPick: ({ maxDate, minDate }) => {
                    this.choiceDate = minDate.getTime()
                    if (maxDate) {
                        this.choiceDate = ''
                    }
                },
                disabledDate: (time) => {
                    const self = this;
                    if (!!self.choiceDate) {
                        const startDay = (new Date(self.choiceDate).getDate() - 1) * 24 * 3600 * 1000;
                        const endDay = (new Date(new Date(self.choiceDate).getFullYear(),new Date(self.choiceDate).getMonth() + 1,0).getDate() - new Date(self.choiceDate).getDate()) * 24 * 3600 * 1000;
                        let minTime = self.choiceDate - startDay;
                        let maxTime = self.choiceDate + endDay;
                        return time.getTime() < minTime || time.getTime() > maxTime
                    }
                }
            }
        }
    },
    created () {
        const end = new Date();
        const start = new Date();
        const starttime = start.getTime() - 3600 * 1000 * 24 * 30;
        this.queryParams.date = [this.parseTime(starttime, '{y}-{m}-{d}'), this.parseTime(end, '{y}-{m}-{d}')];
        this.postToData = {yuntaiInfo:this.yuntaiInfo,timedata:this.queryParams.date}
    },
    methods: {
        ccc(data) {
             if(data.node && data.node.length>0 && data.node[0].node && data.node[0].node.type == "yuntai") {
                if(data.node[0].node.state == 1) {
                    this.postToData = {yuntaiInfo:data.node[0].node,timedata:this.queryParams.date};
                } else {
                    this.$message("云台离线状态");
                }
            }
        },
        handleQuery() {
            this.postToData = {};
            this.postToData = {yuntaiInfo:this.yuntaiInfo,timedata:this.queryParams.date};
        },
        tabchange() {
            // this.show = false;
            // this.$nextTick(() => {
            //     this.show = true;
                this.iscomponent = this.tabs[this.activeName].component;
            // });
        }
    }
}
</script>

<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
  ::v-deep{.el-card__body{height:100%;position: relative;}}
  .historydata{position: relative;height:100%;display: flex; flex-direction: column;
      .timeAbs{position: absolute;right:0;top:5px;}
      .tabsControl{
        ::v-deep{.el-tabs__content{flex:1;overflow: hidden;}}
      }
      .tabPane{flex:1;}
  }
}
</style>