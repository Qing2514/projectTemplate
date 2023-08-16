package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.api.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台用户角色表
 *
 * @author Qing2514
 */
@Data
@TableName("ums_role")
@ApiModel(value = "后台用户角色表对象", description = "UmsRole")
public class UmsRole extends BaseModel {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("帐号启用状态：0->禁用；1->启用")
    private Integer status = 1;

}
