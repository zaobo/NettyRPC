package com.zab.example.client.controller;

import com.zab.example.client.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ProductController
 * @Description TODO
 * @Author zab
 * @Date 2021/4/26 11:08
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/b")
    public void testProductServiceBuyOne(){
        productService.buyOne(47289384);
    }

    @RequestMapping("/c")
    public List<String> testProductServiceGetAll(){
        return productService.getAllProductByUserId("5324534");
    }

}
