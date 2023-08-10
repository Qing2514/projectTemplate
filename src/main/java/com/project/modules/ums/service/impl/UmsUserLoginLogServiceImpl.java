package com.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.mapper.UmsUserLoginLogMapper;
import com.project.modules.ums.model.UmsUserLoginLog;
import com.project.modules.ums.service.UmsUserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户登录日志管理 Service 实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsUserLoginLogServiceImpl extends ServiceImpl<UmsUserLoginLogMapper, UmsUserLoginLog> implements UmsUserLoginLogService {}
