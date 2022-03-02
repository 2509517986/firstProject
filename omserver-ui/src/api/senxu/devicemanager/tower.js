import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 新增杆塔信息
export function add_tower(data) {
  return request({
    url: 'service/towerInfo',
    method: 'post',
    data: data
  })
}
// 修改杆塔信息
export function edit_tower(data) {
  return request({
    url: 'service/towerInfo',
    method: 'put',
    data: data
  })
}
// 获取杆塔详情
export function get_tower_detail(id) {
  return request({
    url: '/service/towerInfo/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 删除杆塔详细
export function del_tower(ids) {
  return request({
    url: '/service/towerInfo/' + ids,
    method: 'delete'
  })
}
// 获取杆塔列表
export function get_towerList(query) {
  return request({
    url: '/service/towerInfo/list',
    method: 'get',
    params: query
  })
}
// 导出杆塔列表
export function export_tower(query) {
  return request({
    url: '/service/towerInfo/export',
    method: 'get',
    params: query
  })
}