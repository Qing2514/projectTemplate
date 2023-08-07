package com.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.mapper.UmsUserRoleRelationMapper;
import com.project.modules.ums.model.UmsUserRoleRelation;
import com.project.modules.ums.service.UmsUserRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * 管理员角色关系管理Service实现类
 * @author Qing2514
 */
@Service
public class UmsUserRoleRelationServiceImpl extends ServiceImpl<UmsUserRoleRelationMapper, UmsUserRoleRelation> implements UmsUserRoleRelationService {
}
