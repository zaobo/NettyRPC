package com.zab.example.client.service;

import com.zab.netty.rpc.annotation.NettyRpc;

import java.util.List;

/**
 * @ClassName ProductService
 * @Description TODO
 * @Author zab
 * @Date 2021/4/26 11:08
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@NettyRpc(name = "Server", callBack = ProductServiceCallback.class)
public interface ProductService {
    List<String> getAllProductByUserId(String id);

    void buyOne(Integer productId);
}
