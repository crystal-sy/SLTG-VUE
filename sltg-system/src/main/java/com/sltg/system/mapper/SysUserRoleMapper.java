package com.sltg.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.sltg.system.domain.SysUserRole;

/**
 * 用户与角色关联表 数据层
 * 
 * @author styra~
 */
public interface SysUserRoleMapper {
    /**
     * 通过用户ID删除用户和角色关联
     * 
     * @param userId 用户ID
     */
    void deleteUserRoleByUserId(Long userId);

    /**
     * 批量删除用户和角色关联
     * 
     * @param ids 需要删除的数据ID
     */
    void deleteUserRole(Long[] ids);

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(Long roleId);

    /**
     * 批量新增用户角色信息
     * 
     * @param userRoleList 用户角色列表
     */
    void batchUserRole(List<SysUserRole> userRoleList);
}
