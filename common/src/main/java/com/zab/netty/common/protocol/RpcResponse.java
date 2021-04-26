package com.zab.netty.common.protocol;

import com.zab.netty.common.enums.RequestTypeEnum;
import lombok.Data;

/**
 * @ClassName RpcResponse
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:24
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Data
public class RpcResponse extends RpcProtocol {
    /**
     * 对应的请求id
     */
    private String requestId;

    /**
     * 请求异常信息
     */
    private Exception exception;

    /**
     * 结果
     */
    private Object result;

    @Override
    public Byte getRequestType() {
        return RequestTypeEnum.RESPONSE.getType();
    }
}
