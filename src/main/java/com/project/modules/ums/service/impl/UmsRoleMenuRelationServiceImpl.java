package com.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.mapper.UmsRoleMenuRelationMapper;
import com.project.modules.ums.model.UmsRoleMenuRelation;
import com.project.modules.ums.service.UmsRoleMenuRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关系管理 Service 实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationMapper, UmsRoleMenuRelation> implements UmsRoleMenuRelationService {}
