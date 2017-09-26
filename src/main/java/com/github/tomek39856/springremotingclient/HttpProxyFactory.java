package com.github.tomek39856.springremotingclient;

import java.lang.reflect.Proxy;
import java.util.Map;

public class HttpProxyFactory {
    public <T> T buildProxy(Class<T> remoteInterface, String url, Map<String, String> headers) {
        RemoteInvocationExecutor remoteInvocationExecutor = new RemoteInvocationExecutor(url, headers);

        return (T) Proxy.newProxyInstance(
                HttpProxyFactory.class.getClassLoader(),
                new Class[] { remoteInterface },
                new RemoteInvocationInterceptor(remoteInvocationExecutor)
        );
    }
}