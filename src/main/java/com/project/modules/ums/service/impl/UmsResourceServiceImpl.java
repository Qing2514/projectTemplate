package com.project.modules.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.mapper.UmsResourceMapper;
import com.project.modules.ums.model.UmsResource;
import com.project.modules.ums.service.UmsResourceService;
import com.project.modules.ums.service.UmsUserCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台资源管理Service实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper,UmsResource>implements UmsResourceService {

    @Autowired
    private UmsUserCacheService userCacheService;

    @Override
    public boolean create(UmsResource umsResource) {
        return save(umsResource);
    }

    @Override
    public boolean update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        boolean success = updateById(umsResource);
        userCacheService.delResourceListByResource(id);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        boolean success = removeById(id);
        userCacheService.delResourceListByResource(id);
        return success;
    }

    @Override
    public Page<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        Page<UmsResource> page = new Page<>(pageNum,pageSize);
        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsResource> lambda = wrapper.lambda();
        if(categoryId!=null){
            lambda.eq(UmsResource::getCategoryId,categoryId);
        }
        if(StrUtil.isNotEmpty(nameKeyword)){
            lambda.like(UmsResource::getName,nameKeyword);
        }
        if(StrUtil.isNotEmpty(urlKeyword)){
            lambda.like(UmsResource::getUrl,urlKeyword);
        }
        return page(page,wrapper);
    }

}
