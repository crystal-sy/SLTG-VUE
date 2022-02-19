package com.sltg.system.mapper;

import com.sltg.system.domain.SysLoginInfo;

/**
 * 系统访问日志情况信息 数据层
 * 
 * @author styra~
 */
public interface SysLoginInfoMapper {
    /**
     * 新增系统登录日志
     * 
     * @param loginInfo 访问日志对象
     */
    void insertLoginInfo(SysLoginInfo loginInfo);
}
