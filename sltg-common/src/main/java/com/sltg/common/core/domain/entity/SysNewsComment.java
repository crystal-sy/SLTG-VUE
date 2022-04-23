package com.sltg.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sltg.common.annotation.Excel;
import com.sltg.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 新闻评论库信息 sys_news_comment
 * 
 * @author styra~
 */
public class SysNewsComment extends BaseEntity {
    private static final long serialVersionUID = 2763427491374273197L;

    @Excel(name = "评论编号")
    private String commentId;

    @Excel(name = "新闻编号")
    private String newsId;

    @Excel(name = "用户id")
    private String userId;

    @Excel(name = "用户名称")
    private String userName;

    @Excel(name = "评论内容")
    private String commentText;

    @Excel(name = "源评论id")
    private String rootId;

    @Excel(name = "评论时间")
    private String commentTime;

    @Excel(name = "点赞数")
    private int likeCount;

    @Excel(name = "情感百分比")
    private String sentimentPercent;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public SysNewsComment() {}

    public SysNewsComment(String newsId) {
        this.newsId = newsId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getSentimentPercent() {
        return sentimentPercent;
    }

    public void setSentimentPercent(String sentimentPercent) {
        this.sentimentPercent = sentimentPercent;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
