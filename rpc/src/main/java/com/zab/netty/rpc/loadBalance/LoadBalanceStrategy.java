package com.zab.netty.rpc.loadBalance;

import java.util.List;

/**
 * @ClassName LoadBalanceStrategy
 * @Description TODO
 * @Author zab
 * @Date 2021/4/23 11:31
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public abstract class LoadBalanceStrategy {

    public abstract <T> T getServer(List<T> list);

}
