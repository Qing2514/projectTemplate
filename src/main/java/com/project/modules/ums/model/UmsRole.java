package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 后台用户角色表
 * </p>
 *
 * @author Qing2514
 */
@Data
@TableName("ums_role")
@ApiModel(value="UmsRole对象", description="后台用户角色表")
public class UmsRole extends BaseModel {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "后台用户数量")
    private Integer userCount = 0;

    @ApiModelProperty(value = "帐号启用状态：0->禁用；1->启用")
    private Integer status = 1;

}
