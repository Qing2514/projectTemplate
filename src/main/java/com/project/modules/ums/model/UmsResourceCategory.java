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
 * 资源分类表
 * </p>
 *
 * @author Qing2514
 */
@Data
@TableName("ums_resource_category")
@ApiModel(value="UmsResourceCategory对象", description="资源分类表")
public class UmsResourceCategory extends BaseModel {

    @ApiModelProperty(value = "分类名称")
    private String name;

}
