package com.zab.netty.rpc.boot;

import com.zab.netty.common.utils.RpcCacheHolder;
import com.zab.netty.common.utils.ZkUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName RpcBoot
 * @Description TODO
 * @Author zab
 * @Date 2021/4/23 10:05
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Component
@ComponentScan(basePackages = {"com.zab.netty.*"})
public class RpcBoot implements ApplicationContextAware {

    @Resource
    private ZkUtil zkUtil;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        RpcCacheHolder.APPLICATION_CONTEXT = context;
        RpcCacheHolder.SUBSCRIBE_SERVICE.forEach(e -> zkUtil.discover(e));
    }
}
