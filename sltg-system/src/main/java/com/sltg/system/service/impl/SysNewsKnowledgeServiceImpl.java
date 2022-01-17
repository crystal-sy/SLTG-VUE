package com.sltg.system.service.impl;

import com.sltg.common.core.domain.entity.SysNewsKnowledge;
import com.sltg.system.mapper.SysNewsKnowledgeMapper;
import com.sltg.system.service.SysKnowledgeService;
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

    @Override
    public List<SysNewsKnowledge> selectKnowledgeList(SysNewsKnowledge news) {
        return newsKnowledgeMapper.selectNewsKnowledgeList(news);
    }

    @Override
    public SysNewsKnowledge queryKnowledgeById(String newsId) {
        return newsKnowledgeMapper.queryNewsKnowledgeById(newsId);
    }
}
