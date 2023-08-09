package com.project.modules.ums.mapper;

import com.project.modules.ums.model.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户角色表 Mapper 接口
 *
 * @author Qing2514
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 根据用户ID查询用户所有角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<UmsRole> getByUserId(@Param("userId") Long userId);

}
