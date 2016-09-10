package com.familyan.smarth.manager.controllers;

import com.familyan.smarth.manager.domain.EmployeeDO;
import com.familyan.smarth.manager.domain.LoginEmployee;
import com.familyan.smarth.manager.manager.EmployeeManager;
import com.familyan.smarth.manager.manager.PermissionManager;
import com.familyan.smarth.manager.util.DWZResponse;
import com.familyan.smarth.manager.util.PermissionUtil;
import com.familyan.smarth.manager.vo.MenuVO;
import com.familyan.smarth.manager.domain.EmployeePermissionDO;
import com.familyan.smarth.manager.util.PasswordUtil;
import com.lotus.core.web.cookyjar.Cookyjar;
import com.lotus.core.web.security.Security;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2015/8/16.
 */
@Controller
public class HomeController {

    @Autowired
    EmployeeManager employeeManager;

    @Autowired
    PermissionManager permissionManager;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public String login(LoginEmployee employee,String url,ModelMap result){
        //已登录
        if(employee != null){
            if(StringUtils.isNotBlank(url)){
                return "redirect:"+url;
            }
            return "redirect:/index.htm";
        }
        result.put("url",url);
        return "login";
    }

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String userLogin(String username, String password, String url,
                            Cookyjar cookyjar,LoginEmployee employee,
                            ModelMap result){
        //未登录
        if(employee == null){
            if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
                result.put("error","参数不能为空");
                return "login";
            }
            EmployeeDO employeeDO  = employeeManager.findByEmpNumAndPassword(username,PasswordUtil.encrypt(password));
            if(employeeDO == null){
                result.put("error","密码错误");
                return "login";
            }
            if(employeeDO.getStatus() != 1){
                result.put("error","离职用户不能登录");
                return "login";
            }

            employee = new LoginEmployee();

            employee.setName(employeeDO.getRealName());
            employee.setEmpNum(employeeDO.getEmpNum());
            employee.setId(employeeDO.getId());
            employee.setVersion(employeeDO.getPwdVersion().getTime());
            //过期时间60分钟
            cookyjar.set(employee,60 * 60 * 4);
            cookyjar.set("_empId", employeeDO.getId());
        }

        if(StringUtils.isNotBlank(url)){
            return "redirect:"+url;
        }
        return "redirect:/index.htm";
    }

    @RequestMapping({"index","/"})
    @Security(needPermission = false) //不需要校验权限，只检验是否登录
    public void index(LoginEmployee employee,ModelMap result){

        EmployeePermissionDO employeePermissionDO = employeeManager.findEmployeePermissionBeanById(employee.getId());
        if(employeePermissionDO != null){
            List<Long> channels = PermissionUtil.splitList(employeePermissionDO.getChannels());
            if(channels.size() > 0 ){
                //频道
                List<MenuVO> channelMenus = permissionManager.findByIds(channels);
                result.put("channels",channelMenus);

                //菜单
                MenuVO current = channelMenus.get(0);
                result.addAttribute("currentChannel", current.getId());//默认到1

                List<MenuVO> menus = permissionManager.findMenuByChannelId(current.getId());

                result.addAttribute("menus",menus);
            }
        }

//        List<PermissionDO> channels =  permissionManager.findByParent(0L);
//        final Set<Long> userPermissions = employeeManager.findEmployeePermissionById(employee.getId());
//
//        channels = FluentIterable.from(channels).filter(new Predicate<PermissionDO>() {
//            @Override
//            public boolean apply(PermissionDO input) {
//                return userPermissions.contains(input.getId());
//            }
//        }).toList();
//
//        if(channels.size() > 0){
//            //first
//            PermissionDO first = channels.get(0);
//            result.addAttribute("currentChannel", first.getId());//默认到1
//
//            List<MenuVO> menus = permissionManager.findMenuByChannelId(first.getId());
//
//            result.addAttribute("channels",channels);
//            result.addAttribute("menus",menus);
//        }
    }

    @RequestMapping("logout")
    @Security(needPermission = false)
    public String logout( Cookyjar cookyjar){
        cookyjar.remove(LoginEmployee.class);
        cookyjar.remove("_empId");
        return "redirect:/login.htm";
    }

    @RequestMapping(value = "changePwd", method = RequestMethod.POST)
    @ResponseBody
    @Security(needPermission = false)
    public DWZResponse<?> changePwd( String pwd, String newPwd, String pwdAgain,
            LoginEmployee employee,Cookyjar cookyjar){
        if(StringUtils.length(newPwd) < 6){
            return DWZResponse.dialogError( "密码长度小于6");
        }
        if(!StringUtils.equals(newPwd,pwdAgain)){
            return DWZResponse.dialogError( "密码不一致");
        }
        EmployeeDO employeeDO = employeeManager.findById(employee.getId());

        if(!StringUtils.equals(PasswordUtil.encrypt(pwd),employeeDO.getPassword())){
            return DWZResponse.dialogError( "原密码错误");
        }
        Date pwdVersion = employeeManager.changePassword(employee.getId(), PasswordUtil.encrypt(newPwd));

        employee.setVersion(pwdVersion.getTime());

        cookyjar.set(employee, 60 * 60);

        return DWZResponse.dialogSuccess("密码修改成功");
    }

    @RequestMapping("channel/{channelId}")
    @Security(needPermission = false)
    public String channel(@PathVariable Long channelId,LoginEmployee employee,ModelMap result){
        result.addAttribute("currentChannel", channelId);
        List<MenuVO> menus = permissionManager.findMenuByChannelId(channelId);
        result.addAttribute("menus", menus);
        return "channel";
    }

    @RequestMapping("exception")
    public void exception(){
        throw new NullPointerException("ee");
    }


}
