package com.project.modules.ums.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 用户登录参数
 *
 * @author Qing2514
 */
@Data
@ApiModel(value = "用户注册参数", description = "UmsUserParam")
public class UmsUserParam {

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty("用户名")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Size(min = 6, max = 16, message = "密码必须为6~16位")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户头像")
    private String icon;

    @Size(min = 11, max = 11, message = "手机号必须为11位")
    @ApiModelProperty("手机号")
    private String phone;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("备注信息")
    private String note;

}
