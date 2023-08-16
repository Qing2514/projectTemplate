package com.project.modules.ums.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 用户登录参数
 *
 * @author Qing2514
 */
@Data
@ApiModel(value = "用户登录参数", description = "UmsUserLoginParam")
public class UmsUserLoginParam {

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "密码必须为6~16位")
    @ApiModelProperty("密码")
    private String password;

}
