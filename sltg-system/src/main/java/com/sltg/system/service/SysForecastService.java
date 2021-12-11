package com.sltg.system.service;



import com.sltg.common.core.domain.entity.SysNewForecast;
import com.sltg.common.core.domain.entity.SysNewsKnowledge;

import java.util.List;

/**
 * 新闻预测 业务层
 *
 * @author styra~
 */
public interface SysForecastService {


    /**
     * 根据条件分页查询新闻知识库列表
     *
     * @param news 新闻知识库信息
     * @return 新闻知识库信息集合信息
     */
    List<SysNewForecast> selectForecastList(SysNewForecast newForecast);




}
