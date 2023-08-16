package com.project.modules.ums.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "修改密码参数", description = "UpdatePasswordParam")
public class UpdatePasswordParam {

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotEmpty(message = "旧密码不能为空")
    @Size(min = 6, max = 16, message = "密码必须为6~16位")
    @ApiModelProperty("旧密码")
    private String oldPassword;

    @NotEmpty(message = "新密码不能为空")
    @Size(min = 6, max = 16, message = "密码必须为6~16位")
    @ApiModelProperty("新密码")
    private String newPassword;

}
