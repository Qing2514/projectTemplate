package com.project.modules.ums.mapper;

import com.project.modules.ums.model.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author Qing2514
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 获取用户所有角色
     * @param userId 用户ID
     * @return List<UmsRole>
     */
    List<UmsRole> getRoleList(@Param("userId") Long userId);

}
