package com.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.mapper.UmsResourceCategoryMapper;
import com.project.modules.ums.model.UmsResourceCategory;
import com.project.modules.ums.service.UmsResourceCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台资源分类管理Service实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsResourceCategoryServiceImpl extends ServiceImpl<UmsResourceCategoryMapper, UmsResourceCategory> implements UmsResourceCategoryService {

    @Override
    public List<UmsResourceCategory> listAll() {
        return list();
    }

    @Override
    public boolean create(UmsResourceCategory umsResourceCategory) {
        return save(umsResourceCategory);
    }

}
