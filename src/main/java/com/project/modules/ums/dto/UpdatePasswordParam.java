package com.project.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 修改用户名密码参数
 *
 * @author Qing2514
 */
@Data
public class UpdatePasswordParam {

    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 16, message = "密码必须为6~16位")
    @ApiModelProperty(value = "旧密码", required = true)
    private String oldPassword;

    @NotEmpty
    @Size(min = 6, max = 16, message = "密码必须为6~16位")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

}
