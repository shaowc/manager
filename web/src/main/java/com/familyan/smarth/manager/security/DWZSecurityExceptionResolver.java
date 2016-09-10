package com.familyan.smarth.manager.security;

import com.lotus.core.web.security.SecurityExceptionResolver;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Admin on 2015/8/16.
 */
public class DWZSecurityExceptionResolver extends SecurityExceptionResolver {

    // keys: {statusCode:"code", message:"msg"}, //【可选】
    // statusCode:{ok:1, error:2, timeout:3}, //【可选】
    @Override
    protected ModelAndView noPermission(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(isAjax(request)){
            ModelAndView mv =  new ModelAndView();
            mv.setView(new MappingJackson2JsonView());
            mv.addObject("code", 2);
            mv.addObject("msg","没有权限，请申请!");
            return mv;
        }
        return super.noPermission(request, response, handler, ex);
    }

    @Override
    protected ModelAndView notLogin(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(isAjax(request)){
            ModelAndView mv =  new ModelAndView();
            mv.setView(new MappingJackson2JsonView());
            mv.addObject("code", 3);
            mv.addObject("msg","登录超时或未登录");
            return mv;
        }
        return super.notLogin(request, response, handler, ex);
    }

    private boolean isAjax(HttpServletRequest request) {
        //X-Requested-With:XMLHttpRequest
        return StringUtils.equals(request.getHeader("X-Requested-With"), "XMLHttpRequest");
    }

}
