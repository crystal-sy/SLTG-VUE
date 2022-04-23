package com.sltg.system.service.impl;

import com.sltg.common.core.domain.entity.SysNewsKnowledge;
import com.sltg.system.mapper.SysNewsCommentMapper;
import com.sltg.system.mapper.SysNewsKnowledgeMapper;
import com.sltg.system.service.SysKnowledgeService;
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
public class SysNewsKnowledgeServiceImpl implements SysKnowledgeService {
    @Autowired
    private SysNewsKnowledgeMapper newsKnowledgeMapper;

    @Autowired
    private SysNewsCommentMapper newsCommentMapper;

    @Override
    public List<SysNewsKnowledge> selectKnowledgeList(SysNewsKnowledge news) {
        String newsTheme = news.getNewsTheme();
        if (Strings.isNotBlank(newsTheme)) {
            news.setNewsThemes(newsTheme.replace("，", ",").split(","));
        }
        return newsKnowledgeMapper.selectNewsKnowledgeList(news);
    }

    @Override
    public SysNewsKnowledge queryKnowledgeById(String newsId) {
        SysNewsKnowledge newsKnowledge = newsKnowledgeMapper.queryNewsKnowledgeById(newsId);
        newsKnowledge.setComments(newsCommentMapper.queryNewsCommentCnt(newsId));
        return newsKnowledge;
    }
}
