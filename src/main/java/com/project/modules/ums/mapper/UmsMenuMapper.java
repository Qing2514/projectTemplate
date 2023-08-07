package com.project.modules.ums.mapper;

import com.project.modules.ums.model.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author Qing2514
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    /**
     * 根据后台用户ID获取菜单
     * @param userId 用户ID
     * @return List<UmsMenu>
     */
    List<UmsMenu> getMenuList(@Param("userId") Long userId);

    /**
     * 根据角色ID获取菜单
     * @param roleId 角色ID
     * @return List<UmsMenu>
     */
    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

}
