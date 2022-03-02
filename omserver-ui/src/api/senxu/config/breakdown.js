import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 新增云台缺销记录
export function add_breakdown(data) {
    return request({
      url: 'service/breakdown',
      method: 'post',
      data: data
    })
}
// 查询云台缺销记录
export function get_breakdownList(query) {
  return request({
    url: 'service/breakdown/list',
    method: 'get',
    params: query
  })
}
// 修改云台缺销记录
export function edit_breakdownInfo(data) {
  return request({
    url: '/service/breakdown',
    method: 'put',
    data: data
  })
}
// 删除云台缺销记录
export function del_breakdownInfo(ids) {
  return request({
    url: '/service/breakdown/' + ids,
    method: 'delete'
  })
}
