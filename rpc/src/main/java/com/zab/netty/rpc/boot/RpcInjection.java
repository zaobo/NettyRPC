package com.zab.netty.rpc.boot;

import com.zab.netty.rpc.annotation.EnableNettyRpc;
import com.zab.netty.rpc.annotation.NettyRpc;
import org.reflections.Reflections;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName RpcInjection
 * @Description TODO
 * @Author zab
 * @Date 2021/4/23 10:16
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class RpcInjection implements ImportBeanDefinitionRegistrar {
    /**
     * @param metadata:当前类的注解信息；                             就是使用了@Import({RpcBoot.class})并且RpcBoot是ImportBeanDefinitionRegistrar的实现类才会被调用了
     * @param registry:注册类，其registerBeanDefinition()可以注册bean
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableNettyRpc.class.getName());
        String[] provider = (String[]) attributes.get("provider");

        Reflections reflections = new Reflections(provider);
        // honorInherited-不包含子类
        Set<Class<?>> rpcClass = reflections.getTypesAnnotatedWith(NettyRpc.class, true);
        rpcClass.forEach(clazz -> {
            NettyRpc rpc = clazz.getAnnotation(NettyRpc.class);
            String serverName = StringUtils.isEmpty(rpc.name()) ? rpc.name() : rpc.name();
        });
    }
}
