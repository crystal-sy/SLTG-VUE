package com.sltg.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sltg.common.annotation.Excel;
import com.sltg.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 新闻信息 sys_news
 * 
 * @author styra~
 */
public class SysNews extends BaseEntity {
    private static final long serialVersionUID = 4076941533402724059L;
    
    @Excel(name = "新闻编号")
    private String newsId;

    @Excel(name = "新闻标题")
    private String newsTitle;

    @Excel(name = "是否有新闻标题")
    private String haveTitle;

    @Excel(name = "新闻链接")
    private String newsUrl;

    @Excel(name = "新闻内容")
    private String newsText;

    @Excel(name = "新闻关键词")
    private String newsTheme;

    @Excel(name = "虚假检测百分比")
    private String detectionPercent;

    @Excel(name = "检测类型")
    private String detectionType;

    @Excel(name = "新闻时间")
    private String newsDate;

    @Excel(name = "新闻获取来源")
    private String newsSpider;

    @Excel(name = "新闻真实来源")
    private String newsFrom;

    @Excel(name = "新闻内容是否文件")
    private String isFile;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String[] newsThemes;

    private int comments;

    private boolean collected;

    public SysNews() {}

    public SysNews(String newsId) {
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

    public String getHaveTitle() {
        return haveTitle;
    }

    public void setHaveTitle(String haveTitle) {
        this.haveTitle = haveTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public String getNewsTheme() {
        return newsTheme;
    }

    public void setNewsTheme(String newsTheme) {
        this.newsTheme = newsTheme;
    }

    public String getDetectionPercent() {
        return detectionPercent;
    }

    public void setDetectionPercent(String detectionPercent) {
        this.detectionPercent = detectionPercent;
    }

    public String getDetectionType() {
        return detectionType;
    }

    public void setDetectionType(String detectionType) {
        this.detectionType = detectionType;
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

    public String getIsFile() {
        return isFile;
    }

    public void setIsFile(String isFile) {
        this.isFile = isFile;
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

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
