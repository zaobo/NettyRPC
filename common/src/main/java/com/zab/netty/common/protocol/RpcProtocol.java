package com.zab.netty.common.protocol;

/**
 * @ClassName RpcProtocol
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:15
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public abstract class RpcProtocol {

    /**
     * 请求的数据的类型
     *
     * @return
     */
    public abstract Byte getRequestType();

}
