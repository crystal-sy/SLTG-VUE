package com.sltg.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sltg.common.annotation.Excel;
import com.sltg.common.annotation.Excel.ColumnType;
import com.sltg.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 用户新闻信息 sys_user_news
 * 
 * @author styra~
 */
public class UserNews extends BaseEntity {
    private static final long serialVersionUID = 45437544149292050L;

    @Excel(name = "新闻编号", cellType = ColumnType.NUMERIC, prompt = "新闻编号")
    private Long newsId;

    /** 用户编号 */
    private Long userId;

    @Excel(name = "新闻标题")
    private String newsTitle;

    @Excel(name = "新闻主题")
    private String newsTopic;

    @Excel(name = "新闻日期")
    private String newsDate;

    @Excel(name = "新闻来源")
    private String newsFrom;

    @Excel(name = "虚假检测百分比")
    private String detectionPercent;

    @Excel(name = "检测类型")
    private String detectionType;

    /** 新闻内容 */
    private String newsInfo;

    /** 新闻内容路径 */
    private String newsPath;

    /** 创建时间 */
    @Excel(name = "新闻创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    private Long[] newsIds;

    public UserNews() {}

    public UserNews(Long newsId) {
        this.newsId = newsId;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
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

    public String getNewsTopic() {
        return newsTopic;
    }

    public void setNewsTopic(String newsTopic) {
        this.newsTopic = newsTopic;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsFrom() {
        return newsFrom;
    }

    public void setNewsFrom(String newsFrom) {
        this.newsFrom = newsFrom;
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

    public String getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(String newsInfo) {
        this.newsInfo = newsInfo;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNewsPath() {
        return newsPath;
    }

    public void setNewsPath(String newsPath) {
        this.newsPath = newsPath;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Long[] getNewsIds() {
        return newsIds;
    }

    public void setNewsIds(Long[] newsIds) {
        this.newsIds = newsIds;
    }
}
