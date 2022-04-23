import request from '@/utils/request'

// 查询新闻评论库列表
export function commentList(query) {
  return request({
    url: '/news/comment/list',
    method: 'get',
    params: query
  })
}
