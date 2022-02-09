package com.sltg.system.mapper;

/**
 * 新闻分析 数据层
 * 
 * @author styra~
 */
public interface SysNewsAnalysisMapper {
    /**
     * 查询新闻数
     *
     * @return 系统新闻数
     */
    int queryNewsTotal();

    /**
     * 查询真实新闻数
     *
     * @return 系统真实新闻数
     */
    int queryRealNewsTotal();

    /**
     * 查询虚假新闻数
     *
     * @return 系统虚假新闻数
     */
    int queryFakeNewsTotal();

    /**
     * 查询用户新闻数
     *
     * @return 系统用户新闻数
     */
    int queryUserNewsTotal();

    /**
     * 查询新闻趋势日期
     *
     * @return 返回日期
     */
    String[] queryTrendDate();

    /**
     * 查询新闻趋势
     *
     * @return 返回日期和新闻数
     */
    Integer[] queryNewsTotalTrend();

    /**
     * 查询虚假新闻趋势
     *
     * @return 返回虚假新闻数
     */
    Integer[] queryRealNewsTrend();
}
