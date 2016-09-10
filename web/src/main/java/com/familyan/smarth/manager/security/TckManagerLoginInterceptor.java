package com.familyan.smarth.manager.security;

import com.familyan.smarth.manager.domain.LoginEmployee;
import com.familyan.smarth.manager.manager.EmployeeManager;
import com.familyan.smarth.manager.manager.PermissionManager;
import com.familyan.smarth.manager.domain.EmployeeOperationDO;
import com.lotus.core.web.security.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * 验证用户是否包含某个权限
 * Created by Admin on 2015/8/18.
 */
public class TckManagerLoginInterceptor extends SecurityHandlerInterceptor<LoginEmployee> {

    @Autowired
    EmployeeManager employeeManager;

    @Autowired
    PermissionManager permissionManager;

    @Override
    public boolean hasPermission(LoginEmployee employee, String code, HandlerMethod hm, HttpServletRequest request) throws com.lotus.core.web.security.SecurityException {
        //提供给 PermissionTool
        Set<Long> userPermissions = employeeManager.findEmployeePermissionById(employee.getId());

        Set<String> userPermissionCodes  = permissionManager.findPermissionCodeByIds(userPermissions);

        request.setAttribute("_userPermissions",userPermissionCodes);

        if(StringUtils.isBlank(code)){
            //没有code,不需要验证
            return true;
        }
        //记录访问日志
        log(employee, code, hm, request);
        // 检查用户是否有某个权限
        if(userPermissionCodes.contains(code))
            return true;
        return false;
    }

    private void log(LoginEmployee employee, String code, HandlerMethod hm, HttpServletRequest request) {
        EmployeeOperationDO op = new EmployeeOperationDO();
        op.setEmpId(employee.getId());
        op.setCode(code);
        op.setEmpName(employee.getName());
        op.setEmpNum(employee.getEmpNum());
        op.setDomain(request.getHeader("Host"));
        op.setUrl(request.getRequestURI());
        op.setParams(extractParam(request));
        op.setIp(request.getRemoteAddr());
        employeeManager.saveEmployeeOperation(op);
    }

    private String extractParam(HttpServletRequest request) {
        Map<String,String[]> param = request.getParameterMap();
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String,String[]> p : param.entrySet()){
            String[] values = p.getValue();
            if(values != null && values.length > 0){
                for(String v : values){
                    builder.append(p.getKey()+"="+v+"&");
                }
            }else{
                builder.append(p.getKey()+"&");
            }
        }
        return builder.toString();
    }

    @Override
    protected String buildPermissionCode(Security methodSecurity, Security classSecurity, HandlerMethod hm) {
        //如果都没有设置 @Security 不需要生成code;
        if(methodSecurity == null  && classSecurity == null)
            return "";
        //方法上设置了不需要权限验证，也返回""
        if( methodSecurity != null && !methodSecurity.needPermission())
            return "";
        //只在类上设置不需要验证，方法上没有设置Security
        if(methodSecurity == null &&  !classSecurity.needPermission()){
            return "";
        }

        //先用指定的code
        String code = super.buildPermissionCode(methodSecurity, classSecurity, hm);
        if(StringUtils.isNotBlank(code))
            return code;

        //如果 code empty，用RequestMapping 来生成
        RequestMapping methodMapping = AnnotationUtils.findAnnotation(hm.getMethod(),RequestMapping.class);
        if(methodMapping == null || methodMapping.value().length == 0)
            throw new RuntimeException("Framework error,please use RequestMapping in handler method. "+hm.toString());

        RequestMapping classMapping = AnnotationUtils.findAnnotation(hm.getBeanType(),RequestMapping.class);
        //只用第一个?
        return classMapping == null ? methodMapping.value()[0]+"" : classMapping.value()[0] +":" + methodMapping.value()[0];
    }
}
