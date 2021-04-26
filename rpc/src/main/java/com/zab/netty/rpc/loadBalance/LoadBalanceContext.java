package com.zab.netty.rpc.loadBalance;

import lombok.Setter;

import java.util.List;

/**
 * @ClassName LoadBalanceContext
 * @Description TODO
 * @Author zab
 * @Date 2021/4/23 11:31
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Setter
public class LoadBalanceContext {

    private LoadBalanceStrategy loadBalanceStrategy;

    public <T> T executeLoadBalances(List<T> list) {
        return loadBalanceStrategy.getServer(list);
    }

}
