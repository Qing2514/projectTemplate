package com.project.modules.ums.service;


import com.project.modules.ums.model.UmsUser;
import com.project.modules.ums.model.UmsResource;

import java.util.List;

/**
 * 后台用户缓存管理 Service
 *
 * @author Qing2514
 */
public interface UmsUserCacheService {

    /**
     * 根据用户名查询用户缓存
     *
     * @param username 用户名
     * @return 用户
     */
    UmsUser getUserByUsername(String username);

    /**
     * 根据用户ID查询资源列表缓存
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    List<UmsResource> getResourceListByUserId(Long userId);

    /**
     * 添加用户缓存
     *
     * @param user 用户
     */
    void setUser(UmsUser user);

    /**
     * 根据用户ID添加资源列表缓存
     *
     * @param userId       用户ID
     * @param resourceList 资源列表
     */
    void setResourceList(Long userId, List<UmsResource> resourceList);

    /**
     * 根据用户ID删除用户缓存
     *
     * @param userId 用户ID
     */
    void delUserByUserId(Long userId);

    /**
     * 根据用户ID删除资源列表缓存
     *
     * @param userId 用户ID
     */
    void delResourceListByUserId(Long userId);

    /**
     * 根据角色ID删除资源列表缓存
     *
     * @param roleId 角色ID
     */
    void delResourceListByRoleId(Long roleId);

    /**
     * 根据角色ID列表删除资源列表缓存
     *
     * @param roleIds 角色ID列表
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 根据资源Id删除资源列表缓存
     *
     * @param resourceId 资源ID
     */
    void delResourceListByResourceId(Long resourceId);

}
