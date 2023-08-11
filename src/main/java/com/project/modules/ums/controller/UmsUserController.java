package com.project.modules.ums.controller;

import cn.hutool.core.collection.CollUtil;
import com.project.common.api.CommonResult;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UmsUserService userService;

    @Autowired
    private UmsRoleService roleService;

    @Autowired
    private UmsMenuService menuService;

    @ApiOperation(value = "查询当前登录用户信息")
    @GetMapping(value = "")
    public CommonResult<Map<String, Object>> getByUsername(Principal principal) {
        UmsUser umsUser = userService.getByUsername(principal.getName());
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

    @ApiOperation("修改当前登录用户密码")
    @PostMapping(value = "/updatePassword")
    public CommonResult<Object> updatePassword(@Validated @RequestBody UpdatePasswordParam updatePasswordParam,
                                               Principal principal) {
        boolean success = userService.updatePasswordByUsername(principal.getName(), updatePasswordParam);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改当前登录用户信息")
    @PutMapping(value = "")
    public CommonResult<Object> updateByUsername(@RequestBody UmsUser user, Principal principal) {
        boolean success = userService.updateByUsername(principal.getName(), user);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("注销")
    @DeleteMapping(value = "")
    public CommonResult<Object> deleteByUsername(Principal principal) {
        boolean success = userService.deleteByUsername(principal.getName());
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
