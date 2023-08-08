package com.project.modules.ums.controller;

import com.project.common.api.CommonResult;
import com.project.modules.ums.model.UmsResourceCategory;
import com.project.modules.ums.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源分类管理 Controller
 *
 * @author Qing2514
 */
@RestController
@Api(tags = "UmsResourceCategoryController")
@Tag(name = "UmsResourceCategoryController", description = "后台资源分类管理")
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有资源分类")
    @GetMapping(value = "/listAll")
    public CommonResult<List<UmsResourceCategory>> listAll() {
        List<UmsResourceCategory> resourceList = resourceCategoryService.list();
        return CommonResult.success(resourceList);
    }

    @ApiOperation("添加资源分类")
    @PostMapping(value = "")
    public CommonResult<Object> create(@RequestBody UmsResourceCategory umsResourceCategory) {
        boolean success = resourceCategoryService.save(umsResourceCategory);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("修改资源分类")
    @PutMapping(value = "")
    public CommonResult<Object> update(@RequestBody UmsResourceCategory umsResourceCategory) {
        boolean success = resourceCategoryService.updateById(umsResourceCategory);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("根据ID删除资源")
    @DeleteMapping(value = "/{id}")
    public CommonResult<Object> delete(@PathVariable Long id) {
        boolean success = resourceCategoryService.removeById(id);
        if (success) {
            return CommonResult.success(null);
        } else {
            return CommonResult.failed();
        }
    }

}
