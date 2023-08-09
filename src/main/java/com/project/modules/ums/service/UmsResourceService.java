package com.project.modules.ums.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.project.modules.ums.model.UmsResource;

import java.util.List;

/**
 * 后台资源管理 Service
 *
 * @author Qing2514
 */
public interface UmsResourceService extends IService<UmsResource> {

    /**
     * 根据角色ID查询资源列表
     *
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<UmsResource> getByRoleId(Long roleId);

    /**
     * 分页查询资源列表
     *
     * @param categoryId  资源分类ID
     * @param nameKeyword 名称
     * @param urlKeyword  URL
     * @param pageSize    页大小
     * @param pageNum     页码
     * @return 资源列表
     */
    Page<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

    /**
     * 修改资源
     *
     * @param umsResource 资源
     * @return 成功标志
     */
    boolean update(UmsResource umsResource);

    /**
     * 根据资源ID删除资源
     *
     * @param id 资源ID
     * @return 成功标志
     */
    boolean delete(Long id);

}
