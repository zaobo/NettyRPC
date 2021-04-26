package com.zab.netty.rpc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName NettyRpc
 * @Description TODO
 * @Author zab
 * @Date 2021/4/23 9:59
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NettyRpc {

    /**
     * 服务名
     *
     * @return
     */
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    /**
     * 降级回调
     *
     * @return
     */
    Class<?> callBack() default void.class;

}
