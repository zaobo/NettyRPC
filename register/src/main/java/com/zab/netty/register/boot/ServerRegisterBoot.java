package com.zab.netty.register.boot;

import com.zab.netty.common.config.RpcProperties;
import com.zab.netty.common.utils.ZkUtil;
import com.zab.netty.register.annotation.NService;
import com.zab.netty.register.server.RpcServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ServerRegisterBoot
 * @Description TODO
 * @Author zab
 * @Date 2021/4/22 16:17
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Component
@Configuration
@ComponentScan(basePackages = "com.zab.netty.*")
@Slf4j
public class ServerRegisterBoot implements ApplicationContextAware {

    @Resource
    private RpcProperties rpcProperties;
    @Resource
    private ZkUtil zkUtil;
    @Resource
    private RpcServer rpcServer;
    /**
     * 维护本地服务接口和其实现类的关系
     */
    public static Map<String, Object> imClassMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (StringUtils.isEmpty(rpcProperties.getApplicationName())) {
            return;
        }

        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(NService.class);
        serviceMap.values().forEach(service -> {
            String className = service.getClass().getAnnotation(NService.class).value();
            if (StringUtils.isEmpty(className)) {
                className = service.getClass().getName();
            }
            imClassMap.put(className, service);
        });

        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String address = zkUtil.register("/" + rpcProperties.getApplicationName() + "/" + ip + ":" + rpcProperties.getServerPort(), "");
            if (address != null && address != "") {
                log.info("=========================================");
                log.info("服务发布成功");
                log.info("=========================================");
                rpcServer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
