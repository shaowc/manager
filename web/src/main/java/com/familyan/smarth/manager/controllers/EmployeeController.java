package com.familyan.smarth.manager.controllers;

import com.familyan.smarth.manager.domain.EmployeeDO;
import com.familyan.smarth.manager.domain.EmployeeOperationDO;
import com.familyan.smarth.manager.domain.EmployeePermissionDO;
import com.familyan.smarth.manager.domain.LoginEmployee;
import com.familyan.smarth.manager.manager.EmployeeManager;
import com.familyan.smarth.manager.manager.PermissionManager;
import com.familyan.smarth.manager.util.DWZPage;
import com.familyan.smarth.manager.util.DWZResponse;
import com.familyan.smarth.manager.util.PasswordUtil;
import com.familyan.smarth.manager.util.PermissionUtil;
import com.familyan.smarth.manager.vo.MenuVO;
import com.lotus.core.web.security.Security;
import com.lotus.service.result.Page;
import com.lotus.service.result.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Admin on 2015/8/18.
 */
@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    EmployeeManager employeeManager;

    @Autowired
    PermissionManager permissionManager;

    @InitBinder
    public  void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
    }

    @RequestMapping(value = "create",method = RequestMethod.POST)
    @ResponseBody
    @Security //默认生成的权限code (employee:create)
    public DWZResponse<?> create(EmployeeDO employeeDO){
        employeeDO.setPassword(PasswordUtil.encrypt(employeeDO.getPassword()));
        employeeDO.setPwdVersion(new Date());
        employeeDO.setStatus(1);

        employeeManager.save(employeeDO);

        return DWZResponse.dialogSuccess("创建员工信息成功!");
    }

    @RequestMapping("list")
    @Security
    public String list(DWZPage page, ModelMap result){
        Page p = new Page(page.getPageNum(),page.getNumPerPage());
        PageResult<List<EmployeeDO>> list = employeeManager.findByPage(p);
        result.addAttribute("result", list);
        return "employee/list";
    }

    @RequestMapping("updateDialog")
    @Security(code = "employee:update")
    public String updateDialog(Long id , ModelMap result){
        EmployeeDO employeeDO = employeeManager.findById(id);
        result.addAttribute("employee", employeeDO);
        return "employee/updateDialog";
    }

    @RequestMapping("update")
    @ResponseBody
    @Security(code = "employee:update")
    public DWZResponse update(EmployeeDO employee){
        if(employee.getId() == null || StringUtils.isBlank(employee.getRealName()) ||
                StringUtils.isBlank(employee.getEmpNum())){
            return DWZResponse.dialogError("参数错误");
        }
        employeeManager.save(employee);
        return DWZResponse.dialogSuccess("更新用户信息成功", "nav_employee:list");
    }

    @RequestMapping("dimission")
    @ResponseBody
    @Security(code = "employee:update")
    public DWZResponse dimission(Long id){
        if(id== null  ){
            return DWZResponse.dialogError("参数错误");
        }
        EmployeeDO employeeDO = new EmployeeDO();
        employeeDO.setId(id);
        employeeDO.setStatus(-1);
        employeeManager.save(employeeDO);
        return DWZResponse.navTabSuccess("离职成功", "nav_employee:list");
    }

    @RequestMapping("setPermission")
    @ResponseBody
    @Security(code="employee:setPermission")
    public DWZResponse setPermission(Long channelId, Long menuId, Long employee,String[] p,LoginEmployee loginEmployee){
        if(menuId == null || employee == null || channelId == null ){
            return DWZResponse.dialogError("参数错误");
        }

        EmployeePermissionDO oldPermissionDO = employeeManager.findEmployeePermissionBeanById(employee);
        if(oldPermissionDO == null){
            if(p == null || p.length == 0){
                return DWZResponse.dialogError("没有设置权限");
            }else{
                //新加权限
                EmployeePermissionDO permissionDO = new EmployeePermissionDO();
                permissionDO.setEmpId(employee);
                permissionDO.setAuthorizer(loginEmployee.getId());
                permissionDO.setPermissions(PermissionUtil.join(p));
                permissionDO.setChannels(channelId + "");
                permissionDO.setMenus(menuId + "");
                employeeManager.saveEmployeePermission(permissionDO);
            }
        }else{

            //设置权限
            //取出二级菜单下所有的子节点id
            Set<Long> ids = permissionManager.findAllChildrenIds(menuId);

            Set<Long> oldPermissions = PermissionUtil.split(oldPermissionDO.getPermissions());

            //删除该节点下所有权限
            oldPermissions.removeAll(ids);

            String permissions = null;
            String channels =  null ;
            String menus = null;
            if(p != null && p.length != 0){
                //加入新配置的权限
                for(String per : p){
                    oldPermissions.add(Long.parseLong(per));
                }
                channels = PermissionUtil.addPermissions(oldPermissionDO.getChannels(),channelId);
                menus = PermissionUtil.addPermissions(oldPermissionDO.getMenus(),menuId);
            }else{
                //删除权限,需要判断频道和菜单需不需要删除
                //菜单肯定删除
                menus = PermissionUtil.removePermissions(oldPermissionDO.getMenus(),menuId);
                if(StringUtils.isBlank(menus)){
                    //该频道下已经没有菜单,该频道也删除
                    channels = PermissionUtil.removePermissions(oldPermissionDO.getChannels(),channelId);
                }else
                    channels = oldPermissionDO.getChannels();
            }
            permissions = PermissionUtil.join(oldPermissions);

            EmployeePermissionDO permissionDO = new EmployeePermissionDO();
            permissionDO.setEmpId(employee);
            permissionDO.setAuthorizer(loginEmployee.getId());
            permissionDO.setPermissions(permissions);
            permissionDO.setChannels(channels);
            permissionDO.setMenus(menus);
            employeeManager.saveEmployeePermission(permissionDO);
        }
        //不关闭当前页面
        return new DWZResponse<>(DWZResponse.CODE_SUCCESS,"保存用户权限成功", null, null,null,null);

    }

    @RequestMapping("operations")
    @Security
    public String operations(String empNum  ,DWZPage page,ModelMap result){
        PageResult<List<EmployeeOperationDO>> operation = null;
        if(StringUtils.isNotBlank(empNum)){
            EmployeeDO employeeDO = employeeManager.findByEmpNum(empNum);
            if(employeeDO != null)
                operation = employeeManager.findOperationsByEmpId(employeeDO.getId(),
                    new Page(page.getPageNum(),page.getNumPerPage()));
        }else
            operation = employeeManager.findOperationsByEmpId(null,
                    new Page(page.getPageNum(),page.getNumPerPage()));

        result.put("result", operation);
        return "employee/operations";
    }

    @RequestMapping("setPermissionDialog2")
    @Security(code="employee:setPermission")
    public String setPermissionDialog2(Long id ,ModelMap result){
        List<MenuVO> allMenu = permissionManager.findAll();
        Set<Long> userPermissions = employeeManager.findEmployeePermissionById(id);
        EmployeeDO user = employeeManager.findById(id);
        result.put("menus", allMenu);
        result.put("userPermissions", userPermissions);
        result.put("user", user);
        return "employee/setPermissionDialog2";
    }

    @RequestMapping("functions")
    @Security(code="employee:setPermission")
    public String functions(Long channelId,Long menuId ,Long employee ,ModelMap result){
        List<MenuVO> childrens = permissionManager.findAllChildrens(menuId);
        Set<Long> userPermissions = employeeManager.findEmployeePermissionById(employee);
        EmployeeDO user = employeeManager.findById(employee);

        result.put("channelId",channelId);
        result.put("menuId",menuId);
        result.put("employee",employee);
        result.put("menus", childrens);
        result.put("userPermissions", userPermissions);
        result.put("user", user);
        return "employee/functions";
    }
}
