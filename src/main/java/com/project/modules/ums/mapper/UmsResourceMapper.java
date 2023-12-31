package com.project.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.modules.ums.model.UmsResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台资源表 Mapper 接口
 *
 * @author Qing2514
 */
@Mapper
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    /**
     * 根据用户ID查询
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    List<UmsResource> getByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询
     *
     * @param roleId 角色ID
     * @return 资源列表
     */
    List<UmsResource> getByRoleId(@Param("roleId") Long roleId);

}
