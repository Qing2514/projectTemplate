package com.project.modules.ums.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.api.CommonPage;
import com.project.common.api.CommonResult;
import com.project.modules.ums.dto.UmsUserLoginParam;
import com.project.modules.ums.dto.UmsUserParam;
import com.project.modules.ums.dto.UpdatePasswordParam;
import com.project.modules.ums.model.UmsRole;
import com.project.modules.ums.model.UmsUser;
import com.project.modules.ums.service.UmsMenuService;
import com.project.modules.ums.service.UmsRoleService;
import com.project.modules.ums.service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台用户管理
 *
 * @author Qing2514
 */
@RestController
@Api(tags = "UmsUserController")
@Tag(name = "UmsUserController", description = "后台用户管理")
@RequestMapping("/user")
public class UmsUserController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsUserService userService;

    @Autowired
    private UmsRoleService roleService;

    @Autowired
    private UmsMenuService menuService;

    @ApiOperation(value = "注册")
    @PostMapping(value = "/register")
    public CommonResult<UmsUser> register(@Validated @RequestBody UmsUserParam umsUserParam) {
        UmsUser umsUser = userService.register(umsUserParam);
        return CommonResult.success(umsUser);
    }

    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public CommonResult<Map<String, String>> login(@Validated @RequestBody UmsUserLoginParam umsUserLoginParam) {
        String token = userService.login(umsUserLoginParam);
        Map<String, String> tokenMap = new HashMap<>(2);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "登出")
    @PostMapping(value = "/logout")
    public CommonResult<Object> logout() {
        return CommonResult.success();
    }

    @ApiOperation(value = "刷新token")
    @GetMapping(value = "/refreshToken")
    public CommonResult<Map<String, String>> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = userService.refreshToken(token);
        Map<String, String> tokenMap = new HashMap<>(2);
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("根据ID查询")
    @GetMapping(value = "/{id}")
    public CommonResult<UmsUser> getById(@PathVariable Long id) {
        UmsUser user = userService.getById(id);
        return CommonResult.success(user);
    }

    @ApiOperation(value = "查询当前登录用户信息")
    @GetMapping(value = "/info")
    public CommonResult<Map<String, Object>> getUserInfo(Principal principal) {
        String username = principal.getName();
        UmsUser umsUser = userService.getByUsername(username);
        Map<String, Object> data = new HashMap<>(3);
        data.put("user", umsUser);
        data.put("menus", menuService.getByUserId(umsUser.getId()));
        List<UmsRole> roleList = roleService.getByUserId(umsUser.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return CommonResult.success(data);
    }

    @ApiOperation("根据用户名或昵称分页模糊查询")
    @GetMapping(value = "/page")
    public CommonResult<CommonPage<UmsUser>> getPage(@RequestParam(value = "keyword", required = false) String keyword,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsUser> userList = userService.getPage(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(userList));
    }

    @ApiOperation("修改")
    @PutMapping(value = "")
    public CommonResult<Object> update(@RequestBody UmsUser user) {
        boolean success = userService.updateById(user);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改密码")
    @PostMapping(value = "/updatePassword")
    public CommonResult<Object> updatePassword(@Validated @RequestBody UpdatePasswordParam updatePasswordParam) {
        boolean success = userService.updatePassword(updatePasswordParam);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改状态")
    @PostMapping(value = "/{id}/status")
    public CommonResult<Object> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        boolean success = userService.updateStatus(id, status);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据用户ID分配角色")
    @PostMapping(value = "/allocRole")
    public CommonResult<Object> allocRole(@RequestParam("userId") Long userId,
                                           @RequestParam("roleIds") List<Long> roleIds) {
        int count = userService.allocRole(userId, roleIds);
        return CommonResult.success(count);
    }

    @ApiOperation("根据ID删除")
    @DeleteMapping(value = "/{id}")
    public CommonResult<Object> delete(@PathVariable Long id) {
        boolean success = userService.delete(id);
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
