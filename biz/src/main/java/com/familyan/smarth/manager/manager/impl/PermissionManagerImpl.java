package com.familyan.smarth.manager.manager.impl;

import com.familyan.smarth.manager.domain.PermissionDO;
import com.familyan.smarth.manager.manager.PermissionManager;
import com.familyan.smarth.manager.mapper.PermissionMapper;
import com.familyan.smarth.manager.vo.MenuVO;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Admin on 2015/8/18.
 */
@Service
@Transactional(readOnly = true)
public class PermissionManagerImpl implements PermissionManager {

    @Autowired
    PermissionMapper mapper;

    @Override
    public List<PermissionDO> findByParent(Long parentId) {
        return mapper.findByParent(parentId);
    }

    @Override
    public List<MenuVO> findMenuByChannelId(Long id) {
        List<PermissionDO> permissions = mapper.findByRootIdAndTypes(id, new Integer[]{TYPE_MENU_1, TYPE_MENU_2});
        return createMenuTree(TYPE_MENU_1, permissions);
    }

    private List<MenuVO> createMenuTree(final Integer rootType, List<PermissionDO> permissions) {

        Map<Long,MenuVO> maps = FluentIterable.from(permissions).transform(new Function<PermissionDO, MenuVO>() {
            @Override
            public MenuVO apply(PermissionDO input) {
                return createMenu(input);
            }
        }).uniqueIndex(new Function<MenuVO, Long>() {
            @Override
            public Long apply(MenuVO input) {
                return input.getId();
            }
        });

        List<MenuVO> menus = new ArrayList<>();
        for(MenuVO menu : maps.values()){
            if(menu.getType().equals(rootType)){
                menus.add(menu);
                continue;
            }
            MenuVO parent = maps.get(menu.getParentId());
            if(parent != null){
                parent.addChildren(menu);
            }
        }

        return menus;
    }

    private MenuVO createMenu(PermissionDO permission) {
        MenuVO menu = new MenuVO();
        menu.setId(permission.getId());
        menu.setCode(permission.getCode());
        menu.setDomain(permission.getDomain());
        menu.setName(permission.getName());
        menu.setParentId(permission.getParent());
        menu.setType(permission.getType());
        menu.setUrl(permission.getUrl());
        menu.setShow(permission.getShow());
        return menu;
    }

    @Override
    public PageResult<List<PermissionDO>> findByPage(Page p) {
        List<PermissionDO> r = mapper.findByPage(p);
        int total = mapper.countAll();
        return new PageResult<>(p.getStart(),p.getPageSize(),total, r);
    }

    @Override
    @Transactional(readOnly = false)
    public void create(PermissionDO permission) {
        mapper.insert(permission);
    }

    @Override
    @Transactional(readOnly = false)
    public void createRoot(PermissionDO permission) {
        create(permission);
        permission.setRoot(permission.getId());
        update(permission);
    }

    @Transactional(readOnly = false)
    public void update(PermissionDO permission) {
        mapper.update(permission);
    }


    @Override
    public PermissionDO findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public List<MenuVO> findAll() {
        List<PermissionDO> all = mapper.findAll();
        return createMenuTree(TYPE_CHANNEL,all);
    }

    @Override
    public Set<String> findPermissionCodeByIds(Collection<Long> userPermissions) {
        if(userPermissions == null || userPermissions.size() == 0)
            return Collections.EMPTY_SET;
        List<String> permissions = mapper.findPermissionCodeByIds(userPermissions);
        return new HashSet<>(permissions);
    }

    @Override
    @Transactional(readOnly = false)
    public void remove(Long id) {
        mapper.delete(id);
    }


    @Override
    public List<MenuVO> findAllChildrens(Long id) {
//        PermissionDO p = mapper.findById(id);
//        if(p == null){
//            return Collections.EMPTY_LIST;
//        }
        List<MenuVO> childs = new ArrayList<>();
        List<PermissionDO> children = mapper.findByParent(id);
        for(PermissionDO p : children){
            MenuVO menuVO = createMenu(p);
            childs.add(menuVO);
            if(p.getType() < TYPE_FUNC_3){
                List<MenuVO> cls = findAllChildrens(p.getId());
                if(cls.size() > 0){
                    menuVO.setChildrens(cls);
                }
            }
        }
        return childs;
    }

    @Override
    public Set<Long> findAllChildrenIds(Long id) {
        Set<Long> childs = new HashSet<>();
        List<PermissionDO> children = mapper.findByParent(id);
        for(PermissionDO p : children){
            childs.add(p.getId());
            if(p.getType() < TYPE_FUNC_3){
                Set<Long>  cls = findAllChildrenIds(p.getId());
                if(cls.size() > 0){
                    childs.addAll(cls);
                }
            }
        }
        return childs;
    }

    @Override
    public List<MenuVO> findByIds(List<Long> ids) {
        List<PermissionDO> permissions = mapper.findByIds(ids);
        return Lists.transform(permissions, new Function<PermissionDO, MenuVO>() {
            @Override
            public MenuVO apply(PermissionDO input) {
                return createMenu(input);
            }
        });
    }
}
