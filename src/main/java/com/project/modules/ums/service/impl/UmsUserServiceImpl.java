package com.project.modules.ums.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.common.api.ResultCode;
import com.project.common.exception.Asserts;
import com.project.domain.AdminUserDetails;
import com.project.modules.ums.dto.UmsUserParam;
import com.project.modules.ums.dto.UpdatePasswordParam;
import com.project.modules.ums.mapper.UmsResourceMapper;
import com.project.modules.ums.mapper.UmsRoleMapper;
import com.project.modules.ums.mapper.UmsUserLoginLogMapper;
import com.project.modules.ums.mapper.UmsUserMapper;
import com.project.modules.ums.model.*;
import com.project.modules.ums.service.UmsUserCacheService;
import com.project.modules.ums.service.UmsUserRoleRelationService;
import com.project.modules.ums.service.UmsUserService;
import com.project.security.util.JwtTokenUtil;
import com.project.security.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 后台管理员管理Service实现类
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
    private UmsUserLoginLogMapper loginLogMapper;

    @Autowired
    private UmsUserRoleRelationService userRoleRelationService;

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsResourceMapper resourceMapper;

    @Override
    public UmsUser getUserByUsername(String username) {
        UmsUser user = getCacheService().getUser(username);
        if (user != null) {
            return user;
        }
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUser::getUsername, username);
        List<UmsUser> userList = list(wrapper);
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
            getCacheService().setUser(user);
            return user;
        }
        return null;
    }

    @Override
    public UmsUser register(UmsUserParam umsUserParam) {
        UmsUser umsUser = new UmsUser();
        BeanUtils.copyProperties(umsUserParam, umsUser);
        // 查询是否有相同用户名的用户
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUser::getUsername, umsUser.getUsername());
        List<UmsUser> umsUserList = list(wrapper);
        if (umsUserList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsUser.getPassword());
        umsUser.setPassword(encodePassword);
        baseMapper.insert(umsUser);
        return umsUser;
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                Asserts.fail(ResultCode.PASSWORD_INCORRECT);
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail(ResultCode.ACCOUNT_FORBIDDEN);
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        UmsUser user = getUserByUsername(username);
        if (user == null) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        UmsUserLoginLog loginLog = UmsUserLoginLog.builder()
                .userId(user.getId())
                .ip(request.getRemoteAddr())
                // .userAgent(request.getHeader("User-Agent"))
                .build();
        loginLogMapper.insert(loginLog);
    }

    /**
     * 根据用户名修改登录时间
     */
    private void updateLoginTimeByUsername(String username) {
        UmsUser record = new UmsUser();
        record.setLoginTime(new Date());
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUser::getUsername, username);
        update(record, wrapper);
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public Page<UmsUser> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsUser> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsUser> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(UmsUser::getUsername, keyword);
            lambda.or().like(UmsUser::getNickName, keyword);
        }
        return page(page, wrapper);
    }

    @Override
    public boolean update(Long id, UmsUser user) {
        user.setId(id);
        UmsUser rawUser = getById(id);
        if (rawUser.getPassword().equals(user.getPassword())) {
            //与原加密密码相同的不需要修改
            user.setPassword(null);
        } else {
            //与原加密密码不同的需要加密修改
            if (StrUtil.isEmpty(user.getPassword())) {
                user.setPassword(null);
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        boolean success = updateById(user);
        getCacheService().delUser(id);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        getCacheService().delUser(id);
        boolean success = removeById(id);
        getCacheService().delResourceList(id);
        return success;
    }

    @Override
    public int updateRole(Long userId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        QueryWrapper<UmsUserRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUserRoleRelation::getUserId, userId);
        userRoleRelationService.remove(wrapper);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UmsUserRoleRelation> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UmsUserRoleRelation roleRelation = new UmsUserRoleRelation();
                roleRelation.setUserId(userId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            userRoleRelationService.saveBatch(list);
        }
        getCacheService().delResourceList(userId);
        return count;
    }

    @Override
    public List<UmsRole> getRoleList(Long userId) {
        return roleMapper.getByUserId(userId);
    }

    @Override
    public List<UmsResource> getResourceList(Long userId) {
        List<UmsResource> resourceList = getCacheService().getResourceList(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = resourceMapper.getResourceList(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            getCacheService().setResourceList(userId, resourceList);
        }
        return resourceList;
    }

    @Override
    public int updatePassword(UpdatePasswordParam param) {
        if (StrUtil.isEmpty(param.getUsername()) || StrUtil.isEmpty(param.getOldPassword()) || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }
        QueryWrapper<UmsUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsUser::getUsername, param.getUsername());
        List<UmsUser> userList = list(wrapper);
        if (CollUtil.isEmpty(userList)) {
            return -2;
        }
        UmsUser umsUser = userList.get(0);
        if (!passwordEncoder.matches(param.getOldPassword(), umsUser.getPassword())) {
            return -3;
        }
        umsUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        updateById(umsUser);
        getCacheService().delUser(umsUser.getId());
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        UmsUser user = getUserByUsername(username);
        if (user != null) {
            List<UmsResource> resourceList = getResourceList(user.getId());
            return new AdminUserDetails(user, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public UmsUserCacheService getCacheService() {
        return SpringUtil.getBean(UmsUserCacheService.class);
    }

}
