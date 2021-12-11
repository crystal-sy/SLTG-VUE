package com.sltg.controller.news;

import com.sltg.common.core.controller.BaseController;
import com.sltg.common.core.domain.entity.SysNewForecast;
import com.sltg.common.core.domain.entity.SysNewsKnowledge;
import com.sltg.common.core.page.TableDataInfo;
import com.sltg.system.service.SysForecastService;
import com.sltg.system.service.SysKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 知识库信息
 *
 * @author styra~
 */
@RestController
@RequestMapping("/news/forecast/")
public class SysForecastController extends BaseController {
    @Autowired
    private SysForecastService forcastService;

/*
    */
/**
     * 获取知识库列表
     *//*

    @PreAuthorize("@ss.hasPermi('news:forecast:list')")

    public void  forecastList(SysNewForecast newForecast) {
        startPage();

    }
*/


    /**
     * 获取预测列表
     */
    @PreAuthorize("@ss.hasPermi('news:forecast:list')")
    @GetMapping("/list")
    public TableDataInfo knowledgeList(SysNewForecast newForecast) {
        startPage();
        List<SysNewForecast> list = forcastService.selectForecastList(newForecast);
        return getDataTable(list);
    }


}