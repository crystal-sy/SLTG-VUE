package com.sltg.controller.news;

import com.sltg.common.annotation.Log;
import com.sltg.common.constant.Constants;
import com.sltg.common.core.controller.BaseController;
import com.sltg.common.core.domain.AjaxResult;
import com.sltg.common.core.domain.entity.UserNews;
import com.sltg.common.core.domain.model.LoginUser;
import com.sltg.common.core.page.TableDataInfo;
import com.sltg.common.enums.BusinessType;
import com.sltg.common.utils.SecurityUtils;
import com.sltg.common.utils.ServletUtils;
import com.sltg.common.utils.file.FileUtils;
import com.sltg.common.utils.poi.ExcelUtil;
import com.sltg.framework.web.service.TokenService;
import com.sltg.system.service.UserNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 知识库信息
 * 
 * @author styra~
 */
@RestController
@RequestMapping("/user/management")
public class UserManagementController extends BaseController {
    @Autowired
    private UserNewsService userNewsService;

    @Autowired
    private TokenService tokenService;

    /**
     * 获取用户新闻列表
     */
    @PreAuthorize("@ss.hasPermi('user:news:list')")
    @GetMapping("/list")
    public TableDataInfo userNewsList(UserNews news) {
        startPage();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        news.setUserId(loginUser.getUser().getUserId());
        List<UserNews> list = userNewsService.selectUserNewsList(news);
        return getDataTable(list);
    }

    /**
     * 根据新闻编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('user:news:query')")
    @GetMapping(value = { "/detail/{newsId}" })
    public AjaxResult getUserNews(@PathVariable(value = "newsId", required = false) Long newsId) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put(AjaxResult.DATA_TAG, userNewsService.queryUserNewsById(newsId));
        return ajax;
    }

    @Log(title = "文件上传", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        String message = "请上传txt文件！";
        if (file == null || file.isEmpty()) {
            return AjaxResult.error(message);
        }

        if (!Constants.FILE_TYPE_TXT.equals(FileUtils.getFileExtension(file))) {
            return AjaxResult.error(message);
        }
        return AjaxResult.success(userNewsService.importData(file));
    }

    /**
     * 新增用户新闻
     */
    @PreAuthorize("@ss.hasPermi('user:news:add')")
    @Log(title = "新闻管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addUserNews(@Validated @RequestBody UserNews news) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        news.setUserId(loginUser.getUser().getUserId());
        if (!userNewsService.checkUserNewsUnique(news.getNewsTitle(), news.getUserId())) {
            return AjaxResult.error("新增用户新闻'" + news.getNewsTitle() + "'失败，该新闻已存在");
        }

        news.setCreateBy(SecurityUtils.getUsername());
        return toAjax(userNewsService.insertUserNews(news));
    }

    /**
     * 修改用户新闻
     */
    @PreAuthorize("@ss.hasPermi('user:news:edit')")
    @Log(title = "新闻管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult editUserNews(@Validated @RequestBody UserNews news) {
        UserNews userNews = userNewsService.queryUserNewsById(news.getNewsId());
        if (userNews == null) {
            return AjaxResult.error("修改用户新闻失败，该新闻不存在");
        }

        news.setStoreId(userNews.getStoreId());
        news.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userNewsService.updateUserNews(news));
    }

    /**
     * 删除用户新闻
     */
    @PreAuthorize("@ss.hasPermi('user:news:remove')")
    @Log(title = "新闻管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{newsIds}")
    public AjaxResult remove(@PathVariable Long[] newsIds) {
        return toAjax(userNewsService.deleteUserNewsByIds(newsIds));
    }

    @Log(title = "新闻管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('user:news:export')")
    @GetMapping("/export")
    public AjaxResult export(UserNews news) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long userId = loginUser.getUser().getUserId();
        List<UserNews> list = userNewsService.selectUserNewsByIds(news.getNewsIds(), userId);
        ExcelUtil<UserNews> util = new ExcelUtil<>(UserNews.class);
        return util.exportExcel(list, "用户新闻数据");
    }
}
