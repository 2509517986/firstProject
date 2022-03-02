/*
 * @Author: YJY
 * @Date: 2022-02-01 16:02:59
 * @System: proManagement
 * @Company: LESSO
 * @LastEditTime: 2022-02-10 11:11:29
 * @Description: file content
 */
// 巡检管理
import request from '@/utils/request'
// 查询历史巡检记录
export function get_historical_list(query) {
    return request({
      url: 'system/history/list',
      method: 'get',
      params: query
    })
}
// 查询无人机巡检列表
export function get_uavInspection_list(query) {
    return request({
      url: '/system/taskPicture/list',
      method: 'get',
      params: query
    })
}
// 无人机巡检手动告警
export function set_manual_alarm(query) {
  return request({
    url: '/system/taskPicture',
    method: 'put',
    data: query
  })
}
// 查询巡检报告数据
export function get_report_list(query) {
    return request({
      url: `service/unmannedVehiclesInspectionReport/info`,
      method: 'get',
      // params: query
    })
}
// 导出巡检报告数据
export function export_report(query) {
  return request({
    url: `service/unmannedVehiclesInspectionReport/exportWord`,
    method: 'get',
    responseType : 'blob',
    params: query
  })
}

