package com.zab.netty.rpc.client;

import com.zab.netty.common.coder.RpcProtocalDecoder;
import com.zab.netty.common.coder.RpcProtocalEncoder;
import com.zab.netty.common.coder.UnpackerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @ClassName RpcClientHandlerInitializer
 * @Description TODO
 * @Author zab
 * @Date 2021/4/23 11:50
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
public class RpcClientHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private RpcClient rpcClient;

    public RpcClientHandlerInitializer(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    /**
     * 每次都连接会重新构建pipleline
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new UnpackerHandler());
        pipeline.addLast(RpcProtocalDecoder.INSTANCE);
        pipeline.addLast(rpcClient);
        pipeline.addLast(RpcProtocalEncoder.INSTANCE);
    }
}
