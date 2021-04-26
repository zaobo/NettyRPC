package com.zab.netty.rpc.degradation;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName Callback
 * @Description TODO
 * @Author zab
 * @Date 2021/4/26 9:33
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Setter
@Getter
public class Callback {
    protected Throwable throwable;
}
