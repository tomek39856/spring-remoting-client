package com.github.tomek39856.springremotingclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

class PackageChangingObjectInputStream extends ObjectInputStream {
    private static final String SPRING_INVOCATION_RESULT_PACKAGE = "org.springframework.remoting.support.RemoteInvocationResult";

    PackageChangingObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        ObjectStreamClass descriptor = super.readClassDescriptor();
        if (descriptor.getName().equals(SPRING_INVOCATION_RESULT_PACKAGE)) {
            return ObjectStreamClass.lookup(RemoteInvocationResult.class);
        }

        return descriptor;
    }
}