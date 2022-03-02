import request from '@/utils/request'

// 查询云台设备事件列表
export function get_yuntailog_list(query) {
    return request({
      url: '/service/yuntailog/list',
      method: 'get',
      params: query
    })
}
// 导出云台设备事件列表
export function export_yuntailog(query) {
    return request({
      url: '/service/yuntailog/export',
      method: 'get',
      params: query
    })
  }