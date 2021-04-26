package com.zab.netty.register.server.handler;

import com.zab.netty.common.protocol.RpcRequest;
import com.zab.netty.common.protocol.RpcResponse;
import com.zab.netty.common.utils.StrUtil;
import com.zab.netty.register.boot.ServerRegisterBoot;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ServerRequesthandler
 * @Description TODO
 * @Author zab
 * @Date 2021/4/22 16:37
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@ChannelHandler.Sharable
@Slf4j
public class ServerRequesthandler extends SimpleChannelInboundHandler<RpcRequest> {

    protected ServerRequesthandler() {
    }

    public static final ServerRequesthandler INSTANCE = new ServerRequesthandler();

    private static final ThreadPoolExecutor POOL = new ThreadPoolExecutor(16, 32, 500L,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(50000), new ThreadPoolExecutor.DiscardOldestPolicy());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        POOL.execute(() -> {
            log.info(String.valueOf(msg));

            RpcResponse response = new RpcResponse();
            response.setRequestId(msg.getRequestId());

            try {
                Object res = handleRequest(msg);
                response.setResult(res);
            } catch (Exception e) {
                e.printStackTrace();
                response.setException(e);
            }
            log.info(String.valueOf(response));
            ctx.pipeline().writeAndFlush(response);
        });
    }

    private Object handleRequest(RpcRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String className = request.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        Object obj = ServerRegisterBoot.imClassMap.get(StrUtil.decapitalize(className));
        Class<?> clazz = obj.getClass();
        Method method = clazz.getMethod(methodName, parameterTypes);
        return method.invoke(obj, parameters);

    }
}
