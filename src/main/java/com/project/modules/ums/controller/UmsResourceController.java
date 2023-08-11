package com.project.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.common.api.CommonPage;
import com.project.common.api.CommonResult;
import com.project.modules.ums.model.UmsResource;
import com.project.modules.ums.service.UmsResourceService;
import com.project.security.component.DynamicSecurityMetadataSource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源管理 Controller
 *
 * @author Qing2514
 */
@RestController
@Api(value = "UmsResourceController", tags = "后台资源管理")
@RequestMapping("/resource")
public class UmsResourceController {

    @Autowired
    private UmsResourceService resourceService;

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("根据ID查询")
    @GetMapping("/{id}")
    public CommonResult<UmsResource> getById(@PathVariable Long id) {
        UmsResource umsResource = resourceService.getById(id);
        return CommonResult.success(umsResource);
    }

    @ApiOperation("根据角色ID查询")
    @GetMapping("/role/{roleId}")
    public CommonResult<List<UmsResource>> getByRoleId(@PathVariable Long roleId) {
        List<UmsResource> resourceList = resourceService.getByRoleId(roleId);
        return CommonResult.success(resourceList);
    }

    @ApiOperation("查询所有")
    @GetMapping("")
    public CommonResult<List<UmsResource>> getAll() {
        List<UmsResource> resourceList = resourceService.list();
        return CommonResult.success(resourceList);
    }

    @ApiOperation("根据菜单类型ID、名称、URL分页模糊查询")
    @GetMapping("/page")
    public CommonResult<CommonPage<UmsResource>> getPage(@RequestParam(required = false) Long categoryId,
                                                      @RequestParam(required = false) String nameKeyword,
                                                      @RequestParam(required = false) String urlKeyword,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsResource> resourceList = resourceService.getPage(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(resourceList));
    }

    @ApiOperation("添加")
    @PostMapping("")
    public CommonResult<Object> create(@RequestBody UmsResource umsResource) {
        boolean success = resourceService.save(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改")
    @PutMapping("")
    public CommonResult<Object> update(@RequestBody UmsResource umsResource) {
        boolean success = resourceService.update(umsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID删除")
    @DeleteMapping("/{id}")
    public CommonResult<Object> delete(@PathVariable Long id) {
        boolean success = resourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
