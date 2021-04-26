package com.zab.netty.rpc.proxy;

import com.zab.netty.common.protocol.RpcRequest;
import com.zab.netty.common.protocol.RpcResponse;
import com.zab.netty.common.utils.RpcCacheHolder;
import com.zab.netty.rpc.client.RpcClient;
import com.zab.netty.rpc.degradation.Callbacker;
import com.zab.netty.rpc.loadBalance.LoadBalanceContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName RpcInvoker
 * @Description TODO
 * @Author zab
 * @Date 2021/4/23 10:26
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Slf4j
public class RpcInvoker implements InvocationHandler {

    private String serverName;

    public RpcInvoker(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString().replaceAll("-", ""));
        String className = method.getDeclaringClass().getName();
        request.setClassName(className);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);

        List<String> providerList = RpcCacheHolder.SERVER_PROVIDERS.get(serverName);
        if (null == providerList || providerList.size() == 0) {
            Callbacker.Builder(request)
                    .ifNotCallBack(() -> {
                        throw new RuntimeException(serverName + "服务不存在，调用失败");
                    })
                    .orElseSet(new RuntimeException(serverName + "服务不存在，调用失败"));
        }

        // 负载均衡
        // ConsistentHashStrategy strategy = new ConsistentHashStrategy();
        //String serverIp = strategy.initHashRing(providerList).getServer(rpcRequest.getMethodName()); //相同的方法名会被负载到相同的节点

        LoadBalanceContext loadBalanceContext = RpcCacheHolder.APPLICATION_CONTEXT.getBean(LoadBalanceContext.class);
        String serverIp = loadBalanceContext.executeLoadBalances(providerList);
        log.info("负载均衡：调用服务{}的{}服务器节点", serverName, serverIp);

        String[] hosts = serverIp.split(":");
        RpcClient client = new RpcClient(hosts[0].trim(), Integer.parseInt(hosts[1].trim()), serverName);

        RpcResponse response;
        try {
            response = client.sendRequest(request);
            if (response != null && response.getException() != null) {
                Exception e = response.getException();
                return Callbacker.Builder(request).ifNotCallBack(e::printStackTrace).orElseSet(e);
            }
        } catch (Exception e) {
            // 捕获异常，容错处理
            return Callbacker.Builder(request).ifNotCallBack(e::printStackTrace).orElseSet(e);
        }
        return response != null ? response.getResult() : null;
    }
}
