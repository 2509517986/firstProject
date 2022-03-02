import request from '@/utils/request';
import { praseStrEmpty } from "@/utils/ruoyi";

// 获取机巣列表
export function getAirportList (query) {
  return request({
    url: '/system/airport/list',
    method: 'get',
    params: query
  })
}
// 获取任务列表
export function getAirportTastList (query) {
  return request({
    url: '/system/task/list',
    method: 'get',
    params: query
  })
}
// 根据任务id获取具体航线信息
export function getAirportRoadDetail (Str) {
  return request({
    // url: '/system/route/list?taskId='+praseStrEmpty(id),
    url: `/system/route/list${Str}`,
    method: 'get'
  })
}
// 获取所有航线信息
export function getAllAirportRoadList (query) {
  return request({
    url: '/system/route/list',
    method: 'get',
    params: query
  })
}
// 根据任务id获取航线列表
export function getAirportRoadList (id) {
  return request({
    url: '/system/route/task/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 根据任务id查看任务详情
// export function getAirportTaskByTaskId (id) {
//   return request({
//     url: '/system/task/' + praseStrEmpty(id),
//     method: 'get'
//   })
// }
// 根据任务id查看任务详情
export function getAirportTaskByTaskId (id) {
  return request({
    url: '/system/route/task/' + praseStrEmpty(id),
    method: 'get'
  })
}