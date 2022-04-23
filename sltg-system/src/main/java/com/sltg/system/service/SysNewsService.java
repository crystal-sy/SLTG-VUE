package com.sltg.system.service;

import com.sltg.common.core.domain.entity.SysNews;

import java.util.List;

/**
 * 新闻 业务层
 * 
 * @author styra~
 */
public interface SysNewsService {
    /**
     * 根据条件分页查询新闻列表
     * 
     * @param news 新闻信息
     * @return 新闻信息集合信息
     */
    List<SysNews> selectNewsList(SysNews news);

    /**
     * 通过新闻ID查询新闻详情
     * 
     * @param newsId 新闻ID
     * @param userId 用户id
     * @return 新闻对象信息
     */
    SysNews queryNewsById(String newsId, Long userId);
}
