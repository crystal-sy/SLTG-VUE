package com.sltg.system.service.impl;

import com.sltg.common.config.SltgSysConfig;
import com.sltg.common.core.domain.entity.UserNews;
import com.sltg.common.enums.DetectionType;
import com.sltg.system.mapper.UserNewsMapper;
import com.sltg.system.service.UserNewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * 公告 服务层实现
 * 
 * @author styra~
 */
@Service
public class UserNewsServiceImpl implements UserNewsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserNewsServiceImpl.class);

    @Autowired
    private UserNewsMapper userNewsMapper;

    @Override
    public List<UserNews> selectUserNewsList(UserNews news) {
        return userNewsMapper.selectUserNewsList(news);
    }

    @Override
    public UserNews queryUserNewsById(Long newsId) {
        return userNewsMapper.queryUserNewsById(newsId);
    }

    @Override
    public String importData(MultipartFile file) throws Exception {
        String fileId = UUID.randomUUID().toString();
        File tempDir = new File(SltgSysConfig.getUploadPath() + File.separator + fileId);
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            LOGGER.error("mkdirs failed.");
            throw new IOException("上传失败");
        }

        try(FileOutputStream fs = new FileOutputStream(tempDir + File.separator + file.getOriginalFilename());
            InputStream inputStream = file.getInputStream()) {
            byte[] buffer = new byte[1024*1024];
            int byteread;
            while ((byteread = inputStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
                fs.flush();
            }
        }
        return fileId;
    }

    @Override
    public boolean checkUserNewsUnique(String newsTitle, Long userId) {
        UserNews userNews = userNewsMapper.checkUserNewsUnique(newsTitle, userId);
        return userNews == null;
    }

    @Override
    public int insertUserNews(UserNews news) {
        // TODO 调python和获取文件内容
        news.setNewsTopic("test");
        return userNewsMapper.insertUserNews(news);
    }

    @Override
    public int updateUserNews(UserNews news) {
        // TODO 调python和获取文件内容
        return userNewsMapper.updateUserNews(news);
    }

    @Override
    public int deleteUserNewsByIds(Long[] newsIds) {
        return userNewsMapper.deleteUserNewsByIds(newsIds);
    }

    @Override
    public List<UserNews> selectUserNewsByIds(Long[] newsIds, Long userId) {
        List<UserNews> userNewsList = userNewsMapper.selectUserNewsByIds(newsIds, userId);
        for (UserNews userNews: userNewsList) {
            userNews.setDetectionType(DetectionType.getTypeInfo(userNews.getDetectionType()));
        }
        return userNewsList;
    }
}
