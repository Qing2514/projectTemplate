package com.project.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.modules.ums.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台角色管理 Service
 *
 * @author Qing2514
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 分页获取角色列表
     *
     * @param keyword  关键字
     * @param pageSize 页大小
     * @param pageNum  页码
     * @return 角色列表
     */
    Page<UmsRole> getPage(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据ID列表批量删除角色
     *
     * @param ids ID列表
     * @return 成功标志
     */
    boolean deleteBatch(List<Long> ids);

    /**
     * 给角色分配菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     * @return 成功标志
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     *
     * @param roleId      角色ID
     * @param resourceIds 资源ID列表
     * @return 成功标志
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);

}
