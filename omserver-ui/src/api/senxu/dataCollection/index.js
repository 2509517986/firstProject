import request from '@/utils/request'
// 导出气象列表
export function export_mete(query) {
  return request({
    url: '/system/mete/export/system/mete/list',
    method: 'get',
    params: query
  })
}
// 查询气象列表
export function get_mete(query) {
  return request({
    url: '/system/mete/list',
    method: 'get',
    params: query
  })
}
// 导出导线温度采样表
export function export_temperature(query) {
  return request({
    url: '/system/temperature/export',
    method: 'get',
    params: query
  })
}
// 查询导线温度采样表
export function get_temperature(query) {
  return request({
    url: '/system/temperature/list',
    method: 'get',
    params: query
  })
}
// 导出导线振动采样表
export function export_conductorVibration(query) {
  return request({
    url: '/system/conductorVibration/export',
    method: 'get',
    params: query
  })
}
// 查询导线振动采样表
export function get_conductorVibration(query) {
  return request({
    url: '/system/conductorVibration/list',
    method: 'get',
    params: query
  })
}
// 导出导线舞动振幅频率
export function export_amplitude(query) {
  return request({
    url: '/system/amplitude/export',
    method: 'get',
    params: query
  })
}
// 查询导线舞动振幅频率
export function get_amplitude(query) {
  return request({
    url: '/system/amplitude/list',
    method: 'get',
    params: query
  })
}
// 导出杆塔振动
export function export_vibration(query) {
  return request({
    url: '/system/vibration/export',
    method: 'get',
    params: query
  })
}
// 查询杆塔振动
export function get_vibration(query) {
  return request({
    url: '/system/vibration/list',
    method: 'get',
    params: query
  })
}
// 导出杆塔倾斜数据
export function export_tilt(query) {
  return request({
    url: '/system/tilt/export',
    method: 'get',
    params: query
  })
}
// 查询杆塔倾斜数据
export function get_tilt(query) {
  return request({
    url: '/system/tilt/list',
    method: 'get',
    params: query
  })
}
// 导出杆塔导地线
export function export_pull(query) {
  return request({
    url: '/system/pull/export',
    method: 'get',
    params: query
  })
}
// 查询杆塔导地线
export function get_pull(query) {
  return request({
    url: '/system/pull/list',
    method: 'get',
    params: query
  })
}
// 导出报警-综合防盗数据表
export function export_antitheft(query) {
  return request({
    url: '/system/antitheft/export',
    method: 'get',
    params: query
  })
}
// 查询报警-综合防盗数据表
export function get_antitheft(query) {
  return request({
    url: '/system/antitheft/list',
    method: 'get',
    params: query
  })
}
// 导出报警-山火告警表
export function export_fire_alarm(query) {
  return request({
    url: '/system/alarm/export',
    method: 'get',
    params: query
  })
}
// 查询报警-山火告警表
export function get_fire_alarm(query) {
  return request({
    url: '/system/alarm/list',
    method: 'get',
    params: query
  })
}
// 导出报警-大风舞动报警表
export function export_danced(query) {
  return request({
    url: '/system/danced/export',
    method: 'get',
    params: query
  })
}
// 查询报警-大风舞动报警表
export function get_danced(query) {
  return request({
    url: '/system/danced/list',
    method: 'get',
    params: query
  })
}
// 导出报警-设备故障报警表
export function export_failure(query) {
  return request({
    url: '/system/failure/export',
    method: 'get',
    params: query
  })
}
// 查询报警-设备故障报警表
export function get_failure(query) {
  return request({
    url: '/system/failure/list',
    method: 'get',
    params: query
  })
}

// 查询实时数据
export function get_realtimeData(query) {
  return request({
    url: '/system/realtimeData/list/'+ praseStrEmpty(id),
    method: 'get',
    params: query
  })
}