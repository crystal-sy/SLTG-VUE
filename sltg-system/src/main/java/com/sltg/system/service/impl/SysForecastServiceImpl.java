package com.sltg.system.service.impl;

import com.sltg.common.core.domain.entity.SysNewForecast;
import com.sltg.common.core.domain.entity.SysNewsKnowledge;
import com.sltg.system.mapper.SysNewsKnowledgeMapper;
import com.sltg.system.service.SysForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author styra
 * @title: SysForecastServiceImpl
 * @projectName SLTG-VUE
 * @description: TODO
 * @date 2021/10/2623:35
 */
@Service
public class SysForecastServiceImpl implements SysForecastService {


    /*@Autowired
    private SysNewsKnowledgeMapper newsForecastMapper;*/


    @Override
    public List<SysNewForecast> selectForecastList(SysNewForecast newForecast) {
        ArrayList  lsit  = new ArrayList();
        return lsit;
    }



}
