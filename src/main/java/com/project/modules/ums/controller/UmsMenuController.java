package com.project.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.api.CommonPage;
import com.project.common.api.CommonResult;
import com.project.modules.ums.dto.UmsMenuNode;
import com.project.modules.ums.model.UmsMenu;
import com.project.modules.ums.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台菜单管理 Controller
 *
 * @author Qing2514
 */
@RestController
@Api(tags = "UmsMenuController")
@Tag(name = "UmsMenuController", description = "后台菜单管理")
@RequestMapping("/menu")
public class UmsMenuController {

    @Autowired
    private UmsMenuService menuService;

    @ApiOperation("根据ID查询")
    @GetMapping(value = "/{id}")
    public CommonResult<UmsMenu> getById(@PathVariable Long id) {
        UmsMenu umsMenu = menuService.getById(id);
        return CommonResult.success(umsMenu);
    }

    @ApiOperation("根据角色ID查询")
    @GetMapping(value = "/role/{roleId}")
    public CommonResult<List<UmsMenu>> getByRoleId(@PathVariable Long roleId) {
        List<UmsMenu> menuList = menuService.getByRoleId(roleId);
        return CommonResult.success(menuList);
    }

    @ApiOperation("根据父级菜单ID分页查询")
    @GetMapping(value = "/page/{parentId}")
    public CommonResult<CommonPage<UmsMenu>> getPage(@PathVariable Long parentId,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsMenu> menuList = menuService.getPage(parentId, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(menuList));
    }

    @ApiOperation("查询树形结构列表")
    @GetMapping(value = "/tree")
    public CommonResult<List<UmsMenuNode>> treeList() {
        List<UmsMenuNode> list = menuService.treeList();
        return CommonResult.success(list);
    }

    @ApiOperation("添加")
    @PostMapping(value = "")
    public CommonResult<Object> create(@RequestBody UmsMenu umsMenu) {
        boolean success = menuService.create(umsMenu);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改")
    @PutMapping(value = "")
    public CommonResult<Object> update(@RequestBody UmsMenu umsMenu) {
        boolean success = menuService.update(umsMenu);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID修改显示状态")
    @PutMapping(value = "/{id}/hidden")
    public CommonResult<Object> updateHidden(@PathVariable Long id, @RequestParam("hidden") Integer hidden) {
        boolean success = menuService.updateHidden(id, hidden);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID删除")
    @DeleteMapping(value = "/{id}")
    public CommonResult<Object> delete(@PathVariable Long id) {
        boolean success = menuService.removeById(id);
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
