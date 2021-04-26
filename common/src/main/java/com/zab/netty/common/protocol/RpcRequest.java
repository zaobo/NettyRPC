package com.zab.netty.common.protocol;

import com.zab.netty.common.enums.RequestTypeEnum;
import lombok.Data;

/**
 * @ClassName RpcRequest
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:17
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Data
public class RpcRequest extends RpcProtocol {

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 调用类名
     */
    private String className;

    /**
     * 调用方法名
     */
    private String methodName;

    /**
     * 方法参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     * 方法参数
     */
    private Object[] parameters;

    @Override
    public Byte getRequestType() {
        return RequestTypeEnum.REQUEST.getType();
    }
}
