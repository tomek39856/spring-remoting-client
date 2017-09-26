package com.github.tomek39856.springremotingclient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.lang.reflect.Field;

class PackageChangingObjectOutputStream extends ObjectOutputStream {
    private static final String INVOCATION_PACKAGE = "com.github.tomek39856.springremotingclient.RemoteInvocation";
    private static final String SPRING_INVOCATION_PACKAGE = "org.springframework.remoting.support.RemoteInvocation";

    public PackageChangingObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeClassDescriptor(ObjectStreamClass descriptor) throws IOException {
        if (descriptor.getName().equals(INVOCATION_PACKAGE)) {
          tryChangePackage(descriptor);
        }

        super.writeClassDescriptor(descriptor);
    }

    private void tryChangePackage(ObjectStreamClass descriptor) {
        try {
            Field nameField = descriptor.getClass().getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(descriptor, SPRING_INVOCATION_PACKAGE);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}