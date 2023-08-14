import request from '@/utils/request'

// 查询itop信息列表
export function listItop(query) {
  return request({
    url: '/work/itop/list',
    method: 'get',
    params: query
  })
}

// 查询itop信息详细
export function getItop(UserRequestId) {
  return request({
    url: '/work/itop/' + UserRequestId,
    method: 'get'
  })
}

// 新增itop信息
export function addItop(data) {
  return request({
    url: '/work/itop',
    method: 'post',
    data: data
  })
}

// 修改itop信息
export function updateItop(data) {
  return request({
    url: '/work/itop',
    method: 'put',
    data: data
  })
}

// 删除itop信息
export function delItop(UserRequestId) {
  return request({
    url: '/work/itop/' + UserRequestId,
    method: 'delete'
  })
}
