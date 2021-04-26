package com.zab.netty.common.utils;

import org.springframework.util.StringUtils;

/**
 * @ClassName StrUtil
 * @Description TODO
 * @Author zab
 * @Date 2021/4/26 16:56
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class StrUtil {

    public static String decapitalize(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

}
