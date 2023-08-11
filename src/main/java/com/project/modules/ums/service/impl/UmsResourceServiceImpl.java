package com.project.modules.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.mapper.UmsResourceMapper;
import com.project.modules.ums.model.UmsResource;
import com.project.modules.ums.service.UmsResourceService;
import com.project.modules.ums.service.UmsUserCacheService;
import com.project.security.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台资源管理 Service 实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements UmsResourceService {

    public UmsUserCacheService getCacheService() {
        return SpringUtil.getBean(UmsUserCacheService.class);
    }

    @Override
    public List<UmsResource> getByUserId(Long userId) {
        return getBaseMapper().getByUserId(userId);
    }

    @Override
    public List<UmsResource> getByRoleId(Long roleId) {
        return getBaseMapper().getByRoleId(roleId);
    }

    @Override
    public Page<UmsResource> getPage(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize,
                                  Integer pageNum) {
        Page<UmsResource> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UmsResource> wrapper = new LambdaQueryWrapper<>();
        if (categoryId != null) {
            wrapper.eq(UmsResource::getCategoryId, categoryId);
        }
        if (StrUtil.isNotEmpty(nameKeyword)) {
            wrapper.like(UmsResource::getName, nameKeyword);
        }
        if (StrUtil.isNotEmpty(urlKeyword)) {
            wrapper.like(UmsResource::getUrl, urlKeyword);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean update(UmsResource umsResource) {
        boolean success = updateById(umsResource);
        getCacheService().delResourceListByResourceId(umsResource.getId());
        return success;
    }

    @Override
    public boolean delete(Long id) {
        boolean success = removeById(id);
        getCacheService().delResourceListByResourceId(id);
        return success;
    }

}
