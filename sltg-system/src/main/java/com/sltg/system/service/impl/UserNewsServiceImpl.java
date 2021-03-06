package com.sltg.system.service.impl;

import com.sltg.common.config.SltgSysConfig;
import com.sltg.common.constant.Constants;
import com.sltg.common.core.domain.entity.UserNews;
import com.sltg.common.enums.DetectionType;
import com.sltg.common.utils.PythonUtils;
import com.sltg.common.utils.file.FileUtils;
import com.sltg.system.mapper.SysNewsCollectMapper;
import com.sltg.system.mapper.SysNewsCommentMapper;
import com.sltg.system.mapper.UserNewsMapper;
import com.sltg.system.service.UserNewsService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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

    @Value("${sltg.python.script-path}")
    private String pythonScriptPath;

    @Value("${sltg.python.lib-path}")
    private String pythonLibPath;

    @Autowired
    private UserNewsMapper userNewsMapper;

    @Autowired
    private SysNewsCommentMapper newsCommentMapper;

    @Autowired
    private SysNewsCollectMapper collectMapper;

    @Override
    public List<UserNews> selectUserNewsList(UserNews news) {
        return userNewsMapper.selectUserNewsList(news);
    }

    @Override
    public UserNews queryUserNewsById(Long newsId, Long userId) {
        UserNews userNews = userNewsMapper.queryUserNewsById(newsId);
        userNews.setComments(newsCommentMapper.queryNewsCommentCnt(newsId.toString()));
        userNews.setCollected(collectMapper.queryNewsCollectCnt(newsId.toString(), userId) == 1);
        userNews.setNewsThemes(userNews.getNewsTheme().split(","));
        return userNews;
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
    public UserNews checkUserNewsUnique(String newsTitle, Long userId) {
        return userNewsMapper.checkUserNewsUnique(newsTitle, userId);
    }

    @Override
    public int insertUserNews(UserNews news) {
        Long userId = news.getUserId();
        String storeId = UUID.randomUUID().toString();
        news.setStoreId(storeId);

        String contentFilePath = SltgSysConfig.getUserContent() + File.separator + userId + File.separator + storeId;
        news.setContentFile(FileUtils.saveFile(news.getContentFile(), new File(contentFilePath)));

        String commentFileId = news.getCommentFile();
        String commentFilePath = "";
        if (Strings.isNotBlank(commentFileId)) {
            commentFilePath = SltgSysConfig.getUserComment() + File.separator + userId + File.separator + storeId;
            news.setCommentFile(FileUtils.saveFile(commentFileId, new File(commentFilePath)));
            commentFilePath += File.separator + news.getCommentFile();
        }

        contentFilePath += File.separator + news.getContentFile();
        String themes = new PythonUtils().executePythonScript(new String[]{"cmd", "/c",
            "python " + pythonScriptPath + "util/common.py", contentFilePath}, pythonLibPath);
        news.setNewsTheme(themes);

        String detectionPercent = new PythonUtils().executePythonScript(new String[]{"cmd", "/c",
            "python " + pythonScriptPath + "news_detection.py", contentFilePath, commentFilePath}, pythonLibPath);
        String[] detectionPercents = detectionPercent.split("\n");
        news.setDetectionPercent(detectionPercents[0] + "%");
        news.setDetectionType(getDetectionType(detectionPercents[1]));
        return userNewsMapper.insertUserNews(news);
    }

    @Override
    public int updateUserNews(UserNews news) {
        Long userId = news.getUserId();
        String storeId = news.getStoreId();
        String contentFileId = news.getContentFile();
        String contentFilePath = SltgSysConfig.getUserContent() + File.separator + userId + File.separator + storeId;
        if (!contentFileId.contains(Constants.FILE_EXTENSION_TXT)) {
            news.setContentFile(FileUtils.saveFile(contentFileId, new File(contentFilePath)));

            contentFilePath += File.separator + news.getContentFile();
            String themes = new PythonUtils().executePythonScript(new String[]{"cmd", "/c",
                "python " + pythonScriptPath + "util/common.py", contentFilePath}, pythonLibPath);
            news.setNewsTheme(themes);
        } else {
            contentFilePath += File.separator + news.getContentFile();
        }

        String commentFileId = news.getCommentFile();
        String commentFilePath = "";
        if (Strings.isNotBlank(commentFileId) && !commentFileId.contains(Constants.FILE_EXTENSION_TXT)) {
            commentFilePath = SltgSysConfig.getUserComment() + File.separator + userId + File.separator + storeId;
            news.setCommentFile(FileUtils.saveFile(commentFileId, new File(commentFilePath)));
            commentFilePath += File.separator + news.getCommentFile();
        }

        String detectionPercent = new PythonUtils().executePythonScript(new String[]{"cmd", "/c",
            "python " + pythonScriptPath + "news_detection.py", contentFilePath, commentFilePath}, pythonLibPath);
        String[] detectionPercents = detectionPercent.split("\n");
        news.setDetectionPercent(detectionPercents[0] + "%");
        news.setDetectionType(getDetectionType(detectionPercents[1]));
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

    private String getDetectionType(String detectionPercent) {
        if ("1".equals(detectionPercent)) {
            return "2";
        }

        if ("0".equals(detectionPercent)) {
            return "0";
        }

        if (Strings.isBlank(detectionPercent) || !detectionPercent.contains("%")) {
            return "5"; // 其他
        }

        BigDecimal coverData = new BigDecimal(detectionPercent.replace("%",""));
        if (coverData.compareTo(new BigDecimal(90)) > 0) {
            return "0"; // 虚假
        } else if (coverData.compareTo(new BigDecimal(70)) > 0) {
            return "1"; // 疑似诈骗
        } else if (coverData.compareTo(new BigDecimal(50)) > 0) {
            return "4"; // 分情况
        } else if (coverData.compareTo(new BigDecimal(30)) > 0) {
            return "3"; // 待定
        } else {
            return "2"; // 真实
        }
    }
}
