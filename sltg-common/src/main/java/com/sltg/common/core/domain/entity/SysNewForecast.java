package com.sltg.common.core.domain.entity;

import com.sltg.common.annotation.Excel;
import com.sltg.common.core.domain.BaseEntity;

/**
 * @author styra
 * @title: SysNewForecast
 * @projectName SLTG-VUE
 * @description: 新闻预测数据模型
 * @date 2021/10/3016:10
 */
public class SysNewForecast extends BaseEntity {
    private static final long serialVersionUID = 4044789510886192953L;

    @Excel(name = "预测编号", cellType = Excel.ColumnType.NUMERIC, prompt = "预测编号")
    private Long forecastId;


}
