package com.project.util;

import com.project.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO 工具类
 *
 * @author Qing2514
 */
@Slf4j
@Component
public class MinioUtil {

    @Autowired
    private MinioConfig prop;

    @Autowired
    private MinioClient minioClient;

    /**
     * 根据桶名查询桶是否存在
     */
    public boolean bucketExists(String bucketName) {
        boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }

    /**
     * 根据桶名添加桶
     */
    public boolean createBucket(String bucketName) {
        if (bucketExists(bucketName)) {
            return false;
        }
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据桶名删除桶
     */
    public boolean deleteBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据文件名查询url
     */
    public String getObject(String fileName) {
        // 查看文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
                .bucket(prop.getBucketName())
                .object(fileName)
                .method(Method.GET)
                .build();
        try {
            return minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     */
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (!StringUtils.hasLength(originalFilename)) {
            throw new RuntimeException();
        }
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(prop.getBucketName())
                    .object(originalFilename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            // 文件名称相同会覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return getObject(originalFilename);
    }

    /**
     * 删除文件
     */
    public boolean deleteObject(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(prop.getBucketName())
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}