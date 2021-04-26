package com.zab.netty.rpc.client;

import com.zab.netty.common.protocol.RpcRequest;
import com.zab.netty.common.protocol.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RpcClient
 * @Description 默认每个handler对象只能使用一次(内部用add状态维护)，标记sharable就可以使用多次，否则进行重连时抛出异常
 * @Author zab
 * @Date 2021/4/23 11:39
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@ChannelHandler.Sharable
@Slf4j
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {

    /**
     * 连接失败重连接次数
     */
    private static final int MAX_RETRY_TIMES = 2;
    /**
     * 请求超时重发次数
     */
    private static final int MAX_RETRY_SEND_TIMES = 3;

    private Bootstrap bootstrap;
    private NioEventLoopGroup mainGroup;
    private final String ip;
    private final int port;
    private final String serverName;
    private Channel providerChannel;
    private RpcResponse response;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public RpcClient(String ip, int port, String serverName) {
        this.ip = ip;
        this.port = port;
        this.serverName = serverName;
        initConnect();
    }

    private void initConnect() {
        bootstrap = new Bootstrap();
        mainGroup = new NioEventLoopGroup();
        bootstrap.group(mainGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new RpcClientHandlerInitializer(this));

    }

    private void connect(int remainingConnectTimes) {
        try {
            bootstrap.connect(ip, port).addListener(future -> {
                if (future.isSuccess()) {
                    providerChannel = ((ChannelFuture) future).channel();
                    countDownLatch.countDown();
                } else if (remainingConnectTimes <= 0) {
                    log.error("重试次数已用完，放弃连接！");
                } else {
                    // 第几次
                    int order = (MAX_RETRY_TIMES - remainingConnectTimes) + 1;
                    log.error("{}：连接失败，第{}次连接......", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()), order);
                    bootstrap.config().group()
                            .schedule(() -> connect(remainingConnectTimes - 1), (1 << order) * 200, TimeUnit.MILLISECONDS);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RpcResponse sendRequest(RpcRequest request) {
        connect(MAX_RETRY_TIMES);

        try {
            boolean await = countDownLatch.await(3, TimeUnit.SECONDS);
            if (!await) {
                throw new RuntimeException(serverName + "服务连接异常");
            }

            send(MAX_RETRY_SEND_TIMES, request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mainGroup.shutdownGracefully();
        }
        return this.response;
    }

    private void send(int remainingTimes, RpcRequest request) throws InterruptedException {
        log.info("第{}次发送", (MAX_RETRY_SEND_TIMES - remainingTimes) + 1);
        if (remainingTimes <= 0) {
            throw new RuntimeException("请求服务超时");
        } else {
            providerChannel.writeAndFlush(request);
            // 超时阻塞等待关闭于服务端的连接
            boolean status = providerChannel.closeFuture().await(2, TimeUnit.SECONDS);
            if (!status) {
                send(remainingTimes - 1, request);
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {
        this.response = msg;
        ctx.close();
    }
}
