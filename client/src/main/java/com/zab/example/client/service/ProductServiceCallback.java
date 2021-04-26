package com.zab.example.client.service;

import com.zab.netty.rpc.degradation.Callback;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName ProductServiceCallback
 * @Description TODO
 * @Author zab
 * @Date 2021/4/26 11:10
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class ProductServiceCallback extends Callback implements ProductService {
    @Override
    public List<String> getAllProductByUserId(String id) {
        Throwable throwable = getThrowable();
        System.err.println("getAllProductByUserId服务调用失败： "+throwable.getMessage());
        return Arrays.asList("获取商品失败");
    }

    @Override
    public void buyOne(Integer productId) {
        Throwable throwable = getThrowable();
        System.err.println("buyOne服务调用失败： "+throwable.getMessage());
    }
}
