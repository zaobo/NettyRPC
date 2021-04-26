package com.zab.netty.rpc.annotation;

import com.zab.netty.rpc.boot.RpcBoot;
import com.zab.netty.rpc.boot.RpcInjection;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName EnableNettyRpc
 * @Description 开启 EnableNettyRpc 的服务调用功能
 * @Author zab
 * @Date 2021/4/23 10:02
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcBoot.class, RpcInjection.class})
public @interface EnableNettyRpc {
    String[] provider() default {};
}
