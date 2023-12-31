package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.api.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台资源表
 *
 * @author Qing2514
 */
@Data
@TableName("ums_resource")
@ApiModel(value = "后台资源表对象", description = "UmsResource")
public class UmsResource extends BaseModel {

    @ApiModelProperty("资源名称")
    private String name;

    @ApiModelProperty("资源URL")
    private String url;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("资源分类ID")
    private Long categoryId;

}
