package com.sltg.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sltg.common.annotation.Excel;
import com.sltg.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 新闻知识库信息 sys_news_knowledge
 * 
 * @author styra~
 */
public class SysNewsKnowledge extends BaseEntity {
    private static final long serialVersionUID = -579668501089482074L;

    @Excel(name = "新闻编号")
    private String newsId;

    @Excel(name = "新闻标题")
    private String newsTitle;

    @Excel(name = "新闻链接")
    private String newsUrl;

    @Excel(name = "新闻关键词")
    private String newsTheme;

    @Excel(name = "检测类型")
    private String detectionType;

    @Excel(name = "检测结果")
    private String detectionResult;

    @Excel(name = "新闻时间")
    private String newsDate;

    @Excel(name = "新闻获取来源")
    private String newsSpider;

    @Excel(name = "新闻真实来源")
    private String newsFrom;

    @Excel(name = "新闻原始链接")
    private String originalUrl;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String[] newsThemes;

    public SysNewsKnowledge() {}

    public SysNewsKnowledge(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsTheme() {
        return newsTheme;
    }

    public void setNewsTheme(String newsTheme) {
        this.newsTheme = newsTheme;
    }

    public String getDetectionType() {
        return detectionType;
    }

    public void setDetectionType(String detectionType) {
        this.detectionType = detectionType;
    }

    public String getDetectionResult() {
        return detectionResult;
    }

    public void setDetectionResult(String detectionResult) {
        this.detectionResult = detectionResult;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsSpider() {
        return newsSpider;
    }

    public void setNewsSpider(String newsSpider) {
        this.newsSpider = newsSpider;
    }

    public String getNewsFrom() {
        return newsFrom;
    }

    public void setNewsFrom(String newsFrom) {
        this.newsFrom = newsFrom;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String[] getNewsThemes() {
        return newsThemes;
    }

    public void setNewsThemes(String[] newsThemes) {
        this.newsThemes = newsThemes;
    }
}
