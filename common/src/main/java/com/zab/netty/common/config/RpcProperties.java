package com.zab.netty.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @ClassName RpcProperties
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:39
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Component
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true)
@Data
public class RpcProperties {

    /**
     * 服务名
     */
    @Value("${spring.application.name:null}")
    private String applicationName;

    /**
     * 本服务的ip地址的端口
     */
    @Value("${server.port:8080}")
    private String serverPort;

    /**
     * 服务注册中心地址
     */
    @Value("${netty.rpc.register.address:127.0.0.0.1:2181}")
    private String registerAddress;

    /**
     * 负载均衡策略配置
     * <p>
     * 配置选项:
     * roundRobin(轮询)
     * random（随机）
     */
    @Value("${netty.rpc.loadblacne:random}")
    private String loadblacne;

}
