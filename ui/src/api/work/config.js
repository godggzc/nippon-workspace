import request from '@/utils/request'

// 查询itop用户配置列表
export function listConfig(query) {
  return request({
    url: '/work/config/list',
    method: 'get',
    params: query
  })
}

// 查询itop用户配置详细
export function getConfig(configId) {
  return request({
    url: '/work/config/' + configId,
    method: 'get'
  })
}

// 新增itop用户配置
export function addConfig(data) {
  return request({
    url: '/work/config',
    method: 'post',
    data: data
  })
}

// 修改itop用户配置
export function updateConfig(data) {
  return request({
    url: '/work/config',
    method: 'put',
    data: data
  })
}

// 删除itop用户配置
export function delConfig(configId) {
  return request({
    url: '/work/config/' + configId,
    method: 'delete'
  })
}
