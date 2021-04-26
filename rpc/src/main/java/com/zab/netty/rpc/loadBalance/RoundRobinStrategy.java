package com.zab.netty.rpc.loadBalance;

import java.util.List;

/**
 * @ClassName RoundRobinStrategy
 * @Description 轮询策略
 * @Author zab
 * @Date 2021/4/23 11:33
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class RoundRobinStrategy extends LoadBalanceStrategy {

    private volatile static Integer pos = 0;

    @Override
    public synchronized <T> T getServer(List<T> list) {
        if (pos >= list.size()) {
            pos = 0;
        }
        return list.get(pos++);
    }
}
