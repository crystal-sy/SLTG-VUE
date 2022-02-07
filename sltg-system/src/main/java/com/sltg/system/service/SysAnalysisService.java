package com.sltg.system.service;

import com.sltg.common.core.domain.entity.SysNewsAnalysis;

/**
 * 新闻分析 业务层
 *
 * @author styra~
 */
public interface SysAnalysisService {

    /**
     * 查询新闻汇总信息
     *
     * @return 返回结果
     */
    SysNewsAnalysis queryNewsTotalInfo();
}
