package com.project.modules.ums.mapper;

import com.project.modules.ums.model.UmsUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author Qing2514
 */
public interface UmsUserMapper extends BaseMapper<UmsUser> {

    /**
     * 获取资源相关用户ID列表
     * @param resourceId 资源ID
     * @return List<Long>
     */
    List<Long> getUserIdList(@Param("resourceId") Long resourceId);

}
