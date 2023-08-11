package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.api.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 后台用户登录日志表
 *
 * @author Qing2514
 */
@Data
@Builder
@TableName("ums_user_login_log")
@ApiModel(value = "后台用户登录日志表对象", description = "UmsUserLoginLog")
public class UmsUserLoginLog extends BaseModel {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("登陆地IP")
    private String ip;

    @ApiModelProperty("登录地址")
    private String address;

    @ApiModelProperty("浏览器登录类型")
    private String userAgent;

}
