package com.github.tomek39856.springremotingclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

class RemoteInvocationExecutor {
    private final String remoteServiceAddress;
    private final Map<String, String> requestProperties;

    RemoteInvocationExecutor(String url, Map<String, String> requestProperties) {
        this.remoteServiceAddress = url;
        this.requestProperties = requestProperties;
    }

    RemoteInvocationResult invoke(RemoteInvocation remoteInvocation) throws IOException, ClassNotFoundException {
        HttpURLConnection httpURLConnection = prepareConnection();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        writeRemoteInvocationIntoOutputStream(outputStream, remoteInvocation);
        outputStream.writeTo(httpURLConnection.getOutputStream());
        ObjectInputStream objectInputStream = new PackageChangingObjectInputStream(httpURLConnection.getInputStream());

        return (RemoteInvocationResult) objectInputStream.readObject();
    }

    private void writeRemoteInvocationIntoOutputStream(ByteArrayOutputStream outputStream, RemoteInvocation remoteInvocation) throws IOException {
        PackageChangingObjectOutputStream objectOutputStream = new PackageChangingObjectOutputStream(outputStream);
        objectOutputStream.writeObject(remoteInvocation);
    }

    private HttpURLConnection prepareConnection() throws IOException {
        URL url = new URL(remoteServiceAddress);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.addRequestProperty("Content-Type", " application/x-java-serialized-object");
        requestProperties.forEach((key, value) -> {httpURLConnection.addRequestProperty(key, value);});
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");

        return httpURLConnection;
    }
}