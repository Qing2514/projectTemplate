package com.project.modules.ums.mapper;

import com.project.modules.ums.model.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台菜单表 Mapper 接口
 *
 * @author Qing2514
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<UmsMenu> getByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<UmsMenu> getByRoleId(@Param("roleId") Long roleId);

}
