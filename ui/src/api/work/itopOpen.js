import request from '@/utils/request'

// 查询itop信息未处理列表
export function listItopOpen(query) {
  return request({
    url: '/work/itopOpen/list',
    method: 'get',
    params: query
  })
}

// 查询itop信息未处理详细
export function getItopOpen(userRequestId) {
  return request({
    url: '/work/itopOpen/' + userRequestId,
    method: 'get'
  })
}

// 新增itop信息未处理
export function addItopOpen(data) {
  return request({
    url: '/work/itopOpen',
    method: 'post',
    data: data
  })
}

// 修改itop信息未处理
export function updateItopOpen(data) {
  return request({
    url: '/work/itopOpen',
    method: 'put',
    data: data
  })
}

// 删除itop信息未处理
export function delItopOpen(userRequestId) {
  return request({
    url: '/work/itopOpen/' + userRequestId,
    method: 'delete'
  })
}
