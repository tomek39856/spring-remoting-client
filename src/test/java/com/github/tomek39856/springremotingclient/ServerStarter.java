package com.github.tomek39856.springremotingclient;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.springframework.remoting.httpinvoker.SimpleHttpInvokerServiceExporter;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerStarter {
    private static HttpServer httpServer;

    public static void startServer(int port) throws IOException {
        SimpleHttpInvokerServiceExporter simpleHttpInvokerServiceExporter = new SimpleHttpInvokerServiceExporter();
        simpleHttpInvokerServiceExporter.setServiceInterface(TestService.class);
        simpleHttpInvokerServiceExporter.setService(new TestServiceImpl());
        simpleHttpInvokerServiceExporter.afterPropertiesSet();
        Map<String, HttpHandler> contexts = new HashMap<String, HttpHandler>() {{
            put("/test", simpleHttpInvokerServiceExporter);
        }};

        SimpleHttpServerFactoryBean simpleHttpServerFactoryBean = new SimpleHttpServerFactoryBean();
        simpleHttpServerFactoryBean.setContexts(contexts);
        simpleHttpServerFactoryBean.setPort(port);
        simpleHttpServerFactoryBean.afterPropertiesSet();
        httpServer = simpleHttpServerFactoryBean.getObject();
    }

    public static void stopServer() {
        if(httpServer != null) {
            httpServer.stop(1);
        }
    }
}
