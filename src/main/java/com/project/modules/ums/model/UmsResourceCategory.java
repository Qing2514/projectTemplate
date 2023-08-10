package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.api.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源分类表
 *
 * @author Qing2514
 */
@Data
@TableName("ums_resource_category")
@ApiModel(value = "UmsResourceCategory 对象", description = "资源分类表")
public class UmsResourceCategory extends BaseModel {

    @ApiModelProperty(value = "分类名称")
    private String name;

}
