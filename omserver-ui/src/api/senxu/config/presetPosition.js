import request from '@/utils/request'

// 查询云台设备事件列表（废弃）
export function get_presetPosition_list(query) {
    return request({
      url: '/service/presetPosition/list',
      method: 'get',
      params: query
    })
}
// 导出云台设备事件列表(废弃)
export function export_presetPosition(query) {
    return request({
      url: '/service/presetPosition/export',
      method: 'get',
      params: query
    })
  }