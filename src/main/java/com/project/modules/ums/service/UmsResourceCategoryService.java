package com.project.modules.ums.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.project.modules.ums.model.UmsResourceCategory;

import java.util.List;

/**
 * 后台资源分类管理Service
 * @author Qing2514
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 获取所有资源分类
     * @return 资源分类列表
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     * @param umsResourceCategory 资源分类
     * @return 成功标志
     */
    boolean create(UmsResourceCategory umsResourceCategory);

}
