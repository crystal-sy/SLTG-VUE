package com.sltg.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sltg.system.domain.SysLoginInfo;
import com.sltg.system.mapper.SysLoginInfoMapper;
import com.sltg.system.service.SysLoginInfoService;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author styra~
 */
@Service
public class SysLoginInfoServiceImpl implements SysLoginInfoService {

    @Autowired
    private SysLoginInfoMapper loginInfoMapper;

    /**
     * 新增系统登录日志
     * 
     * @param loginInfo 访问日志对象
     */
    @Override
    public void insertLoginInfo(SysLoginInfo loginInfo) {
        loginInfoMapper.insertLoginInfo(loginInfo);
    }
}
