package com.project.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.modules.ums.dto.UmsUserLoginParam;
import com.project.modules.ums.dto.UmsUserParam;
import com.project.modules.ums.dto.UpdatePasswordParam;
import com.project.modules.ums.model.UmsUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台用户管理 Service
 *
 * @author Qing2514
 */
public interface UmsUserService extends IService<UmsUser> {

    /**
     * 注册
     *
     * @param umsUserParam 用户参数
     * @return 用户
     */
    UmsUser register(UmsUserParam umsUserParam);

    /**
     * 登录
     *
     * @param umsUserLoginParam 登录参数
     * @return 生成的JWT的token
     */
    String login(UmsUserLoginParam umsUserLoginParam);

    /**
     * 刷新token
     *
     * @param oldToken 旧token
     * @return 新token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return UserDetails对象
     */
    UserDetails loadByUsername(String username);

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户
     */
    UmsUser getByUsername(String username);

    /**
     * 根据资源ID查询ID列表
     *
     * @param resourceId 资源ID
     * @return ID列表
     */
    List<Long> getIdsByResourceId(Long resourceId);

    /**
     * 根据用户名或昵称分页模糊查询
     *
     * @param keyword  关键字
     * @param pageSize 页大小
     * @param pageNum  页码
     * @return 用户列表
     */
    Page<UmsUser> getPage(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据用户名修改密码
     *
     * @param username 用户名
     * @param updatePasswordParam 修改密码参数
     * @return 成功标志
     */
    boolean updatePasswordByUsername(String username, UpdatePasswordParam updatePasswordParam);

    /**
     * 修改密码
     *
     * @param updatePasswordParam 修改密码参数
     * @return 成功标志
     */
    boolean updatePassword(UpdatePasswordParam updatePasswordParam);

    /**
     * 修改状态
     *
     * @param id   用户ID
     * @param status 状态
     * @return 成功标志
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 根据用户名修改
     *
     * @param username 用户名
     * @param user 用户
     * @return 成功标志
     */
    boolean updateByUsername(String username, UmsUser user);

    /**
     * 根据用户ID分配角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID列表
     * @return 成功标志
     */
    @Transactional
    int addRole(Long userId, List<Long> roleIds);

    /**
     * 根据ID删除
     *
     * @param id 用户ID
     * @return 成功标志
     */
    boolean deleteById(Long id);

    /**
     * 根据用户名删除
     *
     * @param username 用户名
     * @return 成功标志
     */
    boolean deleteByUsername(String username);
}
