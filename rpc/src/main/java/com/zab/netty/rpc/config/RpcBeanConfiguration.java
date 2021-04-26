package com.zab.netty.rpc.config;

import com.zab.netty.common.config.RpcProperties;
import com.zab.netty.rpc.loadBalance.LoadBalanceContext;
import com.zab.netty.rpc.loadBalance.RandomStrategy;
import com.zab.netty.rpc.loadBalance.RoundRobinStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RpcBeanConfiguration
 * @Description TODO
 * @Author zab
 * @Date 2021/4/26 8:48
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Configuration
public class RpcBeanConfiguration {

    private final RpcProperties properties;

    public RpcBeanConfiguration(RpcProperties properties) {
        this.properties = properties;
    }

    @Bean
    public LoadBalanceContext loadBalanceContext() {
        LoadBalanceContext loadBalanceContext = new LoadBalanceContext();
        String loadBalance = properties.getLoadblacne().trim();
        switch (loadBalance) {
            case "roundRobin":
                loadBalanceContext.setLoadBalanceStrategy(new RoundRobinStrategy());
                break;
            case "random":
                loadBalanceContext.setLoadBalanceStrategy(new RandomStrategy());
            case "hash": // todo
                break;
            default:
                loadBalanceContext.setLoadBalanceStrategy(new RandomStrategy());
                break;
        }
        return loadBalanceContext;
    }

}
