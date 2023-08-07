package com.project.common.api;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础 Model
 *
 * @author Qing2514
 */
@Data
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建用户")
    private String createBy;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "修改用户")
    private String updateBy;

    @ApiModelProperty(value = "逻辑删除标识：0->否；1->是")
    @TableField(select = false)
    private Integer deleted = 0;

}
