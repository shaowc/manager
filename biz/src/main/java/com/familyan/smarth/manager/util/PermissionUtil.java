package com.familyan.smarth.manager.util;

import com.google.common.base.Joiner;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xifeng on 2015/9/18.
 */
public class PermissionUtil {

    /**
     * 解析账号的权限, 以, 分隔
     * @param permissions
     * @return
     */
    public static Set<Long> split(String permissions){
        Set<Long> result = new HashSet<>();
        if( StringUtils.isNotBlank(permissions)){
            String[] ps = StringUtils.split(permissions,',');
            for(String p : ps){
                result.add(Long.parseLong(p));
            }
        }
        return result;
    }

    public static List<Long> splitList(String permissions){
        List<Long> result = new ArrayList<>();
        if( StringUtils.isNotBlank(permissions)){
            String[] ps = StringUtils.split(permissions,',');
            for(String p : ps){
                result.add(Long.parseLong(p));
            }
        }
        return result;
    }

    public static String join(Set<Long> permissions){
        return Joiner.on(",").join(permissions);
    }

    public static String join( Object ... permissions){
        return Joiner.on(",").join(permissions);
    }

    public static String addPermissions(String permissions, Long ... add){
        Set<Long> ps = split(permissions);
        for(Long a : add){
            ps.add(a);
        }
        return join(ps);
    }

    public static String removePermissions(String permissions, Long ... remove) {
        Set<Long> ps = split(permissions);
        for(Long a : remove){
            ps.remove(a);
        }
        return join(ps);
    }
}
