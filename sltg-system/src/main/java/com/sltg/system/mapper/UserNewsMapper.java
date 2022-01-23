package com.sltg.system.mapper;

import com.sltg.common.core.domain.entity.UserNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户新闻表 数据层
 * 
 * @author styra~
 */
public interface UserNewsMapper {
    /**
     * 查询用户新闻列表
     *
     * @param news 用户新闻信息
     * @return 用户新闻集合
     */
    List<UserNews> selectUserNewsList(UserNews news);

    /**
     * 查询用户新闻信息
     *
     * @param newsId 用户新闻ID
     * @return 用户新闻信息
     */
    UserNews queryUserNewsById(Long newsId);

    /**
     * 校验新闻名称是否唯一
     *
     * @param newsTitle 新闻名称
     * @param userId 用户id
     * @return 返回结果
     */
    UserNews checkUserNewsUnique(@Param("newsTitle") String newsTitle, @Param("userId") Long userId);

    /**
     * 新增用户新闻信息
     *
     * @param news 用户新闻信息
     * @return 结果
     */
    int insertUserNews(UserNews news);

    /**
     * 修改用户新闻信息
     *
     * @param news 用户新闻信息
     * @return 结果
     */
    int updateUserNews(UserNews news);

    /**
     * 批量删除用户新闻信息
     *
     * @param newsIds 需要删除的新闻ID
     * @return 结果
     */
    int deleteUserNewsByIds(Long[] newsIds);

    /**
     * 获取用户所选的新闻列表
     *
     * @param newsIds 需要导出的新闻ID
     * @param userId 用户id
     * @return 新闻列表
     */
    List<UserNews> selectUserNewsByIds(@Param("newsIds") Long[] newsIds, @Param("userId") Long userId);


}
