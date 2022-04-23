package com.sltg.system.service;

import com.sltg.common.core.domain.entity.SysNewsComment;

import java.util.List;

/**
 * 新闻评论 业务层
 * 
 * @author styra~
 */
public interface SysNewsCommentService {
    /**
     * 查询新闻评论内容
     * @param comment 评论内容
     * @return 评论列表
     */
    List<SysNewsComment> queryNewsCommentInfo(SysNewsComment comment);
}
