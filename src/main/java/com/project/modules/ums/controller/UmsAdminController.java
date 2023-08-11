package com.project.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.api.CommonPage;
import com.project.common.api.CommonResult;
import com.project.modules.ums.dto.UmsUserLoginParam;
import com.project.modules.ums.dto.UmsUserParam;
import com.project.modules.ums.dto.UpdatePasswordParam;
import com.project.modules.ums.model.UmsUser;
import com.project.modules.ums.service.UmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户管理 Controller
 *
 * @author Qing2514
 */
@RestController
@Api(value = "UmsAdminController", tags = "后台用户管理")
@RequestMapping("/admin")
public class UmsAdminController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsUserService userService;

    @ApiOperation("注册")
    @PostMapping("/register")
    public CommonResult<UmsUser> register(@Validated @RequestBody UmsUserParam umsUserParam) {
        UmsUser umsUser = userService.register(umsUserParam);
        return CommonResult.success(umsUser);
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public CommonResult<Map<String, String>> login(@Validated @RequestBody UmsUserLoginParam umsUserLoginParam) {
        String token = userService.login(umsUserLoginParam);
        Map<String, String> tokenMap = new HashMap<>(2);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("登出")
    @PostMapping("/logout")
    public CommonResult<Object> logout() {
        return CommonResult.success();
    }

    @ApiOperation("刷新token")
    @GetMapping("/refreshToken")
    public CommonResult<Map<String, String>> refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = userService.refreshToken(token);
        Map<String, String> tokenMap = new HashMap<>(2);
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    public CommonResult<UmsUser> getById(@PathVariable Long id) {
        UmsUser user = userService.getById(id);
        return CommonResult.success(user);
    }

    @ApiOperation("根据用户名或昵称分页模糊查询")
    @GetMapping("/page")
    public CommonResult<CommonPage<UmsUser>> getPage(@RequestParam(value = "keyword", required = false) String keyword,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsUser> userList = userService.getPage(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(userList));
    }

    @ApiOperation("修改")
    @PutMapping("")
    public CommonResult<Object> update(@RequestBody UmsUser user) {
        boolean success = userService.updateById(user);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePassword")
    public CommonResult<Object> updatePassword(@Validated @RequestBody UpdatePasswordParam updatePasswordParam) {
        boolean success = userService.updatePassword(updatePasswordParam);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改状态")
    @PostMapping("/{id}/status")
    public CommonResult<Object> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        boolean success = userService.updateStatus(id, status);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据用户ID分配角色")
    @PostMapping("/{id}/addRole")
    public CommonResult<Object> addRole(@PathVariable("id") Long id, @RequestParam("roleIds") List<Long> roleIds) {
        int count = userService.addRole(id, roleIds);
        return CommonResult.success(count);
    }

    @ApiOperation("根据ID删除")
    @DeleteMapping("/{id}")
    public CommonResult<Object> deleteById(@PathVariable Long id) {
        boolean success = userService.deleteById(id);
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
