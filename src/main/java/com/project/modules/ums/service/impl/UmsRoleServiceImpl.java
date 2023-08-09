package com.project.modules.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.mapper.UmsRoleMapper;
import com.project.modules.ums.model.UmsRole;
import com.project.modules.ums.model.UmsRoleMenuRelation;
import com.project.modules.ums.model.UmsRoleResourceRelation;
import com.project.modules.ums.service.UmsRoleMenuRelationService;
import com.project.modules.ums.service.UmsRoleResourceRelationService;
import com.project.modules.ums.service.UmsRoleService;
import com.project.modules.ums.service.UmsUserCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 后台角色管理 Service 实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Autowired
    private UmsUserCacheService userCacheService;

    @Autowired
    private UmsRoleMenuRelationService roleMenuRelationService;

    @Autowired
    private UmsRoleResourceRelationService roleResourceRelationService;

    @Override
    public Page<UmsRole> getPage(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsRole> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsRole> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(UmsRole::getName, keyword);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean deleteBatch(List<Long> ids) {
        boolean success = removeByIds(ids);
        userCacheService.delResourceListByRoleIds(ids);
        return success;
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        QueryWrapper<UmsRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleMenuRelation::getRoleId, roleId);
        roleMenuRelationService.remove(wrapper);
        //批量插入新关系
        List<UmsRoleMenuRelation> relationList = new ArrayList<>();
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        roleMenuRelationService.saveBatch(relationList);
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        QueryWrapper<UmsRoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleResourceRelation::getRoleId, roleId);
        roleResourceRelationService.remove(wrapper);
        //批量插入新关系
        List<UmsRoleResourceRelation> relationList = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        roleResourceRelationService.saveBatch(relationList);
        userCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }

}
