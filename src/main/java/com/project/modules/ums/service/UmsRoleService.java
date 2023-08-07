package com.project.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.modules.ums.model.UmsMenu;
import com.project.modules.ums.model.UmsResource;
import com.project.modules.ums.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台角色管理Service
 * @author Qing2514
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 添加角色
     * @param role 角色
     * @return 成功标志
     */
    boolean create(UmsRole role);

    /**
     * 批量删除角色
     * @param ids 角色ID列表
     * @return 成功标志
     */
    boolean delete(List<Long> ids);

    /**
     * 分页获取角色列表
     * @param keyword 关键字
     * @param pageSize 页大小
     * @param pageNum 页码
     * @return 角色列表
     */
    Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据管理员ID获取对应菜单
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<UmsMenu> getMenuList(Long userId);

    /**
     * 获取角色相关菜单
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * 获取角色相关资源
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * 给角色分配菜单
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     * @return 成功标志
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     * @param roleId 角色ID
     * @param resourceIds 资源ID列表
     * @return 成功标志
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);

}
