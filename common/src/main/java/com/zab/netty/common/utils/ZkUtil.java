package com.zab.netty.common.utils;

import com.zab.netty.common.config.RpcProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName ZkUtil
 * @Description TODO
 * @Author zab
 * @Date 2021/4/21 17:48
 * @Company: 中冶赛迪重庆信息技术有限公司
 * Version 1.0
 **/
@Component
@Slf4j
public class ZkUtil {

    private static final String ROOT_NODE = "NettyRpc";

    private final RpcProperties properties;

    public ZkUtil(RpcProperties properties) {
        this.properties = properties;
    }

    public static CuratorFramework zkClient = null;

    private static ReentrantLock updateProviderLock = new ReentrantLock();

    @PostConstruct
    public void init() {
        connect(properties.getRegisterAddress());
    }

    private boolean connect(String zkServerPath) {
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(zkServerPath)
                .retryPolicy(new RetryNTimes(3, 5000))
                .namespace(ROOT_NODE)
                .build();
        zkClient.start();
        return zkClient != null;
    }

    /**
     * 服务注册
     *
     * @param nodePath
     * @param nodeData
     * @return
     */
    public String register(String nodePath, String nodeData) {
        isConnenct();
        String path = null;
        try {
            path = zkClient.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(nodePath, nodeData.getBytes());
        } catch (KeeperException.NodeExistsException e) {
            log.error("NodeExistsException ----服务注册失败，该服务器节点 {} 已经注册,请修改", e.getPath());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 服务发现
     *
     * @param serverName
     * @return
     */
    public List<String> discover(String serverName) {
        isConnenct();
        List<String> serverList = null;
        try {
            PathChildrenCache childrenCache = new PathChildrenCache(zkClient, "/" + serverName, true);
            childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
            addlistener(childrenCache, serverName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serverList;
    }

    private void addlistener(PathChildrenCache childrenCache, String serverName) {
        childrenCache.getListenable().addListener((curatorFramework, event) -> {
            // 创建子节点
            if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                String path = event.getData().getPath();
                String host = path.substring(path.lastIndexOf("/" + 1));
                log.info("服务器上线:{}", path);

                updateProviderLock.lock();
                try {
                    List<String> list = RpcCacheHolder.SERVER_PROVIDERS.getOrDefault(serverName, new ArrayList<>());
                    list.add(host);
                    RpcCacheHolder.SERVER_PROVIDERS.put(serverName, list);
                } finally {
                    updateProviderLock.unlock();
                }
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                // 删除子节点
                String path = event.getData().getPath();
                String host = path.substring(path.lastIndexOf("/" + 1));
                log.info("服务器下线:{}", path);

                updateProviderLock.lock();
                try {
                    List<String> list = RpcCacheHolder.SERVER_PROVIDERS.get(serverName);
                    list.remove(host);
                    RpcCacheHolder.SERVER_PROVIDERS.put(serverName, list);
                } finally {
                    updateProviderLock.unlock();
                }
            }
        });
    }

    private void isConnenct() {
        if (zkClient == null) {
            throw new RuntimeException("have not connect Zookeeper Server");
        }
    }

}
