package com.zab.example.server.provider;

import java.util.List;

/**
 * @ClassName ProductService
 * @Description TODO
 * @Author zab
 * @Date 2021/4/26 10:58
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public interface ProductService {

    List<String> getAllProductByUserId(String id);

    void buyOne(Integer productId);

}
