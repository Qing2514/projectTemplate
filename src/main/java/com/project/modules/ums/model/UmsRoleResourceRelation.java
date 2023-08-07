package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.api.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 后台角色资源关系表
 * </p>
 *
 * @author Qing2514
 */
@Data
@TableName("ums_role_resource_relation")
@ApiModel(value="UmsRoleResourceRelation对象", description="后台角色资源关系表")
public class UmsRoleResourceRelation extends BaseModel {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "资源ID")
    private Long resourceId;

}
