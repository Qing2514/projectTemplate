package com.project.modules.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.project.common.service.RedisService;
import com.project.modules.ums.model.UmsResource;
import com.project.modules.ums.model.UmsUser;
import com.project.modules.ums.model.UmsUserRoleRelation;
import com.project.modules.ums.service.UmsUserCacheService;
import com.project.modules.ums.service.UmsUserRoleRelationService;
import com.project.modules.ums.service.UmsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户缓存管理 Service 实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsUserCacheServiceImpl implements UmsUserCacheService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsUserService userService;

    @Autowired
    private UmsUserRoleRelationService userRoleRelationService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;

    @Value("${redis.key.user}")
    private String REDIS_KEY_ADMIN;

    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public UmsUser getUserByUsername(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsUser)redisService.get(key);
    }

    @Override
    public List<UmsResource> getResourceListByUserId(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
        return (List<UmsResource>)redisService.get(key);
    }

    @Override
    public void setResourceList(Long userId, List<UmsResource> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }

    @Override
    public void setUser(UmsUser user) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + user.getUsername();
        redisService.set(key, user, REDIS_EXPIRE);
    }

    @Override
    public void delUserByUserId(Long userId) {
        UmsUser user = userService.getById(userId);
        if (user != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + user.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceListByUserId(Long userId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + userId;
        redisService.del(key);
    }

    @Override
    public void delResourceListByRoleId(Long roleId) {
        LambdaQueryWrapper<UmsUserRoleRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUserRoleRelation::getRoleId, roleId);
        List<UmsUserRoleRelation> relationList = userRoleRelationService.list(wrapper);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream()
                    .map(relation -> keyPrefix + relation.getUserId())
                    .collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<UmsUserRoleRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(UmsUserRoleRelation::getRoleId, roleIds);
        List<UmsUserRoleRelation> relationList = userRoleRelationService.list(wrapper);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream()
                    .map(relation -> keyPrefix + relation.getUserId())
                    .collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByResourceId(Long resourceId) {
        List<Long> userIdList = userService.getIdsByResourceId(resourceId);
        if (CollUtil.isNotEmpty(userIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = userIdList.stream()
                    .map(userId -> keyPrefix + userId)
                    .collect(Collectors.toList());
            redisService.del(keys);
        }
    }

}
