package com.sltg.system.service.impl;

import com.sltg.common.core.domain.entity.SysNewsAnalysis;
import com.sltg.system.mapper.SysNewsAnalysisMapper;
import com.sltg.system.service.SysAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private SysNewsAnalysisMapper sysNewsAnalysis;

    @Override
    public SysNewsAnalysis queryNewsTotalInfo() {
        SysNewsAnalysis newsAnalysis = new SysNewsAnalysis();
        newsAnalysis.setNewsTotalNum(sysNewsAnalysis.queryNewsTotal());
        newsAnalysis.setNewsRealNum(sysNewsAnalysis.queryRealNewsTotal());
        newsAnalysis.setNewsFakeNum(sysNewsAnalysis.queryFakeNewsTotal());
        newsAnalysis.setNewsUserNum(sysNewsAnalysis.queryUserNewsTotal());
        newsAnalysis.setNewsTrendDate(sysNewsAnalysis.queryTrendDate());
        Integer[] newsTotalTrend = sysNewsAnalysis.queryNewsTotalTrend();
        newsAnalysis.setNewsTrendNews(newsTotalTrend);
        Integer[] realNewsTrend = sysNewsAnalysis.queryRealNewsTrend();

        Integer[] fakeNewsTrend = new Integer[30];
        for (int i = 0; i < newsTotalTrend.length; i++) {
            fakeNewsTrend[i] = newsTotalTrend[i] - realNewsTrend[i];

        }
        newsAnalysis.setNewsTrendFakeNews(fakeNewsTrend);
        return newsAnalysis;
    }
}
