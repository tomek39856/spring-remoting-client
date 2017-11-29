package com.github.tomek39856.springremotingclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

class RemoteInvocationExecutor {
    private final String remoteServiceAddress;
    private final Optional<RequestPropertiesProvider> basicAuthCredentialsProvider;

    RemoteInvocationExecutor(String url, Optional<RequestPropertiesProvider> basicAuthCredentialsProvider) {
        this.remoteServiceAddress = url;
        this.basicAuthCredentialsProvider = basicAuthCredentialsProvider;
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
        addCustomRequestProperties(httpURLConnection);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");

        return httpURLConnection;
    }

    private void addCustomRequestProperties(HttpURLConnection httpURLConnection) {
        basicAuthCredentialsProvider.ifPresent((properties) -> {
            properties.getRequestProperties().forEach(httpURLConnection::addRequestProperty);
        });
    }
}