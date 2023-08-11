package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.api.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台角色资源关系表
 *
 * @author Qing2514
 */
@Data
@TableName("ums_role_resource_relation")
@ApiModel(value = "后台角色资源关系表对象", description = "UmsRoleResourceRelation")
public class UmsRoleResourceRelation extends BaseModel {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("资源ID")
    private Long resourceId;

}
