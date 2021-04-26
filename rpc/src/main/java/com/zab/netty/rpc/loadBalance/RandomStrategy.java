package com.zab.netty.rpc.loadBalance;

import java.util.List;
import java.util.Random;

/**
 * @ClassName RandomStrategy
 * @Description 随机策略
 * @Author zab
 * @Date 2021/4/23 11:32
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class RandomStrategy extends LoadBalanceStrategy {
    @Override
    public <T> T getServer(List<T> list) {
        int index = new Random().nextInt(list.size());
        return list.get(index);
    }
}
