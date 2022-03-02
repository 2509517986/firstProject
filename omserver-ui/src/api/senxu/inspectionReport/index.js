import request from '@/utils/request'

// 新增巡检告警记录
export function add_inspectionReport(data) {
  return request({
    url: '/service/inspectionReport',
    method: 'post',
    data: data
  })
}
// 获取巡检报告明细
export function get_inspectionReport(query) {
  return request({
    url: '/service/inspectionReport/findReportByDate',
    method: 'get',
    params: query
  })
}
// 导出巡检报告列表
export function export_inspectionReport(query) {
  return request({
    url: '/service/inspectionReport/export',
    method: 'get',
    params: query
  })
}
// 查询巡检报告图片列表-编辑修改查看详情
export function get_inspectionReport_id(ids) {
  return request({
    url: '/service/inspectionReport/' + ids,
    method: 'get'
  })
}
// 查询巡检报告图片列表
export function get_report_pictureList(query) {
  return request({
    url: '/service/inspectionReport/pictureList',
    method: 'get',
    params: query
  })
}

// 删除巡检报告
export function del_inspectionReport(ids) {
  return request({
    url: '/service/inspectionReport/' + ids,
    method: 'delete'
  })
}
// 查询巡检报告列表
export function get_inspectionReport_list(query) {
  return request({
    url: '/service/inspectionReport/list',
    method: 'get',
    params: query
  })
}
// 查询巡检报告列表 
export function get_inspectionReport_listByDay(query) {
  return request({
    url: '/service/inspectionReport/listByDay',
    method: 'get',
    params: query
  })
}