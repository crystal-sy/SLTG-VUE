package com.sltg.system.service.impl;

import com.sltg.common.core.domain.entity.SysNewsCollect;
import com.sltg.system.mapper.SysNewsCollectMapper;
import com.sltg.system.service.SysNewsCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏 服务层实现
 * 
 * @author styra~
 */
@Service
public class SysNewsCollectServiceImpl implements SysNewsCollectService {
    @Autowired
    private SysNewsCollectMapper newsCollectMapper;

    @Override
    public int queryNewsCollectCnt(String newsId, Long userId) {
        return newsCollectMapper.queryNewsCollectCnt(newsId, userId);
    }

    @Override
    public List<SysNewsCollect> queryNewsCollectInfo(SysNewsCollect collect) {
        return newsCollectMapper.queryNewsCollectInfo(collect);
    }

    @Override
    public int addCollect(SysNewsCollect collect) {
        return newsCollectMapper.addCollect(collect);
    }

    @Override
    public int deleteCollectById(String collectId) {
        return newsCollectMapper.deleteCollectById(collectId);
    }
}
