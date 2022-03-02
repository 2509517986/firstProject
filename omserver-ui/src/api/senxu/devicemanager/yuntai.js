import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 新增云台信息
export function add_yuntai(data) {
  return request({
    url: 'service/yuntaiInfo',
    method: 'post',
    data: data
  })
}
// 修改云台信息
export function edit_yuntai(data) {
  return request({
    url: 'service/yuntaiInfo',
    method: 'put',
    data: data
  })
}
// 获取云台详情
export function get_yuntai_detail(id) {
  return request({
    url: '/service/yuntaiInfo/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 删除云台详细
export function del_yuntai(ids) {
  return request({
    url: '/service/yuntaiInfo/' + ids,
    method: 'delete'
  })
}
// 获取云台列表
export function get_yuntaiList(query) {
  return request({
    url: '/service/yuntaiInfo/list',
    method: 'get',
    params: query
  })
}
// 导出云台列表
export function export_yuntai(query) {
  return request({
    url: '/service/yuntaiInfo/export',
    method: 'get',
    params: query
  })
}
// 导出云台列表
export function selectTYuntaiTotal(query) {
  return request({
    url: '/service/yuntaiInfo/selectTYuntaiTotal',
    method: 'get',
    params: query
  })
}