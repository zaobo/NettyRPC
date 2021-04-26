package com.zab.netty.register.client;

import com.zab.netty.register.annotation.EnableServerRegister;
import com.zab.netty.register.config.BeanConfiguration;
import com.zab.netty.register.interfaces.Preprocessing;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName BoomRpcDiscoverApplication
 * @Description 服务注册启动器
 * @Author zab
 * @Date 2021/4/22 17:12
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class NettyRpcDiscoverApplication {

    public static AnnotationConfigApplicationContext context;

//    public static bui

    public static class Builder {
        public Builder() {
            if (null == context) {
                synchronized (NettyRpcDiscoverApplication.class) {
                    if (null == context) {
                        context = new AnnotationConfigApplicationContext();
                    }
                }
            }
        }

        public Builder customize(Preprocessing preprocessing) {
            preprocessing.doSomething(context);
            return this;
        }

        public Builder register(Class<?>... annotatedClassed) {
            context.register(annotatedClassed);
            return this;
        }

        public void run(Class<?> primarySource) {
            if (null == primarySource) {
                throw new RuntimeException("NettyRpc discover application start fail, primarySource can not be null");
            }

            EnableServerRegister register = primarySource.getAnnotation(EnableServerRegister.class);
            String[] arrs = register.provider();

            if (arrs.length > 0) {
                context.scan(arrs);
            }

            context.register(BeanConfiguration.class);
            context.refresh();
        }

    }

}
