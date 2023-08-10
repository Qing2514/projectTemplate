package com.project.modules.ums.mapper;

import com.project.modules.ums.model.UmsUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户表 Mapper 接口
 *
 * @author Qing2514
 */
public interface UmsUserMapper extends BaseMapper<UmsUser> {

    /**
     * 根据资源ID查询ID列表
     *
     * @param resourceId 资源ID
     * @return ID列表
     */
    List<Long> getIdsByResourceId(@Param("resourceId") Long resourceId);

}
