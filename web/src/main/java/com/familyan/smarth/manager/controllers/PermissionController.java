package com.familyan.smarth.manager.controllers;

import com.familyan.smarth.manager.manager.PermissionManager;
import com.familyan.smarth.manager.util.DWZResponse;
import com.familyan.smarth.manager.vo.MenuVO;
import com.familyan.smarth.manager.domain.PermissionDO;
import com.familyan.smarth.manager.util.DWZPage;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Admin on 2015/8/18.
 */
@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    PermissionManager permissionManager;

    @RequestMapping("create")
    @ResponseBody
    @Security
    public DWZResponse<?> create(PermissionDO permission){
        if(StringUtils.isBlank(permission.getCode())){
            return DWZResponse.dialogError("Code 为null");
        }
        if(StringUtils.isBlank(permission.getName())){
            return DWZResponse.dialogError("Name 为null");
        }
        if(permission.getType() == null || permission.getType() < 0 ){
            return DWZResponse.dialogError("参数错误");
        }
        permission.setBase(false);
        if(StringUtils.isBlank(permission.getDomain())){
            permission.setDomain(null);
        }
        if( permission.getType() == PermissionManager.TYPE_CHANNEL){
            //频道
            permission.setParent(0L);
            permission.setRoot(0L);//
            permissionManager.createRoot(permission);;
        }else {
            //2, 3, 4
            if(permission.getParent() == null || permission.getParent() < 1){
                return DWZResponse.dialogError("参数错误");
            }
            //if((permission.getType() == PermissionManager.TYPE_MENU_2 || permission.getType() == PermissionManager.TYPE_FUNC_3)
            if(permission.getType() == PermissionManager.TYPE_MENU_2
                    && StringUtils.isBlank(permission.getUrl())){
                return DWZResponse.dialogError("设置URL地址");
            }
            PermissionDO parent = permissionManager.findById(permission.getParent());
            if(parent == null)
                return DWZResponse.dialogError("参数错误");

            permission.setRoot(parent.getRoot());

            permissionManager.create(permission);
        }

        return DWZResponse.dialogSuccess("添加权限成功","nav_permission:list");
    }

    @RequestMapping("list")
    @Security
    public String list(DWZPage page,ModelMap map){
        Page p = new Page(page.getPageNum(),page.getNumPerPage());
        PageResult<List<PermissionDO>> result = permissionManager.findByPage(p);
        map.put("result",result);
        return "permission/list";
    }

    @RequestMapping("showPermissionTree")
    @Security
    public String showPermissionTree(ModelMap result){
        List<MenuVO> menus = permissionManager.findAll();
        result.addAttribute("menus", menus);
        return "permission/showPermissionTree";
    }

    @RequestMapping("show")
    @ResponseBody
    @Security
    public DWZResponse show(Long id, Boolean show){
        if(id == null || show == null)
            return DWZResponse.dialogError("参数错误");
        PermissionDO permission = new PermissionDO();
        permission.setId(id);
        permission.setShow(show);
        permissionManager.update(permission);

        return DWZResponse.navTabSuccess("操作成功","nav_permission:list");
    }

    @RequestMapping("remove")
    @ResponseBody
    @Security
    public DWZResponse remove(Long id ){
        if(id == null || id < 1){
            return DWZResponse.navTabError("参数错误");
        }
        List<PermissionDO> childrens = permissionManager.findByParent(id);
        if(childrens.size() > 0){
            return DWZResponse.navTabError("请先删除子权限!");
        }

        permissionManager.remove(id);

        return  DWZResponse.navTabSuccess("删除权限成功","nav_permission:list");
    }

    @RequestMapping("updateDialog")
    @Security(code="permission:update")
    public String updateDialog(Long id,ModelMap result){
        PermissionDO permissionDO = permissionManager.findById(id);
        result.put("permission", permissionDO);
        return "permission/updateDialog";
    }

    @RequestMapping("update")
    @ResponseBody
    @Security(code="permission:update")
    public DWZResponse update(PermissionDO permission){
        if(permission.getId() == null || StringUtils.isBlank(permission.getCode())||
                StringUtils.isBlank(permission.getName())){
            return DWZResponse.dialogError("参数错误");
        }
        permissionManager.update(permission);
        return DWZResponse.dialogSuccess("修改权限信息成功","nav_permission:list");
    }
}
