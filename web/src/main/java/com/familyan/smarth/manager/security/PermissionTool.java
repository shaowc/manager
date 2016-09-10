package com.familyan.smarth.manager.security;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.view.ViewContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Admin on 2015/8/22.
 */
public class PermissionTool {

    private HttpServletRequest request;

    private Set<String> userPermissionCodes ;

    public void init(Object obj) {
        if (!(obj instanceof ViewContext)) {
            throw new IllegalArgumentException(
                    "Tool can only be initialized with a ViewContext");
        }
        ViewContext viewContext = (ViewContext) obj;
        request = viewContext.getRequest();
    }

    public boolean contains(String code){
        if(StringUtils.isBlank(code))  throw new IllegalArgumentException("arg 'code' can not be null.");
        if(userPermissionCodes == null){
            userPermissionCodes = (Set<String>) request.getAttribute("_userPermissions");
            if(userPermissionCodes == null)
                userPermissionCodes = new HashSet<>();
        }
        return userPermissionCodes.contains(code);
    }


}
