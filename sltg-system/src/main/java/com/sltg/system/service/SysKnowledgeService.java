package com.sltg.system.service;

import com.sltg.common.core.domain.entity.SysNewsKnowledge;

import java.util.List;

/**
 * 知识库 业务层
 * 
 * @author styra~
 */
public interface SysKnowledgeService {
    /**
     * 根据条件分页查询新闻知识库列表
     * 
     * @param news 新闻知识库信息
     * @return 新闻知识库信息集合信息
     */
    List<SysNewsKnowledge> selectKnowledgeList(SysNewsKnowledge news);

    /**
     * 通过新闻ID查询新闻知识库详情
     * 
     * @param newsId 新闻ID
     * @param userId 用户id
     * @return 新闻知识库对象信息
     */
    SysNewsKnowledge queryKnowledgeById(String newsId, Long userId);
}
