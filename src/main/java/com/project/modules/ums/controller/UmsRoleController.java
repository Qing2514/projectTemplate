package com.project.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.api.CommonPage;
import com.project.common.api.CommonResult;
import com.project.modules.ums.model.UmsRole;
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

    @ApiOperation("根据用户ID查询")
    @GetMapping(value = "/user/{userId}")
    public CommonResult<List<UmsRole>> getByUserId(@PathVariable Long userId) {
        List<UmsRole> roleList = roleService.getByUserId(userId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("查询所有")
    @GetMapping(value = "")
    public CommonResult<List<UmsRole>> getAll() {
        List<UmsRole> roleList = roleService.list();
        return CommonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页模糊查询")
    @GetMapping(value = "/page")
    public CommonResult<CommonPage<UmsRole>> getPage(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsRole> roleList = roleService.getPage(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("添加")
    @PostMapping(value = "")
    public CommonResult<Object> create(@RequestBody UmsRole role) {
        boolean success = roleService.save(role);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID分配菜单")
    @PostMapping(value = "/{id}/addMenu")
    public CommonResult<Integer> addMenu(@PathVariable Long id, @RequestParam List<Long> menuIds) {
        int count = roleService.addMenu(id, menuIds);
        return CommonResult.success(count);
    }

    @ApiOperation("根据ID分配资源")
    @PostMapping(value = "/{id}/addResource")
    public CommonResult<Integer> addResource(@PathVariable Long id, @RequestParam List<Long> resourceIds) {
        int count = roleService.addResource(id, resourceIds);
        return CommonResult.success(count);
    }

    @ApiOperation("修改")
    @PutMapping(value = "")
    public CommonResult<Object> update(@RequestBody UmsRole role) {
        boolean success = roleService.updateById(role);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID修改状态")
    @PutMapping(value = "/{id}/status")
    public CommonResult<Object> updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        boolean success = roleService.updateStatus(id, status);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID删除")
    @DeleteMapping(value = "/{id}")
    public CommonResult<Object> delete(@PathVariable("id") Long id) {
        boolean success = roleService.removeById(id);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID列表批量删除")
    @DeleteMapping(value = "deleteBatch")
    public CommonResult<Object> deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean success = roleService.deleteBatch(ids);
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
