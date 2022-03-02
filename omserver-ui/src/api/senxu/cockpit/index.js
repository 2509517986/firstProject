/*
 * @Author: YJY
 * @Date: 2022-02-01 10:00:20
 * @System: proManagement
 * @Company: API
 * @LastEditTime: 2022-02-01 10:02:04
 * @Description: 驾驶舱接口
 */
import request from '@/utils/request'

// 新增云台抓拍图片
export function get_cockpitData(data) {
  return request({
    url: 'service/unmannedVehiclesIndex/homeTotal',
    method: 'get',
    data: data
  })
}