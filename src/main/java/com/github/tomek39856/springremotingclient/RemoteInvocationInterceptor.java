package com.github.tomek39856.springremotingclient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class RemoteInvocationInterceptor implements InvocationHandler {
    private final RemoteInvocationExecutor remoteInvocationExecutor;

    RemoteInvocationInterceptor(RemoteInvocationExecutor remoteInvocationExecutor) {
        this.remoteInvocationExecutor = remoteInvocationExecutor;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RemoteInvocationResult remoteInvocationResult = remoteInvocationExecutor.invoke(
                new RemoteInvocation(method.getName(), method.getParameterTypes(), args)
        );

        return remoteInvocationResult.getValue();
    }
}