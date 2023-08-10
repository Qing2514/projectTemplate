package com.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.mapper.UmsUserRoleRelationMapper;
import com.project.modules.ums.model.UmsUserRoleRelation;
import com.project.modules.ums.service.UmsUserRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户角色关系管理 Service 实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsUserRoleRelationServiceImpl extends ServiceImpl<UmsUserRoleRelationMapper, UmsUserRoleRelation>
        implements UmsUserRoleRelationService {}
