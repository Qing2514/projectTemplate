package com.project.modules.ums.controller;

import com.project.common.api.CommonResult;
import com.project.modules.ums.model.UmsResourceCategory;
import com.project.modules.ums.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台资源分类管理 Controller
 *
 * @author Qing2514
 */
@RestController
@Api(value = "UmsResourceCategoryController", tags = "后台资源分类管理")
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有")
    @GetMapping("")
    public CommonResult<List<UmsResourceCategory>> getAll() {
        List<UmsResourceCategory> resourceList = resourceCategoryService.list();
        return CommonResult.success(resourceList);
    }

    @ApiOperation("添加")
    @PostMapping("")
    public CommonResult<Object> create(@RequestBody UmsResourceCategory umsResourceCategory) {
        boolean success = resourceCategoryService.save(umsResourceCategory);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("修改")
    @PutMapping("")
    public CommonResult<Object> update(@RequestBody UmsResourceCategory umsResourceCategory) {
        boolean success = resourceCategoryService.updateById(umsResourceCategory);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation("根据ID删除")
    @DeleteMapping("/{id}")
    public CommonResult<Object> delete(@PathVariable Long id) {
        boolean success = resourceCategoryService.removeById(id);
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
