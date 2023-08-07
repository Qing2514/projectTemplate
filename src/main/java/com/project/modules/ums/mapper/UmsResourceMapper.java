package com.project.modules.ums.mapper;

import com.project.modules.ums.model.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author Qing2514
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * 获取用户所有可访问资源
     * @param userId 用户ID
     * @return List<UmsResource>
     */
    List<UmsResource> getResourceList(@Param("userId") Long userId);

    /**
     * 根据角色ID获取资源
     * @param roleId 角色ID
     * @return List<UmsResource>
     */
    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);

}
