package com.sltg.system.service.impl;

import com.sltg.common.core.domain.entity.SysNews;
import com.sltg.system.mapper.SysNewsCollectMapper;
import com.sltg.system.mapper.SysNewsCommentMapper;
import com.sltg.system.mapper.SysNewsMapper;
import com.sltg.system.service.SysNewsService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告 服务层实现
 * 
 * @author styra~
 */
@Service
public class SysNewsServiceImpl implements SysNewsService {
    @Autowired
    private SysNewsMapper newsMapper;

    @Autowired
    private SysNewsCommentMapper newsCommentMapper;

    @Autowired
    private SysNewsCollectMapper collectMapper;

    @Override
    public List<SysNews> selectNewsList(SysNews news) {
        String newsTheme = news.getNewsTheme();
        if (Strings.isNotBlank(newsTheme)) {
            news.setNewsThemes(newsTheme.replace("，", ",").split(","));
        }
        return newsMapper.selectNewsList(news);
    }

    @Override
    public SysNews queryNewsById(String newsId, Long userId) {
        SysNews sysNews = newsMapper.queryNewsById(newsId);
        sysNews.setComments(newsCommentMapper.queryNewsCommentCnt(newsId));
        sysNews.setCollected(collectMapper.queryNewsCollectCnt(newsId, userId) == 1);
        sysNews.setNewsThemes(sysNews.getNewsTheme().split(","));
        return sysNews;
    }
}
