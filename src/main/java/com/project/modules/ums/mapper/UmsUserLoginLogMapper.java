package com.project.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.modules.ums.model.UmsUserLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台用户登录日志表 Mapper 接口
 *
 * @author Qing2514
 */
@Mapper
public interface UmsUserLoginLogMapper extends BaseMapper<UmsUserLoginLog> {}
