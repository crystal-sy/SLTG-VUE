package com.sltg.controller.news;

import com.sltg.common.core.controller.BaseController;
import com.sltg.common.core.domain.AjaxResult;
import com.sltg.common.core.domain.entity.SysNewsKnowledge;
import com.sltg.common.core.domain.model.LoginUser;
import com.sltg.common.core.page.TableDataInfo;
import com.sltg.common.utils.ServletUtils;
import com.sltg.framework.web.service.TokenService;
import com.sltg.system.service.SysKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 知识库信息
 * 
 * @author styra~
 */
@RestController
@RequestMapping("/news/knowledge")
public class SysKnowledgeController extends BaseController {
    @Autowired
    private SysKnowledgeService knowledgeService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取知识库列表
     */
    @PreAuthorize("@ss.hasPermi('news:knowledge:list')")
    @GetMapping("/list")
    public TableDataInfo knowledgeList(SysNewsKnowledge news) {
        startPage();
        List<SysNewsKnowledge> list = knowledgeService.selectKnowledgeList(news);
        return getDataTable(list);
    }

    /**
     * 根据新闻编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('news:knowledge:list')")
    @GetMapping(value = "/detail/{newsId}")
    public AjaxResult getKnowledgeInfo(@PathVariable(value = "newsId", required = false) String newsId) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, knowledgeService.queryKnowledgeById(newsId, userId));
        return ajax;
    }
}
