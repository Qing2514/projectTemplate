package com.project.modules.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.common.api.ResultCode;
import com.project.common.exception.Asserts;
import com.project.domain.AdminUserDetails;
import com.project.modules.ums.dto.UmsUserLoginParam;
import com.project.modules.ums.dto.UmsUserParam;
import com.project.modules.ums.dto.UpdatePasswordParam;
import com.project.modules.ums.mapper.UmsUserMapper;
import com.project.modules.ums.model.UmsResource;
import com.project.modules.ums.model.UmsUser;
import com.project.modules.ums.model.UmsUserLoginLog;
import com.project.modules.ums.model.UmsUserRoleRelation;
import com.project.modules.ums.service.*;
import com.project.security.util.JwtTokenUtil;
import com.project.security.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 后台用户管理 Service 实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsUserServiceImpl extends ServiceImpl<UmsUserMapper, UmsUser> implements UmsUserService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsUserLoginLogService loginLogService;

    @Autowired
    private UmsResourceService resourceService;

    @Autowired
    private UmsUserRoleRelationService userRoleRelationService;

    public UmsUserCacheService getCacheService() {
        return SpringUtil.getBean(UmsUserCacheService.class);
    }

    @Override
    public UmsUser register(UmsUserParam umsUserParam) {
        UmsUser umsUser = new UmsUser();
        BeanUtils.copyProperties(umsUserParam, umsUser);
        // 查询是否有相同用户名的用户
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUser::getUsername, umsUser.getUsername());
        List<UmsUser> umsUserList = list(wrapper);
        Asserts.isEmpty(umsUserList, ResultCode.USER_EXISTS);
        // 将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsUser.getPassword());
        umsUser.setPassword(encodePassword);
        getBaseMapper().insert(umsUser);
        return umsUser;
    }

    @Override
    public String login(UmsUserLoginParam umsUserLoginParam) {
        String token = null;
        // 密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadByUsername(umsUserLoginParam.getUsername());
            if (!passwordEncoder.matches(umsUserLoginParam.getPassword(), userDetails.getPassword())) {
                Asserts.fail(ResultCode.PASSWORD_INCORRECT);
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail(ResultCode.ACCOUNT_FORBIDDEN);
            }
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            updateLoginTimeByUsername(umsUserLoginParam.getUsername());
            insertLoginLog(umsUserLoginParam.getUsername());
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        Asserts.notNull(token, ResultCode.USERNAME_OR_PASSWORD_INCORRECT);
        return token;
    }

    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsUser user = getByUsername(username);
        Asserts.notNull(user, ResultCode.USER_NOT_EXISTS);
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        UmsUserLoginLog loginLog = UmsUserLoginLog.builder()
                .userId(user.getId())
                .ip(request.getRemoteAddr())
                // .userAgent(request.getHeader("User-Agent"))
                .build();
        loginLogService.save(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        UmsUser record = new UmsUser();
        record.setLoginTime(new Date());
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUser::getUsername, username);
        update(record, wrapper);
    }

    @Override
    public String refreshToken(String oldToken) {
        String refreshToken = jwtTokenUtil.refreshHeadToken(oldToken);
        Asserts.notNull(refreshToken, ResultCode.TOKEN_EXPIRE);
        return refreshToken;
    }

    @Override
    public UserDetails loadByUsername(String username) {
        // 获取用户信息
        UmsUser user = getByUsername(username);
        Asserts.notNull(user, ResultCode.USERNAME_OR_PASSWORD_INCORRECT);
        List<UmsResource> resourceList = getResourceList(user.getId());
        return new AdminUserDetails(user, resourceList);
    }

    @Override
    public UmsUser getByUsername(String username) {
        UmsUser user = getCacheService().getUserByUsername(username);
        if (user != null) {
            return user;
        }
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUser::getUsername, username);
        List<UmsUser> userList = list(wrapper);
        if (!ObjectUtils.isEmpty(userList)) {
            user = userList.get(0);
            getCacheService().setUser(user);
            return user;
        }
        return null;
    }

    /**
     * 根据ID查询资源
     *
     * @param userId 用户ID
     * @return 资源列表
     */
    public List<UmsResource> getResourceList(Long userId) {
        List<UmsResource> resourceList = getCacheService().getResourceListByUserId(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = resourceService.getByUserId(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            getCacheService().setResourceList(userId, resourceList);
        }
        return resourceList;
    }

    @Override
    public List<Long> getIdsByResourceId(Long resourceId) {
        return getBaseMapper().getIdsByResourceId(resourceId);
    }

    @Override
    public Page<UmsUser> getPage(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotEmpty(keyword)) {
            wrapper.like(UmsUser::getUsername, keyword);
            wrapper.or().like(UmsUser::getNickName, keyword);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean updatePasswordByUsername(String username, UpdatePasswordParam updatePasswordParam) {
        if (!username.equals(updatePasswordParam.getUsername())) {
            Asserts.fail(ResultCode.NOT_CURRENT_USER);
        }
        return updatePassword(updatePasswordParam);
    }

    @Override
    public boolean updatePassword(UpdatePasswordParam param) {
        if (!param.getNewPassword().equals(param.getOldPassword())) {
            Asserts.fail(ResultCode.NEW_PASSWORD_EQUALS_OLD);
        }
        LambdaQueryWrapper<UmsUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUser::getUsername, param.getUsername());
        List<UmsUser> userList = list(wrapper);
        Asserts.notEmpty(userList, ResultCode.USER_NOT_EXISTS);
        UmsUser umsUser = userList.get(0);
        if (!passwordEncoder.matches(param.getOldPassword(), umsUser.getPassword())) {
            Asserts.fail(ResultCode.OLD_PASSWORD_INCORRECT);
        }
        umsUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        getCacheService().delUserByUserId(umsUser.getId());
        return updateById(umsUser);
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        UmsUser user = new UmsUser();
        user.setId(id);
        user.setStatus(status);
        getCacheService().delUserByUserId(id);
        return updateById(user);
    }

    @Override
    public boolean updateByUsername(String username, UmsUser user) {
        if (!username.equals(user.getUsername())) {
            Asserts.fail(ResultCode.NOT_CURRENT_USER);
        }
        UmsUser oldUser = getByUsername(username);
        BeanUtils.copyProperties(user, oldUser);
        getCacheService().delUserByUserId(oldUser.getId());
        return updateById(oldUser);
    }

    @Override
    public int addRole(Long userId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        LambdaQueryWrapper<UmsUserRoleRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UmsUserRoleRelation::getUserId, userId);
        userRoleRelationService.remove(wrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsUserRoleRelation> list = roleIds.stream().map(roleId -> {
                UmsUserRoleRelation roleRelation = new UmsUserRoleRelation();
                roleRelation.setUserId(userId);
                roleRelation.setRoleId(roleId);
                return roleRelation;
            }).collect(Collectors.toList());
            userRoleRelationService.saveBatch(list);
        }
        getCacheService().delResourceListByUserId(userId);
        return count;
    }

    @Override
    public boolean deleteById(Long id) {
        getCacheService().delUserByUserId(id);
        getCacheService().delResourceListByUserId(id);
        return removeById(id);
    }

    @Override
    public boolean deleteByUsername(String username) {
        UmsUser user = getByUsername(username);
        getCacheService().delUserByUserId(user.getId());
        getCacheService().delResourceListByUserId(user.getId());
        return removeById(user.getId());
    }

}
