package com.sltg.system.service.impl;

import com.sltg.common.core.domain.entity.SysNewsAnalysis;
import com.sltg.system.service.SysAnalysisService;
import org.springframework.stereotype.Service;

/**
 * @author styra
 * @title: SysAnalysisServiceImpl
 * @projectName SLTG-VUE
 * @description: 新闻分析
 * @date 2021/10/2623:35
 */
@Service
public class SysAnalysisServiceImpl implements SysAnalysisService {

    @Override
    public SysNewsAnalysis queryNewsTotalInfo() {
        return new SysNewsAnalysis();
    }
}
