package com.project.modules.ums.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.modules.ums.dto.UmsMenuNode;
import com.project.modules.ums.model.UmsMenu;

import java.util.List;

/**
 * 后台菜单管理 Service
 *
 * @author Qing2514
 */
public interface UmsMenuService extends IService<UmsMenu> {

    /**
     * 根据用户ID查询
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<UmsMenu> getByUserId(Long userId);

    /**
     * 根据角色ID查询
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<UmsMenu> getByRoleId(Long roleId);

    /**
     * 根据父级菜单ID分页查询
     *
     * @param parentId 父级菜单ID
     * @param pageSize 页大小
     * @param pageNum  页码
     * @return 菜单列表
     */
    Page<UmsMenu> getPage(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 查询树形结构列表
     *
     * @return 菜单节点列表
     */
    List<UmsMenuNode> treeList();

    /**
     * 添加
     *
     * @param umsMenu 菜单
     * @return 成功标志
     */
    boolean create(UmsMenu umsMenu);

    /**
     * 修改
     *
     * @param umsMenu 菜单
     * @return 成功标志
     */
    boolean update(UmsMenu umsMenu);

    /**
     * 修改显示状态
     *
     * @param id     菜单ID
     * @param hidden 隐藏标志
     * @return 成功标志
     */
    boolean updateHidden(Long id, Integer hidden);

}
