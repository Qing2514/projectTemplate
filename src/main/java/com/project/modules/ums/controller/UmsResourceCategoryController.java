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

    @ApiOperation("查询所有")
    @GetMapping(value = "")
    public CommonResult<List<UmsResourceCategory>> getAll() {
        List<UmsResourceCategory> resourceList = resourceCategoryService.list();
        return CommonResult.success(resourceList);
    }

    @ApiOperation("添加")
    @PostMapping(value = "")
    public CommonResult<Object> create(@RequestBody UmsResourceCategory umsResourceCategory) {
        boolean success = resourceCategoryService.save(umsResourceCategory);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改")
    @PutMapping(value = "")
    public CommonResult<Object> update(@RequestBody UmsResourceCategory umsResourceCategory) {
        boolean success = resourceCategoryService.updateById(umsResourceCategory);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID删除")
    @DeleteMapping(value = "/{id}")
    public CommonResult<Object> delete(@PathVariable Long id) {
        boolean success = resourceCategoryService.removeById(id);
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
