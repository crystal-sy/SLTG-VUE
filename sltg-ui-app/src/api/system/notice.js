import request from '@/utils/request'

// 查询消息列表
export function listNotice(query) {
  return request({
    url: '/system/notice/list',
    method: 'get',
    params: query
  })
}
