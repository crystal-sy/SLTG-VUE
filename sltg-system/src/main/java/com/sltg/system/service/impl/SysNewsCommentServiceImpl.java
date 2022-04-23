package com.sltg.system.service.impl;

import com.sltg.common.core.domain.entity.SysNews;
import com.sltg.common.core.domain.entity.SysNewsComment;
import com.sltg.system.mapper.SysNewsCommentMapper;
import com.sltg.system.service.SysNewsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论 服务层实现
 * 
 * @author styra~
 */
@Service
public class SysNewsCommentServiceImpl implements SysNewsCommentService {
    @Autowired
    private SysNewsCommentMapper newsCommentMapper;

    @Override
    public List<SysNewsComment> queryNewsCommentInfo(SysNewsComment comment) {
        return newsCommentMapper.queryNewsCommentInfo(comment);
    }
}
