package com.zab.netty.register.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Service
 * @Description TODO
 * @Author zab
 * @Date 2021/4/22 16:07
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NService {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

}
