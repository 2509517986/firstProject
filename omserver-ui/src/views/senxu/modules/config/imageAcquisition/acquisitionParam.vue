<template>
    <!-- 图像采集-采集参数配置 -->
    <el-card class="box-card">
        <div slot="header" class="head">
            <el-tooltip class="item" effect="dark" content="收藏采集参数配置" placement="bottom-start" v-if="favIcon">
                <svg-icon :icon-class="act?'fav_sed':'fav'" class="fav" @click="addFav"></svg-icon>
            </el-tooltip>
            <span>采集参数配置</span>
            <el-button size="mini" type="primary" class="querybtn" @click="query" :disabled="fromTreeData.node[0].yuntais.length<0">查询设备</el-button>
        </div>
        <div class="box-content"  v-loading="open && Object.keys(thisYuntaiInfo).length>0">
            <el-form ref="form" :model="form" label-width="80px" size="mini">
                <el-form-item label="通道号">
                   <span class="channel">{{form.pictureConduit?form.pictureConduit:0}}</span>
                </el-form-item>
                <el-form-item label="通道类型" porp="pictureConduitType">
                    <el-radio-group v-model="form.pictureConduitType">
                        <el-radio label="1">主通道</el-radio>
                        <el-radio label="2">从通道</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="色彩模式" porp="pictureColor">
                    <el-radio-group v-model="form.pictureColor">
                        <el-radio label="1">彩色</el-radio>
                        <el-radio label="0">黑白</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="亮度" porp="pictureBrightness">
                    <el-slider v-model="form.pictureBrightness" size="mini" :show-tooltip="false" :min="1" :max="100" show-input input-size="mini"></el-slider>
                </el-form-item>
               <el-form-item label="对比度" porp="pictureContrast">
                    <el-slider v-model="form.pictureContrast" size="mini" :show-tooltip="false" :min="1" :max="100" show-input input-size="mini"></el-slider>
                </el-form-item>
                <el-form-item label="饱和度" porp="pictureSaturation">
                    <el-slider v-model="form.pictureSaturation" size="mini" :show-tooltip="false" :min="1" :max="100" show-input input-size="mini"></el-slider>
                </el-form-item>
                <el-form-item label="分辨率" porp="pictureResolution">
                    <el-select v-model="form.pictureResolution" placeholder="请选择" style="width:100%" size="small">
                        <el-option v-for="item in dicts.resolution.obj" :key="item.value" :label="item.dictLabel" :value="item.dictValue"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" size="mini" @click="onSubmit" :disabled="Object.keys(thisYuntaiInfo).length<1 || open">设置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </el-card>
</template>
<script>
import * as global  from "@/utils/global";
import { mapGetters } from 'vuex'
import { getPicture,savePicture } from "@/api/senxu/config/index";
export default {
    props: ['favIcon', 'treeData'],
    name: 'acquisitionParam',
    data () {
        return{
            open: true,
            act:false,
            form: {pictureConduitType:"1",pictureColor:"1",pictureBrightness:0,pictureContrast:0,pictureSaturation:0,pictureResolution:"1"},
            thisYuntaiInfo: {},
            fromTreeData: {}
        }
    },
    computed: {
        ...mapGetters([
            'dicts',
        ])
    },  
    watch: {
        treeData:{
            handler (newVal){
                this.open = true;
                this.form = {pictureConduitType:"1",pictureColor:"1",pictureBrightness:0,pictureContrast:0,pictureSaturation:0,pictureResolution:"1"};
                this.fromTreeData = newVal;
            },
            deep:true
        },
    },
    created() {
        this.fromTreeData = this.treeData;
        let name = this.$options.name;
        this.act = global.fav_init_toggle(name) == 0?false:true;
    },
    methods: {
        addFav() {
            let name = this.$options.name;
            this.act = global.addfav(name) == 0?false:true;
        },
        onSubmit() {
            this.form['yuntaiId'] = this.thisYuntaiInfo.id;
            savePicture(this.form).then(response => {
                if(response.code == 200) { 
                    this.$message.success(response.msg);
                } else {
                    this.$message(response.msg);
                }
            });
        },
        query() {
            // 查询设备
            this.thisYuntaiInfo = {};
            this.open = true;
            // this.thisYuntaiInfo['id'] = 1; // 测试
            for(let i = 0;i < this.fromTreeData.node[0].yuntais.length;i++) {
                if(this.fromTreeData.node[0].yuntais[i].state == 1) {
                    this.thisYuntaiInfo = this.fromTreeData.node[0].yuntais[i];
                    getPicture(this.thisYuntaiInfo.id).then(response => {
                        if(response.code == 200 && response.data) {   
                            this.open = false;
                            this.form = {
                                pictureConduitType:response.data.picture.pictureConduitType?response.data.picture.pictureConduitType:"1",
                                pictureColor:response.data.picture.pictureColor?response.data.picture.pictureColor:"1",
                                pictureBrightness: response.data.picture.pictureBrightness?parseInt(response.data.picture.pictureBrightness):1
                                ,pictureContrast: response.data.picture.pictureContrast?parseInt(response.data.picture.pictureContrast):1
                                ,pictureSaturation: response.data.picture.pictureSaturation?parseInt(response.data.picture.pictureSaturation):1
                                ,pictureResolution:response.data.picture.pictureResolution?response.data.picture.pictureResolution:"1"
                            } 
                        } 
                    });
                    return;
                }
            }
            if(Object.keys(this.thisYuntaiInfo).length<1) {
                this.$message("该线路无在线设备");
                return;
            }
        },
    }
}
</script>
<style lang="scss" scoped>
.head{display:flex;align-items:center;
    .fav{margin-right:5px;cursor: pointer;font-size:20px;color:$base-color-default;}
    .querybtn{margin-left:auto;}
}
.box-content{padding:10px 120px 0;
    ::v-deep{       
        .el-form-item__label{line-height:34px;}                          
        // el-silder
        .el-slider{flex:1;padding-left:10px;margin: 0 auto;}
        .el-input-number__decrease,.el-input-number__increase{display: none;}
        .el-slider__runway.show-input{margin-right:50px;}
        .el-input-number{width:40px;}
        .el-input__inner{padding-left:5px;}
        .el-slider__input .el-input__inner{padding:0px;}
    }
    .channel{border-radius: 3px;background:$base-color-default;color:#fff;display:inline-block;width:38px;height:28px;line-height: 28px;text-align:center;}
}
</style>