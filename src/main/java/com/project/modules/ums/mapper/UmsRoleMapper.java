package com.project.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.modules.ums.model.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户角色表 Mapper 接口
 *
 * @author Qing2514
 */
@Mapper
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 根据用户ID查询
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<UmsRole> getByUserId(@Param("userId") Long userId);

}
