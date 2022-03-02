<template>
     <!-- 线路巡检报告 -->
    <el-card class="app-container">
        <tree-container @ceshi="ccc">
            <el-tabs type="border-card" class="eltabs" v-model="activeName">
                <el-tab-pane label="巡检报告" name="one">
                    <chart-report v-if="activeName == 'one' && Object.keys(treeData).length>0" :treeData.sync="treeData" :msg.sync="activeName" :childData="childData"></chart-report>
                </el-tab-pane>
                <el-tab-pane label="日报表" name="two">
                    <date-report v-if="activeName == 'two' && Object.keys(treeData).length>0" :treeData.sync="treeData" :msg.sync="activeName" :childData.sync="childData"></date-report>
                </el-tab-pane>
            </el-tabs>    
         </tree-container>
        <!-- 双击显示详情 -->
        <picture-info v-if="show == 1"></picture-info>
    </el-card>
</template>
<script>
export default {
    data () {
        return {
            treeData: {},
            childData: {},
            activeName: "one",
            show: 0, // true 显示tab,false 显示图片详情
        }
    },    
    mounted() {
    },
    methods: {
        ccc(data) {
            this.treeData = {};
            this.treeData = data;
        }
    }

}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;
    ::v-deep{
        .el-card,.el-card__body{height:100%;}
        .el-tabs__content{flex:1;
            .el-tab-pane{height:100%;}
        }
    }
    .eltabs {display:flex; flex-direction: column;border-radius: 4px;box-shadow:none;height:100%;}        
}
</style>