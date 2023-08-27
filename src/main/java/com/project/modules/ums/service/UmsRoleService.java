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
     * 根据用户ID查询
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<UmsRole> getByUserId(Long userId);

    /**
     * 根据角色名称分页模糊查询
     *
     * @param keyword  关键字
     * @param pageSize 页大小
     * @param pageNum  页码
     * @return 角色列表
     */
    Page<UmsRole> getPage(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据ID分配菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID列表
     * @return 成功标志
     */
    @Transactional(rollbackFor = Exception.class)
    int addMenu(Long roleId, List<Long> menuIds);

    /**
     * 根据ID分配资源
     *
     * @param roleId      角色ID
     * @param resourceIds 资源ID列表
     * @return 成功标志
     */
    @Transactional(rollbackFor = Exception.class)
    int addResource(Long roleId, List<Long> resourceIds);

    /**
     * 根据ID修改状态
     *
     * @param id     角色ID
     * @param status 状态标志
     * @return 成功标志
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 根据ID列表批量删除
     *
     * @param ids ID列表
     * @return 成功标志
     */
    boolean deleteBatch(List<Long> ids);

}
