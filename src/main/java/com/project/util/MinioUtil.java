package com.project.util;

import com.project.common.api.ResultCode;
import com.project.common.exception.Asserts;
import com.project.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
            found = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (Exception e) {
            log.error("get bucket failed: {}", e.getMessage());
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
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (Exception e) {
            log.error("create bucket failed: {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 根据桶名删除桶
     */
    public boolean deleteBucket(String bucketName) {
        try {
            minioClient.removeBucket(
                    RemoveBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (Exception e) {
            log.error("delete bucket failed: {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 根据文件名查询文件是否存在
     */
    public boolean objectExists(String fileName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(prop.getBucketName())
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            log.error("file not exits: {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 根据文件名查询url
     */
    public String getObject(String fileName) {
        String encryptedFileName = encryptFileName(fileName);
        Asserts.isTrue(objectExists(encryptedFileName), ResultCode.FILE_NOT_EXISTS);
        // 查看文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder()
                .bucket(prop.getBucketName())
                .object(encryptedFileName)
                .method(Method.GET)
                .build();
        try {
            return minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            log.error("get object failed: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 上传文件
     */
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String encryptedFileName = encryptFileName(originalFilename);
        Asserts.notTrue(objectExists(encryptedFileName), ResultCode.FILE_EXISTS);
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder()
                    .bucket(prop.getBucketName())
                    .object(encryptedFileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            // 文件名相同时原文件会被覆盖
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            log.error("upload file error: {}", e.getMessage());
            return null;
        }
        return getObject(originalFilename);
    }

    /**
     * 删除文件
     */
    public boolean deleteObject(String fileName) {
        String encryptedFileName = encryptFileName(fileName);
        Asserts.isTrue(objectExists(encryptedFileName), ResultCode.FILE_NOT_EXISTS);
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(prop.getBucketName())
                            .object(encryptedFileName)
                            .build()
            );
        } catch (Exception e) {
            log.error("delete object failed: {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 使用MD5算法对文件名加密
     */
    private static String encryptFileName(String fileName) {
        Asserts.notNull(fileName, ResultCode.FILENAME_NULL);
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            Asserts.fail(ResultCode.FILE_NOT_EXISTS);
        }
        String fileType = fileName.substring(index);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(fileName.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString().concat(fileType);
        } catch (NoSuchAlgorithmException e) {
            log.error("encrypt file name failed: {}", e.getMessage());
        }
        return null;
    }

}