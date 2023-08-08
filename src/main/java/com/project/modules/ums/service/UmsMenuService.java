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
     * 分页查询后台菜单
     *
     * @param parentId 父级菜单ID
     * @param pageSize 页大小
     * @param pageNum  页码
     * @return 菜单列表
     */
    Page<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     *
     * @return 菜单节点列表
     */
    List<UmsMenuNode> treeList();

    /**
     * 创建后台菜单
     *
     * @param umsMenu 菜单
     * @return 成功标志
     */
    boolean create(UmsMenu umsMenu);

    /**
     * 修改后台菜单
     *
     * @param umsMenu 菜单
     * @return 成功标志
     */
    boolean update(UmsMenu umsMenu);

    /**
     * 修改菜单显示状态
     *
     * @param id     菜单ID
     * @param hidden 隐藏标志
     * @return 成功标志
     */
    boolean updateHidden(Long id, Integer hidden);

}
