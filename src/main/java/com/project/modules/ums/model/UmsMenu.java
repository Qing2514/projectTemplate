package com.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.project.common.api.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后台菜单表
 *
 * @author Qing2514
 */
@Data
@TableName("ums_menu")
@ApiModel(value = "后台菜单表对象", description = "UmsMenu")
public class UmsMenu extends BaseModel {

    @ApiModelProperty("父级ID")
    private Long parentId;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单级数")
    private Integer level;

    @ApiModelProperty("前端名称")
    private String name;

    @ApiModelProperty("前端图标")
    private String icon;

    @ApiModelProperty("前端隐藏")
    private Integer hidden;

}
