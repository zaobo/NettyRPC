package com.zab.netty.common.coder;

import com.zab.netty.common.utils.CodeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PpcProtocalDecoder
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 16:55
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/

@ChannelHandler.Sharable
@NoArgsConstructor
public class RpcProtocalDecoder extends MessageToMessageDecoder<ByteBuf> {

    public static final RpcProtocalDecoder INSTANCE = new RpcProtocalDecoder();

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf msg, List<Object> out) throws Exception {
        out.add(CodeUtil.decoder(msg));
    }
}
