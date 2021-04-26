package com.zab.netty.register.annotation;

import com.zab.netty.register.boot.ServerRegisterBoot;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName EableServerRegister
 * @Description TODO
 * @Author zab
 * @Date 2021/4/22 16:16
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ServerRegisterBoot.class})
public @interface EnableServerRegister {

    String[] provider() default {};

}
