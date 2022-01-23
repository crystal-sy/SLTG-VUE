import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/sltg";

// 查询用户新闻列表
export function userNewsList(query) {
  return request({
    url: '/user/management/list',
    method: 'get',
    params: query
  })
}

// 查询用户新闻详细
export function getUserNews(newsId) {
  return request({
    url: '/user/management/detail/' + praseStrEmpty(newsId),
    method: 'get'
  })
}

// 删除用户新闻
export function delUserNews(newsIds) {
  return request({
    url: '/user/management/' + newsIds,
    method: 'delete'
  })
}

// 导出定时任务调度
export function exportUserNews(newsIds) {
  const news = {};
  news.newsIds = newsIds
  return request({
    url: '/user/management/export',
    method: 'get',
    params: news
  })
}

// 新增用户新闻
export function addUserNews(data) {
  return request({
    url: '/user/management',
    method: 'post',
    data: data
  })
}

// 修改用户新闻
export function updateUserNews(data) {
  return request({
    url: '/user/management',
    method: 'put',
    data: data
  })
}
