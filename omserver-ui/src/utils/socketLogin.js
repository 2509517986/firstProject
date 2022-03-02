import axios from 'axios'
import qs from 'qs'
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
axios.defaults.timeout = 50000;
axios.defaults.retry = 4;
axios.defaults.retryDelay = 1000;
// 创建axios实例
const services = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: '/socketLogin',
  // 超时
  // timeout: 50000
})

export default services