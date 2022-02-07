package com.sltg.controller.news;

import com.sltg.common.core.controller.BaseController;
import com.sltg.common.core.domain.AjaxResult;
import com.sltg.system.service.SysAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识库信息
 *
 * @author styra~
 */
@RestController
@RequestMapping("/news/analysis/")
public class SysAnalysisController extends BaseController {
    @Autowired
    private SysAnalysisService analysisService;

    /**
     * 根据新闻编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('news:analysis:list')")
    @GetMapping(value = "/newsTotalInfo")
    public AjaxResult getNewsTotalInfo() {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, analysisService.queryNewsTotalInfo());
        return ajax;
    }
}