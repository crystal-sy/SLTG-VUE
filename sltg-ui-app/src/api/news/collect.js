import request from '@/utils/request'

// 查询新闻收藏库列表
export function collectList(query) {
  return request({
    url: '/news/collect/list',
    method: 'get',
    params: query
  })
}

// 删除收藏
export function delCollect(collectId) {
  return request({
    url: '/news/collect/' + collectId,
    method: 'delete'
  })
}

// 新增收藏
export function addCollect(data) {
  return request({
    url: '/news/collect',
    method: 'post',
    data: data
  })
}
