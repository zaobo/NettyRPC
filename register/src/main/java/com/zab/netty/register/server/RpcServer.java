package com.zab.netty.register.server;

import com.zab.netty.common.coder.RpcProtocalDecoder;
import com.zab.netty.common.coder.RpcProtocalEncoder;
import com.zab.netty.common.coder.UnpackerHandler;
import com.zab.netty.common.config.RpcProperties;
import com.zab.netty.register.server.handler.ServerRequesthandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @ClassName RpcServer
 * @Description TODO
 * @Author zab
 * @Date 2021/4/22 16:21
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Component
@Slf4j
public class RpcServer {

    @Resource
    private RpcProperties rpcProperties;

    private NioEventLoopGroup parentGroup;

    private NioEventLoopGroup childGroup;

    private ServerBootstrap server;

    public RpcServer() {
        parentGroup = new NioEventLoopGroup(1);
        childGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new UnpackerHandler());
                        pipeline.addLast(RpcProtocalDecoder.INSTANCE);
                        pipeline.addLast(ServerRequesthandler.INSTANCE);
                        pipeline.addLast(RpcProtocalEncoder.INSTANCE);
                    }
                });
    }

    public void start() throws InterruptedException {
        int port = Integer.parseInt(rpcProperties.getServerPort());
        ChannelFuture channelFuture = server.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("{}：端口【{}】绑定成功！", LocalDateTime.now(), port);
            } else {
                log.error("端口【{}】绑定失败", port);
            }
        });
        channelFuture.channel().closeFuture().sync();
    }

    public void destroy() throws InterruptedException {
        if (null != parentGroup) {
            parentGroup.shutdownGracefully().sync();
        }
        if (null != childGroup) {
            childGroup.shutdownGracefully().sync();
        }
    }

}
