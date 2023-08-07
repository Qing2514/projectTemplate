package com.project.modules.ums.service;



import com.project.modules.ums.model.UmsUser;
import com.project.modules.ums.model.UmsResource;

import java.util.List;

/**
 * 后台用户缓存管理Service
 * @author Qing2514
 */
public interface UmsUserCacheService {

    /**
     * 删除后台用户缓存
     * @param userId 用户ID
     */
    void delUser(Long userId);

    /**
     * 删除后台用户资源列表缓存
     * @param userId 用户ID
     */
    void delResourceList(Long userId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     * @param roleId 角色ID
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     * @param roleIds 角色ID列表
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     * @param resourceId 资源ID
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 获取缓存后台用户信息
     * @param username 用户名
     * @return 用户
     */
    UmsUser getUser(String username);

    /**
     * 设置缓存后台用户信息
     * @param user 用户
     */
    void setUser(UmsUser user);

    /**
     * 获取缓存后台用户资源列表
     * @param userId 用户ID
     * @return 资源列表
     */
    List<UmsResource> getResourceList(Long userId);

    /**
     * 设置后台后台用户资源列表
     * @param userId 用户ID
     * @param resourceList 资源列表
     */
    void setResourceList(Long userId, List<UmsResource> resourceList);

}
