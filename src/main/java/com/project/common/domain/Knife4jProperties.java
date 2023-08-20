package com.project.common.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger 自定义配置
 *
 * @author Qing2514
 */
@Data
@Component
@ConfigurationProperties(prefix = "knife4j")
public class Knife4jProperties {

    /**
     * 是否要启用登录认证
     */
    private boolean enableSecurity;

    /**
     * 是否开启增强功能
     */
    private boolean enableKnife4j;

    /**
     * 基础扫描包范围
     */
    private String basePackage;

    /**
     * 服务器接口文档访问地址
     */
    private String serviceUrl;

    /**
     * 文档标题
     */
    private String title;

    /**
     * 文档描述
     */
    private String description;

    /**
     * 文档版本
     */
    private String version;

    /**
     * 文档联系人姓名
     */
    private String contactName;

    /**
     * 文档联系人网址
     */
    private String contactUrl;

    /**
     * 文档联系人邮箱
     */
    private String contactEmail;

}
