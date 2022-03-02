<template>
  <!-- 树结构 -->
  <div class="treeContainer">
    <div class="left">
      <div class="treeComponent">
        <div class="topbox" v-if="fatherName != 'drone'">
          <h3>设备列表</h3>
          <div class="tools">
            <svg-icon slot="prefix" icon-class="filter" :class="['el-input__icon input-icon',{act:fillterMenu}]" @click="fillterMenu=!fillterMenu;" />
            <svg-icon slot="prefix" icon-class="refesh" class="el-input__icon input-icon" @click.stop="refresh" />
          </div>
          <el-drawer class="drawerbox" :visible.sync="fillterMenu" direction="ttb" :withHeader="false" size="100%" :show-close="false" :modal="false" :modal-append-to-body="false">
            <div class="drawerlist">
              <div class="item" v-for="(e,i) in device" :key="i">
                <span class="title">{{e.name}}:</span>
                <span :class="{act: e.act == -100}" @click="filter(e,-100)">全部</span>
                <span v-for="(ee,ii) in e.data" :key="ii" :class="{act: e.act == ii}" @click="filter(e,ii)">{{ee.dictLabel}}</span>
              </div>
            </div>
            <div class="btns">
              <el-button size="mini" icon="el-icon-refresh" @click="resizefilter">重置</el-button>
              <el-button type="primary" size="mini" icon="el-icon-search" @click.stop="getlist(filterdata);input3='';fillterMenu=false;">查询</el-button>
            </div>
          </el-drawer>
        </div>
        <div class="searchbox" v-if="fatherName != 'drone'">
          <el-input placeholder="请输入关键字" size="small" v-model="input3" class="input-with-select">
            <el-select v-model="select" slot="prepend" placeholder="请选择" @change="filterdata[options[0].key] = undefined;filterdata[options[1].key] = undefined;filterdata[options[2].key] = undefined;filterdata[options[3].key] = undefined;">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
            <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
          </el-input>
        </div>
        <div class="treebox">
          <el-tabs v-model="activeName" type="card" @tab-click="handleClick" class="eltab">
            <el-tab-pane label="线路视图" name="0" v-if="fatherName != 'drone'">
              <el-tree v-if="activeName == 0" :default-expanded-keys="current" :data="line_tree_data" node-key="key" accordion @node-click="handleNodeClick" highlight-current ref="tree">
                <span class="custom-tree-node" slot-scope="{data}">
                  <span>
                    <svg-icon :icon-class="data.type=='yuntai'?data.monitorType==1?'bolt':'yuntaiIcon':data.icon" :class="['treeicon',data.state && data.state == -1?'hui':data.state && data.state == 1 || data.state == 0?'green':'']"></svg-icon>{{ data.name }} <template v-if="data.type != 'yuntai'"> ({{data.online}}/{{data.total}})</template>
                  </span>
                </span>
              </el-tree>
            </el-tab-pane>
            <el-tab-pane label="机构视图" name="1"  v-if="fatherName != 'drone'">
              <el-tree v-if="activeName == 1" :default-expanded-keys="current" :data="dept_tree_data" node-key="key" accordion @node-click="handleNodeClick" highlight-current ref="tree">
                <span class="custom-tree-node" slot-scope="{data}">
                  <span>
                    <svg-icon :icon-class="data.type=='yuntai'?data.monitorType==1?'bolt':'yuntaiIcon':data.icon" :class="['treeicon',data.state && data.state == -1?'hui':data.state && data.state == 1 || data.state == 0?'green':'']"></svg-icon>{{ data.name }} <template v-if="data.type != 'yuntai'"> ({{data.online}}/{{data.total}})</template>
                  </span>
                </span>
              </el-tree>
            </el-tab-pane>
            <el-tab-pane label="机场" name="2">
              <el-tree v-if="activeName == 2" :default-expanded-keys="current" :data="airPort_tree_data" node-key="key" accordion @node-click="handleNodeClick" highlight-current ref="tree">
                <span class="custom-tree-node" slot-scope="{data}">
                  <span>
                    <span>
                      <svg-icon :icon-class="data.type=='yuntai'?data.monitorType==1?'bolt':'yuntaiIcon':data.icon" :class="['treeicon',data.state && data.state == -1?'hui':data.state && data.state == 1 || data.state == 0?'green':'']"></svg-icon>{{ data.airportNames }}
                    </span>
                  </span>
                </span>
              </el-tree>
            </el-tab-pane>
          </el-tabs>

        </div>
      </div>
    </div>
    <div class="right">
      <slot></slot>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import { getTreeLine, getTreeDept, getTreeAirport, socketUserLogin, SOCKET_SERVER } from '@/api/senxu/devicemanager/line'
import io from "socket.io-client"
export default {
  name: "treeContainer",
  props: {
    fatherName: {
      default: '',
      type: String
    }
  },
  data () {
    return {
      treeshow: true,
      fillterMenu: false,
      filterdata: {}, // 下拉筛选数据
      treePointData: [], // 筛选出来的地图数据
      nodedata: [],
      timmer: '',
      input3: '',
      select: '0',
      activeName: '0',
      options: [{ value: '0', label: '云台名称', key: 'yuntaiName' }, { value: '1', label: '云台Code', key: 'yuntaiCode' }, { value: '2', label: '杆塔名称', key: 'towerName' }, { value: '3', label: '杆塔Code', key: 'towerCode' }],
      device: [
        {
          name: "设备厂商",
          key: 'factory',
          keyP: 'factory',
          act: -100,
          data: {}
        },
        {
          name: "监测类型",
          key: 'monitor_type',
          keyP: 'monitorType',
          act: -100,
          data: {}
        },
        {
          name: "安装状态",
          key: 'install_state',
          keyP: 'installState',
          act: -100,
          data: {}
        },
        {
          name: "在线状态",
          key: 'state',
          keyP: 'state',
          act: -100,
          data: {}
        },
        {
          name: "上图状态",
          key: 'image_status',
          keyP: 'imageStatus',
          act: -100,
          data: {}
        }
      ],
      line_tree_data: [], // 线路
      dept_tree_data: [], // 机构
      airPort_tree_data: [], // 机场
      parentList: [],
      current: [],
      treeId: '',
      yuntais: [],
      total: {}, // 统计
      num: 0,
      tag: "",// 点击的标签
      websockData: [],
      socket:null
    }
  },
  computed: {
    ...mapGetters([
      'dicts'
    ])
  },
  created () {
    this.init();
  },
  mounted () {

  },
  methods: {
    createWebSocket (Token) {
      const url = SOCKET_SERVER + '/api/v2'
      this.socket = io(url, {
        query:
          { token: Token }
      })
      this.socket.connect
      this.socket.emit("status", { action: "start" })
      this.socket.on("status", (data) => {
        this.websockData = JSON.stringify(data);
        this.$emit('socketData', this.websockData)
      })
      this.socket.on("error", (msg) => {
        console.log(JSON.stringify(msg))
      })
    },
    getTreeLine () {
      // 获取线路
      getTreeLine(this.filterdata).then(res => {
        if (res.code == 200 && res.tree && res.tree.length > 0) {
          this.getTowerPoint(res.tree, 0);
          this.line_tree_data = res.tree;
          this.total = res.total;
          if (this.activeName == 0) {
            this.getRoleTreeRootNode(this.line_tree_data);
          }
        }
      });
    },
    getTreeDept () {
      // 获取部门
      getTreeDept(this.filterdata).then(res => {
        if (res.code == 200 && res.tree && res.tree.length > 0) {
          this.getTowerPoint(res.tree, 1);
          this.dept_tree_data = res.tree;
          this.total = res.total;
          if (this.activeName == 1) {
            this.getRoleTreeRootNode(this.line_tree_data);
          }
        }
      });
    },
    async getTreeAirport () {
      var that = this;
      await socketUserLogin().then(res => {
        if (res.token) {
          this.createWebSocket(res.token)
          // 获取机场
          setTimeout(() => {
            getTreeAirport(that.filterdata).then(res => {
              console.log(JSON.parse(that.websockData), 'websockDataa');
              if (res.code == 200 && res.tree && res.tree.length > 0) {
                this.$emit('uavRoadList', res.tRoute)
                res.tree.forEach(item => {
                  item.type = 'jichao'
                  item.id = (Math.random() * (1 - 100) + 100);
                  item.children.forEach(ite => {
                    ite.id = (Math.random() * (1 - 100) + 100);
                    item.airportNames = item.airportName;
                    console.log(JSON.parse(that.websockData).stationarys, ' JSON.parse(that.websockData).stationarys');
                    ite.point = [ite.locationLon, ite.locationLat];
                    JSON.parse(that.websockData).stationarys.forEach(web => {
                      if (web.id == ite.airportCode) {
                        console.log(web.devices.airport, 'web.devices.airport');
                        ite.point = [web.devices.airport.vehicular_RTK.lon, web.devices.airport.vehicular_RTK.lat]
                      }
                    })
                    // ite.point = ["114.45791992400473", "30.03700717799722"];//接口暂无经纬度数据，模拟位置
                    ite.children = [ite.children];
                    ite.children.forEach(i => {
                      i.type = "jichao"
                      i.id = (Math.random() * (1 - 100) + 100);
                      i.airportNames = i.uavId;
                      i.airportNames = i.uavId;
                      JSON.parse(that.websockData).stationarys.forEach(web => {
                        if (web.id == ite.airportCode) {
                          i.point = [web.devices.drone.status.physicDrone.location.lon, web.devices.drone.status.physicDrone.location.lat]
                        }
                      })
                    })
                    if (ite.type == 1) {
                      ite.airportNames = '固定机场'
                    } else {
                      ite.airportNames = '胶囊机场'
                    }
                  })
                })
                console.log(res.tree, 'tree----');
                that.getTowerPoint(res.tree, 2);
                that.airPort_tree_data = res.tree;
                if (that.activeName == 2) {
                  that.getRoleTreeRootNode(that.airPort_tree_data);
                }
              }
            });
          }, 500)
        }
      })
    },
    init () {


      // 树形默认第一个
      let that = this;
      that.device[0].data = that.dicts.oem && that.dicts.oem.obj ? that.dicts.oem.obj : '';
      that.device[1].data = that.dicts.monitor_type && that.dicts.monitor_type.obj ? that.dicts.monitor_type.obj : '';
      that.device[2].data = that.dicts.install_state && that.dicts.install_state.obj ? that.dicts.install_state.obj : '';
      that.device[3].data = that.dicts.state && that.dicts.state.obj ? that.dicts.state.obj : '';
      that.device[4].data = that.dicts.image_status && that.dicts.image_status.obj ? that.dicts.image_status.obj : '';
      // 如果是无人机巡检，则直接显示机场，其余不显示
      if (this.fatherName == 'drone') {
        this.activeName = '2';

        this.getTreeAirport();
      } else {
        this.getTreeLine();
        this.getTreeDept();
        this.getTreeAirport();
      }
    },
    getTowerPoint (data, index) {
      for (let i in data) {
        data[i]['key'] = data[i].type + '_' + data[i].id;
        if (data[i].children && data[i].children.length > 0) {
          this.getTowerPoint(data[i].children);
        }
      }
    },
    filter (item, id) {
      // 筛选点击
      item.act = id;
      this.filterdata[item.keyP] = id;
      if (id == -100) {
        delete this.filterdata[item.keyP];
      }
    },
    resizefilter () {
      if (this.device.length < 1) { return; }
      // 重置筛选
      for (let i = 0; i < this.device.length; i++) {
        this.device[i].act = -100;
      }
    },
    handleClick () {
      this.current = [];
      let data = this.activeName == 0 ? this.line_tree_data : this.activeName == 1 ? this.dept_tree_data : this.airPort_tree_data;
      this.getRoleTreeRootNode(data);
    },
    handleNodeClick (data, node, obj) {
      this.getCc(node);
    },
    getCc (node) {
      // 树节点
      let that = this;
      that.parentList = [];
      that.treePointData = [];
      that.current = [];
      that.nodedata = [];
      that.yuntais = [];
      that.parents(node);
      that.resetTreeData(node);
      that.getYuntai([node.data]);
      this.tag = node.data.key;
      // // 传递数据   
      // if(this.yuntais.length<1) {this.$message("没有云台设备");}
      that.parentList.push({ id: node.data.id, name: node.data.name, type: node.data.type }); // 插入当前节点
      that.$emit('ceshi', { node: that.nodedata, parent: that.parentList, total: that.total });
      if (node.data.uavId) {
        this.$emit('currentNode', node)
      }
      if (node.data.type == 'jichao' && !node.data.fcId) {
        this.$emit('airportData', node.data)
      }
    },
    resetTreeData (node) {
      // console.log(node, 'treeNode');
      if (node && node.data && node.data.type) {
        if (node.data.type == "tower") {
          this.nodedata.push(this.getTowerData2(node.data));
        } else if (node.data.type == "yuntai") {
          node.data.point = node.parent.data.point;
          node.data.lineId = node.parent.parent.data.id;
          // 当点云台时，yuntais也被赋当前值
          this.nodedata = [{ node: node.data, towers: [], yuntais: [node.data] }];
        } else {
          let newArry = [];
          newArry.push(node.data);
          this.nodedata = [{ node: node.data, towers: [], yuntais: [node.data] }];
          this.getLineData(newArry);
        }
      }
    },
    getLineData (data) {
      if (data.length < 1) { return; }
      for (let i = 0; i < data.length; i++) {
        if (data[i].type == "line") {
          this.nodedata.push(this.getTowerData(data[i]));
        }
        if (data[i].children && data[i].children.length > 0) {
          this.getLineData(data[i].children);
        }
      }
    },
    getTowerData2 (data) {
      let obj = { node: {}, towers: [], yuntais: [] }
      obj.node = data;
      if (data.children && data.children.length > 0) {
        // 把杆塔的point设到云台上
        for (let i = 0; i < data.children.length; i++) {
          data.children[i].point = data.point;
          obj.yuntais.push(data.children[i]);
        }
      }
      return obj;
    },
    getTowerData (data) {
      let obj = { node: {}, towers: [], yuntais: [] }
      obj.node = data;
      if (data.children && data.children.length > 0) {
        for (let i = 0; i < data.children.length; i++) {
          if (data.children[i].point && data.children[i].point[0].length > 0 && data.children[i].point[1].length > 0) {
            obj.towers.push(data.children[i]);
          }
          if (data.children[i].children && data.children[i].children.length > 0) {
            obj.yuntais.push(this.getYuntaiData(data.children[i]));
          }
        }
      }
      return obj;
    },
    getYuntaiData (data) {
      if (data.children && data.children.length > 0) {
        for (let i = 0; i < data.children.length; i++) {
          data.children[i].point = data.point;
          return data.children[i];
        }
      }
    },
    parents (node) {
      if (!node) { return; }
      if (node.parent && node.parent.level == 0) { return; }
      this.parentList.unshift({ id: node.parent.data.id, name: node.parent.data.name, type: node.parent.data.type });
      this.current.push(node.parent.data.key);
      this.parents(node.parent);
    },
    search () {
      // 搜索
      this.filterdata[this.options[this.select].key] = this.input3.length > 0 ? this.input3 : undefined;
      this.getlist();
    },
    getlist () {
      // 下拉筛选
      this.line_tree_data = [];
      this.dept_tree_data = [];
      this.getTreeLine();
      this.getTreeDept();
    },
    msg (txt) {
      this.$alert(txt, {
        confirmButtonText: '确定',
        callback: action => { }
      });
    },
    getYuntai (data) {
      if (!data) {
        return;
      }
      for (let i = 0; i < data.length; i++) {
        if (data[i].type == "yuntai") {
          this.yuntais.push(data[i].id);
        }
        if (data[i].children && data[i].children.length > 0) {
          this.getYuntai(data[i].children);
        }
      }
    },
    refresh () {
      // 重置
      this.filterdata = {};
      this.getTreeLine();
      this.getTreeDept();
      this.getTreeAirport();
      this.resizefilter();
      this.fillterMenu = false;
      this.input3 = '';
      this.select = '0';
    },
    getRoleTreeRootNode (data) {
      console.log(this.$route.name, 'this.$route.name');
      if (this.$route.name == "control" || this.$route.name == "realtimeConfig") {
        this.treeId = this.$route.params.treeId;
        let key = "yuntai_" + this.treeId;
        this.$nextTick().then(() => {
          this.$refs.tree.setCurrentKey(key);
          this.parents(this.$refs.tree.store.nodesMap[key]);
          // 其它地方跳转，获取当前云台数据，并通过resetTreeData重构的格式传到父组件 
          this.resetTreeData(this.$refs.tree.store.nodesMap[key]);
          this.$emit('ceshi', { node: this.nodedata, parent: this.parentList, total: this.total });
        });
      } else if (this.$route.name == 'GIS地图' && this.activeName == '2') {
        this.$emit('airportData', data)
      } else {
        this.$nextTick().then(() => {
          const firstNode = document.querySelector('.el-tree-node')
          firstNode.click();
        })
        this.tag = data[0].key;
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.treeContainer {
  position: relative;
  width: 100%;
  display: flex;
  height: 100%;
  .left {
    width: 270px;
    border-right: 1px solid $base-border-color;
    padding-right: 10px;
    box-sizing: border-box;
    flex-shrink: none;
  }
  .right {
    flex: 1;
    overflow: hidden;
    padding-left: $base-padding10;
  }
  .treeComponent {
    width: 100%;
    position: relative;
    height: 100%;
    display: flex;
    flex-direction: column;
    ::v-deep {
      .el-input-group__append {
        padding: 0 10px;
      }
      .el-drawer__wrapper {
        position: absolute;
        top: 29px;
        .el-drawer {
          box-shadow: none;
        }
      }
      .el-tabs__header {
        margin-bottom: 5px;
        .el-tabs__item {
          font-size: $base-font-size-default;
          height: 35px;
          line-height: 35px;
        }
      }
    }
    .topbox {
      display: flex;
      align-items: center;
      h3 {
        margin: 0;
        font-size: $base-font-size-default;
        font-weight: normal;
      }
      .tools {
        margin-left: auto;
        display: flex;
        align-items: center;
        svg {
          cursor: pointer;
          font-size: 18px;
          color: $base-menu-color;
          &:hover,
          &.act {
            color: $base-color-default;
          }
        }
        svg + svg {
          margin-left: 10px;
        }
      }
    }
    .searchbox {
      margin: 12px 0;
      ::v-deep {
        .el-input-group__prepend {
          padding: 0 15px;
        }
        .el-select {
          width: 110px;
        }
      }
    }
    .treebox {
      font-size: $base-font-size-small;
      flex: 1;
      overflow: hidden;
      display: flex;
      flex-direction: column;
      .eltab {
        height: 100%;
        display: flex;
        flex-direction: column;
        .el-tab-pane {
          height: 100%;
          overflow-x: hidden;
          overflow-y: auto;
        }
      }
      .treeicon {
        margin-right: 3px;
        color: $base-color-default;
        &.green {
          color: $base-green-color;
        }
        &.hui {
          color: $base-font-color;
        }
      }
      .el-tree {
        overflow-y: auto;
        flex: 1;
      }
    }
    .drawerbox {
      .drawerlist {
        .item {
          padding: 5px 0;
          .title {
            font-weight: bold;
          }
          span {
            color: $base-font-color;
            cursor: pointer;
            &.act {
              color: $base-color-default;
            }
          }
          span + span {
            margin-left: 3px;
          }
        }
      }
      .btns {
        display: flex;
        margin-top: 10px;
        button {
          flex: 1;
        }
      }
    }
  }
}
</style>
