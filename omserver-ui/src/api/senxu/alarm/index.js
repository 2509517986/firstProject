import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 新增云台告警
export function add_alarm_picture(data) {
  return request({
    url: 'service/yuntaiAlarm/add',
    method: 'post',
    data: data
  })
}
// 修改告警浏览状态
export function get_editAlarm(query) {
  return request({
    url: '/service/yuntaiAlarm/editAlarm',
    method: 'get',
    params: query
  })
}
// 修改云台抓告警
export function edit_yuntaiAlarm(data) {
  return request({
    url: '/service/yuntaiAlarm',
    method: 'put',
    data: data
  })
}
// 获取云台抓告警详细
export function get_yuntaiAlarm_detail(id) {
  return request({
    url: '/service/yuntaiAlarm/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 删除云台抓告警
export function del_yuntaiAlarm(ids) {
  return request({
    url: '/service/yuntaiAlarm/' + ids,
    method: 'delete'
  })
}
// 批量确认告警
export function get_batchConfirm(query) {
  return request({
    url: '/service/yuntaiAlarm/batchConfirm',
    method: 'get',
    params: query
  })
}
// 导出云台抓告警列表
export function export_yuntaiAlarm(query) {
  return request({
    url: '/service/yuntaiAlarm/export',
    method: 'get',
    params: query
  })
}
// 查询云台抓告警列表
export function ge_yuntaiAlarm_list(query) {
  return request({
    url: '/service/yuntaiAlarm/list',
    method: 'get',
    params: query
  })
}
// 查询云台抓告警列表2
export function ge_yuntaiAlarm_list2(query) {
  return request({
    url: '/service/yuntaiAlarm/listByDetail',
    method: 'get',
    params: query
  })
}
// 单条记录确认告警
export function singleConfirm(query) {
  return request({
    url: '/service/yuntaiAlarm/singleConfirm',
    method: 'get',
    params: query
  })
}