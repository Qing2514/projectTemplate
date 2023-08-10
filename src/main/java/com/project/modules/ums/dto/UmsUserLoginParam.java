package com.project.modules.ums.dto;

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
public class UmsUserLoginParam {

    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 16, message = "密码必须为6~16位")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
