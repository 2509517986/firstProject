import request from '@/utils/request';
import { praseStrEmpty } from "@/utils/ruoyi";

// 通用上传请求，支持带子目录参数
export function uploadFile(data, subdirectory) {
  return request({
    url: '/common/uploadFile?subdirectory=' + subdirectory,
    method: 'post',
    data: data,
    type: 'form'
  })
}