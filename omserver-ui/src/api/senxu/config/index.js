import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";
// 获取云台配置记录：终端功能配置
export function getFunction(id) {
    return request({
      url: '/service/yuntaiConfig/getFunction/' + praseStrEmpty(id),
      method: 'get'
    })
}
// 获取云台配置记录：心跳配置参数
export function getHeartbeat(id) {
    return request({
      url: '/service/yuntaiConfig/getHeartbeat/' + praseStrEmpty(id),
      method: 'get'
    })
}
// 获取云台配置记录：图像采集配置
export function getPicture(id) {
    return request({
      url: '/service/yuntaiConfig/getPicture/' + praseStrEmpty(id),
      method: 'get'
    })
}
// 获取云台配置记录：平台参数配置
export function getPlatform(id) {
    return request({
      url: '/service/yuntaiConfig/getPlatform/' + praseStrEmpty(id),
      method: 'get'
    })
}
// 获取云台配置记录：视频参数配置
export function getVideo(id) {
    return request({
      url: '/service/yuntaiConfig/getVideo/' + praseStrEmpty(id),
      method: 'get'
    })
}
// 获取云台配置记录：查询云台配置记录列表
export function get_yuntaiConfig_list(query) {
    return request({
      url: '/service/yuntaiConfig/list',
      method: 'get',
      params: query
    })
}
// 删除云台配置记录
export function del_yuntaiConfig(ids) {
    return request({
      url: '/service/yuntaiConfig/' + ids,
      method: 'delete'
    })
}
// 修改云台配置记录
export function edit_yuntaiConfig(data) {
  return request({
    url: '/service/yuntaiConfig',
    method: 'put',
    data: data
  })
}
// 保存云台配置记录详细
export function saveAllInfo(data) {
  return request({
    url: 'service/yuntaiConfig/saveAllInfo',
    method: 'post',
    data: data
  })
}
// 保存云台配置记录：终端功能配置
export function saveFunction(data) {
  return request({
    url: 'service/yuntaiConfig/saveFunction',
    method: 'post',
    data: data
  })
}
// 保存云台配置记录：心跳配置参数
export function saveHeartbeat(data) {
  return request({
    url: 'service/yuntaiConfig/saveHeartbeat',
    method: 'post',
    data: data
  })
}
// 保存云台配置记录：图像采集配置
export function savePicture(data) {
  return request({
    url: 'service/yuntaiConfig/savePicture',
    method: 'post',
    data: data
  })
}
// 保存云台配置：平台参数配置
export function savePlatform(data) {
  return request({
    url: 'service/yuntaiConfig/savePlatform',
    method: 'post',
    data: data
  })
}
// 保存云台配置：视频参数配置
export function saveVideo(data) {
  return request({
    url: 'service/yuntaiConfig/saveVideo',
    method: 'post',
    data: data
  })
}
// 查询拍照策略
export function getCaptureStrategy(id) {
  return request({
    url: '/service/yuntaiConfig/getCaptureStrategy/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 查询视频策略
export function getVideoStrategy(id) {
  return request({
    url: '/service/yuntaiConfig/getVideoStrategy/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 设置视频策略
export function saveVideoStrategy(data, id) {
  return request({
    url: 'service/yuntaiConfig/saveVideoStrategy?yuntaiId=' + praseStrEmpty(id),
    method: 'post',
    data: data
  })
}
// 设置拍照策略
export function saveCaptureStrategy(data,id) {
  return request({
    url: 'service/yuntaiConfig/saveCaptureStrategy/' + praseStrEmpty(id),
    method: 'post',
    data: data
  })
}
// 文件上传接口
export function uploadFileZip(data) {
  return request({
    url: 'common/uploadFileZip',
    method: 'post',
    data: data
  })
}
// 文件上传信息-查询
export function get_uploadList(query) {
  return request({
    url: 'service/uploadList/list',
    method: 'get',
    params: query
  })
}
// 文件上传信息-修改
export function set_uploadList(data) {
  return request({
    url: 'service/uploadList',
    method: 'put',
    data: data
  })
}
// 文件上传信息-删除
export function del_uploadList(ids) {
  return request({
    url: '/service/uploadList/' + ids,
    method: 'delete'
  })
}
// 设备升级记录-查询
export function get_record(query) {
  return request({
    url: 'service/record/list',
    method: 'get',
    params: query
  })
}
// 设备升级记录-查询
export function add_record(data) {
  return request({
    url: 'service/record',
    method: 'post',
    data: data
  })
}
// 设备升级记录-修改
export function set_record(data) {
  return request({
    url: 'service/record',
    method: 'put',
    data: data
  })
}
// 设备升级记录-删除
export function del_record(ids) {
  return request({
    url: '/service/record/' + ids,
    method: 'delete'
  })
}
// 获取工控机设备时间
export function getDeviceDate(id) {
  return request({
    url: '/service/yuntaiConfig/getDeviceDate/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 修改设备密码
export function setDevicePassword(data,id) {
  return request({
    url: '/service/yuntaiConfig/setDevicePassword/' + praseStrEmpty(id),
    method: 'post',
    data: data
  })
}
// OSD参数查询
export function getOSD(query, id) {
  return request({
    url: '/service/yuntaiConfig/getOSD/' + praseStrEmpty(id),
    method: 'get',
    params: query
  })
}
// OSD参数查询
export function setOSD(data, id) {
  return request({
    url: '/service/yuntaiConfig/setOSD/' + praseStrEmpty(id),
    method: 'post',
    data: data
  })
}
// 系统设备管理-设备校时
export function getTiming(data, id) {
  return request({
    url: '/service/yuntaiConfig/getTiming/' + praseStrEmpty(id),
    method: 'post',
    data: data
  })
}
// 系统设备管理-装置重启
export function restart(id) {
  return request({
    url: '/service/yuntaiConfig/restart/' + praseStrEmpty(id),
    method: 'get'
  })
}


// 系统设备管理-短信唤醒
export function smsWakeUp(id) {
  return request({
    url: '/service/yuntaiConfig/smsWakeUp/' + praseStrEmpty(id),
    method: 'get'
  })
}
// 系统设备管理-下载文件
export function downloadFile(data,id) {
  return request({
    url: '/service/yuntaiConfig/downloadFile/' + praseStrEmpty(id),
    method: 'post',
    data: data
  })
}
