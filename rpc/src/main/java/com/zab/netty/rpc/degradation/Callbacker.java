package com.zab.netty.rpc.degradation;

import com.zab.netty.common.protocol.RpcRequest;
import com.zab.netty.rpc.annotation.NettyRpc;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @ClassName Callbacker
 * @Description 容错处理器
 * @Author zab
 * @Date 2021/4/23 10:34
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Slf4j
public class Callbacker {

    private RpcRequest rpcRequest;

    private Class<?> callBackClass;

    private Class<?> callBack;

    public static Callbacker Builder(RpcRequest rpcRequest) {
        return new Callbacker(rpcRequest);
    }

    private Callbacker(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
        try {
            log.info(rpcRequest.toString());
            callBackClass = Class.forName(rpcRequest.getClassName());
            callBack = callBackClass.getAnnotation(NettyRpc.class).callBack();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Object orElseSet(Throwable e) throws Exception {
        if (shouldCallback()) {
            Object obj = callBack.newInstance();
            Method method = callBack.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
            Method setThrowable = callBack.getMethod("setThrowable", Throwable.class);
            setThrowable.invoke(obj, e);
            return method.invoke(obj, rpcRequest.getParameters());
        } else {
            return null;
        }
    }

    public Callbacker ifNotCallBack(Process process) {
        if (!shouldCallback()) {
            process.doSomething();
        }
        return this;
    }

    private boolean shouldCallback() {
        return callBack != void.class;
    }

    public interface Process {
        void doSomething();
    }

}
