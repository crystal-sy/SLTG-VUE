package com.sltg.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sltg.common.annotation.Excel;
import com.sltg.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 新闻收藏库信息 sys_news_collect
 * 
 * @author styra~
 */
public class SysNewsCollect extends BaseEntity {
    private static final long serialVersionUID = 6248997432485644178L;

    @Excel(name = "收藏编号")
    private String collectId;

    @Excel(name = "新闻编号")
    private String newsId;

    @Excel(name = "用户id")
    private Long userId;

    @Excel(name = "新闻标题")
    private String newsTitle;

    @Excel(name = "新闻来源")
    private String newsFrom;

    @Excel(name = "新闻日期")
    private String newsDate;

    @Excel(name = "新闻信息表")
    private String newsTable;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private int comments;

    public SysNewsCollect() {}

    public SysNewsCollect(String newsId) {
        this.newsId = newsId;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsFrom() {
        return newsFrom;
    }

    public void setNewsFrom(String newsFrom) {
        this.newsFrom = newsFrom;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsTable() {
        return newsTable;
    }

    public void setNewsTable(String newsTable) {
        this.newsTable = newsTable;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
