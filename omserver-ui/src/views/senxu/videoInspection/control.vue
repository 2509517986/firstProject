<template>
     <!-- 控制云台 -->
    <el-card class="app-container">
        <tree-container ref="treecontainer" @ceshi="ccc">
            <div class="pictureInfo">
                <div class="left">
                   <!-- 视频多窗口 -->
                    <div :class="['boxs', 'boxs-'+layout.length]" v-if="tab == 0">
                        <template>
                            <div v-for="(e,i) in layout" :key="i" :class="['item', 'item-'+i, {act:thisvideo == i}]">
                                <div class="top"  @click.stop="videoselect(i)"><template v-if="e.parent.length>0 && false"><span v-for="(ee,ii) in e.parent" :key="ii">{{ee.name}} - </span></template>{{e.yuntai.name}}实时视频</div>
                                <div class="videopp"><video-player :ref="'videoplayer'+i" :id="'videoplayer'+i" :videoSrc="e.video" v-if="e.video.length>0" :yuntai="e.yuntai"  :control="control"  :thisPresetBit="thisPresetBit"></video-player></div>
                                <div v-if="e.video.length<1 && i == thisvideo && e.state == 1" style="display:flex;width:100%;height:100%; align-items:center;flex-direction: column;justify-content: center;"><span style="display:block;text-align:center;padding:10px;color:#fff;">读取失败</span><el-button icon="el-icon-refresh-left" size="mini" type="primary" style="width:100px;" @click="get_common_video">重新加载</el-button></div>
                            </div>
                        </template>
                    </div>
                    <!-- 录像回放 -->
                    <div class="boxs boxs1" v-if="tab== 1">
                        <template>
                            <div class="item item-0 act" style="height:100%;overflow:hidden;">
                                <div class="top">{{historyYuntaiObj.name}}视频回放</div>
                                <history-video-player v-if="Object.keys(thisHistoryVideo).length>0 && form.playbackType == 1" ref="videoplayerhistory" id="videoplayerhistory" :historyVideoObj="thisHistoryVideo" :yuntai="historyYuntaiObj"></history-video-player>
                             <!-- <video src="" v-if="form.playbackType == 2"></video>-->
                              <!-- <history-video-player ref="videoplayerhistory" id="videoplayerhistory" :videoSrc="thisHistoryVideo" v-if="thisHistoryVideo.length>0" :yuntai="historyYuntaiObj"></history-video-player> -->
                            </div>
                        </template>
                    </div>
                    <div class="layoutbox">
                        <div class="videoSource" v-if="tab == 0">
                            <span>平台连接{{layout[thisvideo].video.length>0?'成功':'失败'}}</span>
                            <div class="tools" v-if="false">
                                <span>视频清晰度：</span>
                                 <el-radio-group v-model="clarityval" @change="clarityChange" size="mini">
                                    <el-radio size="mini" v-for="(e,i) in clarity" :key="i" :label="e.id">{{e.name}}</el-radio>
                                </el-radio-group>
                            </div>
                        </div>
                        <div class="split-wrapper" v-if="tab == 0">
                            <span class="rtsp" v-if="false"><el-switch v-model="rtsp" active-color="#13ce66" size="mini" inactive-text="RTSP取流"></el-switch></span>
                            <svg-icon iconClass="fullscreen" className='fullscreen' :class="{act:layout.length==0}" @click="thisFullScreen"></svg-icon>
                            <div @click="layoutselect(1)" :class="['layOut-item',{act:layout.length== 1}]"><table class="split-table"><tr><td colspan="1" rowspan="1" style="width: 100%; height: 100%;"></td></tr></table></div>
                            <div @click="layoutselect(4)" :class="['layOut-item',{act:layout.length== 4}]"><table class="split-table"><tr><td colspan="1" rowspan="1" style="width: 50%; height: 50%;"></td><td  colspan="1" rowspan="1" style="width: 50%; height: 50%;"></td></tr><tr><td colspan="1" rowspan="1" style="width: 50%; height: 50%;"></td><td  colspan="1" rowspan="1" style="width: 50%; height: 50%;"></td></tr></table></div>
                            <div @click="layoutselect(6)" :class="['layOut-item',{act:layout.length== 6}]"><table class="split-table"><tr><td colspan="2" rowspan="2" style="width: 66.6667%; height: 66.6667%;"></td><td colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td> </tr><tr> <td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td> </tr><tr> <td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td></tr></table></div>
                            <div @click="layoutselect(9)" :class="['layOut-item',{act:layout.length== 9}]"><table class="split-table"><tr><td colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td>  </tr><tr> <td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td> </tr><tr>  <td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td></tr></table></div>
                            <div @click="layoutselect(12)" :class="['layOut-item',{act:layout.length== 12}]"><table class="split-table"><tr><td colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td>  </tr><tr> <td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td> </tr><tr>  <td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="2" rowspan="1" style="height: 33.3333%;" class="before" title="x12"></td></tr></table></div>
                            <!-- <div @click="layoutselect(15)" :class="['layOut-item',{act:layout== 15}]"><table class="split-table"><tr><td colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td>  </tr><tr> <td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td> </tr><tr>  <td  colspan="1" rowspan="1" style="width: 33.3333%; height: 33.3333%;"></td><td  colspan="2" rowspan="1" style="height: 33.3333%;" class="before" title="x15"></td></tr></table></div> -->
                            <div class="controlboard">
                                <span>控制面板</span>
                                <svg-icon iconClass="layout" className='layout'></svg-icon>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="right">
                    <div class="rightbox">
                        <!-- 实时视频、录像回放 -->
                        <div class="tabs">
                            <div :class="['item',{act:tab==0}]" @click="tabb(0)">实时视频</div>
                            <div :class="['item',{act:tab==1}]" @click="tabb(1)">录像回放</div>
                        </div>
                            <div class="content" v-show="tab==0">
                            <!-- 实时视频 -->
                            <el-input placeholder="预置位查询" v-model="preset_bit_val" class="input-with-select" size="mini">
                                <el-button slot="append" icon="el-icon-search" size="mini" @click="searchPresetBit"></el-button>
                            </el-input>
                            <div class="bit_lists" v-loading="bit_loading">
                                <el-scrollbar horizontal="false" style="height: 100%" size="mini" ref="scroll">
                                    <div class="infinite-list" v-infinite-scroll="getPresetList">
                                        <div :class="['item', {set:e.set && Object.keys(layout[thisvideo].yuntai).length>0}]" v-for="(e,i) in preset_bit_list" :key="i">
                                            <i class="el-icon-video-camera"></i>
                                            <span v-if="!e.showedit">{{e.name}}</span>
                                            <el-input class="edit_bit" v-if="e.showedit" v-model="preset_bit_set_val" size="mini"></el-input>
                                            <div class="tools">
                                                <el-tooltip class="item" effect="dark" content="设置" placement="top-end" v-if="!e.set && !e.showedit && Object.keys(layout[thisvideo].yuntai).length>0" size="mini">
                                                    <i class="el-icon-setting" @click="e.set = true;add_presetPosition(e,i)"></i>
                                                </el-tooltip>
                                                <el-tooltip class="item" effect="dark" content="删除设置" placement="top-end" v-if="e.set && !e.showedit && Object.keys(layout[thisvideo].yuntai).length>0" size="mini">
                                                    <i class="el-icon-delete" @click="e.set = false;del_presetPosition(e,i);thisPresetBit={}"></i>
                                                </el-tooltip>
                                                <el-tooltip class="item" effect="dark" content="调用" placement="top-end" v-if="e.set && !e.showedit && Object.keys(layout[thisvideo].yuntai).length>0" size="mini">
                                                    <i class="el-icon-circle-check" @click="transferClick(e,i)"></i>
                                                </el-tooltip>
                                                <el-tooltip class="item" effect="dark" content="编辑" placement="top-end" v-if="e.set && !e.showedit && Object.keys(layout[thisvideo].yuntai).length>0" size="mini">
                                                    <i class="el-icon-edit-outline" @click="e.showedit = true;preset_bit_set_val=e.name;"></i>
                                                </el-tooltip>
                                                <el-tooltip class="item" effect="dark" content="初始值" placement="top-end" v-if="e.showedit && Object.keys(layout[thisvideo].yuntai).length>0" size="mini">
                                                    <i class="el-icon-refresh" @click="preset_bit_set_val=e.oldname;"></i>
                                                </el-tooltip>
                                                <el-tooltip class="item" effect="dark" content="修改" placement="top-end" v-if="e.showedit && Object.keys(layout[thisvideo].yuntai).length>0" size="mini">
                                                    <i class="el-icon-check" @click="e.showedit = false;e.name=preset_bit_set_val;edit_presetPosition(e,i)"></i>
                                                </el-tooltip>
                                                <el-tooltip class="item" effect="dark" content="取消" placement="top-end" v-if="e.showedit && Object.keys(layout[thisvideo].yuntai).length>0" size="mini">
                                                    <i class="el-icon-close" @click="e.showedit = false"></i>
                                                </el-tooltip>
                                            </div>
                                        </div>
                                    </div>
                                </el-scrollbar>
                            </div>
                            <div class="yuntaicontrol">
                                <h4>云台控制</h4>
                                <div class="tabMain" v-loading="control_loading">
                                    <el-radio-group v-model="yuntai_tab" size="mini">
                                        <el-radio-button size="mini" :label="i" v-for="(e,i) in tabs" :key="i">{{e}}</el-radio-button>
                                    </el-radio-group>
                                    <!-- 控制 -->
                                    <div class="plane" v-show="yuntai_tab==0">
                                        <div class="circleDirect">
                                            <el-row :gutter="5">
                                                <!-- <el-col :span="8" class="item"><el-button :disabled="disabled" size="mini" class="lt45" icon="el-icon-caret-top" @mousedown.native.prevent="remoteControl(53, control.speed)" @mouseup.native.prevent="gotouchend"></el-button></el-col>     -->
                                                <el-col :span="24" class="item" style="padding-left:0;padding-right:4px;"><el-button  style="margin: 0 auto;" :disabled="disabled" size="mini" icon="el-icon-caret-top" @mouseleave.native.prevent="omLeave()" @mousedown.native.prevent="gotouchstart(49, control.speed)" @mouseup.native.prevent="gotouchend" @click.native="ccclick(3,0)"></el-button></el-col>
                                                <!-- <el-col :span="8" class="item"><el-button :disabled="disabled" size="mini" class="rt45" icon="el-icon-caret-top" @mousedown.native.prevent="remoteControl(54, control.speed)" @mouseup.native.prevent="gotouchend"></el-button></el-col>  -->
                                            </el-row>
                                            <el-row :gutter="5">
                                                <el-col :span="8" class="item"><el-button :disabled="disabled" size="mini" icon="el-icon-caret-left"  @mousedown.native.prevent="gotouchstart(51, control.speed)" @mouseleave.native.prevent="omLeave()" @mouseup.native.prevent="gotouchend" @click.native="ccclick(5,0)"></el-button></el-col>
                                                <el-col :span="8" class="item"><el-button disabled size="mini">{{control.speed}}</el-button></el-col>
                                                <el-col :span="8" class="item"><el-button :disabled="disabled" size="mini" icon="el-icon-caret-right"  @mousedown.native.prevent="gotouchstart(52, control.speed)" @mouseleave.native.prevent="omLeave()" @mouseup.native.prevent="gotouchend" @click.native="ccclick(6,0)"></el-button></el-col>
                                            </el-row>
                                            <el-row :gutter="5">
                                                <!-- <el-col :span="8" class="item"><el-button :disabled="disabled" size="mini" class="lb45" icon="el-icon-caret-bottom"  @mousedown.native.prevent="remoteControl(55, control.speed)" @mouseup.native.prevent="gotouchend"></el-button></el-col> -->
                                                <el-col :span="24" class="item" style="padding-left:0;padding-right:4px;"><el-button style="margin: 0 auto;" :disabled="disabled" size="mini" icon="el-icon-caret-bottom" @mouseleave.native.prevent="omLeave()" @mousedown.native.prevent="gotouchstart(50, control.speed)" @mouseup.native.prevent="gotouchend"  @click.native="ccclick(4,0)"></el-button></el-col>
                                                <!-- <el-col :span="8" class="item"><el-button :disabled="disabled" size="mini" class="rb45" icon="el-icon-caret-bottom"  @mousedown.native.prevent="remoteControl(56, control.speed)" @mouseup.native.prevent="gotouchend"></el-button></el-col> -->
                                            </el-row>
                                        </div>
                                        <div class="speed">
                                            <el-slider size="mini" v-model="control.speed" :show-tooltip="false" :min="1" :max="9" show-input input-size="mini" @change="speedChange"></el-slider>
                                        </div>
                                        <div class="controlitem">
                                            <el-button icon="el-icon-plus" circle size="mini"  @click="remoteControl(11, 0)"></el-button>
                                            <span>光圈</span>
                                            <el-button icon="el-icon-minus" circle size="mini"  @click="remoteControl(12, 0)"></el-button>
                                        </div>
                                        <div class="controlitem">
                                            <el-button icon="el-icon-plus" circle size="mini"  @click="remoteControl(7, 0)"></el-button>
                                            <span>放缩</span>
                                            <el-button icon="el-icon-minus" circle size="mini"  @click="remoteControl(8, 0)"></el-button>
                                        </div>
                                        <div class="controlitem">
                                            <el-button icon="el-icon-plus" circle size="mini"  @click="remoteControl(13, 0)"></el-button>
                                            <span>聚焦</span>
                                            <el-button icon="el-icon-minus" circle size="mini"  @click="remoteControl(14, 0)"></el-button>
                                        </div>
                                    </div>
                                    <!-- 图像 -->
                                    <div class="plane picturebox" v-show="yuntai_tab==1">
                                        <div class="picturebox-item">
                                            <span class="demonstration">亮度</span>
                                            <el-slider v-model="control.brightness" size="mini" :mix="1" :max="100"></el-slider>
                                        </div>
                                        <div class="picturebox-item">
                                            <span class="demonstration">对比度</span>
                                            <el-slider v-model="control.contrast" size="mini" :mix="1" :max="100"></el-slider>
                                        </div>
                                        <div class="picturebox-item">
                                            <span class="demonstration">饱和度</span>
                                            <el-slider v-model="control.saturation" size="mini" :mix="1" :max="100"></el-slider>
                                        </div>
                                        <div class="tools">
                                            <el-button size="mini" type="primary" @click="setdemonstration({brightness:control.brightness,contrast:control.contrast,saturation:control.saturation})">设置</el-button>
                                            <el-button size="mini" type="success" @click="setdemonstration({brightness:50,contrast:50,saturation:50});brightness,control.brightness=50;control.contrast=50;control.saturation=50;">恢复默认值</el-button>
                                        </div>
                                    </div>
                                    <!-- 巡检 -->
                                    <div class="plane inspection" v-show="yuntai_tab==2">
                                        <div class="inspection-item">
                                            <span>巡航</span>
                                            <el-input-number  v-model="control.cruise" size="mini" :min="1" :max="16"></el-input-number>
                                            <p class="bbttn" @click="remoteControl(15, control.cruise)"><font>开始</font></p>
                                            <p class="bbttn red" @click="remoteControl(16, control.cruise)"><font>停止</font></p>
                                            <p class="bbttn" @click="opencruiseset"><font>设置</font></p>
                                        </div>
                                        <div class="inspection-item">
                                            <span>巡检</span>
                                            <el-input-number  v-model="control.inspection" size="mini" :min="1" :max="4"></el-input-number>
                                            <p class="bbttn" @click="remoteControl(31, control.inspection)"><font>开始</font></p>
                                            <p class="bbttn red" @click="remoteControl(32, control.inspection)"><font>停止</font></p>
                                            <p class="bbttn" @click="openinspectionset"><font>设置</font></p>
                                        </div>
                                        <div class="inspection-item">
                                            <span>辅助</span>
                                            <el-input-number v-model="control.assist" size="mini" :min="1"></el-input-number>
                                            <p class="bbttn" @click="remoteControl(17, control.assist)"><font>开始</font></p>
                                            <p class="bbttn red" @click="remoteControl(18, control.assist)"><font>停止</font></p>
                                        </div>
                                        <div class="inspection-item">
                                            <el-switch size="mini" v-model="control.autoscan"  @change="toggleSwitch(19,20,0,control.autoscan)" inactive-text="自动扫描" :active-text="control.autoscan?'开':'关'"> </el-switch>
                                        </div>
                                        <div class="inspection-item">
                                            <el-switch size="mini" v-model="control.randomscan"  @change="toggleSwitch(21,22,0,control.randomscan)" inactive-text="随机扫描" :active-text="control.randomscan?'开':'关'"> </el-switch>
                                        </div>
                                    </div>
                                    <!-- 其他 -->
                                    <div class="plane" v-show="yuntai_tab==3">
                                        <div class="inspection-item">
                                            <el-switch size="mini" v-model.number="control.wiper" inactive-text="雨刮" @change="setdemonstration({wiper:control.wiper?1:0})" :active-text="control.wiper?'开':'关'"> </el-switch>
                                        </div>
                                        <div class="inspection-item">
                                          视频协议：<el-select ref="videoProtocol" style="width:100px" v-model="control.videoProtocol" placeholder="请选择" >
                                           <!-- <el-option key="tcp"
                                               label="tcp"
                                               value="tcp"/>-->
                                            <el-option key="udp"
                                                       label="udp"
                                                       value="udp"/>
                                          </el-select>
                                        </div>
                                        <!-- <div class="inspection-item">
                                            <el-switch size="mini" v-model.number="control.windowsHeat" inactive-text="视窗加热" @change="setdemonstration({windowsHeat:control.windowsHeat?1:0})" :active-text="control.windowsHeat?'开':'关'"> </el-switch>
                                        </div> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="content" v-show="tab==1">
                            <!-- 录像回放 -->
                            <div class="playback-container">
                                <el-form ref="form" :model="form" label-width="90px" label-position="top">
                                    <el-form-item label="位置">
                                        <el-select size="mini" v-model="form.playbackType" placeholder="请选择位置" @change="playbackchange">
                                            <el-option label="前端" value="1"></el-option>
                                            <!-- <el-option label="中心" value="2"></el-option> -->
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="日期">
                                        <el-date-picker type="date" placeholder="选择日期" format="yyyy-MM-dd"  value-format="yyyy-MM-dd" size="mini" v-model="form.captureTime"></el-date-picker>
                                    </el-form-item>
                                    <el-form-item v-if="false">
                                        <el-checkbox-group  size="mini" v-model="form.type">
                                            <el-checkbox size="mini" label="定时" name="type"></el-checkbox>
                                            <el-checkbox size="mini" label="手动" name="type"></el-checkbox>
                                            <el-checkbox size="mini" label="联动" name="type"></el-checkbox>
                                        </el-checkbox-group>
                                    </el-form-item>
                                    <el-form-item label="文件格式" v-if="false">
                                        <el-radio-group size="mini" v-model="form.resource">
                                            <el-radio size="mini" label="录像"></el-radio>
                                            <el-radio size="mini" label="图片"></el-radio>
                                        </el-radio-group>
                                    </el-form-item>
                                    <el-form-item>
                                        <el-button size="mini" type="primary" @click="searchPlayBack">查询</el-button>
                                        <!-- <el-button size="mini" type="success" @click="downPlayBack">下载</el-button> -->
                                    </el-form-item>
                                </el-form>
                                <div class="lists">
                                    <h4>录像文件列表</h4>
                                    <el-scrollbar horizontal="false" style="height: 100%" size="mini" v-if="playbackLists.length>0">
                                          <el-radio-group v-model="download" v-loading="playbackLoadding" @change="historyPlay" ref="playbackLists"><!---->
                                            <el-radio name="playbackfiles" style="display:block;" v-for="(e,i) in playbackLists" :key="i" size="mini" :label="i" >{{e.filename}}</el-radio>
                                            <!--有时切换设备，重查，点回同样索引的radio，并不会引发change事件-->
                                        </el-radio-group>
                                    </el-scrollbar>
                                    <span v-else>暂无数据</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </tree-container>
        <!-- 巡航 -->
        <el-dialog title="巡航设置" :visible.sync="dialogVisible"  width="50%" class="dialogform"  :destroy-on-close="true" @close="dialogclose">
            <el-row :gutter="20" style="margin-bottom: 20px;" v-if="dialogVisible">
                <el-col :span="7">
                    <div class="caishu">
                        <span style="margin-right:10px;">通道号:</span>
                        <el-select size="mini" v-model="cruisequery.chanelNum" @change="cruiseDisabled = true">
                            <el-option label="通道1" value="1">通道1</el-option>
                            <!-- <el-option label="通道2" value="2">通道2</el-option> -->
                        </el-select>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="caishu">
                        <span style="margin-right:10px;">巡航组号:</span>
                        <el-select size="mini" v-model="cruisequery.cruiseGroupNum" @change="cruiseDisabled = true">
                            <el-option :label="i+1" :value="i+1" v-for="(e,i) in cruiseTotal" :key="i">{{i+1}}</el-option>
                        </el-select>
                    </div>
                </el-col>
                <el-col :span="8">
                    <el-button size="mini" type="primary" @click="cruiseclick">查询</el-button>
                    <el-button size="mini" type="success" @click="addcruise" :disabled="cruiseDisabled">新增</el-button>
                </el-col>
            </el-row>
            <el-row v-if="dialogVisible">
                <el-col :span="24">
                    <el-table :data="tableData" border style="width: 100%" height="250" size="mini" v-loading="cruiseLoading">
                        <el-table-column label="序号" align="center" type="index" width="50"> </el-table-column>
                        <el-table-column prop="presetNum" label="预置位号">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.presetNum" :min="1" label="1" size="mini" :max="bits" v-show="scope.row.show"></el-input-number>
                                <span v-show="!scope.row.show">{{scope.row.presetNum}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="residenceTime" label="停留时间(s)">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.residenceTime" :min="1" label="1" :max="255" size="mini" v-show="scope.row.show"></el-input-number>
                                <span v-show="!scope.row.show">{{scope.row.residenceTime}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="speed" label="速率">
                            <template slot-scope="scope">
                                <el-input-number v-model="scope.row.speed" :min="1" label="1" size="mini" v-show="scope.row.show"></el-input-number>
                                <span v-show="!scope.row.show">{{scope.row.speed}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <!-- <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button> -->
                                <el-button size="mini" @click="scope.row.show = true" v-if="!scope.row.show">编辑</el-button>
                                <el-button size="mini" @click="editCruiseRow(scope.row)" type="primary" v-if="scope.row.show">确定</el-button>
                                <el-button type="danger" size="mini" @click="deleteCruiseRow(scope.row)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-col>
            </el-row>
            <!-- <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false" size="mini">取 消</el-button>
                <el-button type="primary" size="mini" @click="cruiseSure">确 定</el-button>
            </span> -->
        </el-dialog>
        <!-- 巡检 -->
        <el-dialog title="巡检设置" :visible.sync="dialogVisible2"  width="50%" class="dialogform" @close="dialogclose2">
            <el-row :gutter="20" style="margin-bottom: 20px;">
                <el-col :span="7">
                    <div class="caishu">
                        <span style="margin-right:10px;">通道号:</span>
                        <el-select size="mini" v-model="tacticsquery.chanelNum"  @change="argumentsDisabled = true;strategDisabled=true;inspectionData=[];strategyData=[]">
                            <el-option label="通道1" value="1">通道1</el-option>
                            <!-- <el-option label="通道2" value="2">通道2</el-option> -->
                        </el-select>
                    </div>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-tabs v-model="activeName">
                        <el-tab-pane label="巡检参数配置" name="0"></el-tab-pane>
                        <el-tab-pane label="巡检策略配置" name="1"></el-tab-pane>
                    </el-tabs>
                    <!-- 巡检参数配置 -->
                    <div  v-if="activeName == 0">
                        <el-row style="margin-bottom: 20px;">
                            <el-col :span="7">
                                <div class="caishu">
                                    <span style="margin-right:10px;">巡检组号:</span>
                                    <el-select size="mini" v-model="tacticsquery.groupId" @change="argumentsDisabled = true">
                                        <el-option :label="i+1" :value="i+1" v-for="(e,i) in inspectionTotal" :key="i">{{i+1}}</el-option>
                                    </el-select>
                                </div>
                            </el-col>
                            <el-col :span="16">
                                <el-button size="mini" @click="get_arguments">查询</el-button>
                                <el-button size="mini" :disabled="argumentsDisabled" type="success" @click="addInspection">新增</el-button>
                            </el-col>
                        </el-row>
                        <el-table :data="inspectionData" border style="width: 100%" height="250" size="mini" v-loading="argumentLoading">
                            <el-table-column label="序号" align="center" type="index" width="50"> </el-table-column>
                            <el-table-column  label="巡检点序号">
                                 <template slot-scope="scope">
                                    <span>{{scope.row.inspectionTacticsId}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作">
                                <template slot-scope="scope">
                                    <el-button size="mini" @click="tacticsSetting(scope.row)">设置</el-button>
                                    <el-button type="danger" size="mini" @click="deleteArgumRow(scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    <!-- 巡检策略配置 -->
                     <div  v-if="activeName == 1">
                        <el-row style="margin-bottom: 20px;">
                            <el-col>
                                <el-button size="mini" @click="strategyQuery">查询</el-button>
                                <el-button size="mini" type="primary" @click="strategyAdd" :disabled="strategDisabled"  >新增</el-button>
                                <el-button size="mini" type="success"   @click="strateSet" :disabled="strategDisabled">设置巡检策略</el-button>
                            </el-col>
                        </el-row>
                        <el-table :data="strategyData" border style="width: 100%" height="250" size="mini"  v-loading="tacticsLoading">
                            <el-table-column label="序号" align="center" type="index" width="50"> </el-table-column>
                            <el-table-column prop="shu" label="巡检组号码">
                                <template slot-scope="scope">
                                    <el-select size="mini" v-model="scope.row.groupNum"  v-show="scope.row.show">
                                        <el-option :label="i+1" :value="i+1" v-for="(e,i) in inspectionTotal" :key="i">{{i+1}}</el-option>
                                    </el-select>
                                    <span v-show="!scope.row.show">{{scope.row.groupNum}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="shu" label="使能标志">
                                <template slot-scope="scope">
                                    <el-checkbox v-model="scope.row.enable" :checked="scope.row.enable==1? true:false">巡检使能</el-checkbox>
                                </template>
                            </el-table-column>
                            <el-table-column prop="num" label="巡检次数">
                                <template slot-scope="scope">
                                    <el-input-number v-model="scope.row.inspectTimes" :min="1" label="1" size="mini" v-show="scope.row.show"></el-input-number>
                                    <span v-show="!scope.row.show">{{scope.row.inspectTimes}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="time" label="开始时间">
                                <template slot-scope="scope">
                                    <el-time-picker v-show="scope.row.show" style="width:120px" :clearable="false" v-model="scope.row.beginTime" value-format="HH:mm:ss" format="HH:mm:ss" size="mini"></el-time-picker>
                                    <span v-show="!scope.row.show">{{scope.row.beginTime}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="操作" width="280">
                                <template slot-scope="scope">
                                    <el-button size="mini" @click="scope.row.show = !scope.row.show" :type="scope.row.show?'primary':''">{{scope.row.show?"确定":"编辑"}}</el-button>
                                    <el-button type="danger" size="mini" @click="deleteRow(scope.$index, strategyData)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-col>
            </el-row>
        </el-dialog>
    </el-card>
</template>
<script>
import {videoPlaybackDetail, historyvideo_stop, videoPlayback, edit_arameters, getVideoInfo, remoteControl,get_history_video, sendPlayCmd, sendStopCmd, setPicParams,add_presetPosition,edit_presetPosition,del_presetPosition,get_presetPosition,get_arguments,add_arguments,del_arguments,edit_arguments, get_tactics, add_tactics, del_tactics, edit_tactics, get_cruise, add_cruise, del_cruise, edit_cruise } from "@/api/senxu/yuntai_video/index";
import * as global from "@/utils/global"
export default {
    data () {
        return {
            mouseupCC:false,
            timmer: '',
            bit_loading: false, // 预置位loading
            control_loading: false, // 云台loading
            activeName:0, // 巡检tab
            dialogVisible2:false, // 巡检
            dialogVisible: false, // 巡航
            cruisequery: {chanelNum:'1', cruiseGroupNum:1}, // 巡航查询条件
            tacticsquery: {chanelNum:'1', groupId:1}, // 巡检查询条件
            tableData: [], // 巡航数据
            bits: global.PRESET_BIT_TOTAL, // 预置位列表
            inspectionData: [], // 巡检参数配置
            strategyData: [], // 巡检策略
            inspectionsSetNum:undefined, // 巡检策略查询后返回的数据，要post上去设置巡检策略的
            cruiseTotal: global.CRUISE_TOTAL,
            inspectionTotal:global.INSPECTION_TOTAL,
            tabs: ["控制","图像","巡检","其他"], // 云台控制
            yuntai_tab:0,
            argumentLoading: false, // 巡检参数loading
            argumentsDisabled: true, // 巡检先查询后才能开启新增
            tacticsLoading: false, // 巡检策略loading
            strategDisabled: true, // 巡检策略先查询后才能开启新增和设置
            cruiseDisabled: true, // 巡航
            cruiseLoading: false, // 巡航loading
            clarity: [{id:1,name:"差"},{id:2,name: "较差"}, {id:3,name: "一般"}, {id:4,name:"好"},{id:5,name: "较好"}],
            clarityval: 1,
            thisvideo:0, // 当前layout窗口index索引
            thisvideoP:0, // 当前layout窗口播放视频的index
            thisPresetBit: {}, // 当前预置位信息
            tab:0, // 切换录像回放和控制云台
            defaultobj:{yuntai:{},video:'',parent: [],perset: {},control: {}},
            layout: undefined,
            defaultlayout:[],
            layoutNum: 12, // 共12个窗口
            thisNum: 4, // 当前4个窗口
            preset_bit_list: [], // 预置位列表
            preset_bit_lists: [], // 预置位分页列表255个
            editPresetBitData: [], // 获取已修改的预置位
            pageNum: 0, // 预置位分页数
            rtsp:false, // 是否rtsp取流
            preset_bit_val: "", // 预置位
            preset_bit_set_val: "",
            disabled: false,
            playbackLoadding: false, // 录像回放loadding
            playbackLists: [], // 录像回放列表
            control: {// 云台参数
                speed:5, // 云台转速
                aperture:0, // 光圈
                zoom:0, // 放缩
                focus:0, // 聚焦
                brightness: 1, // 亮度
                contrast: 1, // 对比度
                saturation: 1, // 饱和度
                cruise: 1, // 巡航
                inspection: 1, // 巡检
                assist: 1, // 辅助
                wiper: 0, // 雨刮
                videoProtocol: "udp",//视频协议，默认udp
                autoscan: false, // 自动扫描
                randomscan: false, // 随机扫描
                windowsHeat: 0, // 视窗加热
                pictureColor1: 1, // 色彩模式
                pictureResolution1: 1 // 分辨率
            },
            form: { // 历史回放
                playbackType: '1', // 位置
                captureTime: '' // 时间
            },
            download: undefined, // 录像文件下载
            thisHistoryVideo: {}, // 点击当前录像
            mouseDownAndUpTimer:null, // onmousedown onclick
            onMouseDownFlag:false, // onmousedown onclick
            stopingFlag:false,//停止中标记
            movingFlag:false,//移动中标记
            historyYuntaiObj: {} // 录像回放云台对象
        }
    },
    mounted() {
         window.addEventListener('beforeunload', e => this.beforeunloadFn());
    },
    created () {
        //预置位列表
        this.getPresetData();
        this.preset_bit_list = this.preset_bit_lists[0];
        // 默认四个视频窗口
        for(let i = 0;i<this.layoutNum;i++) {
            this.defaultlayout.push({yuntai:{},video:'',parent: [],perset: {},control: {}});
        }
        this.layout = this.defaultlayout.slice(0, this.thisNum);
        // 录像回放当前时间
        this.form.captureTime = this.parseTime(new Date(), '{y}-{m}-{d}');
    },
    methods: {
        speedChange() {
            // 步长 1-9 后端收到必需是11-99
            let speed2 = this.control.speed.toString() + this.control.speed.toString();
            this.remoteControl(30,speed2);
        },
        domain(src) {
            let domainSrc = src;
            return domainSrc;
        },
        dialogclose() {
            // 巡航close
            this.tableData = [];
            this.cruisequery.cruiseGroupNum = '1';
            this.cruisequery.chanelNum = '1';
            this.cruiseDisabled = true;
            this.cruiseLoading = false;

        },
        dialogclose2() {
            // 巡检close
            this.activeName = "0";
            this.inspectionData = [];
            this.strategyData = [];
            this.tacticsquery.groupId = '1';
            this.tacticsquery.chanelNum = "1";
            this.strategDisabled = true;
            this.argumentsDisabled = true;
            this.argumentLoading= false;
            this.tacticsLoading = false;
        },
        toggleSwitch(cmd,cmd2,param, status){
            // 自动扫描/随机扫描
            if(Object.keys(this.layout[this.thisvideo].yuntai).length<1) {this.$message("请选择设备");return;}
            remoteControl({cmd:status?cmd:cmd2, param, deviceCode: this.layout[this.thisvideo].yuntai.code}).then(response => {
                if(response.code == 200) {
                    this.$message({
                        type: "success",
                        message: "操作成功"
                    });
                }
            });
        },
        ccc(data) {
            if(data.node && data.node.length>0 && data.node[0].node && data.node[0].node.type == "yuntai") {
                if(data.node[0].node.state == 1) {

                    this.historyYuntaiObj = data.node[0].node; // 录像回放
                    this.defaultlayout[this.thisvideo].yuntai = data.node[0].node;
                    this.layout = this.defaultlayout.slice(0,this.thisNum);
                    // 要点击设备再获取视频
                    this.thisvideoP = this.thisvideo;
                    //     // 实时视频才触发
                    this.get_common_video();
                    this.get_presetPosition();
                    this.clearTimmer();
                    console.info("点击了"+data.node[0].node.code);
                    if(this.lastNode){//关闭上一个打开的设备，避免受上一个影响
                      historyvideo_stop({code: this.lastNode.code});
                      console.info("先清定时器，停止播放");
                      historyvideo_stop({code: this.lastNode.code}) .then(response => {
                      });
                      // 播放历史视频
                      this.thisHistoryVideo = {};//这两句也要写才关到定时器
                      //this.$nextTick 将回调延迟到下次DOM更新循环之后执行。在修改数据之后立即使用它，然后等待DOM更新

                    }
                    // 因后台不能返回步长的值到前端，所以前端每次拨乱反正，发指令55到设置
                    this.remoteControl(30,55);
                    this.control.speed = 5;
                    this.lastNode=data.node[0].node;
                } else {
                    this.$message("云台离线状态");
                }
            }
        },
        get_common_video () {
             // 获取视频路径
             this.control_loading = true;
            getVideoInfo({id:this.layout[this.thisvideoP].yuntai.id}).then(response => {
                if(response.code == 200) {
                    this.layout[this.thisvideoP].video = "";
                    this.control_loading = false;
                    this.$nextTick(()=>{
                        this.layout[this.thisvideoP].video = response.data.videoUrl;
                        // 获取亮度对比度，雨刮等参数
                        if(response.data.tFunctionParameters && Object.keys(response.data.tFunctionParameters).length>0) {
                            this.control.brightness = response.data.tFunctionParameters.brightness&&response.data.tFunctionParameters.brightness == ""?1:parseInt(response.data.tFunctionParameters.brightness); // 亮度
                            this.control.contrast = response.data.tFunctionParameters.contrast&&response.data.tFunctionParameters.contrast == ""?1:parseInt(response.data.tFunctionParameters.contrast); // 对比度
                            this.control.saturation = response.data.tFunctionParameters.saturation&&response.data.tFunctionParameters.saturation == ""?1:parseInt(response.data.tFunctionParameters.saturation); // 饱和度
                            this.control.wiper = response.data.tFunctionParameters.wiper; // 雨刮
                            this.control.windowsHeat = response.data.tFunctionParameters.windowsHeat; // 视窗加热
                            this.control.pictureColor1 = response.data.tFunctionParameters.pictureColor1; // 色彩模式(后端需求传回，防止影响参数实时配置的图像采集)
                            this.control.pictureResolution1 = response.data.tFunctionParameters.pictureResolution1; // 分辩率(后端需求传回，防止影响参数实时配置的图像采集)
                        }
                    });
                } else {
                    this.$message("该设备无法播放");
                    return;
                }
            });
        },
        clarityChange(data) {
            // 视频清晰度切换
            console.log(data)
        },
        layoutselect(num) {
            // 布局选择
            this.thisvideo = 0;
            this.thisNum = num;
            this.layout = this.defaultlayout.slice(0,num);
        },
        thisFullScreen() {
            // 点击全屏
            if(Object.keys(this.layout[this.thisvideo].yuntai).length>1) {
                let index = this.thisvideo;
                let videoPlayer = eval("this.$refs.videoplayer" +index)[0]
                videoPlayer.setFullScreen();
            } else {
                this.$message("无视频播放");
            }
        },
        videoselect(idx) {
            // 选中当前视频窗口
            this.thisvideo= idx;
            this.get_presetPosition();
        },

        sleep (time) {
           for(var t = Date.now();Date.now() - t <= time;);
        },
        stopAndMove(cmd, param) {
            if( this.stopingFlag||this.movingFlag){
                for(var i=0;i<100 && (this.stopingFlag ||this.movingFlag);i++){
                    this.sleep(20);
                }
                console.info("上次转动指令未执行完毕，本次推后执行。");
            }

            if(cmd == 48){
                this.stopingFlag = true;
            }
            if(cmd == 49 || cmd == 50 || cmd == 51 || cmd == 52){
                this.movingFlag = true;
            }

            this.remoteControl(cmd, param);
        },
        remoteControl(cmd, param) {
            // 发送远程控制命令
            if(Object.keys(this.layout[this.thisvideo].yuntai).length<1) {this.$message("请选择设备");return;}
            remoteControl({cmd, param, deviceCode: this.layout[this.thisvideo].yuntai.code}).then(response => {
                if(response.code == 200) {
                    console.log("远程连接ok");
                } else {
                    console.log("远程连接fail")
                }
                if(cmd == 48){
                    this.sleep(100);
                    this.stopingFlag = false;
                }

                if(cmd == 49 || cmd == 50 || cmd == 51 || cmd == 52){
                    this.sleep(100);
                    this.movingFlag = false;
                }
            });
        },
        gotouchstart(cmd, param) {
            // onmousestart
            let self = this;
            self.onMouseDownFlag = false;
            self.mouseupCC = true;
            self.mouseDownAndUpTimer = setTimeout(function(){
                self.stopAndMove(cmd, param);
                self.onMouseDownFlag = true;
            },200);
        },
        gotouchend(e) {
            e.stopPropagation();
            if(this.onMouseDownFlag){
                this.stopAndMove(48, 0);
                this.mouseupCC = false;
            }else{
                clearTimeout(this.mouseDownAndUpTimer); // 清除延迟时间
            }
        },
        omLeave() {
            setTimeout(()=>{
                // 鼠标移到按钮外触发事件
               if(this.onMouseDownFlag && this.mouseupCC){
                   this.stopAndMove(48, 0);
               }
               this.mouseupCC = false;
            },400)
        },
        ccclick(cmd,param) {
            // click
            if(!this.onMouseDownFlag) {
                this.remoteControl(cmd, param);
            }
        },
        clearTimmer() {
            // 清除计时
            clearTimeout(this.timmer);
            this.timmer = "";
        },
        getPresetData() {
            // 分页显示预置位
            let max = 20;
            let arrNum = global.PRESET_BIT_TOTAL % max == 0? global.PRESET_BIT_TOTAL / max : (Math.floor( global.PRESET_BIT_TOTAL/ max) + 1);
            for (let i = 0; i < arrNum; i++) {
                let newArry = [];
                for (let j = 1; j<=max; j++) {
                    let n = max * i;
                    let bit = i == 0? j : n + j;
                    if(bit <= global.PRESET_BIT_TOTAL)    {
                        let obj = {id: undefined, preset_bit: bit, set: false, showedit:false, name:"预置位" + bit,oldname:"预置位" + bit};
                        newArry.push(obj);
                    }
                }
                this.preset_bit_lists.push(newArry);
                this.editPresetBitData.push(newArry);
            }
        },
        getPresetList() {
            // 滚动加载分页预置位
            // if(this.preset_bit_val.length>0 && this.editPresetBitData.length< 1) {return;}
            if((parseInt( this.pageNum ) + 1 ) < parseInt(this.editPresetBitData.length)) {
                ++this.pageNum;
                this.preset_bit_list = this.preset_bit_list.concat(this.editPresetBitData[this.pageNum]);
            }
        },
        searchPresetBit () {
            // 查询预置位
            this.preset_bit_list = [];
            this.$refs.scroll.wrap.scrollTop = 0;
            this.pageNum = 0;
            if(this.preset_bit_val.length>0) {
                 for(let j = 0; j < this.editPresetBitData.length;j++)  {
                    for(let k = 0; k< this.editPresetBitData[j].length; k++) {
                        if(this.editPresetBitData[j][k].name.indexOf(this.preset_bit_val) != -1) {
                            this.preset_bit_list.push(this.editPresetBitData[j][k]);
                        }
                    }
                }
            } else {
                this.preset_bit_list = this.editPresetBitData[0];
            }
        },
        get_presetPosition() {
            // 获取预置位的设置信息
            if(Object.keys(this.layout[this.thisvideo].yuntai).length>0) {
                this.bit_loading = true;
                // 初始化
                this.preset_bit_list = [];
                this.editPresetBitData = [];
                this.editPresetBitData = JSON.parse(JSON.stringify(this.preset_bit_lists));
                get_presetPosition({yuntaiId: this.layout[this.thisvideo].yuntai.id}).then(response => {
                    if(response.code == 200) {
                        this.bit_loading = false;
                        if(response.rows && response.rows.length>0) {
                            this.pageNum = 0;
                            for(let i=0;i < response.rows.length;i++) {
                                for(let j = 0; j < this.editPresetBitData.length;j++)  {
                                    for(let k = 0; k< this.editPresetBitData[j].length; k++) {
                                        if(this.editPresetBitData[j][k].preset_bit == response.rows[i].presetBit) {
                                            this.editPresetBitData[j][k].name = response.rows[i].presetName;
                                            this.editPresetBitData[j][k].set = true;
                                            this.editPresetBitData[j][k].id = response.rows[i].id;
                                        }
                                    }
                                }
                            }
                        }
                        this.preset_bit_list = this.editPresetBitData[0];
                        this.$refs.scroll.wrap.scrollTop = 0;
                    }
                });
            };
        },
        add_presetPosition(obj, index) {
            // 添加预置位的设置信息
            add_presetPosition({yuntaiId: this.layout[this.thisvideo].yuntai.id, presetBit:obj.preset_bit, presetName: obj.name}).then(response => {
                if(response.code == 200) {
                    // 保存预置位信息--对设备
                    remoteControl({cmd:9, param:obj.preset_bit, deviceCode: this.layout[this.thisvideo].yuntai.code}).then(response => {
                        this.get_presetPosition();
                        this.$message({
                            message: response.msg,
                            type: 'success'
                        });
                    });
                }
            });
        },
        edit_presetPosition(obj, index) {
            // 编辑预置位的设置信息
            edit_presetPosition({id: obj.id, yuntaiId: this.layout[this.thisvideo].yuntai.id, presetBit:obj.preset_bit, presetName: obj.name}).then(response => {
                if(response.code == 200) {
                    this.$message({
                        message: response.msg,
                        type: 'success'
                    });
                    this.get_presetPosition();
                } else {
                    this.$message(response.msg);
                    return;
                }
            });
        },
        del_presetPosition(obj, index) {
            // 删除预置位的设置信息
            del_presetPosition(obj.id).then(response => {
                if(response.code == 200) {
                    obj.name = obj.oldname;
                    this.preset_bit_set_val = obj.oldname;
                    // 保存预置位信息--对设备
                    remoteControl({cmd:26, param:obj.preset_bit, deviceCode: this.layout[this.thisvideo].yuntai.code}).then(response => {
                        this.get_presetPosition();
                        this.$message({
                            message: response.msg,
                            type: 'success'
                        });
                    });
                }
            });
        },
        transferClick(item) {
            // 调用预置位
            this.thisPresetBit = item;
            this.remoteControl(2,item.preset_bit);
        },
        setdemonstration(obj) {
            // 设置亮度、对比度、饱和度、雨刮、视窗加热
            if(Object.keys(this.layout[this.thisvideo].yuntai).length<1) {this.$message("请选择设备");return;}
            obj['yuntaiId'] = this.layout[this.thisvideo].yuntai.id;
            obj['code'] = this.layout[this.thisvideo].yuntai.code;
            obj['pictureColor1'] = this.control.pictureColor1; // 色彩模式(后端需求传回，防止影响参数实时配置的图像采集)
            obj['pictureResolution1'] = this.control.pictureResolution1;// 分辩率(后端需求传回，防止影响参数实时配置的图像采集)
            // obj['id'] = this.layout[this.thisvideo].yuntai.id;
            edit_arameters(obj).then(response => {
                this.$message({
                    message: "设置成功",
                    type: 'success'
                });
            });
        },
        opencruiseset() {
            // 设置巡航弹框
            if(Object.keys(this.layout[this.thisvideo].yuntai).length<1) {this.$message("请选择设备");return;}
            this.dialogVisible=true;
        },
        openinspectionset() {
            // 设置巡检弹框
            if(Object.keys(this.layout[this.thisvideo].yuntai).length<1) {this.$message("请选择设备");return;}
            this.dialogVisible2=true;
        },
        get_arguments() {
            // 获取巡检参数配置信息
            this.argumentLoading = true;
            this.argumentsDisabled = true;
            this.inspectionData = [];
            get_arguments({chanelNum:this.tacticsquery.chanelNum,groupId: this.tacticsquery.groupId,code: this.layout[this.thisvideo].yuntai.code}).then(response => {
                if(response.code == 200) {
                    this.argumentLoading = false;
                    this.inspectionData = response.rows;
                    this.argumentsDisabled = false;
                }
            });
        },
        tacticsSetting(row) {
            // 设置巡检参数
            this.argumentLoading = true;
            this.argumentsDisabled = true;
            edit_arguments(row).then(response => {
                if(response.code == 200) {
                    this.argumentLoading = false;
                    this.argumentsDisabled = false;
                    this.$message({
                        message: "设置成功",
                        type: 'success'
                    });
                }
            });
        },
        addInspection() {
            // 新增巡检参数配置
            this.argumentLoading = true;
            this.argumentsDisabled = true;
            let num = parseInt(this.inspectionData.length) + 1;
            add_arguments({chanelNum:this.tacticsquery.chanelNum,inspectionTacticsId:num, groupId:this.tacticsquery.groupId,code: this.layout[this.thisvideo].yuntai.code}).then(response => {
                if(response.code == 200) {
                    this.argumentLoading = false;
                    this.argumentsDisabled = false;
                    this.$message({
                        message: "新增成功",
                        type: 'success'
                    });
                    this.get_arguments();
                }
            });
        },
        deleteArgumRow(row) {
            // 巡检参数配置删除
            this.argumentLoading = true;
            this.argumentsDisabled = true;
             del_arguments({deviceCode: this.layout[this.thisvideo].yuntai.code, groupNum:row.groupId, inspectIndex:row.inspectionTacticsId, channelnum:row.chanelNum}).then(response => {
                if(response.code == 200) {
                    this.argumentLoading = false;
                    this.argumentsDisabled = false;
                    this.get_arguments();
                    this.msgSuccess("删除成功");
                }
            });
        },
        setFormatTime(data) {
            if(parseInt(data) < 10) {
                return "0" + data;
            } else {
                return data;
            }
        },
        strategyQuery() {
            // 查询巡检策略
            this.tacticsLoading = true;
            this.strategDisabled = true;
            this.strategyData = [];
            get_tactics({code: this.layout[this.thisvideo].yuntai.code}).then(response => {
                // if(response.code == 200) {
                    this.tacticsLoading = false;
                    if(response.CmdB8HResponse && Object.keys(response.CmdB8HResponse).length>0&& response.CmdB8HResponse.strategySets && response.CmdB8HResponse.strategySets.length>0) {
                        // 增加编辑状态
                        for(let i = 0; i < response.CmdB8HResponse.strategySets.length;i++) {
                            response.CmdB8HResponse.strategySets[i]['show'] = false;
                            let h = response.CmdB8HResponse.strategySets[i].startHour?response.CmdB8HResponse.strategySets[i].startHour: 0,
                            m = response.CmdB8HResponse.strategySets[i].startMininue?response.CmdB8HResponse.strategySets[i].startMininue: 0,
                            s = response.CmdB8HResponse.strategySets[i].startSecond?response.CmdB8HResponse.strategySets[i].startSecond: 0
                            response.CmdB8HResponse.strategySets[i].beginTime = this.setFormatTime(h) + ":" +this.setFormatTime(m) + ":" + this.setFormatTime(s);
                        }
                       this.strategyData = response.CmdB8HResponse.strategySets;
                       this.inspectionsSetNum = response.CmdB8HResponse.inspectionsSetNum;
                    }
                    this.strategDisabled = false;
                // }
            });
        },
        strategyAdd() {
            // 新增巡检策略
            let obj = {groupNum:'1',enable :0,inspectTimes:1,beginTime:'00:00:00',show:false}
            this.strategyData.push(obj);
        },
        addcruise() {
            // 新增巡航
            let obj = {code:this.layout[this.thisvideo].yuntai.code,presetNum:1, residenceTime:1,speed:1, chanelNum:this.cruisequery.chanelNum, cruiseGroupNum: this.cruisequery.cruiseGroupNum}
            add_cruise(obj).then(response => {
                if(response.code == 200) {
                    this.$message({
                        type: "success",
                        message: "添加成功"
                    });
                    this.cruiseclick();
                } else {
                    this.$message(response.msg);
                }
            });
        },
        cruiseclick() {
            // 查询巡航
            this.cruiseLoading = true;
            this.cruiseDisabled = true;
            this.tableData = [];
             get_cruise({chanelNum:this.cruisequery.chanelNum, cruiseGroupNum: this.cruisequery.cruiseGroupNum, code: this.layout[this.thisvideo].yuntai.code}).then(response => {
                if(response.code == 200) {
                    this.cruiseLoading = false;
                    this.cruiseDisabled = false;
                    if(response.rows.length>0) {
                        // 增加编辑状态
                        for(let i = 0; i < response.rows.length;i++) {
                            response.rows[i]['show'] = false;
                        }
                        this.tableData = response.rows;
                    }
                }
            });
        },
        editCruiseRow(row) {
            // 编辑巡航
            this.cruiseLoading = true;
            this.cruiseDisabled = true;
            edit_cruise({code: this.layout[this.thisvideo].yuntai.code, cruiseGroupNum:row.cruiseGroupNum, id:row.id, chanelNum:row.chanelNum,presetNum:row.presetNum,speed:row.speed,residenceTime:row.residenceTime}).then( response => {
                for(let i = 0; i<this.tableData.length;i++) {
                    for(let key in this.tableData[i]) {
                        if(typeof(this.tableData[i][key]) == 'undefined') {
                            this.$message("提交表单不能有空值");
                            return;
                        }
                    }
                }
                if(response.code == 200) {
                    this.cruiseDisabled = false;
                    this.cruiseLoading = false;
                    this.$message({
                        type: "success",
                        message: "操作成功"
                    });
                    this.cruiseclick();
                } else {
                    this.$message(response.msg);
                }
            });
        },
        deleteCruiseRow(row) {
            // 删除巡航
            this.cruiseDisabled = true;
            this.cruiseLoading = true;
            del_cruise({deviceCode: this.layout[this.thisvideo].yuntai.code, groupNum:row.cruiseGroupNum, pointIndex:row.id, channelnum:row.chanelNum,preset:row.presetNum,speed:row.speed,stayTime:row.residenceTime}).then(response => {
                if(response.code == 200) {
                    this.cruiseDisabled = false;
                    this.cruiseLoading = false;
                    this.cruiseclick();
                    this.msgSuccess("删除成功");
                }
            });
        },
        deleteRow(index, rows) {
            // 巡航/巡检策略本地删除
            rows.splice(index, 1);
        },
        timeToSec(time) {
            if(time.length<1) return;
            if(time.toString().indexOf(":") != -1) {
                let ary = time.split(':');
                let hour = ary[0], min = ary[1], sec = ary[2];
                let s = Number(hour * 3600) + Number(min * 60) + Number(sec);
                return s * 1000;
            } else {
                return time;
            }
        },
        strateSet () {
            // 设置巡检策略
            let newArray = [];
            this.strategDisabled = true;
            for(let i = 0; i<this.strategyData.length;i++) {
                for(let key in this.strategyData[i]) {
                    if(typeof(this.strategyData[i][key]) == 'undefined') {
                        this.$message("提交表单不能有空值");
                        return;
                    }
                }
                let startHour=this.strategyData[i].beginTime.split(':')[0],startMininue=this.strategyData[i].beginTime.split(':')[1], startSecond=this.strategyData[i].beginTime.split(':')[2];
                // 重构数据
                let obj = {groupNum:this.strategyData[i].groupNum,enable:this.strategyData[i].enable || this.strategyData[i].enable == 1?1:0,inspectTimes:this.strategyData[i].inspectTimes,startHour,startMininue,startSecond}
                newArray.push(obj);
            }
            add_tactics({channelNo:this.tacticsquery.chanelNum,inspectionGroupNum:"1", password:"1234",strategySetNum:"1", strategySets:newArray}, this.layout[this.thisvideo].yuntai.id).then(response => {
                if(response.code == 200) {
                    // 设备反应慢，不查
                    // this.strategyQuery();
                    this.strategDisabled = false;
                    this.$message({
                        type: "success",
                        message: "设置成功"
                    });
                }
            });
        },
        searchPlayBack() {
            console.info("搜索录像");
            // 查询录像
            if(Object.keys(this.historyYuntaiObj).length<1) {this.$message("请选择设备");return;}
            this.playbackLoadding = true;
            var self = this;//.playbackLists = response.rows
            if(this.form.playbackType == 1) {
                let obj = {captureTime:this.form.captureTime,code: this.layout[this.thisvideo].yuntai.code,presetBit:0}
                videoPlayback(obj).then(response => {
                    if(response.code == 200) {
                        self.playbackLists = response.rows;
                        console.info("取消所有选中项！");
                        this.download=-1;
                     /* self.$nextTick(()=>{
                         self.$refs.playbackLists.children.each(function (ele) {
                            ele.checked=false;
                          });
                       });*/

                       // this.thisHistoryVideo = this.playbackLists[this.download];
                        self.playbackLoadding = false;
                    }
                });
            } else if (this.form.playbackType == 2) {
                let obj = {captureTime:this.form.captureTime,yuntaiId: this.layout[this.thisvideo].yuntai.id}
                get_history_video(obj).then(response => {
                    if(response.code == 200) {
                        if(response.rows.length>0) {
                            for(let i = 0; i < response.rows.length; i++) {
                                response.rows[i]['videoname'] = this.getHistoryVideo(response.rows[i].videoUrl);
                            }
                        }
                        this.playbackLists = response.rows;
                    // this.thisHistoryVideo = this.playbackLists[this.download];
                        this.playbackLoadding = false;
                    }
                })
            }
        },
        downPlayBack() {
            // 下载录像
            if(Object.keys(this.thisHistoryVideo).length<1) {this.$message("请选择录像列表中的视频进行下载！");return;}
        },
        downloadUrlFile(url, fileName) {
            const url2 = url.replace(/\\/g, '/');
            const xhr = new XMLHttpRequest();
            xhr.open('GET', url2, true);
            xhr.responseType = 'blob';
            //xhr.setRequestHeader('Authorization', 'Basic a2VybWl0Omtlcm1pdA==');
            // 为了避免大文件影响用户体验，建议加loading
            xhr.onload = () => {
                if (xhr.status === 200) {
                    // 获取文件blob数据并保存
                    saveAs(xhr.response, fileName);
                }
            };
            xhr.send();
        },
        saveAs(data, name) {
            const urlObject = window.URL || window.webkitURL || window;
            const export_blob = new Blob([data]);
            const save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
            save_link.href = urlObject.createObjectURL(export_blob);
            save_link.download = name;
            save_link.click();
        },
        getHistoryVideo(name) {
            let n = name.replace(/(.*\/)*([^.]+).*/ig,"$2");
            return n;
        },
        tabb(index) {
            // 实时视频和录像回放切换
            this.tab = index;
            if(index == 0 && Object.keys(this.historyYuntaiObj).length>0) {
                // 录像回放停止播放
                sendStopCmd({deviceCode :this.historyYuntaiObj.code}).then(response => {
                    if(response.code == 200) {
                        console.log(this.historyYuntaiObj.code + '停止播放')
                    }
                })
            } else if (index == 1 && Object.keys(this.layout[this.thisvideo]).length>0) {
                // 实时视频停止视频播放
                this.beforeunloadFn();
            }
        },
        /**
         * 前端中心的切换
         **/
        playbackchange() {
            // 录像回放切换 前端+中心
            this.playbackLists = [];
            this.thisHistoryVideo = {};
            console.info("先清定时器，停止播放，再播放");
            clearTimeout(this.timmer);
            historyvideo_stop({code: this.historyYuntaiObj.code}) .then(response => {
              /*if(response.code == 200) {
                console.log(this.historyYuntaiObj.code + '停止播放')
              }*/
            });

        },
        /***
         * 文件的切换
         ***/
        historyPlay(value) {
          //先关闭，防止前面的影响
          //停止定时器
          console.info("先清定时器，停止播放，再播放");
          clearTimeout(this.timmer);
          historyvideo_stop({code: this.historyYuntaiObj.code}) .then(response => {
            /*if(response.code == 200) {
              console.log(this.historyYuntaiObj.code + '停止播放')
            }*/
          });
         // console.info("新的值："+value);

           /* var eles=document.getElementsByName("playbackfiles");
          for (let i = 0; i < eles.length; i++) {

            var ele=eles[i];
          }*/
            // 播放历史视频
            this.thisHistoryVideo = {};
            //this.$nextTick 将回调延迟到下次DOM更新循环之后执行。在修改数据之后立即使用它，然后等待DOM更新
            this.$nextTick(()=>{
                this.thisHistoryVideo = this.playbackLists[this.download];
            });
        },
        beforeunloadFn() {
            // 页面销毁，把所有设备停止播放
            for(let i = 0;i < this.layout.length;i++) {
                if(this.layout[i].video.length>0) {
                    sendStopCmd({deviceCode :this.layout[i].yuntai.code}).then(response => {
                        if(response.code == 200) {
                            console.log(this.layout[i].yuntai.code + '停止播放')
                        }
                    })
                }
            }
            // 历史回放
            if(Object.keys(this.historyYuntaiObj).length>0 && this.tab == 1) {
                historyvideo_stop({code: this.historyYuntaiObj.code}) .then(response => {
                    if(response.code == 200) {
                        console.log(this.historyYuntaiObj.code + '停止播放')
                    }
                });
            }
        }
    },
    destroyed() {
        this.beforeunloadFn();
    },
}
</script>
<style lang="scss" scoped>
.app-container{height:$base-app-main-height;color:$base-font-color;
    ::v-deep{
        .el-card,.el-card__body{height:100%;}
        .el-radio{margin-right:10px;}
        .el-radio__input{transform: scale(0.8);}
        .el-radio__label{font-size:$base-font-size-small;padding-left:5px;}
        .el-switch__label.is-active{color:$base-font-color;}
        .el-switch__label *{font-size:$base-font-size-small;color:$base-font-color;}
        .el-radio-button__orig-radio:checked + .el-radio-button__inner{background:$base-color-blue2;border-color:$base-color-blue2;}
    }
   .pictureInfo{display:flex;height:100%;overflow:hidden;position: relative;
        .left{height:100%;width:calc(100% - #{$base-right-width});display:flex;flex-direction: column;
            .boxs{display: grid;grid-auto-flow: row dense;flex:1;margin:-1px;overflow:hidden;
                .item {margin:1px;display:flex; flex-direction: column;background:#000;cursor: pointer;
                    &.act{
                        .top{background:$base-color-blue2;}
                    }
                    .top{font-size:$base-font-size-small;background:#f2f2f2;color:#fff;height:25px;background:$base-font-color;line-height:25px;text-align:center;}
                    video{width:100%;height:100%;}
                    .videopp{flex:1;overflow: hidden;}
                }
            }
            .boxs-1{grid-template-columns: 100%;grid-template-rows: 100%;}
            .boxs-4{grid-template-columns: 50% 50%;grid-template-rows: 50% 50%;}
            .boxs-6{grid-template-columns: repeat(3, 33.33%);grid-template-rows: repeat(3, 33.33%);
                .item-0 {grid-column-start: 1;grid-column-end: 3;grid-row-start: 1;grid-row-end: 3;}
            }
            .boxs-9{grid-template-columns: repeat(3, 33.33%);grid-template-rows: repeat(3, 33.33%);}
            .boxs-12{grid-template-columns: repeat(3, 33.33%);grid-template-rows: repeat(4, 25%);}
            .boxs-15{grid-template-columns: repeat(3, 33.33%);grid-template-rows: repeat(5, 20%);}
            .layoutbox{margin-top:$base-padding10;
                .videoSource{background:$base-color-blue4;display:flex;padding:8px $base-padding10;
                    span{font-size:$base-font-size-small;color:$base-font-color;}
                    .tools{margin-left:auto;display:flex;align-items:center;
                    }
                }
                .split-wrapper{display: flex;align-items:center;padding:5px $base-padding10;
                    .fullscreen{color:$base-border-color;font-size:26px;margin-left: $base-padding10;cursor: pointer;&.act{color:$base-color-blue2;}}
                    .layOut-item{width:30px;height:30px;margin-left: $base-padding10;cursor: pointer;
                        &.act,&:hover{td{border-color:$base-color-blue2!important; background:$base-color-blue2!important;&.before:before{color:#fff!important;}}}
                        .split-table{table-layout: fixed;width: 100%;height: 100%;border-spacing: 1px;
                            tr{width: 100%;
                                td{border: 1px solid $base-border-color;box-sizing: border-box;background:transparent;position: relative;
                                    &.before:before{content:attr(title);position:absolute;top:0;left:2px;transform-origin: top left;font-size:12px;transform: scale(0.6);color:$base-font-color;}
                                }
                            }
                        }
                    }
                    .controlboard{margin-left:auto;color:$base-font-color;display:flex;align-items:center;
                        span{display:inline-block;margin-right:5px;}
                        .layout{font-size:20px;color:$base-font-color;}
                    }
                }
            }
        }
        .right{width:$base-right-width;position: absolute;top:0px;right:0;bottom:0;background: #fff;transition: width 0.2s;padding-left:0px;
            .rightbox{width:100%;height:100%;padding-left:5px;overflow:hidden;display:flex; flex-direction: column;
                .tabs{height:35px;background:$base-color-blue2;display:flex; align-items: flex-end;padding:0 5px;
                    .item{width:80px;height:28px;line-height:28px;font-size:12px;text-align:center;color:#fff;cursor: pointer;position: relative;
                        &.act{background:#fff;color:$base-font-color;box-shadow: 0 0px 5px rgba(0,0,0,0.15);border-top-left-radius: 3px;border-top-right-radius: 3px;}
                    }
                }
                .content{padding: $base-padding10 0;display:flex; flex-direction: column;flex:1;overflow:hidden;
                    .bit_lists{padding:$base-padding10 0 5px;flex:1;overflow:hidden;
                        .item{display:flex;align-items:center;padding:5px 10px 5px;color:$base-font-color;position: relative;
                            &.set{i.el-icon-video-camera,span{color:$base-color-blue2;}}
                            .edit_bit{font-size:12px;position: absolute;width:90px;left:32px;::v-deep{.el-input__inner{padding-left:5px;}}}
                            i.el-icon-video-camera{font-size: 18px;color:$base-font-color;}
                            span{display:block;overflow:hidden;flex:1;text-overflow: ellipsis;white-space: nowrap;line-height: 12px;font-size:12px;padding-left:5px;}
                            .tools{display:flex;align-items:center;margin-left:auto;
                                i{padding:0;outline:none;font-size:16px;margin-left:5px;cursor: pointer;color:$base-border-color;&:hover{color:$base-color-blue3;}}
                                i.el-icon-delete{color:$base-danger-color;}
                                i.el-icon-check{color:$base-color-blue2;}
                                i.el-icon-close{color:$base-green-color;}
                            }
                        }
                    }
                    .yuntaicontrol{flex-shrink: 0;padding:0px 5px 0 5px;border-top:1px solid $base-border-color;height:320px;
                        h4{padding: $base-padding10 0px;margin:0;color:$base-font-color;font-weight: normal;font-size:$base-font-size-small;}
                        ::v-deep{.el-radio-button--mini .el-radio-button__inner{padding-left:11px;padding-right:12px;}}
                        .plane{padding-top:$base-padding10;text-align:center;width:100%;margin:0 auto;
                            .circleDirect{width:120px;height:120px;margin:0 auto;
                                ::v-deep{.el-button--mini{font-size:16px;padding:0;width:32px;height:32px;display:flex;align-items: center;justify-content: center;}}
                                .item{margin:2.5px 0;
                                    ::v-deep{
                                        .lt45{i{transform: rotate(-45deg);}}
                                        .rt45{i{transform: rotate(45deg);}}
                                        .lb45{i{transform: rotate(45deg);}}
                                        .rb45{i{transform: rotate(-45deg);}}
                                    }
                                }
                            }
                            ::v-deep{
                                // el-silder
                                .el-slider{flex:1;padding-left:20px;margin: 0 auto;}
                                .el-input-number__decrease,.el-input-number__increase{display: none;}
                                .el-slider__runway.show-input{margin-right:50px;}
                                .el-input-number{width:40px;}
                                .el-input__inner{padding-left:5px;}
                            }
                            .controlitem{margin: 10px 0;
                                .el-button{background:$base-color-blue2;border-color:$base-color-blue2;color:#fff;padding:4px;}
                                span{display: inline-block;padding:0 15px;}
                            }
                            .speed{
                                ::v-deep{.el-input__inner{padding:0px;}}
                            }
                            .picturebox-item{display:flex;align-items:center;
                                span{white-space: nowrap;width:50px;text-align:right;}
                            }
                            &.picturebox{
                                .tools{padding-top:10px;
                                    .el-button{background:$base-color-blue2;border-color:$base-color-blue2;}
                                    .el-button:last-child{background-color:$base-color-green;}
                                }
                            }
                            .inspection-item{display:flex;align-items:center;margin:10px 0;
                                ::v-deep{.el-switch>.el-switch__label--left>span{width:50px;display:inline-block;text-align:right;}}
                                span{white-space: nowrap;margin-right:5px;}
                                .el-button{&:first-child{background:$base-color-blue2;}&:last-child{margin-left:0;}transform: scale(0.8);transform-origin: right;}
                                .el-input{margin:0 5px 0 10px;}
                                p.bbttn{cursor:pointer;margin-left:3px;background:#66b1ff;border-radius: 2px;padding:3px 5px;font-size:12px;color:#fff; white-space: nowrap;
                                    font{transform: scale(0.9);display: inline-block;}
                                    &.red{background:#f78989;}
                                }
                                &.mt10{margin-top:25px;}
                                ::v-deep{
                                    .el-input-number__decrease,.el-input-number__increase{display: none;}
                                    .el-input-number .el-input__inner{padding:0 5px;text-align: left;}
                                    .el-input-number{width:100%!important;}
                                }
                            }
                        }
                    }
                    .playback-container{width:100%;margin:0 auto;display:flex; flex-direction: column;height:100%;padding-left:5px;
                        // .el-button{background:$base-color-blue2;border-color:$base-color-blue2;}
                        // .el-button:last-child{background-color:$base-color-green;}
                        // .el-checkbox{margin-right:12px;}
                        // ::v-deep{.el-checkbox__label{font-size:12px;}.el-form-item--mini .el-form-item__label{line-height:12px;font-size:12px;}}
                        ::v-deep{.el-date-editor{width:168px;}.el-form-item__label{flex-shrink: 0;line-height:32px;margin-right:5px;padding: 0;}.el-form-item{display:flex;align-items: center;}}
                        .lists{padding:0px 5px 0 5px;border-top:1px solid $base-border-color;flex:1;display:flex;overflow:hidden; flex-direction: column;
                            h4{padding: $base-padding10 0px;margin:0;color:$base-font-color;font-weight: normal;font-size:$base-font-size-small;}
                            .el-radio{margin:4px 0;}
                        }
                    }
                }
            }
        }
    }
    .dialogform{
        ::v-deep{
            .el-input-number__decrease,.el-input-number__increase{display: none;}
            .el-input-number .el-input__inner{padding:0 15px;text-align: left;}
            .el-input-number{width:100%!important;}
        }
    }
}
</style>
