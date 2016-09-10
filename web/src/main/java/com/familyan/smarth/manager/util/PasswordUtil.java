package com.familyan.smarth.manager.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Admin on 2015/8/18.
 */
public class PasswordUtil {

    public static String encrypt(String s){
        return DigestUtils.md5Hex(s + "Laden");
    }
}
