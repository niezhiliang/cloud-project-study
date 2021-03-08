package com.niezhiliang.cloud.zuul.fallback;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author : niezhiliang
 * @Date : 2021/3/8
 */
@Component
public class FilterBack implements FallbackProvider {
    /**
     * 设置拦截的路由 *表示所有
     * @return
     */
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            /**
             * 设置返回的http状态值
             * @return
             * @throws IOException
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST;
            }

            /**
             * 该值不能少，不然前端请求会一直阻塞不返回
             * @return
             * @throws IOException
             */
            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.BAD_REQUEST.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return "message";
            }

            @Override
            public void close() {

            }

            /**
             * 返回的body
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                JSONObject json = new JSONObject();
                json.put("code",-1);
                json.put("message","网关异常默认返回");

                return new ByteArrayInputStream(json.toJSONString().getBytes());
            }

            /**
             * 返回请求头
             * @return
             */
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }
        };
    }
}
