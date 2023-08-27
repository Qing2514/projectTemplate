package com.project.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.modules.ums.model.UmsUserRoleRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户和角色关系表 Mapper 接口
 *
 * @author Qing2514
 */
@Mapper
public interface UmsUserRoleRelationMapper extends BaseMapper<UmsUserRoleRelation> {}
