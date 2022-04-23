package com.sltg.system.service;

import com.sltg.common.core.domain.entity.SysNewsCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 新闻收藏 业务层
 * 
 * @author styra~
 */
public interface SysNewsCollectService {
    /**
     * 查询是否已经收藏过
     *
     * @param newsId 新闻id
     * @param userId 用户id
     * @return 收藏数
     */
    int queryNewsCollectCnt(String newsId, Long userId);

    /**
     * 查询收藏新闻列表
     *
     * @param collect 收藏信息
     * @return 收藏信息列表
     */
    List<SysNewsCollect> queryNewsCollectInfo(SysNewsCollect collect);

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
