package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.api.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台用户和角色关系表
 *
 * @author Qing2514
 */
@Data
@TableName("ums_user_role_relation")
@ApiModel(value = "UmsUserRoleRelation对象", description = "后台用户和角色关系表")
public class UmsUserRoleRelation extends BaseModel {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

}
