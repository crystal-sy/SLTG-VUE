package com.sltg.system.mapper;

import com.sltg.common.core.domain.entity.SysNewsCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 新闻收藏库表 数据层
 * 
 * @author styra~
 */
public interface SysNewsCollectMapper {
    /**
     * 查询是否收藏
     *
     * @param newsId 新闻id
     * @param userId 用户id
     * @return 收藏数
     */
    int queryNewsCollectCnt(@Param("newsId") String newsId, @Param("userId") Long userId);

    /**
     * 查询新闻收藏列表
     *
     * @param comment 评论信息
     * @return 评论信息列表
     */
    List<SysNewsCollect> queryNewsCollectInfo(SysNewsCollect comment);

    /**
     * 新增收藏
     *
     * @param collect 收藏信息
     * @return 结果
     */
    int addCollect(SysNewsCollect collect);

    /**
     * 删除收藏
     *
     * @param collectId 收藏id
     * @return 结果
     */
    int deleteCollectById(String collectId);
}
