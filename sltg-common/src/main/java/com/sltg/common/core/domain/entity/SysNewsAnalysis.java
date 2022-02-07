package com.sltg.common.core.domain.entity;

import com.sltg.common.core.domain.BaseEntity;

/**
 * 新闻分析
 * 
 * @author styra~
 */
public class SysNewsAnalysis extends BaseEntity {
    private static final long serialVersionUID = 7856178909117317045L;

    private int newsTotalNum;

    private int newsRealNum;

    private int newsFakeNum;

    private int newsUserNum;

    public int getNewsTotalNum() {
        return newsTotalNum;
    }

    public void setNewsTotalNum(int newsTotalNum) {
        this.newsTotalNum = newsTotalNum;
    }

    public int getNewsRealNum() {
        return newsRealNum;
    }

    public void setNewsRealNum(int newsRealNum) {
        this.newsRealNum = newsRealNum;
    }

    public int getNewsFakeNum() {
        return newsFakeNum;
    }

    public void setNewsFakeNum(int newsFakeNum) {
        this.newsFakeNum = newsFakeNum;
    }

    public int getNewsUserNum() {
        return newsUserNum;
    }

    public void setNewsUserNum(int newsUserNum) {
        this.newsUserNum = newsUserNum;
    }
}
