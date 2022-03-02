<template>
  <el-card class="app-container">
    <tree-container @ceshi="gettreedata" @socketData="getsocketData" @uavRoadList="getuavRoadList" @currentNode="getcurrentNode" @airportData="getairportData">
      <div class="mapbox">
        <!-- 统计 -->
        <div class="leftc">
          <span>全部：<font>{{Object.keys(total).length>0 && total.total?total.total:0}}</font></span>
          <span class="green">在线：<font>{{Object.keys(total).length>0 && total.online?total.online:0}}</font></span>
          <span class="red">离线：<font>{{Object.keys(total).length>0 && total.offline?total.offline:0}}</font></span>
          <span>休眠：<font>{{Object.keys(total).length>0 && total.dormancy?total.dormancy:0}}</font></span>
          <span class="blue">在线率：<font>{{Object.keys(total).length>0 &&total.onlineRate?(total.onlineRate * 100).toFixed(2):0}}%</font></span>
        </div>
        <div id="mapcontainer"></div>
        <el-dialog :title="thisPics.info.name" :visible.sync="open" width="1100px" append-to-body :destroy-on-close="true">
          <div v-if="open" class="swiper-gallery">
            <swiper class="swiper gallery-top swiper-no-swiping" :options="swiperOptionTop" ref="swiperTop" v-if="thisPics.pics.length>0">
              <swiper-slide v-for="(e,i) in thisPics.pics" :key="i">
                <div class="img-box"><img class="swiper-lazy" style="transform:scale(1)" :data-src="reDomain(imgType=='jichao'?e.path:e.imageDir, e.imageName)" :ref="img_index ==i?'imgDiv':''" alt=""></div>
                <div class="swiper-lazy-preloader"></div>
              </swiper-slide>
            </swiper>
            <template v-if="thisPics.pics.length>0">
              <el-button type="primary" icon="el-icon-arrow-left" circle class="arrowleft arrowicon" @click="arrowleft"></el-button>
              <el-button type="primary" icon="el-icon-arrow-right" circle class="arrowright arrowicon" @click="arrowright"></el-button>
            </template>
          </div>
          <div class="swiper-thumbnail">
            <div class="swiper gallery-thumbs">
              <template v-if=" thisPics.pics.length>0">
                <div :class="['swiper-slide', {act:img_index == i}]" v-for="(e,i) in  thisPics.pics" :key="i" @click="slideto(i)">
                  <div class="box">
                    <div class="mask">
                      <span>{{e.describes}}</span>
                      <span>{{e.updateTime}}</span>
                    </div>
                    <img :src="reDomain(imgType=='jichao'?e.path:e.imageDir, e.imageName)">
                  </div>
                </div>
              </template>
            </div>
            <div class="thumbnail-arrow thumbnail-arrow-left" @click="lesspageto()"><i class="el-icon-arrow-left"></i></div>
            <div class="thumbnail-arrow thumbnail-arrow-right" @click="addpageto()"><i class="el-icon-arrow-right"></i></div>
          </div>
        </el-dialog>
        <!-- 地图标识 -->
        <div class="mmm">
          <div class="item">
            <img src="/static/images/tower.png" alt="">
            <span>杆塔</span>
          </div>
          <div class="item">
            <img src="/static/images/t2.png" alt="">
            <span>杆塔球机</span>
          </div>
          <div class="item">
            <img src="/static/images/t3.png" alt="">
            <span>杆塔枪机</span>
          </div>
          <div class="item">
            <img src="/static/images/uav.png" alt="">
            <span>无人机</span>
          </div>
          <div class="item">
            <img src="/static/images/airport.png" alt="">
            <span>机巣</span>
          </div>
        </div>
      </div>
    </tree-container>
    <uavVideo v-if="videoModal" @showVideo="getshowVideo" :uavUrl="uavUrl"></uavVideo>
    <remoteCall v-if="isRemoteCall" :remoteCallObj="remoteCallObj" @close="closeRemoteCall"></remoteCall>
  </el-card>
</template>
<script>
import { get_pictureListByDay, get_pictureListByDetails, } from "@/api/senxu/yuntai_picture/index";
import { get_uavInspection_list } from '@/api/senxu/inspection/index'
// import { originTalking } from "@/api/senxu/devicemanager/line";
// import { Swiper, SwiperSlide } from 'vue-awesome-swiper';

import { set_machine } from './setMachine';
import { originTalking, getUavVideoInfo, getUavVideoInfoOff } from "@/api/senxu/devicemanager/line";
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import uavVideo from './uavVideo.vue'
import remoteCall from './remoteCall.vue'
import * as global from '@/utils/global'
import 'swiper/css/swiper.css'

export default {
  name: 'onePMap',
  components: { Swiper, SwiperSlide, uavVideo, remoteCall },
  data () {
    let self = this;
    return {
      map: '',
      bPoints: [],
      onePoints: {},
      img_index: 0,
      total: {},
      open: false,
      thisPics: {
        info: {},
        pics: []
      },
      totalnum: '',
      queryParams: { pageNum: 1, pageSize: 8, yuntaiId: undefined },
      swiperOptionTop: {
        spaceBetween: 0,
        lazy: true,
        observer: true,
        observeParents: true,
        updateOnWindowResize: true,
        initialSlide: 0,
        navigation: {
          nextEl: '.arrowright',
          prevEl: '.arrowleft'
        },
        on: {
          slideChange: function () {
            self.img_index = this.activeIndex
            // 当前页面最后一张时触发
            if (this.activeIndex == (parseInt(self.thisPics.pics.length) - 1)) {
              if ((parseInt(self.queryParams.pageNum) + 1) > self.pageTotal()) {
                return;
              } else {
                self.queryParams.pageNum++;
                self.getPics();
              }
            }
          }
        }
      },
      airportUavData: [],//无人机和机巣数据
      bPointslist: [],
      points: [],
      uavRoadListData: [],//无人机航线数据
      donePoints: [],//已经存在的点
      timerArr: [],//定时器
      linePoints: [],
      timer: null,
      num: 0,
      allRoadList: [],
      selectNode: [],//选的树
      carMk: {},
      drivingPoint: {},
      terminalPoint: {},
      interval: null,
      childCurrentNode: [],
      videoModal: false,
      hasUavVideo: false,//是否有无人机推流视频
      uavUrl: '',//无人机推流视频url
      currentSelectNode: {},//当前选择的树
      airportImg: [],//机巣里面的图片
      imgType: '',
      isRemoteCall: false,//打开远程喊话
      remoteCallObj: {},//传入远程喊话弹窗
      airportListData: {}

    }
  },
  computed: {
  },
  mounted () {
    this.init();
    window.videoclick = this.videoclick;
    window.toimgDetail = this.toimgDetail;
    window.originTalking = this.originTalking;
    window.uavBacking = this.uavBacking;

    window.machineEvent = this.machineEvent;
    window.uavVideoInfo = this.uavVideoInfo;
    window.hasUavVideo = this.hasUavVideo;
    window.airportImg = this.airportImg;
    window.machineImgEvent = this.machineImgEvent;
  },
  methods: {
    getcurrentNode (val) {
      console.log(val, 'currentNode');
      this.childCurrentNode = val;
      var that = this;
      let pointList = [];
      this.uavRoadListData.forEach(item => {
        if (item.airportId == val.parent.data.airportId) {
          pointList = [
            { "lng": item.startLon, "lat": item.startLat, "status": 1, "id": val.data },
            // { "lng": '110.98005492232747', "lat": '60.63934783935547', "status": 1, "id": val.data },
            // { "lng": '100.98005492232747', "lat": '50.53934783935547', "status": 1, "id": val.data },
            // { "lng": '95.98005492232747', "lat": '40.33934783935547', "status": 1, "id": val.data },
          ];
          //中间三条数字呈现的数据模拟假如，其余均为真实数据
          item.tRoutePointList.forEach(ite => {
            pointList.push({ "lng": ite.lon, "lat": ite.lat, "status": 1, "id": val.data })
          })
          pointList.push({ "lng": item.endLon, "lat": item.endLat, "status": 1, "id": val.data })
        }
      })

      this.allRoadList = pointList;
      this.addStartMarker(new BMap.Point(pointList[0].lng, pointList[0].lat), '起点', this.map);
      //无人机飞行图标
      this.drivingPoint = new BMap.Icon('/static/images/uav.png', new BMap.Size(52, 26), {
        anchor: new BMap.Size(27, 13),
        imageSize: new BMap.Size(52, 26)
      });
      //终点图标
      this.terminalPoint = new BMap.Icon('/static/images/uav.png', new BMap.Size(45, 45), {
        anchor: new BMap.Size(20, 45),
        imageSize: new BMap.Size(45, 45)
      });
      var i = 0;
      var interval = setInterval(function () {
        if (i >= pointList.length) {
          clearInterval(interval);
          return;
        }
        that.drowLine(that.map, pointList[i], pointList[i + 1]);//画线调用
        i = i + 1;
      }, 1000);
    },



    // 划线
    drowLine (map, PointArr, PointArrNext) {
      if (PointArrNext != undefined) {
        var polyline = new BMap.Polyline(
          [
            new BMap.Point(PointArr.lng, PointArr.lat),
            new BMap.Point(PointArrNext.lng, PointArrNext.lat)
          ],
          {
            strokeColor: "red",
            strokeWeight: 2,
            strokeOpacity: 1
          });   //创建折线
        map.addOverlay(polyline);
        this.addMarkerEnd(new BMap.Point(PointArrNext.lng, PointArrNext.lat), '无人机飞行', map, PointArrNext, new BMap.Point(PointArr.lng, PointArr.lat));//添加图标
      } else {
        this.addMarkerEnd(new BMap.Point(PointArr.lng, PointArr.lat), '终点', map);//添加终点图标
      }
    },
    addStartMarker (point, name, mapInit) {
      if (name == "起点") {
        var myIcon = new BMap.Icon('/static/images/start.png', new BMap.Size(45, 45), {
          anchor: new BMap.Size(20, 45),//这句表示图片相对于所加的点的位置mapStart
          imageSize: new BMap.Size(45, 45)//图标所用的图片的大小，此功能的作用等同于CSS中的background-size属性。可用于实现高清屏的高清效果
          // offset: new BMap.Size(-10, 45), // 指定定位位置
          // imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
        });
        window.marker = new BMap.Marker(point, { icon: myIcon });  // 创建标注
        mapInit.addOverlay(marker);               // 将标注添加到地图中
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
      }
    },
    //添加行驶和终点图标
    addMarkerEnd (point, name, mapInit, trackUnit, prePoint) {
      var that = this;
      if (name == "无人机飞行") {
        if (this.carMk) {//先判断第一次进来的时候这个值有没有定义，有的话就清除掉上一次的。然后在进行画图标。第一次进来时候没有定义也就不走这块，直接进行画图标
          mapInit.removeOverlay(this.carMk);
        }
        this.carMk = new BMap.Marker(point, { icon: this.drivingPoint });  // 创建标注
        this.carMk.setRotation(trackUnit.route);//trackUnit.route
        //getAngle(point,prePoint);// js求解两点之间的角度
        this.carMk.setRotation(this.getAngle(point, prePoint) - 90);// 旋转的角度
        mapInit.addOverlay(this.carMk);               // 将标注添加到地图中
        // this.carMk.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
      } else {
        mapInit.removeOverlay(this.carMk);
        this.carMk = new BMap.Marker(point, { icon: this.terminalPoint });  // 创建标注
        let txt = "无人机编码：" + this.currentSelectNode.uavId;
        this.carMk.setLabel(this.setLabelStyle2(txt));
        mapInit.addOverlay(this.carMk);
      }
      this.carMk.addEventListener("click", function () {
        console.log(that.selectNode, 'this.selectNode----');
        that.addClickHandler(that.selectNode[0].node, point)
      });
    },
    //获得角度的函数
    getAngle (n, next) {
      var ret
      var w1 = n.lat / 180 * Math.PI
      var j1 = n.lng / 180 * Math.PI

      var w2 = next.lat / 180 * Math.PI
      var j2 = next.lng / 180 * Math.PI

      ret = 4 * Math.pow(Math.sin((w1 - w2) / 2), 2) - Math.pow(Math.sin((j1 - j2) / 2) * (Math.cos(w1) - Math.cos(w2)), 2);
      ret = Math.sqrt(ret);

      // var temp = Math.sin(Math.abs(j1 - j2) / 2) * (Math.cos(w1) + Math.cos(w2));
      var temp = Math.sin((j1 - j2) / 2) * (Math.cos(w1) + Math.cos(w2));
      ret = ret / temp;

      ret = Math.atan(ret) / Math.PI * 180;
      ret += 90;

      // 这里用如此臃肿的if..else是为了判定追踪单个点的具体情况,从而调整ret的值
      if (j1 - j2 < 0) {
        // console.log('j1<j2')
        if (w1 - w2 < 0) {
          // console.log('w1<w2')
          ret;
        } else {
          // console.log('w1>w2')
          ret = -ret + 180;
        }
      } else {
        // console.log('j1>j2')
        if (w1 - w2 < 0) {
          // console.log('w1<w2')
          ret = 180 + ret;
        } else {
          // console.log('w1>w2')
          ret = -ret;
        }
      }
      return ret;
    },
    getuavRoadList (val) {
      this.uavRoadListData = val;
    },
    getairportData (val) {
      console.log(val, 'airportData');
      this.airportListData = val;
      if (Object.prototype.toString.call(this.airportListData) == '[object Array]') {
        this.airportListData.forEach(item => {
          item.children.forEach(ite => {
            this.setMarket(ite)
            this.map.centerAndZoom(new BMap.Point(item.children[0].point[0], item.children[0].point[1]), 13);
          })
        })
      } else {
        this.airportListData.children.forEach(ite => {
          this.setMarket(ite)
          this.map.centerAndZoom(new BMap.Point(ite.point[0], ite.point[1]), 13);
        })
      }
    },
    //获取子组件传来的socket数据（实时航线数据）
    getsocketData (val) {
      this.airportUavData = JSON.parse(val).stationarys;
      if (this.num <= this.allRoadList.length - 1) {
        this.num++;
        // var lng = this.airportUavData[0].devices.drone.status.physicDrone.location.lon;//无人机位置经度
        // var lat = this.airportUavData[0].devices.drone.status.physicDrone.location.lat;无人机位置纬度
        // var lng = 100 + this.getRandom(5);
        // var lat = 35 + this.getRandom(5);
        var lng = Number(this.allRoadList[this.num - 1].lng);
        var lat = Number(this.allRoadList[this.num - 1].lat);
        var id = this.getRandom(1000);
        var point = { "lng": lng, "lat": lat, "status": 1, "id": id }
        var makerPoints = [];
        makerPoints.push(point);
        this.selectNode.map(item => {
          item.node.point = [this.allRoadList[this.num - 1].lng, this.allRoadList[this.num - 1].lat]
        })
        this.bPoints.push(new BMap.Point(lng, lat));
        this.setZoom(this.bPoints);
      }
    },
    getRandom (n) {
      return Math.floor(Math.random() * n + 1)
    },
    //添加线
    addLine (points) {
      var linePoints = [], pointsLen = points.length, i, polyline;
      if (pointsLen == 0) {
        return;
      }
      // 创建标注对象并添加到地图   
      for (var i = 0; i < pointsLen; i++) {
        linePoints.push(new BMap.Point(points[i].lng, points[i].lat));
      }

      polyline = new BMap.Polyline(linePoints, { strokeColor: "red", strokeWeight: 2, strokeOpacity: 0.5 });   //创建折线
      this.map.addOverlay(polyline);   //增加折线
    },
    reDomain (url, name) {
      // 图片地址
      return url;
    },
    gettreedata (data) {
      console.log(data, '子集的数据');
      let oneData = data.node;
      this.onePoints = oneData;
      this.total = data.total;
      this.judgeTreeData(this.onePoints);
    },
    init () {
      this.map = new BMap.Map('mapcontainer');
      if (Object.keys(this.map).length < 1) { return; }
      this.map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放        
      this.map.centerAndZoom(new BMap.Point(121.421, 29.326), 13);
    },
    randomHexColor () {
      //随机生成十六进制颜色
      let color = ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).substr(-6);
      return '#' + color;
    },
    judgeTreeData (data) {
      this.selectNode = JSON.parse(JSON.stringify(data));
      console.log(data, '航线数据');
      if (Object.keys(this.map).length < 1) { return; }
      this.map.clearOverlays();
      this.bPoints = [];
      if (data.length == 1 && data[0].towers.length < 1) {
        // 单个
        this.setMarket(data[0].node);
        if (this.bmPoint(data[0].node.point)) {
          this.map.centerAndZoom(this.bmPoint(data[0].node.point), 13);
        }
      } else {
        // 多个
        this.showTowersData(data);
        this.setZoom(this.bPoints);
      }
    },
    showTowersData (data) {
      for (let i = 0; i < data.length; i++) {
        data[i].pLine = [];
        if (data[i].towers && data[i].towers.length > 0) {
          for (let j = 0; j < data[i].towers.length; j++) {
            this.setMarket(data[i].towers[j]);
            data[i].pLine.push(this.bmPoint(data[i].towers[j].point));
          }
        }
        this.showPolyLine(data[i].pLine);
      }
    },
    bmPoint (data) {
      if (data) {
        let lng = data.length > 0 && data[0] && data[0].length > 0 ? data[0] : '',
          lnt = data.length > 0 && data[1] && data[1].length > 0 ? data[1] : '',
          point = new BMap.Point(lng, lnt);
        return point;
      }
    },
    getAirportImg () {
      // console.log(this.uavRoadListData,'this.uavRoadListData-------');
      this.uavRoadListData.forEach(item => {
        if (item.airportId == this.currentSelectNode.airportId) {
          this.routeCode = item.routeCode;
        }
      })
      setTimeout(() => {
        get_uavInspection_list({ airportId: this.airportId }).then((res) => {
          res.rows.forEach(item => {
            this.airportImg.push(item.path)
          })
        })
      }, 0)
    },
    async setMarket (data) {
      console.log(data, 'data---');
      this.currentSelectNode = data;
      if (data.type == '1') {
        await this.getAirportImg();
      }
      // console.log(this.airportImg, 'this.airportImg');
      let that = this;
      let img = "";
      if (data && data.point) {
        if (data.children && data.children.length > 0 && data.children[0].type == 'yuntai') {
          for (let i = 0; i < data.children.length; i++) {
            img = data.children[i].monitorType == 2 ? '/static/images/tower2.png' : '/static/images/tower22.png';
          }
        } else if (data.type == 'yuntai') {
          img = data.monitorType == 2 ? '/static/images/tower2.png' : '/static/images/tower22.png';
        } else if (data.airportCode) {
          img = '/static/images/airport.png';
        } else if (data.uavId) {
          img = '/static/images/uav.png';
        } else {
          img = '/static/images/tower.png';
        }
        let myIcon = new BMap.Icon(img, new BMap.Size(50, 50)),
          point = that.bmPoint(data.point),
          marker = new BMap.Marker(point, { icon: myIcon });
        if (data.name != "") {
          if (data.airportName) {
            let txt = "机场名称：" + data.airportName;
            marker.setLabel(that.setLabelStyle2(txt));
          } else if (data.uavId) {
            let txt = "无人机编码：" + data.uavId;
            marker.setLabel(that.setLabelStyle2(txt));
          } else {
            let txt = "杆塔名称：" + data.name;
            marker.setLabel(that.setLabelStyle2(txt));
          }
        }
        that.map.addOverlay(marker);
        that.bPoints.push(point);
        marker.addEventListener("click", function () {
          that.addClickHandler(data, point)
        });
      }
    },
    // 根据点的数组自动调整缩放级别
    setZoom (bPoints) {
      var view = this.map.getViewport(eval(bPoints));
      var mapZoom = view.zoom;
      var centerPoint = view.center;
      this.map.centerAndZoom(centerPoint, mapZoom);
    },
    showPolyLine (data) {
      let polyline = new BMap.Polyline(data, {
        // strokeColor : this.randomHexColor(),
        strokeWeight: 2,
        strokeOpacity: 0.8
      });
      this.map.addOverlay(polyline);
    },
    addClickHandler (data, point) {
      let queryParams = { lineId: undefined, towerId: undefined, yuntaiIds: undefined, airportId: undefined, uavId: undefined, fcId: undefined, airportCode: undefined, airportName: undefined, imgSize: 4 }
      if (data.type == "tower") {
        queryParams.lineId = data.parentId;
        queryParams.towerId = data.id;
      } else if (data.type == "yuntai") {
        queryParams.yuntaiIds = data.id;
      } else if (data.type == "1") {
        data.name = '机巣';
        queryParams.airportId = data.airportId;
        queryParams.airportCode = data.airportCode;
        queryParams.airportName = data.airportName;
        queryParams.state = data.state;
      } else if (data.type == "jichao") {
        data.name = '无人机';
        queryParams.uavId = data.uavId;
        queryParams.fcId = data.fcId;
        queryParams.uav_state = data.uav_state;
      }
      this.setInfoWindow(data.name, queryParams, point);
    },
    setLabelStyle2 (content) {
      //左偏移  右偏移
      var offsetSize = new BMap.Size(0, 0);
      var labelStyle = {
        color: "#fff",
        backgroundColor: "#333333",
        border: "0",
        fontSize: "12px",
        opacity: "0.8",
        verticalAlign: "center",
        borderRadius: "2px",
        whiteSpace: "nowrap",
        wordWrap: "break-word",
        padding: "5px",
      };
      //用于设置样式
      var spanA = "<span class='angle'><span>"
      //不同数字长度需要设置不同的样式。
      var num = parseInt(content.length / 10)
      switch (num) {
        case 0:
          offsetSize = new BMap.Size(-20, -40);
          break;
        case 1:
          offsetSize = new BMap.Size(-20, -40);
          break;
        case 2:
          offsetSize = new BMap.Size(-20, -60);
          break;
        case 3:
          offsetSize = new BMap.Size(-20, -80);
          break;
        default:
          break;
      }

      var label = new BMap.Label(content + spanA, {
        offset: offsetSize
      });
      label.setStyle(labelStyle);
      return label;
    },
    setHtml (content) {
      let html = "";
      if (content.length > 0) {
        html += '<div class="listbox">';
        for (let i = 0; i < content.length; i++) {
          html += '<div class="item ' + (content[i].yuntai.state && content[i].yuntai.state != -1 ? "online" : "outline") + '">';
          html += '<div class="top">';
          html += '<img class="icon" src="' + (content[i].yuntai.state && content[i].yuntai.state != -1 ? "/static/images/j.png" : "/static/images/j2.png") + '">';
          html += '<span>编号:' + content[i].yuntai.code + '</span>';
          html += '<span>名称:' + content[i].yuntai.name + '</span>';
          html += '</div>';
          html += '<div class="txt">';
          html += '<div class="li">';
          html += '<img class="smallicon" src="' + (content[i].yuntai.state && content[i].yuntai.state != -1 ? "/static/images/wifi.png" : "/static/images/outline.png") + '">';
          html += '<span class="ontxt">' + (content[i].yuntai.state && content[i].yuntai.state == 1 ? "在线" : content[i].yuntai.state && content[i].yuntai.state == 2 ? "休眠" : "离线") + '</span>';
          html += '</div>';
          html += '<div class="li">';
          html += '<img class="smallicon" src="' + (content[i].yuntai.state && content[i].yuntai.state != -1 ? content[i].yuntai.imageStatus == 1 ? "/static/images/s2.png" : "/static/images/s1.png" : "/static/images/s3.png") + '">';
          html += '</div>';
          html += '<div class="li">';
          html += '<a onclick="' + (content[i].yuntai.state && content[i].yuntai.state != -1 ? "videoclick(" + content[i].yuntai.id + ")" : "") + '"><img class="smallicon" src="' + (content[i].yuntai.state && content[i].yuntai.state != -1 ? "/static/images/play.png" : "/static/images/noplay.png") + '"></a>';
          html += '</div>';
          html += '<div class="li">';
          html += '<div class="batteryIcon">';
          html += '<div class="batteryIconBody">';
          html += '<div class="batteryIconProgress" style="width:' + (content[i].yuntai.state && content[i].yuntai.state != -1 ? content[i].yuntai.electricPower ? content[i].yuntai.electricPower : 0 : 0) + '%;"></div>';
          html += '<label class="batteryIconContent"">' + (content[i].yuntai.state && content[i].yuntai.state != -1 ? content[i].yuntai.electricPower ? content[i].yuntai.electricPower : 0 : 0) + '%</label>';
          html += '<div class="batteryIconHead"></div>';
          html += '</div>';
          html += '</div>';
          html += '</div>';
          html += '</div>';
          html += '<div class="imgs">';
          if (content[i].pictures && content[i].pictures.length > 0) {
            for (let j = 0; j < content[i].pictures.length; j++) {
              html += '<a class="imgitem" imgindex="' + j + '" name="' + content[i].yuntai.name + '" id="' + content[i].yuntai.id + '" onclick="toimgDetail(this)">';
              html += '<img src="' + content[i].pictures[j].imageDir + content[i].pictures[j].imageName + '">'
              html += '</a>';
            }
          } else {
            html += '<div class="imgitem">';
            html += '<span>暂无图片</span>'
            html += '</div>';
          }
          html += '</div>'
          html += '</div>';
        }
        html += '</div>';
      } else {
        html += '<span style="padding:20px 0;display:block;">暂无云台设备</span>';
      }
      return html;
    },
    machineEvent (e) {
      this.uavVideoInfo(e)
    },
    machineImgEvent (event) {
      this.toimgDetail(event);
    },
    setUavHtml (content) {
      let html = "";
      console.log(content, 'content');
      this.airportUavData.forEach(item => {
        if (content.length > 0) {
          html += '<div class="listbox">';
          for (let i = 0; i < content.length; i++) {
            if (item.devices.drone.droneId == content[i].yuntai.uavId) {
              html += '<div class="item ' + (content[i].yuntai.uav_state && content[i].yuntai.uav_state != -1 ? "online" : "outline") + '">';
              html += '<div class="top">';
              html += '<img class="icon" src="' + (content[i].yuntai.uavId ? "/static/images/uav.png" : "/static/images/airport.png") + '">';
              html += '<span>编号:' + (content[i].yuntai.uavId ? content[i].yuntai.uavId : content[i].yuntai.airportCode) + '</span>';
              html += '<span>名称:' + (content[i].yuntai.uavId ? content[i].yuntai.fcId : content[i].yuntai.airportName) + '</span>';
              html += '</div>';
              html += '<div class="txt">';
              html += '<div class="li">';
              html += '<img class="smallicon" src="' + (content[i].yuntai.uav_state && content[i].yuntai.uav_state != -1 ? "/static/images/wifi.png" : "/static/images/outline.png") + '">';
              html += '<span class="ontxt">' + (content[i].yuntai.uav_state == 1 ? "在线" : "离线") + '</span>';
              html += '</div>';
              html += '<div class="li">';
              html += '<img class="smallicon" src="/static/images/RTK.png">';
              html += '<span class="ontxt">' + (item.devices.drone.status.physicDrone.isRTKBeingUsed ? "正常" : "异常") + '</span>';
              html += '</div>';
              html += '<div class="li">';
              html += '<div class="batteryIcon">';
              html += '<div class="batteryIconBody">';
              html += '<div class="batteryIconProgress" style="width:' + (content[i].yuntai.uav_state && content[i].yuntai.uav_state != -1 ? item.devices.drone.status.physicDrone.chargeRemainingInPercent ? item.devices.drone.status.physicDrone.chargeRemainingInPercent : 0 : 0) + '%;"></div>';
              html += '<label class="batteryIconContent"">' + (content[i].yuntai.uav_state && content[i].yuntai.uav_state != -1 ? item.devices.drone.status.physicDrone.chargeRemainingInPercent ? item.devices.drone.status.physicDrone.chargeRemainingInPercent : 0 : 0) + '%</label>';
              html += '<div class="batteryIconHead"></div>';
              html += '</div>';
              html += '</div>';
              html += '</div>';
              html += '</div>';
              html += '<div class="txt">';
              html += '<div class="li">';
              html += '<img class="smallicon" src="/static/images/alt.png">';
              html += '<span class="ontxt">' + item.devices.drone.status.physicDrone.absoluteAltitude + '米' + '</span>';
              html += '</div>';
              html += '<div class="li">';
              html += '<a onclick="' + (content[i].yuntai.uav_state && content[i].yuntai.uav_state != -1 ? "uavVideoInfo( 'uav')" : "") + '"><img class="smallicon" src="/static/images/play.png"></a>';
              html += '</div>';
              html += '<div class="li">';
              html += '<img class="smallicon" src="/static/images/data.png">';
              html += '<span class="ontxt">' + item.devices.drone.status.groundStationDataStatus.length > 0 ? '传输中' : '暂无' + '</span>';
              html += '</div>';
              html += '</div>';
              html += '<div class="imgs">';
              if (content[i].yuntai.uavId) {
                html += '<div class="originTalking">';
                html += '<a  id="' + content[i].yuntai.uavId + '" onclick="originTalking(this)">';
                html += '<span>远程喊话</span>';
                html += '</a>';
                html += '</div>';
                html += '<div class="uavBacking">';
                html += '<a  id="' + content[i].yuntai.airportId + '" onclick="uavBacking(this)">';
                html += '<span>一键返航</span>';
                html += '</a>';
                html += '</div>';
              } else {
                html += '<div class="imgitem">';
                html += '<span>暂无图片</span>'
                html += '</div>';
              }
              html += '</div>'
              html += '</div>';
            }
          }
          html += '</div>';
        } else {
          html += '<span style="padding:20px 0;display:block;">暂无云台设备</span>';
        }
      })
      return html;
    },
    setAirportHtml (content) {
      console.log(this.airportImg, 'airportImg');//获取的图片数据

      let html = "";
      if (this.airportUavData.length == 0) {
        html += '<span style="padding:20px 0;display:block;text-align:center">暂无云台设备</span>';
      }
      this.airportUavData.forEach(item => {
        if (content.length > 0 && item) {
          html += '<div class="machine-content">';
          for (let i = 0; i < content.length; i++) {
            if (item.id == content[i].yuntai.airportCode) {
              let slotbatteryObj = {
                full: '已充满',
                slotEmpty: '电池槽空',
                charging: '充电中',
                voltmeterBad: '电压表坏',
                loose: '未插紧',
              }
              let obj = {};//四个电池巢
              item.devices.airport.slotBattery?.forEach((it, index) => {
                obj[`slotbattery${index + 1}`] = slotbatteryObj[it.status]
              })
              let meteorological = item.devices.airport.meteorologicalStation.status
              let params = {
                airportId: item.id,//	机巢ID
                airportCode: content[i].yuntai.airportCode,//	机巢编号
                airportName: content[i].yuntai.airportName,//	机巢名称
                state: content[i].yuntai.state == 1 ? "在线" : "不在线",//	状态
                smoke: item.devices.airport.plc.smoke ? '是' : '否',//	是否有烟雾
                isPonding: item.devices.airport.plc.flood ? '是' : '否',//	是否积水
                inTemperature: item.devices.airport.tempHumiTransmitterPost.data.temperature.value + item.devices.airport.tempHumiTransmitterPost.data.temperature.unit,//	舱内温度
                cityElectricity: item.devices.airport.ups.status,//	供电类型

                // slotbatteryId: item.devices.airport.slotBattery[0].status,//	电池槽ID

                temperatureCelsius: meteorological.temperatureCelsius + item.devices.airport.tempHumiTransmitterPost.data.temperature.unit,//	气象站-温度
                humidity: meteorological.humidity + '%',//	气象站-湿度
                windSpeed: meteorological.windSpeed + 'm/s',//	气象站-风速
                rainfall: meteorological.rainfall + 'mm',//	气象站-雨量
                airPressure: meteorological.airPressure + 'Pa',//	气象站-气压
                windDirection: meteorological.windDirection,//	气象站-风向
                airportImg: this.airportImg ? this.airportImg : [],//图片
                ...obj
              }
              html += `<div class="machine-content">${set_machine(params)}</div>`;
            }
          }
          html += '</div>';
        } else {
          html += '<span style="padding:20px 0;display:block;text-align:center">暂无云台设备</span>';
        }
      })
      return html;
    },
    videoclick (id) {
      this.$router.push({ path: 'videoInspection/control/' + id });
    },
    getshowVideo (val) {
      this.videoModal = false;
      this.uavVideoInfoOff()
    },
    //无人机、机巣视频推流(开启)
    uavVideoInfo (type) {
      console.log(type, 'type');
      this.videoModal = true;
      let videoQuery = {
        airportCode: this.currentSelectNode.airportCode,
        fcId: '',//如果是机巣那么fcId的值为三种选择（WEIGAN、JITOU、JIWEI）
        spare0: 5000,
      }
      if (type == 'uav') {
        videoQuery.fcId = this.childCurrentNode.data.fcId;
      } else {
        videoQuery.fcId = type;
      }
      console.log(this.childCurrentNode, '无人机信息');
      getUavVideoInfo(videoQuery).then((res) => {
        if (res.data.isSuccess && res.data.result.srcUrl) {
          this.uavUrl = res.data.result.srcUrl;
          this.hasUavVideo = true;//如果有推流数据，改变无人机面板的视频图标
        }
        console.log(res, 'res无人机推流');
      })
    },
    //无人机、机巣视频推流(关闭)
    uavVideoInfoOff () {
      let videoQuery = {
        airportCode: this.childCurrentNode.parent.data.airportCode,
        fcId: this.childCurrentNode.data.fcId,//如果是机巣那么fcId的值为三种选择（WEIGAN、JITOU、JIWEI）
      }
      getUavVideoInfoOff(videoQuery).then((res) => {
        console.log(res, 'res无人机推流关闭');
      })
    },
    setInfoWindow (name, queryParams, point) {
      let opts = {
        width: 250,     // 信息窗口宽度
        height: 200,     // 信息窗口高度
        title: name  // 信息窗口标题
      };
      let infoWindow;
      if (queryParams.uavId) {
        console.log('展示无人机');
        let mockrows = [
          {
            pictures: [],
            yuntai: {
              uavId: queryParams.uavId,
              fcId: queryParams.fcId,
              uav_state: queryParams.uav_state,
            }
          }
        ]
        infoWindow = new BMap.InfoWindow(this.setUavHtml(mockrows), opts);
        this.map.openInfoWindow(infoWindow, point);
      } else if (queryParams.airportId) {
        let mockrows = [
          {
            pictures: [],
            yuntai: {
              airportCode: queryParams.airportCode,
              airportName: queryParams.airportName,
              state: queryParams.state,
            }
          }
        ]
        let opts = {
          width: 250,     // 信息窗口宽度
          height: 200,     // 信息窗口高度
          title: name  // 信息窗口标题
        };
        infoWindow = new BMap.InfoWindow(this.setAirportHtml(mockrows), { width: 330, height: 320, title: name });
        this.map.openInfoWindow(infoWindow, point);
        console.log('展示机巣');
      } else {
        get_pictureListByDay(queryParams).then(response => {
          console.log(response.rows, 're');
          if (response.code == 200) {
            infoWindow = new BMap.InfoWindow(this.setHtml(response.rows), opts);
            this.map.openInfoWindow(infoWindow, point);
          }
        })
      }
    },
    closeRemoteCall () {
      this.isRemoteCall = false;
    },
    //远程喊话
    originTalking (event) {



      this.isRemoteCall = true;//打开远程喊话
      this.remoteCallObj.uavId = event.getAttribute('id');//传入远程喊话弹窗


      // let uavData = {
      //   uavId: event.getAttribute('id'),
      //   soundType: '1',
      //   circulateCount: 2,
      //   content: '无人机喊话功能'
      // }

      // originTalking(uavData).then(res => {
      //   console.log(res, 'RES');
      // })
    },
    //一键返航
    uavBacking (event) {
      this.$message({
        type: 'warning',
        message: '待完成'
      })
    },
    toimgDetail (event, type) {
      this.imgType = type;
      console.log(event, type, '远程喊话');
      let self = this;
      self.open = true;
      self.queryParams.pageNum = 1;
      if (type == 'jichao') {
        self.thisPics.info = { name: '机场：' + event.getAttribute('name') };
        self.getPics({ imgIndex: event.getAttribute('imgindex') });
      } else {
        self.queryParams.yuntaiId = event.getAttribute('id');
        self.thisPics.info = { name: '云台：' + event.getAttribute('name') };
        self.getPics();
      }
      self.img_index = event.getAttribute('imgindex');
      if (self.img_index != 0) {
        self.swiperOptionTop.initialSlide = self.img_index;
      } else {
        self.swiperOptionTop.initialSlide = 0;
      }
    },
    getPics (obj) {
      let self = this;
      self.img_index = 0;
      self.swiperOptionTop.initialSlide = 0;
      self.thisPics.pics = [];
      if (self.imgType == 'jichao') {
        if (obj && obj.imgIndex) {
          self.img_index = obj.imgIndex;
        }
        this.queryParams.routeCode = this.routeCode;
        get_uavInspection_list(this.queryParams).then(response => {
          if (response.code == 200) {
            self.thisPics.pics = response.rows;
            self.totalnum = response.total;
          }
        });
      } else {
        get_pictureListByDetails(this.queryParams).then(response => {
          if (response.code == 200) {
            self.thisPics.pics = response.rows[0].pictures;
            self.totalnum = response.total;
          }
        });
      }
    },
    arrowleft () {
      if (this.img_index == 0 && this.queryParams.pageNum != 1) {
        this.queryParams.pageNum--;
        this.getPics();
      }
    },
    arrowright () {
      if ((parseInt(this.img_index) + 1) == this.thisPics.pics.length && this.queryParams.pageNum < this.pageTotal()) {
        this.queryParams.pageNum++;
        this.getPics();
      }
    },
    slideto (idx) {
      // 点击缩略图
      this.img_index = idx;
      if (this.$refs.swiperTop) {
        this.$refs.swiperTop.$swiper.slideTo(idx, 400, false);
      }
    },
    lesspageto () {
      // 上一页
      let page = parseInt(this.queryParams.pageNum);
      if (page < 2) {
        return;
      }
      page = page - 1;
      this.queryParams.pageNum = page;
      this.getPics();
    },
    pageTotal () {
      let v = this.totalnum % this.queryParams.pageSize == 0 ? this.totalnum / this.queryParams.pageSize : Math.ceil(this.totalnum / this.queryParams.pageSize);
      return v;
    },
    addpageto () {
      // 下一页
      let page = parseInt(this.queryParams.pageNum);
      if ((page + 1) > this.pageTotal()) {
        return;
      }
      page = page + 1;
      this.queryParams.pageNum = page;
      this.getPics();
    },
  }
}
</script>
<style lang="scss" scoped>
.app-container {
  position: relative;
  height: $base-app-main-height;
  ::v-deep {
    .el-card__body {
      height: 100%;
    }
    .listbox {
      overflow-y: auto;
      height: 170px;
      .item + .item {
        border-top: 1px solid $base-border-color;
      }
      .item {
        padding: 15px 0;
        .top {
          display: flex;
          align-items: center;
          .icon {
            width: 40px;
            display: block;
          }
          span {
            padding-left: 20px;
            &:last-child {
              margin-left: auto;
              padding-right: 10px;
            }
          }
        }
        .txt {
          margin-top: 10px;
          display: flex;
          align-items: center;
          padding-left: 50px;
          margin-right: 10px;
          justify-content: space-between;
          .li {
            display: flex;
            align-items: center;
            .smallicon {
              width: 16px;
              display: block;
              height: 16px;
              object-fit: cover;
            }
            span {
              padding-left: 6px;
              color: $base-color-black;
            }
            .batteryIcon {
              .batteryIconBody {
                position: relative;
                width: 30px;
                height: 16px;
                border-radius: 2px;
                &:before {
                  content: "";
                  border: 1px solid $base-color-default;
                  position: absolute;
                  top: 0;
                  bottom: 0;
                  right: 2px;
                  left: 0;
                }
                .batteryIconProgress {
                  height: 16px;
                  background: $base-color-default;
                }
                .batteryIconContent {
                  position: relative;
                  font-size: 12px;
                  left: 0px;
                  top: -15px;
                  transform: scale(0.8);
                  font-weight: normal;
                  display: block;
                  text-align: center;
                }
                .batteryIconHead {
                  position: relative;
                  height: 7px;
                  width: 3px;
                  background: $base-color-default;
                  display: block;
                  position: absolute;
                  top: 4px;
                  left: 28px;
                }
              }
            }
          }
        }
        .imgs {
          display: flex;
          align-items: center;
          padding-top: 10px;
          justify-content: center;
          .originTalking {
            padding: 8px 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;
          }
          .uavBacking {
            padding: 8px 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            cursor: pointer;
            margin-left: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
          }
          .imgitem {
            cursor: pointer;
            flex: 0 0 23%;
            height: 60px;
            margin: 1%;
            background: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
            img {
              width: 100%;
              height: 100%;
              object-fit: cover;
              display: block;
            }
          }
        }
        &.outline {
          .top span {
            color: #babbbb !important;
          }
          .txt span {
            color: $base-color-red !important;
          }
          .batteryIconHead,
          .batteryIconBody:before {
            border-color: #babbbb !important;
          }
          .batteryIconHead {
            background: #babbbb !important;
          }
          .batteryIconContent {
            color: #babbbb !important;
          }
        }
        &.online {
          .ontxt {
            color: $base-green-color !important;
          }
        }
      }
    }
    .machine-content {
      height: 295px;
      overflow-y: auto;
      .dialog-set-machine {
        .set-machine-row {
          width: 100%;
          display: block;
          display: flex;
          flex-wrap: wrap;
          .col-12 {
            width: 50%;
            display: inline-block;
            line-height: 30px;
            height: 30px;
            .title {
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
            .img {
              width: 30px;
              height: 30px;
              line-height: 30px;
              display: inline-block;
              vertical-align: middle;
            }
          }
          .col-6 {
            width: 25%;
            display: inline-block;
            text-align: left;
            padding: 5px 0;
            .img {
              width: 20px;
              height: 20px;
              line-height: 20px;
              display: inline-block;
              vertical-align: middle;
            }
          }
          .playImg {
            width: 25px;
            height: 25px;
            display: inline-block;
            margin: 5px;
          }
          .playImg:hover {
            cursor: pointer;
          }
          .btnImg {
            width: 70px;
            height: 70px;
            margin: 10px 10px 0 10px;
          }
        }
      }
    }
  }
  .mapbox {
    width: 100%;
    height: 100%;
    padding-left: $base-padding10;
    position: relative;
    #mapcontainer {
      width: 100%;
      height: 100%;
    }
  }
  .mmm {
    position: absolute;
    z-index: 1;
    padding: 8px 0px;
    bottom: 10px;
    right: 10px;
    background: rgba(255, 255, 255, 1);
    border-radius: 4px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
    display: flex;
    align-items: center;
    .item {
      margin: 0 8px;
      display: flex;
      align-items: center;
      img {
        width: 24px;
        display: block;
        height: auto;
      }
      span {
        font-size: 12px;
        display: block;
        padding-left: 8px;
        color: #999;
      }
    }
  }
  .leftc {
    position: absolute;
    z-index: 1;
    padding: 8px 0px;
    top: 10px;
    left: 20px;
    background: rgba(255, 255, 255, 1);
    border-radius: 4px;
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
    display: flex;
    align-items: center;
    span {
      margin: 0 $base-padding10;
      &.blue {
        color: $base-color-default;
      }
      &.green {
        color: $base-green-color;
      }
      &.red {
        color: $base-color-red;
      }
      font {
        font-size: 14px;
      }
    }
  }
}
.arrowicon {
  transition: all 0.3s;
  position: absolute;
  z-index: 5;
  width: 40px;
  height: 40px;
  font-size: 18px;
  border: 0;
  background: rgba(0, 0, 0, 0.8);
  color: rgba(255, 255, 255, 0.7);
  &.arrowleft,
  &.arrowright {
    top: 50%;
    margin-top: -30px;
  }
  &.arrowtop,
  &.arrowbottom {
    left: 50%;
    margin-left: -30px;
  }
  &.arrowleft {
    left: 16px;
  }
  &.arrowright {
    right: 16px;
  }
}
.swiper-gallery {
  flex: 1;
  width: 100%;
  overflow: hidden;
  position: relative;
  height: 400px;
  .gallery-top {
    width: 100%;
    height: 100%;
    .swiper-slide {
      overflow: hidden;
    }
    .img-box {
      position: relative;
      width: 100%;
      height: 100%;
      img {
        position: absolute;
        object-fit: contain;
        width: 100%;
        height: 100%;
        display: block;
      }
    }
  }
}
.swiper-thumbnail {
  height: 120px;
  padding: 10px 25px 10px;
  position: relative;
  overflow: hidden;
  .gallery-thumbs {
    height: 100%;
    width: 100%;
    display: flex;
    .swiper-slide {
      width: 12%;
      height: 100%;
      overflow: hidden;
      margin: 0 0.25%;
      cursor: pointer;
      &:before {
        content: "";
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.7);
        z-index: 2;
      }
      &.act {
        &::before {
          background: rgba(0, 0, 0, 0);
        }
      }
      .box {
        display: flex;
        flex-direction: column;
        height: 100%;
        position: relative;
        // span.title{display:block;text-align:center;color:#fff; flex-shrink: 0;height: 23px;line-height: 23px;font-size:$base-font-size-small;}
        img {
          object-fit: cover;
          width: 100%;
          flex: 1;
          display: block;
        }
        .mask {
          position: absolute;
          background: rgba(0, 0, 0, 0.6);
          bottom: 0;
          left: 0;
          right: 0;
          height: 35px;
          padding-top: 2px;
          z-index: 2;
          opacity: 0.7;
          span {
            display: block;
            text-align: center;
            color: #fff;
            font-size: $base-font-size-small;
          }
          span.absolute {
            position: absolute;
            color: $base-color-green;
            left: 7px;
            top: 8px;
          }
        }
      }
    }
    .swiper-slide-active {
      opacity: 1;
    }
  }
  .thumbnail-arrow {
    position: absolute;
    width: 25px;
    top: 10px;
    bottom: 10px;
    background: rgba(0, 0, 0, 0.7);
    color: #fff;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
  }
  .thumbnail-arrow-left {
    left: 0;
  }
  .thumbnail-arrow-right {
    right: 0;
  }
}
</style>
