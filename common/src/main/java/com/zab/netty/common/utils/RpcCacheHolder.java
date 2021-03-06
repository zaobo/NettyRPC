package com.zab.netty.common.utils;

import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName RpcCacheHolder
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:48
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class RpcCacheHolder {

    private RpcCacheHolder() {
    }

    /**
     * spring 上下文
     */
    public static ApplicationContext APPLICATION_CONTEXT;


    /**
     * 客户端订阅的服务列表
     */
    public static Set<String> SUBSCRIBE_SERVICE = new TreeSet<>();

    /**
     * 客户端订阅的服务的提供列表
     * key - 服务名
     * value - 对应的提供者服务器列表
     */
    public static Map<String, List<String>> SERVER_PROVIDERS = new ConcurrentHashMap<>();

}
