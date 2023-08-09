package com.project.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.api.CommonPage;
import com.project.common.api.CommonResult;
import com.project.modules.ums.model.UmsMenu;
import com.project.modules.ums.model.UmsResource;
import com.project.modules.ums.model.UmsRole;
import com.project.modules.ums.service.UmsMenuService;
import com.project.modules.ums.service.UmsResourceService;
import com.project.modules.ums.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户角色管理 Controller
 *
 * @author Qing2514
 */
@RestController
@Api(tags = "UmsRoleController")
@Tag(name = "UmsRoleController", description = "后台用户角色管理")
@RequestMapping("/role")
public class UmsRoleController {

    @Autowired
    private UmsRoleService roleService;

    @Autowired
    private UmsMenuService menuService;

    @Autowired
    private UmsResourceService resourceService;

    @ApiOperation("查询所有角色")
    @GetMapping(value = "/getAll")
    public CommonResult<List<UmsRole>> getAll() {
        List<UmsRole> roleList = roleService.list();
        return CommonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页查询角色列表")
    @GetMapping(value = "/getPage")
    public CommonResult<CommonPage<UmsRole>> getPage(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsRole> roleList = roleService.getPage(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("根据角色ID查询角色相关菜单")
    @GetMapping(value = "/getMenus/{roleId}")
    public CommonResult<List<UmsMenu>> getMenus(@PathVariable Long roleId) {
        List<UmsMenu> roleList = menuService.getByRoleId(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("查询角色相关资源")
    @GetMapping(value = "/getResources/{roleId}")
    public CommonResult<List<UmsResource>> getResources(@PathVariable Long roleId) {
        List<UmsResource> roleList = resourceService.getByRoleId(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("添加角色")
    @PostMapping(value = "")
    public CommonResult<Object> create(@RequestBody UmsRole role) {
        boolean success = roleService.save(role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色")
    @PutMapping(value = "")
    public CommonResult<Object> update(@RequestBody UmsRole role) {
        boolean success = roleService.updateById(role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色状态")
    @PutMapping(value = "/updateStatus/{id}")
    public CommonResult<Object> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsRole umsRole = new UmsRole();
        umsRole.setId(id);
        umsRole.setStatus(status);
        boolean success = roleService.updateById(umsRole);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据ID删除角色")
    @DeleteMapping(value = "")
    public CommonResult<Object> delete(@RequestParam("id") Long id) {
        boolean success = roleService.removeById(id);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("根据ID列表批量删除角色")
    @DeleteMapping(value = "deleteBatch")
    public CommonResult<Object> deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean success = roleService.deleteBatch(ids);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenu")
    public CommonResult<Integer> allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping(value = "/allocResource")
    public CommonResult<Integer> allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }

}
