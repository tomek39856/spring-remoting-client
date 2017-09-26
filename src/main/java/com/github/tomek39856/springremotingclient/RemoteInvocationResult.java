package com.github.tomek39856.springremotingclient;

import java.io.Serializable;

class RemoteInvocationResult implements Serializable {
        private static final long serialVersionUID = 2138555143707773549L;
        private Object value;
        private Throwable exception;

    public Object getValue() {
        return value;
    }
}
