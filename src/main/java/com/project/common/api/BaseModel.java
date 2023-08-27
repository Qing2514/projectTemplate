package com.project.common.api;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId("id")
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建用户")
    private String createBy;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty("修改时间")
    private Date updateTime;

    @TableField(fill = FieldFill.UPDATE)
    @ApiModelProperty("修改用户")
    private String updateBy;

    @ApiModelProperty("逻辑删除标识：0->否；1->是")
    @TableField(select = false)
    private Integer deleted = 0;

}
