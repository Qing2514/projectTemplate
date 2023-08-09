package com.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.modules.ums.dto.UmsMenuNode;
import com.project.modules.ums.mapper.UmsMenuMapper;
import com.project.modules.ums.model.UmsMenu;
import com.project.modules.ums.service.UmsMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台菜单管理 Service 实现类
 *
 * @author Qing2514
 */
@Slf4j
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements UmsMenuService {

    @Override
    public List<UmsMenu> getByUserId(Long userId) {
        return getBaseMapper().getByUserId(userId);
    }

    @Override
    public List<UmsMenu> getByRoleId(Long roleId) {
        return getBaseMapper().getByRoleId(roleId);
    }

    @Override
    public Page<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        Page<UmsMenu> page = new Page<>(pageNum,pageSize);
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsMenu::getParentId,parentId);
        return page(page,wrapper);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList = list();
        return menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
    }

    @Override
    public boolean create(UmsMenu umsMenu) {
        updateLevel(umsMenu);
        return save(umsMenu);
    }

    @Override
    public boolean update(UmsMenu umsMenu) {
        updateLevel(umsMenu);
        return updateById(umsMenu);
    }

    @Override
    public boolean updateHidden(Long id, Integer hidden) {
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return updateById(umsMenu);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(UmsMenu umsMenu) {
        if (umsMenu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            umsMenu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            UmsMenu parentMenu = getById(umsMenu.getParentId());
            if (parentMenu != null) {
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                umsMenu.setLevel(0);
            }
        }
    }

    /**
     * 将 UmsMenu 转化为 UmsMenuNode 并设置 children 属性
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

}
