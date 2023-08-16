package com.project.modules.ums.dto;

import com.project.modules.ums.model.UmsMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台菜单节点封装
 *
 * @author Qing2514
 */
@Getter
@Setter
@ApiModel(value = "菜单节点", description = "UmsMenuNode")
public class UmsMenuNode extends UmsMenu {

    @ApiModelProperty("子级菜单")
    private List<UmsMenuNode> children;

}
