import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/ruoyi";

// 新增云台抓拍图片
export function add_picture(data) {
  return request({
    url: 'service/yuntaPicture',
    method: 'post',
    data: data
  })
}
// 修改云台抓拍图片
export function edit_picture(data) {
  return request({
    url: 'service/yuntaPicture',
    method: 'put',
    data: data
  })
}
// 获取云台抓拍图片详细
export function get_picture_detail(id) {
    return request({
        url: '/service/yuntaPicture/' + praseStrEmpty(id),
        method: 'get'
    })
}
// 删除云台抓拍图片
export function del_picture(ids) {
  return request({
    url: '/service/yuntaPicture/' + ids,
    method: 'delete'
  })
}
// 导出云台抓拍图片列表
export function export_picture(query) {
    return request({
      url: '/service/yuntaPicture/export',
      method: 'get',
      params: query
    })
  }
// 查询云台抓拍图片列表
export function get_pictureList(query) {
  return request({
    url: '/service/yuntaPicture/list',
    method: 'get',
    params: query
  })
}
// 查询云台今日抓拍图片列表
export function get_pictureListByDay(query) {
    return request({
      url: '/service/yuntaPicture/listByDay',
      method: 'get',
      params: query
    })
}
// 查询今日抓拍详情图片列表，支持分页查询
export function get_pictureListByDetails(query) {
  return request({
    url: '/service/yuntaPicture/ListByDetails',
    method: 'get',
    params: query
  })
}
  // 查询云台历史抓拍图片列表
export function get_pictureHistory(query) {
    return request({
      url: '/service/yuntaPicture/listByHistory',
      method: 'get',
      params: query
    })
}
// 标识图片已预览批量
export function yuntaiPreview(data) {
  return request({
    url: '/service/yuntaiPreview',
    method: 'post',
    data: data
  })
}
// 手动抓拍图片
export function manualCap(data) {
  return request({
    url: '/service/yuntaPicture/manualCap',
    method: 'post',
    data: data
  })
}
// 抓拍小视频
export function capShortVideo(data) {
  return request({
    url: '/service/yuntaPicture/capShortVideo',
    method: 'post',
    data: data
  })
}