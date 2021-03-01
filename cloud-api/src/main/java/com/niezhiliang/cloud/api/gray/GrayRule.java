package com.niezhiliang.cloud.api.gray;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import java.util.List;
import java.util.Map;

/**
 * @Classname niezhiliang
 * @Date 2021/2/23 19:42
 * 自定义路由规则
 */
public class GrayRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {

        return chooseService(getLoadBalancer(),key);
    }

    /**
     * 根据灰度规则，取对应的服务
     * @param loadBalancer
     * @param key
     * @return
     */
    private Server chooseService(ILoadBalancer loadBalancer, Object key) {
        System.out.println("灰度测试");
        Server server = null;
        while (server == null) {
            //获取所有可用的服务
            List<Server> reachableServers = loadBalancer.getReachableServers();
            //遍历可用的服务
            for (Server reachableServer : reachableServers) {
                Map<String, String> metadata = ((DiscoveryEnabledServer) reachableServer).getInstanceInfo().getMetadata();
                //元数据的version属性
                String version = metadata.get("version");
                //从ThreadLocal中取出aop放入的version
                Map<String,String> map = ThreadLocalUtils.get();
                String headVersion = "";
                if (map != null && map.containsKey("version")) {
                    headVersion = map.get("version");
                }
                if (version.trim().equals(headVersion)) {
                    server = reachableServer;
                    return reachableServer;
                }
            }
        }
        return server;
    }
}
