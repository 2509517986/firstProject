<template>
  <div id="mapcontainer"></div>
</template>
<script>
export default {
  name: 'mapCom',
  props: {
    point: {
      type: Array,
      default: [121.421, 29.326]
    },
    type: { // 点击添加标记
      type: Boolean,
      default: false
    },
    deviceType: {
      type: String,
      default: ''
    },
    monitorType: {
      type: String,
      default: '1'
    },
    roadList: {
      type: Array,
      default: []
    },
  },
  data () {
    return {
      map: ''
    }
  },
  mounted () {
    var that = this;
    that.map = new BMap.Map('mapcontainer');
    that.map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放  
    if (that.point.length > 0 && Array.isArray(that.point)) {
      // var point = new BMap.Point(that.point[0], that.point[1]);
      // that.map.centerAndZoom(point, 13);
      // var marker = new BMap.Marker(point);
      console.log(this.deviceType, 'deviceType');
      let img = '';
      if (this.deviceType == "yuntai") {
        img = this.monitorType == 2 ? '/static/images/tower2.png' : '/static/images/tower22.png';
      } else if (this.deviceType == "tower") {
        img = '/static/images/tower.png';
      } else {
        img = '/static/images/uav.png';
      }
      let myIcon = new BMap.Icon(img, new BMap.Size(50, 50)),
        point = new BMap.Point(that.point[0], that.point[1]),
        marker = new BMap.Marker(point, { icon: myIcon });
      that.map.centerAndZoom(point, 13);
      that.map.addOverlay(marker);
    } else {
      that.map.centerAndZoom(new BMap.Point(121.421, 29.326), 13);
    }
    console.log(that.roadList,'roadList');
    if (that.roadList.length > 0) {
      that.setZoom([new BMap.Point(that.roadList[0].startLon, that.roadList[0].startLat)]);
      for (let i = 0; i < that.roadList.length; i++) {
        this.drowLine(that.map, { lng: that.roadList[i].startLon, lat: that.roadList[i].startLat }, { lng: that.roadList[i].endLon, lat: that.roadList[i].endLat }, that.roadList[i].routeList)
      }
    } else {
      that.$message({
        message: '暂无航线数据',
        type: 'warning'
      })
    }
    if (that.type) {
      // // 创建城市选择控件
      // var cityControl = new BMap.CityListControl({
      //     // 控件的停靠位置（可选，默认左上角）
      //     anchor: BMAP_ANCHOR_TOP_LEFT,
      //     // 控件基于停靠位置的偏移量（可选）
      //     offset: new BMap.Size(10, 5)
      // });
      // // 将控件添加到地图上
      // that.map.addControl(cityControl);
      function ZoomControl () {
        this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
        this.defaultOffset = new BMap.Size(20, 20)
      }
      ZoomControl.prototype = new BMap.Control();
      if (this.deviceType != 'jichao') {
        ZoomControl.prototype.initialize = function (map) {
          var div = document.createElement('div');
          div.setAttribute("id", "areaInput");
          div.setAttribute("style", "background: #fff;box-shadow: 0 0 5px rgba(0,0,0,0.15);height: 32px;border-radius: 4px;overflow: hidden;");
          var ipt = document.createElement('input');
          ipt.setAttribute("class", "ipt");
          ipt.setAttribute("placeholder", "输入搜索内容");
          ipt.setAttribute("style", "border: 0;outline: none;padding: 0 10px;height: 32px;font-size: 13px;");
          var btn = document.createElement('input');
          btn.setAttribute("class", "btn");
          btn.setAttribute("value", "搜索");
          btn.setAttribute("style", "width: 45px;cursor:pointer;border: 0;outline: none;text-align: center;background: #2196F3;color: #fff;height: 32px;font-size: 13px;");
          div.appendChild(ipt);
          div.appendChild(btn);
          // 添加DOM元素到地图中
          map.getContainer().appendChild(div);
          btn.onclick = function () {
            var positionName = ipt.value;
            var local = new BMap.LocalSearch(that.map, { renderOptions: { map: that.map, autoViewport: true, selectFirstResult: false } });
            local.search(positionName);
          }
          // 将DOM元素返回
          return div;
        }
      }
      //创建控件元素
      var myZoomCtrl = new ZoomControl();
      //添加到地图中
      that.map.addControl(myZoomCtrl);
      // 点击锚点
      if (that.deviceType == 'jichao') return;
      that.map.addEventListener("click", function (e) {
        // 清除覆盖物
        that.map.clearOverlays();
        var geoc = new BMap.Geocoder();
        var lng = e.point.lng, lat = e.point.lat;
        var point = new BMap.Point(lng, lat);
        let img = that.deviceType == "yuntai" ? '/static/images/tower2.png' : that.deviceType == "tower" ? '/static/images/tower.png' : '/static/images/uav.png',
          myIcon = new BMap.Icon(img, new BMap.Size(50, 50)),
          marker = new BMap.Marker(point, { icon: myIcon });
        that.map.addOverlay(marker);
        geoc.getLocation(point, function (rs) {
          console.log(rs, 'rs');
          var addComp = rs.address;
          that.$emit('list', rs);
        });
      });
    }
  },
  methods: {
    // 划线
    drowLine (map, PointArr, PointArrNext, allRoadList) {
      let newallRoadList = [];
      allRoadList.map((item, ind) => {
        newallRoadList.push(new BMap.Point(item.lng, item.lat))
        var polyline = new BMap.Polyline(
          newallRoadList,
          {
            strokeColor: this.color16(),
            strokeWeight: 2,
            strokeOpacity: 1
          });   //创建折线
        map.addOverlay(polyline);
      })
      this.addStartMarker(new BMap.Point(newallRoadList[0].lng, newallRoadList[0].lat), '起点', this.map);
      this.addMarkerEnd(new BMap.Point(newallRoadList[newallRoadList.length - 1].lng, newallRoadList[newallRoadList.length - 1].lat), '无人机飞行', map, newallRoadList[newallRoadList.length - 1], new BMap.Point(PointArr.lng, PointArr.lat));//添加图标
      console.log(newallRoadList, 'newallRoadList---');
    },
    //随机色
    color16 () {//十六进制颜色随机
      var r = Math.floor(Math.random() * 256);
      var g = Math.floor(Math.random() * 256);
      var b = Math.floor(Math.random() * 256);
      var color = '#' + r.toString(16) + g.toString(16) + b.toString(16);
      return color;
    },
    addStartMarker (point, name, mapInit) {
      var myIcon = new BMap.Icon('/static/images/start.png', new BMap.Size(45, 45), {
        anchor: new BMap.Size(20, 45),//这句表示图片相对于所加的点的位置mapStart
        imageSize: new BMap.Size(45, 45)//图标所用的图片的大小，此功能的作用等同于CSS中的background-size属性。可用于实现高清屏的高清效果
        // offset: new BMap.Size(-10, 45), // 指定定位位置
        // imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
      });
      window.marker = new BMap.Marker(point, { icon: myIcon });  // 创建标注
      mapInit.addOverlay(marker);               // 将标注添加到地图中
      marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    },
    //添加行驶和终点图标
    addMarkerEnd (point, name, mapInit, trackUnit, prePoint) {
      var myIcon = new BMap.Icon('/static/images/end.png', new BMap.Size(45, 45), {
        anchor: new BMap.Size(20, 45),//这句表示图片相对于所加的点的位置mapStart
        imageSize: new BMap.Size(45, 45)//图标所用的图片的大小，此功能的作用等同于CSS中的background-size属性。可用于实现高清屏的高清效果
        // offset: new BMap.Size(-10, 45), // 指定定位位置
        // imageOffset: new BMap.Size(0, 0 - 10 * 25) // 设置图片偏移
      });
      window.marker = new BMap.Marker(point, { icon: myIcon });  // 创建标注
      mapInit.addOverlay(marker);               // 将标注添加到地图中
      marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
    },
    // 根据点的数组自动调整缩放级别
    setZoom (bPoints) {
      var view = this.map.getViewport(eval(bPoints));
      var mapZoom = view.zoom;
      var centerPoint = view.center;
      this.map.centerAndZoom(centerPoint, mapZoom);
    },
  }
}
</script>

<style lang="scss" scoped>
#mapcontainer {
  width: 100%;
  height: 400px;
}
</style>
