package com.project.modules.ums.controller;

import com.project.common.api.CommonResult;
import com.project.util.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO 文件管理 Controller
 *
 * @author Qing2514
 */
@RestController
@Api(value = "FileController", tags = "文件管理")
@RequestMapping("/file")
public class FileController {

    @Autowired
    private MinioUtil minioUtil;

    @ApiOperation(value = "创建文件夹")
    @PostMapping("/bucket")
    public CommonResult<Object> createBucket(@RequestParam("bucketName") String bucketName) {
        boolean success = minioUtil.createBucket(bucketName);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation(value = "删除文件夹")
    @DeleteMapping("/bucket")
    public CommonResult<Object> deleteBucket(@RequestParam("bucketName") String bucketName) {
        boolean success = minioUtil.deleteBucket(bucketName);
        return success ? CommonResult.success() : CommonResult.failed();
    }

    @ApiOperation(value = "根据文件名查询url")
    @GetMapping("")
    public CommonResult<String> getFileUrl(@RequestParam("fileName") String fileName) {
        return CommonResult.success(minioUtil.getObject(fileName));
    }

    @ApiOperation(value = "上传文件返回url")
    @PostMapping("/upload")
    public CommonResult<String> upload(MultipartFile file) {
        return CommonResult.success(minioUtil.upload(file));
    }

    @ApiOperation(value = "删除文件")
    @DeleteMapping("")
    public CommonResult<Object> deleteFile(@RequestParam("fileName") String fileName) {
        boolean success = minioUtil.deleteObject(fileName);
        return success ? CommonResult.success() : CommonResult.failed();
    }

}
