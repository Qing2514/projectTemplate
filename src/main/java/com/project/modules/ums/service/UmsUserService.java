package com.project.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.modules.ums.dto.UmsUserParam;
import com.project.modules.ums.dto.UpdatePasswordParam;
import com.project.modules.ums.model.UmsUser;
import com.project.modules.ums.model.UmsResource;
import com.project.modules.ums.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理员管理Service
 *
 * @author Qing2514
 */
public interface UmsUserService extends IService<UmsUser> {

    /**
     * 根据用户名获取后台管理员
     *
     * @param username 用户名
     * @return 用户
     */
    UmsUser getUserByUsername(String username);

    /**
     * 注册功能
     *
     * @param umsUserParam 用户参数
     * @return 用户
     */
    UmsUser register(UmsUserParam umsUserParam);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的token
     * @return 新的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名或昵称分页查询用户
     *
     * @param keyword  关键字
     * @param pageSize 页大小
     * @param pageNum  页码
     * @return 用户分页列表
     */
    Page<UmsUser> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     *
     * @param id    用户ID
     * @param user 用户
     * @return 成功标志
     */
    boolean update(Long id, UmsUser user);

    /**
     * 删除指定用户
     *
     * @param id 用户ID
     * @return 成功标志
     */
    boolean delete(Long id);

    /**
     * 修改用户角色关系
     *
     * @param userId 用户ID
     * @param roleIds 角色ID列表
     * @return 成功标志
     */
    @Transactional
    int updateRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<UmsRole> getRoleList(Long userId);

    /**
     * 获取指定用户的可访问资源
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    List<UmsResource> getResourceList(Long userId);

    /**
     * 修改密码
     *
     * @param updatePasswordParam 修改密码参数
     * @return 成功标志
     */
    int updatePassword(UpdatePasswordParam updatePasswordParam);

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return UserDetails对象
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取缓存服务
     *
     * @return UmsUserCacheService
     */
    UmsUserCacheService getCacheService();
}
