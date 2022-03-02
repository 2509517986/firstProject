import request from '@/utils/request'

// 查询云台视频信息（视频url、亮度、对比度、饱和度、雨刮、视窗加热接口）
export function getVideoInfo(query) {
 /* var paths=window.location.href.split("//");//.replace("/","");
 // path.split("//");

  var path=paths[1].split(":")[0];
  console.info("path:"+path);
  var basePath="/"+path.split("/")[0];//":8080";//*/
  var path=window.location.href;
  if(path.indexOf("192.168")==-1&&path.indexOf("localhost")==-1){
    query.waiNet=1;
  }
  return request({
      url: '/service/liveVideo/getVideoInfo',
    //  url: 'http://'+basePath+':8080/service/liveVideo/getVideoInfo',
      method: 'get',
      params: query
  })
}
// 发送远程控制命令
export function remoteControl(query) {
  return request({
      url: '/service/liveVideo/remoteControl',
      method: 'get',
      params: query
  })
}
// 发送播放命令
export function sendPlayCmd(query) {
  return request({
      url: '/service/liveVideo/sendPlayCmd',
      method: 'get',
      params: query
  })
}
// 发送停止播放命令
export function sendStopCmd(query) {
  return request({
      url: '/service/liveVideo/sendStopCmd',
      method: 'get',
      params: query
  })
}
// 设置图像参数
export function setPicParams(query) {
  return request({
      url: '/service/liveVideo/setPicParams',
      method: 'get',
      params: query
  })
}
// 新增云台预置位信息
export function add_presetPosition(data) {
  return request({
    url: '/service/presetPosition',
    method: 'post',
    data: data
  })
}
// 修改云台预置位信息
export function edit_presetPosition(data) {
  return request({
    url: '/service/presetPosition',
    method: 'put',
    data: data
  })
}
// 删除云台预置位信息
export function del_presetPosition(ids) {
  return request({
    url: '/service/presetPosition/' + ids,
    method: 'delete'
  })
}
// 查询云台预置位信息
export function get_presetPosition(query) {
  return request({
    url: '/service/presetPosition/list',
    method: 'get',
    params: query
  })
}
// 巡检功能--查询
export function get_arguments(query) {
  return request({
    url: 'service/arguments/list',
    method: 'get',
    params: query
  })
}
// 巡检功能--新增新增
export function add_arguments(data) {
  return request({
    url: '/service/arguments',
    method: 'post',
    data: data
  })
}
// 巡检功能--删除
export function del_arguments(data) {
  let deviceCode = data.deviceCode, groupNum = data.groupNum, inspectIndex = data.inspectIndex, channelnum = data.channelnum;
  return request({
    url: '/service/arguments?deviceCode='+deviceCode + '&groupNum='+ groupNum + '&inspectIndex='+inspectIndex + '&channelnum='+channelnum,
    method: 'delete'
  })
}
// 巡检功能--修改
export function edit_arguments(data) {
  return request({
    url: '/service/arguments',
    method: 'put',
    data: data
  })
}
// 巡检策略功能--查询
export function get_tactics(query) {
  return request({
    url: 'service/tactics/getInspectionTactics',
    method: 'get',
    params: query
  })
}
// 巡检策略功能--新增
export function add_tactics(data, id) {
  return request({
    url: '/service/tactics/saveInspectionTactics/' + id,
    method: 'post',
    data: data
  })
}
// 巡检策略功能--删除
export function del_tactics(ids) {
  return request({
    url: '/service/tactics/' + ids,
    method: 'delete'
  })
}
// 巡检策略功能--修改
export function edit_tactics(data) {
  return request({
    url: '/service/tactics',
    method: 'put',
    data: data
  })
}
// 巡航功能--查询
export function get_cruise(query) {
  return request({
    url: 'service/cruise/list',
    method: 'get',
    params: query
  })
}
// 巡航功能--新增
export function add_cruise(data) {
  return request({
    url: '/service/cruise',
    method: 'post',
    data: data
  })
}
// 巡航功能--删除
export function del_cruise(data) {
  let deviceCode = data.deviceCode, groupNum = data.groupNum, inspectIndex = data.inspectIndex, channelnum = data.channelnum,pointIndex = data.pointIndex,preset = data.preset,speed = data.speed,stayTime = data.stayTime;
  return request({
    url: '/service/cruise?deviceCode='+deviceCode + '&groupNum='+ groupNum + '&channelnum='+channelnum + '&pointIndex='+ pointIndex + '&preset='+ preset + '&speed='+ speed + '&stayTime='+ stayTime,
    method: 'delete'
  })
}
// 巡航功能--修改
export function edit_cruise(data) {
  return request({
    url: '/service/cruise',
    method: 'put',
    data: data
  })
}
// 查询云台历史视频列表
export function get_history_video(query) {
  return request({
    url: 'service/yuntaiVideo/list',
    method: 'get',
    params: query
  })
}
// 修改功能参数
export function edit_arameters(data) {
  return request({
    url: '/service/function',
    method: 'put',
    data: data
  })
}
// 录像回放
export function videoPlayback(query) {
  return request({
    url: '/service/videoPlayback/list',
    method: 'get',
    params: query
  })
}
// 录像回放,播放
export function historyvideo_play(query) {
  return request({
    url: '/service/videoPlayback/play',
    method: 'get',
    params: query
  })
}
// 录像回放,停止
export function historyvideo_stop(query) {
  return request({
    url: '/service/videoPlayback/stop',
    method: 'get',
    params: query
  })
}
// 录像回放,控制
export function historyvideo_control(query) {
  return request({
    url: '/service/videoPlayback/control',
    method: 'get',
    params: query
  })
}
export function historyvideo_getCurPlayTime(query) {
  return request({
    url: '/service/videoPlayback/getCurPlayTime',
    method: 'get',
    params: query
  })
}
