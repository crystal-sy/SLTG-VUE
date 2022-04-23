package com.sltg.system.mapper;

import com.sltg.common.core.domain.entity.SysNewsComment;

import java.util.List;

/**
 * 新闻评论库表 数据层
 * 
 * @author styra~
 */
public interface SysNewsCommentMapper {
    /**
     * 查询新闻评论字数
     *
     * @param newsId 新闻id
     * @return 评论数
     */
    int queryNewsCommentCnt(String newsId);

    /**
     * 查新新闻评论信息
     *
     * @param newsId 新闻id
     * @return 评论信息列表
     */
    List<SysNewsComment> queryNewsCommentInfo(String newsId);
}
