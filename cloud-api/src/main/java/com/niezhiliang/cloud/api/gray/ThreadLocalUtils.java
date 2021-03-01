package com.niezhiliang.cloud.api.gray;

import org.springframework.stereotype.Component;

/**
 * @Classname niezhiliang
 * @Date 2021/2/23 18:18
 */
@Component
public class ThreadLocalUtils {
    private static final ThreadLocal threadLocal = new ThreadLocal();


    public static  <T> T get() {
        return (T) threadLocal.get();
    }

    public static <T> void set(T t) {
        threadLocal.set(t);
    }
}
