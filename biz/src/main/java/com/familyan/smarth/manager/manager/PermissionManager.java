package com.familyan.smarth.manager.manager;

import com.familyan.smarth.manager.vo.MenuVO;
import com.familyan.smarth.manager.domain.PermissionDO;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 2015/8/18.
 */
public interface PermissionManager {

    Integer TYPE_CHANNEL = 1; //频道
    Integer TYPE_MENU_1 = 2; //一级菜单
    Integer TYPE_MENU_2 = 3; //二级菜单 菜单只展示到这一级
    Integer TYPE_FUNC_3 = 4; //二级菜单子功能

    /**
     * 按parentId 返回权限列表
     * @param parentId
     * @return
     */
    List<PermissionDO> findByParent(Long parentId);

    /**
     * 从权限的2,3 类型里面创建菜单列表
     * @param id
     * @return
     */
    List<MenuVO> findMenuByChannelId(Long id);

    /**
     * 分页查询
     * @param p
     * @return
     */
    PageResult<List<PermissionDO>> findByPage(Page p);

    /**
     * 保存权限
     * @param permission
     */
    void create(PermissionDO permission);

    /**
     * 保存ROOT 频道
     * 保存完后需要更新root id
     * @param permission
     */
    void createRoot(PermissionDO permission);

    /**
     * 按id查询权限
     * @param id
     * @return
     */
    PermissionDO findById(Long id);

    /**
     * 更新权限信息
     * @param permission
     */
    public void update(PermissionDO permission);

    /**
     * 加载整个权限树
     * @return
     */
    List<MenuVO> findAll();

    /**
     * 按id 批量查询 code
     * @param userPermissions
     * @return
     */
    Set<String> findPermissionCodeByIds(Collection<Long> userPermissions);

    /**
     * 删除权限
     * @param id
     */
    void remove(Long id);

    /**
     * 找出所有的层级的子节点
     * @param menuId
     * @return
     */
    List<MenuVO> findAllChildrens(Long menuId);

    /**
     * 查出所有子节点的id
     * @param menuId
     * @return
     */
    Set<Long> findAllChildrenIds(Long menuId);

    /**
     * 按 id 批量获取
     * @param ids
     * @return
     */
    List<MenuVO> findByIds(List<Long> ids);

}
