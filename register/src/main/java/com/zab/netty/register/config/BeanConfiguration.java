package com.zab.netty.register.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName BeanConfiguration
 * @Description TODO
 * @Author zab
 * @Date 2021/4/23 9:44
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Configuration
@ComponentScan(basePackages = {"com.zab.netty.*"})
public class BeanConfiguration {
}
