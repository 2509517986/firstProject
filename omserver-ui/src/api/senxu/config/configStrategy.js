import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 新增云台配置策略信息
export function add_configStrategy(data) {
    return request({
      url: 'service/configStrategy',
      method: 'post',
      data: data
    })
}

// 修改云台配置策略
export function edit_configStrategy(data) {
    return request({
      url: '/service/configStrategy',
      method: 'put',
      data: data
    })
  }
// 获取云台配置策略详细
export function get_configStrategy_detail(id) {
    return request({
      url: '/service/configStrategy/' + praseStrEmpty(id),
      method: 'get'
    })
}
// 删除云台配置策略详细
export function del_configStrategy(ids) {
  return request({
    url: '/service/configStrategy/' + ids,
    method: 'delete'
  })
}
// 查询云台配置策略列表
export function get_configStrategy_list(query) {
  return request({
    url: '/service/configStrategy/list',
    method: 'get',
    params: query
  })
}