package com.github.tomek39856.springremotingclient;

import java.lang.reflect.Proxy;
import java.util.Optional;

public class HttpProxyFactory<T> {
    private final Class<T> remoteInterface;
    private final String url;
    private RequestPropertiesProvider requestPropertiesProvider;

    public HttpProxyFactory(Class<T> remoteInterface, String url) {
        this.remoteInterface = remoteInterface;
        this.url = url;
    }

    public <T> T buildProxy() {
        RemoteInvocationExecutor remoteInvocationExecutor =
                new RemoteInvocationExecutor(url, Optional.ofNullable(requestPropertiesProvider));

        return (T) Proxy.newProxyInstance(
                HttpProxyFactory.class.getClassLoader(),
                new Class[]{remoteInterface},
                new RemoteInvocationInterceptor(remoteInvocationExecutor)
        );
    }

    public void setRequestPropertiesProvider(RequestPropertiesProvider requestPropertiesProvider) {
        this.requestPropertiesProvider = requestPropertiesProvider;
    }
}