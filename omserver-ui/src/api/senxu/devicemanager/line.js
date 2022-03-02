import request from '@/utils/request';
import { praseStrEmpty } from "@/utils/ruoyi";
let SOCKET_SERVER = 'http://159.75.216.222:9009';
export {SOCKET_SERVER}
// 新增线路信息
export function add_lineInfo(data) {
  return request({
    url: '/service/lineInfo',
    method: 'post',
    data: data
  })
}
// 修改线路信息
export function edit_lineInfo(data) {
  return request({
    url: '/service/lineInfo',
    method: 'put',
    data: data
  })
}
// 获取线路详情
export function get_lineInfo_detail(id) {
  return request({
    url: '/service/lineInfo/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 删除线路详细
export function del_lineInfo(ids) {
  return request({
    url: '/service/lineInfo/' + ids,
    method: 'delete'
  })
}
// 获取线路列表
export function get_lineList(query) {
  return request({
    url: '/service/lineInfo/list',
    method: 'get',
    params: query
  })
}
// 导出线路列表
export function export_lineInfo(query) {
  return request({
    url: '/service/lineInfo/export',
    method: 'get',
    params: query
  })
}
// 获取线路树
export function getTreeLine(query) {
  return request({
    url: '/service/lineInfo/getTreeLine',
    method: 'get',
    params: query
  })
}
// 获取组织机构线路树
export function getTreeDept(query) {
  return request({
    url: '/service/lineInfo/getTreeDept',
    method: 'get',
    params: query
  })
}
// 获取机场线路树
export function getTreeAirport(query) {
  return request({
    url: '/system/airport/airportTree',
    method: 'get',
    params: query
  })
}
//无人机喊话功能
export function originTalking(data) {
  return request({
    url: '/system/shouting',
    method: 'post',
    data: data
  })
}
//获取socket的token
export function socketUserLogin(query) {
  return request({
    url: '/system/airport/getDyToken',
    method: 'get',
    params: query
  })
}
//获取无人机、机巣视频推流（开启）
export function getUavVideoInfo(query) {
  return request({
    url: '/system/airport/liveviewStart',
    method: 'get',
    params: query
  })
}
//关闭无人机、机巣视频推流（关闭）
export function getUavVideoInfoOff(query) {
  return request({
    url: '/system/airport/liveviewStop',
    method: 'get',
    params: query
  })
}