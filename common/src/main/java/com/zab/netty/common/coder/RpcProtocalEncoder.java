package com.zab.netty.common.coder;

import com.zab.netty.common.protocol.RpcProtocol;
import com.zab.netty.common.utils.CodeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.NoArgsConstructor;

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
public class RpcProtocalEncoder extends MessageToByteEncoder<RpcProtocol> {

    public static final RpcProtocalEncoder INSTANCE = new RpcProtocalEncoder();

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol msg, ByteBuf out) throws Exception {
        CodeUtil.encoder(out, msg);
    }
}
