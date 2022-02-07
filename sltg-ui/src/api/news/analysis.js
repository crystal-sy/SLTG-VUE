import request from '@/utils/request'

// 查询系统新闻汇总信息
export function getNewsTotalInfo() {
  return request({
    url: '/news/analysis/newsTotalInfo',
    method: 'get'
  })
}
