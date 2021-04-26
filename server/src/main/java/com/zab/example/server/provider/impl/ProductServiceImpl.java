package com.zab.example.server.provider.impl;

import com.zab.example.server.provider.ProductService;
import com.zab.netty.register.annotation.NService;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @Author zab
 * @Date 2021/4/26 11:01
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@NService
public class ProductServiceImpl implements ProductService {
    @Override
    public List<String> getAllProductByUserId(String id) {
        return Arrays.asList("苹果","西瓜","饮料");
    }

    @Override
    public void buyOne(Integer productId) {
        System.out.println("购买商品:" + productId);
    }
}
