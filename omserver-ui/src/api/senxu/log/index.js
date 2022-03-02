import request from '@/utils/request'
// 查询日志列表
export function get_log_list(query) {
    return request({
      url: '/service/log/list',
      method: 'get',
      params: query
    })
}