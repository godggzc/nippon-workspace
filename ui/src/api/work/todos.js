import request from '@/utils/request'

// 查询代做事项列表
export function listTodos(query) {
  return request({
    url: '/work/todos/list',
    method: 'get',
    params: query
  })
}

export function listTodosByUser() {

  return request({
    url: '/work/todos/user',
    method: 'get',

  })
}
export function autoSetSysUserTodos() {

  return request({
    url: '/work/todos/auto',
    method: 'get',

  })
}
export function addTodoByUser(data) {
  return request({
    url: '/work/todos/user',
    method: 'post',
    data: data
  })
}

// 查询代做事项详细
export function getTodos(todoId) {
  return request({
    url: '/work/todos/' + todoId,
    method: 'get'
  })
}

// 新增代做事项
export function addTodos(data) {
  return request({
    url: '/work/todos',
    method: 'post',
    data: data
  })
}

// 修改代做事项
export function updateTodos(data) {
  return request({
    url: '/work/todos',
    method: 'put',
    data: data
  })
}

// 删除代做事项
export function delTodos(todoId) {
  return request({
    url: '/work/todos/' + todoId,
    method: 'delete'
  })
}
export function deleteSysUserTodosByUserId() {
  return request({
    url: '/work/todos/user' ,
    method: 'delete'
  })
}

