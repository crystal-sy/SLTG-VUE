package com.sltg.controller.news;

import com.sltg.common.core.controller.BaseController;
import com.sltg.common.core.domain.entity.SysNewsComment;
import com.sltg.common.core.page.TableDataInfo;
import com.sltg.system.service.SysNewsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 新闻评论信息
 * 
 * @author styra~
 */
@RestController
@RequestMapping("/news/comment")
public class SysNewsCommentController extends BaseController {
    @Autowired
    private SysNewsCommentService commentService;

    /**
     * 获取新闻评论列表
     */
    @PreAuthorize("@ss.hasPermi('news:table:list')")
    @GetMapping("/list")
    public TableDataInfo newsList(SysNewsComment comment) {
        startPage();
        List<SysNewsComment> list = commentService.queryNewsCommentInfo(comment);
        return getDataTable(list);
    }
}
