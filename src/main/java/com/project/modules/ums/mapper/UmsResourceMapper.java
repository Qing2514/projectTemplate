package com.project.modules.ums.mapper;

import com.project.modules.ums.model.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台资源表 Mapper 接口
 *
 * @author Qing2514
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * 根据用户ID查询用户可访问资源列表
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    List<UmsResource> getResourceList(@Param("userId") Long userId);

    /**
     * 根据角色ID查询资源
     *
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<UmsResource> getByRoleId(@Param("roleId") Long roleId);

}
