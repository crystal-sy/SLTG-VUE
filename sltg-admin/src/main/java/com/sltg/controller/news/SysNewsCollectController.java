package com.sltg.controller.news;

import com.sltg.common.annotation.Log;
import com.sltg.common.constant.UserConstants;
import com.sltg.common.core.controller.BaseController;
import com.sltg.common.core.domain.AjaxResult;
import com.sltg.common.core.domain.entity.SysNewsCollect;
import com.sltg.common.core.domain.entity.SysNewsComment;
import com.sltg.common.core.domain.entity.SysUser;
import com.sltg.common.core.domain.model.LoginUser;
import com.sltg.common.core.page.TableDataInfo;
import com.sltg.common.enums.BusinessType;
import com.sltg.common.utils.ServletUtils;
import com.sltg.framework.web.service.TokenService;
import com.sltg.system.service.SysNewsCollectService;
import com.sltg.system.service.SysNewsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 新闻收藏信息
 * 
 * @author styra~
 */
@RestController
@RequestMapping("/news/collect")
public class SysNewsCollectController extends BaseController {
    @Autowired
    private SysNewsCollectService collectService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取新闻收藏列表
     */
    @PreAuthorize("@ss.hasPermi('news:table:list')")
    @GetMapping("/list")
    public TableDataInfo newsList(SysNewsCollect collect) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        collect.setUserId(loginUser.getUser().getUserId());
        startPage();
        List<SysNewsCollect> list = collectService.queryNewsCollectInfo(collect);
        return getDataTable(list);
    }

    /**
     * 新增收藏
     */
    @PreAuthorize("@ss.hasPermi('news:table:list')")
    @Log(title = "新增收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addCollect(@Validated @RequestBody SysNewsCollect collect) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        collect.setUserId(loginUser.getUser().getUserId());
        if (collectService.queryNewsCollectCnt(collect.getNewsId(), collect.getUserId()) == 1) {
            return AjaxResult.error("该新闻已收藏!");
        }

        return toAjax(collectService.addCollect(collect));
    }

    /**
     * 删除收藏
     */
    @PreAuthorize("@ss.hasPermi('news:table:list')")
    @Log(title = "删除收藏", businessType = BusinessType.DELETE)
    @DeleteMapping("/{collectId}")
    public AjaxResult delCollect(@PathVariable String collectId) {
        return toAjax(collectService.deleteCollectById(collectId));
    }

}
