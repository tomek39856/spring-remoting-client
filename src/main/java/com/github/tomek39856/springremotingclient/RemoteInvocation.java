package com.github.tomek39856.springremotingclient;

import java.io.Serializable;
import java.util.Map;

class RemoteInvocation implements Serializable {
    private static final long serialVersionUID = 6876024250231820554L;
    private String methodName;
    private Class[] parameterTypes;
    private Object[] arguments;
    private Map<String, Serializable> attributes;

    public RemoteInvocation(String methodName, Class[] parameterTypes, Object[] arguments) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }
}